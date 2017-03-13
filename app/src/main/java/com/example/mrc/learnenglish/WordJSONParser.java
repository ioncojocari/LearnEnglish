package com.example.mrc.learnenglish;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



public class WordJSONParser {
    public static List<Word> parseData(String data){
        List<Word> result=new ArrayList<>();
        try {
            JSONArray jsonArray=new JSONArray(data);
            Log.v("trt","json array size:"+jsonArray.length());
            for(int i=0;i<jsonArray.length();i++){
                JSONObject object=jsonArray.getJSONObject(i);
                Word word=new Word();

                word.setId(object.getInt("id"));
                word.setWord(object.getString("word"));
                if(object.optString("russianTranslation")!=null) {
                    word.setRussianTranslation(object.optString("russianTranslation"));
                }
                if(object.optString("romanianTranslation")!=null) {
                    word.setRomanianTranslation(object.optString("romanianTranslation"));
                }
                    if(object.optString("ukrainianTranslation")!=null) {
                        word.setUkrainianTranslation(object.optString("ukrainianTranslation"));
                    }
                if(object.optInt("first_false")!=0){
                    word.setFirst_false(object.getInt("first_false"));
                }
                if(object.optInt("second_false")!=0){
                    word.setSecond_false(object.getInt("second_false"));
                }

              //  word.setRussianTranslation(object.getString("russianTranslation"));
             //     word.setUkraineTranslation(object.getString("ukraineTranslation"));
                result.add(word);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }
    public static List<WordFromDb> parse(String data){
        List<WordFromDb> result=new ArrayList<>();
        try {
            JSONArray jsonArray=new JSONArray(data);
            Log.v("trt","json array size:"+jsonArray.length());
            for(int i=0;i<jsonArray.length();i++){
                JSONObject object=jsonArray.getJSONObject(i);
                WordFromDb word=new WordFromDb();

                word.id=(object.getInt("id"));
                if(object.optBoolean("add") ) {
                    word.add = (object.getBoolean("add"));
                }
                if(object.optBoolean("delete") ) {
                    word.delete = (object.getBoolean("delete"));
                }
                if(object.optBoolean("update") ) {
                    word.update = (object.getBoolean("update"));
                }
                if(object.optString("word")!=null) {
                    word.word = (object.getString("word"));
                }

                if(object.optString("russianTranslation")!=null) {
                    word.russianTranslation=(object.getString("russianTranslation"));
                }
                if(object.optString("romanianTranslation")!=null) {
                    word.romanianTranslation=(object.getString("romanianTranslation"));
                }
                if(object.optString("ukrainianTranslation")!=null) {
                    word.ukrainianTranslation=(object.getString("ukrainianTranslation"));
                }
                if(object.optString("uk_link")!=null) {
                    word.uk_link=(object.getString("uk_link"));
                }
                if(object.optString("us_link")!=null) {
                    word.us_link=(object.getString("us_link"));
                }
                if(object.optInt("first_false")!=0){
                    word.first_false=(object.getInt("first_false"));
                }
                if(object.optInt("second_false")!=0){
                    word.second_false=(object.getInt("second_false"));
                }

                //  word.setRussianTranslation(object.getString("russianTranslation"));
                //     word.setUkraineTranslation(object.getString("ukraineTranslation"));
                result.add(word);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }
}
