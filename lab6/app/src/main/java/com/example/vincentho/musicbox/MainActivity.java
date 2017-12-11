package com.example.vincentho.musicbox;

import android.animation.ObjectAnimator;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {

    private IBinder mBinder;
    private ServiceConnection mconnection;
    private Button play_btn;
    private Button stop_btn;
    private Button exit_btn;
    private TextView music_time;
    private TextView music_length;
    private TextView music_state;
    private SeekBar progress_bar;
    private ImageView album_cover;
    private Handler mHandler;
    private SimpleDateFormat time = new SimpleDateFormat("mm:ss");
    private ObjectAnimator mCircleAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        play_btn = findViewById(R.id.play_btn);
        stop_btn = findViewById(R.id.stop_btn);
        exit_btn = findViewById(R.id.quit_btn);
        music_state = findViewById(R.id.music_state);
        music_time = findViewById(R.id.music_time);
        music_length = findViewById(R.id.musci_length);
        progress_bar = findViewById(R.id.progress_bar);
        album_cover = findViewById(R.id.albumcover);

        mCircleAnimator = ObjectAnimator.ofFloat(album_cover, "rotation", 0.0f, 360.0f);
        mCircleAnimator.setDuration(18000);
        mCircleAnimator.setInterpolator(new LinearInterpolator());
        mCircleAnimator.setRepeatCount(-1);
        mCircleAnimator.setRepeatMode(ObjectAnimator.RESTART);


        mconnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mBinder = service;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mconnection = null;
            }
        };

        final Intent play_music = new Intent(this, MusicService.class);
        startService(play_music);
        bindService(play_music, mconnection, Context.BIND_AUTO_CREATE);

        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                switch (msg.what){
                    case 123:{
                        try{
                            Parcel data = Parcel.obtain();
                            Parcel reply = Parcel.obtain();
                            mBinder.transact(103, data, reply, 0);
                            int length = reply.readInt();
                            int current_time = reply.readInt();
                            progress_bar.setMax(length);
                            progress_bar.setProgress(current_time);
                            music_length.setText(time.format(length));
                            music_time.setText(time.format(current_time));
                        } catch(RemoteException e){
                            e.printStackTrace();
                        }
                        break;
                    }
                }
            }
        };


        progress_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser){
                    try{
                        Parcel data = Parcel.obtain();
                        Parcel reply = Parcel.obtain();
                        data.writeInt(progress_bar.getProgress());
                        mBinder.transact(104, data, reply, 0);

                    } catch(RemoteException e){
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        final Thread mThread = new Thread(){
            @Override
            public void run(){
                while(true){
                    try{
                        Thread.sleep(100);
                    } catch(InterruptedException e){
                        e.printStackTrace();
                    }
                    if(mconnection != null)
                        mHandler.obtainMessage(123).sendToTarget();

                }
            }
        };
        mThread.start();

        final boolean[] hasStarted = {false};
        play_btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                try {
                    Parcel data = Parcel.obtain();
                    Parcel reply = Parcel.obtain();
                    mBinder.transact(101, data, reply, 0);
                    int flag = reply.readInt();

                    if(flag == 0){
                        music_state.setText("playing");
                        if(hasStarted[0] == false){
                            mCircleAnimator.start();
                            hasStarted[0] = true;
                        }
                        else
                            mCircleAnimator.resume();
                    }
                    else{
                        music_state.setText("paused");
                        mCircleAnimator.pause();
                    }

                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        stop_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Parcel data = Parcel.obtain();
                    Parcel reply = Parcel.obtain();
                    mBinder.transact(102, data, reply, 0);
                    music_state.setText("stopped");
                    mCircleAnimator.end();
                    hasStarted[0] = false;
                } catch(RemoteException e){
                    e.printStackTrace();
                }
            }
        });

        exit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Parcel data = Parcel.obtain();
                    Parcel reply = Parcel.obtain();
                    mBinder.transact(105, data, reply, 0);
                    mHandler.removeCallbacks(mThread);
                    unbindService(mconnection);
                    mconnection = null;
                    try{
                        MainActivity.this.finish();
                        System.exit(0);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onBackPressed(){
        moveTaskToBack(false);
    }
}
