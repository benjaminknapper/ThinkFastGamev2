package edu.augustana.csc490.thinkfastgame;

import android.graphics.Color;

import java.util.Random;

/**
 * Created by Benjamin on 4/1/2015.
 */
public class TFState {
    public static final int ACTION_TOUCH = 0;
    public static final int ACTION_SWIPE = 1;
    public static final int COLOR_GREEN = 2;
    public static final int COLOR_RED = 3;

    private boolean opposite;
    private int targetAction;

    public TFState(Random rand) {
        int imageState = rand.nextInt(2);
        int textState = (rand.nextInt(2) + 2);

        if(imageState == ACTION_TOUCH) {
            targetAction = ACTION_TOUCH;
        } else if(imageState == ACTION_SWIPE) {
            targetAction = ACTION_SWIPE;
        }

        if(textState == COLOR_GREEN) {
            opposite = false;
        } else if(textState == COLOR_RED) {
            opposite = true;
        }
    }

    public boolean isOpposite() {
        return opposite;
    }

    public int getAction() {
        return targetAction;
    }

    public boolean isCorrectMove(int userAction) {
        return true;
    }
}
