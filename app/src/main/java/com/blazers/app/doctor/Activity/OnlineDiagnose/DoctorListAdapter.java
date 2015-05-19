package com.blazers.app.doctor.Activity.OnlineDiagnose;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.blazers.app.doctor.R;

/**
 * Created by liang on 2015/5/19.
 */
public class DoctorListAdapter extends BaseAdapter {

    private LayoutInflater inflater;

    public DoctorListAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
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
        convertView = inflater.inflate(R.layout.test_doctor, null);
        return convertView;
    }
}
