package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mrc.learnenglish.R;

import java.util.ArrayList;

/**
 * Created by mrT on 05.03.2017.
 */

public class TestPassedWithMistakesAdapter extends RecyclerView.Adapter<TestPassedWithMistakesAdapter.MyHolder> {
    private ArrayList<String> items;
    private LayoutInflater inflater;
     public TestPassedWithMistakesAdapter(ArrayList<String> items, Context context){
        this.items=items;
        inflater=LayoutInflater.from(context);
    }

    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= inflater.inflate(R.layout.recycle_word_view_row,parent,false);
        MyHolder holder=new MyHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.button.setText(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        public Button button;
        public MyHolder(View itemView) {
            super(itemView);
            button= (Button) itemView.findViewById(R.id.rowTextView);
        }
    }
}
