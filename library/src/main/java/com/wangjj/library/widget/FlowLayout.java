package com.wangjj.library.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.wangjj.library.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by wangjianjun on 16/12/5.
 */

public class FlowLayout extends ViewGroup{
    private final String TAG = "FlowLayout";
    private int mMaxLines = -1; // -1 不限制行数
    private int mLines; // 总行数
    private int mGravity;
    private static final int LEFT = -1;
    private static final int CENTER = 0;
    private static final int RIGHT = 1;
    private List<Integer> mLineWidthList; // 所有行 宽度集合
    private List<Integer> mLineHeightList; //所有行 高度集合
    private List<List<View>> mLineViewList; //所有行 所有View的集合

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FlowLayout);
        mGravity = a.getInt(R.styleable.FlowLayout_flowlayout_gravity, LEFT);
        mMaxLines = a.getInt(R.styleable.FlowLayout_flowlayout_max_lines, -1);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i(TAG, "onMeasure===");
        final int widthSize = MeasureSpec.getSize(widthMeasureSpec); // 测量行宽
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int heightSize = MeasureSpec.getSize(heightMeasureSpec); // 测量行高
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int lineWidth = 0;
        int lineHeight = 0;
        int totalHeight = 0;
        int maxWidth = 0;
        mLineWidthList = new ArrayList<>();
        mLineHeightList = new ArrayList<>();
        mLineViewList = new ArrayList<>();
        List<View> lineViews = new ArrayList<>(); // 单行View集合

        int childCount = getChildCount();
        if(childCount > 0){
            mLines = 1;
        }else{
            mLines = 0;
        }
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            if(childView.getVisibility() == GONE){ //不可见时跳出，如果到了最后一个，要最后记录一次
                if(i == childCount - 1){
                    totalHeight += lineHeight;
                    mLineWidthList.add(lineWidth);
                    mLineHeightList.add(lineHeight);
                    mLineViewList.add(lineViews);
                }
                continue;
            }
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams childLayoutParams = (MarginLayoutParams) childView.getLayoutParams();
            int childWidth = childView.getMeasuredWidth() + childLayoutParams.leftMargin+ childLayoutParams.rightMargin;
            int childHeight = childView.getMeasuredHeight() + childLayoutParams.topMargin+ childLayoutParams.bottomMargin;
            if(lineWidth + childWidth > widthSize - getPaddingLeft() - getPaddingRight()){ //换行
                Log.i(TAG, "lineHeight: "+lineHeight);
                //记录当行宽度，当行高度，当行所有view，总行数，总高度
                mLineWidthList.add(lineWidth);
                mLineHeightList.add(lineHeight);
                mLineViewList.add(lineViews);
                mLines++;
                totalHeight += lineHeight;
                // 重置变量
                lineWidth = 0;
                lineHeight = 0;
                lineViews = new ArrayList<>();
                // 达到最大行后跳出
                if(mMaxLines != -1 &&  mLines > mMaxLines){
                    break;
                }
            }
            lineWidth += childWidth;
            maxWidth = Math.max(lineWidth, maxWidth);
            lineHeight = Math.max(lineHeight, childHeight);
            lineViews.add(childView);
            // 最后一个view 单独处理
            if(i == childCount - 1){
                totalHeight += lineHeight;
                Log.i(TAG, "lineHeight: "+lineHeight);
                mLineWidthList.add(lineWidth);
                mLineHeightList.add(lineHeight);
                mLineViewList.add(lineViews);
            }
        }
        final int finalWidth = widthMode == MeasureSpec.EXACTLY ? widthSize : maxWidth + getPaddingLeft() + getPaddingRight(); // 精确值模式取行宽, 否则取算出来的宽度
        final int finalHeight = heightMode == MeasureSpec.EXACTLY ? heightSize : totalHeight + getPaddingTop() + getPaddingBottom(); // 精确值模式取行高, 否则取算出来的高度
        setMeasuredDimension(finalWidth, finalHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.i(TAG, "onLayout===");
        final int widthSize = getWidth();
        final int heightSize = getHeight();
        Log.i(TAG, "widthSize: " + widthSize +", heightSize:"+heightSize);

        int lines = mLineViewList.size();
        int left = getPaddingLeft();
        int top = getPaddingTop();
        for (int i = 0; i < lines; i++) {
            List<View> lineViews = mLineViewList.get(i);
            int lineWidth = mLineWidthList.get(i);
            // 根据gravity 算出左边距
            switch (mGravity){
                case LEFT:
                    left = getPaddingLeft();
                    break;
                case CENTER:
                    left = (widthSize-getPaddingLeft()- getPaddingRight()- lineWidth)/2 + getPaddingLeft();
                    break;
                case RIGHT:
                    left = widthSize - lineWidth - getPaddingRight();
                    break;
            }
            // 根据测量的宽度排列当行
            int lineCount = lineViews.size();
            for (int j = 0; j < lineCount; j++) {
                View childView = lineViews.get(j);
                MarginLayoutParams childLayoutParams = (MarginLayoutParams) childView.getLayoutParams();
                int childWeight = childView.getMeasuredWidth()+ childLayoutParams.leftMargin + childLayoutParams.rightMargin;
                int cl = left+ childLayoutParams.leftMargin;
                int ct = top + childLayoutParams.topMargin;
                int cr = cl + childView.getMeasuredWidth();
                int cb = ct + childView.getMeasuredHeight();
                childView.layout(cl, ct, cr, cb);
                left += childWeight;
                Log.i(TAG, "cl: " + cl + ", ct:"+ct+ ", cr:"+cr+ ", cb:"+cb);

            }
            top += mLineHeightList.get(i);

        }

    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
