package com.keydom.mianren.ih_doctor.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.keydom.ih_common.im.ImClient;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.online_triage.TriageOrderDetailActivity;
import com.keydom.mianren.ih_doctor.bean.InquiryBean;
import com.keydom.mianren.ih_doctor.m_interface.SingleClick;

import java.util.List;

/**
 * @Name：com.kentra.yxyz.adapter
 * @Description：问诊单列表适配器
 * @Author：song
 * @Date：18/11/6 下午6:52
 * 修改人：xusong
 * 修改时间：18/11/6 下午6:52
 */
public class VIPDiagnoseOrderRecyclrViewAdapter extends BaseEmptyAdapter<InquiryBean> {
    public static final String IS_ORDER = "is_order";
    private static final int NORMAL_VIEW = 0;
    private static final int FOOT_VIEW = 1;

    public VIPDiagnoseOrderRecyclrViewAdapter(Context context, List<InquiryBean> data) {
        super(data, context);
    }


    @Override
    public RecyclerView.ViewHolder createMyViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.vip_diagnose_order_item, parent,
                false);

        return new ViewHolder(view);
    }

    private class FootHolder extends RecyclerView.ViewHolder {
        public FootHolder(View itemView) {
            super(itemView);
        }
    }

    public void showNotAccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("未开通服务");
        builder.setMessage("您暂未开通该服务，无法使用这个功能！");
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.create().show();
    }

    @Override
    public void bindMyViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((VIPDiagnoseOrderRecyclrViewAdapter.ViewHolder) holder).bind(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView orderStatusTv;
        public TextView dateTv;
        public TextView patientInfoTv;
        public TextView vipDescTv;
        public TextView sendTv;
        public TextView vipAcceptTv;


        public ViewHolder(View itemView) {
            super(itemView);

            orderStatusTv = itemView.findViewById(R.id.vip_diag_order_status_tv);
            dateTv = itemView.findViewById(R.id.vip_diag_order_item_date_tv);
            patientInfoTv = itemView.findViewById(R.id.vip_diag_order_item_patient_info_tv);
            vipDescTv = itemView.findViewById(R.id.vip_diag_order_item_vip_desc_tv);
            sendTv = itemView.findViewById(R.id.vip_diag_order_item_send_tv);
            vipAcceptTv = itemView.findViewById(R.id.vip_diag_order_item_vip_accept_tv);
        }

        public void bind(final int position) {
            final InquiryBean bean = mDatas.get(position);

            if (null != bean) {

                StringBuilder stringBuilder = new StringBuilder();

                if (!TextUtils.isEmpty(bean.getName())) {
                    stringBuilder.append(bean.getName() + "、");
                }

                stringBuilder.append(bean.getAge() + "、");
                stringBuilder.append(CommonUtils.getSex(bean.getSex()));


                patientInfoTv.setText(stringBuilder.toString());

                if (!TextUtils.isEmpty(bean.getConditionDesc())) {
                    vipDescTv.setText(bean.getConditionDesc());
                }


                Drawable img;
                orderStatusTv.setVisibility(View.VISIBLE);
                /**
                 * 0未支付 1 待接收 2问诊中 3审核不通过 4 待转诊确认 5 待评价 7完成 -1已取消
                 */
                switch (bean.getState()) {

                    case 0:
                        img = mContext.getResources().getDrawable(R.mipmap.patient_cicle_yellow);
                        img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
                        orderStatusTv.setCompoundDrawables(img, null, null, null);
                        orderStatusTv.setTextColor(mContext.getResources().getColor(R.color.diagnose_font_yellow));
                        orderStatusTv.setText("未支付");
                        break;
                    case 1:
                        img = mContext.getResources().getDrawable(R.mipmap.patient_cicle_yellow);
                        img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
                        orderStatusTv.setCompoundDrawables(img, null, null, null);
                        orderStatusTv.setTextColor(mContext.getResources().getColor(R.color.diagnose_font_yellow));
                        if (bean.getType() == 4) {
                            orderStatusTv.setText("待接收");
                        } else {
                            orderStatusTv.setText("待接诊");
                        }

                        break;
                    case 2:
                        img = mContext.getResources().getDrawable(R.mipmap.patient_circle_green);
                        img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
                        orderStatusTv.setCompoundDrawables(img, null, null, null);
                        orderStatusTv.setTextColor(mContext.getResources().getColor(R.color.font_green));
                        orderStatusTv.setText("问诊中");
                        break;
                    case 3:
                        img = mContext.getResources().getDrawable(R.mipmap.patient_cicle_red);
                        img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
                        orderStatusTv.setCompoundDrawables(img, null, null, null);
                        orderStatusTv.setTextColor(mContext.getResources().getColor(R.color.diagnose_font_red));
                        orderStatusTv.setText("审核不通过");
                        break;
                    case 4:
                        img = mContext.getResources().getDrawable(R.mipmap.patient_circle_green);
                        img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
                        orderStatusTv.setCompoundDrawables(img, null, null, null);
                        orderStatusTv.setTextColor(mContext.getResources().getColor(R.color.font_green));
                        orderStatusTv.setText("问诊中");
                        break;
                    case 5:
                        img = mContext.getResources().getDrawable(R.mipmap.patient_circle_green);
                        img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
                        orderStatusTv.setCompoundDrawables(img, null, null, null);
                        orderStatusTv.setTextColor(mContext.getResources().getColor(R.color.font_green));
                        orderStatusTv.setText("问诊中");
                        break;
                    case 6:
                        img = mContext.getResources().getDrawable(R.mipmap.patient_circle_green);
                        img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
                        orderStatusTv.setCompoundDrawables(img, null, null, null);
                        orderStatusTv.setTextColor(mContext.getResources().getColor(R.color.font_green));
                        orderStatusTv.setText("问诊中");
                        break;
                    case 7:
                        img = mContext.getResources().getDrawable(R.mipmap.patient_cicle_yellow);
                        img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
                        orderStatusTv.setCompoundDrawables(img, null, null, null);
                        orderStatusTv.setTextColor(mContext.getResources().getColor(R.color.diagnose_font_yellow));
                        orderStatusTv.setText("待接诊");
                        break;
                    case 8:
                        img = mContext.getResources().getDrawable(R.mipmap.patient_cicle_yellow);
                        img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
                        orderStatusTv.setCompoundDrawables(img, null, null, null);
                        orderStatusTv.setTextColor(mContext.getResources().getColor(R.color.diagnose_font_yellow));
                        orderStatusTv.setText("待评价");
                        break;
                    case 9:
                        img = mContext.getResources().getDrawable(R.mipmap.patient_circle_green);
                        img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
                        orderStatusTv.setCompoundDrawables(img, null, null, null);
                        orderStatusTv.setTextColor(mContext.getResources().getColor(R.color.font_green));
                        orderStatusTv.setText("已完成");
                        break;
                    case -1:
                        img = mContext.getResources().getDrawable(R.mipmap.patient_cicle_red);
                        img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
                        orderStatusTv.setCompoundDrawables(img, null, null, null);
                        orderStatusTv.setTextColor(mContext.getResources().getColor(R.color.diagnose_font_red));
                        orderStatusTv.setText("已取消");
                        break;
                    default:
                        orderStatusTv.setVisibility(View.GONE);
                        break;
                }


                itemView.setOnClickListener(new View.OnClickListener() {
                    @SingleClick(1000)
                    @Override
                    public void onClick(View v) {
                        if (ImClient.getUserInfoProvider().getUserInfo(mDatas.get(position).getUserCode()) != null) {
                            TriageOrderDetailActivity.startWithAction(mContext, bean.getId());
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                            builder.setTitle("未获取到该用户信息");
                            builder.setMessage("请检查后重新尝试！");
                            builder.setNegativeButton("确定", (dialog, which) -> {
                            });
                            builder.create().show();
                        }
                    }
                });
            }

        }
    }
}
