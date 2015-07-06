package com.blazers.app.doctor.model.bmob;

import cn.bmob.v3.BmobObject;

/**
 * Created by liang on 2015/5/26.
 */
public class ChatModel extends BmobObject {

    private int type;
    private String content;

    private String sender;
    private String date;

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

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
