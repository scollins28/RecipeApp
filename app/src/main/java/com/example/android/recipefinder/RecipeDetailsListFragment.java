package com.example.android.recipefinder;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInstaller;
import android.database.Cursor;
import android.media.session.PlaybackState;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectorResult;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;
import com.omega_r.libs.OmegaCenterIconButton;

import java.util.ArrayList;
import java.util.List;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;
import static android.content.res.Configuration.ORIENTATION_PORTRAIT;
import static android.support.constraint.Constraints.TAG;
import static com.example.android.recipefinder.Data.RecipeContract.RecipeTable.CONTENT_URI;
import static com.example.android.recipefinder.R.id.checkbox;
import static com.example.android.recipefinder.R.id.recipe_steps_instructions;

public class RecipeDetailsListFragment extends Fragment {

    int mCurrentId = MainActivity.currentRecipeId;
    int mStepNumber = 0;
    TextView currentStepTv;
    PlayerView playerView;
    TextView instructionsTv;
    static String ingredients;
    ArrayList<String> numbersArrayList = new ArrayList<>();
    Context mContext;
    static View rootView;
    String currentVideoData = null;
    OmegaCenterIconButton nextButton;
    OmegaCenterIconButton ingredientsButton;
    OmegaCenterIconButton previousButton;
    public Boolean twoPane;
    TextView recipeName;
    TextView noVideoTv;
    String currentVideoValue;
    String currentRecipeName;
    public SimpleExoPlayer mExoPlayer;
    TrackSelector trackSelector;
    BandwidthMeter bandwidthMeter;
    Handler mainHandler;
    DataSource.Factory dataSourceFactory;
    MediaSource videoSource;
    Player.EventListener eventListener;
    Boolean playing;
    MediaSessionCompat mMediaSession;
    public Long currentVideoPosition;
    CheckBox widgetCheckBox;


    public RecipeDetailsListFragment() {
    }

    public interface OnChangeSlideListener {
        public void onArticleSelected(int position);
    }

    OnChangeSlideListener mCallback;

