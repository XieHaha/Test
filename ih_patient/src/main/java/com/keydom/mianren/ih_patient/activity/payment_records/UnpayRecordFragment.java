package com.keydom.mianren.ih_patient.activity.payment_records;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.AgreementActivity;
import com.keydom.mianren.ih_patient.activity.location_manage.LocationManageActivity;
import com.keydom.mianren.ih_patient.activity.logistic.FixHeightBottomSheetDialog;
import com.keydom.mianren.ih_patient.activity.payment_records.controller.UnpayRecordController;
import com.keydom.mianren.ih_patient.activity.payment_records.view.UnpayRecordView;
import com.keydom.mianren.ih_patient.adapter.UnPayRecordAdapter;
import com.keydom.mianren.ih_patient.bean.Event;
import com.keydom.mianren.ih_patient.bean.LocationInfo;
import com.keydom.mianren.ih_patient.bean.PayRecordBean;
import com.keydom.mianren.ih_patient.bean.entity.pharmacy.PharmacyBean;
import com.keydom.mianren.ih_patient.callback.SingleClick;
import com.keydom.mianren.ih_patient.constant.Const;
import com.keydom.mianren.ih_patient.constant.EventType;
import com.keydom.mianren.ih_patient.constant.Global;
import com.keydom.mianren.ih_patient.constant.Type;
import com.keydom.mianren.ih_patient.utils.CommUtil;
import com.keydom.mianren.ih_patient.utils.GotoActivityUtil;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 未缴费页面
 */
public class UnpayRecordFragment extends BaseControllerFragment<UnpayRecordController> implements UnpayRecordView, UnPayRecordAdapter.IOnSelectedChanged {
    public static final int CAN_MERGE = 1;
    public static final int CANNOT_MERGE = 2;

    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private CheckBox mCheckBox;
    private TextView mTotalNum;
    private TextView mTotalMoney;
    private TextView mPay;
    private UnPayRecordAdapter mUnPayRecordAdapter;
    /**
     * 缴费总价str
     */
    private BigDecimal mTotalMoneyStr;
    /**
     * 缴费实体集合
     */
    private List<PayRecordBean> mPayRecordBeanList;

    private List<PayRecordBean> mPayRecordBeanData;
    /**
     * 地址id
     */
    private long mAddressId;

    private TextView mPayAddress;
    private RadioButton mHosptalCost;
    private TextView mTotalPayTv;
    private double mPSfee;
    private double mPsTotal;
    /**
     * 缴费总价
     */
    private double mTotalFee;

    private int mType = 0;

