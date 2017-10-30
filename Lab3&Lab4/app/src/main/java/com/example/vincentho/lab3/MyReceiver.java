package com.example.vincentho.lab3;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

public class MyReceiver extends BroadcastReceiver {

    private Resources resources;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        if(intent.getAction().equals("initiate"))
        {
            String name = intent.getStringExtra("name");
            String info = intent.getStringExtra("info");
            String price = intent.getStringExtra("price");
            int p = intent.getIntExtra("pos", 0);
            String s = name + "仅售" + price;

            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.p5);
            NotificationManager iniNotifyManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            Notification.Builder builder = new Notification.Builder(context);
            builder.setContentTitle("新商品热卖")
                    .setContentText(s)
                    .setTicker("今日新推荐")
                    .setAutoCancel(true)
                    .setWhen(System.currentTimeMillis())
                    .setLargeIcon(bitmap);

            if(p == 0)
                builder.setSmallIcon(R.drawable.p1);
            else if(p == 1)
                builder.setSmallIcon(R.drawable.p2);
            else if(p == 2)
                builder.setSmallIcon(R.drawable.p3);
            else if(p == 3)
                builder.setSmallIcon(R.drawable.p4);
            else if(p == 4)
                builder.setSmallIcon(R.drawable.p5);
            else if(p == 5)
                builder.setSmallIcon(R.drawable.p6);
            else if(p == 6)
                builder.setSmallIcon(R.drawable.p7);
            else if(p == 7)
                builder.setSmallIcon(R.drawable.p8);
            else if(p == 8)
                builder.setSmallIcon(R.drawable.p9);
            else if(p == 9)
                builder.setSmallIcon(R.drawable.p10);

            Intent mintent = new Intent(context, detail.class);
            mintent.putExtra("name",name);
            mintent.putExtra("price", price);
            mintent.putExtra("pos",p);
            mintent.putExtra("info",info);
            mintent.putExtra("source", "noti");
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, mintent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);

            Notification n = builder.build();
            iniNotifyManager.notify(0, n);
        }
        else if(intent.getAction().equals("addedShoplist"))
        {
            int p = intent.getIntExtra("pos", 0);
            String name = intent.getStringExtra("name");
            String s = name + "已被加到购物车";
            Notification.Builder builder = new Notification.Builder(context);
            NotificationManager handManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);;

            builder.setContentTitle("马上下单")
                    .setContentText(s)
                    .setTicker("恭喜你！剁手成功")
                    .setSmallIcon(R.drawable.full_star)
                    .setAutoCancel(true)
                    .setWhen(System.currentTimeMillis());

            if(p == 0)
                builder.setSmallIcon(R.drawable.p1);
            else if(p == 1)
                builder.setSmallIcon(R.drawable.p2);
            else if(p == 2)
                builder.setSmallIcon(R.drawable.p3);
            else if(p == 3)
                builder.setSmallIcon(R.drawable.p4);
            else if(p == 4)
                builder.setSmallIcon(R.drawable.p5);
            else if(p == 5)
                builder.setSmallIcon(R.drawable.p6);
            else if(p == 6)
                builder.setSmallIcon(R.drawable.p7);
            else if(p == 7)
                builder.setSmallIcon(R.drawable.p8);
            else if(p == 8)
                builder.setSmallIcon(R.drawable.p9);
            else if(p == 9)
                builder.setSmallIcon(R.drawable.p10);

            Intent mintent = new Intent(context, MainActivity.class);
            mintent.putExtra("flag", 2);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 , mintent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);

            Notification n = builder.build();
            handManager.notify(0, n);
        }



//        throw new UnsupportedOperationException("Not yet implemented");
    }


    public Resources getResources() {
        return resources;
    }
}
