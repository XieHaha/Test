package com.keydom.ih_patient.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.keydom.ih_patient.R;
import com.keydom.ih_patient.bean.DateInfo;
import com.keydom.ih_patient.callback.GeneralCallback;
import com.orhanobut.logger.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
/**
 * 订单预约时间适配器
 */
public class OrderExtDateAdapter extends RecyclerView.Adapter<OrderExtDateAdapter.VH>{
    private Context context;
    //选中角标
    private int selectPosition=0;
    //是否是首次加载
    private boolean isInit=true;
    private List<DateInfo> dateList;
    private GeneralCallback.dateAdapterCallBack adapterCallBack;
    /**
     * 构造方法
     */
    public OrderExtDateAdapter(Context context,List<DateInfo> dateList,GeneralCallback.dateAdapterCallBack adapterCallBack) {
        this.context = context;
        this.dateList=dateList;
        this.adapterCallBack=adapterCallBack;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_exa_date_item, parent, false);
        return new OrderExtDateAdapter.VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final VH holder, final int position) {
        Logger.e("onBindViewHolder"+position);
        String dateStr=dateList.get(position).getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date=sdf.parse(dateStr);
            SimpleDateFormat sdfChange = new SimpleDateFormat("MM/dd");
            dateStr=sdfChange.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.date_tv.setText(dateStr);
        holder.week_tv.setText(dateList.get(position).getWeek());
        if(selectPosition==position){
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
                selectPosition=position;
                notifyDataSetChanged();
                if(adapterCallBack!=null){
                    adapterCallBack.getSelectedDate(position);
                }
            }
        });
        if(isInit){
            if(adapterCallBack!=null){
                if(dateList!=null&&dateList.size()!=0){
                    adapterCallBack.getSelectedDate(0);
                    isInit=false;
                }
            }
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

    public class VH extends RecyclerView.ViewHolder{
        private TextView date_tv,week_tv;
        private LinearLayout date_layout;
        public VH(View v) {
            super(v);
            date_tv=v.findViewById(R.id.date_tv);
            week_tv=v.findViewById(R.id.week_tv);
            date_layout=v.findViewById(R.id.date_layout);
        }
    }
    public void defaultQuery(){
        if(adapterCallBack!=null){
            adapterCallBack.getSelectedDate(0);
        }
    }
    public void refreshInit(){
        selectPosition=0;
        isInit=true;
    }
}
