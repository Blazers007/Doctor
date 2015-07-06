package com.blazers.app.doctor.ui.fragments.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.*;

import android.widget.ListView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.blazers.app.doctor.adapter.CureRecordAdapter;
import com.blazers.app.doctor.R;

/**
 * Created by liang on 2015/5/7.
 */
public class FragCureRecord extends Fragment {

    private View root;
    @InjectView(R.id.cure_record_list) ListView listView;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_cure_record, container, false);
            ButterKnife.inject(this, root);
            initTestCards();
        }
        return root;
    }

    void initTestCards() {
        listView.setAdapter(new CureRecordAdapter(getActivity()));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
