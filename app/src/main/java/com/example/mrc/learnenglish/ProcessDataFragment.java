package com.example.mrc.learnenglish;


import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import io.realm.Realm;



public class ProcessDataFragment  extends Fragment {




    private GetDataTask mTask;
    public static final String PROCCESS_DATA_FRAGMENT_TAG="PROCESSDATAFRAGMENTTAG";
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mTask = new GetDataTask();
        mTask.execute();
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.nothing,container,false);
    }

    public  String readString(InputStream is) throws IOException {
            char[] buf = new char[2048];
            Reader r = new InputStreamReader(is, "UTF-8");
            StringBuilder s = new StringBuilder();
            while (true) {
                int n = r.read(buf);
                if (n < 0)
                    break;
                s.append(buf, 0, n);
            }
            return s.toString();
    }
    public  String load(Context context){
            AssetManager assetManager=context.getAssets();
            InputStream inputStream = null;
            try {
                inputStream = assetManager.open("words.txt");
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                return readString(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
    }

    private class GetDataTask extends AsyncTask<String,String ,List<Word>> {

        protected List<Word> doInBackground(String... params) {
            List<Word> wordList=null;
            Realm realm=Realm.getDefaultInstance();
            realm.beginTransaction();
            if(realm.where(Word.class).findAll().size()==0) {
                String data=load(getActivity());
                wordList = WordJSONParser.parseData(data);
                realm.copyToRealm(wordList);
                realm.commitTransaction();
            }
            return wordList;
        }

        protected void onPostExecute(List<Word> words) {
            DecompileActivity.finishActivity();
        }

    }





}