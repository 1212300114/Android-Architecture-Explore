package com.jijunjie.androidlibrarysystem.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jijunjie.androidlibrarysystem.R;
import com.jijunjie.androidlibrarysystem.model.User;
import com.jijunjie.myandroidlib.utils.DrawableUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserInfoActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.ivUserIcon)
    ImageView ivUserIcon;
    @Bind(R.id.tvNickName)
    TextView tvNickName;
    @Bind(R.id.tvPhone)
    TextView tvPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        initToolbar();
        bindUserData();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfoActivity.this.onBackPressed();
            }
        });
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_white_24dp);
        getSupportActionBar().setTitle("用户信息");
    }

    private void bindUserData() {
        User currentUser = User.getCurrentUser(this, User.class);
        if (currentUser == null)
            return;
        DrawableUtils.disPlayLocRoundImg(ivUserIcon, R.drawable.user_icon);
        String nickName = currentUser.getNickName();
        if (!TextUtils.isEmpty(nickName)) {
            tvNickName.setText(nickName);
        } else {
            tvNickName.setText("请修改昵称");
        }
        String mobileNumber = currentUser.getMobilePhoneNumber();
        if (!TextUtils.isEmpty(mobileNumber)) {
            tvPhone.setText(mobileNumber);
        } else {
            tvPhone.setText("请修改手机号");
        }
    }

    @OnClick(R.id.tvLoginOut)
    public void setTvLoginOut() {
        User.logOut(this);
        this.finish();
        startActivity(new Intent(this, HomeDrawerActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK)
        );
    }

    @OnClick(R.id.tvBorrowRecord)
    public void borrowRecord() {
        startActivity(new Intent(this, BorrowRecordActivity.class));
    }
}
