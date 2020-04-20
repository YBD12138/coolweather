package com.coolweather.android.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 返回状态，更新时间，城市信息类，天气数据类
 */
public class Weather {
    @SerializedName("status")
    public String status;
    @SerializedName("time")
    public String time;
    @SerializedName("cityInfo")
    public cityInfo cityInfo;
    @SerializedName("data")
    public data data;

    public void setCityInfo(com.coolweather.android.gson.cityInfo cityInfo) {
        this.cityInfo = cityInfo;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public String getStatus() {
        return status;
    }

    public com.coolweather.android.gson.cityInfo getCityInfo() {
        return cityInfo;
    }

    public com.coolweather.android.gson.data getData() {
        return data;
    }

    public void setData(com.coolweather.android.gson.data data) {
        this.data = data;
    }
}
