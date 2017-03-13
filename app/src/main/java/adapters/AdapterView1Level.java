package adapters;

/**
 * Created by mrT on 24.12.2016.
 */

import android.content.Context;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mrc.learnenglish.Data;
import com.example.mrc.learnenglish.R;
import com.example.mrc.learnenglish.Word;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

import static java.lang.Math.abs;



/**
 * Created by mrT on 23.12.2016.
 */

public class AdapterView1Level extends RecyclerView.Adapter<AdapterView1Level.Holder1View> {



    private LayoutInflater inflater;
    private ArrayList<String > items=new ArrayList<>();
    private Drawable lastDrawable;
    public AdapterView1Level(Context context, int from, int to,Drawable drawable){
        inflater=LayoutInflater.from(context);
        items=getItems(from,to);
        this.lastDrawable=drawable;


    }
    public ArrayList<String> getItems(int from,int to){
        ArrayList<String> result=new  ArrayList<String>();




      ArrayList<Word>   words= Data.getData();


      // RealmResults<Word> words= realm.where(Word.class).greaterThan("id",from-1).lessThan("id",to+1).findAll().sort("id", Sort.ASCENDING);
      if( (words.size()>0) && ( from>0) ) {

          for (int i = from-1; i <to; i++) {
              result.add(words.get(i).getWord());
          }
      }



        return result;
    }

    @Override
    public Holder1View onCreateViewHolder(ViewGroup parent, int viewType) {

        View v= inflater.inflate(R.layout.recycle_word_view_row,parent,false);
        Holder1View holder=new Holder1View(v);
        return holder;
    }


    public void onBindViewHolder(Holder1View holder, int position) {
        if(position==items.size()-1) {
            ((LinearLayout)( holder.button.getParent())).setBackground(lastDrawable);
        }
        holder.button.setText(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class Holder1View extends RecyclerView.ViewHolder {
        Button button;
        public Holder1View(View itemView) {
            super(itemView);
            button= (Button) itemView.findViewById(R.id.rowTextView);

        }
    }
}

