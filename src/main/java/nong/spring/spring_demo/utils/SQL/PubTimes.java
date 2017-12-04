package nong.spring.spring_demo.utils.SQL;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by linzhurui on 2017/7/15.
 * 说明：
 * 常用的时间方法 类
 */
public class PubTimes {

    private static PubTimes pubTimes;

    public static synchronized PubTimes getInstance() {
        if (pubTimes == null) {
            pubTimes = new PubTimes();
        }
        return pubTimes;
    }

    //获取年月
    public String getYyyyMM() {
        SimpleDateFormat _df = new SimpleDateFormat("yyyyMM");
        return _df.format(new Date());
    }

    //获取年月日期
    public String getYyyyMMdd() {
        SimpleDateFormat _df = new SimpleDateFormat("yyyyMMdd");
        return _df.format(new Date());
    }

    //获取时间
    public String getHHmmss() {
        SimpleDateFormat _df = new SimpleDateFormat("HHmmss");
        return _df.format(new Date());
    }

    //获取日期+时间
    public String getYyyyMMddHHmmss() {
        SimpleDateFormat _df = new SimpleDateFormat("yyyyMMddHHmmss");
        return _df.format(new Date());
    }

    //获取日期+时间
    public String getYYMMddHHmm() {
        SimpleDateFormat _df = new SimpleDateFormat("yyMMddHHmm");
        return _df.format(new Date());
    }

    //获取日期+时间完整
    public String getTimeFull() {
        SimpleDateFormat _df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return _df.format(new Date());
    }

    //获取字符串日期
    public String getStr2Date(String _dateTime, String _flag) {
        String _yyyy = _dateTime.substring(0, 4);
        String _MM = _dateTime.substring(4, 4 + 2);
        String _dd = _dateTime.substring(6, 6 + 2);
        return _yyyy + _flag + _MM + _flag + _dd;
    }

    //获取字符串时间
    public String getStr2Time(String _dateTime, String _flag) {
        String _HH = _dateTime.substring(8, 8 + 2);
        String _mm = _dateTime.substring(10, 10 + 2);
        String _ss = _dateTime.substring(12, 12 + 2);
        return _HH + _flag + _mm + _flag + _ss;
    }

    //获取格式日期时间
    public String getDateTime(String _format, Date _date) {
        //SimpleDateFormat _df = new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat _df = new SimpleDateFormat(_format);
        return _df.format(_date);
    }

    //获取当前 时间戳
    public String getNow2Str() {
        //转成mysql标准长度
        String _str = Long.toString(System.currentTimeMillis());
        _str = _str.substring(0, 10);
        return _str;
    }

    //获取时间转 时间戳
    public String getTime2Int(Date _time) {
        String _int = "";
        String _logInt = Long.toString(_time.getTime());
        if (_logInt.length() == 13) {
            _int = _logInt.substring(0, 10);
        } else {
            _int = _logInt;
        }
        return _int;
    }

