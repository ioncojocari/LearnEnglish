package com.example.mrc.learnenglish;

import android.content.Context;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by mrT on 02.02.2017.
 */

public class Data {
    private static ArrayList<Word> allWords=null;
    public static ArrayList<Word> getData(){
        if(allWords==null){
            initiate();
        }
        return allWords;
    }
    public static void update(){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        RealmResults<Word> realmResult = realm.where(Word.class).findAll().sort("id", Sort.ASCENDING);
        if (realmResult.size() > 0) {
            allWords = new ArrayList<Word>();
            for (Word word : realmResult) {
                allWords.add(word);
            }
        }
        realm.commitTransaction();
    }
    public static void initiate(){
        if(allWords==null) {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();

            RealmResults<Word> realmResult = realm.where(Word.class).findAll().sort("id", Sort.ASCENDING);
            if (realmResult.size() > 0) {
                allWords = new ArrayList<Word>();   
                for (Word word : realmResult) {
                    allWords.add(word);
                }
            }
            realm.commitTransaction();
        }
    }
    public static int getNr(int id){
        if(allWords==null){
            initiate();
        }
        if(allWords.size()>0) {
            for (int i = 0; i<allWords.size();i++) {
                if (allWords.get(i).getId() == id) {
                    return i;
                }
            }
        }
        return 0;

    }
    public static String getTranslationById(int id,Context context){
        for(int i=0;i<allWords.size();i++){
            if(allWords.get(i).getId()==id){
                return allWords.get(i).getTranslation(context);
            }
        }
        return "";
    }
    public static Word getWordById(int id){
        for(int i=0;i<allWords.size();i++){
            if(allWords.get(i).getId()==id){
                return allWords.get(i);
            }
        }
        return null;
    }

    public static ArrayList<Integer> getIdsOfPositions(int from,int to){
        from--;
        to--;
      ArrayList<Integer> result=new ArrayList<Integer>();
        if(to>from){
            for(int i=from;i<=to;i++){
                result.add(allWords.get(i).getId());
            }
        }
        return result;
    }
}
