package com.coolweather.android.gson;

import com.google.gson.annotations.SerializedName;

/**
 * 城市信息，包括地点名字，地点天气码，所属地，更新时间
 */
public class cityInfo {
    @SerializedName("city")
    public String cityName;
    @SerializedName("citykey")
    public String cityCode;
    @SerializedName("parent")
    public String parent;
    @SerializedName("updateTime")
    public String upTime;

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getParent() {
        return parent;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public String getUpTime() {
        return upTime;
    }

    public void setUpTime(String upTime) {
        this.upTime = upTime;
    }
}
