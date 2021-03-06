package com.keydom.mianren.ih_patient.activity.online_diagnoses_order;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SpanUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.ih_common.im.ImClient;
import com.keydom.ih_common.im.config.ImConstants;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.mianren.ih_patient.App;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.AgreementActivity;
import com.keydom.mianren.ih_patient.activity.location_manage.LocationManageActivity;
import com.keydom.mianren.ih_patient.activity.logistic.FixHeightBottomSheetDialog;
import com.keydom.mianren.ih_patient.activity.online_diagnoses_order.controller.OnlineDiagnonsesOrderController;
import com.keydom.mianren.ih_patient.activity.online_diagnoses_order.view.OnlineDiagnonsesOrderView;
import com.keydom.mianren.ih_patient.activity.order_evaluate.OrderEvaluateActivity;
import com.keydom.mianren.ih_patient.adapter.DiagnosesOrderAdapter;
import com.keydom.mianren.ih_patient.bean.DiagnosesOrderBean;
import com.keydom.mianren.ih_patient.bean.Event;
import com.keydom.mianren.ih_patient.bean.LocationInfo;
import com.keydom.mianren.ih_patient.bean.entity.pharmacy.PharmacyBean;
import com.keydom.mianren.ih_patient.callback.SingleClick;
import com.keydom.mianren.ih_patient.constant.Const;
import com.keydom.mianren.ih_patient.constant.EventType;
import com.keydom.mianren.ih_patient.constant.Global;
import com.keydom.mianren.ih_patient.constant.Type;
import com.keydom.mianren.ih_patient.constant.TypeEnum;
import com.keydom.mianren.ih_patient.utils.CommUtil;
import com.keydom.mianren.ih_patient.utils.GotoActivityUtil;
import com.keydom.mianren.ih_patient.utils.SelectDialogUtils;
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
 * ??????????????????fragment
 */
public class OnlineDiagnonsesOrderFragment extends BaseControllerFragment<OnlineDiagnonsesOrderController> implements OnlineDiagnonsesOrderView, DiagnosesOrderAdapter.onItemBtnClickListener, BaseQuickAdapter.OnItemClickListener {
    public static final String STATUS = "subscribe_status";
    public static final String TYPE = "type";
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private RelativeLayout emptyLayout;
    private DiagnosesOrderAdapter diagnosesOrderAdapter;
    private int mStatus;

    private long mAddressId;

    private TextView mPayAddress;
    private TextView mTotalPayTv;
    private RadioButton mHosptalCost;
    private double mTotalFee;
    private double mPSfee;
    private double mPsTotal;
    private int mType;//0 ?????? 1??????

    /**
     * fragment????????????
     */
    public static OnlineDiagnonsesOrderFragment newInstance(int status) {
        Bundle args = new Bundle();
        args.putInt(STATUS, status);
        OnlineDiagnonsesOrderFragment fragment = new OnlineDiagnonsesOrderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * fragment????????????
     */
    public static OnlineDiagnonsesOrderFragment newInstance(int status, int type) {
        Bundle args = new Bundle();
        args.putInt(STATUS, status);
        args.putInt(TYPE, type);
        OnlineDiagnonsesOrderFragment fragment = new OnlineDiagnonsesOrderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_subscribe_examination_order;
    }

    @Override
    public void lazyLoad() {
        getController().getLocationList();
    }

    @Override
    public void getView(View view) {
        mRefreshLayout = view.findViewById(R.id.examination_refresh);
        mRecyclerView = view.findViewById(R.id.examination_rv);
        emptyLayout = view.findViewById(R.id.empty_layout);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        List<DiagnosesOrderBean> orderBeanArrayList = new ArrayList<>();
        diagnosesOrderAdapter = new DiagnosesOrderAdapter(orderBeanArrayList, this);
        diagnosesOrderAdapter.setOnItemClickListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(diagnosesOrderAdapter);
        assert getArguments() != null;
        mStatus = getArguments().getInt(STATUS);
        mType = getArguments().getInt(TYPE);

        mRefreshLayout.setOnRefreshListener(refreshLayout -> getController().getlistPatientInquisition(getMap(), mStatus, TypeEnum.REFRESH));
        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> getController().getlistPatientInquisition(getMap(), mStatus, TypeEnum.LOAD_MORE));
        EventBus.getDefault().register(this);
    }

