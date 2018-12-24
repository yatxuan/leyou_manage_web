package com.calabashbrothers.common.util;

import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;

/**
 *  网络工具类
 *  注：与网络相关的操作封装到此类中
 */
public class HttpUtil {

    /**
     *  向网络上提供的api接口发送数据
     *  url：网络上的api接口路径  https://way.jd.com/yingyan/telvertify
     *  map：
     *       http://www.x.com:8080/checkCookie
     *       key：是网络api接口需要的参数名称   cookieName   cookieValue
     *       value: 是该参数名称对应的值   412726199101070819  asdf
     *
     *      HttpUtil.sendHttpRequest("http://localhost:8080/checkCookie",map)
     */
    public static String sendHttpRequest(String url, Map<String,String> map) throws IOException {

        //1.定义需要访问的地址
        URL u = new URL(url);

        //2.开启连接
        HttpURLConnection conn = (HttpURLConnection) u.openConnection();

        //3.设置请求的方式
        conn.setRequestMethod("POST");

        //4.设置需要来输出参数
        conn.setDoOutput(true);

        //5.拼接参数的信息
        StringBuilder builder = new StringBuilder();
        if(map!=null&&map.size()>0){
            for (Map.Entry<String,String> entry:map.entrySet()){
                builder.append("&").append(entry.getKey()).append("=").append(entry.getValue());
            }
        }

        //6.通过输出流写出参数
        conn.getOutputStream().write(builder.substring(1).toString().getBytes("UTF-8"));

        //7.发起请求
        conn.connect();

        //8.返回出对象（api接口）响应的信息
        return StreamUtils.copyToString(conn.getInputStream(), Charset.forName("UTF-8"));
    }

}
