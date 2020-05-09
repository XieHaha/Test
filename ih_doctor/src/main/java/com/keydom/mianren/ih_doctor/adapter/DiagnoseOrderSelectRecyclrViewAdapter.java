package com.keydom.mianren.ih_doctor.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.CircleImageView;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.bean.InquiryBean;
import com.keydom.mianren.ih_doctor.constant.Const;
import com.keydom.mianren.ih_doctor.m_interface.SingleClick;

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
        public TextView userName, userAge, userSex, diagnoseDec, diagnoseTime,order_type_tv,order_status;
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
            order_type_tv=itemView.findViewById(R.id.order_type_tv);
            order_status=itemView.findViewById(R.id.order_status);
        }

        public void bind(final int position) {
            GlideUtils.load(userIcon, Const.IMAGE_HOST + mDatas.get(position).getUserAvatar(), 0, R.mipmap.user_icon, false, null);

            delete.setVisibility(View.GONE);
            order_status.setVisibility(View.GONE);
            userName.setText(mDatas.get(position).getName());
            userAge.setText(mDatas.get(position).getAge());
            userSex.setText(CommonUtils.getSex(mDatas.get(position).getSex()));
            diagnoseDec.setText(mDatas.get(position).getConditionDesc());
            diagnoseTime.setText(mDatas.get(position).getApplyTime());
            itemView.setOnClickListener(new View.OnClickListener() {
                @SingleClick(1000)
                @Override
                public void onClick(View v) {
                    if (mDatas.get(position).getType() != (doctorType - 1) && doctorType != 3) {
                        ToastUtil.showMessage(mContext, "选择的医生没有开通这个服务！");
                        return;
                    }
                    Intent intent = new Intent();
                    intent.putExtra(Const.DATA, (Serializable) mDatas.get(position));
                    ((Activity) mContext).setResult(Activity.RESULT_OK, intent);
                    ((Activity) mContext).finish();
                }
            });

            if(mDatas.get(position).getInquisitionType()==0){
                order_type_tv.setText("图文问诊");
                Drawable rightDrawable = mContext.getResources().getDrawable(R.mipmap.diagnose_illustration);
                rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
                order_type_tv.setCompoundDrawables(rightDrawable,null,null,null);
            }else {
                order_type_tv.setText("视频问诊");
                Drawable rightDrawable = mContext.getResources().getDrawable(R.mipmap.video_diagnoses_icon);
                rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
                order_type_tv.setCompoundDrawables(rightDrawable,null,null,null);

            }
        }
    }
}
