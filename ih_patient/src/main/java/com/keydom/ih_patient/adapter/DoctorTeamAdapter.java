package com.keydom.ih_patient.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ScreenUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.ih_common.constant.Const;
import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.ih_common.view.CircleImageView;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.my_doctor_or_nurse.DoctorOrNurseDetailActivity;
import com.keydom.ih_patient.bean.DoctorTeamItem;

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
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(mContext, DoctorOrNurseDetailActivity.class);
//                i.putExtra(DoctorOrNurseDetailActivity.CODE, teamItem.getUuid());
//                ActivityUtils.startActivity(i);
                DoctorOrNurseDetailActivity.startDoctorPage(mContext,DoctorOrNurseDetailActivity.DOCTOR,teamItem.getUuid());
            }
        });
        GlideUtils.load((CircleImageView)helper.getView(R.id.head_img), Const.IMAGE_HOST+teamItem.getAvatar(),0,0,true,null);
    }
}