    //获取日期转 时间戳
    public String getStrDate2Int(String _date) {
        SimpleDateFormat sdf;
        _date = _date.trim();
        if (_date.length() == 8) {
            sdf = new SimpleDateFormat("yyyyMMdd");
        } else if (_date.length() == 14) {
            sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        } else if (_date.length() == 19) {
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        } else {
            sdf = new SimpleDateFormat("yyyy-MM-dd");
        }
        //String str = sdf.format(_date);

        try {
            Date time = sdf.parse(_date);
            return getTime2Int(time);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取 时间戳 转格式时间
     */
    public String getInt2DateTime(String _dateInt, String _format) {//yyyyMMddHHmmss
        //支持mysql标准长度
        if (_dateInt.length() == 10) {
            _dateInt += "000";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(_format);
        return sdf.format(new Date(Long.parseLong(_dateInt)));
    }

    /**
     * 获取 时间戳 转时间
     */
    public String getInt2YMDHms(String timeIntStr) {
        String strs = "";
        try {
            if (timeIntStr.equals("0")) {
                strs = " ";
            } else {
                long mill = Long.parseLong(timeIntStr += "000");
                Date date = new Date(mill);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                strs = sdf.format(date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strs;
    }

    /**
     * 获取 时间戳 转时间
     */
    public String getInt2Hms(String timeIntStr) {
        String strs = "";
        try {
            if (timeIntStr.equals("0")) {
                strs = "00:00:00";
            } else {
                long mill = Long.parseLong(timeIntStr += "000");
                Date date = new Date(mill);
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                strs = sdf.format(date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strs;
    }

    /**
     * 获取 时间戳 转时间
     */
    public String getInt2ms(String timeIntStr) {
        String strs = "";
        try {
            if (timeIntStr.equals("0")) {
                strs = "00:00";
            } else {
                long mill = Long.parseLong(timeIntStr += "000");
                Date date = new Date(mill);
                SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
                strs = sdf.format(date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strs;
    }

    /**
     * 获取 时间戳 转时间
     */
    public String getInt2yyyymmdd(String timeIntStr) {
        String strs = "";
        try {
            if (timeIntStr.equals("0")) {
                strs = "00:00";
            } else {
                long mill = Long.parseLong(timeIntStr += "000");
                Date date = new Date(mill);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                strs = sdf.format(date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strs;
    }

    /**
     * 获取 时间戳 转时间
     */
    public String getInt2mmdd(String timeIntStr) {
        String strs = "";
        try {
            if (timeIntStr.equals("0")) {
                strs = "00:00";
            } else {
                long mill = Long.parseLong(timeIntStr += "000");
                Date date = new Date(mill);
                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
                strs = sdf.format(date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strs;
    }


    /**
     * 转换日期 将日期转为今天, 昨天, 前天, XXXX-XX-XX, ...
     *
     * @param time 时间
     * @return 当前日期转换为更容易理解的方式
     */
    public String translateDate(Long time) {
        long oneDay = 24 * 60 * 60 * 1000;
        Calendar current = Calendar.getInstance();
        Calendar today = Calendar.getInstance();    //今天

        today.set(Calendar.YEAR, current.get(Calendar.YEAR));
        today.set(Calendar.MONTH, current.get(Calendar.MONTH));
        today.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH));
        //  Calendar.HOUR——12小时制的小时数 Calendar.HOUR_OF_DAY——24小时制的小时数
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);

        long todayStartTime = today.getTimeInMillis();

        if (time >= todayStartTime && time < todayStartTime + oneDay) { // today
            return "今天";
        } else if (time >= todayStartTime - oneDay && time < todayStartTime) { // yesterday
            return "昨天";
        } else if (time >= todayStartTime - oneDay * 2 && time < todayStartTime - oneDay) { // the day before yesterday
            return "前天";
        } else if (time > todayStartTime + oneDay) { // future
            return "将来某一天";
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(time);
            return dateFormat.format(date);
        }
    }

    public boolean isEmpty(CharSequence str) {
        throw new RuntimeException("Stub!");
    }

    /**
     * 将时间戳转为代表"距现在多久之前"的字符串
     *
     * @param timeStr 时间戳
     * @return
     */
    public String getAgoTime(String timeStr) {

        String sb = "";
        long t = Long.parseLong(timeStr);
        long time = System.currentTimeMillis() - (t * 1000);

        long day = (long) Math.floor(time / 24 / 60 / 60 / 1000.0f);// 天前
        if ((long) Math.floor(time / 1000) < 61) {
            sb += "刚刚";
        } else {
            if (day > 0) {
                time = (long) (time - day * (24 * 60 * 60 * 1000.0f));
            }
            long hour = (long) Math.floor(time / 60 / 60 / 1000.0f);// 小时
            if (hour > 0) {
                sb += hour + "小时";
                time = (long) (time - hour * (60 * 60 * 1000.0f));
            }
            long minute = (long) Math.floor(time / 60 / 1000.0f);// 分钟前
            if (minute > 0) {
                sb += minute + "分钟前";
                time = (long) (time - minute * (60 * 1000.0f));
            }
/*		    long mill = (long) Math.floor (time /1000);//秒前
            if (mill > 0) {
		    	sb+=mill + "秒前";
		    }*/
        }
        return sb;
    }

    /**
     * getAddTimeSS 加减秒数
     * 传入时间和秒数（+为增加时间,-为减少时间）
     *
     * @param timeStr(时间戳)
     * @param ss
     * @return
     */
    public String getAddTimeSS(String timeStr, int ss) {
        int _timeStr = Integer.parseInt(timeStr);
        _timeStr = _timeStr + ss;
        timeStr = String.valueOf(_timeStr);
        return timeStr;
    }

    /**
     * getAddTimeHH 加减小时
     * 传入时间和小时（+为增加时间,-为减少时间）
     *
     * @param timeStr(时间戳)
     * @param hh
     * @return
     */
    public String getAddTimeHH(String timeStr, int hh) {
        int ss = hh * 60 * 60;
        int _timeStr = Integer.parseInt(timeStr);
        _timeStr = _timeStr + ss;
        timeStr = String.valueOf(_timeStr);
        return timeStr;
    }

    /**
     * getAddTimeDD 加减天数
     * 传入时间和天数（+为增加时间,-为减少时间）
     *
     * @param timeStr(时间戳)
     * @param dd
     * @return
     */
    public String getAddTimeDD(String timeStr, int dd) {
        int ss = dd * 60 *60 *24 ;
        int _timeStr = Integer.parseInt(timeStr);
        _timeStr = _timeStr + ss;
        timeStr = String.valueOf(_timeStr);
        return timeStr;
    }

    /**
     * 比较开始时间是否大于结束时间
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public boolean timeCompare(String startTime, String endTime) {
        try {
            if (Integer.valueOf(startTime) > Integer.valueOf(endTime)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 日期格式字符串转换成时间戳
     *
     * @param date_str 字符串日期
     * @param format   如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public String date2TimeStamp(String date_str, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(date_str).getTime() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /*
    得到当天0点时间戳
     */
    public String getDayBeginStr() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 001);
        return String.valueOf(cal.getTimeInMillis()).substring(0, 10);
    }

    // 获得当天0点时间（方法内部使用）
    public static Date getTimesmorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    // 获得昨天0点时间
    public static Date getYesterdaymorning() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(getTimesmorning().getTime() - 3600 * 24 * 1000);
        return cal.getTime();
    }
    /*
    得到当天0点时间戳
     */
    public static String getDayNowStr() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 001);
        return String.valueOf(cal.getTimeInMillis()).substring(0, 10);
    }
    // 获得当天近7天时间
    public static Date getWeekFromNow() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(getTimesmorning().getTime() - 3600 * 24 * 1000 * 7);
        return cal.getTime();
    }

    // 获得当天24点时间
    public static Date getTimesnight() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 24);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    // 获得本周一0点时间
    public static Date getTimesWeekmorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return cal.getTime();
    }

    // 获得本周日24点时间
    public static Date getTimesWeeknight() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getTimesWeekmorning());
        cal.add(Calendar.DAY_OF_WEEK, 7);
        return cal.getTime();
    }

    // 获得本月第一天0点时间
    public static Date getTimesMonthmorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    // 获得本月最后一天24点时间
    public static Date getTimesMonthnight() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 24);
        return cal.getTime();
    }

    /**
     * 得当月第一天的数据
     *
     * @return
     */
    public static String getFirstDayToMonth() {
        Calendar cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        return String.valueOf(cale.getTimeInMillis()).substring(0, 10);
    }

    /**
     * 得当月最后一天的数据
     *
     * @return
     */
    public static String getLastDayToMonth() {
        Calendar cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 1);
        cale.set(Calendar.DAY_OF_MONTH, 0);
        return String.valueOf(cale.getTimeInMillis()).substring(0, 10);
    }
    public static Date getLastMonthStartMorning() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getTimesMonthmorning());
        cal.add(Calendar.MONTH, -1);
        return cal.getTime();
    }

