package com.example.mrc.learnenglish;

import android.app.Activity;
import android.app.AlertDialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.method.KeyListener;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.apache.commons.lang3.SerializationUtils;

import java.util.ArrayList;

import adapters.TestPassedWithMistakesAdapter;
import io.realm.Realm;
import knowledge.test.*;
import knowledge.test.LearnWordsActivity;

/**
 * Created by mrT on 27.02.2017.
 */

public class ShowMessageDialog extends DialogFragment implements View.OnClickListener {
    private  boolean correct=false;
    private  boolean wrong=false;
    public final static String CORRECTANSWERKEY="CORRECT";
    public final static String WRONGANSWERKEY="WRONG";
    public final static String WORDKEY="WORD";
    public final static String RESULTSKEY="RESULTS";
    public  ArrayList<String > wrongWords=null;
    public final static String FAILEDKEY="FAILED";
    private boolean failed=false;
    private  Word word;
    private View layout;
    private Context context;


    private View secondLayout;
     AlertDialog alert;



    @Nullable
    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//
//        return super.onCreateView(inflater, container, savedInstanceState);
//    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle data=getArguments();
        context=getContext();
        if(data!=null && !data.isEmpty()) {
            if (data.containsKey(CORRECTANSWERKEY)) {
                correct = data.getBoolean(CORRECTANSWERKEY);
            }
            if(data.containsKey(WRONGANSWERKEY)){
                wrong=data.getBoolean(WRONGANSWERKEY);
            }
            if(data.containsKey(RESULTSKEY)){
                wrongWords=data.getStringArrayList(RESULTSKEY);
            }
            if(data.containsKey(FAILEDKEY)){
                failed=true;
            }

            if(data.containsKey(WORDKEY)){
               String wordStr= data.getString(WORDKEY);
                Realm realm=Realm.getDefaultInstance();
                realm.beginTransaction();
                word=realm.where(Word.class).equalTo("word",wordStr).findFirst();


                realm.commitTransaction();
                Log.v("trt","word" +word.getWord());
                Log.v("trt","translation" +word.getTranslation(getContext()));
            }
        }



        DialogInterface.OnKeyListener key;
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        Context context = getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);



        if(correct){
            layout = inflater.inflate(R.layout.word_dialog_succes_layout, null);
            ImageButton okButton=(ImageButton) layout.findViewById(R.id.ok_button);
            okButton.setOnClickListener(this);


            dialog.setView(layout);



        }else if (wrong){


            layout = inflater.inflate(R.layout.word_dialog_wrong_layout, null);
            ImageButton okButton=(ImageButton) layout.findViewById(R.id.ok_button);
            okButton.setOnClickListener(this);

            dialog.setView(layout);

        }
        else if(word!=null && failed )
        {
            layout = inflater.inflate(R.layout.word_dialog_wrong_layout, null);
            ImageButton okButton=(ImageButton) layout.findViewById(R.id.ok_button);
            okButton.setOnClickListener(this);


            dialog.setView(layout);
            secondLayout = inflater.inflate(R.layout.word_dialog_layout, null);
            TextView wordTextView = (TextView) secondLayout.findViewById(R.id.wordTextView);
            wordTextView.setText(word.getWord());
            TextView translationTextView = (TextView) secondLayout.findViewById(R.id.translationTextView);
            Button usButton= (Button) secondLayout.findViewById(R.id.usPronunciationButton);
            usButton.setOnClickListener(this);
            Button ukButton= (Button) secondLayout.findViewById(R.id.ukPronunciationButton);
            ukButton.setOnClickListener(this);
            translationTextView.setText(word.getTranslation(getActivity()));
            Button okButton1 = (Button) secondLayout.findViewById(R.id.ok_button);
            okButton1.setOnClickListener(this);

        }else if(word!=null){
            key=new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if ((keyCode ==  android.view.KeyEvent.KEYCODE_BACK))
                    {
                        Log.v("trt","From Word On back Pressed");



                        alert.hide();

                        return true; // pretend we've processed it
                    }
                    else
                        return false; // pass on to be processed as normal
                }

            };
            dialog.setOnKeyListener(key);
            secondLayout = inflater.inflate(R.layout.word_dialog_layout, null);

            TextView wordTextView = (TextView) secondLayout.findViewById(R.id.wordTextView);
            wordTextView.setText(word.getWord());
            TextView translationTextView = (TextView) secondLayout.findViewById(R.id.translationTextView);
            Button usButton= (Button) secondLayout.findViewById(R.id.usPronunciationButton);
            usButton.setOnClickListener(this);
            Button ukButton= (Button) secondLayout.findViewById(R.id.ukPronunciationButton);
            ukButton.setOnClickListener(this);
            translationTextView.setText(word.getTranslation(getActivity()));
            Button okButton1 = (Button) secondLayout.findViewById(R.id.ok_button);
            okButton1.setOnClickListener(this);
            dialog.setView(secondLayout);
        }


        else if(wrongWords!=null ){

            if(wrongWords.size()==0){
               layout= inflater.inflate(R.layout.test_passed_succesful,null);


            }else{


                layout=inflater.inflate(R.layout.test_passed_with_mistakes,null);
                layout.clearFocus();
                TestPassedWithMistakesAdapter adapter=new TestPassedWithMistakesAdapter(wrongWords,getActivity());
                RecyclerView recyclerView= (RecyclerView) layout.findViewById(R.id.recycle_view);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()) );
                key=new DialogInterface.OnKeyListener()
                {



                    public boolean onKey(android.content.DialogInterface dialog, int keyCode,android.view.KeyEvent event) {


                        if ((keyCode ==  android.view.KeyEvent.KEYCODE_BACK))
                        {
                            FragmentManager fragmentManager=getFragmentManager();
                            Fragment dialogFragment=fragmentManager.findFragmentByTag("ShowWordDialog");
                            if(dialogFragment!=null ){
                                Log.v("trt","dialogFragment exists");
                                fragmentManager.beginTransaction().remove(fragmentManager.findFragmentByTag("ShowWordDialog")).commit();
                            }else{

                                    LearnWordsActivity.finishActivity();

                            }

                            Log.v("trt","From mistakes On back Pressed");

//                            if(wrongWords!=null   ) {
//                                Log.v("trt","azazazaaz i fucked you little pig");
//                                LearnWordsActivity.finishActivity();
//                            }else{
//                                Log.v("trt","It is not focused");
//                            }


                            return true; // pretend we've processed it
                        }
                        else
                            return false; // pass on to be processed as normal
                    }
                };
                dialog.setOnKeyListener(   key);



            }
            Button button= (Button) layout.findViewById(R.id.ok_button);
            button.setOnClickListener(this);
           dialog.setView(layout);

