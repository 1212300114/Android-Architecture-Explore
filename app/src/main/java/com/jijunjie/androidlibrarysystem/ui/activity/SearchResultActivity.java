package com.jijunjie.androidlibrarysystem.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jijunjie.androidlibrarysystem.R;
import com.jijunjie.androidlibrarysystem.adapter.FavourListAdapter;
import com.jijunjie.androidlibrarysystem.model.Book;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;


public class SearchResultActivity extends AppCompatActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.lvBooks)
    ListView lvBooks;
    private FavourListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String input = getIntent().getStringExtra("input");
        boolean isBookName = getIntent().getBooleanExtra("isBookName", true);
        setContentView(R.layout.activity_search_result);
        ButterKnife.bind(this);

        // Set up the toolbar.
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchResultActivity.this.onBackPressed();
            }
        });
        tvTitle.setText("搜索结果");

        adapter = new FavourListAdapter(this);
        lvBooks.setAdapter(adapter);
        Log.e("current search arg", "input = " + input + " isBookName = " + isBookName);
        BmobQuery<Book> bookSearch = new BmobQuery<>();
        String column = isBookName ? "bookName" : "bookAuthor";
        bookSearch.addWhereContains(column, input);
        Log.e("current tag", "column = " + column + " input = " + input);
        bookSearch.order("bookID");
        bookSearch.findObjects(this, new FindListener<Book>() {
            @Override
            public void onSuccess(List<Book> list) {
                if (null != list && list.size() > 0) {
                    Toast.makeText(SearchResultActivity.this.getApplicationContext(),
                            "查询到" + list.size() + "个结果", Toast.LENGTH_SHORT).show();
                    adapter.setBooks((ArrayList<Book>) list);
                } else {
                    Toast.makeText(SearchResultActivity.this.getApplicationContext(),
                            "查询无结果", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(SearchResultActivity.this.getApplicationContext(),
                        "查询无结果", Toast.LENGTH_SHORT).show();
                Log.e("error", "error = " + s + " i = " + i);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, R.anim.default_push_right_out);
    }
}
