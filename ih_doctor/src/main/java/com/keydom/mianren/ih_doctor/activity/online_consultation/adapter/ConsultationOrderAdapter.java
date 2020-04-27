package com.keydom.mianren.ih_doctor.activity.online_consultation.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.ih_common.utils.BaseImageUtils;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.bean.ConsultationBean;

import java.util.List;

/**
 * @date 4月3日 14:15
 * 会诊订单适配器
 */
public class ConsultationOrderAdapter extends BaseQuickAdapter<ConsultationBean, BaseViewHolder> {
    public ConsultationOrderAdapter(@Nullable List<ConsultationBean> data) {
        super(R.layout.item_consultation_order, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ConsultationBean item) {
        helper.setText(R.id.consultation_order_name_tv, item.getPatientName())
                .setText(R.id.consultation_order_sex_tv,
                        CommonUtils.getPatientSex(item.getPatientGender()))
                .setText(R.id.consultation_order_age_tv, item.getPatientAge() + "岁")
                .setText(R.id.consultation_order_card_tv,
                        String.format(mContext.getString(R.string.txt_visit_card),
                                item.getEleCardNumber()))
                .setText(R.id.consultation_order_date_tv, item.getMdtTime());
        ImageView headerImage = helper.getView(R.id.consultation_order_header_iv);
        GlideUtils.load(headerImage, BaseImageUtils.getHeaderUrl(item.getRegisterImage()), 0,
                R.mipmap.im_default_head_image, true, null);
        TextView tvStatus = helper.getView(R.id.consultation_order_status_tv);
        if (item.getLevel() == 0) {
            tvStatus.setBackgroundResource(R.drawable.corner5_fbd54e_bg);
            tvStatus.setText("普通");
        } else {
            tvStatus.setBackgroundResource(R.drawable.corner5_ff3939_bg);
            tvStatus.setText("紧急");
        }
    }
}
