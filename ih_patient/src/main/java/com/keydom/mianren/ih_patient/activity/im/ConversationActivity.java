package com.keydom.mianren.ih_patient.activity.im;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
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
import com.blankj.utilcode.util.ToastUtils;
import com.keydom.ih_common.activity.DiagnoseOrderDetailActivity;
import com.keydom.ih_common.activity.HandleProposeAcitivity;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.bean.InquiryStatus;
import com.keydom.ih_common.bean.PageBean;
import com.keydom.ih_common.bean.TriageBean;
import com.keydom.ih_common.constant.Const;
import com.keydom.ih_common.im.ImClient;
import com.keydom.ih_common.im.config.ImConstants;
import com.keydom.ih_common.im.listener.IConversationBehaviorListener;
import com.keydom.ih_common.im.model.custom.ConsultationResultAttachment;
import com.keydom.ih_common.im.model.custom.DisposalAdviceAttachment;
import com.keydom.ih_common.im.model.custom.EndInquiryAttachment;
import com.keydom.ih_common.im.model.custom.ExaminationAttachment;
import com.keydom.ih_common.im.model.custom.GetDrugsAttachment;
import com.keydom.ih_common.im.model.custom.InquiryAttachment;
import com.keydom.ih_common.im.model.custom.InspectionAttachment;
import com.keydom.ih_common.im.model.custom.ReceiveDrugsAttachment;
import com.keydom.ih_common.im.model.custom.ReferralApplyAttachment;
import com.keydom.ih_common.im.model.custom.ReferralDoctorAttachment;
import com.keydom.ih_common.im.model.custom.TriageOrderAttachment;
import com.keydom.ih_common.im.model.custom.UserFollowUpAttachment;
import com.keydom.ih_common.im.model.event.EndInquiryEvent;
import com.keydom.ih_common.im.model.event.PrescriptionEvent;
import com.keydom.ih_common.im.model.event.ReferralApplyEvent;
import com.keydom.ih_common.im.model.event.StartInquiryEvent;
import com.keydom.ih_common.im.widget.ImMessageView;
import com.keydom.ih_common.im.widget.plugin.VideoPlugin;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.CalculateTimeUtils;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.AgreementActivity;
import com.keydom.mianren.ih_patient.activity.apply_for_order_detail.CheckOrderDetailActivity;
import com.keydom.mianren.ih_patient.activity.apply_for_order_detail.TransferTreatmentOrderDetailActivity;
import com.keydom.mianren.ih_patient.activity.common_document.CommonDocumentActivity;
import com.keydom.mianren.ih_patient.activity.location_manage.LocationManageActivity;
import com.keydom.mianren.ih_patient.activity.logistic.FixHeightBottomSheetDialog;
import com.keydom.mianren.ih_patient.activity.my_doctor_or_nurse.DoctorOrNurseDetailActivity;
import com.keydom.mianren.ih_patient.activity.online_diagnoses_order.DianoseCaseDetailActivity;
import com.keydom.mianren.ih_patient.activity.order_doctor_register.DoctorIndexActivity;
import com.keydom.mianren.ih_patient.activity.order_evaluate.OrderEvaluateActivity;
import com.keydom.mianren.ih_patient.activity.order_triage.TriageOrderDetailActivity;
import com.keydom.mianren.ih_patient.activity.prescription.PrescriptionGetDetailActivity;
import com.keydom.mianren.ih_patient.activity.prescription_check.PrescriptionDetailActivity;
import com.keydom.mianren.ih_patient.activity.user_info_operate.UserInfoOperateActivity;
import com.keydom.mianren.ih_patient.bean.DiagnosesOrderBean;
import com.keydom.mianren.ih_patient.bean.Event;
import com.keydom.mianren.ih_patient.bean.InquiryBean;
import com.keydom.mianren.ih_patient.bean.LocationInfo;
import com.keydom.mianren.ih_patient.bean.PrescriptionDetailBean;
import com.keydom.mianren.ih_patient.bean.PrescriptionDrugBean;
import com.keydom.mianren.ih_patient.bean.entity.pharmacy.PharmacyBean;
import com.keydom.mianren.ih_patient.bean.entity.pharmacy.PrescriptionItemEntity;
import com.keydom.mianren.ih_patient.callback.GeneralCallback;
import com.keydom.mianren.ih_patient.callback.MessageSingleClick;
import com.keydom.mianren.ih_patient.constant.EventType;
import com.keydom.mianren.ih_patient.constant.Global;
import com.keydom.mianren.ih_patient.constant.Type;
import com.keydom.mianren.ih_patient.net.LocationService;
import com.keydom.mianren.ih_patient.net.PrescriptionService;
import com.keydom.mianren.ih_patient.utils.CommUtil;
import com.keydom.mianren.ih_patient.utils.DataCacheUtil;
import com.keydom.mianren.ih_patient.utils.DateUtils;
import com.keydom.mianren.ih_patient.utils.GotoActivityUtil;
import com.keydom.mianren.ih_patient.utils.SelectDialogUtils;
import com.keydom.mianren.ih_patient.view.im_plugin.VoiceInputPlugin;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.constant.MsgDirectionEnum;
import com.netease.nimlib.sdk.msg.constant.MsgTypeEnum;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;
import org.jsoup.helper.StringUtil;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * 问诊界面
 */
public class ConversationActivity extends BaseControllerActivity<ConversationController> implements View.OnClickListener, ConversationView {
    /**
     * 确认结束问诊
     */
    private static final int END_TYPE_CONFIRM = 1;
    /**
     * 取消结束问诊
     */
    private static final int END_TYPE_CANCEL = -1;

    private ImageView mBackImage;
    private TextView mTitle;
    private TextView mRightText;
    private TextView mRightText2;

    private ImageView mInquiryTypeImage;
    private TextView mInquiryTypeTv;
    private TextView mQuestionRemainingTimeTv;
    private LinearLayout mEndLl;
    private TextView mEndTips;
    /**
     * 问诊费用支付（预付费）
     */
    private TextView payConsultationFeeHint;
    private TextView mCancel;
    private TextView mDisagree;
    private TextView mConfirm;
    private int myDoctor;
    private ImMessageView mMessageView;
    private double mPSfee;
    private double mPsTotal;
    private boolean isWaitingForComment = false;

    /**
     * 问诊状态
     */
    private int inquiryStatus = 2;

    /**
     * -1 拒绝 1同意
     */
    private int referralStatus = 0;
    /**
     * -1 拒绝 1同意
     */
    private int chageDoctor = 0;
    /**
     * 问诊剩余时长
     */
    //    private String inquiryRemainingTime = "10小时59分后问诊结束";

    /**
     * 患者管理进入的聊天
     */
    private boolean chatting;


    private String sessionId;
    private Integer mHour;
    private Integer mMin;
    private Disposable timeDisposable;
    private int endType;
    private InquiryBean orderBean;


    /**
     * 当前位置信息
     */
    LocationInfo mLocationInfo;

