package com.keydom.ih_patient.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.keydom.ih_patient.R;

/**
 * created date: 2018/12/30 on 15:09
 * des:公共支付弹框
 */
public class CommonPayDialog extends BottomSheetDialog implements View.OnClickListener {
    public static String WX_PAY = "1";
    public static String ALI_PAY = "2";

    private TextView mAliPayText;//ali_pay_tv
    private ImageView mAliPayImg;//ali_pay_selected_img
    private TextView mWxPayText;//wechat_pay_tv
    private ImageView mWxPayImg;//wechat_pay_selected_img
    private TextView mCommit;//pay_commit
    private ImageView mClose;//close_img
    private TextView mCost;//order_price_tv

    /**
     * 监听接口
     */
    private iOnCommitOnClick mIOnCommitOnClick;

    /**
     * 提交支付监听
     */
    public interface iOnCommitOnClick {
        void commitPay(String type);
    }

    /**
     * 构建方法
     */
    public CommonPayDialog(@NonNull Context context, iOnCommitOnClick iOnCommitOnClick) {
        super(context);
        this.mIOnCommitOnClick = iOnCommitOnClick;
    }

    /**
     * 设置金额
     */
    public void setCost(double money){
        mCost.setText("¥"+money+"元");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_go_pay_dialog);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        initView();
    }

    /**
     * 初始化
     */
    private void initView() {
        mClose = this.findViewById(R.id.close_img);
        mAliPayText = this.findViewById(R.id.ali_pay_tv);
        mAliPayImg = this.findViewById(R.id.ali_pay_selected_img);
        mWxPayText = this.findViewById(R.id.wechat_pay_tv);
        mWxPayImg = this.findViewById(R.id.wechat_pay_selected_img);
        mCommit = this.findViewById(R.id.pay_commit);
        mCost = this.findViewById(R.id.order_price_tv);

        mClose.setOnClickListener(this);
        mWxPayImg.setOnClickListener(this);
        mAliPayImg.setOnClickListener(this);
        mCommit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close_img:
                this.dismiss();
                break;
            case R.id.ali_pay_selected_img:
                mAliPayText.setTextColor(getContext().getResources().getColor(R.color.pay_selected));
                mWxPayText.setTextColor(getContext().getResources().getColor(R.color.pay_unselected));
                mAliPayImg.setSelected(true);
                mWxPayText.setSelected(false);
                break;
            case R.id.wechat_pay_selected_img:
                mWxPayText.setTextColor(getContext().getResources().getColor(R.color.pay_selected));
                mAliPayText.setTextColor(getContext().getResources().getColor(R.color.pay_unselected));
                mAliPayImg.setSelected(false);
                mWxPayText.setSelected(true);
                break;
            case R.id.pay_commit:
                if (mAliPayImg.isSelected()) {
                    mIOnCommitOnClick.commitPay(ALI_PAY);
                    dismiss();
                } else if (mWxPayImg.isSelected()) {
                    mIOnCommitOnClick.commitPay(WX_PAY);
                    dismiss();
                } else {
                    ToastUtils.showShort("请选择支付方式");
                }
                break;


        }
    }
}
