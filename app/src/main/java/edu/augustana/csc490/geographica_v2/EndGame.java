package edu.augustana.csc490.geographica_v2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaFragment;
import com.parse.*;

import java.util.List;

/**
 * Created by Ethan on 5/9/2015.
 */
public class EndGame extends Activity {

    int currentPlayer;
    String playerName;
    int playerTotal;
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
        initiateValues(intent);

        roundManager = new RoundManager(getBaseContext());

        Button mainMenuButton = (Button) findViewById(R.id.mainMenuButton);
        mainMenuButton.setOnClickListener(mainMenuButtonListener);
        roundManager.styleButton(mainMenuButton,24);

        if(gameMode == 1){
            roundManager.styleTextView((TextView) findViewById(R.id.roundScoreView),"Total Score: " + scorePlayer1,24);
            //call to method that handles the parse calls.
            updatePlayerCount();
            updateLeaderboard(this);
        }else{
            roundManager.styleTextView((TextView) findViewById(R.id.roundScoreView),"Player1 Score: " + scorePlayer1,24);
        }
        roundManager.styleTextView((TextView) findViewById(R.id.topTextView), "Game Over",24);

        if(gameMode != 1) {
            roundManager.styleTextView((TextView) findViewById(R.id.totalScoreView), "Player2 Score: " + scorePlayer2, 24);
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

    /**
     * method updatePlayerCount - sends a query to parse to get information about the
     * total number of players
     */
    public void updatePlayerCount(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("PlayerCount");
        query.getInBackground("FA6PbcJq7N", new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject playerCount, ParseException e) {
                if (e == null) {
                    int tempNum = (int) playerCount.getNumber("totalPlayers");
                    tempNum = tempNum + 1;
                    playerTotal = tempNum;
                    playerCount.put("totalPlayers", tempNum);
                    playerCount.saveInBackground();
                } else {
                    Log.d("QUERY", "Error in query for PlayerCount: " + e.getMessage());
                }
            }
        });
    }

    /**
     * @param context
     * Allows the user to input their score, and adds it to the high score list
     */
    public void updateLeaderboard(final Context context){
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        //used to enter new data into parse HighScores table
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("New Score Entry")
                .setMessage("Enter Player Name")
                .setView(input)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    //set what happens when the user clicks the OK button
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        playerName = input.getText().toString();
                        if(playerName.equals("")){
                            playerName = "PlayerNum_" + playerTotal;
                        }
                        ParseObject highScores = new ParseObject("HighScores");
                        highScores.put("userName", playerName);
                        highScores.put("score", scorePlayer1);
                        highScores.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                displayHighScore(context);
                            }
                        });
                    }
                });
        builder.show();
    }

    /**
     * @param context
     * Displays the high score list after the user's score has been added
     * If the user is in the top 10 scores, adds to the top 10 list
     */
    public void displayHighScore(final Context context){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("HighScores");
        query.orderByAscending("score");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> scoreList, ParseException e) {
                if (e == null) {
                    int counter = 0;
                    boolean isTopTen = false;
                    String scores = "";
                    while(counter < 10){
                        //user's score is in the top 10
                        if(scoreList.get(counter).get("userName").equals(playerName)){
                            isTopTen = true;
                            scores = scores + (counter+1) + ". " + scoreList.get(counter).get("userName") + ":\t" + scoreList.get(counter).get("score") + "*" + "\n";
                        }else{
                            scores = scores + (counter+1) + ". " + scoreList.get(counter).get("userName") + ":\t" + scoreList.get(counter).get("score") + "\n";
                        }
                        counter++;
                    }
                    //creates display of high score if the user is not in the top 10
                    //show the top 10 scores, and then a ... and then the user's score
                    if(!isTopTen){
                        while(counter < scoreList.size()){
                            if(scoreList.get(counter).get("userName").equals(playerName)){
                                scores = scores + "\t . . . \n" + (counter+1) + ". " + playerName + ":\t" + scorePlayer1;
                                counter = scoreList.size();
                            }
                            counter++;
                        }
                    }
                    //create message showing high scores
                    AlertDialog.Builder builder = new AlertDialog.Builder(context)
                            .setTitle("Current High Scores")
                            .setMessage(scores);
                    builder.show();
                } else {
                    //error if query fails
                    Log.d("QUERY", "Error in query for HighScores: " + e.getMessage());
                }
            }
        });
    }

    public View.OnClickListener mainMenuButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent menuIntnet = new Intent(EndGame.this, MainActivity.class);
            startActivity(menuIntnet);
        }
    };

}
