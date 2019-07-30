package com.keydom.ih_patient.activity.payment_records;

import android.content.Intent;
import android.os.Bundle;
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
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.AgreementActivity;
import com.keydom.ih_patient.activity.location_manage.LocationManageActivity;
import com.keydom.ih_patient.activity.payment_records.controller.UnpayRecordController;
import com.keydom.ih_patient.activity.payment_records.view.UnpayRecordView;
import com.keydom.ih_patient.adapter.UnPayRecordAdapter;
import com.keydom.ih_patient.bean.Event;
import com.keydom.ih_patient.bean.LocationInfo;
import com.keydom.ih_patient.bean.PayRecordBean;
import com.keydom.ih_patient.constant.EventType;
import com.keydom.ih_patient.constant.Type;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_unpay_record_layout;
    }

    @Override
    public void getView(@Nullable View view) {
        mPayRecordBeanData = new ArrayList<>();
        mRecyclerView = view.findViewById(R.id.unpay_record_rv);
        mRefreshLayout = view.findViewById(R.id.unpay_record_refresh);
        mCheckBox = view.findViewById(R.id.select_all_record_cb);
        mTotalNum = view.findViewById(R.id.all_record_num_tv);
        mTotalMoney = view.findViewById(R.id.all_price_tv);

        mPay = view.findViewById(R.id.pay_tv);
        mPay.setOnClickListener(getController());
        mCheckBox.setOnClickListener(v -> {
            for (int i = 0; i < mUnPayRecordAdapter.getData().size(); i++) {
                mUnPayRecordAdapter.getData().get(i).setSelect(mCheckBox.isChecked());
            }
            mUnPayRecordAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        initPayInfo();
        mUnPayRecordAdapter = new UnPayRecordAdapter(new ArrayList<>(), this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mUnPayRecordAdapter);
        mRefreshLayout.setOnRefreshListener(refreshLayout -> getController().getConsultationPayList(mRefreshLayout));
        mUnPayRecordAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.pay:
                    PayRecordBean payRecordBean = (PayRecordBean) adapter.getData().get(position);
                    getController().createOrder(payRecordBean.getRecordState() == 8, payRecordBean.getDocumentNo(), payRecordBean.getSumFee());
                    break;
            }
        });
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mCheckBox.isPressed()) {
                    mTotalMoneyStr = new BigDecimal(0.00);
                    mPayRecordBeanList.clear();
                    for (int i = 0; i < mUnPayRecordAdapter.getData().size(); i++) {
                        mUnPayRecordAdapter.getData().get(i).setSelect(isChecked);
                        if (isChecked) {
                            mTotalMoneyStr = mTotalMoneyStr.add(mUnPayRecordAdapter.getData().get(i).getSumFee());
                        }
                    }
                    if (isChecked) {
                        mPayRecordBeanList.addAll(mUnPayRecordAdapter.getData());
                    }
                    refreshTotal();
                }
                //新需求
