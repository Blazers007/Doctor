package com.blazers.app.doctor.BmobModel;

import cn.bmob.v3.BmobObject;

/**
 * Created by Blazers on 15/6/4.
 */
public class AppointmentModel extends BmobObject {

    private String state;               /* 当前状态: 申请预约-预约接受-处理中- 预约失败 : 预约成功 - 诊治结束 */
    private String doctorId; /* 应该是病人的主键 通过该主键来查询病人的信息 */
    private String patientId;
    private String createDate;
    private String appointment;
    private String appointmentDate;
    private String detail;
    private String backup;

    private String handleDate;
    private String result;
    private String reason;

    /* 添加字段 如：最大可能接受的预约时间 */
    private String endDate;

    /* */

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getAppointment() {
        return appointment;
    }

    public void setAppointment(String appointment) {
        this.appointment = appointment;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getBackup() {
        return backup;
    }

    public void setBackup(String backup) {
        this.backup = backup;
    }

    public String getHandleDate() {
        return handleDate;
    }

    public void setHandleDate(String handleDate) {
        this.handleDate = handleDate;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
