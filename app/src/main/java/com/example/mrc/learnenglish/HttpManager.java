package com.example.mrc.learnenglish;

/**
 * Created by mrT on 21.12.2016.
*/
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpManager {
    public static StringBuffer getData(String urlString) {
        BufferedReader rd = null;
        StringBuffer chaine = new StringBuffer("");
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("User-Agent", "");
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            rd = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while ((line = rd.readLine()) != null) {
                chaine.append(line);
            }
            return chaine;
        } catch (IOException e) {
            // Writing exception to log
            e.printStackTrace();
        } finally {
            if (rd != null) {
                try {
                    rd.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    public static String getData(RequestPackage requestpackage){

        BufferedReader rd = null;
        StringBuffer chaine = new StringBuffer("");
        try {
            String uri=requestpackage.getUri();
            if(requestpackage.getRequestmethod()== RequestPackage.RequestMethod.GET){
                uri+="?"+requestpackage.getStringParams();
            };
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("User-Agent", "");
            connection.setRequestMethod(requestpackage.getRequestmethod().getName());
            if(requestpackage.getRequestmethod()== RequestPackage.RequestMethod.POST){
                connection.setDoInput(true);
                JSONObject jsonObject=new JSONObject(requestpackage.getData());
                String params=jsonObject.toString();
                OutputStreamWriter writer=new OutputStreamWriter(connection.getOutputStream());
                writer.write(params);
                writer.flush();
            }
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            rd = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while ((line = rd.readLine()) != null) {
                chaine.append(line);
            }
            return chaine.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (rd != null) {
                try {
                    rd.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
