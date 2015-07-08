package com.blazers.app.doctor.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import com.afollestad.materialdialogs.MaterialDialog;
import com.blazers.app.doctor.R;
import com.blazers.app.doctor.library.view.NewMessageNotification;
import com.blazers.app.doctor.ui.activity.register.RegisterActivity;
import com.blazers.app.doctor.model.bmob.AppUserModel;

import java.util.List;


public class LoginActivity extends AppCompatActivity {

    private static final int REGISTER_CODE = 21;

    @InjectView(R.id.editText) EditText user;
    @InjectView(R.id.editText2) EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        /* 判断是否已经登陆 */
        AppUserModel user = BmobUser.getCurrentUser(this, AppUserModel.class);
        if (user != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("tour", false);
            intent.putExtra("name", user.getRealName());
            intent.putExtra("email", user.getEmail());
            intent.putExtra("icon", "http://www.ttoou.com/qqtouxiang/allimg/120328/co12032PR929-3-lp.jpg");
            startActivity(intent);
            finish();
        }
        /* For test */
        NewMessageNotification.notify(this, "Test", 1);
    }

    public void doRegister(View view) {
        startActivityForResult(new Intent(this, RegisterActivity.class), REGISTER_CODE);
    }

    public void doLogin(View view) {
//        startActivity(new Intent(this, MainActivity.class));
//        finish();
        BmobQuery<AppUserModel> query = new BmobQuery<>();
        query.addWhereEqualTo("username", user.getText().toString());
        query.findObjects(this, new FindListener<AppUserModel>() {
            @Override
            public void onSuccess(List<AppUserModel> list) {
                if (list.size() == 0) {
                    new MaterialDialog.Builder(LoginActivity.this)
                            .title("该手机号尚未注册")
                            .content("您输入的手机号码尚未注册，是否注册新用户?")
                            .negativeText("随便看看")
                            .positiveText("免费注册")
                            .neutralText("算了")
                            .callback(new MaterialDialog.ButtonCallback() {
                                @Override
                                public void onPositive(MaterialDialog dialog) {
                                    super.onPositive(dialog);
                                    startActivityForResult(new Intent(LoginActivity.this, RegisterActivity.class), REGISTER_CODE);
                                }

                                @Override
                                public void onNegative(MaterialDialog dialog) {
                                    super.onNegative(dialog);
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.putExtra("tour", true);
                                    startActivity(intent);
                                    finish();
                                }
                            })
                            .build()
                            .show();
                } else {
                     /* 重构采用登陆方法进行登陆 */
                    AppUserModel login = new AppUserModel();
                    login.setUsername(user.getText().toString());
                    login.setPassword(password.getText().toString());
                    login.login(LoginActivity.this, new SaveListener() {
                        @Override
                        public void onSuccess() {
                            /* 缓存登陆数据 */
                            AppUserModel user = BmobUser.getCurrentUser(LoginActivity.this, AppUserModel.class);
                            /* 跳转页面 */
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("tour", false);
                            intent.putExtra("name", user.getRealName());
                            intent.putExtra("email", user.getEmail());
                            intent.putExtra("icon", "http://www.ttoou.com/qqtouxiang/allimg/120328/co12032PR929-3-lp.jpg");
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            Log.e("登录失败", s);
                        }
                    });
                }
            }

            @Override
            public void onError(int i, String s) {
                /* 登录失败 */
                Log.e("Failure", s);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REGISTER_CODE) {
            AppUserModel user = BmobUser.getCurrentUser(this, AppUserModel.class);
            if (user != null) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("tour", false);
                intent.putExtra("name", user.getRealName());
                intent.putExtra("email", user.getEmail());
                intent.putExtra("icon", "http://www.ttoou.com/qqtouxiang/allimg/120328/co12032PR929-3-lp.jpg");
                startActivity(intent);
                finish();
            }
        }
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