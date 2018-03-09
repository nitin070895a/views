package nitin.thecrazyprogrammer.viewsexample;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Nitin on 09/03/18.
 */

public class RecyclerViewExampleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private int size;
    private LayoutInflater inflater;

    public class MyHolder extends RecyclerView.ViewHolder{

        private LinearLayout back;
        private TextView textView;
        private View view;

        public MyHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            textView = itemView.findViewById(R.id.text);
            back = itemView.findViewById(R.id.back);
        }

        public View getView() {
            return view;
        }
    }

    public RecyclerViewExampleAdapter(Context context, int size){
        this.context = context;
        this.size = size;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_recycler_view, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(position %2 == 0)
            ((MyHolder)holder).back.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
        else
            ((MyHolder)holder).back.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));

        ((MyHolder)holder).textView.setText("Item " + position);

    }

    @Override
    public int getItemCount() {
        return size;
    }
}
