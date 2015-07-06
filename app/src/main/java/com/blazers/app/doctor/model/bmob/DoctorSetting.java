package com.blazers.app.doctor.model.bmob;

import cn.bmob.v3.BmobObject;

/**
 * 存储医生设置的表 在这读取要请时候需要填写的内容 以及拓展信息
 */
public class DoctorSetting extends BmobObject {

    private String DoctorId;
    private String InviteSetting;

    public String getDoctorId() {
        return DoctorId;
    }

    public String getInviteSetting() {
        return InviteSetting;
    }
}
