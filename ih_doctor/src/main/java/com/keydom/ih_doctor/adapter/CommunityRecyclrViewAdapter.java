package com.keydom.ih_doctor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.keydom.ih_common.activity.ArticleDetailActivity;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.ApiService;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.CalculateTimeUtils;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_common.utils.CostomToastUtils;
import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.ih_doctor.MyApplication;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.bean.ArticleListBean;
import com.keydom.ih_doctor.constant.Const;
import com.keydom.ih_doctor.m_interface.SingleClick;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;

/**
 * @Name：com.kentra.yxyz.adapter
 * @Description：社区文章适配器
 * @Author：song
 * @Date：18/11/6 下午6:52
 * 修改人：xusong
 * 修改时间：18/11/6 下午6:52
 */
public class CommunityRecyclrViewAdapter extends BaseEmptyAdapter<ArticleListBean> {


    public CommunityRecyclrViewAdapter(List<ArticleListBean> mDatas, Context context) {
        super(mDatas, context);
    }

    @Override
    public RecyclerView.ViewHolder createMyViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.community_trends_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void bindMyViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((CommunityRecyclrViewAdapter.ViewHolder) holder).bind(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView articleTagTv;
        public TextView communityAuthorName;
        public TextView communityItemTitle;
        public TextView communityItemDec;
        public TextView readedAmount;
        public TextView attentionAmount;
        public TextView commentAmount;
        public ImageView attentionSelect;
        public ImageView communityItemImg;
        public ImageView communityAuthorIcon;


        public ViewHolder(View itemView) {
            super(itemView);
            articleTagTv = (TextView) itemView.findViewById(R.id.article_tag);
            communityAuthorName = (TextView) itemView.findViewById(R.id.community_author_name);
            communityItemTitle = (TextView) itemView.findViewById(R.id.community_item_title);
            communityItemDec = (TextView) itemView.findViewById(R.id.community_item_dec);
            readedAmount = (TextView) itemView.findViewById(R.id.readed_amount);
            attentionAmount = (TextView) itemView.findViewById(R.id.attention_amount);
            commentAmount = (TextView) itemView.findViewById(R.id.comment_amount);
            attentionSelect = (ImageView) itemView.findViewById(R.id.attention_select);
            communityItemImg = (ImageView) itemView.findViewById(R.id.community_item_img);
            communityAuthorIcon = (ImageView) itemView.findViewById(R.id.community_author_icon);
        }

        public void bind(final int position) {
            articleTagTv.setText(mDatas.get(position).getArticleType() == 1 ? "专家看法" : "成长指南");
            communityAuthorName.setText(mDatas.get(position).getSubmiter());
            communityItemTitle.setText(mDatas.get(position).getTitle());
            communityItemDec.setText(mDatas.get(position).getSummary());
            readedAmount.setText(String.valueOf(mDatas.get(position).getReadQuantity()));
            attentionAmount.setText(String.valueOf(mDatas.get(position).getLikeQuantity()));
            commentAmount.setText(String.valueOf(mDatas.get(position).getCommentQuantity()));
            GlideUtils.load(communityItemImg, Const.IMAGE_HOST + CommonUtils.getIcon(mDatas.get(position).getArticleImage()), 0, 0, false, null);
            GlideUtils.load(communityAuthorIcon, Const.IMAGE_HOST + mDatas.get(position).getDoctorImage(), 0, 0, false, null);
            if (mDatas.get(position).getIsLike() == 1) {
                attentionSelect.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.attention_sel));
            } else {
                attentionSelect.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.attention_nol));
            }
            attentionSelect.setOnClickListener(new View.OnClickListener() {
                @SingleClick(1000)
                @Override
                public void onClick(View v) {
                    if (CalculateTimeUtils.isFastClick(2000)) {
                        return;
                    }
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("userId", MyApplication.userInfo.getId());
                    map.put("id", mDatas.get(position).getId());
                    map.put("isLike", (mDatas.get(position).getIsLike() == 1 ? 0 : 1));
                    map.put("doctorImage", MyApplication.userInfo.getAvatar());
                    ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ApiService.class).likeArticle(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<String>() {
                        @Override
                        public void requestComplete(@Nullable String res) {
                            mDatas.get(position).setIsLike(mDatas.get(position).getIsLike() == 1 ? 0 : 1);
                            if (mDatas.get(position).getIsLike() == 1) {
                                attentionSelect.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.attention_sel));
                                CostomToastUtils.getInstance().ToastMessage(mContext, "点赞成功");
                                mDatas.get(position).setLikeQuantity(mDatas.get(position).getLikeQuantity() + 1);
                                attentionAmount.setText(String.valueOf(mDatas.get(position).getLikeQuantity()));
                            } else {
                                attentionSelect.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.attention_nol));
                                CostomToastUtils.getInstance().ToastMessage(mContext, "取消点赞成功");
                                if (mDatas.get(position).getLikeQuantity() > 0) {
                                    mDatas.get(position).setLikeQuantity(mDatas.get(position).getLikeQuantity() - 1);
                                    attentionAmount.setText(String.valueOf(mDatas.get(position).getLikeQuantity()));
                                }


                            }
                        }

                        @Override
                        public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                            return super.requestError(exception, code, msg);
                        }
                    });
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @SingleClick(1000)
                @Override
                public void onClick(View v) {
                    ArticleDetailActivity.startArticle(mContext, mDatas.get(position).getId(), MyApplication.userInfo.getId(), MyApplication.userInfo.getName(), MyApplication.userInfo.getAvatar(),true);
                }
            });
        }
    }
}
