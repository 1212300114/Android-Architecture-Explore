package com.jijunjie.androidlibrarysystem.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.jijunjie.androidlibrarysystem.R;
import com.jijunjie.myandroidlib.view.BannerView.BannerView;
import com.jijunjie.myandroidlib.view.BannerView.BaseBannerEntity;

import java.util.ArrayList;

import cn.bmob.v3.Bmob;

public class HomeDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    BannerView bannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();

//        BmobQuery<Book> query = new BmobQuery<>();
//        query.setLimit(10);
//        query.findObjects(this, new FindListener<Book>() {
//            @Override
//            public void onSuccess(List<Book> list) {
//                Log.e("get!1", "yes!!!");
//                for (Book book :
//                        list) {
//                    Log.e("bookName", book.getBookName());
//                    Log.e("bookType", book.getClassName());
//                }
////                tvHello.setText(list.get(0).getBookName());
//            }
//
//            @Override
//            public void onError(int i, String s) {
//                Log.e("error", "error");
//            }
//        });

    }

    private void initView() {
        setContentView(R.layout.activity_home_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bmob.initialize(this, "eeea90c06c19494c0c2610bb59c23e44");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        fab.setVisibility(View.GONE);
        toolbar.setLogo(R.drawable.ic_menu_camera);
        toolbar.setTitle("My DrawerActivity");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        bannerView = (BannerView) findViewById(R.id.bannerView);
        ArrayList<BaseBannerEntity> entities = new ArrayList<>(3);
        BaseBannerEntity entity = new BaseBannerEntity();
        entity.setTitle("heiheihei");
        entity.setImgUrl("http://www.jjjc.yn.gov.cn/getimg.php?file=/upload/2016-02/26/8e6f53210380ec457e959748abf6eb75_thumb.jpg");
        entities.add(entity);
        entity = new BaseBannerEntity();//have to new or it will use the same object in memory
        entity.setTitle("hahaha");
        entity.setImgUrl("http://www.jjjc.yn.gov.cn/getimg.php?file=/upload/2016-02/26/fdf87c4c39d8d9924d15aab8fd698fe6_thumb.jpg");
        entities.add(entity);
        entity = new BaseBannerEntity();
        entity.setTitle("lalala");
        entity.setImgUrl("http://www.jjjc.yn.gov.cn/getimg.php?file=/upload/2016-02/25/310413c2c566746971d241fbce78bcb3_thumb.jpg");
        entities.add(entity);
        bannerView.setBannerEntitiesAndLoopEnable(entities, false);
        bannerView.setOnBannerClickListener(new BannerView.onBannerClickListener() {
            @Override
            public void click(BaseBannerEntity entity, int position) {
                Log.e("click event", "click at position = " + position);
            }
        });
        bannerView.setAutoPlay();
        BannerView bannerView1 = (BannerView) findViewById(R.id.bannerViewTwo);
        bannerView1.setBannerEntitiesAndLoopEnable(entities, true);
        bannerView1.setAutoPlay();
        BannerView bannerView2 = (BannerView) findViewById(R.id.bannerViewThree);
        bannerView2.setBannerEntitiesAndLoopEnable(entities, true);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_menu, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_slideshow) {
            startActivity(new Intent(this, ScrollingActivity.class));
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        Log.e("click tag", "clicked!");
    }

}
