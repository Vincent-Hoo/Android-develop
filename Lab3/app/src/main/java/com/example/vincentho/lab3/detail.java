package com.example.vincentho.lab3;

import android.content.Intent;
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
    private boolean isPurchase;




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.iteminfo);

        TextView tv = (TextView) findViewById(R.id.name);
        String value = getIntent().getStringExtra("name");
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
        String p = getIntent().getStringExtra("price");
        tv1.setText(p);

        TextView tv2 = (TextView) findViewById(R.id.info);
        String i = getIntent().getStringExtra("info");
        tv2.setText(i);

        int pos = getIntent().getIntExtra("pos", 0);
        pos = pos + 1;
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
                if(isPurchase == true){
                    Intent intent = new Intent();
                    intent.setClass(detail.this, MainActivity.class);
                    TextView tv = (TextView) findViewById(R.id.name);
                    TextView tv1 = (TextView) findViewById(R.id.price);
                    intent.putExtra("name", tv.getText());
                    intent.putExtra("price", tv1.getText());
                    setResult(1, intent);
                    finish();//进行跳转窗口，如果去掉这句，那就不能回去主界面
                }
                else{
                    Intent intent = new Intent();
                    intent.setClass(detail.this, MainActivity.class);
                    finish();
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

        isPurchase = false;
        ImageButton purchasebtn = (ImageButton) findViewById(R.id.purchase);
        purchasebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Toast.makeText(getApplicationContext(), "已加入购物车", Toast.LENGTH_SHORT).show();
                isPurchase = true;
            }
        });



        final String[] ss = new String[]{"一键下单", "分享商品", "不感兴趣", "查看更多商品促销"};
        //more_detail = new ArrayList<String>();
        //for(int j = 0; j < ss.length; j++)
         //   more_detail.add(new String(ss[j]));

        mListView = (ListView) findViewById(R.id.more);
       // ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ss);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.moredetail, ss);

        mListView.setAdapter(arrayAdapter);




    }


    @Override
    public void onBackPressed() {
        if(isPurchase == true){
            Intent intent = new Intent();
            intent.setClass(detail.this, MainActivity.class);
            TextView tv = (TextView) findViewById(R.id.name);
            TextView tv1 = (TextView) findViewById(R.id.price);
            intent.putExtra("name", tv.getText());
            intent.putExtra("price", tv1.getText());
            setResult(1, intent);
            finish();//进行跳转窗口，如果去掉这句，那就不能回去主界面
        }
        else{
            Intent intent = new Intent();
            intent.setClass(detail.this, MainActivity.class);
            finish();
        }


        super.onBackPressed();
    }
}
