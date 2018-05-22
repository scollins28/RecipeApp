package com.example.android.recipefinder;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

public class RecipeListWidetIntents extends IntentService {
    public static final String ACTION_PREVIOUS = "ActionRecieverPrevious";
    public static final String ACTION_NEXT = "ActionRecieverNext";

    /**
     */
    public RecipeListWidetIntents() {
        super("RecipeListWidetIntents");
    }


    public static void startActionPrevious(Context context) {
        Log.e( "SAP", "ACTUALLY STARTS" );
        Intent intent = new Intent( context, RecipeListWidetIntents.class );
        intent.setAction( ACTION_PREVIOUS );
        context.startService( intent );
    }

    public static void startActionNext(Context context) {
        Log.e( "SAN", "ACTUALLY STARTS" );
        Intent intent = new Intent( context, RecipeListWidetIntents.class );
        intent.setAction( ACTION_NEXT );
        context.startService( intent );
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.e( "DO YOU HANDLE", "AN INTENT" );
        if (intent != null) {
            final String action = intent.getAction();
            Log.e( "SO FAR SO GOOD", "CAN WE GO FURTHER" );
            if (action.equals( ACTION_PREVIOUS)) {
                Log.e( "LOOKS LIKE IT", "BUT THE LAST STEP" );
                handlePrevious();
            }
            else if (action.equals( ACTION_NEXT)) {
                Log.e( "LOOKS LIKE IT", "BUT THE LAST STEP" );
                handleNext();
            }
        }
    }

    private void handlePrevious() {
        Log.e("GOT TO HERE", String.valueOf( RecipeListWidget.ingredientNumber ) );
            if (RecipeListWidget.ingredientNumber>0){
                RecipeListWidget.ingredientNumber--;
            }
            else {
                RecipeListWidget.ingredientNumber=RecipeListWidget.widgetIngredients.size();
            }
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance( this );
        int [] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName( this, RecipeListWidget.class ));
        RecipeListWidget.updateAppWidget( this, appWidgetManager, appWidgetIds );
        }


    private void handleNext() {
        Log.e("GOT TO HERE", String.valueOf( RecipeListWidget.ingredientNumber ) );
        if (RecipeListWidget.ingredientNumber<RecipeListWidget.widgetIngredients.size()){
            RecipeListWidget.ingredientNumber++;
        }
        else {
            RecipeListWidget.ingredientNumber=0;
        }
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance( this );
        int [] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName( this, RecipeListWidget.class ));
        RecipeListWidget.updateAppWidget( this, appWidgetManager, appWidgetIds );
    }
    }