package com.coolweather.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.coolweather.android.gson.Forrecast;
import com.coolweather.android.gson.Weather;
import com.coolweather.android.util.HttpUtil;
import com.coolweather.android.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {
    private ScrollView weatherLayou;
    private TextView titleCity,titleUpdateTime,degreeText,weatherInfoText,degreeTextshidu;
    private LinearLayout forecastLayout;
    private TextView pm25Text,pm10Text,note,ganmao,wuranzhishu;
    private static final String TAG = "WeatherActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        //初始化各控件
        weatherLayou = (ScrollView) findViewById(R.id.weather_layout);
        titleCity = (TextView)findViewById(R.id.title_city);
        titleUpdateTime = (TextView)findViewById(R.id.title_update_time);
        degreeText = (TextView)findViewById(R.id.degree_text);
        weatherInfoText=(TextView)findViewById(R.id.weather_info_text);
        degreeTextshidu = (TextView)findViewById(R.id.degreeshiwu_text);
        forecastLayout = (LinearLayout) findViewById(R.id.forecast_layout);
        pm25Text = (TextView)findViewById(R.id.pm25_text);
        pm10Text = (TextView)findViewById(R.id.pm10_text);
        wuranzhishu = (TextView)findViewById(R.id.wuran_text);
        note = (TextView)findViewById(R.id.note_text);
        ganmao = (TextView)findViewById(R.id.ganmao_text);
        //以上是主界面初始化，没有辅界面
        //获取本地缓存实例!!
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //读取本地缓存数据
        String weatherString = prefs.getString("weather",null);
        if(weatherString!=null){
            //有缓存时直接解析天气数据
            Log.d(TAG, "onCreate: 本地有数据");
            Weather weather = Utility.handleWeatherResponse(weatherString);
            showWeatherInfo(weather);
        }else{
            //无缓存时去服务器查询天气
            Log.d(TAG, "onCreate: 本地没有数据");
            String weatherId = getIntent().getStringExtra("weather_id");
            weatherLayou.setVisibility(View.INVISIBLE);
            requestWeather(weatherId);
        }
    }
    /**
     * 根据天气ID请求城市天气信息
     */
    public void requestWeather(final String wetherId){
        String weatherUrl = "http://t.weather.sojson.com/api/weather/city/"+wetherId;//获取天气的链接
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {//向服务器发送请求
            @Override
            public void onFailure(Call call, IOException e) {//获取失败
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this,
                                "获取天气信息失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {//获取成功
                final String responseText = response.body().string();//返回的大数据
                final Weather weather = Utility.handleWeatherResponse(responseText);//进行调用解析
                runOnUiThread(new Runnable() {//回到主线程
                    @Override
                    public void run() {
                        if(weather!=null&&"200".equals(weather.status)){
                            Log.d(TAG, "run: success");
                            SharedPreferences.Editor editor =//保存数据缓存到本地
                                    PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                            editor.putString("weather",responseText);//压入数据
                            editor.apply();//提交数据
                            if(weather!=null)
                            showWeatherInfo(weather);
                        }else{
                            Toast.makeText(WeatherActivity.this,
                                    "获取天气信息失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
    /**
     * 处理并展示Weather实体类中的数据
     */
    private void showWeatherInfo(Weather weather){
        if(weather!=null) {
            weatherLayou.setVisibility(View.VISIBLE);
            Log.d(TAG, "showWeatherInfo: 开始设置中上");
            String cityName = weather.getCityInfo().getCityName();
            String upTime = weather.getCityInfo().getUpTime();
            String degree = weather.getData().getWendu();
            String weatherInfo = weather.getData().getForrecastList().get(0).getTyPe();
            String degreeshidu = weather.getData().getShidu();
            titleCity.setText(cityName);
            titleUpdateTime.setText(upTime);
            degreeText.setText(degree);
            degreeTextshidu.setText(degreeshidu);
            weatherInfoText.setText(weatherInfo);
            Log.d(TAG, "showWeatherInfo: 开始中下");
            for (Forrecast forrecast : weather.getData().forrecastList) {
                //以下有个粗心大意的坑，就是这里没有用view.findViewById，而是直接findViewById,导致出现找不到准确的ID
                    View view = LayoutInflater.from(this).inflate(R.layout.forecast_item, forecastLayout, false);
                    TextView dateText = (TextView) view.findViewById(R.id.date_text);
                    TextView infoText = (TextView) view.findViewById(R.id.info_text);
                    TextView maxText = (TextView) view.findViewById(R.id.max_text);
                    TextView minText = (TextView) view.findViewById(R.id.min_text);
                if (forrecast.getLdate() != null && forrecast.getTyPe() != null
                        && forrecast.getLtemp() != null && forrecast.getHtemp() != null) {
                    Log.d(TAG, "showWeatherInfo: 中下开始");
                    Log.d(TAG, "showWeatherInfo: "+forrecast.getLdate()+forrecast.getTyPe()+
                            forrecast.getLtemp()+forrecast.getHtemp());
                    dateText.setText(forrecast.getLdate());
                    Log.d(TAG, "showWeatherInfo: "+forrecast.getTyPe());
                    Log.d(TAG, "showWeatherInfo: 日期?");
                    infoText.setText(forrecast.getTyPe());
                    Log.d(TAG, "showWeatherInfo: "+forrecast.getHtemp());
                    Log.d(TAG, "showWeatherInfo: 类型?");
                    maxText.setText(forrecast.getHtemp());
                    Log.d(TAG, "showWeatherInfo: "+forrecast.getLtemp());
                    Log.d(TAG, "showWeatherInfo: 最高温?");
                    minText.setText(forrecast.getLtemp());
                    Log.d(TAG, "showWeatherInfo: 设置完毕");}
                    forecastLayout.addView(view);

            }
            if (weather.getData().PM25 != null) {
                Log.d(TAG, "showWeatherInfo: 没进来？");
                pm10Text.setText(weather.getData().getPM10());
                pm25Text.setText(weather.getData().getPM25());
                wuranzhishu.setText(weather.getData().getWuran());
            }
            Log.d(TAG, "showWeatherInfo: 这到没到");
            note.setText(weather.getData().getForrecastList().get(0).getNote());
            ganmao.setText(weather.getData().getTishi());
        }
    }
}
