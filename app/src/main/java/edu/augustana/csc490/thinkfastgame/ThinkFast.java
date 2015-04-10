package edu.augustana.csc490.thinkfastgame;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
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


public class ThinkFast extends ActionBarActivity implements ShakeListener {

    private SensorManager mySensorManager;
    private Sensor myAccelerometer;


    private String TAG = "THINK_FAST";
    private int score = 0;
    private int state;
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
    ShakeSensorListener myShakeSensorListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_think_fast);

        commandText = (TextView) findViewById(R.id.commandText);
        commandImage = (ImageView) findViewById(R.id.commandImage);
        scoreTextView = (TextView) findViewById(R.id.scoreTextView);
        timeRemainingTextView = (TextView) findViewById(R.id.timeRemainingTextView);

//taken/edited from
// http://code.tutsplus.com/tutorials/using-the-accelerometer-on-android--mobile-22125
        mySensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        myAccelerometer = mySensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        myShakeSensorListener = new ShakeSensorListener(this);
        mySensorManager.registerListener(myShakeSensorListener, myAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

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


    //commandImage click listener
    View.OnClickListener commandImageClickHandler = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

        }
    };

    private void nextState() {
        candidateState = new TFState(rand);
        while (candidateState.equals(currentState)) {
            candidateState = new TFState(rand);
        }
        currentState = candidateState;
        timer = new CountDownTimer(time, 100) {

            @Override
            public void onTick(long millisUntilFinished) {
                //Display time code from Trevor Warner
                String seconds = "" + (int) millisUntilFinished / 1000;
                String ms = "" + millisUntilFinished / 100;
                if (ms.length() > 1) {
                    ms = ms.substring(1);
                }
                timeRemainingTextView.setText("Time: " + seconds + "." + ms);
            }

            @Override
            public void onFinish() {
                Log.w(TAG, "Time Out");
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


            if (actionTaken == MotionEvent.ACTION_DOWN) {
                startX = (int) event.getX();
                startY = (int) event.getY();
            } else if (actionTaken == MotionEvent.ACTION_UP) {

                int endX = (int) event.getX();
                int endY = (int) event.getY();

                if ((Math.abs(startX - endX) <= 20) && ((Math.abs(startY - endY)) <= 20)) {
                    state = TFState.ACTION_TOUCH;
                } else {
                    state = TFState.ACTION_SWIPE;
                }

                updateGame(state);

            }
            return false;
        }


    };

    public void updateGame(int actionTaken) {
        if (currentState.isCorrectMove(actionTaken)) {
            score++;
            time = time - time / 20;
            timer.cancel();
            nextState();
        } else {
            Log.w(TAG, "Incorrect Move");
            timer.cancel();
            endGame();
        }
    }

    public void updateStateDisplayed() {
        if (currentState.isOpposite()) {
            commandText.setTextColor(Color.RED);
        } else {
            commandText.setTextColor(Color.GREEN);
        }

        if (currentState.getAction() == TFState.ACTION_TOUCH) {
            commandImage.setImageResource(R.drawable.colored_bullseye);
            commandText.setText("Touch It");
        } else if (currentState.getAction() == TFState.ACTION_SWIPE) {
            commandImage.setImageResource(R.drawable.swipe_arrow);
            commandText.setText("Swipe It");
        } else if (currentState.getAction() == TFState.ACTION_SHAKE) {
            commandImage.setImageResource(android.R.drawable.ic_menu_always_landscape_portrait);
            commandText.setText("Shake It");
        }

        scoreTextView.setText("Score: " + score);

    }


    public void endGame() {
        mySensorManager.unregisterListener(myShakeSensorListener);

        //code from http://stackoverflow.com/questions/2115758/how-to-display-alert-dialog-in-android
        Log.w(TAG, "Start Dialog");
        new AlertDialog.Builder(this)
                .setTitle("Game Over")
                .setMessage("Score: " + score)
                .setPositiveButton(R.string.reset_button, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton(R.string.cancel_button, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
        Log.w(TAG, "End Dialog");
    }


    @Override
    protected void onPause() {
        super.onPause();
        mySensorManager.unregisterListener(myShakeSensorListener);
        timer.cancel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mySensorManager.registerListener(myShakeSensorListener, myAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        timer.start();
    }

    @Override
    public void onShakeEvent() {
        updateGame(TFState.ACTION_SHAKE);
        Log.w(TAG, "Shake Occurred");
    }
}
