package com.blazers.app.doctor.SocialController;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.im.db.BmobDB;
import cn.bmob.v3.listener.FindListener;
import com.blazers.app.doctor.Adapter.NewFriendAdapter;
import com.blazers.app.doctor.R;
import com.blazers.app.doctor.Util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class NewFriendActivity extends AppCompatActivity {

    @InjectView(R.id.toolbar) Toolbar mToolbar;
    @InjectView(R.id.list_newfriend)  ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_friend);
        ButterKnife.inject(this);
        /* 读取信息 */
        /* Init */
        initViews();

    }

    void initViews() {
        /* 动态获取医生姓名 */
        mToolbar.setTitle("添加好友");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /* 初始化添加好友列表 */
        listView.setAdapter(new NewFriendAdapter(this, BmobDB.create(this).queryBmobInviteList()));

        String from = getIntent().getStringExtra("from");
        if(from == null){//若来自通知栏的点击，则定位到最后一条
            listView.setSelection(listView.getChildCount() - 1);
        }
    }
}
