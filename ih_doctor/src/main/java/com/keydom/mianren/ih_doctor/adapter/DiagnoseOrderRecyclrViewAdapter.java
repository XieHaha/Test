package com.keydom.mianren.ih_doctor.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keydom.ih_common.im.ImClient;
import com.keydom.ih_common.im.config.ImConstants;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.ih_common.utils.SharePreferenceManager;
import com.keydom.ih_common.view.CircleImageView;
import com.keydom.mianren.ih_doctor.MyApplication;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.prescription_check.DiagnosePrescriptionActivity;
import com.keydom.mianren.ih_doctor.bean.InquiryBean;
import com.keydom.mianren.ih_doctor.constant.Const;
import com.keydom.mianren.ih_doctor.constant.ServiceConst;
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
public class DiagnoseOrderRecyclrViewAdapter extends BaseEmptyAdapter<InquiryBean> {
    public static final String IS_ORDER = "is_order";
    private static final int NORMAL_VIEW = 0;
    private static final int FOOT_VIEW = 1;

    public DiagnoseOrderRecyclrViewAdapter(Context context, List<InquiryBean> data) {
        super(data, context);
    }


    @Override
    public RecyclerView.ViewHolder createMyViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.diagnose_order_item, parent, false);

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
        ((DiagnoseOrderRecyclrViewAdapter.ViewHolder) holder).bind(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView userName, userAge, userSex, diagnoseDec, diagnoseTime, orderStatus,
                checkIdea, updatePrescription, orderTypeTv;
        public ImageView delete;
        public CircleImageView userIcon;
        public RelativeLayout checkIdeaRl;

        public ViewHolder(View itemView) {
            super(itemView);
            checkIdeaRl = itemView.findViewById(R.id.check_idea_rl);
            checkIdea = itemView.findViewById(R.id.check_idea);
            updatePrescription = itemView.findViewById(R.id.update_prescription);
            userIcon = itemView.findViewById(R.id.user_icon);
            userName = itemView.findViewById(R.id.user_name);
            userAge = itemView.findViewById(R.id.user_age);
            userSex = itemView.findViewById(R.id.user_sex);
            diagnoseDec = itemView.findViewById(R.id.diagnose_dec);
            diagnoseTime = itemView.findViewById(R.id.diagnose_time);
            orderTypeTv = itemView.findViewById(R.id.order_type_tv);
            delete = itemView.findViewById(R.id.delete);
            orderStatus = itemView.findViewById(R.id.order_status);
        }

        public void bind(final int position) {

            delete.setVisibility(View.GONE);
            checkIdeaRl.setVisibility(View.GONE);
            final InquiryBean bean = mDatas.get(position);
            GlideUtils.load(userIcon, Const.IMAGE_HOST + bean.getUserAvatar(), 0, 0, false, null);
            userName.setText(bean.getName());
            if (bean.getIsVip() == 1) {
                userName.setTextColor(ContextCompat.getColor(mContext, R.color.nursing_status_red));
                userName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                Drawable leftImg = ContextCompat.getDrawable(mContext, R.mipmap.icon_vip_label);
                leftImg.setBounds(0, 0, leftImg.getMinimumWidth(), leftImg.getMinimumHeight());
                userName.setCompoundDrawables(leftImg, null, null, null);
                userName.setBackgroundResource(R.drawable.corner3_ffeb3b_bg);
            } else {
                userName.setTextColor(ContextCompat.getColor(mContext, R.color.other_login_color));
                userName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                userName.setCompoundDrawables(null, null, null, null);
                userName.setBackground(null);
            }

            userAge.setText(bean.getAge());
            userSex.setText(CommonUtils.getSex(bean.getSex()));
            diagnoseDec.setText(bean.getConditionDesc());
            diagnoseTime.setText(bean.getApplyTime());
            checkIdea.setText(bean.getRemark());
            Drawable img;
            if (bean.getSource() == 1) {
                Drawable leftImg = ContextCompat.getDrawable(mContext,
                        R.mipmap.diagnose_change_icon);
                leftImg.setBounds(0, 0, leftImg.getMinimumWidth(), leftImg.getMinimumHeight());
                orderTypeTv.setText("转诊");
                orderTypeTv.setTextColor(ContextCompat.getColor(mContext, R.color.income_bg));
                orderTypeTv.setCompoundDrawables(leftImg, null, null, null);
            } else {
                if (bean.getInquisitionType() == 0) {
                    Drawable leftImg = ContextCompat.getDrawable(mContext,
                            R.mipmap.diagnose_illustration);
                    leftImg.setBounds(0, 0, leftImg.getMinimumWidth(), leftImg.getMinimumHeight());
                    if (SharePreferenceManager.getRoleId() == Const.ROLE_NURSE) {
                        orderTypeTv.setText("图文咨询");
                    } else {
                        orderTypeTv.setText("图文问诊");
                    }
                    orderTypeTv.setTextColor(ContextCompat.getColor(mContext,
                            R.color.font_order_type_image_with_video));
                    orderTypeTv.setCompoundDrawables(leftImg, null, null, null);
                } else {
                    Drawable leftImg = ContextCompat.getDrawable(mContext,
                            R.mipmap.diagnose_change_icon);
                    leftImg.setBounds(0, 0, leftImg.getMinimumWidth(), leftImg.getMinimumHeight());
                    orderTypeTv.setCompoundDrawables(leftImg, null, null, null);
                    if (SharePreferenceManager.getRoleId() == Const.ROLE_NURSE) {
                        orderTypeTv.setText("视频咨询");
                    } else {
                        orderTypeTv.setText("视频问诊");
                    }
                    orderTypeTv.setTextColor(ContextCompat.getColor(mContext, R.color.income_bg));
                }
            }
            orderStatus.setVisibility(View.VISIBLE);
            /**
             * 0未支付 1 待接收 2问诊中 3审核不通过 4 待转诊确认 5 待评价 7完成 -1已取消
             */
            switch (bean.getState()) {
                case 0:
                    img = ContextCompat.getDrawable(mContext, R.mipmap.patient_cicle_yellow);
                    img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
                    orderStatus.setCompoundDrawables(img, null, null, null);
                    orderStatus.setTextColor(ContextCompat.getColor(mContext,
                            R.color.diagnose_font_yellow));
                    orderStatus.setText("未支付");
                    break;
                case 1:
                    img = ContextCompat.getDrawable(mContext, R.mipmap.patient_cicle_yellow);
                    img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
                    orderStatus.setCompoundDrawables(img, null, null, null);
                    orderStatus.setTextColor(ContextCompat.getColor(mContext,
                            R.color.diagnose_font_yellow));
                    if (bean.getType() == 4) {
                        orderStatus.setText("待接收");
                    } else {
                        orderStatus.setText("待接诊");
                    }
                    break;
                case 2:
                    img = ContextCompat.getDrawable(mContext, R.mipmap.patient_circle_green);
                    img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
                    orderStatus.setCompoundDrawables(img, null, null, null);
                    orderStatus.setTextColor(ContextCompat.getColor(mContext, R.color.font_green));
                    orderStatus.setText("问诊中");
                    break;
                case 3:
                    checkIdeaRl.setVisibility(View.VISIBLE);
                    img = ContextCompat.getDrawable(mContext, R.mipmap.patient_cicle_red);
                    img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
                    orderStatus.setCompoundDrawables(img, null, null, null);
                    orderStatus.setTextColor(ContextCompat.getColor(mContext,
                            R.color.diagnose_font_red));
                    orderStatus.setText("审核不通过");
                    break;
                case 4:
                    img = ContextCompat.getDrawable(mContext, R.mipmap.patient_circle_green);
                    img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
                    orderStatus.setCompoundDrawables(img, null, null, null);
                    orderStatus.setTextColor(ContextCompat.getColor(mContext, R.color.font_green));
                    orderStatus.setText("问诊中");
                    break;
                case 5:
                    img = ContextCompat.getDrawable(mContext, R.mipmap.patient_circle_green);
                    img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
                    orderStatus.setCompoundDrawables(img, null, null, null);
                    orderStatus.setTextColor(ContextCompat.getColor(mContext, R.color.font_green));
                    orderStatus.setText("问诊中");
                    break;
                case 6:
                    img = ContextCompat.getDrawable(mContext, R.mipmap.patient_cicle_green_1);
                    img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
                    orderStatus.setCompoundDrawables(img, null, null, null);
                    orderStatus.setTextColor(ContextCompat.getColor(mContext, R.color.font_green));
                    orderStatus.setText("问诊中");
                    break;
                case 7:
                    img = ContextCompat.getDrawable(mContext, R.mipmap.patient_cicle_yellow);
                    img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
                    orderStatus.setCompoundDrawables(img, null, null, null);
                    orderStatus.setTextColor(ContextCompat.getColor(mContext,
                            R.color.diagnose_font_yellow));
                    orderStatus.setText("待接诊");
                    break;
                case 8:
                    img = ContextCompat.getDrawable(mContext, R.mipmap.patient_cicle_yellow);
                    img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
                    orderStatus.setCompoundDrawables(img, null, null, null);
                    orderStatus.setTextColor(ContextCompat.getColor(mContext,
                            R.color.diagnose_font_yellow));
                    orderStatus.setText("待评价");
                    break;
                case 9:
                    img = ContextCompat.getDrawable(mContext, R.mipmap.patient_circle_green);
                    img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
                    orderStatus.setCompoundDrawables(img, null, null, null);
                    orderStatus.setTextColor(ContextCompat.getColor(mContext, R.color.font_green));
                    orderStatus.setText("已完成");
                    break;
                case 11:
                    //分诊中
                    orderStatus.setText("分诊中");
                    break;
                case 13:
                    img = ContextCompat.getDrawable(mContext, R.mipmap.patient_cicle_green_1);
                    img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
                    orderStatus.setCompoundDrawables(img, null, null, null);
                    orderStatus.setText("会诊中");
                    orderStatus.setTextColor(ContextCompat.getColor(mContext, R.color.font_green));
                    break;
                case 14:
                    img = ContextCompat.getDrawable(mContext, R.mipmap.patient_cicle_green_1);
                    img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
                    orderStatus.setCompoundDrawables(img, null, null, null);
                    orderStatus.setTextColor(ContextCompat.getColor(mContext, R.color.font_green));
                    orderStatus.setText("会诊结束");
                    break;
                case 15:
                    img = ContextCompat.getDrawable(mContext, R.mipmap.patient_cicle_yellow);
                    img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
                    orderStatus.setCompoundDrawables(img, null, null, null);
                    orderStatus.setTextColor(ContextCompat.getColor(mContext,
                            R.color.diagnose_font_yellow));
                    orderStatus.setText("待会诊");
                    break;
                case -1:
                    img = ContextCompat.getDrawable(mContext, R.mipmap.patient_cicle_red);
                    img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
                    orderStatus.setCompoundDrawables(img, null, null, null);
                    orderStatus.setTextColor(ContextCompat.getColor(mContext,
                            R.color.diagnose_font_red));
                    orderStatus.setText("已取消");
                    break;
                default:
                    orderStatus.setVisibility(View.GONE);
                    break;
            }


            itemView.setOnClickListener(new View.OnClickListener() {
                @SingleClick(1000)
                @Override
                public void onClick(View v) {
                    InquiryBean inquiryBean = mDatas.get(position);

                    if (ImClient.getUserInfoProvider().getUserInfo(inquiryBean.getUserCode()) != null) {
                        Bundle bundle = new Bundle();
                        bundle.putBoolean(IS_ORDER, true);
                        bundle.putLong("orderId", inquiryBean.getId());
                        if (TextUtils.isEmpty(inquiryBean.getGroupTid())) {
                            ImClient.startConversation(mContext, inquiryBean.getUserCode(), bundle);
                        } else {
                            bundle.putBoolean(ImConstants.TEAM, true);
                            ImClient.startConversation(mContext, inquiryBean.getGroupTid(), bundle);
                        }
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setTitle("未获取到该用户信息");
                        builder.setMessage("请检查后重新尝试！");
                        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        builder.create().show();
                    }
                }
            });

            updatePrescription.setOnClickListener(new View.OnClickListener() {
                @SingleClick(1000)
                @Override
                public void onClick(View v) {
                    if (MyApplication.serviceEnable(new String[]{ServiceConst.DOCTOR_PRESCRIPTION_SERVICE_CODE, ServiceConst.MEDICINE_PRESCRIPTION_SERVICE_CODE})) {
                        DiagnosePrescriptionActivity.startUpdate(mContext,
                                bean.getPrescriptionId());
                    } else {
                        showNotAccessDialog();
                    }
                }
            });

        }
    }
}