    private long patientId;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_unpay_record_layout;
    }

    /**
     * fragment创建
     */
    public static UnpayRecordFragment newInstance(long patient) {
        Bundle args = new Bundle();
        args.putLong(Const.PATIENT_ID, patient);
        UnpayRecordFragment fragment = new UnpayRecordFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void getView(@Nullable View view) {
        patientId = getArguments().getLong(Const.PATIENT_ID);
        mPayRecordBeanData = new ArrayList<>();
        mRecyclerView = view.findViewById(R.id.unpay_record_rv);
        mRefreshLayout = view.findViewById(R.id.unpay_record_refresh);
        mCheckBox = view.findViewById(R.id.select_all_record_cb);
        mTotalNum = view.findViewById(R.id.all_record_num_tv);
        mTotalMoney = view.findViewById(R.id.all_price_tv);

        mPay = view.findViewById(R.id.pay_tv);
        mCheckBox.setOnClickListener(v -> {
            for (int i = 0; i < mUnPayRecordAdapter.getData().size(); i++) {
                if (mUnPayRecordAdapter.getData().get(i).isWaiYan()) {
                    mUnPayRecordAdapter.getData().get(i).setSelect(false);
                }
            }
            refreshTotal();
            mUnPayRecordAdapter.notifyDataSetChanged();
        });
        //        mCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
        //            for (int i = 0; i < mUnPayRecordAdapter.getData().size(); i++) {
        //                mUnPayRecordAdapter.getData().get(i).setSelect(isChecked);
        //            }
        //
        //        });
        mPay.setOnClickListener(getController());
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        initPayInfo();
        mUnPayRecordAdapter = new UnPayRecordAdapter(new ArrayList<>(), this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mUnPayRecordAdapter);
        mRefreshLayout.setOnRefreshListener(refreshLayout -> getController().getConsultationPayList(mRefreshLayout));
        mUnPayRecordAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @SingleClick(1000)
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.pay) {
                    PayRecordBean payRecordBean =
                            (PayRecordBean) adapter.getData().get(position);
                    getController().createOrder(payRecordBean.getRecordState() == 8,
                            payRecordBean.getDocumentNo(), payRecordBean.getSumFee(),
                            payRecordBean.getPrescriptionId(), payRecordBean.isWaiYan());
                }
            }
        });
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
             /*   if (mCheckBox.isPressed()) {
                    mTotalMoneyStr = new BigDecimal(0.00);
                    mPayRecordBeanList.clear();
                    for (int i = 0; i < mUnPayRecordAdapter.getData().size(); i++) {
                        mUnPayRecordAdapter.getData().get(i).setSelect(isChecked);
                        if (isChecked) {
                            mTotalMoneyStr = mTotalMoneyStr.add(mUnPayRecordAdapter.getData().get
                            (i).getSumFee());
                        }
                    }
                    if (isChecked) {
                        mPayRecordBeanList.addAll(mUnPayRecordAdapter.getData());
                    }
                    refreshTotal();
                }*/
                //新需求
                if (mCheckBox.isPressed()) {
                    mTotalMoneyStr = new BigDecimal(0.00);
                    mPayRecordBeanList.clear();
                    if (isChecked && mUnPayRecordAdapter.getData().size() > 0) {
                        String card = mUnPayRecordAdapter.getData().get(0).getEleCardNumber();
                        int type = mUnPayRecordAdapter.getData().get(0).getType();
                        String hint = "";
                        for (int i = 0; i < mUnPayRecordAdapter.getData().size(); i++) {
                            PayRecordBean payRecordBean = mUnPayRecordAdapter.getData().get(i);
                            payRecordBean.setSelect(true);
                            if (payRecordBean.getType() == CAN_MERGE && !payRecordBean.getEleCardNumber().equals(card)) {
                                hint = "不能同时为多个就诊人合并缴费";
                                break;
                            }
                            if (payRecordBean.getType() != type) {
                                hint = "不能同时缴纳诊间费用和预约挂号、在线问诊、护理服务和预约体检项目费用";
                                break;
                            }
                            if (payRecordBean.getType() == CANNOT_MERGE && mPayRecordBeanList.size() > 1) {
                                hint = "不能同时缴纳预约挂号、在线问诊、护理服务和预约体检项目费用";
                                break;
                            }

                            if (payRecordBean.isWaiYan()) {
                                payRecordBean.setSelect(false);
                                continue;
                            }
                            mTotalMoneyStr =
                                    mTotalMoneyStr.add(mUnPayRecordAdapter.getData().get(i).getSumFee());
                            mPayRecordBeanList.add(mUnPayRecordAdapter.getData().get(i));
                        }
                        if (!StringUtils.isEmpty(hint)) {
                            ToastUtils.showLong(hint);
                            initPayInfo();
                            for (int i = 0; i < mUnPayRecordAdapter.getData().size(); i++) {
                                mUnPayRecordAdapter.getData().get(i).setSelect(false);
                            }
                            mCheckBox.setChecked(false);
                        }
                    } else {
                        for (int i = 0; i < mUnPayRecordAdapter.getData().size(); i++) {
                            mUnPayRecordAdapter.getData().get(i).setSelect(false);
                        }
                        mCheckBox.setChecked(false);
                        mType = 0;
                    }
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            mUnPayRecordAdapter.notifyDataSetChanged();
                        }
                    });
                    refreshTotal();
                }
                //新需求
                //                if (mCheckBox.isPressed()) {
                //                    mTotalMoneyStr = new BigDecimal(0.00);
                //                    mPayRecordBeanList.clear();
                //                    if (isChecked && mUnPayRecordAdapter.getData().size() > 0) {
                //                        String card = mUnPayRecordAdapter.getData().get(0)
                //                        .getEleCardNumber();
                //                        int type = mUnPayRecordAdapter.getData().get(0).getType();
                //                        String hint = "";
                //                        for (int i = 0; i < mUnPayRecordAdapter.getData().size
                //                        (); i++) {
                //                            PayRecordBean payRecordBean = mUnPayRecordAdapter
                //                            .getData().get(i);
                //                            if (payRecordBean.getType() == CAN_MERGE &&
                //                            !payRecordBean.getEleCardNumber().equals(card)) {
                //                                hint = "不能同时为多个就诊人合并缴费";
                //                            }
                //                            if (payRecordBean.getType() != type) {
                //                                hint = "不能同时缴纳诊间费用和预约挂号、在线问诊、护理服务和预约体检项目费用";
                //                            }
                //                            if (payRecordBean.getType() == CANNOT_MERGE &&
                //                            mPayRecordBeanList.size() > 1) {
                //                                hint = "不能同时缴纳预约挂号、在线问诊、护理服务和预约体检项目费用";
                //                            }
                //                            mTotalMoneyStr = mTotalMoneyStr.add
                //                            (mUnPayRecordAdapter.getData().get(i).getSumFee());
                //                            mPayRecordBeanList.add(mUnPayRecordAdapter.getData
                //                            ().get(i));
                //                        }
                //                        if (!StringUtils.isEmpty(hint)) {
                //                            ToastUtils.showLong(hint);
                //                        }
                //                    } else {
                //                        for (int i = 0; i < mUnPayRecordAdapter.getData().size
                //                        (); i++) {
                //                            mUnPayRecordAdapter.getData().get(i).setSelect(false);
                //                        }
                //                        mType = 0;
                //                    }
                //                    refreshTotal();
                //                }
            }
        });
        mUnPayRecordAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @SingleClick(1000)
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                PayRecordBean payRecordBean = (PayRecordBean) adapter.getData().get(position);
                String docNum = payRecordBean.getDocumentNo();
                Intent i = new Intent(getActivity(), PaymentDetailActivity.class);
                i.putExtra(PaymentDetailActivity.DOCUMENT_NO, docNum);
                ActivityUtils.startActivity(i);
            }
        });
        getController().getConsultationPayList(mRefreshLayout);
    }

    @Override
    public void refreshData() {
        getController().getConsultationPayList(mRefreshLayout);
    }


    /**
     * 初始化数据
     */
    private void initPayInfo() {
        mTotalNum.setText("共计0项");
        mTotalMoney.setText("0元");
        mTotalMoneyStr = null;
        mTotalMoneyStr = new BigDecimal(0.00);
        mPayRecordBeanList = null;
        mPayRecordBeanList = new ArrayList<>();
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
        getController().getConsultationPayList(mRefreshLayout);
    }

    @Override
    public void paymentListCallBack(List<PayRecordBean> list) {
        if (mRefreshLayout.isRefreshing()) {
            initPayInfo();
            mCheckBox.setChecked(false);
        }
        if (list.size() != 0) {
            mType = list.get(0).getType();
        }

        mRefreshLayout.finishRefresh();
        pageLoadingSuccess();
        mUnPayRecordAdapter.setNewData(list);
        getController().currentPagePlus();
    }

    @Override
    public long getPatientId() {
        return patientId;
    }

    @Override
    public BigDecimal getTotalPay() {
        return mTotalMoneyStr;
    }

    @Override
    public List<PayRecordBean> getSelectList() {
        return mPayRecordBeanList;
    }

    @Override
    public String getDocument() {
        StringBuilder documents = new StringBuilder();
        for (int i = 0; i < mPayRecordBeanList.size(); i++) {
            documents.append(mPayRecordBeanList.get(i).getDocumentNo());
            if (i != mPayRecordBeanList.size() - 1) {
                documents.append(";");
            }
        }
        return documents.toString();
    }

    @Override
    public void goPay(boolean needDispatch, String orderNum, String orderId, double totalMoney,
                      String prescriptionId, boolean isWaiYan) {
        mTotalFee = totalMoney;
        if (isWaiYan) {
            showPayTypeDialog(String.valueOf(totalMoney), totalMoney, orderNum, orderId,
                    prescriptionId);
        } else {
            showPayDialog(needDispatch, String.valueOf(totalMoney), totalMoney, orderNum);
        }

    }

    @Override
    public void paySuccess() {
        getController().getConsultationPayList(mRefreshLayout);
        ActivityUtils.startActivity(PaymentSuccessActivity.class);
    }

    @Override
    public void getDistributionFee(String fee) {
        BigDecimal bd = new BigDecimal(fee);
        Double d = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        mPSfee = d;
        double total = d + mTotalFee;
        mPsTotal = total;
        if (mHosptalCost != null && mHosptalCost.isChecked()) {
            String f = new DecimalFormat("0.00").format(total);
            mTotalPayTv.setText("去付款¥" + f + "元");
            // mTotalPayTv.setText("去付款¥" + total + "元");
        }
        SpannableStringBuilder medicalTv =
                new SpanUtils().append("医院配送").setFontSize(13, true).setForegroundColor(getResources().getColor(R.color.pay_unselected))
                        .append("（配送费用").setFontSize(13, true).setForegroundColor(getResources().getColor(R.color.edit_hint_color))
                        .append("¥" + fee + "元").setFontSize(13, true).setForegroundColor(getResources().getColor(R.color.nursing_status_red))
                        .append("）").setFontSize(13, true).setForegroundColor(getResources().getColor(R.color.edit_hint_color)).create();
        mHosptalCost.setText(medicalTv);
    }

    /**
     * 刷新总价数据
     */
    private void refreshTotal() {
        StringBuilder numSb = new StringBuilder();
        numSb.append("共计");
        numSb.append(mPayRecordBeanList.size());
        numSb.append("项");
        mTotalNum.setText(numSb);

        StringBuilder moneySb = new StringBuilder();
        moneySb.append(mTotalMoneyStr);
        moneySb.append("元");
        mTotalMoney.setText(moneySb);
    }

    @Override
    public void onPriceChanged(PayRecordBean payRecordBean, int position) {
     /*   if (!payRecordBean.isSelect()) {
            mTotalMoneyStr = mTotalMoneyStr.subtract(payRecordBean.getSumFee());
            mPayRecordBeanList.remove(payRecordBean);
        } else {
            mTotalMoneyStr = mTotalMoneyStr.add(payRecordBean.getSumFee());
            mPayRecordBeanList.add(payRecordBean);
        }
        mRefreshLayout.post(() -> {
            if (mUnPayRecordAdapter.getData().size() != mPayRecordBeanList.size()) {
                mCheckBox.setChecked(false);
            }
        });
        refreshTotal();*/

        if (mPayRecordBeanList.size() == 0) {
            mType = payRecordBean.getType();
        } else {
            mType = mPayRecordBeanList.get(0).getType();
        }

        String card = "";
        if (mPayRecordBeanList.size() == 0) {
            card = payRecordBean.getEleCardNumber();
        } else {
            card = mPayRecordBeanList.get(0).getEleCardNumber();
        }
        if (payRecordBean.isSelect()) {
            if (mType == CAN_MERGE) {
                if (payRecordBean.getType() == mType) {
                    if (card.equals(payRecordBean.getEleCardNumber())) {
                        mTotalMoneyStr = mTotalMoneyStr.add(payRecordBean.getSumFee());
                        mPayRecordBeanList.add(payRecordBean);
                        mUnPayRecordAdapter.getData().get(position).setSelect(true);
                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                mUnPayRecordAdapter.notifyItemChanged(position);
                            }
                        });
                    } else {
                        mUnPayRecordAdapter.getData().get(position).setSelect(false);
                        ToastUtils.showLong("不能同时为多个就诊人合并缴费");
                    }
                } else {
                    mUnPayRecordAdapter.getData().get(position).setSelect(false);
                    ToastUtils.showLong("不能同时缴纳诊间费用和预约挂号、在线问诊、护理服务和预约体检项目费用");
                }
            }
            if (mType == CANNOT_MERGE) {
                if (mPayRecordBeanList.size() != 0) {
                    mUnPayRecordAdapter.getData().get(position).setSelect(false);
                    ToastUtils.showLong("不能同时缴纳预约挂号、在线问诊、护理服务和预约体检项目费用");
                } else {
                    mTotalMoneyStr = mTotalMoneyStr.add(payRecordBean.getSumFee());
                    mPayRecordBeanList.add(payRecordBean);
                    mUnPayRecordAdapter.getData().get(position).setSelect(true);
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            mUnPayRecordAdapter.notifyItemChanged(position);
                        }
                    });
                }
            }
        } else {
            mTotalMoneyStr = mTotalMoneyStr.subtract(payRecordBean.getSumFee());
            mPayRecordBeanList.remove(payRecordBean);
        }
        mRefreshLayout.post(() -> {
            if (mUnPayRecordAdapter.getData().size() != mPayRecordBeanList.size()) {
                mCheckBox.setChecked(false);
            }
        });
        refreshTotal();

        //        if (mPayRecordBeanList.size() == 0) {
        //            mType = payRecordBean.getType();
        //        } else {
        //            mType = mPayRecordBeanList.get(0).getType();
        //        }
        //
        //        String card = "";
        //        if (mPayRecordBeanList.size() == 0) {
        //            card = payRecordBean.getEleCardNumber();
        //        } else {
        //            card = mPayRecordBeanList.get(0).getEleCardNumber();
        //        }
        //        if (payRecordBean.isSelect()) {
        //            if (mType == CAN_MERGE) {
        //                if (payRecordBean.getType() == mType) {
        //                    if (card.equals(payRecordBean.getEleCardNumber())) {
        //                        mTotalMoneyStr = mTotalMoneyStr.add(payRecordBean.getSumFee());
        //                        mPayRecordBeanList.add(payRecordBean);
        //                        mUnPayRecordAdapter.getData().get(position).setSelect(true);
        //                        new Handler().post(new Runnable() {
        //                            @Override
        //                            public void run() {
        //                                mUnPayRecordAdapter.notifyItemChanged(position);
        //                            }
        //                        });
        //                    } else {
        //                        mUnPayRecordAdapter.getData().get(position).setSelect(false);
        //                        ToastUtils.showLong("不能同时为多个就诊人合并缴费");
        //                    }
        //                } else {
        //                    mUnPayRecordAdapter.getData().get(position).setSelect(false);
        //                    ToastUtils.showLong("不能同时缴纳诊间费用和预约挂号、在线问诊、护理服务和预约体检项目费用");
        //                }
        //            }
        //            if (mType == CANNOT_MERGE) {
        //                if (mPayRecordBeanList.size() != 0) {
        //                    mUnPayRecordAdapter.getData().get(position).setSelect(false);
        //                    ToastUtils.showLong("不能同时缴纳预约挂号、在线问诊、护理服务和预约体检项目费用");
        //                } else {
        //                    mTotalMoneyStr = mTotalMoneyStr.add(payRecordBean.getSumFee());
        //                    mPayRecordBeanList.add(payRecordBean);
        //                    mUnPayRecordAdapter.getData().get(position).setSelect(true);
        //                    new Handler().post(new Runnable() {
        //                        @Override
        //                        public void run() {
        //                            mUnPayRecordAdapter.notifyItemChanged(position);
        //                        }
        //                    });
        //                }
        //            }
        //        } else {
        //            mTotalMoneyStr = mTotalMoneyStr.subtract(payRecordBean.getSumFee());
        //            mPayRecordBeanList.remove(payRecordBean);
        //        }
        //        mRefreshLayout.post(() -> {
        //            if (mUnPayRecordAdapter.getData().size() != mPayRecordBeanList.size()) {
        //                mCheckBox.setChecked(false);
        //            }
        //        });
        //        refreshTotal();
    }

    @Subscribe
    public void paymenSuccess(Event event) {
        if (event.getType() == EventType.PAYMENT_SUCCESS) {
            mRefreshLayout.autoRefresh();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        getController().getLocationList();
    }

    @Override
    public void getLocationList(List<LocationInfo> data) {
        if (mPayAddress != null) {
            boolean isDelete = false;
            for (int i = 0; i < data.size(); i++) {
                LocationInfo locationInfo = data.get(i);
                if ((locationInfo.getProvinceName() + locationInfo.getCityName() + locationInfo.getAreaName() + locationInfo.getAddress()).equals(mPayAddress.getText().toString())) {
                    isDelete = true;
                    break;
                }
            }
            if (!isDelete) {
                mPayAddress.setText("请选择配送详细地址和联系人");
                mPayAddress.setTextColor(getResources().getColor(R.color.edit_hint_color));
                mAddressId = 0;
                mPSfee = 0.00;
                SpannableStringBuilder medicalTv = new SpanUtils().append("医院配送").setFontSize(13,
                        true).setForegroundColor(getResources().getColor(R.color.pay_unselected))
                        .create();
                if (null != mHosptalCost) {
                    mHosptalCost.setText(medicalTv);
                }

            }
        }
    }

    /**
     * 显示支付弹框
     */
    private void showPayDialog(boolean needAddress, String titleFee, double totalFee,
                               String orderNum) {
        String feeTv = new DecimalFormat("0.00").format(totalFee);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity(),
                R.style.BottomSheetDialog);
        bottomSheetDialog.setCancelable(false);
        mPSfee = 0.00;
        final boolean[] isAgree = {false};
        int[] payType = {2};
        bottomSheetDialog.setCanceledOnTouchOutside(false);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.pay_ment_dialog_layout,
                null, false);
        bottomSheetDialog.setContentView(view);

        //区别普通用户和预付费用户
        paymentNormalLayout = view.findViewById(R.id.payment_normal_layout);
        paymentVipLayout = view.findViewById(R.id.payment_vip_layout);
        paymentNextTv = view.findViewById(R.id.prepaid_order_next_tv);
        if (Global.isMember()) {
            //预付费用户
            payType[0] = 4;
            paymentNormalLayout.setVisibility(View.GONE);
            paymentVipLayout.setVisibility(View.VISIBLE);
            paymentNextTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (needAddress && mHosptalCost.isChecked()) {
                        if (mAddressId == 0) {
                            ToastUtils.showShort("请选择配送地址");
                        } else {
                            getController().pay(mAddressId, orderNum, payType[0], mPsTotal);
                            bottomSheetDialog.dismiss();
                        }
                    } else {
                        getController().pay(0, orderNum, payType[0], totalFee);
                        bottomSheetDialog.dismiss();
                    }
                }
            });

        } else {
            paymentNormalLayout.setVisibility(View.VISIBLE);
            paymentVipLayout.setVisibility(View.GONE);
        }

        final TextView order_price_tv = view.findViewById(R.id.order_price_tv);
        order_price_tv.setText("¥" + titleFee + "");
        LinearLayout addressSelectGroup = view.findViewById(R.id.address_select_group);
        LinearLayout addressSelect = view.findViewById(R.id.address_select);
        mPayAddress = view.findViewById(R.id.address);
        mPayAddress.setText("请选择配送详细地址和联系人");
        mPayAddress.setTextColor(getResources().getColor(R.color.edit_hint_color));
        mAddressId = 0;
        RadioButton selfRadio = view.findViewById(R.id.self);
        mHosptalCost = view.findViewById(R.id.hospital);
        addressSelectGroup.setVisibility(needAddress ? View.VISIBLE : View.GONE);
        SpannableStringBuilder medicalTv =
                new SpanUtils().append("医院配送").setFontSize(13, true).setForegroundColor(getResources().getColor(R.color.pay_unselected))
                        .create();
        mHosptalCost.setText(medicalTv);
        addressSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationManageActivity.start(getActivity(), Type.PAY_SELECT_ADDRESS);
            }
        });

        final TextView ali_pay_tv = view.findViewById(R.id.ali_pay_tv);
        final TextView wechat_pay_tv = view.findViewById(R.id.wechat_pay_tv);
        TextView pay_agreement_tv = view.findViewById(R.id.pay_agreement_tv);
        mTotalPayTv = view.findViewById(R.id.pay_commit_tv);
        final ImageView ali_pay_selected_img = view.findViewById(R.id.ali_pay_selected_img);
        final ImageView wechat_pay_selected_img = view.findViewById(R.id.wechat_pay_selected_img);
        CheckBox payAgreementCb = view.findViewById(R.id.pay_agreement_cb);
        payAgreementCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isAgree[0] = b;
            }
        });
        ImageView close_img = view.findViewById(R.id.close_img);
        ali_pay_selected_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ali_pay_selected_img.setImageResource(R.mipmap.pay_selected_icon);
                wechat_pay_selected_img.setImageResource(R.mipmap.pay_unselected_icon);
                ali_pay_tv.setTextColor(getResources().getColor(R.color.pay_selected));
                wechat_pay_tv.setTextColor(getResources().getColor(R.color.pay_unselected));
                payType[0] = 2;
            }
        });
        wechat_pay_selected_img.setOnClickListener(new View.OnClickListener() {
            @SingleClick(1000)
            @Override
            public void onClick(View view) {
                ali_pay_selected_img.setImageResource(R.mipmap.pay_unselected_icon);
                wechat_pay_selected_img.setImageResource(R.mipmap.pay_selected_icon);
                ali_pay_tv.setTextColor(getResources().getColor(R.color.pay_unselected));
                wechat_pay_tv.setTextColor(getResources().getColor(R.color.pay_selected));
                payType[0] = 1;
            }
        });
        mTotalPayTv.setText("去付款¥" + feeTv + "元");
        selfRadio.setOnCheckedChangeListener((compoundButton, b) -> {
            if (selfRadio.isPressed() && b) {
                String f = new DecimalFormat("0.00").format(totalFee);
                mTotalPayTv.setText("去付款¥" + f + "元");
            }
        });
        mHosptalCost.setOnCheckedChangeListener((compoundButton, b) -> {
            if (mHosptalCost.isPressed() && b) {
                if (mAddressId == 0) {
                    mPsTotal = totalFee;
                }
                String f = new DecimalFormat("0.00").format(mPsTotal);
                mTotalPayTv.setText("去付款¥" + f + "元");
            }
        });
        mTotalPayTv.setOnClickListener(new View.OnClickListener() {
            @SingleClick(1000)
            @Override
            public void onClick(View view) {
                //                if (isAgree[0]) {
                if (needAddress && mHosptalCost.isChecked()) {
                    if (mAddressId == 0) {
                        ToastUtils.showShort("请选择配送地址");
                    } else {
                        getController().pay(mAddressId, orderNum, payType[0], mPsTotal);
                        bottomSheetDialog.dismiss();
                    }
                } else {
                    getController().pay(0, orderNum, payType[0], totalFee);
                    bottomSheetDialog.dismiss();
                }
                //                } else {
                //取消支付协议
                //                    ToastUtil.showMessage(getContext(), "请阅读并同意支付协议");
                //                }

            }
        });
        pay_agreement_tv.setOnClickListener(new View.OnClickListener() {
            @SingleClick(1000)
            @Override
            public void onClick(View view) {
                //跳转支付协议页面
                AgreementActivity.startPayAgreement(getContext());
            }
        });
        close_img.setOnClickListener(new View.OnClickListener() {
            @SingleClick(1000)
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.show();
    }


    TextView mOrderPriceTv;
    private TextView mPyName;
    //配送费
    TextView mDeliveryCostTv;
    TextView mTvAliPay;
    TextView mTvWechatPay;
    TextView mTvUnionPay;
    TextView pay_agreement_tv;
    private TextView mWaiYanTotalPayTv;
    LinearLayout mLinPay;
    TextView mTvShopPay;
    TextView mTvGoPay;

    //支付宝，微信，银联
    ImageView aliPay;
    ImageView wechatPay;
    ImageView unionPay;
    int[] WaiPayType = {2};
    String payWaiType = Type.ALIPAY;

    //外院选择按钮
    RadioGroup mRadioGroup;
    RadioButton mRadioSelf;
    RadioButton mRadioHome;

    //父组件 药店自取/配送到家
    LinearLayout mLinAddress;
    LinearLayout mLinShop;
    RelativeLayout mReZxingTitle;

    /**
     * 外延地址ID
     */
    private long mWaiYanAddressId;

    public boolean isSendDrugsToHome = false;

    private String mPprescriptionId;

    /**
     * 药店选中，配送费
     */
    List<PharmacyBean> mPharmacyBeans = null;

    LinearLayout payOutSideNormalLayout, payOutSideVipLayout;
    TextView payOutSideNextTv;

    LinearLayout paymentNormalLayout, paymentVipLayout;
    TextView paymentNextTv;

    /**
     * 展示支付弹框
     */
    private void showPayTypeDialog(String titleFee, double totalFee, String orderNum,
                                   String orderId, String prescriptionId) {
        isSendDrugsToHome = false;
        WaiPayType[0] = 2;
        payWaiType = Type.ALIPAY;
        mPharmacyBean = null;
        mPharmacyBeans = null;
        mPharmacyName = null;
        mPharmacyAddress = null;
        mWaiYanAddressId = 0;
        FixHeightBottomSheetDialog bottomWaiYanSheetDialog =
                new FixHeightBottomSheetDialog(getActivity());
        bottomWaiYanSheetDialog.setCancelable(false);
        final boolean[] isAgree = {false};

        mPprescriptionId = prescriptionId;

        bottomWaiYanSheetDialog.setCanceledOnTouchOutside(false);
        View view =
                LayoutInflater.from(getActivity()).inflate(R.layout.pay_outside_dialog_layout
                        , null, false);
        bottomWaiYanSheetDialog.setContentView(view);

        //区别普通用户和预付费用户
        payOutSideNormalLayout = view.findViewById(R.id.pay_outside_normal);
        payOutSideVipLayout = view.findViewById(R.id.pay_outside_vip);
        payOutSideNextTv = view.findViewById(R.id.prepaid_order_next_tv);
        if (Global.isMember()) {
            //预付费用户
            payOutSideNormalLayout.setVisibility(View.GONE);
            payOutSideVipLayout.setVisibility(View.VISIBLE);
            payOutSideNextTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PharmacyBean pharmacyBean = null;
                    if (mRadioHome.isChecked()) {
                        if (mWaiYanAddressId == 0) {
                            ToastUtils.showShort("请选择配送地址");
                            return;
                        }
                        pharmacyBean = mPharmacyBeans.get(0);
                    } else {
                        if (CommUtil.isEmpty(mPharmacyName) && CommUtil.isEmpty(mPharmacyAddress)) {
                            ToastUtils.showShort("请选择药店");
                            return;
                        }
                        pharmacyBean = mPharmacyBean;
                    }

                    getController().updatePrescriptionOrder(4, isSendDrugsToHome, true,
                            prescriptionId, orderNum, pharmacyBean, mLocationInfo);
                    bottomWaiYanSheetDialog.dismiss();
                }
            });

        } else {
            payOutSideNormalLayout.setVisibility(View.VISIBLE);
            payOutSideVipLayout.setVisibility(View.GONE);
        }

        mOrderPriceTv = view.findViewById(R.id.order_price_tv);
        mOrderPriceTv.setText("¥" + titleFee);
        LinearLayout addressSelect = view.findViewById(R.id.address_select);
        mPayAddress = view.findViewById(R.id.tv_m_address);
        mPyName = view.findViewById(R.id.tv_shop_name);
        mDeliveryCostTv = view.findViewById(R.id.tv_logistic_fee);


        //todo 跳转选择地址界面
        addressSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationManageActivity.start(getActivity(), Type.WAI_PAY_SELECT_ADDRESS);
            }
        });

        mTvAliPay = view.findViewById(R.id.ali_pay_tv);
        mTvWechatPay = view.findViewById(R.id.wechat_pay_tv);
        mTvUnionPay = view.findViewById(R.id.union_pay_tv);
        pay_agreement_tv = view.findViewById(R.id.pay_agreement_tv);

        //付款按钮
        mWaiYanTotalPayTv = view.findViewById(R.id.pay_commit_tv);
        mLinPay = view.findViewById(R.id.linear_pay);
        mTvShopPay = view.findViewById(R.id.tv_shop_pay);
        mTvGoPay = view.findViewById(R.id.tv_go_pay);


        //支付宝，微信，银联
        aliPay = view.findViewById(R.id.ali_pay_selected_img);
        wechatPay = view.findViewById(R.id.wechat_pay_selected_img);
        unionPay = view.findViewById(R.id.union_pay_selected_img);

        //选择 药店自取/配送到家
        mRadioGroup = view.findViewById(R.id.sex_rg);
        mRadioSelf = view.findViewById(R.id.radio_self);
        mRadioHome = view.findViewById(R.id.radio_home);
        mRadioSelf.setChecked(true);

        //父组件 药店自取/配送到家
        mLinAddress = view.findViewById(R.id.address_select);
        mLinShop = view.findViewById(R.id.linear_shop_select);
        mReZxingTitle = view.findViewById(R.id.re_zxing_title);

        mRadioGroup.setOnCheckedChangeListener(listen);
        //todo 跳转选择药店界面
        mLinShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GotoActivityUtil.gotoChoosePharmacyActivity(getActivity(), prescriptionId);
            }
        });

        ImageView close_img = view.findViewById(R.id.close_img);
        aliPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aliPay.setImageResource(R.mipmap.pay_selected_icon);
                wechatPay.setImageResource(R.mipmap.pay_unselected_icon);
                mTvAliPay.setTextColor(getResources().getColor(R.color.pay_selected));
                mTvWechatPay.setTextColor(getResources().getColor(R.color.pay_unselected));
                unionPay.setImageResource(R.mipmap.pay_unselected_icon);
                mTvUnionPay.setTextColor(getResources().getColor(R.color.pay_unselected));
                WaiPayType[0] = 2;
                payWaiType = Type.ALIPAY;
            }
        });
        wechatPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aliPay.setImageResource(R.mipmap.pay_unselected_icon);
                wechatPay.setImageResource(R.mipmap.pay_selected_icon);
                mTvAliPay.setTextColor(getResources().getColor(R.color.pay_unselected));
                mTvWechatPay.setTextColor(getResources().getColor(R.color.pay_selected));

                unionPay.setImageResource(R.mipmap.pay_unselected_icon);
                mTvUnionPay.setTextColor(getResources().getColor(R.color.pay_unselected));
                WaiPayType[0] = 1;
                payWaiType = Type.WECHATPAY;
            }
        });
        unionPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aliPay.setImageResource(R.mipmap.pay_unselected_icon);
                wechatPay.setImageResource(R.mipmap.pay_unselected_icon);
                mTvAliPay.setTextColor(getResources().getColor(R.color.pay_unselected));
                mTvWechatPay.setTextColor(getResources().getColor(R.color.pay_unselected));
                unionPay.setImageResource(R.mipmap.pay_selected_icon);
                mTvUnionPay.setTextColor(getResources().getColor(R.color.pay_selected));
                WaiPayType[0] = 3;
                payWaiType = Type.UNIONPAY;
            }
        });
        mWaiYanTotalPayTv.setText("去付款¥" + totalFee + "元");
        mWaiYanTotalPayTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRadioHome.isChecked() && mWaiYanAddressId == 0) {
                    ToastUtils.showShort("请选择配送地址");
                } else {
                    PharmacyBean pharmacyBean = mPharmacyBeans.get(0);

                    getController().updatePrescriptionOrder(WaiPayType[0],
                            isSendDrugsToHome,
                            true, prescriptionId, orderNum, pharmacyBean, mLocationInfo);

                    Logger.e("1=" + mWaiYanAddressId);
                    Logger.e("2=" + orderNum);
                    Logger.e("3=" + WaiPayType[0]);
                    Logger.e("4=" + totalFee);
                    bottomWaiYanSheetDialog.dismiss();
                }
            }
        });
        mTvShopPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommUtil.isEmpty(mPharmacyName) && CommUtil.isEmpty(mPharmacyAddress)) {
                    ToastUtils.showShort("请选择药店");
                } else {
                    PharmacyBean pharmacyBean = mPharmacyBean;

                    getController().updatePrescriptionOrder(WaiPayType[0],
                            isSendDrugsToHome,
                            false, prescriptionId, orderNum, pharmacyBean, mLocationInfo);
                    bottomWaiYanSheetDialog.dismiss();
                }
            }
        });
        mTvGoPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommUtil.isEmpty(mPharmacyName) && CommUtil.isEmpty(mPharmacyAddress)) {
                    ToastUtils.showShort("请选择药店");
                } else {
                    //去支付
                    Map<String, Object> map = new HashMap<>();
                    map.put("orderId", orderId);
                    if (payWaiType.equals(Type.WECHATPAY)) {
                        map.put("type", 1);
                    }
                    if (payWaiType.equals(Type.ALIPAY)) {
                        map.put("type", 2);
                    }

                    PharmacyBean pharmacyBean = mPharmacyBean;

                    if (null != pharmacyBean) {
                        getController().updatePrescriptionOrder(WaiPayType[0],
                                isSendDrugsToHome,
                                true, prescriptionId, orderNum, pharmacyBean,
                                mLocationInfo);
                    }


                    Logger.e("map=" + map);
                    bottomWaiYanSheetDialog.dismiss();
                }
                //   ToastUtils.showShort("暂未接入支付");
            }
        });

        pay_agreement_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转支付协议页面
                AgreementActivity.startPayAgreement(getActivity());
            }
        });
        close_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomWaiYanSheetDialog.dismiss();
            }
        });
        bottomWaiYanSheetDialog.show();
    }


    private RadioGroup.OnCheckedChangeListener listen = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (group.getCheckedRadioButtonId()) {
                case R.id.radio_self:
                    //todo 到店自取
                    mLinAddress.setVisibility(View.GONE);
                    mLinShop.setVisibility(View.VISIBLE);
                    mReZxingTitle.setVisibility(View.VISIBLE);
                    mWaiYanTotalPayTv.setVisibility(View.GONE);
                    mLinPay.setVisibility(View.VISIBLE);


                    aliPay.setImageResource(R.mipmap.pay_selected_icon);
                    wechatPay.setImageResource(R.mipmap.pay_unselected_icon);
                    mTvAliPay.setTextColor(getResources().getColor(R.color.pay_selected));
                    mTvWechatPay.setTextColor(getResources().getColor(R.color.pay_unselected));
                    unionPay.setImageResource(R.mipmap.pay_unselected_icon);
                    mTvUnionPay.setTextColor(getResources().getColor(R.color.pay_unselected));
                    WaiPayType[0] = 2;
                    payWaiType = Type.ALIPAY;
                    isSendDrugsToHome = false;
                    if (null != mPharmacyBean) {
                        refreshPriceView(Arrays.asList(mPharmacyBean));
                    }
                    break;

                case R.id.radio_home:
                    //todo 配送到家
                    mLinAddress.setVisibility(View.VISIBLE);
                    mLinShop.setVisibility(View.GONE);
                    mReZxingTitle.setVisibility(View.GONE);

                    mWaiYanTotalPayTv.setVisibility(View.VISIBLE);
                    mLinPay.setVisibility(View.GONE);

                    aliPay.setImageResource(R.mipmap.pay_selected_icon);
                    wechatPay.setImageResource(R.mipmap.pay_unselected_icon);
                    mTvAliPay.setTextColor(getResources().getColor(R.color.pay_selected));
                    mTvWechatPay.setTextColor(getResources().getColor(R.color.pay_unselected));
                    unionPay.setImageResource(R.mipmap.pay_unselected_icon);
                    mTvUnionPay.setTextColor(getResources().getColor(R.color.pay_unselected));
                    WaiPayType[0] = 2;
                    payWaiType = Type.ALIPAY;
                    isSendDrugsToHome = true;
                    if (null != mPharmacyBeans && mPharmacyBeans.size() > 0) {
                        refreshDeliveryCostView(mPharmacyBeans);
                        refreshPriceView(mPharmacyBeans);
                    }
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 药店名字和地址
     */
    String mPharmacyName = null;
    String mPharmacyAddress = null;

    /**
     * 药店选中，详情bean
     */
    PharmacyBean mPharmacyBean = null;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResultPharmacy(Event event) {
        if (event.getType() == EventType.SELECTPHARMACY) {
            Map<String, Object> map = (Map<String, Object>) event.getData();
            mPharmacyName = (String) map.get("mName");
            mPharmacyAddress = (String) map.get("mAddress");
            mPharmacyBean = (PharmacyBean) map.get("pharmacy");
            mPyName.setText(mPharmacyName);
            refreshPriceView(Arrays.asList(mPharmacyBean));
        }
    }

    public void refreshPriceView(List<PharmacyBean> data) {
        if (!CommUtil.isEmpty(data) && null != data.get(0)) {
            //mPharmacyBean = data.get(0);
            //BigDecimal deliveryCost = new BigDecimal(data.get(0).getDeliveryCost());
            BigDecimal sumFee = new BigDecimal(data.get(0).getSumFee());
            //四舍五入，保留两位小数
            mOrderPriceTv.setText("￥" + sumFee.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() + "元");
            if (mRadioHome.isChecked()) {
                mWaiYanTotalPayTv.setText("去付款¥" + sumFee.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() + "元");
            }

        }

    }

    LocationInfo mLocationInfo;

    /**
     * 获取位置信息
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getLocation(Event event) {
        if (event.getType() == EventType.PAY_SELECT_ADDRESS) {
            mLocationInfo = (LocationInfo) event.getData();
            String address =
                    mLocationInfo.getProvinceName() + mLocationInfo.getCityName() + mLocationInfo.getAreaName() + mLocationInfo.getAddress();
            mPayAddress.setText(address);
            mPayAddress.setTextColor(getResources().getColor(R.color.pay_unselected));
            mAddressId = mLocationInfo.getId();
            getController().getDistributionFee(mAddressId);
        } else if (event.getType() == EventType.WAI_PAY_SELECT_ADDRESS) {
            mLocationInfo = (LocationInfo) event.getData();
            String address =
                    mLocationInfo.getProvinceName() + mLocationInfo.getCityName() + mLocationInfo.getAreaName() + mLocationInfo.getAddress();
            mPayAddress.setText(address);
            mWaiYanAddressId = mLocationInfo.getId();
            Logger.e("地址=" + address);
            getController().getPrescriptionDetailDrugs(address, mPprescriptionId);
        }
    }


    public void refreshDeliveryCostView(List<PharmacyBean> data) {
        if (!CommUtil.isEmpty(data) && null != data.get(0)) {
            mDeliveryCostTv.setText("￥" + String.valueOf(data.get(0).getDeliveryCost()) + "元");
        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void setPharmacyBeans(List<PharmacyBean> data) {
        mPharmacyBeans = data;
    }
}
