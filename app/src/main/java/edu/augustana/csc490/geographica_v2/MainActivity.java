package edu.augustana.csc490.geographica_v2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        final Typeface font = Typeface.createFromAsset(this.getAssets(), "fonts/moon_light.otf");

        Button singlePlayerButton = (Button) findViewById(R.id.single);
        singlePlayerButton.setOnClickListener(singlePlayerButtonListener);
        singlePlayerButton.setTypeface(font);
        singlePlayerButton.setTextSize(24);
        singlePlayerButton.setTextColor(Color.BLACK);

        Button localMultiplayerButton = (Button) findViewById(R.id.multiplayerLocal);
        localMultiplayerButton.setOnClickListener(localMultiplayerButtonListener);
        localMultiplayerButton.setTypeface(font);
        localMultiplayerButton.setTextSize(24);
        localMultiplayerButton.setTextColor(Color.BLACK);

        Button onlineMultiplayerButton = (Button) findViewById(R.id.multiplayerOnline);
        onlineMultiplayerButton.setOnClickListener(onlineMultiplayerButtonListener);
        onlineMultiplayerButton.setTypeface(font);
        onlineMultiplayerButton.setTextSize(24);
        onlineMultiplayerButton.setTextColor(Color.BLACK);

    }

    // method that handles a user's click and launches the single player option
    public View.OnClickListener singlePlayerButtonListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            startGame(1);
        }
    };

    // method that handles a user's click and launches the local multiplayer option
    public View.OnClickListener localMultiplayerButtonListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            startGame(2);
        }
    };

    // method that handles a user's click and launches the online multiplayer option
    public View.OnClickListener onlineMultiplayerButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startGame(3);
        }
    };

    private void startGame(int gameMode){
        Intent startGameIntent = new Intent(MainActivity.this, Panorama.class);
        startGameIntent.putExtra("scorePlayer1", 0);
        startGameIntent.putExtra("scorePlayer2",0);
        startGameIntent.putExtra("roundNum", 1);
        startGameIntent.putExtra("currentPlayer", 1);
        startGameIntent.putExtra("gameMode", gameMode);
        startActivity(startGameIntent);
    }




}
