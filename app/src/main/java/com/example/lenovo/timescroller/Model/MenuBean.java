package com.example.lenovo.timescroller.Model;

import java.io.Serializable;

/**
 * Created by kevin.tian on 2016/8/11.
 */
public class MenuBean implements Serializable{
    String iconUrl;
    String title;

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
