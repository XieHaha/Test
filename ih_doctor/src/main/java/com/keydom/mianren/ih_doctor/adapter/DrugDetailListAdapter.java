package com.keydom.mianren.ih_doctor.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.bean.DrugBean;
import com.keydom.mianren.ih_doctor.m_interface.SingleClick;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DrugDetailListAdapter extends BaseQuickAdapter<DrugBean, BaseViewHolder> {
    private DeleteListener deleteListener;
    private Context context;
    public DrugDetailListAdapter(Context context, @Nullable List<DrugBean> data, DeleteListener deleteListener) {
        super(R.layout.diagnose_medicine_item_layout, data);
        this.deleteListener = deleteListener;
        this.context=context;
    }

    public interface DeleteListener {
        void delete(int positon);
    }

    @Override
    protected void convert(BaseViewHolder helper, DrugBean item) {
        TextView update_tv = helper.getView(R.id.update_tv);
        TextView delete_tv = helper.getView(R.id.delete_tv);
        helper.setText(R.id.medicine_num, String.valueOf(helper.getPosition() + 1) + "、").setText(R.id.medicine_name, item.getDrugsName())
                .setText(R.id.medicine_specifications, item.getSpec()).setText(R.id.medicine_amount, String.valueOf(item.getQuantity()) + item.getPackUnit())
                .setText(R.id.medicine_fee, item.getPrice() == null ? "" : item.getPrice().multiply(new BigDecimal(item.getQuantity())) + "元")
                .setText(R.id.use_once, "用法：" + item.getSingleDose() + item.getDosageUnit()).setText(R.id.use_method, item.getWay())
                .setText(R.id.times, String.valueOf(item.getFrequency()));
        update_tv.setOnClickListener(new View.OnClickListener() {
            @SingleClick(1000)
            @Override
            public void onClick(View view) {
//                ToastUtils.showShort("点击了修改按钮");
                List<DrugBean> resultList=new ArrayList<>();
                resultList.add(item);
//                DrugUseActivity.start(context,resultList);
            }
        });
        delete_tv.setOnClickListener(new View.OnClickListener() {
            @SingleClick(1000)
            @Override
            public void onClick(View view) {
                if (deleteListener != null)
                    deleteListener.delete(helper.getPosition());
               /* remove(helper.getPosition());
                              notifyDataSetChanged();
                notify();
                Logger.e("list.size=="+getItemCount());*/
            }
        });
    }
}
