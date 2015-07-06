package com.blazers.app.doctor.ui.activity.register;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.bmob.im.BmobUserManager;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.VerifySMSCodeListener;
import com.afollestad.materialdialogs.MaterialDialog;
import com.blazers.app.doctor.model.bmob.AppUserModel;
import com.blazers.app.doctor.R;
import com.blazers.app.doctor.util.LocationParser;
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

    /* Vars */
    private String PHONE_NUMBER;
    private AppUserModel register;

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

    void getPhoneNumber() {
        /* 获取手机号码 如果不能获取则弹框要求填写 */
        TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        String tel = tm.getLine1Number();//手机号码
        if (tel == null || tel.length() <= 11) {
            final EditText editText = new EditText(this);
            editText.setInputType(InputType.TYPE_CLASS_PHONE);
            MaterialDialog dialog = new MaterialDialog.Builder(this)
                    .title("填写您的手机号")
                    .customView(editText, false)
                    .positiveText("确定发送")
                    .negativeText("取消")
                    .cancelable(false)
                    .autoDismiss(false)
                    .callback(new MaterialDialog.ButtonCallback() {
                        @Override
                        public void onPositive(MaterialDialog dialog) {
                            /* 验证合法性 并有动画震动提示 */
                            if (editText.length() < 11) {
                                /* 提示输入错误 */
                                Snackbar.make(mToolbar, "输入的手机号过短", Snackbar.LENGTH_SHORT).show();
                            } else {
                                PHONE_NUMBER = editText.getText().toString();
                                sendSMSCode();
                                dialog.dismiss();
                            }
                        }

                        @Override
                        public void onNegative(MaterialDialog dialog) {
                            super.onNegative(dialog);
                        }
                    })
                    .build();
            dialog.show();
        } else {
            PHONE_NUMBER = tel;
            sendSMSCode();
        }
    }

    void sendSMSCode() {
        BmobSMS.requestSMSCode(this, PHONE_NUMBER, "test1",new RequestSMSCodeListener() {
            @Override
            public void done(Integer integer, BmobException e) {
                if(e == null){//验证码发送成功
                    Log.i("smile", "短信id："+integer);//用于查询本次短信发送详情
                }
            }
        });

        /* 开启验证页面 尝试自动读取验证码 在这注册一个短信监听的Receiver进行监听填写操作 并判断是否为邀请的用户 是则自动弹出需要填写的内容 以及医生信息   */
    }

    /* 可以自动调用(读短信取验证码) 或手动调用验证 */
    void verifyCode(String code) {
        BmobSMS.verifySmsCode(this, PHONE_NUMBER, code, new VerifySMSCodeListener() {
            @Override
            public void done(BmobException e) {
                if(e == null){
                //短信验证码已验证成功
                    Log.i("smile", "验证通过");
                }else{
                    Log.i("smile", "验证失败：code ="+e.getErrorCode()+",msg = "+e.getLocalizedMessage());
                }
            }
        });
    }

    /* 提交注册信息 并返回注册结果 自动跳转 */
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


    /* 短信Receiver */
    class SMSReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            /* 判断号码或文字确认短信 并提取短信内容 自动填写  */
        }
    }
}