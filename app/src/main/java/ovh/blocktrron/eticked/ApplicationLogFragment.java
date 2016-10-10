package ovh.blocktrron.eticked;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ovh.blocktrron.eticked.dataset.Cards.BaseCard;
import ovh.blocktrron.eticked.dataset.Cards.MessageCard;

public class ApplicationLogFragment extends Fragment {
    private static final String KEY_TICKET_CHECKS = "ticket_checks";
    public static final String TAG = "ApplicationLogFragment";
    private View rootView;
    private RecyclerView recyclerView;

    private ArrayList<BaseCard> applicationLog;
    private BaseCardAdapter baseCardAdapter;

    public ApplicationLogFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_applicationlog, container, false);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_applicationlog);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(llm);
        if (applicationLog == null) {
            if (savedInstanceState != null && savedInstanceState.containsKey(KEY_TICKET_CHECKS)) {
                ArrayList<BaseCard> applicationLog = savedInstanceState.getParcelableArrayList(KEY_TICKET_CHECKS);
                setApplicationLog(applicationLog);
            } else {
                clearApplicationLog();
            }
        } else {
            setApplicationLog(applicationLog);
        }
        return rootView;
    }

    public void setApplicationLog(ArrayList<BaseCard> applicationLog) {
        this.applicationLog = applicationLog;
        baseCardAdapter = new BaseCardAdapter(applicationLog);
        updateApplicationLogRecyclerView();
    }

    private void updateApplicationLogRecyclerView() {
        if (recyclerView != null) {
            recyclerView.setAdapter(baseCardAdapter);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle saveInstanceState) {
        if (applicationLog != null) {
            saveInstanceState.putParcelableArrayList(KEY_TICKET_CHECKS, applicationLog);
        }
        super.onSaveInstanceState(saveInstanceState);
    }

    public void clearApplicationLog() {
        ArrayList<BaseCard> tmpApplicationLog = new ArrayList<>();
        tmpApplicationLog.add(new MessageCard(getString(R.string.info_place_card)));
        setApplicationLog(tmpApplicationLog);
    }
}
