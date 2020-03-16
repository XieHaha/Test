package com.keydom.mianren.ih_patient.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.ih_common.constant.Const;
import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.ih_common.view.CircleImageView;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.bean.DiagnoseIndexBean;

import java.util.List;
/**
 * 我的医生和护士适配器
 */
public class MyDocAndNurAdapter  extends BaseQuickAdapter<DiagnoseIndexBean.AttentionListBean,BaseViewHolder> {
    public MyDocAndNurAdapter(@Nullable List<DiagnoseIndexBean.AttentionListBean> data) {
        super(R.layout.my_doc_nur_item,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DiagnoseIndexBean.AttentionListBean item) {
        helper.setText(R.id.name_tv,item.getName())
                .setText(R.id.dept_ranks_tv,item.getJobTitle())//+"   "+item.getAdept()
                .setText(R.id.adeption_tv,item.getAdept());
        CircleImageView headImg=helper.getView(R.id.head_img);
        GlideUtils.load(headImg, item.getAvatar() == null ? "" : Const.IMAGE_HOST+item.getAvatar(), 0, 0, false, null);
    }
}
