package com.keydom.ih_patient.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.order_doctor_register.ChooseDoctorActivity;
import com.keydom.ih_patient.bean.DateInfo;
import com.orhanobut.logger.Logger;

import java.util.List;
/**
 * 医生预约时间适配器
 */
public class OrderDoctorDateAdapter extends RecyclerView.Adapter<OrderDoctorDateAdapter.VH> {
    private ChooseDoctorActivity context;
    //选中的角标
    private int selectPosition = 0;
    private boolean isInit = true;
    private List<DateInfo> dateList;
    /**
     * 构造方法
     */
    public OrderDoctorDateAdapter(ChooseDoctorActivity context, List<DateInfo> dateList) {
        this.context = context;
        this.dateList = dateList;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_exa_date_item, parent, false);
        return new OrderDoctorDateAdapter.VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final VH holder, final int position) {
        Logger.e("onBindViewHolder" + position);
        holder.date_tv.setText(dateList.get(position).getDate());
        holder.week_tv.setText(dateList.get(position).getWeek());
        if (selectPosition == position) {
            holder.date_layout.setBackgroundColor(context.getResources().getColor(R.color.exa_date_selected_bg));
            holder.date_tv.setTextColor(context.getResources().getColor(R.color.other_login_color));
            holder.week_tv.setTextColor(context.getResources().getColor(R.color.other_login_color));
        } else {
            holder.date_layout.setBackgroundColor(context.getResources().getColor(R.color.primary_color));
            holder.date_tv.setTextColor(context.getResources().getColor(R.color.fontColorDirection));
            holder.week_tv.setTextColor(context.getResources().getColor(R.color.fontColorDirection));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.date_layout.setBackgroundColor(context.getResources().getColor(R.color.exa_date_selected_bg));
                holder.date_tv.setTextColor(context.getResources().getColor(R.color.other_login_color));
                holder.week_tv.setTextColor(context.getResources().getColor(R.color.other_login_color));
                selectPosition = position;
                notifyDataSetChanged();
                //context.querySoruInfo(position);

            }
        });
        if (isInit) {
            //context.querySoruInfo(0);
            isInit = false;
        }
       /* holder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    holder.date_layout.setBackgroundColor(context.getResources().getColor(R.color.exa_date_selected_bg));
                    holder.date_tv.setTextColor(context.getResources().getColor(R.color.other_login_color));
                    holder.week_tv.setTextColor(context.getResources().getColor(R.color.other_login_color));
                }else {
                    holder.date_layout.setBackgroundColor(context.getResources().getColor(R.color.primary_color));
                    holder.date_tv.setTextColor(context.getResources().getColor(R.color.fontColorDirection));
                    holder.week_tv.setTextColor(context.getResources().getColor(R.color.fontColorDirection));
                }

            }
        });*/
    }

    @Override
    public int getItemCount() {
        return dateList.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        private TextView date_tv, week_tv;
        private LinearLayout date_layout;

        public VH(View v) {
            super(v);
            date_tv = v.findViewById(R.id.date_tv);
            week_tv = v.findViewById(R.id.week_tv);
            date_layout = v.findViewById(R.id.date_layout);
        }
    }
}
