package com.example.mrc.learnenglish;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import java.io.File;

/**
 * Created by MrT on 18.12.2016.
 */

public class Music {
    private static MediaPlayer myMediaPlayer = null;

    private static int getRawIdOfSongName(String songName, String language, Context context  ) {
        String playSongName = songName + "_" + language;
        return context.getResources().getIdentifier(playSongName, "raw", context.getPackageName());

    }
    public static boolean songExists(String songName, String language, Context context){
        if(getRawIdOfSongName( songName,  language,  context) !=0 || isFileExisting(songName,language,context) ){
            return true;
        }


            return false;


    }
    public static boolean isFileExisting(String songName, String language, Context context){
        String fileName=songName+"_"+language+".mp3";

        String path=   context.getFilesDir()+ "/" +fileName;
        File file =new File(path);
        if(file!=null&&   file.exists() && file.isFile()){
            return true;
        }
        return false;
    }
    public static String getFilePath(String songName, String language, Context context ){
        String fileName=songName+"_"+language+".mp3";

        String path=   context.getFilesDir()+ "/" +fileName;

        return path;
    }
    public static void play(String name, String language, Context context){

        if(myMediaPlayer!=null && myMediaPlayer.isPlaying()){
            myMediaPlayer.stop();
        }
        int rawId = getRawIdOfSongName(name, language, context);

            if (rawId != 0 ) {
                myMediaPlayer = MediaPlayer.create(context, rawId);
                myMediaPlayer.setLooping(false);
                myMediaPlayer.start();

    }else  if( isFileExisting(name,language,context)){
           String filePath=getFilePath(name,language,context);
           myMediaPlayer = MediaPlayer.create(context, Uri.parse( filePath));
           myMediaPlayer.setLooping(false);
           myMediaPlayer.start();

       }





    }
}
