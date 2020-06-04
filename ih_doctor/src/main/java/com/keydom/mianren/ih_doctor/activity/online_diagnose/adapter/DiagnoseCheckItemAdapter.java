package com.keydom.mianren.ih_doctor.activity.online_diagnose.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.ih_common.bean.CheckOutParentBean;
import com.keydom.ih_common.bean.CheckOutSubBean;
import com.keydom.mianren.ih_doctor.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @date 20/4/26 10:52
 * @des 检查项目  二级列表
 */
public class DiagnoseCheckItemAdapter extends BaseQuickAdapter<CheckOutSubBean, BaseViewHolder> {
    private List<CheckOutParentBean> selectCheck = new ArrayList<>();
    private int groupPosition;

    public DiagnoseCheckItemAdapter(@Nullable List<CheckOutSubBean> data) {
        super(R.layout.item_diagnose_sub, data);
    }

    public void setSelectCheck(List<CheckOutParentBean> selectCheck,
                               int groupPosition) {
        this.selectCheck = selectCheck;
        this.groupPosition = groupPosition;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, CheckOutSubBean item) {
        helper.setText(R.id.diagnose_sub_name, item.getItemName());

        if (selectCheck.size() > 0 && groupPosition != -1) {
            ArrayList<CheckOutSubBean> list = selectCheck.get(groupPosition).getItems();
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
