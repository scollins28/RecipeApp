package com.example.android.recipefinder.Data;

import android.net.Uri;
import android.provider.BaseColumns;

import com.tjeannin.provigen.ProviGenBaseContract;
import com.tjeannin.provigen.annotation.Column;
import com.tjeannin.provigen.annotation.ContentUri;

public class RecipeContract {

    public static final String AUTHORITY = "com.example.android.recipefinder";
    public static final Uri BASE_CONTENT_URI = Uri.parse( "content://"+AUTHORITY );
    public static final String PATH_RECIPES = "recipes";

    public static final class RecipeTable implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath( PATH_RECIPES).build();

        public static final String TABLE_NAME = "recipes";
        public static final String RECIPE_NAME = "recipeName";

        public static final String INGREDIENT_ONE_QUANTITY = "ingredientOneQuantity";
        public static final String INGREDIENT_ONE_MEASURE = "ingredientOneMeasure";
        public static final String INGREDIENT_ONE_NAME = "ingredientOneName";

        public static final String INGREDIENT_TWO_QUANTITY = "ingredientTwoQuantity";
        public static final String INGREDIENT_TWO_MEASURE = "ingredientTwoMeasure";
        public static final String INGREDIENT_TWO_NAME = "ingredientTwoName";

        public static final String INGREDIENT_THREE_QUANTITY = "ingredientThreeQuantity";
        public static final String INGREDIENT_THREE_MEASURE = "ingredientThreeMeasure";
        public static final String INGREDIENT_THREE_NAME = "ingredientThreeName";

        public static final String INGREDIENT_FOUR_QUANTITY = "ingredientFourQuantity";
        public static final String INGREDIENT_FOUR_MEASURE = "ingredientFourMeasure";
        public static final String INGREDIENT_FOUR_NAME = "ingredientFourName";

        public static final String INGREDIENT_FIVE_QUANTITY = "ingredientFiveQuantity";
        public static final String INGREDIENT_FIVE_MEASURE = "ingredientFiveMeasure";
        public static final String INGREDIENT_FIVE_NAME = "ingredientFiveName";

        public static final String INGREDIENT_SIX_QUANTITY = "ingredientSixQuantity";
        public static final String INGREDIENT_SIX_MEASURE = "ingredientSixMeasure";
        public static final String INGREDIENT_SIX_NAME = "ingredientSixName";

        public static final String INGREDIENT_SEVEN_QUANTITY = "ingredientSevenQuantity";
        public static final String INGREDIENT_SEVEN_MEASURE = "ingredientSevenMeasure";
        public static final String INGREDIENT_SEVEN_NAME = "ingredientSevenName";

        public static final String INGREDIENT_EIGHT_QUANTITY = "ingredientEightQuantity";
        public static final String INGREDIENT_EIGHT_MEASURE = "ingredientEightMeasure";
        public static final String INGREDIENT_EIGHT_NAME = "ingredientEightName";

        public static final String INGREDIENT_NINE_QUANTITY = "ingredientNineQuantity";
        public static final String INGREDIENT_NINE_MEASURE = "ingredientNineMeasure";
        public static final String INGREDIENT_NINE_NAME = "ingredientNineName";

        public static final String INGREDIENT_TEN_QUANTITY = "ingredientTenQuantity";
        public static final String INGREDIENT_TEN_MEASURE = "ingredientTenMeasure";
        public static final String INGREDIENT_TEN_NAME = "ingredientTenName";

        public static final String INGREDIENT_ELEVEN_QUANTITY = "ingredientElevenQuantity";
        public static final String INGREDIENT_ELEVEN_MEASURE = "ingredientElevenMeasure";
        public static final String INGREDIENT_ELEVEN_NAME = "ingredientElevenName";

        public static final String INGREDIENT_TWELVE_QUANTITY = "ingredientTwelveQuantity";
        public static final String INGREDIENT_TWELVE_MEASURE = "ingredientTwelveMeasure";
        public static final String INGREDIENT_TWELVE_NAME = "ingredientTwelveName";

        public static final String STEP_ONE_SHORT_DESCRIPTION = "stepOneShortDescription";
        public static final String STEP_ONE_FULL_DESCRIPTION = "stepOneFullDescription";
        public static final String STEP_ONE_VIDEO_URL = "stepOneVideoURL";
        public static final String STEP_ONE_THUMB_URL = "stepOneThumbURL";

