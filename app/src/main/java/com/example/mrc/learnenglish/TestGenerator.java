package com.example.mrc.learnenglish;

import android.content.Context;


import java.util.ArrayList;

import java.util.HashSet;
import java.util.Random;


import Enums.LANGUAGE;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;



public class TestGenerator {
    private int id;

    private String word;

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id=id;
    }

  public  static Random random = new Random();

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    private String[] posibleAnswer=new String[3];

    private int correctAnswer;

    public String[] getPosibleAnswer() {
        return posibleAnswer;
    }

    public void setPosibleAnswer(String[] posibleAnswer) {
        this.posibleAnswer = posibleAnswer;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }


//    public static TestGenerator getTestGenerator(int id,Context context){
//
//
//        TestGenerator testGenerator=new TestGenerator();
//        testGenerator.setId(id);
//
//        Realm realm=Realm.getDefaultInstance();
//
//        realm.beginTransaction();
//        Integer[] unneededId=getUnnededIds(context,id,realm);
//        Word currentWord=realm.where(Word.class).equalTo("id",id).findFirst();
//        testGenerator.setWord(currentWord.getWord());
//            RealmQuery<Word> query = realm.where(Word.class);
//
//        if(unneededId != null && unneededId.length > 0) {
//            boolean isFirst = true;
//            query = query.beginGroup();
//            for (int unId : unneededId) {
//                if(!isFirst) {
//                    query = query.or();
//                }
//                query = query.notEqualTo("id", unId);
//                isFirst = false;
//            }
//            query = query.endGroup();
//        }
//
//
//
//
//  //          Log.v("tst","all needed data it trying to be found");
//            RealmResults<Word>   neededWords = query.findAll();
//
//            Word[] randomWords=getTwoRandomWords(neededWords);
//            int correctAnswer=random.nextInt(3);
//            int firstFalseAnswerPosition=random.nextInt(3);
//            int secondFalseAnswerPosition=random.nextInt(3);
//            while(correctAnswer==firstFalseAnswerPosition){
//                firstFalseAnswerPosition=random.nextInt(3);
//            }
//            while((secondFalseAnswerPosition==firstFalseAnswerPosition) ||(secondFalseAnswerPosition==correctAnswer)){
//                secondFalseAnswerPosition=random.nextInt(3);
//            }
//            String[] posAnswer=new String[3];
//        posAnswer[correctAnswer]=LearnNewWordsActivity.getFirstTwoTranslation(currentWord.getTranslation(context));
//            posAnswer[firstFalseAnswerPosition]=LearnNewWordsActivity.getFirstTwoTranslation(randomWords[0].getTranslation(context));
//            posAnswer[secondFalseAnswerPosition]=LearnNewWordsActivity.getFirstTwoTranslation(randomWords[1].getTranslation(context));
//            testGenerator.setPosibleAnswer(posAnswer);
//            testGenerator.setCorrectAnswer(correctAnswer);
//            realm.commitTransaction();
//
//
//
//
//
//
//        return testGenerator;
//    }
//    private static  Integer[] getUnnededIds(Context context,int id,Realm realm){
//        Integer[] learnedIds=getLearnedId(realm);
//
//
//        String lineSeparator=System.getProperty("line.separator");
//        HashSet<Integer> unneededIdHash=new HashSet<Integer>();
//
//
//        if(learnedIds.length>0) {
//            for (Integer learnedId : learnedIds) {
//                unneededIdHash.add(learnedId);
//            }
//        }
//
//        Word currentWord = realm.where(Word.class).equalTo("id",id).findFirst();
//
//        String translation=currentWord.getTranslation(context);
//        LANGUAGE currentLangauge= LANGUAGE.getCurrentLanguage(context);
//        String translationFieldName=currentLangauge.name.toLowerCase()+"Translation";
//
//
//        String[] words=translation.split(lineSeparator);
//        if(words.length>0) {
//            for (int i=0;i<words.length;i++){
//                RealmResults<Word> unnededWords=     realm.where(Word.class).contains(translationFieldName,words[i]).findAll();
//
//                if(unnededWords.size()>0){
//                    for (int j=0;j<unnededWords.size();j++){
//                        unneededIdHash.add(unnededWords.get(j).getId());
//
//
//                    }
//                }
//            }
//
//        }
//
//
//        ArrayList<Integer> unneededIdArr = new ArrayList<Integer>(unneededIdHash);
//
//        if(unneededIdHash.size()>0) {
//            Integer[] unneededId = new Integer[unneededIdHash.size()];
//
//
//            for (int i = 0; i < unneededIdArr.size(); i++) {
//                unneededId[i] = unneededIdArr.get(i);
//            }
//            return unneededId;
//        }
//        return  new Integer[0];
//    }
//    private  static Integer[] getLearnedId(Realm realm){
//
//        Integer[] result;
//
//        RealmResults<LearnedWord> learnedWords=realm.where(LearnedWord.class).findAll();
//
//        result=new Integer[learnedWords.size()];
//        if(result.length>0) {
//            for (int i = 0; i < learnedWords.size(); i++) {
//                result[i]=learnedWords.get(i).getId();
//            }
//        }
//
//
//        return result;
//    }
//    public static Word[] getTwoRandomWords(RealmResults<Word>neededWords){
//        Word[] words=new Word[2];
//
//        int firstNr=random.nextInt(neededWords.size());
//        int secondNr=random.nextInt(neededWords.size());
//        while(firstNr==secondNr){
//            secondNr=random.nextInt(neededWords.size());
//        }
//        words[0]=neededWords.get(firstNr);
//        words[1]=neededWords.get(secondNr);
//        return words;
//    }

}
