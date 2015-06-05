package com.blazers.app.doctor.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.*;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.blazers.app.doctor.R;
import uk.co.senab.photoview.PhotoView;

import java.util.ArrayList;

public class ImageViewerActivity extends AppCompatActivity {

    @InjectView(R.id.viewPager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);
        ButterKnife.inject(this);
        /* 初始化 */
        initViews();
        /* 设定当前选中的图片页面 */
    }

    void initViews() {
        Intent intent = getIntent();
        viewPager.setAdapter(new ImageViewerPagerAdapter(intent.getStringArrayListExtra("urls")));
    }

    class ImageViewerPagerAdapter extends PagerAdapter {

        private final ArrayList<String> urls;

        public ImageViewerPagerAdapter(ArrayList<String> urls) {
            this.urls = urls;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            PhotoView photoView = new PhotoView(container.getContext());
            photoView.setImageURI(Uri.parse(urls.get(position)));
            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }



        @Override
        public int getCount() {
            return urls.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image_viewer, menu);
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
