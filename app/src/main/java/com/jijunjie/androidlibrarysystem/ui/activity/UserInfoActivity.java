package com.jijunjie.androidlibrarysystem.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jijunjie.androidlibrarysystem.R;
import com.jijunjie.androidlibrarysystem.model.User;
import com.jijunjie.myandroidlib.utils.DrawableUtils;
import com.jijunjie.myandroidlib.utils.RegexValidateUtils;
import com.jijunjie.myandroidlib.utils.SharedPreferenceUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.listener.UpdateListener;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

public class UserInfoActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.ivUserIcon)
    ImageView ivUserIcon;
    @Bind(R.id.tvNickName)
    TextView tvNickName;
    @Bind(R.id.tvPhone)
    TextView tvPhone;
    @Bind(R.id.tvBorrowRecord)
    TextView tvBorrowRecord;
    @Bind(R.id.tvUserType)
    TextView tvUserType;
    @Bind(R.id.tvModify)
    TextView tvModify;

    private static final int NICK_NAME = 1;
    private static final int PHONE = 2;
    private static final int REQUEST_CODE_GALLERY = 5;

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
        int userType = currentUser.getUserType();
        if (userType == 1) {
            tvUserType.setText("管理员");
            tvBorrowRecord.setVisibility(View.GONE);
            tvModify.setVisibility(View.GONE);
        } else {
            tvUserType.setText("普通用户");
            tvModify.setVisibility(View.VISIBLE);
            tvBorrowRecord.setVisibility(View.VISIBLE);
        }
        String path = (String) SharedPreferenceUtils.get(this, "icon", "");
        if (!TextUtils.isEmpty(path))
            DrawableUtils.disPlayLocRoundImg(ivUserIcon, path);
    }

    @OnClick(R.id.tvLoginOut)
    public void setTvLoginOut() {
        User.logOut(this);
        SharedPreferenceUtils.clear(this);
        this.finish();
        startActivity(new Intent(this, HomeDrawerActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK)
        );
        overridePendingTransition(R.anim.bottom_slide_in_anim, R.anim.do_nothing_anim);
    }

    @OnClick(R.id.tvBorrowRecord)
    public void borrowRecord() {
        startActivity(new Intent(this, BorrowRecordActivity.class));
        overridePendingTransition(R.anim.default_push_left_in, R.anim.do_nothing_anim);
    }

    @OnClick(R.id.flUserIcon)
    public void modifyUserIcon() {
        GalleryFinal.openGallerySingle(REQUEST_CODE_GALLERY, new GalleryFinal.OnHanlderResultCallback() {
            @Override
            public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                if (null != resultList && resultList.size() > 0) {
                    String path = resultList.get(0).getPhotoPath();
                    if (!TextUtils.isEmpty(path)) {
                        DrawableUtils.disPlayLocRoundImg(ivUserIcon, path);
                        SharedPreferenceUtils.put(UserInfoActivity.this, "icon", path);
                    }
                }
            }

            @Override
            public void onHanlderFailure(int requestCode, String errorMsg) {

            }
        });
    }

    @OnClick(R.id.flNickName)
    public void modifyNickName() {
        showMyDialog(NICK_NAME);
    }

    @OnClick(R.id.flPhone)
    public void modifyPhone() {
        showMyDialog(PHONE);
    }

    @OnClick(R.id.tvModify)
    public void modifyPreferences() {
        startActivity(new Intent(this, FavorSelectActivity.class));
    }

    private EditText etInput;

    private void showMyDialog(final int type) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.edit_dialog_layout, null);
        dialog.setView(layout);
        TextView tvNotice = (TextView) layout.findViewById(R.id.tvNotice);
        etInput = (EditText) layout.findViewById(R.id.searchC);
        if (type == PHONE) {
            tvNotice.setText("请输入联系电话");
        } else {
            tvNotice.setText("请输入昵称");
        }
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String searchC = etInput.getText().toString().trim();
                updateUserInfo(searchC, type);
            }
        });

        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }

        });
        dialog.show();
    }

    private void updateUserInfo(String userInfo, int type) {
        if (TextUtils.isEmpty(userInfo)) {
            Toast.makeText(this, "请输入信息", Toast.LENGTH_SHORT).show();
            return;
        }
        if (type == NICK_NAME) {
            tvNickName.setText(userInfo);
            updateDataBase(userInfo, type);
        } else {
            if (!RegexValidateUtils.checkCellphone(userInfo)) {
                Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                return;
            }
            tvPhone.setText(userInfo);
            updateDataBase(userInfo, type);
        }

    }

    private void updateDataBase(final String userInfo, int type) {
        User newUser = new User();
        if (type == PHONE) {
            newUser.setMobilePhoneNumber(userInfo);
        } else {
            newUser.setNickName(userInfo);
        }
        User bmobUser = User.getCurrentUser(this, User.class);
        newUser.update(this, bmobUser.getObjectId(), new UpdateListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(UserInfoActivity.this, "更改成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int code, String msg) {
                Toast.makeText(UserInfoActivity.this, "更改失败" + msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        overridePendingTransition(0, R.anim.default_push_right_out);
    }
}
