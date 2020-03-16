package com.keydom.mianren.ih_doctor.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.bean.MaterialBean;
import com.keydom.mianren.ih_doctor.m_interface.SingleClick;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MaterialChooseAdapter extends BaseQuickAdapter<MaterialBean, BaseViewHolder> {

    List<MaterialBean> selectList;

    public MaterialChooseAdapter(@Nullable List<MaterialBean> data, List<MaterialBean> selectList) {
        super(R.layout.choose_medical_item, data);
        if (selectList == null) {
            selectList = new ArrayList<>();
        }
        this.selectList = selectList;

    }

    @Override
    protected void convert(BaseViewHolder helper, MaterialBean item) {
        RelativeLayout prient_rl = helper.itemView.findViewById(R.id.prient_rl);
        TextView medical_name_tv = helper.itemView.findViewById(R.id.medical_name_tv);
        ImageView select_img = helper.itemView.findViewById(R.id.select_img);
        TextView medical_spec_tv = helper.itemView.findViewById(R.id.medical_spec_tv);
        TextView medical_usage_tv = helper.itemView.findViewById(R.id.medical_usage_tv);
        TextView medical_vender_tv = helper.itemView.findViewById(R.id.medical_vender_tv);
        medical_name_tv.setText(item.getName());
        medical_spec_tv.setText("规格:" + item.getSpec());
        medical_usage_tv.setVisibility(View.GONE);
        medical_vender_tv.setVisibility(View.GONE);
        if (isSelect(item)) {
            prient_rl.setBackground(mContext.getResources().getDrawable(R.drawable.blue_corner_bg));
            select_img.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.drug_selected_icon));
            medical_name_tv.setTextColor(mContext.getResources().getColor(R.color.white));
            medical_spec_tv.setTextColor(mContext.getResources().getColor(R.color.font_medical_select));
            medical_usage_tv.setTextColor(mContext.getResources().getColor(R.color.font_medical_select));
            medical_vender_tv.setTextColor(mContext.getResources().getColor(R.color.font_medical_select));
        } else {
            prient_rl.setBackground(mContext.getResources().getDrawable(R.drawable.white_corner_bg));
            select_img.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.nursing_project_unselect));
            medical_name_tv.setTextColor(mContext.getResources().getColor(R.color.primary_font_color));
            medical_spec_tv.setTextColor(mContext.getResources().getColor(R.color.fontColorNavigate));
            medical_usage_tv.setTextColor(mContext.getResources().getColor(R.color.fontColorNavigate));
            medical_vender_tv.setTextColor(mContext.getResources().getColor(R.color.fontColorNavigate));
        }
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @SingleClick(1000)
            @Override
            public void onClick(View v) {
                if (isSelect(item)) {
                    Iterator<MaterialBean> iterator = selectList.iterator();
                    while (iterator.hasNext()) {
                        MaterialBean bean = iterator.next();
                        if (bean.getId() == item.getId())
                            iterator.remove();
                    }
                } else {
                    selectList.add(item);
                }
                notifyDataSetChanged();
            }
        });
    }

    public List<MaterialBean> getSelectList() {
        return selectList;
    }

    private boolean isSelect(MaterialBean item) {
        for (MaterialBean bean : selectList) {
            if (item.getId() == bean.getId()) {
                return true;
            }
        }
        return false;
    }
}
