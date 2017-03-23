package com.example.mrc.learnenglish;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;

import adapters.SearchWordsAdapterView;
import io.realm.Realm;
import io.realm.RealmResults;

public class Search extends AppCompatActivity {
//TODO i need to sort results first should be that begin with this part of string;
   private class OnSearch implements View.OnKeyListener{
   public boolean onKey(View v, int keyCode, KeyEvent event) {
            if(keyCode==KeyEvent.KEYCODE_ENTER ){
                search();
            }

            return false;
        }
    };
    private static final int maxAmountOfShowedWords=25;
    private LinearLayoutManager mLayoutManager;
    private String lastSearch=null;
    private EditText editText=null;
    private OnSearch onSearchListener;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
         editText= (EditText) findViewById(R.id.editText);
        if(onSearchListener==null){
            onSearchListener=new OnSearch();
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String word=editText.getText().toString();

                        lastSearch = word;
                        search(word);
                    }
                public void afterTextChanged(Editable s) {

                }
            });
            editText.setOnKeyListener(onSearchListener);

        }


    }
    public void search(View view){

        search();
    }
    public void search( ){
            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        String word=editText.getText().toString();
            if(!(editText!=null && lastSearch !=null  && word.equals(lastSearch))){
                lastSearch=word;
                search(word);
            }
    }
    public void search(String wordStr){
        ArrayList<Word> results=new ArrayList<Word>();
        if(wordStr!=null && !wordStr.isEmpty()) {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            Word word = realm.where(Word.class).equalTo("word", wordStr).findFirst();
            if(word!=null){
                results.add(word);
            }
            RealmResults<Word> resultSet=realm.where(Word.class).beginsWith("word",wordStr).findAll();
            if( !resultSet.isEmpty()) {
                for (Word wordFromResultSet : resultSet) {
                    if ( !results.contains(wordFromResultSet)) {
                        results.add(wordFromResultSet);
                    }
                }
            }
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
            TextView textView = (TextView) findViewById(R.id.no_such_word_text_view);
           ArrayList<String> result=sortResult(results,wordStr);
                if(result.isEmpty()){
                recyclerView.setVisibility(View.INVISIBLE);
                textView.setVisibility(View.VISIBLE);
            }else{
                mLayoutManager = new LinearLayoutManager(this);
                recyclerView.setVisibility(View.VISIBLE);
                textView.setVisibility(View.INVISIBLE);
                recyclerView.setLayoutManager(mLayoutManager);
                SearchWordsAdapterView searchWordsAdapterView = new SearchWordsAdapterView(this, result);
                recyclerView.setAdapter(searchWordsAdapterView);
            }
            realm.commitTransaction();
        }
    }
    public ArrayList<String> sortResult(ArrayList<Word> words , String word){
        ArrayList<String> results = new ArrayList<String>();
        if( word!=null  && words!=null  && !word.isEmpty()) {
                int[][] fromPositionWordBegins = new int[words.size()][3];
                for (int i = 0; i < words.size(); i++) {
                    fromPositionWordBegins[i][0] = i;
                    fromPositionWordBegins[i][1] = fromWhereBeginsString(words.get(i).getWord(), word);
                    fromPositionWordBegins[i][2] = words.get(i).getWord().length();
                }
                fromPositionWordBegins = sortIntArray(fromPositionWordBegins);

                for (int i = 0; i < fromPositionWordBegins.length; i++) {
                    results.add(words.get(fromPositionWordBegins[i][0]).getWord());
                }
            }

        return results;
    }

    public int fromWhereBeginsString(String wholeWord,String partOfWord){
        int result;
        result=wholeWord.indexOf(partOfWord);

        return result;
    }

    public void toNext(View view){
        Intent intent=new Intent(this,WordActivity.class);
        String word=((Button)view).getText().toString();
        intent.putExtra(WordActivity.WORDKEY,word);
        intent.putExtra(WordActivity.SEARCHMODE,true);
        startActivity(intent);

    }

    public int[][] sortIntArray(int[][] ints){
        int orderNr;
        int value;
        int size;
        int[][] result=new int[25][3];
        for(int i=0;i<ints.length;i++){
            for(int j=0;j<ints.length-1;j++){
                if(( ints[j][1]>ints[j+1][1]) || (ints[j][1]==ints[j+1][1] && ints[j][2] >ints[j+1][2]) ){
                    orderNr=ints[j][0];
                    value=ints[j][1];
                    size=ints[j][2];
                    ints[j][0]=ints[j+1][0];
                    ints[j][1]=ints[j+1][1];
                    ints[j][2]=ints[j+1][2];
                    ints[j+1][0]=orderNr;
                    ints[j+1][1]=value;
                    ints[j+1][2]=size;
                }
            }
        }
        if(ints.length<maxAmountOfShowedWords){
            return ints;
        }else {

            for (int i = 0; i < maxAmountOfShowedWords; i++) {
                result[i] = ints[i];
            }
            return result;
        }
    }
}
