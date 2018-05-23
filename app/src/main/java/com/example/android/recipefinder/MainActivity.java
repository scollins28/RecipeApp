package com.example.android.recipefinder;

import android.app.ActionBar;
import android.app.LoaderManager;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.android.recipefinder.Data.RecipeContract.RecipeTable;

import java.util.ArrayList;

import static com.example.android.recipefinder.Data.RecipeContract.RecipeTable.CONTENT_URI;
import static com.example.android.recipefinder.Data.RecipeContract.RecipeTable.RECIPE_NAME;



public class MainActivity extends FragmentActivity implements MasterListFragment.OnImageClickListener, RecipeStepsListFragment.OnStepClickListener, RecipeDetailsListFragment.OnChangeSlideListener, LoaderManager.LoaderCallbacks {

    MasterListFragment masterListFragment;
    static FrameLayout secondaryFrame;
    static RecipeDetailsListFragment detailsListFragment;
    Fragment recipeStepsFragment;
    public static boolean mTwoPane;
    public int currentView;
    public static int currentRecipeId = 0;
    public static int currentRecipeStep =0;
    public static ArrayList<Recipe> recipes;
    public String recipeJSON;
    public Context mContext;
    private static int RECIPE_LOADER_ID = 0;
    static View.OnClickListener listener;
    public static int maxSteps = 0;
    ActionBar actionBar;
    android.support.v4.app.FragmentTransaction fragmentTransaction;
    android.support.v4.app.FragmentTransaction fragmentSecondaryTransaction;
    static long playerCurrentPosition = 0;
    static Cursor widgetCursor;
    public static int widgetRecipe;
    public static CheckBox widgetCheckbox;


