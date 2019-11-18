package com.example.akshita.sensorpro;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.io.FileOutputStream;

public class MyService extends Service implements SensorEventListener {
    private final String LOG_TAG ="MainActivity";
    private SensorManager sm;
    private Sensor m,g,r,gr;
    public float[] mval;
    public float[] gval;
    public float[] rval;
    public float[] grval;

    public String fileName = "akshita2.txt";
    public FileOutputStream fos;


    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        Toast.makeText(this, "Invoke background service onCreate method.", Toast.LENGTH_LONG).show();
        super.onCreate();
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        m = sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        g = sm.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        r = sm.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        gr = sm.getDefaultSensor(Sensor.TYPE_GRAVITY);
        sm.registerListener(this,m,SensorManager.SENSOR_DELAY_NORMAL);
        sm.registerListener(this,g,SensorManager.SENSOR_DELAY_NORMAL);
        sm.registerListener(this,r,SensorManager.SENSOR_DELAY_NORMAL);
        sm.registerListener(this,gr,SensorManager.SENSOR_DELAY_NORMAL);


    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Invoke background service onStartCommand method.", Toast.LENGTH_LONG).show();
        return super.onStartCommand(intent, flags, startId);



    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Invoke background service onDestroy method.", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Long tslong =  System.currentTimeMillis()/1000;
        String ts = tslong.toString();
        mval = event.values;
        gval = event.values;
        rval = event.values;
        grval = event.values;
        String x  = ts+" Magnetometer: "+ mval[0]+","+mval[1]+","+mval[2]+" Gyroscope: "+gval[0]+","+gval[1]+","+gval[2]+" Rotation: "+rval[0]+","+rval[1]+","+rval[2]+" Gravity: "+grval[0]+","+grval[1]+","+grval[2];
        writemsg(x);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    public void writemsg(String msg)
    {
        try{
        fos=openFileOutput(fileName,MODE_APPEND);
        fos.write(msg.getBytes());
            fos.write("\n".getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
