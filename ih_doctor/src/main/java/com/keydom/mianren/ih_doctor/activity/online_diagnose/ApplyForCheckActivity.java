package com.keydom.mianren.ih_doctor.activity.online_diagnose;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.im.ImClient;
import com.keydom.ih_common.im.model.custom.ExaminationAttachment;
import com.keydom.ih_common.im.model.custom.InspectionAttachment;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.im.ConversationActivity;
import com.keydom.mianren.ih_doctor.activity.online_diagnose.controller.ApplyForCheckController;
import com.keydom.mianren.ih_doctor.activity.online_diagnose.view.ApplyForCheckView;
import com.keydom.mianren.ih_doctor.activity.patient_main_suit.PatientMainSuitActivity;
import com.keydom.mianren.ih_doctor.adapter.DiagnoseOrderSecondaryListRecyclerAdapter;
import com.keydom.mianren.ih_doctor.adapter.InspectItemListAdapter;
import com.keydom.mianren.ih_doctor.adapter.SecondaryListAdapter;
import com.keydom.mianren.ih_doctor.bean.CheckItemListBean;
import com.keydom.ih_common.bean.CheckOutGroupBean;
import com.keydom.mianren.ih_doctor.bean.InquiryBean;
import com.keydom.mianren.ih_doctor.bean.OrderApplyResponse;
import com.keydom.mianren.ih_doctor.bean.SubmitCheckOrderReqBean;
import com.keydom.ih_common.bean.SubmitInspectOrderReqBean;
import com.keydom.mianren.ih_doctor.constant.Const;
import com.keydom.mianren.ih_doctor.m_interface.OnItemChangeListener;
import com.keydom.mianren.ih_doctor.m_interface.SingleClick;
import com.keydom.mianren.ih_doctor.view.DiagnosePrescriptionItemView;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * 修改检查单类型
     */
    public static final int UPDATE_INSPECT = 803;
    /**
     * 修改检验单类型
     */
    public static final int UPDATE_CHECK = 804;
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
    public static final String CHECKITEMLISTBEAN = "check_item_list_bean";
    private TextView applyItemAmountTv, totalFeeTv;
    /**
     * 选择的检查项目列表
     */
    private List<CheckOutGroupBean> selectTestList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RelativeLayout diseaseRl;
    private TextView applyTestAddTv, diagnoseTv, userName, userSex, userAge, diseaseTv;
    /**
     * 主诉
     */
    private DiagnosePrescriptionItemView mainDec;
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
     * 查询到的所有检查项目列表
     */
    private List<CheckOutGroupBean> inspactItemList;
    /**
     * 选中的检查项目列表
     */
    private List<CheckOutGroupBean> inspactSelectItemList = new ArrayList<>();
    /**
     * 问诊单对象
     */
    private InquiryBean orderBean;
    /**
     * 检查、检验单项目（修改用）
     */
    private CheckItemListBean checkItemListBean;
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
     * 修改检查单
     */
    public static void startUpdateInspect(Context context, CheckItemListBean checkItemListBean,
                                          InquiryBean bean) {
        Intent starter = new Intent(context, ApplyForCheckActivity.class);
        starter.putExtra(Const.TYPE, UPDATE_INSPECT);
        starter.putExtra(CHECKITEMLISTBEAN, checkItemListBean);
        starter.putExtra(INQUIRYBEAN, bean);
        ((Activity) context).startActivityForResult(starter, ConversationActivity.SEND_MESSAGE);
    }

    /**
     * 修改检验单
     */
    public static void startUpdateTest(Context context, CheckItemListBean checkItemListBean,
                                       InquiryBean bean) {
        Intent starter = new Intent(context, ApplyForCheckActivity.class);
        starter.putExtra(Const.TYPE, UPDATE_CHECK);
        starter.putExtra(CHECKITEMLISTBEAN, checkItemListBean);
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
        orderBean = (InquiryBean) getIntent().getSerializableExtra(INQUIRYBEAN);
        checkItemListBean = (CheckItemListBean) getIntent().getSerializableExtra(CHECKITEMLISTBEAN);
        String title = "";

        switch (mType) {
            case CREATE_INSPECT_ORDER:
                title = "检查申请";
                initUserInfo();
                getController().inspectList();
                initInspactListDate();
                diseaseTv.setText(orderBean.getConditionDesc());
                diseaseRl.setVisibility(View.VISIBLE);
                checkTipLl.setVisibility(View.GONE);
                break;
            case CREATE_TEST_ORDER:
                initUserInfo();
                title = "检验申请";
                diseaseRl.setVisibility(View.GONE);
                checkTipLl.setVisibility(View.VISIBLE);
                break;
            case UPDATE_INSPECT:
                title = "检查申请";
                getController().inspectList();
                setInspectInfo(checkItemListBean);
                diseaseRl.setVisibility(View.VISIBLE);
                checkTipLl.setVisibility(View.GONE);
                break;
            case UPDATE_CHECK:
                title = "检验申请";

                setCheckOutInfo(checkItemListBean);
                diseaseRl.setVisibility(View.GONE);
                checkTipLl.setVisibility(View.VISIBLE);
                break;
        }
        setTitle(title);
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
                    getController().deleteInquisition(checkItemListBean.getId());
                }
            });
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
        mainDec = findViewById(R.id.main_dec);
        mainDec.setFragmentActivity(this);
        applyTestAddTv.setOnClickListener(getController());
        diagnoseTv.setOnClickListener(getController());
        diseaseTv.setOnClickListener(getController());

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


    /**
     * 设置检查列表
     */
    private void initInspactListDate() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        inspectItemListAdapter = new InspectItemListAdapter(inspactSelectItemList);
        recyclerView.setAdapter(inspectItemListAdapter);
        diseaseRl.setVisibility(View.VISIBLE);
    }


    @Override
    public String getMainDec() {
        return diagnoseTv.getText().toString().trim();
    }

    @Override
    public Map<String, Object> getTestMap() {
        List<SubmitCheckOrderReqBean> list = new ArrayList<>();
        for (CheckOutGroupBean testItemBean : selectTestList) {
            SubmitCheckOrderReqBean bean = new SubmitCheckOrderReqBean();
            List<SubmitCheckOrderReqBean> childList = new ArrayList<>();
            for (CheckOutGroupBean item : testItemBean.getItems()) {
                if (item.isSelect()) {
                    SubmitCheckOrderReqBean submitCheckOrderReqBean = new SubmitCheckOrderReqBean();
                    submitCheckOrderReqBean.setId(item.getProjectId());
                    submitCheckOrderReqBean.setName(item.getInsCheckCateName());
                    childList.add(submitCheckOrderReqBean);
                }
            }
            bean.setItems(childList);
            bean.setId(testItemBean.getProjectId());
            bean.setName(testItemBean.getInsCheckCateName());
            bean.setDeptName(testItemBean.getDeptName());
            bean.setSpecimenName(testItemBean.getSpecimenName());
            bean.setDeptId(testItemBean.getDeptId());
            list.add(bean);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("items", list);
        map.put("diagnosis", getMainDec());
        if (orderBean != null) {
            map.put("inquiryId", orderBean.getId());
        }
        if (checkItemListBean != null) {
            map.put("id", checkItemListBean.getId());
        }

        return map;
    }

    @Override
    public Map<String, Object> getInspectMap() {
        List<SubmitInspectOrderReqBean> reqList = new ArrayList<>();
        for (CheckOutGroupBean bean : inspactSelectItemList) {
            SubmitInspectOrderReqBean reqLevel1Bean = new SubmitInspectOrderReqBean();
            reqLevel1Bean.setItems(bean.getSelectReqList());
            reqLevel1Bean.setId(bean.getProjectId());
            reqLevel1Bean.setName(bean.getInsCheckCateName());
            reqLevel1Bean.setDeptId(bean.getDeptId());
            reqLevel1Bean.setRemark(bean.getRemark());
            reqList.add(reqLevel1Bean);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("items", reqList);
        map.put("diagnosis", getMainDec());
        map.put("conditionDesc", diseaseTv.getText().toString().trim());
        if (orderBean != null) {
            map.put("inquiryId", orderBean.getId());
        }
        if (checkItemListBean != null) {
            map.put("id", checkItemListBean.getId());
        }
        return map;
    }

    @Override
    public void saveTestOrderSuccess(List<OrderApplyResponse> list) {
        List<IMMessage> messageList = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (OrderApplyResponse bean : list) {
                InspectionAttachment inspectionAttachment = new InspectionAttachment();
                inspectionAttachment.setInsCheckOrderId(String.valueOf(bean.getId()));
                inspectionAttachment.getInsCheckApplication().setAmount(bean.getFee().toString());
                inspectionAttachment.getInsCheckApplication().setDiagnosis(bean.getName());
                messageList.add(ImClient.createInspectionMessage(orderBean.getUserCode(),
                        SessionTypeEnum.P2P, "检验申请单", inspectionAttachment));
            }
        }
        Intent intent = new Intent();
        intent.putExtra(Const.DATA, (Serializable) messageList);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void saveTestOrderFailed(String errMsg) {
        ToastUtil.showMessage(this, errMsg);

    }

    @Override
    public void saveInspectOrderSuccess(List<OrderApplyResponse> list) {
        List<IMMessage> messageList = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (OrderApplyResponse bean : list) {
                ExaminationAttachment examinationAttachment = new ExaminationAttachment();
                examinationAttachment.setId(bean.getId());
                examinationAttachment.setAmount(bean.getFee().toString());
                examinationAttachment.setExaminationContent(bean.getName());
                messageList.add(ImClient.createInspectionMessage(orderBean.getUserCode(),
                        SessionTypeEnum.P2P, "检查申请单", examinationAttachment));
            }
        }
        Intent intent = new Intent();
        intent.putExtra(Const.DATA, (Serializable) messageList);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void saveInspectOrderFailed(String errMsg) {
        ToastUtil.showMessage(this, errMsg);
    }

    @Override
    public void getInspectItemListSuccess(List<CheckOutGroupBean> list) {
        inspactItemList = list;
    }

    @Override
    public void getInspectItemListFailed(String errMsg) {
        ToastUtil.showMessage(this, errMsg);
    }

    @Override
    public boolean isInspect() {
        return mType == UPDATE_INSPECT || mType == CREATE_INSPECT_ORDER;
    }

    @Override
    public List<CheckOutGroupBean> getInspectItemList() {
        return inspactItemList;
    }

    @Override
    public List<CheckOutGroupBean> getInspactSelectItemList() {
        return inspactSelectItemList;
    }

    @Override
    public void getSelectInspectItemList(List<CheckOutGroupBean> list) {
        //        inspactSelectItemList.clear();
        inspactSelectItemList.addAll(list);
        inspectItemListAdapter.notifyDataSetChanged();
        setInspactFee();
    }


    /**
     * 设置检验报告信息
     */
    private void setCheckOutInfo(CheckItemListBean bean) {
        userName.setText(bean.getName());
        userSex.setText(CommonUtils.getSex(bean.getSex()));
        userAge.setText(String.valueOf(bean.getAge()));
        diagnoseTv.setText(bean.getDiagnosis());
        selectTestList.clear();
        selectTestList.addAll(bean.getItems());
        setTestListData(selectTestList);
        setCheckFee();
        adapter.notifyDataSetChanged();
    }

    /**
     * 设置检查报告信息
     */
    private void setInspectInfo(CheckItemListBean bean) {
        inspactSelectItemList.addAll(checkItemListBean.getItems());
        initInspactListDate();
        userName.setText(bean.getName());
        userSex.setText(CommonUtils.getSex(bean.getSex()));
        userAge.setText(String.valueOf(bean.getAge()));
        diagnoseTv.setText(bean.getDiagnosis());
        diseaseTv.setText(bean.getConditionDesc());
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
    public boolean isSaveInspectOrder() {
        if (inspactSelectItemList == null || inspactSelectItemList.size() == 0) {
            return false;
        }
        for (CheckOutGroupBean bean : inspactSelectItemList) {
            if (bean.selectedItem() == null) {
                return false;
            } else {
                for (CheckOutGroupBean subBean : bean.selectedItems()) {
                    if (subBean.selectedItem() == null || subBean.selectedItems().size() == 0) {
                        return false;
                    }
                }

            }
        }
        return true;
    }

    @Override
    public boolean isSaveCheckOutOrder() {
        for (CheckOutGroupBean testItemBean : selectTestList) {
            SubmitCheckOrderReqBean bean = new SubmitCheckOrderReqBean();
            List<SubmitCheckOrderReqBean> childList = new ArrayList<>();
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
