package com.keydom.mianren.ih_patient.activity.apply_for_order_detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.bean.CheckOutGroupBean;
import com.keydom.ih_common.bean.InspectionBean;
import com.keydom.ih_common.constant.Const;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.mianren.ih_patient.App;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.apply_for_order_detail.controller.CheckOrderDetailController;
import com.keydom.mianren.ih_patient.activity.apply_for_order_detail.view.CheckOrderDetailView;
import com.keydom.mianren.ih_patient.adapter.TestDetailItemListAdapter;
import com.keydom.mianren.ih_patient.bean.InquiryBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.activity.personal
 * @Description：检验检查申请单详情页面
 * @Author：song
 * @Date：18/11/14 上午10:37
 * 修改人：xusong
 * 修改时间：18/11/14 上午10:37
 */
public class CheckOrderDetailActivity extends BaseControllerActivity<CheckOrderDetailController> implements CheckOrderDetailView {

    public static final int TEST_ORDER = 800;
    public static final int INSPACT_ORDER = 801;
    private RecyclerView recyclerView;
    private TestDetailItemListAdapter testDetailItemListAdapter;
    private int mType;

    private TextView hospitalName, userName, userSex, userAge, diagnoseNumber, sampleType, deptName,
            diagnoseResTv, diseaseDecTv, checkItemTv, doctorInstructionTv, diseaseDecTipTv,
            doctorInstructionTipTv,
            checkItemAmount, totalFee, applyDoctor, applyTime, sampleTypeTip, check_item_tip_tv,
            table_title_tv;
    private ImageButton editOrderIb;
    private List<CheckOutGroupBean> checkOutList = new ArrayList<>();
    private InspectionBean inspectionBean;

    /**
     * 初始化控件
     */
    private void initView() {
        table_title_tv = findViewById(R.id.table_title_tv);
        check_item_tip_tv = findViewById(R.id.check_item_tip_tv);
        doctorInstructionTipTv = this.findViewById(R.id.doctor_instruction_tip_tv);
        diseaseDecTipTv = this.findViewById(R.id.disease_dec_tip_tv);
        editOrderIb = this.findViewById(R.id.edit_order_ib);
        hospitalName = this.findViewById(R.id.hospital_name);
        userName = this.findViewById(R.id.user_name);
        userSex = this.findViewById(R.id.user_sex);
        userAge = this.findViewById(R.id.user_age);
        diagnoseNumber = this.findViewById(R.id.diagnose_number);
        sampleType = this.findViewById(R.id.sample_type);
        sampleTypeTip = this.findViewById(R.id.sample_type_tip);
        deptName = this.findViewById(R.id.dept_name);
        diagnoseResTv = this.findViewById(R.id.diagnose_res_tv);
        diseaseDecTv = this.findViewById(R.id.disease_dec_tv);
        checkItemTv = this.findViewById(R.id.check_item_tv);
        doctorInstructionTv = this.findViewById(R.id.doctor_instruction_tv);
        checkItemAmount = this.findViewById(R.id.check_item_amount);
        totalFee = this.findViewById(R.id.total_fee);
        applyDoctor = this.findViewById(R.id.apply_doctor);
        applyTime = this.findViewById(R.id.apply_time);
        recyclerView = this.findViewById(R.id.check_item_rv);
        testDetailItemListAdapter = new TestDetailItemListAdapter(this, checkOutList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(testDetailItemListAdapter);
    }

    public static final String ID = "id";
    public static final String INQUIRYBEAN = "inquiry_bean";
    private InquiryBean inquiryBean;

    /**
     * 启动检验单详情页面
     */
    public static void startTestOrder(Context context, InspectionBean inspectionBean,
                                      InquiryBean bean) {
        Intent starter = new Intent(context, CheckOrderDetailActivity.class);
        starter.putExtra(INQUIRYBEAN, bean);
        starter.putExtra(Const.DATA, inspectionBean);
        starter.putExtra(Const.TYPE, TEST_ORDER);
        context.startActivity(starter);
    }

    /**
     * 启动方法
     */
    public static void startInspactOrder(Context context, InspectionBean inspectionBean,
                                         InquiryBean bean) {
        Intent starter = new Intent(context, CheckOrderDetailActivity.class);
        starter.putExtra(INQUIRYBEAN, bean);
        starter.putExtra(Const.DATA, inspectionBean);
        starter.putExtra(Const.TYPE, INSPACT_ORDER);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_apply_order_detail;
    }

    @Override
    public void initData(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        inquiryBean = (InquiryBean) getIntent().getSerializableExtra(INQUIRYBEAN);
        inspectionBean = (InspectionBean) getIntent().getSerializableExtra(Const.DATA);
        mType = getIntent().getIntExtra(Const.TYPE, -1);
        initView();
        if (mType == TEST_ORDER) {
            setTitle("检验申请详情");
            table_title_tv.setText("检验申请单");
            check_item_tip_tv.setText("检验项目：");
        } else {
            setTitle("检查申请详情");
            table_title_tv.setText("检查申请单");
            check_item_tip_tv.setText("检查项目：");
        }
        setCheckOrderInfo();
    }

    /**
     * 设置检验单数据
     */
    private void setCheckOrderInfo() {
        //        editOrderIb.setVisibility(bean.isEdit() ? View.VISIBLE : View.GONE);
        hospitalName.setText(App.hospitalName);
        userName.setText(inspectionBean.getPatientName());
        userSex.setText(CommonUtils.getSex(inspectionBean.getSex()));
        userAge.setText(String.valueOf(inspectionBean.getAge()));
        //        diagnoseNumber.setText(String.valueOf(inspectionBean.getOutpatientNumber()));
        sampleTypeTip.setText("标本类型：");
        //        sampleType.setText(inspectionBean.getSpecimenName());
        deptName.setText(inspectionBean.getDeptName());
        diagnoseResTv.setText(inspectionBean.getDiagnosis());
        checkItemAmount.setText("总共" + CommonUtils.numberToChinese(inspectionBean.getCateS().size()) + "项");
        totalFee.setText("总金额：¥" + inspectionBean.getAmount() + "元");
        applyDoctor.setText(inspectionBean.getDoctorName());
        applyTime.setText(inspectionBean.getUpdateTime());
        diseaseDecTv.setVisibility(View.GONE);
        checkItemTv.setVisibility(View.GONE);
        diseaseDecTipTv.setVisibility(View.GONE);
        doctorInstructionTv.setVisibility(View.GONE);
        doctorInstructionTipTv.setVisibility(View.GONE);
        checkOutList.addAll(getCheckOutList());
        testDetailItemListAdapter.notifyDataSetChanged();
    }

    /**
     * 过滤一级列表（只展示二级）
     */
    private List<CheckOutGroupBean> getCheckOutList() {
        List<CheckOutGroupBean> list = new ArrayList<>();
        for (CheckOutGroupBean bean :
                inspectionBean.getCateS()) {
            list.addAll(bean.getItems());
        }
        return list;
    }

    @Override
    public int getType() {
        return mType;
    }
}
