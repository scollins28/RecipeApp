package com.example.android.recipefinder;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.LauncherActivity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.zip.Inflater;

import static android.content.ContentValues.TAG;
import static android.support.v7.widget.RecyclerView.*;
import static com.example.android.recipefinder.R.drawable.sample_image;


// Custom adapter class that displays a list of Android-Me images in a GridView
public class StepListAdapter extends BaseAdapter {

    // Keeps track of the context and list of images to display
    private Context mContext;
    private ArrayList<String> steps;
    private int mStepsSize;
    private LayoutInflater layoutInflater;



    public StepListAdapter(Context context, ArrayList<String>steps, int stepsSize) {
        mContext = context;
        this.steps= steps;
        mStepsSize = stepsSize;
    }

    @Override
    public int getCount() {
        return steps.size();
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
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public View getView(final int position, View convertView, ViewGroup parent) {
        TextView textView =null;

            layoutInflater = LayoutInflater.from( mContext );
            textView = new TextView( mContext );
            if (convertView == null) {
                textView.setLayoutParams( new GridView.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT ) );
            }

            String stepName = steps.get( position );
            textView.setText( stepName );
            textView.setTextSize( 32 );
            textView.setBackgroundResource( R.drawable.samplebackgroudncake );
            textView.setGravity( Gravity.BOTTOM );
            textView.setTextAlignment( View.TEXT_ALIGNMENT_CENTER );
            textView.setTextColor( Color.WHITE );
            textView.setBackgroundTintMode( PorterDuff.Mode.DARKEN);

        return textView;
    }

}