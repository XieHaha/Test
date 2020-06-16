package com.keydom.mianren.ih_patient.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.bean.PayRecordBean;

import java.util.List;

/**
 * created date: 2019/1/15 on 18:16
 * des:未缴费适配器
 */
public class UnPayRecordAdapter extends BaseQuickAdapter<PayRecordBean, BaseViewHolder> {

    /**
     * 监听接口
     */
    private IOnSelectedChanged mIOnSelectedChanged;

    /**
     * 点击事件监听
     */
    public interface IOnSelectedChanged {
        void onPriceChanged(PayRecordBean payRecordBean, int position);
    }

    /**
     * 构建方法
     */
    public UnPayRecordAdapter(@Nullable List<PayRecordBean> data, IOnSelectedChanged i) {
        super(R.layout.item_un_pay_record, data);
        this.mIOnSelectedChanged = i;
    }

    @Override
    protected void convert(BaseViewHolder helper, PayRecordBean item) {
        helper.setText(R.id.name, item.getName())
                .setText(R.id.project, TextUtils.isEmpty(item.getPayRegister())?"":item.getPayRegister())
                .setText(R.id.time, item.getDate())
                .setText(R.id.money, item.getSumFee() + "")
                .addOnClickListener(R.id.pay);
        CheckBox checkBox = helper.getView(R.id.select);

        if (item.isWaiYan()) {
            checkBox.setVisibility(View.INVISIBLE);
            checkBox.setChecked(false);
            checkBox.setOnCheckedChangeListener(null);
        } else {
            //            checkBox.setVisibility(View.VISIBLE);
            checkBox.setChecked(item.isSelect());
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (checkBox.isPressed()) {
                    item.setSelect(isChecked);
                    checkBox.setChecked(false);  //新需求
                    mIOnSelectedChanged.onPriceChanged(item, helper.getAdapterPosition());
                }
            });
        }


    }
}