//                if (mCheckBox.isPressed()) {
//                    mTotalMoneyStr = new BigDecimal(0.00);
//                    mPayRecordBeanList.clear();
//                    if (isChecked && mUnPayRecordAdapter.getData().size() > 0) {
//                        String card = mUnPayRecordAdapter.getData().get(0).getEleCardNumber();
//                        int type = mUnPayRecordAdapter.getData().get(0).getType();
//                        String hint = "";
//                        for (int i = 0; i < mUnPayRecordAdapter.getData().size(); i++) {
//                            PayRecordBean payRecordBean = mUnPayRecordAdapter.getData().get(i);
//                            payRecordBean.setSelect(true);
//                            if (payRecordBean.getType() == CAN_MERGE && !payRecordBean.getEleCardNumber().equals(card)) {
//                                hint = "不能同时为多个就诊人合并缴费";
//                                break;
//                            }
//                            if (payRecordBean.getType() != type) {
//                                hint = "不能同时缴纳诊间费用和预约挂号、在线问诊、护理服务和预约体检项目费用";
//                                break;
//                            }
//                            if (payRecordBean.getType() == CANNOT_MERGE && mPayRecordBeanList.size() > 1) {
//                                hint = "不能同时缴纳预约挂号、在线问诊、护理服务和预约体检项目费用";
//                                break;
//                            }
//                            mTotalMoneyStr = mTotalMoneyStr.add(mUnPayRecordAdapter.getData().get(i).getSumFee());
//                            mPayRecordBeanList.add(mUnPayRecordAdapter.getData().get(i));
//                        }
//                        if (!StringUtils.isEmpty(hint)) {
//                            ToastUtils.showLong(hint);
//                            initPayInfo();
//                            for (int i = 0; i < mUnPayRecordAdapter.getData().size(); i++) {
//                                mUnPayRecordAdapter.getData().get(i).setSelect(false);
//                            }
//                            mCheckBox.setChecked(false);
//                        }
//                    } else {
//                        for (int i = 0; i < mUnPayRecordAdapter.getData().size(); i++) {
//                            mUnPayRecordAdapter.getData().get(i).setSelect(false);
//                        }
//                        mCheckBox.setChecked(false);
//                        mType = 0;
//                    }
//                    new Handler().post(new Runnable() {
//                        @Override
//                        public void run() {
//                            mUnPayRecordAdapter.notifyDataSetChanged();
//                        }
//                    });
//                    refreshTotal();
//                }
            }
        });
        mUnPayRecordAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
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

    @Override
    public void paymentListCallBack(List<PayRecordBean> list) {
        if (mRefreshLayout.isRefreshing()) {
            initPayInfo();
            mCheckBox.setChecked(false);
            mRefreshLayout.finishRefresh();
        }
        if (list.size() != 0) {
            mType = list.get(0).getType();
        }
        mPayRecordBeanData = list;
        mUnPayRecordAdapter.setNewData(list);
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
    public void goPay(boolean needDispatch, String orderNum, double totalMoney) {
        mTotalFee = totalMoney;
        showPayDialog(needDispatch, String.valueOf(totalMoney), totalMoney, orderNum);
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
        SpannableStringBuilder medicalTv = new SpanUtils().append("医院配送").setFontSize(13, true).setForegroundColor(getResources().getColor(R.color.pay_unselected))
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
        if (!payRecordBean.isSelect()) {
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
        refreshTotal();
//
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getLocation(Event event) {
        if (event.getType() == EventType.PAY_SELECT_ADDRESS) {
            LocationInfo locationInfo = (LocationInfo) event.getData();
            String address = locationInfo.getProvinceName() + locationInfo.getCityName() + locationInfo.getAreaName() + locationInfo.getAddress();
            mPayAddress.setText(address);
            mPayAddress.setTextColor(getResources().getColor(R.color.pay_unselected));
            mAddressId = locationInfo.getId();
            getController().getDistributionFee(mAddressId);
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
                SpannableStringBuilder medicalTv = new SpanUtils().append("医院配送").setFontSize(13, true).setForegroundColor(getResources().getColor(R.color.pay_unselected))
                        .create();
                mHosptalCost.setText(medicalTv);
            }
        }
    }

    /**
     * 显示支付弹框
     */
    private void showPayDialog(boolean needAddress, String titleFee, double totalFee, String orderNum) {
        String feeTv = new DecimalFormat("0.00").format(totalFee);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity(), R.style.BottomSheetDialog);
        bottomSheetDialog.setCancelable(false);
        mPSfee = 0.00;
        final boolean[] isAgree = {false};
        int[] payType = {2};
        bottomSheetDialog.setCanceledOnTouchOutside(false);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.pay_ment_dialog_layout, null, false);
        bottomSheetDialog.setContentView(view);
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

        SpannableStringBuilder medicalTv = new SpanUtils().append("医院配送").setFontSize(13, true).setForegroundColor(getResources().getColor(R.color.pay_unselected))
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
//                    ToastUtil.shortToast(getContext(), "请阅读并同意支付协议");
//                }

            }
        });
        pay_agreement_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转支付协议页面
                AgreementActivity.startPayAgreement(getContext());
            }
        });
        close_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.show();
        addressSelectGroup.setVisibility(needAddress ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
