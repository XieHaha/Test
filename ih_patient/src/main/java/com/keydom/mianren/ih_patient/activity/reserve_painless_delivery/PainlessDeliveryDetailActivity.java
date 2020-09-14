package com.keydom.mianren.ih_patient.activity.reserve_painless_delivery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.reserve_painless_delivery.controller.PainlessDeliveryDetailController;
import com.keydom.mianren.ih_patient.activity.reserve_painless_delivery.view.PainlessDeliveryDetailView;
import com.keydom.mianren.ih_patient.bean.PainlessDeliveryBean;
import com.keydom.mianren.ih_patient.constant.Const;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 无痛分娩预约
 *
 * @author 顿顿
 */
public class PainlessDeliveryDetailActivity extends BaseControllerActivity<PainlessDeliveryDetailController> implements PainlessDeliveryDetailView {
    @BindView(R.id.tv_visit_name)
    TextView tvVisitName;
    @BindView(R.id.et_age)
    TextView etAge;
    @BindView(R.id.tv_last)
    TextView tvLast;
    @BindView(R.id.tv_due_date)
    TextView tvDueDate;
    @BindView(R.id.tv_fetus)
    TextView tvFetus;
    @BindView(R.id.et_phone)
    TextView etPhone;
    @BindView(R.id.tv_reserve_date)
    TextView tvReserveDate;
    private PainlessDeliveryBean deliveryBean;

    /**
     * 启动
     */
    public static void start(Context context, PainlessDeliveryBean bean) {
        Intent intent = new Intent(context, PainlessDeliveryDetailActivity.class);
        intent.putExtra(Const.DATA, bean);
        context.startActivity(intent);
    }

    /**
     * 胎数
     */
    List<String> fetusDit = new ArrayList<>();

    {
        fetusDit.add("单胎");
        fetusDit.add("双胎");
        fetusDit.add("多胎");
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_painless_delivery_detail;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_painless_delivery_reserve));

        deliveryBean = (PainlessDeliveryBean) getIntent().getSerializableExtra(Const.DATA);

        tvVisitName.setText(deliveryBean.getPatientName());
        etAge.setText(deliveryBean.getAge());
        tvLast.setText(deliveryBean.getLastMenstrualPeriodTime());
        tvDueDate.setText(deliveryBean.getExpectedDateOfConfinement());
        tvFetus.setText(fetusDit.get(deliveryBean.getEmbryoNumber()));
        etPhone.setText(deliveryBean.getPhoneNumber());
        tvReserveDate.setText(deliveryBean.getAppointmentDate());

        if (deliveryBean.getIsConfirm() == 0) {
            setRightTxt("取消");
            setRightBtnListener(getController());
        }
    }

    @Override
    public void cancelSuccess() {
        setRightTxt("");
        ToastUtil.showMessage(this, "操作成功");
    }

    @Override
    public String getId() {
        return deliveryBean.getId();
    }
}
