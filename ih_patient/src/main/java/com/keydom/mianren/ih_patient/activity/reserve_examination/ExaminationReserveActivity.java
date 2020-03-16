package com.keydom.mianren.ih_patient.activity.reserve_examination;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.reserve_examination.controller.ExaminationReserveController;
import com.keydom.mianren.ih_patient.activity.reserve_examination.view.ExaminationReserveView;
import com.keydom.mianren.ih_patient.bean.Event;
import com.keydom.mianren.ih_patient.bean.ManagerUserBean;
import com.keydom.mianren.ih_patient.constant.EventType;
import com.keydom.mianren.ih_patient.utils.DateUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

import butterknife.BindView;

/**
 * @date 20/2/25 10:49
 * @des 检验检查预约
 */
public class ExaminationReserveActivity extends BaseControllerActivity<ExaminationReserveController> implements ExaminationReserveView {
    @BindView(R.id.examination_reserve_visit_tv)
    TextView examinationReserveVisitTv;
    @BindView(R.id.examination_reserve_date_tv)
    TextView examinationReserveDateTv;
    @BindView(R.id.examination_reserve_checkout_tv)
    TextView examinationReserveCheckoutTv;
    @BindView(R.id.examination_reserve_examine_tv)
    TextView examinationReserveExamineTv;
    @BindView(R.id.examination_reserve_project_tv)
    TextView examinationReserveProjectTv;
    @BindView(R.id.examination_reserve_notice_iv)
    ImageView examinationReserveNoticeIv;
    @BindView(R.id.examination_reserve_notice_layout)
    RelativeLayout examinationReserveNoticeLayout;
    @BindView(R.id.examination_reserve_note_tv)
    TextView examinationReserveNoteTv;
    @BindView(R.id.examination_reserve_next_tv)
    TextView examinationReserveNextTv;

    private ManagerUserBean userBean;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_examination_reserve;
    }

    /**
     * 启动
     */
    public static void start(Context context) {
        context.startActivity(new Intent(context, ExaminationReserveActivity.class));
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        setTitle(getString(R.string.txt_check_reserve));

        examinationReserveVisitTv.setOnClickListener(getController());
        examinationReserveDateTv.setOnClickListener(getController());
        examinationReserveCheckoutTv.setOnClickListener(getController());
        examinationReserveExamineTv.setOnClickListener(getController());
        examinationReserveProjectTv.setOnClickListener(getController());
        examinationReserveNextTv.setOnClickListener(getController());
        examinationReserveNoticeLayout.setOnClickListener(getController());
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserSelected(Event event) {
        if (event.getType() == EventType.SENDPATIENTINFO && event.getData() != null) {
            userBean = (ManagerUserBean) event.getData();
            examinationReserveVisitTv.setText(userBean.getName());
        }
    }

    @Override
    public void setSelect() {
        if (isSelect()) {
            examinationReserveNoticeIv.setVisibility(View.INVISIBLE);
        } else {
            examinationReserveNoticeIv.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean isSelect() {
        return examinationReserveNoticeIv.isShown();
    }

    @Override
    public void setVisitDate(Date visitDate) {
        examinationReserveDateTv.setText(DateUtils.dateToString(visitDate));
    }


    @Override
    public long getCurrentUserId() {
        return userBean == null ? -1 : userBean.getId();
    }

    @Override
    public void setCategory(boolean category) {
        examinationReserveCheckoutTv.setSelected(category);
        examinationReserveExamineTv.setSelected(!category);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
