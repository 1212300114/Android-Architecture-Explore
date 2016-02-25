package com.jijunjie.androidlibrarysystem.model;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by jijunjie on 16/1/14.
 */
public class CustomTable extends BmobObject {
    private BmobFile image;

    public BmobFile getImage() {
        return image;
    }

    public void setImage(BmobFile image) {
        this.image = image;
    }
}
