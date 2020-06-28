package com.keydom.ih_common.im.widget.plugin;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.keydom.ih_common.R;
import com.keydom.ih_common.im.ImClient;
import com.keydom.ih_common.im.listener.IPluginModule;
import com.keydom.ih_common.im.widget.ImExtension;
import com.keydom.ih_common.utils.FloatPermissionManager;
import com.netease.nimlib.sdk.avchat.constant.AVChatType;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

public class VideoPlugin implements IPluginModule {
    private Context context;

    private boolean permissionsResult;

    private boolean team;
    private String teamId;

    public VideoPlugin(Context context) {
        this.context = context;
    }

    public void setTeam(boolean team) {
        this.team = team;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    @Override
    public Drawable obtainDrawable(Context context) {
        return ContextCompat.getDrawable(context, R.mipmap.im_plugin_video);
    }

    @Override
    public String obtainTitle(Context context) {
        return "视频通话";
    }

    @Override
    public void onClick(AppCompatActivity activity, ImExtension extension) {
        if (requestCallPermissions(activity) && FloatPermissionManager.INSTANCE.applyFloatWindow(activity)) {
            if (team) {
                //                Intent intent = new Intent(activity, ContactSelectActivity.class);
                //                intent.putExtra("teamId", teamId);
                //                activity.startActivityForResult(intent, 1000);
                if (onClickCallback != null) {
                    onClickCallback.callback();
                }
            } else {
                ImClient.startAVChatCall(activity, extension.accountId,
                        AVChatType.VIDEO.getValue());
            }
        }
    }

    private OnClickCallback onClickCallback;

    public void setOnClickCallback(OnClickCallback onClickCallback) {
        this.onClickCallback = onClickCallback;
    }

    public interface OnClickCallback {
        void callback();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    @SuppressLint("CheckResult")
    private boolean requestCallPermissions(AppCompatActivity appCompatActivity) {
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
        if (!granted) {
            RxPermissions rxPermissions = new RxPermissions(appCompatActivity);
            rxPermissions.request(permissions)
                    .subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean granted) throws Exception {
                            permissionsResult = granted;
                        }
                    });
        } else {
            permissionsResult = true;
        }
        return permissionsResult;
    }
}
