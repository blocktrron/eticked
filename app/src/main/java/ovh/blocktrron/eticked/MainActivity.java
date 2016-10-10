package ovh.blocktrron.eticked;

import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.IOException;
import java.util.ArrayList;

import ovh.blocktrron.eticked.dataset.Cards.ApplicationActivationCard;
import ovh.blocktrron.eticked.dataset.Cards.ApplicationDetailsCard;
import ovh.blocktrron.eticked.dataset.Cards.BaseCard;
import ovh.blocktrron.eticked.dataset.Cards.JourneyCard;
import ovh.blocktrron.eticked.dataset.Cards.TicketActivationCard;
import ovh.blocktrron.eticked.dataset.eticket.Station;

public class MainActivity extends AppCompatActivity {
    public static final String KEY_CURRENT_FRAGMENT = "current_fragment";
    public static final String KEY_CURRENT_NAVDRAWER_ELEMENT = "current_navdrawer_element";

    private String currentFragment;

    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    private ApplicationLogFragment applicationLogFragment;
    private ApplicationDetailFragment applicationDetailFragment;

    private StationProvider stationProvider;

    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAdapter();
            }
        });

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass())
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        stationProvider = new StationProvider(getApplicationContext());

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nvView);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        int size = navigationView.getMenu().size();
                        for (int i = 0; i < size; i++) {
                            navigationView.getMenu().getItem(i).setChecked(false);
                        }

                        menuItem.setChecked(true);

                        switch (menuItem.getItemId()) {
                            case R.id.action_switch_applog:
                                switchFragment(ApplicationLogFragment.TAG);
                                break;
                            case R.id.action_switch_appdetails:
                                switchFragment(ApplicationDetailFragment.TAG);
                                break;
                        }
                        drawerLayout.closeDrawers();
                        return true;
                    }
                });

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // code here will execute once the drawer is opened
                getSupportActionBar().setTitle(getTitle());
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Code here will execute once drawer is closed
                getSupportActionBar().setTitle(getTitle());
                invalidateOptionsMenu();
            }
        };
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        applicationLogFragment = (ApplicationLogFragment) getSupportFragmentManager()
                .findFragmentByTag(ApplicationLogFragment.TAG);

        if (applicationLogFragment == null) {
            applicationLogFragment = new ApplicationLogFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_activity_fragment, applicationLogFragment, ApplicationLogFragment.TAG)
                    .commit();
            currentFragment = ApplicationLogFragment.TAG;
        }

        applicationDetailFragment = (ApplicationDetailFragment) getSupportFragmentManager()
                .findFragmentByTag(ApplicationDetailFragment.TAG);

        if (applicationDetailFragment == null) {
            applicationDetailFragment = new ApplicationDetailFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_activity_fragment, applicationDetailFragment, ApplicationDetailFragment.TAG)
                    .hide(applicationDetailFragment)
                    .commit();
            getSupportFragmentManager().beginTransaction()
                    .hide(applicationDetailFragment)
                    .commit();
        }

        if (savedInstanceState == null && getIntent() != null &&
                NfcAdapter.ACTION_TECH_DISCOVERED.equals(getIntent().getAction())) {
            Tag tag = getIntent().getParcelableExtra(NfcAdapter.EXTRA_TAG);
            readTransactionLog(tag);
        } else if (savedInstanceState != null) {
            if (savedInstanceState.getString(KEY_CURRENT_FRAGMENT) != null) {
                currentFragment = savedInstanceState.getString(KEY_CURRENT_FRAGMENT);
                switchFragment(currentFragment);
            }

            if (savedInstanceState.getInt(KEY_CURRENT_NAVDRAWER_ELEMENT, -1) != -1) {
                navigationView.getMenu().getItem(savedInstanceState.getInt(KEY_CURRENT_NAVDRAWER_ELEMENT))
                        .setChecked(true);
            }
        } else {
            navigationView.getMenu().getItem(0).setChecked(true);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    protected void onSaveInstanceState(Bundle saveInstanceState) {
        saveInstanceState.putString(KEY_CURRENT_FRAGMENT, currentFragment);
        int selected_element = -1;
        for (int i = 0; i < navigationView.getMenu().size(); i++) {
            if (navigationView.getMenu().getItem(i).isChecked()) {
                selected_element = i;
            }
        }
        saveInstanceState.putInt(KEY_CURRENT_NAVDRAWER_ELEMENT, selected_element);
        super.onSaveInstanceState(saveInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (nfcAdapter != null) {
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);
        } else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage(R.string.error_missing_nfc_message)
                    .setTitle(R.string.error_missing_nfc_title)
                    .setCancelable(false)
                    .setPositiveButton(R.string.action_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            moveTaskToBack(true);
                        }
                    });
            alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (nfcAdapter != null) {
            nfcAdapter.disableForegroundDispatch(this);
        } else {
            alertDialog.cancel();
        }
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        readTransactionLog(tag);
    }

    private void readTransactionLog(Tag tag) {
        IsoDep card = IsoDep.get(tag);

        if (card == null) {
            Snackbar.make(findViewById(R.id.coordinatorLayout),
                    R.string.error_card_incompatible,
                    Snackbar.LENGTH_SHORT).show();
            return;
        }

        try {
            byte[] reset_card = new byte[]{(byte) 0x00, (byte) 0xA4, (byte) 0x00, (byte) 0x00, (byte) 0x00};
            byte[] select_vdv = new byte[]{(byte) 0x00, (byte) 0xA4, (byte) 0x04,
                    (byte) 0x00, (byte) 0x0C, (byte) 0xD2, (byte) 0x76, (byte) 0x00, (byte) 0x01,
                    (byte) 0x35, (byte) 0x4B, (byte) 0x41, (byte) 0x4E, (byte) 0x4D, (byte) 0x30,
                    (byte) 0x31, (byte) 0x00, (byte) 0x00};

            card.connect();
            card.transceive(reset_card);
            byte[] response = card.transceive(select_vdv);
            if (response[response.length - 2] == (byte) 0x90 && response[response.length - 1] == (byte) 0x00) {
                ArrayList<BaseCard> applicationLog = new ArrayList<>();
                for (int i = 1; i < 11; i++) {
                    byte[] byteResponse = card.transceive(new byte[]{(byte) 0x00, (byte) 0xCA,
                            (byte) 0x01, (byte) 0xF0, (byte) 0x02, (byte) 0xE5, (byte) i, (byte) 0x00});
                    if (byteResponse.length > 2) {
                        if (byteResponse[0] == (byte) 0xF1) {
                            JourneyCard tc = new JourneyCard(byteResponse);
                            int stationID = tc.transactionData.location.getID().intValue();
                            Station station = stationProvider.getStation(stationID);
                            if (station != null) {
                                tc.transactionData.location.setName(station.getName());
                            } else {
                                tc.transactionData.location
                                        .setName(getString(R.string.info_not_available_short));
                            }
                            applicationLog.add(tc);
                        } else if (byteResponse[0] == (byte) 0xF6) {
                            TicketActivationCard ta = new TicketActivationCard(byteResponse);
                            applicationLog.add(ta);
                        } else if (byteResponse[0] == (byte) 0xF7) {
                            ApplicationActivationCard aa = new ApplicationActivationCard(byteResponse);
                            applicationLog.add(aa);
                        }
                    }
                }
                applicationLogFragment.setApplicationLog(applicationLog);

                card.transceive(new byte[]{(byte) 0x00, (byte) 0xA4, (byte) 0x00, (byte) 0x00, (byte) 0x00});
                card.transceive(new byte[]{(byte) 0x00, (byte) 0xA4, (byte) 0x04,
                        (byte) 0x00, (byte) 0x0C, (byte) 0xD2, (byte) 0x76, (byte) 0x00, (byte) 0x01,
                        (byte) 0x35, (byte) 0x4B, (byte) 0x41, (byte) 0x4E, (byte) 0x4D, (byte) 0x30,
                        (byte) 0x31, (byte) 0x00, (byte) 0x00});

                ArrayList<BaseCard> applicationDetailEntries = new ArrayList<>();
                applicationDetailEntries.add(new ApplicationDetailsCard(card.transceive(new byte[]{(byte) 0x00, (byte) 0xCA,
                        (byte) 0x01, (byte) 0xF0, (byte) 0x02, (byte) 0xE2, (byte) 0x00, (byte) 0x00})));
                applicationDetailFragment.setApplicationDetails(applicationDetailEntries);

                card.close();

                Snackbar.make(findViewById(R.id.coordinatorLayout),
                        R.string.info_read_card_successful,
                        Snackbar.LENGTH_SHORT).show();
            } else {
                Snackbar.make(findViewById(R.id.coordinatorLayout),
                        R.string.error_card_no_eticket,
                        Snackbar.LENGTH_SHORT).show();
            }

        } catch (IOException e) {
            Snackbar.make(findViewById(R.id.coordinatorLayout),
                    R.string.error_card_communication,
                    Snackbar.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    protected void clearAdapter() {
        applicationLogFragment.clearApplicationLog();
        applicationDetailFragment.clearApplicationDetails();
    }

    protected void switchFragment(String tag) {
        switch (tag) {
            case ApplicationLogFragment.TAG:
                getSupportFragmentManager().beginTransaction()
                        .hide(applicationDetailFragment)
                        .show(applicationLogFragment)
                        .commit();
                currentFragment = ApplicationLogFragment.TAG;
                break;
            case ApplicationDetailFragment.TAG:
                getSupportFragmentManager().beginTransaction()
                        .hide(applicationLogFragment)
                        .show(applicationDetailFragment)
                        .commit();
                currentFragment = ApplicationDetailFragment.TAG;
                break;
        }
    }
}