        public static final String STEP_TWO_SHORT_DESCRIPTION = "stepTwoShortDescription";
        public static final String STEP_TWO_FULL_DESCRIPTION = "stepTwoFullDescription";
        public static final String STEP_TWO_VIDEO_URL = "stepTwoVideoURL";
        public static final String STEP_TWO_THUMB_URL = "stepTwoThumbURL";

        public static final String STEP_THREE_SHORT_DESCRIPTION = "stepThreeShortDescription";
        public static final String STEP_THREE_FULL_DESCRIPTION = "stepThreeFullDescription";
        public static final String STEP_THREE_VIDEO_URL = "stepThreeVideoURL";
        public static final String STEP_THREE_THUMB_URL = "stepThreeThumbURL";

        public static final String STEP_FOUR_SHORT_DESCRIPTION = "stepFourShortDescription";
        public static final String STEP_FOUR_FULL_DESCRIPTION = "stepFourFullDescription";
        public static final String STEP_FOUR_VIDEO_URL = "stepFourVideoURL";
        public static final String STEP_FOUR_THUMB_URL = "stepFourThumbURL";

        public static final String STEP_FIVE_SHORT_DESCRIPTION = "stepFiveShortDescription";
        public static final String STEP_FIVE_FULL_DESCRIPTION = "stepFiveFullDescription";
        public static final String STEP_FIVE_VIDEO_URL = "stepFiveVideoURL";
        public static final String STEP_FIVE_THUMB_URL = "stepFiveThumbURL";

        public static final String STEP_SIX_SHORT_DESCRIPTION = "stepSixShortDescription";
        public static final String STEP_SIX_FULL_DESCRIPTION = "stepSixFullDescription";
        public static final String STEP_SIX_VIDEO_URL = "stepSixVideoURL";
        public static final String STEP_SIX_THUMB_URL = "stepSixThumbURL";

        public static final String STEP_SEVEN_SHORT_DESCRIPTION = "stepSevenShortDescription";
        public static final String STEP_SEVEN_FULL_DESCRIPTION = "stepSevenFullDescription";
        public static final String STEP_SEVEN_VIDEO_URL = "stepSevenVideoURL";
        public static final String STEP_SEVEN_THUMB_URL = "stepSevenThumbURL";

        public static final String STEP_EIGHT_SHORT_DESCRIPTION = "stepEightShortDescription";
        public static final String STEP_EIGHT_FULL_DESCRIPTION = "stepEightFullDescription";
        public static final String STEP_EIGHT_VIDEO_URL = "stepEightVideoURL";
        public static final String STEP_EIGHT_THUMB_URL = "stepEightThumbURL";

        public static final String STEP_NINE_SHORT_DESCRIPTION = "stepNineShortDescription";
        public static final String STEP_NINE_FULL_DESCRIPTION = "stepNineFullDescription";
        public static final String STEP_NINE_VIDEO_URL = "stepNineVideoURL";
        public static final String STEP_NINE_THUMB_URL = "stepNineThumbURL";

        public static final String STEP_TEN_SHORT_DESCRIPTION = "stepTenShortDescription";
        public static final String STEP_TEN_FULL_DESCRIPTION = "stepTenFullDescription";
        public static final String STEP_TEN_VIDEO_URL = "stepTenVideoURL";
        public static final String STEP_TEN_THUMB_URL = "stepTenThumbURL";

        public static final String STEP_ELEVEN_SHORT_DESCRIPTION = "stepElevenShortDescription";
        public static final String STEP_ELEVEN_FULL_DESCRIPTION = "stepElevenFullDescription";
        public static final String STEP_ELEVEN_VIDEO_URL = "stepElevenVideoURL";
        public static final String STEP_ELEVEN_THUMB_URL = "stepElevenThumbURL";

        public static final String STEP_TWELVE_SHORT_DESCRIPTION = "stepTwelveShortDescription";
        public static final String STEP_TWELVE_FULL_DESCRIPTION = "stepTwelveFullDescription";
        public static final String STEP_TWELVE_VIDEO_URL = "stepTwelveVideoURL";
        public static final String STEP_TWELVE_THUMB_URL = "stepTwelveThumbURL";

        public static final String STEP_THIRTEEN_SHORT_DESCRIPTION = "stepThirteenShortDescription";
        public static final String STEP_THIRTEEN_FULL_DESCRIPTION = "stepThirteenFullDescription";
        public static final String STEP_THIRTEEN_VIDEO_URL = "stepThirteenVideoURL";
        public static final String STEP_THIRTEEN_THUMB_URL = "stepThirteenThumbURL";

        public static final String SERVINGS = "servings";
        public static final String IMAGE_URL = "imageUrl";
    }

}

