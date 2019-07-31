package com.keydom.ih_common.im.widget;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.keydom.Common;
import com.keydom.ih_common.R;
import com.keydom.ih_common.im.ImClient;
import com.keydom.ih_common.im.config.ImConstants;
import com.keydom.ih_common.im.listener.OnTimeListener;
import com.keydom.ih_common.im.manager.AVChatSoundPlayer;
import com.keydom.ih_common.im.model.ImMessageConstant;
import com.keydom.ih_common.utils.FloatActionController;
import com.keydom.ih_common.widget.FloatLayout;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.avchat.AVChatManager;
import com.netease.nimlib.sdk.avchat.model.AVChatCommonEvent;
import com.netease.nimlib.sdk.avchat.model.AVChatSurfaceViewRenderer;

import static com.keydom.ih_common.im.ImClient.simpleAVChatStateObserver;

public class CallFloatBoxView {
    private static long mTime = 0;
    private static boolean isShow = false;
    private static Bundle mBundle = null;

    private static FloatLayout mFloatLayout = null;
    /**
     * 通话过程中，收到对方挂断电话
     */
    private static Observer<AVChatCommonEvent> callHangupObserver;

    public static void showFloatBox(Bundle bundle) {
        showFloatBox(bundle, null);
    }

    /**
     * 启动悬浮窗
     * <p>
     *
     * @param bundle
     * @param remoteVideo
     */
    public static void showFloatBox(Bundle bundle, AVChatSurfaceViewRenderer remoteVideo) {
        if (isShow) {
            return;
        }
        isShow = true;
        mBundle = bundle;
        mFloatLayout = new FloatLayout(Common.INSTANCE.getApplication());
        mFloatLayout.setOnClickListener(new FloatLayout.ForbidClickListener() {
            @Override
            public void forbidClick(View v) {
                onClickToResume();
            }
        });
        FrameLayout mView = (FrameLayout) LayoutInflater.from(Common.INSTANCE.getApplication()).inflate(R.layout.rc_voip_float_box, mFloatLayout, false);
        if (mFloatLayout != null) {
            mFloatLayout.addView(mView);
        }
        if (remoteVideo == null) {
            View view = LayoutInflater.from(Common.INSTANCE.getApplication()).inflate(R.layout.float_audio_layout, mView, false);
            final TextView timeTv = view.findViewById(R.id.time);
            ImClient.setOnTimeListener(new OnTimeListener() {
                @Override
                public void onTimeResult(long time) {
                    if (time >= 3600) {
                        timeTv.setText(String.format("%d:%02d:%02d", time / 3600, time % 3600 / 60, time % 60));
                    } else {
                        timeTv.setText(String.format("%02d:%02d", time % 3600 / 60, time % 60));
                    }
                }
            });
            mView.addView(view);
        } else {
            if (remoteVideo.getParent() != null) {
                ((ViewGroup) remoteVideo.getParent()).removeView(remoteVideo);
            }
            mView.addView(remoteVideo);
        }

        FloatActionController.INSTANCE.startFloatWindow(Common.INSTANCE.getApplication(), mFloatLayout, Common.INSTANCE.getApplication().getResources().getDisplayMetrics().widthPixels);
        callHangupObserver = new Observer<AVChatCommonEvent>() {
            @Override
            public void onEvent(AVChatCommonEvent avChatHangUpInfo) {
                Log.e("CallFloatBoxView", " avChatHangUpInfo:" + avChatHangUpInfo.getEvent().name());
                if (Common.INSTANCE.getAVChatData() != null && Common.INSTANCE.getAVChatData().getChatId() == avChatHangUpInfo.getChatId()) {
                    AVChatSoundPlayer.instance().stop();
                    AVChatManager.getInstance().disableRtc();
//                    cancelCallingNotifier();
//                    // 如果是incoming call主叫方挂断，那么通知栏有通知
//                    if (mIsInComingCall && !isCallEstablished) {
//                        activeMissCallNotifier();
//                    }
                    if (mFloatLayout != null) {
                        FloatActionController.INSTANCE.stopFloatWindow(mFloatLayout);
                        isShow = false;
                        mFloatLayout = null;
                        mTime = 0;
                    }
//                    NotificationUtil.clearNotification(Common.getApplication(), ImConstants.CALL_NOTIFICATION_ID)
//                    RongCallClient.getInstance().setVoIPCallListener(ImCallProxy.getInstance())
                }

            }
        };
        AVChatManager.getInstance().observeHangUpNotification(callHangupObserver, true);
//        EventBus.getDefault().post(new FloatBoxEvent());
    }

    public static void hideFloatBox() {
        AVChatManager.getInstance().observeHangUpNotification(callHangupObserver, false);
        AVChatManager.getInstance().observeAVChatState(simpleAVChatStateObserver, true);
        if (isShow && mFloatLayout != null) {
            FloatActionController.INSTANCE.stopFloatWindow(mFloatLayout);
            isShow = false;
            mFloatLayout = null;
            mTime = 0;
            mBundle = null;
        }
    }

    private static void onClickToResume() {
        if (mBundle == null) {
            return;
        }
        Intent intent = new Intent(mBundle.getString("action"));
        intent.setPackage(Common.INSTANCE.getApplication().getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtras(mBundle);
        intent.putExtra(ImConstants.CALL_CALL_ACTION, ImMessageConstant.ACTION_RESUME_CALL);
        Common.INSTANCE.getApplication().startActivity(intent);
        mBundle = null;
    }

    public static boolean isShow() {
        return isShow;
    }
}
