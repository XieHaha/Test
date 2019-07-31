package com.keydom.ih_patient.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.bean.NursingOrderChargeBackBean;
import com.keydom.ih_patient.bean.NursingOrderChargeBackItem;

import java.util.List;

/**
 * created date: 2018/12/24 on 10:25
 * des:护理退单适配器
 */
public class NursingChargeBackAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity,BaseViewHolder> {
    /**
     * 多布局类型一
     */
    public static final int TYPE_1=1;
    /**
     * 多布局类型二
     */
    public static final int TYPE_2=2;
    /**
     * 多布局类型三
     */
    public static final int TYPE_3=3;
    /**
     * 多布局类型四
     */
    public static final int TYPE_4=4;
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     */
    public NursingChargeBackAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_1, R.layout.item_charge_back_1);
        addItemType(TYPE_2,R.layout.item_charge_back_2);
        addItemType(TYPE_3,R.layout.item_nursing_charge_back_order);
        addItemType(TYPE_4,R.layout.item_charge_back_3);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()){
            case TYPE_1:
                NursingOrderChargeBackBean chargeBackBean = (NursingOrderChargeBackBean) item;

                helper.setText(R.id.title,chargeBackBean.getProjectName()+"（"+((NursingOrderChargeBackBean) item).getFrequency()+"次）")
                        .setText(R.id.money,chargeBackBean.getFee()+"元");
                break;

            case TYPE_2:
                NursingOrderChargeBackBean chargeBackBean2 = (NursingOrderChargeBackBean) item;

                helper.setText(R.id.num,chargeBackBean2.getFrequency()+"、")
                        .setText(R.id.title,chargeBackBean2.getProjectName()+"");

                break;

            case TYPE_3:
                NursingOrderChargeBackItem chargeBackItem = (NursingOrderChargeBackItem) item;

                helper.setText(R.id.name,chargeBackItem.getServiceName()+"")
                        .setText(R.id.money,chargeBackItem.isCanChargeBack()?chargeBackItem.getServiceFee()+"元":chargeBackItem.getServicePercent()+"");

                break;

            case TYPE_4:
                NursingOrderChargeBackBean chargeBackBean3 = (NursingOrderChargeBackBean) item;

                helper.setText(R.id.total,chargeBackBean3.getTotalReturnMoney()+"元");

                break;

        }
    }
}
