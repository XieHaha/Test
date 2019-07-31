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

import com.keydom.ih_common.constant.Const;
import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.ih_common.view.CircleImageView;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.bean.ManagerUserBean;
import com.keydom.ih_patient.callback.GeneralCallback;

import java.util.ArrayList;
import java.util.List;

public class ChoosePatientAdapter extends RecyclerView.Adapter<ChoosePatientAdapter.VH> {

    private Context context;
    private List<ManagerUserBean> patientList=new ArrayList<>();
    private int selectPosition=0;
    private GeneralCallback.SelectPatientListener selectPatientListener;
    public ChoosePatientAdapter(Context context, List<ManagerUserBean> patientList,GeneralCallback.SelectPatientListener selectPatientListener) {
        this.context = context;
        this.patientList = patientList;
        this.selectPatientListener=selectPatientListener;
    }
    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.choose_patient_item, parent, false);
        return new ChoosePatientAdapter.VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        if(selectPosition==0){
            selectPatientListener.getSelectedPatient(patientList.get(0));
        }
        holder.order_doc_name.setText(patientList.get(position).getName());
        holder.sex_tv.setText("0".equals(patientList.get(position).getSex())?"男":"女");
        GlideUtils.load(holder.order_doctor_img, Const.IMAGE_HOST+patientList.get(position).getUserImage(),0,0,true,null);
        holder.order_card_num.setText("".equals(patientList.get(position).getCardId())||patientList.get(position).getCardId()==null?"身份证号："+patientList.get(position).getCardId():"身份证号："+patientList.get(position).getCardId());
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
                if(selectPatientListener!=null){
                    selectPatientListener.getSelectedPatient(patientList.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return patientList.size();
    }

    public class VH extends RecyclerView.ViewHolder{
        private TextView order_doc_name,order_card_num,sex_tv  ;
        private ImageView card_selected_img;
        private RelativeLayout select_bg_layout,item_layout;
        private CircleImageView order_doctor_img;
        public VH(View v) {
            super(v);
            order_doc_name=v.findViewById(R.id.order_doc_name);
            order_card_num=v.findViewById(R.id.order_card_num);
            sex_tv=v.findViewById(R.id.sex_tv);
            order_doctor_img=v.findViewById(R.id.order_doctor_img);
            card_selected_img=v.findViewById(R.id.card_selected_img);
            select_bg_layout=v.findViewById(R.id.select_bg_layout);
            item_layout=v.findViewById(R.id.item_layout);
        }
    }
}
