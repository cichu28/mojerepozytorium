package com.example.marcin.clientservertutorial.model;

/**
 * Created by Marcin on 2016-12-06.
 */
public class ItemSlideMenu {

    private int imgId;
    private String title;


    public ItemSlideMenu(int imgId, String title) {
        this.imgId = imgId;
        this.title = title;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
