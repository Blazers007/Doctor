package com.blazers.app.doctor.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.blazers.app.doctor.DatabaseModel.DoctorOnlineChatModel;
import com.blazers.app.doctor.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liang on 2015/5/26.
 */
public class DoctorOnlineChatAdapter extends BaseAdapter {

    private Context ctx;
    private String targetId;

    private LayoutInflater inflater;
    private List<DoctorOnlineChatModel> chatModels;

    public DoctorOnlineChatAdapter(Context ctx, String targetId) {
        this.ctx = ctx;
        this.targetId = targetId;
        /* Init Inflater */
        inflater = LayoutInflater.from(ctx);
        /* Read From database */
        chatModels = DoctorOnlineChatModel.listAll(DoctorOnlineChatModel.class);
    }

    @Override
    public int getCount() {
        return chatModels.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public void notifyDataSetChanged() {
        chatModels = DoctorOnlineChatModel.listAll(DoctorOnlineChatModel.class);
        super.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        /* 判断 */
        convertView = inflater.inflate(R.layout.item_dortor_chat_send_text, parent, false);

        ((TextView) convertView.findViewById(R.id.chat_send_text)).setText(chatModels.get(position).getMessageJson());
        /* 填入内容 */

        return convertView;
    }

}
