package com.example.mrc.learnenglish;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.lang3.SerializationUtils;

import io.realm.Realm;
import knowledge.test.*;

public class RepeatWordsActivity extends AppCompatActivity {

    public static final String IDSKEY="IDS";
    public static final String NEWTEST="NEWTEST";
    private static int currentPos;
    private static int correctAnswer;
    private TextView wordTextView;
    private TextView firstPosition;
    private TextView secondPosition;
    private TextView thirdPosition;
    private Toolbar toolbar;
   private static  int ids[];

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle=getIntent().getExtras();
        if(!bundle.containsKey(NEWTEST)) {
            if (bundle.containsKey(IDSKEY)) {
                if (ids == null || !areArrayEqual(ids, bundle.getIntArray(IDSKEY))) {
                    ids = bundle.getIntArray(IDSKEY);
                    currentPos = 0;
                }
                if (currentPos >= ids.length) {
                    currentPos = 0;
                }
            }
        }else{

            if(bundle.containsKey(IDSKEY)) {
                ids = bundle.getIntArray(IDSKEY);;
                currentPos = 0;
                correctAnswer=-1;
            }
        }
        setContentView(R.layout.activity_repeat_words);


        TestGenerator testGenerator=Word.getTestGenerator(ids[currentPos],this);


        wordTextView= (TextView) findViewById(R.id.wordTextView);
        firstPosition= (TextView) findViewById(R.id.firstPosition);
        secondPosition= (TextView) findViewById(R.id.secondPosition);
        thirdPosition= (TextView) findViewById(R.id.thirdPosition);
        wordTextView.setText(testGenerator.getWord());
        firstPosition.setText(testGenerator.getPosibleAnswer()[0]);
        secondPosition.setText(testGenerator.getPosibleAnswer()[1]);
        thirdPosition.setText(testGenerator.getPosibleAnswer()[2]);
        correctAnswer=testGenerator.getCorrectAnswer();
        toolbar= (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.learn_words_toolbar_menu, menu);




        // Configure the search info and add any event listeners...

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        if(MainActivity.languageChanged){
            TestGenerator testGenerator=Word.getTestGenerator(ids[currentPos],this);


            wordTextView= (TextView) findViewById(R.id.wordTextView);
            firstPosition= (TextView) findViewById(R.id.firstPosition);
            secondPosition= (TextView) findViewById(R.id.secondPosition);
            thirdPosition= (TextView) findViewById(R.id.thirdPosition);
            wordTextView.setText(testGenerator.getWord());
            firstPosition.setText(testGenerator.getPosibleAnswer()[0]);
            secondPosition.setText(testGenerator.getPosibleAnswer()[1]);
            thirdPosition.setText(testGenerator.getPosibleAnswer()[2]);
            correctAnswer=testGenerator.getCorrectAnswer();
            toolbar= (Toolbar) findViewById(R.id.toolbar);

        }
        super.onResume();
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
    private boolean areArrayEqual(int[] a, int[] b) {
        if (a == null) return false;

        if (b == null) return false;

        if (a.length != b.length) return false;
        for (int i = 0; i < a.length; i++) {
            int x = a[i];
            int y = b[i];
            if (x!= y) return false;

        }
        return true;
    }



    public void showNextOne(){
        ++currentPos;

        int idsLength=ids.length;
        if(currentPos>=idsLength){

           this.finish();

        }else {

            TestGenerator testGenerator = Word.getTestGenerator(ids[currentPos], this);
            wordTextView.setText(testGenerator.getWord());
            firstPosition.setText(testGenerator.getPosibleAnswer()[0]);
            secondPosition.setText(testGenerator.getPosibleAnswer()[1]);
            thirdPosition.setText(testGenerator.getPosibleAnswer()[2]);
            correctAnswer = testGenerator.getCorrectAnswer();

        }
    }

    public void answer(int id){
        if(id==correctAnswer){
            correct();
        }else{
            fail();
        }
    }

    public void answer(View v ){
        switch (v.getId()){
            case R.id.firstPosition:
                    answer(0);
                break;
            case R.id.secondPosition:
                answer(1);
                break;
            case R.id.thirdPosition:
                answer(2);
                break;
        }
        showNextOne();
    }

    public void correct( ){
        Realm realm=Realm.getDefaultInstance();
        realm.beginTransaction();
        LearnedWord learnedWord=new LearnedWord();
        learnedWord.setId(ids[currentPos]);
       UnlearnedWord unlearnedWord= realm.where(UnlearnedWord.class).equalTo("id",ids[currentPos]).findFirst();
        if(unlearnedWord!=null){
            unlearnedWord.deleteFromRealm();
        }
        realm.copyToRealm(learnedWord);
        realm.commitTransaction();

        showItsCorrect();
    }
    public void showCorrectAnswer(){
//        Toast toast = Toast.makeText(this, "you rock ", Toast.LENGTH_LONG);
      //  toast.show();
        if(wordTextView!=null) {
            String wordStr=wordTextView.getText().toString();

            ShowMessageDialog dialogFragment = new ShowMessageDialog();


            Bundle data = new Bundle();
//            data.putBoolean(ShowMessageDialog.CORRECTANSWERKEY,false);
            data.putString(ShowMessageDialog.WORDKEY, wordStr);
            data.putBoolean(ShowMessageDialog.FAILEDKEY, true);
            dialogFragment.setArguments(data);

            dialogFragment.show(getSupportFragmentManager(), "ShowWordDialog");
        }
    }

    @Override
    public void onBackPressed() {
        LearnWordsActivity.finishActivity=true;
        finish();
        super.onBackPressed();
    }

    public void showItsCorrect(){


        ShowMessageDialog dialogFragment = new ShowMessageDialog();


        Bundle data = new Bundle();
        data.putBoolean(ShowMessageDialog.CORRECTANSWERKEY,true);

        dialogFragment.setArguments(data);

        dialogFragment.show(getSupportFragmentManager(), "ShowMessageDialog");
        //ShowMessageDialog dialogFragment=new ShowMessageDialog();

    }

    public void fail( ){
        showCorrectAnswer();
//       Toast toast = Toast.makeText(context, "you failed", Toast.LENGTH_LONG);
//        toast.show();
    }



}
