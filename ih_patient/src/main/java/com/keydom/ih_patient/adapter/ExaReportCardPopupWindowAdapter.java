package com.keydom.ih_patient.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.inspection_report.InspectionReportActivity;
import com.keydom.ih_patient.bean.MedicalCardInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 检查预约弹框适配器
 */
public class ExaReportCardPopupWindowAdapter extends  RecyclerView.Adapter<ExaReportCardPopupWindowAdapter.VH>{
    private InspectionReportActivity context;
    private List<MedicalCardInfo> cardList=new ArrayList<>();

    /**
     * 构造方法
     */
    public ExaReportCardPopupWindowAdapter(InspectionReportActivity context, List<MedicalCardInfo> cardList) {
        this.context = context;
        this.cardList = cardList;
    }

    @NonNull
    @Override
    public ExaReportCardPopupWindowAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.exa_report_card_item, parent, false);
        return new ExaReportCardPopupWindowAdapter.VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ExaReportCardPopupWindowAdapter.VH holder, final int position) {
        holder.cardUserName.setText(cardList.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.saveSelectCard(cardList.get(position).getEleCardNumber(),cardList.get(position).getName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    public class VH extends RecyclerView.ViewHolder{
        private TextView cardUserName;
        public VH(View v) {
            super(v);
            cardUserName=v.findViewById(R.id.card_user_name_tv);

        }
    }
}
