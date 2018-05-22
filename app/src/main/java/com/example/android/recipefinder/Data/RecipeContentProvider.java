package com.example.android.recipefinder.Data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


import static android.content.UriMatcher.NO_MATCH;
import static com.example.android.recipefinder.Data.RecipeContract.AUTHORITY;
import static com.example.android.recipefinder.Data.RecipeContract.RecipeTable.TABLE_NAME;

public class RecipeContentProvider extends ContentProvider{

    private RecipeDbHelper mRecipeDbHelper;
    public static final int RECIPES = 100;
    public static final int RECIPE_WITH_ID = 101;
    public static final int RECIPE_WITH_NAME = 102;
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    public SQLiteDatabase db;

    public static UriMatcher buildUriMatcher(){
        UriMatcher uriMatcher = new UriMatcher( NO_MATCH );
        uriMatcher.addURI( AUTHORITY, RecipeContract.PATH_RECIPES, RECIPES);
        uriMatcher.addURI( AUTHORITY, RecipeContract.PATH_RECIPES+ "/#", RECIPE_WITH_ID);
        uriMatcher.addURI( AUTHORITY, RecipeContract.PATH_RECIPES+ "/*", RECIPE_WITH_NAME);
        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        mRecipeDbHelper = new RecipeDbHelper( context );
        db = mRecipeDbHelper.getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = mRecipeDbHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);
        Cursor cursor;

        switch (match) {
            case RECIPES:
                cursor =  db.query(TABLE_NAME, null, null, null, null, null, null);
                break;
            case RECIPE_WITH_ID:
                String id = uri.getPathSegments().get( 1 );
                String mSelection = "_id=?";
                String [] mSelectionArgs = new String[]{id};
                cursor = db.query( TABLE_NAME, projection, mSelection, mSelectionArgs , null, null, sortOrder );
                break;
            case RECIPE_WITH_NAME:
                String altID = uri.getPathSegments().get( 1 );
                String mAltSelection = "RECIPE_NAME=?";
                String [] mAltSelectionArgs = new String[]{altID};
                cursor = db.query( TABLE_NAME, projection, mAltSelection, mAltSelectionArgs , null, null, sortOrder );
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {


        int match = sUriMatcher.match(uri);
        long id = 0;
        Uri returnUri;

        switch (match) {
            case RECIPES:
                id = db.insert( RecipeContract.RecipeTable.TABLE_NAME, null, values);
                if ( id < 0 ) {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                } else {
                    returnUri = ContentUris.withAppendedId(RecipeContract.RecipeTable.CONTENT_URI, id);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        return returnUri;

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mRecipeDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);

        int recipesDeleted;

        switch (match) {
            case RECIPE_WITH_ID:
                String id = uri.getPathSegments().get(1);
                String newSelection = "_ID=?";
                String [] newSelectionArgs = new String[]{id};
                recipesDeleted = db.delete( TABLE_NAME, newSelection, newSelectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        return recipesDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
