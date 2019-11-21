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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DrugChooseAdapter extends BaseQuickAdapter<DrugBean, BaseViewHolder> {

    List<DrugBean> selectList= new ArrayList<>();;
    private int selectedPositon=-1;

    public DrugChooseAdapter(@Nullable List<DrugBean> data) {
        super(R.layout.choose_medical_item, data);
    }
	
	    public DrugChooseAdapter(@Nullable List<DrugBean> data, List<DrugBean> selectList) {
        super(R.layout.choose_medical_item, data);
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
        TextView medical_vender_tv = helper.itemView.findViewById(R.id.medical_vender_tv);
        medical_name_tv.setText(item.getDrugsName());
        medical_spec_tv.setText("规格:" + item.getSpec());
        medical_usage_tv.setText(item.getWay() + "," + item.getUsage());
        medical_vender_tv.setText(item.getManufacturerName());
        if (item.isSelecte()) {
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
                for (int i = 0; i <getData().size() ; i++) {
                    getData().get(i).setSelecte(false);
                }
                item.setSelecte(true);
                selectedPositon=getParentPosition(item);
                selectList.clear();
                selectList.add(item);
                notifyDataSetChanged();
            }
        });
    }

    public int arraycount(List<DrugBean> list) {
        int size = list.size();
        int Num = 1;
        List<DrugBean> sortList = sortList(list);
        for (int i = 1; i < sortList.size(); i++) {
            if (sortList.get(i).getDrugsId() != sortList.get(i - 1).getDrugsId()) {
                Num++;
            }
        }
        return Num;
    }

    /**
     * 把结果集合按字段排序，内部类重写排序规则：
     *
     * @param list
     * @return
     */
    private List<DrugBean> sortList(List<DrugBean> list) {
        Collections.sort(list, new Comparator<DrugBean>() {
            @Override
            public int compare(DrugBean drugBean1, DrugBean drugBean2) {
                return String.valueOf(drugBean1.getDrugsId()).compareTo(String.valueOf(drugBean2.getDrugsId()));
            }
        });
        return list;
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
