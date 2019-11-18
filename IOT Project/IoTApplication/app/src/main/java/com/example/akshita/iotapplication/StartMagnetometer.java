package com.example.akshita.iotapplication;

//mport android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
//import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import java.io.FileOutputStream;

public class StartMagnetometer extends AppCompatActivity implements SensorEventListener {
    Button b1;
    SensorManager sm;
    Sensor g, m, r, gr; // all the sensors we are using
    // arrays to hold all the sensor values
    public float gvalue[];
    public float mvalue[];
    public float rvalue[];
    public float grvalue[];
    //File stuff

    // ------ CHANGE THE NAME OF THE FILE TO yournameapplicationname.txt-----------------
    String fileName = "akshita.txt";





    FileOutputStream fos;
   // public ActivityManager am = (ActivityManager)this.getSystemService(Context.ACTIVITY_SERVICE);

    //App stuff

    @Override
    // every time values change the data is picked up
    public void onSensorChanged(SensorEvent event) {

        //List l = am.getRecentTasks(1, ActivityManager.RECENT_WITH_EXCLUDED);
        gvalue = event.values;
        mvalue = event.values;
        rvalue=event.values;
;
        grvalue = event.values;

        String av = gvalue[0] + "," + gvalue[1] + "," + gvalue[2];
        String bv = mvalue[0] + "," + mvalue[1] + "," + mvalue[2];
        String rv = rvalue[0] + "," + rvalue[1] + "," + rvalue[2];
        String grv = grvalue[0] + "," + grvalue[1] + "," + grvalue[2];



        String msg = "At" + System.currentTimeMillis() + ": Gyroscope: " + av + " Magnetometer: " + bv + " Rotation: "+rv +  " Gravity: "+grv;

        Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_LONG).show(); // just to check if the app is reading the values
        writemsg(msg); // writing the values to the result file

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    // resumes activity and listens to sensor events and if the sensors become available, they are registered
    protected void onResume() {
        super.onResume();
        sm.registerListener(this, g, SensorManager.SENSOR_DELAY_NORMAL);
    }

    // pauses and unregisters sensor data
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(this);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_magnetometer);


        sm = (SensorManager) getSystemService(SENSOR_SERVICE);

        // checking if the sensors are present on the device or not
        // registering sensors
        if ((sm.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null) && (sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null)&&(sm.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)!=null)&&(sm.getDefaultSensor(Sensor.TYPE_GRAVITY)!=null))
        {
            g = sm.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
            m = sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
            r = sm.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
            gr = sm.getDefaultSensor(Sensor.TYPE_GRAVITY);

        }

        // ending the application if the sensors are not present.
        else {

        }
    }

    // method to write the sensor readings to the result file
    public void writemsg(String msg) {
        try {
            fos = openFileOutput(fileName, Context.MODE_APPEND);
            fos.write(msg.getBytes());
            fos.write("\n".getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}














