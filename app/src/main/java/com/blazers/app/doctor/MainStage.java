package com.blazers.app.doctor;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.SparseIntArray;
import android.view.Menu;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import br.liveo.interfaces.NavigationLiveoListener;
import br.liveo.navigationliveo.NavigationLiveo;
import com.blazers.app.doctor.Fragments.MainStageFrags.FragAppointment;
import com.blazers.app.doctor.Fragments.MainStageFrags.FragCaseIllness;
import com.blazers.app.doctor.Fragments.MainStageFrags.FragCureRecord;
import com.blazers.app.doctor.Fragments.MainStageFrags.FragHealthyRecord;

/**
 * Created by Blazers on 15/5/6.
 */
public class MainStage extends NavigationLiveo implements NavigationLiveoListener {

    private FragmentManager mFragmentManager;

    private static final int FRAG_APP = 1;
    private static final int FRAG_CUR = 2;
    private static final int FRAG_HEA = 3;
    private static final int FRAG_ILL = 4;
    private Fragment mNowFrag;
    private Fragment mAppointment, mCureRecord, mHealthyRecord, mIllCase;

    @Override
    public void onUserInformation() {
        this.mUserName.setText("患者某某某");
        this.mUserEmail.setText("blazersdar@gmail.com");
        this.mUserPhoto.setImageResource(R.drawable.patient);
        this.mUserBackground.setImageResource(R.drawable.patient_home);
    }

    @Override
    public void onInt(Bundle bundle) {
        setNavigationListener(this);
        getToolbar().setTitleTextColor(Color.WHITE);

        /* Init Fragment */
        if(bundle == null) {
            setDefaultStartPositionNavigation(1);
        }

        List<String> mListNameItem = new ArrayList<>();
        mListNameItem.add(0, getString(R.string.func_main_1));
        mListNameItem.add(1, getString(R.string.func_appointment));
        mListNameItem.add(2, getString(R.string.func_hospital_record));
        mListNameItem.add(3, getString(R.string.func_healthy_record));
        mListNameItem.add(4, getString(R.string.func_case_illness));

        //  icons list items
        List<Integer> mListIconItem = new ArrayList<>();
        mListIconItem.add(0, 0);
        mListIconItem.add(1, R.drawable.ic_drawer_phone);
        mListIconItem.add(2, R.drawable.ic_drawer_heart); //Item no icon set 0
        mListIconItem.add(3, R.drawable.ic_drawer_ecg); //Item no icon set 0
        mListIconItem.add(4, R.drawable.ic_drawer_illness); //Item no icon set 0

        //  Header
        List<Integer> mListHeaderItem = new ArrayList<>();
        mListHeaderItem.add(0);

        //  Number
        SparseIntArray mSparseCounterItem = new SparseIntArray(); //indicate all items that have a counter
        mSparseCounterItem.put(2, 3);
        mSparseCounterItem.put(4, 5);

        this.setFooterInformationDrawer(getString(R.string.func_setting), R.drawable.ic_settings_black_24dp);

        this.setNavigationAdapter(mListNameItem, mListIconItem, mListHeaderItem, mSparseCounterItem);
    }

    @Override
    public void onItemClickNavigation(int i, int i1) {
         /* 初次展示的页面 */
        if(mNowFrag == null) {
            mNowFrag = mAppointment = new FragAppointment();
            getSupportFragmentManager().beginTransaction()
                    .add(i1, mCureRecord = new FragCureRecord())
                    .add(i1, mHealthyRecord = new FragHealthyRecord())
                    .add(i1, mIllCase = new FragCaseIllness())
                    .add(i1, mNowFrag)
                    .commit();
        }else{
            switch (i) {
                case FRAG_APP:
                    if(mNowFrag == mAppointment)
                        return;
                    else {
                        getSupportFragmentManager().beginTransaction().hide(mNowFrag).show(mAppointment).commit();
                        mNowFrag = mAppointment;
                    }
                    break;
                case FRAG_CUR:
                    if(mNowFrag == mCureRecord)
                        return;
                    else {
                        getSupportFragmentManager().beginTransaction().hide(mNowFrag).show(mCureRecord).commit();
                        mNowFrag = mCureRecord;
                    }
                    break;
                case FRAG_HEA:
                    if(mNowFrag == mHealthyRecord)
                        return;
                    else {
                        getSupportFragmentManager().beginTransaction().hide(mNowFrag).show(mAppointment).commit();
                        mNowFrag = mHealthyRecord;
                    }
                    break;
                case FRAG_ILL:
                    if(mNowFrag == mIllCase)
                        return;
                    else {
                        getSupportFragmentManager().beginTransaction().hide(mNowFrag).show(mIllCase).commit();
                        mNowFrag = mIllCase;
                    }
                    break;
            }
        }
    }

    @Override
    public void onPrepareOptionsMenuNavigation(Menu menu, int i, boolean b) {

    }

    @Override
    public void onClickFooterItemNavigation(View view) {

    }

    @Override
    public void onClickUserPhotoNavigation(View view) {

    }
}