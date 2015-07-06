package com.blazers.app.doctor.model.database;

import com.orm.SugarRecord;

/**
 * Created by liang on 2015/5/26.
 */
public class DoctorOnlineChatModel extends SugarRecord<DoctorOnlineChatModel> {

    private String toId;
    private String fromId;

    private String date;
    private int type;
    private String content;

    public DoctorOnlineChatModel() {

    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
