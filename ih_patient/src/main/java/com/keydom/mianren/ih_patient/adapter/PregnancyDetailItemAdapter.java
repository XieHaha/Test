package com.keydom.mianren.ih_patient.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
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

    /**
     * 胎动 下标
     */
    public static final int FETAL_MOVEMENT_POSITION = 8;
    /**
     * 妊娠分级 下标
     */
    public static final int PREGNANCY_POSITION = 9;
    /**
     * 水肿 下标
     */
    public static final int EDEMA_POSITION = 10;
    /**
     * 尿蛋白 下标
     */
    public static final int URINE_PROTEIN_POSITION = 11;

    public PregnancyDetailItemAdapter(@Nullable List<PregnancyItemBean> data) {
        super(R.layout.item_pregnancy_detail, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PregnancyItemBean item) {
        helper.setText(R.id.pregnancy_detail_key_tv, item.getName())
                .setText(R.id.pregnancy_detail_unit_tv, item.getUnit());
        switch (helper.getAdapterPosition()) {
            case FETAL_MOVEMENT_POSITION:
                helper.setText(R.id.pregnancy_detail_value_tv, TextUtils.equals("1",
                        item.getValue()) ? "不正常" : "正常");
                break;
            case PREGNANCY_POSITION:
                helper.getView(R.id.pregnancy_detail_level_layout).setVisibility(View.VISIBLE);
                helper.getView(R.id.pregnancy_detail_value_layout).setVisibility(View.GONE);
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
                break;
            case EDEMA_POSITION:
                helper.setText(R.id.pregnancy_detail_value_tv, TextUtils.equals("1",
                        item.getValue()) ? "有" : "无");
                break;
            case URINE_PROTEIN_POSITION:
                helper.setText(R.id.pregnancy_detail_value_tv, TextUtils.equals("-1",
                        item.getValue()) ? "阴性" : "阳性");
                break;
            default:
                helper.getView(R.id.pregnancy_detail_value_layout).setVisibility(View.VISIBLE);
                helper.getView(R.id.pregnancy_detail_level_layout).setVisibility(View.GONE);
                helper.setText(R.id.pregnancy_detail_value_tv, item.getValue());
                break;
        }
    }
}
