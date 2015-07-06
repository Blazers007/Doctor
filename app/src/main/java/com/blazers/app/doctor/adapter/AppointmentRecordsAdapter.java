package com.blazers.app.doctor.adapter;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import com.blazers.app.doctor.model.bmob.AppointmentModel;
import com.blazers.app.doctor.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liang on 2015/6/5.
 */
public class AppointmentRecordsAdapter extends BaseAdapter {

    private final Context context;
    private final LayoutInflater inflater;
    private final List<AppointmentModel> appointmentModelList;
    private int cursor;

    public AppointmentRecordsAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        appointmentModelList = new ArrayList<>();
    }

    public void doQuery() {
        BmobQuery<AppointmentModel> query = new BmobQuery<>();
//        query.addWhereEqualTo("userid", your id); /* 只查询自己的 */
        query.setSkip(cursor);
        query.setLimit(10);
        query.order("-createDate");
        query.findObjects(context, new FindListener<AppointmentModel>() {
            @Override
            public void onSuccess(List<AppointmentModel> list) {
                /* 返回结果 */
                appointmentModelList.addAll(list);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    @Override
    public int getCount() {
//        return appointmentModelList.size();
        return 10;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_appointment_record, parent, false);
            holder = new ViewHolder();
//            holder.swipeLayout = (SwipeLayout) convertView.findViewById(R.id.records_swipe_layout);
            holder.btn_delete = (Button) convertView.findViewById(R.id.btn_delete);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        /* 填入数据 */
//        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        holder.btn_delete.setOnClickListener(new MyOnClickListener());
        return convertView;
    }


    class ViewHolder {
//        public SwipeLayout swipeLayout;
        public Button btn_delete;
    }

    class MyOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Snackbar.make(v, "呵呵呵呵", Snackbar.LENGTH_SHORT)
                    .setAction("撤销", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    })
                    .show();
        }
    }
}
