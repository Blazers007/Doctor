package com.blazers.app.doctor.ui.fragments.main;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.blazers.app.doctor.adapter.AppointmentRecordsAdapter;
import com.blazers.app.doctor.R;
import com.blazers.app.doctor.util.Dppx;
import com.blazers.app.doctor.util.PhoneUtils;
import github.chenupt.dragtoplayout.AttachUtil;
import github.chenupt.dragtoplayout.DragTopLayout;

import java.util.ArrayList;

/**
 * Created by Blazers on 15/6/4.
 */
public class FragRecords extends Fragment implements AbsListView.OnScrollListener, ViewSwitcher.ViewFactory {
    private View root;
    @InjectView(R.id.records_viewpager) ViewPager recordsViewpager;
    @InjectView(R.id.materialTabHost) TabLayout tabHost;
    @InjectView(R.id.drag_layout) DragTopLayout dragTopLayout;
    @InjectView(R.id.image_switcher) ImageSwitcher imageSwitcher;
    /* Vars */
    private RecordsPagerAdapter pagerAdapter;
    private int[] headerIds = new int[]{R.drawable.records_header_1, R.drawable.records_header_2, R.drawable.records_header_3};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_records, container, false);
            ButterKnife.inject(this, root);
//            materialViewPager.getViewPager().setAdapter(new RecordsPagerAdapter());
            RecordsPagerAdapter pagerAdapter = new RecordsPagerAdapter();
            recordsViewpager.setAdapter(pagerAdapter);
            recordsViewpager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                @Override
                public void onPageSelected(int position) {
                    imageSwitcher.setImageResource(headerIds[position]);
                }
            });

            /* Setup tab */
            tabHost.setTabTextColors(Color.argb(192, 250,250,250), Color.WHITE);
            tabHost.setupWithViewPager(recordsViewpager);
            tabHost.setTabsFromPagerAdapter(pagerAdapter);
            /* Init Switcher */
            imageSwitcher.setFactory(this);
            imageSwitcher.setImageResource(headerIds[0]);
            imageSwitcher.setInAnimation(getActivity(), R.anim.abc_fade_in);
            imageSwitcher.setOutAnimation(getActivity(), R.anim.abc_fade_out);
        }

        return root;
    }

    /* 实现该方法来改变DragTopLayout的expand条件 */
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        dragTopLayout.setTouchMode(AttachUtil.isAdapterViewAttach(view));
    }

    /* ImageSwitcher */
    @Override
    public View makeView() {
        ImageView view = new ImageView(getActivity());
        int width = PhoneUtils.getScreenWidth(getActivity());
        int height = (int)  ((float)width * 0.618f) - Dppx.Dp2Px(getActivity(), 56);
        view.setLayoutParams(new FrameLayout.LayoutParams(width, height));
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return view;
    }

    /* 使用自己写的parallax效果控件 */

    class RecordsPagerAdapter extends PagerAdapter {

        private final String[] titles = new String[]{"预约记录", "手术记录", "复诊记录"};
        private final ArrayList<View> views;

        public RecordsPagerAdapter() {
            views = new ArrayList<>();
            for (int i = 0 ; i < 3 ; i ++) {
                View v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_cure_record, null);
                ListView listView = (ListView) v.findViewById(R.id.cure_record_list);
                listView.setAdapter(new AppointmentRecordsAdapter(getActivity()));
                listView.setOnScrollListener(FragRecords.this);
                views.add(v);
            }
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View v = views.get(position);
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
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
