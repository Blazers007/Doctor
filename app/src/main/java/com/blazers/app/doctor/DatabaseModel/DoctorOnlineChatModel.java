package com.blazers.app.doctor.DatabaseModel;

import com.orm.SugarRecord;

/**
 * Created by liang on 2015/5/26.
 */
public class DoctorOnlineChatModel extends SugarRecord<DoctorOnlineChatModel> {

    String messageJson;

    public DoctorOnlineChatModel() {

    }

    public DoctorOnlineChatModel(String messageJson) {
        this.messageJson = messageJson;
    }

    public String getMessageJson() {
        return messageJson;
    }

    public void setMessageJson(String messageJson) {
        this.messageJson = messageJson;
    }
}
