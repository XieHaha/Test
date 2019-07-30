package com.keydom.ih_common.view;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * @Name：com.kentra.yxyz.view
 * @Description：自适应高度
 * @Author：song
 * @Date：18/11/6 下午3:42
 * 修改人：xusong
 * 修改时间：18/11/6 下午3:42
 */
public class AutoGridLayoutManager extends GridLayoutManager{

        private int measuredWidth = 0;
        private int measuredHeight = 0;

        public AutoGridLayoutManager(Context context, AttributeSet attrs,
                                     int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }

        public AutoGridLayoutManager(Context context, int spanCount) {
            super(context, spanCount);
        }

        public AutoGridLayoutManager(Context context, int spanCount,
                                     int orientation, boolean reverseLayout) {
            super(context, spanCount, orientation, reverseLayout);
        }

        @Override
        public void onMeasure(RecyclerView.Recycler recycler,
                              RecyclerView.State state, int widthSpec, int heightSpec) {
            if (measuredHeight <= 0) {
                View view=null;
                if(recycler.getScrapList()!=null&&recycler.getScrapList().size()>0)
                    view= recycler.getViewForPosition(0);
                if (view != null) {
                    measureChild(view, widthSpec, heightSpec);
                    measuredWidth = View.MeasureSpec.getSize(widthSpec);
                    measuredHeight = view.getMeasuredHeight() * getSpanCount();
                }
            }
            setMeasuredDimension(measuredWidth, measuredHeight);
        }

    }

