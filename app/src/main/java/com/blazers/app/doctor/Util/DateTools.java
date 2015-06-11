package com.blazers.app.doctor.Util;

import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by liang on 2015/6/11.
 */
public class DateTools {
    public static int DAYS;
    public static int DAYS_AND_TIMES;//新增一个参数 可以按照类型进行默认的设置
    //用Calendar 如何规范格式？
    public static void setDefaultDate(EditText et){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(date);
        et.setText(dateStr);
    }

    public static String getNowDate(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public static String getNowTime() {
        Calendar calendar = Calendar.getInstance();
        return String.format("%02d:%02d",calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
    }

    public static String getNowDate(String format){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static String getDate(int offset){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,offset);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(calendar.getTime());
    }

    public static String getDate(long time){
        Date date = new Date(time);
        //
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(date);
        Date now = new Date();
        Calendar targetCalendar = Calendar.getInstance();
        targetCalendar.setTime(now);
        targetCalendar.set(Calendar.HOUR_OF_DAY, 0);
        targetCalendar.set(Calendar.MINUTE, 0);
        if (dateCalendar.after(targetCalendar)) {
            SimpleDateFormat sfd = new SimpleDateFormat("今日  HH:mm:ss");
            return sfd.format(date);
        } else {
            targetCalendar.add(Calendar.DATE, -1);
            if (dateCalendar.after(targetCalendar)) {
                SimpleDateFormat sfd = new SimpleDateFormat("昨日  HH:mm:ss");
                return sfd.format(date);
            }
        }
        //
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return sdf.format(date);
    }

    public static String getUpOrDown(){
        Calendar dateCalendar = Calendar.getInstance();
        if(dateCalendar.get(Calendar.HOUR_OF_DAY) > 12)
            return "下午";
        else
            return "上午";
    }
}
