package com.keydom.ih_doctor.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.view.OptionsPickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.bean.MaterialBean;

import java.text.DecimalFormat;
import java.util.List;

public class MaterialUseAdapter extends BaseQuickAdapter<MaterialBean, BaseViewHolder> {

    private DecimalFormat df1 = new DecimalFormat("#.#");
    private OptionsPickerView optionsPickerView;

    public MaterialUseAdapter(@Nullable List<MaterialBean> data) {
        super(R.layout.material_use_item_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MaterialBean item) {
        helper.setText(R.id.medical_name_tv, item.getName())
                .setText(R.id.medical_desc_tv, item.getSpec())
                .setText(R.id.use_amount_tv, String.valueOf(item.getMaterialAmount()));

        TextView use_amount_tv = helper.itemView.findViewById(R.id.use_amount_tv);
        RelativeLayout use_amount_scaler_minus_layout = helper.itemView.findViewById(R.id.use_amount_scaler_minus_layout);
        RelativeLayout use_amount_scaler_add_layout = helper.itemView.findViewById(R.id.use_amount_scaler_add_layout);

        use_amount_scaler_minus_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.getMaterialAmount() > 1) {
                    item.setMaterialAmount(item.getMaterialAmount() - 1);
                    notifyDataSetChanged();
                }
            }
        });
        use_amount_scaler_add_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setMaterialAmount(item.getMaterialAmount() + 1);
                notifyDataSetChanged();
            }
        });

    }

}
