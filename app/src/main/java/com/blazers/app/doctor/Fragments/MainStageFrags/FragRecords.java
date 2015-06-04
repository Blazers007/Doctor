package com.blazers.app.doctor.Fragments.MainStageFrags;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.blazers.app.doctor.Adapter.IllnessCaseAdapter;
import com.blazers.app.doctor.R;
import com.github.florent37.materialviewpager.MaterialViewPager;

import java.util.ArrayList;

/**
 * Created by Blazers on 15/6/4.
 */
public class FragRecords extends Fragment {
    private View root;
    @InjectView(R.id.materialViewPager)
    MaterialViewPager materialViewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_records, container, false);
            ButterKnife.inject(this, root);
            materialViewPager.getViewPager().setAdapter(new RecordsPagerAdapter());
        }
        return root;
    }

    class RecordsPagerAdapter extends PagerAdapter {


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_cure_record, null);
            container.addView(v);
            return v;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
