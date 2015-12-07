package preamped.empirestate.co.za.preamped.loadshedding;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;

import java.util.ArrayList;
import java.util.List;

import preamped.empirestate.co.za.preamped.R;

/**
 * Created by George Kapoya on 2015-04-17.
 */
public class Util {
    static int count = 0;
    static final String LOG = Util.class.getSimpleName();






    //my Animation Methothods


    private void animateText(View txt) {
        final ObjectAnimator an = ObjectAnimator.ofFloat(txt, View.SCALE_X, 0);
        an.setRepeatCount(1);
        an.setDuration(200);
        an.setRepeatMode(ValueAnimator.REVERSE);
        an.start();
    }


    public static void animateScaleX(View txt, long duration) {
        final ObjectAnimator an = ObjectAnimator.ofFloat(txt, View.SCALE_X, 0);
        an.setRepeatCount(1);
        an.setDuration(duration);
        an.setRepeatMode(ValueAnimator.REVERSE);
        an.start();
    }

    public static void shrink(View view, long duration, final UtilAnimationListener listener) {
        ObjectAnimator anx = ObjectAnimator.ofFloat(view, "scaleX", 1, 0);
        ObjectAnimator any = ObjectAnimator.ofFloat(view, "scaleY", 1, 0);

        anx.setDuration(duration);
        any.setDuration(duration);
        anx.setInterpolator(new AccelerateInterpolator());
        any.setInterpolator(new AccelerateInterpolator());

        AnimatorSet set = new AnimatorSet();
        List<Animator> animatorList = new ArrayList<>();
        animatorList.add((Animator) anx);
        animatorList.add((Animator) any);
        set.playTogether(animatorList);
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (listener != null)
                    listener.onAnimationEnded();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        set.start();
    }

    public static void animateRotationY(View view, long duration) {
        final ObjectAnimator an = ObjectAnimator.ofFloat(view, "rotation", 0.0f, 360f);
        //an.setRepeatCount(ObjectAnimator.REVERSE);
        an.setDuration(duration);
        an.setInterpolator(new AccelerateDecelerateInterpolator());
        an.start();
    }

    public static void animateRollup(View view, long duration) {
        final ObjectAnimator an = ObjectAnimator.ofFloat(view, View.SCALE_Y, 0.0f);
        //an.setRepeatCount(ObjectAnimator.REVERSE);
        an.setDuration(duration);
        an.setInterpolator(new AccelerateDecelerateInterpolator());
        an.start();
    }

    public static void animateSlideRight(View view, long duration) {
        final ObjectAnimator an = ObjectAnimator.ofFloat(
                view, "translate", 0, 100,0,100);
        //an.setRepeatCount(ObjectAnimator.REVERSE);
        an.setDuration(duration);
        an.setInterpolator(new AccelerateDecelerateInterpolator());
        an.start();
    }

    public static void animateFlipFade(Context ctx, View v) {
        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(ctx,
                R.animator.flip_fade);
        set.setTarget(v);
        set.start();
    }
    public static void flashSeveralTimes(final View view,
                                         final long duration, final int max,
                                         final UtilAnimationListener listener) {
        final ObjectAnimator an = ObjectAnimator.ofFloat(view, "alpha", 0, 1);
        an.setRepeatMode(ObjectAnimator.REVERSE);
        an.setDuration(duration);
        an.setInterpolator(new AccelerateDecelerateInterpolator());
        an.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                count++;
                if (count > max) {
                    count = 0;
                    an.cancel();
                    if (listener != null)
                        listener.onAnimationEnded();
                    return;
                }
                flashSeveralTimes(view, duration, max, listener);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        an.start();

    }


    public static void pretendFlash(final View v, final int duration, final int max, final UtilAnimationListener listener) {
        final ObjectAnimator an = ObjectAnimator.ofFloat(v, "alpha", 1, 1);
        an.setRepeatMode(ObjectAnimator.REVERSE);
        an.setDuration(duration);
        an.setInterpolator(new AccelerateDecelerateInterpolator());
        an.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                count++;
                if (count > max) {
                    count = 0;
                    an.cancel();
                    if (listener != null)
                        listener.onAnimationEnded();
                    return;
                }
                flashSeveralTimes(v, duration, max, listener);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        an.start();
    }

}
