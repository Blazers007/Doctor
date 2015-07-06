package com.blazers.app.doctor.ui.activity.diagnose;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.bmob.im.BmobChatManager;
import cn.bmob.im.BmobUserManager;
import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.im.config.BmobConfig;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.PushListener;
import com.blazers.app.doctor.adapter.DoctorListAdapter;
import com.blazers.app.doctor.model.bmob.AppUserModel;
import com.blazers.app.doctor.R;

import java.util.List;

public class OnlineDiagnose extends AppCompatActivity {

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.spinnerDisease)
    Spinner spinnerDiease;
    @InjectView(R.id.spinnerCity)
    Spinner spinnerCity;
    @InjectView(R.id.spinnerHospital)
    Spinner spinnerHospital;
    @InjectView(R.id.doctor_list)
    ListView onlineListView;

    /* Vars */
    private DoctorListAdapter doctorListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_online);
        ButterKnife.inject(this);
        initViews();

        queryByEvents();
//        queryAndInitList();
    }

    void initViews() {
        mToolbar.setTitle(R.string.title_activity_chat_online);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /* Spinner Filter */
        spinnerDiease.setAdapter(new ArrayAdapter<>(this, R.layout.spinner_white_text, new String[]{"按病种筛选"}));
        spinnerCity.setAdapter(new ArrayAdapter<>(this, R.layout.spinner_white_text, new String[]{"按地区筛选"}));
        spinnerHospital.setAdapter(new ArrayAdapter<>(this, R.layout.spinner_white_text, new String[]{"按医院筛选"}));
    }

    void queryByEvents() {
        BmobQuery<AppUserModel> query = new BmobQuery<>();
        query.addWhereEqualTo("role", "doctor");
        query.findObjects(this, new FindListener<AppUserModel>() {
            @Override
            public void onSuccess(List<AppUserModel> list) {
                doctorListAdapter = new DoctorListAdapter(OnlineDiagnose.this, list);
                onlineListView.setAdapter(doctorListAdapter);
            }

            @Override
            public void onError(int i, String s) {

            }
        });

        onlineListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AppUserModel doctor = (AppUserModel)doctorListAdapter.getItem(i);
                Intent intent = new Intent(OnlineDiagnose.this, DoctorOnlineChat.class);
                intent.putExtra("user", doctor);
                startActivity(intent);
            }
        });
    }

    private BmobChatUser testTargetId;

    void queryAndInitList() {
        /* Query */
        BmobUserManager.getInstance(this)
                .queryCurrentContactList(new FindListener<BmobChatUser>() {
                    @Override
                    public void onSuccess(List<BmobChatUser> list) {
                        for (BmobChatUser user : list) {
                            Log.e("User Info", user.getUsername());
                            testTargetId = user;
                        }
                    }

                    @Override
                    public void onError(int i, String s) {
                        /* 如果没有好友也会出现该情况 */
                        Log.e("Query List", s);
                    }
                });
        /* Doctor List */
//        onlineListView.setAdapter(new DoctorListAdapter(LayoutInflater.from(this)));
        onlineListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(OnlineDiagnose.this, DoctorOnlineChat.class);
                intent.putExtra("user", testTargetId);
                startActivity(intent);
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chat_online, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            /* Test Add Doctor LI */
            BmobChatManager.getInstance(this).sendTagMessage(BmobConfig.TAG_ADD_CONTACT, "R12lZZZe", new PushListener() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onFailure(int i, String s) {

                }
            });
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
