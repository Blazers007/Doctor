package com.blazers.app.doctor;

import cn.bmob.v3.Bmob;
import cn.smssdk.SMSSDK;
import com.orm.SugarApp;

/**
 * Created by liang on 2015/5/25.
 */
public class App extends SugarApp {

    @Override
    public void onCreate() {
        super.onCreate();
        /* Init SMS Service */
        SMSSDK.initSDK(this, "7ad8c54f5ba0", "103ca1da08756dd7a7939c762b5fd5c4");
        /* 初始化 Bmob */
        Bmob.initialize(this, "f0d74dc5fda96aa9becdbd2a0875225c");
    }
}
