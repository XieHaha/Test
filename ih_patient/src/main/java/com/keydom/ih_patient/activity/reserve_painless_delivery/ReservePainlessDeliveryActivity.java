package com.keydom.ih_patient.activity.reserve_painless_delivery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.view.InterceptorEditText;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.reserve_painless_delivery.controller.ReservePainlessDeliveryController;
import com.keydom.ih_patient.activity.reserve_painless_delivery.view.ReservePainlessDeliveryView;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.Nullable;

/**
 * 无痛分泌预约
 */
public class ReservePainlessDeliveryActivity extends BaseControllerActivity<ReservePainlessDeliveryController> implements ReservePainlessDeliveryView {
    private ImageView ivSelect;
    private TextView tvVisitName, tvLastMenstruation, tvDueDate, tvFetus, tvNote, tvNext;
    private InterceptorEditText etAge, etPhone;
    private LinearLayout layoutVisit, layoutFetus, layoutMenstruation, layoutDueDate;

    /**
     * 启动
     */
    public static void start(Context context) {
        context.startActivity(new Intent(context, ReservePainlessDeliveryActivity.class));
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_reserve_painless_delivery;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_painless_delivery_reserve));
        bindView();
    }

    private void bindView() {
        ivSelect = findViewById(R.id.iv_select);
        etAge = findViewById(R.id.et_age);
        etPhone = findViewById(R.id.et_phone);
        tvVisitName = findViewById(R.id.tv_visit_name);
        tvLastMenstruation = findViewById(R.id.tv_last);
        tvDueDate = findViewById(R.id.tv_due_date);
        tvFetus = findViewById(R.id.tv_fetus);
        tvNote = findViewById(R.id.tv_note);
        tvNext = findViewById(R.id.tx_next);
        layoutVisit = findViewById(R.id.layout_visit);
        layoutFetus = findViewById(R.id.layout_fetus);
        layoutMenstruation = findViewById(R.id.layout_menstruation);
        layoutDueDate = findViewById(R.id.layout_due_date);
        tvNext.setOnClickListener(getController());
        layoutVisit.setOnClickListener(getController());
        layoutFetus.setOnClickListener(getController());
        layoutMenstruation.setOnClickListener(getController());
        layoutDueDate.setOnClickListener(getController());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void reserveSuccess() {

    }

    @Override
    public void getVisitUser() {

    }

    @Override
    public void getAge() {

    }

    @Override
    public void getLastDate() {

    }

    @Override
    public void getDueDate() {

    }

    @Override
    public void getFutes() {

    }

    @Override
    public void getPhone() {

    }
}
