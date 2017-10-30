package com.example.vincentho.lab3;

/**
 * Created by Vincent Ho on 2017/10/23 0023.
 */



public class item {
    private String name;
    private String price;
    private String info;
    item(String n, String p, String i)
    {
        this.name = n;
        this.price = p;
        this.info = i;
    }
    String getName()
    {
        return name;
    }
    String getPrice()
    {
        return price;
    }
    String getInfo()
    {
        return info;
    }
}
