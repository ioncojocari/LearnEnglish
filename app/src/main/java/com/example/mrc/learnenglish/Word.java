package com.example.mrc.learnenglish;

import android.content.Context;

import java.io.Serializable;
import java.util.ArrayList;

import Enums.LANGUAGE;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mrT on 20.12.2016.
 */

public class Word extends RealmObject  implements Serializable{
    @PrimaryKey
    private String word;

   private int id;

    private String  russianTranslation="";
    private String romanianTranslation="";
    private String ukrainianTranslation="";
    private int first_false;

    public int getFirst_false() {
        return first_false;
    }

    public void setFirst_false(int first_false) {
        this.first_false = first_false;
    }

    public int getSecond_false() {
        return second_false;
    }

    public void setSecond_false(int second_false) {
        this.second_false = second_false;
    }

    private int second_false;


    public String getRussianTranslation() {
        return russianTranslation;
    }

    public void setRussianTranslation(String russianTranslation) {
        this.russianTranslation = russianTranslation;
    }

    public String getUkrainianTranslation() {
        return ukrainianTranslation;
    }

    public void setUkrainianTranslation(String ukrainianTranslation) {
        this.ukrainianTranslation = ukrainianTranslation;
    }

    public String getRomanianTranslation() {
        return romanianTranslation;
    }

    public void setRomanianTranslation(String romanianTranslation) {
        this.romanianTranslation = romanianTranslation;
    }

    public void setId(int id){
        this.id=id;
    }
    public int getId(){
        return id;
    }
    public  static TestGenerator getTestGenerator(int wordId,Context context){
        Word word=Data.getWordById(wordId);

        TestGenerator result=new TestGenerator();
        result.setId(word.getId());
        result.setWord(word.getWord());
        String[] posibleAnswer=new String[3];
        int correctPos=TestGenerator.random.nextInt(3);
        result.setCorrectAnswer(correctPos);
        int secondPos=TestGenerator.random.nextInt(3);
        while (correctPos==secondPos){
            secondPos=TestGenerator.random.nextInt(3);
        }
        int thirdPos=TestGenerator.random.nextInt(3);
        while (thirdPos==correctPos || thirdPos==secondPos){
            thirdPos=TestGenerator.random.nextInt(3);
        }
        posibleAnswer[correctPos]=LearnNewWordsActivity.getFirstTwoTranslation(word.getTranslation(context));
        posibleAnswer[secondPos]=LearnNewWordsActivity.getFirstTwoTranslation(Data.getTranslationById(word.getFirst_false(),context));
        posibleAnswer[thirdPos]=LearnNewWordsActivity.getFirstTwoTranslation(Data.getTranslationById(word.getSecond_false(),context));
        result.setPosibleAnswer(posibleAnswer);









        return result;
    }





    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
    public String getTranslation(Context context){
        LANGUAGE currentLanguage=LANGUAGE.getCurrentLanguage(context);
        if(currentLanguage ==LANGUAGE.ROMANIAN) {
            return getRomanianTranslation();
        }else if(currentLanguage==LANGUAGE.RUSSIAN){
            return getRussianTranslation();}
        else if(currentLanguage==LANGUAGE.UKRANIAN){
            return getUkrainianTranslation();}
        return "";

        }







}
