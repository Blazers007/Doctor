package com.blazers.app.doctor.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import cn.bmob.im.util.BmobLog;

/**
 * Created by Blazers on 15/5/25.
 */
public class ChatMessageReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String json = intent.getStringExtra("msg");
        BmobLog.i("收到的message = " + json);
    }
}
