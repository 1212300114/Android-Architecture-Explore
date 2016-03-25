package com.jijunjie.androidlibrarysystem.ui.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.jijunjie.androidlibrarysystem.R;
import com.jijunjie.androidlibrarysystem.adapter.PagerFragmentAdapter;
import com.jijunjie.androidlibrarysystem.ui.fragments.FragmentBeauty;
import com.jijunjie.androidlibrarysystem.ui.fragments.FragmentFavor;
import com.jijunjie.androidlibrarysystem.ui.fragments.FragmentSearch;

import java.util.ArrayList;

public class HomeDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int[] titleIDs = new int[]{R.string.title_first, R.string.title_second, R.string.title_third};
    private PagerFragmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
    }

    private void initView() {
        setContentView(R.layout.activity_home_drawer);
        //set up tool bar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //set up drawer control to tool bar
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        //set up navigation view
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //set up view pager
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        adapter = new PagerFragmentAdapter(getSupportFragmentManager());
        adapter.setData(generateFragments(), generateTitles());
//        adapter.setData();
        // TODO: 16/3/2  need set adapter
        viewPager.setAdapter(adapter);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

    }


    /**
     * to get the tab title of the fragment
     *
     * @return the titl
     */
    private ArrayList<String> generateTitles() {
        ArrayList<String> titles = new ArrayList<>();
        for (int titleID : titleIDs) {
            titles.add(getResources().getString(titleID));
        }
        return titles;
    }

    private ArrayList<Fragment> generateFragments() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new FragmentFavor());
        fragments.add(new FragmentSearch());
        fragments.add(new FragmentBeauty());
        return fragments;
    }

    long lastPressTime = 0;

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            long currentTimeMillis = System.currentTimeMillis();

            if ((currentTimeMillis - lastPressTime) > 3000) {
                lastPressTime = currentTimeMillis;
                Toast.makeText(HomeDrawerActivity.this, "再次按下退出程序", Toast.LENGTH_SHORT).show();
            } else {

                super.onBackPressed();
            }

        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_slideshow) {
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

    @Override
    protected void onPause() {
        super.onPause();
    }
}
