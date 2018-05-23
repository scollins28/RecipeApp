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

import static com.example.android.recipefinder.RecipeListWidget.ingredientNumber;
import static com.example.android.recipefinder.RecipeListWidget.widgetIngredients;

public class RecipeListWidetIntents extends IntentService {
    public static final String ACTION_NEXT = "ActionRecieverNext";

    public RecipeListWidetIntents() {
        super("RecipeListWidetIntents");
    }

    public static void startActionNext(Context context) {
        Intent nextIntent = new Intent( context, RecipeListWidetIntents.class );
        nextIntent.setAction( ACTION_NEXT );
        context.startService( nextIntent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
                if (action.equals( ACTION_NEXT)) {
                handleNext();
            }
        }
    }

    private void handleNext() {
        int tempIngNumber = widgetIngredients.size()-1;
        if (ingredientNumber==tempIngNumber){
            ingredientNumber=0;
        }
        else {
            ingredientNumber++;
        }
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance( this );
        int [] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName( this, RecipeListWidget.class ));
        RecipeListWidget.updateAppWidget( this, appWidgetManager, appWidgetIds );
    }
    }
