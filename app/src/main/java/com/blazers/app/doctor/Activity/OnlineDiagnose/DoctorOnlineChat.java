package com.blazers.app.doctor.Activity.OnlineDiagnose;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.bmob.im.BmobChatManager;
import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.im.bean.BmobMsg;
import cn.bmob.v3.BmobRealTimeData;
import cn.bmob.v3.listener.ValueEventListener;
import com.blazers.app.doctor.Adapter.DoctorOnlineChatAdapter;
import com.blazers.app.doctor.R;
import org.json.JSONObject;

public class DoctorOnlineChat extends AppCompatActivity {

    @InjectView(R.id.toolbar) Toolbar mToolbar;
    @InjectView(R.id.listView) ListView listView;
    @InjectView(R.id.chatEditText) EditText chatEditText;

    /* Vars */
    private BmobRealTimeData rtd;
    private DoctorOnlineChatAdapter adapter;

    /* Test */
    private BmobChatUser testTargetId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_online_chat);
        ButterKnife.inject(this);
        /* 并不适用于 RealTimeData因为没有专一的表结构供一对一交流 */
        testTargetId = (BmobChatUser)getIntent().getSerializableExtra("user");
        initViews();
    }

    void initViews() {
        /* 动态获取医生姓名 */
        mToolbar.setTitle("张仲景医生");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        /**/
        adapter = new DoctorOnlineChatAdapter(this, testTargetId.getObjectId());
        listView.setAdapter(adapter);
    }

    public void testSend(View view) {
        BmobMsg msg = BmobMsg.createTextSendMsg(this, testTargetId.getObjectId(), chatEditText.getText().toString());
        BmobChatUser targetUser = new BmobChatUser();
        BmobChatManager.getInstance(this).sendTextMessage(targetUser, msg);

        /* TODO:报错 the bind value at index 1 is null */
    }

    public void testNotify(View view) {
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_doctor_online_chat, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /* 注册广播来更新Adapter */
}