    public static Date getCurrentQuarterStartTime() {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
        Date now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 3)
                c.set(Calendar.MONTH, 0);
            else if (currentMonth >= 4 && currentMonth <= 6)
                c.set(Calendar.MONTH, 3);
            else if (currentMonth >= 7 && currentMonth <= 9)
                c.set(Calendar.MONTH, 4);
            else if (currentMonth >= 10 && currentMonth <= 12)
                c.set(Calendar.MONTH, 9);
            c.set(Calendar.DATE, 1);
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 当前季度的结束时间，即2012-03-31 23:59:59
     *
     * @return
     */
    public static Date getCurrentQuarterEndTime() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getCurrentQuarterStartTime());
        cal.add(Calendar.MONTH, 3);
        return cal.getTime();
    }


    public static Date getCurrentYearStartTime() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.YEAR));
        return cal.getTime();
    }

    public static Date getCurrentYearEndTime() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getCurrentYearStartTime());
        cal.add(Calendar.YEAR, 1);
        return cal.getTime();
    }

    public static Date getLastYearStartTime() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getCurrentYearStartTime());
        cal.add(Calendar.YEAR, -1);
        return cal.getTime();
    }


//    public Timestamp getDayBeginTimestamp() {
//        Date date = new Date();
//        GregorianCalendar gc = new GregorianCalendar();
//        gc.setTime(date);
//        Date date2 = new Date(date.getTime() - gc.get(gc.HOUR_OF_DAY) * 60 * 60
//                * 1000 - gc.get(gc.MINUTE) * 60 * 1000 - gc.get(gc.SECOND)
//                * 1000);
//        return new Timestamp(date2.getTime());
//    }
}
