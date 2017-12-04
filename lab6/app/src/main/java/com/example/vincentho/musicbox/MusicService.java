package com.example.vincentho.musicbox;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.widget.Toast;

public class MusicService extends Service {
    private IBinder mBinder = new MyBinder();
    public static MediaPlayer mp;

    public MusicService() {
        try{
            mp = new MediaPlayer();
            mp.setDataSource(Environment.getExternalStorageDirectory().getAbsolutePath() + "/music/melt.mp3");
            mp.prepare();
            mp.setLooping(true);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mBinder;
    }

    public class MyBinder extends Binder{
        @Override
        protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case 101:
                    if(mp.isPlaying()){
                        mp.pause();
                        reply.writeInt(1);
                    }
                    else{
                        mp.start();
                        reply.writeInt(0);
                    }
                    break;
                case 102:
                     mp.stop();
                    try {
                        mp.prepare();
                        mp.seekTo(0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                     break;
                case 103:
                    reply.writeInt(mp.getDuration());
                    reply.writeInt(mp.getCurrentPosition());
                    break;
                case 104:
                    int progress = data.readInt();
                    mp.seekTo(progress);
                    break;
                case 105:
                    mp.stop();
                    mp.release();
                    break;
            }
            return super.onTransact(code, data, reply, flags);

        }
    }
}
