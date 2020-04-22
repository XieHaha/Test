package com.keydom.ih_common.avchatkit.selector;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.ih_common.R;
import com.keydom.ih_common.utils.GlideUtils;
import com.netease.nimlib.sdk.uinfo.model.UserInfo;

import java.util.List;

/**
 * @date 20/4/22 11:45
 * @des
 */
public class ContactSelectAdapter extends BaseQuickAdapter<UserInfo, BaseViewHolder> {
    private List<String> selectIds;

    public ContactSelectAdapter(@Nullable List<UserInfo> data) {
        super(R.layout.item_contact_select, data);
    }

    public void setSelectId(List<String> selectIds) {
        this.selectIds = selectIds;
    }

    @Override
    protected void convert(BaseViewHolder helper, UserInfo item) {
        helper.setText(R.id.count_tv, item.getName());
        ImageView header = helper.getView(R.id.iv_avatar);
        ImageView selectImage = helper.getView(R.id.iv_select);
        GlideUtils.loadWithBorder(header, item.getAvatar());
        if (selectIds != null && selectIds.contains(item.getAccount())) {
            selectImage.setSelected(true);
        } else {
            selectImage.setSelected(false);
        }
    }
}