    @SingleClick(1000)
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        DiagnosesOrderBean bean = diagnosesOrderAdapter.getItem(position);
        int state = bean.getState();
        if (state == diagnosesOrderAdapter.waiteRecieve
                || state == diagnosesOrderAdapter.diagnosing
                || state == diagnosesOrderAdapter.unPassCheck
                || state == diagnosesOrderAdapter.waiteChangeDiagnose
                || state == diagnosesOrderAdapter.doctorCloseDiagnose
                || state == diagnosesOrderAdapter.doctorMakePrescriptions
                || state == diagnosesOrderAdapter.changDoctor
                || state == diagnosesOrderAdapter.waiteEvaluate
                || state == diagnosesOrderAdapter.complete
                || state == diagnosesOrderAdapter.triage
                || state == diagnosesOrderAdapter.consultationWait
                || state == diagnosesOrderAdapter.consultationComplete) {
            if (TextUtils.isEmpty(bean.getGroupTid())) {
                ImClient.startConversation(getContext(), bean.getDoctorCode(), null);
            } else {
                Bundle bundle = new Bundle();
                bundle.putBoolean(ImConstants.TEAM, true);
                bundle.putLong("orderId", bean.getId());
                ImClient.startConversation(getContext(), bean.getGroupTid(), bundle);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mRefreshLayout.autoRefresh();
    }


    /**
     * ????????????map
     */
    private Map<String, Object> getMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", Global.getUserId());
        map.put("hospitalId", App.hospitalId);
        map.put("currentPage", getController().getCurrentPage());
        map.put("pageSize", Const.PAGE_SIZE);
        map.put("type", mType);
        return map;
    }

    /**
     * ????????????
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshList(Event event) {
        if (EventType.REFRESHDIAGNOSESORDER == event.getType()) {
            getController().getlistPatientInquisition(getMap(), mStatus, TypeEnum.REFRESH);
        }
    }

    @Override
    public void onCancelDiagnosesClick(DiagnosesOrderBean item) {
        new GeneralDialog(getContext(), "??????????????????5????????????????????????????????????????????????????????????,??????????????????",
                () -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", item.getId());
                    getController().returnedInquisition(map);
                }).setTitle("??????").setPositiveButton("??????").show();

    }

    @Override
    public void onPayClick(DiagnosesOrderBean item) {
        if (item.getState() == 0) {
            String descStr = item.getInquisitionType() == 0 ?
                    "????????????-" + item.getDoctorName() :
                    "????????????-" + item.getDoctorName();
            if (Global.isMember()) {
                SelectDialogUtils.showPrePaidDialog(getContext(), false,
                        String.valueOf(item.getFee()),
                        descStr, type -> {
                            Map<String, Object> payMap = new HashMap<>();
                            payMap.put("orderId", item.getId());
                            payMap.put("type", 4);
                            getController().inquiryPay(payMap, item, 4);
                        });
            } else {
                SelectDialogUtils.showPayDialog(getContext(), item.getFee() + "", descStr,
                        type -> {
                            Map<String, Object> payMap = new HashMap<>();
                            payMap.put("orderId", item.getId());
                            if (Type.ALIPAY.equals(type)) {
                                payMap.put("type", 2);
                                getController().inquiryPay(payMap, item, 2);
                            } else if (Type.WECHATPAY.equals(type)) {
                                payMap.put("type", 1);
                                getController().inquiryPay(payMap, item, 1);
                            }
                        });
            }

        } else if (item.getState() != 0 && item.getIsSubOrderUnPay() == 1) {
            getController().getChildOrderBean(item.getId(), item.getPrescriptionId());
        }
    }

    @Override
    public void onCommentClick(DiagnosesOrderBean item) {
        OrderEvaluateActivity.start(getContext(), "????????????", Type.DIAGNOSES_ORDER_EVALUATE,
                item);
    }

    @Override
    public void goPay(boolean needDispatch, String orderNum, String orderId,
                      double totalMoney,
                      String prescriptionId, boolean isWaiYan) {
        mTotalFee = totalMoney;
        if (isWaiYan) {
            showPayTypeDialog(String.valueOf(totalMoney), totalMoney, orderNum, orderId,
                    prescriptionId);
        } else {
            showPayDialog(needDispatch, String.valueOf(totalMoney), totalMoney, orderNum);
        }

    }

    /**
     * ??????????????????
     */
    private void showPayDialog(boolean needAddress, String titleFee, double totalFee,
                               String orderNum) {
        String feeTv = new DecimalFormat("0.00").format(totalFee);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity(),
                R.style.BottomSheetDialog);
        bottomSheetDialog.setCancelable(false);
        mPSfee = 0.00;
        final boolean[] isAgree = {false};
        int[] payType = {1};
        bottomSheetDialog.setCanceledOnTouchOutside(false);
        View view =
                LayoutInflater.from(getContext()).inflate(R.layout.pay_ment_dialog_layout,
                        null, false);
        bottomSheetDialog.setContentView(view);

