package com.coolweather.android.util;

import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.Request;
/*
 ***发送HTTP请求调用，传入请求地址，并注册一个回调来处理服务器响应
 */

public class HttpUtil {
    private static final String TAG = "HttpUtil";
    public static void sendOkHttpRequest(String address, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);//把请求放入消息队列
    }
}