//            if(wrongWords.size()==0){
//
//            }else{
//
//            }
        }



         alert = dialog.create();

        if(correct || wrong ) {

            alert.show();

// Hide after some seconds

        final Handler handler  = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (alert.isShowing()) {


                    alert.dismiss();

                }
            }
        };

        alert.setOnDismissListener(new DialogInterface.OnDismissListener() {

            public void onDismiss(DialogInterface dialog) {
                handler.removeCallbacks(runnable);
            }
        });

                handler.postDelayed(runnable, 1000);


        }

        else if(word!=null && failed){
            alert.show();
            final Handler handler  = new Handler();
            final Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    dialog.setView(secondLayout);
                    alert.dismiss();
                    alert=dialog.create();
                    alert.show();
                    if(  alert.isShowing()){
                        Log.v("trt","Alert is showing");
                    }
                }
            };

            alert.setOnDismissListener(new DialogInterface.OnDismissListener() {

                public void onDismiss(DialogInterface dialog) {
                    handler.removeCallbacks(runnable);
                }
            });
            handler.postDelayed(runnable, 1000);
        }
        else if(wrongWords!=null){

                alert.show();

        }






        return alert;
    }

    @Override
    public void onPause() {
        Log.v("trt","onPause ShoWMessageDIalog");
        super.onPause();


    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public void onDestroy() {
//        if(wrongWords!=null){
//            LearnWordsActivity.finishActivity();
//        }



        super.onDestroy();
    }

    public int getDp(int nr){
        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, nr,getResources().getDisplayMetrics());
    }

    @Override
    public void onStart() {
        super.onStart();
        if(correct || wrong || (word!=null && failed&& getDialog()!=null )) {
            getDialog().getWindow().setLayout(getDp(240), getDp(240));
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.ok_button){
            Log.v("trt","okButton");
            if(wrongWords!=null){
                LearnWordsActivity.finishActivity();
            }
            try {
                alert.dismiss();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }else {
            String pronunciation="";
            if (v.getId() == R.id.ukPronunciationButton) {
                pronunciation = "uk";
            } else if (v.getId() == R.id.usPronunciationButton) {
                pronunciation = "us";
            }

            String wordStr = word.getWord();
            Music.play(wordStr, pronunciation,context );
        }
    }

}
