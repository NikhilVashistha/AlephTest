package com.ndroidpro.alephtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


// http://stackoverflow.com/questions/19845059/slide-in-while-fade-in-a-textview
// http://stackoverflow.com/questions/22478114/android-item-swiping-on-listview
// http://www.androidhive.info/2012/02/android-custom-listview-with-image-and-text/
// https://github.com/lgvalle/Material-Animations
// http://stackoverflow.com/questions/26724964/how-to-animate-recyclerview-items-when-they-appear
// important
//http://stackoverflow.com/questions/35919060/animating-display-of-items-in-listview-in-android

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void fadeOutView(View view) {
        Animation fadeOut = AnimationUtils.loadAnimation(view.getContext(), R.anim.fade_out);
        if (fadeOut != null) {
            fadeOut.setAnimationListener(new ViewAnimationListener(view) {
                @Override
                protected void onAnimationStart(View view, Animation animation) {

                }

                @Override
                protected void onAnimationEnd(View view, Animation animation) {
                    view.setVisibility(View.GONE);
                }
            });
            view.startAnimation(fadeOut);
        }
    }

    private void fadeInView(View view) {
        Animation fadeIn = AnimationUtils.loadAnimation(view.getContext(), R.anim.fade_in);
        if (fadeIn != null) {
            fadeIn.setAnimationListener(new ViewAnimationListener(view) {
                @Override
                protected void onAnimationStart(View view, Animation animation) {
                    view.setVisibility(View.VISIBLE);
                }

                @Override
                protected void onAnimationEnd(View view, Animation animation) {

                }
            });
            view.startAnimation(fadeIn);
        }
    }

    private void slideInView(View view) {
        Animation slideIn = AnimationUtils.loadAnimation(view.getContext(), R.anim.slide_in_left);
        if (slideIn != null) {
            slideIn.setAnimationListener(new ViewAnimationListener(view) {
                @Override
                protected void onAnimationStart(View view, Animation animation) {
                    view.setVisibility(View.VISIBLE);
                }

                @Override
                protected void onAnimationEnd(View view, Animation animation) {

                }
            });
            view.startAnimation(slideIn);
        }
    }

    private void slideOutView(View view) {
        Animation slideOut = AnimationUtils.loadAnimation(view.getContext(), R.anim.slide_out_left);
        if (slideOut != null) {
            slideOut.setAnimationListener(new ViewAnimationListener(view) {
                @Override
                protected void onAnimationStart(View view, Animation animation) {

                }

                @Override
                protected void onAnimationEnd(View view, Animation animation) {
                    view.setVisibility(View.GONE);
                }
            });
            view.startAnimation(slideOut);
        }
    }

    private abstract class ViewAnimationListener implements Animation.AnimationListener {

        private final View view;

        protected ViewAnimationListener(View view) {
            this.view = view;
        }

        @Override
        public void onAnimationStart(Animation animation) {
            onAnimationStart(this.view, animation);
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            onAnimationEnd(this.view, animation);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }

        protected abstract void onAnimationStart(View view, Animation animation);
        protected abstract void onAnimationEnd(View view, Animation animation);
    }
}
