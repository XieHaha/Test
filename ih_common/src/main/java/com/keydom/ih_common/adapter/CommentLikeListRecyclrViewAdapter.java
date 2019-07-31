package com.keydom.ih_common.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.keydom.ih_common.R;
import com.keydom.ih_common.bean.ArticleLikeUserBean;
import com.keydom.ih_common.constant.Const;
import com.keydom.ih_common.view.CircleImageView;

import java.util.List;

/**
 * @Name：com.kentra.yxyz.adapter
 * @Description：描述信息
 * @Author：song
 * @Date：18/11/6 下午6:52
 * 修改人：xusong
 * 修改时间：18/11/6 下午6:52
 */
public class CommentLikeListRecyclrViewAdapter extends RecyclerView.Adapter<CommentLikeListRecyclrViewAdapter.ViewHolder> {


    private Context context;
    private List<ArticleLikeUserBean> commentBeanList;

    public CommentLikeListRecyclrViewAdapter(Context context, List<ArticleLikeUserBean> data) {
        this.context = context;
        this.commentBeanList = data;

    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.like_icon_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Glide.with(context).load(Const.IMAGE_HOST + commentBeanList.get(position).getDoctorImage()).into(holder.icon);
    }

    @Override
    public int getItemCount() {
        return commentBeanList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView icon;

        public ViewHolder(View itemView) {
            super(itemView);
            icon = (CircleImageView) itemView.findViewById(R.id.like_icon);

        }
    }
}
