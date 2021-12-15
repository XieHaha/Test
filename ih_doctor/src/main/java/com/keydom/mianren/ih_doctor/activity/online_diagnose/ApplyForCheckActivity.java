package com.keydom.mianren.ih_doctor.activity.online_diagnose;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.bean.CheckOutApplyBean;
import com.keydom.ih_common.bean.CheckOutGroupBean;
import com.keydom.ih_common.bean.InspectionApplyBean;
import com.keydom.ih_common.bean.InspectionBean;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.mianren.ih_doctor.MyApplication;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.im.ConversationActivity;
import com.keydom.mianren.ih_doctor.activity.online_diagnose.controller.ApplyForCheckController;
import com.keydom.mianren.ih_doctor.activity.online_diagnose.view.ApplyForCheckView;
import com.keydom.mianren.ih_doctor.activity.patient_main_suit.PatientMainSuitActivity;
import com.keydom.mianren.ih_doctor.adapter.DiagnoseOrderSecondaryListRecyclerAdapter;
import com.keydom.mianren.ih_doctor.adapter.InspectItemListAdapter;
import com.keydom.mianren.ih_doctor.adapter.SecondaryListAdapter;
import com.keydom.mianren.ih_doctor.bean.InquiryBean;
import com.keydom.mianren.ih_doctor.bean.OrderApplyResponse;
import com.keydom.mianren.ih_doctor.constant.Const;
import com.keydom.mianren.ih_doctor.m_interface.OnItemChangeListener;
import com.keydom.mianren.ih_doctor.m_interface.SingleClick;
import com.keydom.mianren.ih_doctor.utils.DateUtils;
import com.keydom.mianren.ih_doctor.view.DiagnosePrescriptionItemView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.activity.personal
 * @Description：新建／修改检查检验
 * @Author：song
 * @Date：18/11/14 上午10:37
 * 修改人：xusong
 * 修改时间：18/11/14 上午10:37
 */
public class ApplyForCheckActivity extends BaseControllerActivity<ApplyForCheckController> implements ApplyForCheckView {
    /**
     * 修改检验、检查单类型
     */
    public static final int UPDATE_ORDER = 804;
    /**
     * 创建检查单类型
     */
    public static final int CREATE_INSPECT_ORDER = 805;
    /**
     * 创建检验单类型
     */
    public static final int CREATE_TEST_ORDER = 806;
    /**
     * 输入病情资料类型
     */
    public static final int INPUT_DISEASE = 807;
    /**
     * 问诊单类型
     */
    public static final String INQUIRYBEAN = "inquiry_bean";
    /**
     * 检查／检验单类型
     */
    public static final String INSPECTION_BEAN = "inspection_bean";
    private TextView applyItemAmountTv, totalFeeTv;
    /**
     * 选择的检查项目列表
     */
    private ArrayList<CheckOutGroupBean> selectTestList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RelativeLayout diseaseRl;
    private TextView applyTestAddTv, diagnoseTv, userName, userSex, userAge, diseaseTv,
            tvMorbidityDate;
    private LinearLayout layoutMorbidityDate;
    /**
     * 主诉
     */
    private DiagnosePrescriptionItemView mainDec, medicalHistory, epidemicHistory;
    /**
     * 检查项目适配器（两级列表）
     */
    private DiagnoseOrderSecondaryListRecyclerAdapter adapter;
    /**
     * 检查项目列表适配器
     */
    private InspectItemListAdapter inspectItemListAdapter;
    /**
     * 检查项目两级列表数据
     */
    private List<SecondaryListAdapter.DataTree<CheckOutGroupBean, CheckOutGroupBean>> datas =
            new ArrayList<>();
    /**
     * 总费用
     */
    private BigDecimal totalFee = BigDecimal.ZERO;
    /**
     * 页面类型
     */
    private int mType;

