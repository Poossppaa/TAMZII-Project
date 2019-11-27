package com.example.tamz2project;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class PlayerOrientationData implements SensorEventListener {
    private SensorManager manager;
    private Sensor accelorometer;
    private Sensor magnometer;


    private float[] accelOutput;
    private float[] magOutput;

    private float[] orientation = new float[3];
    private float[] startOrientation = null;

    public PlayerOrientationData(Context context) {
        this.manager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
        this.accelorometer = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.magnometer = manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    public float[] getStartOrientation() {
        return startOrientation;
    }

    public void register(){
        manager.registerListener(this,accelorometer, SensorManager.SENSOR_DELAY_GAME);
        manager.registerListener(this,magnometer, SensorManager.SENSOR_DELAY_GAME);
    }

    public void pause(){
        manager.unregisterListener(this);
    }

    public float[] getOrientation(){
        return this.orientation;
    }

    public void newGame(){
        startOrientation = null;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            accelOutput = event.values;
        else if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
            magOutput = event.values;
        if(accelOutput != null && magOutput != null){
            float[] rotationMatrix = new float[9];
            float[] inclanationMatrix = new float[9];
            boolean isChanged = SensorManager.getRotationMatrix(rotationMatrix, inclanationMatrix ,accelOutput,magOutput);

            if(isChanged)
                SensorManager.getOrientation(rotationMatrix,orientation);

            if(startOrientation == null){
                startOrientation = new float[orientation.length];
                System.arraycopy(orientation,0,startOrientation,0,orientation.length);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
