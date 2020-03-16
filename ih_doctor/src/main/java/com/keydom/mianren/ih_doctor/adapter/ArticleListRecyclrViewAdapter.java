package com.keydom.mianren.ih_doctor.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keydom.ih_common.activity.ArticleDetailActivity;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.mianren.ih_doctor.MyApplication;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.personal.ArticleListActivity;
import com.keydom.mianren.ih_doctor.bean.ArticleItemBean;
import com.keydom.mianren.ih_doctor.constant.Const;
import com.keydom.mianren.ih_doctor.constant.TypeEnum;
import com.keydom.mianren.ih_doctor.m_interface.SingleClick;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.HashMap;
import java.util.List;

/**
 * @Name：com.kentra.yxyz.adapter
 * @Description：文章列表适配器
 * @Author：song
 * @Date：18/11/6 下午6:52
 * 修改人：xusong
 * 修改时间：18/11/6 下午6:52
 */
public class ArticleListRecyclrViewAdapter extends BaseEmptyAdapter<ArticleItemBean> {
    private TypeEnum type;
    private ArticleListActivity context;
    public ArticleListRecyclrViewAdapter(List<ArticleItemBean> mDatas, ArticleListActivity context, TypeEnum type) {
        super(mDatas, context);
        this.type=type;
        this.context=context;
    }


