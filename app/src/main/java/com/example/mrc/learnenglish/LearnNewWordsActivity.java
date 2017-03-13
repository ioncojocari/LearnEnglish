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

public class LearnNewWordsActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String LASTLEARNEDIDKEY="LASTNR";
    public static final String AMOUNTOFNEWWORDSKEY="AMOUNT";
    Toolbar toolbar;
    private  static int lastNr;
    private static int amountOfNewWords;
    Button nextButton;
    Button previousButton;
    TextView wordTextView;
    TextView translationTextView;

    private static int remains=0;
    public final static int noLastNr=-1;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Data.initiate();
        Intent intent=getIntent();
        Bundle data=intent.getExtras();

        if(data.containsKey(AMOUNTOFNEWWORDSKEY)) {

            if ( remains==0) {
                if(data.containsKey(LASTLEARNEDIDKEY)){
                    lastNr=data.getInt(LASTLEARNEDIDKEY);
                }
                amountOfNewWords = data.getInt(AMOUNTOFNEWWORDSKEY);
                remains = amountOfNewWords;
            }else{
                lastNr--;
                remains++;
            }

        }

        setContentView(R.layout.activity_learn_new_words);


        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nextButton= (Button) findViewById(R.id.goToNextWord);
        previousButton= (Button) findViewById(R.id.goToPreviousWord);
        previousButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        wordTextView= (TextView) findViewById(R.id.wordTextView);
        translationTextView= (TextView) findViewById(R.id.translationTextView);

        showNextWord();

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.learn_words_toolbar_menu, menu);




        // Configure the search info and add any event listeners...

        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings :showSettings();
                break;

        }
        return super.onOptionsItemSelected(item);

    }
    public void showSettings(){
        Intent intent=new Intent(this,ChooseLanguage.class);

        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        LearnWordsActivity.finishActivity=true;
        finish();
        super.onBackPressed();

    }

    private void showNextWord( ){
        lastNr++;
        remains--;





            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();


            if (realm.where(UnlearnedWord.class).equalTo("id", Data.getData().get(lastNr).getId()).findFirst() != null) {
                Log.v("txt", "this word is  unlearned");


            } else {
                if(lastNr>0) {
                    UnlearnedWord unlearnedWord = new UnlearnedWord();
                    unlearnedWord.setId(Data.getData().get(lastNr-1).getId());
                    if(realm.where(UnlearnedWord.class).equalTo("id",Data.getData().get(lastNr-1).getId()).findAll().size()==0) {
                        realm.copyToRealm(unlearnedWord);
                    }

                }
            }
            ;

            realm.commitTransaction();
            showWord(lastNr);
        if(remains==-1) {
            remains = 0;
            finish();
        }

    }

    private void showPreviousWord( ){
        lastNr--;
        remains++;
        showWord(lastNr);

    }

    @Override
    protected void onResume() {
        Log.v("trt","onResume Learn New Words");
        if(MainActivity.languageChanged) {


            showWord(lastNr);
        }
        super.onResume();
    }

    private void showWord(int nr){

//        if(remains<=0){
//            nextButton.setVisibility(View.INVISIBLE);
//        }else{
//            nextButton.setVisibility(View.VISIBLE);
//        }
        if(remains+1>=amountOfNewWords){
            previousButton.setVisibility(View.INVISIBLE);
        }else{
            previousButton.setVisibility(View.VISIBLE);
        }
        String translation="";
        Realm realm=Realm.getDefaultInstance();
        realm.beginTransaction();
        Word word=Data.getData().get(nr);
        String wordText=word.getWord();
        LANGUAGE currentLanguage=LANGUAGE.getCurrentLanguage(this);
        if(currentLanguage==LANGUAGE.ROMANIAN){
             translation=word.getRomanianTranslation();
        }else if(currentLanguage==LANGUAGE.RUSSIAN){
            translation=word.getRussianTranslation();
        }else if(currentLanguage==LANGUAGE.UKRANIAN){
            translation=word.getUkrainianTranslation();
        }
        translation=getFirstTwoTranslation(translation);
        translationTextView.setText(translation);
        realm.commitTransaction();
        wordTextView.setText(wordText);
        checkIfSongsExist();
    }
    private  boolean checkIfSongExist(String name,String pronunciation){
        return Music.songExists(name,pronunciation,LearnNewWordsActivity.this);
    }
    private  void checkIfSongsExist(){
        TextView wordTextView=(TextView)findViewById(R.id.wordTextView);
        String name=wordTextView.getText().toString();
        boolean ukPronExist=checkIfSongExist(name,"uk");
        Log.v("trt","ukPronExist "+ukPronExist);
        boolean usPronExist=checkIfSongExist(name,"us");
        Log.v("trt","usPronExist "+usPronExist);
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
    public void playMusic(View v){
        String pronunciation;
        if(v.getId()==R.id.ukPronunciationButton){
            pronunciation="uk";
        }else{
            pronunciation="us";
        }
        TextView wordTextView=(TextView)findViewById(R.id.wordTextView);
        String word=wordTextView.getText().toString();
        Music.play(word,pronunciation,LearnNewWordsActivity.this);
    }
    public static String getFirstTwoTranslation(String text){
        String lineSeparator=System.getProperty("line.separator");
        String[] words=text.split(lineSeparator);
        String result="";
        if(words.length>1) {
           result=words[0]+lineSeparator+words[1];

        }else if(words.length==1){
            return words[0];
        }
        return result;
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.goToNextWord){
            showNextWord();
        }else{
            showPreviousWord();
        }
    }
}
