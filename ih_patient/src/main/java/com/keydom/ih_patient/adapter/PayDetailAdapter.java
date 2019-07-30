package com.keydom.ih_patient.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.bean.PayDetailContent;
import com.keydom.ih_patient.bean.PayDetailTitle;
import com.keydom.ih_patient.bean.PayDetailTotal;

import java.util.List;

/**
 * created date: 2019/1/15 on 15:35
 * des:缴费详情适配器
 */
public class PayDetailAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity,BaseViewHolder> {

    /**
     * 多布局类型
     */
    public static final int HEAD = 1;
    public static final int TITLE = 2;
    public static final int CONTENT = 3;
    public static final int TOTAL = 4;
    public static final int LINE = 5;
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     */
    public PayDetailAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(HEAD, R.layout.item_pay_detail_head);
        addItemType(TITLE, R.layout.item_pay_detail_title);
        addItemType(CONTENT, R.layout.item_pay_detail_content);
        addItemType(TOTAL, R.layout.item_pay_detail_total);
        addItemType(LINE, R.layout.item_pay_detail_line);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()){
            case TITLE:
                PayDetailTitle payDetailTitle = (PayDetailTitle) item;
                helper.setText(R.id.title,payDetailTitle.getProjectClassificationName()+"");
                break;
            case CONTENT:
                PayDetailContent payDetailContent = (PayDetailContent) item;
                helper.setText(R.id.name,payDetailContent.getSonProjectClassificationName()+"")
                        .setText(R.id.num,payDetailContent.getNumber()+"")
                        .setText(R.id.price,"¥"+payDetailContent.getUnitPrice()+"");
                break;
            case TOTAL:
                PayDetailTotal payDetailTotal = (PayDetailTotal) item;
                helper.setText(R.id.total,"小计：¥"+payDetailTotal.getMoney());
                break;
        }
    }
}
