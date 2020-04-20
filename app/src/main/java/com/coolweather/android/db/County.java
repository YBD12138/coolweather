package com.coolweather.android.db;

import org.litepal.crud.LitePalSupport;

public class County extends LitePalSupport {
    private int id;
    private String countName;//区县名字
    private int countId;//区县代号
    private String url;//天气代码
    private int cityId;//所属市ID

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getCityId() {
        return cityId;
    }

    public int getCountId() {
        return countId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCountName() {
        return countName;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public void setCountId(int countId) {
        this.countId = countId;
    }

    public void setCountName(String countName) {
        this.countName = countName;
    }
}
