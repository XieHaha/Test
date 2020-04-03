package com.keydom.mianren.ih_doctor.activity.online_consultation.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.bean.InquiryBean;

import java.util.List;

/**
 * @date 4月3日 14:15
 * 会诊订单适配器
 */
public class ConsultationOrderAdapter extends BaseQuickAdapter<InquiryBean, BaseViewHolder> {
    public ConsultationOrderAdapter( @Nullable List<InquiryBean> data) {
        super(R.layout.item_consultation_order, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, InquiryBean item) {
        //        helper.setText(R.id.consultation_order_name_tv, item.getName())
        //                .setText(R.id.consultation_order_sex_tv, item.getSex())
        //                .setText(R.id.consultation_order_age_tv, item.getAge())
        //                .setText(R.id.consultation_order_card_tv,
        //                        String.format(mContext.getString(R.string.txt_visit_card),
        //                                item.getCardNumber()))
        //                .setText(R.id.consultation_order_date_tv, item.getConditionData());

        TextView tvStatus = helper.getView(R.id.consultation_order_status_tv);
        tvStatus.setText("紧急");
    }
}
