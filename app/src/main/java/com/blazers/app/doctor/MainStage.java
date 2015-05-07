package com.blazers.app.doctor;

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
import com.blazers.app.doctor.MainStageFrags.FragAppointment;

/**
 * Created by Blazers on 15/5/6.
 */
public class MainStage extends NavigationLiveo implements NavigationLiveoListener {

    private FragmentManager mFragmentManager;
    private static final String FRAG_APP = "fragment_app";
    private static final String FRAG_CUR = "fragment_cur";
    private static final String FRAG_HEA = "fragment_hea";
    private Fragment nowFrag;
    private Fragment mAppointment, mCureRecord, mHealthyRecord;

    @Override
    public void onUserInformation() {
        this.mUserName.setText("Rudson Lima");
        this.mUserEmail.setText("rudsonlive@gmail.com");
        this.mUserPhoto.setImageResource(R.drawable.ic_rudsonlive);
        this.mUserBackground.setImageResource(R.drawable.drawer_background);
    }

    @Override
    public void onInt(Bundle bundle) {
        setNavigationListener(this);

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
         /* Put Original */
        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction()
                .replace(i1, new FragAppointment())
                .commit();
        getSupportActionBar().setTitle("我的医生");
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