package com.nzc.quartz.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName DateUtils
 * @Description TODO
 * @Author yiGeXinDong
 * @Date 2020/5/11 9:53
 * @Version 1.0
 **/
public class DateUtils   {
    public final static int FORMAT_DEFAULT = 0; // 默认格式

    /** 日时字符串格式：长格式（如：年份用4位表示） */
    public final static int FORMAT_LONG = 1; // 长格式（如：年份用4位表示）

    /** 日时字符串格式：短格式（如：年份用2位表示） */
    public final static int FORMAT_SHORT = 2; // 短格式（如：年份用2位表示）

    /** 默认日期字符串格式 "yyyy-MM-dd" */
    public final static String DATE_DEFAULT = "yyyy-MM-dd";
    /** 日期字符串格式 "yyyy" */
    private final static String DATE_YYYY="yyyy";
    /** 日期字符串格式 "mm" */
    private final static String DATE_MM="mm";
    /** 日期字符串格式 "dd" */
    private final static String DATE_DD="dd";
    /** 日期字符串格式 "yyyyMM" */
    public final static String DATE_YYYYMM = "yyyyMM";

    /** 日期字符串格式 "yyyyMMdd" */
    public final static String DATE_YYYYMMDD = "yyyyMMdd";

    /** 日期字符串格式 "yyyy-MM" */
    public final static String DATE_YYYY_MM = "yyyy-MM";

    /** 日期字符串格式 "yyyy-MM-dd" */
    public final static String DATE_YYYY_MM_DD = "yyyy-MM-dd";

    /** 默认日时字符串格式 "yyyy-MM-dd HH:mm:ss" */
    public final static String DATETIME_DEFAULT = "yyyy-MM-dd HH:mm:ss";

    /** 日时字符串格式 "yyyy-MM-dd HH:mm" */
    public final static String DATETIME_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

    /** 日时字符串格式 "yyyy-MM-dd HH:mm:ss" */
    public final static String DATETIME_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    /** 日时字符串格式 "yyyy-MM-dd HH:mm:ss.SSS" */
    public final static String DATETIME_YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";

    /** 默认时间字符串格式 "HH:mm:ss" */
    public final static String TIME_DEFAULT = "HH:mm:ss";

    /** 默认时间字符串格式 "HH:mm" */
    public final static String TIME_HH_MM = "HH:mm";

    /** 默认时间字符串格式 "HH:mm:ss" */
    public final static String TIME_HH_MM_SS = "HH:mm:ss";
    public static final long YEAR_NUMBER=365;
    /** 分 */
    public static final long MINUTE_TTL = 60 * 1000l;
    /** 时 */
    public static final long HOURS_TTL = 60 * 60 * 1000l;
    /** 半天 */
    public static final long HALF_DAY_TTL = 12 * 60 * 60 * 1000l;
    /** 天 */
    public static final long DAY_TTL = 24 * 60 * 60 * 1000l;
    /** 月 */
    public static final long MONTH_TTL = 30 * 24 * 60 * 60 * 1000l;



