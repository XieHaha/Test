package com.keydom.mianren.ih_doctor.adapter;

import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ScreenUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.ih_common.constant.Const;
import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.ih_common.view.CircleImageView;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.bean.DoctorTeamItem;

import java.util.List;

/**
 * created date: 2019/4/26 on 10:54
 * des:医生团队适配器
 */
public class DoctorTeamAdapter extends BaseQuickAdapter<DoctorTeamItem,BaseViewHolder> {
    public DoctorTeamAdapter(@Nullable List<DoctorTeamItem> data) {
        super(R.layout.item_doctor_detail_team,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DoctorTeamItem teamItem) {
        ViewGroup.LayoutParams layoutParams = helper.getView(R.id.group).getLayoutParams();
        layoutParams.width = (ScreenUtils.getScreenWidth()) / 3;
        helper.getView(R.id.group).setLayoutParams(layoutParams);
        helper.setText(R.id.job,teamItem.getJobTitle())
                .setText(R.id.name,teamItem.getName())
                .addOnClickListener(R.id.group);
        GlideUtils.load((CircleImageView)helper.getView(R.id.head_img), Const.IMAGE_HOST+teamItem.getAvatar(),0,0,true,null);
    }
}
