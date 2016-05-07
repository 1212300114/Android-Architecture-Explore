package com.jijunjie.androidlibrarysystem.model;

import cn.bmob.v3.BmobUser;

/**
 * Created by jijunjie on 16/1/14.
 */
public class User extends BmobUser {

    private Integer sid;
    private Integer userId;
    private Integer userType;
    private Boolean userSex;
    private String nickName;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Boolean getUserSex() {
        return userSex;
    }

    public void setUserSex(Boolean userSex) {
        this.userSex = userSex;
    }
}
