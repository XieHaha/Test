package com.keydom.ih_patient.activity.apply_for_order_detail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.constant.Const;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.apply_for_order_detail.controller.CheckOrderDetailController;
import com.keydom.ih_patient.activity.apply_for_order_detail.view.CheckOrderDetailView;
import com.keydom.ih_patient.adapter.TestDetailItemListAdapter;
import com.keydom.ih_patient.bean.CheckItemListBean;
import com.keydom.ih_patient.bean.CheckOutItemBean;
import com.keydom.ih_patient.bean.InquiryBean;

import java.math.BigDecimal;
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
    private long orderId;
    private int mType;

    private TextView hospitalName, userName, userSex, userAge, diagnoseNumber, sampleType, deptName,
            diagnoseResTv, diseaseDecTv, checkItemTv, doctorInstructionTv, diseaseDecTipTv, doctorInstructionTipTv,
            checkItemAmount, totalFee, applyDoctor, applyTime, sampleTypeTip,check_item_tip_tv,table_title_tv;
    private ImageButton editOrderIb;
    private List<CheckOutItemBean> checkOutList = new ArrayList<>();
    private CheckItemListBean checkItemListBean;

    /**
     * 初始化控件
     */
    private void initView() {
        table_title_tv=findViewById(R.id.table_title_tv);
        check_item_tip_tv=findViewById(R.id.check_item_tip_tv);
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
     * 启动方法
     */
    public static void startCheckOrder(Context context, long orderId, InquiryBean bean) {
        Intent starter = new Intent(context, CheckOrderDetailActivity.class);
        starter.putExtra(INQUIRYBEAN, bean);
        starter.putExtra(ID, orderId);
        starter.putExtra(Const.TYPE, TEST_ORDER);
        ((Activity) context).startActivity(starter);
    }

    /**
     * 启动方法
     */
    public static void startInspactOrder(Context context, long orderId, InquiryBean bean) {
        Intent starter = new Intent(context, CheckOrderDetailActivity.class);
        starter.putExtra(INQUIRYBEAN, bean);
        starter.putExtra(ID, orderId);
        starter.putExtra(Const.TYPE, INSPACT_ORDER);
        ((Activity) context).startActivity(starter);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_apply_order_detail;
    }

    @Override
    public void initData(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        orderId = getIntent().getLongExtra(ID, 0);
        inquiryBean = (InquiryBean) getIntent().getSerializableExtra(INQUIRYBEAN);
        mType = getIntent().getIntExtra(Const.TYPE, -1);
        initView();
        if (mType == TEST_ORDER) {
            setTitle("检验申请详情");
            getController().getCheckoutDetail(orderId);
            table_title_tv.setText("检验申请单");
            check_item_tip_tv.setText("检验项目：");
        } else {
            setTitle("检查申请详情");
            getController().getInspectDetail(orderId);
            table_title_tv.setText("检查申请单");
            check_item_tip_tv.setText("检查项目：");

        }

    }

    /**
     * 设置检验单数据
     */
    private void setCheckOrderInfo(CheckItemListBean bean) {
        hospitalName.setText(bean.getHospitalName());
        userName.setText(bean.getName());
        userSex.setText(CommonUtils.getSex(bean.getSex()));
        userAge.setText(String.valueOf(bean.getAge()));
        diagnoseNumber.setText(String.valueOf(bean.getOutpatientNumber()));
        sampleTypeTip.setText("标本类型：");
        sampleType.setText(bean.getSpecimenName());
        deptName.setText(bean.getDeptName());
        diagnoseResTv.setText(bean.getDiagnosis());
        checkItemAmount.setText("总共" + CommonUtils.numberToChinese(bean.getItems().size()) + "项");
        totalFee.setText("总金额：¥" + getTotalFee(bean));
        applyDoctor.setText(bean.getDoctor());
        applyTime.setText(bean.getApplyTime());
        diseaseDecTv.setVisibility(View.GONE);
        checkItemTv.setVisibility(View.GONE);
        diseaseDecTipTv.setVisibility(View.GONE);
        doctorInstructionTv.setVisibility(View.GONE);
        doctorInstructionTipTv.setVisibility(View.GONE);
        checkOutList.addAll(bean.getItems());
        testDetailItemListAdapter.notifyDataSetChanged();

    }

    /**
     * 设置检查单数据
     */
    private void setInspactOrderInfo(CheckItemListBean bean) {
        hospitalName.setText(bean.getHospitalName());
        userName.setText(bean.getName());
        userSex.setText(CommonUtils.getSex(bean.getSex()));
        userAge.setText(String.valueOf(bean.getAge()));
        diagnoseNumber.setText(String.valueOf(bean.getOutpatientNumber()));
        sampleTypeTip.setText("申请项目：");
        String sampleTypeString = "";
        String checkItemStr = "";
        String doctorInstructionStr = "";
        for (CheckOutItemBean item : bean.getItems()) {
            checkItemStr+=assembleProjectName(item.getItems());
            if ("".equals(doctorInstructionStr)) {
                if (item.selectedItem() != null) {
                    doctorInstructionStr = doctorInstructionStr + item.getRemark();
                }
            } else {
                if (item.selectedItem() != null) {
                    doctorInstructionStr = doctorInstructionStr + " " + item.getRemark();
                }
            }
            if ("".equals(sampleTypeString)) {
                sampleTypeString = sampleTypeString + item.getName();
            } else {
                sampleTypeString = sampleTypeString + "," + item.getName();
            }
        }
        sampleType.setText(sampleTypeString);
        deptName.setText(bean.getDeptName());
        diagnoseResTv.setText(bean.getDiagnosis());
        checkItemAmount.setText("总共" + CommonUtils.numberToChinese(bean.getItems().size()) + "项");
        totalFee.setText("总金额：¥" + getTotalFee(bean));
        applyDoctor.setText(bean.getDoctor());
        applyTime.setText(bean.getApplyTime());
        diseaseDecTv.setText(bean.getConditionDesc());
        checkItemTv.setText(checkItemStr);
        doctorInstructionTv.setText(doctorInstructionStr);
        recyclerView.setVisibility(View.GONE);
    }
    private String assembleProjectName( List<CheckOutItemBean> items){
        String assembleNameStr="";
        for (CheckOutItemBean item : items) {
            if(item.isSelect()){
                if(item.getItems()!=null&&item.getItems().size()!=0){
                    for(CheckOutItemBean secodItem : item.getItems()){
                        if(secodItem.isSelect()){
                            if ("".equals(assembleNameStr)) {
                                assembleNameStr = assembleNameStr + secodItem.getName();
                            } else {
                                assembleNameStr = assembleNameStr + "," + secodItem.getName();
                            }
                        }

                    }
                }/*else {
                    if ("".equals(assembleNameStr)) {
                        assembleNameStr = assembleNameStr + item.getName();
                    } else {
                        assembleNameStr = assembleNameStr + "," + item.getName();
                    }
                }*/
            }
        }

        return assembleNameStr;
    }

    /**
     * 计算总的费用
     */
    private BigDecimal getTotalFee(CheckItemListBean bean) {
        BigDecimal totalFee = BigDecimal.ZERO;
        if (bean != null) {
            if (bean.getItems() != null && bean.getItems().size() > 0) {
                for (CheckOutItemBean subBean : bean.getItems()) {
                    if (subBean.selectedItem() != null) {
                        if (subBean.selectedItem().selectedItem() != null) {
                            totalFee = totalFee.add(subBean.selectedItem().totalFee());
                        } else {
                            totalFee = totalFee.add(subBean.getPrice());
                        }
                    }
                }
            }
        }
        return totalFee;
    }


    @Override
    public void getCheckOutDetailSuccess(CheckItemListBean bean) {
        checkItemListBean = bean;
        setCheckOrderInfo(bean);
    }

    @Override
    public void getCheckOutDetailFailed(String errMsg) {
        pageLoadingFail();
        ToastUtil.showMessage(this, "订单不存在");
        finish();
    }

    @Override
    public void getInspactDetailSuccess(CheckItemListBean bean) {
        checkItemListBean = bean;
        setInspactOrderInfo(bean);
    }

    @Override
    public void getInspactDetailFailed(String errMsg) {
        pageLoadingFail();
        ToastUtil.showMessage(this, "订单不存在");
        finish();
    }

    @Override
    public CheckItemListBean getCheckOutOrder() {
        return checkItemListBean;
    }

    @Override
    public InquiryBean getInqueryOrder() {
        return inquiryBean;
    }

    @Override
    public int getType() {
        return mType;
    }
}
