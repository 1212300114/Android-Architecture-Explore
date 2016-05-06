package com.jijunjie.androidlibrarysystem.ui.activity;

import android.content.Context;
import android.content.Intent;
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
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.jijunjie.androidlibrarysystem.R;
import com.jijunjie.androidlibrarysystem.adapter.PagerFragmentAdapter;
import com.jijunjie.androidlibrarysystem.ui.fragments.FragmentBeauty;
import com.jijunjie.androidlibrarysystem.ui.fragments.FragmentFavor;
import com.jijunjie.androidlibrarysystem.ui.fragments.FragmentSearch;
import com.jijunjie.myandroidlib.utils.KeyBoardUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawer;
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.viewPager)
    ViewPager viewPager;
    private int[] titleIDs = new int[]{R.string.title_first, R.string.title_second, R.string.title_third};
    private PagerFragmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        setContentView(R.layout.activity_home_drawer);
        ButterKnife.bind(this);
        //set up tool bar
        setSupportActionBar(toolbar);
        //set up drawer control to tool bar
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        //set up navigation view
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //set up view pager
        adapter = new PagerFragmentAdapter(getSupportFragmentManager());
        adapter.setData(generateFragments(), generateTitles());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                    View view = getWindow().peekDecorView();
                    if (view != null) {
                        InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        if (KeyBoardUtils.isKeyboardOpened(HomeDrawerActivity.this))
                            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                }
            }
        });
    }

    /**
     * to get the tab title of the fragment
     *
     * @return the title
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
        switch (id) {
            case R.id.nav_home:
                startActivity(new Intent(this, HomeDrawerActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK)
                );
                break;
            case R.id.nav_manage:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.nav_info_modify:
                break;
            default:
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
