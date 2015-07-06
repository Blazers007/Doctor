package com.blazers.app.doctor.test;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.TextView;
import com.blazers.app.doctor.R;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        /**/
        ((ViewPager) findViewById(R.id.big_pager)).setAdapter(new BigAdapter(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test, menu);
        return true;
    }


    class SmallAdapter extends PagerAdapter {

        private ArrayList<View> views;

        public SmallAdapter(Context ctx) {
            views = new ArrayList<>();
            for(int i = 0 ; i < 5 ; i++) {
                TextView view = new TextView(ctx);
                view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                view.setBackgroundColor(Color.rgb(i * 50,i * 40,i * 25));
                view.setText("SMALL " + i);
                view.setTextSize(30);
                views.add(view);
            }
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View v = views.get(position);
            container.addView(v);
            return v;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    class BigAdapter extends PagerAdapter {

        private ArrayList<View> views;

        public BigAdapter(Context ctx) {
            views = new ArrayList<>();
            /* small */
            View small = LayoutInflater.from(ctx).inflate(R.layout.test_small_pager, null);
            ViewPager smallPager = (ViewPager) small.findViewById(R.id.small_pager);
            smallPager.setAdapter(new SmallAdapter(ctx));
            views.add(small);
            /* Others */
            for(int i = 0 ; i < 4 ; i ++) {
                TextView view = new TextView(ctx);
                view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                view.setBackgroundColor(Color.rgb(i * 50,i * 60,i * 30));
                view.setText("OUT SIDE " + i);
                view.setTextSize(30);
                views.add(view);
            }
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View v = views.get(position);
            container.addView(v);
            return v;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
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
