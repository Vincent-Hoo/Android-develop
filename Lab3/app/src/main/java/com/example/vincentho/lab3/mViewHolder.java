package com.example.vincentho.lab3;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Vincent Ho on 2017/10/23 0023.
 */



public class mViewHolder extends RecyclerView.ViewHolder {
        private SparseArray<View> mViews; // 每一项View里面的子View，比如一些textView之类的
        private View mConvertView; // 每一项list_item的View，相当于每一项的layout

    public mViewHolder(Context context, View itemView, ViewGroup parent)
    {
        super(itemView);
        mConvertView = itemView;
        mViews = new SparseArray<View>();
    }

    public static mViewHolder get(Context context, ViewGroup parent, int layoutId)
    {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        mViewHolder holder = new mViewHolder(context, itemView, parent);
        return holder;
    }

    public <T extends View> T getView(int viewId){
        View view = mViews.get(viewId);
        if(view == null){
            // 创建view
            view = mConvertView.findViewById(viewId);
            // 将view存入mviews
            mViews.put(viewId, view);
        }
        return (T) view;
    }


}
