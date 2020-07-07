package com.keydom.mianren.ih_doctor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.doctor_cooperation.DianoseCaseDetailActivity;
import com.keydom.mianren.ih_doctor.bean.DiagnoseRecoderBean;
import com.keydom.mianren.ih_doctor.constant.Const;
import com.keydom.mianren.ih_doctor.m_interface.SingleClick;
import com.keydom.mianren.ih_doctor.utils.CalculateTimeUtils;

import java.util.List;

/**
 * @Name：com.kentra.yxyz.adapter
 * @Description：问诊记录适配器
 * @Author：song
 * @Date：18/11/6 下午6:52
 * 修改人：xusong
 * 修改时间：18/11/6 下午6:52
 */
public class DiagnoseRecoderRecyclrViewAdapter extends BaseEmptyAdapter<DiagnoseRecoderBean> {

    public DiagnoseRecoderRecyclrViewAdapter(Context context, List<DiagnoseRecoderBean> data) {
        super(data, context);

    }

    @Override
    public RecyclerView.ViewHolder createMyViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.diagnose_recoder_item_layout,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void bindMyViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((DiagnoseRecoderRecyclrViewAdapter.ViewHolder) holder).bind(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView doctorName, timeTv, userName, userDiagnose, sex, age;
        public ImageView typePoint;

        public ViewHolder(View itemView) {
            super(itemView);
            doctorName = itemView.findViewById(R.id.doctor_name);
            timeTv = itemView.findViewById(R.id.time_tv);
            userName = itemView.findViewById(R.id.user_name);
            userDiagnose = itemView.findViewById(R.id.user_diagnose);
            sex = itemView.findViewById(R.id.sex);
            age = itemView.findViewById(R.id.age);
            typePoint = itemView.findViewById(R.id.type_point);
        }

        public void bind(final int position) {
            final DiagnoseRecoderBean bean = mDatas.get(position);
            doctorName.setText("问诊医生：" + bean.getDoctor());
            timeTv.setText(CalculateTimeUtils.getYMDTime(bean.getTime()));
            userName.setText(bean.getPatient());
            userDiagnose.setText(bean.getContent());
            sex.setText(CommonUtils.getSex(bean.getSex()));
            age.setText(bean.getAge());
            if (bean.getSex() == Const.MALE) {
                typePoint.setImageResource(R.mipmap.point_blue);
            } else if (bean.getSex() == Const.FEMALE) {
                typePoint.setImageResource(R.mipmap.point_green);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @SingleClick(1000)
                @Override
                public void onClick(View v) {
                    if (bean.getMedicalId() != null || !"".equals(bean.getMedicalId())) {
                        DianoseCaseDetailActivity.start(mContext, bean.getMedicalId());
                    } else {
                        ToastUtil.showMessage(mContext, "查询不到问诊病历记录");
                    }
                }
            });
        }
    }
}
