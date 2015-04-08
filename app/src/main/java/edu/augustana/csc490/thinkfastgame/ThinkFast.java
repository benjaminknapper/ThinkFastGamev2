package edu.augustana.csc490.thinkfastgame;

import android.graphics.Color;
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
import android.widget.Toast;

import java.util.Random;


public class ThinkFast extends ActionBarActivity {

    private int score = 0;
    private Random rand = new Random();
    private TextView commandText;
    private ImageView commandImage;
//for use in the MotionEvent handler
    int startX;
    int startY;
    TFState currentState;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_think_fast);

        commandText = (TextView) findViewById(R.id.commandText);
        commandImage = (ImageView) findViewById(R.id.commandImage);

        commandImage.setOnClickListener(commandImageClickHandler);
        commandImage.setOnTouchListener(commandImageTouchHandler);

        currentState = new TFState(rand);
        updateStateDisplayed();

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

//commandImage swipe listener
    View.OnTouchListener commandImageTouchHandler = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            boolean correctAction = false;
            int endX;
            int endY;

            int actionTaken = event.getAction();

           // Log.w("THINK_FAST", "actionTaken=" + actionTaken);

            if (actionTaken == MotionEvent.ACTION_DOWN) {
                startX = (int) event.getX();
                startY = (int) event.getY();
            }

            if(actionTaken == MotionEvent.ACTION_UP) {
                endX = (int) event.getX();
                endY = (int) event.getY();

                if((Math.abs(startX - endX) <= 7) && ((Math.abs(startY - endY)) <= 7)) {
                    correctAction = currentState.isCorrectMove(TFState.ACTION_TOUCH);
                    Log.w("Sent info to TFState","TOUCH Action");
                } else if((Math.abs(startX - endX) > 7) && ((Math.abs(startY - endY)) > 7)) {
                    correctAction = currentState.isCorrectMove(TFState.ACTION_SWIPE);
                    Log.w("Sent info to TFState","SWIPE + startX = " + startX + " endX " + endX);
                }

                if (correctAction) {
                    score++;
                    currentState = new TFState(rand);
                    updateStateDisplayed();
                } else {
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