    /**
     * TODO 计算两个时间差值
     * @param startDate,endDate
     * @Author XuWenXiao
     * @return int(两个时间相差的天数)
     * @throws ParseException
     */
    public static int daysBetween(Date startDate, Date endDate) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        long sTime = calendar.getTimeInMillis();
        calendar.setTime(endDate);
        long endTime = calendar.getTimeInMillis();
        long between_days=(endTime-sTime)/DAY_TTL;
        return Integer.parseInt(String.valueOf(between_days));
    }

    public static Date getCurrentTime() {
        Date date = new Date(System.currentTimeMillis());

        return date;
//        String dateFormat = "ss mm HH dd MM ? yyyy";
//
//        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
//        String formatTimeStr = null;
//        if (date != null) {
//            formatTimeStr = sdf.format(date);
//        }
//        return formatTimeStr;
    }

    /**
     * TODO 获取当前系统时间
     * @Author XuWenXiao
     * @return Date
     */
    public static Date getNewDate(){
        SimpleDateFormat df = new SimpleDateFormat(DATETIME_YYYY_MM_DD_HH_MM_SS);//设置日期格式
        Date newDate=null;
        try {
            newDate=df.parse(df.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDate;
    }
    /**
     * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String formatDate(Date date, Object... pattern) {
        String formatDate = null;
        if (pattern != null && pattern.length > 0) {
            formatDate = DateFormatUtils.format(date, pattern[0].toString());
        } else {
            formatDate = DateFormatUtils.format(date, DATE_YYYY_MM_DD);
        }
        return formatDate;
    }
    /**
     * 得到当前年份字符串 格式（yyyy）
     */
    public static String getYear() {
        return formatDate(new Date(), DATE_YYYY);
    }
    /**
     * 得到当前月份字符串 格式（MM）
     */
    public static String getMonth() {
        return formatDate(new Date(), DATE_MM);
    }
    /**
     * 得到当天字符串 格式（dd）
     */
    public static String getDay() {
        return formatDate(new Date(), DATE_DD);
    }
    /**
     * 获取当前时间的字符串形式（例如；"201806291135"）
     * @return 年月日时分
     */
    public static String getDateToString(){
        Calendar c = Calendar.getInstance();
        return getYear()+getMonth()+getDay()+c.get(Calendar.HOUR_OF_DAY)+c.get(Calendar.MINUTE);
    }
    /**
     * 获取过去的年数
     * @param date
     * @return
     */
    public static Long pastYear(Date date) {
        return date==null?null:pastDays(date)/YEAR_NUMBER;
    }

    /**
     * 获取过去的天数
     * @param date
     * @return
     */
    public static long pastDays(Date date) {
        long t = new Date().getTime()-date.getTime();
        return t/DAY_TTL;
    }

    /**
     * 获取过去的小时
     * @param date
     * @return
     */
    public static long pastHour(Date date) {
        long t = new Date().getTime()-date.getTime();
        return t/HOURS_TTL;
    }

    /**
     * 获取过去的分钟
     * @param date
     * @return
     */
    public static long pastMinutes(Date date) {
        long t = new Date().getTime()-date.getTime();
        return t/MINUTE_TTL;
    }
    /**
     * 获取两个日期之间的天数
     *
     * @param before
     * @param after
     * @return
     */
    public static double getDistanceOfTwoDate(Date before, Date after) {
        long beforeTime = before.getTime();
        long afterTime = after.getTime();
        return (afterTime - beforeTime) /DAY_TTL;
    }
    /**
     * 根据身份证计算出身日期
     *
     */
    /**
     * 通过身份证号码获取出生日期、性别、年齡
     * @param certificateNo
     * @return 返回的出生日期格式：1990-01-01   性别格式：2-女，1-男
     */
    public static Map<String, String> getBirAgeSex(String certificateNo) {
        System.out.println(certificateNo);
        String birthday = "";
        String age = "";
        String sexCode = "";
        int year = Calendar.getInstance().get(Calendar.YEAR);
        char[] number = certificateNo.toCharArray();
        boolean flag = true;
        if (number.length == 15) {
            for (int x = 0; x < number.length; x++) {
                if (!flag) return new HashMap<String, String>();
                flag = Character.isDigit(number[x]);
            }
        } else if (number.length == 18) {
            for (int x = 0; x < number.length - 1; x++) {
                if (!flag) return new HashMap<String, String>();
                flag = Character.isDigit(number[x]);
            }
        }
        if (flag && certificateNo.length() == 15) {
            birthday = "19" + certificateNo.substring(6, 8) + "-"
                    + certificateNo.substring(8, 10) + "-"
                    + certificateNo.substring(10, 12);
            sexCode = Integer.parseInt(certificateNo.substring(certificateNo.length() - 3, certificateNo.length())) % 2 == 0 ? "2" : "1";
            age = (year - Integer.parseInt("19" + certificateNo.substring(6, 8))) + "";
        } else {
            birthday = certificateNo.substring(6, 10) + "-"
                    + certificateNo.substring(10, 12) + "-"
                    + certificateNo.substring(12, 14);
            sexCode = Integer.parseInt(certificateNo.substring(certificateNo.length() - 4, certificateNo.length() - 1)) % 2 == 0 ? "2" : "1";
            age = (year - Integer.parseInt(certificateNo.substring(6, 10))) + "";
        }
        Map<String, String> map = new HashMap<String, String>();
        map.put("birthday", birthday);
        map.put("age", age);
        map.put("sexCode", sexCode);
        return map;
    }
    /**
     * 根据身份证的号码算出当前身份证持有者的年龄 18位身份证
     * @author 黄涛
     * @return
     * @throws Exception
     */
    public static int getCarAge(String birthday){
        String year = birthday.substring(0, 4);//得到年份
        String yue = birthday.substring(4, 6);//得到月份
        //String day = birthday.substring(6, 8);//
        Date date = new Date();// 得到当前的系统时间
        SimpleDateFormat format = new SimpleDateFormat(DATE_YYYY_MM_DD);
        String fYear = format.format(date).substring(0, 4);// 当前年份
        String fYue = format.format(date).substring(5, 7);// 月份
        // String fday=format.format(date).substring(8,10);
        int age = 0;
        if (Integer.parseInt(yue) <= Integer.parseInt(fYue)) { // 当前月份大于用户出身的月份表示已过生
            age = Integer.parseInt(fYear) - Integer.parseInt(year) + 1;
        } else {// 当前用户还没过生
            age = Integer.parseInt(fYear) - Integer.parseInt(year);
        }
        return age;
    }
    /**
     *
     * 根据身份证获取性别
     */
    public static String getCarSex(String CardCode){
        String sex;
        if (Integer.parseInt(CardCode.substring(16).substring(0, 1)) % 2 == 0) {// 判断性别
            sex = "2";
        } else {
            sex = "1";
        }
        return sex;
    }



    /**
     * 根据传入的日历型日期，计算出执行的时间值（精确版）
     * @param beginTime
     * @param endTime
     * @return
     */
    public static String countExecTimeToString_exact(Calendar beginTime,Calendar endTime)
    {//计算两个日期类型之间的差值（单位：毫秒）
        Long timeDispersion = endTime.getTimeInMillis() - beginTime.getTimeInMillis();
        String tmpMsg = "耗时: ";//拼写输出的字符串
        int timeNum = 0;//记录时间的数值（几小时、几分、几秒）
        if(timeDispersion >= (HOURS_TTL))//判断是否足够一小时
        {//若足够则计算有几小时
            timeNum = (int) (timeDispersion/HOURS_TTL);
            tmpMsg += timeNum + "时";//拼写输出几小时
            timeDispersion = timeDispersion - (timeNum*HOURS_TTL);//减去小时数（这样剩下的就是分钟数了）
        }
        if(timeDispersion >= (MINUTE_TTL))//判断是否足够一分钟
        {//若足够则计算有几分钟
            timeNum = (int) (timeDispersion/MINUTE_TTL);
            tmpMsg += timeNum + "分";//拼写输出几分钟
            timeDispersion = timeDispersion - (timeNum*MINUTE_TTL);//减去分钟数（这样就剩下秒数了）
        }
        if(timeDispersion >= 1000)//判断是否足够一秒
        {//若足够则计算几秒
            timeNum = (int) (timeDispersion/1000);
            tmpMsg +=  timeNum + "秒";//拼写输出秒数
            timeDispersion = timeDispersion - timeNum*1000;//减去秒数（这样就剩下毫秒数了）
        }
        tmpMsg += timeDispersion + "毫秒";//拼写输出毫秒数
        return tmpMsg;

    }//重载方法，返回Long类型（毫秒）
    public static Long countExecTimeToLong_exact(Calendar beginTime,Calendar endTime)
    {//直接返回毫秒数
        return (endTime.getTimeInMillis() - beginTime.getTimeInMillis());
    }
    /**
     * 根据传入的long类型日期，计算出执行的时间值（精确版）
     * @param beginTime
     * @param endTime
     * @return
     */
    public static String countExecTimeToString_exact(Long beginTime,Long endTime)
    {//计算两个日期类型之间的差值（单位：毫秒）
        Long timeDispersion = endTime - beginTime;
        String tmpMsg = "耗时: ";//拼写输出的字符串
        int timeNum = 0;//记录时间的数值（几小时、几分、几秒）
        if(timeDispersion >= (HOURS_TTL))//判断是否足够一小时
        {//若足够则计算有几小时
            timeNum = (int) (timeDispersion/(HOURS_TTL));
            tmpMsg += timeNum + "时";//拼写输出几小时
            timeDispersion = timeDispersion - (timeNum*HOURS_TTL);//减去小时数（这样剩下的就是分钟数了）
        }
        if(timeDispersion >= (MINUTE_TTL))//判断是否足够一分钟
        {//若足够则计算有几分钟
            timeNum = (int) (timeDispersion/(MINUTE_TTL));
            tmpMsg += timeNum + "分";//拼写输出几分钟
            timeDispersion = timeDispersion - (timeNum*MINUTE_TTL);//减去分钟数（这样就剩下秒数了）
        }
        if(timeDispersion >= 1000)//判断是否足够一秒
        {//若足够则计算几秒
            timeNum = (int) (timeDispersion/1000);
            tmpMsg +=  timeNum + "秒";//拼写输出秒数
            timeDispersion = timeDispersion - timeNum*1000;//减去秒数（这样就剩下毫秒数了）
        }
        tmpMsg += timeDispersion + "毫秒";//拼写输出毫秒数
        return tmpMsg;

    }
    /**
     * 获取指定年度全部月份集合<br>
     * 格式为yyyy-MM
     * @author 王鹏
     * @version 2019年11月16日 下午4:52:55
     * @param year 年度
     * @return
     */
    public static List<String> getYearMonthByYear(String year) {
        List<String> monthList =new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            monthList.add(year+"-"+(i<10?"0":"")+i);
        }
        return monthList;
    }

    /**
     * 获取下个月
     * dateStr 日期
     * format 格式：yyyy-MM或yyyyMM
     * @return
     */
    public static String getPreMonth(String dateStr,String format) {
        String preMonth = "";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date;
        try {
            date = sdf.parse(dateStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(calendar.MONTH, 1);
            sdf.format(calendar.getTime());

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return preMonth;
    }
    /**
     * 获取下个月
     * dateStr 日期
     * format 格式：yyyy-MM或yyyyMM
     * @return
     */
    public static String getPreMonthFormat(String dateStr,String format) {
        String preMonth = "";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date;
        try {
            date = sdf.parse(dateStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(calendar.MONTH, 1);
            preMonth= sdf.format(calendar.getTime());

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return preMonth;
    }
    /**
     * 日期比较
     * date1比date2大true
     */
    public static boolean dateSize(String aDate,String bDate,SimpleDateFormat format) {
        boolean str = false;
        try {
            if(StringUtils.isNotBlank(aDate)&&StringUtils.isNotBlank(bDate)) {
                Date date1 = format.parse(aDate);
                Date date2 = format.parse(bDate);
                str = date1.after(date2);
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return str;
    }

}



