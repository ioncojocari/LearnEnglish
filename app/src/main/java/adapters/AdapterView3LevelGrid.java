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
    private LayoutInflater inflater;

    private ArrayList<String > items=new ArrayList<>();
    public AdapterView3LevelGrid(Context c, Resources resources) {
        inflater= LayoutInflater.from(c);
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

    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layout;
        layout= (LinearLayout) inflater.inflate(R.layout.recycle_view_row,parent,false);
        Button button= (Button) layout.findViewById(R.id.rowTextView);
        button.setText(items.get(position));
        return layout;
    }

}