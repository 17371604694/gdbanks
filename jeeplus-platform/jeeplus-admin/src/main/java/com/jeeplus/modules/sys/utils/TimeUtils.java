package com.jeeplus.modules.sys.utils;

import com.jeeplus.modules.sys.entity.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 时间工具类
 */
public class TimeUtils {

    private static List<String> timeList;
    /**
     *获取查询周期的表头的方法，如果不传去参数time,默认按当前日期往后一周
     * @param time
     * @return
     */
    public static void setTimeList(String time){
        List<String> list = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try{
            if(time==null||time==""){
                Calendar calendar = Calendar.getInstance();
                for(int i = 0;i<7;i++){
                    calendar.setTime(new Date());
                    calendar.add(Calendar.DATE, i);
                    String  day = sdf.format( calendar.getTime());//
                    list.add(getWeekAndDataStr(day));
                }
            }else {
                Calendar calendar = Calendar.getInstance();
                for(int i = 0;i<7;i++){
                    calendar.setTime(sdf.parse(time));
                    calendar.add(Calendar.DATE, i);
                    String  day = sdf.format( calendar.getTime());//当天
                    list.add(getWeekAndDataStr(day));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        timeList = list;

    }

    /**
     * 获取传入字符串代表的星期数字
     * @param sdate
     * @return
     */
    public static String getWeek(String sdate) {
        // 再转换为时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(sdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return new SimpleDateFormat("EEEE").format(c.getTime());
    }

    /**
     * 获取一个字符串时间转为星期几
     * @param sdate
     * @return
     */
    public static String getWeekAndDataStr(String sdate){
        String str = "";
        str = getWeek(sdate);
        if("1".equals(str)){
            str = "星期日";
        }else if("2".equals(str)){
            str = "星期一";
        }else if("3".equals(str)){
            str = "星期二";
        }else if("4".equals(str)){
            str = "星期三";
        }else if("5".equals(str)){
            str = "星期四";
        }else if("6".equals(str)){
            str = "星期五";
        }else if("7".equals(str)){
            str = "星期六";
        }
        return sdate+" "+str;
    }

    /**
     * 获取会议时间字符串
     * @param beginTime
     * @param endTime
     * @return
     */
    public static String getTimeStr(Date beginTime,Date endTime){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String beginTimeStr = sdf.format(beginTime);
        String endTimeStr = sdf.format(endTime);
        return beginTimeStr+"-"+endTimeStr;
    }

    /**
     * 传入一个时间获取查询周期的表头的方法
     * @param time
     * @return
     */
    public static List<String> getTimeList(String time){
        setTimeList(time);
        return timeList;
    }

    /**
     * 获取当前时间的字符串形式
     * @return
     */
    public static String getTimeStr( ){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
        String timeStr = sdf.format(new Date());
        return timeStr;
    }

    public static void main(String[] args) {
        System.out.println(getTimeList("2019-11-21"));
        System.out.println(getTimeStr(new Date(),new Date()));
    }
}
