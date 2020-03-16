package com.keydom.mianren.ih_patient.adapter;

import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.bean.ChargeMemberPriceItemBean;

import java.util.List;

/**
 * created date: 2019/1/15 on 16:44
 * des:充值项目适配器
 */
public class ChargeMemberAdapter extends BaseQuickAdapter<ChargeMemberPriceItemBean, BaseViewHolder> {
    /**
     * 构建方法
     */

    List<ChargeMemberPriceItemBean> mDatas;

    public ChargeMemberAdapter(@Nullable List<ChargeMemberPriceItemBean> data) {
        super(R.layout.item_charge_member, data);
        mDatas = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, ChargeMemberPriceItemBean item) {
        if (item.isEditText()) {
            helper.getView(R.id.item_charge_member_price_tv).setVisibility(View.GONE);
            helper.getView(R.id.item_charge_member_price_et).setVisibility(View.VISIBLE);
            helper.getView(R.id.item_charge_member_root_rl).setOnClickListener(null);
            ((EditText) helper.getView(R.id.item_charge_member_price_et)).setKeyListener(DigitsKeyListener.getInstance("1234567890"));
            ((EditText) helper.getView(R.id.item_charge_member_price_et)).addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String str = s.toString();
                    if (TextUtils.isEmpty(str)) {
                        return;
                    }

                    if ("0".equals(str)) {
                        return;
                    }

                    item.setPrice(Integer.valueOf(s.toString()));
                }
            });
        } else {
            helper.setText(R.id.item_charge_member_price_tv, "¥" + item.getPrice());
            helper.getView(R.id.item_charge_member_price_tv).setVisibility(View.VISIBLE);
            helper.getView(R.id.item_charge_member_price_et).setVisibility(View.GONE);
            helper.getView(R.id.item_charge_member_root_rl).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setAllItemState(item);
                    notifyDataSetChanged();
                }
            });

            if (item.isSelected()) {
                helper.getView(R.id.item_charge_member_root_rl).setBackgroundResource(R.drawable.yellow_corner_bg);
            } else {
                helper.getView(R.id.item_charge_member_root_rl).setBackgroundResource(R.drawable.yellow_line_corner_bg);
            }
        }

    }


    private void setAllItemState(ChargeMemberPriceItemBean data) {
        for (ChargeMemberPriceItemBean item : mDatas) {
            if (data.equals(item)) {
                item.setSelected(!data.isSelected());
            } else {
                item.setSelected(false);
            }
        }
    }
}
