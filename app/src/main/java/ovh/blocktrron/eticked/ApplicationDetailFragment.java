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


public class ApplicationDetailFragment extends Fragment {
    public static final String TAG = "ApplicationDetailFragment";
    public static final String KEY_APPLICATION_DATA = "key_application_data";

    private View rootView;
    private RecyclerView recyclerView;

    private BaseCardAdapter baseCardAdapter;

    public ApplicationDetailFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_application_detail, container, false);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_applicationdetail);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(llm);

        if (savedInstanceState != null &&
                savedInstanceState.getParcelableArrayList(KEY_APPLICATION_DATA) != null) {
            ArrayList<BaseCard> applicationDetails = savedInstanceState.getParcelableArrayList(KEY_APPLICATION_DATA);
            setApplicationDetails(applicationDetails);
        } else {
            clearApplicationDetails();
        }

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle saveInstanceState) {
        if (baseCardAdapter != null && baseCardAdapter.getDataset().size() > 0) {
            saveInstanceState.putParcelableArrayList(KEY_APPLICATION_DATA, baseCardAdapter.getDataset());
        }
        super.onSaveInstanceState(saveInstanceState);
    }

    public void setApplicationDetails(ArrayList<BaseCard> in) {
        baseCardAdapter = new BaseCardAdapter(in);
        updateApplicationDetailsRecyclerView();
    }

    public void updateApplicationDetailsRecyclerView() {
        if (recyclerView != null) {
            recyclerView.setAdapter(baseCardAdapter);
            recyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    public void clearApplicationDetails() {
        ArrayList<BaseCard> tmpApplicationLog = new ArrayList<>();
        tmpApplicationLog.add(new MessageCard(getString(R.string.info_place_card)));
        setApplicationDetails(tmpApplicationLog);
    }
}
