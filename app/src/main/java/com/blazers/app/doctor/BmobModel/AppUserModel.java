package com.blazers.app.doctor.BmobModel;

import cn.bmob.im.BmobChat;
import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

/**
 * Created by liang on 2015/5/25.
 */
public class AppUserModel extends BmobChatUser {

    /* 自带的 用户名 密码 电子邮件 */
    /* 拓展部分 */
    private String realName;
    private String userPhone;
    private String userHead;

    /* TAG部分 */
    private String role;

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
