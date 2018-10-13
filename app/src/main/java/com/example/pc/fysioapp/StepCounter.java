package com.example.pc.fysioapp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

public class StepCounter extends AppCompatActivity implements SensorEventListener{

    TextView lbl_steps;
    SensorManager sensorManager;
    boolean runnning = false;
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_counter);

        lbl_steps = (TextView)findViewById(R.id.lbl_steps);
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);


    }

    @Override
    protected void onResume() {
        super.onResume();
        runnning = true;

        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if(accelerometer != null)
        {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        }
        else
        {
            Toast.makeText(this, "step detector not found", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        runnning = false;


    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {

        if (runnning)
        {
            float axisX = event.values[0];
            float axisY = event.values[1];
            float axisZ = event.values[2];

            if(axisX > 13|| axisY > 13|| axisZ > 13  )
            {


                lbl_steps.setText(String.valueOf(count++ ));
            }


        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
