package com.keydom.mianren.ih_patient.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.keydom.ih_common.view.CircleImageView;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.order_examination.OrderExaminationActivity;
import com.keydom.mianren.ih_patient.bean.MedicalCardInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 就诊卡弹框适配器
 */
public class CardPopupWindowAdapter extends  RecyclerView.Adapter<CardPopupWindowAdapter.VH>{
    private OrderExaminationActivity context;
    private List<MedicalCardInfo> cardList=new ArrayList<>();

    /**
     * 构造方法
     */
    public CardPopupWindowAdapter(OrderExaminationActivity context, List<MedicalCardInfo> cardList) {
        this.context = context;
        this.cardList = cardList;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_card_tem, parent, false);
        return new CardPopupWindowAdapter.VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, final int position) {
        holder.order_doc_name.setText(cardList.get(position).getName());
        holder.order_card_num.setText("".equals(cardList.get(position).getEntCardNumber())||cardList.get(position).getEntCardNumber()==null?"就诊卡号："+cardList.get(position).getEleCardNumber():"就诊卡号："+cardList.get(position).getEntCardNumber());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.saveSelectCard(cardList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    public class VH extends RecyclerView.ViewHolder{
        private TextView order_doc_name,order_card_num  ;
        private CircleImageView order_doctor_img;
        public VH(View v) {
            super(v);
            order_doc_name=v.findViewById(R.id.order_doc_name);
            order_card_num=v.findViewById(R.id.order_card_num);
            order_doctor_img=v.findViewById(R.id.order_doctor_img);
        }
    }
}
