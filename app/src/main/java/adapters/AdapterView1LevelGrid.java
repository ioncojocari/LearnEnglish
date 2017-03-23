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

import com.example.mrc.learnenglish.Data;
import com.example.mrc.learnenglish.R;
import com.example.mrc.learnenglish.Word;

import java.util.ArrayList;

import static java.lang.Math.abs;


/**
 * Created by mrT on 22.02.2017.
 */

public class AdapterView1LevelGrid extends BaseAdapter {
    private LayoutInflater inflater;

    private ArrayList<String > items=new ArrayList<>();
    public AdapterView1LevelGrid(Context c, Resources resources, int from, int to) {
        inflater= LayoutInflater.from(c);
      items=  getItems(from,to);
    }
    public ArrayList<String> getItems(int from,int to){
        ArrayList<String> result=new  ArrayList<String>();
        ArrayList<Word>   words= Data.getData();
        if( (words.size()>0) && ( from>0) ) {
            for (int i = from-1; i <to; i++) {
                result.add(words.get(i).getWord());
            }
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
        layout= (LinearLayout) inflater.inflate(R.layout.recycle_word_view_row,parent,false);
        Button button= (Button) layout.findViewById(R.id.rowTextView);
        button.setText(items.get(position));
        return layout;
    }
}