package com.keydom.ih_doctor.view.im_plugin;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.keydom.ih_common.event.VoiceInputEvent;
import com.keydom.ih_common.im.listener.IPluginModule;
import com.keydom.ih_common.im.widget.ImExtension;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_doctor.MyApplication;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.utils.JsonUtils;
import com.keydom.ih_doctor.view.CustomRecognizerDialog;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.functions.Consumer;

public class VoiceInputPlugin implements IPluginModule {

    private boolean permissionsResult;

    // 语音听写UI
    private CustomRecognizerDialog mIatDialog;


    /**
     * 初始化监听器。
     */
    private InitListener mInitListener = new InitListener() {

        @Override
        public void onInit(int code) {

            if (code != ErrorCode.SUCCESS) {
                Log.e("xunfei","初始化失败，错误码：" + code+",请点击网址https://www.xfyun.cn/document/error-code查询解决方案");
            }
        }
    };

    /**
     * 听写UI监听器
     */
    private RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {
        public void onResult(RecognizerResult results, boolean isLast) {
            String text = JsonUtils.handleXunFeiJson(results);
            if(!TextUtils.isEmpty(text)){
                EventBus.getDefault().post(new VoiceInputEvent(text));
            }

        }

        /**
         * 识别回调错误.
         */
        public void onError(SpeechError error) {
            ToastUtil.showMessage(MyApplication.mApplication,error.getPlainDescription(true));

        }

    };


    public VoiceInputPlugin(Context context) {
        mIatDialog = new CustomRecognizerDialog(context, mInitListener);
        mIatDialog.setListener(mRecognizerDialogListener);

    }

    @Override
    public Drawable obtainDrawable(Context context) {
        return ContextCompat.getDrawable(context, R.drawable.im_voice_toggle_selector);
    }

    @Override
    public String obtainTitle(Context context) {
        return "语音输入";
    }

    @Override
    public void onClick(AppCompatActivity activity, ImExtension extension) {
        if (requestCallPermissions(activity)) {
            if(mIatDialog.isShowing()){
                mIatDialog.dismiss();
            }
            mIatDialog.show();
            ToastUtil.showMessage(MyApplication.mApplication,"请开始说话…");
        } else {
            ToastUtil.showMessage(MyApplication.mApplication,"请开启录音需要的权限");

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @SuppressLint("CheckResult")
    private boolean requestCallPermissions(AppCompatActivity appCompatActivity) {
        String[] permissions = new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE};
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
