package com.keydom.ih_patient.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.keydom.ih_common.constant.Const;
import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.ih_common.view.CircleImageView;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.order_doctor_register.DoctorIndexActivity;
import com.keydom.ih_patient.bean.DepartmentSchedulingBean;
import com.keydom.ih_patient.bean.DoctorInfo;

import java.util.List;

/**
 * 医生适配器
 */
public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.VH> {
    private Context context;
    private boolean isSelect;
    private int curPosition = -1;
    private long curDoctorId = -1;
    private List<DepartmentSchedulingBean> dataList;

    public void setSelect(boolean select) {
        isSelect = select;
    }

    /**
     * 构造方法
     */
    public DoctorAdapter(Context context, List<DepartmentSchedulingBean> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.order_register_doctor_item, parent, false);
        return new DoctorAdapter.VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, final int position) {
        DoctorInfo doctorInfo = dataList.get(position).getHUser();
        if (doctorInfo != null) {
            if (isSelect) {
                holder.doctor_is_selected_iv.setVisibility(View.VISIBLE);
                holder.doctor_is_selected_iv.setOnClickListener(v -> updateSelectDoctor(doctorInfo.getId(), position));
                holder.doctor_ishave_registered_tv.setVisibility(View.GONE);
                if (curDoctorId == doctorInfo.getId()) {
                    holder.doctor_is_selected_iv.setSelected(true);
                } else {
                    holder.doctor_is_selected_iv.setSelected(false);
                }
            } else {
                holder.doctor_ishave_registered_tv.setVisibility(View.VISIBLE);
                holder.doctor_is_selected_iv.setVisibility(View.GONE);
                if ("1".equals(dataList.get(position).getNumber())) {
                    holder.doctor_ishave_registered_tv.setText("有号");
                    holder.doctor_ishave_registered_tv.setOnClickListener(view -> DoctorIndexActivity.start(context, doctorInfo));
                } else {
                    holder.doctor_ishave_registered_tv.setText("无号");
                    holder.doctor_ishave_registered_tv.setClickable(false);
                }
            }
            holder.doctor_name_tv.setText(doctorInfo.getName() == null ||
                    "".equals(doctorInfo.getName()) ? "" :
                    doctorInfo.getName());
            holder.doctor_dsc_tv.setText("擅长:" + doctorInfo.getAdept());
            if (doctorInfo.getJobTitleName() != null && !"".equals(doctorInfo.getJobTitleName()))
                holder.main_doctor_label.setText(doctorInfo.getJobTitleName());
            else
                holder.main_doctor_label.setVisibility(View.GONE);
            if (doctorInfo.getPopularDoctor() == 1) {
                holder.hot_doctor_label.setVisibility(View.VISIBLE);
            } else {
                holder.hot_doctor_label.setVisibility(View.GONE);
            }
        }
        GlideUtils.load(holder.order_doctor_img, Const.IMAGE_HOST + doctorInfo.getImage(),
                R.mipmap.order_doctor_icon, R.mipmap.order_doctor_icon, false, null);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    private void updateSelectDoctor(long id, int position) {
        curDoctorId = id;
        curPosition = position;
        notifyDataSetChanged();
    }

    public void setCurDoctorId(long curDoctorId) {
        this.curDoctorId = curDoctorId;
    }

    public int getCurPosition() {
        return curPosition;
    }

    public class VH extends RecyclerView.ViewHolder {
        private TextView doctor_name_tv, main_doctor_label, hot_doctor_label, doctor_dsc_tv,
                doctor_ishave_registered_tv;
        private ImageView doctor_is_selected_iv;
        private CircleImageView order_doctor_img;

        public VH(View v) {
            super(v);
            doctor_name_tv = v.findViewById(R.id.doctor_name_tv);
            main_doctor_label = v.findViewById(R.id.main_doctor_label);
            hot_doctor_label = v.findViewById(R.id.hot_doctor_label);
            doctor_dsc_tv = v.findViewById(R.id.doctor_dsc_tv);
            doctor_ishave_registered_tv = v.findViewById(R.id.doctor_ishave_registered_tv);
            doctor_is_selected_iv = v.findViewById(R.id.doctor_is_selected_iv);
            order_doctor_img = v.findViewById(R.id.order_doctor_img);
        }
    }
}
