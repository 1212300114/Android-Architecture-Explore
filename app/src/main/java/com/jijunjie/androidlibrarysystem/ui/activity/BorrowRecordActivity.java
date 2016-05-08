package com.jijunjie.androidlibrarysystem.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.jijunjie.androidlibrarysystem.R;
import com.jijunjie.androidlibrarysystem.adapter.BorrowListAdapter;
import com.jijunjie.androidlibrarysystem.model.Book;
import com.jijunjie.androidlibrarysystem.model.BorrowRecord;
import com.jijunjie.androidlibrarysystem.model.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class BorrowRecordActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.lvBooks)
    ListView lvBooks;
    @Bind(R.id.tvEmpty)
    TextView tvEmpty;
    private BorrowListAdapter adapter;
    private List<BorrowRecord> records;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow_record);
        ButterKnife.bind(this);
        initToolbar();

        adapter = new BorrowListAdapter(this);
        lvBooks.setAdapter(adapter);
        User currentUser = User.getCurrentUser(this, User.class);
        if (currentUser == null)
            return;
        BmobQuery<BorrowRecord> bmobQuery = new BmobQuery<>();
        bmobQuery.order("createdAt");
        bmobQuery.addWhereEqualTo("userId", currentUser.getUserId());
        bmobQuery.findObjects(this, new FindListener<BorrowRecord>() {
            @Override
            public void onSuccess(List<BorrowRecord> list) {
                Log.e("result", list.size() + "result found");
                if (list.size() == 0) {
                    tvEmpty.setVisibility(View.VISIBLE);
                    return;
                }
                tvEmpty.setVisibility(View.GONE);
                records = list;
                ArrayList<Integer> bookIds = new ArrayList<>();
                for (BorrowRecord record : list) {
                    bookIds.add(record.getBookID());
                }
                BmobQuery<Book> bookBmobQuery = new BmobQuery<>();
                bookBmobQuery.addWhereContainedIn("bookID", bookIds);
                bookBmobQuery.findObjects(BorrowRecordActivity.this, new FindListener<Book>() {
                    @Override
                    public void onSuccess(List<Book> list) {
                        adapter.setBooks(list, records);
                    }

                    @Override
                    public void onError(int i, String s) {

                    }
                });
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BorrowRecordActivity.this.onBackPressed();
            }
        });
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_white_24dp);
        getSupportActionBar().setTitle("借书记录");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.do_nothing_anim,R.anim.default_push_right_out);
    }
}
