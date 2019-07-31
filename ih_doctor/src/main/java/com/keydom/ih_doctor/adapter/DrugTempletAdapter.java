package com.keydom.ih_doctor.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.bean.DrugBean;
import com.keydom.ih_doctor.m_interface.SingleClick;
import com.keydom.ih_doctor.utils.ToastUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DrugTempletAdapter extends BaseQuickAdapter<DrugBean, BaseViewHolder> {

    List<DrugBean> selectList;

    public DrugTempletAdapter(@Nullable List<DrugBean> data, List<DrugBean> selectList) {
        super(R.layout.medical_templet_item, data);
        if (selectList == null) {
            selectList = new ArrayList<>();
        }
        this.selectList = selectList;

    }

    @Override
    protected void convert(BaseViewHolder helper, DrugBean item) {
        RelativeLayout prient_rl = helper.itemView.findViewById(R.id.prient_rl);
        TextView medical_name_tv = helper.itemView.findViewById(R.id.medical_name_tv);
        ImageView select_img = helper.itemView.findViewById(R.id.select_img);
        TextView medical_spec_tv = helper.itemView.findViewById(R.id.medical_spec_tv);
        TextView medical_usage_tv = helper.itemView.findViewById(R.id.medical_usage_tv);
        TextView usage_frequency_tv = helper.itemView.findViewById(R.id.usage_frequency_tv);
        TextView way_tv = helper.itemView.findViewById(R.id.way_tv);
        TextView quantity_tv = helper.itemView.findViewById(R.id.quantity_tv);
        TextView fee_tv = helper.itemView.findViewById(R.id.fee_tv);
        medical_name_tv.setText(item.getDrugsName());
        medical_spec_tv.setText("规格:" + item.getSpec());
        medical_usage_tv.setText("用法:" + item.getSingleDose() + item.getDosageUnit());
        way_tv.setText(item.getWay());
        usage_frequency_tv.setText(String.valueOf(item.getFrequency()));
        quantity_tv.setText(String.valueOf(item.getQuantity() + item.getPackUnit()));
        fee_tv.setText(String.valueOf((item.getPrice().multiply(new BigDecimal(item.getQuantity())))) + "元");
        if (isSelect(item)) {
            prient_rl.setBackground(mContext.getResources().getDrawable(R.drawable.blue_corner_bg));
            select_img.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.drug_selected_icon));
            medical_name_tv.setTextColor(mContext.getResources().getColor(R.color.white));
            medical_spec_tv.setTextColor(mContext.getResources().getColor(R.color.font_medical_select));
            medical_usage_tv.setTextColor(mContext.getResources().getColor(R.color.font_medical_select));
            way_tv.setTextColor(mContext.getResources().getColor(R.color.font_medical_select));
            usage_frequency_tv.setTextColor(mContext.getResources().getColor(R.color.font_medical_select));
            quantity_tv.setTextColor(mContext.getResources().getColor(R.color.font_medical_select));
            fee_tv.setTextColor(mContext.getResources().getColor(R.color.font_medical_select));
        } else {
            prient_rl.setBackground(mContext.getResources().getDrawable(R.drawable.white_corner_bg));
            select_img.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.nursing_project_unselect));
            medical_name_tv.setTextColor(mContext.getResources().getColor(R.color.primary_font_color));
            medical_spec_tv.setTextColor(mContext.getResources().getColor(R.color.fontColorNavigate));
            medical_usage_tv.setTextColor(mContext.getResources().getColor(R.color.fontColorNavigate));
            way_tv.setTextColor(mContext.getResources().getColor(R.color.fontColorNavigate));
            usage_frequency_tv.setTextColor(mContext.getResources().getColor(R.color.fontColorNavigate));
            quantity_tv.setTextColor(mContext.getResources().getColor(R.color.fontColorNavigate));
            fee_tv.setTextColor(mContext.getResources().getColor(R.color.fontColorNavigate));
        }
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @SingleClick(1000)
            @Override
            public void onClick(View v) {
                if (isSelect(item)) {
                    Iterator<DrugBean> iterator = selectList.iterator();
                    while (iterator.hasNext()) {
                        DrugBean bean = iterator.next();
                        if (bean.getId() == item.getId())
                            iterator.remove();
                    }
                } else {
                    if (selectList != null && selectList.size() < 5) {
                        selectList.add(item);
                    } else {
                        ToastUtil.shortToast(mContext, "每个处方最多限5种药品");
                    }

                }
                notifyDataSetChanged();
            }
        });
    }

    public List<DrugBean> getSelectList() {
        return selectList;
    }

    private boolean isSelect(DrugBean item) {
        for (DrugBean bean : selectList) {
            if (item.getId() == bean.getId()) {
                return true;
            }
        }
        return false;
    }
}
