package com.example.demo.util;

import lombok.Data;
import net.sf.json.JSONObject;

import java.util.Objects;
@Data
public class ToJsonObject {

    /**
     * code=1,成功，code=-1,失败
     * @param ob
     * @param msg
     * @param code
     * @return
     */
    static public JSONObject getJSONObject(Object ob,String msg,Integer code){
        JSONObject jsonObject=new JSONObject();
        if(Objects.nonNull(ob)){
            jsonObject.put("data",ob);
            jsonObject.put("msg",msg);
            jsonObject.put("code",code);
            return jsonObject;
        }
        jsonObject.put("data","");
        jsonObject.put("msg",msg);
        return jsonObject;
    }
    static public JSONObject getSuccessJSONObject(Object ob){
        JSONObject jsonObject=new JSONObject();
        if(Objects.nonNull(ob)){
            jsonObject.put("data",ob);
            jsonObject.put("msg","success");
            jsonObject.put("code",1);
            return jsonObject;
        }
        jsonObject.put("data","");
        jsonObject.put("msg","success");
        jsonObject.put("code",1);
        return jsonObject;
    }
    static public JSONObject getSuccessJSONObject(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("data",null);
        jsonObject.put("msg","success");
        jsonObject.put("code",1);
        return jsonObject;
    }
    static public JSONObject getFailJSONObject(Object ob){
        JSONObject jsonObject=new JSONObject();
        if(Objects.nonNull(ob)){
            jsonObject.put("data",ob);
            jsonObject.put("msg","fail");
            jsonObject.put("code",-1);
            return jsonObject;
        }
        jsonObject.put("data","");
        jsonObject.put("msg","fail");
        jsonObject.put("code",-1);
        return jsonObject;
    }
    static public JSONObject getFailJSONObject2(Object ob,String meg){
        JSONObject jsonObject=new JSONObject();
        if(Objects.nonNull(ob)){
            jsonObject.put("data",ob);
            jsonObject.put("msg","fail");
            jsonObject.put("code",-1);
            return jsonObject;
        }
        jsonObject.put("data","");
        jsonObject.put("msg",meg);
        jsonObject.put("code",-1);
        return jsonObject;
    }
}
