package edu.augustana.csc490.thinkfastgame;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainMenu extends ActionBarActivity {

    private String TAG = "MainMenuTAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Button normalButton = (Button) findViewById(R.id.normalButton);
        normalButton.setOnClickListener(normalButtonClickHandler);

        Button howToButton = (Button) findViewById(R.id.fastButton);
        howToButton.setOnClickListener(howToButtonClickHandler);
    }

    View.OnClickListener normalButtonClickHandler = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent activateGame = new Intent(MainMenu.this, ThinkFast.class);
            startActivity(activateGame);
        }
    };

    View.OnClickListener howToButtonClickHandler = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Log.w(TAG, "Reached onClick");
            new AlertDialog.Builder(MainMenu.this)
                    .setTitle("Game Instructions")
                    .setMessage("An image and some text will appear on the screen. " +
                            "There are 3 commands: Touch It, Swipe It, and Shake It. " +
                            "If the text above the image is red you must do one of the two actions. " +
                            "For example if the \"Touch It\" text is red you must either Swipe or Shake the device. " +
                            "If the text is green follow the command shown.")
                    .setPositiveButton(R.string.play_button, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent activateGame = new Intent(MainMenu.this, ThinkFast.class);
                            startActivity(activateGame);
                        }
                    })
                    .setNegativeButton(R.string.main_menu_button, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
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
}
