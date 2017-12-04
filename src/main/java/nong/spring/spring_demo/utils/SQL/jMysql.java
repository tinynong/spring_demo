package nong.spring.spring_demo.utils.SQL;

import net.sf.json.JSONArray;
import nong.spring.spring_demo.utils.jLogs.jLogs;

import java.sql.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by linzhurui on 2017/7/18.
 * mysql 数据库 连接
 */
public class jMysql {
    private static String thisDbLinkR = "";
    private static String thisDbLinkW = "";
    private static Connection conn_r;//读操作的connection的对象
    private static Connection conn_w;//写操作的connection的对象
    private static jMysql jmysql = null;
    private static int mysqlRetryDoCount = 0;//重试执行次数，最多不超6次;
    private static int mysqlRetryDoMax = 6;//重试执行次数，最多不超6次;

    public static void doMysqlDemo() {
        //本CLASS DEMO 例子
        //初始化连接 注意一个CLASS两个 帐号，一个为W，另一个为R
        //String dbW = "[h=192.168.1.113,p=3306,u=javacw,pwd=123456,db=ryiot]";
        //String dbR = "[h=192.168.1.113,p=3306,u=javacr,pwd=123456,db=ryiot]";
        /*
         String dbW = "[h=127.0.0.1,p=3306,u=root,pwd=135246,db=ryiot]";
        String dbR = "[h=127.0.0.1,p=3306,u=root,pwd=135246,db=ryiot]";
        getInstance(dbW, dbR);//初始化数据库连接


        //查询sql语句返回 rs数据集
        String sql01 = "select * from tb01 limit 10 ";
        ResultSet rs01 = sqlSelect(sql01);
        try {
            int i = 0;
            //rs01.first();
            while (rs01.next()) {
                i++;
                int id = getRsValueInt(rs01, "sysid", 0);
                String aaa = getRsValue(rs01, "aDa", "空1");
                jLogs.wLogs(String.format("%d=>sysid:[%d] aaaa:[%s]", i, id, aaa), 6);
            }
            rs01.first();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        jLogs.wLogs("--------------------", 6);

        //判断是否在 rs  数据集中的表字段
        String field = "";
        String fieldInit = "";
        field = "aAa";
        fieldInit = (isExistRsField(rs01, field)) ? "有存在" : "不存在";
        jLogs.wLogs(String.format("字段：%s %s", field, fieldInit), 6);
        field = "ttt";
        fieldInit = (isExistRsField(rs01, field)) ? "有存在" : "不存在";
        jLogs.wLogs(String.format("字段：%s %s", field, fieldInit), 6);
        //etRsValue 获取数据集中的 指定字段 值

        String value = "";
        field = "aaa";
        value = getRsValue(rs01, field, "空值1");
        jLogs.wLogs(String.format("字段名：%s 数值：%s", field, value), 6);

        field = "aAa";
        value = getRsValue(rs01, field, "空值2");
        jLogs.wLogs(String.format("字段名：%s 数值：%s", field, value), 6);

        field = "ttt";
        value = getRsValue(rs01, field, "空值3");
        jLogs.wLogs(String.format("字段名：%s 数值：%s", field, value), 6);

        //获取 SQL语句中的 唯一一个值
        String oneStr = getSqlOneValue("select count(*) as cs from tb01", "无");
        jLogs.wLogs(String.format("SQL语句中的 唯一一个值(Str)：%s ", oneStr), 6);
        int oneInt = getSqlOneValueInt("select count(*) as ci from tb01", 9999);
        jLogs.wLogs(String.format("SQL语句中的 唯一一个值(Int)：%d ", oneInt), 6);

        //新写数据记录
        HashMap<String, String> fieldsMap01 = new HashMap();
        fieldsMap01.put("aaa", "a1");
        fieldsMap01.put("bbb", "b1");
        fieldsMap01.put("eee", "e1");
        sqlInsert("tb01", fieldsMap01);


        //更新表记录
        PubTimes pt = new PubTimes();
        String eee = pt.getHHmmss();
        HashMap<String, String> fieldsMap02 = new HashMap();
        fieldsMap02.put("eee", eee);
        sqlUpdate("tb01", fieldsMap02, " sysid='1' ");

        //执行exec SQL语句
        String sqlCmd = "update tb01 set eee='123' where sysid='1'; ";
        sqlExec(sqlCmd);


                boolean b=false;
		String str="";
		b=jMysql.inExistKeyValue("modules_menu","mdid","35");
		str=b?"存在":"不存在";
		jLogs.wLogs(String.format("[%s] key[%s] value[%s]:结果[%s]","modules_menu","mdid","35",str),6);

		b=jMysql.inExistKeyValue("modules_menu","mdid","aaa");
		str=b?"存在":"不存在";
		jLogs.wLogs(String.format("[%s] key[%s] value[%s]:结果[%s]","modules_menu","mdid","aaa",str),6);


		b=jMysql.inExistKeyValue("modules_menu","mdidxxx","aaa");
		str=b?"存在":"不存在";
		jLogs.wLogs(String.format("[%s] key[%s] value[%s]:结果[%s]","modules_menu","mdidxxx","aaa",str),6);
		b=jMysql.isExistWhere("","ffff");
		str=b?"存在":"不存在";
		jLogs.wLogs(String.format("[%s] key[%s] value[%s]:结果[%s]","modules_menu","mdidxxx","aaa",str),6);

		HashMap<String, String> fieldsMap01 = new HashMap();
		fieldsMap01.put("upid", "40");
		fieldsMap01.put("mdid", "41");

		b=jMysql.isExistFieldsValues("modules_menu",fieldsMap01);
		str=b?"存在":"不存在";
		jLogs.wLogs(String.format("[%s] key[%s] value[%s]:结果[%s]","modules_menu","fieldsMap01","aaa",str),6);

		//批量插入
		   List<Map<String, String>> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("dw_id", "1");
            map.put("role_id", "3");
            list.add(map);
        }
         int ex= jMysql.sqlBatchInsert("sys_dw_role", list);

         */

    }


    /**
     * 获取 jmysql 对象
     *
     * @param dbLinkW 数据库写 连接帐号
     * @param dbLinkR 数据库读 连接帐号
     * @return jMysql对象
     */
    public static jMysql getInstance(String dbLinkW, String dbLinkR) {
        if (jmysql == null) {
            return new jMysql(dbLinkW, dbLinkR);
        } else {
            return jmysql;
        }
    }

