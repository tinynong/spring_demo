package nong.spring.spring_demo.utils.SQL;


import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import nong.spring.spring_demo.utils.jLogs.jLogs;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

/**
 * Created by linzhurui on 2017/7/15.
 * JSON格式说明：
 * {"key":"value"} //JSONObject(对象)  【本Class使用该格式】【推荐用该格式】
 * {"key"=>"value"} //JSONObject(对象)  【本Class使用该格式】
 * [{"key1":"value1"}, {"key2":"value2"}]//JSONArray(数组)   【XXXXX 本Class禁用该格式】
 * JSONObject可以用key取值，JSONArray只能遍历取值
 */
public class jJson {


    public static void doJsonDemo() {
        /**
         * 本方法类 DEMO 示例
         */

        //=====================================
        //StrJSon 格式
        //jsonStr与 jsonObj互转
        System.out.println("=====================【jsonStr与 jsonObj互转】");
        String jsonStr = "";
        JSONObject jsonObj = null;

        jsonStr = " {'id':11,'name':'root'}";//【采用该格式】
        jsonObj = jsonStr2Obj(jsonStr);//JSON字符串转OBJ 如果[] 则 返回null
        System.out.println("jsonStr:" + jsonObj2Str(jsonObj)); //OBJ 转 JSON 字符串

        jsonStr = " {\"id\":'11',\"name\":'root'}        ";//【采用该格式】
        jsonObj = jsonStr2Obj(jsonStr);//JSON字符串转OBJ 如果[] 则 返回null
        System.out.println("jsonStr:" + jsonObj2Str(jsonObj)); //OBJ 转 JSON 字符串

        jsonStr = "       ";//【空JSON时】
        jsonObj = jsonStr2Obj(jsonStr);//JSON字符串转OBJ 如果[] 则 返回null
        System.out.println("jsonStr:" + jsonObj2Str(jsonObj)); //OBJ 转 JSON 字符串

        jsonStr = "{'id'=>11,'name'=>'root'}       ";// =>  格式
        jsonObj = jsonStr2Obj(jsonStr);//JSON字符串转OBJ 如果[] 则 返回null
        System.out.println("jsonStr:" + jsonObj2Str(jsonObj)); //OBJ 转 JSON 字符串

        // 复杂多层格式【1】
        jsonStr = "{\"aaa\":\"111\",\"bbb\":\"222\",\"ccc\":\"333\",\"ddd\":\"444\",\"eee\":\"555\",\"ccc_c01Arr\":{\"ccc_c01\":\"111_01\",\"ccc_c02\":\"111_02\",\"ccc_c03\":\"111_03\",\"ccc_c04\":\"111_04\"},\"ccc_c02Arr\":{\"ccc_c01\":\"111_01\",\"ccc_c02\":\"111_02\",\"ccc_c03\":\"111_03\",\"ccc_c04\":\"111_04\"}}";
        jsonObj = jsonStr2Obj(jsonStr);//JSON字符串转OBJ 如果[] 则 返回null
        System.out.println("jsonStr:" + jsonObj2Str(jsonObj)); //OBJ 转 JSON 字符串

        // 复杂多层格式【2】
        jsonStr = "{\"aaa\":\"111\",\"bbb\":\"222\",\"ccc\":\"333\",\"ddd\":\"444\",\"eee\":\"555\",\"ccc_c01Arr\":{\"ccc_c01\":\"111_01\",\"ccc_c02\":\"111_02\",\"ccc_c03\":\"111_03\",\"ccc_c04\":\"111_04\"},\"ccc_c02Arr\":{\"ccc_c01\":\"111_01\",\"ccc_c02\":\"111_02\",\"ccc_c03\":\"111_03\",\"ccc_c04\":\"111_04\",\"0\":{\"ccc_c02x_1\":\"c02x_01\",\"ccc_c02x_2\":\"c02x_02\",\"ccc_c02x_3\":\"c02x_03\",\"ccc_c02x_4\":\"c02x_04\"},\"1\":{\"ccc_c02x_1\":\"c02x_01\",\"ccc_c02x_2\":\"c02x_02\",\"ccc_c02x_3\":\"c02x_03\",\"ccc_c02x_4\":\"c02x_04\"},\"2\":{\"ccc_c02x_1\":\"c02x_01\",\"ccc_c02x_2\":\"c02x_02\",\"ccc_c02x_3\":\"c02x_03\",\"ccc_c02x_4\":\"c02x_04\"},\"3\":{\"ccc_c02x_1\":\"c02x_01\",\"ccc_c02x_2\":\"c02x_02\",\"ccc_c02x_3\":\"c02x_03\",\"ccc_c02x_4\":\"c02x_04\"},\"4\":{\"ccc_c02x_1\":\"c02x_01\",\"ccc_c02x_2\":\"c02x_02\",\"ccc_c02x_3\":\"c02x_03\",\"ccc_c02x_4\":\"c02x_04\"},\"5\":{\"ccc_c02x_1\":\"c02x_01\",\"ccc_c02x_2\":\"c02x_02\",\"ccc_c02x_3\":\"c02x_03\",\"ccc_c02x_4\":\"c02x_04\"},\"6\":{\"ccc_c02x_1\":\"c02x_01\",\"ccc_c02x_2\":\"c02x_02\",\"ccc_c02x_3\":\"c02x_03\",\"ccc_c02x_4\":\"c02x_04\"},\"7\":{\"ccc_c02x_1\":\"c02x_01\",\"ccc_c02x_2\":\"c02x_02\",\"ccc_c02x_3\":\"c02x_03\",\"ccc_c02x_4\":\"c02x_04\"},\"8\":{\"ccc_c02x_1\":\"c02x_01\",\"ccc_c02x_2\":\"c02x_02\",\"ccc_c02x_3\":\"c02x_03\",\"ccc_c02x_4\":\"c02x_04\"},\"9\":{\"ccc_c02x_1\":\"c02x_01\",\"ccc_c02x_2\":\"c02x_02\",\"ccc_c02x_3\":\"c02x_03\",\"ccc_c02x_4\":\"c02x_04\"}}}";
        jsonObj = jsonStr2Obj(jsonStr);//JSON字符串转OBJ 如果[] 则 返回null
        System.out.println("jsonStr:" + jsonObj2Str(jsonObj)); //OBJ 转 JSON 字符串

        //=====================================
        //检索关键 key 是否存在
        System.out.println("=====================【检索关键 key 是否存在】");
        String key = "";
        boolean inB = false;
        jsonStr = "{\"aaa\":\"111\",\"bbb\":\"222\",\"ccc\":\"333\",\"ddd\":\"444\",\"eee\":\"555\",\"ccc_c01Arr\":{\"ccc_c01\":\"111_01\",\"ccc_c02\":\"111_02\",\"ccc_c03\":\"111_03\",\"ccc_c04\":\"111_04\"},\"ccc_c02Arr\":{\"ccc_c01\":\"111_01\",\"ccc_c02\":\"111_02\",\"ccc_c03\":\"111_03\",\"ccc_c04\":\"111_04\"}}";
        jsonObj = jsonStr2Obj(jsonStr);
        key = "bBb";
        inB = keyInJson(jsonObj, key); //检索 jsonKey 是否在 1级 数据Key对象中
        System.out.println(inB ? key + "==============in json key:" : key + " not in json key");
        key = "bBb";
        inB = keyInJson(jsonStr, key); //检索 jsonKey 是否在 1级 数据Key对象中
        System.out.println(inB ? key + "==============in json key:" : key + " not in json key");

        key = "ccc_c02";
        inB = keyInJson(jsonObj, key); //检索 jsonKey 是否在 1级 数据Key对象中
        System.out.println(inB ? key + "==============in json key:" : key + " not in json key");
        key = "CCC_c02";
        inB = keyInJson(jsonStr, key); //检索 jsonKey 是否在 1级 数据Key对象中
        System.out.println(inB ? key + "==============in json key:" : key + " not in json key");

        //=====================================
        //获取【1级 Key】值
        System.out.println("=====================【获取【1级 Key】值】");
        jsonStr = "{\"aaa\":\"111\",\"bbb\":\"222\",\"ccc\":\"333\",\"ddd\":\"444\",\"eee\":\"555\",\"ccc_c01Arr\":{\"ccc_c01\":\"111_01\",\"ccc_c02\":\"111_02\",\"ccc_c03\":\"111_03\",\"ccc_c04\":\"111_04\"},\"ccc_c02Arr\":{\"ccc_c01\":\"111_01\",\"ccc_c02\":\"111_02\",\"ccc_c03\":\"111_03\",\"ccc_c04\":\"111_04\"}}";
        String value = "";
        jsonObj = jsonStr2Obj(jsonStr);
        key = "bBb";
        value = get1KeyValue(jsonObj, key, "999");//获取1级 JSON 中指定KEY 的值，如果不存在，则返回 jsonValue
        System.out.println("jsonObj:::[" + key + "]=>:" + key + " [value]=>:" + value);

        key = "bBb";
        value = get1KeyValue(jsonStr, key, "999");//获取1级 JSON 中指定KEY 的值，如果不存在，则返回 jsonValue
        System.out.println("jsonStr:::[" + key + "]=>:" + key + " [value]=>:" + value);

        key = "ccc_c01Arr";
        value = get1KeyValue(jsonObj, key, "999");//获取1级 JSON 中指定KEY 的值，如果不存在，则返回 jsonValue
        System.out.println("jsonObj:::[" + key + "]=>:" + key + " [value]=>:" + value);

        key = "CCc_c02";
        value = get1KeyValue(value, key, "999");//获取1级 JSON 中指定KEY 的值，如果不存在，则返回 jsonValue
        System.out.println("jsonStr:::[" + key + "]=>:" + key + " [value]=>:" + value);

        //=====================================
        //获取【2级 Key】值
        System.out.println("=====================【获取【2级 Key】值】");
        jsonStr = "{\"aaa\":\"111\",\"bbb\":\"222\",\"ccc\":\"333\",\"ddd\":\"444\",\"eee\":\"555\",\"ccc_c01Arr\":{\"ccc_c01\":\"111_01\",\"ccc_c02\":\"111_02\",\"ccc_c03\":\"111_03\",\"ccc_c04\":\"111_04\"},\"ccc_c02Arr\":{\"ccc_c01\":\"111_0102Arr\",\"ccc_c02\":\"111_0202Arr\",\"ccc_c03\":\"111_0302Arr\",\"ccc_c04\":\"111_0402Arr\"}}";
        jsonObj = jsonStr2Obj(jsonStr);

        String key2 = "";
        key = "ccc_c01Arr";
        key2 = "ccc_c02";
        value = get2KeyValue(jsonObj, key, key2, "999");//获取2级 JSON 中指定KEY 的值，如果不存在，则返回 jsonValue
        System.out.println("jsonObj:::[" + key + "]=>:" + key + " [value]=>:" + value);

        key = "ccc_c02Arr";
        key2 = "ccc_c02";
        value = get2KeyValue(jsonStr, key, key2, "999");//获取2级 JSON 中指定KEY 的值，如果不存在，则返回 jsonValue
        System.out.println("jsonStr:::[" + key + "]=>:" + key + " [value]=>:" + value);

        //找不到时
        key = "ccc_c02ArrXXXX";
        key2 = "ccc_c02";
        value = get2KeyValue(jsonStr, key, key2, "999");//获取2级 JSON 中指定KEY 的值，如果不存在，则返回 jsonValue
        System.out.println("jsonStr:::[" + key + "]=>:" + key + " [value]=>:" + value);


        //=====================================
        //新增与修改 【1级 Key】值 不存在则新增
        System.out.println("=====================【新增与修改 【新增与修改 【1级 Key】值 不存在则新增】");
        jsonStr = " {'id':11,'name':'root'}";//【采用该格式】


        jsonObj = jsonStr2Obj(jsonStr);
        System.out.println("jsonStr:" + jsonObj2Str(jsonObj));

        JSONObject jsonObj2 = null;
        String jsonStr2 = "";

        jsonObj2 = set1KeyValueObj(jsonObj, "tttt", "999");//设置【1级】 JSON 中指定KEY 的值，如果不存在，则新增
        jsonStr2 = jsonObj2Str(jsonObj2);
        System.out.println("jsonStr2:" + jsonObj2Str(jsonObj));

        jsonObj2 = set1KeyValueObj(jsonObj, "tttt222", "222999");//设置【1级】 JSON 中指定KEY 的值，如果不存在，则新增
        jsonStr2 = jsonObj2Str(jsonObj2);
        System.out.println("jsonStr2222:" + jsonObj2Str(jsonObj));


        jsonStr2 = set1KeyValueStr(jsonStr, "yyyy", "y999");//设置【1级】 JSON 中指定KEY 的值，如果不存在，则新增
        System.out.println("jsonStr2yyyy:" + jsonStr2);


        //=====================================
        //新增与修改 【2级 Key】值 不存在则新增
        System.out.println("=====================【新增与修改 【2级 Key】值 不存在则新增】");
        jsonStr = " {'id':11,'name':'root'}";//【采用该格式】
        jsonObj = jsonStr2Obj(jsonStr);

        System.out.println("jsonStr:" + jsonObj2Str(jsonObj));

        jsonObj2 = set2KeyValueObj(jsonObj, "aaa", "aaa22", "aaa2222");  //设置【2级】 JSON 中指定KEY 的值，如果不存在，则新增
        jsonStr2 = jsonObj2Str(jsonObj2);
        System.out.println("jsonObj2Str aaa:" + jsonObj2Str(jsonObj));

        jsonObj2 = set2KeyValueObj(jsonObj2, "aaa", "abb22", "abb2222"); //设置【2级】 JSON 中指定KEY 的值，如果不存在，则新增
        jsonStr2 = jsonObj2Str(jsonObj2);
        System.out.println("jsonObj2Str aaa:" + jsonObj2Str(jsonObj));

        jsonStr2 = set2KeyValueStr(jsonStr, "bbb", "baa22", "baa2222");//设置【2级】 JSON 中指定KEY 的值，如果不存在，则新增
        System.out.println("set2KeyValueStr bbb:" + jsonStr2);
        jsonStr2 = set2KeyValueStr(jsonStr2, "bbb", "bbb22", "bbb2222");//设置【2级】 JSON 中指定KEY 的值，如果不存在，则新增
        System.out.println("set2KeyValueStr bbb:" + jsonStr2);

    }

