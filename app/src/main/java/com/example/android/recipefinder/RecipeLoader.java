package com.example.android.recipefinder;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class RecipeLoader extends AsyncTaskLoader<List<Recipe>> {
    private static final String LOG_TAG = RecipeLoader.class.getSimpleName();
    private String mURL;
    private int webOrLocal = 0;

    //Constructor that takes in and stores mURL.
    public RecipeLoader (Context context, String url){
        super(context);
        mURL=url;
        webOrLocal = 1;}

    public RecipeLoader (Context context){
        super(context);
    }

    //Forces the load.
    @Override
    protected void onStartLoading(){
        forceLoad();
    }

    //Loads the data for the grid in the background. If the url is null, terminates here. Initiates the Film Data fetchfilms methods (which then uses subsequent FilmData methods).
    @Override
    public ArrayList<Recipe> loadInBackground (){
        ArrayList<Recipe> recipes = null;
        if (mURL == null && webOrLocal ==1){
            return null;
        }
        else if (mURL!= null && webOrLocal == 1) {
            Log.e( LOG_TAG, mURL );
            recipes = RecipeData.fetchRecipes( mURL );
        }
        return recipes;
    }



}
