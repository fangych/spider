package com.fych;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.fych.util.HttpClientUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class CommonTestPro {

    public static void main(String[] args) {
        String url="http://m.gogobids.com/api.php";
        Map<String,String> param=new HashMap<>();
        param.put("env","prod");
        param.put("userid","1597215568");
        param.put("token","LOGIN_f0ddb189-398c-4c08-a2ea-784f099750e4");
        param.put("mobileos","android");
        param.put("mobileosversion","5.0");
        param.put("vid","pza2md0166vmwc8w6d3c");
        param.put("url","100");
        param.put("method","get");
        param.put("limit","20");
        String endTime=String.valueOf(System.currentTimeMillis());
        param.put("end_time",endTime);
        param.put("offset","200");
        JSONObject jsonpObject=new JSONObject();
        jsonpObject.putAll(param);
        String result=HttpClientUtil.sendPost(url,jsonpObject);
        System.out.println("result="+result);


    }
}
