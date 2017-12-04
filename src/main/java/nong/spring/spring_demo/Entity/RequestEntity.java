package nong.spring.spring_demo.Entity;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @Author TinyNong
 * @emaile 2128076626@qq.com
 * @Date 2017/11/29
 * @Class
 */
public class RequestEntity {


    /**
     * uuid : a123
     * mdkey : nssyy4cr0zrlv87r
     * auid : 30159591
     * ptype : 20
     * pver : 0000
     * user_id : 99999999
     * user_role : 1,7,5,3
     * pdata : {"opt":"twzx_twzxfl","act":"getlisttree","tw_type_key":"tw_0000006"}
     */

    private String uuid;
    private String auid;
    private String ptype;
    private String pver;
    private String user_id;
    private JSONObject pdata;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getAuid() {
        return auid;
    }

    public void setAuid(String auid) {
        this.auid = auid;
    }

    public String getPtype() {
        return ptype;
    }

    public void setPtype(String ptype) {
        this.ptype = ptype;
    }

    public String getPver() {
        return pver;
    }

    public void setPver(String pver) {
        this.pver = pver;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public JSONObject getPdata() {
        return pdata;
    }

    public void setPdata(JSONObject pdata) {
        this.pdata = pdata;
    }

    public static class PdataBean {
    }
}