    @Override
    public void onAttach(Context context) {
        super.onAttach( context );
        try {
            if (mExoPlayer!=null){
            mMediaSession.setActive( false );
            mExoPlayer.release();}

            mCallback = (OnChangeSlideListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException( context.toString() + "must implement click listener" );
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        twoPane = MainActivity.mTwoPane;
        mCurrentId = MainActivity.currentRecipeId;
        mContext = getContext();
        mStepNumber = MainActivity.currentRecipeStep;
        rootView = inflater.inflate( R.layout.details_fragment, container, false );
        numbersArrayList = addNumberStrings();
        currentVideoPosition = MainActivity.playerCurrentPosition;
        updateWidget();

        if (twoPane = true) {
            currentStepTv = (TextView) rootView.findViewById( R.id.step_title );
            playerView = (PlayerView) rootView.findViewById( R.id.exoPlayer );
            instructionsTv = rootView.findViewById( R.id.recipe_steps_instructions );
            previousButton = (OmegaCenterIconButton) rootView.findViewById( R.id.previous_button );
            ingredientsButton = (OmegaCenterIconButton) rootView.findViewById( R.id.ingredients_button );
            nextButton = (OmegaCenterIconButton) rootView.findViewById( R.id.next_button );
            widgetCheckBox = (CheckBox) rootView.findViewById( R.id.checkbox_to_display_in_widget );
            if (MainActivity.widgetRecipe == mCurrentId){
                widgetCheckBox.setChecked( true );
            }
            else {
                widgetCheckBox.setChecked( false );
            }
            widgetCheckBox.setOnClickListener( MainActivity.listener );
        } else if (getResources().getConfiguration().orientation == ORIENTATION_PORTRAIT) {
            recipeName = (TextView) rootView.findViewById( R.id.recipe_steps_tv_recipeName );
            currentStepTv = (TextView) rootView.findViewById( R.id.recipe_steps_tv_current_step );
            playerView = (PlayerView) rootView.findViewById( R.id.exoPlayer );
            instructionsTv = (TextView) rootView.findViewById( R.id.recipe_steps_instructions );
            previousButton = (OmegaCenterIconButton) rootView.findViewById( R.id.previous_button );
            ingredientsButton = (OmegaCenterIconButton) rootView.findViewById( R.id.ingredients_button );
            nextButton = (OmegaCenterIconButton) rootView.findViewById( R.id.next_button );
            widgetCheckBox = (CheckBox) rootView.findViewById( R.id.checkbox_to_display_in_widget );
            Cursor tempCursor = getActivity().getContentResolver().query( CONTENT_URI, null, null, null, null );
            assert tempCursor != null;
            tempCursor.moveToPosition( mCurrentId );
            currentRecipeName = tempCursor.getString( tempCursor.getColumnIndex( "recipeName" ) );
            recipeName.setText( currentRecipeName );
            if (MainActivity.widgetRecipe == mCurrentId){
                widgetCheckBox.setChecked( true );
            }
            else {
                widgetCheckBox.setChecked( false );
            }
            widgetCheckBox.setOnClickListener( MainActivity.listener );
        } else if (getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE) {
            playerView = (PlayerView) rootView.findViewById( R.id.exoPlayer );
            noVideoTv = (TextView) rootView.findViewById( R.id.no_video_tv );
        }

        if (mStepNumber == 0) {
            playerView.setVisibility( View.GONE );
        }



        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = 0;
                if (v == previousButton) {
                    position = 0;
                } else if (v == ingredientsButton) {
                    position = 1;
                } else if (v == nextButton) {
                    position = 2;
                }
                mCallback.onArticleSelected( position );
            }
        };

        if (previousButton != null) {
            previousButton.setOnClickListener( listener );
            ingredientsButton.setOnClickListener( listener );
            nextButton.setOnClickListener( listener );
        }
        if (getResources().getConfiguration().orientation == ORIENTATION_PORTRAIT || twoPane) {
            setData( mStepNumber );
        } else if (getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE && twoPane) {
            getVideoDetails( mStepNumber );
            if (currentVideoValue.isEmpty() || currentVideoValue.equals( "noVideo" )) {
                noVideoTv.setVisibility( View.VISIBLE );
            } else {
                noVideoTv.setVisibility( View.GONE );
            }
        }
        return rootView;

    }


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

    public String getIngredients(int mCurrentId) {
        String ingredientsList = "";
        Cursor ingredientsCursor = getActivity().getContentResolver().query( CONTENT_URI, null, null, null, null );
        assert ingredientsCursor != null;
        ingredientsCursor.moveToPosition( mCurrentId );
        if (mStepNumber == 0) {
            for (int i = 0; i < 12; i++) {
                String currentNumber = numbersArrayList.get( i );
                String tempString = "ingredient";
                String tempEnd = "Name";
                String tempEndB = "Quantity";
                String tempEndC = "Measure";
                String nameString = tempString.concat( currentNumber ).concat( tempEnd );
                String quantityString = tempString.concat( currentNumber ).concat( tempEndB );
                String measureString = tempString.concat( currentNumber ).concat( tempEndC );

                if (ingredientsCursor.getString( ingredientsCursor.getColumnIndex( nameString ) ) != null) {
                    ingredientsList = ingredientsList.concat( "Ingredient: " )
                            .concat( ingredientsCursor.getString( ingredientsCursor.getColumnIndex( nameString ) ) )
                            .concat( "\n" + "Quantity: " )
                            .concat( ingredientsCursor.getString( ingredientsCursor.getColumnIndex( quantityString ) ) )
                            .concat( "\n" + "Measure: " )
                            .concat( ingredientsCursor.getString( ingredientsCursor.getColumnIndex( measureString ) ) )
                            .concat( "\n" )
                            .concat( "\n" );
                } else break;
            }
        }
        return ingredientsList;
    }


