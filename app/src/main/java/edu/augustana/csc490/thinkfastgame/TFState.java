package edu.augustana.csc490.thinkfastgame;

import java.util.Random;

/**
 * Created by Benjamin on 4/1/2015.
 */
public class TFState {
    public static final int NUM_ACTIONS = 3;

    public static final int ACTION_TOUCH = 0; // action constants must be declared sequentially from zero
    public static final int ACTION_SWIPE = 1;
    public static final int ACTION_SHAKE = 2;

    private boolean opposite;
    private int targetAction;

    public TFState(Random rand) {
        targetAction = rand.nextInt(NUM_ACTIONS);
        opposite = rand.nextBoolean();
    }

    public boolean isOpposite() {
        return opposite;
    }

    public int getAction() {
        return targetAction;
    }

    public boolean isCorrectMove(int userAction) {
        if(opposite){
            return (userAction != this.targetAction);
        } else{
            return (userAction == this.targetAction);
        }


        }


    @Override
    public boolean equals(Object other) {
        if (other instanceof TFState) {
            TFState otherTFState = (TFState) other;
            return (otherTFState.targetAction == this.targetAction && otherTFState.opposite == this.opposite);
        } else {
            return false;
        }
    }

}
