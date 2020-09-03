package com.keydom.mianren.ih_patient.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.keydom.ih_common.bean.DoctorInfo;
import com.keydom.ih_common.constant.Const;
import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.ih_common.view.CircleImageView;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.order_doctor_register.ChooseDoctorActivity;
import com.keydom.mianren.ih_patient.activity.order_doctor_register.DoctorIndexActivity;
import com.keydom.mianren.ih_patient.activity.order_doctor_register.RegisterSearchActivity;
import com.keydom.mianren.ih_patient.bean.DepartmentInfo;

import java.util.List;
/**
 * 注册医生搜索适配器
 */
public class RegisterDoctorSearchAdapter extends RecyclerView.Adapter<RegisterDoctorSearchAdapter.VH> {
    private List<Object> dataList;
    private RegisterSearchActivity context;
    //ITEM_DOCTOR 搜索医生 ITEM_DEPARTMENT 搜索科室和医生
    private int ITEM_DOCTOR = 1, ITEM_DEPARTMENT = 2;
    /**
     * 构造方法
     */
    public RegisterDoctorSearchAdapter(List<Object> dataList, RegisterSearchActivity context) {
        this.dataList = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.register_search_item, parent, false);
        return new RegisterDoctorSearchAdapter.VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        if(dataList.get(position) instanceof DoctorInfo){
            DoctorInfo doctorInfo= (DoctorInfo) dataList.get(position);
            holder.item_name_tv.setText(doctorInfo.getName());
            holder.item_dsc_tv.setText(doctorInfo.getAdept());
            GlideUtils.load(holder.item_head_img,Const.IMAGE_HOST+doctorInfo.getImage(),R.mipmap.test_doctor_head_icon,R.mipmap.test_doctor_head_icon,false,null);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    DoctorIndexActivity.start(context,doctorInfo);
                }
            });
        }else {
            DepartmentInfo departmentInfo= (DepartmentInfo) dataList.get(position);
            holder.item_name_tv.setText(departmentInfo.getName());
            holder.item_dsc_tv.setVisibility(View.INVISIBLE);
            holder.item_head_img.setVisibility(View.INVISIBLE);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ChooseDoctorActivity.start(context,departmentInfo.getHospitalAreaId(),departmentInfo.getHospitalAreaName(),Long.parseLong(departmentInfo.getId()),departmentInfo.getName(),null);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
    @Override
    public int getItemViewType(int position) {
        if (dataList.get(position) instanceof DoctorInfo) {
            return ITEM_DOCTOR;
        } else{
            return ITEM_DEPARTMENT;
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
