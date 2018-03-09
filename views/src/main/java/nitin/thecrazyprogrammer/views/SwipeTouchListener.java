package nitin.thecrazyprogrammer.views;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/**
 * Provide swipe to dismiss gesture to a view
 * This class can be used to remove a view by swiping left to right or right to lefts
 *
 * To use this class, set an instance of this class as a touch listener to your view
 *
 * eg : myView.setOnTouchListener(new SwipeTouchListener(...);
 *
 * Created by Nitin Khurana on 8/23/2017.
 */
public class SwipeTouchListener implements View.OnTouchListener {

    int x = 0; // where touch was started or start position
    int vx = 0; // current location of the view
    int mx = 0; // current finger location
    int dx = 0; // delta x;  distance between current position and start position
    int percentage_scrolled = 0; // how much percentage of the view has been scrolled

    boolean isLeft = true;

    boolean fade = true;
    int percentage_scrolled_to_dismiss;
    SwipeViewDismissListener swipeViewDismissListener;
    DisplayMetrics metrics;

    /**
     * @param fade whether to fade the view while it is being swiped
     * @param percentage_scrolled_to_dismiss criteria to dismiss the view (minimum percentage required to be scrolled to dismiss the view)
     * @param swipeViewDismissListener the listener that will be invoked when the view should be dismissed
     */
    public SwipeTouchListener(Context context, boolean fade, int percentage_scrolled_to_dismiss, SwipeViewDismissListener swipeViewDismissListener){
        this.fade = fade;
        this.percentage_scrolled_to_dismiss = percentage_scrolled_to_dismiss;
        this.swipeViewDismissListener = swipeViewDismissListener;


        metrics = context.getResources().getDisplayMetrics();
    }

    @Override
    public boolean onTouch(final View view, MotionEvent event) {

        view.getParent().requestDisallowInterceptTouchEvent(true);
        switch (event.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN:
                vx = ((int) view.getX());
                x = (int) event.getRawX();
                percentage_scrolled = 0;

                break;

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                x = 0;

                if(percentage_scrolled >= percentage_scrolled_to_dismiss && event.getAction() == MotionEvent.ACTION_UP){

                    // scrolled enough to dismiss
                    // animate to end
                    TranslateAnimation animation = new TranslateAnimation(0, isLeft ? (-metrics.widthPixels - view.getX()) : (metrics.heightPixels - view.getX()), 0, 0);
                    animation.setDuration(200);
                    animation.setRepeatCount(0);
                    animation.setStartOffset(0);
                    animation.setFillAfter(true);
                    animation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {

                            // dismissing the view
                            swipeViewDismissListener.dismiss(view);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    view.startAnimation(animation);

                }
                else {
                    // not dismissed
                    // back to initial state

                    view.setX(vx);
                    if(fade)
                        view.setAlpha(1.0f);
/*
                    BasicFunctions.showLog1("X " + view.getX() + " VX " + vx);
                    TranslateAnimation animation = new TranslateAnimation(0, vx - view.getX(), 0, 0);
                    animation.setDuration(2000);
                    animation.setFillAfter(true);
                    animation.setFillEnabled(true);
                    animation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {


                            view.setX(vx);
                            if(fade) view.setAlpha(1.0f);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    animation.setInterpolator(new AccelerateDecelerateInterpolator());
                    view.startAnimation(animation);*/
                }
                break;

            case MotionEvent.ACTION_POINTER_DOWN: break;
            case MotionEvent.ACTION_POINTER_UP: break;

            case MotionEvent.ACTION_MOVE:

                mx = (int) event.getRawX();
                dx = mx - x;
                if(dx < 0)
                    isLeft = true;
                else
                    isLeft = false;
                percentage_scrolled = Math.abs(dx) * 100 / view.getWidth();

                view.setX(vx + dx);
                if(fade){

                    float alpha = (100 - percentage_scrolled * 1.5f) / 100.0f;
                    view.setAlpha(alpha <= 0.1f ? 0.1f : alpha);
                }

                break;
        }

        return percentage_scrolled > 0;
    }

    public interface SwipeViewDismissListener {

        void dismiss(View view);
    }
}
