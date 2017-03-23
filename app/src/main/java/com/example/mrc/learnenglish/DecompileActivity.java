package com.example.mrc.learnenglish;

import android.content.Intent;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.widget.TextView;


public class DecompileActivity extends AppCompatActivity    {
    private static final String dataUrl = "https://aversive-dynamomete.000webhostapp.com/data.html";

    public static DecompileActivity context;
    protected void onCreate(Bundle savedInstanceState) {
                context=this;
                super.onCreate(savedInstanceState);
                setContentView(R.layout.wait_layout);
    }
    public static void finishActivity() {
            if(context!=null){
                context.finish();
            }
    }
}

