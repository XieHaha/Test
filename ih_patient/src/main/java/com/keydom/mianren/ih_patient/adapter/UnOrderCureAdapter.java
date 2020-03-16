package com.keydom.mianren.ih_patient.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.order_hospital_cure.OrderHospitalCureActivity;
import com.keydom.mianren.ih_patient.bean.HospitalCureInfo;
import com.keydom.mianren.ih_patient.constant.Type;

import java.util.List;

/**
 * 检查病历适配器
 */
public class UnOrderCureAdapter extends RecyclerView.Adapter<UnOrderCureAdapter.VH> {
    private Context context;
    private List<HospitalCureInfo> dataList;
    /**
     * 构造方法
     */
    public UnOrderCureAdapter(Context context,List<HospitalCureInfo> dataList) {
        this.context = context;
        this.dataList=dataList;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_cure_list_item, parent, false);
        return new UnOrderCureAdapter.VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, final int position) {
        holder.order_cure_name_tv.setText(dataList.get(position).getName());
        switch (dataList.get(position).getState()){
            case 0:
                holder.add_order_cure_tv.setText("未申请");
               break;
            case 1:

            case 2:
                holder.add_order_cure_tv.setText("审核中");
                break;
            case 3:
                holder.add_order_cure_tv.setText("待确认");
                break;
            case 4:

            case 5:
                holder.add_order_cure_tv.setText("已确认");
                break;
            case 6:

            case 7:
                holder.add_order_cure_tv.setText("已取消");
                break;
        }

        holder.cure_department_tv.setText(dataList.get(position).getAdmissionDept());
        holder.cure_doctor_tv.setText(dataList.get(position).getIssuingDoctor());
        holder.cure_time_tv.setText(dataList.get(position).getIssuingTime());
        if(dataList.get(position).getIsOverdue()==0){
            holder.add_order_cure_tv.setVisibility(View.VISIBLE);
            holder.order_overdue_tv.setVisibility(View.GONE);
            holder.item_layout.setClickable(true);
            holder.item_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(dataList.get(position).getState()==0){
                        OrderHospitalCureActivity.start(context,Type.ORDERCUREAPPLY,dataList.get(position));
                    }else if(dataList.get(position).getState()==4||dataList.get(position).getState()==5||dataList.get(position).getState()==6||dataList.get(position).getState()==7){
                        OrderHospitalCureActivity.start(context,Type.ORDERCURESUCCESS,dataList.get(position));
                    }else if(dataList.get(position).getState()==3) {
                        OrderHospitalCureActivity.start(context, Type.ORDERCURECONFIRM, dataList.get(position));
                    }else {
                        OrderHospitalCureActivity.start(context, Type.ORDERCUREAUDIT, dataList.get(position));
                    }
                }
            });
        }else {
            holder.add_order_cure_tv.setVisibility(View.GONE);
            holder.order_overdue_tv.setVisibility(View.VISIBLE);
            holder.item_layout.setClickable(false);
        }

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class VH extends RecyclerView.ViewHolder{
        private TextView cure_department_tv,cure_doctor_tv,cure_time_tv,add_order_cure_tv,order_cure_name_tv,order_overdue_tv;
        private RelativeLayout item_layout;
        public VH(View v) {
            super(v);
            cure_department_tv=v.findViewById(R.id.cure_department_tv);
            cure_doctor_tv=v.findViewById(R.id.cure_doctor_tv);
            cure_time_tv=v.findViewById(R.id.cure_time_tv);
            add_order_cure_tv=v.findViewById(R.id.add_order_cure_tv);
            order_cure_name_tv=v.findViewById(R.id.order_cure_name_tv);
            order_overdue_tv=v.findViewById(R.id.order_overdue_tv);
            item_layout=v.findViewById(R.id.item_layout);
        }
    }
}
