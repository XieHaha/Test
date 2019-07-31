package com.keydom.ih_doctor.adapter;

import android.content.Context;
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
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.ih_doctor.MyApplication;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.issue_information.NotificationListActivity;
import com.keydom.ih_doctor.bean.NotificationBean;
import com.keydom.ih_doctor.m_interface.SingleClick;

import java.util.List;

/**
 * @Name：com.kentra.yxyz.adapter
 * @Description：通知公告适配器
 * @Author：song
 * @Date：18/11/6 下午6:52
 * 修改人：xusong
 * 修改时间：18/11/6 下午6:52
 */
public class NotificationRecyclrViewAdapter extends BaseEmptyAdapter<NotificationBean> {


    public NotificationRecyclrViewAdapter(Context context, List<NotificationBean> data) {
        super(data, context);
    }


    @Override
    public RecyclerView.ViewHolder createMyViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.notification_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void bindMyViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((NotificationRecyclrViewAdapter.ViewHolder) holder).bind(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView notificationName, notificationTime, notificationDec, notificationTag, feedbacktv;
        public ImageView notificationIcon;
        public Button delete,update;
        public RelativeLayout notification_main_layout;

        public ViewHolder(View itemView) {
            super(itemView);
            notificationName = itemView.findViewById(R.id.notification_name);
            notificationTime = itemView.findViewById(R.id.notification_time);
            notificationDec = itemView.findViewById(R.id.notification_dec);
            notificationIcon = itemView.findViewById(R.id.notification_icon);
            notificationTag = itemView.findViewById(R.id.notification_tag);
            feedbacktv = itemView.findViewById(R.id.feedback_tv);
            delete = itemView.findViewById(R.id.delete);
            update=itemView.findViewById(R.id.update);
            notification_main_layout = itemView.findViewById(R.id.notification_main_layout);
        }

        public void bind(final int position) {

            if (mDatas.get(position).isMine()){
                delete.setVisibility(View.VISIBLE);
                update.setVisibility(View.VISIBLE);
            } else{
                delete.setVisibility(View.GONE);
                update.setVisibility(View.GONE);
            }
            if(mDatas.get(position).getAuditResult() == 2)
                update.setVisibility(View.VISIBLE);
            else
                update.setVisibility(View.GONE);



            if (mDatas.get(position).getNoticeType() == 0) {
                notificationName.setText("医院通知");
                notificationIcon.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.notification_hospital));
            } else {
                notificationName.setText("专科通知");
                notificationIcon.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.notification_nomal));
            }

            if (mDatas.get(position).getAuditResult() == 0) {
                notificationTag.setVisibility(View.VISIBLE);
                notificationTag.setText("审核中");
                notificationTag.setBackground(mContext.getResources().getDrawable(R.drawable.audit_working));
                notificationTag.setTextColor(mContext.getResources().getColor(R.color.font_audit_working_color));
                feedbacktv.setVisibility(View.GONE);
            } else if (mDatas.get(position).getAuditResult() == 1) {
                notificationTag.setVisibility(View.GONE);
                feedbacktv.setVisibility(View.GONE);
            } else {
                notificationTag.setVisibility(View.VISIBLE);
                notificationTag.setText("未通过");
                notificationTag.setBackground(mContext.getResources().getDrawable(R.drawable.audit_stop));
                notificationTag.setTextColor(mContext.getResources().getColor(R.color.font_audit_stop_color));
                if (mDatas.get(position).getAuditOpinion() == null || "".equals(mDatas.get(position).getAuditOpinion())) {
                    feedbacktv.setVisibility(View.GONE);
                } else {
                    feedbacktv.setVisibility(View.VISIBLE);
                    feedbacktv.setText(mDatas.get(position).getAuditOpinion());
                }
            }
            notificationTime.setText(mDatas.get(position).getSubmitTime());
            notificationDec.setText(mDatas.get(position).getTitle());
            LinearLayout.LayoutParams deleteLayoutParams= (LinearLayout.LayoutParams) delete.getLayoutParams();
            LinearLayout.LayoutParams updateLayoutParams= (LinearLayout.LayoutParams) update.getLayoutParams();
            int w = View.MeasureSpec.makeMeasureSpec(0,
                    View.MeasureSpec.UNSPECIFIED);
            int h = View.MeasureSpec.makeMeasureSpec(0,
                    View.MeasureSpec.UNSPECIFIED);
            notification_main_layout.measure(w, h);
            int height=notification_main_layout.getMeasuredHeight();
            deleteLayoutParams.height=height;
            updateLayoutParams.height=height;
            delete.setLayoutParams(deleteLayoutParams);
            update.setLayoutParams(updateLayoutParams);
            notification_main_layout.setOnClickListener(new View.OnClickListener() {
                @SingleClick(1000)
                @Override
                public void onClick(View v) {
                    ArticleDetailActivity.startNotification(mContext, mDatas.get(position).getId(), MyApplication.userInfo.getHospitalId());
                }
            });
            delete.setOnClickListener(new View.OnClickListener() {
                @SingleClick(1000)
                @Override
                public void onClick(View view) {
                    new GeneralDialog(mContext, "确认要删除该条通知公告？", new GeneralDialog.OnCloseListener() {
                        @Override
                        public void onCommit() {
                            ((NotificationListActivity) mContext).todoNotification(position, 1);

                        }
                    }).setTitle("提示").setPositiveButton("确认").show();
                }
            });
            update.setOnClickListener(new View.OnClickListener() {
                @SingleClick(1000)
                @Override
                public void onClick(View view) {
                    ((NotificationListActivity) mContext).todoNotification(position, 0);
                }
            });
        }
    }


}
