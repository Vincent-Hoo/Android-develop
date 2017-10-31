package com.example.vincentho.lab3;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * Implementation of App Widget functionality.
 */


public class MyWidget extends AppWidgetProvider {

    private static RemoteViews my_remoteview;
    static int state  = 0;
    private static PendingIntent pi;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
/*
        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.my_widget);
        views.setTextViewText(R.id.widget_text, widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
*/
    }



    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        /*
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
        */

        my_remoteview = new RemoteViews(context.getPackageName(), R.layout.my_widget);



            Intent intent = new Intent(context, MainActivity.class);
            pi = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            my_remoteview.setOnClickPendingIntent(R.id.widget_text, pi);


            /*
            Intent widget_jumpToDetail = new Intent("widget_to_detail");
            PendingIntent pi2 = PendingIntent.getBroadcast(context, 0, widget_jumpToDetail, PendingIntent.FLAG_UPDATE_CURRENT);
            my_remoteview.setOnClickPendingIntent(R.id.widget_text, pi2);
*/


            Toast.makeText(context, "进来", Toast.LENGTH_SHORT).show();


        ComponentName me = new ComponentName(context, MyWidget.class);
        appWidgetManager.updateAppWidget(me, my_remoteview);

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context,Intent intent) {
        super.onReceive(context, intent);
        AppWidgetManager widgetManager = AppWidgetManager.getInstance(context);
        if (intent.getAction().equals("initiate")) {
            String name = intent.getStringExtra("name");
            String price = intent.getStringExtra("price");
            int p = intent.getIntExtra("pos", 0);
            String s = name + "仅售" + price;

            Intent jumpDetail = new Intent(context, detail.class);
            jumpDetail.putExtra("name", name);
            jumpDetail.putExtra("price", price);
            jumpDetail.putExtra("info", intent.getStringExtra("info"));
            jumpDetail.putExtra("pos", p);
            jumpDetail.putExtra("source", "widget");
            pi = PendingIntent.getActivity(context, 0, jumpDetail, PendingIntent.FLAG_UPDATE_CURRENT);
            my_remoteview.setOnClickPendingIntent(R.id.widget_text, pi);

            my_remoteview.setTextViewText(R.id.widget_text, s);


            if (p == 0)
                my_remoteview.setImageViewResource(R.id.widget_icon, R.drawable.p1);
            else if (p == 1)
                my_remoteview.setImageViewResource(R.id.widget_icon, R.drawable.p2);
            else if (p == 2)
                my_remoteview.setImageViewResource(R.id.widget_icon, R.drawable.p3);
            else if (p == 3)
                my_remoteview.setImageViewResource(R.id.widget_icon, R.drawable.p4);
            else if (p == 4)
                my_remoteview.setImageViewResource(R.id.widget_icon, R.drawable.p5);
            else if (p == 5)
                my_remoteview.setImageViewResource(R.id.widget_icon, R.drawable.p6);
            else if (p == 6)
                my_remoteview.setImageViewResource(R.id.widget_icon, R.drawable.p7);
            else if (p == 7)
                my_remoteview.setImageViewResource(R.id.widget_icon, R.drawable.p8);
            else if (p == 8)
                my_remoteview.setImageViewResource(R.id.widget_icon, R.drawable.p9);
            else if (p == 9)
                my_remoteview.setImageViewResource(R.id.widget_icon, R.drawable.p10);

            ComponentName me = new ComponentName(context, MyWidget.class);
            widgetManager.updateAppWidget(me, my_remoteview);
        }
        else if(intent.getAction().equals("addedShoplist"))
        {
            int p = intent.getIntExtra("pos", 0);
            String name = intent.getStringExtra("name");
            String s = name + "已被加到购物车";

            my_remoteview.setTextViewText(R.id.widget_text, s);

            if (p == 0)
                my_remoteview.setImageViewResource(R.id.widget_icon, R.drawable.p1);
            else if (p == 1)
                my_remoteview.setImageViewResource(R.id.widget_icon, R.drawable.p2);
            else if (p == 2)
                my_remoteview.setImageViewResource(R.id.widget_icon, R.drawable.p3);
            else if (p == 3)
                my_remoteview.setImageViewResource(R.id.widget_icon, R.drawable.p4);
            else if (p == 4)
                my_remoteview.setImageViewResource(R.id.widget_icon, R.drawable.p5);
            else if (p == 5)
                my_remoteview.setImageViewResource(R.id.widget_icon, R.drawable.p6);
            else if (p == 6)
                my_remoteview.setImageViewResource(R.id.widget_icon, R.drawable.p7);
            else if (p == 7)
                my_remoteview.setImageViewResource(R.id.widget_icon, R.drawable.p8);
            else if (p == 8)
                my_remoteview.setImageViewResource(R.id.widget_icon, R.drawable.p9);
            else if (p == 9)
                my_remoteview.setImageViewResource(R.id.widget_icon, R.drawable.p10);

            ComponentName me = new ComponentName(context, MyWidget.class);
            widgetManager.updateAppWidget(me, my_remoteview);
        }
        else if(intent.getAction().equals("widget_to_detail")){
            Toast.makeText(context, "出来吧皮卡丘", Toast.LENGTH_SHORT).show();


            Intent jumpDetail = new Intent(context, detail.class);
            jumpDetail.putExtra("name", "屎坑");
            jumpDetail.putExtra("price", "100蚊");
            jumpDetail.putExtra("info", "价值连城");
            jumpDetail.putExtra("pos", 3);
            context.startService(jumpDetail);

        }
    }
}

