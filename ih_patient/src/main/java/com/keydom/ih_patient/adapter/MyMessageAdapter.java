package com.keydom.ih_patient.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.my_message.MyMessageActivity;
import com.keydom.ih_patient.activity.my_message.NoticeDeatailActivity;
import com.keydom.ih_patient.activity.nursing_order.NursingOrderActivity;
import com.keydom.ih_patient.activity.online_diagnoses_order.OnlineDiagnonsesOrderActivity;
import com.keydom.ih_patient.activity.order_doctor_register.RegistrationRecordActivity;
import com.keydom.ih_patient.activity.order_examination.OrderExaminationActivity;
import com.keydom.ih_patient.activity.order_hospital_cure.OrderHospitalCureListActivity;
import com.keydom.ih_patient.activity.payment_records.PaymentRecordActivity;
import com.keydom.ih_patient.bean.IndexData;
import com.keydom.ih_patient.bean.MessageBean;

import java.util.List;

/**
 * 我的消息适配器
 */
public class MyMessageAdapter extends RecyclerView.Adapter<MyMessageAdapter.VH> {
    private Context context;
    private List<Object> dataList;

    /**
     * 构造方法
     */
    public MyMessageAdapter(Context context, List<Object> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_message_item, parent, false);
        return new MyMessageAdapter.VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        if (dataList.get(position) instanceof IndexData.NotificationsBean) {
            IndexData.NotificationsBean notificationsBean = (IndexData.NotificationsBean) dataList.get(position);
            holder.massage_icon_img.setImageResource(R.mipmap.my_message_notification_icon);
            holder.message_type_tv.setText("医院通知");
            holder.message_title_tv.setText(notificationsBean.getTitle());
            holder.message_time_tv.setText(notificationsBean.getSubmitTime());
            holder.isReadTv.setVisibility(View.GONE);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NoticeDeatailActivity.start(context, notificationsBean);
                }
            });
        } else if (dataList.get(position) instanceof MessageBean) {
            MessageBean messageBean = (MessageBean) dataList.get(position);
            holder.massage_icon_img.setImageResource(R.mipmap.my_message_notification_icon);
            holder.message_type_tv.setText("系统通知");
            holder.message_title_tv.setText(messageBean.getTitle());
            holder.message_time_tv.setText(messageBean.getDateTime());
            if ("1".equals(messageBean.getIsRead())) {
                holder.isReadTv.setText("已读");
                holder.isReadTv.setTextColor(Color.parseColor("#BBBBBB"));
            } else if ("0".equals(messageBean.getIsRead())) {
                holder.isReadTv.setText("未读");
                holder.isReadTv.setTextColor(Color.parseColor("#F83535"));
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (messageBean.getType() == 0)
                        NoticeDeatailActivity.start(context, messageBean);
                    else {
                        ((MyMessageActivity)context).getController().updateMessageState(messageBean.getId());
                        switch (messageBean.getInfoType()){
                            case "APPOINT_REGISTR":
                                //就诊提醒
                                RegistrationRecordActivity.start(context);
                                break;
                            case "APPOINT_CHECK_NOTICE":
                                //检查预约提醒
                                OrderExaminationActivity.start(context);
                                break;
                            case "ADJUST_APPOINT_CHECK":
                                //检查预约调整通知
                                NoticeDeatailActivity.start(context, messageBean);
                                break;
                            case "APPOINT_HOSPITAL":
                                //住院预约提醒
                                OrderHospitalCureListActivity.start(context);
                                break;
                            case "APPOINT_HOSPITAL_CHECKIN":
                                //住院报到通知
                                OrderHospitalCureListActivity.start(context);
                                break;
                            case "NOTIC_APPOINT_INQUIRY":
                                //在线问诊开始提醒
                                OnlineDiagnonsesOrderActivity.start(context,OnlineDiagnonsesOrderActivity.DIAGNOSESING);
                                break;
                            case "REFER_APPOINT_INQUIRY":
                                //在线问诊换诊通知
                                OnlineDiagnonsesOrderActivity.start(context,OnlineDiagnonsesOrderActivity.WAITEDIAGNOSES);
                                break;
                            case "CONVER_APPOINT_INQUIRY":
                                //在线问诊转诊通知
                                OnlineDiagnonsesOrderActivity.start(context,OnlineDiagnonsesOrderActivity.DIAGNOSESING);
                                break;
                            case "CLINIC_PAY":
                                //诊间缴费通知
                                PaymentRecordActivity.start(context);
                                break;
                            case "CHARGBACK_APPOINT_NURSING":
                                //护理服务退回通知
                                NursingOrderActivity.start(context,NursingOrderActivity.WAITEDNURSE);
                                break;
                        }
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        private TextView message_type_tv, message_title_tv, message_time_tv, isReadTv;
        private ImageView massage_icon_img;

        public VH(View v) {
            super(v);
            massage_icon_img = v.findViewById(R.id.massage_icon_img);
            message_type_tv = v.findViewById(R.id.message_type_tv);
            message_title_tv = v.findViewById(R.id.message_title_tv);
            message_time_tv = v.findViewById(R.id.message_time_tv);
            isReadTv = v.findViewById(R.id.isReadTv);
        }
    }
}
