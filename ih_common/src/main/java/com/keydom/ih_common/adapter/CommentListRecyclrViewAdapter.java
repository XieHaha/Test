package com.keydom.ih_common.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.keydom.ih_common.R;
import com.keydom.ih_common.activity.ArticleDetailActivity;
import com.keydom.ih_common.bean.ArticleCommentBean;
import com.keydom.ih_common.constant.Const;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.ApiService;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.CalculateTimeUtils;
import com.keydom.ih_common.utils.CostomToastUtils;
import com.keydom.ih_common.utils.DialogCreator;
import com.keydom.ih_common.utils.SharePreferenceManager;
import com.keydom.ih_common.view.CircleImageView;
import com.keydom.ih_common.view.GeneralDialog;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;

/**
 * @Name：com.kentra.yxyz.adapter
 * @Description：描述信息
 * @Author：song
 * @Date：18/11/6 下午6:52
 * 修改人：xusong
 * 修改时间：18/11/6 下午6:52
 */
public class CommentListRecyclrViewAdapter extends RecyclerView.Adapter<CommentListRecyclrViewAdapter.ViewHolder> {


    private Context context;
    private List<ArticleCommentBean> commentBeanList;
    Dialog dialog;

    public CommentListRecyclrViewAdapter(Context context, List<ArticleCommentBean> data) {
        this.context = context;
        this.commentBeanList = data;

    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.comment_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final ArticleCommentBean bean = commentBeanList.get(position);
        Glide.with(context).load(Const.IMAGE_HOST + bean.getCriticsImage()).into(holder.logo);
        holder.tv_name.setText(bean.getCriticsName());
        holder.tv_time.setText(CalculateTimeUtils.getYMDTime(bean.getCommentTime()));
        holder.tv_content.setText(bean.getMyCommentContext());
        holder.like_amount.setText((bean.getCommentLike() > 0) ? String.valueOf(bean.getCommentLike()) : "");
        if (bean.getByCriticsName() != null && !bean.getByCriticsName().equals("")) {
            holder.replyItemll.setVisibility(View.VISIBLE);
            holder.reply_item_content.setText(bean.getByCriticsName() + ":" + bean.getByCommentsContext());
        } else {
            holder.replyItemll.setVisibility(View.GONE);
        }
        final Integer isLike = bean.getIsLike();
        if (isLike != null && isLike > 0) {
            holder.iv_like.setColorFilter(Color.parseColor("#3f98f7"));
        } else {
            holder.iv_like.setColorFilter(Color.parseColor("#aaaaaa"));
        }

        long id1 = commentBeanList.get(position).getCriticsId();
        long id2 = SharePreferenceManager.getId();
        if (commentBeanList.get(position).getCriticsId().longValue() == SharePreferenceManager.getId()) {
            holder.delete_tv.setVisibility(View.VISIBLE);
        }else {
            holder.delete_tv.setVisibility(View.GONE);

        }
        holder.mHeadGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent("com.keydom.ih_doctor.DoctorOrNurseDetailActivity");
//                intent.putExtra("doctorCode", bean.getCriticsCode());
//                ActivityUtils.startActivity(intent);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ArticleDetailActivity) context).showReplyDialog(position);
               /* if (commentBeanList.get(position).getCriticsId().longValue() == SharePreferenceManager.getId()) {
                    dialog = DialogCreator.createDelDialog(context, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            if (v.getId() == R.id.delete_ll) {
                                HashMap<String, Object> map = new HashMap<>();
                                map.put("articleCommentId", commentBeanList.get(position).getId());
                                map.put("articleId", commentBeanList.get(position).getArticleId());
                                ((ArticleDetailActivity) context).getController().showLoading();
                                ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ApiService.class).deleteArticleComment(map), new HttpSubscriber<String>(((ArticleDetailActivity) context).getController().getContext(), ((ArticleDetailActivity) context).getController().getDisposable(), false) {
                                    @Override
                                    public void requestComplete(@Nullable String data) {
                                        ((ArticleDetailActivity) context).getController().hideLoading();
                                        commentBeanList.remove(position);
                                        CommentListRecyclrViewAdapter.this.notifyDataSetChanged();
                                    }

                                    @Override
                                    public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                                        ((ArticleDetailActivity) context).getController().hideLoading();
                                        return super.requestError(exception, code, msg);
                                    }
                                });
                                dialog.hide();
                            } else if (v.getId() == R.id.update_ll) {
                                dialog.hide();
                            }
                        }
                    }, false);
                    dialog.show();
                } else {
                    ((ArticleDetailActivity) context).showReplyDialog(position);
                }*/

            }
        });
        holder.delete_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new GeneralDialog(context, "确认要删除该条评论？", new GeneralDialog.OnCloseListener() {
                    @Override
                    public void onCommit() {
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("articleCommentId", commentBeanList.get(position).getId());
                        map.put("articleId", commentBeanList.get(position).getArticleId());
                        ((ArticleDetailActivity) context).getController().showLoading();
                        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ApiService.class).deleteArticleComment(map), new HttpSubscriber<String>(((ArticleDetailActivity) context).getController().getContext(), ((ArticleDetailActivity) context).getController().getDisposable(), false) {
                            @Override
                            public void requestComplete(@Nullable String data) {
                                ((ArticleDetailActivity) context).getController().hideLoading();
                                commentBeanList.remove(position);
                                CommentListRecyclrViewAdapter.this.notifyDataSetChanged();
                            }

                            @Override
                            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                                ((ArticleDetailActivity) context).getController().hideLoading();
                                Toast.makeText(context,"删除失败"+msg,Toast.LENGTH_SHORT).show();
                                return super.requestError(exception, code, msg);
                            }
                        });
                    }
                }).setTitle("提示").setPositiveButton("确认").show();
            }
        });
       /* holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                long id1 = commentBeanList.get(position).getCriticsId();
                long id2 = SharePreferenceManager.getId();
                if (commentBeanList.get(position).getCriticsId().longValue() == SharePreferenceManager.getId()) {
                    dialog = DialogCreator.createDelDialog(context, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (v.getId() == R.id.delete_ll) {
                                HashMap<String, Object> map = new HashMap<>();
                                map.put("articleCommentId", commentBeanList.get(position).getId());
                                map.put("articleId", commentBeanList.get(position).getArticleId());
                                ((ArticleDetailActivity) context).getController().showLoading();
                                ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ApiService.class).deleteArticleComment(map), new HttpSubscriber<String>(((ArticleDetailActivity) context).getController().getContext(), ((ArticleDetailActivity) context).getController().getDisposable(), false) {
                                    @Override
                                    public void requestComplete(@Nullable String data) {
                                        ((ArticleDetailActivity) context).getController().hideLoading();
                                        commentBeanList.remove(position);
                                        CommentListRecyclrViewAdapter.this.notifyDataSetChanged();
                                    }

                                    @Override
                                    public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                                        ((ArticleDetailActivity) context).getController().hideLoading();
                                        return super.requestError(exception, code, msg);
                                    }
                                });
                                dialog.hide();
                            } else if (v.getId() == R.id.update_ll) {
                                dialog.hide();
                            }
                        }
                    }, false);
                    dialog.show();
                }
                return false;
            }
        });*/


        holder.iv_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CalculateTimeUtils.isFastClick(2000)) {
                    return;
                }
                HashMap<String, Object> map = ((ArticleDetailActivity) context).getCommentLikeMap();
                map.put("id", bean.getId());
                map.put("isLike", (isLike != null && isLike > 0) ? 0 : 1);
                ((ArticleDetailActivity) context).getController().showLoading();
                ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ApiService.class).updateArticleCommentLike(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<String>(((ArticleDetailActivity) context).getController().getContext(), ((ArticleDetailActivity) context).getController().getDisposable(), false) {
                    @Override
                    public void requestComplete(@Nullable String data) {
                        CostomToastUtils.getInstance().ToastMessage(context, (isLike != null && isLike > 0) ? "取消点赞成功" : "点赞成功");
                        bean.setIsLike((isLike != null && isLike > 0) ? 0 : 1);
                        bean.setCommentLike((isLike != null && isLike > 0) ? (bean.getCommentLike() - 1) : (bean.getCommentLike() + 1));
                        notifyDataSetChanged();
                        ((ArticleDetailActivity) context).getController().hideLoading();
                    }

                    @Override
                    public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                        CostomToastUtils.getInstance().ToastMessage(context, "点赞失败");
                        ((ArticleDetailActivity) context).getController().hideLoading();
                        return super.requestError(exception, code, msg);
                    }
                });


            }
        });


    }

    @Override
    public int getItemCount() {
        return commentBeanList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView logo;
        public TextView tv_name, tv_content, tv_time, reply_item_content, like_amount,delete_tv;
        public ImageView iv_like;
        public LinearLayout replyItemll;
        public FrameLayout mHeadGroup;

        public ViewHolder(View itemView) {
            super(itemView);
            logo = (CircleImageView) itemView.findViewById(R.id.comment_item_logo);
            tv_content = (TextView) itemView.findViewById(R.id.comment_item_content);
            tv_name = (TextView) itemView.findViewById(R.id.comment_item_userName);
            tv_time = (TextView) itemView.findViewById(R.id.comment_item_time);
            like_amount = (TextView) itemView.findViewById(R.id.like_amount);
            reply_item_content = (TextView) itemView.findViewById(R.id.reply_item_content);
            iv_like = (ImageView) itemView.findViewById(R.id.comment_item_like);
            replyItemll = (LinearLayout) itemView.findViewById(R.id.reply_item_ll);
            mHeadGroup = itemView.findViewById(R.id.head_group);
            delete_tv=itemView.findViewById(R.id.delete_tv);

        }
    }
}
