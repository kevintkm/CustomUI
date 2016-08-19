package com.example.lenovo.timescroller.Util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * 日期操作类
 * @author linjinfa@126.com 
 * @version 2012-9-16 下午4:16:47
 */
public class DateUtil {

    public static final String DEFAULT_DATE_TIME_ZERO = "0000-00-00 00:00:00";
    public static final String DATE = "yyyy-MM-dd";
    public static final String DATES = "yyyy/MM/dd";
    public static final String DATEMONTHCZ = "yyyy年MM月";
    public static final String DATEMONTHDAY = "MM月dd日";
    public static final String DATESTR = "yyyyMMdd";
    public static final String TIME = "HH:mm:ss";
    public static final String HOUR_MINUTE = "HH:mm";
    public static final String DATETIME = "yyyy-MM-dd HH:mm:ss";
    public static final String DATETIME_NO_SECOND = "yyyy-MM-dd HH:mm";
    public static final String DATETIME_NO_SECOND_ = "yyyy.MM.dd HH:mm";
    public static final String PRECISE = "yyyy-MM-dd HH.mm.ss.SSS";
    public static final String $PRECISE = "yyyy-MM-dd$HH.mm.ss";
    public static final String DATETIMESTR = "yyyyMMddHHmmss";
    public static final String TIMESTR = "HHmmss";


