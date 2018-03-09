package nitin.thecrazyprogrammer.viewsexample;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Nitin on 09/03/18.
 */
public class ViewpagerAdapter extends PagerAdapter{

    private int size;
    private Context context;
    private LayoutInflater inflater;
    private TextView textView;
    private LinearLayout back;

    public ViewpagerAdapter(Context context, int size){

        this.size = size;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return size;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = inflater.inflate(R.layout.item_viewpager, container, false);
        textView = view.findViewById(R.id.text);
        back = view.findViewById(R.id.back);

        switch (position){
            case 0: back.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary)); textView.setText("First"); break;
            case 1: back.setBackgroundColor(context.getResources().getColor(R.color.colorAccent)); textView.setText("Second"); break;
            case 2: back.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark)); textView.setText("Third"); break;
        }

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
