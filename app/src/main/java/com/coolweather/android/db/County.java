package com.coolweather.android.db;

import org.litepal.crud.LitePalSupport;

public class County extends LitePalSupport {
    private int id;
    private String countName;//区县名字
    private int countCode;//区县代号
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

    public int getCountCode() {
        return countCode;
    }

    public String getCountName() {
        return countName;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public void setCountCode(int countCode) {
        this.countCode = countCode;
    }

    public void setCountName(String countName) {
        this.countName = countName;
    }
}
