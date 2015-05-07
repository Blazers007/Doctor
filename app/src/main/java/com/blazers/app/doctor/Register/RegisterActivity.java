package com.blazers.app.doctor.Register;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.blazers.app.doctor.R;
import com.blazers.app.doctor.Util.LocationParser;
import com.fourmob.datetimepicker.date.DatePickerDialog;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    /*User Info*/
    private TextView mBirthday, mAge;
    private Spinner mProvince, mCity, mDistrict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initViews();
    }

    void initViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.title_activity_register);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        /**/
        mBirthday = (TextView) findViewById(R.id.register_birthday);
        mAge = (TextView) findViewById(R.id.register_age);
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
                mCity.setAdapter(new ArrayAdapter<String>(RegisterActivity.this,
                        android.R.layout.simple_spinner_dropdown_item,
                        LocationParser.getInstance(RegisterActivity.this).getCitiesByProvince(parent.getSelectedItem().toString())));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mDistrict.setAdapter(new ArrayAdapter<String>(RegisterActivity.this,
                        android.R.layout.simple_spinner_dropdown_item,
                        LocationParser.getInstance(RegisterActivity.this).getDistrictsByCity(parent.getSelectedItem().toString())));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
        getMenuInflater().inflate(R.menu.menu_register, menu);
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