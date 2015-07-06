package com.blazers.app.doctor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.blazers.app.doctor.model.database.DoctorOnlineChatModel;
import com.blazers.app.doctor.R;
import com.john.waveview.WaveView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by liang on 2015/5/26.
 */
public class DoctorOnlineChatMessageAdapter extends BaseAdapter {

    private final Context ctx;
    private final String targetId;

    private final LayoutInflater inflater;
    private List<DoctorOnlineChatModel> chatModels;

    private final HashMap<String, WaveView> waveViewHashMap;

    public DoctorOnlineChatMessageAdapter(Context ctx, String targetId) {
        this.ctx = ctx;
        this.targetId = targetId;
        /* Init Inflater */
        inflater = LayoutInflater.from(ctx);
        /* Read From database TODO:这里容易报错！ */
        chatModels = DoctorOnlineChatModel.listAll(DoctorOnlineChatModel.class);
        waveViewHashMap = new HashMap<>();
    }

    @Override
    public int getCount() {
        return chatModels == null ? 0 : chatModels.size();
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
    public int getViewTypeCount() {
        return 4;
    }

    @Override
    public int getItemViewType(int position) {
        return chatModels.get(position).getType();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        /* 判断 */
        ViewHolder1 viewHolder1 = null;
        ViewHolder2 viewHolder2 = null;
        ViewHolderN1 viewHolderN1 = null;
        ViewHolderN2 viewHolderN2 = null;
        DoctorOnlineChatModel model = chatModels.get(position);
        int type = model.getType();

        if (convertView == null) {
            switch (type) {
                case 1:
                    convertView = inflater.inflate(R.layout.item_doctor_chat_send_text, parent, false);
                    viewHolder1 = new ViewHolder1();
                    viewHolder1.textView = (TextView) convertView.findViewById(R.id.chat_send_text);
                    convertView.setTag(viewHolder1);
                    break;
                case 2:
                    convertView = inflater.inflate(R.layout.item_doctor_chat_send_image, parent, false);
                    viewHolder2 = new ViewHolder2();
                    viewHolder2.imageView = (ImageView) convertView.findViewById(R.id.send_imageview);
                    viewHolder2.waveView = (WaveView) convertView.findViewById(R.id.send_progress);
                    waveViewHashMap.put(model.getContent(), viewHolder2.waveView);
                    convertView.setTag(viewHolder2);
                    break;
                case -1:
                    convertView = inflater.inflate(R.layout.item_doctor_chat_receive_text, parent, false);
                    viewHolderN1 = new ViewHolderN1();
                    viewHolderN1.textView = (TextView) convertView.findViewById(R.id.chat_receive);
                    convertView.setTag(viewHolderN1);
                    break;
                case -2:
                    convertView = inflater.inflate(R.layout.item_doctor_chat_receive_image, parent, false);
                    viewHolderN2 = new ViewHolderN2();
                    viewHolderN2.imageView = (ImageView) convertView.findViewById(R.id.send_imageview);
                    viewHolderN2.waveView = (WaveView) convertView.findViewById(R.id.send_progress);
                    waveViewHashMap.put(model.getContent(), viewHolderN2.waveView);
                    convertView.setTag(viewHolderN2);
                    break;
            }
        } else {
            switch (type) {
                case 1:
                    viewHolder1 = (ViewHolder1) convertView.getTag();
                    break;
                case 2:
                    viewHolder2 = (ViewHolder2) convertView.getTag();
                    break;
                case -1:
                    viewHolderN1 = (ViewHolderN1) convertView.getTag();
                    break;
                case -2:
                    viewHolderN2 = (ViewHolderN2) convertView.getTag();
                    break;
            }
        }

        switch (type) {
            case 1:
                viewHolder1.textView.setText(model.getContent());
                break;
            case 2:
                ImageLoader.getInstance().displayImage("file://" + model.getContent(), viewHolder2.imageView);
                /* Try use Fresco */
                break;
            case -1:
                viewHolderN1.textView.setText(model.getContent());
                break;
            case -2:
                ImageLoader.getInstance().displayImage(model.getContent(), viewHolderN2.imageView);
                break;
        }

        /* 填入内容 */
        return convertView;
    }

    /* 返回该List内部含有的所有Image的URI */
    public ArrayList<String> getContainUrls() {
        ArrayList<String> urls = new ArrayList<>();
        for (DoctorOnlineChatModel model : chatModels) {
            if (model.getType() == 2) {
                /* 发送的都是本地图片 */
                urls.add("file://" + model.getContent());
            }
            if (model.getType() == -2) {
                urls.add(model.getContent());
            }
        }
        return urls;
    }

    /* ViewHolders */
    class ViewHolder1 {
        public TextView textView;
    }

    class ViewHolder2 {
        public ImageView imageView;
        public WaveView waveView;
    }

    class ViewHolderN1 {
        public TextView textView;
    }

    class ViewHolderN2 {
        public ImageView imageView;
        public WaveView waveView;
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
