package edu.augustana.csc490.geographica_v2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Ethan on 5/9/2015.
 */
public class Map extends Activity implements OnMapReadyCallback {

    int currentPlayer;
    int scorePlayer1;
    int scorePlayer2;
    int roundNum;
    int gameMode;
    String panoID;
    RoundManager roundManager;

    LatLng latLngOfPanoID;
    GoogleMap mainMap;
    Marker myMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.map);

        final Typeface font = Typeface.createFromAsset(this.getAssets(), "fonts/moon_light.otf");

        Intent intent = getIntent();
        currentPlayer = intent.getIntExtra("currentPlayer", 0);
        scorePlayer1 = intent.getIntExtra("scorePlayer1", 0);
        scorePlayer2 = intent.getIntExtra("scorePlayer2", 0);
        roundNum = intent.getIntExtra("roundNum", 0);
        gameMode = intent.getIntExtra("gameMode", -1);
        panoID = intent.getStringExtra("panoID");
        latLngOfPanoID = new LatLng(Double.parseDouble(intent.getStringArrayListExtra("latLngOfPanoID").get(0)),Double.parseDouble(intent.getStringArrayListExtra("latLngOfPanoID").get(1)));


        roundManager = new RoundManager(getBaseContext());

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);

        Button panoramaButton = (Button) findViewById(R.id.panoramaButton);
        panoramaButton.setOnClickListener(panoramaButtonListener);
        panoramaButton.setTypeface(font);
        panoramaButton.setTextSize(24);
        panoramaButton.setTextColor(Color.BLACK);

        Button submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(submitButtonListener);
        submitButton.setTypeface(font);
        submitButton.setTextSize(24);
        submitButton.setTextColor(Color.BLACK);

        roundManager.setTopTextView((TextView) findViewById(R.id.topTextView),gameMode,roundNum, currentPlayer);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mainMap = map;
        mainMap.setOnMapLongClickListener(mapLongClickListener);
        myMarker = mainMap.addMarker(new MarkerOptions().position(new LatLng(39.828127, -98.579404)).icon(BitmapDescriptorFactory.fromResource(R.drawable.pinsmallred)));
        myMarker.setDraggable(true);
    }

    public GoogleMap.OnMapLongClickListener mapLongClickListener = new GoogleMap.OnMapLongClickListener() {
        @Override
        public void onMapLongClick(LatLng point){
            myMarker.setPosition(point);
        }

    };

    public View.OnClickListener panoramaButtonListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            Intent mapViewIntent = new Intent(Map.this, Panorama.class);
            mapViewIntent.putExtra("scorePlayer1", scorePlayer1);
            mapViewIntent.putExtra("scorePlayer2", scorePlayer2);
            mapViewIntent.putExtra("roundNum", roundNum);
            mapViewIntent.putExtra("currentPlayer", currentPlayer);
            mapViewIntent.putExtra("gameMode", gameMode);
            mapViewIntent.putExtra("panoID", panoID);
            startActivity(mapViewIntent);
        }
    };

    public View.OnClickListener submitButtonListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            mainMap.addMarker(new MarkerOptions().position(latLngOfPanoID).icon(BitmapDescriptorFactory.fromResource(R.drawable.pinsmallblue)));

            myMarker.setDraggable(false);
            mainMap.setOnMapLongClickListener(null);

            Button panoramaButton = (Button) findViewById(R.id.panoramaButton);

            Button submitButton = (Button) findViewById(R.id.submitButton);

            panoramaButton.setClickable(false);
            submitButton.setClickable(false);


            /////////////// http://stackoverflow.com/questions/7965494/how-to-put-some-delay-in-calling-an-activity-from-another-activity

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLngOfPanoID,3);
                    mainMap.animateCamera(cameraUpdate);

                }
            }, 500);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLngOfPanoID,6);
                    mainMap.animateCamera(cameraUpdate);

                }
            }, 1500);


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    int roundScore = roundManager.calculateScore(myMarker, latLngOfPanoID);

                    if(currentPlayer ==1){
                        scorePlayer1 += roundScore;
                    }else{
                        scorePlayer2 += roundScore;
                    }

                    Intent endRoundIntent = new Intent(Map.this, EndRound.class);
                    endRoundIntent.putExtra("scorePlayer1", scorePlayer1);
                    endRoundIntent.putExtra("scorePlayer2",scorePlayer2);
                    endRoundIntent.putExtra("roundNum", roundNum);
                    endRoundIntent.putExtra("currentPlayer", currentPlayer);
                    endRoundIntent.putExtra("gameMode", gameMode);
                    endRoundIntent.putExtra("roundScore", roundScore);
                    endRoundIntent.putExtra("panoID", panoID);

                    startActivity(endRoundIntent);
                }
            }, 10000);

            ////////////// END http://stackoverflow.com/questions/7965494/how-to-put-some-delay-in-calling-an-activity-from-another-activity
        }
    };

}
