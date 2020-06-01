package com.keydom.mianren.ih_doctor.activity.online_diagnose.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.bean.CheckOutGroupBean;
import com.keydom.mianren.ih_doctor.bean.CheckOutSubBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @date 20/4/26 10:52
 * @des 检查项目 一级列表
 */
public class DiagnoseCheckGroupAdapter extends BaseQuickAdapter<CheckOutGroupBean, BaseViewHolder> {
    private int curPosition = 0;
    private Map<String, ArrayList<CheckOutSubBean>> select = new HashMap<>();

    public DiagnoseCheckGroupAdapter(@Nullable List<CheckOutGroupBean> data) {
        super(R.layout.item_diagnose_group, data);
    }

    public void setSelect(Map<String, ArrayList<CheckOutSubBean>> select) {
        this.select = select;
        notifyDataSetChanged();
    }

    public void setCurPosition(int curPosition) {
        this.curPosition = curPosition;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, CheckOutGroupBean item) {
        helper.setText(R.id.diagnose_group_name, item.getName());
        //选中状态
        if (curPosition == helper.getAdapterPosition()) {
            helper.itemView.setSelected(true);
        } else {
            helper.itemView.setSelected(false);
        }
        //已选子选项统计
        TextView selectNum = helper.getView(R.id.diagnose_group_select);
        ArrayList<CheckOutSubBean> list = select.get(item.getCateCode());
        if (list != null && list.size() > 0) {
            selectNum.setVisibility(View.VISIBLE);
            selectNum.setText(String.valueOf(list.size()));
        } else {
            selectNum.setVisibility(View.GONE);
        }
    }
}
