package com.example.android.recipefinder;

import android.content.Context;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isNotChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.anything;



@RunWith(AndroidJUnit4.class)
public class CheckBoxIsCheckedWhenNeededAndBlankWhenNotTest {
    Boolean tabletScreen = MainActivity.screenSizeTablet;

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>( MainActivity.class );

    @Test
    public void checkCheckBoxIsUnCheckedIfNoMatch() {
        MainActivity.widgetRecipe = -1;
        if (tabletScreen) {
            for (int i = 0; i < MainActivity.widgetCursor.getCount(); i++) {
                onData( anything() ).inAdapterView( withId( R.id.master_list_fragment_grid_view ) ).atPosition( i ).perform( click() );
                if (MainActivity.widgetRecipe != MainActivity.currentRecipeId) {
                    onView( withId( R.id.checkbox_to_display_in_widget ) ).check( matches( isNotChecked() ) );
                } else if (MainActivity.widgetRecipe == MainActivity.currentRecipeId) {
                    onView( withId( R.id.checkbox_to_display_in_widget ) ).check( matches( isChecked() ) );
                }
                onView( withContentDescription( "Navigate up" ) ).perform( click() );
            }
        } else {
                for (int i = 0; i < MainActivity.widgetCursor.getCount(); i++) {
                onData( anything() ).inAdapterView( withId( R.id.master_list_fragment_grid_view ) ).atPosition( i ).perform( click() );
                onData( anything() ).inAdapterView( withId( R.id.recipe_steps_list_grid_view ) ).atPosition( 0 ).perform( click() );
                    if (MainActivity.widgetRecipe != MainActivity.currentRecipeId) {
                    onView( withId( R.id.checkbox_to_display_in_widget ) ).check( matches( isNotChecked() ) );
                    } else if (MainActivity.widgetRecipe == MainActivity.currentRecipeId) {
                    onView( withId( R.id.checkbox_to_display_in_widget ) ).check( matches( isChecked() ) );
                }
                onView( withContentDescription( "Navigate up" ) ).perform( click() );
            }}
    }

    @Test
    public void checkCheckboxIsTickedIfMatched() {
        if (tabletScreen) {
            for (int x = 0; x < MainActivity.widgetCursor.getCount(); x++) {
                MainActivity.widgetRecipe = x;

                for (int i = 0; i < MainActivity.widgetCursor.getCount(); i++) {
                    onData( anything() ).inAdapterView( withId( R.id.master_list_fragment_grid_view ) ).atPosition( i ).perform( click() );
                    if (MainActivity.widgetRecipe != MainActivity.currentRecipeId) {
                        onView( withId( R.id.checkbox_to_display_in_widget ) ).check( matches( isNotChecked() ) );
                    } else if (MainActivity.widgetRecipe == MainActivity.currentRecipeId) {
                        onView( withId( R.id.checkbox_to_display_in_widget ) ).check( matches( isChecked() ) );
                    }
                    onView( withContentDescription( "Navigate up" ) ).perform( click() );
                }
            }
        } else {
                for (int x = 0; x < MainActivity.widgetCursor.getCount(); x++) {
                    MainActivity.widgetRecipe = x;
                    for (int i = 0; i < MainActivity.widgetCursor.getCount(); i++) {
                        onData( anything() ).inAdapterView( withId( R.id.master_list_fragment_grid_view ) ).atPosition( i ).perform( click() );
                        onData( anything() ).inAdapterView( withId( R.id.recipe_steps_list_grid_view ) ).atPosition( 0 ).perform( click() );
                        if (MainActivity.widgetRecipe != MainActivity.currentRecipeId) {
                            onView( withId( R.id.checkbox_to_display_in_widget ) ).check( matches( isNotChecked() ) );
                        } else if (MainActivity.widgetRecipe == MainActivity.currentRecipeId) {
                            onView( withId( R.id.checkbox_to_display_in_widget ) ).check( matches( isChecked() ) );
                        }
                        onView( withContentDescription( "Navigate up" ) ).perform( click() );
                    }
                }
        }
}}

