package com.blazers.app.doctor.BmobModel;

import cn.bmob.v3.BmobObject;

/**
 * Created by liang on 2015/5/25.
 */
public class RegisterInfo extends BmobObject{

    private String userName;
    private String userPhone;
    private String userPwd;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }
}
