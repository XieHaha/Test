package com.keydom.ih_doctor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.ih_doctor.MyApplication;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.my_doctor_or_nurse.DoctorOrNurseDetailActivity;
import com.keydom.ih_doctor.activity.personal.ArticleListActivity;
import com.keydom.ih_doctor.activity.personal.MyAttentionActivity;
import com.keydom.ih_doctor.bean.AttentionBean;
import com.keydom.ih_doctor.constant.Const;
import com.keydom.ih_doctor.constant.TypeEnum;
import com.keydom.ih_doctor.m_interface.SingleClick;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.List;

/**
 * @Name：com.kentra.yxyz.adapter
 * @Description：我的关注列表适配器
 * @Author：song
 * @Date：18/11/6 下午6:52
 * 修改人：xusong
 * 修改时间：18/11/6 下午6:52
 */
public class AttentionRecyclrViewAdapter extends BaseEmptyAdapter<AttentionBean> {


    public AttentionRecyclrViewAdapter(List<AttentionBean> mDatas, Context context) {
        super(mDatas, context);
    }

    @Override
    public RecyclerView.ViewHolder createMyViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.personal_attention_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void bindMyViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((AttentionRecyclrViewAdapter.ViewHolder) holder).bind(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView userIconIv = null;
        public TextView userNameTv = null;
        public TextView userPositionTv = null;
        public TextView userHospitalTv = null;
        public TextView userDepartmentTv = null;
        public Button delete=null;
        public RelativeLayout mainLayout=null;
        public ViewHolder(View itemView) {
            super(itemView);
            userIconIv = (ImageView) itemView.findViewById(R.id.user_icon);
            userNameTv = (TextView) itemView.findViewById(R.id.user_name);
            userPositionTv = (TextView) itemView.findViewById(R.id.user_position);
            userHospitalTv = (TextView) itemView.findViewById(R.id.user_hospital);
            userDepartmentTv = (TextView) itemView.findViewById(R.id.user_department);
            delete=itemView.findViewById(R.id.delete);
            mainLayout=itemView.findViewById(R.id.main_layout);

        }

        public void bind(final int position) {
            GlideUtils.load(userIconIv, Const.IMAGE_HOST + mDatas.get(position).getAvatar(), 0, 0, false, null);
            userHospitalTv.setText(mDatas.get(position).getHospital());
            userPositionTv.setText(mDatas.get(position).getJobTitle());
            userNameTv.setText(mDatas.get(position).getName());
            userDepartmentTv.setText(mDatas.get(position).getDepartment());

            delete.setOnClickListener(new View.OnClickListener() {
                @SingleClick(1000)
                @Override
                public void onClick(View v) {

                    new GeneralDialog(mContext, "确定取消关注?", new GeneralDialog.OnCloseListener() {
                        @Override
                        public void onCommit() {
                            HashMap<String, Object> map = new HashMap<>();
                            map.put("doctorId", MyApplication.userInfo.getId());
                            map.put("befocusedDoctorId", mDatas.get(position).getId());
                            map.put("isAttention", 0);
                            ((MyAttentionActivity) mContext).getController().cancelAttention(position, map);
                        }
                    }).show();
                }
            });
            mainLayout.setOnClickListener(new View.OnClickListener() {
                @SingleClick(1000)
                @Override
                public void onClick(View view) {
                    Logger.e("点击了第"+position+"位置");
                    ArticleListActivity.start(mContext, TypeEnum.OHTERSARTICLE,mDatas.get(position).getUuid(),mDatas.get(position).getName());

                }
            });
            userIconIv.setOnClickListener(new View.OnClickListener() {
                @SingleClick(1000)
                @Override
                public void onClick(View v) {
                    Logger.e("点击了第"+position+"位置的头像");
                    DoctorOrNurseDetailActivity.startNursePage(mContext, mDatas.get(position).getUuid());

                    if (!Const.CHECK_AND_ACCEP) {

                    }

                }
            });

        }
    }
}
