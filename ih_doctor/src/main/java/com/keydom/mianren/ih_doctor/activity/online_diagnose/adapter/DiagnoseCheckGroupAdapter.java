package com.keydom.mianren.ih_doctor.activity.online_diagnose.adapter;

import android.support.annotation.Nullable;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.ih_common.bean.CheckOutParentBean;
import com.keydom.ih_common.bean.CheckOutSubBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @date 20/4/26 10:52
 * @des 检查项目 一级列表
 */
public class DiagnoseCheckGroupAdapter extends BaseQuickAdapter<CheckOutParentBean, BaseViewHolder> {
    private int curPosition = 0;
    private SparseArray<ArrayList<CheckOutSubBean>> selectCheck = new SparseArray<>();

    public DiagnoseCheckGroupAdapter(@Nullable List<CheckOutParentBean> data) {
        super(R.layout.item_diagnose_group, data);
    }

    public void setSelectCheck(SparseArray<ArrayList<CheckOutSubBean>> selectCheck) {
        this.selectCheck = selectCheck;
        notifyDataSetChanged();
    }

    public void setCurPosition(int curPosition) {
        this.curPosition = curPosition;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, CheckOutParentBean item) {
        helper.setText(R.id.diagnose_group_name, item.getName());
        //选中状态
        if (curPosition == helper.getAdapterPosition()) {
            helper.itemView.setSelected(true);
        } else {
            helper.itemView.setSelected(false);
        }
        //已选子选项统计
        TextView selectNum = helper.getView(R.id.diagnose_group_select);
        ArrayList<CheckOutSubBean> list = selectCheck.get(helper.getAdapterPosition());
        if (list != null && list.size() > 0) {
            selectNum.setVisibility(View.VISIBLE);
            selectNum.setText(String.valueOf(list.size()));
        } else {
            selectNum.setVisibility(View.GONE);
        }
    }
}