    @Override
    public RecyclerView.ViewHolder createMyViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.article_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void bindMyViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ArticleListRecyclrViewAdapter.ViewHolder) holder).bind(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView communityArticleTag;
        public TextView communityAuthorName;
        public TextView communityItemTitle;
        public TextView communityItemDec;
        public ImageView communityAuthorIcon;
        public RoundedImageView communityItemImg;
        public TextView articleAuditRes;
        public TextView feedbackTv;
        public Button delete,update;
        public LinearLayout article_main_layout;
        public RelativeLayout bottom_layout;
        private TextView article_comment_num_tv;
        private TextView remove_collect_article_tv;


        public ViewHolder(View itemView) {
            super(itemView);
            communityArticleTag = (TextView) itemView.findViewById(R.id.community_article_tag);
            communityAuthorName = (TextView) itemView.findViewById(R.id.community_author_name);
            communityItemTitle = (TextView) itemView.findViewById(R.id.community_item_title);
            communityItemDec = (TextView) itemView.findViewById(R.id.community_item_dec);
            articleAuditRes = (TextView) itemView.findViewById(R.id.article_audit_res);
            feedbackTv = (TextView) itemView.findViewById(R.id.feedback_tv);
            communityAuthorIcon = (ImageView) itemView.findViewById(R.id.community_author_icon);
            communityItemImg = (RoundedImageView) itemView.findViewById(R.id.community_item_img);
            delete = itemView.findViewById(R.id.delete);
            update=itemView.findViewById(R.id.update);
            article_main_layout = itemView.findViewById(R.id.article_main_layout);
            bottom_layout=itemView.findViewById(R.id.bottom_layout);
            article_comment_num_tv=itemView.findViewById(R.id.article_comment_num_tv);
            remove_collect_article_tv=itemView.findViewById(R.id.remove_collect_article_tv);


        }

        public void bind(final int position) {
            if(type==TypeEnum.MYCOLLECT){
                bottom_layout.setVisibility(View.VISIBLE);
                article_comment_num_tv.setText(mDatas.get(position).getCommentQuantity()+" 评论");
                remove_collect_article_tv.setOnClickListener(new View.OnClickListener() {
                    @SingleClick(1000)
                    @Override
                    public void onClick(View v) {
                        new GeneralDialog(context, "你确定要取消该文章的收藏吗？", new GeneralDialog.OnCloseListener() {
                            @Override
                            public void onCommit() {
                                HashMap<String, Object> map = new HashMap<>();
                                map.put("doctorId", MyApplication.userInfo.getId());
                                map.put("articleId", mDatas.get(position).getArticleId());
                                map.put("isCollect",0);
                                context.getController().removeCollect(map);
                            }
                        }).setTitle("提示").show();

                    }
                });
            }else {
                bottom_layout.setVisibility(View.GONE);
            }
            communityArticleTag.setText(mDatas.get(position).getArticleType() == 1 ? "专家看法" : "成长指南");
            communityAuthorName.setText(mDatas.get(position).getSubmiter());
            communityItemDec.setText(mDatas.get(position).getContent());
            communityItemTitle.setText(mDatas.get(position).getTitle());
            GlideUtils.load(communityItemImg, Const.IMAGE_HOST + CommonUtils.getIcon(mDatas.get(position).getImage()), 0, 0, false, null);
            GlideUtils.load(communityAuthorIcon, Const.IMAGE_HOST + mDatas.get(position).getAvatar(), 0, 0, false, null);
            article_main_layout.setOnClickListener(new View.OnClickListener() {
                @SingleClick(1000)
                @Override
                public void onClick(View v) {
                    ArticleDetailActivity.startArticle(mContext, mDatas.get(position).getArticleId(), MyApplication.userInfo.getId(), MyApplication.userInfo.getName(), MyApplication.userInfo.getAvatar(), mDatas.get(position).getAuditResult() == 1 ? true : false);
                }
            });

            if (mDatas.get(position).getAuditResult() == 0) {
                articleAuditRes.setVisibility(View.VISIBLE);
                articleAuditRes.setText("审核中");
                articleAuditRes.setBackground(mContext.getResources().getDrawable(R.drawable.audit_working));
                articleAuditRes.setTextColor(mContext.getResources().getColor(R.color.font_audit_working_color));
                feedbackTv.setVisibility(View.GONE);
            } else if (mDatas.get(position).getAuditResult() == 1) {
                articleAuditRes.setVisibility(View.GONE);
                feedbackTv.setVisibility(View.GONE);
            } else {
                articleAuditRes.setVisibility(View.VISIBLE);
                articleAuditRes.setText("未通过");
                articleAuditRes.setBackground(mContext.getResources().getDrawable(R.drawable.audit_stop));
                articleAuditRes.setTextColor(mContext.getResources().getColor(R.color.font_audit_stop_color));
                if (mDatas.get(position).getAuditOpinion() == null || "".equals(mDatas.get(position).getAuditOpinion())) {
                    feedbackTv.setVisibility(View.GONE);
                } else {
                    feedbackTv.setVisibility(View.VISIBLE);
                    feedbackTv.setText(mDatas.get(position).getAuditOpinion());
                }
            }
            if (((ArticleListActivity) mContext).getType() == TypeEnum.MYARTICLE){
                delete.setVisibility(View.VISIBLE);
                update.setVisibility(View.VISIBLE);
            } else{
                delete.setVisibility(View.GONE);
                update.setVisibility(View.GONE);
            }
            if(mDatas.get(position).getAuditResult() == 1 || mDatas.get(position).getAuditResult() == 0)
                update.setVisibility(View.GONE);
            else
                update.setVisibility(View.VISIBLE);

            LinearLayout.LayoutParams deleteLayoutParams= (LinearLayout.LayoutParams) delete.getLayoutParams();
            LinearLayout.LayoutParams updateLayoutParams= (LinearLayout.LayoutParams) update.getLayoutParams();
            int w = View.MeasureSpec.makeMeasureSpec(0,
                    View.MeasureSpec.UNSPECIFIED);
            int h = View.MeasureSpec.makeMeasureSpec(0,
                    View.MeasureSpec.UNSPECIFIED);
            article_main_layout.measure(w, h);
            int height=article_main_layout.getMeasuredHeight();
            deleteLayoutParams.height=height;
            updateLayoutParams.height=height;
            delete.setLayoutParams(deleteLayoutParams);
            update.setLayoutParams(updateLayoutParams);

            delete.setOnClickListener(new View.OnClickListener() {
                @SingleClick(1000)
                @Override
                public void onClick(View view) {
                    new GeneralDialog(mContext, "确认要删除该篇文章？", new GeneralDialog.OnCloseListener() {
                        @Override
                        public void onCommit() {
                            ((ArticleListActivity) mContext).todoArticle(position, 1);
                        }
                    }).setTitle("提示").setPositiveButton("确认").show();
                }
            });
            update.setOnClickListener(new View.OnClickListener() {
                @SingleClick(1000)
                @Override
                public void onClick(View view) {
                    ((ArticleListActivity) mContext).todoArticle(position, 0);

                }
            });
        }
    }
}
