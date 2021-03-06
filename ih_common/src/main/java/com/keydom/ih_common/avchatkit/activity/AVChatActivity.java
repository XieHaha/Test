package com.keydom.ih_common.avchatkit.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.keydom.ih_common.R;
import com.keydom.ih_common.avchatkit.AVChatKit;
import com.keydom.ih_common.avchatkit.common.activity.UI;
import com.keydom.ih_common.avchatkit.common.log.LogUtil;
import com.keydom.ih_common.avchatkit.constant.AVChatExitCode;
import com.keydom.ih_common.avchatkit.controll.AVChatController;
import com.keydom.ih_common.avchatkit.controll.AVChatSoundPlayer;
import com.keydom.ih_common.avchatkit.module.AVChatTimeoutObserver;
import com.keydom.ih_common.avchatkit.module.AVSwitchListener;
import com.keydom.ih_common.avchatkit.notification.AVChatNotification;
import com.keydom.ih_common.avchatkit.receiver.PhoneCallStateObserver;
import com.keydom.ih_common.avchatkit.ui.AVChatAudioUI;
import com.keydom.ih_common.avchatkit.ui.AVChatVideoUI;
import com.keydom.ih_common.im.listener.observer.SimpleAVChatStateObserver;
import com.keydom.ih_common.im.profile.AVChatProfile;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.StatusCode;
import com.netease.nimlib.sdk.auth.AuthServiceObserver;
import com.netease.nimlib.sdk.auth.ClientType;
import com.netease.nimlib.sdk.avchat.AVChatManager;
import com.netease.nimlib.sdk.avchat.constant.AVChatControlCommand;
import com.netease.nimlib.sdk.avchat.constant.AVChatEventType;
import com.netease.nimlib.sdk.avchat.constant.AVChatType;
import com.netease.nimlib.sdk.avchat.model.AVChatAudioFrame;
import com.netease.nimlib.sdk.avchat.model.AVChatCalleeAckEvent;
import com.netease.nimlib.sdk.avchat.model.AVChatCommonEvent;
import com.netease.nimlib.sdk.avchat.model.AVChatControlEvent;
import com.netease.nimlib.sdk.avchat.model.AVChatData;
import com.netease.nimlib.sdk.avchat.model.AVChatOnlineAckEvent;
import com.netease.nimlib.sdk.avchat.model.AVChatVideoFrame;

/**
 * ??????????????????
 * 1????????????
 * 2?????????????????????
 * 3?????????
 * 4??????????????????
 * 5??????????????????
 * 6???faceU
 * Created by winnie on 2017/12/10.
 */

