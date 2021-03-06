package com.keydom.ih_common.im.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.keydom.ih_common.R;
import com.keydom.ih_common.base.BaseActivity;
import com.keydom.ih_common.im.ImClient;
import com.keydom.ih_common.im.config.ImConstants;
import com.keydom.ih_common.im.listener.IConversationBehaviorListener;
import com.keydom.ih_common.im.model.ImMessageConstant;
import com.keydom.ih_common.im.widget.ImMessageView;
import com.keydom.ih_common.im.widget.plugin.EmojiPlugin;
import com.keydom.ih_common.utils.SharePreferenceManager;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.constant.MsgDirectionEnum;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.team.model.Team;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;

import org.jetbrains.annotations.Nullable;

public class TeamChatActivity extends BaseActivity {

    protected ImMessageView mMessageView;

    protected String teamName = "";

    @Override
    public int getLayoutRes() {
        return R.layout.activity_team_chat;
    }

    @Override
    protected void onResume() {
        super.onResume();
        NIMClient.getService(MsgService.class).setChattingAccount(MsgService.MSG_CHATTING_ACCOUNT_ALL, SessionTypeEnum.None);
    }

    @Override
    public void onPause() {
        super.onPause();
        NIMClient.getService(MsgService.class).setChattingAccount(MsgService.MSG_CHATTING_ACCOUNT_NONE, SessionTypeEnum.None);
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
                if ("com.keydom.mianren.ih_patient".equals(context.getPackageName())) {
                    if (message.getDirect() == MsgDirectionEnum.Out) {
                        Intent intent = new Intent("com.keydom.mianren.ih_patient.activity" +
                                ".user_info_operate.UserInfoOperateActivity");
                        intent.putExtra("type", "read_type");
                        startActivity(intent);
                    } else {
                        NimUserInfo patientInfo =
                                (NimUserInfo) ImClient.getUserInfoProvider().getUserInfo(message.getFromAccount());
                        if ((ImMessageConstant.DOCTOR).equals(patientInfo.getExtensionMap().get(ImConstants.CALL_USER_TYPE))) {
                            Intent intent = new Intent("com.keydom.mianren.ih_patient.activity" +
                                    ".my_doctor_or_nurse.DoctorOrNurseDetailActivity");
                            intent.putExtra("type", 0);
                            intent.putExtra("doctorCode", String.valueOf(patientInfo.getAccount()));
                            startActivity(intent);
                        } else
                            Toast.makeText(context, "????????????????????????????????????", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    NimUserInfo patientInfo =
                            (NimUserInfo) ImClient.getUserInfoProvider().getUserInfo(message.getFromAccount());
                    NimUserInfo currentUserInfo =
                            (NimUserInfo) ImClient.getUserInfoProvider().getUserInfo(SharePreferenceManager.getUserCode());
                    if ((ImMessageConstant.DOCTOR).equals(currentUserInfo.getExtensionMap().get(ImConstants.CALL_USER_TYPE))) {
                        if ((ImMessageConstant.PATIENT).equals(patientInfo.getExtensionMap().get(ImConstants.CALL_USER_TYPE))) {
                            Intent intent = new Intent("com.keydom.mianren.ih_doctor" +
                                    ".PatientDatumActivity");
                            intent.putExtra("data", patientInfo.getAccount());
                            startActivity(intent);
                        }
                        if ((ImMessageConstant.DOCTOR).equals(patientInfo.getExtensionMap().get(ImConstants.CALL_USER_TYPE))) {
                            Intent intent = new Intent("com.keydom.mianren.ih_doctor" +
                                    ".DoctorOrNurseDetailActivity");
                            intent.putExtra("doctorCode", String.valueOf(patientInfo.getAccount()));
                            startActivity(intent);
                        }
                    }
                }

                return false;
            }

            @Override
            public boolean onMessageClick(Context context, View view, IMMessage message) {
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
    }

    private void initView() {
        Uri data = getIntent().getData();
        if (data != null) {
            String sessionId = data.getQueryParameter(ImConstants.CALL_SESSION_ID);
            mMessageView.setMessageInfo(sessionId, SessionTypeEnum.Team);
            Team team = ImClient.getTeamProvider().getTeamById(sessionId);
            if (team != null) {
                teamName = team.getName();
            } else {
                Toast.makeText(this, "????????????", Toast.LENGTH_SHORT).show();
                finish();
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mMessageView.onActivityPluginResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

}