    /**
     * 是否为检查订单
     */
    private boolean isInspectOrder;
    /**
     * 选中的检查项目列表
     */
    private List<CheckOutGroupBean> inspactSelectItemList = new ArrayList<>();
    /**
     * 问诊单对象
     */
    private InquiryBean orderBean;
    private String orderId;
    /**
     * 已有订单数据（修改模式）
     */
    private InspectionBean inspectionBean;
    private LinearLayout checkTipLl;


    /**
     * 新建检查单
     */
    public static void startCreateInspect(Context context, InquiryBean bean) {
        Intent starter = new Intent(context, ApplyForCheckActivity.class);
        starter.putExtra(Const.TYPE, CREATE_INSPECT_ORDER);
        starter.putExtra(INQUIRYBEAN, bean);
        ((Activity) context).startActivityForResult(starter, ConversationActivity.SEND_MESSAGE);
    }

    /**
     * 新建检验单
     */
    public static void startCreateTest(Context context, InquiryBean bean) {
        Intent starter = new Intent(context, ApplyForCheckActivity.class);
        starter.putExtra(Const.TYPE, CREATE_TEST_ORDER);
        starter.putExtra(INQUIRYBEAN, bean);
        ((Activity) context).startActivityForResult(starter, ConversationActivity.SEND_MESSAGE);
    }

    /**
     * 修改检验、检查单
     */
    public static void startUpdateTest(Context context, InspectionBean inspectionBean,
                                       String orderId, InquiryBean bean) {
        Intent starter = new Intent(context, ApplyForCheckActivity.class);
        starter.putExtra(Const.TYPE, UPDATE_ORDER);
        starter.putExtra(Const.ORDER_ID, orderId);
        starter.putExtra(INSPECTION_BEAN, inspectionBean);
        starter.putExtra(INQUIRYBEAN, bean);
        ((Activity) context).startActivityForResult(starter, ConversationActivity.SEND_MESSAGE);
    }


    @Override
    public int getLayoutRes() {
        return R.layout.activity_apply_for_test;
    }

    @Override
    public void initData(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        initView();
        mType = getIntent().getIntExtra(Const.TYPE, 0);
        orderId = getIntent().getStringExtra(Const.ORDER_ID);
        orderBean = (InquiryBean) getIntent().getSerializableExtra(INQUIRYBEAN);
        inspectionBean = (InspectionBean) getIntent().getSerializableExtra(INSPECTION_BEAN);
        String title = "";
        switch (mType) {
            case CREATE_INSPECT_ORDER:
                isInspectOrder = true;
                title = "检查申请";
                diseaseTv.setText(orderBean.getConditionDesc());
                diseaseRl.setVisibility(View.VISIBLE);
                checkTipLl.setVisibility(View.GONE);
                break;
            case CREATE_TEST_ORDER:
                isInspectOrder = false;
                title = "检验申请";
                diseaseRl.setVisibility(View.GONE);
                checkTipLl.setVisibility(View.VISIBLE);
                break;
            case UPDATE_ORDER:
                if (inspectionBean != null) {
                    if (inspectionBean.getType() == 1) {
                        isInspectOrder = false;
                        title = "检验申请";
                        diseaseRl.setVisibility(View.GONE);
                        checkTipLl.setVisibility(View.VISIBLE);
                    } else {
                        isInspectOrder = true;
                        title = "检查申请";
                        diseaseRl.setVisibility(View.VISIBLE);
                        checkTipLl.setVisibility(View.GONE);
                    }
                }
                break;
            default:
        }
        setTitle(title);
        bindData();
        initUserInfo();
        if (mType == CREATE_INSPECT_ORDER || mType == CREATE_TEST_ORDER) {
            setRightBtnListener(getController());
            setRightTxt("提交");
        } else {
            getTitleLayout().showRightDoubleBtn();
            getTitleLayout().setDoubleRightListener(new IhTitleLayout.OnRightTextClickListener() {
                @SingleClick(1000)
                @Override
                public void OnRightTextClick(View v) {
                    getController().saveOrder();
                }
            }, new IhTitleLayout.OnRightTextClickListener() {
                @SingleClick(1000)
                @Override
                public void OnRightTextClick(View v) {
                    new GeneralDialog(ApplyForCheckActivity.this, "是否确认删除此申请单?",
                            () -> getController().cancelCheckout(orderId)).show();

                }
            });
        }
    }

