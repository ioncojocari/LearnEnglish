package adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.mrc.learnenglish.R;
import com.example.mrc.learnenglish.Word;

import java.util.ArrayList;

import io.realm.Realm;

import static java.lang.Math.abs;


/**
 * Created by mrT on 22.02.2017.
 */

public class AdapterView2LevelGrid extends BaseAdapter {
    private LayoutInflater inflater;
    private Resources mResources;
    private int interval=10;
    private ArrayList<String > items=new ArrayList<>();
    public AdapterView2LevelGrid(Context c, Resources resources,int from,int to) {
        inflater= LayoutInflater.from(c);
        mResources=resources;
      items=  getItems(from,to);
    }
    public ArrayList<String> getItems(int from,int to){
        ArrayList<String> result=new  ArrayList<String>();
        int forLoopTimes=abs(from-(to))/interval;
        int i;
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

    public int getCount() {
        return items.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layout;
        layout= (LinearLayout) inflater.inflate(R.layout.recycle_view_row_2nd,parent,false);
        Button button= (Button) layout.findViewById(R.id.rowTextView);
        button.setText(items.get(position));

        return layout;
    }
     public  int dpToPixel( float dp) {
         float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, mResources.getDisplayMetrics());
        return (int)(px);
    }
    public Drawable getDrawable(int id){
               Drawable drawable;

            drawable=mResources.getDrawable(id);
            return  drawable;
    }
}