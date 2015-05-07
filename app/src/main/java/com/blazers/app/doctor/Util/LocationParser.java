package com.blazers.app.doctor.Util;

import android.content.Context;
import android.util.Log;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by liang on 2015/5/7.
 */
public class LocationParser {

    /* Instance */
    private static LocationParser mInstance;

    private JSONArray jsonArray;
    private ArrayList<String> provinces;

    private LocationParser(Context ctx) {
        /* 读取JSON 构造相应的数据对象 */
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

    public ArrayList<String> getCitiesByProvince(String province) {
        return new ArrayList<>();
    }

    public ArrayList<String> getDistrictsByCity(String city) {
        return new ArrayList<>();
    }
}
