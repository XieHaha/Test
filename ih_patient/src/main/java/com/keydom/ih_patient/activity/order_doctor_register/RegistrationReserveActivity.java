package com.keydom.ih_patient.activity.order_doctor_register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.order_doctor_register.controller.RegistrationReserveController;
import com.keydom.ih_patient.activity.order_doctor_register.view.RegistrationReserveView;
import com.keydom.ih_patient.bean.Event;
import com.keydom.ih_patient.bean.ManagerUserBean;
import com.keydom.ih_patient.constant.EventType;
import com.keydom.ih_patient.utils.DateUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

import butterknife.BindView;

/**
 * @date 20/3/5 16:07
 * @des 挂号预约
 */
public class RegistrationReserveActivity extends BaseControllerActivity<RegistrationReserveController> implements RegistrationReserveView {
    @BindView(R.id.tv_visit_name)
    TextView tvVisitName;
    @BindView(R.id.layout_visit)
    LinearLayout layoutVisit;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.layout_date)
    LinearLayout layoutDate;
    @BindView(R.id.tv_depart)
    TextView tvDepart;
    @BindView(R.id.layout_depart)
    LinearLayout layoutDepart;
    @BindView(R.id.tv_doctor)
    TextView tvDoctor;
    @BindView(R.id.layout_doctor)
    LinearLayout layoutDoctor;
    @BindView(R.id.tv_next)
    TextView txNext;
    @BindView(R.id.tv_morning)
    TextView tvMorning;
    @BindView(R.id.tv_afternoon)
    TextView tvAfternoon;
    @BindView(R.id.tv_night)
    TextView tvNight;

    private ManagerUserBean userBean;

    /**
     * 启动
     */
    public static void start(Context context) {
        context.startActivity(new Intent(context, RegistrationReserveActivity.class));
    }


    @Override
    public int getLayoutRes() {
        return R.layout.activity_registration_reserve;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        setTitle("预约挂号");
        layoutVisit.setOnClickListener(getController());
        layoutDate.setOnClickListener(getController());
        layoutDepart.setOnClickListener(getController());
        layoutDoctor.setOnClickListener(getController());
        tvMorning.setOnClickListener(getController());
        tvAfternoon.setOnClickListener(getController());
        tvNight.setOnClickListener(getController());
        txNext.setOnClickListener(getController());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserSelected(Event event) {
        if (event.getType() == EventType.SENDPATIENTINFO && event.getData() != null) {
            userBean = (ManagerUserBean) event.getData();
            tvVisitName.setText(userBean.getName());
        }
    }

    @Override
    public long getCurrentUserId() {
        return userBean == null ? -1 : userBean.getId();
    }

    @Override
    public void setVisitDate(Date visitDate) {
        tvDate.setText(DateUtils.dateToString(visitDate));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
