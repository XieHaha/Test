package com.keydom.mianren.ih_patient.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.bean.AmniocentesisBean;

import java.util.List;

/**
 * @date 20/3/10 10:12
 * @des 羊水穿刺预约查询及取消
 */
public class AmniocentesisRecordAdapter extends BaseQuickAdapter<AmniocentesisBean,
        BaseViewHolder> {

    public AmniocentesisRecordAdapter(@Nullable List<AmniocentesisBean> data) {
        super(R.layout.item_amniocentesis_record, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AmniocentesisBean item) {
        helper.setText(R.id.item_amniocentesis_name, item.getName())
                .setText(R.id.item_amniocentesis_time, "01月01日")
                .setText(R.id.item_amniocentesis_reserve_time, "01月02日")
                .setText(R.id.item_amniocentesis_reserve_type, "羊膜穿刺")
                .addOnClickListener(R.id.item_amniocentesis_operate);
    }

}
