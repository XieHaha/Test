package com.keydom.ih_common.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.keydom.ih_common.R;
import com.keydom.ih_common.activity.ArticleDetailActivity;
import com.keydom.ih_common.bean.HealthArticleCommentBean;
import com.keydom.ih_common.constant.Const;
import com.keydom.ih_common.constant.Global;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.ApiService;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.CalculateTimeUtils;
import com.keydom.ih_common.utils.CostomToastUtils;
import com.keydom.ih_common.view.CircleImageView;
import com.keydom.ih_common.view.GeneralDialog;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

public class HealthCommentAdapter extends RecyclerView.Adapter<HealthCommentAdapter.ViewHolder> {
    private List<HealthArticleCommentBean> commentBeanList;
    private Context context;
    private long userId;
    private static GeneralDialog generalDialog;

    public HealthCommentAdapter(Context context, List<HealthArticleCommentBean> commentBeanList,long userId) {
        this.context = context;
        this.commentBeanList = commentBeanList;
        this.userId=userId;
    }

    @NonNull
    @Override
    public HealthCommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.comment_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HealthCommentAdapter.ViewHolder holder, final int position) {
        final HealthArticleCommentBean bean = commentBeanList.get(position);
        if(bean.getCriticsImage()!=null&&!"".equals(bean.getCriticsImage())){
            Glide.with(context).load(Const.IMAGE_HOST+bean.getCriticsImage()).into(holder.logo);
            holder.logo.setBackgroundResource(0);
        }else {
            holder.logo.setBackgroundResource(R.mipmap.login_user_name_icon);
        }
        holder.tv_name.setText(bean.getCriticsName());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = formatter.format(commentBeanList.get(position).getCommentTime());
        holder.tv_time.setText(CalculateTimeUtils.CalculateTime(time));
        holder.tv_content.setText(bean.getMyCommentContexxt());
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
        if (commentBeanList.get(position).getCriticsId()== userId) {
            holder.delete_tv.setVisibility(View.VISIBLE);
        }else {
            holder.delete_tv.setVisibility(View.GONE);

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ArticleDetailActivity) context).showHealthReplyDialog(position);
            }
        });

        holder.delete_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(generalDialog==null){
                    generalDialog=  new GeneralDialog(context, "确认要删除该条评论？", new GeneralDialog.OnCloseListener() {
                        @Override
                        public void onCommit() {
                            HashMap<String, Object> map = new HashMap<>();
                            map.put("id", commentBeanList.get(position).getId());
                            ((ArticleDetailActivity) context).getController().showLoading();
                            ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ApiService.class).delComment(map), new HttpSubscriber<String>(((ArticleDetailActivity) context).getController().getContext(), ((ArticleDetailActivity) context).getController().getDisposable(), false) {
                                @Override
                                public void requestComplete(@Nullable String data) {
                                    ((ArticleDetailActivity) context).getController().hideLoading();
                                    commentBeanList.remove(position);
                                    notifyDataSetChanged();
                                }

                                @Override
                                public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                                    ((ArticleDetailActivity) context).getController().hideLoading();
                                    Toast.makeText(context,"删除失败"+msg,Toast.LENGTH_SHORT).show();
                                    return super.requestError(exception, code, msg);
                                }
                            });
                        }
                    });
                    generalDialog.setTitle("提示").setPositiveButton("确认").show();
                }else {
                    generalDialog.setOnCloseListener(new GeneralDialog.OnCloseListener() {
                        @Override
                        public void onCommit() {
                            HashMap<String, Object> map = new HashMap<>();
                            map.put("id", commentBeanList.get(position).getId());
                            ((ArticleDetailActivity) context).getController().showLoading();
                            ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ApiService.class).delComment(map), new HttpSubscriber<String>(((ArticleDetailActivity) context).getController().getContext(), ((ArticleDetailActivity) context).getController().getDisposable(), false) {
                                @Override
                                public void requestComplete(@Nullable String data) {
                                    ((ArticleDetailActivity) context).getController().hideLoading();
                                    commentBeanList.remove(position);
                                    notifyDataSetChanged();
                                }

                                @Override
                                public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                                    ((ArticleDetailActivity) context).getController().hideLoading();
                                    Toast.makeText(context,"删除失败"+msg,Toast.LENGTH_SHORT).show();
                                    return super.requestError(exception, code, msg);
                                }
                            });

                        }
                    });
                    generalDialog.show();
                }

            }
        });
        holder.iv_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("id", bean.getId());
                map.put("iscLick", (isLike != null && isLike > 0) ? 0 : 1);
                map.put("registerUserId", Global.getUserId());
                ((ArticleDetailActivity) context).getController().showLoading();
                ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ApiService.class).updateCommentLike(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<String>(((ArticleDetailActivity) context).getController().getContext(), ((ArticleDetailActivity) context).getController().getDisposable(), false) {
                    @Override
                    public void requestComplete(@Nullable String data) {
                        CostomToastUtils.getInstance().ToastMessage(context, (isLike != null && isLike > 0) ? "取消点赞成功" : "点赞成功");
                        commentBeanList.get(position).setIsLike((isLike != null && isLike > 0) ? 0 : 1);
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
            delete_tv=itemView.findViewById(R.id.delete_tv);
        }
    }
    public void deletedDialog(){
        generalDialog=null;
    }

}
