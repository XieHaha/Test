package com.keydom.mianren.ih_patient.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.order_examination.ExaminationDateChooseActivity;
import com.keydom.mianren.ih_patient.bean.ExaminationInfo;
import com.keydom.mianren.ih_patient.bean.SoruInfo;
import com.keydom.mianren.ih_patient.net.OrderService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 预约订单适配器
 */
public class ExtOrderAdapter extends RecyclerView.Adapter<ExtOrderAdapter.VH>{
    private ExaminationDateChooseActivity context;
    private List<SoruInfo.ListBean> dataList;
    public ExtOrderAdapter(ExaminationDateChooseActivity context,List<SoruInfo.ListBean> dataList) {
        this.context = context;
        this.dataList=dataList;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.exa_order_item, parent, false);
        return new ExtOrderAdapter.VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, final int position) {
        holder.exa_time_tv.setText("".equals(dataList.get(position).getTimeInterval())||dataList.get(position).getTimeInterval()==null?"":dataList.get(position).getTimeInterval());
        holder.surplus_of_exa_num_tv.setText("本时段剩余号码位："+dataList.get(position).getSurplus()+"个");
        if(dataList.get(position).getSurplus()==0){
            holder.exa_order_tv.setText("已满");
            holder.exa_order_tv.setBackground(context.getResources().getDrawable(R.drawable.exa_no_num_bg));
        }else {
            holder.exa_order_tv.setText("预约");
            holder.exa_order_tv.setBackground(context.getResources().getDrawable(R.drawable.exa_has_num_bg));
            holder.exa_order_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new GeneralDialog(context, "确认预约？", new GeneralDialog.OnCloseListener() {
                        @Override
                        public void onCommit() {
                            commitOrder(context.getSelectedDate(),dataList.get(position).getTimeInterval(),context.getApplyNumber(),dataList.get(position).getId());
                        }
                    }).setTitle("提示").setPositiveButton("确认").show();
                }
            });
        }

    }

    private void commitOrder(String appointmentTime, String checkTime, String applyNumber, long id) {
        Map<String,Object> map=new HashMap<>();
        map.put("appointmentTime",appointmentTime);
        map.put("checkTime",checkTime);
        map.put("applyNumber",applyNumber);
        map.put("id",id);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(OrderService.class).inspectMakeAppointment(map), new HttpSubscriber<ExaminationInfo>(context) {
            @Override
            public void requestComplete(@Nullable ExaminationInfo data) {
                context.completeOrder(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                context.completeOrderFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class VH extends RecyclerView.ViewHolder{
        private TextView exa_time_tv,surplus_of_exa_num_tv,exa_order_tv;

        public VH(View v) {
            super(v);
            exa_time_tv=v.findViewById(R.id.exa_time_tv);
            surplus_of_exa_num_tv=v.findViewById(R.id.surplus_of_exa_num_tv);
            exa_order_tv=v.findViewById(R.id.exa_order_tv);
        }
    }

}
