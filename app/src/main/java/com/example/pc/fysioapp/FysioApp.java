package com.example.pc.fysioapp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class FysioApp extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometerSensor;
    private long lastUpdate = 0;
    private float lastX, lastY, lastZ;
    private static final int SHAKE_THRESHOLD = 600;
    private TextView dimensionXView = (TextView) findViewById(R.id.DimensionX);
    private TextView dimensionYView = (TextView) findViewById(R.id.DimensionY);
    private TextView dimensionZView = (TextView) findViewById(R.id.DimensionZ);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fysio_app);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
       // dimensionXView.setText(Float.toString(lastX));
        //dimensionYView.setText(Float.toString(lastY));
        //dimensionZView.setText(Float.toString(lastZ));

        // virker der 
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume(){
        super.onResume();
        sensorManager.registerListener(this, accelerometerSensor, sensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor currentSensor = sensorEvent.sensor;

        if (currentSensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            long lastMeasured = System.currentTimeMillis();

            if ((lastMeasured - lastUpdate) > 100) {
                long difference = (lastMeasured - lastUpdate);
                lastUpdate = lastMeasured;

                float speed = Math.abs(x + y + z - lastX - lastY - lastZ)/ difference * 10000;

                if (speed > SHAKE_THRESHOLD) {
                    lastX = x;
                    lastY = y;
                    lastZ = z;

                }


            }


        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
