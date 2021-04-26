//package com.hj.smalldecision.ui.dice;
//
//import android.content.Context;
//import android.hardware.Sensor;
//import android.hardware.SensorEvent;
//import android.hardware.SensorEventListener;
//import android.hardware.SensorManager;
//import android.media.MediaPlayer;
//import android.os.Bundle;
//import android.view.MotionEvent;
//import android.view.View;
//import android.widget.*;
//import com.hj.smalldecision.R;
//import com.hj.smalldecision.ui.base.BaseActivity;
//
//import java.io.IOException;
//import java.util.Random;
//
//public class Activity_dice extends BaseActivity {
//    SensorManager sensorM;
//    SensorEventListener sensorL;
//    int random_result;
//    int[] imgs={0,
//            R.drawable.box1,
//            R.drawable.box2,
//            R.drawable.box3,
//            R.drawable.box4,
//            R.drawable.box5,
//            R.drawable.box6};
//    ImageButton i_bt;
//    TextView tv_result;
//    MediaPlayer mediaplayer;
//    RadioGroup rg;
//    Context context;
////test
//    TextView tv1,tv2;
//    CheckBox checkbox;
//    float maxNum,minNum;
//
//    private float mLastX;                    //x軸體感(Sensor)偏移
//    private float mLastY;                    //y軸體感(Sensor)偏移
//    private float mLastZ;                    //z軸體感(Sensor)偏移
//    private double mSpeed;                 //甩動力道數度
//    private long mLastUpdateTime;           //觸發時間
//
//    //甩動力道數度設定值 (數值越大需甩動越大力，數值越小輕輕甩動即會觸發)
//    int SPEED_SHRESHOLD = 30000;
//
//    //觸發間隔時間
//    private static final int UPDATE_INTERVAL_TIME = 100;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.tool_dice);
//        i_bt=(ImageButton)findViewById(R.id.btn_shop);
//        tv_result=(TextView)findViewById(R.id.textview2);
//        context=this;
//        mediaplayer=new MediaPlayer();
//        mediaplayer=MediaPlayer.create(context,R.raw.roll_one_dice);
//        rg=(RadioGroup)findViewById(R.id.radio_group);
//        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                mediaplayer=null;
//                switch (checkedId){
//                    case R.id.radioButton:
//                        mediaplayer=new MediaPlayer();
//                        mediaplayer=MediaPlayer.create(context,R.raw.roll_one_dice);
//                        break;
//                    case R.id.radioButton2:
//                        mediaplayer=new MediaPlayer();
//                        mediaplayer=MediaPlayer.create(context,R.raw.drop_one_dice);
//                        break;
//                    case R.id.radioButton3:
//                        mediaplayer=new MediaPlayer();
//                        mediaplayer=MediaPlayer.create(context,R.raw.diceholl);
//                        break;
//                    case R.id.radioButton4:
//                        mediaplayer=new MediaPlayer();
//                        mediaplayer=MediaPlayer.create(context,R.raw.drop_dices);
//                        break;
//                    case R.id.radioButton5:
//                        mediaplayer=new MediaPlayer();
//                        mediaplayer=MediaPlayer.create(context,R.raw.throw_dices);
//                        break;
//                }
//            }
//        });
//        tv1=(TextView)findViewById(R.id.textView10);
//
//        checkbox=(CheckBox)findViewById(R.id.checkBox);
//        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    SPEED_SHRESHOLD = 1350;
//                } else {
//                    SPEED_SHRESHOLD = 30000;
//                }
//            }
//        });
//
//
//        sensorM=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
//        sensorL=new SensorEventListener() {
//            @Override
//            public void onSensorChanged(SensorEvent event) {
//                long mCurrentUpdateTime = System.currentTimeMillis();
//                long mTimeInterval = mCurrentUpdateTime - mLastUpdateTime;
//                if (mTimeInterval < UPDATE_INTERVAL_TIME) return;
//
//                mLastUpdateTime = mCurrentUpdateTime;
//
//                //取得xyz體感(Sensor)偏移
//                float x = event.values[0];
//                float y = event.values[1];
//                float z = event.values[2];
//
//                //甩動偏移速度 = xyz體感(Sensor)偏移 - 上次xyz體感(Sensor)偏移
//                float mDeltaX = x - mLastX;
//                float mDeltaY = y - mLastY;
//                float mDeltaZ = z - mLastZ;
//
//                mLastX = x;
//                mLastY = y;
//                mLastZ = z;
//
//                //體感(Sensor)甩動力道速度公式
//                mSpeed = Math.sqrt(mDeltaX * mDeltaX + mDeltaY * mDeltaY + mDeltaZ * mDeltaZ)/ mTimeInterval * 10000;
//                //若體感(Sensor)甩動速度大於等於甩動設定值則進入 (達到甩動力道及速度)
//                if (mSpeed >= SPEED_SHRESHOLD)
//                {
//                    MyDice();
//                }
//            }
//            @Override
//            public void onAccuracyChanged(Sensor sensor, int accuracy) {
//            }
//        };
//
//        i_bt.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                    MyDice();
//                    return true;
//            }
//        });
//    }
//
//// Self defined Method -- MyDice
//    public void MyDice(){
//        Random generator=new Random();
//        random_result=generator.nextInt(6) + 1;
//        i_bt.setImageResource(imgs[random_result]);
//        tv_result.setText("Result :  "+random_result);
//        if(mediaplayer!=null){
//            try {
//                mediaplayer.seekTo(0);
//                mediaplayer.stop();
//                mediaplayer.prepare();
//                mediaplayer.start();
//            } catch (IOException e) {
//                e.printStackTrace();
//                Toast.makeText(context,"超出負荷, 執行冷卻",Toast.LENGTH_SHORT).show();
//                Thread main=new Thread();
//                try {
//                    main.sleep(3000);
//                } catch (InterruptedException ie) {
//                    ie.printStackTrace();
//                }
//            }
//        }
//    }
////很重要!! sensor註冊 一定要在onResume()階段 原因尚待釐清 by Ray.c 9.10測試結果
//    @Override
//    protected void onResume() {
//        Sensor sensorType = sensorM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//        sensorM.registerListener(sensorL, sensorType, SensorManager.SENSOR_DELAY_NORMAL);
//        super.onResume();
//    }
//    @Override
//    protected void onPause()
//    {
//        sensorM.unregisterListener(sensorL);
//        super.onPause();
//    }
//}