    /**
     * 转诊所需支付金额
     */
    private String referralAmount = "";
    /**
     * 转诊id
     */
    private String referralId = "";
    /**
     * 最终需要判断的是否已支付订单id
     */
    private long isPayOrderId;
    /**
     * 需要支付的订单金额
     */
    private String orderFee = "";

    /**
     * 是否是处方订单
     */
    private boolean isPrescription = false;

    /**
     * 处方单配送费
     */
    private String deliveryAmount;

    /**
     * 处方单单号
     */
    private String orderNum;

    /**
     * 处方单单号
     */
    private String mPprescriptionId;

    /**
     * 处方类型
     */
    private String prescriptionType;
    /**
     * 外延地址ID
     */
    private long mWaiYanAddressId;
    /**
     * 药店名字和地址
     */
    String mPharmacyName = null;
    String mPharmacyAddress = null;
    /**
     * 药店选中，详情bean
     */
    PharmacyBean mPharmacyBean = null;

    /**
     * 药店选中，配送费
     */
    List<PharmacyBean> mPharmacyBeans = null;
    /**
     * 其他界面带入的与IM无关参数，都在这里面
     */
    private Bundle bundle;
    /**
     * 医生类型
     */
    private int doctorType;
    private RadioButton mHosptalCost;
    private TextView mTypeTotalPayTv;

    /**
     * 新加
     */
    private TextView mTypePayAddress;
    private TextView mPyName;
    private double mTotalFee;

    //父组件 药店自取/配送到家
    private LinearLayout mLinAddress;
    private LinearLayout mLinShop;
    private RelativeLayout mReZxingTitle;

    //付款按钮
    private TextView mWaiYanTotalPayTv;
    private TextView mTotalPayTv;
    private LinearLayout mLinPay;
    private TextView mTvShopPay;
    private TextView mTvGoPay;

    //支付宝，微信，银联
    private ImageView aliPay;
    private ImageView wechatPay;
    private ImageView unionPay;

    private TextView mTvAliPay;
    private TextView mTvWechatPay;
    private TextView mTvUnionPay;
    private TextView pay_agreement_tv;
    private TextView mOrderPriceTv;

    //配送费
    private TextView mDeliveryCostTv;

    //外院选择按钮
    private RadioGroup mRadioGroup;
    private RadioButton mRadioSelf;
    private RadioButton mRadioHome;

    private VideoPlugin videoPlugin;

    private boolean team;
    private long orderId;

    /**
     * 成员邀请
     */
    public static final int SELECT_MEMBER = 1000;

    int[] WaiPayType = {2};
    String payWaiType = Type.ALIPAY;
    List<PrescriptionItemEntity> drugs = new ArrayList<>();

    private VoiceInputPlugin mVoiceInputPlugin;

    @Override
    protected void onResume() {
        super.onResume();
        getController().getLocationList();
        getWaiYanLocationList();
        if (team) {
            NIMClient.getService(MsgService.class).setChattingAccount(sessionId,
                    SessionTypeEnum.Team);
        } else {
            NIMClient.getService(MsgService.class).setChattingAccount(sessionId,
                    SessionTypeEnum.P2P);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        NIMClient.getService(MsgService.class).setChattingAccount(MsgService.MSG_CHATTING_ACCOUNT_NONE, SessionTypeEnum.None);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timeDisposable != null && !timeDisposable.isDisposed()) {
            timeDisposable.dispose();
        }
        EventBus.getDefault().unregister(this);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_conversation;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);

        drugs = DataCacheUtil.getInstance().getPrescriptionItemEntity();

        myDoctor = getIntent().getIntExtra(Const.TYPE, -1);
        mBackImage = findViewById(R.id.left_image);
        mTitle = findViewById(R.id.title_center);
        mRightText = findViewById(R.id.right_text);
        mRightText2 = findViewById(R.id.right_text2);
        mInquiryTypeImage = findViewById(R.id.inquiry_type_image);
        mInquiryTypeTv = findViewById(R.id.inquiry_type_tv);
        mQuestionRemainingTimeTv = findViewById(R.id.question_remaining_time_tv);
        mEndLl = findViewById(R.id.end_ll);
        mMessageView = findViewById(R.id.im_view);
        mEndTips = findViewById(R.id.end_tips);
        payConsultationFeeHint = findViewById(R.id.conversation_pay_hint_tv);
        mCancel = findViewById(R.id.cancel);
        mDisagree = findViewById(R.id.disagree);
        mConfirm = findViewById(R.id.confirm);

        getLifecycle().addObserver(mMessageView);

        mCancel.setOnClickListener(this);
        mDisagree.setOnClickListener(this);
        mConfirm.setOnClickListener(this);

