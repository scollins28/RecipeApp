package com.example.android.recipefinder;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Recipe {

    String mRecipeName;
    private ArrayList <String> mIngredientQuantityArray = new ArrayList<>(  );
    private ArrayList <String> mIngredientMeasureArray= new ArrayList<>(  );
    private ArrayList <String> mIngredientNameArray= new ArrayList<>(  );
    public List <String> mStepShortDescriptionArray;
    private ArrayList <String> mStepLongDescriptionArray= new ArrayList<>(  );
    private ArrayList <String> mStepVideoUrlArray= new ArrayList<>(  );
    private ArrayList <String> mStepImageUrlArray= new ArrayList<>(  );
    private String mRecipeServingSize;
    private String mMainImageURL;

    Recipe() {
    }



    Recipe (String recipeName, ArrayList<String> ingredientQuantityArray, ArrayList<String> ingredientMeasureArray,
            ArrayList<String> ingredientNameArray, List stepShortDescriptonArray, ArrayList<String> stepLongDescriptionArray,
            ArrayList<String> stepVideoUrlArray, ArrayList<String> stepImageUrlArray, String recipeServingSize, String mainImageURL){
        mRecipeName = recipeName;
        mIngredientMeasureArray = ingredientMeasureArray;
        mIngredientQuantityArray = ingredientQuantityArray;
        mIngredientNameArray = ingredientNameArray;
        mStepShortDescriptionArray = stepShortDescriptonArray;
        mStepLongDescriptionArray = stepLongDescriptionArray;
        mStepVideoUrlArray = stepVideoUrlArray;
        mStepImageUrlArray = stepImageUrlArray;
        mRecipeServingSize = recipeServingSize;
        mMainImageURL = mainImageURL;
    }


    public String getRecipeName() {
        return mRecipeName;
    }

    public ArrayList<String> getIngredientQuantityArray() {
        return mIngredientQuantityArray;
    }

    public ArrayList<String> getIngredientMeasureArray () {
        return mIngredientMeasureArray;
    }

    public ArrayList<String> getIngredientNameArray () {
        return mIngredientNameArray;
    }

    public List getStepShortDescriptionArray () {
        return mStepShortDescriptionArray;
    }

    public ArrayList<String> getStepLongDescriptionArray () {
        return mStepLongDescriptionArray;
    }

    public ArrayList<String> getStepVideoUrlArray () {
        return mStepVideoUrlArray;
    }

    public ArrayList<String> getStepImageUrlArray () {
        return mStepImageUrlArray;
    }

    public String getRecipeServingSize() {
        return mRecipeServingSize;
    }

    public String getMainImageURL() {
        return mMainImageURL;
    }



}

