package com.example.vincentho.lab3;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vincent Ho on 2017/10/24 0024.
 */

public class detail extends AppCompatActivity {

    private boolean starflag;
    private List<String> more_detail;
    private ListView mListView;
    MyReceiver dynamicReceiver;
    String source;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.iteminfo);


        IntentFilter dynamic_filter = new IntentFilter();
        dynamicReceiver = new MyReceiver();
        dynamic_filter.addAction("addedShoplist");
        registerReceiver(dynamicReceiver, dynamic_filter);


        source = getIntent().getStringExtra("source");

        TextView tv = (TextView) findViewById(R.id.name);
        final String value = getIntent().getStringExtra("name");
        String temp = "";
        for(int i = 0; i <value.length(); i++)
        {
            if(value.charAt(i) != ' ')
                temp += value.charAt(i);
            else
                temp += '\n';
        }
        tv.setText(temp);

        TextView tv1 = (TextView) findViewById(R.id.price);
        final String p = getIntent().getStringExtra("price");
        tv1.setText(p);

        TextView tv2 = (TextView) findViewById(R.id.info);
        final String i = getIntent().getStringExtra("info");
        tv2.setText(i);

        final int pos = getIntent().getIntExtra("pos", 0) + 1;

        ImageView iv = (ImageView) findViewById(R.id.pic);
        if(pos == 1)
            iv.setImageResource(R.drawable.p1);
        else if(pos == 2)
            iv.setImageResource(R.drawable.p2);
        else if(pos == 3)
            iv.setImageResource(R.drawable.p3);
        else if(pos == 4)
            iv.setImageResource(R.drawable.p4);
        else if(pos == 5)
            iv.setImageResource(R.drawable.p5);
        else if(pos == 6)
            iv.setImageResource(R.drawable.p6);
        else if(pos == 7)
            iv.setImageResource(R.drawable.p7);
        else if(pos == 8)
            iv.setImageResource(R.drawable.p8);
        else if(pos == 9)
            iv.setImageResource(R.drawable.p9);
        else if(pos == 10)
            iv.setImageResource(R.drawable.p10);




        ImageButton btn = (ImageButton) findViewById(R.id.back);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                if(source.equals("widget")){
                    finish();
                }
                else if(source.equals("carlist")) {
                    intent.putExtra("flag", 2);
                    intent.setClass(detail.this, MainActivity.class);
                    detail.this.startActivity(intent);
                }
                else{
                    intent.putExtra("flag", 1);
                    intent.setClass(detail.this, MainActivity.class);
                    detail.this.startActivity(intent);
                }
            }
        });



        starflag = true;
        ImageButton starbtn = (ImageButton) findViewById(R.id.star);
        starbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                ImageButton temp = (ImageButton) findViewById(R.id.star);
                if(starflag == true) {
                    starflag = false;
                    temp.setImageResource(R.drawable.full_star);
                }
                else{
                    starflag = true;
                    temp.setImageResource(R.drawable.empty_star);
                }
            }
        });




        ImageButton purchasebtn = (ImageButton) findViewById(R.id.purchase);
        purchasebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Toast.makeText(getApplicationContext(), "已加入购物车", Toast.LENGTH_SHORT).show();

                item addedItem = new item(value, p, i);
                EventBus.getDefault().post(new MyEventbus("you've clipped ur hands", addedItem));

                Intent add_to_shoplist = new Intent("addedShoplist");
                add_to_shoplist.putExtra("name", value);
                add_to_shoplist.putExtra("pos", pos - 1);
                sendBroadcast(add_to_shoplist);
            }
        });



        final String[] ss = new String[]{"一键下单", "分享商品", "不感兴趣", "查看更多商品促销"};
        mListView = (ListView) findViewById(R.id.more);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.moredetail, ss);
        mListView.setAdapter(arrayAdapter);


    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(dynamicReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        if(source.equals("widget")){
            finish();
        }
        else if(source.equals("carlist")) {
            intent.putExtra("flag", 2);
            intent.setClass(detail.this, MainActivity.class);
            detail.this.startActivity(intent);
        }
        else{
            intent.putExtra("flag", 1);
            intent.setClass(detail.this, MainActivity.class);
            detail.this.startActivity(intent);
        }
        super.onBackPressed();
    }

}
