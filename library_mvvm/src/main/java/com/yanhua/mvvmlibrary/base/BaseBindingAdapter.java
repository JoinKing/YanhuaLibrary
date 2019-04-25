package com.yanhua.mvvmlibrary.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public abstract class BaseBindingAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    public List<T> mDataList;
    public Context mContext;

    public BaseBindingAdapter(Context context) {
        mContext = context;
        mDataList = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return onCreateVH(parent, viewType);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        onBindVH(holder, position);
    }


    public void onRefreshData(List<T> list) {
        if (mDataList != null) {
            mDataList.clear();
            mDataList.addAll(list);
            notifyDataSetChanged();
        }
    }

    public void onLoadMoreData(List<T> list) {
        if (mDataList != null && list != null) {
            mDataList.addAll(list);
            notifyDataSetChanged();
        }
    }


   public abstract VH onCreateVH(ViewGroup parent, int viewType);

   public abstract void onBindVH(VH holder, int position);
}
