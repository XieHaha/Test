package com.keydom.mianren.ih_doctor.activity.online_diagnose.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.ih_common.bean.CheckOutGroupBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @date 20/4/26 10:52
 * @des 检查项目  二级列表
 */
public class DiagnoseCheckItemAdapter extends BaseQuickAdapter<CheckOutGroupBean, BaseViewHolder> {
    private List<CheckOutGroupBean> selectCheck = new ArrayList<>();
    private int groupPosition;

    public DiagnoseCheckItemAdapter(@Nullable List<CheckOutGroupBean> data) {
        super(R.layout.item_diagnose_sub, data);
    }

    public void setSelectCheck(List<CheckOutGroupBean> selectCheck,
                               int groupPosition) {
        this.selectCheck = selectCheck;
        this.groupPosition = groupPosition;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, CheckOutGroupBean item) {
        helper.setText(R.id.diagnose_sub_name, item.getItemName());

        if (selectCheck.size() > 0 && groupPosition != -1) {
            List<CheckOutGroupBean> list = selectCheck.get(groupPosition).getItems();
            if (list != null && list.contains(item)) {
                helper.itemView.setSelected(true);
            } else {
                helper.itemView.setSelected(false);
            }
        } else {
            helper.itemView.setSelected(false);
        }
    }
}
