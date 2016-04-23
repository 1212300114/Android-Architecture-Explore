package com.jijunjie.androidlibrarysystem.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.jijunjie.androidlibrarysystem.R;
import com.jijunjie.myandroidlib.utils.DrawableUtils;


public class ImageActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ImageView ivGirl;
    private String url, desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        url = getIntent().getStringExtra("url");
        desc = getIntent().getStringExtra("desc");
        initToolbar();
        initViews();
    }

    private void initViews() {
//        simpleDraweeView = (SimpleDraweeView) findViewById(R.id.draweeview);
//        GenericDraweeHierarchy hierarchy = simpleDraweeView.getHierarchy();
//        hierarchy.setProgressBarImage(new ProgressBarDrawable());
//        Uri uri = Uri.parse(url);
//        simpleDraweeView.setImageURI(uri);
        ivGirl = (ImageView) findViewById(R.id.ivGirl);
        DrawableUtils.displayNormalImgOnNet(ivGirl,url);
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_white_24dp);
        getSupportActionBar().setTitle("beautiful girls");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_image, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
//            case R.id.action_share:
//                ShareUtils.shareImage(this, SaveImageUtils.saveImage(url, desc, this));
//                break;
            case R.id.action_save:
//
// .saveImage(url, desc, this);
                Toast.makeText(this, "已经保存图片啦", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
