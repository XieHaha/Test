package com.keydom.ih_patient.adapter;

import android.content.Intent;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.prescription_check.PrescriptionDetailActivity;
import com.keydom.ih_patient.bean.PrescriptionBean;
import com.keydom.ih_patient.bean.PrescriptionTitleBean;

import java.util.List;

/**
 * created date: 2019/1/18 on 11:12
 * des: 电子处方适配器
 */
public class PrescriptionListAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    public final static int HEAD = 1;
    public final static int CONTENT = 2;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     */
    public PrescriptionListAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(HEAD, R.layout.item_prescription_head);
        addItemType(CONTENT, R.layout.item_prescription_content);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()) {
            case HEAD:
                PrescriptionTitleBean titleBean = (PrescriptionTitleBean) item;
                helper.itemView.setOnClickListener(v -> {
                    int pos = helper.getAdapterPosition();
                    boolean isex = titleBean.isExpanded();
                    if (isex) {
                        collapse(pos);
                    } else {
                        expand(pos);
                    }
                });
                helper.setText(R.id.time, titleBean.getValue())
                        .setImageResource(R.id.icon, titleBean.isExpanded() ? R.mipmap.exa_report_up : R.mipmap.exa_report_down);
                break;
            case CONTENT:
                PrescriptionBean bean = (PrescriptionBean) item;
                helper.setText(R.id.name,bean.getName())
                        .setText(R.id.pre_num,bean.getNum())
                        .setOnClickListener(R.id.group, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent i = new Intent(mContext, PrescriptionDetailActivity.class);
                                i.putExtra(PrescriptionDetailActivity.ID, String.valueOf(bean.getId()));
                                ActivityUtils.startActivity(i);
                            }
                        })
                        .setGone(R.id.bottom,bean.isBottom());
                break;
        }
    }
}
