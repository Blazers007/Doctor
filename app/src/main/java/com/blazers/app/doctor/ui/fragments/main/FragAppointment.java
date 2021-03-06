package com.blazers.app.doctor.ui.fragments.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.*;
import android.widget.Toast;
import com.blazers.app.doctor.ui.activity.appointment.MakeAppointment;
import com.blazers.app.doctor.ui.activity.cardiograph.DoctorSeeImage;
import com.blazers.app.doctor.ui.activity.diagnose.OnlineDiagnose;
import com.blazers.app.doctor.ui.activity.call.PhoneCall;
import com.blazers.app.doctor.R;
import com.blazers.app.doctor.ui.activity.tips.SmallTips;
import com.blazers.app.doctor.ui.activity.trace.UpdateMyStatus;

import java.util.ArrayList;

/**
 * Created by liang on 2015/5/7.
 */
public class FragAppointment extends Fragment implements View.OnClickListener{

    private static final int MAKE_APPOINTMENT = 31;
    private View root;

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
        if(root == null) {
            root = inflater.inflate(R.layout.fragment_appointment, container, false);
            root.findViewById(R.id.btn_make_appointment).setOnClickListener(this);
            ((ViewPager)root.findViewById(R.id.doctor_match_pager)).setAdapter(new DoctorMatchAdapter(getActivity()));
            /* Set Up Menu Click Event */
            root.findViewById(R.id.menu_online_diagnoseig).setOnClickListener(this);
            root.findViewById(R.id.menu_doctor_see_image).setOnClickListener(this);
            root.findViewById(R.id.menu_phone_call).setOnClickListener(this);
            root.findViewById(R.id.menu_small_tips).setOnClickListener(this);
            root.findViewById(R.id.menu_updata_my_status).setOnClickListener(this);
        }
        return root;
    }

    class DoctorMatchAdapter extends PagerAdapter {

        private final ArrayList<View> viewArrayList;

        public DoctorMatchAdapter(Context ctx) {
            LayoutInflater inflater = LayoutInflater.from(ctx);
            viewArrayList = new ArrayList<>();
            for(int i = 0 ; i < 4 ; i ++) {
                viewArrayList.add(inflater.inflate(R.layout.test_doctor, null));
            }
        }

        @Override
        public int getCount() {
            return viewArrayList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(viewArrayList.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewArrayList.get(position));
            return viewArrayList.get(position);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_make_appointment:
                startActivityForResult(new Intent(getActivity(), MakeAppointment.class), MAKE_APPOINTMENT);
                break;
            case R.id.menu_online_diagnoseig:
                startActivity(new Intent(getActivity(), OnlineDiagnose.class));
                break;
            case R.id.menu_doctor_see_image:
                startActivity(new Intent(getActivity(), DoctorSeeImage.class));
                break;
            case R.id.menu_phone_call:
                startActivity(new Intent(getActivity(), PhoneCall.class));
                break;
            case R.id.menu_small_tips:
                startActivity(new Intent(getActivity(), SmallTips.class));
                break;
            case R.id.menu_updata_my_status:
                startActivity(new Intent(getActivity(), UpdateMyStatus.class));
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case MAKE_APPOINTMENT:
                if(resultCode != Activity.RESULT_OK){
                    Toast.makeText(getActivity(), "没有预约成功", Toast.LENGTH_SHORT).show();
                    return;
                }
                break;
        }
    }
}