    /**
     * 根据日期返回对应当前月的最大天数
     * @return
     */
    public static int getMaxDays(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 根据日期返回年
     * @param date
     * @return	date为null时返回0
     */
    public static int getYear(Date date) {
        return get(date, Calendar.YEAR);
    }

    /**
     * 根据日期返回年
     * @param date
     * @return date为null时返回0
     */
    public static int getYear(String date) {
        return get(toDate(date), Calendar.YEAR);
    }

    /**
     * 根据日期返回月
     * @param date
     * @return date为null时返回0
     */
    public static int getMonth(Date date) {
        return get(date, Calendar.MONTH);
    }

    /**
     * 根据日期返回月
     * @param date
     * @return date为null时返回0
     */
    public static int getMonth(String date) {
        return get(toDate(date), Calendar.MONTH);
    }

    /**
     * 根据日期返回天
     * @param date
     * @return date为null时返回0
     */
    public static int getDay(Date date) {
        return get(date, Calendar.DAY_OF_MONTH);
    }

    /**
     * 根据日期返回天
     * @param date
     * @return date为null时返回0
     */
    public static int getDay(String date) {
        return get(toDate(date), Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取当前小时 (24小时制)
     * @return
     */
    public static int getHour(){
        return get(getNowDate(), Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取指定日期时间 的 小时 (24小时制)
     * @param dateTimeStr
     * @return
     */
    public static int getHour(String dateTimeStr){
        return get(toDateTime(dateTimeStr), Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取当前分钟 (24小时制)
     * @return
     */
    public static int getMin(){
        return get(getNowDate(), Calendar.MINUTE);
    }

    /**
     * 获取指定日期时间的 分钟 (24小时)
     * @param dateTimeStr
     * @return
     */
    public static int getMin(String dateTimeStr){
        return get(toDateTime(dateTimeStr), Calendar.MINUTE);
    }



    public static int get(Date date, int type) {
        if (date == null) {
            return 0;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(type);
    }

    /**
     * 日期字符串转换成指定的格式
     * @param dateString
     * @param pattern
     * @return
     */
    public static String toPattern(String dateString, String pattern) {
        if (dateString == null || "".equals(dateString)) {
            return null;
        }
        if (pattern == null || "".equals(pattern)) {
            return null;
        }
        Date date = null;
        if(DateUtil.DATE.equals(pattern) || DateUtil.DATES.equals(pattern) || DateUtil.DATESTR.equals(pattern) || DateUtil.DATEMONTHCZ.equals(pattern))
            date = toDate(dateString);
        else
            date = toDateTime(dateString);
        return toPattern(date, pattern);
    }

    /**
     * 时间戳转换成指定的格式
     * @param pattern
     * @return
     */
    public static String toPattern(long timeStamp, String pattern) {
        if (timeStamp<=0) {
            return null;
        }
        if (pattern == null || "".equals(pattern)) {
            return null;
        }
        try {
            Date date = new Date(timeStamp);
            return toPattern(date, pattern);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 日期字符串转换成Date	不包含Time
     * @param dateTimeStr
     * @return
     */
    public static Date toDate(String dateTimeStr) {
        if (dateTimeStr == null || dateTimeStr.equals("")) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATE);
        try {
            return sdf.parse(dateTimeStr);
        } catch (ParseException e1) {
            return null;
        }
    }

    /**
     * 日期字符串转换成Date	包含Time
     * @param dateTimeStr
     * @return
     */
    public static Date toDateTime(String dateTimeStr) {
        if (dateTimeStr == null || dateTimeStr.equals("")) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATETIME);
        try {
            return sdf.parse(dateTimeStr);
        } catch (ParseException e1) {
            return null;
        }
    }

    /**
     * 日期时间字符串转换成时间只包含Time
     * @return
     */
    public static String toTime(Date date) {
        if(date==null)
            return null;
        SimpleDateFormat sdf = new SimpleDateFormat(TIME);
        try {
            return sdf.format(date);
        } catch (Exception e1) {
            return null;
        }
    }

    public static String toMonthAndDay(Date date){
        if(date==null)
            return null;
        SimpleDateFormat sdf = new SimpleDateFormat(DATEMONTHDAY);
        try {
            return sdf.format(date);
        } catch (Exception e1) {
            return null;
        }
    }


    /**
     * 日期Date转换成指定的格式
     * @param date
     * @param pattern
     * @return
     */
    public static String toPattern(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * 比较两个日期
     * 判断DATE1是否在时间date2之前
     * @param date1
     * @param date1
     * @return	1：DATE1 在 DATE2之后
     * 		   -1：DATE1 在 DATE2之前
     * 		   0：日期相等
     */
    public static int compareDateTime(String date1, String date2, String pattern) {
        DateFormat df = new SimpleDateFormat(pattern);
        try {
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 比较两个日期   日期的时间没有秒数
     * 判断DATE1是否在时间date2之前
     * @param date1
     * @param date1
     * @return	1：DATE1 在 DATE2之后
     * 		   -1：DATE1 在 DATE2之前
     * 		   0：日期相等
     */
    public static int compareDateTimeNoSecond(String date1, String date2) {
        return compareDateTime(date1,date2,DATETIME_NO_SECOND);
    }

    /**
     * 计算times开始经过seconds秒后的日期时间
     * @param startDate
     * @param seconds   秒数 不是毫秒
     * @return
     */
    public static String getDateAfterTime(String startDate, long seconds){
        try {
            SimpleDateFormat format = new SimpleDateFormat(DATETIME);
            return format.format(toDateTime(startDate).getTime() + seconds*1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param datestr
     *            日期字符串
     * @param day
     *            相对天数，为正数表示之后，为负数表示之前
     * @return 指定日期字符串n天之前或者之后的日期
     */
    public static String getBeforeAfterDate(String datestr, int day) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date olddate = null;
        try {
            df.setLenient(false);
            olddate = new Date(df.parse(datestr).getTime());
        } catch (ParseException e) {
            return datestr;
        }
        Calendar cal = new GregorianCalendar();
        cal.setTime(olddate);

        int Year = cal.get(Calendar.YEAR);
        int Month = cal.get(Calendar.MONTH);
        int Day = cal.get(Calendar.DAY_OF_MONTH);

        int NewDay = Day + day;

        cal.set(Calendar.YEAR, Year);
        cal.set(Calendar.MONTH, Month);
        cal.set(Calendar.DAY_OF_MONTH, NewDay);

        return df.format(new Date(cal.getTimeInMillis()));
    }

    /**
     * 计算两个月份的月份差
     * @param date1
     * @param date2
     * @return
     */
    public static int diffMonth(String date1, String date2){
        try {
            Date d = toDate(date1);
            Date d1 = toDate(date2);
            Calendar c = Calendar.getInstance();
            c.setTime(d);
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            c.setTime(d1);
            int year1 = c.get(Calendar.YEAR);
            int month1 = c.get(Calendar.MONTH);
            if (year == year1) {//两个日期相差几个月，即月份差
                return Math.abs(month1 - month);
            } else {//两个日期相差几个月，即月份差
                return Math.abs(12 * (year1 - year) + month1 - month);
            }
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 计算两个日期间隔的天数
     * @param startDate
     * @param endDate
     * @return
     */
    public static Long getDaysBetween(Date startDate, Date endDate) {
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(startDate);
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(endDate);
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);

        return (toCalendar.getTime().getTime() - fromCalendar.getTime()
                .getTime()) / (1000 * 60 * 60 * 24);
    }

    /**
     * 实现给定某日期，判断是星期几 <br>
     * date 必须yyyy-MM-dd
     * @return <br>
     */
    public static String getWeekday(String date) {
        if (date==null || "".equals(date) || date.length() == 0) {
            return "";
        }
        try {
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdw = new SimpleDateFormat("E");
            Date d = sd.parse(date);
            return sdw.format(d);
        } catch (Exception e) {}
        return "";
    }



    /**
     * 计算某一星期几的日期
     * @param delay	 推迟的周数，0本周，-1向前推迟一周，1下周，依次类推
     * @param week	Calendar中的值    例如：Calendar.MONDAY ...等等
     * @return
     */
    public static String getDateByWeek(int delay, int week){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, delay * 7);
        cal.set(Calendar.DAY_OF_WEEK, week);
        return toPattern(cal.getTime(), DATE);
    }

    /**
     * 返回本月第一天日期
     * @return
     */
    public static String getCurrentMonthFirst(){
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.DATE, 1);
        return toPattern(calendar.getTime(), DATE);
    }

    /**
     * 返回本月最后一天日期
     * @return
     */
    public static String getCurrentMonthLast(){
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.DATE, 1);
        calendar.roll(Calendar.DATE, -1);
        return toPattern(calendar.getTime(), DATE);
    }

    /**
     * 计算两个日期之间的所有日期	(不包含临界点)
     * @param dateFrom
     * @param dateTo
     * @return
     */
    public static List<String> getDateBetween(String dateFrom, String dateTo){
        List<String> list = new ArrayList<String>();
        String dateFormat = "yyyy-MM-dd";
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        if(dateFrom.equals(dateTo)){
            return list;
        }
        String tmp;
        if(dateFrom.compareTo(dateTo) > 0){  //确保 dateFrom的日期不晚于dateTo
            tmp = dateFrom;
            dateFrom = dateTo;
            dateTo = tmp;
        }
        tmp = format.format(toDate(dateFrom).getTime() + 3600*24*1000);
        int num = 0;
        while(tmp.compareTo(dateTo) < 0){
            list.add(tmp);
            num++;
            tmp = format.format(toDate(tmp).getTime() + 3600*24*1000);
        }
        if(num == 0)
            return list;
        return list;
    }

    /**
     * 计算两个日期之间所有的时间(每隔60s)
     * @param dateFrom
     * @param dateTo
     * @return
     */
    public static List<String> getTimeBetween(String dateFrom, String dateTo){
        List<String> list = new ArrayList<String>();
        SimpleDateFormat format = new SimpleDateFormat(DATETIME);
        if(dateFrom.equals(dateTo)){
            return list;
        }
        String tmp;
        if(dateFrom.compareTo(dateTo) > 0){  //确保 dateFrom的日期不晚于dateTo
            tmp = dateFrom;
            dateFrom = dateTo;
            dateTo = tmp;
        }
        tmp = format.format(toDate(dateFrom).getTime() + 60*1000);
        int num = 0;
        while(tmp.compareTo(dateTo) < 0){
            list.add(tmp);
            num++;
            tmp = format.format(toDateTime(tmp).getTime() + 60*1000);
        }
        if(num == 0)
            return list;
        return list;
    }

    /**
     * 返回当前日期时间
     *
     * @return Date
     */
    public static Date getNowDate() {
        return new Date(System.currentTimeMillis());
    }

    /**
     * yyyy-MM-dd HH:mm:ss"格式返回当前时间
     *
     * @return String
     */
    public static String getNowString() {
        SimpleDateFormat sdf = new SimpleDateFormat(DATETIME);
        return sdf.format(getNowDate());
    }

    /**
     * 根据指定格式返回当前日期时间
     *
     * @return String
     */
    public static String getNowString(String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(getNowDate());
    }

    /**
     * 计算两个日期时间间隔的毫秒数
     * @param startDateTime	格式 yyyy-MM-dd HH:mm:ss
     * @param endDateTime	格式 yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static Long getTimesBetween(String startDateTime, String endDateTime){
        try {
            Calendar fromCalendar = Calendar.getInstance();
            Date startDate = toDateTime(startDateTime);
            fromCalendar.setTime(startDate);
            fromCalendar.set(Calendar.HOUR_OF_DAY, get(startDate, Calendar.HOUR_OF_DAY));
            fromCalendar.set(Calendar.MINUTE, get(startDate, Calendar.MINUTE));
            fromCalendar.set(Calendar.SECOND, get(startDate, Calendar.SECOND));
            fromCalendar.set(Calendar.MILLISECOND, get(startDate, Calendar.MILLISECOND));

            Calendar toCalendar = Calendar.getInstance();
            Date endDate = toDateTime(endDateTime);
            toCalendar.setTime(endDate);
            toCalendar.set(Calendar.HOUR_OF_DAY, get(endDate, Calendar.HOUR_OF_DAY));
            toCalendar.set(Calendar.MINUTE, get(endDate, Calendar.MINUTE));
            toCalendar.set(Calendar.SECOND, get(endDate, Calendar.SECOND));
            toCalendar.set(Calendar.MILLISECOND, get(endDate, Calendar.MILLISECOND));
            return toCalendar.getTime().getTime() - fromCalendar.getTime().getTime();
        } catch (Exception e) {
            return 0L;
        }
    }

    /**
     * 计算两个日期时间间隔的毫秒数
     * @param startDateTime	格式 yyyy-MM-dd HH:mm:ss
     * @param endDateTime	格式 毫秒数
     * @return
     */
    public static Long getTimesBetweenMilliseconds(String startDateTime, String endDateTime){
        try {
            Calendar fromCalendar = Calendar.getInstance();
            Date startDate = toDateTime(startDateTime);
            fromCalendar.setTime(startDate);
            fromCalendar.set(Calendar.HOUR_OF_DAY, get(startDate, Calendar.HOUR_OF_DAY));
            fromCalendar.set(Calendar.MINUTE, get(startDate, Calendar.MINUTE));
            fromCalendar.set(Calendar.SECOND, get(startDate, Calendar.SECOND));
            fromCalendar.set(Calendar.MILLISECOND, get(startDate, Calendar.MILLISECOND));
            return Long.parseLong(endDateTime) - fromCalendar.getTime().getTime();
        } catch (Exception e) {
            return 0L;
        }
    }

    /**
     * *天*小时*分*秒
     * 计算两个日期间隔的 天数时间数
     * @param startDateTime
     * @param endDateTime
     * @return
     */
    public static String calFormatDatesDuring(String startDateTime, String endDateTime){
        return formatDuring(getTimesBetween(startDateTime,endDateTime),true,true);
    }

    /**
     * *天*小时*分*秒
     * 计算两个日期间隔的 天数时间数
     * @param startDateTime 格式 yyyy-MM-dd HH:mm:ss
     * @param endDateTime   格式 毫秒数
     * @return
     */
    public static String calFormatDatesDuringGrp(String startDateTime, String endDateTime){
        return formatDuringGrp(getTimesBetweenMilliseconds(startDateTime,endDateTime));

    }

    /**
     * *天*小时*分*秒
     * 计算两个日期间隔的 天数时间数
     * @param startDateTime 格式 毫秒数
     * @param endDateTime   格式 毫秒数
     * @return
     */
    public static String calFormatDatesDuringCom(String startDateTime, String endDateTime){
        return formatDuringGrp(getTimesBetween(startDateTime,endDateTime));
    }

    /**
     * *天*小时*分  只精确到分  可看 calFormatDatesDuring 方法
     * 计算两个日期间隔的 天数时间数
     * @param startDateTime
     * @param endDateTime
     * @return
     */
    public static String calFormatDatesDuringToMin(String startDateTime, String endDateTime){
        return formatDuring(getTimesBetween(startDateTime,endDateTime),false,false);
    }

    /**
     * *天*小时*分*秒
     * 计算两个日期间隔的 天数时间数
     * @param startDateTime
     * @param endDateTime
     * @return
     */
    public static String calFormatDatesDuringMD(String startDateTime, String endDateTime){
        long time = getTimesBetween(startDateTime,endDateTime);
        if(time<0){
            return "";
        }
        return formatDuringMd(getTimesBetween(startDateTime,endDateTime));
    }

    /**
     * 倒计时显示规则是：超过天，显示天、时、分；不超过天超过时，显示时、分、秒；不超过时超过分，显示分、秒；不超过分超过秒，显示秒
     * @param mss 要转换的毫秒数
     */
    public static String formatDuringMd(long mss) {
        long days = mss / (1000 * 60 * 60 * 24);
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;
        if(days<0){
            days = 0;
        }
        if(hours<0){
            hours = 0;
        }
        if(minutes<0){
            minutes = 0;
        }
        if(seconds<0){
            seconds = 0;
        }
        StringBuffer sb = new StringBuffer();
        if(days>0){ //超过天
            sb.append(days+"天"+hours+"时"+minutes+"分");
        }else if(hours>0){  //不超过天超过时
            sb.append(hours+"时"+minutes+"分"+seconds+"秒");
        }else if(minutes>0){ //不超过时超过分
            sb.append(minutes+"分"+seconds+"秒");
        }else if(seconds>0){  //不超过分超过秒
            sb.append(seconds+"秒");
        }else{
            sb.append("");
        }
        return sb.toString();
    }

    /**
     * 将毫秒数 转换成 *天*小时*分*秒
     * @param mss 要转换的毫秒数
     * @param isShowZero 是否显示0  即：0天0小时50分  直接显示成 50分
     * @param isToSeconds 是否精确到秒
     * @return 该毫秒数转换为 * days * hours * minutes * seconds 后的格式
     */
    public static String formatDuring(long mss, boolean isShowZero, boolean isToSeconds) {
        long days = mss / (1000 * 60 * 60 * 24);
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;
        if(days<0){
            days = 0;
        }
        if(hours<0){
            hours = 0;
        }
        if(minutes<0){
            minutes = 0;
        }
        if(seconds<0){
            seconds = 0;
        }
        StringBuffer sb = new StringBuffer();
        if(isShowZero){ //即使是0 也显示
            sb.append(days+"天"+hours+"时"+minutes+"分");
        }else{  //是0不显示
            if(days==0 && hours==0){    //天、时为0 只显示 分
                sb.append(minutes+"分");
            }else if(days==0 && hours!=0){  //天为0 只显示 时 分
                sb.append(hours+"时"+minutes+"分");
            }else if(days!=0 && hours!=0){  //都不为0 都显示
                sb.append(days+"天"+hours+"时"+minutes+"分");
            }
        }
        if(isToSeconds){   //精确到秒
            sb.append(seconds+"秒");
        }
        return sb.toString();
    }


    /**
     * 团购订单
     * 将毫秒数 转换成 *天*小时*分*秒 格式如：1天01时02分02秒。01时02分03秒。12分01秒，01秒
     * @param mss 要转换的毫秒数
     * @return 该毫秒数转换为 * days * hours * minutes * seconds 后的格式
     */
    public static String formatDuringGrp(long mss) {
        long days = mss / (1000 * 60 * 60 * 24);
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;

        if(days<0){
            days = 0;
        }
        if(hours<0){
            hours = 0;
        }
        if(minutes<0){
            minutes = 0;
        }

        StringBuffer sb = new StringBuffer();
        if (days > 0){
            sb.append(days + "天");
        }
        if (hours >= 10){
            sb.append(hours + "时");
        }else if (hours > 0 && hours < 10){
            sb.append("0" + hours + "时");
        }else if (days > 0){
            sb.append("00时");
        }
        if (minutes >= 10){
            sb.append(minutes + "分");
        }else if (minutes > 0 && minutes < 10){
            sb.append("0" + minutes + "分");
        }else if (days > 0 || hours > 0){
            sb.append("00分");
        }
        if (seconds >= 10){
            sb.append(seconds + "秒");
        }else if (seconds >= 0 && seconds < 10){
            sb.append("0" + seconds + "秒");
        }
        return sb.toString();
    }

    /**
     * 获取当前日期是星期几<br>
     *
     * @param dt
     * @return 当前日期是星期几
     */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        if(w>=weekDays.length){
            w = weekDays.length-1;
        }
        return weekDays[w];
    }

    /**
     * 时间显示规则：
     1.当天：直接显示时间，例如14:20
     2.昨天：显示日期，例如”昨天“
     3.过去7天内的其他日期：显示星期几，例如星期三
     4.7天之前：直接显示年月日，例如15-05-08
     * @param timestamp
     * @return
     */
    public static String toHourMinute(long timestamp) {
        if(timestamp < 1)
            return "";
        SimpleDateFormat format;
        String res = "";
        long dex = getDaysBetween(new Date(timestamp),getNowDate());
        if(dex>=7){
            format = new SimpleDateFormat(DATE);
            res = format.format(new Date(timestamp));
        }else if(dex<7 && dex>=2){
            format = new SimpleDateFormat(HOUR_MINUTE);
            res = getWeekOfDate(new Date(timestamp));
        }else if(dex<2 && dex>=1){
            format = new SimpleDateFormat(HOUR_MINUTE);
            res = "昨天";
        }else{
            format = new SimpleDateFormat(HOUR_MINUTE);
            res = format.format(new Date(timestamp));
        }
        return res;
    }

    public static String toStartHourMinute(long timestamp) {
        if(timestamp < 1)
            return "";
        SimpleDateFormat format = new SimpleDateFormat(HOUR_MINUTE);
        return format.format(new Date(timestamp));
    }

}
