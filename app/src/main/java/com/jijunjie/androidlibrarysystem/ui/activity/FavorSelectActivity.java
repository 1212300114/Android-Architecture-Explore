package com.jijunjie.androidlibrarysystem.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.jijunjie.androidlibrarysystem.R;
import com.jijunjie.androidlibrarysystem.adapter.FavorSelectAdapter;
import com.jijunjie.androidlibrarysystem.model.Book;
import com.jijunjie.myandroidlib.utils.SharedPreferenceUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class FavorSelectActivity extends AppCompatActivity {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.progress)
    ProgressBar progressBar;
    private FavorSelectAdapter adapter;
    private ArrayList<String> classNames;
    private ArrayList list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (classNames == null)
            classNames = new ArrayList<>();
        setContentView(R.layout.activity_favor_select);
        ButterKnife.bind(this);
        initToolbar();
        String preferences = (String) SharedPreferenceUtils.get(this, "preferences", "");
        if (!TextUtils.isEmpty(preferences)) {
            list = new Gson().fromJson(preferences, ArrayList.class);
        }
        adapter = new FavorSelectAdapter(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(adapter);
        BmobQuery<Book> query = new BmobQuery<>();
        query.order("bookID");
        query.setLimit(50);
        query.findObjects(this, new FindListener<Book>() {
            @Override
            public void onSuccess(List<Book> list) {

                if (list != null) {
                    for (Book item : list) {
                        Log.e("result", new Gson().toJson(item));
                        if (!classNames.contains(item.getClassName()))
                            classNames.add(item.getClassName());

                    }
                    Log.e("result size", classNames.size() + "item");
                    adapter.setClassNames(classNames);
                    for (String names : classNames) {
                        if (FavorSelectActivity.this.list.contains(names)) {
                            int index = classNames.indexOf(names);
                            adapter.setSelectPosition(index);
                        }
                    }
                    progressBar.setVisibility(View.GONE);
                }

            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FavorSelectActivity.this.onBackPressed();
//            }
//        });
//        toolbar.setNavigationContentDescription("跳过");
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_white_24dp);
        getSupportActionBar().setTitle("偏好选择");
    }


    @OnClick(R.id.tvSure)
    public void sure() {
        this.onBackPressed();
        startActivity(new Intent(this, HomeDrawerActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
        ArrayList<String> selectedNames = adapter.getSelectedClassNames();
        SharedPreferenceUtils.put(this, "preferences", new Gson().toJson(selectedNames));
        overridePendingTransition(R.anim.bottom_slide_out_anim, R.anim.do_nothing_anim);
    }

    @OnClick(R.id.tvJump)
    public void jump() {
        this.onBackPressed();
        startActivity(new Intent(this, HomeDrawerActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
        overridePendingTransition(R.anim.bottom_slide_out_anim, R.anim.do_nothing_anim);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.do_nothing_anim, R.anim.default_push_right_out);
    }
}
