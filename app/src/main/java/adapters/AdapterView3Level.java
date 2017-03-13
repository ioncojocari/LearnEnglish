package adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mrc.learnenglish.R;
import com.example.mrc.learnenglish.Word;

import java.util.ArrayList;

import io.realm.Realm;

/**
 * Created by mrT on 23.12.2016.
 */

public class AdapterView3Level extends RecyclerView.Adapter<AdapterView3Level.Holder100View> {

    private LayoutInflater inflater;
    private Drawable lastDrawable;
    private ArrayList<String > items=new ArrayList<>();
    public ArrayList<String> getItems(){
        Realm realm=Realm.getDefaultInstance();
        realm.beginTransaction();

        int maxSize=realm.where(Word.class).findAll().size();
        realm.commitTransaction();
        int forLoopTimes=maxSize/100;
        if(maxSize%100!=0){
            forLoopTimes++;
        }
        ArrayList<String> result=new ArrayList<>();
        for(int i=0;i<forLoopTimes;i++){
            int start=i*100+1;

            int finish;
            if(forLoopTimes==(i+1)){
                finish=maxSize;
            }else{
                finish=(i+1)*100;
            }


            result.add(start+".."+finish);
        }
        return result;
    }
    public AdapterView3Level(Context context, Drawable drawable){
        inflater=LayoutInflater.from(context);
        lastDrawable=drawable;
        items=getItems();
    }

    @Override
    public Holder100View onCreateViewHolder(ViewGroup parent, int viewType) {

       View v= inflater.inflate(R.layout.recycle_view_row,parent,false);
        Holder100View holder=new Holder100View(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder100View holder, int position) {
        if(position==items.size()-1) {
             ((LinearLayout) ((RelativeLayout) holder.button.getParent()).getParent()).setBackground(lastDrawable);
        }
        holder.button.setText(items.get(position));
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class Holder100View extends RecyclerView.ViewHolder {
        Button button;
        public Holder100View(View itemView) {
            super(itemView);
            button= (Button) itemView.findViewById(R.id.rowTextView);

        }
    }
}
