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
   // private Context mContext;
    private LayoutInflater inflater;
    private Resources mResources;
    private int interval=10;
    private ArrayList<String > items=new ArrayList<>();
    public AdapterView1LevelGrid(Context c, Resources resources, int from, int to) {
   //     mContext = c;
        inflater= LayoutInflater.from(c);
        mResources=resources;
      items=  getItems(from,to);
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

    public int getCount() {
        return items.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layout;
        layout= (LinearLayout) inflater.inflate(R.layout.recycle_word_view_row,parent,false);
//        if (convertView == null) {
//            // if it's not recycled, initialize some attributes
//            button = new Button(mContext);
////            button.setLayoutParams(new GridView.LayoutParams(85, 85));
////
////            button.setPadding(8, 8, 8, 8);
//        } else {
//            button = (Button) convertView;
//        }
        Button button= (Button) layout.findViewById(R.id.rowTextView);
        button.setText(items.get(position));
     //   button.setTextSize(TypedValue.COMPLEX_UNIT_SP,22);
     //   button.setBackground(getDrawable(R.drawable.row_bg));
//
   //     button.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,dpToPixel(70)));

      //  imageView.setImageResource(mThumbIds[position]);
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

//    // references to our images
//    private Integer[] mThumbIds = {
//            R.drawable.sample_2, R.drawable.sample_3,
//            R.drawable.sample_4, R.drawable.sample_5,
//            R.drawable.sample_6, R.drawable.sample_7,
//            R.drawable.sample_0, R.drawable.sample_1,
//            R.drawable.sample_2, R.drawable.sample_3,
//            R.drawable.sample_4, R.drawable.sample_5,
//            R.drawable.sample_6, R.drawable.sample_7,
//            R.drawable.sample_0, R.drawable.sample_1,
//            R.drawable.sample_2, R.drawable.sample_3,
//            R.drawable.sample_4, R.drawable.sample_5,
//            R.drawable.sample_6, R.drawable.sample_7
//    };
}