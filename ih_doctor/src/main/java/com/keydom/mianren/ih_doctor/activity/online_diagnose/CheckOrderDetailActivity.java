package com.keydom.mianren.ih_doctor.activity.online_diagnose;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ganxin.library.LoadDataLayout;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_doctor.MyApplication;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.im.ConversationActivity;
import com.keydom.mianren.ih_doctor.activity.online_diagnose.controller.CheckOrderDetailController;
import com.keydom.mianren.ih_doctor.activity.online_diagnose.view.CheckOrderDetailView;
import com.keydom.mianren.ih_doctor.adapter.TestDetailItemListAdapter;
import com.keydom.mianren.ih_doctor.bean.CheckItemListBean;
import com.keydom.mianren.ih_doctor.bean.CheckOutItemBean;
import com.keydom.mianren.ih_doctor.bean.InquiryBean;
import com.keydom.mianren.ih_doctor.constant.Const;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.activity.personal
 * @Author：song
 * @Date：18/11/14 上午10:37
 * 修改人：xusong
 * 修改时间：18/11/14 上午10:37
 */
public class CheckOrderDetailActivity extends BaseControllerActivity<CheckOrderDetailController> implements CheckOrderDetailView {

    /**
     * 检验单详情类型
     */
    public static final int TEST_ORDER = 800;
    /**
     * 检查单详情类型
     */
    public static final int INSPACT_ORDER = 801;
    private RecyclerView recyclerView;
    /**
     * 检验单详情－检验项目适配器
     */
    private TestDetailItemListAdapter testDetailItemListAdapter;
    private long orderId;
    private int mType;

    private TextView hospitalName, userName, userSex, userAge, diagnoseNumber, sampleType, deptName,
            diagnoseResTv, diseaseDecTv, checkItemTv, doctorInstructionTv, diseaseDecTipTv,
            doctorInstructionTipTv,
            checkItemAmount, totalFee, applyDoctor, applyTime, sampleTypeTip, titleTipTv,
            checkItemTipTv;
    private ImageButton editOrderIb;
    /**
     * 检验、检查项目列表
     */
    private List<CheckOutItemBean> checkOutList = new ArrayList<>();
    /**
     * 检验、检查详情对象
     */
    private CheckItemListBean checkItemListBean;

