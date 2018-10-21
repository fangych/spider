package com.fych.util;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


/**
 * @ClassName: HttpClientUtil
 * @Description: TODO(HttpClient工具类)
 * @author wangxy
 * @date 2018年5月8日 下午5:23:39
 * @version 1.0
 */
public class HttpClientUtil {

    // 默认字符集
    private static final String ENCODING = "UTF-8";

    /**
     * @Title: sendPost
     * @Description: TODO(发送post请求)
     * @param url 请求地址
     * @param headers 请求头
     * @param data 请求实体
     * @param encoding 字符集
     * @author wangxy
     * @return String
     * @date 2018年5月10日 下午4:36:17
     * @throws
     */
    public static String sendPost(String url,Map<String, String> headers, JSONObject data, String encoding) {
        // 请求返回结果
        String resultJson = null;
        // 创建Client
        CloseableHttpClient client = HttpClients.createDefault();
        // 创建HttpPost对象
        HttpPost httpPost = new HttpPost();

        try {
            // 设置请求地址
            httpPost.setURI(new URI(url));
            // 设置请求头
            if (headers != null) {
                Header[] allHeader = new BasicHeader[headers.size()];
                int i = 0;
                for (Map.Entry<String, String> entry: headers.entrySet()){
                    allHeader[i] = new BasicHeader(entry.getKey(), entry.getValue());
                    i++;
                }
                httpPost.setHeaders(allHeader);
            }
            // 设置实体
            httpPost.setEntity(new StringEntity(JSON.toJSONString(data)));
            // 发送请求,返回响应对象
            CloseableHttpResponse response = client.execute(httpPost);
            System.out.println("response=========="+response);
            // 获取响应状态
            int status = response.getStatusLine().getStatusCode();
            if (status == HttpStatus.SC_OK) {
                // 获取响应结果
                resultJson = EntityUtils.toString(response.getEntity(), encoding);
            } else {
               System.out.println("响应失败，状态码：" + status);
            }

        } catch (Exception e) {
           e.printStackTrace();
        } finally {
            httpPost.releaseConnection();
        }
        return resultJson;
    }

    /**
     * @Title: sendPost
     * @Description: TODO(发送post请求，请求数据默认使用json格式，默认使用UTF-8编码)
     * @param url 请求地址
     * @param data 请求实体
     * @author wangxy
     * @return String
     * @date 2018年5月10日 下午4:37:28
     * @throws
     */
    public static String sendPost(String url, JSONObject data) {
        // 设置默认请求头
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        String cookie="Hm_lvt_cde840e5f94437dfa1178c5938ec4eb9=1539178621,1539504791; Hm_lpvt_cde840e5f94437dfa1178c5938ec4eb9=";
        String millis=String.valueOf(System.currentTimeMillis()-600000);
        String finalCookie=cookie+millis.substring(0,millis.length()-3);
        System.out.println(finalCookie);
        headers.put("Cookie", finalCookie);

        return sendPost(url, headers, data, ENCODING);
    }

    /**
     * @Title: sendPost
     * @Description: TODO(发送post请求，请求数据默认使用json格式，默认使用UTF-8编码)
     * @param url 请求地址
     * @param params 请求实体
     * @author wangxy
     * @return String
     * @date 2018年5月10日 下午6:11:05
     * @throws
     */
    public static String sendPost(String url,Map<String,Object> params){
        // 设置默认请求头
        Map<String, String> headers = new HashMap<>();
        headers.put("content-type", "application/json");
        // 将map转成json
        JSONObject data = JSONObject.parseObject(JSON.toJSONString(params));
        return sendPost(url,headers,data,ENCODING);
    }

    /**
     * @Title: sendPost
     * @Description: TODO(发送post请求，请求数据默认使用UTF-8编码)
     * @param url 请求地址
     * @param headers 请求头
     * @param data 请求实体
     * @author wangxy
     * @return String
     * @date 2018年5月10日 下午4:39:03
     * @throws
     */
    public static String sendPost(String url, Map<String, String> headers, JSONObject data) {
        return sendPost(url, headers, data, ENCODING);
    }

    /**
     * @Title: sendPost
     * @Description:(发送post请求，请求数据默认使用UTF-8编码)
     * @param url 请求地址
     * @param headers 请求头
     * @param params 请求实体
     * @author wangxy
     * @return String
     * @date 2018年5月10日 下午5:58:40
     * @throws
     */
    public static String sendPost(String url,Map<String,String> headers,Map<String,String> params){
        // 将map转成json
        JSONObject data = JSONObject.parseObject(JSON.toJSONString(params));
        return sendPost(url,headers,data,ENCODING);
    }

    /**
     * @Title: sendGet
     * @Description: TODO(发送get请求)
     * @param url 请求地址
     * @param params 请求参数
     * @param encoding 编码
     * @author wangxy
     * @return String
     * @date 2018年5月14日 下午2:39:01
     * @throws
     */
    public static String sendGet(String url,Map<String,Object> params,String encoding){
        // 请求结果
        String resultJson = null;
        // 创建client
        CloseableHttpClient client = HttpClients.createDefault();
        // 创建HttpGet
        HttpGet httpGet = new HttpGet();
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            // 封装参数
            if(params != null){
                for (String key : params.keySet()) {
                    builder.addParameter(key, params.get(key).toString());
                }
            }
            URI uri = builder.build();
            // 设置请求地址
            httpGet.setURI(uri);
            // 发送请求，返回响应对象
            CloseableHttpResponse response = client.execute(httpGet);
            // 获取响应状态
            int status = response.getStatusLine().getStatusCode();
            if(status == HttpStatus.SC_OK){
                // 获取响应数据
                resultJson = EntityUtils.toString(response.getEntity(), encoding);
            }else{
                System.out.println("响应码code:"+status);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpGet.releaseConnection();
        }
        return resultJson;
    }
    /**
     * @Title: sendGet
     * @Description: TODO(发送get请求)
     * @param url 请求地址
     * @param params 请求参数
     * @author wangxy
     * @return String
     * @date 2018年5月14日 下午2:32:39
     * @throws
     */
    public static String sendGet(String url,Map<String,Object> params){
        return sendGet(url,params,ENCODING);
    }
    /**
     * @Title: sendGet
     * @Description: TODO(发送get请求)
     * @param url 请求地址
     * @author wangxy
     * @return String
     * @date 2018年5月14日 下午2:33:45
     * @throws
     */
    public static String sendGet(String url){
        return sendGet(url,null,ENCODING);
    }
}