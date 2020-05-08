package com.keydom.mianren.ih_patient.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
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
    public final int consultationComplete= 14;
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
        TextView diagnoses_type_tv = helper.getView(R.id.diagnoses_type_tv);
        LinearLayout order_complete_layout = helper.getView(R.id.order_complete_layout);
        TextView back_diagnoses_tv = helper.getView(R.id.back_diagnoses_tv);
        TextView change_diagnoses_tv = helper.getView(R.id.change_diagnoses_tv);
        TextView diagnoses_doctor_change_tv = helper.getView(R.id.diagnoses_doctor_change_tv);
        TextView wait_people_tv = helper.getView(R.id.wait_people_tv);
        back_diagnoses_tv.setOnClickListener(new View.OnClickListener() {
            @SingleClick(1000)
            @Override
            public void onClick(View view) {
                if (iOnItemBtnClickListener != null)
                    iOnItemBtnClickListener.onCancelDiagnosesClick(item);
            }
        });
        if (item.getState() == waiteRecieve && item.getWaitInquiryCount() > 0) {
            wait_people_tv.setVisibility(View.VISIBLE);
            wait_people_tv.setText("前面待诊人数:" + item.getWaitInquiryCount());
        } else
            wait_people_tv.setVisibility(View.GONE);
        if (item.getState() == unPay || item.getState() == waiteRecieve || item.getState() == changDoctor)
            back_diagnoses_tv.setVisibility(View.VISIBLE);
        else
            back_diagnoses_tv.setVisibility(View.GONE);
        TextView pay_tv = helper.getView(R.id.pay_tv);
        pay_tv.setOnClickListener(new View.OnClickListener() {
            @SingleClick(1000)
            @Override
            public void onClick(View view) {
                if (iOnItemBtnClickListener != null)
                    iOnItemBtnClickListener.onPayClick(item);
            }
        });
        if (item.getState() == unPay)
            pay_tv.setVisibility(View.VISIBLE);
        else if (item.getState() != unPay && item.getIsSubOrderUnPay() == 1)
            pay_tv.setVisibility(View.VISIBLE);
        else
            pay_tv.setVisibility(View.GONE);

       /* if(item.getState()==1)
            wait_people_tv.setVisibility(View.VISIBLE);
        else
            wait_people_tv.setVisibility(View.GONE);*/
        if (item.getState() == changDoctor)
            change_diagnoses_tv.setVisibility(View.VISIBLE);
        else
            change_diagnoses_tv.setVisibility(View.GONE);

        if (item.getState() == waiteChangeDiagnose)
            diagnoses_doctor_change_tv.setVisibility(View.VISIBLE);
        else
            diagnoses_doctor_change_tv.setVisibility(View.GONE);
        TextView comment_tv = helper.getView(R.id.comment_tv);
        comment_tv.setOnClickListener(new View.OnClickListener() {
            @SingleClick(1000)
            @Override
            public void onClick(View view) {
                if (iOnItemBtnClickListener != null)
                    iOnItemBtnClickListener.onCommentClick(item);
            }
        });

        if (item.getState() == waiteEvaluate)
            comment_tv.setVisibility(View.VISIBLE);
        else
            comment_tv.setVisibility(View.GONE);

        LinearLayout state_layout = helper.getView(R.id.state_layout);
        if (item.getState() == cancel) {
            state_layout.setVisibility(View.VISIBLE);
            TextView state_tv = helper.getView(R.id.state_tv);
            if (item.getRefundState() == 0)
                state_tv.setText("已取消");
            else if (item.getRefundState() == 1)
                state_tv.setText("退款中");
            else
                state_tv.setText("已退款");
        } else
            state_layout.setVisibility(View.GONE);


        if (item.getState() == complete || item.getState() == waiteEvaluate)
            order_complete_layout.setVisibility(View.VISIBLE);
        else
            order_complete_layout.setVisibility(View.GONE);
        if (item.getInquisitionType() == 0) {
            diagnoses_type_tv.setText("图文问诊");
            Drawable leftimg = mContext.getResources().getDrawable(R.mipmap.photo_diagnoses_icon);
            leftimg.setBounds(0, 0, leftimg.getMinimumWidth(), leftimg.getMinimumHeight());
            diagnoses_type_tv.setCompoundDrawables(leftimg, null, null, null);
        } else {
            diagnoses_type_tv.setText("视频问诊");
            Drawable leftimg = mContext.getResources().getDrawable(R.mipmap.video_diagnoses_icon);
            leftimg.setBounds(0, 0, leftimg.getMinimumWidth(), leftimg.getMinimumHeight());
            diagnoses_type_tv.setCompoundDrawables(leftimg, null, null, null);
        }
        //        if (TextUtils.isEmpty(item.getGroupTid())) {
        helper.setText(R.id.doctor_name_tv, item.getDoctorName());
        //        } else {
        //            Team team = ImClient.getTeamProvider().getTeamById(item.getGroupTid());
        //            if (team == null) {
        //                helper.setText(R.id.doctor_name_tv, "临时会诊");
        //            } else {
        //                helper.setText(R.id.doctor_name_tv, team.getName());
        //            }
        //        }
        helper.setText(R.id.doctor_depart_tv, item.getDeptName())
                .setText(R.id.diagnoses_desc_tv, item.getConditionDesc())
                .setText(R.id.time_tv, item.getApplyTime());
        // .setText(R.id.wait_people_tv,item.getInquisitionType())
    }


}
