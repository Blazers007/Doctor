package com.blazers.app.doctor.Util;

import android.content.Context;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by liang on 2015/5/7.
 */
public class LocationParser {

    /* Instance */
    private static LocationParser mInstance;

    private JSONArray jsonArray;

    private ArrayList<String> provinces;

    private ArrayList<String> cities;
    private JSONArray cityJSONArray;

    private ArrayList<String> districts;

    private LocationParser(Context ctx) {

        try {
            InputStream is = ctx.getResources().getAssets().open("location.json");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            jsonArray = JSON.parseArray(stringBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static LocationParser getInstance(Context context) {
        if(mInstance == null) {
            mInstance = new LocationParser(context);
        }
        return mInstance;
    }

    public ArrayList<String> getProvinces() {
        if(provinces == null) {
            provinces = new ArrayList<>();
            for(int i = 0 ; i < jsonArray.size(); i ++) {
                provinces.add(jsonArray.getJSONObject(i).getString("label"));
            }
        }
        return provinces;
    }

    public ArrayList<String> getCitiesByProvince(String provinceName) {
        cities = new ArrayList<>();
        if(provinceName.equals("请选择"))
            return cities;
        for(int onePro = 0 ; onePro < jsonArray.size() ; onePro ++) {
            JSONObject province = jsonArray.getJSONObject(onePro);
            if(provinceName.equals(province.getString("label"))) {
                /* */
                JSONArray cityArray = cityJSONArray =  province.getJSONArray("children");
                /* */
                for(int oneCity = 0 ; oneCity < cityArray.size() ; oneCity ++) {
                    JSONObject city = cityArray.getJSONObject(oneCity);
                    cities.add(city.getString("label"));
                }
                return cities;
            }
        }
        return cities;
    }

    public ArrayList<String> getDistrictsByCity(String cityName) {
        districts = new ArrayList<>();
        if(cityName.equals("请选择") || cityJSONArray == null)
            return districts;
        for(int cityIndex = 0 ; cityIndex < cityJSONArray.size() ; cityIndex ++) {
            JSONObject city = cityJSONArray.getJSONObject(cityIndex);
            if(cityName.equals(city.getString("label"))) {
                /**/
                if(city.containsKey("children")) {
                    JSONArray districtArray = city.getJSONArray("children");
                    for(int disIndex = 0 ; disIndex < districtArray.size() ; disIndex ++) {
                        JSONObject district = districtArray.getJSONObject(disIndex);
                        districts.add(district.getString("label"));
                    }
                }
                return districts;
            }
        }
        return districts;
    }
}
