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
import butterknife.OnClick;
import cn.bmob.im.BmobUserManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.VerifySMSCodeListener;
import com.afollestad.materialdialogs.MaterialDialog;
import com.blazers.app.doctor.model.bmob.AppUserModel;
import com.blazers.app.doctor.R;
import com.blazers.app.doctor.model.bmob.DoctorSetting;
import com.blazers.app.doctor.model.bmob.Invite;
import com.blazers.app.doctor.util.LocationParser;
import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    @InjectView(R.id.toolbar) Toolbar mToolbar;
    /*User Info*/

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

    }

   

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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

    /* 弹出填写手机号码的弹窗 */
    void popUpVerify() {
        if (PHONE_NUMBER != null && PHONE_NUMBER.length() >= 11)
            sendSMSCode();

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
        /* 获取手机号码 如果不能获取则弹框要求填写 */
        TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        String tel = tm.getLine1Number();//手机号码
        if (tel == null || tel.length() <= 11) {
            final EditText editText = new EditText(this);
            editText.setInputType(InputType.TYPE_CLASS_PHONE);

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
    void verifyCode() {
        String code = mPhone.getText().toString();
        if(code.length() < 6) {
            return;
        }
        /* 本地校验 */
        BmobSMS.verifySmsCode(this, PHONE_NUMBER, code, new VerifySMSCodeListener() {
            @Override
            public void done(BmobException e) {
                if(e == null){
                    //短信验证码已验证成功
                    Log.i("smile", "验证通过");
                    /* 根据号码读取配置 */
                    BmobQuery<Invite> query = new BmobQuery<>();
                    query.addWhereEqualTo("PatientPhone", PHONE_NUMBER);
                    query.findObjects(RegisterActivity.this, new FindListener<Invite>() {
                        @Override
                        public void onSuccess(List<Invite> list) {
                            Log.e("查询要请","[ 成功 ] ->" + list.size());
                            if (list.size() == 0) {
                                 makeNormalForm();
                            } else {
                                Invite invite = list.get(0);
                                makeSpecialForm(invite.getDoctorId(), invite.getArray());
                            }
                        }

                        @Override
                        public void onError(int i, String s) {
                            /* TODO:有漏洞，如果为读取失败 而并非不存在该条数据怎么办 */
                            Log.e("查询要请","[ 失败 ]");
                            makeNormalForm();
                        }
                    });
                }else{
                    Log.i("smile", "验证失败：code =" + e.getErrorCode() + ",msg = " + e.getLocalizedMessage());
                }
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

    /* 提交注册信息 并返回注册结果 自动跳转 */
    void submit() {
        /* 校验相关字段是否完整 或使用InputChecker 或 EditText Wrapper */

        /* 组装Register数据 提交 并显示进度条 */
        register = new AppUserModel();
        register.setUsername(PHONE_NUMBER);
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