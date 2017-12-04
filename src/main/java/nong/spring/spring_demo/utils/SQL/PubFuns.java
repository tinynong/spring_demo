package nong.spring.spring_demo.utils.SQL;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by linzhurui on 2017/7/15.
 * 说明：
 * 常用的方法 函数类
 */
public class PubFuns {
    //判断是否为INT型
    public static boolean checkInt(String _int) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(_int).matches();
    }

    //获取GUID，支持可替换功能
    //oldStr 为空时替换，有值则替换成 newStr
    public String gGuid(String oldStr, String newStr) {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll(oldStr, newStr);
    }

    //获取随机数
    //iMin >=最小值 <=最大值
    public static int gRandom(int iMin, int iMax) {
        Random ran = new Random();
        int iRan = ran.nextInt(iMax + 1);
        return iRan >= iMin ? iRan : gRandom(iMin, iMax);
    }

    //获取随机数(指定长度) 如果长时需用字符串返回
    //iLen 随机数的长度
    public static String gRandomLenStr(int iLen) {
        String grlStr = "";
        for (int grlI = 0; grlI < iLen; grlI++) {
            if (grlI == 0)
                grlStr += gRandom(1, 9);
            else
                grlStr += gRandom(0, 9);
        }
        return grlStr;
    }

    public static String gRandomTimeLen(int iLen) {
        //输入>=10 否则返回空
        //例子：输入16返回 ：10 time int + 6 int
        if (iLen < 10) {
            return "";
        } else {
            PubTimes pubTimes = new PubTimes();
            String newStr = pubTimes.getNow2Str();
            String grlStr = "";
            iLen = iLen - 10;
            for (int grlI = 0; grlI < iLen; grlI++) {
                if (grlI == 0)
                    grlStr += gRandom(1, 9);
                else
                    grlStr += gRandom(0, 9);
            }
            grlStr = newStr + grlStr;
            return grlStr;
        }

    }

    //获取随机数(指定长度)
    //iLen 随机数的长度
    public static int gRandomLen(int iLen) {
        String grlStr = "";
        for (int grlI = 0; grlI < iLen; grlI++) {
            if (grlI == 0)
                grlStr += gRandom(1, 9);
            else
                grlStr += gRandom(0, 9);
        }
        return Integer.parseInt(grlStr);
    }

    //获取随机数(指定长度) 如果长时需用字符串返回
    //iLen 随机数的长度
    public static String gKeyStr(int iLen) {
        String key = "ABCDEFGHIJKLMNOPQRSTUVWZYXabcdefghijklmnopqrstuvwxyz0123456789";
        String grlStr = "";
        String keyA = "";
        int RanI;
        for (int grlI = 0; grlI < iLen; grlI++) {
            RanI = gRandom(1, 61);
            keyA = key.substring(RanI, RanI + 1);
            grlStr += keyA;
        }
        return grlStr;
    }

    /*
     * 获取数组位置
     */
    public static int getArrIndex(String[] _arr, String _str) {
        int _r = -1;
        for (int _i = 0; _i < _arr.length; _i++) {
            if (_arr[_i] == _str) {
                _r = _i;
                break;
            }
        }
        return _r;
    }

    /*
     * 获取数组位置
     */
    public static int getArr2Index(String[][] _arr, String _str) {
        int _r = -1;
        _str = _str.toLowerCase();
        for (int _i = 0; _i < _arr.length - 1; _i++) {
            //System.out.println("\r\n【getArr2Index】【"+_i+"】"+_arr[0][_i]+"|"+_str);
            if (_i > 90) {
                break;
            }
            if (_arr[0][_i] != null && _arr[0][_i].toLowerCase().equals(_str)) {
                _r = _i;
                return _r;
            }
        }
        return _r;
    }

    /**
     * @return
     */
    public static String gMarkStr(String sStr, String sMarkParam, String sMarkEnd) {
        String _vStr = sStr;
        int _vP;
        _vP = _vStr.indexOf(sMarkParam);
        if (_vP > -1) {
            _vStr = _vStr.substring(_vP, _vStr.length());
            int _vP1;
            _vP1 = _vStr.indexOf(sMarkEnd);
            if (_vP1 < 0) {
                _vStr = _vStr.substring(sMarkParam.length(), _vStr.length());
            } else {
                _vStr = _vStr.substring(sMarkParam.length(), _vP1);
            }
            return _vStr;
        } else {
            return "";
        }
    }

    /**
     * 根据ip地址计算出long型的数据
     *
     * @param strIP
     * @return
     */
    public long ip2Long(String strIP) {
        long[] ip = new long[4];
        //先找到IP地址字符串中.的位置
        int position1 = strIP.indexOf(".");
        int position2 = strIP.indexOf(".", position1 + 1);
        int position3 = strIP.indexOf(".", position2 + 1);
        //将每个.之间的字符串转换成整型
        ip[0] = Long.parseLong(strIP.substring(0, position1));
        ip[1] = Long.parseLong(strIP.substring(position1 + 1, position2));
        ip[2] = Long.parseLong(strIP.substring(position2 + 1, position3));
        ip[3] = Long.parseLong(strIP.substring(position3 + 1));
        return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
    }

    //将10进制整数形式转换成127.0.0.1形式的IP地址
    public String long2IP(long longIP) {
        StringBuffer sb = new StringBuffer("");
        //直接右移24位
        sb.append(String.valueOf(longIP >>> 24));
        sb.append(".");
        //将高8位置0，然后右移16位
        sb.append(String.valueOf((longIP & 0x00FFFFFF) >>> 16));
        sb.append(".");
        sb.append(String.valueOf((longIP & 0x0000FFFF) >>> 8));
        sb.append(".");
        sb.append(String.valueOf(longIP & 0x000000FF));
        return sb.toString();
    }

    //URL码转字符
    @SuppressWarnings("deprecation")
    public static String gUrlEncoder(String _sStr) {
        return URLEncoder.encode(_sStr);
    }

    //URL码转字符
    @SuppressWarnings("deprecation")
    public static String gUrlDecoder(String _sStr) {
        //return java.net.URLDecoder.decode(_sStr);
        return URLDecoder.decode(_sStr);
    }

    public static String convert(String utfString) {
        StringBuilder sb = new StringBuilder();
        int i = -1;
        int pos = 0;
        //utfString=utfString.replace("\"", "");
        //utfString=utfString.replace("\\\\\\", "");
        if (utfString.indexOf("\\u") < 0) {
            return utfString;
        } else {
            while ((i = utfString.indexOf("\\u", pos)) != -1) {
                sb.append(utfString.substring(pos, i));
                if (i + 5 < utfString.length()) {
                    pos = i + 6;
                    sb.append((char) Integer.parseInt(utfString.substring(i + 2, i + 6), 16));
                }
            }
            return sb.toString();
        }
    }

    /**
     * 判断该字符是否为空或长度为0
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if (str != null && str.length() > 0)
            return false;
        return true;
    }
//
//    /**
//     * 将base64 字符存入本地
//     *
//     * @param imgStr base64
//     * @param format 图片格式  PNG、GIF、JPG
//     * @return 生成后的路径 ，否则返回空字符
//     */
//    public static String GenerateImage(String imgStr, String format) {   //对字节数组字符串进行Base64解码并生成图片
//        if (PubFuns.isEmpty(imgStr)) //图像数据为空
//            return jMap.validate("-1", "图像数据为空");
//        if (!imgStr.startsWith("data:image"))
//            return jMap.validate("-1", "图像格式不对》" + imgStr);
//        format = isEmpty(format) ? ".jpg" : format;
////        imgStr = imgStr.replace("data:image/jpeg;base64,", "");
//        imgStr = imgStr.substring(imgStr.indexOf(",") + 1, imgStr.length());
//        BASE64Decoder decoder = new BASE64Decoder();
//        try {
//            //Base64解码
//            byte[] b = decoder.decodeBuffer(imgStr);
//            for (int i = 0; i < b.length; ++i) {
//                if (b[i] < 0) {//调整异常数据
//                    b[i] += 256;
//                }
//            }
//            //生成jpeg图片
////            String imgFilePath = "d://222.jpg";//新生成的图片
//            // /home/tomcat/webapps/RYIOTImgs
//            String path = ValIniParams.getPicturePath();
//            File file = new File(path);
//            if (!file.exists())
//                file.mkdir();
//            String filename = System.currentTimeMillis() + format;
//            String imgFilePath = file.getAbsolutePath() + "/" + filename;//新生成的图片
//            OutputStream out = new FileOutputStream(imgFilePath);
//            out.write(b);
//            out.flush();
//            out.close();
////            return jMap.validate("2", imgFilePath.substring(3).replace("\\", "/"));
//            JSONObject object = new JSONObject();
//            object.put("imgpath", "kjjimgs/" + filename);
//            return jOuter.outDataBuildToObject("2", "上传成功", object.toString(), "");
//        } catch (Exception e) {
//            e.printStackTrace();
//            return jMap.validate("-1", "图像生成失败" + e.getMessage());
//        }
//    }


    /**
     * 大陆号码或香港号码均可
     */
    public static boolean isPhoneLegal(String str) throws PatternSyntaxException {
        return isChinaPhoneLegal(str) || isHKPhoneLegal(str);
    }

    /**
     * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数
     * 此方法中前三位格式有：
     * 13+任意数
     * 15+除4的任意数
     * 18+除1和4的任意数
     * 17+除9的任意数
     * 147
     */
    public static final String REGEX_MOBILE_EXACT = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|(147))\\d{8}$";

    public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
