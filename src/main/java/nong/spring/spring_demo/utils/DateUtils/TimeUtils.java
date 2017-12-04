package nong.spring.spring_demo.utils.DateUtils;

/**
 * @Author TinyNong
 * @emaile 2128076626@qq.com
 * @Date 2017/11/25
 * @Class
 */
public class TimeUtils {

    //获取当前 时间戳
    public static String getNow2Str() {
        //转成mysql标准长度
        String _str = Long.toString(System.currentTimeMillis());
        _str = _str.substring(0, 10);
        return _str;
    }
}
