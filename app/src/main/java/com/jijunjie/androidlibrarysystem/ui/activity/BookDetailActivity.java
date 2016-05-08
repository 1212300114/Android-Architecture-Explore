package com.jijunjie.androidlibrarysystem.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jijunjie.androidlibrarysystem.R;
import com.jijunjie.androidlibrarysystem.model.Book;
import com.jijunjie.androidlibrarysystem.model.BorrowRecord;
import com.jijunjie.androidlibrarysystem.model.User;
import com.jijunjie.myandroidlib.utils.DrawableUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class BookDetailActivity extends AppCompatActivity {

    @Bind(R.id.ivBook)
    ImageView ivBook;
    @Bind(R.id.tvBook)
    EditText tvBook;
    @Bind(R.id.tvAuthor)
    EditText tvAuthor;
    @Bind(R.id.tvPress)
    EditText tvPress;
    @Bind(R.id.tvRemain)
    EditText tvRemain;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.tvBorrow)
    TextView tvBorrow;
    @Bind(R.id.tvDelete)
    TextView tvDelete;
    Book data;
    private int type = 0;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getIntent().getIntExtra("type", 0);
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
        if (type == 0) {
            tvBook.setBackgroundResource(android.R.color.transparent);
            tvAuthor.setBackgroundResource(android.R.color.transparent);
            tvPress.setBackgroundResource(android.R.color.transparent);
            tvRemain.setBackgroundResource(android.R.color.transparent);
            tvDelete.setVisibility(View.GONE);
            enableEdit(false);
            tvTitle.setText("书籍详情");
        } else {
            tvBorrow.setText("提交编辑");
            tvTitle.setText("编辑书籍");
        }
        data = (Book) getIntent().getSerializableExtra("data");
        if (data != null) {
            Log.e("tag", data.getBookName());
            DrawableUtils.displayNormalImgOnNet(ivBook, data.getBookImage().getFileUrl(this));
            tvBook.setText(data.getBookName());
            tvAuthor.setText(data.getBookAuthor());
            tvPress.setText(data.getBookPress());
            tvRemain.setText(data.getBookLeft() + "");
        }
    }

    boolean enable = false;

    @OnClick(R.id.tvBorrow)
    public void borrow() {
        if (type != 0) {
            String changedBookName = tvBook.getText().toString().trim();
            String changedAuthor = tvAuthor.getText().toString().trim();
            String changedPress = tvPress.getText().toString().trim();
            String left = tvRemain.getText().toString().trim();
            if (TextUtils.isEmpty(changedAuthor) || TextUtils.isEmpty(changedBookName)
                    || TextUtils.isEmpty(changedPress) || TextUtils.isEmpty(left)) {
                Toast.makeText(getApplicationContext(), "请输入完整的信息", Toast.LENGTH_SHORT).show();
                return;
            }
            data.setBookName(changedBookName);
            data.setBookAuthor(changedAuthor);
            data.setBookPress(changedPress);
            data.setBookLeft(Integer.valueOf(left));
            data.update(this, data.getObjectId(), new UpdateListener() {
                @Override
                public void onSuccess() {
                    Toast.makeText(getApplicationContext(), "修改成功", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(int i, String s) {
                    Toast.makeText(getApplicationContext(), "修改失败" + s, Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            if (data.getBookLeft() == 0) {
                Toast.makeText(getApplicationContext(), "已无余量借书失败", Toast.LENGTH_SHORT).show();
            } else {
                User currentUser = User.getCurrentUser(this, User.class);
                if (currentUser == null)
                    return;
                BorrowRecord record = new BorrowRecord();
                record.setBookID(data.getBookID());
                record.setUserId(currentUser.getUserId());
                record.save(this, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(getApplicationContext(), "借书成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Toast.makeText(getApplicationContext(), "借书失败" + s, Toast.LENGTH_SHORT).show();
                    }
                });
                data.setBookLeft(data.getBookLeft() - 1);
                data.update(this, data.getObjectId(), new UpdateListener() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onFailure(int i, String s) {
                    }
                });
            }


        }
    }

    @OnClick(R.id.tvDelete)
    public void delete() {
        data.delete(this, new DeleteListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(getApplicationContext(), "删除成功", Toast.LENGTH_SHORT).show();
                BookDetailActivity.this.onBackPressed();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(getApplicationContext(), "删除失败" + s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void enableEdit(boolean enable) {
        tvAuthor.setFocusable(enable);
        tvBook.setFocusable(enable);
        tvPress.setFocusable(enable);
        tvRemain.setFocusable(enable);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, R.anim.default_push_right_out);
    }
}
