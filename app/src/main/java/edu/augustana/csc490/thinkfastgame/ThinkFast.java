package edu.augustana.csc490.thinkfastgame;

import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;


public class ThinkFast extends ActionBarActivity {

    private int score = 0;
    private Random rand = new Random();
    private int textRand;
    private int iconRand;
    private int textState;
    private TextView commandText;
    private ImageView commandImage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_think_fast);

        commandText = (TextView) findViewById(R.id.commandText);
        commandImage = (ImageView) findViewById(R.id.commandImage);

        commandImage.setOnClickListener(commandImageClickHandler);
        commandImage.setOnTouchListener(commandImageTouchHandler);

        runGame();
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
            if(event.getAction() == MotionEvent.ACTION_MOVE) {

            }
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

    public void runGame() {
        setTextAndIcon();

    }

    public void setTextAndIcon() {
        textRand = rand.nextInt(2);
        iconRand = rand.nextInt(2);
        if(textRand == 0) {
            commandText.setTextColor(Color.GREEN);
        } else if(textRand == 1) {
            commandText.setTextColor(Color.RED);
        }

        if(iconRand == 0) {
            commandImage.setImageResource(R.drawable.colored_bullseye);
            commandText.setText("Touch It");
        } else if(iconRand == 1) {
            commandImage.setImageResource(R.drawable.swipe_arrow);
            commandText.setText("Swipe It");
        }
    }
}
