package com.keydom.ih_patient.adapter;

import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SpanUtils;
import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.bean.NursingOrderBean;


import java.util.List;

/**
 * created date: 2018/12/12 on 15:32
 * des:护理订单适配器
 */

public class NursingOrderAdapter extends BaseQuickAdapter<NursingOrderBean, BaseViewHolder> {
    public static final int NO_PAY = -4;
    public static final int CHANGE = -2;
    public static final int CHARGE_BACK = -1;
    public static final int WAIT = 0;
    public static final int WAIT_GO = 1;
    public static final int ON_WAY = 2;
    public static final int NURSING = 3;
    public static final int COMPLETE = 4;
    public static final int WAIT_NUR = 5;
    public static final int Evaluted = 6;

    public NursingOrderAdapter(@Nullable List<NursingOrderBean> data) {
        super(R.layout.item_nursing_order, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NursingOrderBean item) {
        int state = item.getState();
        int color = 0;
        String backState="";
        boolean chargeBackShow = false;
        boolean assessShow = false;
        boolean goPayShow = false;
        boolean showRemark = true;
        SpannableStringBuilder serviceNum =
                new SpanUtils().append("服务次数：" + item.getFrequency() + "次").setFontSize(12, true).setForegroundColor(mContext.getResources().getColor(R.color.primary_font_color)).create();
        switch (state) {
            case NO_PAY:
                //未支付
                color = mContext.getResources().getColor(R.color.nursing_status_red);
                goPayShow = true;
                chargeBackShow = true;
                break;
            case CHANGE:
                color = mContext.getResources().getColor(R.color.nursing_status_red);
                chargeBackShow = true;
                showRemark = false;
                serviceNum = new SpanUtils().append("修改申请单").setFontSize(12, true).setForegroundColor(mContext.getResources().getColor(R.color.register_success_color)).setUnderline().create();
                break;
            case CHARGE_BACK:
                //已退单
                color = mContext.getResources().getColor(R.color.nursing_status_gray);
                if(item.getRefundState()==0)
                    backState="已取消";
                else if(item.getRefundState()==1)
                    backState="退款中";
                else
                    backState="已退款";
                break;
            case WAIT:
                //待接单
                color = mContext.getResources().getColor(R.color.nursing_status_red);
                chargeBackShow = true;
                break;
            case WAIT_GO:
                //护士已接单
                color = mContext.getResources().getColor(R.color.nursing_status_red);
                chargeBackShow = true;
                if (item.getEquipmentOrSubOrderPay() == 0) {
                    goPayShow = true;
                }
                break;
            case ON_WAY:
                //在途中
                color = mContext.getResources().getColor(R.color.nursing_status_purple);
                chargeBackShow = true;
                if (item.getEquipmentOrSubOrderPay() == 0) {
                    goPayShow = true;
                }
                break;
            case NURSING:
                //服务中
                color = mContext.getResources().getColor(R.color.register_success_color);
                if (item.getEquipmentOrSubOrderPay() == 0) {
                    goPayShow = true;
                }
                if(!item.isFrequencyUsed()){ //是否可以退单
                    chargeBackShow = true;
                }
                break;
            case COMPLETE:
                //已完成
                color = mContext.getResources().getColor(R.color.list_tab_color);
                assessShow = true;
                if (item.getEquipmentOrSubOrderPay() == 0) {
                    goPayShow = true;
                }
                break;
            case Evaluted:
                //已评价
                color = mContext.getResources().getColor(R.color.list_tab_color);
                assessShow = false;
                if (item.getEquipmentOrSubOrderPay() == 0) {
                    goPayShow = true;
                }
                break;
            case WAIT_NUR:
                //已派单
                color = mContext.getResources().getColor(R.color.nursing_status_yellow);
                chargeBackShow = true;
                break;

        }
        ImageView statusIv = helper.getView(R.id.nursing_status_iv);
        statusIv.setColorFilter(color);
        String sex = "";
        if ("1".equals(item.getPatientSex())) {
            sex = "女";
        }
        if ("0".equals(item.getPatientSex())) {
            sex = "男";
        }
        String pCot = StringUtils.isEmpty(item.getPateintName())?"":(item.getPateintName()+"、");
        String sCot = StringUtils.isEmpty(sex)?"":(sex+"、");
        String aCot = item.getPatientAge()+"岁";
        helper.setTextColor(R.id.nursing_status_tv, color)
                .setGone(R.id.go_pay, goPayShow)
                .setGone(R.id.assess, assessShow)
                .setVisible(R.id.order_num_tv, showRemark)
                .setVisible(R.id.time_tv, showRemark)
                .setVisible(R.id.content_tv, showRemark)
                .setVisible(R.id.time, showRemark)
                .setVisible(R.id.order_num, showRemark)
                .setVisible(R.id.content, showRemark)
                .setGone(R.id.remark_tv, !showRemark)
                .setGone(R.id.remark, !showRemark)
                .setGone(R.id.charge_back, chargeBackShow)
                .setText(R.id.remark, item.getRemark() + "")
                .setText(R.id.nursing_title, item.getServerName() + "")
                .setText(R.id.money, "¥ " + item.getPrice())
                .setText(R.id.order_num, item.getOrderNumber() + "")
                .setText(R.id.time, item.getAppointmentTime() + "")
                .setText(R.id.num, serviceNum)
                .setText(R.id.content, pCot+sCot+aCot)
                .addOnClickListener(R.id.group)
                .addOnClickListener(R.id.go_pay)
                .addOnClickListener(R.id.charge_back)
                .addOnClickListener(R.id.num)
                .addOnClickListener(R.id.assess);
        TextView nursing_status_tv=helper.getView(R.id.nursing_status_tv);
        if(state==CHARGE_BACK)
            nursing_status_tv.setText(backState);
        else
            nursing_status_tv.setText(item.getStateString() + "");
    }
}
