package com.example.android.recipefinder;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecipeDetailsAdapter extends BaseAdapter {

        // Keeps track of the context and list of images to display
        private Context mContext;

        public RecipeDetailsAdapter(Context context) {
            mContext = context;
        }

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        /**
         * Creates a new ImageView for each item referenced by the adapter
         */
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        public View getView(final int position, View convertView, ViewGroup parent) {
            TextView textView = null;
            return textView;
        }

        public void setData (ViewGroup currentViewGroup){
            TextView textView;
            textView = currentViewGroup.findViewById( R.id.recipe_steps_instructions );
            // Define the layout parameters
            textView.setText( RecipeDetailsListFragment.ingredients);
            textView.setTextSize( 32 );

        }

    }