    public static JSONObject jsonStr2Obj(String jsonStr) {
        //JSON字符串转OBJ 如果[] 则 返回null
        try {
            jsonStr = jsonStr == null ? "" : jsonStr;
            jsonStr = jsonStr.trim();
            return jsonStr.equals("") ? null : JSONObject.fromObject(jsonStr);
        } catch (JSONException e) {
            return null;
        }
    }

    public static String jsonObj2Str(JSONObject jsonObj) {
        //OBJ 转 JSON 字符串
        return jsonObj == null ? "" : jsonObj.toString();
    }

    public static boolean keyInJson(JSONObject jsonObj, String jsonKey) {
        //检索 jsonKey 是否在 1级 数据Key对象中
        // 支持大小写 通用
        if (jsonObj == null)
            return false;
        jsonKey = jsonKey.trim().toLowerCase();
        if (jsonKey.equals("")) return false;
        String thisKey = "";
        Iterator iterator = jsonObj.keys();
        while (iterator.hasNext()) {
            thisKey = (String) iterator.next();
            thisKey = thisKey.trim().toLowerCase();
            if (thisKey.equals(jsonKey)) {
                return true;
            }
        }
        return false;
    }

    public static boolean keyInJson(String jsonStr, String jsonKey) {
        //检索 jsonKey 是否在 1级 数据Key对象中
        // 支持大小写 通用
        JSONObject jsonObj = jsonStr2Obj(jsonStr);
        return keyInJson(jsonObj, jsonKey);
    }

