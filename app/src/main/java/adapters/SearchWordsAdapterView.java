package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mrc.learnenglish.R;
import com.example.mrc.learnenglish.Search;

import java.util.ArrayList;

/**
 * Created by mrT on 26.02.2017.
 */

public class SearchWordsAdapterView extends RecyclerView.Adapter<SearchWordsAdapterView.ViewHolder> {
    private LayoutInflater inflater;
    public ArrayList<String > items=null;
    public  SearchWordsAdapterView(Context context,ArrayList<String> words){
        inflater=LayoutInflater.from(context);
        items=words;
    }
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= inflater.inflate(R.layout.recycle_word_view_row,parent,false);
        ViewHolder holder=new SearchWordsAdapterView.ViewHolder(v);
        return holder;
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.button.setText(items.get(position));
    }

    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button button;
        public ViewHolder(View itemView) {
            super(itemView);
            button= (Button) itemView.findViewById(R.id.rowTextView);
        }
    }

}
