package com.example.mrc.learnenglish;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;

import java.util.regex.Pattern;

import adapters.AdapterView3Level;
import adapters.AdapterView3LevelGrid;
import knowledge.test.*;
import knowledge.test.LearnWordsActivity;

public class Top3LevelWordsActivity extends AppCompatActivity {

    GridView m100RecyclerView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_top_3_level_words);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);

        m100RecyclerView= (GridView) findViewById(R.id.top100WordsRyclerView);
        Resources resources=getResources();
//        Drawable drawable;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            drawable= resources.getDrawable(R.drawable.row_bg_last_one, this.getTheme());
//        } else {
//            drawable=resources.getDrawable(R.drawable.row_bg_last_one);
//        }

        AdapterView3LevelGrid adapterView3Level =new AdapterView3LevelGrid(this,resources);
     //   m100RecyclerView.setLayoutManager(linearLayoutManager);

        m100RecyclerView.setAdapter(adapterView3Level);


    }
    public void toTest(View view){
        String text="";
        for(int i = 0; i<((RelativeLayout)(view.getParent())).getChildCount(); i++) {
            String txt=((Button) ((RelativeLayout) (view.getParent())).getChildAt(i)).getText().toString();
            if(txt!=null &&!txt.isEmpty() ){
                text=txt;
            }
        }
        text=text.replace("..","-");

        String[] strs=text.split(Pattern.quote("-"));

        String strFrom=strs[0];

        String strTo=strs[1];
        Intent intent=new Intent(this, LearnWordsActivity.class);
        int from=Integer.parseInt(strFrom);
        int to=Integer.parseInt(strTo);


        intent.putExtra(knowledge.test.LearnWordsActivity.FROMPOSITION,from);
        intent.putExtra(knowledge.test.LearnWordsActivity.TOPOSITION,to);
        startActivity(intent);
    }
    public void toNext(View view){
        Log.v("hei","hei from Top100Words");
        int from=1;
        int to;
        String fullStr=((Button)view).getText().toString();
        Log.v("hei",fullStr);
        fullStr=fullStr.replace("..","-");
        Log.v("hei",fullStr);
        String[] strs=fullStr.split(Pattern.quote("-"));
        Log.v("hei length",""+strs.length);
        String strFrom=strs[0];
        Log.v("hei from",strFrom);
        String strTo=strs[1];
        Log.v("hei to",strTo);
        from=Integer.parseInt(strFrom);
        to=Integer.parseInt(strTo);
        Intent intent=new Intent(this,Top2LevelWordsActivity.class);
        intent.putExtra(Top2LevelWordsActivity.FROMKEY,from);
        intent.putExtra(Top2LevelWordsActivity.TOKEY,to);
        startActivity(intent);
   //     i need to start new intent and call

    }
}