    @Override
    public void onImageSelected(int position) {
        currentRecipeId = position;
        currentView = 2;
        setActionBar();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        recipeStepsFragment = new RecipeStepsListFragment();
        fragmentTransaction.replace( R.id.master_list_fragment, recipeStepsFragment ).commit();
        if (findViewById( R.id.secondary_tablet_fragment ) != null) {
            mTwoPane = true;
        }
        if (mTwoPane == true) {
            LinearLayout titlebar = (LinearLayout) findViewById( R.id.title_bar );
            titlebar.setVisibility( View.GONE );
            secondaryFrame = (FrameLayout) findViewById( R.id.secondary_tablet_fragment );
            secondaryFrame.setVisibility( View.VISIBLE );
            currentRecipeStep = 0;
            fragmentSecondaryTransaction = getSupportFragmentManager().beginTransaction();
            playerCurrentPosition = 0;
            detailsListFragment = new RecipeDetailsListFragment();
            fragmentSecondaryTransaction.replace( R.id.secondary_tablet_fragment, detailsListFragment).commit();
            }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
            mContext = getApplicationContext();
            recipeJSON = getString( R.string.recipe_JSON );
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader( RECIPE_LOADER_ID, null, this );
            super.onCreate( savedInstanceState );
            setContentView( R.layout.main_activity );
            mTwoPane = false;
            currentView = 1;
            if (getSharedPreferences("sharedPrefs", 0)!=null){
            SharedPreferences preferences = getSharedPreferences( "sharedPrefs", 0 );
            widgetRecipe = preferences.getInt( "Widget recipe", widgetRecipe );
              }
            masterListFragment = new MasterListFragment();
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add( R.id.master_list_fragment, masterListFragment ).commit();
            actionBar = getActionBar();
            actionBar.show();
            setActionBar();
            widgetCursor = getContentResolver().query( CONTENT_URI, null, null, null, null );
            if (savedInstanceState!=null){
                widgetRecipe = savedInstanceState.getInt( "Widget recipe");
            }
            listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (detailsListFragment.widgetCheckBox.isChecked()){
                        MainActivity.widgetRecipe = MainActivity.currentRecipeId;
                    }
                    else if (detailsListFragment.widgetCheckBox.isChecked()==false){
                        MainActivity.widgetRecipe=-1;
                    }
                    detailsListFragment.updateWidget();
                    Intent intent = new Intent(mContext, RecipeListWidget.class);
                    intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
                    int ids[] = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), RecipeListWidget.class));
                    intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
                    sendBroadcast(intent);
            }
        };
    }

    @Override
    public void onStepSelected(int position) {
        if (mTwoPane == false) {
            currentRecipeStep = position;
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            playerCurrentPosition = 0;
            detailsListFragment = new RecipeDetailsListFragment();
            fragmentTransaction.replace( R.id.master_list_fragment, detailsListFragment ).commit();
            currentView = 3;
            setActionBar();
        } else {
            currentRecipeStep = position;
            LinearLayout titlebar = (LinearLayout) findViewById( R.id.title_bar );
            titlebar.setVisibility( View.GONE );
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            playerCurrentPosition = 0;
            detailsListFragment = new RecipeDetailsListFragment();
            fragmentTransaction.replace( R.id.secondary_tablet_fragment, detailsListFragment ).commit();
            setActionBar();
        }
    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean( "Two Pane State", mTwoPane );
        outState.putInt( "Current Page", currentView );
        outState.putLong("Video position", playerCurrentPosition);
        outState.putStringArrayList( "Widget list", RecipeListWidget.widgetIngredients );
        outState.putInt( "Widget recipe", widgetRecipe );
        super.onSaveInstanceState( outState );
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        mTwoPane = savedInstanceState.getBoolean( "Two Pane State" );
        currentView = savedInstanceState.getInt( "Current Page" );
        playerCurrentPosition = savedInstanceState.getLong( "Video position");
        RecipeListWidget.widgetIngredients = savedInstanceState.getStringArrayList( "Widget list" );
        widgetRecipe = savedInstanceState.getInt( "Widget recipe" );
        FrameLayout secondaryFrameLayout;
        if (mTwoPane == true) {
            switch (currentView) {
                case 1:
                        mTwoPane = false;
                        secondaryFrameLayout = (FrameLayout) findViewById( R.id.secondary_tablet_fragment );
                        secondaryFrameLayout.setVisibility( View.INVISIBLE);
                        masterListFragment = new MasterListFragment();
                        fragmentTransaction.replace( R.id.master_list_fragment, masterListFragment ).commit();
                        break;
                case 2: LinearLayout titlebar = (LinearLayout) findViewById( R.id.title_bar );
                        titlebar.setVisibility( View.GONE);
                        secondaryFrameLayout = (FrameLayout) findViewById( R.id.secondary_tablet_fragment );
                        secondaryFrameLayout.setVisibility( View.VISIBLE );
                        recipeStepsFragment = new RecipeStepsListFragment();
                        fragmentTransaction.replace( R.id.master_list_fragment, recipeStepsFragment ).commit();
                        fragmentSecondaryTransaction = getSupportFragmentManager().beginTransaction();
                        detailsListFragment = new RecipeDetailsListFragment();
                        detailsListFragment.currentVideoPosition = playerCurrentPosition;
                        fragmentSecondaryTransaction.replace( R.id.secondary_tablet_fragment, detailsListFragment ).commit();
                        break;
                default:break;
                }
                setActionBar();
        }
        else {
            switch (currentView){
                case 1:  masterListFragment = new MasterListFragment();
                         fragmentTransaction.replace( R.id.master_list_fragment, masterListFragment ).commit();
                         break;
                case 2: recipeStepsFragment = new RecipeStepsListFragment();
                        fragmentTransaction.replace( R.id.master_list_fragment, recipeStepsFragment ).commit();
                        break;
                case 3:
                    detailsListFragment = new RecipeDetailsListFragment();
                    fragmentTransaction.replace( R.id.master_list_fragment, detailsListFragment ).commit();
                        break;
                default: break;
            }
            setActionBar();
        }
        RecipeDetailsListFragment restoreDetailsListFragment = new RecipeDetailsListFragment();
        restoreDetailsListFragment.updateWidget();
        Intent intent = new Intent(mContext, RecipeListWidget.class);
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
        int ids[] = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), RecipeListWidget.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
        sendBroadcast(intent);
        super.onRestoreInstanceState( savedInstanceState );
    }


    public static ContentValues newContentValues (int position, Recipe recipeToAdd) {
        ContentValues contentValues = new ContentValues(  );
        int x = 0;
        int y = 0;
        int ingredientSize = recipes.get( position ).getIngredientNameArray().size();
        int stepsSize = recipes.get( position ).getStepLongDescriptionArray().size();
        contentValues.put( RECIPE_NAME , recipeToAdd.mRecipeName);
        Recipe mRecipeToAdd = recipeToAdd;
        if (ingredientSize>x) {
            contentValues.put( RecipeTable.INGREDIENT_ONE_QUANTITY,  recipes.get( position ).getIngredientQuantityArray().get( x ) );
            contentValues.put( RecipeTable.INGREDIENT_ONE_MEASURE,  recipes.get( position ).getIngredientMeasureArray().get( x ) );
            contentValues.put( RecipeTable.INGREDIENT_ONE_NAME, recipes.get( position ).getIngredientNameArray().get( x ) );
            x++;}
        if (ingredientSize>x) {
            contentValues.put( RecipeTable.INGREDIENT_TWO_QUANTITY,  recipes.get( position ).getIngredientQuantityArray().get( x ) );
            contentValues.put( RecipeTable.INGREDIENT_TWO_MEASURE,  recipes.get( position ).getIngredientMeasureArray().get( x ) );
            contentValues.put( RecipeTable.INGREDIENT_TWO_NAME, recipes.get( position ).getIngredientNameArray().get( x ) );
            x++;}
        if (ingredientSize>x) {
            contentValues.put( RecipeTable.INGREDIENT_THREE_QUANTITY,  recipes.get( position ).getIngredientQuantityArray().get( x ) );
            contentValues.put( RecipeTable.INGREDIENT_THREE_MEASURE,  recipes.get( position ).getIngredientMeasureArray().get( x ) );
            contentValues.put( RecipeTable.INGREDIENT_THREE_NAME, recipes.get( position ).getIngredientNameArray().get( x ) );
            x++;}
        if (ingredientSize>x) {
            contentValues.put( RecipeTable.INGREDIENT_FOUR_QUANTITY, recipes.get( position ).getIngredientQuantityArray().get( x ) );
            contentValues.put( RecipeTable.INGREDIENT_FOUR_MEASURE, recipes.get( position ).getIngredientMeasureArray().get( x ) );
            contentValues.put( RecipeTable.INGREDIENT_FOUR_NAME, recipes.get( position ).getIngredientNameArray().get( x ) );
            x++;}
        if (ingredientSize>x) {
            contentValues.put( RecipeTable.INGREDIENT_FIVE_QUANTITY, recipes.get( position ).getIngredientQuantityArray().get( x ) );
            contentValues.put( RecipeTable.INGREDIENT_FIVE_MEASURE, recipes.get( position ).getIngredientMeasureArray().get( x ) );
            contentValues.put( RecipeTable.INGREDIENT_FIVE_NAME, recipes.get( position ).getIngredientNameArray().get( x ) );
            x++;}
        if (ingredientSize>x) {
            contentValues.put( RecipeTable.INGREDIENT_SIX_QUANTITY, recipes.get( position ).getIngredientQuantityArray().get( x ) );
            contentValues.put( RecipeTable.INGREDIENT_SIX_MEASURE, recipes.get( position ).getIngredientMeasureArray().get( x ) );
            contentValues.put( RecipeTable.INGREDIENT_SIX_NAME, recipes.get( position ).getIngredientNameArray().get( x ) );
            x++;}
        if (ingredientSize>x) {
            contentValues.put( RecipeTable.INGREDIENT_SEVEN_QUANTITY, recipes.get( position ).getIngredientQuantityArray().get( x ) );
            contentValues.put( RecipeTable.INGREDIENT_SEVEN_MEASURE, recipes.get( position ).getIngredientMeasureArray().get( x ) );
            contentValues.put( RecipeTable.INGREDIENT_SEVEN_NAME, recipes.get( position ).getIngredientNameArray().get( x ) );
            x++;}
        if (ingredientSize>x) {
            contentValues.put( RecipeTable.INGREDIENT_EIGHT_QUANTITY, recipes.get( position ).getIngredientQuantityArray().get( x ) );
            contentValues.put( RecipeTable.INGREDIENT_EIGHT_MEASURE, recipes.get( position ).getIngredientMeasureArray().get( x ) );
            contentValues.put( RecipeTable.INGREDIENT_EIGHT_NAME, recipes.get( position ).getIngredientNameArray().get( x ) );
            x++;}
        if (ingredientSize>x) {
            contentValues.put( RecipeTable.INGREDIENT_NINE_QUANTITY, recipes.get( position ).getIngredientQuantityArray().get( x ) );
            contentValues.put( RecipeTable.INGREDIENT_NINE_MEASURE, recipes.get( position ).getIngredientMeasureArray().get( x ) );
            contentValues.put( RecipeTable.INGREDIENT_NINE_NAME, recipes.get( position ).getIngredientNameArray().get( x ) );
            x++;}
        if (ingredientSize>x) {
            contentValues.put( RecipeTable.INGREDIENT_TEN_QUANTITY, recipes.get( position ).getIngredientQuantityArray().get( x ) );
            contentValues.put( RecipeTable.INGREDIENT_TEN_MEASURE, recipes.get( position ).getIngredientMeasureArray().get( x ) );
            contentValues.put( RecipeTable.INGREDIENT_TEN_NAME, recipes.get( position ).getIngredientNameArray().get( x ) );
            x++;}
        if (ingredientSize>x) {
            contentValues.put( RecipeTable.INGREDIENT_ELEVEN_QUANTITY, recipes.get( position ).getIngredientQuantityArray().get( x ) );
            contentValues.put( RecipeTable.INGREDIENT_ELEVEN_MEASURE, recipes.get( position ).getIngredientMeasureArray().get( x ) );
            contentValues.put( RecipeTable.INGREDIENT_ELEVEN_NAME, recipes.get( position ).getIngredientNameArray().get( x ) );
            x++;}
        if (ingredientSize>x) {
            contentValues.put( RecipeTable.INGREDIENT_TWELVE_QUANTITY, recipes.get( position ).getIngredientQuantityArray().get( x ) );
            contentValues.put( RecipeTable.INGREDIENT_TWELVE_MEASURE, recipes.get( position ).getIngredientMeasureArray().get( x ) );
            contentValues.put( RecipeTable.INGREDIENT_TWELVE_NAME, recipes.get( position ).getIngredientNameArray().get( x ) );
        }
        if (stepsSize>y) {
            contentValues.put( RecipeTable.STEP_ONE_SHORT_DESCRIPTION, recipeToAdd.mStepShortDescriptionArray.get( y ));
                contentValues.put( RecipeTable.STEP_ONE_FULL_DESCRIPTION, recipes.get( position ).getStepLongDescriptionArray().get( y ) );
                contentValues.put( RecipeTable.STEP_ONE_VIDEO_URL, recipes.get( position ).getStepVideoUrlArray().get( y ) );
                contentValues.put( RecipeTable.STEP_ONE_THUMB_URL, recipes.get( position ).getStepImageUrlArray().get( y ) );
                y++;}
        if (stepsSize>y) {
                contentValues.put( RecipeTable.STEP_TWO_SHORT_DESCRIPTION, recipes.get( position ).mStepShortDescriptionArray.get( y ));
                contentValues.put( RecipeTable.STEP_TWO_FULL_DESCRIPTION, recipes.get( position ).getStepLongDescriptionArray().get( y ) );
                contentValues.put( RecipeTable.STEP_TWO_VIDEO_URL, recipes.get( position ).getStepVideoUrlArray().get( y) );
                contentValues.put( RecipeTable.STEP_TWO_THUMB_URL, recipes.get( position ).getStepImageUrlArray().get( y) );
                y++;}
        if (stepsSize>y) {
                contentValues.put( RecipeTable.STEP_THREE_SHORT_DESCRIPTION, mRecipeToAdd.mStepShortDescriptionArray.get(y));
                contentValues.put( RecipeTable.STEP_THREE_FULL_DESCRIPTION, recipes.get( position ).getStepLongDescriptionArray().get( y) );
                contentValues.put( RecipeTable.STEP_THREE_VIDEO_URL, recipes.get( position ).getStepVideoUrlArray().get( y) );
                contentValues.put( RecipeTable.STEP_THREE_THUMB_URL, recipes.get( position ).getStepImageUrlArray().get( y) );
                y++;}
        if (stepsSize>y) {
                contentValues.put( RecipeTable.STEP_FOUR_SHORT_DESCRIPTION, mRecipeToAdd.mStepShortDescriptionArray.get(y));
                contentValues.put( RecipeTable.STEP_FOUR_FULL_DESCRIPTION, recipes.get( position ).getStepLongDescriptionArray().get( y) );
                contentValues.put( RecipeTable.STEP_FOUR_VIDEO_URL, recipes.get( position ).getStepVideoUrlArray().get( y) );
                contentValues.put( RecipeTable.STEP_FOUR_THUMB_URL, recipes.get( position ).getStepImageUrlArray().get( y) );
                y++;}
        if (stepsSize>y) {
                contentValues.put( RecipeTable.STEP_FIVE_SHORT_DESCRIPTION, mRecipeToAdd.mStepShortDescriptionArray.get(y));
                contentValues.put( RecipeTable.STEP_FIVE_FULL_DESCRIPTION, recipes.get( position ).getStepLongDescriptionArray().get( y) );
                contentValues.put( RecipeTable.STEP_FIVE_VIDEO_URL, recipes.get( position ).getStepVideoUrlArray().get( y) );
                contentValues.put( RecipeTable.STEP_FIVE_THUMB_URL, recipes.get( position ).getStepImageUrlArray().get( y) );
                y++;}
        if (stepsSize>y) {
                contentValues.put( RecipeTable.STEP_SIX_SHORT_DESCRIPTION, mRecipeToAdd.mStepShortDescriptionArray.get(y));
                contentValues.put( RecipeTable.STEP_SIX_FULL_DESCRIPTION, recipes.get( position ).getStepLongDescriptionArray().get( y) );
                contentValues.put( RecipeTable.STEP_SIX_VIDEO_URL, recipes.get( position ).getStepVideoUrlArray().get( y) );
                contentValues.put( RecipeTable.STEP_SIX_THUMB_URL, recipes.get( position ).getStepImageUrlArray().get( y) );
                y++;}
        if (stepsSize>y) {
                contentValues.put( RecipeTable.STEP_SEVEN_SHORT_DESCRIPTION, mRecipeToAdd.mStepShortDescriptionArray.get(y));
                contentValues.put( RecipeTable.STEP_SEVEN_FULL_DESCRIPTION, recipes.get( position ).getStepLongDescriptionArray().get( y) );
                contentValues.put( RecipeTable.STEP_SEVEN_VIDEO_URL, recipes.get( position ).getStepVideoUrlArray().get( y) );
                contentValues.put( RecipeTable.STEP_SEVEN_THUMB_URL, recipes.get( position ).getStepImageUrlArray().get( y ) );
                y++;}
        if (stepsSize>y) {
                contentValues.put( RecipeTable.STEP_EIGHT_SHORT_DESCRIPTION, mRecipeToAdd.mStepShortDescriptionArray.get(y));
                contentValues.put( RecipeTable.STEP_EIGHT_FULL_DESCRIPTION, recipes.get( position ).getStepLongDescriptionArray().get( y) );
                contentValues.put( RecipeTable.STEP_EIGHT_VIDEO_URL, recipes.get( position ).getStepVideoUrlArray().get( y) );
                contentValues.put( RecipeTable.STEP_EIGHT_THUMB_URL, recipes.get( position ).getStepImageUrlArray().get( y ) );
                y++;}
        if (stepsSize>y) {
                contentValues.put( RecipeTable.STEP_NINE_SHORT_DESCRIPTION, mRecipeToAdd.mStepShortDescriptionArray.get(y));
                contentValues.put( RecipeTable.STEP_NINE_FULL_DESCRIPTION, recipes.get( position ).getStepLongDescriptionArray().get( y) );
                contentValues.put( RecipeTable.STEP_NINE_VIDEO_URL, recipes.get( position ).getStepVideoUrlArray().get( y) );
                contentValues.put( RecipeTable.STEP_NINE_THUMB_URL, recipes.get( position ).getStepImageUrlArray().get( y ) );
                y++;}
        if (stepsSize>y) {
                contentValues.put( RecipeTable.STEP_TEN_SHORT_DESCRIPTION, mRecipeToAdd.mStepShortDescriptionArray.get(y));
                contentValues.put( RecipeTable.STEP_TEN_FULL_DESCRIPTION, recipes.get( position ).getStepLongDescriptionArray().get( y) );
                contentValues.put( RecipeTable.STEP_TEN_VIDEO_URL, recipes.get( position ).getStepVideoUrlArray().get( y) );
                contentValues.put( RecipeTable.STEP_TEN_THUMB_URL, recipes.get( position ).getStepImageUrlArray().get( y ) );
                y++;}
        if (stepsSize>y) {
                contentValues.put( RecipeTable.STEP_ELEVEN_SHORT_DESCRIPTION, mRecipeToAdd.mStepShortDescriptionArray.get(y));
                contentValues.put( RecipeTable.STEP_ELEVEN_FULL_DESCRIPTION, recipes.get( position ).getStepLongDescriptionArray().get( y) );
                contentValues.put( RecipeTable.STEP_ELEVEN_VIDEO_URL, recipes.get( position ).getStepVideoUrlArray().get( y) );
                contentValues.put( RecipeTable.STEP_ELEVEN_THUMB_URL, recipes.get( position ).getStepImageUrlArray().get( y ) );
                y++;}
        if (stepsSize>y) {
                contentValues.put( RecipeTable.STEP_TWELVE_SHORT_DESCRIPTION, mRecipeToAdd.mStepShortDescriptionArray.get(y));
                contentValues.put( RecipeTable.STEP_TWELVE_FULL_DESCRIPTION, recipes.get( position ).getStepLongDescriptionArray().get( y) );
                contentValues.put( RecipeTable.STEP_TWELVE_VIDEO_URL, recipes.get( position ).getStepVideoUrlArray().get( y) );
                contentValues.put( RecipeTable.STEP_TWELVE_THUMB_URL, recipes.get( position ).getStepImageUrlArray().get( y ) );
            y++;}
        if (stepsSize>y) {
            contentValues.put( RecipeTable.STEP_THIRTEEN_SHORT_DESCRIPTION, mRecipeToAdd.mStepShortDescriptionArray.get(y));
            contentValues.put( RecipeTable.STEP_THIRTEEN_FULL_DESCRIPTION, recipes.get( position ).getStepLongDescriptionArray().get( y) );
            contentValues.put( RecipeTable.STEP_THIRTEEN_VIDEO_URL, recipes.get( position ).getStepVideoUrlArray().get( y) );
            contentValues.put( RecipeTable.STEP_THIRTEEN_THUMB_URL, recipes.get( position ).getStepImageUrlArray().get( y ) );
        }


            contentValues.put( RecipeTable.SERVINGS, recipes.get( position ).getRecipeServingSize() );
            contentValues.put( RecipeTable.IMAGE_URL, recipes.get( position ).getMainImageURL() );

        return contentValues;
    }

    public void insertNewData (int position, Recipe recipeToAdd){
        ContentValues contentValues = newContentValues(position, recipeToAdd);
        getContentResolver().insert( CONTENT_URI, contentValues);
    }


    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new RecipeLoader( this, recipeJSON);
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        Cursor loaderFinishedCursor = getContentResolver().query( CONTENT_URI, null, null, null, null );
        int cursorLength = loaderFinishedCursor.getCount();
        int recipesLength = recipes.size();

            for (int i = 0; i<recipesLength;i++) {
                String potentialRecipeToAdd = recipes.get( i ).mRecipeName;
                Boolean matchedRecipe = false;
                for (int x = 0; x < cursorLength; x++) {
                    loaderFinishedCursor.moveToPosition( x );
                    String cursorRecipe = loaderFinishedCursor.getString( loaderFinishedCursor.getColumnIndex( "recipeName" ) );
                    if (cursorRecipe.equals( potentialRecipeToAdd )){
                        matchedRecipe = true;
                        break;
                    }
                }
                if (matchedRecipe){
                    Log.e( "Matched Recipe", recipes.get(i).mRecipeName);
                }
                else {
                    Recipe recipeToAdd = recipes.get( i );
                    insertNewData(i, recipeToAdd);
                }
            }
            if (currentView==1) {
                masterListFragment = new MasterListFragment();
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace( R.id.master_list_fragment, masterListFragment ).commit();
            }
        widgetCursor = getContentResolver().query( CONTENT_URI, null, null, null, null );
        Intent intent = new Intent(this, RecipeListWidget.class);
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
        int ids[] = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), RecipeListWidget.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
        sendBroadcast(intent);
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

    @Override
    public void onArticleSelected(int position) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (position){
            case 0:
            if (currentRecipeStep - 1 >= 0) {
                currentRecipeStep--;
                playerCurrentPosition = 0;
                detailsListFragment = new RecipeDetailsListFragment();
                if (mTwoPane) {
                    fragmentTransaction.replace( R.id.secondary_tablet_fragment, detailsListFragment ).commit();
                } else {
                    fragmentTransaction.replace( R.id.master_list_fragment, detailsListFragment).commit();
                }
            }
            break;
            case 1:
                currentRecipeStep = 0;
                playerCurrentPosition = 0;
                detailsListFragment = new RecipeDetailsListFragment();
                if (mTwoPane) {
                    fragmentTransaction.replace( R.id.secondary_tablet_fragment, detailsListFragment ).commit();
                } else {
                    fragmentTransaction.replace( R.id.master_list_fragment, detailsListFragment).commit();
                }

            break;
                case 2:
                    int tempStepNumber = currentRecipeStep + 1;
                    if (maxSteps > tempStepNumber) {
                        currentRecipeStep++;
                        playerCurrentPosition = 0;
                        detailsListFragment = new RecipeDetailsListFragment();
                if (mTwoPane) {
                    fragmentTransaction.replace( R.id.secondary_tablet_fragment, detailsListFragment ).commit();
                } else {
                    fragmentTransaction.replace( R.id.master_list_fragment, detailsListFragment ).commit();
                }
            }
            break;
        }
        setActionBar();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (currentView==3){
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace( R.id.master_list_fragment, recipeStepsFragment ).commit();
                }
                currentView = 1;
                masterListFragment = new MasterListFragment();
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace( R.id.master_list_fragment, masterListFragment ).commit();
                }
        if (secondaryFrame!=null) {
            secondaryFrame = (FrameLayout) findViewById( R.id.secondary_tablet_fragment );
            secondaryFrame.setVisibility( View.GONE );
        }
        return super.onOptionsItemSelected(item);
    }

    public void setActionBar(){
        if (currentView!=1) {
            actionBar.setDisplayHomeAsUpEnabled( true );
        }
        else actionBar.setDisplayHomeAsUpEnabled( false );
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        SharedPreferences preferences = getSharedPreferences("sharedPrefs", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("Widget recipe", widgetRecipe);
        editor.commit();
    }

}


