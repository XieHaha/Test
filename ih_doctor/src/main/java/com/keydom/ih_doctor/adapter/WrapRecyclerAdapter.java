package com.keydom.ih_doctor.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keydom.ih_common.activity.ArticleDetailActivity;
import com.keydom.ih_common.utils.CalculateTimeUtils;
import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.ih_doctor.MyApplication;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.bean.ArticleListBean;
import com.keydom.ih_doctor.constant.Const;
import com.keydom.ih_doctor.m_interface.SingleClick;
import com.makeramen.roundedimageview.RoundedImageView;

/**
 * @Name：com.keydom.ih_doctor.adapter
 * @Description：带头部的适配器
 * @Author：song
 * @Date：18/11/19 上午9:46
 * 修改人：xusong
 * 修改时间：18/11/19 上午9:46
 */
public class WrapRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final static String TAG = "WrapRecyclerAdapter";
    private SparseArray<View> mHeaderViews;
    private SparseArray<View> mFooterViews;
    private static int BASE_ITEM_TYPE_HEADER = 10000000;
    private static int BASE_ITEM_TYPE_FOOTER = 20000000;
    private RecyclerView.Adapter mAdapter;
    private ArticleListBean topArticle;
    private Context context;


    public WrapRecyclerAdapter(Context context, RecyclerView.Adapter adapter) {
        this.mAdapter = adapter;
        mHeaderViews = new SparseArray<>();
        mFooterViews = new SparseArray<>();
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (isHeaderViewType(viewType)) {
            View headerView = mHeaderViews.get(viewType);
            return createHeaderFooterViewHolder(headerView);
        }

        if (isFooterViewType(viewType)) {
            View footerView = mFooterViews.get(viewType);
            return createHeaderFooterViewHolder(footerView);
        }
        return mAdapter.onCreateViewHolder(parent, viewType);
    }

    private boolean isFooterViewType(int viewType) {
        int position = mFooterViews.indexOfKey(viewType);
        return position >= 0;
    }

    /**
     * 创建头部或者底部的ViewHolder
     */
    private RecyclerView.ViewHolder createHeaderFooterViewHolder(View view) {
        return new HeadViewHolder(view);
    }

    /**
     * 是不是头部类型
     */
    private boolean isHeaderViewType(int viewType) {
        int position = mHeaderViews.indexOfKey(viewType);
        return position >= 0;
    }

    public void setTopData(ArticleListBean article) {
        this.topArticle = article;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isHeaderPosition(position) || isFooterPosition(position)) {
            if (topArticle == null) {
                return;
            }
            String author = "";
            String hospitalName = "";
            if (topArticle.getSubmiter().length() > 8) {
                author = topArticle.getSubmiter().substring(0, 2) + "..." + topArticle.getSubmiter().substring(topArticle.getSubmiter().length() - 2, topArticle.getSubmiter().length());
            } else {
                author = topArticle.getSubmiter();
            }
            if (topArticle.getHospitalName().length() > 8) {
                hospitalName = topArticle.getHospitalName().substring(0, 5) + "..." + topArticle.getHospitalName().substring(topArticle.getHospitalName().length() - 2, topArticle.getHospitalName().length());
            } else {
                hospitalName = topArticle.getHospitalName();
            }
            ((HeadViewHolder) holder).headAutorTv.setText(hospitalName + ":" + author + "•" + CalculateTimeUtils.CalculateTime(topArticle.getSubmitTime()));
            ((HeadViewHolder) holder).headTitleTv.setText(topArticle.getTitle());
            ((HeadViewHolder) holder).headTagTv.setText("置顶");
            String[] strArray = topArticle.getArticleImage().split(",");
            if (strArray != null)
                GlideUtils.load(((HeadViewHolder) holder).headImgIv, Const.IMAGE_HOST + strArray[0], 0, 0, false, null);
            ((HeadViewHolder) holder).headItemRl.setOnClickListener(new View.OnClickListener() {
                @SingleClick(1000)
                @Override
                public void onClick(View v) {
                    ArticleDetailActivity.startArticle(context, topArticle.getId(), MyApplication.userInfo.getId(), MyApplication.userInfo.getName(), MyApplication.userInfo.getAvatar(), true);
                }
            });
            return;
        }
        position = position - mHeaderViews.size();
        mAdapter.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderPosition(position)) {
            return mHeaderViews.keyAt(position);
        }
        if (isFooterPosition(position)) {
            position = position - mHeaderViews.size() - mAdapter.getItemCount();
            return mFooterViews.keyAt(position);
        }
        position = position - mHeaderViews.size();
        return mAdapter.getItemViewType(position);
    }

    /**
     * 是不是底部位置
     */
    private boolean isFooterPosition(int position) {
        return position >= (mHeaderViews.size() + mAdapter.getItemCount());
    }

    /**
     * 是不是头部位置
     */
    private boolean isHeaderPosition(int position) {
        return position < mHeaderViews.size();
    }

    @Override
    public int getItemCount() {
        return mAdapter.getItemCount() + mHeaderViews.size() + mFooterViews.size();
    }

    /**
     * 获取列表的Adapter
     */
    private RecyclerView.Adapter getAdapter() {
        return mAdapter;
    }

    public void addHeaderView(View view) {
        int position = mHeaderViews.indexOfValue(view);
        if (position < 0) {
            mHeaderViews.put(BASE_ITEM_TYPE_HEADER++, view);
        }
        notifyDataSetChanged();
    }

    public void addFooterView(View view) {
        int position = mFooterViews.indexOfValue(view);
        if (position < 0) {
            mFooterViews.put(BASE_ITEM_TYPE_FOOTER++, view);
        }
        notifyDataSetChanged();
    }

    public void removeHeaderView(View view) {
        int index = mHeaderViews.indexOfValue(view);
        if (index < 0) return;
        mHeaderViews.removeAt(index);
        notifyDataSetChanged();
    }

    public void removeFooterView(View view) {
        int index = mFooterViews.indexOfValue(view);
        if (index < 0) return;
        mFooterViews.removeAt(index);
        notifyDataSetChanged();
    }

    /**
     * 解决GridLayoutManager添加头部和底部不占用一行的问题
     *
     * @param recycler
     * @version 1.0
     */
    public void adjustSpanSize(RecyclerView recycler) {
        if (recycler.getLayoutManager() instanceof GridLayoutManager) {
            final GridLayoutManager layoutManager = (GridLayoutManager) recycler.getLayoutManager();
            layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    boolean isHeaderOrFooter =
                            isHeaderPosition(position) || isFooterPosition(position);
                    return isHeaderOrFooter ? layoutManager.getSpanCount() : 1;
                }
            });
        }
    }

    public class HeadViewHolder extends RecyclerView.ViewHolder {
        public TextView headTitleTv;
        public TextView headAutorTv;
        public TextView headTagTv;
        public RoundedImageView headImgIv;
        public RelativeLayout headItemRl;

        public HeadViewHolder(View itemView) {
            super(itemView);
            headTitleTv = (TextView) itemView.findViewById(R.id.community_title);
            headTagTv = (TextView) itemView.findViewById(R.id.community_tag);
            headAutorTv = (TextView) itemView.findViewById(R.id.author);
            headImgIv = (RoundedImageView) itemView.findViewById(R.id.community_img);
            headItemRl = (RelativeLayout) itemView.findViewById(R.id.head_item_rl);

        }
    }


}