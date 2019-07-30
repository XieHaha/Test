package com.keydom.ih_patient.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.keydom.ih_common.constant.Const;
import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.ih_common.view.CircleImageView;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.my_doctor_or_nurse.DoctorOrNurseDetailActivity;
import com.keydom.ih_patient.activity.online_diagnoses_search.DiagnoseSearchActivity;
import com.keydom.ih_patient.bean.RecommendDocAndNurBean;

import java.util.List;

/**
 * 问诊搜索适配器
 */
public class DiagnoseSearchAdapter  extends RecyclerView.Adapter<DiagnoseSearchAdapter.VH> {
    private  List<RecommendDocAndNurBean> recommendList;
    private DiagnoseSearchActivity context;
    private int ITEM_DOCTOR = 0, ITEM_NURSE = 1;
    private int type;

    /**
     * 构造方法
     */
    public DiagnoseSearchAdapter( int type,List<RecommendDocAndNurBean> recommendList, DiagnoseSearchActivity context) {
        this.type=type;
        this.recommendList = recommendList;
        this.context = context;
    }

    @NonNull
    @Override
    public DiagnoseSearchAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.register_search_item, parent, false);
        return new DiagnoseSearchAdapter.VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DiagnoseSearchAdapter.VH holder, int position) {
        holder.item_name_tv.setText(recommendList.get(position).getName());
        holder.item_dsc_tv.setText("科室："+recommendList.get(position).getDeptName()+" 擅长："+recommendList.get(position).getAdept());
        GlideUtils.load(holder.item_head_img, recommendList.get(position).getAvatar() == null ? "" : Const.IMAGE_HOST+recommendList.get(position).getAvatar(), 0, 0, false, null);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,DoctorOrNurseDetailActivity.class);
                intent.putExtra("type",type);
                intent.putExtra("doctorCode",recommendList.get(position).getUuid());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recommendList.size();
    }
    @Override
    public int getItemViewType(int position) {
        if (type==ITEM_DOCTOR) {
            return ITEM_DOCTOR;
        } else{
            return ITEM_NURSE;
        }
    }
    public class VH extends RecyclerView.ViewHolder{
        private TextView item_name_tv,item_dsc_tv;
        private CircleImageView item_head_img;

        public VH(View v) {
            super(v);
            item_name_tv=v.findViewById(R.id.item_name_tv);
            item_dsc_tv=v.findViewById(R.id.item_dsc_tv);
            item_head_img=v.findViewById(R.id.item_head_img);
        }
    }
}
