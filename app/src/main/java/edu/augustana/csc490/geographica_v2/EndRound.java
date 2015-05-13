package edu.augustana.csc490.geographica_v2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
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
public class EndRound extends Activity {

    public final int NUM_ROUNDS = 5;

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
        setContentView(R.layout.endround);

        Intent intent = getIntent();
        initiateValues(intent);

        roundManager = new RoundManager(getBaseContext());

        Button nextRoundButton = (Button) findViewById(R.id.nextRoundButton);
        nextRoundButton.setOnClickListener(nextRoundButtonListener);
        roundManager.styleButton(nextRoundButton,24);
        if(gameMode == 2 && currentPlayer == 1){
            nextRoundButton.setText("Pass to Player 2");
        }else if(gameMode == 2 && currentPlayer == 2){
            nextRoundButton.setText("Pass to Player 1");
        }

        //Style and set roundScoreView
        roundManager.styleTextView((TextView) findViewById(R.id.roundScoreView),"Last Round: " + roundScore+"km",24);

        //Style and set topTextView
        roundManager.setTopTextView((TextView) findViewById(R.id.topTextView),gameMode,roundNum, currentPlayer);

        //Style and set totalScoreView depending on player
        if(currentPlayer==1){
            roundManager.styleTextView((TextView) findViewById(R.id.totalScoreView),"Total Score: " + scorePlayer1+"km",24);
        }else{
            roundManager.styleTextView((TextView) findViewById(R.id.totalScoreView),"Total Score: " + scorePlayer2+"km",24);
        }

        int numPlayers = 1;
        if(gameMode==2){
            numPlayers = 2;
        }

        if(roundNum >= NUM_ROUNDS && currentPlayer == numPlayers){
            nextRoundButton.setVisibility(View.GONE);

            /////////////// http://stackoverflow.com/questions/7965494/how-to-put-some-delay-in-calling-an-activity-from-another-activity

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent endGameIntent = new Intent(EndRound.this,EndGame.class);
                    endGameIntent.putExtra("scorePlayer1", scorePlayer1);
                    endGameIntent.putExtra("scorePlayer2",scorePlayer2);
                    endGameIntent.putExtra("roundNum", roundNum);
                    endGameIntent.putExtra("currentPlayer", currentPlayer);
                    endGameIntent.putExtra("gameMode", gameMode);
                    endGameIntent.putExtra("roundScore", roundScore);
                    endGameIntent.putExtra("panoID", panoID);

                    startActivity(endGameIntent);

                }
            }, 3000);

            /////////////// END http://stackoverflow.com/questions/7965494/how-to-put-some-delay-in-calling-an-activity-from-another-activity
        }

    }

    /**
     * @param intent
     * Initiates player scores, round number, round scores, and Google panorama ID
     */
    public void initiateValues(Intent intent){
        currentPlayer = intent.getIntExtra("currentPlayer", 0);
        scorePlayer1 = intent.getIntExtra("scorePlayer1", 0);
        scorePlayer2 = intent.getIntExtra("scorePlayer2", 0);
        roundNum = intent.getIntExtra("roundNum", 0);
        gameMode = intent.getIntExtra("gameMode", -1);
        roundScore = intent.getIntExtra("roundScore", -1);
        panoID = intent.getStringExtra("panoID");
    }

    public View.OnClickListener nextRoundButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(gameMode == 2){
                if(currentPlayer == 2){
                    roundNum += 1;
                    currentPlayer = 1;
                }else{
                    currentPlayer = 2;
                }
            }else{
                roundNum += 1;
            }
            Intent startGameIntent = new Intent(EndRound.this, Panorama.class);
            startGameIntent.putExtra("scorePlayer1", scorePlayer1);
            startGameIntent.putExtra("scorePlayer2", scorePlayer2);
            startGameIntent.putExtra("roundNum", roundNum);
            startGameIntent.putExtra("currentPlayer", currentPlayer);
            startGameIntent.putExtra("gameMode", gameMode);
            if(currentPlayer == 2){
                startGameIntent.putExtra("panoID", panoID);
            }
            startActivity(startGameIntent);
        }
    };

}
