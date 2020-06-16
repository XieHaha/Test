package com.keydom.mianren.ih_patient.activity.reserve_painless_delivery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.InterceptorEditText;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.reserve_painless_delivery.controller.ReservePainlessDeliveryController;
import com.keydom.mianren.ih_patient.activity.reserve_painless_delivery.view.ReservePainlessDeliveryView;
import com.keydom.mianren.ih_patient.bean.Event;
import com.keydom.mianren.ih_patient.bean.ManagerUserBean;
import com.keydom.mianren.ih_patient.constant.EventType;
import com.keydom.mianren.ih_patient.utils.DateUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

/**
 * 无痛分娩预约
 */
public class ReservePainlessDeliveryActivity extends BaseControllerActivity<ReservePainlessDeliveryController> implements ReservePainlessDeliveryView {
    private ImageView ivSelect;
    private TextView tvVisitName, tvLastMenstruation, tvDueDate, tvFetus, tvNote, tvNext;
    private InterceptorEditText etAge, etPhone;
    private LinearLayout layoutVisit, layoutFetus, layoutMenstruation, layoutDueDate;
    private RelativeLayout layoutSelect;
    /**
     * 就诊人
     */
    private ManagerUserBean managerUserBean;

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
        EventBus.getDefault().register(this);
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
        layoutSelect = findViewById(R.id.layout_select);

        tvNext.setOnClickListener(getController());
        tvNote.setOnClickListener(getController());
        layoutVisit.setOnClickListener(getController());
        layoutFetus.setOnClickListener(getController());
        layoutMenstruation.setOnClickListener(getController());
        layoutDueDate.setOnClickListener(getController());
        layoutSelect.setOnClickListener(getController());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onVisitPeopleSelect(Event event) {
        if (event.getType() == EventType.SENDPATIENTINFO) {
            managerUserBean = (ManagerUserBean) event.getData();
            bindVisitData();
        }
    }

    /**
     * 就诊人信息
     */
    private void bindVisitData() {
        if (managerUserBean != null) {
            tvVisitName.setText(managerUserBean.getName());
            etAge.setText(managerUserBean.getAge());
            etPhone.setText(managerUserBean.getPhone());
        }
    }

    @Override
    public void reserveSuccess() {
        ToastUtil.showMessage(getContext(), "成功");
    }

    @Override
    public void reserveFailed() {
        ToastUtil.showMessage(getContext(), "失败");
    }

    @Override
    public ManagerUserBean getVisitUser() {
        return managerUserBean;
    }

    @Override
    public String getAge() {
        return etAge.getText().toString();
    }

    @Override
    public String getLastDate() {
        return tvLastMenstruation.getText().toString();
    }

    @Override
    public String getDueDate() {
        return tvDueDate.getText().toString();
    }

    @Override
    public String getFetus() {
        return tvFetus.getText().toString();
    }

    @Override
    public String getPhone() {
        return etPhone.getText().toString();
    }

    @Override
    public long getCurUserId() {
        return managerUserBean == null ? -1 : managerUserBean.getId();
    }

    @Override
    public void setSelect() {
        if (isSelect()) {
            ivSelect.setVisibility(View.INVISIBLE);
        } else {
            ivSelect.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean isSelect() {
        return ivSelect.isShown();
    }

    @Override
    public void setMenstruation(Date menstruation) {
        tvLastMenstruation.setText(DateUtils.dateToString(menstruation));
    }

    @Override
    public void setDueDate(Date dueDate) {
        tvDueDate.setText(DateUtils.dateToString(dueDate));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