    public static String get1KeyValue(JSONObject jsonObj, String jsonKey, String jsonValue) {
        //获取1级 JSON 中指定KEY 的值，如果不存在，则返回 jsonValue
        // 支持大小写 通用
        if (jsonKey.equals("")) return jsonValue;
        if (!keyInJson(jsonObj, jsonKey)) return jsonValue;

        jsonKey = jsonKey.trim().toLowerCase();
        String thisKey = "";
        String thisKeyLower = "";
        Iterator iterator = jsonObj.keys();
        while (iterator.hasNext()) {
            thisKey = (String) iterator.next();
            thisKeyLower = thisKey.trim().toLowerCase();
            if (thisKeyLower.equals(jsonKey)) {
                String kValue = jsonObj.getString(thisKey);
                return (kValue.equals("") && !jsonValue.equals("")) ? jsonValue : kValue;
            }
        }
        return jsonValue;
    }

    public static String get1KeyValue(String jsonStr, String jsonKey, String jsonValue) {
        //获取1级 JSON 中指定KEY 的值，如果不存在，则返回 jsonValue
        // 支持大小写 通用
        JSONObject jsonObj = jsonStr2Obj(jsonStr);
        return get1KeyValue(jsonObj, jsonKey, jsonValue);
    }

    public static String get2KeyValue(JSONObject jsonObj, String jsonKey1, String jsonKey2, String jsonValue) {
        //获取2级 JSON 中指定KEY 的值，如果不存在，则返回 jsonValue
        // 支持大小写 通用
        if (jsonKey1.equals("")) return "";
        if (jsonKey2.equals("")) return "";
        if (!keyInJson(jsonObj, jsonKey1)) return jsonValue;

        jsonKey1 = jsonKey1.trim().toLowerCase();
        String thisKey = "";
        String thisKeyLower = "";
        Iterator iterator = jsonObj.keys();
        while (iterator.hasNext()) {
            thisKey = (String) iterator.next();
            thisKeyLower = thisKey.trim().toLowerCase();
            if (thisKeyLower.equals(jsonKey1)) {
                if (jsonObj.get(thisKey) instanceof JSONObject) {
                    //必须在二级 才有返回
                    return get1KeyValue(jsonObj.getJSONObject(thisKey), jsonKey2, jsonValue);
                } else {
                    return jsonValue;
                }
            }
        }
        return jsonValue;
    }

