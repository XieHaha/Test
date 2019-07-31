package com.keydom.ih_patient.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keydom.ih_common.view.CircleImageView;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.bean.MedicalCardInfo;
import com.keydom.ih_patient.callback.GeneralCallback;

import java.util.ArrayList;
import java.util.List;
/**
 * 挂号适配器
 */
public class RegistrationCardAdapter extends  RecyclerView.Adapter<RegistrationCardAdapter.VH>{
    private Context context;
    private List<MedicalCardInfo> cardList=new ArrayList<>();
    //选中角标
    private int selectPosition=0;
    private GeneralCallback.SelectCardListener selectCardListener;
    /**
     * 构造方法
     */
    public RegistrationCardAdapter(Context context, List<MedicalCardInfo> cardList,GeneralCallback.SelectCardListener selectCardListener) {
        this.context = context;
        this.cardList = cardList;
        this.selectCardListener=selectCardListener;
    }

    @NonNull
    @Override
    public RegistrationCardAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.registration_card_item, parent, false);
        return new RegistrationCardAdapter.VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final RegistrationCardAdapter.VH holder, final int position) {
        if(selectPosition==0){
            selectCardListener.getSelectedCard(cardList.get(0));
        }
        holder.order_doc_name.setText(cardList.get(position).getName());
        holder.order_card_num.setText("".equals(cardList.get(position).getEntCardNumber())||cardList.get(position).getEntCardNumber()==null?"就诊卡号："+cardList.get(position).getEleCardNumber():"就诊卡号："+cardList.get(position).getEntCardNumber());
        if(selectPosition==position){
            holder.item_layout.setBackgroundColor(Color.parseColor("#F8FFFB"));
            holder.select_bg_layout.setBackgroundResource(R.mipmap.card_selected_bg);
            holder.card_selected_img.setImageResource(R.mipmap.card_selected_icon);
        } else {
            holder.item_layout.setBackgroundColor(context.getResources().getColor(R.color.primary_color));
            holder.select_bg_layout.setBackgroundResource(R.mipmap.card_unselect_bg);
            holder.card_selected_img.setImageResource(R.mipmap.card_unselect_icon);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.item_layout.setBackgroundColor(Color.parseColor("#F8FFFB"));
                holder.select_bg_layout.setBackgroundResource(R.mipmap.card_selected_bg);
                holder.card_selected_img.setImageResource(R.mipmap.card_selected_icon);
                selectPosition=position;
                notifyDataSetChanged();
                if(selectCardListener!=null){
                    selectCardListener.getSelectedCard(cardList.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    public class VH extends RecyclerView.ViewHolder{
        private TextView order_doc_name,order_card_num  ;
        private ImageView card_selected_img;
        private RelativeLayout select_bg_layout,item_layout;
        private CircleImageView order_doctor_img;
        public VH(View v) {
            super(v);
            order_doc_name=v.findViewById(R.id.order_doc_name);
            order_card_num=v.findViewById(R.id.order_card_num);
            order_doctor_img=v.findViewById(R.id.order_doctor_img);
            card_selected_img=v.findViewById(R.id.card_selected_img);
            select_bg_layout=v.findViewById(R.id.select_bg_layout);
            item_layout=v.findViewById(R.id.item_layout);
        }
    }
}
