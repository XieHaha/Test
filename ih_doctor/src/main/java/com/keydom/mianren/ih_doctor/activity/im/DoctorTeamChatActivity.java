package com.keydom.mianren.ih_doctor.activity.im;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.keydom.Common;
import com.keydom.ih_common.activity.DiagnoseOrderDetailActivity;
import com.keydom.ih_common.activity.HandleProposeAcitivity;
import com.keydom.ih_common.im.ImClient;
import com.keydom.ih_common.im.activity.TeamChatActivity;
import com.keydom.ih_common.im.config.ImConstants;
import com.keydom.ih_common.im.listener.IConversationBehaviorListener;
import com.keydom.ih_common.im.model.ImMessageConstant;
import com.keydom.ih_common.im.model.custom.ConsultationResultAttachment;
import com.keydom.ih_common.im.model.custom.DisposalAdviceAttachment;
import com.keydom.ih_common.im.model.custom.ExaminationAttachment;
import com.keydom.ih_common.im.model.custom.InquiryAttachment;
import com.keydom.ih_common.im.model.custom.InspectionAttachment;
import com.keydom.ih_common.im.model.custom.ReferralApplyAttachment;
import com.keydom.ih_common.im.model.custom.ReferralDoctorAttachment;
import com.keydom.ih_common.im.model.custom.TriageOrderAttachment;
import com.keydom.ih_common.im.model.custom.UserFollowUpAttachment;
import com.keydom.ih_common.im.widget.plugin.EmojiPlugin;
import com.keydom.ih_common.utils.FloatPermissionManager;
import com.keydom.ih_common.utils.SharePreferenceManager;
import com.keydom.ih_common.utils.permissions.PermissionListener;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.common_document.CommonDocumentActivity;
import com.keydom.mianren.ih_doctor.activity.doctor_cooperation.DianoseCaseDetailActivity;
import com.keydom.mianren.ih_doctor.activity.my_doctor_or_nurse.DoctorOrNurseDetailActivity;
import com.keydom.mianren.ih_doctor.activity.patient_manage.PatientDatumActivity;
import com.keydom.mianren.ih_doctor.view.im_plugin.VoiceInputPlugin;
import com.netease.nimlib.sdk.msg.constant.MsgTypeEnum;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DoctorTeamChatActivity extends TeamChatActivity {

    private VoiceInputPlugin mVoiceInputPlugin;

    private static boolean permissionsResult;


    @SuppressLint("CheckResult")
    private static boolean requestCallPermissions(AppCompatActivity appCompatActivity) {
        String[] permissions = new String[]{Manifest.permission.RECORD_AUDIO,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.INTERNET, Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.MODIFY_AUDIO_SETTINGS};
        boolean granted = true;
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(appCompatActivity, permission) != PackageManager.PERMISSION_GRANTED) {
                granted = false;
            }
        }

        if (granted) {
            permissionsResult = true;
        } else {
            Common.INSTANCE.getPermissions(appCompatActivity).requestPermissions(permissions,
                    new PermissionListener() {
                        @Override
                        public void onGranted() {

                        }

                        @Override
                        public void onDenied(@NotNull List<String> deniedPermissions) {

                        }
                    });

        }
        return permissionsResult;
    }


    /**
     * 跳到群聊
     */
    public static void startTeamChat(Context context, String sessionId) {
        if (requestCallPermissions((AppCompatActivity) context) && FloatPermissionManager.INSTANCE.applyFloatWindow(context)) {
            Intent starter = new Intent(context, DoctorTeamChatActivity.class);
            starter.putExtra(ImConstants.CALL_SESSION_ID, sessionId);
            context.startActivity(starter);
        }
    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mMessageView = findViewById(R.id.message_view);
        mMessageView.addPlugin(new EmojiPlugin());
        getLifecycle().addObserver(mMessageView);
        initView();
        setTitle(teamName);
        initListener();
    }


    private void initListener() {
        mMessageView.setOnConversationBehaviorListener(new IConversationBehaviorListener() {
            @Override
            public boolean onUserPortraitClick(Context context, IMMessage message) {

                NimUserInfo patientInfo =
                        (NimUserInfo) ImClient.getUserInfoProvider().getUserInfo(message.getFromAccount());
                NimUserInfo currentUserInfo =
                        (NimUserInfo) ImClient.getUserInfoProvider().getUserInfo(SharePreferenceManager.getUserCode());
                if ((ImMessageConstant.DOCTOR).equals(currentUserInfo.getExtensionMap().get(ImConstants.CALL_USER_TYPE))) {
                    if ((ImMessageConstant.PATIENT).equals(patientInfo.getExtensionMap().get(ImConstants.CALL_USER_TYPE))) {
                        Intent intent = new Intent(DoctorTeamChatActivity.this,
                                PatientDatumActivity.class);
                        intent.putExtra("data", patientInfo.getAccount());
                        startActivity(intent);
                    }
                    if ((ImMessageConstant.DOCTOR).equals(patientInfo.getExtensionMap().get(ImConstants.CALL_USER_TYPE))) {
                        Intent intent = new Intent(DoctorTeamChatActivity.this,
                                DoctorOrNurseDetailActivity.class);
                        intent.putExtra("doctorCode", String.valueOf(patientInfo.getAccount()));
                        startActivity(intent);
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
                    } else if (message.getAttachment() instanceof TriageOrderAttachment) //分诊单
                    {

                    } else if (message.getAttachment() instanceof ExaminationAttachment) {//检查单
                        //                        ApiRequest.INSTANCE.request(HttpService
                        //                        .INSTANCE.createService(DiagnoseApiService
                        //                        .class).getInspectDetail((
                        //                        (ExaminationAttachment) message.getAttachment()
                        //                        ).getId()), new
                        //                        HttpSubscriber<CheckItemListBean>(getContext(),
                        //                        getDisposable(), false) {
                        //                            @Override
                        //                            public void requestComplete(@Nullable
                        //                            CheckItemListBean data) {
                        //                                if (data != null) {
                        //                                    CheckOrderDetailActivity
                        //                                    .startInspactOrder(context,
                        //                                            ((ExaminationAttachment)
                        //                                            message.getAttachment())
                        //                                            .getId(), orderBean);
                        //                                } else {
                        //                                    ToastUtil.showMessage(context,
                        //                                    "检查报告单不存在");
                        //                                }
                        //                            }
                        //
                        //                            @Override
                        //                            public boolean requestError(@NotNull
                        //                            ApiException exception, int code
                        //                                    , @NotNull String msg) {
                        //                                ToastUtil.showMessage(context, msg);
                        //                                return super.requestError(exception,
                        //                                code, msg);
                        //                            }
                        //                        });

                    } else if (message.getAttachment() instanceof InspectionAttachment) {//检验单
                        //                        ApiRequest.INSTANCE.request(HttpService
                        //                        .INSTANCE.createService(DiagnoseApiService
                        //                        .class).getCheckoutDetail((
                        //                        (InspectionAttachment) message.getAttachment())
                        //                        .getId()), new
                        //                        HttpSubscriber<CheckItemListBean>(getContext(),
                        //                        getDisposable(), false) {
                        //                            @Override
                        //                            public void requestComplete(@Nullable
                        //                            CheckItemListBean data) {
                        //                                if (data != null) {
                        //                                    CheckOrderDetailActivity
                        //                                    .startTestOrder(context,
                        //                                            ((InspectionAttachment)
                        //                                            message.getAttachment())
                        //                                            .getId(), orderBean);
                        //                                } else {
                        //                                    ToastUtil.showMessage(context,
                        //                                    "检验报告单不存在");
                        //                                }
                        //                            }
                        //
                        //                            @Override
                        //                            public boolean requestError(@NotNull
                        //                            ApiException exception, int code
                        //                                    , @NotNull String msg) {
                        //                                ToastUtil.showMessage(context, msg);
                        //                                return super.requestError(exception,
                        //                                code, msg);
                        //                            }
                        //                        });

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
                        HandleProposeAcitivity.start(DoctorTeamChatActivity.this,
                                ((DisposalAdviceAttachment) message.getAttachment()).getContent());

                    }
                    //随访表
                    else if (message.getAttachment() instanceof UserFollowUpAttachment) {
                        UserFollowUpAttachment userFollowUpAttachment =
                                (UserFollowUpAttachment) message.getAttachment();
                        CommonDocumentActivity.start(DoctorTeamChatActivity.this,
                                userFollowUpAttachment.getFileName(),
                                userFollowUpAttachment.getUrl());
                    }
                }
                return false;
            }

            @Override
            public boolean onPayClick(Context context, View view,
                                      @android.support.annotation.Nullable IMMessage message) {
                return false;
            }

            @Override
            public boolean onPrescriptionClick(Context context,
                                               @android.support.annotation.Nullable IMMessage message) {
                return false;
            }
        });
        mVoiceInputPlugin = new VoiceInputPlugin(this);
        mMessageView.addPlugin(mVoiceInputPlugin);
    }

    private void initView() {
        String sessionID = getIntent().getStringExtra(ImConstants.CALL_SESSION_ID);
        if (!TextUtils.isEmpty(sessionID)) {
            mMessageView.setMessageInfo(sessionID, SessionTypeEnum.Team);
            if (ImClient.getTeamProvider().getTeamById(sessionID) != null) {
                teamName = ImClient.getTeamProvider().getTeamById(sessionID).getName();
            } else {
                Toast.makeText(this, "群不存在", Toast.LENGTH_SHORT).show();
                finish();
            }

        }
    }
}
