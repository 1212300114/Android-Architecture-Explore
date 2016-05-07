package com.jijunjie.androidlibrarysystem.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by jijunjie on 16/5/6.
 */
public class BorrowRecord extends BmobObject {
    private Integer id;
    private Integer bookID;
    private Integer userId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBookID() {
        return bookID;
    }

    public void setBookID(Integer bookID) {
        this.bookID = bookID;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