    private List<View> getAllChildren(View v) {

        if (!(v instanceof ViewGroup)) {
            ArrayList<View> viewArrayList = new ArrayList<View>();
            viewArrayList.add( v );
            return viewArrayList;
        }

        ArrayList<View> result = new ArrayList<View>();

        ViewGroup viewGroup = (ViewGroup) v;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {

            View child = viewGroup.getChildAt( i );
            //Do not add any parents, just add child elements
            result.addAll( getAllChildren( child ) );
        }
        return result;
    }

    public void setData(int mStepNumber) {
        ingredients = getIngredients( mCurrentId );
        if (mStepNumber == 0) {
            instructionsTv.setText( ingredients );
        } else {
            getInstructions( mStepNumber );
            getTitle( mStepNumber );
            getVideoDetails( mStepNumber );
        }
    }

    public void getInstructions(int mStepNumber) {
        Cursor detailsCursor = getActivity().getContentResolver().query( CONTENT_URI, null, null, null, null );
        detailsCursor.moveToPosition( MainActivity.currentRecipeId );
        String instructionColumnToRetrieve = "step";
        instructionColumnToRetrieve = instructionColumnToRetrieve.concat( addNumberStrings().get( (mStepNumber - 1) ) ).concat( "FullDescription" );
        String currentStepInstruction = detailsCursor.getString( detailsCursor.getColumnIndex( instructionColumnToRetrieve ) );
        instructionsTv.setText( currentStepInstruction );
    }

    public void getTitle(int mStepNumber) {
        Cursor detailsCursor = getActivity().getContentResolver().query( CONTENT_URI, null, null, null, null );
        detailsCursor.moveToPosition( MainActivity.currentRecipeId );
        String instructionColumnToRetrieve = "step";
        instructionColumnToRetrieve = instructionColumnToRetrieve.concat( addNumberStrings().get( (mStepNumber - 1) ) ).concat( "ShortDescription" );
        String currentStepInstruction = detailsCursor.getString( detailsCursor.getColumnIndex( instructionColumnToRetrieve ) );
        currentStepTv.setText( currentStepInstruction );
    }

    public void getVideoDetails(int mStepNumber) {
        Cursor detailsCursor = getActivity().getContentResolver().query( CONTENT_URI, null, null, null, null );
        detailsCursor.moveToPosition( MainActivity.currentRecipeId );
        String instructionColumnToRetrieve = "step";
        instructionColumnToRetrieve = instructionColumnToRetrieve.concat( addNumberStrings().get( (mStepNumber - 1) ) ).concat( "VideoURL" );
        String currentVideoData = detailsCursor.getString( detailsCursor.getColumnIndex( instructionColumnToRetrieve ) );
        currentVideoValue = currentVideoData;
        Log.e( "current video = ", detailsCursor.getString( detailsCursor.getColumnIndex( instructionColumnToRetrieve ) ) );
        if (currentVideoData.isEmpty() || currentVideoData.equals( "noVideo" )) {
            playerView.setVisibility( View.GONE );
        } else {
            playerView.setVisibility( View.VISIBLE );
            mExoPlayer = createExoPlayer();
            playerView.setPlayer( mExoPlayer );
        }
    }

    public void getMStepNumber(int number) {
        mStepNumber = number;
    }

    public SimpleExoPlayer createExoPlayer() {
            TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory( bandwidthMeter );
            trackSelector = new DefaultTrackSelector( videoTrackSelectionFactory );
            mExoPlayer = ExoPlayerFactory.newSimpleInstance( mContext, trackSelector );
            mainHandler = new Handler();
            bandwidthMeter = new DefaultBandwidthMeter();
            dataSourceFactory = new DefaultDataSourceFactory( mContext, Util.getUserAgent( mContext, "recipefinder" ), (TransferListener<? super DataSource>) bandwidthMeter );
            videoSource = new ExtractorMediaSource.Factory( dataSourceFactory ).createMediaSource( Uri.parse( currentVideoValue ) );
        if (currentVideoValue != null && currentVideoValue != "noValue") {
            mExoPlayer.prepare( videoSource );

            eventListener = new Player.EventListener() {
                @Override
                public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

                }

                @Override
                public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

                }

                @Override
                public void onLoadingChanged(boolean isLoading) {

                }

                @Override
                public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                    if (playWhenReady && (playbackState == ExoPlayer.STATE_READY)) {
                        playing = true;
                    } else if (playWhenReady) {
                        playing = false;
                    }
                    currentVideoPosition = mExoPlayer.getCurrentPosition();
                    MainActivity.playerCurrentPosition = currentVideoPosition;

                }

