package com.blazers.app.doctor.Activity.Register;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.bmob.im.BmobUserManager;
import cn.bmob.v3.listener.SaveListener;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;
import com.blazers.app.doctor.BmobModel.AppUserModel;
import com.blazers.app.doctor.R;
import com.blazers.app.doctor.Util.LocationParser;
import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Calendar;
import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    @InjectView(R.id.toolbar) Toolbar mToolbar;
    /*User Info*/
    @InjectView(R.id.register_birthday) TextView mBirthday;
    @InjectView(R.id.register_age) TextView mAge;

    @InjectView(R.id.register_username) MaterialEditText mRealname;
    private Spinner mProvince, mCity, mDistrict;
    @InjectView(R.id.register_doctor_info) AutoCompleteTextView doctorName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.inject(this);
        initViews();
    }

    void initViews() {
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
                mCity.setAdapter(new ArrayAdapter<>(RegisterActivity.this,
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
                mDistrict.setAdapter(new ArrayAdapter<>(RegisterActivity.this,
                        android.R.layout.simple_spinner_dropdown_item,
                        LocationParser.getInstance(RegisterActivity.this).getDistrictsByCity(parent.getSelectedItem().toString())));
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
        switch (item.getItemId()) {
            case R.id.action_settings:
                sendSMSCode();
                return true;
            case R.id.action_submit:
                submit();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    void sendSMSCode() {
        //打开注册页面
        RegisterPage registerPage = new RegisterPage();
        registerPage.setRegisterCallback(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                // 解析注册结果
                if (result == SMSSDK.RESULT_COMPLETE) {
                    @SuppressWarnings("unchecked")
                    HashMap<String,Object> phoneMap = (HashMap<String, Object>) data;
                    String country = (String) phoneMap.get("country");
                    String phone = (String) phoneMap.get("phone");

                    // 提交用户信息
                    Log.e("Register Info", "C:" + country +  "  P:" + phone);
//                    registerUser(country, phone);
                    phoneNumber = phone;
                }
            }
        });
        registerPage.show(this);
    }

    /* 用于注册的相关信息 */
    private String phoneNumber;
    private AppUserModel register;

    void submit() {
        register = new AppUserModel();
        register.setUsername("18321704036");
        register.setPassword("taaita1314");
        register.setEmail("308802880@qq.com");
        register.setRole("patient");
        /* Extra */
        register.setRealName(mRealname.getText().toString());
        /* 必须采用 signUp方法进行注册 */
        register.signUp(this, new SaveListener() {
            @Override
            public void onSuccess() {
                Log.e("Save Success", "ID: " + register.getObjectId());
                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                BmobUserManager.getInstance(RegisterActivity.this).bindInstallationForRegister(register.getObjectId());
                /* 更新地理信息 */
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onFailure(int i, String s) {
                Log.e("Save Failed", s);
            }
        });
    }
}