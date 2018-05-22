package com.example.android.recipefinder;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


// Custom adapter class that displays a list of Android-Me images in a GridView
public class MasterListAdapter extends BaseAdapter {


    // Keeps track of the context and list of images to display
    private Context mContext;
    private ArrayList<String> recipes;


    public MasterListAdapter(Context context, ArrayList<String>recipes) {
        mContext = context;
        this.recipes= recipes;
    }

    @Override
    public int getCount() {
        return recipes.size();
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
        FrameLayout frameLayout;
        ImageView imageView;
        TextView textView;
        if (convertView == null) {
            // If the view is not recycled, this creates a new ImageView to hold an image
            frameLayout = new FrameLayout(mContext);
            imageView = new ImageView( mContext );
            textView = new TextView( mContext );
            // Define the layout parameters
            frameLayout.addView( imageView );
            imageView.getLayoutParams().height = (int) mContext.getResources().getDimension(R.dimen.list_view_height_dp);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
            frameLayout.addView( textView);
            imageView.setImageResource( R.drawable.sample_image);
            textView.setText( recipes.get( position ));
            textView.setGravity( Gravity.BOTTOM );
            textView.setTextAlignment( View.TEXT_ALIGNMENT_CENTER );
            textView.setTextColor( Color.WHITE );
            textView.setTextSize( 32 );
        } else {
            frameLayout = (FrameLayout) convertView;
        }
        return frameLayout;
    }

}