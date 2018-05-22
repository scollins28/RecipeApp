package com.example.android.recipefinder;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;


import com.example.android.recipefinder.Data.RecipeContentProvider;
import com.example.android.recipefinder.Data.RecipeContract;
import com.example.android.recipefinder.Data.RecipeDbHelper;

import java.util.ArrayList;

import static com.example.android.recipefinder.Data.RecipeContract.RecipeTable.CONTENT_URI;

public class MasterListFragment extends Fragment {
    ArrayList<String> recipes;
    Context mContext;

    public MasterListFragment() {
        }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mContext = getContext();
        recipes = addRecipes();
        final View rootView = inflater.inflate( R.layout.master_list_fragment, container, false );
        GridView gridView = (GridView) rootView.findViewById( R.id.master_list_fragment_grid_view );
        MasterListAdapter mAdapter = new MasterListAdapter( mContext, recipes );
        gridView.setAdapter( mAdapter );
        gridView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCallback.onImageSelected( position );
                MainActivity.currentRecipeId=position;
            }
        } );
        return rootView;
    }


    OnImageClickListener mCallback;

    public interface OnImageClickListener {
        void onImageSelected(int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach( context );
        try {
            mCallback = (OnImageClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException( context.toString() + "must implement click listener" );
        }
    }

    public ArrayList<String> addRecipes() {
        Cursor cursor = getActivity().getContentResolver().query( CONTENT_URI, null, null, null, null );
        ArrayList<String> recipeList = new ArrayList<>();
        for (int i = 0; i< cursor.getCount(); i++) {
            cursor.moveToPosition( i );
            String nameToAdd = cursor.getString( cursor.getColumnIndex( RecipeContract.RecipeTable.RECIPE_NAME ) );
            recipeList.add( nameToAdd );
        }
        return recipeList;
    }


}
