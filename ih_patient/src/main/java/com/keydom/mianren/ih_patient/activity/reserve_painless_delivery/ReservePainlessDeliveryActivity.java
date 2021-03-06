package com.keydom.mianren.ih_patient.activity.reserve_painless_delivery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.ih_common.view.InterceptorEditText;
import com.keydom.mianren.ih_patient.App;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.diagnose_user_manager.AddManageUserActivity;
import com.keydom.mianren.ih_patient.activity.reserve_painless_delivery.controller.ReservePainlessDeliveryController;
import com.keydom.mianren.ih_patient.activity.reserve_painless_delivery.view.ReservePainlessDeliveryView;
import com.keydom.mianren.ih_patient.bean.Event;
import com.keydom.mianren.ih_patient.bean.MedicalCardInfo;
import com.keydom.mianren.ih_patient.constant.EventType;
import com.keydom.mianren.ih_patient.utils.DateUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 无痛分娩预约
 *
 * @author 顿顿
 */
public class ReservePainlessDeliveryActivity extends BaseControllerActivity<ReservePainlessDeliveryController> implements ReservePainlessDeliveryView {
    private ImageView ivSelect;
    private TextView tvVisitName, tvLastMenstruation, tvDueDate, tvNote, tvNext,
            tvReserveDate;
    private InterceptorEditText etAge, etPhone, etFetus;
    private LinearLayout layoutVisit, layoutFetus, layoutMenstruation, layoutDueDate,
            layoutReserveDate;
    private RelativeLayout layoutSelect;
    /**
     * 就诊卡
     */
    private MedicalCardInfo medicalCardInfo;

    private int fetusValue = -1;

    /**
     * 胎数
     */
    private List<String> fetusDit = new ArrayList<>();

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
    protected void onResume() {
        super.onResume();
        getController().queryAllCard();
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_painless_delivery_reserve));
        EventBus.getDefault().register(this);
        setRightTxt("预约记录");
        setRightBtnListener(getController());

        fetusDit.add("单胎");
        fetusDit.add("双胎");
        fetusDit.add("多胎");

        bindView();
    }

    private void bindView() {
        ivSelect = findViewById(R.id.iv_select);
        etAge = findViewById(R.id.et_age);
        etPhone = findViewById(R.id.et_phone);
        etFetus = findViewById(R.id.et_fetus);
        tvVisitName = findViewById(R.id.tv_visit_name);
        tvLastMenstruation = findViewById(R.id.tv_last);
        tvDueDate = findViewById(R.id.tv_due_date);
        tvReserveDate = findViewById(R.id.tv_reserve_date);
        tvNote = findViewById(R.id.tv_note);
        tvNext = findViewById(R.id.tx_next);
        layoutVisit = findViewById(R.id.layout_visit);
        layoutFetus = findViewById(R.id.layout_fetus);
        layoutMenstruation = findViewById(R.id.layout_menstruation);
        layoutDueDate = findViewById(R.id.layout_due_date);
        layoutSelect = findViewById(R.id.layout_select);
        layoutReserveDate = findViewById(R.id.layout_reserve_date);

        tvNext.setOnClickListener(getController());
        tvNote.setOnClickListener(getController());
        layoutVisit.setOnClickListener(getController());
        //        layoutFetus.setOnClickListener(getController());
        layoutMenstruation.setOnClickListener(getController());
        layoutDueDate.setOnClickListener(getController());
        layoutReserveDate.setOnClickListener(getController());
        layoutSelect.setOnClickListener(getController());
    }

    /**
     * 获取患者就诊卡
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getPatientCard(Event event) {
        if (event.getType() == EventType.SENDSELECTNURSINGPATIENT) {
            medicalCardInfo = (MedicalCardInfo) event.getData();
            bindVisitData();
        }
    }

    /**
     * 就诊人信息
     */
    private void bindVisitData() {
        if (medicalCardInfo != null) {
            tvVisitName.setText(medicalCardInfo.getName());
            if (!TextUtils.isEmpty(medicalCardInfo.getAge())) {
                String age = medicalCardInfo.getAge().replace("岁", "");
                etAge.setText(age);
            }
            etPhone.setText(medicalCardInfo.getPhoneNumber());
        }
    }

    @Override
    public void getAllCardSuccess(List<MedicalCardInfo> dataList) {
        //只获取当前登录帐号的就诊卡
        for (MedicalCardInfo info : dataList) {
            if (App.userInfo.getIdCard().equals(info.getIdCard())) {
                medicalCardInfo = info;
                bindVisitData();
                break;
            }
        }
        if (medicalCardInfo == null) {
            new GeneralDialog(this, "未获取到本人就诊卡信息", new GeneralDialog.OnCloseListener() {
                @Override
                public void onCommit() {
                    Intent i = new Intent(getContext(), AddManageUserActivity.class);
                    i.putExtra(AddManageUserActivity.TYPE, AddManageUserActivity.ADD);
                    i.putExtra(AddManageUserActivity.ELECTRONIC_CARD, true);
                    ActivityUtils.startActivity(i);
                }
            }).setPositiveButton("去添加").setNegativeButtonIsGone(true).show();
        }
    }

    @Override
    public void getAllCardFailed(String errMsg) {
        ToastUtil.showMessage(getContext(), errMsg);
    }

    @Override
    public void reserveSuccess() {
        ToastUtil.showMessage(getContext(), "预约成功");
        PainlessDeliveryListActivity.start(getContext());
        finish();
    }

    @Override
    public void reserveFailed() {
        ToastUtil.showMessage(getContext(), "失败");
    }


    @Override
    public List<String> getFetusDit() {
        return fetusDit;
    }

    @Override
    public MedicalCardInfo getVisitUser() {
        return medicalCardInfo;
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
    public String getReserveDate() {
        return tvReserveDate.getText().toString();
    }

    @Override
    public String getFetus() {
        return etFetus.getText().toString();
    }

    @Override
    public String getPhone() {
        return etPhone.getText().toString();
    }

    @Override
    public int getFetusValue() {
        return fetusValue;
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
    public void setFetus(int position) {
        fetusValue = position;
        //        tvFetus.setText(fetusDit.get(position));
    }

    @Override
    public void setDueDate(Date dueDate) {
        tvDueDate.setText(DateUtils.dateToString(dueDate));
    }

    @Override
    public void setReserveDate(Date reserveDate) {
        tvReserveDate.setText(DateUtils.dateToString(reserveDate));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
