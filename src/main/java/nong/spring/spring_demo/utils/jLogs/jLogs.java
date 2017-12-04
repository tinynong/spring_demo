package nong.spring.spring_demo.utils.jLogs;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日志工作者
 */
public class jLogs {
    private static int loglevel = 9;//日志等级
    private static String logPath = "";//日志路径
    private static boolean isFail = false;//是否发布

    private static String thisLogFileName = "%slog%s.log";//本日志文件名
    private static String thisErrorLogFileName = "%serr%s.log";//本日志文件名

    public static boolean createLogWorker(String _logPath, boolean _isFail) {
        //创建日志工作者
        logPath = _logPath;//日志路径
        isFail = _isFail;//是否发布
        if (!createLogPath()) return false;//创建日志目录
        return true;
    }

    public static void wLogs(String sLog, int iLevel) {
        //写入日志格式1[HHmmss:> 日志内容]
        if (iLevel > loglevel) return;
        String sLogStr = String.format("[%s]%s:> %s", getFrom(),getHHmmss(), sLog);
        write2LogFile(sLogStr, false);//写入到日志文件中
        if (!isFail) System.out.println(String.format("[%d]=>%s", iLevel, sLogStr));//是否发布
    }

    public static void wLogs02(String sKey, String sLog, int iLevel) {
        //写入日志格式2[sKey-->HHmmss:> 日志内容]
        if (iLevel > loglevel) return;
        String sLogStr = String.format("[%s]%s-->%s:> %s",getFrom(), sKey, getHHmmss(), sLog);//写入日志
        write2LogFile(sLogStr, false);//写入到日志文件中
        if (!isFail) System.out.println(String.format("[%d]=>%s",iLevel,sLogStr));//是否发布
    }

    public static void wErrLogs(String sLog) {
        //写入错误日志
        String sLogStr = String.format("[%s]=>[%s]%s:> %s", "wErrLogs",getFrom(), getHHmmss(), sLog); //写入日志
        write2LogFile(sLogStr, true);//写入到日志文件中
        if (!isFail) System.out.println(String.format("%s", sLogStr));//是否发布
    }
    public static void wErrLogs(Exception ex) {
        //写入错误日志
            String errStr="";
            for (StackTraceElement ste : ex.getStackTrace()) {
                errStr=errStr+ste.toString()+"\n";
            }
        String sLogStr = String.format("[%s]=>[%s]%s:> %s", "wErrLogs",getFrom(), getHHmmss(), errStr); //写入日志
        write2LogFile(sLogStr, true);//写入到日志文件中
        if (!isFail) System.out.println(String.format("%s", sLogStr));//是否发布
    }

    private static String getFrom(){
        //获取上级目录和调用日志的方法
        String className = Thread.currentThread().getStackTrace()[3].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[3].getMethodName();
        //切割字符串（点必须要转义）
        String[] classNames= className.split("\\.");
        if(classNames[classNames.length-1].equals("jMysql")||classNames[classNames.length-1].equals("PubHttpRequest")){
            className = Thread.currentThread().getStackTrace()[4].getClassName();
            methodName= Thread.currentThread().getStackTrace()[4].getMethodName();
            String[] classNamejm= className.split("\\.");
            if(classNamejm.length>2){
                className=classNamejm[classNamejm.length-2]+"."+classNamejm[classNamejm.length-1];
            }
        }else{
            if(classNames.length>2){
                className=classNames[classNames.length-2]+"."+classNames[classNames.length-1];
            }
        }
        return className+"("+methodName+")";
    }
    private static boolean write2LogFile(String sLogStr, boolean isErr) {
        //写入错误信息到日志文件中，正常日志与错误日志 共用
        //注：不带格式，格式在public 中 规范
        try {
            String nowDate = getYyyymmdd();//现在日期
            String logFileName = isErr ?
                    String.format(thisErrorLogFileName, logPath, nowDate)//错误日志文件
                    : String.format(thisLogFileName, logPath, nowDate);//创建日志文件
            FileWriter thisWriter = new FileWriter(logFileName, true);
            thisWriter.write("\r\n" + sLogStr);
            thisWriter.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean createLogPath() {
        //创建日志目录
        File filePath = new File(logPath);
        //判断文件夹是否存在 ，不存在则新建
        if (filePath.exists()) {
            return true;
        } else {
            boolean createTrue = filePath.mkdirs();
            if (createTrue) {
                System.out.println(String.format("日志文件夹[ %s ]已创建...", logPath));
                return true;
            } else {
                System.out.println(String.format("日志文件夹[ %s ]创建失败，请检查相应参数...", logPath));
                System.exit(0);//创建失败,强制退出
                return false;
            }
        }
    }

    private static String getYyyymmdd() {
        //获取 yyyyMMdd 日期格式
        SimpleDateFormat dfd = new SimpleDateFormat("yyyyMMdd");
        return dfd.format(new Date());
    }

    private static String getHHmmss() {
        //获取 HHmmss 时间格式
        SimpleDateFormat dfd = new SimpleDateFormat("HHmmss");
        return dfd.format(new Date());
    }



    public static int getLoglevel() {
        return loglevel;
    }

    public static void setLoglevel(int loglevel) {
        wLogs(String.format("日志输出级别，已调为：%s级", String.valueOf(loglevel)), 0);
        jLogs.loglevel = loglevel;
    }

}
