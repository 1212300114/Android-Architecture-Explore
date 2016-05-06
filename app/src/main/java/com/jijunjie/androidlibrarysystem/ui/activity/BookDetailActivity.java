package com.jijunjie.androidlibrarysystem.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jijunjie.androidlibrarysystem.R;
import com.jijunjie.androidlibrarysystem.model.Book;
import com.jijunjie.myandroidlib.utils.DrawableUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BookDetailActivity extends AppCompatActivity {

    @Bind(R.id.ivBook)
    ImageView ivBook;
    @Bind(R.id.tvBook)
    TextView tvBook;
    @Bind(R.id.tvAuthor)
    TextView tvAuthor;
    @Bind(R.id.tvPress)
    TextView tvPress;
    @Bind(R.id.tvRemain)
    TextView tvRemain;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tvTitle)
    TextView tvTitle;

    Book data;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        ButterKnife.bind(this);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookDetailActivity.this.onBackPressed();
            }
        });
        tvTitle.setText("书籍详情");
        data = (Book) getIntent().getSerializableExtra("data");
        if (data != null) {
            Log.e("tag", data.getBookName());
            DrawableUtils.displayNormalImgOnNet(ivBook, data.getBookImage().getFileUrl(this));
            tvBook.setText(data.getBookName());
            tvAuthor.setText("作者"+data.getBookAuthor());
            tvPress.setText("出版社"+data.getBookPress());
            tvRemain.setText("剩余：" + data.getBookLeft());
        }
    }

    @OnClick(R.id.tvBorrow)
    public void borrow() {
        if (data.getBookLeft() == 0)
            return;
        Toast.makeText(getApplicationContext(), "借书", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, R.anim.default_push_right_out);
    }
}
