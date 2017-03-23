package Enums;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by MrT on 15.12.2016.
 */

public enum LANGUAGE {
    RUSSIAN("RUSSIAN"),UKRANIAN("UKRANIAN"),ROMANIAN("ROMANIAN"),NONE("");
    public String name;
    LANGUAGE(String s){
        name=s;
    }

    @Override
    public String toString() {
        return name;
    }
    public static LANGUAGE fromStringToLanguage(String s){
        if(s.equals(LANGUAGE.RUSSIAN.toString())){
            return LANGUAGE.RUSSIAN;
        }else if(s.equals(LANGUAGE.UKRANIAN.toString())) {
            return LANGUAGE.UKRANIAN;
        }else if(s.equals(LANGUAGE.ROMANIAN.toString())) {
        return LANGUAGE.ROMANIAN;

        }else{
            return LANGUAGE.NONE;
        }
    }
    private static final String APP_PREFERENCES = "mysettings";
    private static final String LANGUAGE_VAR="LANGUAGE";

    public static void saveLanguage(LANGUAGE language,Context context ){
        SharedPreferences mySharedPreferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= mySharedPreferences.edit();
        editor.putString(LANGUAGE_VAR,language.toString());
        editor.apply();

    }

    public static LANGUAGE getCurrentLanguage(Context context ){

        SharedPreferences mySharedPreferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        if(mySharedPreferences.contains(LANGUAGE_VAR)) {
            String language = mySharedPreferences.getString(LANGUAGE_VAR,"");
            return fromStringToLanguage(language);
        }
            return LANGUAGE.NONE;
    }
}
