package com.keydom.ih_patient.activity.im;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
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
import com.keydom.ih_common.im.ImClient;
import com.keydom.ih_common.im.activity.TeamChatActivity;
import com.keydom.ih_common.im.config.ImConstants;
import com.keydom.ih_common.im.listener.IConversationBehaviorListener;
import com.keydom.ih_common.im.model.ImMessageConstant;
import com.keydom.ih_common.im.widget.plugin.EmojiPlugin;
import com.keydom.ih_common.utils.FloatPermissionManager;
import com.keydom.ih_common.utils.permissions.PermissionListener;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.view.im_plugin.VoiceInputPlugin;
import com.netease.nimlib.sdk.msg.constant.MsgDirectionEnum;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PatientTeamChatActivity extends TeamChatActivity {

    private VoiceInputPlugin mVoiceInputPlugin;

    private static boolean permissionsResult;


    @SuppressLint("CheckResult")
    private static boolean requestCallPermissions(AppCompatActivity appCompatActivity) {
        String[] permissions = new String[]{Manifest.permission.RECORD_AUDIO,
                Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.INTERNET, Manifest.permission.READ_PHONE_STATE, Manifest.permission.MODIFY_AUDIO_SETTINGS};
        boolean granted = true;
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(appCompatActivity, permission) != PackageManager.PERMISSION_GRANTED) {
                granted = false;
            }
        }

        if (granted) {
            permissionsResult = true;
        } else {
            Common.INSTANCE.getPermissions(appCompatActivity).requestPermissions(permissions, new PermissionListener() {
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
     *
     * @param context
     */
    public static void startTeamChat(Context context, String sessionId) {
        if (requestCallPermissions((AppCompatActivity) context) && FloatPermissionManager.INSTANCE.applyFloatWindow(context)) {
            Intent starter = new Intent(context, PatientTeamChatActivity.class);
            starter.putExtra(ImConstants.CALL_SESSION_ID, sessionId);
            ((Activity) context).startActivity(starter);
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

                if (message.getDirect() == MsgDirectionEnum.Out) {
                    Intent intent = new Intent("com.keydom.ih_patient.activity.user_info_operate.UserInfoOperateActivity");
                    intent.putExtra("type", "read_type");
                    startActivity(intent);
                }else {
                    NimUserInfo patientInfo = (NimUserInfo) ImClient.getUserInfoProvider().getUserInfo(message.getFromAccount());
                    if ((ImMessageConstant.DOCTOR).equals(patientInfo.getExtensionMap().get(ImConstants.CALL_USER_TYPE))){
                        Intent intent = new Intent("com.keydom.ih_patient.activity.my_doctor_or_nurse.DoctorOrNurseDetailActivity");
                        intent.putExtra("type", 0);
                        intent.putExtra("doctorCode", String.valueOf(patientInfo.getAccount()));
                        startActivity(intent);
                    }else
                        Toast.makeText(context,"暂不支持查看其他患者资料",Toast.LENGTH_SHORT).show();
                }

                return false;
            }

            @Override
            public boolean onMessageClick(Context context, View view, IMMessage message) {
                return false;
            }

            @Override
            public boolean onPayClick(Context context, View view, @android.support.annotation.Nullable IMMessage message) {
                return false;
            }

            @Override
            public boolean onPrescriptionClick(Context context, @android.support.annotation.Nullable IMMessage message) {
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
