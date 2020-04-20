package com.coolweather.android;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.coolweather.android.db.City;
import com.coolweather.android.db.County;
import com.coolweather.android.db.Province;
import com.coolweather.android.util.HttpUtil;
import com.coolweather.android.util.Utility;

import org.litepal.LitePal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 重要！！！！！！解析地址的时候，头名一定得是https!!没有s的自己加一个~~~~
 */
public class ChooseAreaFragment  extends Fragment {
    public static final int LEVEL_PROVINCE = 0;
    public static final int LEVEL_CITY = 1;
    public static final int LEVEL_COUNTY = 2;
    private static final String TAG = "ChooseAreaFragment";
    private ProgressDialog progressDialog;
    private TextView titleText;
    private Button backButton;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> dataList = new ArrayList<>();
    /*
    省列表
     */
    private List<Province>provinceList=null;
    /**
     * 市列表
     */
    private List<City>cityList=null;
    /**
     * 区县列表
     */
    private List<County>countyList=null;
    /**
     * 选中的省份
     */
    private Province selectedProvince;
    /**
     * 选中的城市
     */
    private City selectedCity;
    private String cityname;
    /**
     * 当前选中的级别
     */
    private int currentLevel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_area,container,false);
        titleText = (TextView)view.findViewById(R.id.title_text);
        backButton = (Button)view.findViewById(R.id.back_button);
        listView = (ListView)view.findViewById(R.id.list_view);
        //初始化适配器并设置到LIST控件上
        adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,dataList);
        listView.setAdapter(adapter);
        Log.d(TAG, "onCreateView: create");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {//选中状态
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated: ");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(currentLevel==LEVEL_PROVINCE){//选中省级
                    selectedProvince = provinceList.get(position+1);
                    Log.d(TAG, "onItemClick: 1");
                    queryCities();
                }
                else if(currentLevel == LEVEL_CITY){//选中市级
                    selectedCity = cityList.get(position+1);
                    queryCounties();
                }
                else if(currentLevel == LEVEL_COUNTY) {//选中区县级
                    String weatherId = countyList.get(position+1).getUrl();
                    Intent intent = new Intent(getActivity(),WeatherActivity.class);
                    intent.putExtra("weather_id",weatherId);
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {//后退键
            @Override
            public void onClick(View v) {
                if(currentLevel==LEVEL_COUNTY)
                    queryCities();
                else if(currentLevel == LEVEL_CITY)
                    queryProvinces();
            }
        });
            queryProvinces();//初始化省级列表
    }
    /**
     * 查询全国所有的省，优先从数据库查询，如果没有查询到再去服务器上查询
     */
    private void queryProvinces(){

        titleText.setText("中国");

        backButton.setVisibility(View.GONE);

        provinceList = LitePal.findAll(Province.class);//查找数据库


        if(provinceList.size()>0){

            dataList.clear();//清空当前LIST列表内容
            for(Province province:provinceList){
                if(province.getProvinceName()!=null){//为空值不添加
                    dataList.add(province.getProvinceName());
                }
            }

            adapter.notifyDataSetChanged();//通知刷新一下适配器里面的数据

            listView.setSelection(0);//默认选中列表第一个

            currentLevel = LEVEL_PROVINCE;
        }else{
            Log.d(TAG, "queryProvinces: 6");
            String address = "https://flash.weather.com.cn/wmaps/xml/china.xml";//////整个中国所有省的接口
            queryFromServer(address,"province");
        }
    }
    /**
     * 查询选中省内所有的市，优先从数据库查询，如果没有查询到再去服务器上查询
     */
    private void queryCities(){
        Log.d(TAG, "queryCities: 2");
        titleText.setText(selectedProvince.getProvinceName());//设置标题
        Log.d(TAG, "queryCities: 3"+selectedProvince.getProvinceName());
        backButton.setVisibility(View.VISIBLE);
        cityList = LitePal.where("provinceId=?",
                String.valueOf(selectedProvince.getId())).find(City.class);//查找到省ID下级城市
        Log.d(TAG, "name"+selectedProvince.getProvinceCode());
        if(cityList.size()>0){
            Log.d(TAG, "queryCities: 4");
            dataList.clear();//清空当前LIST列表内容
            for(City city:cityList){
                if(city.getCityName()!=null){//为空不添加
                    dataList.add(city.getCityName());
                    Log.d(TAG, "queryCities: "+city.getCityName());
                }
            }
            adapter.notifyDataSetChanged();//通知刷新适配器数据
            listView.setSelection(0);//默认选中列表第一个
            currentLevel = LEVEL_CITY;
        }else{
            Log.d(TAG, "queryCities: 5"+selectedProvince.getPyName());
            String address = "https://flash.weather.com.cn/wmaps/xml/"+
                    selectedProvince.getPyName()+".xml";//填写省的名字
            Log.d(TAG, "queryCities: 6");
            queryFromServer(address,"city");
        }
    }
    /**
     * 查询选中市内所有的县，优先从数据库查询，如果没有查询到再去服务器上查询
     */
    private void queryCounties(){
        titleText.setText(selectedCity.getCityName());
        Log.d(TAG, "queryCounties: "+selectedCity.getCityName());//设置标题名
        backButton.setVisibility(View.VISIBLE);
        countyList = LitePal.where("cityId=?"
                ,String.valueOf(selectedCity.getId())).find(County.class);//查找市ID下级区县
        if(countyList.size()>0){
            Log.d(TAG, "queryCounties: 有数据");
            dataList.clear();//清空当前LIST列表内容
            for(County county:countyList){
                if(county.getCountName()!=null)//为空不添加
                {
                    dataList.add(county.getCountName());
                    Log.d(TAG, "URL: "+county.getUrl());
                }
            }
            adapter.notifyDataSetChanged();//通知刷新适配器数据
            listView.setSelection(0);//默认选中列表第一个
            currentLevel=LEVEL_COUNTY;
        }else{
            Log.d(TAG, "queryCounties: 申请");
            String address = "https://flash.weather.com.cn/wmaps/xml/"+selectedCity.getPyName()+".xml";//填写市的名字
            queryFromServer(address,"county");
        }
    }
    /**
     * 根据传入的地址和类型从服务器上查询省市县数据
     */
    private void queryFromServer(String address,final String type){
        showProgressDialog();
        HttpUtil.sendOkHttpRequest(address, new Callback() {//发出网络请求
            @Override
            public void onResponse(Call call, Response response) throws IOException {//成功返回值
                Log.d(TAG, "onResponse: 成功");
                String responseText = response.body().string();//转换成字符类型
                boolean result = false;
                //匹配以下三种方法，解析省市县
                if("province".equals(type)){
                    result = Utility.handleProvinceResponse(responseText);
                }else if("city".equals(type)){
                    result=Utility.handCityResponse(responseText,selectedProvince.getId());
                    Log.d(TAG, "AAonResponse: "+selectedProvince.getProvinceCode());
                }else if("county".equals(type)){
                    result = Utility.handCountyResponse(responseText,selectedCity.getId());
                }
                if(result){
                    getActivity().runOnUiThread(new Runnable() {//返回主线程,根据匹配重新刷新列表
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if("province".equals(type)){
                                queryProvinces();
                            }else if("city".equals(type)){
                                queryCities();
                            }else if("county".equals(type)){
                                queryCounties();
                            }
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call call, IOException e) {//请求失败
                Log.d(TAG, "onFailure: 失败");
                //通过runOnUiThread()方法回到主线程处理逻辑
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(getContext(),"加载失败",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });
    }

    /**
     * 显示进度对话框
     */
    private void showProgressDialog(){//加载框
        if(progressDialog==null){
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("正在加载……");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }
    /**
     * 关闭进度对话框
     */
    private void closeProgressDialog(){
        if(progressDialog!=null){
            progressDialog.dismiss();
        }
    }
}
