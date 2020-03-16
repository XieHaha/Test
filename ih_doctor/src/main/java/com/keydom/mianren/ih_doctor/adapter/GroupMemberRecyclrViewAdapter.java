package com.keydom.mianren.ih_doctor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.ih_common.view.CircleImageView;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.my_doctor_or_nurse.DoctorOrNurseDetailActivity;
import com.keydom.mianren.ih_doctor.bean.DeptDoctorBean;
import com.keydom.mianren.ih_doctor.constant.Const;
import com.keydom.mianren.ih_doctor.m_interface.SingleClick;

import java.util.List;

/**
 * @Name：com.kentra.yxyz.adapter
 * @Description：团队成员适配器
 * @Author：song
 * @Date：18/11/6 下午6:52
 * 修改人：xusong
 * 修改时间：18/11/6 下午6:52
 */
public class GroupMemberRecyclrViewAdapter extends BaseEmptyAdapter<DeptDoctorBean> {


    public GroupMemberRecyclrViewAdapter(Context context, List<DeptDoctorBean> data) {
        super(data, context);

    }


    @Override
    public RecyclerView.ViewHolder createMyViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.group_member_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void bindMyViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((GroupMemberRecyclrViewAdapter.ViewHolder) holder).bind(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView userIcon;
        public TextView userName;

        public ViewHolder(View itemView) {
            super(itemView);
            userIcon = itemView.findViewById(R.id.user_icon);
            userName = itemView.findViewById(R.id.user_name);

        }

        public void bind(final int position) {
            DeptDoctorBean bean = mDatas.get(position);
            GlideUtils.load(userIcon, Const.IMAGE_HOST + bean.getAvatar(), 0, 0, false, null);
            userName.setText(bean.getName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @SingleClick(1000)
                @Override
                public void onClick(View v) {
//                    ToastUtil.showMessage(mContext, "跳转到医生详情页面");
                    DoctorOrNurseDetailActivity.startNursePage(mContext,bean.getUuid());
                }
            });
        }
    }
}
