package com.blazers.app.doctor.BmobModel;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

/**
 * Created by liang on 2015/5/25.
 */
public class RegisterInfo extends BmobUser{

    /* 自带的 用户名 密码 电子邮件 */
    /* 拓展部分 */
    private String realName;
    private String userPhone;
    private String userHead;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserHead() {
        return userHead;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }
}
