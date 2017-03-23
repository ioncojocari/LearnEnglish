package com.example.mrc.learnenglish;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.google.android.gms.nearby.messages.internal.Update;

import org.apache.commons.lang3.StringUtils;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by mrT on 08.03.2017.
 */

public class UpdateDataTask extends AsyncTask<String,String,String> {
   private static   volatile boolean updateIsntNeeded=false;
    private static Context mContext;
    private static final String updateUri="https://bull-nosed-brains.000webhostapp.com/training.php";
    private static final int maxDaysWithoutUpdates=2;
    private static final int updateIsOnlineIntensity=20;
    protected static boolean isOnline(ConnectivityManager mConnectivityManager){
        NetworkInfo networkInfo=mConnectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnectedOrConnecting()){


            return true;
        }
        return false;
    }

    private void addUpdate(Updates update){
        Realm realm=Realm.getDefaultInstance();
        while (realm.isInTransaction()){
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        realm.beginTransaction();
        if(update.getVersion()==0){
            if(!realm.where(Updates.class).findAll().isEmpty()){
                Date lastUpdateTime=realm.where(Updates.class).maximumDate("date");
                Updates lastUpdate=realm.where(Updates.class).equalTo("date",lastUpdateTime).findFirst();
                int lastUpdateVersion=lastUpdate.getVersion();
                update.setVersion(lastUpdateVersion);
            }
        }
        realm.copyToRealm(update);
        realm.commitTransaction();
    }

    protected void onPostExecute(String s) {
        Data.update();
        updateIsntNeeded=true;
    }

    protected String doInBackground(String... params) {
        int version;
        RequestPackage requestPackage=new RequestPackage();
        requestPackage.setUri(updateUri);
        requestPackage.setRequestmethod(RequestPackage.RequestMethod.POST);

        Realm realm=Realm.getDefaultInstance();
        while (realm.isInTransaction()){

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        realm.beginTransaction();

        if(realm.where(Updates.class).findAll().isEmpty()){
            version=0;
        }else {
            version = realm.where(Updates.class).findAllSorted("date", Sort.DESCENDING).first().getVersion();
        }

        realm.commitTransaction();
        requestPackage.addParam("version",String.valueOf(version));
        String result=HttpManager.getData(requestPackage);

        if(!result.isEmpty()) {
            int updatedVersion = getVersionFromString(result);
            result = getDataWithoutVersion(result);

            List<WordFromDb> words = WordJSONParser.parse(result);
            if (words.size() > 0) {

                while (realm.isInTransaction()) {
                    Log.v("trt", "realm is in transaction");
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Log.v("trt", "if word size >0 ");
                realm.beginTransaction();
                for (int i = 0; i < words.size(); i++) {

                    if (words.get(i).add) {
                        addWord(words.get(i), realm);
                    }
                    if (words.get(i).update) {
                        updateWord(words.get(i), realm);
                    }
                    if (words.get(i).delete) {
                        deleteWord(words.get(i), realm);
                    }
                }
                realm.commitTransaction();
            }
            Updates update = new Updates();
            update.setDate(new Date());
            update.setVersion(updatedVersion);
            addUpdate(update);
        }
            return null;
    }

    private String getDataWithoutVersion(String result) {


        while(isNumber(result)){
            if(result.length()>2){
                result =result.substring(1,result.length());
                Log.v("trt","next "+result);
            }
        }
        return result;
    }
    public boolean isNumber(String s){
        if(!s.isEmpty()) {
            Log.v("trt", "is NUmeric character" + s.substring(0, 1));
            Log.v("trt", "is NUmeric " + StringUtils.isNumeric(s.substring(0, 1)));
            return StringUtils.isNumeric(s.substring(0, 1));
        }return false;


    }

    public void updateWord(WordFromDb wordFromDb,Realm realm){
           Word word= realm.where(Word.class).equalTo("id",wordFromDb.id).findFirst();
        if(wordFromDb.first_false!=0) {
            word.setFirst_false(wordFromDb.first_false);
        }
        if(wordFromDb.second_false!=0) {
            word.setSecond_false(wordFromDb.second_false);
        }
        if(wordFromDb.romanianTranslation!=null && !wordFromDb.romanianTranslation.isEmpty()){
            word.setRomanianTranslation(wordFromDb.romanianTranslation);
        }
        if(wordFromDb.russianTranslation!=null && !wordFromDb.russianTranslation.isEmpty()){
            word.setRussianTranslation(wordFromDb.russianTranslation);
        }
        if(wordFromDb.ukrainianTranslation!=null && !wordFromDb.ukrainianTranslation.isEmpty()){
            word.setUkrainianTranslation(wordFromDb.ukrainianTranslation);
        }
        realm.copyToRealmOrUpdate(word);
    }

    public void addWord(WordFromDb wordFromDb,Realm realm){
        Log.v("trt","adding new word "+wordFromDb.word);
        Word word= realm.where(Word.class).equalTo("id",wordFromDb.id).findFirst();
        if(word==null){
            word=new Word();
            word.setWord(wordFromDb.word);
        }

        if(wordFromDb.id!=0) {
            word.setId(wordFromDb.id);
        }
        if(wordFromDb.first_false!=0) {
            word.setFirst_false(wordFromDb.first_false);
        }
        if(wordFromDb.second_false!=0) {
            word.setSecond_false(wordFromDb.second_false);
        }
        if(wordFromDb.romanianTranslation!=null){
            Log.v("trt","romanianTranslation "+wordFromDb.romanianTranslation);
            word.setRomanianTranslation(wordFromDb.romanianTranslation);
        }
        if(wordFromDb.russianTranslation!=null){
            Log.v("trt","russianTranslation "+wordFromDb.russianTranslation);
            word.setRussianTranslation(wordFromDb.russianTranslation);
        }
        if(wordFromDb.ukrainianTranslation!=null){
            Log.v("trt","ukrainianTranslation "+wordFromDb.ukrainianTranslation);

            word.setUkrainianTranslation(wordFromDb.ukrainianTranslation);
        }
        if(wordFromDb.uk_link!=null && !wordFromDb.uk_link.isEmpty()){
            download(wordFromDb.uk_link,wordFromDb.word,"uk.mp3");
        }
        if(wordFromDb.us_link!=null && !wordFromDb.us_link.isEmpty()){
            download(wordFromDb.us_link,wordFromDb.word,"us.mp3");
        }



        realm.copyToRealmOrUpdate(word);


    }
    public void download(String url ,String name,String suffix ){
        String fullName=name+"_"+suffix;
        try {
            URL u = new URL(url);
            InputStream is = u.openStream();

            DataInputStream dis = new DataInputStream(is);

            byte[] buffer = new byte[1024];
            int length;

            FileOutputStream fos = new FileOutputStream(new File(mContext.getFilesDir() + "/" +fullName));
            while ((length = dis.read(buffer))>0) {
                fos.write(buffer, 0, length);
            }
          fos.close();

        } catch (MalformedURLException mue) {
            Log.e("SYNC getUpdate", "malformed url error", mue);
        } catch (IOException ioe) {
            Log.e("SYNC getUpdate", "io error", ioe);
        } catch (SecurityException se) {
            Log.e("SYNC getUpdate", "security error", se);
        }

    }
    public void deleteWord(WordFromDb wordFromDb,Realm realm){
        Log.v("trt","deleting word id "+wordFromDb.id );
        if(wordFromDb.id!=0
           && realm.where(Word.class).equalTo("id", wordFromDb.id).findFirst()!=null) {
                realm.where(Word.class).equalTo("id", wordFromDb.id).findFirst().deleteFromRealm();

        }else if(wordFromDb.word!=null &&
            realm.where(Word.class).equalTo("word", wordFromDb.word).findFirst()!=null) {
                realm.where(Word.class).equalTo("word", wordFromDb.word).findFirst().deleteFromRealm();
            }
        }

    public int getVersionFromString(String string){
        String str="";
        int result=0;
        while (isNumber(string)){
            str+=string.substring(0,1);
            string=string.substring(1,string.length());
        }
        if(!str.isEmpty()) {
            result = Integer.parseInt(str);
        }

        return result;
    }

    public static void checkLastData(ConnectivityManager connectivityManager,Context context){
       if(!updateIsntNeeded) {
            mContext=context;
            UpdateDataTaskManager taskManager = new UpdateDataTaskManager(connectivityManager);
            taskManager.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }

    }

    public static void updateData(){
            UpdateDataTask task = new UpdateDataTask();
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private static class UpdateDataTaskManager extends AsyncTask<String,String,String>{
        ConnectivityManager mConnectivityManager;
        public UpdateDataTaskManager(ConnectivityManager connectivityManager){
                mConnectivityManager=connectivityManager;
        }

        protected String doInBackground(String... params) {
            Realm realm = Realm.getDefaultInstance();
            while (realm.isInTransaction()){
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            realm.beginTransaction();
            Date date = realm.where(Updates.class).maximumDate("date");
            realm.commitTransaction();

            if(date==null){
                date=new Date(0x0);
            }
            Date today = new Date();
            long lastUpdateMilliseconds = date.getTime();
            lastUpdateMilliseconds += maxDaysWithoutUpdates * 24 * 3600 * 1000;
            Log.v("trt","today long : "+today);
            Log.v("trt","last update time : "+date);
            Log.v("trt","lastUpdateMilliseconds date  : "+new Date(lastUpdateMilliseconds).toString());
            Date maxDate=new Date(lastUpdateMilliseconds);
            if ( ( today.after(maxDate))) {
                Log.v("trt","today is after maxDate");
            while (true) {
                if (isOnline(mConnectivityManager)) {

                    Log.v("trt","it is online");
                    updateData();
                    break;

                } else {
                    try {
                        Thread.sleep(updateIsOnlineIntensity*1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            }else{
                updateIsntNeeded=true;
            }
            return null;
        }
    }



}