                @Override
                public void onRepeatModeChanged(int repeatMode) {

                }

                @Override
                public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

                }

                @Override
                public void onPlayerError(ExoPlaybackException error) {

                }

                @Override
                public void onPositionDiscontinuity(int reason) {

                }

                @Override
                public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

                }

                @Override
                public void onSeekProcessed() {

                }
            };

            class MySessionCallback extends MediaSessionCompat.Callback {
                @Override
                public void onPlay() {
                    super.onPlay();
                }

                @Override
                public void onPause() {
                    super.onPause();
                }
            }

            mExoPlayer.addListener( eventListener );

            mMediaSession = new MediaSessionCompat( mContext, TAG );
            mMediaSession.setFlags( MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS | MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS );
            mMediaSession.setMediaButtonReceiver( null );
            PlaybackStateCompat.Builder mStateBuilder = new PlaybackStateCompat.Builder().setActions( PlaybackState.ACTION_PLAY | PlaybackState.ACTION_PAUSE | PlaybackState.ACTION_PLAY_PAUSE );

            mMediaSession.setPlaybackState( mStateBuilder.build() );
            mMediaSession.setCallback( new MySessionCallback() );
            mMediaSession.setActive( true );
            if (currentVideoPosition!=null) {
                mStateBuilder.setState( PlaybackState.STATE_PLAYING, currentVideoPosition, 1f );
                Log.e( "psotion", String.valueOf( currentVideoPosition ) );
                mExoPlayer.seekTo( currentVideoPosition );
                mExoPlayer.setPlayWhenReady( true );
            }
            else{
                mStateBuilder.setState( PlaybackState.STATE_PLAYING, 0, 1f );
            }
            mMediaSession.setPlaybackState( mStateBuilder.build() );


        }
        return mExoPlayer;
    }

    public void updateWidget() {
        if (MainActivity.widgetRecipe == -1) {
            RecipeListWidget.widgetIngredients = null;
        } else {
            RecipeListWidget.widgetIngredients = getWidgetIngredients(MainActivity.widgetRecipe );
        }
    }


    public ArrayList <String> getWidgetIngredients(int mCurrentId) {
        ArrayList<String> ingredientsList = new ArrayList<>();
        Cursor ingredientsCursor = MainActivity.widgetCursor;
        assert ingredientsCursor != null;
        ingredientsCursor.moveToPosition( mCurrentId );
        for (int i = 0; i < 12; i++) {
            String ingredientHolderString = "";
            String currentNumber = addNumberStrings().get( i );
            String tempString = "ingredient";
            String tempEnd = "Name";
            String tempEndB = "Quantity";
            String tempEndC = "Measure";
            String nameString = tempString.concat( currentNumber ).concat( tempEnd );
            String quantityString = tempString.concat( currentNumber ).concat( tempEndB );
            String measureString = tempString.concat( currentNumber ).concat( tempEndC );

            if (ingredientsCursor.getString( ingredientsCursor.getColumnIndex( nameString ) ) != null) {
                ingredientHolderString = ingredientHolderString.concat( "Ingredient: " )
                        .concat( ingredientsCursor.getString( ingredientsCursor.getColumnIndex( nameString ) ) )
                        .concat( "\n" + "Quantity: " )
                        .concat( ingredientsCursor.getString( ingredientsCursor.getColumnIndex( quantityString ) ) )
                        .concat( "\n" + "Measure: " )
                        .concat( ingredientsCursor.getString( ingredientsCursor.getColumnIndex( measureString ) ) )
                        .concat( "\n" );
                ingredientsList.add( ingredientHolderString );
            } else break;
        }

        return ingredientsList;
    }

}
