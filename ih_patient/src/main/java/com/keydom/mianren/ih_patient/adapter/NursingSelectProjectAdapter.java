package com.keydom.mianren.ih_patient.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.bean.NursingProjectInfo;

import java.util.List;

/**
 * 护理项目选中适配器
 */
public class NursingSelectProjectAdapter extends BaseQuickAdapter<NursingProjectInfo, BaseViewHolder> {
    /**
     * 构建方法
     */
    public NursingSelectProjectAdapter(@Nullable List<NursingProjectInfo> data) {
        super(R.layout.nursing_project_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NursingProjectInfo item) {
        helper.setText(R.id.project_name_tv,item.getName());
    }
}
