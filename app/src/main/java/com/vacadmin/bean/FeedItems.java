package com.vacadmin.bean;

import java.io.Serializable;

/**
 * Created by hemendrag on 12/9/2016.
 */

public class FeedItems implements Serializable{

    private String title;
    private String desc;
    private int imgResourceId;

    public FeedItems(String title, String desc, int imgResourceId) {
        this.title = title;
        this.desc = desc;
        this.imgResourceId = imgResourceId;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public int getImgResourceId() {
        return imgResourceId;
    }
}

