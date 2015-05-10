package edu.augustana.csc490.geographica_v2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.StreetViewPanoramaLocation;

import java.util.ArrayList;

/**
 * Created by Ethan on 5/9/2015.
 */
public class Panorama extends Activity implements OnStreetViewPanoramaReadyCallback {

    int currentPlayer;
    int scorePlayer1;
    int scorePlayer2;
    int roundNum;
    int gameMode;
    String panoID;
    RoundManager roundManager;

    StreetViewPanorama mainPanorama;
    SharedPreferences visitedLocations;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.panorama);

        Intent intent = getIntent();
        currentPlayer = intent.getIntExtra("currentPlayer", 0);
        scorePlayer1 = intent.getIntExtra("scorePlayer1", 0);
        scorePlayer2 = intent.getIntExtra("scorePlayer2", 0);
        roundNum = intent.getIntExtra("roundNum", 0);
        gameMode = intent.getIntExtra("gameMode", -1);
        panoID = intent.getStringExtra("panoID");

        Log.w("gameMode", ""+gameMode);
        Log.w("CurrentPlayer", ""+currentPlayer);
        Log.w("roundNum", ""+roundNum);

        roundManager = new RoundManager(getBaseContext());

        StreetViewPanoramaFragment panoramaFragment = (StreetViewPanoramaFragment) getFragmentManager().findFragmentById(R.id.panoramaFragment);
        panoramaFragment.getStreetViewPanoramaAsync(this);

        Button mapButton = (Button) findViewById(R.id.mapButton);
        mapButton.setOnClickListener(mapButtonListener);

    }

    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama panorama){
        mainPanorama = panorama;
        mainPanorama.setOnStreetViewPanoramaChangeListener(panoramaChangeListener);

        visitedLocations = this.getSharedPreferences("locations", Context.MODE_PRIVATE);

        if(panoID != null){
            Log.w("panoID",panoID);
            mainPanorama.setPosition(panoID);
            roundManager.setTopTextView((TextView) findViewById(R.id.topTextView),gameMode,roundNum, currentPlayer);
        }else {
            mainPanorama.setPosition(playRound(),10000000);
        }
        mainPanorama.setStreetNamesEnabled(false);
    }

    public StreetViewPanorama.OnStreetViewPanoramaChangeListener panoramaChangeListener = new StreetViewPanorama.OnStreetViewPanoramaChangeListener() {
        @Override
        public void onStreetViewPanoramaChange(StreetViewPanoramaLocation streetViewPanoramaLocation) {
            Log.w("panoid", streetViewPanoramaLocation.panoId);
            SharedPreferences.Editor visitedLocationsEditor = visitedLocations.edit();

            if(panoID != null) {
                if (visitedLocations.contains(streetViewPanoramaLocation.panoId)) {
                    mainPanorama.setPosition(playRound(), 10000000);
                }
                visitedLocationsEditor.putBoolean(streetViewPanoramaLocation.panoId, true);
                visitedLocationsEditor.commit();
            }
        }
    };

    private LatLng playRound(){


        if(gameMode == 1){
            roundManager.setTopTextView((TextView) findViewById(R.id.topTextView),gameMode,roundNum, currentPlayer);
            return roundManager.getRandomLocation();
        }
        if(gameMode == 2){
            roundManager.setTopTextView((TextView) findViewById(R.id.topTextView),gameMode,roundNum, currentPlayer);
            return roundManager.getRandomLocation();
        }
        return new LatLng(0,0);
    }


    public View.OnClickListener mapButtonListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            ArrayList<String> currentPositionArray = new ArrayList<String>();
            currentPositionArray.add(Double.toString(mainPanorama.getLocation().position.latitude));
            currentPositionArray.add(Double.toString(mainPanorama.getLocation().position.longitude));

            Intent mapViewIntent = new Intent(Panorama.this, Map.class);
            mapViewIntent.putExtra("scorePlayer1", scorePlayer1);
            mapViewIntent.putExtra("scorePlayer2", scorePlayer2);
            mapViewIntent.putExtra("roundNum", roundNum);
            mapViewIntent.putExtra("currentPlayer", currentPlayer);
            mapViewIntent.putExtra("gameMode", gameMode);
            mapViewIntent.putExtra("panoID", mainPanorama.getLocation().panoId);
            mapViewIntent.putStringArrayListExtra("latLngOfPanoID", currentPositionArray);
            startActivity(mapViewIntent);
        }
    };

}
