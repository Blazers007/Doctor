package com.blazers.app.doctor.Activity.OnlineDiagnose;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
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
import cn.bmob.im.inteface.UploadListener;
import cn.bmob.v3.BmobRealTimeData;
import cn.bmob.v3.listener.ValueEventListener;
import com.blazers.app.doctor.Adapter.DoctorOnlineChatAdapter;
import com.blazers.app.doctor.BmobModel.AppUserModel;
import com.blazers.app.doctor.R;
import com.blazers.app.doctor.Util.StorageConfig;
import org.json.JSONObject;

import java.io.File;

public class DoctorOnlineChat extends AppCompatActivity {

    @InjectView(R.id.toolbar) Toolbar mToolbar;
    @InjectView(R.id.listView) ListView listView;
    @InjectView(R.id.chatEditText) EditText chatEditText;

    /* Vars */
    private BmobRealTimeData rtd;
    private DoctorOnlineChatAdapter adapter;

    /* Test */
    private AppUserModel testTargetId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_online_chat);
        ButterKnife.inject(this);
        /* 并不适用于 RealTimeData因为没有专一的表结构供一对一交流 */
        testTargetId = (AppUserModel)getIntent().getSerializableExtra("user");
        initViews();
    }

    void initViews() {
        /* 动态获取医生姓名 */
        mToolbar.setTitle(testTargetId.getRealName());
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
        BmobChatManager.getInstance(this).sendTextMessage(testTargetId, msg);

        /* TODO:报错 the bind value at index 1 is null */
    }

    private String localCameraPath;

    public void testNotify(View view) {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File dir = new File(StorageConfig.BMOB_PICTURE_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, String.valueOf(System.currentTimeMillis())
                + ".jpg");
        localCameraPath = file.getPath();
        Uri imageUri = Uri.fromFile(file);
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(openCameraIntent,
                StorageConfig.REQUESTCODE_TAKE_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case StorageConfig.REQUESTCODE_TAKE_CAMERA:// µ±È¡µ½ÖµµÄÊ±ºò²ÅÉÏ´«pathÂ·¾¶ÏÂµÄÍ¼Æ¬µ½·þÎñÆ÷
                    sendImageMessage(localCameraPath);
                    break;
            }
        }
    }

    private void sendImageMessage(String local) {
//        if (layout_more.getVisibility() == View.VISIBLE) {
//            layout_more.setVisibility(View.GONE);
//            layout_add.setVisibility(View.GONE);
//            layout_emo.setVisibility(View.GONE);
//        }
        BmobChatManager.getInstance(this).sendImageMessage(testTargetId, local, new UploadListener() {

            @Override
            public void onStart(BmobMsg msg) {
                // TODO Auto-generated method stub
//                refreshMessage(msg);
                Log.e("Send Image", "Sending");
            }

            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                Log.e("Send Image", "Success");
//                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int error, String arg1) {
                // TODO Auto-generated method stub
//                ShowLog("ÉÏ´«Ê§°Ü -->arg1£º" + arg1);
                Log.e("Send Image", "Failure");
//                mAdapter.notifyDataSetChanged();
            }
        });
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
