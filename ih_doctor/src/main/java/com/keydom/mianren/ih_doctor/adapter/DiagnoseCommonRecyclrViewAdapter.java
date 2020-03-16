package com.keydom.mianren.ih_doctor.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.doctor_cooperation.DiagnoseOrderDetailActivity;
import com.keydom.mianren.ih_doctor.bean.ChangeDiagnoseRecoderBean;
import com.keydom.mianren.ih_doctor.constant.TypeEnum;
import com.keydom.mianren.ih_doctor.m_interface.SingleClick;

import java.util.List;

/**
 * @Name：com.kentra.yxyz.adapter
 * @Description：问诊记录适配器
 * @Author：song
 * @Date：18/11/6 下午6:52
 * 修改人：xusong
 * 修改时间：18/11/6 下午6:52
 */
public class DiagnoseCommonRecyclrViewAdapter extends BaseEmptyAdapter<ChangeDiagnoseRecoderBean> {


    private TypeEnum mType;

    public DiagnoseCommonRecyclrViewAdapter(Context context, List<ChangeDiagnoseRecoderBean> data, TypeEnum type) {
        super(data, context);
        this.mType = type;

    }


    @Override
    public RecyclerView.ViewHolder createMyViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.change_dianose_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void bindMyViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((DiagnoseCommonRecyclrViewAdapter.ViewHolder) holder).bind(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView timeTv, diagnoseStatus, userName, diagnoseDec, apply, receive;

        public ViewHolder(View itemView) {
            super(itemView);
            timeTv = itemView.findViewById(R.id.time_tv);
            diagnoseStatus = itemView.findViewById(R.id.diagnose_status);
            userName = itemView.findViewById(R.id.user_name);
            diagnoseDec = itemView.findViewById(R.id.diagnose_dec);
            apply = itemView.findViewById(R.id.apply);
            receive = itemView.findViewById(R.id.receive);

        }

        public void bind(final int position) {
            final ChangeDiagnoseRecoderBean bean = mDatas.get(position);
            timeTv.setText(bean.getReferralTime());

            if (bean.getState() == -1) {
                diagnoseStatus.setText("已拒绝");
                diagnoseStatus.setTextColor(mContext.getResources().getColor(R.color.font_red));
                Drawable img = mContext.getResources().getDrawable(R.mipmap.patient_cicle_red);
                img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
                diagnoseStatus.setCompoundDrawables(img, null, null, null);


            } else if (bean.getState() == 0) {
                diagnoseStatus.setText("待接收");
                diagnoseStatus.setTextColor(mContext.getResources().getColor(R.color.diagnose_font_yellow));
                Drawable img = mContext.getResources().getDrawable(R.mipmap.patient_cicle_yellow);
                img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
                diagnoseStatus.setCompoundDrawables(img, null, null, null);
            } else {
                diagnoseStatus.setText("已接收");
                diagnoseStatus.setTextColor(mContext.getResources().getColor(R.color.font_recoder_status_green));
                Drawable img = mContext.getResources().getDrawable(R.mipmap.patient_cicle_green);
                img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
                diagnoseStatus.setCompoundDrawables(img, null, null, null);

            }

            userName.setText(bean.getPatientName() + "、" + CommonUtils.getSex(bean.getPatientSex()) + "、" + bean.getPatientAge() + "岁");
            diagnoseDec.setText(bean.getReferralExplain());
            receive.setText(bean.getChangeInfoDoctor() + "、" + (bean.getChangeInfoJobTitle() == null || "".equals(bean.getChangeInfoJobTitle()) ? "" : (bean.getChangeInfoJobTitle() + "、")) + bean.getChangeInfoDept());
            apply.setText(bean.getDoctor() + "、" + (bean.getJobTitle() == null || "".equals(bean.getJobTitle()) ? "" : (bean.getJobTitle() + "、")) + bean.getDept());
            itemView.setOnClickListener(new View.OnClickListener() {
                @SingleClick(1000)
                @Override
                public void onClick(View v) {
                    if (mType == TypeEnum.DIAGNOSE_CHANGE_RECEIVE) {
                        DiagnoseOrderDetailActivity.startWithAction(mContext, bean.getId());
                    } else {
                        DiagnoseOrderDetailActivity.startCommon(mContext, bean.getId());
                    }

                }
            });
        }
    }
}