    public static String get2KeyValue(String jsonStr, String jsonKey1, String jsonKey2, String jsonValue) {
        //获取2级 JSON 中指定KEY 的值，如果不存在，则返回 jsonValue
        // 支持大小写 通用
        JSONObject jsonObj = jsonStr2Obj(jsonStr);
        return get2KeyValue(jsonObj, jsonKey1, jsonKey2, jsonValue);
    }

    public static JSONObject set1KeyValueObj(JSONObject jsonObj, String jsonKey, String jsonValue) {
        //设置【1级】 JSON 中指定KEY 的值，如果不存在，则新增
        // 支持大小写 判断 通用
        // 支持key 原大小写格式
        if (jsonObj == null) {
            jsonObj = new JSONObject();
            jsonObj.put(jsonKey, jsonValue);
            return jsonObj;
        }
        String jsonKeyLower = jsonKey.trim().toLowerCase();
        String thisKey = "";
        String thisKeyLower = "";
        Iterator iterator = jsonObj.keys();
        while (iterator.hasNext()) {
            thisKey = (String) iterator.next();
            thisKeyLower = thisKey.trim().toLowerCase();
            if (thisKeyLower.equals(jsonKeyLower)) {
                jsonObj.put(thisKey, jsonValue);
                return jsonObj;
            }
        }
        jsonObj.put(jsonKey, jsonValue);
        return jsonObj;
    }

