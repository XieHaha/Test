package com.keydom.mianren.ih_patient.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.keydom.ih_common.view.CircleImageView;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.bean.HospitalAreaInfo;
import com.keydom.mianren.ih_patient.callback.GeneralCallback;

import java.util.ArrayList;
import java.util.List;
/**
 * 医院地区选择弹框适配器
 */
public class HospitalAreaPopupWindowAdapter extends RecyclerView.Adapter<HospitalAreaPopupWindowAdapter.VH>{
    private Context context;
    private List<HospitalAreaInfo> hospitalAreaInfoList=new ArrayList<>();
    private GeneralCallback.hospitalAreaAdapterCallBack hospitalAreaAdapterCallBack;
    /**
     * 构造方法
     */
    public HospitalAreaPopupWindowAdapter(Context context, List<HospitalAreaInfo> hospitalAreaInfoList,GeneralCallback.hospitalAreaAdapterCallBack hospitalAreaAdapterCallBack) {
        this.context = context;
        this.hospitalAreaInfoList = hospitalAreaInfoList;
        this.hospitalAreaAdapterCallBack=hospitalAreaAdapterCallBack;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_hospital_area_layout, parent, false);
        return new HospitalAreaPopupWindowAdapter.VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, final int position) {
        holder.order_hospital_name_name.setText(hospitalAreaInfoList.get(position).getName());
        holder.order_hospital_label_name.setText(hospitalAreaInfoList.get(position).getLevel()+" | "+hospitalAreaInfoList.get(position).getNature());
        holder.order_hospital_address_tv.setText(hospitalAreaInfoList.get(position).getAddress());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(hospitalAreaAdapterCallBack!=null){
                    hospitalAreaAdapterCallBack.commitInfo(hospitalAreaInfoList.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return hospitalAreaInfoList.size();
    }

    public class VH extends RecyclerView.ViewHolder{
        private TextView order_hospital_name_name,order_hospital_label_name,order_hospital_address_tv;
        private CircleImageView order_hospital_img;
        public VH(View v) {
            super(v);
            order_hospital_name_name=v.findViewById(R.id.order_hospital_name_name);
            order_hospital_label_name=v.findViewById(R.id.order_hospital_label_name);
            order_hospital_address_tv=v.findViewById(R.id.order_hospital_address_tv);
            order_hospital_img=v.findViewById(R.id.order_hospital_img);
        }
    }
}