        //????????????????????????????????????
        paymentNormalLayout = view.findViewById(R.id.payment_normal_layout);
        paymentVipLayout = view.findViewById(R.id.payment_vip_layout);
        paymentNextTv = view.findViewById(R.id.prepaid_order_next_tv);
        if (Global.isMember()) {
            //???????????????
            payType[0] = 4;
            paymentNormalLayout.setVisibility(View.GONE);
            paymentVipLayout.setVisibility(View.VISIBLE);
            paymentNextTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (needAddress && mHosptalCost.isChecked()) {
                        if (mAddressId == 0) {
                            ToastUtils.showShort("?????????????????????");
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
        order_price_tv.setText("??" + titleFee + "");
        LinearLayout addressSelectGroup = view.findViewById(R.id.address_select_group);
        LinearLayout addressSelect = view.findViewById(R.id.address_select);
        mPayAddress = view.findViewById(R.id.address);
        mPayAddress.setText("???????????????????????????????????????");
        mPayAddress.setTextColor(getResources().getColor(R.color.edit_hint_color));
        mAddressId = 0;
        RadioButton selfRadio = view.findViewById(R.id.self);
        mHosptalCost = view.findViewById(R.id.hospital);

        SpannableStringBuilder medicalTv =
                new SpanUtils().append("????????????").setFontSize(13, true).setForegroundColor(getResources().getColor(R.color.pay_unselected))
                        .create();
        mHosptalCost.setText(medicalTv);
        addressSelect.setOnClickListener(v -> LocationManageActivity.start(getActivity(),
                Type.PAY_SELECT_ADDRESS));

        final TextView ali_pay_tv = view.findViewById(R.id.ali_pay_tv);
        final TextView wechat_pay_tv = view.findViewById(R.id.wechat_pay_tv);
        TextView pay_agreement_tv = view.findViewById(R.id.pay_agreement_tv);
        mTotalPayTv = view.findViewById(R.id.pay_commit_tv);
        final ImageView ali_pay_selected_img = view.findViewById(R.id.ali_pay_selected_img);
        final ImageView wechat_pay_selected_img =
                view.findViewById(R.id.wechat_pay_selected_img);
        CheckBox payAgreementCb = view.findViewById(R.id.pay_agreement_cb);
        payAgreementCb.setOnCheckedChangeListener((compoundButton, b) -> isAgree[0] = b);
        ImageView close_img = view.findViewById(R.id.close_img);
        ali_pay_selected_img.setOnClickListener(view1 -> {
            ali_pay_selected_img.setImageResource(R.mipmap.pay_selected_icon);
            wechat_pay_selected_img.setImageResource(R.mipmap.pay_unselected_icon);
            ali_pay_tv.setTextColor(getResources().getColor(R.color.pay_selected));
            wechat_pay_tv.setTextColor(getResources().getColor(R.color.pay_unselected));
            payType[0] = 2;
        });
        wechat_pay_selected_img.setOnClickListener(view12 -> {
            ali_pay_selected_img.setImageResource(R.mipmap.pay_unselected_icon);
            wechat_pay_selected_img.setImageResource(R.mipmap.pay_selected_icon);
            ali_pay_tv.setTextColor(getResources().getColor(R.color.pay_unselected));
            wechat_pay_tv.setTextColor(getResources().getColor(R.color.pay_selected));
            payType[0] = 1;
        });
        mTotalPayTv.setText("???????????" + feeTv + "???");
        selfRadio.setOnCheckedChangeListener((compoundButton, b) -> {
            if (selfRadio.isPressed() && b) {
                String f = new DecimalFormat("0.00").format(totalFee);
                mTotalPayTv.setText("???????????" + f + "???");
            }
        });
        mHosptalCost.setOnCheckedChangeListener((compoundButton, b) -> {
            if (mHosptalCost.isPressed() && b) {
                if (mAddressId == 0) {
                    mPsTotal = totalFee;
                }
                String f = new DecimalFormat("0.00").format(mPsTotal);
                mTotalPayTv.setText("???????????" + f + "???");
            }
        });
        mTotalPayTv.setOnClickListener(view13 -> {
            //                if (isAgree[0]) {
            if (needAddress && mHosptalCost.isChecked()) {
                if (mAddressId == 0) {
                    ToastUtils.showShort("?????????????????????");
                } else {
                    getController().pay(mAddressId, orderNum, payType[0], mPsTotal);
                    bottomSheetDialog.dismiss();
                }
            } else {
                getController().pay(0, orderNum, payType[0], totalFee);
                bottomSheetDialog.dismiss();
            }
            //                } else {
            //??????????????????
            //                    ToastUtil.showMessage(getContext(), "??????????????????????????????");
            //                }

        });
        pay_agreement_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //????????????????????????
                AgreementActivity.startPayAgreement(getContext());
            }
        });
        close_img.setOnClickListener(view14 -> bottomSheetDialog.dismiss());
        bottomSheetDialog.show();
        addressSelectGroup.setVisibility(needAddress ? View.VISIBLE : View.GONE);
    }


    TextView mOrderPriceTv;
    private TextView mPyName;
    //?????????
    TextView mDeliveryCostTv;
    TextView mTvAliPay;
    TextView mTvWechatPay;
    TextView mTvUnionPay;
    TextView pay_agreement_tv;
    private TextView mWaiYanTotalPayTv;
    LinearLayout mLinPay;
    TextView mTvShopPay;
    TextView mTvGoPay;

    //???????????????????????????
    ImageView aliPay;
    ImageView wechatPay;
    ImageView unionPay;
    int[] WaiPayType = {1};
    String payWaiType = Type.WECHATPAY;

    //??????????????????
    RadioGroup mRadioGroup;
    RadioButton mRadioSelf;
    RadioButton mRadioHome;

    //????????? ????????????/????????????
    LinearLayout mLinAddress;
    LinearLayout mLinShop;
    RelativeLayout mReZxingTitle;

    LinearLayout payOutSideNormalLayout, payOutSideVipLayout;
    TextView payOutSideNextTv;

    LinearLayout paymentNormalLayout, paymentVipLayout;
    TextView paymentNextTv;

    /**
     * ????????????ID
     */
    private long mWaiYanAddressId;

    public boolean isSendDrugsToHome = false;

    private String mPprescriptionId;

    /**
     * ????????????????????????
     */
    List<PharmacyBean> mPharmacyBeans = null;


    /**
     * ??????????????????
     */
    private void showPayTypeDialog(String titleFee, double totalFee, String orderNum,
                                   String orderId, String prescriptionId) {
        isSendDrugsToHome = false;
        WaiPayType[0] = 1;
        payWaiType = Type.WECHATPAY;
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

        //????????????????????????????????????
        payOutSideNormalLayout = view.findViewById(R.id.pay_outside_normal);
        payOutSideVipLayout = view.findViewById(R.id.pay_outside_vip);
        payOutSideNextTv = view.findViewById(R.id.prepaid_order_next_tv);
        if (Global.isMember()) {
            //???????????????
            payOutSideNormalLayout.setVisibility(View.GONE);
            payOutSideVipLayout.setVisibility(View.VISIBLE);
            payOutSideNextTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PharmacyBean pharmacyBean = null;
                    if (mRadioHome.isChecked()) {
                        if (mWaiYanAddressId == 0) {
                            ToastUtils.showShort("?????????????????????");
                            return;
                        }
                        pharmacyBean = mPharmacyBeans.get(0);
                    } else {
                        if (CommUtil.isEmpty(mPharmacyName) && CommUtil.isEmpty(mPharmacyAddress)) {
                            ToastUtils.showShort("???????????????");
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
        mOrderPriceTv.setText("??" + titleFee);
        LinearLayout addressSelect = view.findViewById(R.id.address_select);
        mPayAddress = view.findViewById(R.id.tv_m_address);
        mPyName = view.findViewById(R.id.tv_shop_name);
        mDeliveryCostTv = view.findViewById(R.id.tv_logistic_fee);


        // ????????????????????????
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

        //????????????
        mWaiYanTotalPayTv = view.findViewById(R.id.pay_commit_tv);
        mLinPay = view.findViewById(R.id.linear_pay);
        mTvShopPay = view.findViewById(R.id.tv_shop_pay);
        mTvGoPay = view.findViewById(R.id.tv_go_pay);


        //???????????????????????????
        aliPay = view.findViewById(R.id.ali_pay_selected_img);
        wechatPay = view.findViewById(R.id.wechat_pay_selected_img);
        unionPay = view.findViewById(R.id.union_pay_selected_img);

        //?????? ????????????/????????????
        mRadioGroup = view.findViewById(R.id.sex_rg);
        mRadioSelf = view.findViewById(R.id.radio_self);
        mRadioHome = view.findViewById(R.id.radio_home);
        mRadioSelf.setChecked(true);

        //????????? ????????????/????????????
        mLinAddress = view.findViewById(R.id.address_select);
        mLinShop = view.findViewById(R.id.linear_shop_select);
        mReZxingTitle = view.findViewById(R.id.re_zxing_title);

        mRadioGroup.setOnCheckedChangeListener(listen);
        // ????????????????????????
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
        mWaiYanTotalPayTv.setText("???????????" + totalFee + "???");
        mWaiYanTotalPayTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRadioHome.isChecked() && mWaiYanAddressId == 0) {
                    ToastUtils.showShort("?????????????????????");
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
                    ToastUtils.showShort("???????????????");
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
                    ToastUtils.showShort("???????????????");
                } else {
                    //?????????
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
                //   ToastUtils.showShort("??????????????????");
            }
        });

        pay_agreement_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //????????????????????????
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


    private RadioGroup.OnCheckedChangeListener listen =
            new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (group.getCheckedRadioButtonId()) {
                        case R.id.radio_self:
                            // ????????????
                            mLinAddress.setVisibility(View.GONE);
                            mLinShop.setVisibility(View.VISIBLE);
                            mReZxingTitle.setVisibility(View.VISIBLE);
                            mWaiYanTotalPayTv.setVisibility(View.GONE);
                            mLinPay.setVisibility(View.VISIBLE);

                            aliPay.setImageResource(R.mipmap.pay_selected_icon);
                            wechatPay.setImageResource(R.mipmap.pay_selected_icon);
                            mTvAliPay.setTextColor(getResources().getColor(R.color.pay_selected));
                            mTvWechatPay.setTextColor(getResources().getColor(R.color.pay_selected));
                            unionPay.setImageResource(R.mipmap.pay_unselected_icon);
                            mTvUnionPay.setTextColor(getResources().getColor(R.color.pay_unselected));
                            WaiPayType[0] = 1;
                            payWaiType = Type.WECHATPAY;
                            isSendDrugsToHome = false;
                            if (null != mPharmacyBean) {
                                refreshPriceView(Arrays.asList(mPharmacyBean));
                            } else {
                                mOrderPriceTv.setText("??" + mTotalFee);
                            }
                            break;
                        case R.id.radio_home:
                            // ????????????
                            mLinAddress.setVisibility(View.VISIBLE);
                            mLinShop.setVisibility(View.GONE);
                            mReZxingTitle.setVisibility(View.GONE);

                            mWaiYanTotalPayTv.setVisibility(View.VISIBLE);
                            mLinPay.setVisibility(View.GONE);

                            aliPay.setImageResource(R.mipmap.pay_selected_icon);
                            wechatPay.setImageResource(R.mipmap.pay_selected_icon);
                            mTvAliPay.setTextColor(getResources().getColor(R.color.pay_selected));
                            mTvWechatPay.setTextColor(getResources().getColor(R.color.pay_selected));
                            unionPay.setImageResource(R.mipmap.pay_unselected_icon);
                            mTvUnionPay.setTextColor(getResources().getColor(R.color.pay_unselected));
                            WaiPayType[0] = 1;
                            payWaiType = Type.WECHATPAY;
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
     * ?????????????????????
     */
    String mPharmacyName = null;
    String mPharmacyAddress = null;

    /**
     * ?????????????????????bean
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

    @Override
    public void refreshPriceView(List<PharmacyBean> data) {
        if (!CommUtil.isEmpty(data) && null != data.get(0)) {
            //mPharmacyBean = data.get(0);
            //BigDecimal deliveryCost = new BigDecimal(data.get(0).getDeliveryCost());
            BigDecimal sumFee = new BigDecimal(data.get(0).getSumFee());
            //?????????????????????????????????
            mOrderPriceTv.setText("???" + sumFee.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() + "???");
            if (mRadioHome.isChecked()) {
                mWaiYanTotalPayTv.setText("???????????" + sumFee.setScale(2,
                        BigDecimal.ROUND_HALF_UP).doubleValue() + "???");
            }

        }

    }

    LocationInfo mLocationInfo;

    /**
     * ??????????????????
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
            Logger.e("??????=" + address);
            getController().getPrescriptionDetailDrugs(address, mPprescriptionId);
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
                mPayAddress.setText("???????????????????????????????????????");
                mPayAddress.setTextColor(getResources().getColor(R.color.edit_hint_color));
                mAddressId = 0;
                SpannableStringBuilder medicalTv =
                        new SpanUtils().append("????????????").setFontSize(13,
                                true).setForegroundColor(getResources().getColor(R.color.pay_unselected))
                                .create();
                if (null != mHosptalCost) {
                    mHosptalCost.setText(medicalTv);
                }
            }
        }
    }


    double deliveryCost;

    @Override
    public void refreshDeliveryCostView(List<PharmacyBean> data) {
        if (!CommUtil.isEmpty(data) && null != data.get(0)) {
            deliveryCost = data.get(0).getDeliveryCost();
            mDeliveryCostTv.setText("???" + deliveryCost + "???");
        }
    }

    @Override
    public double getDeliveryCost() {
        return deliveryCost;
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
            mTotalPayTv.setText("???????????" + f + "???");
            // mTotalPayTv.setText("???????????" + total + "???");
        }
        SpannableStringBuilder medicalTv =
                new SpanUtils().append("????????????").setFontSize(13, true).setForegroundColor(getResources().getColor(R.color.pay_unselected))
                        .append("???????????????").setFontSize(13, true).setForegroundColor(getResources().getColor(R.color.edit_hint_color))
                        .append("??" + fee + "???").setFontSize(13, true).setForegroundColor(getResources().getColor(R.color.nursing_status_red))
                        .append("???").setFontSize(13, true).setForegroundColor(getResources().getColor(R.color.edit_hint_color)).create();
        mHosptalCost.setText(medicalTv);
       /* BigDecimal bd = new BigDecimal(fee);
        Double d = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        double total = d + mTotalFee;
        mTotalPayTv.setText("???????????" + total + "???");*/
    }

    @Override
    public void paySuccess() {
        mRefreshLayout.autoRefresh();
    }

    @Override
    public void getDiagnosesOrderListSuccess(List<DiagnosesOrderBean> orderBeanArrayList,
                                             TypeEnum typeEnum) {
        if (orderBeanArrayList.size() < Const.PAGE_SIZE) {
            mRefreshLayout.finishLoadMoreWithNoMoreData();
        } else {
            mRefreshLayout.finishLoadMore();
        }
        mRefreshLayout.finishRefresh();
        if (typeEnum == TypeEnum.REFRESH) {
            diagnosesOrderAdapter.setNewData(orderBeanArrayList);
        } else {
            diagnosesOrderAdapter.addData(orderBeanArrayList);
        }
        getController().currentPagePlus();
        if (diagnosesOrderAdapter.getItemCount() != 0) {
            emptyLayout.setVisibility(View.GONE);
        } else {
            emptyLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void getDiagnosesOrderListFailed(String errMsg) {
        mRefreshLayout.finishLoadMore();
        mRefreshLayout.finishRefresh();
        ToastUtil.showMessage(getContext(), "??????????????????" + errMsg);
    }

    @Override
    public void returnBackSuccess() {
        ToastUtil.showMessage(getContext(), "????????????");
        EventBus.getDefault().post(new Event(EventType.REFRESHDIAGNOSESORDER, null));
    }

    @Override
    public void returnBackFailed(String errMsg) {
        ToastUtil.showMessage(getContext(), errMsg);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void setPharmacyBeans(List<PharmacyBean> data) {
        mPharmacyBeans = data;
    }
}