    private void initView() {
        doctorInstructionTipTv = this.findViewById(R.id.doctor_instruction_tip_tv);
        diseaseDecTipTv = this.findViewById(R.id.disease_dec_tip_tv);
        titleTipTv = this.findViewById(R.id.title_tip_tv);
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
        checkItemTipTv = this.findViewById(R.id.check_item_tip_tv);
        doctorInstructionTv = this.findViewById(R.id.doctor_instruction_tv);
        checkItemAmount = this.findViewById(R.id.check_item_amount);
        totalFee = this.findViewById(R.id.total_fee);
        applyDoctor = this.findViewById(R.id.apply_doctor);
        applyTime = this.findViewById(R.id.apply_time);
        recyclerView = this.findViewById(R.id.check_item_rv);
        testDetailItemListAdapter = new TestDetailItemListAdapter(this, checkOutList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(testDetailItemListAdapter);
        editOrderIb.setOnClickListener(getController());
    }

    public static final String ID = "id";
    public static final String INQUIRYBEAN = "inquiry_bean";
    private InquiryBean inquiryBean;

    /**
     * 启动检验单详情页面
     *
     * @param context
     * @param orderId 检验单ID
     * @param bean    问诊单对象
     */
    public static void startTestOrder(Context context, long orderId, InquiryBean bean) {
        Intent starter = new Intent(context, CheckOrderDetailActivity.class);
        starter.putExtra(INQUIRYBEAN, bean);
        starter.putExtra(ID, orderId);
        starter.putExtra(Const.TYPE, TEST_ORDER);
        ((Activity) context).startActivityForResult(starter, ConversationActivity.SEND_MESSAGE);
    }

    /**
     * 启动检查单详情页面
     *
     * @param context
     * @param orderId 检查单ID
     * @param bean    问诊单对象
     */
    public static void startInspactOrder(Context context, long orderId, InquiryBean bean) {
        Intent starter = new Intent(context, CheckOrderDetailActivity.class);
        starter.putExtra(INQUIRYBEAN, bean);
        starter.putExtra(ID, orderId);
        starter.putExtra(Const.TYPE, INSPACT_ORDER);
        ((Activity) context).startActivityForResult(starter, ConversationActivity.SEND_MESSAGE);
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
        pageLoading();
        if (mType == TEST_ORDER) {
            setTitle("检验申请详情");
            getController().getCheckoutDetail(orderId);
            titleTipTv.setText("检验申请单");
            checkItemTipTv.setText("检验项目：");
        } else {
            setTitle("检查申请详情");
            getController().getInspectDetail(orderId);
            titleTipTv.setText("检查申请单");
            checkItemTipTv.setText("检查项目：");
        }
        setReloadListener(new LoadDataLayout.OnReloadListener() {
            @Override
            public void onReload(View v, int status) {
                if (mType == TEST_ORDER) {
                    getController().getCheckoutDetail(orderId);
                } else {
                    getController().getInspectDetail(orderId);
                }
            }
        });

    }


    /**
     * 设置检验信息
     *
     * @param bean
     */
    private void setCheckOrderInfo(CheckItemListBean bean) {
        editOrderIb.setVisibility(bean.isEdit() ? View.VISIBLE : View.GONE);
        hospitalName.setText(MyApplication.userInfo.getHospitalName());
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
     * 组装检验项目列表
     *
     * @param list
     * @return
     */
    private List<CheckOutItemBean> assembleInspactItemList(List<CheckOutItemBean> list) {
        List<CheckOutItemBean> assembleList = new ArrayList<>();
        for (CheckOutItemBean bean : list) {
            if (bean.selectedItems() != null && bean.selectedItems().size() > 1) {
                for (CheckOutItemBean subBean : bean.selectedItems()) {
                    try {
                        CheckOutItemBean cpBean = bean.copy();
                        cpBean.reset();
                        cpBean.setSelectItem(subBean);
                        assembleList.add(cpBean);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                assembleList.add(bean);
            }
        }
        return assembleList;
    }


    /**
     * 设置检查单详情
     *
     * @param bean
     */
    private void setInspactOrderInfo(CheckItemListBean bean) {
        List<CheckOutItemBean> beans = assembleInspactItemList(bean.getItems());
        bean.setItems(beans);
        editOrderIb.setVisibility((bean.isEdit() && inquiryBean != null) ? View.VISIBLE :
                View.GONE);
        hospitalName.setText(MyApplication.userInfo.getHospitalName());
        userName.setText(bean.getName());
        userSex.setText(CommonUtils.getSex(bean.getSex()));
        userAge.setText(String.valueOf(bean.getAge()));
        diagnoseNumber.setText(String.valueOf(bean.getOutpatientNumber()));
        sampleTypeTip.setText("申请项目：");
        String sampleTypeString = "";
        String checkItemStr = "";
        String doctorInstructionStr = "";
        for (CheckOutItemBean item : bean.getItems()) {
            if ("".equals(checkItemStr)) {
                if (item.selectedItem() != null) {
                    checkItemStr = item.selectedItem().getName();
                }
            } else {
                if (item.selectedItem() != null) {
                    checkItemStr = checkItemStr + " " + item.selectedItem().getName();
                }
            }
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

    private String assembleProjectName(List<CheckOutItemBean> items) {
        String assembleNameStr = "";
        for (CheckOutItemBean item : items) {
            if (item.isSelect()) {
                if (item.getItems() != null && item.getItems().size() != 0) {
                    for (CheckOutItemBean secodItem : item.getItems()) {
                        if (secodItem.isSelect()) {
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
     * 获取总费用
     *
     * @param bean
     * @return
     */
    private BigDecimal getTotalFee(CheckItemListBean bean) {
        BigDecimal totalFee = BigDecimal.ZERO;
        if (bean != null) {
            if (bean.getItems() != null && bean.getItems().size() > 0) {
                for (CheckOutItemBean subBean : bean.getItems()) {
                    if (subBean.selectedItem() != null) {
                        if (subBean.selectedItem().selectedItem() != null) {
                            for (CheckOutItemBean partBean : subBean.selectedItems()) {
                                totalFee = totalFee.add(partBean.totalFee());
                            }
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
        pageLoadingSuccess();
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
        pageLoadingSuccess();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case ConversationActivity.SEND_MESSAGE:
                    Intent intent = new Intent();
                    intent.putExtra(Const.DATA, data.getSerializableExtra(Const.DATA));
                    setResult(RESULT_OK, intent);
                    finish();
                    break;
            }
        }
    }
}
