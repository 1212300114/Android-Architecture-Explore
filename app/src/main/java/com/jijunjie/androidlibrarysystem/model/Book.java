package com.jijunjie.androidlibrarysystem.model;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by jijunjie on 16/1/14.
 */
public class Book extends BmobObject {
    private String bookName;
    private String className;
    private Integer bookID;
    private String bookAuthor;
    private String booCountry;
    private Integer bookCount;
    private Integer bookLeft;
    private BmobFile bookImage;
    private String bookPress;

    public String getBookPress() {
        return bookPress;
    }

    public void setBookPress(String bookPress) {
        this.bookPress = bookPress;
    }

    public Integer getBookID() {
        return bookID;
    }

    public void setBookID(Integer bookID) {
        this.bookID = bookID;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBooCountry() {
        return booCountry;
    }

    public void setBooCountry(String booCountry) {
        this.booCountry = booCountry;
    }

    public Integer getBookCount() {
        return bookCount;
    }

    public void setBookCount(Integer bookCount) {
        this.bookCount = bookCount;
    }

    public Integer getBookLeft() {
        return bookLeft;
    }

    public void setBookLeft(Integer bookLeft) {
        this.bookLeft = bookLeft;
    }

    public String getClassName() {

        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getBookName() {

        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void setBookImage(BmobFile bookImage) {
        this.bookImage = bookImage;
    }

    public BmobFile getBookImage() {
        return bookImage;
    }
}
