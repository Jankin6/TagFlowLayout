package com.wangjj.library.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.wangjj.library.utils.DisplayUtil;

/**
 * Created by wangjianjun on 16/12/6.
 */

public class TagViewLayout extends FlowLayout {
    private TagAdapter mTagAdapter;
    private onTagClickListener mOnTagClickListener;

    public void setOnTagClickListener(onTagClickListener onTagClickListener) {
        mOnTagClickListener = onTagClickListener;
    }

    public interface onTagClickListener{
        <T> void onTagClick(int position, T t);
    }

    public void setTagAdapter(TagAdapter tagAdapter) {
        mTagAdapter = tagAdapter;
        updateView();
    }

    public TagViewLayout(Context context) {
        super(context);
    }

    public TagViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TagViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void updateView() {
        removeAllViews();
        int childCount = mTagAdapter.getItemCount();
        for (int i = 0; i < childCount; i++) {
            View view = mTagAdapter.getView(i, this, mTagAdapter.getItem(i));
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if(layoutParams == null){
                ViewGroup.MarginLayoutParams layoutParamsNew = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                int margin = DisplayUtil.dip2px(getContext(), 5);
                layoutParamsNew.setMargins(margin, margin, margin, margin);
                addView(view, layoutParamsNew);
            }else{
                addView(view);
            }
            final int finalI = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mOnTagClickListener != null){
                        mOnTagClickListener.onTagClick(finalI, mTagAdapter.getItem(finalI));
                    }
                }
            });
        }
    }

}
