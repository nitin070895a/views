package nitin.thecrazyprogrammer.views;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

/**
 * Created by Nitin Khurana on 11/2/2017.
 *
 * Scrolls a view with an animation
 */
public class AnimatedScroller {

    View view;

    /**
     * Scrolls a view with an animation
     * @param view the view to be scrolled with an animation
     */
    public AnimatedScroller(View view){

        this.view = view;
    }

    /**
     * Scrolls the view given in the constructor with an animation
     * @param x0 the x coordinate of the destination position
     * @param y0 the y coordinate of the destination position
     * @param duration the duration of the animation, i.e the time the animation should take to reach the destination coordinates
     */
    public void scrollTo(int x0, int y0, int duration) {
        int x = x0;
        int y = y0;
        ObjectAnimator xTranslate = ObjectAnimator.ofInt(view, "scrollX", x);
        ObjectAnimator yTranslate = ObjectAnimator.ofInt(view, "scrollY", y);

        AnimatorSet animators = new AnimatorSet();
        animators.setDuration(duration);
        animators.playTogether(xTranslate, yTranslate);
        animators.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator arg0) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onAnimationRepeat(Animator arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animator arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationCancel(Animator arg0) {
                // TODO Auto-generated method stub

            }
        });
        animators.start();
    }
}
