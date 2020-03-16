package com.keydom.mianren.ih_patient.activity.payment_records;

import android.os.Bundle;
import android.view.View;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.index_main.MainActivity;
import com.keydom.mianren.ih_patient.activity.payment_records.controller.PaymentSuccessController;
import com.keydom.mianren.ih_patient.activity.payment_records.view.PaymentSuccessView;
import com.keydom.mianren.ih_patient.bean.Event;
import com.keydom.mianren.ih_patient.callback.SingleClick;
import com.keydom.mianren.ih_patient.constant.EventType;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.Nullable;

/**
 * created date: 2019/1/23 on 10:40
 * des:缴费成功页面
 */
public class PaymentSuccessActivity extends BaseControllerActivity<PaymentSuccessController> implements PaymentSuccessView, View.OnClickListener {
    @Override
    public int getLayoutRes() {
        return R.layout.activity_pay_success_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("缴费记录");
        findViewById(R.id.see).setOnClickListener(this);
        findViewById(R.id.back_home).setOnClickListener(this);
    }
    @SingleClick(1000)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.see:
                Event event = new Event(EventType.PAYMENT_SUCCESS,null);
                EventBus.getDefault().post(event);
                finish();
                break;
            case R.id.back_home:
                MainActivity.start(this,false);
                break;
        }
    }
}
