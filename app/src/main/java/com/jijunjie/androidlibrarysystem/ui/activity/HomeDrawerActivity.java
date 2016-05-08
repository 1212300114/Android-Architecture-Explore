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
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jijunjie.androidlibrarysystem.R;
import com.jijunjie.androidlibrarysystem.adapter.PagerFragmentAdapter;
import com.jijunjie.androidlibrarysystem.model.User;
import com.jijunjie.androidlibrarysystem.ui.fragments.FragmentBeauty;
import com.jijunjie.androidlibrarysystem.ui.fragments.FragmentFavor;
import com.jijunjie.androidlibrarysystem.ui.fragments.FragmentSearch;
import com.jijunjie.myandroidlib.utils.DrawableUtils;
import com.jijunjie.myandroidlib.utils.KeyBoardUtils;
import com.jijunjie.myandroidlib.utils.SharedPreferenceUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


public class HomeDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawer;
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.viewPager)
    ViewPager viewPager;

    /**
     * view inside navigation view
     */
    TextView textView;
    TextView tvUserName;
    ImageView ivUserIcon;
    private int[] titleIDs = new int[]{R.string.title_first, R.string.title_second, R.string.title_third};
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentUser = User.getCurrentUser(this, User.class);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //update user icon if it is set
        String path = (String) SharedPreferenceUtils.get(this, "icon", "");
        if (!TextUtils.isEmpty(path)) {
            DrawableUtils.disPlayLocRoundImg(ivUserIcon, path);
        }
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
        ivUserIcon = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.imageView);
        textView = (TextView) navigationView.getHeaderView(0).findViewById(R.id.textView);
        tvUserName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tvUserName);
        if (currentUser != null) {
            DrawableUtils.disPlayLocRoundImg(ivUserIcon, R.drawable.user_icon);
            tvUserName.setText(currentUser.getUsername());
            if (currentUser.getUserType() == 1) {
                textView.setText("管理员");
            } else {
                textView.setText("普通用户");
            }
        } else {

        }
        //set up view pager
        PagerFragmentAdapter adapter = new PagerFragmentAdapter(getSupportFragmentManager());
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
                overridePendingTransition(R.anim.bottom_slide_in_anim, R.anim.do_nothing_anim);
                break;
            case R.id.nav_manage:
                if (currentUser == null) {
                    startActivity(new Intent(this, LoginActivity.class));
                    overridePendingTransition(R.anim.bottom_slide_in_anim, R.anim.do_nothing_anim);
                } else {
                    if (currentUser.getUserType() != 1) {
                        Toast.makeText(getApplicationContext(), "非管理员用户无法管理书籍", Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        startActivity(new Intent(this, ManageActivity.class));
                        Toast.makeText(getApplicationContext(), "管理书籍", Toast.LENGTH_SHORT).show();
                        overridePendingTransition(R.anim.default_push_left_in, R.anim.do_nothing_anim);
                    }
                }
                break;
            case R.id.nav_info_modify:
                if (currentUser != null) {
                    startActivity(new Intent(this, UserInfoActivity.class));
                    overridePendingTransition(R.anim.default_push_left_in, R.anim.do_nothing_anim);
                } else {
                    startActivity(new Intent(this, LoginActivity.class));
                    overridePendingTransition(R.anim.bottom_slide_in_anim, R.anim.do_nothing_anim);
                }
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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imageView) {
            Toast.makeText(getApplicationContext(), "image click", Toast.LENGTH_SHORT).show();
        }
    }
}