    /**
     * 获取连接的数据库名称
     *
     * @param isWrite
     * @return
     */
    public static String getDbName(boolean isWrite) {
        String linkInfo = isWrite ? thisDbLinkW : thisDbLinkR;
        String linkStr = "";//
        String[] linkArr = {""};
        linkStr = linkInfo.substring(1, linkInfo.length() - 1).trim();
        linkArr = linkStr.split("=|,");//分割字符串
        if (linkArr.length < 9)
            return "ryiot";
        return linkArr[9];

    }

    /**
     * 得到五不漏数据库表明
     * @param user_id
     * @return
     */
    public static String getWblTableName(String user_id) {
        String tempTable = "rytemp_wbl.temp_" + user_id;
        return tempTable;
    }

    /**
     * 创建  jMysql 对象
     *
     * @param dbLinkW 数据库写 连接帐号
     * @param dbLinkR 数据库读 连接帐号
     */
    public jMysql(String dbLinkW, String dbLinkR) {
        thisDbLinkW = dbLinkW;
        thisDbLinkR = dbLinkR;
        createMysqlConnection(true);//创建数据库写的连接池
        createMysqlConnection(false);//创建数据库写的连接池
    }


    /**
     * 创建数据库连接 createMysqlConnection
     *
     * @param isWrite 是否写，True写，False读
     */
    private static void createMysqlConnection(boolean isWrite) {
        String linkInfo = isWrite ? thisDbLinkW : thisDbLinkR;
        String linkStr = "";//
        String[] linkArr = {""};
        String dbHost = "", dbPort = "", dbUserName = "", dbPsd = "", dbName = "";
        String strWorR = isWrite ? "写" : "读";
        try {
            //加载jdbc驱动
            linkStr = linkInfo.substring(1, linkInfo.length() - 1).trim();
            linkArr = linkStr.split("=|,");//分割字符串
            if (linkArr.length < 9) {
                //参数缺少直接退出
                jLogs.wLogs(String.format("mysql连接=%s=>host[%s]; port[%s]; dbname[%s]; username[%s] ;=>[%s]", strWorR, dbHost, dbPort, dbName, dbUserName, "参数不足，强制退出平台！"), 0);
                System.exit(0);//强制退出
            }
            dbHost = linkArr[1];
            dbPort = linkArr[3];
            dbUserName = linkArr[5];
            dbPsd = linkArr[7];
            dbName = linkArr[9];
            StringBuffer urlw = new StringBuffer("jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName + "?useUnicode=true&characterEncoding=utf8");
            //StringBuffer urlw = new StringBuffer("jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName);
            Class.forName("com.mysql.jdbc.Driver");
            if (isWrite) {
                conn_w = (Connection) DriverManager.getConnection(urlw.toString(), dbUserName, dbPsd);

            } else {
                conn_r = (Connection) DriverManager.getConnection(urlw.toString(), dbUserName, dbPsd);
            }
            jLogs.wLogs(String.format("mysql连接=%s=>host[%s]; port[%s]; dbname[%s]; username[%s] ;=>[%s]", strWorR, dbHost, dbPort, dbName, dbUserName, "连接成功"), 0);
        } catch (SQLException e) {
            //e.printStackTrace();
            String errMsg = String.format("【！error】======>mysql连接=%s=>host[%s]; port[%s]; dbname[%s]; username[%s] ;=>[%s]；错误码[%d]，提示[%s]。"
                    , strWorR, dbHost, dbPort, dbName, dbUserName, "失败，强制退出平台！", e.getErrorCode(), e.getMessage());
            jLogs.wLogs(errMsg, 0);//错误日志输出
            jLogs.wErrLogs(errMsg);//错误日志输出 同时输出到ERROR文件中便于运维
            System.exit(0);//强制退出
        } catch (ClassNotFoundException e) {
            //e.printStackTrace();
            String errMsg = String.format("【！error】======>mysql连接=%s=>host[%s]; port[%s]; dbname[%s]; username[%s] ;=>[%s]；错误提示[%s]。"
                    , strWorR, dbHost, dbPort, dbName, dbUserName, "失败，强制退出平台！", e.getMessage());
            jLogs.wLogs(errMsg, 0);//错误日志输出
            jLogs.wErrLogs(errMsg);//错误日志输出 同时输出到ERROR文件中便于运维
            System.exit(0);//强制退出
        }
    }


    /**
     * getRsValue 获取数据集中的 指定字段 值
     *
     * @param rs    数据集
     * @param field 字段
     * @param value 默认值
     * @return 字段值
     */
    public static String getRsValue(ResultSet rs, String field, String value) {
        try {
            if (!isExistColumn(rs, field)) {
                return value;
            }
            String dbValue = rs.getString(field);
            dbValue = dbValue == null ? "" : dbValue.toString();
            return dbValue;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            String errMsg = String.format("【！error】======>获取字段[%s] 为String错误；错误码[%d]，提示[%s]。", field, e.getErrorCode(), e.getMessage());
            jLogs.wLogs(errMsg, 0);//错误日志输出
            jLogs.wErrLogs(errMsg);//错误日志输出 同时输出到ERROR文件中便于运维
            return value;
        }
    }

    /**
     * getRsValueInt 获取数据集中的 指定字段 值 (INT)类型
     *
     * @param rs    数据集
     * @param field 字段
     * @param value 默认值
     * @return 字段值
     */
    public static int getRsValueInt(ResultSet rs, String field, int value) {
        try {
            if (!isExistRsField(rs, field)) {
                return value;
            }
            return rs.getInt(field);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            String errMsg = String.format("【！error】======>获取字段[%s] 为Int错误；错误码[%d]，提示[%s]。", field, e.getErrorCode(), e.getMessage());
            jLogs.wLogs(errMsg, 0);//错误日志输出
            jLogs.wErrLogs(errMsg);//错误日志输出 同时输出到ERROR文件中便于运维
            return value;
        }
    }

    /**
     * getSqlOneValue 获取sql 语句中的单一数值
     *
     * @param sqlStr sql语句
     * @param value  默认值
     * @return 返回值
     */
    public static String getSqlOneValue(String sqlStr, String value) {
        ResultSet rs =null;
        try {
            rs = sqlSelect(sqlStr);
            if (rs == null || !rs.next()) {
                if(rs!=null){
                    try {
                        rs.close();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
                return value;
            } else {
                rs.first();
                String dbValue = rs.getString(1);
                dbValue = dbValue == null ? value : dbValue.toString();
                rs.close();
                return dbValue;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            String errMsg = String.format("【！error】======>获取单一值[%s]错误；错误码[%d]，提示[%s]。", sqlStr, e.getErrorCode(), e.getMessage());
            jLogs.wLogs(errMsg, 0);//错误日志输出
            jLogs.wErrLogs(errMsg);//错误日志输出 同时输出到ERROR文件中便于运维
            if(rs!=null){
                try {
                    rs.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            return value;
        }
    }

    /**
     * getSqlOneValueInt 获取sql 语句中的单一数值
     *
     * @param sqlStr sql语句
     * @param value  默认值
     * @return 返回值
     */
    public static int getSqlOneValueInt(String sqlStr, int value) {
        String rStr = getSqlOneValue(sqlStr, String.valueOf(value));
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(rStr).matches() ? Integer.parseInt(rStr) : value;
    }

    /**
     * sqlSelect 查询语句
     *
     * @param sqlString 语句
     */
    public static ResultSet sqlSelect(String sqlString) {
        try {
            long iStart = System.currentTimeMillis();
            PreparedStatement ps = conn_r.prepareStatement(sqlString);
            ResultSet rs = ps.executeQuery(sqlString);
            long iEnd = System.currentTimeMillis();
            jLogs.wLogs(String.format("%s =>%d ms", sqlString, iEnd - iStart), 5);
            mysqlRetryDoCount = 0;//重试执行次数恢复为0
            return rs;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            int errCode = e.getErrorCode();
            String errMsg = String.format("【！error】======>%s =>错误；错误码[%d]，提示[%s]。", sqlString, errCode, e.getMessage());
            jLogs.wLogs(errMsg, 0);//错误日志输出
            jLogs.wErrLogs(errMsg);//错误日志输出 同时输出到ERROR文件中便于运维
            //如果是断开，的错误，则 再来一执行次 总次数在 mysqlRetryDoMax 之内
            if (errCode == 0 && mysqlRetryDoCount <= mysqlRetryDoMax) {
                try {
                    mysqlRetryDoCount++;
                    conn_r.close();
                    createMysqlConnection(false);
                    return sqlSelect(sqlString);//再次回调执行
                } catch (SQLException e2) {
                    errMsg = String.format("【！error】======>=>断开重连接，再次执行错误；错误码[%d]，提示[%s]。", e2.getErrorCode(), e2.getMessage());
                    jLogs.wLogs(errMsg, 0);//错误日志输出
                    jLogs.wErrLogs(errMsg);//错误日志输出 同时输出到ERROR文件中便于运维
                    return null;
                }

            }
            return null;
        }
    }


    /**
     * sqlSelect 查询语句（专属没有日志）
     *
     * @param sqlString 语句
     */
    public static ResultSet sqlSelectNotlog(String sqlString) {
        try {
            long iStart = System.currentTimeMillis();
            PreparedStatement ps = conn_r.prepareStatement(sqlString);
            ResultSet rs = ps.executeQuery(sqlString);
            long iEnd = System.currentTimeMillis();
//            jLogs.wLogs(String.format("%s =>%d ms", sqlString, iEnd - iStart), 5);
            mysqlRetryDoCount = 0;//重试执行次数恢复为0
            return rs;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            int errCode = e.getErrorCode();
            String errMsg = String.format("【！error】======>%s =>错误；错误码[%d]，提示[%s]。", sqlString, errCode, e.getMessage());
            jLogs.wLogs(errMsg, 0);//错误日志输出
            jLogs.wErrLogs(errMsg);//错误日志输出 同时输出到ERROR文件中便于运维
            //如果是断开，的错误，则 再来一执行次 总次数在 mysqlRetryDoMax 之内
            if (errCode == 0 && mysqlRetryDoCount <= mysqlRetryDoMax) {
                try {
                    mysqlRetryDoCount++;
                    conn_r.close();
                    createMysqlConnection(false);
                    return sqlSelect(sqlString);//再次回调执行
                } catch (SQLException e2) {
                    errMsg = String.format("【！error】======>=>断开重连接，再次执行错误；错误码[%d]，提示[%s]。", e2.getErrorCode(), e2.getMessage());
                    jLogs.wLogs(errMsg, 0);//错误日志输出
                    jLogs.wErrLogs(errMsg);//错误日志输出 同时输出到ERROR文件中便于运维
                    return null;
                }

            }
            return null;
        }
    }

    /**
     * sqlExec 执行 Insert Update等Exec sql语句
     *
     * @param sqlString 执行的sql 语句
     * @return
     */
    public static int sqlExec(String sqlString) {
        try {
            long iStart = System.currentTimeMillis();
            int execCount = 0;
            Statement st = conn_w.createStatement();

            execCount = st.executeUpdate(sqlString);
            long iEnd = System.currentTimeMillis();
            jLogs.wLogs(String.format("%s =>%d ms count[%d]", sqlString, iEnd - iStart, execCount), 3);
            mysqlRetryDoCount = 0;//重试执行次数恢复为0
            return execCount;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            int errCode = e.getErrorCode();
            String errMsg = String.format("【！error】======>%s =>错误；错误码[%d]，提示[%s]。", sqlString, errCode, e.getMessage());
            jLogs.wLogs(errMsg, 0);//错误日志输出
            jLogs.wErrLogs(errMsg);//错误日志输出 同时输出到ERROR文件中便于运维
            //如果是断开，的错误，则 再来一执行次 总次数在 mysqlRetryDoMax 之内
            if (errCode == 0 && mysqlRetryDoCount <= mysqlRetryDoMax) {
                try {
                    mysqlRetryDoCount++;
                    conn_w.close();
                    createMysqlConnection(true);
                    return sqlExec(sqlString);//再次回调执行
                } catch (SQLException e2) {
                    errMsg = String.format("【！error】======>=>断开重连接，再次执行错误；错误码[%d]，提示[%s]。", e2.getErrorCode(), e2.getMessage());
                    jLogs.wLogs(errMsg, 0);//错误日志输出
                    jLogs.wErrLogs(errMsg);//错误日志输出 同时输出到ERROR文件中便于运维
                    return -1;
                }
            }
            return -1;
        }
    }


    /**
     * sqlExec 执行 Insert Update等Exec sql语句
     *
     * @param sqlString 执行的sql 语句（专属没有日志）
     * @return
     */
    public static int sqlExecNotlog(String sqlString) {
        try {
            long iStart = System.currentTimeMillis();
            int execCount = 0;
            Statement st = conn_w.createStatement();
            execCount = st.executeUpdate(sqlString);
            long iEnd = System.currentTimeMillis();
            //jLogs.wLogs(String.format("%s =>%d ms count[%d]", sqlString, iEnd - iStart, execCount), 3);
            mysqlRetryDoCount = 0;//重试执行次数恢复为0
            return execCount;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            int errCode = e.getErrorCode();
            String errMsg = String.format("【！error】======>%s =>错误；错误码[%d]，提示[%s]。", sqlString, errCode, e.getMessage());
            jLogs.wLogs(errMsg, 0);//错误日志输出
            jLogs.wErrLogs(errMsg);//错误日志输出 同时输出到ERROR文件中便于运维
            //如果是断开，的错误，则 再来一执行次 总次数在 mysqlRetryDoMax 之内
            if (errCode == 0 && mysqlRetryDoCount <= mysqlRetryDoMax) {
                try {
                    mysqlRetryDoCount++;
                    conn_w.close();
                    createMysqlConnection(true);
                    return sqlExec(sqlString);//再次回调执行
                } catch (SQLException e2) {
                    errMsg = String.format("【！error】======>=>断开重连接，再次执行错误；错误码[%d]，提示[%s]。", e2.getErrorCode(), e2.getMessage());
                    jLogs.wLogs(errMsg, 0);//错误日志输出
                    jLogs.wErrLogs(errMsg);//错误日志输出 同时输出到ERROR文件中便于运维
                    return -1;
                }
            }
            return -1;
        }
    }

    /**
     * sqlInsert 执行 Insert sql语句
     *
     * @param tableName 表名
     * @return 字段名，字段值
     */
    public static String sqlInsert(String tableName, HashMap<String, String> fieldsMap) {
        String sqlInsertStr = "";
        try {
            long iStart = System.currentTimeMillis();
            int execCount = 0;
            String sqlFields = "", sqlValue = "";
            for (Map.Entry<String, String> entry : fieldsMap.entrySet()) {
                sqlFields += String.format(" `%s`,", entry.getKey().toString());
                sqlValue += String.format(" '%s',", entry.getValue().toString());
            }

/*            Iterator iter = fieldsMap.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                sqlFields += String.format(" `%s`,", entry.getKey().toString());
                sqlValue += String.format(" '%s',", entry.getValue().toString());
            }*/
            if (sqlFields.length() <= 1) return "0";
            sqlFields = sqlFields.substring(1, sqlFields.length() - 1);
            sqlValue = sqlValue.substring(1, sqlValue.length() - 1);
            sqlInsertStr = String.format(" insert into %s (%s) values (%s) ; ", tableName, sqlFields, sqlValue);
            //Statement st = conn_w.createStatement();
            //execCount = st.executeUpdate(sqlInsert);
            PreparedStatement pstmt = conn_w.prepareStatement(sqlInsertStr, Statement.RETURN_GENERATED_KEYS);
            int count = pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            long iEnd = System.currentTimeMillis();
            jLogs.wLogs(String.format("%s =>%d ms count[%d]", sqlInsertStr, iEnd - iStart, execCount), 3);
            //如果 是自增，则返回 最新自增的ID；非自增型的，则返回 1值
            mysqlRetryDoCount = 0;//重试执行次数恢复为0
            return (rs != null && rs.next()) ? String.valueOf(rs.getInt(1)) : count > 0 ? "1" : "0";
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            int errCode = e.getErrorCode();
            String errMsg = String.format("【！error】======>%s =>错误；错误码[%d]，提示[%s]。", sqlInsertStr, errCode, e.getMessage());
            jLogs.wLogs(errMsg, 0);//错误日志输出
            jLogs.wErrLogs(errMsg);//错误日志输出 同时输出到ERROR文件中便于运维
            //如果是断开，的错误，则 再来一执行次 总次数在 mysqlRetryDoMax 之内
            if (errCode == 0 && mysqlRetryDoCount <= mysqlRetryDoMax) {
                try {
                    mysqlRetryDoCount++;
                    conn_w.close();
                    createMysqlConnection(true);
                    return sqlInsert(tableName, fieldsMap);//再次回调执行
                } catch (SQLException e2) {
                    errMsg = String.format("【！error】======>=>断开重连接，再次执行错误；错误码[%d]，提示[%s]。", e2.getErrorCode(), e2.getMessage());
                    jLogs.wLogs(errMsg, 0);//错误日志输出
                    jLogs.wErrLogs(errMsg);//错误日志输出 同时输出到ERROR文件中便于运维
                    return "0";
                }
            }
            return "0";
        }
    }
    /**
     * sqlInsert 执行 Insert sql语句
     *
     * @param tableName 表名
     * @return 字段名，字段值
     */
    public static String sqlInsertNotlog(String tableName, HashMap<String, String> fieldsMap) {
        String sqlInsertStr = "";
        try {
            long iStart = System.currentTimeMillis();
            int execCount = 0;
            String sqlFields = "", sqlValue = "";
            for (Map.Entry<String, String> entry : fieldsMap.entrySet()) {
                sqlFields += String.format(" `%s`,", entry.getKey().toString());
                sqlValue += String.format(" '%s',", entry.getValue().toString());
            }

/*            Iterator iter = fieldsMap.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                sqlFields += String.format(" `%s`,", entry.getKey().toString());
                sqlValue += String.format(" '%s',", entry.getValue().toString());
            }*/
            if (sqlFields.length() <= 1) return "0";
            sqlFields = sqlFields.substring(1, sqlFields.length() - 1);
            sqlValue = sqlValue.substring(1, sqlValue.length() - 1);
            sqlInsertStr = String.format(" insert into %s (%s) values (%s) ; ", tableName, sqlFields, sqlValue);
            //Statement st = conn_w.createStatement();
            //execCount = st.executeUpdate(sqlInsert);
            PreparedStatement pstmt = conn_w.prepareStatement(sqlInsertStr, Statement.RETURN_GENERATED_KEYS);
            int count = pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            long iEnd = System.currentTimeMillis();
            //jLogs.wLogs(String.format("%s =>%d ms count[%d]", sqlInsertStr, iEnd - iStart, execCount), 3);
            //如果 是自增，则返回 最新自增的ID；非自增型的，则返回 1值
            mysqlRetryDoCount = 0;//重试执行次数恢复为0
            return (rs != null && rs.next()) ? String.valueOf(rs.getInt(1)) : count > 0 ? "1" : "0";
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            int errCode = e.getErrorCode();
            String errMsg = String.format("【！error】======>%s =>错误；错误码[%d]，提示[%s]。", sqlInsertStr, errCode, e.getMessage());
            jLogs.wLogs(errMsg, 0);//错误日志输出
            jLogs.wErrLogs(errMsg);//错误日志输出 同时输出到ERROR文件中便于运维
            //如果是断开，的错误，则 再来一执行次 总次数在 mysqlRetryDoMax 之内
            if (errCode == 0 && mysqlRetryDoCount <= mysqlRetryDoMax) {
                try {
                    mysqlRetryDoCount++;
                    conn_w.close();
                    createMysqlConnection(true);
                    return sqlInsert(tableName, fieldsMap);//再次回调执行
                } catch (SQLException e2) {
                    errMsg = String.format("【！error】======>=>断开重连接，再次执行错误；错误码[%d]，提示[%s]。", e2.getErrorCode(), e2.getMessage());
                    jLogs.wLogs(errMsg, 0);//错误日志输出
                    jLogs.wErrLogs(errMsg);//错误日志输出 同时输出到ERROR文件中便于运维
                    return "0";
                }
            }
            return "0";
        }
    }
    /**
     * sqlInsert 执行 Insert sql语句
     *
     * @param tableName  表名
     * @param fieldsMap  字段名，字段值
     * @param sqlUpWhere 更新的WHERE 条件
     * @return -1表未错误,>=0表示成功
     */
    public static int sqlUpdate(String tableName, HashMap<String, String> fieldsMap, String sqlUpWhere) {
        String sqlUpdate = "";
        try {
            long iStart = System.currentTimeMillis();
            int execCount = 0;
            String sqlSetFields = "";
            Iterator iter = fieldsMap.entrySet().iterator();
            for (Map.Entry<String, String> entry : fieldsMap.entrySet()) {
                sqlSetFields += String.format(" `%s`='%s',", entry.getKey().toString(), entry.getValue().toString());
            }
/*            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                sqlSetFields += String.format(" `%s`='%s',", entry.getKey().toString(), entry.getValue().toString());
            }*/
            sqlSetFields = sqlSetFields.substring(1, sqlSetFields.length() - 1);
            sqlUpdate = String.format(" update %s set %s where %s; ", tableName, sqlSetFields, sqlUpWhere);
            Statement st = conn_w.createStatement();
            execCount = st.executeUpdate(sqlUpdate);
            long iEnd = System.currentTimeMillis();
            jLogs.wLogs(String.format("%s =>%d ms count[%d]", sqlUpdate, iEnd - iStart, execCount), 3);
            mysqlRetryDoCount = 0;//重试执行次数恢复为0
            return execCount;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            int errCode = e.getErrorCode();
            String errMsg = String.format("【！error】======>%s =>错误；错误码[%d]，提示[%s]。", sqlUpdate, errCode, e.getMessage());
            jLogs.wLogs(errMsg, 0);//错误日志输出
            jLogs.wErrLogs(errMsg);//错误日志输出 同时输出到ERROR文件中便于运维
            //如果是断开，的错误，则 再来一执行次 总次数在 mysqlRetryDoMax 之内
            if (errCode == 0 && mysqlRetryDoCount <= mysqlRetryDoMax) {
                try {
                    mysqlRetryDoCount++;
                    conn_w.close();
                    createMysqlConnection(true);
                    return sqlUpdate(tableName, fieldsMap, sqlUpWhere);//再次回调执行
                } catch (SQLException e2) {
                    //e2.printStackTrace();
                    errMsg = String.format("【！error】======>=>断开重连接，再次执行错误；错误码[%d]，提示[%s]。", e2.getErrorCode(), e2.getMessage());
                    jLogs.wLogs(errMsg, 0);//错误日志输出
                    jLogs.wErrLogs(errMsg);//错误日志输出 同时输出到ERROR文件中便于运维
                    return -1;
                }
            }
            return -1;
        }
    }

    /**
     * sqlInsert 执行 Insert sql语句（没有日志）
     *
     * @param tableName  表名
     * @param fieldsMap  字段名，字段值
     * @param sqlUpWhere 更新的WHERE 条件
     * @return -1表未错误,>=0表示成功
     */
    public static int sqlUpdateNolog(String tableName, HashMap<String, String> fieldsMap, String sqlUpWhere) {
        String sqlUpdate = "";
        try {
            long iStart = System.currentTimeMillis();
            int execCount = 0;
            String sqlSetFields = "";
            Iterator iter = fieldsMap.entrySet().iterator();
            for (Map.Entry<String, String> entry : fieldsMap.entrySet()) {
                sqlSetFields += String.format(" `%s`='%s',", entry.getKey().toString(), entry.getValue().toString());
            }
/*            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                sqlSetFields += String.format(" `%s`='%s',", entry.getKey().toString(), entry.getValue().toString());
            }*/
            sqlSetFields = sqlSetFields.substring(1, sqlSetFields.length() - 1);
            sqlUpdate = String.format(" update %s set %s where %s; ", tableName, sqlSetFields, sqlUpWhere);
            Statement st = conn_w.createStatement();
            execCount = st.executeUpdate(sqlUpdate);
            long iEnd = System.currentTimeMillis();
            //jLogs.wLogs(String.format("%s =>%d ms count[%d]", sqlUpdate, iEnd - iStart, execCount), 3);
            mysqlRetryDoCount = 0;//重试执行次数恢复为0
            return execCount;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            int errCode = e.getErrorCode();
            String errMsg = String.format("【！error】======>%s =>错误；错误码[%d]，提示[%s]。", sqlUpdate, errCode, e.getMessage());
            jLogs.wLogs(errMsg, 0);//错误日志输出
            jLogs.wErrLogs(errMsg);//错误日志输出 同时输出到ERROR文件中便于运维
            //如果是断开，的错误，则 再来一执行次 总次数在 mysqlRetryDoMax 之内
            if (errCode == 0 && mysqlRetryDoCount <= mysqlRetryDoMax) {
                try {
                    mysqlRetryDoCount++;
                    conn_w.close();
                    createMysqlConnection(true);
                    return sqlUpdate(tableName, fieldsMap, sqlUpWhere);//再次回调执行
                } catch (SQLException e2) {
                    //e2.printStackTrace();
                    errMsg = String.format("【！error】======>=>断开重连接，再次执行错误；错误码[%d]，提示[%s]。", e2.getErrorCode(), e2.getMessage());
                    jLogs.wLogs(errMsg, 0);//错误日志输出
                    jLogs.wErrLogs(errMsg);//错误日志输出 同时输出到ERROR文件中便于运维
                    return -1;
                }
            }
            return -1;
        }
    }

    /**
     * sqlInsert 执行 Insert sql语句
     *
     * @return -1表未错误,>=0表示成功
     */
    public static int[] sqlUpdateBatch(JSONArray sqls) {
        try {
            long iStart = System.currentTimeMillis();
            int execCount = 0;
            String sqlSetFields = "";

            Statement st = conn_w.createStatement();

            for (Object sql : sqls) {
                st.addBatch(sql.toString());
                System.out.println(sql.toString());
            }

            long iEnd = System.currentTimeMillis();
            jLogs.wLogs(String.format("%s =>%d ms count[%d]", sqls, iEnd - iStart, execCount), 3);
            mysqlRetryDoCount = 0;//重试执行次数恢复为0
            return st.executeBatch();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            int errCode = e.getErrorCode();
            String errMsg = String.format("【！error】======>%s =>错误；错误码[%d]，提示[%s]。", sqls, errCode, e.getMessage());
            jLogs.wLogs(errMsg, 0);//错误日志输出
            jLogs.wErrLogs(errMsg);//错误日志输出 同时输出到ERROR文件中便于运维
            //如果是断开，的错误，则 再来一执行次 总次数在 mysqlRetryDoMax 之内
            if (errCode == 0 && mysqlRetryDoCount <= mysqlRetryDoMax) {
                try {
                    mysqlRetryDoCount++;
                    conn_w.close();
                    createMysqlConnection(true);
                    return sqlUpdateBatch(sqls);//再次回调执行
                } catch (SQLException e2) {
                    //e2.printStackTrace();
                    errMsg = String.format("【！error】======>=>断开重连接，再次执行错误；错误码[%d]，提示[%s]。", e2.getErrorCode(), e2.getMessage());
                    jLogs.wLogs(errMsg, 0);//错误日志输出
                    jLogs.wErrLogs(errMsg);//错误日志输出 同时输出到ERROR文件中便于运维
                    return null;
                }
            }
            return null;
        }
    }


    /**
     * sqlInsert ON DUPLICATE KEY UPDATE  sql语句
     * 如果表存在则更新，否则插入
     *
     * @param tableName 表名
     * @param fieldsMap 字段名，字段值
     * @return -1表未错误,>=0表示成功
     */
    public static int sqlInsertOnUpdate(String tableName, HashMap<String, String> fieldsMap) {
        String sqlUpdate = "";
        try {
            long iStart = System.currentTimeMillis();
            int execCount = 0;
            String sqlSetFields = "";
            String sqlFields = "", sqlValue = "";
            for (Map.Entry<String, String> entry : fieldsMap.entrySet()) {
                sqlSetFields += String.format(" `%s`='%s',", entry.getKey().toString(), entry.getValue().toString());
                sqlFields += String.format(" `%s`,", entry.getKey().toString());
                sqlValue += String.format(" '%s',", entry.getValue().toString());
            }
            sqlFields = sqlFields.substring(1, sqlFields.length() - 1);
            sqlValue = sqlValue.substring(1, sqlValue.length() - 1);
            sqlSetFields = sqlSetFields.substring(1, sqlSetFields.length() - 1);
            sqlUpdate = String.format(" INSERT INTO %s ( %s ) VALUES ( %s ) ON DUPLICATE KEY UPDATE %s", tableName, sqlFields, sqlValue, sqlSetFields);
            Statement st = conn_w.createStatement();
            execCount = st.executeUpdate(sqlUpdate);
            long iEnd = System.currentTimeMillis();
            jLogs.wLogs(String.format("%s =>%d ms count[%d]", sqlUpdate, iEnd - iStart, execCount), 3);
            mysqlRetryDoCount = 0;//重试执行次数恢复为0
            return execCount;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            int errCode = e.getErrorCode();
            String errMsg = String.format("【！error】======>%s =>错误；错误码[%d]，提示[%s]。", sqlUpdate, errCode, e.getMessage());
            jLogs.wLogs(errMsg, 0);//错误日志输出
            jLogs.wErrLogs(errMsg);//错误日志输出 同时输出到ERROR文件中便于运维
            //如果是断开，的错误，则 再来一执行次 总次数在 mysqlRetryDoMax 之内
            if (errCode == 0 && mysqlRetryDoCount <= mysqlRetryDoMax) {
                try {
                    mysqlRetryDoCount++;
                    conn_w.close();
                    createMysqlConnection(true);
                    return sqlInsertOnUpdate(tableName, fieldsMap);//再次回调执行
                } catch (SQLException e2) {
                    //e2.printStackTrace();
                    errMsg = String.format("【！error】======>=>断开重连接，再次执行错误；错误码[%d]，提示[%s]。", e2.getErrorCode(), e2.getMessage());
                    jLogs.wLogs(errMsg, 0);//错误日志输出
                    jLogs.wErrLogs(errMsg);//错误日志输出 同时输出到ERROR文件中便于运维
                    return -1;
                }
            }
            return -1;
        }
    }

    /**
     * 批量修改表字段
     * /**
     * <p>
     * 批量修改单个字段
     * 主键的list长度要与 字段list长度一致否则会出现未知错误
     *
     * @param tableName  表名
     * @param fieldsMaps 字段名，List<字段值>
     * @param majorKey   主键名，List<主键值>
     * @param idList     主键ids
     * @return -1表未错误,>=0表示成功
     */
    public static int sqlBatchUpdate(String tableName, Map<String, List<String>> fieldsMaps, String majorKey, List<String> idList) {

        /**
         UPDATE iot_tw_infousr
         SET status = CASE infousr_id
         WHEN 6 THEN 3
         WHEN 7 THEN 4
         WHEN 8 THEN 5
         END,
         infousr_title = CASE infousr_id
         WHEN 6 THEN 'HFH2'
         WHEN 7 THEN 'FSY2'
         WHEN 8 THEN 'HXG2'
         END
         WHERE infousr_id IN (6,7,8)
         */
        String sqlUpdate = "";
        try {
            if (PubFuns.isEmpty(tableName) || PubFuns.isEmpty(majorKey) || idList.size() == 0 || fieldsMaps.size() == 0)
                return -1;//参数一定要全

            String sql = String.format("UPDATE %s", tableName);
            String keys = "";
            String set = "set ";
            for (Map.Entry<String, List<String>> entry : fieldsMaps.entrySet()) {
                set = set + String.format(" %s = case %s ", entry.getKey(), majorKey);
                List<String> values = entry.getValue();
                for (int i = 0; i < values.size(); i++) {
                    if (idList.size() > i)//为了防止出错
                        set = set + String.format(" when %s THEN '%s' ", idList.get(i), values.get(i));
                }
                set = set + " end,";
            }
            set = set.substring(0, set.length() - 1);
            for (String key : idList) {
                keys = keys + key + ",";
            }
            keys = keys.substring(0, keys.length() - 1);
            sqlUpdate = String.format("%s %s where %s in (%s) ", sql, set, majorKey, keys);
            long iStart = System.currentTimeMillis();
            int execCount = 0;

            Statement st = conn_w.createStatement();
            execCount = st.executeUpdate(sqlUpdate);
            long iEnd = System.currentTimeMillis();
            jLogs.wLogs(String.format("%s =>%d ms count[%d]", sqlUpdate, iEnd - iStart, execCount), 3);
            mysqlRetryDoCount = 0;//重试执行次数恢复为0
            return execCount;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            int errCode = e.getErrorCode();
            String errMsg = String.format("【！error】======>%s =>错误；错误码[%d]，提示[%s]。", sqlUpdate, errCode, e.getMessage());
            jLogs.wLogs(errMsg, 0);//错误日志输出
            jLogs.wErrLogs(errMsg);//错误日志输出 同时输出到ERROR文件中便于运维
            //如果是断开，的错误，则 再来一执行次 总次数在 mysqlRetryDoMax 之内
            if (errCode == 0 && mysqlRetryDoCount <= mysqlRetryDoMax) {
                try {
                    mysqlRetryDoCount++;
                    conn_w.close();
                    createMysqlConnection(true);
                    return sqlBatchUpdate(tableName, fieldsMaps, majorKey, idList);//再次回调执行
                } catch (SQLException e2) {
                    //e2.printStackTrace();
                    errMsg = String.format("【！error】======>=>断开重连接，再次执行错误；错误码[%d]，提示[%s]。", e2.getErrorCode(), e2.getMessage());
                    jLogs.wLogs(errMsg, 0);//错误日志输出
                    jLogs.wErrLogs(errMsg);//错误日志输出 同时输出到ERROR文件中便于运维
                    return -1;
                }
            }
            return -1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

    }

    /**
     * 批量插入数据
     *
     * @param tableName     表名
     * @param listFieldsMap 列表<字段名，字段值>
     * @return -1表未错误,>=0表示成功
     */
    public static int sqlBatchInsert(String tableName, List<Map<String, String>> listFieldsMap) {
        String insertSql = String.format("INSERT INTO %s ", tableName);//"INSERT INTO sys_role_module(role_id, module_id ,module_params) VALUES ";
        String vales = "";
        String insert = " (";
        if (listFieldsMap != null && listFieldsMap.size() > 0) {
            for (Map.Entry<String, String> entry : listFieldsMap.get(0).entrySet()) {
                insert = insert + entry.getKey() + ",";
            }
            insert = insert.substring(0, insert.length() - 1);
            insert = insert + ") VALUES ";
            for (Map<String, String> map : listFieldsMap) {
                String subValue = "(";
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    subValue = subValue + "'" + entry.getValue() + "',";
                }
                subValue = subValue.substring(0, subValue.length() - 1);
                subValue = subValue + "),";
                vales = vales + subValue;
            }
            vales = vales.substring(0, vales.length() - 1);
            insertSql = insertSql + insert + vales;
            return jMysql.sqlExec(insertSql);
        } else {
            return -1;
        }

    }

    /**
     * inRsField 判断是否在 rs  数据集中的表字段
     *
     * @param rs
     * @param field
     * @return
     */
    public static boolean isExistRsField(ResultSet rs, String field) {
        try {
            ResultSetMetaData rsm = rs.getMetaData(); //获得列集
            int columnNum = rsm.getColumnCount();
            String rsmField = "";
            field = field.toLowerCase();
            for (int i = 1; i <= columnNum; i++) {
                rsmField = rsm.getColumnName(i).toLowerCase();
                if (rsmField.equals(field)) {
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            String errMsg = String.format("【！error】======>判断字段[%s]是否存在错误；错误码[%d]，提示[%s]。", field, e.getErrorCode(), e.getMessage());
            jLogs.wLogs(errMsg, 0);//错误日志输出
            jLogs.wErrLogs(errMsg);//错误日志输出 同时输出到ERROR文件中便于运维
            return false;
        }
    }

    /**
     * 判断查询结果集中是否存在某列
     *
     * @param rs         查询结果集
     * @param columnName 列名
     * @return true 存在; false 不存在
     */
    public static boolean isExistColumn(ResultSet rs, String columnName) {
        try {
            return rs.findColumn(columnName) > 0 ? true : false;
        } catch (SQLException e) {
            return false;
        }

    }

    /**
     * isExistFieldValue 查询关键 字段 的 VALUE值是否存在表中
     *
     * @param tableName 表名
     * @param field     字段
     * @param value     字段值
     * @return
     */
    public static boolean isExistFieldValue(String tableName, String field, String value) {
        String sql = String.format("select count(*) from %s where %s ='%s' ", tableName, field, value);
        return getSqlOneValueInt(sql, 0) > 0 ? true : false;
    }

    /**
     * isExistKeysValues 查询多个关键 字段 的 多个VALUE值是否存在表中
     *
     * @param tableName 表名
     * @param fvParames 多个字段名 和 字段值
     * @return
     */
    public static boolean isExistFieldsValues(String tableName, HashMap<String, String> fvParames) {
        if (fvParames == null) return false;
        String sqlWhere = "";
        for (Map.Entry<String, String> entry : fvParames.entrySet()) {
            sqlWhere += String.format(" `%s`='%s'  and ", entry.getKey().toString(), entry.getValue().toString());
        }
        sqlWhere = sqlWhere.substring(1, sqlWhere.length() - 6);
        String sqlSelect = String.format(" select count(*) from %s where %s; ", tableName, sqlWhere);
        return getSqlOneValueInt(sqlSelect, 0) > 0 ? true : false;
    }

    /**
     * isExistWhere 查询where 条件是否有数据
     *
     * @param tableName 表名
     * @param sqlWhere  where条件
     * @return
     */
    public static boolean isExistWhere(String tableName, String sqlWhere) {
        String sqlSelect = String.format(" select count(*) from %s where %s; ", tableName, sqlWhere);
        return getSqlOneValueInt(sqlSelect, 0) > 0 ? true : false;
    }

//    /**
//     * 不分页搜索
//     * @param sql
//     * @param par
//     * @return
//     */
//    public static String getPageAll(String sql ,String... par){
//        ResultSet rs01 = jMysql.sqlSelect(sql);
//        if(rs01==null){
//            return jOuter.outDataBuild(-1, "调用方法出错", "调用方法出错");
//        }
//        JSONArray array = jMap.RetToJsonArray(rs01, par);
//        return jOuter.outDataBuild(array.size(), array.toString(), "");
//    }
//
//
//    /**
//     *
//     * @param sql
//     * @param pageInfo
//     * @return {  "sql":"select * from .,.,,,", "pageInfo":{ "index_page",1,"page_count":10 .... } }
//     */
//    public static String getPageTolimit(String sql, String pageInfo, String... par) {
//        String out = getPageTolimit(sql, pageInfo);//得到分页信息
//        pageInfo = jJson.get1KeyValue(out, "pageInfo", "");
//        ResultSet rs01 = jMysql.sqlSelect(jJson.get1KeyValue(out, "sql", ""));
//        if(rs01==null){
//            return jOuter.outDataBuild(-1, "调用方法出错", pageInfo, "调用方法出错");
//        }
//
//        JSONArray array = jMap.RetToJsonArray(rs01, par);
//        return jOuter.outDataBuild(array.size(), array.toString(), pageInfo, "");
//    }

    /**
     * (
     *
     * @param sql
     * @param pageInfo
     * @return {  "sql":"select * from .,.,,,", "pageInfo":{ "index_page",1,"page_count":10 .... } }
     */
    public static String getPageTolimit(String sql, String pageInfo) {
        sql = sql.toLowerCase();
        int index = sql.indexOf("from") + 4;
        int start = 0;
        int end = 0;
        while (index < sql.length()) {
            if (Integer.valueOf(sql.charAt(index)) == 32) { //如果遇到空格的话
                if (end != 0) {
                    end = index;
                    break;
                } else {
                    start = index;
                }
            } else {
                end = index;
            }
            index++;
        }
        String tableName = sql.substring(start, end + 1);
        String countSql = "";
        String tempSql = sql;
        if (sql.contains("group_concat") || sql.contains("group by")) {
//        countSql = String.format("select count(*) as totalCount from %s  ", sql.substring(start));
            int lastRight = sql.lastIndexOf(")");
            int lastBy = sql.lastIndexOf("group by");
            int lastOrder = sql.lastIndexOf("order by");
            if (lastOrder > lastRight)
                tempSql = sql.substring(0, lastOrder);
            if (lastBy > lastRight)
                countSql = String.format("select count(*) as totalCount from ( select count(*) from %s ) as temp ", tempSql.substring(start));
            else
                countSql = String.format("select count(*) as totalCount from %s ", tempSql.substring(start));
        } else {
//            countSql = String.format("select count(*) as totalCount from %s", tableName);
            int lastRight = sql.lastIndexOf(")");
            int lastOrder = sql.lastIndexOf("order by");
            if (lastOrder > lastRight)
                tempSql = tempSql.substring(0, lastOrder);
            countSql = String.format("select count(*) as totalCount from %s  ", tempSql.substring(start));
        }
        int page_index = Integer.valueOf(jJson.get1KeyValue(pageInfo, "page_index", "1"));//第几页
        int page_limit = Integer.valueOf(jJson.get1KeyValue(pageInfo, "page_limit", "10"));//每页获取多少

        if (page_index <= 1) {
            page_index = 1;
        }
        int record_count = getSqlOneValueInt(countSql, 1);
        int page_count = 0;
        if (record_count != 0)
            if (record_count % page_limit == 0)
                page_count = record_count / page_limit;
            else {
                page_count = record_count / page_limit + 1;
            }

//        if (page_index > 1 && page_index > page_count)
//            page_index = page_count;
        page_index = page_index - 1;
        String limit = String.format(" limit %d,%d ", page_index * page_limit, page_limit);
        pageInfo = jJson.set1KeyValueStr(pageInfo, "page_count", String.valueOf(page_count));//总页数
        pageInfo = jJson.set1KeyValueStr(pageInfo, "record_count", String.valueOf(record_count)); //总条数
        pageInfo = jJson.set1KeyValueStr(pageInfo, "page_index", String.valueOf(page_index + 1));
        pageInfo = jJson.set1KeyValueStr(pageInfo, "page_limit", String.valueOf(page_limit)); //总条数
        String joinData = "";
        joinData = jJson.set1KeyValueStr(joinData, "sql", sql + limit);
        joinData = jJson.set1KeyValueStr(joinData, "pageInfo", pageInfo);
        return joinData;
    }

    /**
     * 直接将sql语句放到from里面
     *
     * @param sql
     * @param pageInfo
     * @return
     */
    public static String getpageinfo(String sql, String pageInfo) {
        String sqlpage = "select count(*) from (%s)as countall";
        sqlpage = String.format(sqlpage, sql);
        int page_index = Integer.valueOf(jJson.get1KeyValue(pageInfo, "page_index", "1"));//第几页
        int page_limit = Integer.valueOf(jJson.get1KeyValue(pageInfo, "page_limit", "10"));//每页获取多少
        if (page_index <= 1) {
            page_index = 1;
        }
        ResultSet rs01 = sqlSelect(sqlpage);
        int record_count = getSqlOneValueInt(sqlpage, 1);
        int page_count = 0;
        if (record_count != 0)
            if (record_count % page_limit == 0)
                page_count = record_count / page_limit;
            else {
                page_count = record_count / page_limit + 1;
            }
        page_index = page_index - 1;
        String limit = String.format(" limit %d,%d ", page_index * page_limit, page_limit);
        pageInfo = jJson.set1KeyValueStr(pageInfo, "page_count", String.valueOf(page_count));//总页数
        pageInfo = jJson.set1KeyValueStr(pageInfo, "record_count", String.valueOf(record_count)); //总条数
        pageInfo = jJson.set1KeyValueStr(pageInfo, "page_index", String.valueOf(page_index + 1));
        pageInfo = jJson.set1KeyValueStr(pageInfo, "page_limit", String.valueOf(page_limit)); //总条数
        String joinData = "";
        joinData = jJson.set1KeyValueStr(joinData, "sql", sql + limit);
        joinData = jJson.set1KeyValueStr(joinData, "pageInfo", pageInfo);
        return joinData;

/**
 *     "pageInfo": {
 "            page_count": "3",
 "            record_count": "30",
 "            page_index": "1",
 "            page_limit": "10"}
 */
    }
}
