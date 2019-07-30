package com.keydom.ih_patient.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.order_examination.OrderExaminationActivity;
import com.keydom.ih_patient.bean.ExaminationInfo;

import java.util.List;
/**
 * 订单适配器
 */
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.VH>  {
    private OrderExaminationActivity context;
    private List<ExaminationInfo> dataList ;
    /**
     * 构造方法
     */
    public OrderAdapter(OrderExaminationActivity context,List<ExaminationInfo> dataList) {
        this.context = context;
        this.dataList=dataList;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_exa_order_item, parent, false);
        return new OrderAdapter.VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.exa_name.setText(dataList.get(position).getProjectName());
        holder.exa_detail_tv.setText(dataList.get(position).getName()+"\n"+dataList.get(position).getSubclassName()+"\n"+dataList.get(position).getAppointmentTime()+" "+dataList.get(position).getCheckTime()+" \n"+dataList.get(position).getNumber()+" \n"+dataList.get(position).getHospitalAreaName()+" \n"+dataList.get(position).getCheckSite());

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class VH extends RecyclerView.ViewHolder{
        private TextView exa_detail_tv,exa_name;

        public VH(View v) {
            super(v);
            exa_name=v.findViewById(R.id.exa_name);
            exa_detail_tv=v.findViewById(R.id.exa_detail_tv);
        }
    }
}
