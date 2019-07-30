package com.keydom.ih_patient.activity.order_doctor_register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_patient.App;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.order_doctor_register.controller.ChooseMedicalCardController;
import com.keydom.ih_patient.activity.order_doctor_register.view.ChooseMedicalCardView;
import com.keydom.ih_patient.adapter.RegistrationCardAdapter;
import com.keydom.ih_patient.bean.DoctorSchedulingBean;
import com.keydom.ih_patient.bean.MedicalCardInfo;
import com.keydom.ih_patient.bean.PaymentOrderBean;
import com.keydom.ih_patient.callback.GeneralCallback;
import com.keydom.ih_patient.constant.Global;
import com.keydom.ih_patient.constant.Type;
import com.keydom.ih_patient.utils.SelectDialogUtils;
import com.keydom.ih_patient.utils.ToastUtil;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 选择就诊卡页面
 */
public class ChooseMedicalCardActivity extends BaseControllerActivity<ChooseMedicalCardController> implements ChooseMedicalCardView {
    /**
     * 启动
     */
    public static void start(Context context, DoctorSchedulingBean doctorSchedulingBean, String doctorName, long doctorId, String describe,String deptId,int registerBackNo) {
        Intent intent = new Intent(context, ChooseMedicalCardActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("schedulingInfo", doctorSchedulingBean);
        intent.putExtras(bundle);
        intent.putExtra("doctorName", doctorName);
        intent.putExtra("doctorId", doctorId);
        intent.putExtra("describe", describe);
        intent.putExtra("deptId", deptId);
        intent.putExtra("registerBackNo", registerBackNo);
        context.startActivity(intent);
    }

    private TextView cure_time_tv, cure_doctor_tv, cure_department_tv, cure_price_tv, jump_to_card_operate_tv, registerDoctorCommitTv,title_label_tv;
    private RecyclerView choose_medical_card_rv;
    private RegistrationCardAdapter registrationCardAdapter;
    private String payType = Type.ALIPAY;
    private List<MedicalCardInfo> dataList = new ArrayList<>();
    private DoctorSchedulingBean doctorSchedulingBean;
    private String doctorName, describe,deptId;
    private MedicalCardInfo selectCardInfo = null;
    private long doctorId;
    private PaymentOrderBean paymentOrderBean;
    private int registerBackNo;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_choose_medical_card_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("选择就诊卡");
        doctorSchedulingBean = (DoctorSchedulingBean) getIntent().getSerializableExtra("schedulingInfo");
        doctorName = getIntent().getStringExtra("doctorName");
        describe = getIntent().getStringExtra("describe");
        deptId = getIntent().getStringExtra("deptId");
        doctorId = getIntent().getLongExtra("doctorId", -1);
        registerBackNo=getIntent().getIntExtra("registerBackNo",-1);
        title_label_tv=findViewById(R.id.title_label_tv);
        title_label_tv.setText("提示：当日号不能退，预约号最迟应于就诊日期前"+registerBackNo+"个自然日提交退号申请");
        cure_time_tv = this.findViewById(R.id.cure_time_tv);
        cure_doctor_tv = this.findViewById(R.id.cure_doctor_tv);
        cure_department_tv = this.findViewById(R.id.cure_department_tv);
        cure_price_tv = this.findViewById(R.id.cure_price_tv);
        jump_to_card_operate_tv = this.findViewById(R.id.jump_to_card_operate_tv);
        jump_to_card_operate_tv.setOnClickListener(getController());
        registerDoctorCommitTv = findViewById(R.id.commit_tv);
        registerDoctorCommitTv.setOnClickListener(getController());
        choose_medical_card_rv = this.findViewById(R.id.choose_medical_card_rv);
        registrationCardAdapter = new RegistrationCardAdapter(this, dataList, new GeneralCallback.SelectCardListener() {
            @Override
            public void getSelectedCard(MedicalCardInfo medicalCardInfo) {
                selectCardInfo = medicalCardInfo;
            }
        });
        choose_medical_card_rv.setAdapter(registrationCardAdapter);
        cure_time_tv.setText(doctorSchedulingBean.getDate());
        cure_doctor_tv.setText(doctorName);
        cure_department_tv.setText(doctorSchedulingBean.getDeptName());
        cure_price_tv.setText("¥" + doctorSchedulingBean.getRegistrationFee());

    }

    @Override
    protected void onResume() {
        getController().queryAllCard();
        super.onResume();
    }

    @Override
    public void showPayDialog() {
        SelectDialogUtils.showPayDialog(getContext(), doctorSchedulingBean.getRegistrationFee(), "预约挂号-" + doctorName, new GeneralCallback.SelectPayMentListener() {
            @Override
            public void getSelectPayMent(String type) {
                Map<String, Object> map = new HashMap<>();
                map.put("orderNumber", paymentOrderBean.getOrderNumber());
                map.put("totalMoney", paymentOrderBean.getFee());

                if (Type.ALIPAY.equals(type)) {
                    map.put("type", 2);
                    getController().hospitalFeeByOrderNumber(map);
                } else if (Type.WECHATPAY.equals(type)) {
                    map.put("type", 1);
                }
            }
        });
    }

    @Override
    public void getAllCardSuccess(List<MedicalCardInfo> dataList) {
        this.dataList.clear();
        this.dataList.addAll(dataList);
        registrationCardAdapter.notifyDataSetChanged();
    }

    @Override
    public void getAllCardFailed(String errMsg) {
        ToastUtil.shortToast(getContext(), "就诊卡列表失败" + errMsg);
    }

    @Override
    public void createOrderNumSuccess(PaymentOrderBean paymentOrderBean) {
        this.paymentOrderBean = paymentOrderBean;
        showPayDialog();
    }

    @Override
    public void createOrderNumFailed(String errMsg) {
        ToastUtil.shortToast(getContext(), "生成订单失败" + errMsg);
    }

    @Override
    public Map<String, Object> getCreateOrderNumQueryMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("date", doctorSchedulingBean.getDate());
        map.put("hospitalId", App.hospitalId);
        map.put("hospitalUserId", doctorId);
        if (selectCardInfo != null)
            map.put("orderNumber", selectCardInfo.getEleCardNumber());
        else {
            ToastUtil.shortToast(getContext(), "请选择就诊卡");
            return null;
        }
        map.put("totalMoney", doctorSchedulingBean.getRegistrationFee());
        map.put("userId", Global.getUserId());
        map.put("timeInterval", describe);
        map.put("deptId",deptId);
        return map;
    }

    @Override
    public void completeOrder() {
        finish();
    }
}
