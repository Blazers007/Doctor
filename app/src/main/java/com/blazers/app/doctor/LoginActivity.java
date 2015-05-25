package com.blazers.app.doctor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindCallback;
import cn.bmob.v3.listener.FindListener;
import com.afollestad.materialdialogs.MaterialDialog;
import com.blazers.app.doctor.Activity.Register.RegisterActivity;
import com.blazers.app.doctor.BmobModel.RegisterInfo;
import org.json.JSONArray;

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
    }

    public void doRegister(View view) {
        startActivityForResult(new Intent(this, RegisterActivity.class), REGISTER_CODE);
    }

    public void doLogin(View view) {
//        startActivity(new Intent(this, MainActivity.class));
//        finish();
        BmobQuery<RegisterInfo> query = new BmobQuery<>();
        query.addWhereEqualTo("userPhone", user.getText().toString());
        query.findObjects(this, new FindListener<RegisterInfo>() {
            @Override
            public void onSuccess(List<RegisterInfo> list) {
                if (list.size() == 0) {
                    new MaterialDialog.Builder(LoginActivity.this)
                            .title("该手机号尚未注册")
                            .content("您输入的手机号码尚未注册，是否注册新用户?")
                            .negativeText("算了")
                            .positiveText("免费注册")
                            .callback(new MaterialDialog.ButtonCallback() {
                                @Override
                                public void onPositive(MaterialDialog dialog) {
                                    super.onPositive(dialog);
                                    startActivityForResult(new Intent(LoginActivity.this, RegisterActivity.class), REGISTER_CODE);
                                }
                            })
                            .build()
                            .show();
                } else if (list.size() == 1) {
                    if (list.get(0).getUserPwd().equals(password.getText().toString())) {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else {
                        /* 密码错误！ */
                        Toast.makeText(LoginActivity.this, "密码错误!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onError(int i, String s) {
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