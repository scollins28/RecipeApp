package com.example.android.recipefinder;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.ArrayList;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.example.android.recipefinder.R.id.widget_next;
import static com.example.android.recipefinder.RecipeListWidetIntents.ACTION_NEXT;


/**
 * Implementation of App Widget functionality.
 */
public class RecipeListWidget extends AppWidgetProvider {
    static ArrayList <String> widgetIngredients = null;
    static int ingredientNumber = 0;
    static Context mContext;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int[] appWidgetIds) {
        mContext = context;
        RemoteViews views = new RemoteViews( context.getPackageName(), R.layout.recipe_list_widget );
        if (widgetIngredients!=null) {
            CharSequence widgetText = widgetIngredients.get( ingredientNumber );
            views.setViewVisibility( R.id.appwidget_text, VISIBLE);
            views.setViewVisibility( R.id.appwidget_ingredient_number, VISIBLE);
            views.setViewVisibility( R.id.appwidget_ingredient_of, VISIBLE);
            views.setViewVisibility( R.id.appwidget_ingredient_number_of_x, VISIBLE);
            views.setViewVisibility( R.id.widget_icon, GONE );
            views.setViewVisibility( R.id.next_button, VISIBLE);
            views.setViewVisibility( R.id.background_view, VISIBLE );
            views.setTextViewText( R.id.appwidget_text, widgetText );
            views.setTextViewText( R.id.appwidget_ingredient_number, String.valueOf( ingredientNumber+1 ) );
            views.setTextViewText( R.id.appwidget_ingredient_number_of_x, String.valueOf( widgetIngredients.size()) );
            Intent intent = new Intent( context, MainActivity.class );
            PendingIntent pendingIntent = PendingIntent.getActivity( context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT );
            views.setOnClickPendingIntent( R.id.appwidget_text, pendingIntent );
            views.setOnClickPendingIntent( R.id.widget_icon , pendingIntent);
            Intent nextIntent = new Intent( context, RecipeListWidetIntents.class );
            nextIntent.setAction( ACTION_NEXT );
            PendingIntent nextPendingIntent = PendingIntent.getService( context, 0, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent( widget_next, nextPendingIntent);
        }
        if (widgetIngredients==null){
            views.setViewVisibility( R.id.appwidget_text, GONE );
            views.setViewVisibility( R.id.appwidget_ingredient_number, GONE);
            views.setViewVisibility( R.id.appwidget_ingredient_of, GONE);
            views.setViewVisibility( R.id.appwidget_ingredient_number_of_x, GONE);
            views.setViewVisibility( R.id.widget_icon, VISIBLE);
            views.setViewVisibility( R.id.next_button, GONE);
            views.setViewVisibility( R.id.background_view, GONE );
        }

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget( appWidgetIds, views );
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget( context, appWidgetManager, appWidgetIds );
        }
    }

    @Override
    public void onEnabled(Context context) {
        ingredientNumber = 0;
    }

    @Override
    public void onDisabled(Context context) {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive( context, intent );
        if (intent != null) {
            final String action = intent.getAction();
            if (action.equals( ACTION_NEXT)) {
                handleNext();
            }
        }
    }

    private void handleNext() {
        int tempIngNumber = widgetIngredients.size()-1;
        if (ingredientNumber<=tempIngNumber){
            ingredientNumber++;
        }
        else {
            ingredientNumber=1;
        }
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance( mContext);
        int [] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName( mContext, RecipeListWidget.class ));
        updateAppWidget( mContext, appWidgetManager, appWidgetIds );
    }

    @Override
    public void onRestored(Context context, int[] oldWidgetIds, int[] newWidgetIds) {
        super.onRestored( context, oldWidgetIds, newWidgetIds );

    }
}

