package com.keydom.mianren.ih_patient.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.ih_common.constant.Const;
import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.ih_common.view.CircleImageView;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.bean.DiagnosesOrderBean;
import com.keydom.mianren.ih_patient.callback.SingleClick;

import java.util.List;

/**
 * 问诊订单适配器
 */
public class DiagnosesOrderAdapter extends BaseQuickAdapter<DiagnosesOrderBean, BaseViewHolder> {
    private onItemBtnClickListener iOnItemBtnClickListener;
    /**
     * item.getState()
     * 0 未支付
     * 1 待接收
     * 2 问诊中
     * 3 审核不通过
     * 4 待转诊确认
     * 5 医生发起结束问诊
     * 6 医生处置建议
     * 7 换诊
     * 8 待评价
     * 9 完成
     * -1 已取消
     */
    public final int unPay = 0;
    public final int waiteRecieve = 1;
    public final int diagnosing = 2;
    public final int unPassCheck = 3;
    public final int waiteChangeDiagnose = 4;
    public final int doctorCloseDiagnose = 5;
    public final int doctorMakePrescriptions = 6;
    public final int changDoctor = 7;
    public final int waiteEvaluate = 8;
    public final int complete = 9;
    public final int triage = 11;
    public final int consultationWait = 13;
    public final int consultationComplete = 14;
    public final int cancel = -1;

    /**
     * 构造方法
     */
    public DiagnosesOrderAdapter(@Nullable List<DiagnosesOrderBean> data,
                                 onItemBtnClickListener onItemBtnClickListener) {
        super(R.layout.diagnoses_order_item, data);
        this.iOnItemBtnClickListener = onItemBtnClickListener;
    }

    public interface onItemBtnClickListener {
        void onCancelDiagnosesClick(DiagnosesOrderBean item);

        void onPayClick(DiagnosesOrderBean item);

        void onCommentClick(DiagnosesOrderBean item);
    }


