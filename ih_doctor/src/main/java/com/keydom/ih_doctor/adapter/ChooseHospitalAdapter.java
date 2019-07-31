package com.keydom.ih_doctor.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.bean.HospitalAreaInfo;
import com.keydom.ih_doctor.m_interface.GeneralCallback;
import com.keydom.ih_doctor.m_interface.SingleClick;

import java.util.ArrayList;
import java.util.List;

public class ChooseHospitalAdapter extends RecyclerView.Adapter<ChooseHospitalAdapter.VH> {

    private Context context;
    private List<HospitalAreaInfo> hospitalList = new ArrayList<>();
    private int selectPosition = -1;
    private GeneralCallback.SelectHospitalListener selectHospitalListener;

    public ChooseHospitalAdapter(Context context, List<HospitalAreaInfo> cardList, GeneralCallback.SelectHospitalListener selectHospitalListener) {
        this.context = context;
        this.hospitalList = cardList;
        this.selectHospitalListener = selectHospitalListener;
    }

    @NonNull
    @Override
    public ChooseHospitalAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.choose_hospital_item, parent, false);
        return new ChooseHospitalAdapter.VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ChooseHospitalAdapter.VH holder, final int position) {
        holder.hospital_name_tv.setText(hospitalList.get(position).getName());
        if (selectPosition == position) {
            holder.hospital_name_tv.setTextColor(Color.parseColor("#3F98F7"));
            holder.lospital_select_img.setVisibility(View.VISIBLE);
        } else {
            holder.hospital_name_tv.setTextColor(Color.parseColor("#999999"));
            holder.lospital_select_img.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @SingleClick(1000)
            @Override
            public void onClick(View view) {
                holder.hospital_name_tv.setTextColor(Color.parseColor("#3F98F7"));
                holder.lospital_select_img.setVisibility(View.VISIBLE);
                selectPosition = position;
                notifyDataSetChanged();
                if (selectHospitalListener != null) {
                    selectHospitalListener.getSelectedHospital(hospitalList.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return hospitalList.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        private TextView hospital_name_tv;
        private ImageView lospital_select_img;

        public VH(View v) {
            super(v);
            hospital_name_tv = v.findViewById(R.id.hospital_name_tv);
            lospital_select_img = v.findViewById(R.id.lospital_select_img);
        }
    }
}
