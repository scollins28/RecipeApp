package com.example.android.recipefinder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.android.recipefinder.Data.RecipeContract;

import java.util.ArrayList;

import static com.example.android.recipefinder.Data.RecipeContract.*;
import static com.example.android.recipefinder.Data.RecipeContract.RecipeTable.*;
import static com.example.android.recipefinder.Data.RecipeContract.RecipeTable.CONTENT_URI;
import static com.example.android.recipefinder.Data.RecipeContract.RecipeTable.STEP_EIGHT_SHORT_DESCRIPTION;
import static com.example.android.recipefinder.Data.RecipeContract.RecipeTable.STEP_SEVEN_SHORT_DESCRIPTION;

public class RecipeStepsListFragment extends Fragment {

    String pizza = "Ingredients";
    String pasta = "Steps";
    static ArrayList<String> steps;
    Context mContext;
    int mRecipeId;
    ArrayList<String> recipeStepColumns;
    static StepListAdapter mAdapter;
    GridView gridView;


    public RecipeStepsListFragment(){
        mRecipeId = MainActivity.currentRecipeId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRecipeId = MainActivity.currentRecipeId;
        recipeStepColumns = getColumnNames();
        steps = addSteps();
        int stepsSize = steps.size();
        MainActivity.maxSteps =stepsSize;
        mContext=getContext();
        mAdapter = new StepListAdapter( mContext, steps, stepsSize);
        final View rootView = inflater.inflate( R.layout.recipe_steps_list_fragment, container, false );
        gridView = (GridView) rootView.findViewById(R.id.recipe_steps_list_grid_view);
        gridView.setAdapter(mAdapter);
        gridView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCallback.onStepSelected( position );
            }
        } );
        return rootView;
    }

    RecipeStepsListFragment.OnStepClickListener mCallback;

    public interface OnStepClickListener {
        void onStepSelected(int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach( context );
        try {
            mCallback = (RecipeStepsListFragment.OnStepClickListener) context;
        }
        catch (ClassCastException e){
            throw new ClassCastException( context.toString() + "must implement click listener" );
        }
    }

    public ArrayList<String> addSteps() {
        Cursor cursor = getActivity().getContentResolver().query( CONTENT_URI, null, null, null, null );
        ArrayList<String> recipeList = new ArrayList<>();
        cursor.moveToPosition( mRecipeId );
                String ingredientsStep = "Ingredients";
                recipeList.add( ingredientsStep);
                int i = 0;
                Boolean noMoreSteps= false;
        while (noMoreSteps!=true && i<getColumnNames().size()){
            if ((cursor.getString( cursor.getColumnIndex(getColumnNames().get( i ))))==null){
                noMoreSteps = true;
                break;
            }
            else {
                String stepName = cursor.getString( cursor.getColumnIndex( getColumnNames().get( i )) );
                recipeList.add( stepName );
                i++;
            }
        }
            return recipeList;
        }

    public ArrayList<String> getColumnNames(){
        ArrayList<String>recipeStepNameColumns = new ArrayList<String>();
        recipeStepNameColumns.add(RecipeContract.RecipeTable.STEP_ONE_SHORT_DESCRIPTION);
        recipeStepNameColumns.add(RecipeContract.RecipeTable.STEP_TWO_SHORT_DESCRIPTION);
        recipeStepNameColumns.add(RecipeContract.RecipeTable.STEP_THREE_SHORT_DESCRIPTION);
        recipeStepNameColumns.add(RecipeContract.RecipeTable.STEP_FOUR_SHORT_DESCRIPTION);
        recipeStepNameColumns.add(RecipeContract.RecipeTable.STEP_FIVE_SHORT_DESCRIPTION);
        recipeStepNameColumns.add(RecipeContract.RecipeTable.STEP_SIX_SHORT_DESCRIPTION);
        recipeStepNameColumns.add(RecipeContract.RecipeTable.STEP_SEVEN_SHORT_DESCRIPTION);
        recipeStepNameColumns.add(RecipeContract.RecipeTable.STEP_EIGHT_SHORT_DESCRIPTION);
        recipeStepNameColumns.add(RecipeContract.RecipeTable.STEP_NINE_SHORT_DESCRIPTION);
        recipeStepNameColumns.add(RecipeContract.RecipeTable.STEP_TEN_SHORT_DESCRIPTION);
        recipeStepNameColumns.add(RecipeContract.RecipeTable.STEP_ELEVEN_SHORT_DESCRIPTION);
        recipeStepNameColumns.add(RecipeContract.RecipeTable.STEP_TWELVE_SHORT_DESCRIPTION);
        recipeStepNameColumns.add(RecipeContract.RecipeTable.STEP_THIRTEEN_SHORT_DESCRIPTION);
    return recipeStepNameColumns;
    }

}
