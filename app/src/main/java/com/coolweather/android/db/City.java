package com.coolweather.android.db;

import org.litepal.crud.LitePalSupport;

public class City extends LitePalSupport {
    private int id;
    private String cityName;//市名字
    private int cityCode;//市代号
    private int provinceId;//所属省的ID
    private String pyName;//市英文名
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPyName(String pyName) {
        this.pyName = pyName;
    }

    public String getPyName() {
        return pyName;
    }

    public int getCityCode() {
        return cityCode;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }
}
