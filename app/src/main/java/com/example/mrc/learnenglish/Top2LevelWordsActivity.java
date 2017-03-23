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
import android.widget.RelativeLayout;

import java.util.regex.Pattern;

import adapters.AdapterView2Level;
import adapters.AdapterView2LevelGrid;
import knowledge.test.*;
import knowledge.test.LearnWordsActivity;

public class Top2LevelWordsActivity extends AppCompatActivity {


    GridView m20RecyclerView;
    public static  final String FROMKEY="FROM";
    public static  final String TOKEY="TO";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_2_level_words);
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
        setContentView(R.layout.activity_top_2_level_words);
        m20RecyclerView= (GridView) findViewById(R.id.top20WordsRyclerView);
        Resources resources=getResources();
        AdapterView2LevelGrid adapterView2Level =new AdapterView2LevelGrid(this,resources,from,to);
        m20RecyclerView.setAdapter(adapterView2Level);
    }

    public void toTest(View view){
        String text="";
        for(int i=0;i<((RelativeLayout)(view.getParent())).getChildCount();i++) {
            String txt=((Button) ((RelativeLayout) (view.getParent())).getChildAt(i)).getText().toString();
            if(txt!=null &&!txt.isEmpty() ){
                text=txt;
            }
        }
        text=text.replace("..","-");
        String[] strs=text.split(Pattern.quote("-"));
        String strFrom=strs[0];
        String strTo=strs[1];
        Intent intent=new Intent(this,LearnWordsActivity.class);
        int from=Integer.parseInt(strFrom);
        int to=Integer.parseInt(strTo);
        intent.putExtra(LearnWordsActivity.FROMPOSITION,from);
        intent.putExtra(LearnWordsActivity.TOPOSITION,to);
        startActivity(intent);
    }
    public void toNext(View view){
        int from=1;
        int to;
        String fullStr=((Button)view).getText().toString();
        fullStr=fullStr.replace("..","-");
        String[] strs=fullStr.split(Pattern.quote("-"));
        String strFrom=strs[0];
        String strTo=strs[1];
        from=Integer.parseInt(strFrom);
        to=Integer.parseInt(strTo);
        Intent intent=new Intent(this,Top1LevelWordsActivity.class);
        intent.putExtra(Top1LevelWordsActivity.FROMKEY,from);
        intent.putExtra(Top1LevelWordsActivity.TOKEY,to);
        startActivity(intent);
    }
}
