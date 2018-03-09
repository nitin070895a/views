package nitin.thecrazyprogrammer.viewsexample;

import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.Random;

import nitin.thecrazyprogrammer.views.AnimatedScroller;
import nitin.thecrazyprogrammer.views.AutoPager;
import nitin.thecrazyprogrammer.views.SwipeTouchListener;

public class MainActivity extends AppCompatActivity {

    private int TOTAL_PAGES = 3;
    private int RECYCLER_VIEW_SIZE = 100;
    private int PAGE_SCROLL_DELAY = 1500;

    private CardView cardView;
    private ViewPager viewPager;
    private Button button;
    NestedScrollView nestedScrollView;
    private RecyclerView recyclerView;

    private AutoPager autoPager;
    private AnimatedScroller animatedScroller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cardView = (CardView) findViewById(R.id.cardView);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        button = (Button) findViewById(R.id.button);
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        cardView.setOnTouchListener(new SwipeTouchListener(this, true, 30, new SwipeTouchListener.SwipeViewDismissListener() {
            @Override
            public void dismiss(View view) {
                cardView.setVisibility(View.GONE);
            }
        }));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float y = recyclerView.getChildAt(0).getHeight() * new Random().nextInt(RECYCLER_VIEW_SIZE);
                Log.e("Hurray", "Y " + y);
                animatedScroller.scrollTo(0, (int) y, 1000);
            }
        });

        autoPager = new AutoPager(this, 0, TOTAL_PAGES);
        autoPager.setDelay(PAGE_SCROLL_DELAY);
        viewPager.setAdapter(new ViewpagerAdapter(this, TOTAL_PAGES));
        autoPager.makeDots(viewPager, (LinearLayout) findViewById(R.id.dots_ll));
        autoPager.startAutoPaging(viewPager);

        animatedScroller = new AnimatedScroller(nestedScrollView);
        recyclerView.setAdapter(new RecyclerViewExampleAdapter(this, RECYCLER_VIEW_SIZE));
    }

    @Override
    protected void onDestroy() {
        if(autoPager != null)
            autoPager.destroyAutoPager();
        super.onDestroy();
    }
}
