package com.vacadmin.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Dialog;
import android.os.Build;
import android.view.View;
import android.view.ViewAnimationUtils;

import com.vacadmin.constant.Constants;

/**
 * Created by hemendrag on 12/19/2016.
 */

public class CustomAnimUtils {

    public static CustomAnimUtils mCustomAnimUtils;


    /**
     * create singleton method for CustomAnimUtils single  objects
     *
     * @return
     */
    public static CustomAnimUtils getCustomAnimUtils() {

        if (mCustomAnimUtils == null) {
            mCustomAnimUtils = new CustomAnimUtils();
        }

        return mCustomAnimUtils;
    }


    // To reveal a previously invisible view using this effect:
    public void showRevealView(final View view) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // get the center for the clipping circle
            int cx = (view.getLeft() + view.getRight()) / 2;
            int cy = (view.getTop() + view.getBottom()) / 2;
            // get the final radius for the clipping circle
            int finalRadius = Math.max(view.getWidth(), view.getHeight());

            // create the animator for this view (the start radius is zero)
            Animator anim = null;
            anim = ViewAnimationUtils.createCircularReveal(view, cx, cy,
                    0, finalRadius);

            // make the view visible and start the animation
            view.setVisibility(View.VISIBLE);

            anim.setDuration(Constants.DURATION_MILI_SECOND_ANIM);

            anim.start();

            /*anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);

                    view.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            hideRevealView(view);

                        }
                    },2500);
                }
            });*/

    }

}

    // To hide a previously visible view using this effect:
    public void hideRevealView(final View view, final Dialog alertDialog) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // get the center for the clipping circle
            int cx = (view.getLeft() + view.getRight()) / 2;
            int cy = (view.getTop() + view.getBottom()) / 2;

            // get the initial radius for the clipping circle
            int initialRadius = view.getWidth();

            // create the animation (the final radius is zero)
            Animator anim = null;
                anim = ViewAnimationUtils.createCircularReveal(view, cx, cy,
                        initialRadius, 0);


                anim.setDuration(Constants.DURATION_MILI_SECOND_ANIM_HIDE);
                // make the view invisible when the animation is done
                anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                         view.setVisibility(View.INVISIBLE);
                        if(alertDialog!=null){
                            alertDialog.dismiss();
                        }

                    }
                });
                // start the animation
                anim.start();


        }


    }

}
