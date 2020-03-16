package com.keydom.mianren.ih_patient.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.callback.GeneralCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择距离、价格适配器
 */
public class ChoosePharmacyPupAdapter extends RecyclerView.Adapter<ChoosePharmacyPupAdapter.VH> {

    private Context context;
    private List<String> datas = new ArrayList<>();
    private int selectPosition = -1;
    private GeneralCallback.SelectHospitalListener selectHospitalListener;
    private OnItemClickListener mOnItemClickListener;
    boolean selected = true;
    private String mContent=null;

    /**
     * 构造方法
     */
    public ChoosePharmacyPupAdapter(Context context, List<String> cardList, String mContent) {
        this.context = context;
        this.datas = cardList;
        this.selectHospitalListener = selectHospitalListener;
        this.mContent=mContent;
    }

    @NonNull
    @Override
    public ChoosePharmacyPupAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.choose_pharmacy_item, parent, false);
        return new ChoosePharmacyPupAdapter.VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ChoosePharmacyPupAdapter.VH holder, final int position) {
        holder.hospital_name_tv.setText(datas.get(position));
        if (datas.get(position).equals(mContent)) {
            holder.hospital_name_tv.setTextColor(Color.parseColor("#3F98F7"));
            holder.lospital_select_img.setVisibility(View.VISIBLE);
        } else {
            holder.hospital_name_tv.setTextColor(Color.parseColor("#999999"));
            holder.lospital_select_img.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onClick(position);
                }
                    holder.hospital_name_tv.setTextColor(Color.parseColor("#3F98F7"));
                    holder.lospital_select_img.setVisibility(View.VISIBLE);
                //   notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
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

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(int pos);
    }
}
