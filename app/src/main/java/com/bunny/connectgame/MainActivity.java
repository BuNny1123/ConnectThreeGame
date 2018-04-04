package com.bunny.connectgame;

import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int activeColor = 0;  // 0 for Yellow , 1 for Red
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};    // 1 = unclicked
    int[][] winningPostions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}}; //winnning positions
    boolean gameIsActive = true;

    public void dropIn(View view) {
        ImageView counter = (ImageView) view;
        //System.out.println(counter.getTag().toString());
        int gameCounter = Integer.parseInt(counter.getTag().toString());

        if (gameState[gameCounter] == 2 && gameIsActive) {
            gameState[gameCounter] = 0;
            counter.setTranslationY(-1000f);

            if (activeColor == 0) {

                counter.setImageResource(R.drawable.yellow);
                activeColor = 1;
                gameState[gameCounter] = activeColor;
            } else {
                counter.setImageResource(R.drawable.red);
                activeColor = 0;
                gameState[gameCounter] = activeColor;
            }

            counter.animate().translationYBy(1000f).setDuration(300);
            for (int[] win : winningPostions) {
                if (gameState[win[0]] == gameState[win[1]] && gameState[win[1]] == gameState[win[2]] && gameState[win[0]] != 2) {
                    // Toast.makeText(getApplicationContext(),"WON",Toast.LENGTH_LONG).show();
                    gameIsActive = false;
                    //Which Color Won
                    TextView youWonText = (TextView) findViewById(R.id.YouWonText);
                    if (activeColor == 1)
                        youWonText.setText("Yellow Won");
                    else if (activeColor == 0)
                        youWonText.setText("Red Won");

                    //Set Linear Layout- playAgain Visible
                    LinearLayout playAgain = (LinearLayout) findViewById(R.id.playAgain);
                    playAgain.bringToFront();
                    playAgain.setVisibility(View.VISIBLE);

                    //set alpha for gridLayout, gridImage to 0.5f
                    GridLayout grid = (GridLayout) findViewById(R.id.allGrid);
                    grid.animate().alpha(0.5f);
                    ImageView gridImage = (ImageView) findViewById(R.id.gridImage);
                    gridImage.animate().alpha(0.5f);
                } else {
                    gameIsActive = false;
                    for (int i : gameState) {
                        if (i == 2)
                            gameIsActive = true;
                    }
                    if (gameIsActive == false) {
                        LinearLayout playAgain = (LinearLayout) findViewById(R.id.playAgain);
                        playAgain.bringToFront();
                        playAgain.setVisibility(View.VISIBLE);
                        TextView youWonText = (TextView) findViewById(R.id.YouWonText);
                        youWonText.setText("Its a Draw!");

                    }
                }
            }


        }

    }


    public void playAgain(View view) {

        //reseting color and gameState Values
        gameIsActive=true;
        activeColor = 0;
        for (int i = 0; i < gameState.length; i++)
            gameState[i] = 2;

        //set Linear Layout- playAgain invisible
        LinearLayout playAgain = (LinearLayout) findViewById(R.id.playAgain);
        playAgain.bringToFront();
        playAgain.setVisibility(View.INVISIBLE);

        //resetting images in gridLayout
        GridLayout grid = (GridLayout) findViewById(R.id.allGrid);
        grid.animate().alpha(1f);
        ImageView gridImage = (ImageView) findViewById(R.id.gridImage);
        gridImage.animate().alpha(1f);
        for (int i = 0; i < grid.getChildCount(); i++) {
            ((ImageView) grid.getChildAt(i)).setImageResource(0);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
