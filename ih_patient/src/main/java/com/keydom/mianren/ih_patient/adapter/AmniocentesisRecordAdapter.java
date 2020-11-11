package com.keydom.mianren.ih_patient.adapter;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.bean.AmniocentesisBean;
import com.keydom.mianren.ih_patient.utils.DateUtils;

import java.util.List;

/**
 * @author 顿顿
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
                .setText(R.id.item_amniocentesis_time, DateUtils.transDate(item.getCreateDate(),
                        DateUtils.YYYY_MM_DD_HH_MM_SS, DateUtils.MM_DD_CH))
                .setText(R.id.item_amniocentesis_reserve_time,
                        DateUtils.transDate(item.getSurgeryTime(),
                                DateUtils.YYYY_MM_DD, DateUtils.MM_DD_CH));
        TextView textView = helper.getView(R.id.item_amniocentesis_operate);
        switch (item.getState()) {
            case "-1":
                textView.setText("已取消");
                textView.setTextColor(ContextCompat.getColor(mContext, R.color.edit_text_color));
                textView.setClickable(false);
                break;
            case "0":
                textView.setText("已取消");
                textView.setTextColor(ContextCompat.getColor(mContext, R.color.edit_text_color));
                textView.setClickable(false);
                break;
            case "1":
                textView.setText(R.string.txt_cancel_reserve);
                textView.setTextColor(ContextCompat.getColor(mContext, R.color.nursing_status_red));
                textView.setClickable(true);
                helper.addOnClickListener(R.id.item_amniocentesis_operate);
                break;
            case "2":
                textView.setText("已确认");
                textView.setTextColor(ContextCompat.getColor(mContext, R.color.other_login_color));
                textView.setClickable(false);
                break;
            default:
        }
    }

}
