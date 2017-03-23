package com.example.mrc.learnenglish;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import io.realm.Realm;
import io.realm.RealmResults;

public class LearnWordsActivity extends AppCompatActivity {
    private int maxAmountOfUnknownWords=10;
    private int amounOfNewWords=5;
    public static boolean finishActivity=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("trt","onCreate LearnWordsActivity");
    //    setContentView(R.layout.activity_learn_words);
        //i should show 5 new words then i should repeat it by asking which is translation and i should also
        //ask the words that wasn't correct if the nr of words that wasn't learned is more than 5
        //i will repeatly ask the unlearned words untill it will be less than 2
        //
//        Realm realm=Realm.getDefaultInstance();
//        realm.beginTransaction();
//        RealmResults<UnlearnedWord> unlearnedWords=realm.where(UnlearnedWord.class).findAll();
//        RealmResults<LearnedWord> learnedWords=realm.where(LearnedWord.class).findAll();
//        realm.commitTransaction();
//        int lastId=0;
//        int lastNr=LearnNewWordsActivity.noLastNr;
//        if(learnedWords.size()>0){
//            lastId= learnedWords.max("id").intValue();
//            lastNr=Data.getNr(lastId);
//
//        }
//        if(unlearnedWords.size()>0){
//            if(unlearnedWords.max("id").intValue()>lastId){
//                lastId=unlearnedWords.max("id").intValue();
//                lastNr=Data.getNr(lastId);
//            }
//        }
//
//
//        if(unlearnedWords.size()<maxAmountOfUnknownWords){
//            learnNewWords(lastNr,amounOfNewWords);
//        }else{
//            repeatWords(unlearnedWords);
//        }
       // setContentView(R.layout.wtf);

    }

    @Override
        protected void onResume() {
        if(finishActivity){
            finishActivity=false;
            finish();
        }else {
            Log.v("trt", "onResume LearnWordsActivity");
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            RealmResults<UnlearnedWord> unlearnedWords = realm.where(UnlearnedWord.class).findAll();
            RealmResults<LearnedWord> learnedWords = realm.where(LearnedWord.class).findAll();

            realm.commitTransaction();
            int lastId = 0;
            int lastNr = LearnNewWordsActivity.noLastNr;
            if (learnedWords.size() > 0) {
                lastId = learnedWords.max("id").intValue();
                lastNr = Data.getNr(lastId);

            }
            if (unlearnedWords.size() > 0) {
                if (unlearnedWords.max("id").intValue() > lastId) {
                    lastId = unlearnedWords.max("id").intValue();
                    lastNr = Data.getNr(lastId);
                }
            }
            if (unlearnedWords.size() < maxAmountOfUnknownWords && (learnedWords.size() + unlearnedWords.size()) != Data.getData().size()) {
                learnNewWords(lastNr, amounOfNewWords);
            } else if (unlearnedWords.size() >= maxAmountOfUnknownWords) {
                repeatWords(unlearnedWords);
            } else if ((learnedWords.size()) == Data.getData().size()) {
                showVictory();
                //i need to show layout that this person have learned all words
            }
        }
        super.onResume();
    }
    private void showVictory(){
        setContentView(R.layout.victory);
    }
    public void clickYes(View view){
        Realm realm=Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.delete(UnlearnedWord.class);
        realm.delete(LearnedWord.class);

        realm.commitTransaction();
        onResume();
    }
    public void clickBack(View view){
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();

    }
    private   void learnNewWords(int lastLearnedId, int amounOfNewWords){
        Intent intent=new Intent(this,LearnNewWordsActivity.class);
        intent.putExtra(LearnNewWordsActivity.AMOUNTOFNEWWORDSKEY,amounOfNewWords);
        intent.putExtra(LearnNewWordsActivity.LASTLEARNEDIDKEY,lastLearnedId);
        startActivity(intent);
    }
    private void repeatWords(RealmResults<UnlearnedWord> unlearnedWords){
        if(unlearnedWords.size()>0) {
            int[] wordsId = new int[unlearnedWords.size()];
            for (int i = 0; i < unlearnedWords.size(); i++) {
                wordsId[i] = unlearnedWords.get(i).getId();
            }
            Intent intent = new Intent(this, RepeatWordsActivity.class);
            intent.putExtra(RepeatWordsActivity.IDSKEY, wordsId);
            startActivity(intent);

        }
    }
}
