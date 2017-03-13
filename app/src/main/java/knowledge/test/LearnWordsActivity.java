package knowledge.test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mrc.learnenglish.Data;
import com.example.mrc.learnenglish.R;
import com.example.mrc.learnenglish.ShowMessageDialog;
import com.example.mrc.learnenglish.TestGenerator;
import com.example.mrc.learnenglish.Word;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.TreeSet;

public class LearnWordsActivity extends AppCompatActivity   {

    public static final String FROMPOSITION="FROM";
    public static final String  TOPOSITION="TO";
    private static AppCompatActivity context;
    private static int currentPos=0;
    private static int correctAnswer;
    private TextView wordTextView;
    private TextView firstPosition;
    private TextView secondPosition;
    private TextView thirdPosition;
    private static ArrayList<String> wrongAttempts=null;
    public static void finishActivity(){
        if(context!=null){
            context.finish();
        }
    }

    public static TreeSet<Integer> unlearned=null;
    public static ArrayList<Integer> all=null;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(wrongAttempts==null){
            wrongAttempts=new ArrayList<String>();
        }

        context=this;



        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();


        if(bundle!=null&& unlearned==null){

            if(bundle.containsKey(FROMPOSITION)&& bundle.containsKey(TOPOSITION)){
                int from=bundle.getInt(FROMPOSITION);
                int to=bundle.getInt(TOPOSITION);


                unlearned=new TreeSet<>(Data.getIdsOfPositions(from,to));
                all=new ArrayList<>(unlearned);
            }


        }

        setContentView(R.layout.activity_test_repeat_words);
        Log.v("trt","wtf is going on learn Words Activity");

        TestGenerator testGenerator= Word.getTestGenerator(all.get(currentPos),this);


        wordTextView= (TextView) findViewById(R.id.wordTextView);
        firstPosition= (TextView) findViewById(R.id.firstPosition);
        secondPosition= (TextView) findViewById(R.id.secondPosition);
        thirdPosition= (TextView) findViewById(R.id.thirdPosition);
        wordTextView.setText(testGenerator.getWord());
        firstPosition.setText(testGenerator.getPosibleAnswer()[0]);
        secondPosition.setText(testGenerator.getPosibleAnswer()[1]);
        thirdPosition.setText(testGenerator.getPosibleAnswer()[2]);
        correctAnswer=testGenerator.getCorrectAnswer();





    }
    public void showNextOne(){
        if(unlearned!=null) {
            if ( unlearned.size() == 0) {

                youFinished();
            } else {
                currentPos++;
                int idsLength = all.size();
                if (currentPos >= idsLength) {

                    currentPos = 0;
                    all = new ArrayList<>(unlearned);


                }


                TestGenerator testGenerator = Word.getTestGenerator(all.get(currentPos), this);

                wordTextView.setText(testGenerator.getWord());
                firstPosition.setText(testGenerator.getPosibleAnswer()[0]);
                secondPosition.setText(testGenerator.getPosibleAnswer()[1]);
                thirdPosition.setText(testGenerator.getPosibleAnswer()[2]);
                correctAnswer = testGenerator.getCorrectAnswer();


            }
        }else{
            finishActivity();
        }
    }
        public void answer(View v ){
        Log.v("txt","answer begin ");
        switch (v.getId()){
            case R.id.firstPosition:
                answer(0);
                break;
            case R.id.secondPosition:
                answer(1);
                break;
            case R.id.thirdPosition:
                answer(2);
                break;


        }
        Log.v("txt","before show next oNe ");
        showNextOne();
        Log.v("txt","after show next oNe ");

    }
    public void answer(int id){
        if(id==correctAnswer){
            correct();
        }else{
            fail();
        }
    }
    public void correct( ){
        if(unlearned!=null) {
            if ( unlearned.size() > 0) {
                if (unlearned.contains(all.get(currentPos))) {
                    unlearned.remove(all.get(currentPos));
                }


                ShowMessageDialog dialogFragment = new ShowMessageDialog();


                Bundle data = new Bundle();
                data.putBoolean(ShowMessageDialog.CORRECTANSWERKEY, true);

                dialogFragment.setArguments(data);

                dialogFragment.show(getSupportFragmentManager(), "ShowMessageDialog");
            }
        }else{
            finishActivity();
        }
    }
    public void fail( ){
        if(wrongAttempts!=null) {
            ShowMessageDialog dialogFragment = new ShowMessageDialog();
            String word = wordTextView.getText().toString();

            if (!wrongAttempts.contains(word)) {

                wrongAttempts.add(word);
            }
            Bundle data = new Bundle();

            data.putBoolean(ShowMessageDialog.WRONGANSWERKEY, true);

            dialogFragment.setArguments(data);

            dialogFragment.show(getSupportFragmentManager(), "ShowMessageDialog");
        }else{
            finishActivity();
        }

    }
    @Override
    protected void onResume() {
        Log.v("trt","onResume test.LearnWordsActivity");
        if(unlearned==null || unlearned.isEmpty()){
            finishActivity();
        }

        super.onResume();
    }








    @Override
    protected void onDestroy() {
        Log.v("trt","onDestroy");

//        currentPos=0;
//
//        unlearned=null;
//        all=null;



        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        currentPos=0;

        unlearned=null;
        all=null;
        wrongAttempts=null;
    }
    public void toNext(View v){
        Bundle data = new Bundle();
        ShowMessageDialog dialogFragment = new ShowMessageDialog();
        Button button=(Button)v;
        String wordStr=button.getText().toString();

        data.putString(ShowMessageDialog.WORDKEY,wordStr);

        dialogFragment.setArguments(data);

        dialogFragment.show(getSupportFragmentManager(), "ShowWordDialog");
        Log.v("trt","azazasza to Next from test.LearnWordsActivity");
    }

    public void youFinished(){

        setContentView(new View(this));

        Bundle data = new Bundle();
        ShowMessageDialog dialogFragment = new ShowMessageDialog();
        data.putStringArrayList(ShowMessageDialog.RESULTSKEY,wrongAttempts);

        dialogFragment.setArguments(data);
        currentPos=0;

        unlearned=null;
        all=null;
        wrongAttempts=null;
        dialogFragment.show(getSupportFragmentManager(), "ShowMessageDialog");

  //      finish();
    }
}