        initView();
        initListener();
        getController().getInquiryStatus();
    }

    private void initListener() {
        mMessageView.setOnConversationBehaviorListener(new IConversationBehaviorListener() {
            @MessageSingleClick(1000)
            @Override
            public boolean onUserPortraitClick(Context context, IMMessage message) {

                if (message.getDirect() == MsgDirectionEnum.Out) {
                    UserInfoOperateActivity.start(getContext(), UserInfoOperateActivity.READTYPE);
                } else {//接受
                    com.orhanobut.logger.Logger.e("进入跳转头像方法");
                    Intent intent = new Intent(getContext(), DoctorOrNurseDetailActivity.class);
                    intent.putExtra("type", 0);
                    intent.putExtra("doctorCode", message.getSessionId());
                    startActivity(intent);
                }
                return false;
            }

            @Override
            public boolean onMessageClick(Context context, View view, IMMessage message) {
                if (message.getMsgType() == MsgTypeEnum.custom) {
                    //问诊单
                    if (message.getAttachment() instanceof InquiryAttachment) {
                        DiagnoseOrderDetailActivity.start(context,
                                ((InquiryAttachment) message.getAttachment()).getId());
                    }
                    //分诊单
                    else if (message.getAttachment() instanceof TriageOrderAttachment) {
                        TriageOrderAttachment attachment =
                                (TriageOrderAttachment) message.getAttachment();
                        TriageBean bean = new TriageBean();
                        bean.setOrderId(attachment.getId());
                        bean.setPatientName(attachment.getPatientName());
                        bean.setDoctor(attachment.getDoctorName());
                        bean.setPatientSex(attachment.getSex());
                        bean.setPatientAge(attachment.getAge());
                        bean.setTriageExplain(attachment.getContent());
                        bean.setGroupTid(attachment.getGroupTid());
                        bean.setDept(attachment.getDept());
                        bean.setTriageTime(DateUtils.getDate(attachment.getApplyTime()));
                        bean.setDiseaseData(StringUtil.join(attachment.getImages(), ","));
                        TriageOrderDetailActivity.startWithAction(context, bean,
                                null, true);
                    }
                    //检查单
                    else if (message.getAttachment() instanceof ExaminationAttachment) {
                        CheckOrderDetailActivity.startInspactOrder(context,
                                ((ExaminationAttachment) message.getAttachment()).getId(),
                                orderBean);
                    }
                    //检验单
                    else if (message.getAttachment() instanceof InspectionAttachment) {
                        CheckOrderDetailActivity.startCheckOrder(context,
                                ((InspectionAttachment) message.getAttachment()).getId(),
                                orderBean);
                    }
                    //转诊单
                    else if (message.getAttachment() instanceof ReferralApplyAttachment) {
                        TransferTreatmentOrderDetailActivity.startCommon(context,
                                ((ReferralApplyAttachment) message.getAttachment()).getId());
                    }
                    //换诊
                    else if (message.getAttachment() instanceof ReferralDoctorAttachment) {

                    }
                    //病历
                    else if (message.getAttachment() instanceof ConsultationResultAttachment) {
                        DianoseCaseDetailActivity.start(getContext(),
                                ((ConsultationResultAttachment) message.getAttachment()).getId());
                        //                        Intent i = new Intent(getContext(),
                        //                        PrescriptionDetailActivity.class);
                        //                        i.putExtra(PrescriptionDetailActivity.ID, (
                        //                        (ConsultationResultAttachment) message
                        //                        .getAttachment()).getId());
                        //                        ActivityUtils.startActivity(i);
                    }
                    //处置建议
                    else if (message.getAttachment() instanceof DisposalAdviceAttachment) {
                        HandleProposeAcitivity.start(getContext(),
                                ((DisposalAdviceAttachment) message.getAttachment()).getContent());
                    }
                    //取药
                    else if (message.getAttachment() instanceof GetDrugsAttachment) {
                        GetDrugsAttachment getDrugsAttachment =
                                (GetDrugsAttachment) message.getAttachment();
                        GotoActivityUtil.gotoPrescriptionGetDetailActivity(ConversationActivity.this, getDrugsAttachment.getId(), PrescriptionGetDetailActivity.TAKE_MEDICINE);
                    }
                    //收药
                    else if (message.getAttachment() instanceof ReceiveDrugsAttachment) {
                        ReceiveDrugsAttachment receiveDrugsAttachment =
                                (ReceiveDrugsAttachment) message.getAttachment();
                        GotoActivityUtil.gotoPrescriptionGetDetailActivity(ConversationActivity.this, receiveDrugsAttachment.getId(), PrescriptionGetDetailActivity.RECEIVE_MEDICINE);
                    }
                    //随访表
                    else if (message.getAttachment() instanceof UserFollowUpAttachment) {
                        UserFollowUpAttachment userFollowUpAttachment =
                                (UserFollowUpAttachment) message.getAttachment();
                        CommonDocumentActivity.start(getContext(),
                                userFollowUpAttachment.getFileName(),
                                userFollowUpAttachment.getUrl());
                    }
                }
                return false;
            }

            @Override
            public boolean onPayClick(Context context, View view, @Nullable IMMessage message) {
                if (message.getMsgType() == MsgTypeEnum.custom) {
                    if (message.getAttachment() instanceof ExaminationAttachment) {
                        ExaminationAttachment attachment =
                                (ExaminationAttachment) message.getAttachment();
                        isPayOrderId = attachment.getId();
                        orderFee = attachment.getAmount();
                        isPrescription = false;
                    } else if (message.getAttachment() instanceof InspectionAttachment) {
                        InspectionAttachment attachment =
                                (InspectionAttachment) message.getAttachment();
                        isPayOrderId = attachment.getId();
                        orderFee = attachment.getAmount();
                        isPrescription = false;
                    } else if (message.getAttachment() instanceof ConsultationResultAttachment) {
                        ConsultationResultAttachment attachment =
                                (ConsultationResultAttachment) message.getAttachment();
                        isPayOrderId = Long.parseLong(attachment.getId());
                        mPprescriptionId = attachment.getId();
                        orderFee = attachment.getAmount();
                        deliveryAmount = attachment.getDeliveryAmount();
                        orderNum = attachment.getOrderNum();
                        isPrescription = true;
                        if (TextUtils.isEmpty(attachment.getPrescriptionType())) {
                            prescriptionType = "0";
                        }
                        prescriptionType = attachment.getPrescriptionType();
                        Logger.e("prescriptionType=" + attachment.getPrescriptionType());
                    }
                    getController().isPay();
                }
                return false;
            }

            /**
             * 处方
             */
            @Override
            public boolean onPrescriptionClick(Context context, @Nullable IMMessage message) {
                if (message.getAttachment() instanceof ConsultationResultAttachment) {
                    Intent i = new Intent(getContext(), PrescriptionDetailActivity.class);
                    i.putExtra(PrescriptionDetailActivity.ID,
                            ((ConsultationResultAttachment) message.getAttachment()).getId());
                    ActivityUtils.startActivity(i);
                }
                return false;
            }
        });

    }

    private void initView() {
        Uri data = getIntent().getData();
        bundle = getIntent().getExtras();
        if (bundle != null) {
            team = bundle.getBoolean(ImConstants.TEAM);
            orderId = bundle.getLong("orderId");
        }
        if (data != null) {
            String title;
            sessionId = data.getQueryParameter(ImConstants.CALL_SESSION_ID);
            if (team) {
                mMessageView.setMessageInfo(sessionId, SessionTypeEnum.Team);
                if (ImClient.getTeamProvider().getTeamById(sessionId) != null) {
                    title = ImClient.getTeamProvider().getTeamById(sessionId).getName();
                } else {
                    title = "临时会诊";
                }
            } else {
                mMessageView.setMessageInfo(sessionId, SessionTypeEnum.P2P);
                title = "问诊详情";
            }
            mTitle.setText(title);
        }

        mBackImage.setOnClickListener(this);
        mRightText.setOnClickListener(this);
        mRightText2.setOnClickListener(this);

        mVoiceInputPlugin = new VoiceInputPlugin(this);
        mMessageView.addPlugin(mVoiceInputPlugin);
    }


    private void initStatus() {
        if (chatting) {
            setRightImgVisibility(false);
            mMessageView.showExtension();
            mEndTips.setVisibility(View.GONE);
            mEndLl.setVisibility(View.GONE);
            findViewById(R.id.inquiry_header).setVisibility(View.GONE);
            mRightText.setVisibility(View.VISIBLE);
            if (bundle != null) {
                doctorType = bundle.getInt("jobType");
                if (doctorType == DoctorOrNurseDetailActivity.DOCTOR) {
                    mRightText2.setVisibility(View.VISIBLE);
                    mRightText2.setText("在线问诊");
                    mRightText.setText("挂号");
                } else {
                    mRightText.setText("在线咨询");
                }
            }
        } else {
            if (orderBean != null && orderBean.getInquisitionType() == 1) {
                if (!isWaitingForComment) {
                    videoPlugin = new VideoPlugin(this);
                    videoPlugin.setTeam(team);
                    videoPlugin.setTeamId(sessionId);
                    mMessageView.addPlugin(videoPlugin);
                }
            }

            //            mRightText.setVisibility(View.VISIBLE);
            //            mRightText.setText("结束诊断");
        }
        if (InquiryStatus.AUDIT_FAILE == inquiryStatus) {
            mRightText.setVisibility(View.GONE);
            mMessageView.hideExtension();
        }
        //待接诊
        if (InquiryStatus.INQUIRY_WAIT == inquiryStatus) {
            mRightText.setVisibility(View.GONE);
            findViewById(R.id.inquiry_header).setVisibility(View.VISIBLE);
            mEndTips.setVisibility(View.GONE);
            mEndLl.setVisibility(View.VISIBLE);
            mConfirm.setVisibility(View.GONE);
            mCancel.setVisibility(View.VISIBLE);
            mCancel.setText("退诊");
            mQuestionRemainingTimeTv.setVisibility(View.GONE);
            mInquiryTypeTv.setText("待接诊");
            mMessageView.hideExtension();
        }
        //问诊中
        if (InquiryStatus.INQUIRY_ING == inquiryStatus) {
            inquiryIng();
            mEndTips.setVisibility(View.GONE);
            mEndLl.setVisibility(View.GONE);
            mRightText.setVisibility(View.VISIBLE);
        }
        //医生发起结束问诊
        else if (InquiryStatus.INQUIRY_APPLY_END == inquiryStatus) {
            inquiryApply();
        }
        //等待医生开具处方或者建议
        else if (InquiryStatus.INQUIRY_PRESCRIBE == inquiryStatus) {
            inquiryWaitPrescribe();
        }
        //问诊已完成未评价
        else if (InquiryStatus.INQUIRY_NOT_EVALUATED == inquiryStatus) {
            inquiryEnd();
        }
        //问诊已完成，已开具处方，未支付
        else if (InquiryStatus.INQUIRY_UNPAID == inquiryStatus) {
            inquiryUnpaid();
        }
        //医生发起转诊后布局
        else if (InquiryStatus.INQUIRY_WAIT_REFERRAL == inquiryStatus) {
            mInquiryTypeTv.setText("问诊中");
            mInquiryTypeTv.setVisibility(View.VISIBLE);
            mInquiryTypeTv.setTextColor(ContextCompat.getColor(this, R.color.im_status_ing));
            if (orderBean.getReferralState() == 0) {
                referralView(orderBean.getReferralFee() + "");
            }
        }
        //换诊
        else if (InquiryStatus.INQUIRY_REFERRAL_DOCTOR == inquiryStatus) {
            findViewById(R.id.inquiry_header).setVisibility(View.VISIBLE);
            mInquiryTypeTv.setText("待接诊");
            mInquiryTypeTv.setVisibility(View.VISIBLE);
            mQuestionRemainingTimeTv.setVisibility(View.GONE);
            mEndTips.setVisibility(View.GONE);
            mEndLl.setVisibility(View.VISIBLE);
            mCancel.setVisibility(View.VISIBLE);
            mConfirm.setVisibility(View.VISIBLE);
            mDisagree.setVisibility(View.VISIBLE);
            mRightText.setVisibility(View.GONE);
            mMessageView.hideExtension();
        }
        if (myDoctor == ImClient.MY_DOCTOR_CONVERSATION) {//显示已经完成服务
            mInquiryTypeTv.setText("已完成问诊");
            mInquiryTypeTv.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 问诊已完成，已开具处方，未支付布局
     */
    private void inquiryUnpaid() {
        mRightText.setVisibility(View.GONE);
        findViewById(R.id.inquiry_header).setVisibility(View.VISIBLE);
        mInquiryTypeImage.setImageResource(R.drawable.inquiry_type_image_completed);
        mInquiryTypeTv.setText("已完成");
        mInquiryTypeTv.setTextColor(ContextCompat.getColor(this, R.color.im_status_completed));
        mQuestionRemainingTimeTv.setVisibility(View.VISIBLE);
        /*
         * 订单类型<br>
         * 0问诊单  1咨询单
         */
        if (orderBean.getType() == 0) {
            mQuestionRemainingTimeTv.setText("医生已完成问诊，并开具处方，请及时\n支付，并按指引到医院取药");
        } else {
            mQuestionRemainingTimeTv.setText("医生已完成问诊");
        }
        mMessageView.hideExtension();
    }

    /**
     * 等待医生开具处方
     */
    private void inquiryWaitPrescribe() {
        mRightText.setVisibility(View.GONE);
        findViewById(R.id.inquiry_header).setVisibility(View.VISIBLE);
        mInquiryTypeImage.setImageResource(R.drawable.inquiry_type_image_completed);
        mInquiryTypeTv.setText("已完成");
        mInquiryTypeTv.setTextColor(ContextCompat.getColor(this, R.color.im_status_completed));
        mQuestionRemainingTimeTv.setVisibility(View.VISIBLE);
        /*
         * 订单类型<br>
         * 0问诊单  1咨询单
         */
        if (orderBean.getType() == 0) {
            mQuestionRemainingTimeTv.setText("问诊时长：" + CalculateTimeUtils.getTimeDuration(orderBean.getBeginTime(), orderBean.getEndTime()));
        } else {
            mQuestionRemainingTimeTv.setText("问诊时长：" + CalculateTimeUtils.getTimeDuration(orderBean.getBeginTime(), orderBean.getEndTime()));
        }
        mEndTips.setVisibility(View.GONE);
        mEndLl.setVisibility(View.GONE);
        mMessageView.hideExtension();
    }

    /**
     * 问诊结束布局
     */
    private void inquiryEnd() {
        mRightText.setVisibility(View.GONE);
        findViewById(R.id.inquiry_header).setVisibility(View.VISIBLE);
        mInquiryTypeImage.setImageResource(R.drawable.inquiry_type_image_completed);
        mInquiryTypeTv.setText("已完成");
        mInquiryTypeTv.setTextColor(ContextCompat.getColor(this, R.color.im_status_completed));
        mEndTips.setVisibility(View.GONE);
        mEndLl.setVisibility(View.VISIBLE);
        mConfirm.setVisibility(View.GONE);
        mCancel.setText("评价");
        mMessageView.hideExtension();
    }

    /**
     * 问诊中布局设置
     */
    private void inquiryIng() {
        findViewById(R.id.inquiry_header).setVisibility(View.VISIBLE);
        mEndTips.setVisibility(View.GONE);
        mEndLl.setVisibility(View.GONE);
        mRightText.setVisibility(View.VISIBLE);
        mInquiryTypeImage.setImageResource(R.drawable.inquiry_type_image_ing);
        mInquiryTypeTv.setText("问诊中");
        mInquiryTypeTv.setTextColor(ContextCompat.getColor(this, R.color.im_status_ing));
        mQuestionRemainingTimeTv.setVisibility(View.VISIBLE);
        mMessageView.showExtension();
    }

    /**
     * 医生申请结束问诊布局
     */
    private void inquiryApply() {
        findViewById(R.id.inquiry_header).setVisibility(View.VISIBLE);
        mRightText.setVisibility(View.GONE);
        mEndTips.setVisibility(View.VISIBLE);
        mEndLl.setVisibility(View.VISIBLE);
        mConfirm.setVisibility(View.VISIBLE);
        mCancel.setVisibility(View.VISIBLE);
        mCancel.setText("取消");
        mConfirm.setText("确定");
        mInquiryTypeTv.setText("问诊中");
        mInquiryTypeTv.setTextColor(ContextCompat.getColor(this, R.color.im_status_ing));
        mQuestionRemainingTimeTv.setVisibility(View.VISIBLE);
        mMessageView.hideExtension();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:
                if (inquiryStatus == InquiryStatus.INQUIRY_APPLY_END) {
                    endType = END_TYPE_CANCEL;
                    getController().patientEndInquisition();

                } else if (inquiryStatus == InquiryStatus.INQUIRY_NOT_EVALUATED) {
                    DiagnosesOrderBean bean = new DiagnosesOrderBean();
                    bean.setId(getId());
                    OrderEvaluateActivity.start(getContext(), "患者评价",
                            Type.DIAGNOSES_ORDER_EVALUATE, bean);
                } else if (inquiryStatus == InquiryStatus.INQUIRY_WAIT_REFERRAL) {
                    referralStatus = -1;
                    getController().userOperateReferral();
                } else if (inquiryStatus == InquiryStatus.INQUIRY_WAIT || inquiryStatus == InquiryStatus.INQUIRY_REFERRAL_DOCTOR) {
                    new GeneralDialog(getContext(), "问诊费用将在5个工作日内按支付路径退回到您的付款账号中,确认要退诊？",
                            new GeneralDialog.OnCloseListener() {
                                @Override
                                public void onCommit() {
                                    getController().returnedInquisition();
                                }
                            }).setTitle("提示").setPositiveButton("确认").show();

                }
                break;
            case R.id.confirm:
                if (inquiryStatus == InquiryStatus.INQUIRY_WAIT_REFERRAL) {
                    referralStatus = 1;
                    getController().userOperateReferral();
                } else if (inquiryStatus == InquiryStatus.INQUIRY_REFERRAL_DOCTOR) {
                    chageDoctor = 1;
                    getController().confirmChangeDoctor();
                } else {

                    endType = END_TYPE_CONFIRM;
                    getController().patientEndInquisition();

                }
                break;
            case R.id.disagree:
                chageDoctor = -1;
                getController().confirmChangeDoctor();
                break;
            case R.id.left_image:
                finish();
                break;
            case R.id.right_text:
                if (chatting) {
                    if (doctorType == DoctorOrNurseDetailActivity.DOCTOR) {
                        DoctorIndexActivity.start(getContext(), sessionId);
                    } else {
                        DoctorOrNurseDetailActivity.startNursePage(getContext(), sessionId);
                    }
                }
                //结束问诊
                else {
                    new GeneralDialog(ConversationActivity.this, "是否确定结束问诊?",
                            new GeneralDialog.OnCloseListener() {
                                @Override
                                public void onCommit() {
                                    getController().patientFinishInquisition();
                                    mMessageView.hideExtension();
                                }
                            }).show();

                }
                break;
            case R.id.right_text2:
                DoctorOrNurseDetailActivity.startDoctorPage(getContext(), 0, sessionId);
                break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mMessageView.onActivityPluginResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_MEMBER) {
                startTeamAVChat(data.getStringArrayListExtra("accounts"));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void startTeamAVChat(ArrayList<String> accounts) {
        ImClient.createRoom(this, sessionId, accounts);
    }

    /**
     * 处理接收到的结束问诊消息
     *
     * @param event 事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEndInquiryEvent(EndInquiryEvent event) {
        if ((event.getMessage().getAttachment() instanceof EndInquiryAttachment)) {
            EndInquiryAttachment attachment =
                    (EndInquiryAttachment) event.getMessage().getAttachment();
            if (attachment.getEndType() == EndInquiryAttachment.DOCTOR_APPLY_END) {
                inquiryStatus = InquiryStatus.INQUIRY_COMPLETE;
                if (team) {
                    mMessageView.addData(ImClient.createLocalTipMessage(sessionId,
                            SessionTypeEnum.Team, "此次问诊已结束"));
                } else {
                    mMessageView.addData(ImClient.createLocalTipMessage(sessionId,
                            SessionTypeEnum.P2P, "此次问诊已结束"));
                }
                getController().getInquiryStatus();
            }
        }
    }

    /**
     * 处理接收到的处方或者处置建议消息
     *
     * @param event 事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPrescriptionEvent(PrescriptionEvent event) {
        getController().getInquiryStatus();
    }

    /**
     * 处理接收到的转诊消息
     *
     * @param event 事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReferralApplyEvent(ReferralApplyEvent event) {
        if (team) {
            mMessageView.addData(ImClient.createLocalTipMessage(sessionId,
                    SessionTypeEnum.Team, "医生已填写转诊单，请及时确认并支付问诊费用"));
        } else {
            mMessageView.addData(ImClient.createLocalTipMessage(sessionId, SessionTypeEnum.P2P,
                    "医生已填写转诊单，请及时确认并支付问诊费用"));
        }
        inquiryStatus = InquiryStatus.INQUIRY_WAIT_REFERRAL;
        referralAmount = ((ReferralApplyAttachment) event.getMessage().getAttachment()).getAmount();
        referralId =
                String.valueOf(((ReferralApplyAttachment) event.getMessage().getAttachment()).getId());
        referralView(referralAmount);
    }

    /**
     * 开始问诊监听
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStartInquiryEvent(StartInquiryEvent event) {
        getController().getInquiryStatus();
    }

    /**
     * 接收到医生转诊申请布局
     */
    private void referralView(String amount) {
        mEndLl.setVisibility(View.VISIBLE);
        mEndTips.setVisibility(View.GONE);
        mRightText.setVisibility(View.GONE);
        findViewById(R.id.inquiry_header).setVisibility(View.VISIBLE);
        mQuestionRemainingTimeTv.setVisibility(View.VISIBLE);
        mQuestionRemainingTimeTv.setText("医生已填写转诊单，请及时确认");
        mCancel.setText("不同意");
        mConfirm.setText("同意并支付￥" + amount + "元");
        mMessageView.hideExtension();
    }

    @Override
    public String getUserId() {
        return String.valueOf(Global.getUserId());
    }

    @Override
    public long getId() {
        return orderId;
    }

    @Override
    public boolean isTeam() {
        return team;
    }

    @Override
    public String getDoctorCode() {
        return sessionId;
    }

    @Override
    public int getEndType() {
        return endType;
    }

    @Override
    public long getReferralId() {
        return TextUtils.isEmpty(referralId) ? orderBean.getReferralId() :
                Long.parseLong(referralId);
    }

    @Override
    public int getReferralState() {
        return referralStatus;
    }

    @Override
    public long getIsPayId() {
        return isPayOrderId;
    }

    @Override
    public void loadSuccess(InquiryBean data) {
        inquiryStatus = data == null ? InquiryStatus.INQUIRY_COMPLETE : data.getState();
        chatting = inquiryStatus == InquiryStatus.INQUIRY_COMPLETE;
        mMessageView.setChatting(chatting);
        if (data != null) {
            orderBean = data;
            orderId = orderBean.getId();
            calculateTime();
        } else {
            isWaitingForComment = true;
        }
        initStatus();
    }

    @Override
    public void handleApplyEndSuccess() {
        EndInquiryAttachment endInquiryAttachment = new EndInquiryAttachment();
        endInquiryAttachment.setId(orderBean.getId());
        endInquiryAttachment.setSponsorId(ImClient.getUserInfoProvider().getAccount());
        endInquiryAttachment.setReceiverId(sessionId);
        endInquiryAttachment.setEndType(endType == END_TYPE_CONFIRM ?
                EndInquiryAttachment.PATIENT_AGREE : EndInquiryAttachment.PATIENT_REFUSE);
        if (team) {
            mMessageView.addData(ImClient.createEndInquiryMessage(sessionId, SessionTypeEnum.Team,
                    "[结束问诊消息]", endInquiryAttachment));
        } else {
            mMessageView.addData(ImClient.createEndInquiryMessage(sessionId, SessionTypeEnum.P2P,
                    "[结束问诊消息]", endInquiryAttachment));
        }
        if (endType == END_TYPE_CONFIRM) {
            inquiryStatus = InquiryStatus.INQUIRY_PRESCRIBE;
            if (team) {
                mMessageView.addData(ImClient.createTipMessage(sessionId, SessionTypeEnum.Team,
                        "此次问诊已结束"));
            } else {
                mMessageView.addData(ImClient.createTipMessage(sessionId, SessionTypeEnum.P2P,
                        "此次问诊已结束"));
            }
            getController().getInquiryStatus();
            //            inquiryWaitPrescribe();
        } else {
            inquiryStatus = InquiryStatus.INQUIRY_ING;
            inquiryIng();
        }

    }

    /**
     * 计算时间
     */
    private void calculateTime() {
        if (inquiryStatus == InquiryStatus.INQUIRY_ING || inquiryStatus == InquiryStatus.INQUIRY_APPLY_END) {
            long duration = (long) (orderBean.getDuration() * 60 * 60 * 1000);
            long remainingTime =
                    CalculateTimeUtils.toLong(orderBean.getBeginTime()) + duration - System.currentTimeMillis();
            SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.CHINA);
            format.setTimeZone(TimeZone.getTimeZone("GMT"));
            int day = 0;
            long dayMillisecond = 24 * 60 * 60 * 1000;
            if (remainingTime < 0) {
                remainingTime = 0;
            } else if (remainingTime > dayMillisecond) {
                day = (int) (remainingTime / dayMillisecond);
                remainingTime = remainingTime % dayMillisecond;
            }
            String date = format.format(new Date(remainingTime));
            mHour = Integer.valueOf(date.split(":")[0]) + day * 24;
            mMin = Integer.valueOf(date.split(":")[1]);
            timeDisposable = Flowable.intervalRange(1, remainingTime / 1000 / 60, 0, 1,
                    TimeUnit.MINUTES)
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(time -> computeTime());
        }
    }

    /**
     * 倒计时计算
     */
    private void computeTime() {
        mMin--;
        if (mMin < 0) {
            mMin = 59;
            mHour--;
            if (mHour < 0) {
                // 倒计时结束
                if (!timeDisposable.isDisposed()) {
                    timeDisposable.dispose();
                }
            }
        }
        if (inquiryStatus == InquiryStatus.INQUIRY_ING || inquiryStatus == InquiryStatus.INQUIRY_APPLY_END) {
            mQuestionRemainingTimeTv.setText(mHour + "小时" + mMin + "分后问诊结束");
        }
    }


    @Override
    public void endSuccess() {
        inquiryStatus = InquiryStatus.INQUIRY_NOT_EVALUATED;
        EndInquiryAttachment endInquiryAttachment = new EndInquiryAttachment();
        endInquiryAttachment.setId(orderBean.getId());
        endInquiryAttachment.setSponsorId(ImClient.getUserInfoProvider().getAccount());
        endInquiryAttachment.setReceiverId(sessionId);
        endInquiryAttachment.setEndType(EndInquiryAttachment.PATIENT_END);
        if (team) {
            mMessageView.addData(ImClient.createEndInquiryMessage(sessionId, SessionTypeEnum.Team,
                    "[结束问诊消息]", endInquiryAttachment));
            mMessageView.addData(ImClient.createLocalTipMessage(sessionId, SessionTypeEnum.P2P,
                    "您已结束此次问诊"));
        } else {
            mMessageView.addData(ImClient.createEndInquiryMessage(sessionId, SessionTypeEnum.Team,
                    "[结束问诊消息]", endInquiryAttachment));
            mMessageView.addData(ImClient.createLocalTipMessage(sessionId, SessionTypeEnum.P2P,
                    "您已结束此次问诊"));
        }
        mMessageView.hideExtension();
        getController().getInquiryStatus();
    }

    @Override
    public void operateReferralSuccess(String id) {
        getController().getInquiryStatus();
        if (referralStatus == -1) {
            inquiryStatus = InquiryStatus.INQUIRY_ING;
            inquiryIng();
        } else {
            SelectDialogUtils.showPayDialog(getContext(),
                    TextUtils.isEmpty(referralAmount) ?
                            String.valueOf(orderBean.getReferralFee()) : referralAmount,
                    "", new GeneralCallback.SelectPayMentListener() {
                        @Override
                        public void getSelectPayMent(String type) {
                            Map<String, Object> map = new HashMap<>();
                            map.put("orderId", id);
                            if (type.equals(Type.WECHATPAY)) {
                                map.put("type", 1);
                                getController().inquiryPay(map, 1);

                            }
                            if (type.equals(Type.ALIPAY)) {
                                map.put("type", 2);
                                getController().inquiryPay(map, 2);

                            }
                        }
                    });
        }
    }

    @Override
    public void paySuccess() {
        getController().getInquiryStatus();
    }

    @Override
    public void payType(boolean isPay, boolean isWaiYan) {
        if (!isPay) {
            if (!isPrescription) {
                SelectDialogUtils.showPayDialog(getContext(), orderFee, "", type -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("orderId", isPayOrderId);
                    if (type.equals(Type.WECHATPAY)) {
                        map.put("type", 1);
                        getController().inquiryPay(map, 1);
                    }
                    if (type.equals(Type.ALIPAY)) {
                        map.put("type", 2);
                        getController().inquiryPay(map, 2);
                    }
                });
            } else {
                if (isWaiYan) {
                    showPayTypeDialog(orderFee, Double.parseDouble(orderFee), orderNum,
                            mPprescriptionId);
                } else {
                    if (deliveryAmount == null) {
                        deliveryAmount = "0.0";
                    }
                    showPayDialog(true, orderFee, Double.parseDouble(orderFee), orderNum);
                }
            }
        } else {
            new GeneralDialog(getContext(), "该订单已支付！", () -> {
            }).setTitle("提示").setPositiveButton("确认").show();
        }
    }

    @Override
    public void returnBackSuccess() {
        mMessageView.hideExtension();
        if (team) {
            mMessageView.addData(ImClient.createLocalTipMessage(sessionId, SessionTypeEnum.Team,
                    "您已退诊"));
        } else {
            mMessageView.addData(ImClient.createLocalTipMessage(sessionId, SessionTypeEnum.P2P,
                    "您已退诊"));
        }
        findViewById(R.id.inquiry_header).setVisibility(View.GONE);
        mEndTips.setVisibility(View.GONE);
        mEndLl.setVisibility(View.GONE);
        mRightText.setVisibility(View.GONE);
    }

    @Override
    public void returnBackFailed(String msg) {

    }

    @Override
    public int getChangeDoctor() {
        return chageDoctor;
    }

    @Override
    public void changeDoctorSuccess() {

        if (chageDoctor == 1) {
            //            inquiryStatus = InquiryStatus.INQUIRY_WAIT;
            //            mEndTips.setVisibility(View.GONE);
            //            mEndLl.setVisibility(View.VISIBLE);
            //            mConfirm.setVisibility(View.GONE);
            //            mCancel.setVisibility(View.VISIBLE);
            //            mCancel.setText("退诊");
            //            mQuestionRemainingTimeTv.setVisibility(View.GONE);
            //            mInquiryTypeTv.setText("待接诊");
            mEndLl.setVisibility(View.GONE);
            findViewById(R.id.inquiry_header).setVisibility(View.GONE);
            if (team) {
                mMessageView.addData(ImClient.createLocalTipMessage(sessionId, SessionTypeEnum.Team,
                        "您已同意换诊，请等待换诊医生接诊"));
            } else {
                mMessageView.addData(ImClient.createLocalTipMessage(sessionId, SessionTypeEnum.P2P,
                        "您已同意换诊，请等待换诊医生接诊"));
            }
            mMessageView.hideExtension();
        } else {
            mEndLl.setVisibility(View.GONE);
            mMessageView.hideExtension();
        }
    }

    /**
     * 配送费
     */
    private Double shippingFee;

    @Override
    public void getDistributionFee(String fee) {

        BigDecimal bd = new BigDecimal(fee);
        Double d = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        mPSfee = d;
        BigDecimal orderfee = new BigDecimal(orderFee);
        double total = d + orderfee.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        mPsTotal = total;
        if (mHosptalCost != null && mHosptalCost.isChecked()) {

            // mTotalPayTv.setText("去付款¥" + total + "元");
            SpannableStringBuilder medicalTv = new SpanUtils().append("医院配送").setFontSize(13,
                    true).setForegroundColor(getResources().getColor(R.color.pay_unselected))
                    .append("（配送费用").setFontSize(13, true).setForegroundColor(getResources().getColor(R.color.edit_hint_color))
                    .append("¥" + fee + "元").setFontSize(13, true).setForegroundColor(getResources().getColor(R.color.nursing_status_red))
                    .append("）").setFontSize(13, true).setForegroundColor(getResources().getColor(R.color.edit_hint_color)).create();
            mHosptalCost.setText(medicalTv);
        }
        String f = new DecimalFormat("0.00").format(total);
        mTotalPayTv.setText("去付款¥" + f + "元");

      /*  BigDecimal bd = new BigDecimal(fee);
        Double d = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        shippingFee = d;
        BigDecimal orderfee = new BigDecimal(orderFee);
        double total = d + orderfee.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        String f = new DecimalFormat("0.00").format(total);
        mTotalPayTv.setText("去付款¥" + f + "元");*/
        //        mTotalPayTv.setText("去付款¥" + total + "元");
    }

    private long mAddressId;

    private TextView mPayAddress;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getLocation(Event event) {
        if (event.getType() == EventType.PAY_SELECT_ADDRESS) {
            LocationInfo locationInfo = (LocationInfo) event.getData();
            String address =
                    locationInfo.getProvinceName() + locationInfo.getCityName() + locationInfo.getAreaName() + locationInfo.getAddress();
            mPayAddress.setText(address);
            mPayAddress.setTextColor(getResources().getColor(R.color.pay_unselected));
            if (locationInfo.getId() != 0) {
                mAddressId = locationInfo.getId();
                getController().getDistributionFee(mAddressId);
            }
        }
    }


    /**
     * 获取外延处方地址
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveEvent(Event event) {
        if (event.getType() == EventType.WAI_PAY_SELECT_ADDRESS) {
            mLocationInfo = (LocationInfo) event.getData();
            String address =
                    mLocationInfo.getProvinceName() + mLocationInfo.getCityName() + mLocationInfo.getAreaName() + mLocationInfo.getAddress();
            mPayAddress.setText(address);
            mWaiYanAddressId = mLocationInfo.getId();
            Logger.e("地址=" + address);
            getPrescriptionDetailDrugs(address, mPprescriptionId);
        }
    }

    /**
     * 获取药品
     */
    public void getPrescriptionDetailDrugs(String address, String id) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PrescriptionService.class).getDetailById(id), new HttpSubscriber<PrescriptionDetailBean>(getContext(), getDisposable(), true, true) {
            @Override
            public void requestComplete(@org.jetbrains.annotations.Nullable PrescriptionDetailBean data) {
                if (null != data && !CommUtil.isEmpty(data.getList())) {
                    getHttpFindDrugstores(address, data.getList());
                }

            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                ToastUtils.showShort(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

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

                if (mHosptalCost != null && mHosptalCost.isChecked()) {

                    SpannableStringBuilder medicalTv =
                            new SpanUtils().append("医院配送").setFontSize(13, true).setForegroundColor(getResources().getColor(R.color.pay_unselected))
                                    .create();
                    mHosptalCost.setText(medicalTv);
                }

            }
        }
    }

    private void showPayDialog(boolean needAddress, String titleFee, double totalFee,
                               String orderNum) {
        String feeTv = new DecimalFormat("0.00").format(totalFee);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(),
                R.style.BottomSheetDialog);
        bottomSheetDialog.setCancelable(false);
        mPSfee = 0.00;
        final boolean[] isAgree = {false};
        int[] payType = {2};
        bottomSheetDialog.setCanceledOnTouchOutside(false);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.pay_ment_dialog_layout,
                null, false);
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

        SpannableStringBuilder medicalTv =
                new SpanUtils().append("医院配送").setFontSize(13, true).setForegroundColor(getResources().getColor(R.color.pay_unselected))
                        .create();
        mHosptalCost.setText(medicalTv);
        addressSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationManageActivity.start(getContext(), Type.PAY_SELECT_ADDRESS);
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
                        //                        getController().pay(mAddressId, orderNum,
                        //                        payType[0], mPsTotal);
                        Map<String, Object> map = new HashMap<>();
                        map.put("orderId", isPayOrderId);
                        map.put("type", payType[0]);
                        map.put("addressId", mAddressId);
                        getController().inquiryPay(map, payType[0]);
                        bottomSheetDialog.dismiss();
                    }
                } else {
                    //                    getController().pay(0, orderNum, payType[0], totalFee);
                    Map<String, Object> map = new HashMap<>();
                    map.put("orderId", isPayOrderId);
                    map.put("type", payType[0]);
                    map.put("addressId", 0);
                    getController().inquiryPay(map, payType[0]);
                    bottomSheetDialog.dismiss();
                }
                //                } else {
                //取消支付协议
                //                    ToastUtil.showMessage(getContext(), "请阅读并同意支付协议");
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
    }

    /**
     * 展示支付弹框
     */
    private void showPayTypeDialog(String titleFee, double totalFee, String orderNum, String id) {
        isSendDrugsToHome = false;
        WaiPayType[0] = 2;
        payWaiType = Type.ALIPAY;
        mPharmacyBean = null;
        mPharmacyBeans = null;
        mPharmacyName = null;
        mPharmacyAddress = null;
        mWaiYanAddressId = 0;
        FixHeightBottomSheetDialog bottomWaiYanSheetDialog = new FixHeightBottomSheetDialog(this);
        bottomWaiYanSheetDialog.setCancelable(false);
        final boolean[] isAgree = {false};

        bottomWaiYanSheetDialog.setCanceledOnTouchOutside(false);
        View view = LayoutInflater.from(this).inflate(R.layout.pay_outside_dialog_layout, null,
                false);
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
                LocationManageActivity.start(ConversationActivity.this,
                        Type.WAI_PAY_SELECT_ADDRESS);
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
                GotoActivityUtil.gotoChoosePharmacyActivity(ConversationActivity.this, id);
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
                    Map<String, Object> map = new HashMap<>();
                    map.put("orderId", isPayOrderId);
                    if (payWaiType.equals(Type.WECHATPAY)) {
                        map.put("type", 1);
                    }
                    if (payWaiType.equals(Type.ALIPAY)) {
                        map.put("type", 2);
                    }

                    updatePrescriptionOrder(true, map);
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
                    Map<String, Object> map = new HashMap<>();
                    map.put("orderId", isPayOrderId);
                    if (payWaiType.equals(Type.WECHATPAY)) {
                        map.put("type", 1);
                    }
                    if (payWaiType.equals(Type.ALIPAY)) {
                        map.put("type", 2);
                    }
                    updatePrescriptionOrder(false, map);
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
                    map.put("orderId", isPayOrderId);
                    if (payWaiType.equals(Type.WECHATPAY)) {
                        map.put("type", 1);
                    }
                    if (payWaiType.equals(Type.ALIPAY)) {
                        map.put("type", 2);
                    }
                    if (null != mPharmacyBean) {
                        updatePrescriptionOrder(true, map);
                    }

                    bottomWaiYanSheetDialog.dismiss();
                }
                //   ToastUtils.showShort("暂未接入支付");
            }
        });


        //  mTotalPayTv.setText("去付款¥" + totalFee + "元");
        //        mTotalPayTv.setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View view) {
        //                if (needAddress && mAddressId == 0 && mHosptalCost.isChecked()) {
        //                    ToastUtils.showShort("请选择配送地址");
        //                } else {
        //                    //去支付
        //                    getController().pay(mAddressId, orderNum, payType[0], totalFee);
        //                    bottomSheetDialog.dismiss();
        //                }
        //            }
        //        });
        pay_agreement_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转支付协议页面
                AgreementActivity.startPayAgreement(ConversationActivity.this);
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


    public boolean isSendDrugsToHome = false;

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
     * 获得药店名字获取配送费
     */
    private void getHttpFindDrugstores(String mAddress, List<List<PrescriptionDrugBean>> drugs) {
        Map<String, Object> map = new HashMap<>();
        map.put("address", mAddress);
        map.put("drugs", drugs);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PrescriptionService.class).getFindDrugstoresByDistribution(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<List<PharmacyBean>>(this, getDisposable(), true, true) {
            @Override
            public void requestComplete(@org.jetbrains.annotations.Nullable List<PharmacyBean> data) {
                if (!CommUtil.isEmpty(data)) {
                    mPharmacyBeans = data;
                    refreshDeliveryCostView(data);
                    refreshPriceView(data);
                }

            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                return super.requestError(exception, code, msg);
            }
        });

    }

    public void refreshDeliveryCostView(List<PharmacyBean> data) {
        if (!CommUtil.isEmpty(data) && null != data.get(0)) {
            mDeliveryCostTv.setText("￥" + String.valueOf(data.get(0).getDeliveryCost()) + "元");
        }

    }

    public void refreshPriceView(List<PharmacyBean> data) {
        if (!CommUtil.isEmpty(data) && null != data.get(0)) {
            //PharmacyBean pharmacyBean = data.get(0);
            //BigDecimal deliveryCost = new BigDecimal(data.get(0).getDeliveryCost());
            BigDecimal sumFee = new BigDecimal(data.get(0).getSumFee());
            //四舍五入，保留两位小数
            mOrderPriceTv.setText("￥" + sumFee.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() + "元");
            if (mRadioHome.isChecked()) {
                mWaiYanTotalPayTv.setText("去付款¥" + sumFee.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() + "元");
            }

        }

    }

    /**
     * 外延处方获取地址列表
     */
    public void getWaiYanLocationList() {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", Global.getUserId());
        map.put("currentPage", "1");
        map.put("pageSize", 50);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(LocationService.class).getAddressList(map), new HttpSubscriber<PageBean<LocationInfo>>(ConversationActivity.this, getDisposable(), false) {
            @Override
            public void requestComplete(@org.jetbrains.annotations.Nullable PageBean<LocationInfo> data) {
                getWaiYanLocationList(data.getRecords());
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {

                return super.requestError(exception, code, msg);
            }
        });
    }


    public void getWaiYanLocationList(List<LocationInfo> data) {
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
                //   mPayAddress.setTextColor(getResources().getColor(R.color.edit_hint_color));
            }
        }
    }

    /**
     * 更新处方订单
     */
    private void updatePrescriptionOrder(boolean isOnline, Map<String, Object> payMap) {
        Map<String, Object> map = new HashMap<>();
        PharmacyBean pharmacyBean;
        if (isSendDrugsToHome) {
            map.put("consigneeAddress",
                    mLocationInfo.getProvinceName() + mLocationInfo.getCityName() + mLocationInfo.getAreaName() + mLocationInfo.getAddress());
            map.put("consigneeName", mLocationInfo.getAddressName());
            map.put("consigneePhone", mLocationInfo.getPhone());
            map.put("delivery", "1");
            pharmacyBean = mPharmacyBeans.get(0);

        } else {
            map.put("delivery", "0");
            pharmacyBean = mPharmacyBean;
        }

        map.put("drugstore", pharmacyBean.getDrugstore());
        map.put("drugstoreCode", pharmacyBean.getDrugstoreCode());
        map.put("drugsStoreAddress", pharmacyBean.getDrugstoreAddress());
        map.put("isOnline", isOnline ? "0" : "1");
        map.put("fee", pharmacyBean.getSumFee());
        map.put("id", mPprescriptionId);
        map.put("orderNumber", orderNum);
        map.put("items", pharmacyBean.getDrugsDtos());
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PrescriptionService.class).updatePrescriptionOrder(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<String>(this, getDisposable(), true, true) {
            @Override
            public void requestComplete(@org.jetbrains.annotations.Nullable String data) {
                if (isOnline) {
                    if (payWaiType.equals(Type.WECHATPAY)) {
                        getController().inquiryPay(payMap, 1);
                    }
                    if (payWaiType.equals(Type.ALIPAY)) {
                        getController().inquiryPay(payMap, 2);
                    }

                } else {
                    paySuccess();
                    ToastUtils.showShort("提交成功");
                }
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                return super.requestError(exception, code, msg);
            }
        });

    }
}

