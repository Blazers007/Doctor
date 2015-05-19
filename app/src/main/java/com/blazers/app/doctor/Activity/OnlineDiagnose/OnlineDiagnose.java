package com.blazers.app.doctor.Activity.OnlineDiagnose;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import com.blazers.app.doctor.R;

public class OnlineDiagnose extends ActionBarActivity {

    private Toolbar mToolbar;
    private Spinner spinnerDiease, spinnerCity, spinnerHospital;
    private ListView onlineListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_online);
        initViews();
    }

    void initViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.title_activity_chat_online);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        /* Spinner Filter */
        spinnerDiease = (Spinner) findViewById(R.id.spinnerDiease);
        spinnerCity = (Spinner) findViewById(R.id.spinnerCity);
        spinnerHospital = (Spinner) findViewById(R.id.spinnerHospital);
        spinnerDiease.setAdapter(new ArrayAdapter<String>(this, R.layout.spinner_white_text, new String[]{"按病种筛选"}));
        spinnerCity.setAdapter(new ArrayAdapter<String>(this, R.layout.spinner_white_text, new String[]{"按地区筛选"}));
        spinnerHospital.setAdapter(new ArrayAdapter<String>(this, R.layout.spinner_white_text, new String[]{"按医院筛选"}));
        /* Doctor List */
        onlineListView = (ListView) findViewById(R.id.doctor_list);
        onlineListView.setAdapter(new DoctorListAdapter(LayoutInflater.from(this)));
        onlineListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(OnlineDiagnose.this, DoctorOnlineChat.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chat_online, menu);
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
