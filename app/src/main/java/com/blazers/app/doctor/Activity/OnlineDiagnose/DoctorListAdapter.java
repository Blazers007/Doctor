package com.blazers.app.doctor.Activity.OnlineDiagnose;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.blazers.app.doctor.BmobModel.AppUserModel;
import com.blazers.app.doctor.R;

import java.util.List;

/**
 * Created by liang on 2015/5/19.
 */
public class DoctorListAdapter extends BaseAdapter {

    private LayoutInflater inflater;

    private Context ctx;
    private List<AppUserModel> list;

    public DoctorListAdapter(Context ctx, List<AppUserModel> list) {
        this.ctx = ctx;
        this.list = list;

        /**/
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        /* Bind */
        convertView = inflater.inflate(R.layout.test_doctor, null);
        /* Fill */
        AppUserModel userModel = list.get(position);
        ((TextView) convertView.findViewById(R.id.doctor_name)).setText(userModel.getRealName());
        return convertView;
    }
}
