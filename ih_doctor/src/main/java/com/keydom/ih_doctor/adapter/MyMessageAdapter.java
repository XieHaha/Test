package com.keydom.ih_doctor.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.my_message.MyMessageActivity;
import com.keydom.ih_doctor.activity.my_message.NoticeDeatailActivity;
import com.keydom.ih_doctor.activity.nurse_service.NurseServiceOrderListActivity;
import com.keydom.ih_doctor.activity.online_diagnose.DiagnoseOrderListActivity;
import com.keydom.ih_doctor.bean.MessageBean;
import com.keydom.ih_doctor.m_interface.SingleClick;

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
       if (dataList.get(position) instanceof MessageBean) {
            MessageBean messageBean = (MessageBean) dataList.get(position);
            holder.massage_icon_img.setImageResource(R.mipmap.my_message_notification_icon);
            holder.message_type_tv.setText("系统通知");
            holder.message_title_tv.setText(messageBean.getTitle());
            holder.message_time_tv.setText(messageBean.getDateTime());
            if ("1".equals(messageBean.getIsRead())) {
                holder.isReadTv.setText("已读");
                holder.isReadTv.setTextColor(Color.parseColor("#BBBBBB"));
            } else if ("0".equals(messageBean.getIsRead())){
                holder.isReadTv.setText("未读");
                holder.isReadTv.setTextColor(Color.parseColor("#F83535"));
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @SingleClick(1000)
                @Override
                public void onClick(View view) {
//                    NoticeDeatailActivity.start(context, messageBean);
                    ((MyMessageActivity)context).getController().updateMessageState(messageBean.getId());
                    switch (messageBean.getInfoType()){
                        case "APPOINT_INQUIRY":
                            DiagnoseOrderListActivity.startDiagnose(context);
                            break;
                        case "ACCEPTCHANGE_APPOINT_INQUIRY":
                        case "CHANGE_APPOINT_INQUIRY":
                        case "INSPECT_APPOINT_INQUIRY":
                            DiagnoseOrderListActivity.startDiagnose(context);

                            break;
                        case "APPOINT_NURSING":
                            NurseServiceOrderListActivity.headNurseStart(context);

                            break;
                        case "ACCEPTUSER_APPOINT_NURSING":
                            NurseServiceOrderListActivity.commonNurseStart(context);

                            break;
                        case "ACCEPTCHANGE_APPOINT_NURSING":
                            NurseServiceOrderListActivity.commonNurseStart(context);
                            break;
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
