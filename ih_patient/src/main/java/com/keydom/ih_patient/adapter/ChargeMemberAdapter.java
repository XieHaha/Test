package com.keydom.ih_patient.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.ih_patient.R;

import java.util.List;

/**
 * created date: 2019/1/15 on 16:44
 * des:充值项目适配器
 */
public class ChargeMemberAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    /**
     * 构建方法
     */
    public ChargeMemberAdapter(@Nullable List<String> data) {
        super(R.layout.item_charge_member,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        if("0".equals(item)){
            helper.getView(R.id.item_charge_member_price_tv).setVisibility(View.GONE);
        }else{
            helper.setText(R.id.item_charge_member_price_tv, "¥" + item);
        }

    }
}
