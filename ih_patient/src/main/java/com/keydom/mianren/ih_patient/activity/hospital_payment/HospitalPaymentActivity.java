package com.keydom.mianren.ih_patient.activity.hospital_payment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.hospital_payment.controller.HospitalPaymentController;
import com.keydom.mianren.ih_patient.activity.hospital_payment.view.HospitalPaymentView;
import com.keydom.mianren.ih_patient.adapter.HospitalPaymentAdapter;
import com.keydom.mianren.ih_patient.bean.Event;
import com.keydom.mianren.ih_patient.bean.HospitalInfoBean;
import com.keydom.mianren.ih_patient.bean.HospitalRecordBean;
import com.keydom.mianren.ih_patient.bean.HospitalRecordRootBean;
import com.keydom.mianren.ih_patient.bean.MedicalCardInfo;
import com.keydom.mianren.ih_patient.constant.EventType;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author 顿顿
 * @date 20/2/25 17:01
 * @des 住院预缴金
 */
public class HospitalPaymentActivity extends BaseControllerActivity<HospitalPaymentController>
        implements HospitalPaymentView {
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.et_amount)
    EditText etAmount;
    @BindView(R.id.tv_sure)
    TextView tvGoPay;
    private TextView tvName, tvTime, tvRegisterPhone, tvWard, tvBedNum, tvRechargeAmount, tvBalance;
    private RelativeLayout patientLayout;
    private HospitalPaymentAdapter adapter;

    private HospitalRecordRootBean rootBean;
    private HospitalInfoBean infoBean;
    private List<HospitalRecordBean> data = new ArrayList<>();
    private View headerView;

    /**
     * 就诊卡
     */
    private MedicalCardInfo medicalCardInfo;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_hospital_payment;
    }

    /**
     * 启动
     */
    public static void start(Context context) {
        context.startActivity(new Intent(context, HospitalPaymentActivity.class));
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        setTitle("住院预缴");
        setRightTxt("住院清单");
        setRightBtnListener(v -> {
            if (infoBean != null) {
                HospitalCheckListActivity.start(HospitalPaymentActivity.this, medicalCardInfo,
                        infoBean.getInHospitalNo());
            } else {
                ToastUtil.showMessage(HospitalPaymentActivity.this, "暂无住院清单");
            }
        });

        swipeRefreshLayout.setOnRefreshListener(refreshLayout -> getController().getHospitalPayment());
        tvGoPay.setOnClickListener(getController());
        bindHeaderView();
        adapter = new HospitalPaymentAdapter(data);
        adapter.addHeaderView(headerView);
        recyclerView.setAdapter(adapter);

        getController().queryAllCard();
    }

    /**
     * 头部 view
     */
    private void bindHeaderView() {
        headerView = getLayoutInflater().inflate(R.layout.header_hospital_payment, null);
        tvName = headerView.findViewById(R.id.tv_name);
        tvTime = headerView.findViewById(R.id.tv_go_hospital_time);
        tvRegisterPhone = headerView.findViewById(R.id.tv_register_phone);
        tvWard = headerView.findViewById(R.id.tv_ward);
        tvBedNum = headerView.findViewById(R.id.tv_bed_num);
        tvRechargeAmount = headerView.findViewById(R.id.tv_recharge_amount);
        tvBalance = headerView.findViewById(R.id.tv_balance);
        patientLayout = headerView.findViewById(R.id.hospital_payment_patient_layout);
        patientLayout.setOnClickListener(getController());
    }

    /**
     * 头部数据绑定
     */
    private void bindHeaderData() {
        tvTime.setText(infoBean.getInHospitalDate());
        tvRegisterPhone.setText(infoBean.getInHospitalNo());
        tvWard.setText(infoBean.getWardNo());
        tvBedNum.setText(infoBean.getBedNo());
        tvRechargeAmount.setText(infoBean.getTotalFee());
        tvBalance.setText(infoBean.getBalance());
    }

    @Override
    public void getAllCardSuccess(List<MedicalCardInfo> data) {
        if (data != null && data.size() > 0) {
            medicalCardInfo = data.get(0);
            tvName.setText(medicalCardInfo.getName());

            getController().getHospitalPayment();
        }
    }

    @Override
    public String getMedicalCardNumber() {
        return medicalCardInfo.getEleCardNumber();
    }

    @Override
    public String getFee() {
        return etAmount.getText().toString();
    }


    @Override
    public MedicalCardInfo getMedicalCardInfo() {
        return medicalCardInfo;
    }

    @Override
    public String getInHospitalNo() {
        if (infoBean == null) {
            return "";
        }
        return infoBean.getInHospitalNo();
    }

    /**
     * 获取患者就诊卡
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getPatientCard(Event event) {
        if (event.getType() == EventType.SENDSELECTNURSINGPATIENT) {
            medicalCardInfo = (MedicalCardInfo) event.getData();
            tvName.setText(medicalCardInfo.getName());
            getController().getHospitalPayment();
        }
    }

    @Override
    public void fillHospitalPaymentData(HospitalRecordRootBean data) {
        swipeRefreshLayout.finishRefresh();
        rootBean = data;
        infoBean = rootBean.getItem();
        bindHeaderData();
        this.data = data.getOrderRecord();
        adapter.setNewData(this.data);
    }

    @Override
    public void fillHospitalPaymentFailed(String error) {
        swipeRefreshLayout.finishRefresh();
        ToastUtil.showMessage(this, error);
    }

    @Override
    public void createOrderSuccess() {
        etAmount.setText("");
        getController().getHospitalPayment();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
