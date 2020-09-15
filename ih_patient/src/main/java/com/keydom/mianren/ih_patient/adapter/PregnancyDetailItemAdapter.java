package com.keydom.mianren.ih_patient.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.bean.PregnancyItemBean;

import java.util.List;

/**
 * created date: 2019/1/15 on 16:44
 * des:
 *
 * @author 顿顿
 */
public class PregnancyDetailItemAdapter extends BaseQuickAdapter<PregnancyItemBean,
        BaseViewHolder> {
    public PregnancyDetailItemAdapter(@Nullable List<PregnancyItemBean> data) {
        super(R.layout.item_pregnancy_detail, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PregnancyItemBean item) {
        helper.setText(R.id.pregnancy_detail_key_tv, item.getName());
        if (helper.getAdapterPosition() == 9) {
            helper.getView(R.id.pregnancy_detail_value_tv).setVisibility(View.GONE);
            helper.getView(R.id.pregnancy_detail_value_layout).setVisibility(View.VISIBLE);
            //妊娠分级   0绿色 1黄色 2橙色 3红色
            ImageView imageView = helper.getView(R.id.pregnancy_detail_value_iv);
            switch (item.getValue()) {
                case "1":
                    imageView.setImageResource(R.color.cycle_yellow);
                    break;
                case "2":
                    imageView.setImageResource(R.color.font_order_type_image_with_video);
                    break;
                case "3":
                    imageView.setImageResource(R.color.red);
                    break;
                default:
                    imageView.setImageResource(R.color.green);
                    break;
            }
        } else {
            helper.getView(R.id.pregnancy_detail_value_layout).setVisibility(View.GONE);
            helper.getView(R.id.pregnancy_detail_value_tv).setVisibility(View.VISIBLE);
            helper.setText(R.id.pregnancy_detail_value_tv, item.getValue());
        }
    }
}
