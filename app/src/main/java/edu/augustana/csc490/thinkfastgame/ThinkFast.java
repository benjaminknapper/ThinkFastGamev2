package edu.augustana.csc490.thinkfastgame;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.lang.Math;

import java.util.Random;


public class ThinkFast extends ActionBarActivity {

    private String TAG = "THINK_FAST";
    private int score = 0;
    private Random rand = new Random();
    private TextView commandText;
    private ImageView commandImage;
    private TextView scoreTextView;
    private TextView timeRemainingTextView;
//for use in the MotionEvent handler
    int startX;
    int startY;
    int time = 5000;
    CountDownTimer timer;
    TFState currentState;
    TFState candidateState;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_think_fast);

        commandText = (TextView) findViewById(R.id.commandText);
        commandImage = (ImageView) findViewById(R.id.commandImage);
        scoreTextView = (TextView) findViewById(R.id.scoreTextView);
        timeRemainingTextView = (TextView) findViewById(R.id.timeRemainingTextView);

        commandImage.setOnClickListener(commandImageClickHandler);
        commandImage.setOnTouchListener(commandImageTouchHandler);

        nextState();


    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_think_fast, menu);

        return true;
    }
//commandImage click listener
    View.OnClickListener commandImageClickHandler = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

        }
    };

    private void nextState() {
        candidateState = new TFState(rand);
        while(candidateState.equals(currentState)) {
            candidateState = new TFState(rand);
        }
        currentState = candidateState;
        timer = new CountDownTimer(time, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                String seconds = "" + (int) millisUntilFinished / 1000;
                String ms = "" + millisUntilFinished / 100;
                if(ms.length() > 1) {
                    ms = ms.substring(1);
                }
                timeRemainingTextView.setText("Time: " + seconds + "." + ms);
            }

            @Override
            public void onFinish() {
                Log.w(TAG,"Time Out" );
                endGame();
            }
        }.start();
        updateStateDisplayed();
    }
//commandImage swipe listener
    View.OnTouchListener commandImageTouchHandler = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            int actionTaken = event.getAction();

           // Log.w("THINK_FAST", "actionTaken=" + actionTaken);

            if (actionTaken == MotionEvent.ACTION_DOWN) {
                startX = (int) event.getX();
                startY = (int) event.getY();
            }
            else if(actionTaken == MotionEvent.ACTION_UP) {
                boolean correctAction = false;
                int endX = (int) event.getX();
                int endY = (int) event.getY();

                if((Math.abs(startX - endX) <= 20) && ((Math.abs(startY - endY)) <= 20)) {
                    correctAction = currentState.isCorrectMove(TFState.ACTION_TOUCH);
                    Log.w(TAG,"TOUCH Action");
                } else { // user moved more than 20 units in X or Y dimension
                    correctAction = currentState.isCorrectMove(TFState.ACTION_SWIPE);
                    Log.w(TAG,"SWIPE + startX = " + startX + " endX " + endX);
                }

                if (correctAction) {
                    score++;
                    time = time - time/20;
                    timer.cancel();
                    nextState();

                } else {
                    Log.w(TAG, "Incorrect Move");
                    endGame();
                }
            }

         //   if(actionTaken == MotionEvent.ACTION_UP) {
         //       correctAction = currentState.isCorrectMove(actionTaken);
         //  } else if(actionTaken == MotionEvent.ACTION_MOVE) {
         //       correctAction = currentState.isCorrectMove(actionTaken);
         //   } else {
         //       correctAction = false;
         //   }
            return false;
        }


    };



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void updateStateDisplayed() {
        if(currentState.isOpposite()) {
            commandText.setTextColor(Color.RED);
        } else {
            commandText.setTextColor(Color.GREEN);
        }

        if(currentState.getAction() == TFState.ACTION_TOUCH) {
            commandImage.setImageResource(R.drawable.colored_bullseye);
            commandText.setText("Touch It");
        } else if(currentState.getAction() == TFState.ACTION_SWIPE) {
            commandImage.setImageResource(R.drawable.swipe_arrow);
            commandText.setText("Swipe It");
        }

        scoreTextView.setText("Score: " + score);





     /*   textRand = rand.nextInt(2);
        imageRand = rand.nextInt(2);
    //Green = 0, Red = 1
        textColorState = textRand;
        imageState = ACTION_TOUCH;
        if(textRand == 0) {
            commandText.setTextColor(Color.GREEN);
        } else if(textRand == 1) {
            commandText.setTextColor(Color.RED);
        }
    //Touch It = 0, Swipe It = 1
        if(imageRand == 0) {
            commandImage.setImageResource(R.drawable.colored_bullseye);
            commandText.setText("Touch It");
        } else if(imageRand == 1) {
            commandImage.setImageResource(R.drawable.swipe_arrow);
            commandText.setText("Swipe It");
        } */
    }

    public void endGame() {
        finish();
    }
}