    @Override
    protected void convert(BaseViewHolder helper, DiagnosesOrderBean item) {
        CircleImageView headImg = helper.getView(R.id.doctor_head_img);
        GlideUtils.load(headImg, item.getAvatar() == null ? "" :
                Const.IMAGE_HOST + item.getAvatar(), 0, 0, false, null);
        TextView diagnosesTypeTv = helper.getView(R.id.diagnoses_type_tv);
        LinearLayout orderCompleteLayout = helper.getView(R.id.order_complete_layout);
        TextView backDiagnosesTv = helper.getView(R.id.back_diagnoses_tv);
        TextView changeDiagnosesTv = helper.getView(R.id.change_diagnoses_tv);
        TextView diagnosesDoctorChangeTv = helper.getView(R.id.diagnoses_doctor_change_tv);
        TextView waitPeopleTv = helper.getView(R.id.wait_people_tv);
        backDiagnosesTv.setOnClickListener(new View.OnClickListener() {
            @SingleClick(1000)
            @Override
            public void onClick(View view) {
                if (iOnItemBtnClickListener != null) {
                    iOnItemBtnClickListener.onCancelDiagnosesClick(item);
                }
            }
        });
        if (item.getState() == waiteRecieve && item.getWaitInquiryCount() > 0) {
            waitPeopleTv.setVisibility(View.VISIBLE);
            waitPeopleTv.setText("前面待诊人数:" + item.getWaitInquiryCount());
        } else {
            waitPeopleTv.setVisibility(View.GONE);
        }
        if (item.getState() == unPay || item.getState() == waiteRecieve || item.getState() == changDoctor) {
            backDiagnosesTv.setVisibility(View.VISIBLE);
        } else {
            backDiagnosesTv.setVisibility(View.GONE);
        }
        TextView payTv = helper.getView(R.id.pay_tv);
        payTv.setOnClickListener(new View.OnClickListener() {
            @SingleClick(1000)
            @Override
            public void onClick(View view) {
                if (iOnItemBtnClickListener != null) {
                    iOnItemBtnClickListener.onPayClick(item);
                }
            }
        });
        if (item.getState() == unPay) {
            payTv.setVisibility(View.VISIBLE);
        } else if (item.getState() != unPay && item.getIsSubOrderUnPay() == 1) {
            payTv.setVisibility(View.VISIBLE);
        } else {
            payTv.setVisibility(View.GONE);
        }

        //        if(item.getState()==1)
        //            wait_people_tv.setVisibility(View.VISIBLE);
        //        else
        //            wait_people_tv.setVisibility(View.GONE);
        if (item.getState() == changDoctor) {
            changeDiagnosesTv.setVisibility(View.VISIBLE);
        } else {
            changeDiagnosesTv.setVisibility(View.GONE);
        }

        if (item.getState() == waiteChangeDiagnose) {
            diagnosesDoctorChangeTv.setVisibility(View.VISIBLE);
        } else {
            diagnosesDoctorChangeTv.setVisibility(View.GONE);
        }
        TextView commentTv = helper.getView(R.id.comment_tv);
        commentTv.setOnClickListener(new View.OnClickListener() {
            @SingleClick(1000)
            @Override
            public void onClick(View view) {
                if (iOnItemBtnClickListener != null) {
                    iOnItemBtnClickListener.onCommentClick(item);
                }
            }
        });

        if (item.getState() == waiteEvaluate) {
            commentTv.setVisibility(View.VISIBLE);
        } else {
            commentTv.setVisibility(View.GONE);
        }

        LinearLayout stateLayout = helper.getView(R.id.state_layout);
        if (item.getState() == cancel) {
            stateLayout.setVisibility(View.VISIBLE);
            TextView stateTv = helper.getView(R.id.state_tv);
            if (item.getRefundState() == 0) {
                stateTv.setText("已取消");
            } else if (item.getRefundState() == 1) {
                stateTv.setText("退款中");
            } else {
                stateTv.setText("已退款");
            }
        } else {
            stateLayout.setVisibility(View.GONE);
        }


        if (item.getState() == complete || item.getState() == waiteEvaluate) {
            orderCompleteLayout.setVisibility(View.VISIBLE);
        } else {
            orderCompleteLayout.setVisibility(View.GONE);
        }
        if (item.getInquisitionType() == 0) {
            diagnosesTypeTv.setText("图文问诊");
            Drawable leftImg = ContextCompat.getDrawable(mContext, R.mipmap.photo_diagnoses_icon);
            leftImg.setBounds(0, 0, leftImg.getMinimumWidth(), leftImg.getMinimumHeight());
            diagnosesTypeTv.setCompoundDrawables(leftImg, null, null, null);
        } else {
            diagnosesTypeTv.setText("视频问诊");
            Drawable leftImg = ContextCompat.getDrawable(mContext, R.mipmap.video_diagnoses_icon);
            leftImg.setBounds(0, 0, leftImg.getMinimumWidth(), leftImg.getMinimumHeight());
            diagnosesTypeTv.setCompoundDrawables(leftImg, null, null, null);
        }
        //        if (TextUtils.isEmpty(item.getGroupTid())) {
        helper.setText(R.id.doctor_name_tv, TextUtils.isEmpty(item.getDoctorName()) ? "等待医生接诊" :
                item.getDoctorName());
        //        } else {
        //            Team team = ImClient.getTeamProvider().getTeamById(item.getGroupTid());
        //            if (team == null) {
        //                helper.setText(R.id.doctor_name_tv, "临时会诊");
        //            } else {
        //                helper.setText(R.id.doctor_name_tv, team.getName());
        //            }
        //        }
        helper.setText(R.id.doctor_depart_tv, item.getDeptName())
                .setText(R.id.diagnoses_desc_tv, TextUtils.isEmpty(item.getConditionDesc()) ?
                        "无" : item.getConditionDesc())
                .setText(R.id.time_tv, item.getApplyTime());
        // .setText(R.id.wait_people_tv,item.getInquisitionType())
    }


}
