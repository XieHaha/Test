package com.keydom.ih_common.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.keydom.ih_common.R;
import com.keydom.ih_common.activity.ArticleDetailActivity;
import com.keydom.ih_common.bean.ArticleCommentBean;
import com.keydom.ih_common.bean.ReplyDetailBean;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.ApiService;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.CalculateTimeUtils;
import com.keydom.ih_common.utils.CostomToastUtils;
import com.keydom.ih_common.view.CircleImageView;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;


/**
 * Author: Moos
 * E-mail: moosphon@gmail.com
 * Date:  18/4/20.
 * Desc: 评论与回复列表的适配器
 */

public class CommentExpandAdapter extends BaseExpandableListAdapter {
    private static final String TAG = "CommentExpandAdapter";
    private List<ArticleCommentBean> commentBeanList;
    private List<ReplyDetailBean> replyBeanList;
    private Context context;
    private int pageIndex = 1;

    public CommentExpandAdapter(Context context, List<ArticleCommentBean> commentBeanList) {
        this.context = context;
        this.commentBeanList = commentBeanList;
    }

    @Override
    public int getGroupCount() {
        return commentBeanList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        if (commentBeanList.get(i).getByCriticsName() == null) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public Object getGroup(int i) {
        return commentBeanList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return commentBeanList.get(i);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return getCombinedChildId(groupPosition, childPosition);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpand, View convertView, ViewGroup viewGroup) {
        final GroupHolder groupHolder;
        final ArticleCommentBean bean = commentBeanList.get(groupPosition);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.comment_item_layout, viewGroup, false);
            groupHolder = new GroupHolder(convertView);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (GroupHolder) convertView.getTag();
        }
        Glide.with(context).load(bean.getCriticsImage()).into(groupHolder.logo);
        groupHolder.tv_name.setText(bean.getCriticsName());
        groupHolder.tv_time.setText(CalculateTimeUtils.CalculateTime(bean.getCommentTime()));
        groupHolder.tv_content.setText(bean.getMyCommentContext());
        groupHolder.like_amount.setText((bean.getCommentLike() > 0) ? String.valueOf(bean.getCommentLike()) : "");
        if (bean.getByCriticsName() != null && !bean.getByCriticsName().equals("")) {
            groupHolder.replyItemll.setVisibility(View.VISIBLE);
            groupHolder.reply_item_content.setText(bean.getByCriticsName() + ":" + bean.getByCommentsContext());
        } else {
            groupHolder.replyItemll.setVisibility(View.GONE);
        }
        final Integer isLike = bean.getIsLike();
        if (isLike != null && isLike > 0) {
            groupHolder.iv_like.setColorFilter(Color.parseColor("#3f98f7"));
        } else {
            groupHolder.iv_like.setColorFilter(Color.parseColor("#aaaaaa"));
        }
        groupHolder.iv_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean b, View convertView, ViewGroup viewGroup) {
        final ChildHolder childHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.comment_reply_item_layout, viewGroup, false);
            childHolder = new ChildHolder(convertView);
            convertView.setTag(childHolder);
        } else {
            childHolder = (ChildHolder) convertView.getTag();
        }
        childHolder.tv_content.setText(commentBeanList.get(groupPosition).getByCriticsName() + ":" + commentBeanList.get(groupPosition).getByCommentsContext());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    private class GroupHolder {
        private CircleImageView logo;
        private TextView tv_name, tv_content, tv_time, reply_item_content, like_amount;
        private ImageView iv_like;
        private LinearLayout replyItemll;

        public GroupHolder(View view) {
            logo = (CircleImageView) view.findViewById(R.id.comment_item_logo);
            tv_content = (TextView) view.findViewById(R.id.comment_item_content);
            tv_name = (TextView) view.findViewById(R.id.comment_item_userName);
            tv_time = (TextView) view.findViewById(R.id.comment_item_time);
            like_amount = (TextView) view.findViewById(R.id.like_amount);
            reply_item_content = (TextView) view.findViewById(R.id.reply_item_content);
            iv_like = (ImageView) view.findViewById(R.id.comment_item_like);
            replyItemll = (LinearLayout) view.findViewById(R.id.reply_item_ll);
        }
    }

    private class ChildHolder {
        private TextView tv_content;

        public ChildHolder(View view) {
            tv_content = (TextView) view.findViewById(R.id.reply_item_content);
        }
    }


}
