package com.keydom.ih_common.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;


import java.util.List;

/**
 * @Name：com.kentra.yxyz.view
 * @Description：描述信息
 * @Author：song
 * @Date：18/11/6 下午3:43
 * 修改人：xusong
 * 修改时间：18/11/6 下午3:43
 */
public class PageRecyclerView extends RecyclerView {

    private Context mContext = null;

    private PageAdapter myAdapter = null;

    private int shortestDistance;
    private float downX = 0;
    private float slideDistance = 0;
    private float scrollX = 0;

    private int spanRow = 1;
    private int spanColumn = 4;
    private int totalPage = -1;
    private int currentPage = -1;

    AutoGridLayoutManager autoGridLayoutManager = null;
    private int pageMargin = 0;

    private PageIndicatorView mIndicatorView = null;

    public PageRecyclerView(Context context) {
        this(context, null);
    }

    public PageRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PageRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        defaultInit(context);
    }

    // 默认初始化
    private void defaultInit(Context context) {
        this.mContext = context;
        autoGridLayoutManager = new AutoGridLayoutManager(
                mContext, spanRow, AutoGridLayoutManager.HORIZONTAL, false);
        setLayoutManager(autoGridLayoutManager);
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    /**
     * 设置行数和每页列数
     *
     * @param spanRow    行数，<=0表示使用默认的行数
     * @param spanColumn 每页列数，<=0表示使用默认每页列数
     */
    public void setPageSize(int spanRow, int spanColumn) {
        this.spanRow = spanRow <= 0 ? this.spanRow : spanRow;
        this.spanColumn = spanColumn <= 0 ? this.spanColumn : spanColumn;
        setLayoutManager(new AutoGridLayoutManager(
                mContext, this.spanRow, AutoGridLayoutManager.HORIZONTAL, false));
    }

    /**
     * 设置页间距
     *
     * @param pageMargin 间距(px)
     */
    public void setPageMargin(int pageMargin) {
        this.pageMargin = pageMargin;
    }

    /**
     * 设置指示器
     *
     * @param indicatorView 指示器布局
     */
    public void setIndicator(PageIndicatorView indicatorView) {
        this.mIndicatorView = indicatorView;
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        shortestDistance = getMeasuredWidth() / 10;
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        this.myAdapter = (PageAdapter) adapter;
    }

    // 更新页码指示器和相关数据
    private void update() {
        int temp = ((int) Math.ceil(myAdapter.dataList.size() / (double) (spanRow * spanColumn)));
        if (temp != totalPage) {
            mIndicatorView.initIndicator(temp);
            if (temp < totalPage && currentPage == totalPage) {
                currentPage = temp;
                smoothScrollBy(-getWidth(), 0);
            }
            mIndicatorView.setSelectedPage(currentPage - 1);
            totalPage = temp;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = ev.getX();
                break;
            default:
        }

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (currentPage == totalPage && downX - event.getX() > 0) {
                    return true;
                }
                break;
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                slideDistance = event.getX() - downX;
                if (Math.abs(slideDistance) > shortestDistance) {
                    if (slideDistance > 0) {
                        currentPage = currentPage == 1 ? 1 : currentPage - 1;
                    } else {
                        currentPage = currentPage == totalPage ? totalPage : currentPage + 1;
                    }
                    mIndicatorView.setSelectedPage(currentPage - 1);
                }

                smoothScrollBy((int) ((currentPage - 1) * getWidth() - scrollX), 0);
                return true;
            default:
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void onScrolled(int dx, int dy) {
        scrollX += dx;
        super.onScrolled(dx, dy);
    }

    /**
     * 数据适配器
     */
    public class PageAdapter extends Adapter<ViewHolder> {

        private List<?> dataList = null;
        private CallBack mCallBack = null;
        private int itemWidth = 0;
        private int screenWithoutPaddingWidth = 0;
        private int itemCount = 0;


        /**
         * @param data
         * @param callBack
         * @param dimenResId 必须填写，左右padding
         */
        public PageAdapter(int dimenResId, List<?> data, CallBack callBack) {
            this.dataList = data;
            this.mCallBack = callBack;
            itemCount = dataList.size() + spanRow * spanColumn;
            WindowManager wm = (WindowManager) mContext
                    .getSystemService(Context.WINDOW_SERVICE);
            int width = wm.getDefaultDisplay().getWidth();
            int padding = getResources().getDimensionPixelSize(dimenResId);
            screenWithoutPaddingWidth = width - padding * 2;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (itemWidth <= 0) {
                itemWidth = (screenWithoutPaddingWidth - pageMargin * 2) / spanColumn;
            }

            ViewHolder holder = mCallBack.onCreateViewHolder(parent, viewType);

            holder.itemView.measure(0, 0);
            holder.itemView.getLayoutParams().width = itemWidth;
            holder.itemView.getLayoutParams().height = holder.itemView.getMeasuredHeight();

            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (totalPage == -1) {
                getPage(position);
            }
            if (spanColumn == 1) {
                holder.itemView.getLayoutParams().width = itemWidth + pageMargin * 2;
                holder.itemView.setPadding(pageMargin, 0, pageMargin, 0);
            } else {
                int m = position % (spanRow * spanColumn);
                if (m < spanRow) {
                    holder.itemView.getLayoutParams().width = itemWidth + pageMargin;
                    holder.itemView.setPadding(pageMargin, 0, 0, 0);
                } else if (m >= spanRow * spanColumn - spanRow) {
                    holder.itemView.getLayoutParams().width = itemWidth + pageMargin;
                    holder.itemView.setPadding(0, 0, pageMargin, 0);
                } else {
                    holder.itemView.getLayoutParams().width = itemWidth;
                    holder.itemView.setPadding(0, 0, 0, 0);
                }
            }

            if (position < dataList.size()) {
                holder.itemView.setVisibility(View.VISIBLE);
                mCallBack.onBindViewHolder(holder, position);
            } else {
                holder.itemView.setVisibility(View.INVISIBLE);
            }

        }


        private void getPage(int position) {
            float t = (float) (position + 1) / (spanRow * spanColumn);
            currentPage = (int) Math.ceil(t);
            update();
        }

        @Override
        public int getItemCount() {
            return itemCount;
        }

        /**
         * 删除Item
         *
         * @param position 位置
         */
        public void remove(int position) {
            if (position < dataList.size()) {
                dataList.remove(position);
                itemCount--;
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, currentPage * spanRow * spanColumn);
                update();
            }
        }

    }

    public interface CallBack {

        /**
         * 创建VieHolder
         *
         * @param parent
         * @param viewType
         */
        ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

        /**
         * 绑定数据到ViewHolder
         *
         * @param holder
         * @param position
         */
        void onBindViewHolder(ViewHolder holder, int position);

    }

}
