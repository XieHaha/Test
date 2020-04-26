package com.keydom.mianren.ih_doctor.activity.im;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.keydom.ih_common.activity.DiagnoseOrderDetailActivity;
import com.keydom.ih_common.activity.HandleProposeAcitivity;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.bean.InquiryStatus;
import com.keydom.ih_common.bean.TriageBean;
import com.keydom.ih_common.im.ImClient;
import com.keydom.ih_common.im.config.ImConstants;
import com.keydom.ih_common.im.listener.IConversationBehaviorListener;
import com.keydom.ih_common.im.model.ImMessageConstant;
import com.keydom.ih_common.im.model.custom.ConsultationResultAttachment;
import com.keydom.ih_common.im.model.custom.DisposalAdviceAttachment;
import com.keydom.ih_common.im.model.custom.EndInquiryAttachment;
import com.keydom.ih_common.im.model.custom.ExaminationAttachment;
import com.keydom.ih_common.im.model.custom.InquiryAttachment;
import com.keydom.ih_common.im.model.custom.InspectionAttachment;
import com.keydom.ih_common.im.model.custom.ReferralApplyAttachment;
import com.keydom.ih_common.im.model.custom.ReferralDoctorAttachment;
import com.keydom.ih_common.im.model.custom.TriageOrderAttachment;
import com.keydom.ih_common.im.model.custom.UserFollowUpAttachment;
import com.keydom.ih_common.im.model.event.EndInquiryEvent;
import com.keydom.ih_common.im.widget.ImMessageView;
import com.keydom.ih_common.im.widget.plugin.EndInquiryPlugin;
import com.keydom.ih_common.im.widget.plugin.UserFollowUpPlugin;
import com.keydom.ih_common.im.widget.plugin.VideoPlugin;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.CalculateTimeUtils;
import com.keydom.ih_common.utils.SharePreferenceManager;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.mianren.ih_doctor.MyApplication;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.common_document.CommonDocumentActivity;
import com.keydom.mianren.ih_doctor.activity.doctor_cooperation.DianoseCaseDetailActivity;
import com.keydom.mianren.ih_doctor.activity.doctor_cooperation.FillOutApplyActivity;
import com.keydom.mianren.ih_doctor.activity.my_doctor_or_nurse.DoctorOrNurseDetailActivity;
import com.keydom.mianren.ih_doctor.activity.online_consultation.ConsultationApplyActivity;
import com.keydom.mianren.ih_doctor.activity.online_diagnose.ApplyForCheckActivity;
import com.keydom.mianren.ih_doctor.activity.online_diagnose.CheckOrderDetailActivity;
import com.keydom.mianren.ih_doctor.activity.online_diagnose.DiagnosePatientInfoActivity;
import com.keydom.mianren.ih_doctor.activity.online_triage.TriageOrderApplyActivity;
import com.keydom.mianren.ih_doctor.activity.online_triage.TriageOrderDetailActivity;
import com.keydom.mianren.ih_doctor.activity.patient_manage.PatientDatumActivity;
import com.keydom.mianren.ih_doctor.activity.prescription_check.DiagnosePrescriptionActivity;
import com.keydom.mianren.ih_doctor.activity.prescription_check.PrescriptionActivity;
import com.keydom.mianren.ih_doctor.adapter.DiagnoseOrderRecyclrViewAdapter;
import com.keydom.mianren.ih_doctor.bean.CheckItemListBean;
import com.keydom.mianren.ih_doctor.bean.InquiryBean;
import com.keydom.mianren.ih_doctor.bean.MessageEvent;
import com.keydom.mianren.ih_doctor.constant.Const;
import com.keydom.mianren.ih_doctor.constant.EventType;
import com.keydom.mianren.ih_doctor.constant.ServiceConst;
import com.keydom.mianren.ih_doctor.constant.TypeEnum;
import com.keydom.mianren.ih_doctor.m_interface.SingleClick;
import com.keydom.mianren.ih_doctor.net.DiagnoseApiService;
import com.keydom.mianren.ih_doctor.utils.DateUtils;
import com.keydom.mianren.ih_doctor.view.FixHeightBottomSheetDialog;
import com.keydom.mianren.ih_doctor.view.im_plugin.VoiceInputPlugin;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.constant.MsgDirectionEnum;
import com.netease.nimlib.sdk.msg.constant.MsgTypeEnum;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;
import com.scwang.smartrefresh.layout.util.DensityUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.helper.StringUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * 问诊界面
 *
 * @author THINKPAD B
 */
public class ConversationActivity extends BaseControllerActivity<ConversationController> implements View.OnClickListener, ConversationView {

    public static final int FINISH_PRESCRIPTION = 1003;
    /**
     * 发送消息
     */
    public static final int SEND_MESSAGE = 1001;
    /**
     * 重新获取问诊单，更新状态
     */
    public static final int UPDATE_STATUS = 1002;
    /**
     * 成员邀请
     */
    public static final int SELECT_MEMBER = 1000;
    private ImageView mInquiryTypeImage;
    private TextView mInquiryTypeTv;
    private TextView mQuestionRemainingTimeTv;
    private LinearLayout mVisitingLl;
    private TextView mVisitingRemainingTimeTv;
    private LinearLayout mEndTheConsultationLl;
    private LinearLayout mDiagnosticPrescriptionLl;
    private LinearLayout mDisposalAdviceLl;
    private ConstraintLayout mCompletedCl;
    private ImMessageView mMessageView;
    private PopupWindow mPopupWindow;
    private TextView inspection, examination, referralTv, inquiryPopTriageTv,
            inquiryPopConsultationTv,
            inquiryPopDiagnosticPrescriptionTv, inquiryPopAdvice;
    private String sessionId;
    private InquiryBean orderBean;
    private boolean isGetStatus = false;

    /**
     * 问诊状态<br>
     * 0未支付 1 待接收 2问诊中 3审核不通过 4 待转诊确认 5 待评价 7完成 -1已取消
     */
    private int inquiryStatus = Integer.MAX_VALUE;
    /**
     * 订单类型<br>
     * 0问诊单  1咨询单
     */
    private int orderType = 0;

