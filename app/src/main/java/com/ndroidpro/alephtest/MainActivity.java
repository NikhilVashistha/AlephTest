package com.ndroidpro.alephtest;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


// http://stackoverflow.com/questions/19845059/slide-in-while-fade-in-a-textview
// http://stackoverflow.com/questions/22478114/android-item-swiping-on-listview
// http://www.androidhive.info/2012/02/android-custom-listview-with-image-and-text/
// https://github.com/lgvalle/Material-Animations
// http://stackoverflow.com/questions/26724964/how-to-animate-recyclerview-items-when-they-appear
// important
//http://stackoverflow.com/questions/35919060/animating-display-of-items-in-listview-in-android

/**
 * Created by Nikhil Vashistha on 25-07-2016 for AlephTest.
 */
public class MainActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeContainer;
    private ListView list;
    private ArrayAdapter<String> listAdapter;
    private int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        list = (ListView)findViewById(R.id.lvItems);

        // Creating the list adapter and populating the list
        listAdapter = new CustomListAdapter(this, R.layout.list_item);

        // loop for adding data in list
        for ( i=0; i<2;i++) {
            listAdapter.add("Aleph Test " + i);
        }
        list.setAdapter(listAdapter);


        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                refreshItems();
            }
        });

        // Creating an item click listener, to open/close our Detail Screen for each item
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {

                View detailScreen = view.findViewById(R.id.detail_screen);

                // Creating the expand animation for the item
                ExpandAnimation expandAni = new ExpandAnimation(detailScreen, 500);

                // Start the animation for the Detail Screen
                detailScreen.startAnimation(expandAni);

                showBackButton();
            }
        });

    }

    private void showBackButton() {

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home :
                this.finish();

        }

        return super.onOptionsItemSelected(item);
    }

    void refreshItems() {
        // Load items
        listAdapter.add("Aleph Test"+i);
        i++;
        // Load complete
        onItemsLoadComplete();
    }

    void onItemsLoadComplete() {
        // Update the adapter and notify data set changed
        list.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();
        list.startLayoutAnimation();

        // Stop refresh animation
        swipeContainer.setRefreshing(false);
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

    class CustomListAdapter extends ArrayAdapter<String> {

        public CustomListAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.list_item, null);
            }

            ((TextView)convertView.findViewById(R.id.title)).setText(getItem(position));

            // Resets the Detail screen to be closed
            View detailScreen = convertView.findViewById(R.id.detail_screen);
            ((LinearLayout.LayoutParams) detailScreen.getLayoutParams()).bottomMargin = -150;
            detailScreen.setVisibility(View.GONE);

            return convertView;
        }
    }
}
