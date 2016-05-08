package com.jijunjie.androidlibrarysystem.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jijunjie.androidlibrarysystem.R;
import com.jijunjie.androidlibrarysystem.adapter.PagerFragmentAdapter;
import com.jijunjie.androidlibrarysystem.ui.fragments.FragmentAddBook;
import com.jijunjie.androidlibrarysystem.ui.fragments.FragmentDownBook;
import com.jijunjie.androidlibrarysystem.ui.fragments.FragmentFavor;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ManageActivity extends AppCompatActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.viewPager)
    ViewPager viewPager;
    private PagerFragmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);
        ButterKnife.bind(this);
        initToolbar();
        initViews();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ManageActivity.this.onBackPressed();
            }
        });
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_white_24dp);
        getSupportActionBar().setTitle("书籍管理");
    }

    private void initViews() {
        adapter = new PagerFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        adapter.setData(generateFragments(), generateTitles());
        tabLayout.setupWithViewPager(viewPager);
    }

    private ArrayList<String> generateTitles() {
        ArrayList<String> titles = new ArrayList<>();
        titles.add("添加书籍");
        titles.add("修改书籍");
        titles.add("下架书籍");
        return titles;
    }

    private ArrayList<Fragment> generateFragments() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new FragmentAddBook());
        FragmentFavor favor = new FragmentFavor();
        Bundle arg = new Bundle();
        arg.putInt("type", 1);
        favor.setArguments(arg);
        fragments.add(favor);
        fragments.add(new FragmentDownBook());
        return fragments;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.do_nothing_anim, R.anim.default_push_right_out);
    }
}
