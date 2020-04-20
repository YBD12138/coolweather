package com.coolweather.android.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 湿度，PM2.5，PM10，污染指数，温度，今日提示，未来天气数据类，昨天天气类
 */
public class data {
    @SerializedName("shidu")
    public String shidu;
    @SerializedName("pm25")
    public String PM25;
    @SerializedName("pm10")
    public String PM10;
    @SerializedName("quality")
    public String wuran;
    @SerializedName("wendu")
    public String wendu;
    @SerializedName("ganmao")
    public String Tishi;
    @SerializedName("forecast")
    public List<Forrecast> forrecastList;
    @SerializedName("yesterday")
    public yesterday yesterday;

    public List<Forrecast> getForrecastList() {
        return forrecastList;
    }

    public String getPM10() {
        return PM10;
    }

    public String getPM25() {
        return PM25;
    }

    public String getShidu() {
        return shidu;
    }

    public String getTishi() {
        return Tishi;
    }

    public String getWendu() {
        return wendu;
    }

    public String getWuran() {
        return wuran;
    }

    public void setForrecastList(List<Forrecast> forrecastList) {
        this.forrecastList = forrecastList;
    }

    public void setPM10(String PM10) {
        this.PM10 = PM10;
    }

    public void setPM25(String PM25) {
        this.PM25 = PM25;
    }

    public void setShidu(String shidu) {
        this.shidu = shidu;
    }

    public void setTishi(String tishi) {
        Tishi = tishi;
    }

    public void setWendu(String wendu) {
        this.wendu = wendu;
    }

    public void setWuran(String wuran) {
        this.wuran = wuran;
    }

    public void setYesterday(com.coolweather.android.gson.yesterday yesterday) {
        this.yesterday = yesterday;
    }

    public com.coolweather.android.gson.yesterday getYesterday() {
        return yesterday;
    }
}
