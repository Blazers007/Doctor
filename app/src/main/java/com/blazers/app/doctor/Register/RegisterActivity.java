package com.blazers.app.doctor.Register;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.blazers.app.doctor.R;
import com.fourmob.datetimepicker.date.DatePickerDialog;

public class RegisterActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    /*User Info*/
    private TextView mBirthday, mAge;

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
        /**/
        mBirthday = (TextView) findViewById(R.id.register_birthday);
        mAge = (TextView) findViewById(R.id.register_old);
    }
    public void setBirthday(View view) {
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog datePickerDialog, int i, int i1, int i2) {
                        mBirthday.setText(i+"年"+i1+"月"+i2+"日");
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