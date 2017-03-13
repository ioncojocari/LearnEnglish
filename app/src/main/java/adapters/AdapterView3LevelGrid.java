package adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.mrc.learnenglish.R;
import com.example.mrc.learnenglish.Word;

import java.util.ArrayList;

import io.realm.Realm;


/**
 * Created by mrT on 22.02.2017.
 */

public class AdapterView3LevelGrid extends BaseAdapter {
   // private Context mContext;
    private LayoutInflater inflater;
    private Resources mResources;
    private ArrayList<String > items=new ArrayList<>();
    public AdapterView3LevelGrid(Context c, Resources resources) {
   //     mContext = c;
        inflater= LayoutInflater.from(c);
        mResources=resources;
      items=  getItems();
    }
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
        layout= (LinearLayout) inflater.inflate(R.layout.recycle_view_row,parent,false);
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
//    public Drawable getDrawable(int id){
//               Drawable drawable;
//
//            drawable=mResources.getDrawable(id);
//            return  drawable;
//    }

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