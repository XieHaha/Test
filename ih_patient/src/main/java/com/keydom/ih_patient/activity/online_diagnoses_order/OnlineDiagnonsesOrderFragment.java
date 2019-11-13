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
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
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
import com.keydom.ih_patient.activity.logistic.FixHeightBottomSheetDialog;
import com.keydom.ih_patient.activity.online_diagnoses_order.controller.OnlineDiagnonsesOrderController;
import com.keydom.ih_patient.activity.online_diagnoses_order.view.OnlineDiagnonsesOrderView;
import com.keydom.ih_patient.activity.order_evaluate.OrderEvaluateActivity;
import com.keydom.ih_patient.adapter.DiagnosesOrderAdapter;
import com.keydom.ih_patient.bean.DiagnosesOrderBean;
import com.keydom.ih_patient.bean.Event;
import com.keydom.ih_patient.bean.LocationInfo;
import com.keydom.ih_patient.bean.entity.pharmacy.PharmacyBean;
import com.keydom.ih_patient.callback.GeneralCallback;
import com.keydom.ih_patient.callback.SingleClick;
import com.keydom.ih_patient.constant.EventType;
import com.keydom.ih_patient.constant.Global;
import com.keydom.ih_patient.constant.Type;
import com.keydom.ih_patient.constant.TypeEnum;
import com.keydom.ih_patient.utils.CommUtil;
import com.keydom.ih_patient.utils.GotoActivityUtil;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.keydom.ih_common.im.ImClient.getUserInfoProvider;

/**
 * 在线问诊订单fragment
 */
public class OnlineDiagnonsesOrderFragment extends BaseControllerFragment<OnlineDiagnonsesOrderController> implements OnlineDiagnonsesOrderView, DiagnosesOrderAdapter.onItemBtnClickListener {
    public static final String STATUS = "subscribe_status";
    public static final String TYPE = "type";
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
    private int mType;//0 医生 1护士

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

