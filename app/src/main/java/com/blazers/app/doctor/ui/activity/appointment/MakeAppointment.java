package com.blazers.app.doctor.ui.activity.appointment;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.bmob.v3.listener.SaveListener;
import com.blazers.app.doctor.model.bmob.AppointmentModel;
import com.blazers.app.doctor.R;
import com.blazers.app.doctor.util.DateTools;

/*
*   该界面为预约的最终场景  应该有一个可预约列表 点击后进入该医生的预约场景进行数据的填写并提交预约
* */

public class MakeAppointment extends AppCompatActivity {

    @InjectView(R.id.toolbar) Toolbar mToolbar;
    @InjectView(R.id.collapsing_toolbar) CollapsingToolbarLayout toolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_appointment);
        ButterKnife.inject(this);
        initViews();
    }

    void initViews() {
        mToolbar.setTitle(R.string.title_make_appointment);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        /*  */
        toolbarLayout.setTitle(getString(R.string.title_make_appointment));
    }

    /* 提交预约申请 */
    @OnClick(R.id.appointment_btn_submit)
    public void submitAppointment(View view) {
        /* 校验 */
        AppointmentModel appointment = new AppointmentModel();
        appointment.setState("申请预约");
        appointment.setDoctorId("a02110001");
        appointment.setPatientId("b02110001");
        appointment.setCreateDate(DateTools.getNowDate());
        appointment.setAppointment("申请的内容字段");
        appointment.setAppointmentDate(DateTools.getDate(30));
        appointment.setDetail("具体的预约内容字段");
        appointment.setBackup("备注(针对客服人员)");
        appointment.setEndDate("结束日期(预约活动截止的时间)");
        /* 存放数据 */
        appointment.save(this, new SaveListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(MakeAppointment.this, "预约数据提交成功!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(MakeAppointment.this, s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_make_appointment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
