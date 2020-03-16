package com.keydom.mianren.ih_patient.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.bean.ManagerUserBean;

import java.util.List;

/**
 * @Description：就诊人适配器
 * @Author：song
 * @Date：18/11/6 下午6:52
 * 修改人：xusong
 * 修改时间：18/11/6 下午6:52
 */
public class ManageUserNewAdapter extends BaseQuickAdapter<ManagerUserBean, BaseViewHolder> {
    private long id;

    /**
     * 构造方法
     */
    public ManageUserNewAdapter(@Nullable List<ManagerUserBean> data) {
        super(R.layout.item_manager_user, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final ManagerUserBean item) {
        helper.setText(R.id.manager_user_name_tv, item.getName())
                .setText(R.id.manager_user_card_tv, item.getCardId())
                .setVisible(R.id.manager_user_current_tv, id == item.getId());
    }

    public void setId(long id) {
        this.id = id;
    }
}
