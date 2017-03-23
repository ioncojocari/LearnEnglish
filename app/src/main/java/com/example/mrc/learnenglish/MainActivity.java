package com.example.mrc.learnenglish;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import Enums.LANGUAGE;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity  {
    android.support.v7.widget.Toolbar toolbar;
    public static boolean languageChanged=false;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
        Data.initiate();
        UpdateDataTask.checkLastData((ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE),this);
        checkIfLanguageChoosed();
        ChooseLanguage.setChoosedLanguage(this);
        setContentView(R.layout.activity_main);
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
//    public void learnAllWords(View v){
//        Realm realm =Realm.getDefaultInstance();
//        realm.beginTransaction();
//        realm.delete(UnlearnedWord.class);
//        realm.delete(LearnedWord.class);
//        List<LearnedWord> learnedWords=new ArrayList<LearnedWord>();
//        for(Word word :Data.getData()){
//            LearnedWord learnedWord=new LearnedWord();
//            learnedWord.setId(word.getId());
//            learnedWords.add(learnedWord);
//        }
//        realm.copyToRealm(learnedWords);
//        realm.commitTransaction();
//    }
    protected void onStart() {
        super.onStart();
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
//    public void clearData(View view){
//        Realm realm=Realm.getDefaultInstance();
//        realm.beginTransaction();
//        realm.delete(UnlearnedWord.class);
//        realm.delete(LearnedWord.class);
//        realm.commitTransaction();
//    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings :showSettings();
                break;
            case R.id.action_search:search();
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    private void search() {
        Intent intent=new Intent(this,Search.class);
        startActivity(intent);
    }
    private void showSettings(){
        Intent intent=new Intent(this,ChooseLanguage.class);
        startActivity(intent);
    }

    protected void onResume() {
        super.onResume();
        if(languageChanged){
            ChooseLanguage.setChoosedLanguage(this);
            languageChanged=false;
            setContentView(R.layout.activity_main);
            toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
        }
        checkIfWordsExist();
    }

    public void checkIfLanguageChoosed(){
      LANGUAGE currentLanguage=  LANGUAGE.getCurrentLanguage(this);
        if(currentLanguage==LANGUAGE.NONE){
            Intent intent=new Intent(this,ChooseLanguage.class);
            startActivity(intent);
        }
    }

    public void checkIfWordsExist(){
        Realm realm=Realm.getDefaultInstance();
        realm.beginTransaction();
        int nr=realm.where(Word.class).findAll().size();
        realm.commitTransaction();
        if(nr==0){
            Intent intent=new Intent(this,DecompileActivity.class);
            startActivity(intent);
        }
    }
    public void goToTopWords(View v){
        Intent intent=new Intent(this,Top3LevelWordsActivity.class);
        startActivity(intent);
    }

    public void goToLearnWords(View v){
        Intent intent =new Intent (this,LearnWordsActivity.class);
        startActivity(intent);
    }

//    public void clearAllData(View view) {
//        LANGUAGE.saveLanguage(LANGUAGE.NONE,this);
//        Realm realm=Realm.getDefaultInstance();
//        realm.beginTransaction();
//        realm.delete(UnlearnedWord.class);
//        realm.delete(Word.class);
//        realm.delete(LearnedWord.class);
//
//        realm.commitTransaction();
//    }

}
