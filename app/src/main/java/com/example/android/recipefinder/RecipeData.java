package com.example.android.recipefinder;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

class RecipeData {

    private static final String LOG_TAG = RecipeData.class.getSimpleName();

    //Fetches the films that will populate the grid. Calls the createURL method and extractFeaturesFromJson method.
    public static ArrayList<Recipe> fetchRecipes(String requestUrl) {
        URL url = createURL( requestUrl );
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest( url );
        } catch (IOException e) {
            Log.e( LOG_TAG, "Problem making the htpp request" );
        }
        ArrayList<Recipe> recipes = (ArrayList<Recipe>) extractFeaturesFromJson( jsonResponse );
        MainActivity.recipes = recipes;
        return recipes;
    }

    //Takes the information from the JSON file and creates a new film for each new entry and stores the relevant information about that film within the new object.
    public static ArrayList<Recipe> extractFeaturesFromJson (String jsonResponse){
        ArrayList <Recipe> recipes = new ArrayList<Recipe>();

        try {
            JSONArray recipesArray = new JSONArray(jsonResponse);
            for (int i=0; i<recipesArray.length(); i++) {
                String recipeName =null;
                ArrayList<String> ingredientQuantityArray = new ArrayList<String>(  );
                ArrayList<String> ingredientMeasureArray = new ArrayList<String>(  );
                ArrayList<String> ingredientNameArray = new ArrayList<String>(  );
                List stepShortDescriptonArray = new ArrayList();
                ArrayList<String> stepLongDescriptionArray = new ArrayList<String>(  );
                ArrayList<String> stepVideoUrlArray = new ArrayList<String>(  );
                ArrayList<String> stepImageUrlArray = new ArrayList<String>(  );
                String recipeServingSize = null;
                String mainImageURL = null;
                JSONObject individualRecipeObject = recipesArray.getJSONObject( i );
                recipeName = individualRecipeObject.getString( "name" );
                JSONArray ingredientsArray = individualRecipeObject.getJSONArray( "ingredients" );
                for (int x = 0; x < ingredientsArray.length(); x++) {
                    JSONObject individualIngredientsObject = ingredientsArray.getJSONObject( x );
                    ingredientQuantityArray.add( individualIngredientsObject.getString( "quantity" ) );
                    ingredientMeasureArray.add( individualIngredientsObject.getString( "measure" ) );
                    ingredientNameArray.add( individualIngredientsObject.getString( "ingredient" ) );
                }

                JSONArray stepsArray = individualRecipeObject.getJSONArray( "steps" );
                for (int x = 0; x < stepsArray.length(); x++) {
                    JSONObject individualStepsObject = stepsArray.getJSONObject( x );
                    stepShortDescriptonArray.add( individualStepsObject.getString( "shortDescription" ) );
                    stepLongDescriptionArray.add( individualStepsObject.getString( "description" ) );
                    if (individualStepsObject.getString( "videoURL" ) == null) {
                        stepVideoUrlArray.add( "noVideo" );
                    } else {
                        stepVideoUrlArray.add( individualStepsObject.getString( "videoURL" ) );
                    }
                    if (individualStepsObject.getString( "thumbnailURL" ) == null) {
                        stepImageUrlArray.add( "noImage" );
                    } else {
                        stepImageUrlArray.add( individualStepsObject.getString( "thumbnailURL" ) );
                    }
                }

                recipeServingSize = individualRecipeObject.getString( "servings" );
                mainImageURL = individualRecipeObject.getString( "name" );

                recipes.add( new Recipe( recipeName, ingredientQuantityArray, ingredientMeasureArray,
                        ingredientNameArray, stepShortDescriptonArray, stepLongDescriptionArray, stepVideoUrlArray,
                        stepImageUrlArray, recipeServingSize, mainImageURL ) );
                }
            } catch (JSONException e1) {
            e1.printStackTrace();
             }
        return recipes;
    }

    //Attempts to connect to the internet using the URL provided. If the connection is unavailable, the jsonResponse will be blank. Uses the readFromStream method to generate the response.
    private static String makeHttpRequest (URL url) throws IOException{
        String jsonResponse = "";
        if (url == null){
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        int urlResponse=0;
        try{
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout( 150000 );
            urlConnection.setReadTimeout( 100000 );
            urlConnection.setRequestMethod( "GET" );
            urlConnection.connect();
            if (urlConnection.getResponseCode()<300){
                inputStream=urlConnection.getInputStream();
                jsonResponse= readFromStream (inputStream);
            }
            else if (urlConnection.getResponseCode()>400){
                urlConnection.disconnect();
                Log.e( "Url response = ", String.valueOf( urlConnection.getResponseCode() ) );
            }

            else {
                Log.e( LOG_TAG, "Problem with the URL connection " + urlConnection.getResponseCode() + urlConnection.getInstanceFollowRedirects());
            }
        }
        catch (IOException e) {
            Log.e( LOG_TAG, "Problem getting the JSON file" );
        }
        finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null && urlResponse!=429) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    //Creates the jsonResponse by appending each new paramater retrieved.
    private static String readFromStream (InputStream inputStream) throws IOException{
        StringBuilder output = new StringBuilder(  );
        if (inputStream!=null){
            InputStreamReader inputStreamReader = new InputStreamReader( inputStream, Charset.forName ("UTF-8") );
            BufferedReader bufferedReader = new BufferedReader( inputStreamReader );
            String line = bufferedReader.readLine();
            while (line!=null){
                output.append(line);
                line = bufferedReader.readLine();
            }
        }
        return output.toString();
    }


    //Turns the String query into a URL. If there are any issues, returns null.
    private static URL createURL (String stringUrl){
        URL url = null;
        try{
            url = new URL (stringUrl);
        }
        catch (MalformedURLException e){
            Log.e (LOG_TAG, "Problem generating the URL");
        }
        return url;
    }

}