    private long orderId;

    /**
     * 转诊状态<br>
     * -1患者拒绝 -2医生拒绝 0等待患者确认 1等待医生确认 2已完成
     */
    private int referralState;

    /**
     * 订单过期时长
     */
    private String visitingRemainingTime = "";

    /**
     * 问诊剩余时长
     */
    private String inquiryRemainingTime = "";

    private boolean chatting;
    private boolean team;
    private Disposable timeDisposable;
    private int mMin;
    private int mHour;

    private VideoPlugin mVideoPlugin;
    private EndInquiryPlugin mEndInquiryPlugin;
    private UserFollowUpPlugin mUserFollowUpPlugin;
    private VoiceInputPlugin mVoiceInputPlugin;
    /**
     * menu
     */
    private View view;

    @Override
    protected void onResume() {
        super.onResume();
        if (team) {
            NIMClient.getService(MsgService.class).setChattingAccount(sessionId,
                    SessionTypeEnum.Team);
        } else {
            NIMClient.getService(MsgService.class).setChattingAccount(sessionId,
                    SessionTypeEnum.P2P);
        }
        getController().getInquiryStatus();
    }

    @Override
    public void onPause() {
        super.onPause();
        NIMClient.getService(MsgService.class).setChattingAccount(MsgService.MSG_CHATTING_ACCOUNT_NONE, SessionTypeEnum.None);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_conversation;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        mInquiryTypeImage = findViewById(R.id.inquiry_type_image);
        mInquiryTypeTv = findViewById(R.id.inquiry_type_tv);
        mQuestionRemainingTimeTv = findViewById(R.id.question_remaining_time_tv);
        ImageView mVisitingImage = findViewById(R.id.visiting_image);
        TextView mVisitingTv = findViewById(R.id.visiting_tv);
        mVisitingLl = findViewById(R.id.visiting_ll);
        mVisitingRemainingTimeTv = findViewById(R.id.visiting_remaining_time_tv);
        LinearLayout mBackLl = findViewById(R.id.back_ll);
        LinearLayout mEndLl = findViewById(R.id.end_ll);
        mEndTheConsultationLl = findViewById(R.id.end_the_consultation_ll);
        mDiagnosticPrescriptionLl = findViewById(R.id.diagnostic_prescription_ll);
        mDisposalAdviceLl = findViewById(R.id.disposal_advice_ll);
        mCompletedCl = findViewById(R.id.completed_cl);
        mMessageView = findViewById(R.id.im_view);
        getLifecycle().addObserver(mMessageView);
        mVisitingImage.setOnClickListener(this);
        mVisitingTv.setOnClickListener(this);
        mDiagnosticPrescriptionLl.setOnClickListener(this);
        mDisposalAdviceLl.setOnClickListener(this);
        mBackLl.setOnClickListener(this);
        mEndLl.setOnClickListener(this);
        //        mMessageView.addPlugin(new EmojiPlugin());
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            isGetStatus = extras.getBoolean(DiagnoseOrderRecyclrViewAdapter.IS_ORDER);
            team = extras.getBoolean(ImConstants.TEAM);
            orderId = extras.getLong("orderId");
        }
        initView();
        initListener();
    }

    /**
     * 消息点击事件
     */
    private void initListener() {
        mMessageView.setOnConversationBehaviorListener(new IConversationBehaviorListener() {
            @Override
            public boolean onUserPortraitClick(Context context, IMMessage message) {
                if (message.getDirect().getValue() == MsgDirectionEnum.In.getValue()) {
                    if (orderBean != null) {
                        DiagnosePatientInfoActivity.start(context, orderBean.getPatientId());
                    } else {
                        PatientDatumActivity.start(context, message.getSessionId());
                    }

                } else {
                    NimUserInfo patientInfo =
                            (NimUserInfo) ImClient.getUserInfoProvider().getUserInfo(message.getFromAccount());
                    NimUserInfo currentUserInfo =
                            (NimUserInfo) ImClient.getUserInfoProvider().getUserInfo(SharePreferenceManager.getUserCode());
                    if ((ImMessageConstant.DOCTOR).equals(currentUserInfo.getExtensionMap().get(ImConstants.CALL_USER_TYPE))) {
                        if ((ImMessageConstant.DOCTOR).equals(patientInfo.getExtensionMap().get(ImConstants.CALL_USER_TYPE))) {
                            Intent intent = new Intent(ConversationActivity.this,
                                    DoctorOrNurseDetailActivity.class);
                            intent.putExtra("doctorCode", String.valueOf(patientInfo.getAccount()));
                            startActivity(intent);
                        }
                    }
                }
                return false;
            }

            @Override
            public boolean onMessageClick(Context context, View view, IMMessage message) {
                if (message.getMsgType() == MsgTypeEnum.custom) {
                    if (message.getAttachment() instanceof InquiryAttachment) {//问诊单
                        DiagnoseOrderDetailActivity.start(context,
                                ((InquiryAttachment) message.getAttachment()).getId());
                    } else if (message.getAttachment() instanceof TriageOrderAttachment) {//分诊单
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
                                TypeEnum.TRIAGE_RECEIVED, true);
                    } else if (message.getAttachment() instanceof ExaminationAttachment) {//检查单
                        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(DiagnoseApiService.class).getInspectDetail(((ExaminationAttachment) message.getAttachment()).getId()), new HttpSubscriber<CheckItemListBean>(getContext(), getDisposable(), false) {
                            @Override
                            public void requestComplete(@Nullable CheckItemListBean data) {
                                if (data != null) {
                                    CheckOrderDetailActivity.startInspactOrder(context,
                                            ((ExaminationAttachment) message.getAttachment()).getId(), orderBean);
                                } else {
                                    ToastUtil.showMessage(context, "检查报告单不存在");
                                }
                            }

                            @Override
                            public boolean requestError(@NotNull ApiException exception, int code
                                    , @NotNull String msg) {
                                ToastUtil.showMessage(context, msg);
                                return super.requestError(exception, code, msg);
                            }
                        });

                    } else if (message.getAttachment() instanceof InspectionAttachment) {//检验单
                        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(DiagnoseApiService.class).getCheckoutDetail(((InspectionAttachment) message.getAttachment()).getId()), new HttpSubscriber<CheckItemListBean>(getContext(), getDisposable(), false) {
                            @Override
                            public void requestComplete(@Nullable CheckItemListBean data) {
                                if (data != null) {
                                    CheckOrderDetailActivity.startTestOrder(context,
                                            ((InspectionAttachment) message.getAttachment()).getId(), orderBean);
                                } else {
                                    ToastUtil.showMessage(context, "检验报告单不存在");
                                }
                            }

                            @Override
                            public boolean requestError(@NotNull ApiException exception, int code
                                    , @NotNull String msg) {
                                ToastUtil.showMessage(context, msg);
                                return super.requestError(exception, code, msg);
                            }
                        });

                    } else if (message.getAttachment() instanceof ReferralApplyAttachment) {//转诊单
                        com.keydom.mianren.ih_doctor.activity.doctor_cooperation.DiagnoseOrderDetailActivity.startCommon(context, ((ReferralApplyAttachment) message.getAttachment()).getId());
                    } else if (message.getAttachment() instanceof ReferralDoctorAttachment) {//换诊

                    } else if (message.getAttachment() instanceof ConsultationResultAttachment) {
                        //处方
                        DianoseCaseDetailActivity.start(context,
                                ((ConsultationResultAttachment) message.getAttachment()).getId());
                        //                        PrescriptionActivity.startCommon(context, Long
                        //                        .parseLong(((ConsultationResultAttachment)
                        //                        message.getAttachment()).getId()));
                    } else if (message.getAttachment() instanceof DisposalAdviceAttachment) {
                        HandleProposeAcitivity.start(getContext(),
                                ((DisposalAdviceAttachment) message.getAttachment()).getContent());

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
            public boolean onPayClick(Context context, View view, IMMessage message) {
                if (message.getAttachment() instanceof ExaminationAttachment) {//检查单
                    ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(DiagnoseApiService.class).getInspectDetail(((ExaminationAttachment) message.getAttachment()).getId()), new HttpSubscriber<CheckItemListBean>(getContext(), getDisposable(), false) {
                        @Override
                        public void requestComplete(@Nullable CheckItemListBean data) {
                            if (data != null) {
                                CheckOrderDetailActivity.startInspactOrder(context,
                                        ((ExaminationAttachment) message.getAttachment()).getId()
                                        , orderBean);
                            } else {
                                ToastUtil.showMessage(context, "检查报告单不存在");
                            }
                        }

                        @Override
                        public boolean requestError(@NotNull ApiException exception, int code,
                                                    @NotNull String msg) {
                            ToastUtil.showMessage(context, msg);
                            return super.requestError(exception, code, msg);
                        }
                    });

                } else if (message.getAttachment() instanceof InspectionAttachment) {//检验单
                    ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(DiagnoseApiService.class).getCheckoutDetail(((InspectionAttachment) message.getAttachment()).getId()), new HttpSubscriber<CheckItemListBean>(getContext(), getDisposable(), false) {
                        @Override
                        public void requestComplete(@Nullable CheckItemListBean data) {
                            if (data != null) {
                                CheckOrderDetailActivity.startTestOrder(context,
                                        ((InspectionAttachment) message.getAttachment()).getId(),
                                        orderBean);
                            } else {
                                ToastUtil.showMessage(context, "检验报告单不存在");
                            }
                        }

                        @Override
                        public boolean requestError(@NotNull ApiException exception, int code,
                                                    @NotNull String msg) {
                            ToastUtil.showMessage(context, msg);
                            return super.requestError(exception, code, msg);
                        }
                    });

                } else
                    PrescriptionActivity.startCommon(context,
                            Long.parseLong(((ConsultationResultAttachment) message.getAttachment()).getId()));
                return false;
            }

            @Override
            public boolean onPrescriptionClick(Context context,
                                               @android.support.annotation.Nullable IMMessage message) {
                if (message.getAttachment() instanceof ExaminationAttachment) {//检查单


                    ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(DiagnoseApiService.class).getInspectDetail(((ExaminationAttachment) message.getAttachment()).getId()), new HttpSubscriber<CheckItemListBean>(getContext(), getDisposable(), false) {
                        @Override
                        public void requestComplete(@Nullable CheckItemListBean data) {
                            if (data != null) {
                                CheckOrderDetailActivity.startInspactOrder(context,
                                        ((ExaminationAttachment) message.getAttachment()).getId()
                                        , orderBean);
                            } else {
                                ToastUtil.showMessage(context, "检查报告单不存在");
                            }
                        }

                        @Override
                        public boolean requestError(@NotNull ApiException exception, int code,
                                                    @NotNull String msg) {
                            ToastUtil.showMessage(context, msg);
                            return super.requestError(exception, code, msg);
                        }
                    });

                } else if (message.getAttachment() instanceof InspectionAttachment) {//检验单

                    ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(DiagnoseApiService.class).getCheckoutDetail(((InspectionAttachment) message.getAttachment()).getId()), new HttpSubscriber<CheckItemListBean>(getContext(), getDisposable(), false) {
                        @Override
                        public void requestComplete(@Nullable CheckItemListBean data) {
                            if (data != null) {
                                CheckOrderDetailActivity.startTestOrder(context,
                                        ((InspectionAttachment) message.getAttachment()).getId(),
                                        orderBean);
                            } else {
                                ToastUtil.showMessage(context, "检验报告单不存在");
                            }
                        }

                        @Override
                        public boolean requestError(@NotNull ApiException exception, int code,
                                                    @NotNull String msg) {
                            ToastUtil.showMessage(context, msg);
                            return super.requestError(exception, code, msg);
                        }
                    });

                } else
                    PrescriptionActivity.startCommon(context,
                            Long.parseLong(((ConsultationResultAttachment) message.getAttachment()).getId()));
                return false;
            }
        });
    }

    private void initView() {
        Uri data = getIntent().getData();
        if (data != null) {
            sessionId = data.getQueryParameter(ImConstants.CALL_SESSION_ID);
            if (team) {
                mMessageView.setMessageInfo(sessionId, SessionTypeEnum.Team);
                if (ImClient.getTeamProvider().getTeamById(sessionId) != null) {
                    setTitle(ImClient.getTeamProvider().getTeamById(sessionId).getName());
                }
            } else {
                mMessageView.setMessageInfo(sessionId, SessionTypeEnum.P2P);
                setTitle("问诊详情");
            }
        }

        setRightImgListener(v -> {
            mPopupWindow.showAsDropDown(getTitleLayout().mViewHolder.ivRightComplete);
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.alpha = 0.7f;
            getWindow().setAttributes(lp);
            mPopupWindow.setOnDismissListener(() -> {
                WindowManager.LayoutParams lp1 = getWindow().getAttributes();
                lp1.alpha = 1f;
                getWindow().setAttributes(lp1);
            });
        });

        view = getLayoutInflater().inflate(R.layout.im_inquiry_pop_layout, null, false);
        initPopupWindow();
        //检验申请
        inspection = view.findViewById(R.id.inspection);
        inspection.setOnClickListener(this);
        //检查申请
        examination = view.findViewById(R.id.examination);
        examination.setOnClickListener(this);
        //转诊
        referralTv = view.findViewById(R.id.referral);
        referralTv.setOnClickListener(this);
        //分诊
        inquiryPopTriageTv = view.findViewById(R.id.inquiry_pop_triage_tv);
        inquiryPopTriageTv.setOnClickListener(this);
        //会诊
        inquiryPopConsultationTv = view.findViewById(R.id.inquiry_pop_consultation_tv);
        inquiryPopConsultationTv.setOnClickListener(this);
        //诊断与处方
        inquiryPopDiagnosticPrescriptionTv =
                view.findViewById(R.id.inquiry_pop_diagnostic_prescription_tv);
        inquiryPopDiagnosticPrescriptionTv.setOnClickListener(this);
        //处置建议
        inquiryPopAdvice = view.findViewById(R.id.inquiry_pop_advice_tv);
        inquiryPopAdvice.setOnClickListener(this);

        mVideoPlugin = new VideoPlugin(this);
        //区分群聊单聊视频发起
        mVideoPlugin.setTeam(team);
        mVideoPlugin.setTeamId(sessionId);

        mEndInquiryPlugin = new EndInquiryPlugin(() -> {
            mMessageView.hideExtension();
            mEndTheConsultationLl.setVisibility(View.VISIBLE);
        });

        mUserFollowUpPlugin = new UserFollowUpPlugin(() -> showUserFollowUp());
        //mMessageView.addPlugin(mUserFollowUpPlugin);

        mVoiceInputPlugin = new VoiceInputPlugin(this);
        mMessageView.addPlugin(mVoiceInputPlugin);
    }

    private void initPopupWindow() {
        mPopupWindow = new PopupWindow(view, DensityUtil.dp2px(200),
                DensityUtil.dp2px(60 * 10));
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);
    }

    private void initStatus() {
        mMessageView.getPluginAdapter().getPluginModule(0);

        if (!chatting) {
            mMessageView.removePlugin(mVideoPlugin);
            mMessageView.removePlugin(mEndInquiryPlugin);
            //            if (orderBean != null && orderBean.getInquisitionType() == 1)
            mMessageView.addPlugin(mVideoPlugin);
            mMessageView.addPlugin(mEndInquiryPlugin);
        }
        if (!chatting) {
            setRightImg(ContextCompat.getDrawable(this, R.mipmap.more_icon));
        }
        //如果是从患者管理进来的，这就是一个纯聊天界面，没其他的布局
        if (chatting) {
            mVisitingLl.setVisibility(View.GONE);
            mCompletedCl.setVisibility(View.GONE);
            mEndTheConsultationLl.setVisibility(View.GONE);
            findViewById(R.id.inquiry_header).setVisibility(View.GONE);
        }
        //待接诊
        if (InquiryStatus.INQUIRY_WAIT == inquiryStatus || InquiryStatus.INQUIRY_REFERRAL_DOCTOR == inquiryStatus) {
            setRightImgVisibility(false);
            findViewById(R.id.inquiry_header).setVisibility(View.VISIBLE);
            mInquiryTypeImage.setImageResource(R.drawable.inquiry_type_image_waiting);
            mInquiryTypeTv.setText("待接诊");
            mInquiryTypeTv.setTextColor(ContextCompat.getColor(this, R.color.im_status_waiting));
            mQuestionRemainingTimeTv.setVisibility(View.GONE);
            mVisitingLl.setVisibility(View.VISIBLE);
            mCompletedCl.setVisibility(View.GONE);
            mMessageView.hideExtension();
        }
        //接诊中
        else if (InquiryStatus.INQUIRY_ING == inquiryStatus) {
            inquiryIng();
        }
        //发起结束问诊，患者还未处理
        else if (InquiryStatus.INQUIRY_APPLY_END == inquiryStatus) {
            inquiryApply();
        }
        //问诊已完成,等待开处方或者处置建议
        else if (InquiryStatus.INQUIRY_PRESCRIBE == inquiryStatus) {
            inquiryEnd();
        }
        //处方审核失败，隐藏结束问诊
        else if (InquiryStatus.AUDIT_FAILE == inquiryStatus) {
            //            mMessageView.removePlugin(mEndInquiryPlugin);
            inquiryEnd();
        }
        //已开具处方，待评价
        else if (InquiryStatus.INQUIRY_NOT_EVALUATED == inquiryStatus) {
            mCompletedCl.setVisibility(View.GONE);
        }
        if (orderBean != null && orderBean.getIsSuggest() == 1) {
            mCompletedCl.setVisibility(View.GONE);
        }

        if (orderBean != null && (orderBean.getCardNumber() == null || "".equals(orderBean.getCardNumber()) || orderType == 1)) {//无就诊卡
            mPopupWindow.getContentView().findViewById(R.id.inspection).setVisibility(View.GONE);
            mPopupWindow.getContentView().findViewById(R.id.examination).setVisibility(View.GONE);
        }
        if (orderType == 1) {
            //            mPopupWindow.getContentView().findViewById(R.id.referral).setVisibility
            //            (View.GONE);
            //            setRightImgVisibility(false);
            inspection.setVisibility(View.GONE);
            examination.setVisibility(View.GONE);
            referralTv.setVisibility(View.GONE);
            inquiryPopTriageTv.setVisibility(View.GONE);
            inquiryPopConsultationTv.setVisibility(View.GONE);
            inquiryPopDiagnosticPrescriptionTv.setVisibility(View.GONE);
        }
        if (referralState == 0 || referralState == 1) {
            referralTv.setText("取消转诊");
        } else {
            referralTv.setText("转诊");
        }

        if (orderBean.getIsVip() == 1) {
            //分诊中
            if (orderBean.getState() == InquiryStatus.INQUIRY_TRIAGE_DOING) {
                inquiryPopTriageTv.setVisibility(View.GONE);
            } else {
                inquiryPopTriageTv.setVisibility(View.VISIBLE);
            }
            inquiryPopConsultationTv.setVisibility(View.VISIBLE);
            if (orderBean.getState() == InquiryStatus.INQUIRY_TRIAGE_DOING || orderBean.getState() == InquiryStatus.INQUIRY_ING) {
                inquiryPopDiagnosticPrescriptionTv.setVisibility(View.VISIBLE);
            } else {
                inquiryPopDiagnosticPrescriptionTv.setVisibility(View.GONE);
            }
        } else {
            inquiryPopTriageTv.setVisibility(View.GONE);
            inquiryPopConsultationTv.setVisibility(View.GONE);
        }
    }

    private void inquiryEnd() {
        setRightImgVisibility(false);
        findViewById(R.id.inquiry_header).setVisibility(View.VISIBLE);
        mInquiryTypeImage.setImageResource(R.drawable.inquiry_type_image_completed);
        mInquiryTypeTv.setTextColor(ContextCompat.getColor(this, R.color.im_status_completed));
        mQuestionRemainingTimeTv.setVisibility(View.VISIBLE);
        if (InquiryStatus.INQUIRY_PRESCRIBE == inquiryStatus) {
            //            mInquiryTypeTv.setText("已完成");
            //            mQuestionRemainingTimeTv.setVisibility(View.VISIBLE);
            mInquiryTypeTv.setText("已完成");
            mQuestionRemainingTimeTv.setText("医生已完成问诊");
            mMessageView.hideExtension();
            //            mMessageView.removePlugin(mEndInquiryPlugin);
        } else if (InquiryStatus.AUDIT_FAILE == inquiryStatus) {
            mMessageView.hideExtension();
            mInquiryTypeTv.setText("已完成");
            mQuestionRemainingTimeTv.setText("医生已完成问诊");
            mMessageView.removePlugin(mEndInquiryPlugin);
        } else {
            mInquiryTypeTv.setText("已完成");
            mQuestionRemainingTimeTv.setText("医生已完成问诊");
            mMessageView.removePlugin(mEndInquiryPlugin);
        }
        mCompletedCl.setVisibility(View.VISIBLE);
        if (orderType == 1) {//咨询单显示一个按钮
            mDiagnosticPrescriptionLl.setVisibility(View.GONE);
            mDisposalAdviceLl.setVisibility(View.VISIBLE);
        } else {
            //有问诊卡、并且是医生则显示两个按钮否则显示一个按钮
            if (orderBean.getCardNumber() != null && !"".equals(orderBean.getCardNumber()) && SharePreferenceManager.getRoleId() == Const.ROLE_DOCTOR) {
                mDiagnosticPrescriptionLl.setVisibility(View.VISIBLE);
                mDisposalAdviceLl.setVisibility(View.VISIBLE);
            } else {
                mDiagnosticPrescriptionLl.setVisibility(View.GONE);
                mDisposalAdviceLl.setVisibility(View.VISIBLE);
            }
        }
        mVisitingLl.setVisibility(View.GONE);
        mEndTheConsultationLl.setVisibility(View.GONE);
    }

    /**
     * 问诊中布局设置
     */
    private void inquiryIng() {
        findViewById(R.id.inquiry_header).setVisibility(View.VISIBLE);
        mInquiryTypeImage.setImageResource(R.drawable.inquiry_type_image_ing);
        mInquiryTypeTv.setText("问诊中");
        mInquiryTypeTv.setTextColor(ContextCompat.getColor(this, R.color.im_status_ing));
        mQuestionRemainingTimeTv.setVisibility(View.VISIBLE);
        mVisitingLl.setVisibility(View.GONE);
        mMessageView.showExtension();
    }

    /**
     * 申请结束问诊后，患者还未处理时布局
     */
    private void inquiryApply() {
        setRightImgVisibility(false);
        findViewById(R.id.inquiry_header).setVisibility(View.VISIBLE);
        mInquiryTypeTv.setText("问诊中");
        mInquiryTypeImage.setImageResource(R.drawable.inquiry_type_image_ing);
        mInquiryTypeTv.setTextColor(ContextCompat.getColor(this, R.color.im_status_ing));
        mEndTheConsultationLl.setVisibility(View.GONE);
        mQuestionRemainingTimeTv.setVisibility(View.VISIBLE);
        mMessageView.showExtension();
    }

    @SingleClick(1000)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.visiting_image:
            case R.id.visiting_tv:
                getController().acceptInquisition();
                break;
            case R.id.end_ll:
                getController().endInquisition();
                break;
            case R.id.back_ll:
                mEndTheConsultationLl.setVisibility(View.GONE);
                mMessageView.showExtension();
                break;
            case R.id.diagnostic_prescription_ll:
                if (MyApplication.serviceEnable(new String[]{ServiceConst.DOCTOR_PRESCRIPTION_SERVICE_CODE})) {
                    DiagnosePrescriptionActivity.startCreate(this, orderBean);
                } else {
                    showNotAccessDialog();
                }
                break;
            case R.id.disposal_advice_ll:
                DiagnosePrescriptionActivity.startHandle(this, orderBean);
                break;
            case R.id.inspection:
                if (MyApplication.serviceEnable(new String[]{ServiceConst.DOCTOR_CHECKOUT_SERVICE_CODE})) {
                    if (orderBean.getType() != 0 || orderBean.getCardNumber() == null || "".equals(orderBean.getCardNumber())) {//无就诊卡
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("未选择就诊人");
                        builder.setMessage("该订单未选择就诊人，无法开检查单！");
                        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        builder.create().show();
                    } else {
                        ApplyForCheckActivity.startCreateTest(this, orderBean);
                    }
                } else {
                    showNotAccessDialog();
                }
                if (mPopupWindow != null) {
                    mPopupWindow.dismiss();
                }
                break;
            case R.id.examination:
                if (MyApplication.serviceEnable(new String[]{ServiceConst.DOCTOR_CHECKOUT_SERVICE_CODE})) {
                    if (orderBean.getType() != 0 || orderBean.getCardNumber() == null || "".equals(orderBean.getCardNumber())) {//无就诊卡
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("未选择就诊人");
                        builder.setMessage("该订单未选择就诊人，无法开检验单");
                        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        builder.create().show();
                    } else {
                        ApplyForCheckActivity.startCreateInspect(this, orderBean);
                    }
                } else {
                    showNotAccessDialog();
                }
                if (mPopupWindow != null) {
                    mPopupWindow.dismiss();
                }
                break;
            case R.id.referral:
                if (referralState == 0 || referralState == 1) {
                    new GeneralDialog(getContext(), "确认取消该条转诊操作？",
                            () -> getController().stopReferral()).setTitle("提示").show();

                } else {
                    FillOutApplyActivity.startFillOut(this, orderBean);
                }
                if (mPopupWindow != null) {
                    mPopupWindow.dismiss();
                }
                break;
            case R.id.inquiry_pop_diagnostic_prescription_tv:
                //诊断与处方
                if (MyApplication.serviceEnable(new String[]{ServiceConst.DOCTOR_PRESCRIPTION_SERVICE_CODE})) {
                    DiagnosePrescriptionActivity.startCreate(this, orderBean);
                } else {
                    showNotAccessDialog();
                }
                if (mPopupWindow != null) {
                    mPopupWindow.dismiss();
                }
                break;
            case R.id.inquiry_pop_advice_tv:
                //处置建议
                DiagnosePrescriptionActivity.startHandle(this, orderBean);
                if (mPopupWindow != null) {
                    mPopupWindow.dismiss();
                }
                break;
            case R.id.inquiry_pop_triage_tv:
                //分诊
                TriageOrderApplyActivity.start(this, orderBean);
                if (mPopupWindow != null) {
                    mPopupWindow.dismiss();
                }
                break;
            case R.id.inquiry_pop_consultation_tv:
                //会诊
                ConsultationApplyActivity.start(this, orderBean);
                if (mPopupWindow != null) {
                    mPopupWindow.dismiss();
                }
                break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == FINISH_PRESCRIPTION) {
            if (team) {
                mMessageView.addData(ImClient.createLocalTipMessage(sessionId,
                        SessionTypeEnum.Team, "处方已发送,请等待药师审核结果"));
            } else {
                mMessageView.addData(ImClient.createLocalTipMessage(sessionId,
                        SessionTypeEnum.P2P, "处方已发送,请等待药师审核结果"));
            }
            getController().getInquiryStatus();
        }
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                default:
                    break;
                case SEND_MESSAGE:
                    List<IMMessage> messageList =
                            (List<IMMessage>) data.getSerializableExtra(Const.DATA);
                    if (messageList != null && messageList.size() > 0) {
                        for (IMMessage msg : messageList) {
                            mMessageView.addData(msg);
                        }
                    }
                    //                    getController().getInquiryStatus();
                    break;
                case UPDATE_STATUS:
                    getController().getInquiryStatus();
                    break;
                case SELECT_MEMBER:
                    startTeamAVChat(data.getStringArrayListExtra("accounts"));
                    break;
            }
        }
        if (resultCode == SEND_MESSAGE) {//启动处方页面，取消复诊提交后返回的信息
            List<IMMessage> messageList = (List<IMMessage>) data.getSerializableExtra(Const.DATA);
            if (messageList != null && messageList.size() > 0) {
                for (IMMessage msg : messageList) {
                    if (msg.getMsgType() == MsgTypeEnum.custom) {
                        getController().getInquiryStatus();
                    }
                    mMessageView.addData(msg);
                }
            }
            getController().getInquiryStatus();
        }
        mMessageView.onActivityPluginResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void startTeamAVChat(ArrayList<String> accounts) {
        ImClient.createRoom(this, sessionId, accounts);
    }

    private interface CallBack {
        void onCallBack();
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
            //如果患者同意结束问诊
            if (attachment.getEndType() == EndInquiryAttachment.PATIENT_AGREE) {
                EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.DIAGNOSE_ORDER_UPDATE).build());
                inquiryStatus = InquiryStatus.INQUIRY_PRESCRIBE;
                inquiryEnd();
                if (team) {
                    mMessageView.addData(ImClient.createLocalTipMessage(sessionId,
                            SessionTypeEnum.Team, "患者已同意结束此次问诊"));
                } else {
                    mMessageView.addData(ImClient.createLocalTipMessage(sessionId,
                            SessionTypeEnum.P2P, "患者已同意结束此次问诊"));
                }
            }
            //如果患者不同意结束问诊
            else if (attachment.getEndType() == EndInquiryAttachment.PATIENT_REFUSE) {
                inquiryStatus = InquiryStatus.INQUIRY_ING;
                inquiryIng();
                if (team) {
                    mMessageView.addData(ImClient.createLocalTipMessage(sessionId,
                            SessionTypeEnum.Team, "患者取消了结束问诊，请与患者沟通完成以后再结束"));
                } else {
                    mMessageView.addData(ImClient.createLocalTipMessage(sessionId,
                            SessionTypeEnum.P2P, "患者取消了结束问诊，请与患者沟通完成以后再结束"));
                }
            }
            //患者主动结束问诊
            else if (attachment.getEndType() == EndInquiryAttachment.PATIENT_END) {
                EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.DIAGNOSE_ORDER_UPDATE).build());
                inquiryStatus = InquiryStatus.INQUIRY_PRESCRIBE;
                inquiryEnd();
                if (orderType == 0) {
                    if (team) {
                        mMessageView.addData(ImClient.createLocalTipMessage(sessionId,
                                SessionTypeEnum.Team, "患者已结束此次问诊"));
                    } else {
                        mMessageView.addData(ImClient.createLocalTipMessage(sessionId,
                                SessionTypeEnum.P2P, "患者已结束此次问诊"));
                    }
                } else {
                    if (team) {
                        mMessageView.addData(ImClient.createLocalTipMessage(sessionId,
                                SessionTypeEnum.Team, "患者已结束此次问诊"));
                    } else {
                        mMessageView.addData(ImClient.createLocalTipMessage(sessionId,
                                SessionTypeEnum.P2P, "患者已结束此次问诊"));
                    }
                }
            }
        }
    }

    @Override
    public String getUserId() {
        return sessionId;
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
    public void loadSuccess(InquiryBean data) {
        inquiryStatus = (data == null ? InquiryStatus.INQUIRY_COMPLETE : data.getState());
        chatting =
                inquiryStatus == InquiryStatus.INQUIRY_COMPLETE || inquiryStatus == InquiryStatus.INQUIRY_UNPAID || inquiryStatus == InquiryStatus.INQUIRY_NOT_EVALUATED;
        if (inquiryStatus != InquiryStatus.INQUIRY_ING) {
            setRightImgVisibility(false);
        } else {
            setRightImgVisibility(true);
        }

        if (data != null) {
            orderType = data.getType();
            orderBean = data;
            orderId = orderBean.getId();
            calculateTime();
            referralState = data.getReferralState();
        }
        initStatus();
    }

    @Override
    public void acceptSuccess() {
        if (team) {
            mMessageView.addData(ImClient.createTipMessage(sessionId, SessionTypeEnum.Team,
                    "问诊开始，本次问诊可持续" + orderBean.getDuration() + "小时"));
        } else {
            mMessageView.addData(ImClient.createTipMessage(sessionId, SessionTypeEnum.P2P,
                    "问诊开始，本次问诊可持续" + orderBean.getDuration() + "小时"));
        }
        //        inquiryStatus = InquiryStatus.INQUIRY_ING;
        //        inquiryIng();
        //        calculateTime();
        //        setRightImgVisibility(true);
        EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.DIAGNOSE_ORDER_UPDATE).build());
        getController().getInquiryStatus();
    }

    @Override
    public void acceptFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void endSuccess() {
        inquiryStatus = InquiryStatus.INQUIRY_COMPLETE;
        EndInquiryAttachment endInquiryAttachment = new EndInquiryAttachment();
        endInquiryAttachment.setId(orderBean.getId());
        endInquiryAttachment.setSponsorId(ImClient.getUserInfoProvider().getAccount());
        endInquiryAttachment.setReceiverId(sessionId);
        endInquiryAttachment.setEndType(EndInquiryAttachment.DOCTOR_APPLY_END);

        if (team) {
            mMessageView.addData(ImClient.createEndInquiryMessage(sessionId, SessionTypeEnum.Team,
                    "[结束问诊消息]", endInquiryAttachment));
            mMessageView.addData(ImClient.createLocalTipMessage(sessionId, SessionTypeEnum.Team,
                    "此次问诊已结束"));
        } else {
            mMessageView.addData(ImClient.createEndInquiryMessage(sessionId, SessionTypeEnum.P2P,
                    "[结束问诊消息]", endInquiryAttachment));
            mMessageView.addData(ImClient.createLocalTipMessage(sessionId, SessionTypeEnum.P2P,
                    "此次问诊已结束"));
        }
        getController().getInquiryStatus();
        EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.DIAGNOSE_ORDER_UPDATE).build());
    }

    @Override
    public boolean isGetStatus() {
        return isGetStatus;
    }

    @Override
    public void stopReferralSuccess() {
        ToastUtil.showMessage(getContext(), "取消转诊成功");
        getController().getInquiryStatus();
    }

    /**
     * 计算时间
     */
    private void calculateTime() {
        long remainingTime;
        if (inquiryStatus == InquiryStatus.INQUIRY_WAIT || inquiryStatus == InquiryStatus.INQUIRY_REFERRAL_DOCTOR) {
            if (orderBean.getApplyTime() != null) {
                remainingTime =
                        CalculateTimeUtils.toLong(orderBean.getApplyTime()) + ((long) (orderBean.getDoctorAcceptTime() * 60 * 60 * 1000)) - System.currentTimeMillis();
            } else {
                remainingTime = (long) (orderBean.getDoctorAcceptTime() * 60 * 60 * 1000);
            }

        } else if (inquiryStatus == InquiryStatus.INQUIRY_ING || inquiryStatus == InquiryStatus.INQUIRY_APPLY_END) {
            remainingTime =
                    CalculateTimeUtils.toLong(orderBean.getBeginTime()) + (long) (orderBean.getDuration() * 60 * 60 * 1000L) - System.currentTimeMillis();
        } else {
            String tz = "GMT+8";
            TimeZone curTimeZone = TimeZone.getTimeZone(tz);
            Calendar calendar = Calendar.getInstance(curTimeZone);
            calendar.setTime(new Date());
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            long applyTime = calendar.getTimeInMillis();
            remainingTime = applyTime + 24 * 60 * 60 * 1000L - System.currentTimeMillis();
        }
        int day = 0;
        long dayMillisecond = 24 * 60 * 60 * 1000;
        if (remainingTime < 0) {
            remainingTime = 0;
        } else if (remainingTime > dayMillisecond) {
            day = (int) (remainingTime / dayMillisecond);
            remainingTime = remainingTime % dayMillisecond;
        }
        mHour = com.keydom.mianren.ih_doctor.utils.CalculateTimeUtils.hourDistance(remainingTime) + day * 24;
        mMin = com.keydom.mianren.ih_doctor.utils.CalculateTimeUtils.minDistance(remainingTime);
        if (inquiryStatus == InquiryStatus.INQUIRY_WAIT || inquiryStatus == InquiryStatus.INQUIRY_REFERRAL_DOCTOR) {
            visitingRemainingTime = mHour + "小时" + mMin + "分";
            SpannableStringBuilder ssb = new SpannableStringBuilder();
            ssb.append("请在");
            ssb.append(" ");
            SpannableString ss = new SpannableString(visitingRemainingTime);
            ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPrimary)), 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ssb.append(ss);
            ssb.append(" ");
            ssb.append("内接单，超时将自动退诊");
            mVisitingRemainingTimeTv.setText(ssb);
        } else if (inquiryStatus == InquiryStatus.INQUIRY_ING || inquiryStatus == InquiryStatus.INQUIRY_APPLY_END) {
            inquiryRemainingTime = mHour + "小时" + mMin + "分后问诊结束";
            mQuestionRemainingTimeTv.setText(inquiryRemainingTime);
        }
        timeDisposable = Flowable.intervalRange(1, remainingTime / 1000 / 60, 0, 1,
                TimeUnit.MINUTES)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(time -> computeTime());
    }

    /**
     * 倒计时计算
     */
    private void computeTime() {

        if (mMin < 0) {
            if (mHour > 0) {
                mHour--;
                mMin = 59;

            } else
                mMin = 0;

            if (mHour == 0 && mMin == 0) {
                // 倒计时结束
                if (!timeDisposable.isDisposed()) {
                    timeDisposable.dispose();
                }
            }
        }
        if (inquiryStatus == InquiryStatus.INQUIRY_WAIT || inquiryStatus == InquiryStatus.INQUIRY_REFERRAL_DOCTOR) {
            visitingRemainingTime = mHour + "小时" + mMin + "分";
            SpannableStringBuilder ssb = new SpannableStringBuilder();
            ssb.append("请在");
            ssb.append(" ");
            SpannableString ss = new SpannableString(visitingRemainingTime);
            ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPrimary)), 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ssb.append(ss);
            ssb.append(" ");
            ssb.append("内接单，超时将自动退诊");
            mVisitingRemainingTimeTv.setText(ssb);
        } else if (inquiryStatus == InquiryStatus.INQUIRY_ING || inquiryStatus == InquiryStatus.INQUIRY_APPLY_END) {
            inquiryRemainingTime = mHour + "小时" + mMin + "分后问诊结束";
            mQuestionRemainingTimeTv.setText(inquiryRemainingTime);
        }
        mMin--;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timeDisposable != null && !timeDisposable.isDisposed()) {
            timeDisposable.dispose();
        }
        EventBus.getDefault().unregister(this);
    }

    public void showNotAccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("未开通服务");
        builder.setMessage("您暂未开通该服务，无法使用这个功能！");
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.create().show();
    }


    FixHeightBottomSheetDialog mFixHeightBottomSheetDialog;

    /**
     * 用户随访表
     */
    private void showUserFollowUp() {

        if (null == mFixHeightBottomSheetDialog) {
            mFixHeightBottomSheetDialog = new FixHeightBottomSheetDialog(this);
            mFixHeightBottomSheetDialog.setCancelable(true);
            mFixHeightBottomSheetDialog.setCanceledOnTouchOutside(true);
            View view = LayoutInflater.from(this).inflate(R.layout.user_follow_up_dialog_layout,
                    null, false);
            mFixHeightBottomSheetDialog.setContentView(view);
            view.findViewById(R.id.user_follow_up_dialog_first_rl).setOnClickListener(view1 -> {
                UserFollowUpAttachment userFollowUpAttachment = new UserFollowUpAttachment();
                userFollowUpAttachment.setId("1");
                userFollowUpAttachment.setDoctorName("伍齐鸣");
                userFollowUpAttachment.setFileName("糖尿病随访管理表");
                userFollowUpAttachment.setUrl("https://www.baidu.com/");

                if (team) {
                    mMessageView.addData(ImClient.createUserFollowUpMessage(sessionId,
                            SessionTypeEnum.Team, "[随访表消息]", userFollowUpAttachment));
                } else {
                    mMessageView.addData(ImClient.createUserFollowUpMessage(sessionId,
                            SessionTypeEnum.P2P, "[随访表消息]", userFollowUpAttachment));
                }
                mFixHeightBottomSheetDialog.dismiss();
            });
            view.findViewById(R.id.user_follow_up_dialog_second_rl).setOnClickListener(view12 -> {
                UserFollowUpAttachment userFollowUpAttachment = new UserFollowUpAttachment();
                userFollowUpAttachment.setId("2");
                userFollowUpAttachment.setDoctorName("伍齐鸣");
                userFollowUpAttachment.setFileName("慢性肺炎随访管理表");
                userFollowUpAttachment.setUrl("https://www.baidu.com/");

                if (team) {
                    mMessageView.addData(ImClient.createUserFollowUpMessage(sessionId,
                            SessionTypeEnum.Team, "[随访表消息]", userFollowUpAttachment));
                } else {
                    mMessageView.addData(ImClient.createUserFollowUpMessage(sessionId,
                            SessionTypeEnum.P2P, "[随访表消息]", userFollowUpAttachment));
                }

                mFixHeightBottomSheetDialog.dismiss();
            });
            view.findViewById(R.id.user_follow_up_dialog_third_rl).setOnClickListener(view13 -> {
                UserFollowUpAttachment userFollowUpAttachment = new UserFollowUpAttachment();
                userFollowUpAttachment.setId("3");
                userFollowUpAttachment.setDoctorName("伍齐鸣");
                userFollowUpAttachment.setFileName("高血压随访管理表");
                userFollowUpAttachment.setUrl("https://www.baidu.com/");

                if (team) {
                    mMessageView.addData(ImClient.createUserFollowUpMessage(sessionId,
                            SessionTypeEnum.Team, "[随访表消息]", userFollowUpAttachment));
                } else {
                    mMessageView.addData(ImClient.createUserFollowUpMessage(sessionId,
                            SessionTypeEnum.P2P, "[随访表消息]", userFollowUpAttachment));
                }

                mFixHeightBottomSheetDialog.dismiss();
            });
        }
        mFixHeightBottomSheetDialog.show();
    }


}

