package com.blazers.app.doctor.ui.activity.register;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.InjectViews;
import com.blazers.app.doctor.R;
import com.blazers.app.doctor.model.bmob.AppUserModel;
import com.blazers.app.doctor.util.LocationParser;
import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Calendar;

public class FullfillProfile extends AppCompatActivity {

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    /*User Info*/
    @InjectView(R.id.register_birthday) TextView mBirthday;
    @InjectView(R.id.register_age) TextView mAge;
    @InjectView(R.id.register_username) MaterialEditText mRealname;
    /* 尝试使用数组注解 */
    private Spinner mProvince, mCity, mDistrict;
    @InjectView(R.id.register_doctor_info) AutoCompleteTextView doctorName;

    private String PHONE_NUMBER;
    private AppUserModel register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullfill_profile);
        ButterKnife.inject(this);
        initViews();
    }

    void initViews(){
          /* Init Spinner */
        mProvince = (Spinner) findViewById(R.id.spinner);
        mCity = (Spinner) findViewById(R.id.spinner2);
        mDistrict = (Spinner) findViewById(R.id.spinner3);

        mProvince.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                LocationParser.getInstance(this).getProvinces()));
        mProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mCity.setAdapter(new ArrayAdapter<>(FullfillProfile.this,
                        android.R.layout.simple_spinner_dropdown_item,
                        LocationParser.getInstance(FullfillProfile.this).getCitiesByProvince(parent.getSelectedItem().toString())));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mDistrict.setAdapter(new ArrayAdapter<>(FullfillProfile.this,
                        android.R.layout.simple_spinner_dropdown_item,
                        LocationParser.getInstance(FullfillProfile.this).getDistrictsByCity(parent.getSelectedItem().toString())));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /* 获取医生的列表与信息 输入的时候能够立刻联想到 */
        doctorName.setAdapter(new ArrayAdapter<>(
                this,
                R.layout.item_register_doc_info,
                R.id.textView7,
                new String[]{"张仲景", "张仲景", "张仲景", "张仲景", "张仲景", "张仲景"}));
        doctorName.setCompletionHint("是否是?");
        doctorName.setDropDownHeight(628);
        doctorName.setThreshold(1);
        doctorName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    ((AutoCompleteTextView) v).showDropDown();
                }
            }
        });
    }

    public void setBirthday(View view) {
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog datePickerDialog, int i, int i1, int i2) {
                        String str = String.format("%04d 年 %02d 月 %02d 日", i, i1, i2);
                        /* 填入生日 */
                        mBirthday.setText(str);
                        /* 计算年龄 */
                        mAge.setText(Calendar.getInstance().get(Calendar.YEAR) - i+"岁");
                    }
                }, 1980, 0, 1);
        datePickerDialog.setVibrate(false);
        datePickerDialog.show(getSupportFragmentManager(), "PICKER");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fullfill_profile, menu);
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
