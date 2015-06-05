package com.blazers.app.doctor;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.widget.DrawerLayout;

import android.widget.AdapterView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.bmob.im.BmobUserManager;
import com.afollestad.materialdialogs.MaterialDialog;
import com.blazers.app.doctor.Activity.Hospital.HospitalAroundMe;
import com.blazers.app.doctor.Fragments.MainStageFrags.*;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.model.*;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;


@SuppressWarnings({"ALL", "ConstantConditions"})
public class MainActivity extends AppCompatActivity implements Drawer.OnDrawerItemClickListener{
    /*  */
    private static final int FRAG_APP = 0;
    private static final int FRAG_CUR = 1;
    private static final int FRAG_HEA = 2;
    private static final int FRAG_ILL = 3;
    private Fragment mNowFrag;
    private Fragment mAppointment, mCureRecord, mHealthyRecord, mIllCase;
    /* Toolbar */
    @InjectView(R.id.toolbar)Toolbar mToolbar;
    @InjectView(R.id.toolbar_shadow)View toolbarShadow;
    /* 菜单容器 */
    private DrawerLayout mDrawerLayout;
    private final int i1 = R.id.main;

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        /* 设置Toolbar */
        setSupportActionBar(mToolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initDrawer();

    }

    /* 接受EventBus发出的Login消息 在主线程上运行才可更新UI */
    void initDrawer() {
        Intent intent = getIntent();
        AccountHeader.Result header;
        if (intent.getBooleanExtra("tour", true)) {
            /* 游客模式 */
            header = new AccountHeader()
                    .withActivity(this)
                    .withHeaderBackground(R.drawable.patient_home)
                    .addProfiles(
                            new ProfileDrawerItem()
                                    .withName("随便看")
                                    .withEmail("尚未注册")
                                    .withIcon(getResources().getDrawable(R.drawable.patient))
                    )
                    .build();
        } else {
            header = new AccountHeader()
                    .withActivity(this)
                    .withHeaderBackground(R.drawable.patient_home)
                    .addProfiles(
                            new ProfileDrawerItem()
                                    .withName(intent.getStringExtra("name"))
                                    .withEmail(intent.getStringExtra("email"))
                                    .withIcon(intent.getStringExtra("icon"))
                    )
                    .build();
        }
         /* 用户模式 */
        Drawer.Result result = new Drawer()
                .withActivity(this)
                .withToolbar(mToolbar)
                .withAccountHeader(header)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.func_my_doctor).withIcon(R.drawable.ic_drawer_phone),
                        new PrimaryDrawerItem().withName(R.string.func_hospital_record).withIcon(R.drawable.ic_drawer_heart),
                        new PrimaryDrawerItem().withName(R.string.func_healthy_record).withIcon(R.drawable.ic_drawer_ecg),
                        new PrimaryDrawerItem().withName(R.string.func_case_illness).withIcon(R.drawable.ic_drawer_illness),
                        new PrimaryDrawerItem().withName(R.string.func_around_me).withIcon(R.drawable.ic_drawer_map),
                        new SectionDrawerItem().withName("设置"),
                        new SwitchDrawerItem().withName(R.string.func_alert).withIcon(R.drawable.ic_drawer_switch_alert),
                        new PrimaryDrawerItem().withName(R.string.func_logout).withIcon(R.drawable.ic_drawer_exit)
                )
                .withSelectedItem(0)
                .withOnDrawerItemClickListener(this)
                .build();
        result.setSelection(0);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        new MaterialDialog.Builder(this)
                .title("退出")
                .content("确定要离开心管家?")
                .positiveText("退出")
                .positiveColor(R.color.blue500)
                .negativeText("取消")
                .negativeColor(R.color.md_black_1000)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        MainActivity.super.onBackPressed();
                    }
                })
                .build()
                .show();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l, IDrawerItem iDrawerItem) {
         /* 初次展示的页面 */
        if(mNowFrag == null) {
            mNowFrag = mAppointment = new FragAppointment();
            getSupportFragmentManager().beginTransaction()
                    .add(i1, mCureRecord = new FragRecords())
                    .add(i1, mHealthyRecord = new FragHealthyRecord())
                    .add(i1, mIllCase = new FragCaseIllness())
                    .add(i1, mNowFrag)
                    .hide(mCureRecord)
                    .hide(mHealthyRecord)
                    .hide(mIllCase)
                    .commit();
        }else{
            setToolbarShadow(true);
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
                        setToolbarShadow(false);
                    }
                    break;
                case FRAG_HEA:
                    if(mNowFrag == mHealthyRecord)
                        return;
                    else {
                        getSupportFragmentManager().beginTransaction().hide(mNowFrag).show(mHealthyRecord).commit();
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
                case 4:
                    startActivity(new Intent(this, HospitalAroundMe.class));
                    break;
                case 6:
                    /* 开关推送功能 */
                    break;
                case 7:
                    BmobUserManager.getInstance(this).logout();
                    finish();
                    break;
            }
        }
    }

    public void setToolbarShadow(boolean show) {
        if (show)
            toolbarShadow.setVisibility(View.VISIBLE);
        else
            toolbarShadow.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}