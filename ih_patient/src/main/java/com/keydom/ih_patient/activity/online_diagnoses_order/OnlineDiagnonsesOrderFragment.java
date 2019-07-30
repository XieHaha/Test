package com.keydom.ih_patient.activity.online_diagnoses_order;

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

import com.blankj.utilcode.util.SpanUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.ih_common.im.ImClient;
import com.keydom.ih_common.im.config.ImConstants;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.ih_patient.App;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.AgreementActivity;
import com.keydom.ih_patient.activity.location_manage.LocationManageActivity;
import com.keydom.ih_patient.activity.online_diagnoses_order.controller.OnlineDiagnonsesOrderController;
import com.keydom.ih_patient.activity.online_diagnoses_order.view.OnlineDiagnonsesOrderView;
import com.keydom.ih_patient.activity.order_evaluate.OrderEvaluateActivity;
import com.keydom.ih_patient.adapter.DiagnosesOrderAdapter;
import com.keydom.ih_patient.bean.DiagnosesOrderBean;
import com.keydom.ih_patient.bean.Event;
import com.keydom.ih_patient.bean.LocationInfo;
import com.keydom.ih_patient.callback.GeneralCallback;
import com.keydom.ih_patient.constant.EventType;
import com.keydom.ih_patient.constant.Global;
import com.keydom.ih_patient.constant.Type;
import com.keydom.ih_patient.utils.SelectDialogUtils;
import com.keydom.ih_patient.utils.ToastUtil;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.keydom.ih_common.im.ImClient.getUserInfoProvider;

/**
 * 在线问诊订单fragment
 */
public class OnlineDiagnonsesOrderFragment extends BaseControllerFragment<OnlineDiagnonsesOrderController> implements OnlineDiagnonsesOrderView, DiagnosesOrderAdapter.onItemBtnClickListener {
    public static final String STATUS = "subscribe_status";
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private DiagnosesOrderAdapter diagnosesOrderAdapter;
    private int mStatus;
    private int page = 1;

    private long mAddressId;

    private TextView mPayAddress;
    private TextView mTotalPayTv;
    private RadioButton mHosptalCost;
    private double mTotalFee;
    private double mPSfee;
    private double mPsTotal;

    /**
     * fragment创建方法
     */
    public static OnlineDiagnonsesOrderFragment newInstance(int status) {
        Bundle args = new Bundle();
        args.putInt(STATUS, status);
        OnlineDiagnonsesOrderFragment fragment = new OnlineDiagnonsesOrderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_subscribe_examination_order;
    }

    @Override
    public void getView(View view) {
        mRefreshLayout = view.findViewById(R.id.examination_refresh);
        mRecyclerView = view.findViewById(R.id.examination_rv);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        List<DiagnosesOrderBean> orderBeanArrayList = new ArrayList<>();
        diagnosesOrderAdapter = new DiagnosesOrderAdapter(orderBeanArrayList, this);
        diagnosesOrderAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Logger.e("click" + position + "  state=" + diagnosesOrderAdapter.getItem(position).getState());
                if (diagnosesOrderAdapter.getItem(position).getState() == diagnosesOrderAdapter.waiteRecieve || diagnosesOrderAdapter.getItem(position).getState() == diagnosesOrderAdapter.diagnosing || diagnosesOrderAdapter.getItem(position).getState() == diagnosesOrderAdapter.unPassCheck ||diagnosesOrderAdapter.getItem(position).getState() == diagnosesOrderAdapter.waiteChangeDiagnose || diagnosesOrderAdapter.getItem(position).getState() == diagnosesOrderAdapter.doctorCloseDiagnose || diagnosesOrderAdapter.getItem(position).getState() == diagnosesOrderAdapter.doctorMakePrescriptions || diagnosesOrderAdapter.getItem(position).getState() == diagnosesOrderAdapter.changDoctor || diagnosesOrderAdapter.getItem(position).getState() == diagnosesOrderAdapter.waiteEvaluate || diagnosesOrderAdapter.getItem(position).getState() == diagnosesOrderAdapter.complete) {
                    if (getUserInfoProvider().getUserInfo(diagnosesOrderAdapter.getItem(position).getDoctorCode()) != null) {
                        NimUserInfo userInfo = (NimUserInfo) getUserInfoProvider().getUserInfo(diagnosesOrderAdapter.getItem(position).getDoctorCode());
                        Map<String, Object> extension = userInfo.getExtensionMap();
                        if (extension != null && extension.get(ImConstants.CALL_USER_TYPE) != null) {
                            ImClient.startConversation(getContext(), diagnosesOrderAdapter.getItem(position).getDoctorCode(), null);

                        } else
                            ToastUtil.shortToast(getContext(), "医生账号异常");
                    }
                }

            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(diagnosesOrderAdapter);
        assert getArguments() != null;
        mStatus = getArguments().getInt(STATUS);

        mRefreshLayout.setOnRefreshListener(refreshLayout -> {
            page = 1;
            getController().getlistPatientInquisition(getMap(), mStatus);
        });
        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> getController().getlistPatientInquisition(getMap(), mStatus));
        EventBus.getDefault().register(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        getController().getLocationList();
        mRefreshLayout.autoRefresh();
    }

