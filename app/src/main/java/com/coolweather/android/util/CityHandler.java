package com.coolweather.android.util;

import android.util.Log;

import com.coolweather.android.db.City;
import com.coolweather.android.db.Province;

import org.litepal.LitePal;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class CityHandler extends DefaultHandler {
    City city=null;
    private String pyName;
    private String cityName;
    private int id;
    private int JK;
    private static final String TAG = "CityHandler";

    public CityHandler(int id){
        this.id = id;
    }
    @Override
    public void startDocument() throws SAXException {

    }
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        city = new City();
        JK=0;
        Log.d(TAG, "localName: "+localName+"\t\tqName:"+qName);
        for ( int i = 0; i < attributes.getLength(); i++ ) {
            switch (attributes.getLocalName(i)){
                case "cityname":
                    cityName=attributes.getValue(i);
                    Log.d(TAG, "cityName: "+attributes.getValue(i));
                    break;
                case "pyName":
                    pyName=attributes.getValue(i);
                    Log.d(TAG, "pyName: "+attributes.getValue(i));
                    break;
            }
        }
        city.setCityName(cityName);
        city.setCityCode(JK++);
        city.setPyName(pyName);
        city.setProvinceId(id);
        city.save();
    }
}