public class AVChatActivity extends UI implements AVChatVideoUI.TouchZoneCallback,
        AVSwitchListener {
    // constant
    private static final String TAG = "AVChatActivity";
    private static final String KEY_IN_CALLING = "KEY_IN_CALLING";
    private static final String KEY_ACCOUNT = "KEY_ACCOUNT";
    private static final String KEY_DISPLAY_NAME = "KEY_DISPLAY_NAME";
    private static final String KEY_CALL_TYPE = "KEY_CALL_TYPE";
    private static final String KEY_SOURCE = "source";
    private static final String KEY_CALL_CONFIG = "KEY_CALL_CONFIG";
    public static final String INTENT_ACTION_AVCHAT = "INTENT_ACTION_AVCHAT";

    public static final int FROM_BROADCASTRECEIVER = 0; // ????????????
    public static final int FROM_INTERNAL = 1; // ???????????????
    public static final int FROM_UNKNOWN = -1; // ???????????????

    // view
    private View root;
    private View videoRoot;
    private View audioRoot;
    private View surfaceRoot;

    // state
    private static boolean needFinish = true; // ???????????????????????????????????????home???????????????????????????????????????????????????????????????finish
    private boolean mIsInComingCall = false;// is incoming call or outgoing call
    private boolean isCallEstablished = false; // ??????????????????
    private boolean isUserFinish = false;
    private boolean hasOnPause = false; // ?????????????????????

    // data
    private AVChatData avChatData; // config for connect video server
    private int state; // calltype ???????????????
    private String receiverId; // ?????????account
    private String displayName; // ?????????????????????

    private AVChatController avChatController;
    private AVChatAudioUI avChatAudioUI; // ????????????
    private AVChatVideoUI avChatVideoUI; // ????????????

    // face unity
    //    private FaceU faceU;

    // notification
    private AVChatNotification notifier;

    // ????????????
    public static void outgoingCall(Context context, String account, String displayName,
                                    int callType, int source) {
        needFinish = false;
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClass(context, AVChatActivity.class);
        intent.putExtra(KEY_ACCOUNT, account);
        intent.putExtra(KEY_DISPLAY_NAME, displayName);
        intent.putExtra(KEY_IN_CALLING, false);
        intent.putExtra(KEY_CALL_TYPE, callType);
        intent.putExtra(KEY_SOURCE, source);
        context.startActivity(intent);
    }

    // ????????????
    public static void incomingCall(Context context, AVChatData config, String displayName,
                                    int source) {
        needFinish = false;
        Intent intent = new Intent();
        intent.setClass(context, AVChatActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(KEY_CALL_CONFIG, config);
        intent.putExtra(KEY_DISPLAY_NAME, displayName);
        intent.putExtra(KEY_IN_CALLING, true);
        intent.putExtra(KEY_SOURCE, source);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ???????????????????????????????????????home???????????????????????????????????????????????????????????????finish
        if (needFinish) {
            finish();
            return;
        }

        // ?????????????????????timeout??????
        AVChatProfile.getInstance().activityLaunched();

        dismissKeyguard();

        root = LayoutInflater.from(this).inflate(R.layout.avchat_activity, null);
        setContentView(root);

        parseIntent();

        initData();

        showUI();

        registerObserves(true);

        notifier = new AVChatNotification(this);
        notifier.init(receiverId != null ? receiverId : avChatData.getAccount(), displayName);

        // face unity
//        initFaceU();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cancelCallingNotifier();

        if (hasOnPause) {
            avChatVideoUI.onResume();
            avChatController.resumeVideo();
            hasOnPause = false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        avChatController.pauseVideo();
        hasOnPause = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        activeCallingNotifier();
    }


    @Override
    public void onBackPressed() {
        // ???????????????
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //??????????????????????????????????????????????????????Note 4X????????????????????????????????????????????????????????????????????????
        try {
            manualHangUp(AVChatExitCode.HANGUP);
        } catch (Exception e) {

        }

        if (avChatAudioUI != null) {
            avChatAudioUI.onDestroy();
        }

        if (avChatVideoUI != null) {
            avChatVideoUI.onDestroy();
        }

        registerObserves(false);
        AVChatProfile.getInstance().setAVChatting(false);
        cancelCallingNotifier();
//        destroyFaceU();
        needFinish = true;
    }

    // ????????????flag?????????????????????/????????????????????????
    private void dismissKeyguard() {
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
        );
    }

    /**
     * ************************ ????????? ***************************
     */

    private void parseIntent() {
        mIsInComingCall = getIntent().getBooleanExtra(KEY_IN_CALLING, false);
        displayName = getIntent().getStringExtra(KEY_DISPLAY_NAME);
        switch (getIntent().getIntExtra(KEY_SOURCE, FROM_UNKNOWN)) {
            case FROM_BROADCASTRECEIVER: // incoming call
                avChatData = (AVChatData) getIntent().getSerializableExtra(KEY_CALL_CONFIG);
                state = avChatData.getChatType().getValue();
                break;
            case FROM_INTERNAL: // outgoing call
                receiverId = getIntent().getStringExtra(KEY_ACCOUNT);
                state = getIntent().getIntExtra(KEY_CALL_TYPE, -1);
                break;
            default:
                break;
        }
    }

    private void initData() {
        avChatController = new AVChatController(this, avChatData);
        avChatAudioUI = new AVChatAudioUI(this, root, displayName, avChatController, this);
        avChatVideoUI = new AVChatVideoUI(this, root, avChatData, displayName, avChatController,
                this, this);
    }


    private void registerObserves(boolean register) {
        AVChatManager.getInstance().observeAVChatState(avchatStateObserver, register);
        AVChatManager.getInstance().observeHangUpNotification(callHangupObserver, register);
        AVChatManager.getInstance().observeCalleeAckNotification(callAckObserver, register);
        AVChatManager.getInstance().observeControlNotification(callControlObserver, register);
        AVChatTimeoutObserver.getInstance().observeTimeoutNotification(timeoutObserver, register,
                mIsInComingCall);
        AVChatManager.getInstance().observeOnlineAckNotification(onlineAckObserver, register);
        PhoneCallStateObserver.getInstance().observeAutoHangUpForLocalPhone(autoHangUpForLocalPhoneObserver, register);
        //????????????UI?????????????????????????????????UI??????onKickOut??????
        NIMClient.getService(AuthServiceObserver.class).observeOnlineStatus(userStatusObserver,
                register);
    }

    /**
     * ************************ ??????????????????????????? ***************************
     */

    private void showUI() {
        audioRoot = root.findViewById(R.id.avchat_audio_layout);
        videoRoot = root.findViewById(R.id.avchat_video_layout);
        surfaceRoot = root.findViewById(R.id.avchat_surface_layout);
        if (state == AVChatType.AUDIO.getValue()) {
            // ??????
            audioRoot.setVisibility(View.VISIBLE);
            videoRoot.setVisibility(View.GONE);
            surfaceRoot.setVisibility(View.GONE);
            if (mIsInComingCall) {
                // ??????
                AVChatSoundPlayer.instance().play(AVChatSoundPlayer.RingerTypeEnum.RING);
                avChatAudioUI.showIncomingCall(avChatData);
            } else {
                // ??????
                AVChatSoundPlayer.instance().play(AVChatSoundPlayer.RingerTypeEnum.CONNECTING);
                avChatAudioUI.doOutGoingCall(receiverId);
            }
        } else {
            // ??????
            audioRoot.setVisibility(View.GONE);
            videoRoot.setVisibility(View.VISIBLE);
            surfaceRoot.setVisibility(View.VISIBLE);
            if (mIsInComingCall) {
                // ??????
                AVChatSoundPlayer.instance().play(AVChatSoundPlayer.RingerTypeEnum.RING);
                avChatVideoUI.showIncomingCall(avChatData);
            } else {
                // ??????
                AVChatSoundPlayer.instance().play(AVChatSoundPlayer.RingerTypeEnum.CONNECTING);
                avChatVideoUI.doOutgoingCall(receiverId);
            }
        }

    }

    /**
     * ****************************** ????????? **********************************
     */

    // ????????????????????????
    private SimpleAVChatStateObserver avchatStateObserver = new SimpleAVChatStateObserver() {
        @Override
        public void onAVRecordingCompletion(String account, String filePath) {
            if (account != null && filePath != null && filePath.length() > 0) {
                String msg = "????????????????????????, " + "?????????" + account + " ???????????????????????????" + filePath;
                Toast.makeText(AVChatActivity.this, msg, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(AVChatActivity.this, "???????????????.", Toast.LENGTH_SHORT).show();
            }
            if (state == AVChatType.VIDEO.getValue()) {
                avChatVideoUI.resetRecordTip();
            } else {
                avChatAudioUI.resetRecordTip();
            }
        }

        @Override
        public void onAudioRecordingCompletion(String filePath) {
            if (filePath != null && filePath.length() > 0) {
                String msg = "?????????????????????, ???????????????????????????" + filePath;
                Toast.makeText(AVChatActivity.this, msg, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(AVChatActivity.this, "???????????????.", Toast.LENGTH_SHORT).show();
            }
            if (state == AVChatType.AUDIO.getValue()) {
                avChatAudioUI.resetRecordTip();
            } else {
                avChatVideoUI.resetRecordTip();
            }
        }

        @Override
        public void onLowStorageSpaceWarning(long availableSize) {
            if (state == AVChatType.VIDEO.getValue()) {
                avChatVideoUI.showRecordWarning();
            } else {
                avChatAudioUI.showRecordWarning();
            }
        }

        @Override
        public void onJoinedChannel(int code, String audioFile, String videoFile, int i) {
            LogUtil.d(TAG, "audioFile -> " + audioFile + " videoFile -> " + videoFile);
            handleWithConnectServerResult(code);
        }

        @Override
        public void onUserJoined(String account) {
            LogUtil.d(TAG, "onUserJoin -> " + account);
            if (state == AVChatType.VIDEO.getValue()) {
                avChatVideoUI.initLargeSurfaceView(account);
            }
        }

        @Override
        public void onUserLeave(String account, int event) {
            LogUtil.d(TAG, "onUserLeave -> " + account);
            manualHangUp(AVChatExitCode.HANGUP);
            finish();
        }

        @Override
        public void onCallEstablished() {
            LogUtil.d(TAG, "onCallEstablished");
            //??????????????????
            AVChatTimeoutObserver.getInstance().observeTimeoutNotification(timeoutObserver, false
                    , mIsInComingCall);
            if (avChatController.getTimeBase() == 0)
                avChatController.setTimeBase(SystemClock.elapsedRealtime());

            if (state == AVChatType.AUDIO.getValue()) {
                avChatAudioUI.showAudioInitLayout();
            } else {
                // ??????????????????????????????????????????????????????????????????????????????
                avChatVideoUI.initSmallSurfaceView(AVChatKit.getAccount());
                avChatVideoUI.showVideoInitLayout();
            }
            isCallEstablished = true;
        }

        @Override
        public boolean onVideoFrameFilter(AVChatVideoFrame frame, boolean maybeDualInput) {
            //            if (faceU != null) {
            //                faceU.effect(frame.data, frame.width, frame.height, FaceU
            //                .VIDEO_FRAME_FORMAT.I420);
            //            }

            return true;
        }

        @Override
        public boolean onAudioFrameFilter(AVChatAudioFrame frame) {
            return true;
        }

    };

    // ??????????????????????????????????????????
    Observer<AVChatCommonEvent> callHangupObserver = new Observer<AVChatCommonEvent>() {
        @Override
        public void onEvent(AVChatCommonEvent avChatHangUpInfo) {
            avChatData = avChatController.getAvChatData();
            if (avChatData != null && avChatData.getChatId() == avChatHangUpInfo.getChatId()) {
                hangUpByOther(AVChatExitCode.HANGUP);
                cancelCallingNotifier();
                // ?????????incoming call??????????????????????????????????????????
                if (mIsInComingCall && !isCallEstablished) {
                    activeMissCallNotifier();
                }
            }

        }
    };

    // ?????????????????????????????????????????????????????????
    Observer<AVChatCalleeAckEvent> callAckObserver = new Observer<AVChatCalleeAckEvent>() {
        @Override
        public void onEvent(AVChatCalleeAckEvent ackInfo) {
            AVChatData info = avChatController.getAvChatData();
            if (info != null && info.getChatId() == ackInfo.getChatId()) {
                if (ackInfo.getEvent() == AVChatEventType.CALLEE_ACK_BUSY) {
                    hangUpByOther(AVChatExitCode.PEER_BUSY);
                } else if (ackInfo.getEvent() == AVChatEventType.CALLEE_ACK_REJECT) {
                    hangUpByOther(AVChatExitCode.REJECT);
                } else if (ackInfo.getEvent() == AVChatEventType.CALLEE_ACK_AGREE) {
                    AVChatSoundPlayer.instance().stop();
                    avChatController.isCallEstablish.set(true);
                }
            }
        }
    };

    Observer<Integer> timeoutObserver = new Observer<Integer>() {
        @Override
        public void onEvent(Integer integer) {
            manualHangUp(AVChatExitCode.CANCEL);
            // ??????????????????????????????
            if (mIsInComingCall) {
                activeMissCallNotifier();
            }
            finish();
        }
    };

    // ?????????????????????????????????, ???????????????????????????
    Observer<AVChatControlEvent> callControlObserver = new Observer<AVChatControlEvent>() {
        @Override
        public void onEvent(AVChatControlEvent netCallControlNotification) {
            handleCallControl(netCallControlNotification);
        }
    };

    // ?????????????????????????????????????????????????????????
    private void handleCallControl(AVChatControlEvent notification) {
        if (AVChatManager.getInstance().getCurrentChatId() != notification.getChatId()) {
            return;
        }
        switch (notification.getControlCommand()) {
            case AVChatControlCommand.SWITCH_AUDIO_TO_VIDEO:
                incomingAudioToVideo();
                break;
            case AVChatControlCommand.SWITCH_AUDIO_TO_VIDEO_AGREE:
                // ???????????????????????????
                state = AVChatType.VIDEO.getValue();
                avChatVideoUI.onAudioToVideoAgree(notification.getAccount());
                break;
            case AVChatControlCommand.SWITCH_AUDIO_TO_VIDEO_REJECT:
                rejectAudioToVideo();
                Toast.makeText(AVChatActivity.this, "???????????????????????????????????????", Toast.LENGTH_SHORT).show();
                break;
            case AVChatControlCommand.SWITCH_VIDEO_TO_AUDIO:
                onVideoToAudio();
                break;
            case AVChatControlCommand.NOTIFY_VIDEO_OFF:
                // ??????????????????????????????
                if (state == AVChatType.VIDEO.getValue()) {
                    avChatVideoUI.peerVideoOff();
                }
                break;
            case AVChatControlCommand.NOTIFY_VIDEO_ON:
                // ??????????????????????????????
                if (state == AVChatType.VIDEO.getValue()) {
                    avChatVideoUI.peerVideoOn();
                }
                break;
            default:
                Toast.makeText(this, "????????????????????????" + notification.getControlCommand(),
                        Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * ?????????????????????????????????
     *
     * @param auth_result
     */
    protected void handleWithConnectServerResult(int auth_result) {
        LogUtil.i(TAG, "result code->" + auth_result);
        if (auth_result == 200) {
            LogUtil.d(TAG, "onConnectServer success");
        } else if (auth_result == 101) { // ????????????
            avChatController.showQuitToast(AVChatExitCode.PEER_NO_RESPONSE);
        } else if (auth_result == 401) { // ????????????
            avChatController.showQuitToast(AVChatExitCode.CONFIG_ERROR);
        } else if (auth_result == 417) { // ?????????channelId
            avChatController.showQuitToast(AVChatExitCode.INVALIDE_CHANNELID);
        } else { // ????????????????????????????????????
            avChatController.showQuitToast(AVChatExitCode.CONFIG_ERROR);
        }
    }

    /**
     * ??????/???????????????????????????????????????????????????
     */
    Observer<AVChatOnlineAckEvent> onlineAckObserver = new Observer<AVChatOnlineAckEvent>() {
        @Override
        public void onEvent(AVChatOnlineAckEvent ackInfo) {
            if (state == AVChatType.AUDIO.getValue()) {
                avChatData = avChatController.getAvChatData();
            } else {
                avChatData = avChatVideoUI.getAvChatData();
            }
            if (avChatData != null && avChatData.getChatId() == ackInfo.getChatId()) {
                AVChatSoundPlayer.instance().stop();

                String client = null;
                switch (ackInfo.getClientType()) {
                    case ClientType.Web:
                        client = "Web";
                        break;
                    case ClientType.Windows:
                        client = "Windows";
                        break;
                    case ClientType.Android:
                        client = "Android";
                        break;
                    case ClientType.iOS:
                        client = "iOS";
                        break;
                    case ClientType.MAC:
                        client = "Mac";
                        break;
                    default:
                        break;
                }
                if (client != null) {
                    String option =
                            ackInfo.getEvent() == AVChatEventType.CALLEE_ONLINE_CLIENT_ACK_AGREE
                                    ? "?????????" : "?????????";
                    Toast.makeText(AVChatActivity.this, "????????????" + client + "??????" + option,
                            Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        }
    };

    Observer<Integer> autoHangUpForLocalPhoneObserver = new Observer<Integer>() {
        @Override
        public void onEvent(Integer integer) {
            hangUpByOther(AVChatExitCode.PEER_BUSY);
        }
    };

    Observer<StatusCode> userStatusObserver = new Observer<StatusCode>() {

        @Override
        public void onEvent(StatusCode code) {
            if (code.wontAutoLogin()) {
                AVChatSoundPlayer.instance().stop();
                AVChatKit.getAvChatOptions().logout(AVChatActivity.this);
                finish();
            }
        }
    };

    /**
     * ******************** ????????????????????? ********************
     */

    @Override
    public void onVideoToAudio() {
        state = AVChatType.AUDIO.getValue();
        videoRoot.setVisibility(View.GONE);
        surfaceRoot.setVisibility(View.GONE);
        audioRoot.setVisibility(View.VISIBLE);
        avChatVideoUI.onVideoToAudio();
        // ???????????????????????????????????????????????????????????????????????????????????????
        avChatAudioUI.onVideoToAudio(AVChatManager.getInstance().isLocalAudioMuted(),
                AVChatManager.getInstance().speakerEnabled(),
                avChatData != null ? avChatData.getAccount() : receiverId);
    }

    @Override
    public void onAudioToVideo() {
        audioRoot.setVisibility(View.GONE);
        videoRoot.setVisibility(View.VISIBLE);
        surfaceRoot.setVisibility(View.VISIBLE);
        avChatVideoUI.onAudioToVideo();
    }

    @Override
    public void onReceiveAudioToVideoAgree() {
        // ?????????????????????
        state = AVChatType.VIDEO.getValue();
        audioRoot.setVisibility(View.GONE);
        videoRoot.setVisibility(View.VISIBLE);
        surfaceRoot.setVisibility(View.VISIBLE);
        avChatVideoUI.onAudioToVideoAgree(avChatData != null ? avChatData.getAccount() :
                receiverId);
    }

    private void rejectAudioToVideo() {
        videoRoot.setVisibility(View.GONE);
        surfaceRoot.setVisibility(View.GONE);
        audioRoot.setVisibility(View.VISIBLE);
        avChatAudioUI.showAudioInitLayout();
    }

    private void incomingAudioToVideo() {
        videoRoot.setVisibility(View.GONE);
        surfaceRoot.setVisibility(View.GONE);
        audioRoot.setVisibility(View.VISIBLE);
        avChatAudioUI.showIncomingAudioToVideo();
    }

    /**
     * ****************** ????????? ********************
     */
    private void activeCallingNotifier() {
        if (notifier != null && !isUserFinish) {
            notifier.activeCallingNotification(true);
        }
    }

    private void cancelCallingNotifier() {
        if (notifier != null) {
            notifier.activeCallingNotification(false);
        }
    }

    private void activeMissCallNotifier() {
        if (notifier != null) {
            notifier.activeMissCallNotification(true);
        }
    }

    @Override
    public void finish() {
        isUserFinish = true;
        super.finish();
    }

    /**
     * ******************************** face unity ?????? ********************************
     */

    //    private void initFaceU() {
    //        showOrHideFaceULayout(false); // hide default
    //
    //        if (VersionUtil.isCompatible(Build.VERSION_CODES.JELLY_BEAN_MR2) && FaceU
    //        .hasAuthorized()) {
    //            // async load FaceU
    //            FaceU.createAndAttach(AVChatActivity.this, findView(R.id
    //            .avchat_video_face_unity), new FaceU.Response<FaceU>() {
    //                @Override
    //                public void onResult(FaceU faceU) {
    //                    AVChatActivity.this.faceU = faceU;
    //                    showOrHideFaceULayout(true); // show
    //                }
    //            });
    //        }
    //    }
    //
    //    private void destroyFaceU() {
    //        if (faceU == null) {
    //            return;
    //        }
    //
    //        try {
    //            faceU.destroy();
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //        }
    //    }
    private void showOrHideFaceULayout(boolean show) {
        ViewGroup vp = findView(R.id.avchat_video_face_unity);
        for (int i = 0; i < vp.getChildCount(); i++) {
            vp.getChildAt(i).setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void onTouch() {
        //        if (faceU == null) {
        //            return;
        //        }
        //
        //        faceU.showOrHideLayout();
    }

    // ????????????
    private void manualHangUp(int exitCode) {
        releaseVideo();
        avChatController.hangUp(exitCode);
    }

    // ???????????????
    private void hangUpByOther(int exitCode) {
        if (exitCode == AVChatExitCode.PEER_BUSY) {
            avChatController.hangUp(AVChatExitCode.HANGUP);
            finish();
        } else {
            releaseVideo();
            avChatController.onHangUp(exitCode);
        }
    }

    private void releaseVideo() {
        if (state == AVChatType.VIDEO.getValue()) {
            avChatVideoUI.releaseVideo();
        }
    }

}
