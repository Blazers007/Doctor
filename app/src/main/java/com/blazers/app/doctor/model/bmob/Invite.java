package com.blazers.app.doctor.model.bmob;

import cn.bmob.v3.BmobObject;

/**
 * Created by Blazers on 2015/7/6.
 */
public class Invite extends BmobObject {
    private String DoctorId;
    private String PatientPhone;
    private String InviteTime;
    private String Array;

    public String getDoctorId() {
        return DoctorId;
    }

    public String getPatientPhone() {
        return PatientPhone;
    }

    public String getInviteTime() {
        return InviteTime;
    }

    public String getArray() {
        return Array;
    }
}
