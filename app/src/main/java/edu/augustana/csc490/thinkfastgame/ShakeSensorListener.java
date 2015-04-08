package edu.augustana.csc490.thinkfastgame;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

/**
 * Taken/edited from
 * http://code.tutsplus.com/tutorials/using-the-accelerometer-on-android--mobile-22125
 */
public class ShakeSensorListener implements SensorEventListener {

    private long lastUpdate = 0;
    private long lastShake = System.currentTimeMillis();
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 1000;
    ShakeListener myListener;

    public ShakeSensorListener(ShakeListener myListener) {
        this.myListener = myListener;

    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        Sensor mySensor = event.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            long curTime = System.currentTimeMillis();

            if ((curTime - lastUpdate) > 100) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                float speed = Math.abs(x + y + z - last_x - last_y - last_z)/ diffTime * 10000;

                if (speed > SHAKE_THRESHOLD && (System.currentTimeMillis() - lastShake) > 500) {
                    lastShake = System.currentTimeMillis();
                    myListener.onShakeEvent();
                }

                last_x = x;
                last_y = y;
                last_z = z;
            }
        }


        //if shake event happened
        // myListener.onShakeEvent();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}
