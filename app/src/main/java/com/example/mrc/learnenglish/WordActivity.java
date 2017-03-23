package com.example.mrc.learnenglish;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



import Enums.LANGUAGE;

import io.realm.Realm;


public class WordActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String WORDKEY="WORD";
    public static final String SEARCHMODE="SEARCHMODE";
    Toolbar toolbar;
    private static String wordStr;
    private  boolean searchMode=false;
    int id;
    Button nextButton;
    Button previousButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent= getIntent();
        Bundle bundle =intent.getExtras();
        if(bundle!=null && !bundle.isEmpty()) {
            if (bundle.containsKey(WORDKEY)) {
                wordStr = bundle.getString(WORDKEY);
                getIntent().removeExtra(WORDKEY);
            }
            if(bundle.containsKey(SEARCHMODE)){
                searchMode=bundle.getBoolean(SEARCHMODE);
            }
        }
        setContentView(R.layout.word_layout);
        setTranslation();
    }
    private void setTranslation(){
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nextButton= (Button) findViewById(R.id.goToNextWord);
        previousButton= (Button) findViewById(R.id.goToPreviousWord);
        nextButton.setOnClickListener(this);
        previousButton.setOnClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Realm realm=Realm.getDefaultInstance();
        realm.beginTransaction();
        Word word=realm.where(Word.class).equalTo("word",wordStr).findFirst();
        realm.commitTransaction();

        String translationText=word.getTranslation(this);
        TextView wordTextView=(TextView)findViewById(R.id.wordTextView);
        wordTextView.setText(wordStr);
        TextView translationTextView=(TextView)findViewById(R.id.translationTextView);
        translationTextView.setText(translationText);
        if(!searchMode) {
            checkNextOrPreviousWordExist(word.getId());
        }else{
            previousButton.setVisibility(View.INVISIBLE);
            nextButton.setVisibility(View.INVISIBLE);
        }
        checkIfSongsExist();

    }
    public boolean isLastWord(int id){
        boolean result=false;

        if(Data.getNr(id)==(Data.getData().size()-1)){
            result=true;
        }

        return result;
    }
    public boolean isFirstWord( int id){
        boolean result=false;

        if(Data.getNr(id)==0){
            result=true;
        }

        return result;
    }
    public void checkNextOrPreviousWordExist(int id){
        this.id=id;
        if(isFirstWord(this.id)){

            previousButton.setVisibility(View.INVISIBLE);
            //go to previous should be invisible
        }else{

            previousButton.setVisibility(View.VISIBLE);
            //go to previous should be visible
        }

        if(isLastWord(id)){
            nextButton.setVisibility(View.INVISIBLE);
//go to next should be invisible
        }else{
            nextButton.setVisibility(View.VISIBLE);
//go to next should be visible
        }

    }


    private  boolean checkIfSongExist(String name,String pronunciation){
        return Music.songExists(name,pronunciation,WordActivity.this);
    }
    private  void checkIfSongsExist(){
        TextView wordTextView=(TextView)findViewById(R.id.wordTextView);
        String name=wordTextView.getText().toString();
        boolean ukPronExist=checkIfSongExist(name,"uk");
        boolean usPronExist=checkIfSongExist(name,"us");
        Button ukButton=(Button)findViewById(R.id.ukPronunciationButton);
        Button usButton=(Button)findViewById(R.id.usPronunciationButton);
        if(ukPronExist==false){

            ukButton.setVisibility(View.INVISIBLE);
        }else{
            ukButton.setVisibility(View.VISIBLE);
        }
        if(usPronExist==false){

            usButton.setVisibility(View.INVISIBLE);
        }else{
            usButton.setVisibility(View.VISIBLE);
        }
    }
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
    public void playMusic(View v){
        String pronunciation;
        if(v.getId()==R.id.ukPronunciationButton){
            pronunciation="uk";
        }else{
            pronunciation="us";
        }
        TextView wordTextView=(TextView)findViewById(R.id.wordTextView);
        String word=wordTextView.getText().toString();
        Music.play(word,pronunciation,WordActivity.this);
    }

    @Override
    protected void onResume() {
        Log.v("tag","onResume");
        if(MainActivity.languageChanged){
            setTranslation();

        }
        super.onResume();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void showSettings(){
        Intent intent=new Intent(this,ChooseLanguage.class);
        startActivity(intent);
    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.goToNextWord){
            goToNextWord();
        }else if(v.getId()==R.id.goToPreviousWord){
            goToPreviousWord();
        }
    }
    public void goToNextWord(){
        int nr = Data.getNr(id) + 1;
        if(nr>0 && nr<Data.getData().size()) {
            String word = Data.getData().get(nr).getWord();
            wordStr = word;
            setTranslation();
        }

    }
    public void goToPreviousWord() {
        int nr = Data.getNr(id) - 1;
        if(nr>=0 && nr<Data.getData().size()) {
            String word = Data.getData().get(nr).getWord();
            wordStr = word;
            setTranslation();
        }
    }
}
