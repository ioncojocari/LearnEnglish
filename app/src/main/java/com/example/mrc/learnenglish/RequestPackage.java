package com.example.mrc.learnenglish;

/**
 * Created by mrT on 10.03.2017.
 */

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;




public class RequestPackage {
    Map<String,String> data=new HashMap<String,String>();
    private String uri;
    public Map<String,String> getData(){
        return data;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public RequestMethod getRequestmethod() {
        return requestmethod;
    }

    public void setRequestmethod(RequestMethod requestmethod) {
        this.requestmethod = requestmethod;
    }

    private RequestMethod requestmethod;
    public void addParam(String paramName,String paramValue){
        data.put(paramName,paramValue);
    }
    public String getStringParams(){
        StringBuilder stringBuilder=new StringBuilder();
        if(!data.isEmpty()) {
           int i=0;
            for (Map.Entry<String,String> entry:data.entrySet()){
                String value="";
                try {
                     value=URLEncoder.encode(entry.getValue(),"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if(!value.isEmpty()) {
                    if (i != 0) {
                        stringBuilder.append("&");
                    } else {
                        i++;
                    }
                    stringBuilder.append(entry.getKey());
                    stringBuilder.append("=");
                    stringBuilder.append(value);
                }

            }


        }
        return stringBuilder.toString();
    }

    public enum RequestMethod{
        POST("POST"),GET("GET");
        String name;
        RequestMethod(String name){
            this.name=name;
        }

        public String getName() {
            return name;
        }
    }

}