    /**
     * 获取请求map
     */
    private Map<String, Object> getMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", Global.getUserId());
        map.put("hospitalId", App.hospitalId);
        map.put("currentPage", page);
        map.put("pageSize", 8);
        return map;
    }

    /**
     * 刷新列表
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshList(Event event) {
        if (EventType.REFRESHDIAGNOSESORDER == event.getType()) {
            page = 1;
            getController().getlistPatientInquisition(getMap(), mStatus);
        }
    }

    @Override
    public void onCancelDiagnosesClick(DiagnosesOrderBean item) {
        new GeneralDialog(getContext(), "问诊费用将在5个工作日内按支付路径退回到您的付款账号中,确认要退诊？", new GeneralDialog.OnCloseListener() {
            @Override
            public void onCommit() {
                Map<String, Object> map = new HashMap<>();
                map.put("id", item.getId());
                getController().returnedInquisition(map);
            }
        }).setTitle("提示").setPositiveButton("确认").show();

    }

    @Override
    public void onPayClick(DiagnosesOrderBean item) {
        if(item.getState()==0){
            String descStr=item.getInquisitionType()==0?"图文问诊-"+item.getDoctorName():"视频问诊-"+item.getDoctorName();
            SelectDialogUtils.showPayDialog(getContext(), item.getFee() + "", descStr, new GeneralCallback.SelectPayMentListener() {
                @Override
                public void getSelectPayMent(String type) {
                    Map<String, Object> payMap = new HashMap<>();
                    payMap.put("orderId", item.getId());
                    if (Type.ALIPAY.equals(type)) {
                        payMap.put("type", 2);
                    } else if (Type.WECHATPAY.equals(type)) {
                        payMap.put("type", 1);
                    }
                    getController().inquiryPay(payMap, item);
                }
            });

        }else if(item.getState()!=0&&item.getIsSubOrderUnPay()==1)
            getController().getChildOrderBean(item.getId());
    }

    @Override
    public void onCommentClick(DiagnosesOrderBean item) {
        OrderEvaluateActivity.start(getContext(), "患者评价", Type.DIAGNOSES_ORDER_EVALUATE, item);
    }

    @Override
    public void goPay(boolean needDispatch, String orderNum, double totalMoney) {
        mTotalFee = totalMoney;
        showPayDialog(needDispatch, String.valueOf(totalMoney), totalMoney, orderNum);
    }

    /**
     * 展示支付弹框
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
    /*private void showPayDialog(boolean needAddress, String titleFee, double totalFee, String orderNum) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity(),R.style.BottomSheetDialog);
        bottomSheetDialog.setCancelable(false);
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
        addressSelectGroup.setVisibility(needAddress ? View.VISIBLE : View.GONE);
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
        mTotalPayTv.setText("去付款¥" + totalFee + "元");
        mTotalPayTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (isAgree[0]) {
                    if (needAddress && mAddressId == 0 && mHosptalCost.isChecked()) {
                        ToastUtils.showShort("请选择配送地址");
                    } else {
                        //去支付
                        getController().pay(mAddressId, orderNum, payType[0], totalFee);
                        bottomSheetDialog.dismiss();
                    }
//                } else {
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
    }*/

    /**
     * 获取位置信息
     */
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
                SpannableStringBuilder medicalTv = new SpanUtils().append("医院配送").setFontSize(13, true).setForegroundColor(getResources().getColor(R.color.pay_unselected))
                        .create();
                mHosptalCost.setText(medicalTv);
            }
        }
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
       /* BigDecimal bd = new BigDecimal(fee);
        Double d = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        double total = d + mTotalFee;
        mTotalPayTv.setText("去付款¥" + total + "元");*/
    }
    @Override
    public void paySuccess() {
    }

    @Override
    public void getDiagnosesOrderListSuccess(List<DiagnosesOrderBean> orderBeanArrayList) {
        if (mRefreshLayout.isRefreshing()) {
            mRefreshLayout.finishRefresh();
        }
        if ((mRefreshLayout.isLoading())) {
            mRefreshLayout.finishLoadMore();
        }
        if (page == 1) {
            diagnosesOrderAdapter.setNewData(orderBeanArrayList);
        } else {
            diagnosesOrderAdapter.addData(orderBeanArrayList);
        }
        if (orderBeanArrayList != null && orderBeanArrayList.size() != 0)
            page += 1;
    }

    @Override
    public void getDiagnosesOrderListFailed(String errMsg) {
        if (mRefreshLayout.isRefreshing()) {
            mRefreshLayout.finishRefresh();
        }
        if ((mRefreshLayout.isLoading())) {
            mRefreshLayout.finishLoadMore();
        }
        ToastUtil.shortToast(getContext(), "数据获取失败" + errMsg);
    }

    @Override
    public void returnBackSuccess() {
        ToastUtil.shortToast(getContext(), "退单成功");
        EventBus.getDefault().post(new Event(EventType.REFRESHDIAGNOSESORDER, null));
    }

    @Override
    public void returnBackFailed(String errMsg) {
        ToastUtil.shortToast(getContext(), "退单失败" + errMsg);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
