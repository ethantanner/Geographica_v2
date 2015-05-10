package edu.augustana.csc490.geographica_v2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaFragment;

/**
 * Created by Ethan on 5/9/2015.
 */
public class EndGame extends Activity {

    int currentPlayer;
    int scorePlayer1;
    int scorePlayer2;
    int roundNum;
    int gameMode;
    String panoID;
    RoundManager roundManager;
    int roundScore;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.endgame);

        Intent intent = getIntent();
        currentPlayer = intent.getIntExtra("currentPlayer", 0);
        scorePlayer1 = intent.getIntExtra("scorePlayer1", 0);
        scorePlayer2 = intent.getIntExtra("scorePlayer2", 0);
        roundNum = intent.getIntExtra("roundNum", 0);
        gameMode = intent.getIntExtra("gameMode", -1);
        roundScore = intent.getIntExtra("roundScore", -1);
        panoID = intent.getStringExtra("panoID");

        roundManager = new RoundManager(getBaseContext());

        final Typeface font = Typeface.createFromAsset(this.getAssets(), "fonts/moon_light.otf");

        Button mainMenuButton = (Button) findViewById(R.id.mainMenuButton);
        mainMenuButton.setOnClickListener(mainMenuButtonListener);
        mainMenuButton.setTypeface(font);
        mainMenuButton.setTextSize(24);
        mainMenuButton.setTextColor(Color.BLACK);

        TextView roundScoreView = (TextView) findViewById(R.id.roundScoreView);
        roundScoreView.setText("Player1 Score: " + scorePlayer1);
        roundScoreView.setTypeface(font);
        roundScoreView.setTextSize(24);
        roundScoreView.setTextColor(Color.BLACK);

        roundManager.setTopTextView((TextView) findViewById(R.id.topTextView), "Game Over");

        TextView totalScoreView = (TextView) findViewById(R.id.totalScoreView);
        totalScoreView.setText("Player2 Score: " + scorePlayer2);
        totalScoreView.setTypeface(font);
        totalScoreView.setTextSize(24);
        totalScoreView.setTextColor(Color.BLACK);

    }
    public View.OnClickListener mainMenuButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent menuIntnet = new Intent(EndGame.this, MainActivity.class);
            startActivity(menuIntnet);
        }
    };

}
