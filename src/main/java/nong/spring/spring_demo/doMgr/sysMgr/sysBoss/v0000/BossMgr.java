package nong.spring.spring_demo.doMgr.sysMgr.sysBoss.v0000;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import nong.spring.spring_demo.Entity.RequestEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author TinyNong
 * @emaile 2128076626@qq.com
 * @Date 2017/11/29
 * @Class
 */
@RestController
@RequestMapping(value = "/boss")
public class BossMgr {
    @RequestMapping(value = "/getlist")
    public String getBossList(@RequestBody RequestEntity requestEntity){
        JSONObject object = requestEntity.getPdata();
        System.out.println(object.toString());
        return  object.toString();
    }

}
