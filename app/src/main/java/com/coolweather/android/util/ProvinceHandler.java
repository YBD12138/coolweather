package com.coolweather.android.util;

import android.util.Log;

import com.coolweather.android.db.Province;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ProvinceHandler extends DefaultHandler {
    Province province=null;
    private String pyName;
    private String quName;
    private int JK;
    private static final String TAG = "ProvinceHandler";

    @Override
    public void startDocument() throws SAXException {
        JK=0;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        province = new Province();
        Log.d(TAG, "localName: "+localName+"\t\tqName:"+qName);
        for ( int i = 0; i < attributes.getLength(); i++ ) {
            switch (attributes.getLocalName(i)) {
                case "quName":
                    quName = attributes.getValue(i);
                    Log.d(TAG, "quName: " + attributes.getValue(i));
                    break;
                case "pyName":
                    pyName = (attributes.getValue(i));
                    Log.d(TAG, "pyName: " + attributes.getValue(i));
                    break;
            }

        }
        province.setProvinceName(quName);
        province.setPyName(pyName);
        province.setProvinceCode(JK++);
        province.save();
    }
}
