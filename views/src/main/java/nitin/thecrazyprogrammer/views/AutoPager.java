package nitin.thecrazyprogrammer.views;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import nitin.thecrazyprogrammer.views.R;

/**
 * Created by Nitin Khurana on 7/25/2017.
 *
 * This class provides functionality for a viewpager to scroll automatically
 * Not only the autoscroll behavior but you can also add bottom dots to the viewpager with each dot representing a page
 * The current page dot will automatically will be inflated by a certain degree
 *
 * Functions :
 *
 * A) Autopaging
 * B) Provides Dots
 *
 * Note : ********** (This class provides both or either of these functions) ***********
 * Note : ********** can be used when you either want autopaging or dots or both *******
 *
 * Usage Steps:
 *
 * 1. Create an instance of this class
 * 2. Pass context, starting page number, total no of pages to the constructor
 * 3. To start autopaging call startAutoPaging() method with passing your viewpager as parameter
 * 4. To add dots create a linearLayout in your layout in which you want to add the dots and call method makeDots() passing your viewpager and that linearLayout
 * 5. When you use autopager don't forget to call destroyAutoPager when you are done using it, usually it is called in onDestroyView() for fragment or onDestroy() for activity
 *
 * eg :
 *
 * AutoPager autoPager = new AutoPager(getActivity(), 0, banners.size());
 * autoPager.startAutoPaging(viewPager); // for autopage
 * autoPager.makeDots(viewPager,ll_bullets); // for making dots
 * viewPager.setAdapter(yourAdapter);
 *
 */
public class AutoPager {

    private int DELAY = 7000;
    private int currPage = 0;
    private int totalPages = 0;
    private boolean touched = false;

    Context context;
    Runnable autoPage;
    Handler handler = new Handler();

    /**
     * Initiate the Autopager
     * @param context the context of the activity
     * @param startPage the page to begin with, usually 0
     * @param totalPages the total no of pages in the viewpager
     */
    public AutoPager(Context context, int startPage, int totalPages){

        currPage = startPage;
        this.totalPages = totalPages;
        this.context = context;
    }

    /**
     * Starts autopaging on a viewpager
     * Scrolls the viewpager automatically after a duration
     * Also handles touch events, the viewpager will not scroll automatically if it is currently being touched
     *
     * Note : Infinite scroll, if reaches to the end starts from first page
     *
     * @param viewPager The viewpager to be scrolled automatically
     */
    public void startAutoPaging(final ViewPager viewPager){

        autoPage = new Runnable() {
            @Override
            public void run() {
                // if viewpager is not being touched
                if (!touched) {/*
                    if(!banner_viewpager.isSelected())
                        banner_viewpager.setCurrentItem(currPage);*/
                    ++currPage;
                    if (currPage >= totalPages)
                        currPage = 0;

                    viewPager.setCurrentItem(currPage);
                }
                handler.postDelayed(autoPage, DELAY);
            }
        };

        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                    touched = true;
                else if (event.getAction() == MotionEvent.ACTION_UP)
                    touched = false;
                return false;
            }
        });

        handler.postDelayed(autoPage, DELAY);
    }

    /**
     * Sets the delay after which the viewpager will be scrolled
     * @param delay time in miliseconds
     */
    public void setDelay(int delay) {
        this.DELAY = delay;
    }

    /**
     * Destroy the auto paging thread
     * Must always be called
     */
    public void destroyAutoPager(){

        handler.removeCallbacks(autoPage);
    }

    /**
     * Make dots around the viewpager
     * @param viewPager the viewpager to have dots
     * @param dot_ll the linear layout added in your layout file that will contain dots
     */
    public void makeDots(ViewPager viewPager, final LinearLayout dot_ll) {

        dot_ll.removeAllViews();

        for (int i = 0; i < totalPages; i++) {
            LinearLayout.LayoutParams params;
            params = new LinearLayout.LayoutParams(context.getResources().getDimensionPixelOffset(R.dimen._5dp), context.getResources().getDimensionPixelOffset(R.dimen._5dp));

            ImageView imageView = new ImageView(context);
            imageView.setLayoutParams(params);
            imageView.setImageResource(R.drawable.dot);
            imageView.setId(987 + i);

            dot_ll.addView(imageView);
        }

        if (totalPages > 0)
            bigDot(0, dot_ll);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                currPage = position;
                bigDot(position, dot_ll);
            }
            @Override
            public void onPageSelected(int position) {}
            @Override
            public void onPageScrollStateChanged(int state) {}
        });

    }

    /**
     * Internal method to highlight dot that is represeting the current page of the viewpager
     * @param n current page no of the viewpager
     * @param dot_ll the linear layout where we have the dots
     */
    private void bigDot(int n, LinearLayout dot_ll) {
        LinearLayout.LayoutParams params;

        for (int i = 0; i < totalPages; i++) {
            ImageView imageView = (ImageView) dot_ll.findViewById(987 + i);
            if (i == n)
                params = new LinearLayout.LayoutParams(context.getResources().getDimensionPixelOffset(R.dimen._8dp), context.getResources().getDimensionPixelOffset(R.dimen._8dp));
            else
                params = new LinearLayout.LayoutParams(context.getResources().getDimensionPixelOffset(R.dimen._5dp), context.getResources().getDimensionPixelOffset(R.dimen._5dp));
            imageView.setLayoutParams(params);
            imageView.setImageResource(R.drawable.dot);
        }
    }
}
