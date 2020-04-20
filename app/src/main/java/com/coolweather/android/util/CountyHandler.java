package com.coolweather.android.util;

import android.util.Log;

import com.coolweather.android.db.City;
import com.coolweather.android.db.County;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * 解析XML之后存到数据库中
 */
public class CountyHandler extends DefaultHandler {
    County county=null;
    private String url;
    private String cityName;
    private int id;
    private int JK;
    private static final String TAG = "CityHandler";

    public CountyHandler(int id){
        this.id = id;
    }
    @Override
    public void startDocument() throws SAXException {

    }
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        county = new County();
        JK=0;
        //Log.d(TAG, "localName: "+localName+"\t\tqName:"+qName);
        for ( int i = 0; i < attributes.getLength(); i++ ) {
            switch (attributes.getLocalName(i)){
                case "cityname":
                    cityName=attributes.getValue(i);
                    Log.d(TAG, "cityName: "+attributes.getValue(i));
                    break;
                case "url":
                    url=attributes.getValue(i);
                    Log.d(TAG, "url: "+attributes.getValue(i));
                    break;
            }
        }
        county.setCountName(cityName);
        county.setCountId(JK++);
        county.setUrl(url);
        county.setCityId(id);
        county.save();
    }
}
