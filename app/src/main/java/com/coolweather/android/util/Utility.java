package com.coolweather.android.util;

import android.text.TextUtils;
import android.util.Log;

import com.coolweather.android.db.City;
import com.coolweather.android.db.County;
import com.coolweather.android.db.Province;
import com.coolweather.android.gson.Weather;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.StringReader;

import javax.xml.parsers.SAXParserFactory;

public class Utility {
    private static final String TAG = "Utility";
    /*
    解析和处理服务器返回的省级数据
     */
    public static boolean handleProvinceResponse(String response){//下面的方法变更成xml解析
        Log.d(TAG, "handleProvinceResponse: 12345");
        if(!TextUtils.isEmpty(response)){
            Log.d(TAG, "handleProvinceResponse: 00");
            try{
                SAXParserFactory factory = SAXParserFactory.newInstance();
                XMLReader xmlReader = factory.newSAXParser().getXMLReader();
                ProvinceHandler handler = new ProvinceHandler();
                xmlReader.setContentHandler(handler);
                Log.d(TAG, "handleProvinceResponse: 12");
                xmlReader.parse(new InputSource(new StringReader(response)));
                Log.d(TAG, "handleProvinceResponse: 34");
                return true;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }

    /*
    解析和处理服务器返回的市级数据
     */
    public static boolean handCityResponse(String response,int ProvinceId){//下面的方法变更成xml解析
        if(!TextUtils.isEmpty(response)){
            try{
                SAXParserFactory factory = SAXParserFactory.newInstance();
                XMLReader xmlReader = factory.newSAXParser().getXMLReader();
                CityHandler handler = new CityHandler(ProvinceId);
                xmlReader.setContentHandler(handler);
                xmlReader.parse(new InputSource(new StringReader(response)));
                return true;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }

    /*
    解析和处理服务器返回的区县级数据
     */
    public static boolean handCountyResponse(String response,int cityId){//下面的方法变更成xml解析
        if(!TextUtils.isEmpty(response)){
            try{
                SAXParserFactory factory = SAXParserFactory.newInstance();
                XMLReader xmlReader = factory.newSAXParser().getXMLReader();
                CountyHandler handler = new CountyHandler(cityId);
                xmlReader.setContentHandler(handler);
                xmlReader.parse(new InputSource(new StringReader(response)));
                return true;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 将返回的JSON数据解析成Weather实体类
     */
    public static Weather handleWeatherResponse(String response){//利用GSON解析方式,解析JSON格式存到Weather类
        try{
            Gson gson = new Gson();
            return gson.fromJson(response,Weather.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
