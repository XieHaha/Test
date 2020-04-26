package com.keydom.mianren.ih_doctor.activity.online_diagnose.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.bean.ICD10Bean;

import java.util.List;

/**
 * @date 20/4/26 10:52
 * @des 初步诊断 已选择适配器
 */
public class DiagnoseSelectAdapter extends BaseQuickAdapter<ICD10Bean, BaseViewHolder> {
    public DiagnoseSelectAdapter(@Nullable List<ICD10Bean> data) {
        super(R.layout.item_diagnose_select, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ICD10Bean item) {
        helper.setText(R.id.diagnose_tv, item.getName()).addOnClickListener(R.id.diagnose_tv);
    }

}