    /**
     * fragment创建方法
     */
    public static OnlineDiagnonsesOrderFragment newInstance(int status,int type) {
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
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        List<DiagnosesOrderBean> orderBeanArrayList = new ArrayList<>();
        diagnosesOrderAdapter = new DiagnosesOrderAdapter(orderBeanArrayList, this);
        diagnosesOrderAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @SingleClick(1000)
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
        mType = getArguments().getInt(TYPE);

        mRefreshLayout.setOnRefreshListener(refreshLayout -> {
            page = 1;
            getController().getlistPatientInquisition(getMap(), mStatus,TypeEnum.REFRESH);
        });
        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> getController().getlistPatientInquisition(getMap(), mStatus,TypeEnum.LOAD_MORE));
        EventBus.getDefault().register(this);
    }

    @Override
    public void onResume() {
        super.onResume();
       // getController().getLocationList();
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
        map.put("type", mType);
        return map;
    }

    /**
     * 刷新列表
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshList(Event event) {
        if (EventType.REFRESHDIAGNOSESORDER == event.getType()) {
            page = 1;
            getController().getlistPatientInquisition(getMap(), mStatus,TypeEnum.REFRESH);
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
                        getController().inquiryPay(payMap, item,2);
                    } else if (Type.WECHATPAY.equals(type)) {
                        payMap.put("type", 1);
                        getController().inquiryPay(payMap, item,1);
                    }

                }
            });

        }else if(item.getState()!=0&&item.getIsSubOrderUnPay()==1)
            getController().getChildOrderBean(item.getId(),item.getPrescriptionId());
    }

    @Override
    public void onCommentClick(DiagnosesOrderBean item) {
        OrderEvaluateActivity.start(getContext(), "患者评价", Type.DIAGNOSES_ORDER_EVALUATE, item);
    }

    @Override
    public void goPay(boolean needDispatch, String orderNum,String orderId, double totalMoney,String prescriptionId,boolean isWaiYan) {
        mTotalFee = totalMoney;
        if(isWaiYan){
            showPayTypeDialog(String.valueOf(totalMoney), totalMoney, orderNum, orderId,prescriptionId);
        }else{
            showPayDialog(needDispatch, String.valueOf(totalMoney), totalMoney, orderNum);
        }

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


    /**
     * 展示支付弹框
     */
    private void showPayTypeDialog(String titleFee, double totalFee, String orderNum,String orderId,String prescriptionId) {
        isSendDrugsToHome = false;
        WaiPayType[0] = 2;
        payWaiType = Type.ALIPAY;
        mPharmacyBean = null;
        mPharmacyBeans = null;
        mPharmacyName = null;
        mPharmacyAddress = null;
        mWaiYanAddressId = 0;
        FixHeightBottomSheetDialog bottomWaiYanSheetDialog = new FixHeightBottomSheetDialog(getActivity());
        bottomWaiYanSheetDialog.setCancelable(false);
        final boolean[] isAgree = {false};

        mPprescriptionId = prescriptionId;

        bottomWaiYanSheetDialog.setCanceledOnTouchOutside(false);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.pay_outside_dialog_layout, null, false);
        bottomWaiYanSheetDialog.setContentView(view);
        mOrderPriceTv = view.findViewById(R.id.order_price_tv);
        mOrderPriceTv.setText("¥" + titleFee + "起");
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
                GotoActivityUtil.gotoChoosePharmacyActivity(getActivity(),prescriptionId);
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

                    getController().updatePrescriptionOrder(WaiPayType[0],isSendDrugsToHome,true,prescriptionId,orderNum,pharmacyBean,mLocationInfo);

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
                if(CommUtil.isEmpty(mPharmacyName) && CommUtil.isEmpty(mPharmacyAddress)){
                    ToastUtils.showShort("请选择药店");
                }else{
                    PharmacyBean pharmacyBean = mPharmacyBean;

                    getController().updatePrescriptionOrder(WaiPayType[0],isSendDrugsToHome,false,prescriptionId,orderNum,pharmacyBean,mLocationInfo);
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

                    if(null != pharmacyBean){
                        getController().updatePrescriptionOrder(WaiPayType[0],isSendDrugsToHome,true,prescriptionId,orderNum,pharmacyBean,mLocationInfo);
                    }


                    Logger.e("map="+map);
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
                    if(null != mPharmacyBean ){
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
                    if(null != mPharmacyBeans && mPharmacyBeans.size() > 0){
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
        if(!CommUtil.isEmpty(data) && null != data.get(0)){
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
            String address = mLocationInfo.getProvinceName() + mLocationInfo.getCityName() + mLocationInfo.getAreaName() + mLocationInfo.getAddress();
            mPayAddress.setText(address);
            mPayAddress.setTextColor(getResources().getColor(R.color.pay_unselected));
            mAddressId = mLocationInfo.getId();
            getController().getDistributionFee(mAddressId);
        }else if (event.getType() == EventType.WAI_PAY_SELECT_ADDRESS) {
            mLocationInfo = (LocationInfo) event.getData();
            String address = mLocationInfo.getProvinceName() + mLocationInfo.getCityName() + mLocationInfo.getAreaName() + mLocationInfo.getAddress();
            mPayAddress.setText(address);
            mWaiYanAddressId = mLocationInfo.getId();
            Logger.e("地址=" + address);
            getController().getPrescriptionDetailDrugs(address,mPprescriptionId);
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
                if(null != mHosptalCost){
                    mHosptalCost.setText(medicalTv);
                }
            }
        }
    }


    public void refreshDeliveryCostView(List<PharmacyBean> data) {
        if(!CommUtil.isEmpty(data) && null != data.get(0)){
            mDeliveryCostTv.setText("￥" + String.valueOf(data.get(0).getDeliveryCost()) + "元");
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
        mRefreshLayout.autoRefresh();
    }

    @Override
    public void getDiagnosesOrderListSuccess(List<DiagnosesOrderBean> orderBeanArrayList, TypeEnum typeEnum) {
        mRefreshLayout.finishLoadMore();
        mRefreshLayout.finishRefresh();
        pageLoadingSuccess();

        if (typeEnum == TypeEnum.REFRESH) {
            diagnosesOrderAdapter.setNewData(orderBeanArrayList);
        }else{
            diagnosesOrderAdapter.addData(orderBeanArrayList);
        }
        if (orderBeanArrayList != null && orderBeanArrayList.size() != 0)
            getController().currentPagePlus();
    }

    @Override
    public void getDiagnosesOrderListFailed(String errMsg) {
        mRefreshLayout.finishLoadMore();
        mRefreshLayout.finishRefresh();
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

    @Override
    public void setPharmacyBeans(List<PharmacyBean> data){
        mPharmacyBeans = data;
    }
}