    public static String set1KeyValueStr(String jsonStr, String jsonKey, String jsonValue) {
        //设置【1级】 JSON 中指定KEY 的值，如果不存在，则新增
        // 支持大小写 判断 通用
        // 支持key 原大小写格式
        JSONObject jsonObj01 = jsonStr2Obj(jsonStr);
        return set1KeyValueObj(jsonObj01, jsonKey, jsonValue).toString();
    }

    public static JSONObject set2KeyValueObj(JSONObject jsonObj, String jsonKey1, String jsonKey2, String jsonValue) {
        //设置【2级】 JSON 中指定KEY 的值，如果不存在，则新增
        // 支持大小写 判断 通用
        // 支持key 原大小写格式
        JSONObject jObject02 = null;
        if (jsonObj == null) {
            jsonObj = new JSONObject();
            jObject02 = new JSONObject();
            jObject02.put(jsonKey2, jsonValue);
            jsonObj.put(jsonKey1, jObject02);
            return jsonObj;
        }
        String json1KeyLower = jsonKey1.trim().toLowerCase();
        String thisKey = "";
        String thisKeyLower = "";
        Iterator iterator = jsonObj.keys();
        while (iterator.hasNext()) {
            thisKey = (String) iterator.next();
            thisKeyLower = thisKey.trim().toLowerCase();
            if (thisKeyLower.equals(json1KeyLower)) {
                if (jsonObj.get(thisKey) instanceof JSONObject) {
                    //必须是二级  才操作
                    jObject02 = jsonObj.getJSONObject(thisKey);
                    jObject02 = set1KeyValueObj(jObject02, jsonKey2, jsonValue);
                    jsonObj.put(jsonKey1, jObject02);
                    return jsonObj;
                } else {
                    //如果不是二级 则不操作
                    return jsonObj;
                }
            }
        }
        //不存在 一级，则也是不存在2级，故直接增加两个
        jObject02 = new JSONObject();
        jObject02.put(jsonKey2, jsonValue);
        jsonObj.put(jsonKey1, jObject02);
        return jsonObj;
    }

