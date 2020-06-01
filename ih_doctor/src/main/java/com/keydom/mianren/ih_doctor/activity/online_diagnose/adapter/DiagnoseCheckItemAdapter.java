package com.keydom.mianren.ih_doctor.activity.online_diagnose.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.bean.CheckOutSubBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @date 20/4/26 10:52
 * @des 检查项目  二级列表
 */
public class DiagnoseCheckItemAdapter extends BaseQuickAdapter<CheckOutSubBean, BaseViewHolder> {
    private String curGroupCode;
    private Map<String, ArrayList<CheckOutSubBean>> select = new HashMap<>();

    public DiagnoseCheckItemAdapter(@Nullable List<CheckOutSubBean> data) {
        super(R.layout.item_diagnose_sub, data);
    }

    public void setSelect(Map<String, ArrayList<CheckOutSubBean>> select) {
        this.select = select;
        notifyDataSetChanged();
    }

    public void setCurGroupCode(String curGroupCode) {
        this.curGroupCode = curGroupCode;
    }

    @Override
    protected void convert(BaseViewHolder helper, CheckOutSubBean item) {
        helper.setText(R.id.diagnose_sub_name, item.getItemName());

        ArrayList<CheckOutSubBean> list = select.get(item.getCateCode());
        if (list != null && list.contains(item)) {
            helper.itemView.setSelected(true);
        } else {
            helper.itemView.setSelected(false);
        }
    }
}
