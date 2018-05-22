package com.example.android.recipefinder;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import java.util.ArrayList;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.example.android.recipefinder.Data.RecipeContract.RecipeTable.CONTENT_URI;
import static com.example.android.recipefinder.R.id.widget_previous;
import static com.example.android.recipefinder.RecipeListWidetIntents.ACTION_NEXT;
import static com.example.android.recipefinder.RecipeListWidetIntents.ACTION_PREVIOUS;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeListWidget extends AppWidgetProvider {
    static ArrayList <String> widgetIngredients = null;
    static int ingredientNumber = 0;
    static Context mContext;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int[] appWidgetIds) {
        Log.e( "UPDATING", "1");
        mContext = context;
        if (widgetIngredients!=null) {
            Log.e( "UPDATING", widgetIngredients.toString() );
        }
        RemoteViews views = new RemoteViews( context.getPackageName(), R.layout.recipe_list_widget );
        if (widgetIngredients!=null) {
            CharSequence widgetText = widgetIngredients.get( ingredientNumber );
            views.setViewVisibility( R.id.appwidget_text, VISIBLE);
            views.setViewVisibility( R.id.appwidget_ingredient_number, VISIBLE);
            views.setViewVisibility( R.id.appwidget_ingredient_of, VISIBLE);
            views.setViewVisibility( R.id.appwidget_ingredient_number_of_x, VISIBLE);
            views.setViewVisibility( R.id.widget_icon, GONE );
            views.setViewVisibility( R.id.next_button, VISIBLE);
            views.setViewVisibility( R.id.previous_button, VISIBLE);
            views.setTextViewText( R.id.appwidget_text, widgetText );
            views.setTextViewText( R.id.appwidget_ingredient_number, String.valueOf( ingredientNumber ) );
            views.setTextViewText( R.id.appwidget_ingredient_number_of_x, String.valueOf( widgetIngredients.size()) );
            Log.e( "UPDATING", "a");
            Intent intent = new Intent( context, MainActivity.class );
            PendingIntent pendingIntent = PendingIntent.getActivity( context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT );
            views.setOnClickPendingIntent( R.id.appwidget_text, pendingIntent );
            Log.e( "UPDATING", "b");
            Intent previousIntent = new Intent( context, RecipeListWidetIntents.class );
            previousIntent.setAction( ACTION_PREVIOUS );
            PendingIntent previousPendingIntent = PendingIntent.getBroadcast( context, 0, previousIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent( R.id.previous_button, previousPendingIntent);
            Log.e( "UPDATING", previousPendingIntent.toString());
            Intent nextIntent = new Intent( context, RecipeListWidetIntents.class );
            nextIntent.setAction( ACTION_NEXT );
            PendingIntent nextPendingIntent = PendingIntent.getService( context, 0, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent( R.id.next_button, nextPendingIntent);
        }
        if (widgetIngredients==null){
            views.setViewVisibility( R.id.appwidget_text, GONE );
            views.setViewVisibility( R.id.appwidget_ingredient_number, GONE);
            views.setViewVisibility( R.id.appwidget_ingredient_of, GONE);
            views.setViewVisibility( R.id.appwidget_ingredient_number_of_x, GONE);
            views.setViewVisibility( R.id.widget_icon, VISIBLE);
            views.setViewVisibility( R.id.next_button, GONE);
            views.setViewVisibility( R.id.previous_button, GONE);
        }

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget( appWidgetIds, views );
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        Log.e( "ON UPDATE", "FFS" );
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget( context, appWidgetManager, appWidgetIds );
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive( context, intent );
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
        Log.e("GOT TO HERE", String.valueOf( ingredientNumber ) );
        if (ingredientNumber>0){
            ingredientNumber--;
        }
        else {
            ingredientNumber=widgetIngredients.size();
        }
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance( mContext );
        int [] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName( mContext, RecipeListWidget.class ));
        updateAppWidget( mContext, appWidgetManager, appWidgetIds );
    }


    private void handleNext() {
        Log.e("GOT TO HERE", String.valueOf(ingredientNumber ) );
        if (ingredientNumber<widgetIngredients.size()){
            ingredientNumber++;
        }
        else {
            ingredientNumber=0;
        }
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance( mContext);
        int [] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName( mContext, RecipeListWidget.class ));
        updateAppWidget( mContext, appWidgetManager, appWidgetIds );
    }
}