    public static String set2KeyValueStr(String jsonStr, String jsonKey1, String jsonKey2, String jsonValue) {
        //设置【1级】 JSON 中指定KEY 的值，如果不存在，则新增
        // 支持大小写 判断 通用
        // 支持key 原大小写格式
        JSONObject jsonObj01 = jsonStr2Obj(jsonStr);
        return set2KeyValueObj(jsonObj01, jsonKey1, jsonKey2, jsonValue).toString();
    }

    /**
     * 递归生成树形结构
     *
     * @param source        数据源
     * @param root          生成的树 地址引用
     * @param root_id_value 根id 如果为0 则生成完整的一个树，目前没有层次判断，有多少层就生成多少层
     * @param id_key        id key
     * @param up_id_key     上级id key
     * @return
     */
    public static JSONArray recursionCreateArray(JSONArray source, JSONArray root, String root_id_value, String id_key, String up_id_key) {
        if (root == null)
            root = new JSONArray();
        if (root.size() == 0) {
            for (int i = 0; i < source.size(); i++) {
                JSONObject object = source.getJSONObject(i);
                if (object.optString(up_id_key).equals(root_id_value)) {
                    root.add(object);
                }
            }
            source.removeAll(root);
            for (int i = 0; i < root.size(); i++) {
                JSONObject object = root.getJSONObject(i);
                if (source.size() > 0) {
                    object.put("childs", recursionCreateArray(source, object.optJSONArray("childs"), object.optString(id_key), id_key, up_id_key));
                } else {
                    object.put("childs", "[]");
                }
            }
            return root;
        }
        return null;
    }

    /**
     * 给定一个值递归遍历，查找下级的数据
     *
     * @param source        数据源
     * @param root          生成的树 地址引用
     * @param root_id_value 根id 如果为0 则生成完整的一个树，目前没有层次判断，有多少层就生成多少层
     * @param id_key        id key
     * @param up_id_key     上级id key
     * @return
     */
    public static JSONArray recursionCreateArrayList(JSONArray source, JSONArray root, String root_id_value, String id_key, String up_id_key) {
        if (root == null)
            root = new JSONArray();
        if (root.size() == 0) {
            for (int i = 0; i < source.size(); i++) {
                JSONObject object = source.getJSONObject(i);
                if (object.optString(id_key).equals(root_id_value))
                    root.add(object);
                if (object.optString(up_id_key).equals(root_id_value)) {
                    root.add(object);
                }

            }
            source.removeAll(root);
            for (int i = 0; i < root.size(); i++) {
                JSONObject object = root.getJSONObject(i);
//                recursionCreateArray(source,root, object.optString(id_key), id_key, up_id_key);

                if (source.size() > 0) {
                    root.addAll(recursionCreateArrayList(source, null, object.optString(id_key), id_key, up_id_key));
//                    object.put("childs", recursionCreateArray(source, object.optJSONArray("childs"), object.optString(id_key), id_key, up_id_key));
                } else {
//                    object.put("childs", "[]");
                    return root;
                }
            }
            return root;
        }
        return null;
    }

    /**
     * 判断 stringarray 中是否包含 value
     *
     * @param strs
     * @param value
     * @return
     */
    public static boolean valueInJsonArray(String[] strs, String value) {
        for (Object s : strs) {
            if (s.equals(value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断 stringarray 中是否包含 value
     *
     * @param strs
     * @param value
     * @return
     */
    public static boolean valueInJsonArray(String[] strs, String... value) {
        for (Object s : strs) {
            for (String r : value)
                if (s.equals(r)) {
                    return true;
                }
        }
        return false;
    }

    /***  mysql 数据集转换成 jsonarray
     * @param resultSet
     * @param params 获取的key 可无限传
     * @return
     */
    public static JSONArray PubRetToJsonArray(ResultSet resultSet, String... params) {
        JSONArray array = new JSONArray();
        try {
            while (resultSet != null && resultSet.next()) {
                JSONObject object = new JSONObject();
                for (String param : params) {
                    object.put(param, jMysql.getRsValue(resultSet, param, ""));
                }
                if (object.containsKey("imgs")) {
                    object.put("imgs", PubFuns.decode(object.optString("imgs")));
                }
                if (object.containsKey("comment_content")) {
                    object.put("comment_content", PubFuns.decode(object.optString("comment_content")));
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

    public static String validInput(String inJson, String... keys) {
        for (String key : keys) {
            if (jJson.get1KeyValue(inJson, key, "").equals(""))
                return jMap.validate("-1", key + "值为空");
        }
        return jMap.validate("2", "");
    }
}
