package com.blazers.app.doctor.ui.activity.register;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.*;
import android.widget.*;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.bmob.im.BmobUserManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.VerifySMSCodeListener;
import com.afollestad.materialdialogs.MaterialDialog;
import com.blazers.app.doctor.R;
import com.blazers.app.doctor.library.view.LockedViewPager;
import com.blazers.app.doctor.model.bmob.AppUserModel;
import com.blazers.app.doctor.model.bmob.DoctorSetting;
import com.blazers.app.doctor.model.bmob.Invite;
import com.blazers.app.doctor.util.LocationParser;
import com.blazers.app.doctor.util.PictureUtils;
import com.blazers.app.doctor.util.StorageConfig;
import com.bmob.BTPFileResponse;
import com.bmob.BmobProFile;
import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.rengwuxian.materialedittext.MaterialEditText;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    @InjectView(R.id.toolbar) Toolbar mToolbar;
    @InjectView(R.id.register_pager) LockedViewPager mViewPager;
    private CountDownTimer mTimer;
    private boolean mVerified = false;
    private Invite mInviteInformation;
    /* Page 1 */
    private EditText mPhoneNumber, mSMSCode, mPassword, mConfirm;;
    private Button mSMSBtn;
    /* Page 2 */
    private ImageView mUserHeadImg;
    private MaterialEditText mRealName;
    private RadioGroup mGender;
    private TextView mBirthday, mAge;
    private Spinner mProvince, mCity, mDistrict;
    /* Vars */
    private String PHONE_NUMBER;
    private String USER_HEAD_PATH, localCameraPath;
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
        /* 初始化Viewpager 与 Step */
        mViewPager.setAdapter(new RegisterAdapter());

         /* 获取手机号码 如果不能获取则弹框要求填写 */
        TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        String tel = tm.getLine1Number();//手机号码
        if (tel == null || tel.length() <= 11) {
            mPhoneNumber.setText(tel);
        }
    }

    /* PagerAdapter TODO:初始化环节太繁琐 能否精简  */
    class RegisterAdapter extends PagerAdapter {
        private ArrayList<View> views = new ArrayList<>();

        public RegisterAdapter() {
            /* Page 1 */
            View page1 = LayoutInflater.from(RegisterActivity.this).inflate(R.layout.page_register_1, mViewPager, false);
            views.add(page1);
            mPhoneNumber = (EditText) page1.findViewById(R.id.page1_phoneNumber);
            mSMSCode = (EditText) page1.findViewById(R.id.page1_smsCode);
            mSMSBtn = (Button) page1.findViewById(R.id.page1_smsBtn);
            mPassword = (EditText) page1.findViewById(R.id.page1_pwd);
            mConfirm = (EditText) page1.findViewById(R.id.page1_confirm);

            mPhoneNumber.addTextChangedListener(phoneNumberWatcher);
            mSMSBtn.setEnabled(false);
            mSMSBtn.setOnClickListener(pagerClickController);
            page1.findViewById(R.id.btn_next1).setOnClickListener(pagerClickController);
            /* Page 2 */
            View page2 = LayoutInflater.from(RegisterActivity.this).inflate(R.layout.page_register_2, mViewPager, false);
            views.add(page2);
            mUserHeadImg = (ImageView) page2.findViewById(R.id.register_photo);
            mRealName = (MaterialEditText) page2.findViewById(R.id.register_username);
            mBirthday = (TextView) page2.findViewById(R.id.register_birthday);
            mAge = (TextView) page2.findViewById(R.id.register_age);
            mProvince = (Spinner) page2.findViewById(R.id.spinner);
            mCity = (Spinner) page2.findViewById(R.id.spinner2);
            mDistrict = (Spinner) page2.findViewById(R.id.spinner3);
            page2.findViewById(R.id.btn_next2).setOnClickListener(pagerClickController);

            mBirthday.setClickable(true);
            mBirthday.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
            });

            mProvince.setAdapter(new ArrayAdapter<>(RegisterActivity.this,
                    android.R.layout.simple_spinner_dropdown_item,
                    LocationParser.getInstance(RegisterActivity.this).getProvinces()));
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
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(views.get(position));
            return views.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(position));
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    /* 检查用户输入的手机号是否正确 */
    private TextWatcher phoneNumberWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            mSMSBtn.setEnabled(s.length() >= 11);
            PHONE_NUMBER = mPhoneNumber.getText().toString();
        }
    };

    /* 每一页的点击处理函数 */
    private View.OnClickListener pagerClickController = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_next1:
                    /* 跳转到第二页 */
                    verifyCode();
                    break;
                case R.id.page1_smsBtn:
                    sendSMSCode();
                    break;
                case R.id.btn_next2:
                    /* 第二个页面 此时已经注册成功 但是仅有基本信息 */
                    submit();
                    break;
            }
        }
    };

    /* 发送验证码 */
    void sendSMSCode() {
        mSMSBtn.setText(getString(R.string.sending_sms_code));
        mSMSBtn.setEnabled(false);
        /* 发送 */
        BmobSMS.requestSMSCode(this, PHONE_NUMBER, "test1", new RequestSMSCodeListener() {
            @Override
            public void done(Integer integer, BmobException e) {
                if(e == null){//验证码发送成功
                    Log.i("smile", "短信id："+integer);//用于查询本次短信发送详情
                    /* 倒计时 */
                    mSMSBtn.setEnabled(false);
                    mTimer = new CountDownTimer(1000*60, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            mSMSBtn.setText(millisUntilFinished/1000 + "秒");
                        }

                        @Override
                        public void onFinish() {
                            mSMSBtn.setEnabled(true);
                            mSMSBtn.setText(getString(R.string.send_sms_code));
                        }
                    }.start();
                }else{
                    /* 重新发送 应该有个次数限定重试次数 */
                    sendSMSCode();
                }
            }
        });
    }

    /* 可以自动调用(读短信取验证码) 或手动调用验证 */
    void verifyCode() {
       if (!mVerified) {
           String code = mSMSCode.getText().toString();
           if (code.length() < 6) {
               Snackbar.make(mToolbar, "验证码为六位数字", Snackbar.LENGTH_SHORT).show();
           }
        /* 本地校验 */
           BmobSMS.verifySmsCode(this, PHONE_NUMBER, code, new VerifySMSCodeListener() {
               @Override
               public void done(BmobException e) {
                   if(e == null){
                       Log.i("smile", "验证通过");
                       /* 采用带校验的√  附带动画效果 */
                       mVerified = true;
                   }else{
                       mVerified = false;
                       Log.i("SMS Verify", "验证失败：code =" + e.getErrorCode() + ",msg = " + e.getLocalizedMessage());
                   }
               }
           });
       } else {
           checkPassword();
       }
    }

    /* 检查密码是否一致 是否符合要求 */
    void checkPassword() {
        if(mPassword.getText().length() < 6) {
            Snackbar.make(mToolbar, "密码长度至少为6位", Snackbar.LENGTH_SHORT).show();
        }else if (!mPassword.getText().toString().equals(mConfirm.getText().toString())){
            Snackbar.make(mToolbar, "请确认您两次的密码输入一致", Snackbar.LENGTH_SHORT).show();
        }else {
            mViewPager.setCurrentItem(1, true);
        }
    }

    void uploadUserHead(File file){
        BTPFileResponse response = BmobProFile.getInstance(this).upload(file.getPath(), new com.bmob.btp.callback.UploadListener() {
            @Override
            public void onSuccess(String fileName, String url) {
                String URL =BmobProFile.getInstance(RegisterActivity.this)
                        .signURL(fileName, url, "eb5830969fd9f69c26e4e4f57ec772f7", 0, null);
            }

            @Override
            public void onProgress(int ratio) {
                Log.e("Progress", "" + ratio);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    /* 提交注册信息 并返回注册结果 自动跳转 */
    void submit() {
        /* 校验相关字段是否完整 或使用InputChecker 或 EditText Wrapper */
        /* 组装Register数据 提交 并显示进度条 */
        JSONObject location = null, extend = null;
        try {
            extend = new JSONObject();
        }catch (Exception e){
            e.printStackTrace();
        }
        register = new AppUserModel();
        register.setUsername(PHONE_NUMBER);
        register.setPassword(mPassword.getText().toString());
        register.setRealName(mRealName.getText().toString());
        register.setUserHeadSrc("http://apks.bj.bcebos.com/head.jpg?responseContentDisposition=attachment");
        register.setEmail("308802880@qq.com");
        register.setBirthday(mBirthday.getText().toString());
        register.setLocation(mProvince.getSelectedItem().toString()+"-"+mCity.getSelectedItem().toString()+"-"+mDistrict.getSelectedItem().toString());
        register.setRole("patient");
        /* Extra */
        /* 必须采用 signUp方法进行注册 */
        register.signUp(this, new SaveListener() {
            @Override
            public void onSuccess() {
                Log.e("Save Success", "ID: " + register.getObjectId());
                Snackbar.make(mToolbar, "注册成功", Snackbar.LENGTH_SHORT).show();
                BmobUserManager.getInstance(RegisterActivity.this).bindInstallationForRegister(register.getObjectId());
                /* 更新地理信息 跳转到页面三 */
                mViewPager.setCurrentItem(2, true);
            }

            @Override
            public void onFailure(int i, String s) {
                Log.e("Save Failed", s);
            }
        });
    }



    /* 如果被邀请 则读取配置文件生成填写的表单 */
    void makeSpecialForm(String doc, String array) {
        BmobQuery<DoctorSetting> query = new BmobQuery<>();
        query.addWhereEqualTo("DoctorId",doc);
        query.findObjects(this, new FindListener<DoctorSetting>() {
            @Override
            public void onSuccess(List<DoctorSetting> list) {
                Log.e("JSON", list.get(0).getInviteSetting());
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }


    /* 未被邀请的则只填写基本情况 */
    void makeNormalForm(){

    }

    /* 日后在填写内容 */
    void fullfillLater() {

    }

    @Override
    protected void onResume() {
        super.onResume();
         /* 监听短信 */
        mReceiver = new SMSReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(mReceiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mReceiver != null)
            unregisterReceiver(mReceiver);
    }

    /* 短信Receiver */
    private SMSReceiver mReceiver;
    class SMSReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 第一步、获取短信的内容和发件人
            StringBuilder body = new StringBuilder();// 短信内容
            StringBuilder number = new StringBuilder();// 短信发件人
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");
                SmsMessage[] message = new SmsMessage[pdus.length];
                for (int i = 0; i < pdus.length; i++) {
                    message[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                }
                for (SmsMessage currentMessage : message) {
                    body.append(currentMessage.getDisplayMessageBody());
                    number.append(currentMessage.getDisplayOriginatingAddress());
                }
                String smsBody = body.toString();
                // 第二步 去除国家编号
                String smsNumber = number.toString();
                if (smsNumber.contains("+86")) {
                    smsNumber = smsNumber.substring(3);
                }
                Log.e("SMS", "短信号码:"+smsNumber+"  短信内容:"+smsBody);
                if (smsBody.contains("您的验证码是")) {
                    int index = smsBody.indexOf("您的验证码是");
                    String code = smsBody.substring(index + 6,index + 12);
                    mSMSCode.setText(code);
                }
            }
        }
    }


    void capturePhtot() {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File dir = new File(StorageConfig.BMOB_PICTURE_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, String.valueOf(System.currentTimeMillis())
                + ".jpg");
        localCameraPath = file.getPath();
        Uri imageUri = Uri.fromFile(file);
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(openCameraIntent,
                StorageConfig.REQUESTCODE_TAKE_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case StorageConfig.REQUESTCODE_TAKE_CAMERA:
                    USER_HEAD_PATH = PictureUtils.compressBitmap(localCameraPath);
                    /* 展示图片 */
                    ImageLoader.getInstance().displayImage("file://" + USER_HEAD_PATH, mUserHeadImg);
                    /* 出现确认/重选按钮 以及进度条? 其他APP如何使用该方式*/
                    break;
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }
}