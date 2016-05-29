package com.jijunjie.androidlibrarysystem.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jijunjie.androidlibrarysystem.R;
import com.jijunjie.androidlibrarysystem.model.User;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.etUserName)
    EditText etUserName;
    @Bind(R.id.etPassword)
    EditText etPassword;
    @Bind(R.id.etEnsurePassword)
    EditText etEnsurePassword;
    @Bind(R.id.etCDKey)
    EditText etCDKey;
    @Bind(R.id.boxManager)
    CheckBox boxManager;
    @Bind(R.id.boxReader)
    CheckBox boxReader;
    @Bind(R.id.tvRegister)
    TextView tvRegister;

    private boolean isReader = true;
    private static final String[] CDKEYS = {"abc", "edf"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initToolbar();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterActivity.this.onBackPressed();
            }
        });
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_white_24dp);
        getSupportActionBar().setTitle("用户登录");
    }

    @OnClick({R.id.boxReader, R.id.boxManager})
    public void toggle() {
        isReader = !isReader;
        boxManager.setChecked(!isReader);
        boxReader.setChecked(isReader);
    }

    @OnClick(R.id.tvRegister)
    public void register() {
        String userName = etUserName.getText().toString().trim();
        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(getApplicationContext(), "请输入用户名", Toast.LENGTH_SHORT).show();
            return;
        }
        String password = etPassword.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }
        String enSurePassword = etPassword.getText().toString().trim();
        if (TextUtils.isEmpty(enSurePassword)) {
            Toast.makeText(getApplicationContext(), "请再次输入密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!enSurePassword.equals(password)) {
            Toast.makeText(getApplicationContext(), "两次输入的密码不同", Toast.LENGTH_SHORT).show();
            return;
        }
        String cdKey = etCDKey.getText().toString().trim();
        if (TextUtils.isEmpty(cdKey) && !isReader) {
            Toast.makeText(getApplicationContext(), "管理员需要输入cdKey", Toast.LENGTH_SHORT).show();
            return;
        }
        boolean flag = false;
        for (String CDKEY : CDKEYS) {
            if (cdKey.equals(CDKEY)) {
                flag = true;
                break;
            } else {
                flag = true;
            }
        }
        if (!isReader && flag) {
            Toast.makeText(getApplicationContext(), "cd key 不正确", Toast.LENGTH_SHORT).show();
            return;
        }
        doRegister(userName, password, cdKey);

    }

    private void doRegister(String userName, String password, String cdKey) {
        User user = new User();
        user.setUsername(userName);
        user.setPassword(password);
        user.setUserSex(true);
        user.setUserType(isReader ? 0 : 1);
        user.signUp(this, new SaveListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                RegisterActivity.this.onBackPressed();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(RegisterActivity.this, "注册失败" + s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, R.anim.default_push_right_out);
    }
}
