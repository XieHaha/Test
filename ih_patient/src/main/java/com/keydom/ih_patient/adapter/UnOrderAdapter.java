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
 * 检查预约适配器
 */
public class UnOrderAdapter extends RecyclerView.Adapter<UnOrderAdapter.VH> {
    private OrderExaminationActivity context;
    private List<ExaminationInfo> dataList ;
    /**
     * 构造方法
     */
    public UnOrderAdapter(OrderExaminationActivity context,List<ExaminationInfo> dataList) {
        this.context = context;
        this.dataList=dataList;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_exa_unorder_item, parent, false);
        return new UnOrderAdapter.VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, final int position) {
        holder.unorder_exa_name_tv.setText(dataList.get(position).getProjectName());
        holder.unorder_exa_dsc_tv.setText("检查项目："+dataList.get(position).getSubclassName());
        holder.unorder_exa_order_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.toDoOrder(dataList.get(position).getInspectProjectId(),dataList.get(position).getApplyNumber());
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class VH extends RecyclerView.ViewHolder{
        private TextView unorder_exa_name_tv,unorder_exa_dsc_tv,unorder_exa_order_tv;

        public VH(View v) {
            super(v);
            unorder_exa_name_tv=v.findViewById(R.id.unorder_exa_name_tv);
            unorder_exa_dsc_tv=v.findViewById(R.id.unorder_exa_dsc_tv);
            unorder_exa_order_tv=v.findViewById(R.id.unorder_exa_order_tv);
        }
    }

}
