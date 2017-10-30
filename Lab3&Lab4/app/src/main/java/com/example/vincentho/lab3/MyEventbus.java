package com.example.vincentho.lab3;

/**
 * Created by Vincent Ho on 2017/10/29 0029.
 */

public class MyEventbus {

    String msg;
    item I;
    MyEventbus(String m, item one){
        msg = m;
        I = one;
    }
    String getMsg()
    {
        return msg;
    }
    item getI()
    {
        return I;
    }
}
