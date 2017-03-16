package com.wangjj.library.widget;

import android.view.View;

import java.util.List;

/**
 * Created by wangjianjun on 16/12/6.
 */

public abstract class TagAdapter<T>{
    private List<T> mData;

    public void setData(List<T> data) {
        mData = data;
    }

    public List<T> getData() {
        return mData;
    }

    public TagAdapter(List<T> data){
        this.mData = data;
    }

    public int getItemCount(){
        return mData == null ? 0 : mData.size();
    }

    public T getItem(int position){
        if(mData != null && mData.size() > 0){
            return mData.get(position);
        }
        return null;
    }

    public abstract View getView(int position, FlowLayout parent, T t);
}
