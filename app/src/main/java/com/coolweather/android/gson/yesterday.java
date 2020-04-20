package com.coolweather.android.gson;

import com.google.gson.annotations.SerializedName;
/**
 * 日期，高温，低温，长日期，星期，日出，日落，风向，风力，天气类型，今日提示
 */
public class yesterday {
    @SerializedName("date")
    public String date;
    @SerializedName("high")
    public String Htemp;
    @SerializedName("low")
    public String Ltemp;
    @SerializedName("ymd")
    public String Ldate;
    @SerializedName("week")
    public String week;
    @SerializedName("sunrise")
    public String SunInput;
    @SerializedName("sunset")
    public String SunOutput;
    @SerializedName("fx")
    public String Fx;
    @SerializedName("fl")
    public String Fl;
    @SerializedName("type")
    public String TyPe;
    @SerializedName("notice")
    public String note;

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public String getFl() {
        return Fl;
    }

    public String getFx() {
        return Fx;
    }

    public String getHtemp() {
        return Htemp;
    }

    public String getLdate() {
        return Ldate;
    }

    public String getLtemp() {
        return Ltemp;
    }

    public String getNote() {
        return note;
    }

    public String getSunInput() {
        return SunInput;
    }

    public String getSunOutput() {
        return SunOutput;
    }

    public String getTyPe() {
        return TyPe;
    }

    public String getWeek() {
        return week;
    }

    public void setFl(String fl) {
        Fl = fl;
    }

    public void setFx(String fx) {
        Fx = fx;
    }

    public void setHtemp(String htemp) {
        Htemp = htemp;
    }

    public void setLdate(String ldate) {
        Ldate = ldate;
    }

    public void setLtemp(String ltemp) {
        Ltemp = ltemp;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setSunInput(String sunInput) {
        SunInput = sunInput;
    }

    public void setSunOutput(String sunOutput) {
        SunOutput = sunOutput;
    }

    public void setTyPe(String tyPe) {
        TyPe = tyPe;
    }

    public void setWeek(String week) {
        this.week = week;
    }
}
