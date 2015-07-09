package com.blazers.app.doctor.model.bmob;

import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by liang on 2015/5/25.
 */
public class AppUserModel extends BmobChatUser {

    /* 自带的 用户名 密码 电子邮件 */
    private String RealName;
    private String UserHeadSrc;
    private String Birthday;
    private String Location;
    private String Role;
    private String JSONExtend;
    /* 与医生的关联? */

    public String getRealName() {
        return RealName;
    }

    public void setRealName(String realName) {
        RealName = realName;
    }

    public String getUserHeadSrc() {
        return UserHeadSrc;
    }

    public void setUserHeadSrc(String userHeadSrc) {
        UserHeadSrc = userHeadSrc;
    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String birthday) {
        Birthday = birthday;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public String getJSONExtend() {
        return JSONExtend;
    }

    public void setJSONExtend(String JSONExtend) {
        this.JSONExtend = JSONExtend;
    }
}
