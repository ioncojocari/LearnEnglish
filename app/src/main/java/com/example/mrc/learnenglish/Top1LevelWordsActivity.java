package com.example.mrc.learnenglish;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import adapters.AdapterView1Level;
import adapters.AdapterView1LevelGrid;
import adapters.AdapterView2Level;

public class Top1LevelWordsActivity extends AppCompatActivity {
    GridView mRecyclerView;
    public static  final String FROMKEY="FROM";
    public static  final String TOKEY="TO";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_1_level_words);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        int from=1;
        int to=1;
        if(        bundle.containsKey(FROMKEY)){
            from=bundle.getInt(FROMKEY);
        }
        if(        bundle.containsKey(TOKEY)){
            to=bundle.getInt(TOKEY);
        }
        mRecyclerView= (GridView) findViewById(R.id.top20WordsRyclerView);
        Resources resources=getResources();
        AdapterView1LevelGrid adapterView1Level =new AdapterView1LevelGrid(this,resources,from,to);
        mRecyclerView.setAdapter(adapterView1Level);
    }

    public void toNext(View view){
       Intent intent=new Intent(this,WordActivity.class);
        String word=((Button)view).getText().toString();
        intent.putExtra(WordActivity.WORDKEY,word);
        startActivity(intent);
    }
}
