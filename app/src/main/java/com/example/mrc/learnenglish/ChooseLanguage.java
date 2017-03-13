package com.example.mrc.learnenglish;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.Locale;

import Enums.LANGUAGE;

public class ChooseLanguage extends AppCompatActivity  {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_language);
    }

    public void selectRussian(View view){
        LANGUAGE.saveLanguage(LANGUAGE.RUSSIAN,getApplicationContext());

        MainActivity.languageChanged=true;
       finish();

    }
    public void selectUkraine(View view){
        LANGUAGE.saveLanguage(LANGUAGE.UKRANIAN,getApplicationContext());

        MainActivity.languageChanged=true;
        finish();
    }
    public void selectRomain(View view){
        LANGUAGE.saveLanguage(LANGUAGE.ROMANIAN,getApplicationContext());

        MainActivity.languageChanged=true;
        finish();
    }
    public  static void setChoosedLanguage(Context context) {
        String languageToLoad;
        LANGUAGE currentLanguage = LANGUAGE.getCurrentLanguage(context);
        if (currentLanguage == LANGUAGE.RUSSIAN) {
            languageToLoad = "ru";
        } else if (currentLanguage == LANGUAGE.UKRANIAN) {
            languageToLoad = "uk";
        }else if(currentLanguage==LANGUAGE.ROMANIAN){
            languageToLoad = "ro";
        }
        else

        {
            return;
        }

        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale( locale);
        context.getResources().updateConfiguration(config,
                context.getResources().getDisplayMetrics());

    }
}
