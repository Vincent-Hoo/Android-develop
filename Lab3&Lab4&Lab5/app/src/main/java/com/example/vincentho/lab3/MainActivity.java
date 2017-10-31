package com.example.vincentho.lab3;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;
import java.util.Random;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.OvershootInLeftAnimator;

import static com.example.vincentho.lab3.R.id.parent;

public class MainActivity extends AppCompatActivity {

    private List<item> goods;
    private List<item> item_list;
    private RecyclerView mRecyclerView;
    private mAdapter<item> storeAdpter;
    private boolean flag;
    private mAdapter<item> shoplistAdpter;
    FloatingActionButton fabtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String[] name = new String[]{"Enchanted Forest", "Arla Milk", "Devondale Milk", "Kindle Oasis", "waitrose 早餐麦片", "Mcvitie's 饼干", "Ferrero Rocher", "Maltesers", "Lindt", "Borggreve"};
        final String[] price = new String[]{"¥ 5.00", "¥ 59.00", "¥ 79.00", "¥ 2399.00", "¥ 179.00", "¥ 14.00", "¥ 132.59", "¥ 141.43", "139.43", "28.90"};
        final String[] info = new String[] {"作者 Johanna Basford", "产地 德国", "产地 澳大利亚", "版本 8GB", "重量 2kg", "产地 英国", "重量 300g", "重量 118g", "重量 249g", "重量 640g"};;

        goods = new ArrayList<item>();
        for(int i = 0; i < name.length; i++)
            goods.add(new item(name[i], price[i], info[i]));

        item_list = new ArrayList<item>();
        item_list.add(new item("购物车", "价格", ""));


        mRecyclerView = (RecyclerView) findViewById(R.id.mainRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        storeAdpter = new mAdapter<item>(this, R.layout.mitem, goods){
            @Override
            public void convert(mViewHolder holder, item t) {

                TextView tv = holder.getView(R.id.itemname);
                tv.setText(t.getName());

                Button btn = holder.getView(R.id.icon);
                btn.setText(String.valueOf(t.getName().charAt(0)));
            }
        };

        shoplistAdpter = new mAdapter<item>(this, R.layout.shop_list_item, item_list){
            @Override
            public void convert(mViewHolder holder, item t) {

                TextView tv = holder.getView(R.id.shop_list_itemname);
                tv.setText(t.getName());

                TextView tv1 = holder.getView(R.id.shop_list_price);
                tv1.setText(t.getPrice());
                Button btn = holder.getView(R.id.shop_list_icon);
                btn.setText(String.valueOf(t.getName().charAt(0)));
            }
        };

        ScaleInAnimationAdapter animationAdapter = new ScaleInAnimationAdapter(storeAdpter);
        animationAdapter.setDuration(1000);
        mRecyclerView.setAdapter(animationAdapter);
        mRecyclerView.setItemAnimator(new OvershootInLeftAnimator());
        flag = true;

        storeAdpter.setOnItemClickListener(new mAdapter.OnItemClickListener() {
            @Override
            public void onClick(View parent, int position)
            {
                Intent intent = new Intent();
                intent.putExtra("name", name[position]);
                intent.putExtra("pos", position);
                intent.putExtra("price", price[position]);
                intent.putExtra("info", info[position]);
                intent.putExtra("source", "itemlist");
                intent.setClass(MainActivity.this, detail.class);
                MainActivity.this.startActivity(intent);
            }

            @Override
            public boolean onLongClick(View parent, final int position)
            {
                Toast.makeText(getApplicationContext(), "长按了第" + (position + 1) + "项", Toast.LENGTH_SHORT).show();
                goods.remove(position);
                storeAdpter.notifyDataSetChanged();
                return false;
            }
        });

        shoplistAdpter.setOnItemClickListener(new mAdapter.OnItemClickListener() {
            @Override
            public void onClick(View parent, int position)
            {
                Toast.makeText(getApplicationContext(), "按我干嘛", Toast.LENGTH_LONG).show();
                Intent intent = new Intent();
                intent.putExtra("name", item_list.get(position).getName());
                int pos = 0;
                for(int i = 0; i < goods.size(); i++){
                    if(goods.get(i).getPrice().equals(item_list.get(position).getPrice()) )
                        pos = i;
                }
                intent.putExtra("pos", pos);
                intent.putExtra("price", item_list.get(position).getPrice());
                intent.putExtra("info", item_list.get(position).getInfo());
                intent.putExtra("source", "carlist");
                intent.setClass(MainActivity.this, detail.class);
                MainActivity.this.startActivity(intent);
            }

            @Override
            public boolean onLongClick(View parent, final int position)
            {
                final AlertDialog.Builder removeitemDialog = new AlertDialog.Builder(MainActivity.this);
                removeitemDialog.setTitle("移除商品").setMessage("请问你想移除"+name[position]+"吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which)
                            {
                                item_list.remove(position);
                                shoplistAdpter.notifyDataSetChanged();
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which)
                    {
                        Toast.makeText(getApplicationContext(), "已取消", Toast.LENGTH_LONG).show();
                    }
                }).show();
                return false;
            }

        });



        fabtn = (FloatingActionButton) findViewById(R.id.shop_list);
        fabtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(flag == true){
                    mRecyclerView.setAdapter(shoplistAdpter);
                    shoplistAdpter.notifyDataSetChanged();
                    fabtn.setImageResource(R.drawable.mainpage);
                    flag = false;
                }
                else{
                    mRecyclerView.setAdapter(storeAdpter);
                    fabtn.setImageResource(R.drawable.shoplist);
                    flag = true;
                }
            }
        });

        /* lab4*/
        Random  random = new Random();
        int t = random.nextInt(10);
        Intent initial = new Intent("initiate");
        initial.putExtra("name", name[t]);
        initial.putExtra("pos", t);
        initial.putExtra("price", price[t]);
        initial.putExtra("info", info[t]);
        sendBroadcast(initial);


        EventBus.getDefault().register(this);

    }

    @Subscribe
    public void onEventMainThread(MyEventbus event) {
        String n = event.getI().getName();
        String p = event.getI().getPrice();
        item_list.add(new item(n, p, ""));
        shoplistAdpter.notifyDataSetChanged();
    }



    @Override
    protected void onDestroy(){
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBus
    }

    @Override
    public void onNewIntent(Intent intent){
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        Intent intent = getIntent();
        int page = intent.getIntExtra("flag", 0);
        if(page == 1){
            flag = true;
            mRecyclerView.setAdapter(storeAdpter);
        }
        else if(page == 2){
            flag = false;
            mRecyclerView.setAdapter(shoplistAdpter);
        }

    }

}
