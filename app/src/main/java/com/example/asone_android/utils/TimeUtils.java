package com.example.asone_android.utils;

import android.text.TextUtils;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by 唐浩 on 2018/3/29.
 */

public class TimeUtils {
    private static final String TAG = "TimeUtils";

    //
    public static long getTime(){
        Calendar calendar = Calendar.getInstance();
        long time = calendar.getTime().getTime();
        return time;
    }

    public static String getTime(Date date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        return format.format(date);
    }

    public static String getTime(long timeStamp){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sd = sdf.format(new Date(Long.parseLong(String.valueOf(timeStamp))));
        return sd;
    }

    public static String getThreeTime(long timeStamp){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String sd = sdf.format(new Date(Long.parseLong(String.valueOf(timeStamp))));
        return sd;
    }

    public static String getFIFTime(long timeStamp){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String sd = sdf.format(new Date(Long.parseLong(String.valueOf(timeStamp))));
        return sd;
    }

    public static String getFIFTime(String timeStamp){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String sd = sdf.format(new Date(Long.parseLong(timeStamp)));
        return sd;
    }

    public static String getTime(String timeStamp){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String sd = sdf.format(new Date(Long.parseLong(timeStamp)));
        return sd;
    }

    public static String getyyyymmddHHmmTime(String timeStamp){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String sd = sdf.format(new Date(Long.parseLong(timeStamp)));
        return sd;
    }

    public static String getAllTime(String timeStamp){
        if (TextUtils.isEmpty(timeStamp)){
            return "";
        }
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sd = sdf.format(new Date(Long.parseLong(timeStamp)));
        return sd;
    }

    public static long getTimeNum(String mStrBegin, String mStrEnd){
            Long beginT = Long.parseLong(mStrBegin);
            Long endT = Long.parseLong(mStrEnd);
        return  endT - beginT;
    }


    public static String getUpTime(long timeStamp){
        SimpleDateFormat sdf=new SimpleDateFormat("MM-dd");
        String sd = sdf.format(new Date(Long.parseLong(String.valueOf(timeStamp))));
        return sd;
    }

    public static String getMunuteTime(String timeStamp){
        SimpleDateFormat sdf=new SimpleDateFormat("mm:ss");
        String sd = sdf.format(new Date(Long.parseLong(timeStamp)));
        return sd;
    }

    public static String getDownTime(long timeStamp){
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
        String sd = sdf.format(new Date(Long.parseLong(String.valueOf(timeStamp))));
        return sd;
    }

    public static String getDownTime(String timeStamp){
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
        String sd = sdf.format(new Date(Long.parseLong(timeStamp)));
        return sd;
    }

    public static String getpublicTime(long time , String yyyyMMddHHmmss){
        SimpleDateFormat sdf=new SimpleDateFormat(yyyyMMddHHmmss);
        String sd = sdf.format(new Date(Long.parseLong(String.valueOf(time))));
        return sd;
    }

    public static List<Integer> getTimeNum(long time){
        int hour = (int) ((time/1000)/60/60);
        int miniue = (int) ((time/1000)/60%60);
        int second = (int) ((time/1000)%60);
        List<Integer> list = new ArrayList<>();
        list.add(hour);
        list.add(miniue);
        list.add(second );
        return list;
    }

    public static Calendar getCalendar(long time){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return calendar;
    }

    /** 增加一个月 */
    public static Date getAddMouth(long time){
        Calendar calendar = getCalendar(time);
        calendar.add(Calendar.MONTH, 1);
        Date date = calendar.getTime();
        return date;
    }

    /** 增加一天 */
    public static Date getAddDay(long time){
        Calendar calendar = getCalendar(time);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date date = calendar.getTime();
        return date;
    }

    /** 豪秒转时间 */
    public static String getAATTTime(long longTime){
        StringBuilder strTime = new StringBuilder();
        int hour = (int) (longTime/1000/60/60);
        if (hour > 0){
            strTime.append(strTime).append(":");
        }
        int minue = (int) (longTime/1000/60%60);
        strTime.append(minue).append(":");
        int m = (int) (longTime/1000%60);
        strTime.append(m);
        return strTime.toString();
    }
}
