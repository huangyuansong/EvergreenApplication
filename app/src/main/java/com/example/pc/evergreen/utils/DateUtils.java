package com.example.pc.evergreen.utils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.text.*;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 */
    public class DateUtils {

    /**
     * 用于计算两个时间的差值
     * @param updateTime 上次时间
     * @return
     */
        public static long timeCalculation(String updateTime){

            String todayTiem = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());//当前时间
            SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long times = 0;
            Date date = null;
            try {
                date = myFormatter.parse(String.valueOf(updateTime));
                java.util.Date mydate = myFormatter.parse(todayTiem);
                times = (date.getTime() - mydate.getTime()) / ( 60 * 1000);//得到当前时间和上次更新时间差.
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return times;
    }

    /**
     * 判断是否为规定的日期格式：（yyyy-MM-dd）
     * @param
     * @return
     */
    public static boolean isValidDate(String sDate) {
        String datePattern1 = "\\d{4}-\\d{2}-\\d{2}";
        String datePattern2 = "^((\\d{2}(([02468][048])|([13579][26]))"
                + "[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|"
                + "(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?"
                + "((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?("
                + "(((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?"
                + "((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
        if ((sDate != null)) {
            Pattern pattern = Pattern.compile(datePattern1);
            Matcher match = pattern.matcher(sDate);
            if (match.matches()) {
                pattern = Pattern.compile(datePattern2);
                match = pattern.matcher(sDate);
                return match.matches();
            }
            else {
                return false;
            }
        }
        return false;
    }
}