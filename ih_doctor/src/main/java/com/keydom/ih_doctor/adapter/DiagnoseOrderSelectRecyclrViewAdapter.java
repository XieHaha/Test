package com.keydom.ih_doctor.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_common.view.CircleImageView;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.bean.InquiryBean;
import com.keydom.ih_doctor.constant.Const;
import com.keydom.ih_doctor.utils.ToastUtil;

import java.io.Serializable;
import java.util.List;

/**
 * @Name：com.kentra.yxyz.adapter
 * @Description：转诊－选择问诊单列表适配器
 * @Author：song
 * @Date：18/11/6 下午6:52
 * 修改人：xusong
 * 修改时间：18/11/6 下午6:52
 */
public class DiagnoseOrderSelectRecyclrViewAdapter extends BaseEmptyAdapter<InquiryBean> {

    private int doctorType = 3;

    public DiagnoseOrderSelectRecyclrViewAdapter(Context context, List<InquiryBean> data) {
        super(data, context);

    }

    public DiagnoseOrderSelectRecyclrViewAdapter(Context context, List<InquiryBean> data, int doctorType) {
        super(data, context);
        this.doctorType = doctorType;

    }

    @Override
    public RecyclerView.ViewHolder createMyViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.diagnose_order_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void bindMyViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((DiagnoseOrderSelectRecyclrViewAdapter.ViewHolder) holder).bind(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView userName, userAge, userSex, diagnoseDec, diagnoseTime;
        public ImageView delete;
        public CircleImageView userIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            userIcon = itemView.findViewById(R.id.user_icon);
            userName = itemView.findViewById(R.id.user_name);
            userAge = itemView.findViewById(R.id.user_age);
            userSex = itemView.findViewById(R.id.user_sex);
            diagnoseDec = itemView.findViewById(R.id.diagnose_dec);
            diagnoseTime = itemView.findViewById(R.id.diagnose_time);
            delete = itemView.findViewById(R.id.delete);
        }

        public void bind(final int position) {

            delete.setVisibility(View.GONE);
            userName.setText(mDatas.get(position).getName());
            userAge.setText(String.valueOf(mDatas.get(position).getAge()));
            userSex.setText(CommonUtils.getSex(mDatas.get(position).getSex()));
            diagnoseDec.setText(mDatas.get(position).getConditionDesc());
            diagnoseTime.setText(mDatas.get(position).getApplyTime());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mDatas.get(position).getType() != (doctorType - 1) && doctorType != 3) {
                        ToastUtil.shortToast(mContext, "选择的医生没有开通这个服务！");
                        return;
                    }
                    Intent intent = new Intent();
                    intent.putExtra(Const.DATA, (Serializable) mDatas.get(position));
                    ((Activity) mContext).setResult(Activity.RESULT_OK, intent);
                    ((Activity) mContext).finish();
                }
            });
        }
    }
}
