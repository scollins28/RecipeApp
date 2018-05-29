package com.example.android.recipefinder;

import android.database.Cursor;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.android.recipefinder.Data.RecipeContract;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isNotChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.example.android.recipefinder.Data.RecipeContract.RecipeTable.CONTENT_URI;
import static org.hamcrest.Matchers.anything;


@RunWith(AndroidJUnit4.class)
public class GridViewDisplaysAllEntrysInTheDataBaseTest {
    Boolean screenSizeTablet = MainActivity.screenSizeTablet;

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>( MainActivity.class );

    @Test
    public void checkTheGridViewDisplaysAllDataBaseEntries() {
        if (screenSizeTablet){
        for (int i = 0; i < MainActivity.widgetCursor.getCount(); i++) {
            onData( anything() ).inAdapterView( withId( R.id.master_list_fragment_grid_view ) )
                    .atPosition( i ).perform( click() );
            ArrayList<String> steps = addSteps( i );
            for (int x = 0; x < steps.size(); x++) {
                if (x==0){
                    onView(withId( R.id.next_button )).perform( scrollTo(), click() );
                }
                int mStepNumber = MainActivity.currentRecipeStep;
                String instructionsToCheck = getInstructions( mStepNumber);
                onView( withId( R.id.recipe_steps_instructions ) ).check( matches( withText( instructionsToCheck) ) );
                if (x+1<=steps.size()) {
                    onView( withId( R.id.next_button ) ).perform( scrollTo(), click() );
                }
            }
            onView( withContentDescription( "Navigate up" ) ).perform( click() );
        }
    }
    else{
            for (int i = 0; i < MainActivity.widgetCursor.getCount(); i++) {
                onData( anything() ).inAdapterView( withId( R.id.master_list_fragment_grid_view ) )
                        .atPosition( i ).perform( click() );
                ArrayList<String> steps = addSteps( i );
                onData( anything() ).inAdapterView( withId( R.id.recipe_steps_list_grid_view ) ).atPosition( 0 ).perform( click() );
                for (int x = 0; x < steps.size(); x++) {
                        if (x == 0) {
                            onView( withId( R.id.next_button ) ).perform( scrollTo(), click() );
                        }
                        int mStepNumber = MainActivity.currentRecipeStep;
                        String instructionsToCheck = getInstructions( mStepNumber );
                        onView( withId( R.id.recipe_steps_instructions ) ).check( matches( withText( instructionsToCheck ) ) );
                        if (x + 1 <= steps.size()) {
                            onView( withId( R.id.next_button ) ).perform(scrollTo(),  click() );
                        }
                    }
                    onView( withContentDescription( "Navigate up" ) ).perform( click() );
            }
        }}

    public ArrayList<String> addNumberStrings() {
        ArrayList<String> numberList = new ArrayList<String>();
        numberList.add( "One" );
        numberList.add( "Two" );
        numberList.add( "Three" );
        numberList.add( "Four" );
        numberList.add( "Five" );
        numberList.add( "Six" );
        numberList.add( "Seven" );
        numberList.add( "Eight" );
        numberList.add( "Nine" );
        numberList.add( "Ten" );
        numberList.add( "Eleven" );
        numberList.add( "Twelve" );
        numberList.add( "Thirteen" );
        return numberList;
    }

    public String getInstructions(int mStepNumber) {
        Cursor detailsCursor = MainActivity.widgetCursor;
        detailsCursor.moveToPosition( MainActivity.currentRecipeId );
        String instructionColumnToRetrieve = "step";
        instructionColumnToRetrieve = instructionColumnToRetrieve.concat( addNumberStrings().get( (mStepNumber - 1) ) ).concat( "FullDescription" );
        String currentStepInstruction = detailsCursor.getString( detailsCursor.getColumnIndex( instructionColumnToRetrieve ) );
        return currentStepInstruction;
    }

    public ArrayList<String> addSteps(int mRecipeId) {
        Cursor cursor = MainActivity.widgetCursor;
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
        recipeStepNameColumns.add( RecipeContract.RecipeTable.STEP_ONE_SHORT_DESCRIPTION);
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
