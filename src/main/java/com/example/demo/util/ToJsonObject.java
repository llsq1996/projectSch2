package com.example.demo.util;

import lombok.Data;
import net.sf.json.JSONObject;

import java.util.Objects;
@Data
public class ToJsonObject {

    static public JSONObject getJSONObject(Object ob,String msg){
        JSONObject jsonObject=new JSONObject();
        if(Objects.nonNull(ob)){
            jsonObject.put("data",ob);
            jsonObject.put("msg",msg);
            return jsonObject;
        }
        jsonObject.put("data","");
        jsonObject.put("msg",msg);
        return jsonObject;
    }
}
