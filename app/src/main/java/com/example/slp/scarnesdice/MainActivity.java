package com.example.slp.scarnesdice;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.logging.Handler;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvStatus, tvPlayerTurnScore, tvComputerScore, tvPlayerOverallScore;
    private ImageView ivDiceFace;
    private Button btHold, btReset, btRoll;

    private int computerTurnScore, computerOverallScore, playerTurnScore, playerOverallScore;
    private int[] diceFaces = {R.drawable.dice1, R.drawable.dice2, R.drawable.dice3, R.drawable.dice4, R.drawable.dice5, R.drawable.dice6};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvPlayerOverallScore = (TextView) findViewById(R.id.tvMeO);
        tvPlayerTurnScore = (TextView) findViewById(R.id.tvMeC);
        tvComputerScore = (TextView) findViewById(R.id.tvCompO);
        tvStatus = (TextView) findViewById(R.id.tvStatus);
        ivDiceFace = (ImageView) findViewById(R.id.image);
        btHold = (Button) findViewById(R.id.hold);
        btRoll = (Button) findViewById(R.id.roll);
        btReset = (Button) findViewById(R.id.reset);

        btHold.setOnClickListener(this);
        btReset.setOnClickListener(this);
        btRoll.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.hold:
                hold();
                break;
            case R.id.roll:
                roll();
                break;
            case R.id.reset:
                reset();
                break;
        }

    }

    private void roll() {
        btHold.setEnabled(true);
        tvPlayerTurnScore.setVisibility(View.VISIBLE);
        int rollNumber = getDiceFaceNumber();
        Toast.makeText(this, "Rolled: " + rollNumber, Toast.LENGTH_SHORT).show();
        if (rollNumber != 1) {
            playerTurnScore = rollNumber;
            playerOverallScore += rollNumber;
            tvPlayerTurnScore.setText("Player's turn score is: " + playerTurnScore);
            tvPlayerOverallScore.setText("Player's Overall score is: " + playerOverallScore);
        } else {
            playerTurnScore = 0;
            tvPlayerTurnScore.setText("Your turn score is: " + playerTurnScore);
            tvPlayerOverallScore.setText("Player's Overall score is: " + playerOverallScore);
            computerTurn();
        }
        if(playerOverallScore>=25)
            winP();
    }

    private int getDiceFaceNumber() {
        Random random = new Random();
        int i = random.nextInt(6);
        ivDiceFace.setImageResource(diceFaces[i]);
        return i + 1;
    }

    private void playerTurn() {
        resetPlayerTurnScore();
        Toast.makeText(this, "Your Turn", Toast.LENGTH_SHORT).show();
        btHold.setEnabled(false);
        btRoll.setEnabled(true);
        if(playerOverallScore>=25)
            winP();
    }

    private void computerTurn() {
        Toast.makeText(this, "Computer's turn", Toast.LENGTH_SHORT).show();
        resetPlayerTurnScore();
        btRoll.setEnabled(false);
        btHold.setEnabled(false);
        final Random random = new Random();
        while (true) {
            int rollNumber = getDiceFaceNumber();
            Toast.makeText(this, "Rolled" + rollNumber, Toast.LENGTH_SHORT).show();
            if (rollNumber != 1) {
                computerTurnScore = rollNumber;
                boolean hold = random.nextBoolean();
                if (hold) {
                    computerOverallScore += computerTurnScore;
                    break;
                }
            } else {
                computerOverallScore += computerTurnScore;
                computerTurnScore = 0;
                break;
            }
        }
        tvComputerScore.setText("Computer's overall score: " + computerOverallScore);
        if(computerOverallScore>=25)
            winC();
        playerTurn();


    }

    private void reset() {
        tvStatus.setText("STATUS");
        tvPlayerTurnScore.setVisibility(View.VISIBLE);
        tvComputerScore.setVisibility(View.VISIBLE);
        btHold.setEnabled(false);
        btRoll.setEnabled(true);
        playerTurnScore = 0;
        playerOverallScore = 0;
        computerOverallScore = 0;
        computerTurnScore = 0;
        tvPlayerOverallScore.setText("Player's overall Score: " + playerOverallScore);
        tvComputerScore.setText("Computer's overall score: " + computerOverallScore);
        tvPlayerTurnScore.setVisibility(View.INVISIBLE);
        ivDiceFace.setImageResource(diceFaces[0]);
    }


    private void resetPlayerTurnScore() {
        playerTurnScore = 0;
        tvPlayerTurnScore.setText("Player's turn score: " + playerTurnScore);
    }

    private void hold() {
        playerTurnScore = 0;
        tvPlayerOverallScore.setText("Player's overall score: " + playerOverallScore);
        computerTurn();
    }


    private void winP() {
        tvStatus.setText("YOU WoN!");
        reset();
    }

    private void winC() {
        tvStatus.setText("COMPUTER WON!");
        reset();
    }
}