//        String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
        if (str == null)
            return false;
        Pattern p = Pattern.compile(REGEX_MOBILE_EXACT);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 香港手机号码8位数，5|6|8|9开头+7位任意数
     */
    public static boolean isHKPhoneLegal(String str) throws PatternSyntaxException {
        String regExp = "^(5|6|8|9)\\d{7}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }


    /*方法二：推荐，速度最快
  * 判断是否为整数
  * @param str 传入的字符串
  * @return 是整数返回true,否则返回false
*/

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断开始时间是大于结束时间
     *
     * @param start_time
     * @param end_time
     * @return
     */
    public static String timeContrast(String start_time, String end_time) {
        if (!PubFuns.isEmpty(start_time) && !PubFuns.isEmpty(end_time)) {
            try {
                if (Integer.valueOf(start_time) > Integer.valueOf(end_time)) return jMap.validate("-1", "开始时间大于结束时间");
                else
                    return jMap.validate("2", "成功");
            } catch (Exception e) {
                e.printStackTrace();
                return jMap.validate("-1", "时间格式错误" + e.getMessage());
            }
//            return jMap.validate("-1", "时间格式错误");
        }
        return jMap.validate("4", "未传");
    }

    /**
     * urlCode 解码
     *
     * @param instr
     * @return
     */
    public static String decode(String instr) {
        try {
            if (instr.equals(""))
                instr = "[]";
            String unDecoder = URLDecoder.decode(instr, "utf-8");
            return unDecoder;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return instr;
    }

    /**
     * 编码
     *
     * @param instr
     * @return
     */
    public static String encode(String instr) {
        try {
            String out = URLEncoder.encode(instr, "utf-8");//编码
            return out;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return instr;
    }

    public static String string2Unicode(String string) {
        StringBuffer unicode = new StringBuffer();
        for (int i = 0; i < string.length(); i++) {
            // 取出每一个字符
            char c = string.charAt(i);
            // 转换为unicode
            unicode.append("\\u" + Integer.toHexString(c));
        }
        return unicode.toString();
    }

    /**
     * 去除字符串的空格回车换行
     * @param str
     * @return
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }
}
