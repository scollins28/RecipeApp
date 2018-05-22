package com.example.android.recipefinder.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.recipefinder.Data.RecipeContract.RecipeTable;


public class RecipeDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "recipes.db";
    public static final int VERSION_NUMBER = 3;

    public RecipeDbHelper (Context context){
        super (context, DATABASE_NAME, null, VERSION_NUMBER);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_RECIPE_TABLE = "CREATE TABLE " +
                RecipeTable.TABLE_NAME + "(" +
                RecipeTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                RecipeTable.RECIPE_NAME + " TEXT," +
                RecipeTable.INGREDIENT_ONE_QUANTITY + " TEXT," +
                RecipeTable.INGREDIENT_ONE_MEASURE + " TEXT," +
                RecipeTable.INGREDIENT_ONE_NAME + " TEXT," +
                RecipeTable.INGREDIENT_TWO_QUANTITY + " TEXT," +
                RecipeTable.INGREDIENT_TWO_MEASURE+ " TEXT," +
                RecipeTable.INGREDIENT_TWO_NAME+ " TEXT," +
                RecipeTable.INGREDIENT_THREE_QUANTITY+ " TEXT," +
                RecipeTable.INGREDIENT_THREE_MEASURE+ " TEXT," +
                RecipeTable.INGREDIENT_THREE_NAME+ " TEXT," +
                RecipeTable.INGREDIENT_FOUR_QUANTITY+ " TEXT," +
                RecipeTable.INGREDIENT_FOUR_MEASURE+ " TEXT," +
                RecipeTable.INGREDIENT_FOUR_NAME+ " TEXT," +
                RecipeTable.INGREDIENT_FIVE_QUANTITY+ " TEXT," +
                RecipeTable.INGREDIENT_FIVE_MEASURE+ " TEXT," +
                RecipeTable.INGREDIENT_FIVE_NAME+ " TEXT," +
                RecipeTable.INGREDIENT_SIX_QUANTITY+ " TEXT," +
                RecipeTable.INGREDIENT_SIX_MEASURE+ " TEXT," +
                RecipeTable.INGREDIENT_SIX_NAME+ " TEXT," +
                RecipeTable.INGREDIENT_SEVEN_QUANTITY+ " TEXT," +
                RecipeTable.INGREDIENT_SEVEN_MEASURE+ " TEXT," +
                RecipeTable.INGREDIENT_SEVEN_NAME+ " TEXT," +
                RecipeTable.INGREDIENT_EIGHT_QUANTITY+ " TEXT," +
                RecipeTable.INGREDIENT_EIGHT_MEASURE+ " TEXT," +
                RecipeTable.INGREDIENT_EIGHT_NAME+ " TEXT," +
                RecipeTable.INGREDIENT_NINE_QUANTITY+ " TEXT," +
                RecipeTable.INGREDIENT_NINE_MEASURE+ " TEXT," +
                RecipeTable.INGREDIENT_NINE_NAME+ " TEXT," +
                RecipeTable.INGREDIENT_TEN_QUANTITY+ " TEXT," +
                RecipeTable.INGREDIENT_TEN_MEASURE+ " TEXT," +
                RecipeTable.INGREDIENT_TEN_NAME+ " TEXT," +
                RecipeTable.INGREDIENT_ELEVEN_QUANTITY+ " TEXT," +
                RecipeTable.INGREDIENT_ELEVEN_MEASURE+ " TEXT," +
                RecipeTable.INGREDIENT_ELEVEN_NAME+ " TEXT," +
                RecipeTable.INGREDIENT_TWELVE_QUANTITY+ " TEXT," +
                RecipeTable.INGREDIENT_TWELVE_MEASURE+ " TEXT," +
                RecipeTable.INGREDIENT_TWELVE_NAME+ " TEXT," +
                RecipeTable.STEP_ONE_SHORT_DESCRIPTION+ " TEXT," +
                RecipeTable.STEP_ONE_FULL_DESCRIPTION+ " TEXT," +
                RecipeTable.STEP_ONE_VIDEO_URL+ " TEXT," +
                RecipeTable.STEP_ONE_THUMB_URL+ " TEXT," +
                RecipeTable.STEP_TWO_SHORT_DESCRIPTION+ " TEXT," +
                RecipeTable.STEP_TWO_FULL_DESCRIPTION+ " TEXT," +
                RecipeTable.STEP_TWO_VIDEO_URL+ " TEXT," +
                RecipeTable.STEP_TWO_THUMB_URL+ " TEXT," +
                RecipeTable.STEP_THREE_SHORT_DESCRIPTION+ " TEXT," +
                RecipeTable.STEP_THREE_FULL_DESCRIPTION+ " TEXT," +
                RecipeTable.STEP_THREE_VIDEO_URL+ " TEXT," +
                RecipeTable.STEP_THREE_THUMB_URL+ " TEXT," +
                RecipeTable.STEP_FOUR_SHORT_DESCRIPTION+ " TEXT," +
                RecipeTable.STEP_FOUR_FULL_DESCRIPTION+ " TEXT," +
                RecipeTable.STEP_FOUR_VIDEO_URL+ " TEXT," +
                RecipeTable.STEP_FOUR_THUMB_URL+ " TEXT," +
                RecipeTable.STEP_FIVE_SHORT_DESCRIPTION+ " TEXT," +
                RecipeTable.STEP_FIVE_FULL_DESCRIPTION+ " TEXT," +
                RecipeTable.STEP_FIVE_VIDEO_URL+ " TEXT," +
                RecipeTable.STEP_FIVE_THUMB_URL+ " TEXT," +
                RecipeTable.STEP_SIX_SHORT_DESCRIPTION+ " TEXT," +
                RecipeTable.STEP_SIX_FULL_DESCRIPTION+ " TEXT," +
                RecipeTable.STEP_SIX_VIDEO_URL+ " TEXT," +
                RecipeTable.STEP_SIX_THUMB_URL+ " TEXT," +
                RecipeTable.STEP_SEVEN_SHORT_DESCRIPTION+ " TEXT," +
                RecipeTable.STEP_SEVEN_FULL_DESCRIPTION+ " TEXT," +
                RecipeTable.STEP_SEVEN_VIDEO_URL+ " TEXT," +
                RecipeTable.STEP_SEVEN_THUMB_URL+ " TEXT," +
                RecipeTable.STEP_EIGHT_SHORT_DESCRIPTION+ " TEXT," +
                RecipeTable.STEP_EIGHT_FULL_DESCRIPTION+ " TEXT," +
                RecipeTable.STEP_EIGHT_VIDEO_URL+ " TEXT," +
                RecipeTable.STEP_EIGHT_THUMB_URL+ " TEXT," +
                RecipeTable.STEP_NINE_SHORT_DESCRIPTION+ " TEXT," +
                RecipeTable.STEP_NINE_FULL_DESCRIPTION+ " TEXT," +
                RecipeTable.STEP_NINE_VIDEO_URL+ " TEXT," +
                RecipeTable.STEP_NINE_THUMB_URL+ " TEXT," +
                RecipeTable.STEP_TEN_SHORT_DESCRIPTION+ " TEXT," +
                RecipeTable.STEP_TEN_FULL_DESCRIPTION+ " TEXT," +
                RecipeTable.STEP_TEN_VIDEO_URL+ " TEXT," +
                RecipeTable.STEP_TEN_THUMB_URL+ " TEXT," +
                RecipeTable.STEP_ELEVEN_SHORT_DESCRIPTION+ " TEXT," +
                RecipeTable.STEP_ELEVEN_FULL_DESCRIPTION+ " TEXT," +
                RecipeTable.STEP_ELEVEN_VIDEO_URL+ " TEXT," +
                RecipeTable.STEP_ELEVEN_THUMB_URL+ " TEXT," +
                RecipeTable.STEP_TWELVE_SHORT_DESCRIPTION+ " TEXT," +
                RecipeTable.STEP_TWELVE_FULL_DESCRIPTION+ " TEXT," +
                RecipeTable.STEP_TWELVE_VIDEO_URL+ " TEXT," +
                RecipeTable.STEP_TWELVE_THUMB_URL+ " TEXT," +
                RecipeTable.STEP_THIRTEEN_SHORT_DESCRIPTION+ " TEXT," +
                RecipeTable.STEP_THIRTEEN_FULL_DESCRIPTION+ " TEXT," +
                RecipeTable.STEP_THIRTEEN_VIDEO_URL+ " TEXT," +
                RecipeTable.STEP_THIRTEEN_THUMB_URL+ " TEXT," +
                RecipeTable.SERVINGS+ " TEXT," +
                RecipeTable.IMAGE_URL+ " TEXT"
                + ");";

    db.execSQL(SQL_CREATE_RECIPE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL( "DROP TABLE IF EXISTS " + RecipeTable.TABLE_NAME );
        onCreate( db );
    }
}