    /**
     * 已有订单数据回填
     */
    private void bindData() {
        if (inspectionBean != null) {
            //主诉
            mainDec.setText(inspectionBean.getComplaint());
            //现病史
            medicalHistory.setText(inspectionBean.getHistoryAllergy());
            //流行病学史
            epidemicHistory.setText(inspectionBean.getEpidemicDiseaseHistory());
            //诊断
            diagnoseTv.setText(inspectionBean.getDiagnosis());
            //描述
            diseaseTv.setText(inspectionBean.getConditionDesc());
            //项目
            selectTestList.clear();
            selectTestList.addAll(inspectionBean.getCateS());
            setTestListData(selectTestList);
            setCheckFee();
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 初始化用户信息
     */
    private void initUserInfo() {
        if (orderBean != null) {
            userName.setText(orderBean.getName());
            userSex.setText(CommonUtils.getSex(orderBean.getSex()));
            userAge.setText(orderBean.getAge());
        }
    }

    /**
     * 初始化布局
     */
    private void initView() {
        checkTipLl = this.findViewById(R.id.check_tip_ll);
        userName = this.findViewById(R.id.user_name);
        diseaseTv = this.findViewById(R.id.disease_tv);
        diseaseRl = this.findViewById(R.id.disease_rl);
        userSex = this.findViewById(R.id.user_sex);
        userAge = this.findViewById(R.id.user_age);
        applyItemAmountTv = this.findViewById(R.id.apply_item_amount);
        totalFeeTv = this.findViewById(R.id.total_fee);
        recyclerView = this.findViewById(R.id.apply_item_rv);
        applyTestAddTv = this.findViewById(R.id.apply_test_add_tv);
        diagnoseTv = this.findViewById(R.id.diagnose_tv);
        tvMorbidityDate = findViewById(R.id.tv_morbidity_date);
        layoutMorbidityDate = findViewById(R.id.layout_morbidity_date);
        mainDec = findViewById(R.id.main_dec);
        medicalHistory = findViewById(R.id.medical_history);
        epidemicHistory = findViewById(R.id.epidemic_history);
        mainDec.setFragmentActivity(this);
        medicalHistory.setFragmentActivity(this);
        epidemicHistory.setFragmentActivity(this);
        applyTestAddTv.setOnClickListener(getController());
        diagnoseTv.setOnClickListener(getController());
        diseaseTv.setOnClickListener(getController());
        layoutMorbidityDate.setOnClickListener(getController());

        mainDec.setAddOnClikListener(v -> PatientMainSuitActivity.start(ApplyForCheckActivity.this,
                mainDec.getInputStr()));
    }


    /**
     * 设置检查费用
     */
    private void setCheckFee() {
        totalFee = BigDecimal.ZERO;
        for (CheckOutGroupBean bean : selectTestList) {
            bean.setTotalFee(BigDecimal.ZERO);
            for (CheckOutGroupBean subBean : bean.getItems()) {
                if (subBean.isSelect()) {
                    if (subBean.getPrice() != null && bean.getTotalFee() != null) {
                        bean.setTotalFee(subBean.getPrice().add(bean.getTotalFee()));
                    }
                }
            }
            totalFee = totalFee.add(bean.getTotalFee());
        }
        applyItemAmountTv.setText("总共" + CommonUtils.sectionTrans(selectTestList.size()) + "项");
        totalFeeTv.setText("金额：¥ " + totalFee);
    }


    /**
     * 设置检验列表
     */
    private void setTestListData(List<CheckOutGroupBean> list) {
        datas.clear();
        for (int i = 0; i < list.size(); i++) {
            datas.add(new SecondaryListAdapter.DataTree<>(list.get(i), list.get(i).getItems()));
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        adapter = new DiagnoseOrderSecondaryListRecyclerAdapter(this, true);
        adapter.setData(datas);
        adapter.setOnItemChangeListener(new OnItemChangeListener() {
            @Override
            public void changeItem(int position) {
                setCheckFee();
            }

            @Override
            public void removeItem(int position) {
                selectTestList.remove(position);
                setCheckFee();
            }
        });
        recyclerView.setAdapter(adapter);
    }


    @Override
    public InspectionApplyBean getCheckoutParams() {
        InspectionApplyBean bean = new InspectionApplyBean();
        //type 1、检验，2、检查
        if (isInspect()) {
            bean.setType(2);
        } else {
            bean.setType(1);
        }
        switch (mType) {
            case CREATE_INSPECT_ORDER:
                bean.setConditionDesc(getDisease());
                break;
            case UPDATE_ORDER:
                //修改订单时 需要订单id
                bean.setId(orderId);
                break;
            default:
        }
        bean.setPatientName(orderBean.getName());
        bean.setAge(orderBean.getAge());
        bean.setSex(String.valueOf(orderBean.getSex()));
        bean.setPatientId(String.valueOf(orderBean.getPatientId()));
        bean.setElcCardNumber(orderBean.getCardNumber());
        bean.setInquiryOrderId(String.valueOf(orderBean.getId()));
        bean.setComplaint(mainDec.getInputStr());
        bean.setDiagnosis(getDiagnose());
        bean.setConditionDesc(orderBean.getConditionDesc());
        bean.setHospitalId(String.valueOf(MyApplication.userInfo.getHospitalId()));
        bean.setAmount(totalFee.toString());
        bean.setCateS(getSelectApplyList());
        bean.setIllnessDate(tvMorbidityDate.getText().toString());
        bean.setEpidemicDiseaseHistory(epidemicHistory.getInputStr());
        bean.setHistoryAllergy(medicalHistory.getInputStr());
        return bean;
    }

    /**
     * @param date 发病日期
     */
    @Override
    public void setMorbidityDate(Date date) {
        tvMorbidityDate.setText(DateUtils.dateToString(date, DateUtils.YYYY_MM_DD));
    }

    private ArrayList<CheckOutApplyBean> getSelectApplyList() {
        ArrayList<CheckOutApplyBean> list = new ArrayList<>();
        for (CheckOutGroupBean groupItem : selectTestList) {
            //父级项目
            CheckOutApplyBean bean = new CheckOutApplyBean();
            bean.setInsCheckApplicationId(groupItem.getId());
            bean.setInsCheckCateCode(TextUtils.isEmpty(groupItem.getCateCode()) ?
                    groupItem.getInsCheckCateCode() : groupItem.getCateCode());
            bean.setInsCheckCateName(TextUtils.isEmpty(groupItem.getName()) ?
                    groupItem.getInsCheckCateName() : groupItem.getName());
            //子级项目
            List<CheckOutApplyBean> subList = new ArrayList<>();
            for (CheckOutGroupBean subItem : groupItem.getItems()) {
                if (subItem.isSelect()) {
                    CheckOutApplyBean subBean = new CheckOutApplyBean();
                    subBean.setInsCheckApplicationId(subItem.getId());
                    subBean.setExecuteDeptCode(subItem.getExecuteDeptCode());
                    subBean.setExecuteDeptName(subItem.getExecuteDeptName());
                    subBean.setApplicationCode(subItem.getApplicationCode());
                    subBean.setApplicationName(subItem.getApplicationName());
                    subBean.setInsCheckItemCode(TextUtils.isEmpty(subItem.getItemCode()) ?
                            subItem.getInsCheckItemCode() : subItem.getItemCode());
                    subBean.setInsCheckItemName(TextUtils.isEmpty(subItem.getItemName()) ?
                            subItem.getInsCheckItemName() : subItem.getItemName());
                    subBean.setPrice(subItem.getPrice().toString());
                    subList.add(subBean);
                }
            }
            bean.setItems(subList);
            list.add(bean);
        }
        return list;
    }

    @Override
    public void saveTestOrderSuccess(List<OrderApplyResponse> list) {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void saveTestOrderFailed(String errMsg) {
        ToastUtil.showMessage(this, errMsg);
    }

    @Override
    public boolean isInspect() {
        return isInspectOrder;
    }

    @Override
    public void getSelectInspectItemList(List<CheckOutGroupBean> list) {
        inspactSelectItemList.addAll(list);
        inspectItemListAdapter.notifyDataSetChanged();
        setInspactFee();
    }

    /**
     * 设置检查费用
     */
    public void setInspactFee() {
        totalFee = BigDecimal.ZERO;
        for (CheckOutGroupBean bean : inspactSelectItemList) {
            List<CheckOutGroupBean> list = bean.selectedItems();
            for (CheckOutGroupBean subBean : list) {
                if (subBean != null) {
                    if (subBean.selectedItem() != null) {
                        totalFee = totalFee.add(subBean.getSelectTotalFee());
                    }
                }
            }
        }
        applyItemAmountTv.setText("总共" + CommonUtils.sectionTrans(inspactSelectItemList.size()) + "项");
        totalFeeTv.setText("金额：¥ " + totalFee);
    }

    @Override
    public boolean isSaveCheckOutOrder() {
        if (TextUtils.isEmpty(mainDec.getInputStr()) || TextUtils.isEmpty(medicalHistory.getInputStr())
                || TextUtils.isEmpty(epidemicHistory.getInputStr()) || TextUtils.isEmpty(getDiagnose())
                || TextUtils.isEmpty(tvMorbidityDate.getText().toString())) {
            return false;
        }
        for (CheckOutGroupBean testItemBean : selectTestList) {
            for (CheckOutGroupBean item : testItemBean.getItems()) {
                if (item.isSelect()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void deleteOrderSuccess() {
        ToastUtil.showMessage(this, "操作成功");
        ActivityUtils.finishActivity(CheckOrderDetailActivity.class);
        finish();
    }

    @Override
    public void deleteOrderFailed(String errMsg) {
        ToastUtil.showMessage(this, errMsg);
    }


    @Override
    public String getDisease() {
        return diseaseTv.getText().toString().trim();
    }

    @Override
    public String getDiagnose() {
        return diagnoseTv.getText().toString().trim();
    }

    @Override
    public List<CheckOutGroupBean> getCheckOutSelectItemList() {
        return selectTestList;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Const.TEST_ITEM_SELECT:
                    selectTestList.clear();
                    selectTestList.addAll((List<CheckOutGroupBean>) data.getSerializableExtra(Const.DATA));
                    setTestListData(selectTestList);
                    setCheckFee();
                    adapter.notifyDataSetChanged();
                    break;
                case DiagnoseInputActivity.DIAGNOSE_RES_INPUT:
                    diagnoseTv.setText(data.getStringExtra(Const.DATA));
                    break;
                case INPUT_DISEASE:
                    diseaseTv.setText(data.getStringExtra(Const.DATA));
                    break;
                case ChooseInspectItemActivity.CHOOSE_INSPECT_ITEM:
                    getSelectInspectItemList((List<CheckOutGroupBean>) data.getSerializableExtra(Const.DATA));
                    break;
                case PatientMainSuitActivity.SELECT_PATIENT_MAIN_DEC:
                    String decStr = (String) data.getSerializableExtra(Const.DATA);
                    mainDec.setText(decStr);
                    break;
                default:
                    break;
            }
        }
    }
}
