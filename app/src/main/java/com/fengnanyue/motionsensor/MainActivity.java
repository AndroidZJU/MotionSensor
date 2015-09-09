package com.fengnanyue.motionsensor;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener{//实现回传必须用接口


    private TextView tvAccelerometer;
    private SensorManager mSensorManager;
    private float[] gravity = new float[3];


    @Override
    protected void onCreate(Bundle savedInstanceState) {//精度变化
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvAccelerometer = (TextView)findViewById(R.id.tvAccelerometer);
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

    }


    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(this,mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY),SensorManager.SENSOR_DELAY_FASTEST);

    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {//数据变化
        switch (event.sensor.getType()){
            case Sensor.TYPE_ACCELEROMETER:
                final float alpha  = (float) 0.8;
                gravity[0] = alpha * gravity[0] + (1-alpha)*event.values[0];
                gravity[1] = alpha * gravity[1] + (1-alpha)*event.values[1];
                gravity[2] = alpha * gravity[2] + (1-alpha)*event.values[2];

                String accelerometer = "加速度\n" + "X:" + (event.values[0]-gravity[0]) + "\n"
                        + "Y:" + (event.values[1]-gravity[1]) + "\n" +"Z:" + (event.values[2]-gravity[2]);

                tvAccelerometer.setText(accelerometer);
                break;
            case Sensor.TYPE_GRAVITY:
                gravity[0] = event.values[0];
                gravity[1] = event.values[1];
                gravity[2] = event.values[2];

                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
