package adapters;

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

import com.example.mrc.learnenglish.R;

import java.util.ArrayList;

import static java.lang.Math.abs;

/**
 * Created by mrT on 23.12.2016.
 */

public class AdapterView2Level extends RecyclerView.Adapter<AdapterView2Level.Holder20View> {
    private int interval=10;
    private Drawable lastDrawable;

    private LayoutInflater inflater;
    private ArrayList<String > items=new ArrayList<>();

    public AdapterView2Level(Context context, int from, int to,Drawable drawable){
        inflater=LayoutInflater.from(context);
        lastDrawable=drawable;
        items=getItems(from,to);



    }
    public ArrayList<String> getItems(int from,int to){
        ArrayList<String> result=new  ArrayList<String>();
        int forLoopTimes=abs(from-(to))/interval;
        int i;
        Log.v("hei forLoopTimes",""+forLoopTimes);
        for( i=0;i<forLoopTimes;i++){
            String str=String.valueOf(from+i*interval)+".."+String.valueOf(from-1+(i+1)*interval    );
            result.add(str);
        }
        if((to-(from))%interval!=1){
            String str=String.valueOf(from+(i*interval))+".."+String.valueOf(to);
            result.add(str);
        }

        return result;
    }

    public Holder20View onCreateViewHolder(ViewGroup parent, int viewType) {

       View v= inflater.inflate(R.layout.recycle_view_row,parent,false);
        Holder20View holder=new Holder20View(v);
        return holder;
    }


    public void onBindViewHolder(Holder20View holder, int position) {

        holder.button.setText(items.get(position));
    }


    public int getItemCount() {
        return items.size();
    }

    public static class Holder20View extends RecyclerView.ViewHolder {
        Button button;
        public Holder20View(View itemView) {
            super(itemView);
            button= (Button) itemView.findViewById(R.id.rowTextView);

        }
    }
}
