package com.example.vincentho.lab3;

/**
 * Created by Vincent Ho on 2017/10/23 0023.
 */
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;


import java.util.List;

public class mAdapter<item>  extends RecyclerView.Adapter<mViewHolder> {
        protected Context mContext;
        protected int mLayoutId;//list中每一个项的layout
        protected List<item> mDatas;//整个recyclerView的全部数据
        protected LayoutInflater mInflater;
        private OnItemClickListener mOnItemClickListener=null;
        private AdapterView.OnItemLongClickListener mOnItemLongClickListener = null;

    public mAdapter(Context context, int layoutId, List<item> datas)
    {
        mContext = context;
        mLayoutId = layoutId;
        mDatas = datas;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public mViewHolder onCreateViewHolder(final ViewGroup parent, int viewType)
    {
        mViewHolder viewHolder = mViewHolder.get(mContext, parent, mLayoutId);
        return viewHolder;
    }

    public void convert(mViewHolder holder, item t){

    }

    @Override
    public void onBindViewHolder(final mViewHolder holder, int position)
    {
        convert(holder, mDatas.get(position));
        if(mOnItemClickListener != null)
        {
            holder.itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    mOnItemClickListener.onClick(holder.itemView, holder.getAdapterPosition());

                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View v){
                    return mOnItemClickListener.onLongClick(holder.itemView, holder.getAdapterPosition());

                }
            });
        }
    }

    public int getItemCount()
    {
        return mDatas.size();
    }

    public interface OnItemClickListener{
        void onClick(View parent, int position);
        boolean onLongClick(View parent, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        this.mOnItemClickListener = onItemClickListener;
    }


}
