package com.blazers.app.doctor.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.blazers.app.doctor.DatabaseModel.DoctorOnlineChatModel;
import com.blazers.app.doctor.R;
import com.john.waveview.WaveView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by liang on 2015/5/26.
 */
public class DoctorOnlineChatAdapter extends BaseAdapter {

    private Context ctx;
    private String targetId;

    private LayoutInflater inflater;
    private List<DoctorOnlineChatModel> chatModels;

    private HashMap<String, WaveView> waveViewHashMap;

    public DoctorOnlineChatAdapter(Context ctx, String targetId) {
        this.ctx = ctx;
        this.targetId = targetId;
        /* Init Inflater */
        inflater = LayoutInflater.from(ctx);
        /* Read From database */
        chatModels = DoctorOnlineChatModel.listAll(DoctorOnlineChatModel.class);
        waveViewHashMap = new HashMap<>();
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
        return chatModels.size();
    }

    @Override
    public void notifyDataSetChanged() {
        chatModels = DoctorOnlineChatModel.listAll(DoctorOnlineChatModel.class);
        super.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        /* 判断 */
        DoctorOnlineChatModel model = chatModels.get(position);

        switch (model.getType()) {
            case 1:
                convertView = inflater.inflate(R.layout.item_dortor_chat_send_text, parent, false);
                ((TextView) convertView.findViewById(R.id.chat_send_text)).setText(model.getContent());
                break;
            case 2:
                convertView = inflater.inflate(R.layout.item_dortor_chat_send_image, parent, false);
                ImageLoader.getInstance().displayImage(model.getContent(),
                        (ImageView) convertView.findViewById(R.id.send_imageview));
                /**/
                WaveView waveView = (WaveView) convertView.findViewById(R.id.send_progress);
                waveViewHashMap.put(model.getContent(), waveView);
                break;
            case -1:
                convertView = inflater.inflate(R.layout.item_dorcot_chat_receive_text, parent, false);
                ((TextView) convertView.findViewById(R.id.chat_receive)).setText(model.getContent());
                break;
        }
        /* 填入内容 */
        return convertView;
    }

    public void updateProgress(String file, int ratio) {
        try {
            WaveView waveView = waveViewHashMap.get(file);
            waveView.setVisibility(View.VISIBLE);
            waveView.setProgress(ratio);
            if (ratio == 100) {
                waveView.setVisibility(View.GONE);
                waveViewHashMap.remove(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
