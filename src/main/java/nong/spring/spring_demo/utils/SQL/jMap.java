package nong.spring.spring_demo.utils.SQL;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import nong.spring.spring_demo.utils.jLogs.jLogs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/26.
 */
public class jMap {

    public static void main(String[] args) {
        new jMap().test();

        HashMap<String, String> map = new HashMap<>();
        map.put("a", "ssss");
        map.put("b", "ssaaadsdsds");

        Map<String, String> map2 = (Map<String, String>) map.clone();
        map2.put("aadd", "sadadasvcxvxcv");

        System.out.println(mapToJson(map).toString());
        System.out.println(map2.toString());


    }

    public void test() {
//        Map<String, String> map = new HashMap<>();
//        map.put("a", "aaa");
//        map.put("ASDS", "aaa");
//        map.put("a", "aaa");
//        map.put("A", "aaaASDASD");
//        jLogs.wLogs(">>>>>>>>" + keyInMap(map, "a"), 9);
//        jLogs.wLogs(">>>>>>>>" + getMapValue(map, "A"), 9);
    }

    /**
     * 判断该字符是否为空或长度为0
     *
     * @param str
     * @return
     */

    public static boolean isEmpty(String str) {
        return (str != null && str.length() > 0) ? false : true;
    }

    /**
     * 判断 key是否存在 大小写通用
     *
     * @param map
     * @param key
     * @return
     */
    public static boolean keyInMap(Map<String, String> map, String key) {
        return (!isEmpty(key) && map != null) ? map.containsKey(key.trim()) : false;

    }

    /**
     * 根据值获取key 大小写通用
     *
     * @param map
     * @param key
     * @return
     */
    public static String getMapValue(Map<String, String> map, String key, String mapValue) {
        return keyInMap(map, key) ? map.get(key) : mapValue;
    }

    /***
     *  MAP 转json
     * @param map
     * @return
     */
    public static JSONObject mapToJson(HashMap<String, String> map) {
        JSONObject object = new JSONObject();
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                object.put(entry.getKey(), entry.getValue());
            }
        }
        return object;
    }

    /**
     * json 对象为单层
     *
     * @param jsonObject
     * @return
     */
    public static Map<String, String> jsonToMap(JSONObject jsonObject) {
        Map<String, String> map = new HashMap<>();
        if (jsonObject != null) {
            Iterator iter = jsonObject.keys();
            while (iter != null && iter.hasNext()) {
                String key = (String) iter.next();
                map.put(key, jsonObject.optString(key));
            }
        }
        return map;
    }

    /**
     * json 对象为单层
     *
     * @param jsonObject
     * @return
     */
    public static Map<String, List<String>> jsonToMapList(JSONObject jsonObject) {
        Map<String, List<String>> map = new HashMap<>();
        if (jsonObject != null) {
            Iterator iter = jsonObject.keys();
            while (iter != null && iter.hasNext()) {
                String key = (String) iter.next();
                map.put(key, JSONArray.toList(jsonObject.optJSONArray(key), String.class));
            }
        }
        return map;
    }

    public static String validate(Map<String, String> params) {
        if (params == null || params.size() == 0)
            return validate("4", "无参数");
        for (Map.Entry<String, String> entry : params.entrySet()) {
            {
                if (PubFuns.isEmpty(entry.getValue()))
                    return validate("-1", String.format("参数 %s 值为空", entry.getKey()));
            }
        }
        return validate("2", "验证正确");
    }

    /**
     * 判断数据是否为空
     *
     * @param params
     * @return
     */
    public static String validate1(Map<String, String> params) {
        if (params == null || params.size() == 0)
            return "未上传参数或参数名错误";
        for (Map.Entry<String, String> entry : params.entrySet()) {
            {
                if (PubFuns.isEmpty(entry.getValue().trim()))
                    return String.format("参数 %s 值为空", entry.getKey());
            }
        }
        return "";
    }

    public static String validate(String rCode, String rMsg) {
        String rJsonStr = "";
        rJsonStr = jJson.set1KeyValueStr(rJsonStr, "rCode", rCode);
        rJsonStr = jJson.set1KeyValueStr(rJsonStr, "rMsg", rMsg);
        return rJsonStr;
    }

    /**
     * 验证map 的key 是否存在于字段中
     *
     * @param Fields
     * @param
     * @return
     */
    public static String validMapKey(String Fields, Iterator iter) {
//        Iterator<String> iter = inMap.keySet().iterator();
        while (iter.hasNext()) {
            if (!jJson.keyInJson(Fields, iter.next().toString()))
                return validate("-1", iter.next() + "字段不存在");
        }

        return validate("2", "验证成功");
    }

    /**
     * 暂时这么写吧
     *
     * @param resultSet
     * @param params
     * @return
     */
    public static JSONArray RetToJsonArray(ResultSet resultSet, String... params) {
        JSONArray array = new JSONArray();
        try {
            while (resultSet != null && resultSet.next()) {
                JSONObject object = new JSONObject();
                for (String param : params) {
                    object.put(param.trim(), jMysql.getRsValue(resultSet, param, ""));
                }
                //特别针对地区
                if (object.containsKey("area_town") && object.containsKey("area_village")) {
                    if (PubFuns.isEmpty(object.optString("area_town"))) {
                        object.put("area_town", object.optString("area_village"));
                        object.put("area_village", "");
                    }
                    object.put("area_name", object.optString("area_town") + object.optString("area_village"));
                }
                if (object.containsKey("imgs")) {
                    object.put("imgs", PubFuns.decode(object.optString("imgs")));
                }
                if (object.containsKey("comment_content")) {
                    object.put("comment_content", PubFuns.decode(object.optString("comment_content")));
                }
                if (object.containsKey("user_head_url")) {
                    object.put("user_head_url", PubFuns.decode(object.optString("user_head_url")));
                }
                if (object.containsKey("ad_img_url")) {
                    object.put("ad_img_url", PubFuns.decode(object.optString("ad_img_url")));
                }
                if (object.containsKey("ad_url")) {
                    object.put("ad_url", PubFuns.decode(object.optString("ad_url")));
                }
                array.add(object);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            jLogs.wLogs(e.getMessage(), 3);
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
                resultSet = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return array;
    }

    /**
     * @param
     * @return
     */
    public static String validValue(String[] names, String... values) {
        for (int i = 0; i < values.length; i++) {
            if (values[i].equals(""))
                return validate("-1", names[i] + "值为空");

        }
        return validate("2", "验证成功");
    }

}

