package com.keydom.ih_common.im.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.keydom.Common;
import com.keydom.ih_common.R;
import com.keydom.ih_common.base.BaseActivity;
import com.keydom.ih_common.im.ImClient;
import com.keydom.ih_common.im.config.ImConstants;
import com.keydom.ih_common.im.listener.AVChatControllerCallback;
import com.keydom.ih_common.im.listener.AVSwitchListener;
import com.keydom.ih_common.im.listener.OnTimeListener;
import com.keydom.ih_common.im.listener.observer.AVChatTimeoutObserver;
import com.keydom.ih_common.im.listener.observer.PhoneCallStateObserver;
import com.keydom.ih_common.im.listener.observer.SimpleAVChatStateObserver;
import com.keydom.ih_common.im.manager.AVChatController;
import com.keydom.ih_common.im.manager.AVChatNotification;
import com.keydom.ih_common.im.manager.AVChatSoundPlayer;
import com.keydom.ih_common.im.model.AVChatExitCode;
import com.keydom.ih_common.im.model.CallStateEnum;
import com.keydom.ih_common.im.model.ImMessageConstant;
import com.keydom.ih_common.im.model.def.AVChatCallAction;
import com.keydom.ih_common.im.profile.AVChatProfile;
import com.keydom.ih_common.im.widget.CallFloatBoxView;
import com.keydom.ih_common.utils.FloatPermissionManager;
import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.ih_common.widget.DragLayout;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.StatusCode;
import com.netease.nimlib.sdk.auth.AuthServiceObserver;
import com.netease.nimlib.sdk.avchat.AVChatCallback;
import com.netease.nimlib.sdk.avchat.AVChatManager;
import com.netease.nimlib.sdk.avchat.constant.AVChatControlCommand;
import com.netease.nimlib.sdk.avchat.constant.AVChatEventType;
import com.netease.nimlib.sdk.avchat.constant.AVChatType;
import com.netease.nimlib.sdk.avchat.constant.AVChatVideoScalingType;
import com.netease.nimlib.sdk.avchat.model.AVChatAudioFrame;
import com.netease.nimlib.sdk.avchat.model.AVChatCalleeAckEvent;
import com.netease.nimlib.sdk.avchat.model.AVChatCommonEvent;
import com.netease.nimlib.sdk.avchat.model.AVChatControlEvent;
import com.netease.nimlib.sdk.avchat.model.AVChatData;
import com.netease.nimlib.sdk.avchat.model.AVChatSurfaceViewRenderer;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;
import com.netease.nrtc.video.render.IVideoRender;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.jetbrains.annotations.Nullable;

import io.reactivex.functions.Consumer;

public class CommonVideoCallActivity extends BaseActivity implements View.OnClickListener, AVSwitchListener {

    private static final String TAG = "CommonVideoCallActivity";


    private View notConnectedLayout;
    /**
     * 对方头像
     */
    private ImageView avatar;
    /**
     * 对方名称
     */
    private TextView title;
    /**
     * 正在呼叫XXX
     */
    private TextView hint;
    /**
     * 取消接听按钮
     */
    private TextView hangUp;
    /**
     * 接起按钮
     */
    private View answer;

    /**
     * 大图surfaceView
     */
    private FrameLayout largePreviewLayout;
    /**
     * 小图surfaceView
     */
    private DragLayout smallPreviewLayout;
    /**
     * 接通后的名字显示
     */
    private TextView name;
    /**
     * 接通后的头像显示
     */
    private ImageView callingAvatar;
    /**
     * 接通后的时间计时
     */
    private TextView timeView;
    /**
     * 相机切换
     */
    private View camera;
    /**
     * 接通后挂断按钮
     */
    private View close;
    /**
     * 最小化按钮
     */
    private View minimize;

    /**
     * 音视频切换按钮
     */
    private TextView actionSwitch, avchatSwitch;

    private String displayName;
    private String portrait;
    private String userType;
    private String sessionId;
    private AVChatType avChatType;
    private @AVChatCallAction
    int callAction;
    private AVChatData avChatData;

    public AVChatSurfaceViewRenderer smallRender;
    public AVChatSurfaceViewRenderer largeRender;
    private IVideoRender remoteRender;
    private IVideoRender localRender;
    private AVChatController avChatController;
    /**
     * 是否能够切换摄像头
     */
    private boolean canSwitchCamera = false;
    /**
     * 权限
     */
    private boolean permissionsResult = false;
    /**
     * 是否释放video
     */
    private boolean isReleasedVideo = false;
    /**
     * 通话是否成功建立
     */
    private boolean isCallEstablished = false;
    /**
     * 是否显示悬浮窗
     */
    private boolean shouldShowFloat = false;
    /**
     * 悬浮窗是否已打开
     */
    private boolean isShowFloat = false;
    /**
     * 是否需要暂停音视频
     */
    private boolean hasOnPause = false;

    /**
     * 显示大图视频的用户账号
     */
    private String largeAccount;
    /**
     * 显示小图视频的用户账号
     */
    private String smallAccount;

    private Bundle mBundle;

    private AVChatNotification notifier;
    /**
     * 是否已经结束掉当前activity
     */
    private boolean isUserFinish;
    private CallStateEnum callingState;

    @Override
    public int getLayoutRes() {
        return R.layout.im_activity_video_call;
    }

    @SuppressLint({"WakelockTimeout", "InvalidWakeLockTag"})
    @Override
    public void beforeInit() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
        );
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        boolean isScreenOn = pm.isScreenOn();
        if (!isScreenOn) {
            PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK, "bright");
            wl.acquire();
            wl.release();
        }
    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        findView();
        mBundle = getIntent().getExtras();
        parseIntent(mBundle);
        setView();
        registerObserves(true);
        notifier = new AVChatNotification(this);
        notifier.init(sessionId != null ? sessionId : avChatData.getAccount(), displayName);
    }


    private void parseIntent(Bundle bundle) {
        isShowFloat = false;
        if (bundle != null) {
            callAction = getIntent().getIntExtra(ImConstants.CALL_CALL_ACTION, -1);
            if (callAction == ImMessageConstant.ACTION_OUTGOING_CALL) {
                avChatType = AVChatType.typeOfValue(bundle.getInt(ImConstants.CALL_AVCHAT_TYPE));
                sessionId = bundle.getString(ImConstants.CALL_SESSION_ID);
                hint.setText("主动呼叫");
                answer.setVisibility(View.GONE);
                hangUp.setText(R.string.im_call_close);
                AVChatSoundPlayer.instance().play(AVChatSoundPlayer.RingerTypeEnum.CONNECTING);
                avChatController = new AVChatController(this, avChatData, false);
                doOutgoingCall(sessionId, avChatType);
            } else if (callAction == ImMessageConstant.ACTION_INCOMING_CALL) {
                avChatController = new AVChatController(this, avChatData, false);
                avChatData = (AVChatData) bundle.getSerializable(ImConstants.CALL_AVCHAT_DATA);
                if (avChatData != null) {
                    avChatController.setAvChatData(avChatData);
                    avChatType = avChatData.getChatType();
                    sessionId = avChatData.getAccount();
                    AVChatSoundPlayer.instance().play(AVChatSoundPlayer.RingerTypeEnum.RING);
                    answer.setVisibility(View.VISIBLE);
                }
            } else {
                avChatData = (AVChatData) bundle.getSerializable(ImConstants.CALL_AVCHAT_DATA);
                avChatController = new AVChatController(this, avChatData, true);
                if (avChatData != null) {
                    avChatController.setAvChatData(avChatData);
                    avChatType = avChatData.getChatType();
                    sessionId = avChatData.getAccount();
                    initLargeSurfaceView(avChatData.getAccount());
                    initSmallSurfaceView(ImClient.getUserInfoProvider().getAccount());
                } else {
                    avChatType = AVChatType.typeOfValue(bundle.getInt(ImConstants.CALL_AVCHAT_TYPE));
                    sessionId = bundle.getString(ImConstants.CALL_SESSION_ID);
                    notConnectedLayout.setVisibility(View.GONE);
                    initLargeSurfaceView(sessionId);
                    initSmallSurfaceView(ImClient.getUserInfoProvider().getAccount());
                }
            }
        }
//        if (avChatType == AVChatType.AUDIO) {
//
//        } else {
//            if (callAction == ImMessageConstant.ACTION_INCOMING_CALL) {
//                // 来电
//                AVChatSoundPlayer.instance().play(AVChatSoundPlayer.RingerTypeEnum.RING);
////                showIncomingCall(avChatData);
//                answer.setVisibility(View.VISIBLE);
//            } else {
//                // 去电
//
//            }
//        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        parseIntent(intent.getExtras());
    }

    @Override
    protected void onResume() {
        super.onResume();
        cancelCallingNotifier();
        if (hasOnPause) {
            avChatController.resumeVideo();
            hasOnPause = false;
        }
        shouldShowFloat = true;
        CallFloatBoxView.hideFloatBox();
    }

    @Override
    protected void onPause() {
        super.onPause();
        avChatController.pauseVideo();
        hasOnPause = true;
    }

    /**
     * ****************** 通知栏 ********************
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
    protected void onStop() {
        super.onStop();
        activeCallingNotifier();
    }

    @Override
    public void finish() {
        isUserFinish = true;
        super.finish();
    }

    /**
     * 大小图像显示切换
     */
    private void switchRender(String user1, String user2) {
        String remoteId = TextUtils.equals(user1, ImClient.getUserInfoProvider().getAccount()) ? user2 : user1;

        if (remoteRender == null && localRender == null) {
            localRender = smallRender;
            remoteRender = largeRender;
        }

        //交换
        IVideoRender render = localRender;
        localRender = remoteRender;
        remoteRender = render;


        //断开SDK视频绘制画布
        AVChatManager.getInstance().setupLocalVideoRender(null, false, 0);
        AVChatManager.getInstance().setupRemoteVideoRender(remoteId, null, false, 0);

        //重新关联上画布
        AVChatManager.getInstance().setupLocalVideoRender(localRender, false, AVChatVideoScalingType.SCALE_ASPECT_BALANCED);
        AVChatManager.getInstance().setupRemoteVideoRender(remoteId, remoteRender, false, AVChatVideoScalingType.SCALE_ASPECT_BALANCED);

    }

    public void doOutgoingCall(String account, final AVChatType avChatType) {
        this.sessionId = account;
        avChatController.doCalling(account, avChatType, new AVChatControllerCallback<AVChatData>() {
            @Override
            public void onSuccess(AVChatData data) {
                avChatData = data;
                Common.INSTANCE.setAVChatData(avChatData);
                avChatController.setAvChatData(data);
                if (!requestCallPermissions()) {
                    return;
                }
                if (avChatType == AVChatType.VIDEO) {
                    canSwitchCamera = true;
                }
            }

            @Override
            public void onFailed(int code, String errorMsg) {
                finish();
            }
        });
    }

    /**
     * 大图像surface view 初始化
     * 设置画布，加入到自己的布局中，用于呈现视频图像
     *
     * @param account 要显示视频的用户帐号
     */
    private void initLargeSurfaceView(String account) {
        largeAccount = account;
        AVChatManager.getInstance().setupRemoteVideoRender(account, null, false, AVChatVideoScalingType.SCALE_ASPECT_BALANCED);
        AVChatManager.getInstance().setupRemoteVideoRender(account, largeRender, false, AVChatVideoScalingType.SCALE_ASPECT_BALANCED);
        addIntoLargeSizePreviewLayout(largeRender);
        remoteRender = largeRender;
    }

    private void addIntoLargeSizePreviewLayout(SurfaceView surfaceView) {
        if (surfaceView.getParent() != null) {
            ((ViewGroup) surfaceView.getParent()).removeView(surfaceView);
        }
        largePreviewLayout.removeAllViews();
        surfaceView.setZOrderMediaOverlay(false);
        surfaceView.setZOrderOnTop(false);
        largePreviewLayout.addView(surfaceView);
    }

    /**
     * 小图像surface view 初始化
     * 设置画布，加入到自己的布局中，用于呈现视频图像
     *
     * @param account 要显示视频的用户帐号
     */
    public void initSmallSurfaceView(String account) {
        smallAccount = account;
        AVChatManager.getInstance().setupLocalVideoRender(null, false, AVChatVideoScalingType.SCALE_ASPECT_BALANCED);
        AVChatManager.getInstance().setupLocalVideoRender(smallRender, false, AVChatVideoScalingType.SCALE_ASPECT_BALANCED);
        addIntoSmallSizePreviewLayout(smallRender);
        smallPreviewLayout.bringToFront();
        localRender = smallRender;
    }

    private void addIntoSmallSizePreviewLayout(SurfaceView surfaceView) {
        if (surfaceView.getParent() != null) {
            ((ViewGroup) surfaceView.getParent()).removeView(surfaceView);
        }
        smallPreviewLayout.removeAllViews();
        surfaceView.setZOrderMediaOverlay(true);
        surfaceView.setZOrderOnTop(true);
        smallPreviewLayout.addView(surfaceView);
    }

    @SuppressLint("CheckResult")
    private boolean requestCallPermissions() {
        String[] permissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO,
                Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.INTERNET, Manifest.permission.READ_PHONE_STATE, Manifest.permission.MODIFY_AUDIO_SETTINGS};
        boolean granted = true;
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                granted = false;
            }
        }
        if (granted) {
            permissionsResult = true;
        } else {
            RxPermissions rxPermissions = new RxPermissions(this);
            rxPermissions.request(permissions)
                    .subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean granted) throws Exception {
                            permissionsResult = granted;
                        }
                    });
        }
        return permissionsResult;
    }

    private void setView() {
        NimUserInfo userInfo = (NimUserInfo) ImClient.getUserInfoProvider().getUserInfo(sessionId);
        displayName = userInfo.getName();
        portrait = userInfo.getAvatar();
        if(null == userInfo.getExtensionMap()){
            if ("com.keydom.mianren.ih_patient".equals(getPackageName())) {
                userType = ImMessageConstant.DOCTOR;
            } else {
                userType = ImMessageConstant.PATIENT;
            }
        }else{
            userType = userInfo.getExtensionMap().get(ImConstants.CALL_USER_TYPE).toString();
        }
        title.setText(displayName);
        name.setText(displayName);
        GlideUtils.load(avatar, portrait, R.mipmap.im_default_head_image, R.mipmap.im_default_head_image, true, null);
        GlideUtils.load(callingAvatar, portrait, R.mipmap.im_default_head_image, R.mipmap.im_default_head_image, true, null);
        if (callAction == ImMessageConstant.ACTION_OUTGOING_CALL) {
            String name = ImMessageConstant.PATIENT.equals(userType) ? "患者" : "医生";
            hint.setText(getString(R.string.im_call_come_out_hint, name));
        }

        ImClient.setOnTimeListener(new OnTimeListener() {
            @Override
            public void onTimeResult(long time) {
                if (time >= 3600) {
                    timeView.setText(String.format("%d:%02d:%02d", time / 3600, time % 3600 / 60, time % 60));
                } else {
                    timeView.setText(String.format("%02d:%02d", time % 3600 / 60, time % 60));
                }
            }
        });
    }

    private void findView() {
        notConnectedLayout = findViewById(R.id.not_connected);
        avatar = findViewById(R.id.iv_avatar);
        callingAvatar = findViewById(R.id.calling_avatar);
        title = findViewById(R.id.tv_title);
        hint = findViewById(R.id.tv_hint);
        hangUp = findViewById(R.id.tv_hang_up);
        answer = findViewById(R.id.tv_answer);
        largePreviewLayout = findViewById(R.id.fl_call_large_preview);
        smallPreviewLayout = findViewById(R.id.dl_call_small_preview);
        name = findViewById(R.id.tv_name);
        timeView = findViewById(R.id.tv_time);
        camera = findViewById(R.id.action_camera);
        close = findViewById(R.id.action_close);
        minimize = findViewById(R.id.action_minimize);
        actionSwitch = findViewById(R.id.action_switch);
        avchatSwitch = findViewById(R.id.avchat_switch);

        smallRender = new AVChatSurfaceViewRenderer(this);
        largeRender = new AVChatSurfaceViewRenderer(this);

        hangUp.setOnClickListener(this);
        minimize.setOnClickListener(this);
        close.setOnClickListener(this);
        camera.setOnClickListener(this);
        answer.setOnClickListener(this);
        avchatSwitch.setOnClickListener(this);
        actionSwitch.setOnClickListener(this);

        smallPreviewLayout.setOnDragViewClickListener(new DragLayout.onDragViewClickListener() {
            @Override
            public void onDragViewClick() {
                switchRender(smallAccount, largeAccount);
            }
        });

    }

    private void registerObserves(boolean register) {
        AVChatManager.getInstance().observeAVChatState(avchatStateObserver, register);
        AVChatManager.getInstance().observeHangUpNotification(callHangupObserver, register);
        AVChatManager.getInstance().observeCalleeAckNotification(callAckObserver, register);
        AVChatManager.getInstance().observeControlNotification(callControlObserver, register);
        AVChatTimeoutObserver.getInstance().observeTimeoutNotification(timeoutObserver, register, callAction == ImMessageConstant.ACTION_INCOMING_CALL);
        PhoneCallStateObserver.getInstance().observeAutoHangUpForLocalPhone(autoHangUpForLocalPhoneObserver, register);
        //放到所有UI的基类里面注册，所有的UI实现onKickOut接口
        NIMClient.getService(AuthServiceObserver.class).observeOnlineStatus(userStatusObserver, register);
    }


    /**
     * 通话过程状态监听
     */
    private SimpleAVChatStateObserver avchatStateObserver = new SimpleAVChatStateObserver() {

        @Override
        public void onJoinedChannel(int code, String audioFile, String videoFile, int i) {
            Log.d(TAG, "audioFile -> " + audioFile + " videoFile -> " + videoFile);
            handleWithConnectServerResult(code);
        }

        @Override
        public void onUserJoined(String account) {
            Log.d(TAG, "onUserJoin -> " + account);
            notConnectedLayout.setVisibility(View.GONE);
            if (avChatType == AVChatType.VIDEO) {
                initLargeSurfaceView(account);
            } else {
                camera.setVisibility(View.GONE);
                smallPreviewLayout.setVisibility(View.GONE);
            }
        }

        @Override
        public void onUserLeave(String account, int event) {
            Log.d(TAG, "onUserLeave -> " + account);
            manualHangUp(AVChatExitCode.HANGUP);
            finish();
        }

        @Override
        public void onCallEstablished() {
            Log.d(TAG, "onCallEstablished");
            //移除超时监听
            AVChatTimeoutObserver.getInstance().observeTimeoutNotification(timeoutObserver, false, callAction == ImMessageConstant.ACTION_INCOMING_CALL);
            isCallEstablished = true;

            if (avChatType == AVChatType.AUDIO) {
                camera.setVisibility(View.GONE);
                smallPreviewLayout.setVisibility(View.GONE);
                callingState = CallStateEnum.INCOMING_AUDIO_CALLING;
            } else {
                callingAvatar.setVisibility(View.GONE);
                camera.setVisibility(View.VISIBLE);
                smallPreviewLayout.setVisibility(View.VISIBLE);
                initSmallSurfaceView(ImClient.getUserInfoProvider().getAccount());
            }
            ImClient.startTimer();
        }

        @Override
        public boolean onAudioFrameFilter(AVChatAudioFrame frame) {
            return true;
        }

    };

    /**
     * 通话过程中，收到对方挂断电话
     */
    Observer<AVChatCommonEvent> callHangupObserver = new Observer<AVChatCommonEvent>() {
        @Override
        public void onEvent(AVChatCommonEvent avChatHangUpInfo) {

            avChatData = avChatController.getAvChatData();
            if (avChatData != null && avChatData.getChatId() == avChatHangUpInfo.getChatId()) {
                hangUpByOther(AVChatExitCode.HANGUP);
                cancelCallingNotifier();
                // 如果是incoming call主叫方挂断，那么通知栏有通知
                if (callAction == ImMessageConstant.ACTION_INCOMING_CALL && !isCallEstablished) {
                    activeMissCallNotifier();
                }
            }
        }
    };

    /**
     * 呼叫时，被叫方的响应（接听、拒绝、忙）
     */
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
            // 来电超时，自己未接听
            if (callAction == ImMessageConstant.ACTION_INCOMING_CALL) {
                activeMissCallNotifier();
            }
            finish();
        }
    };

    /**
     * 监听音视频模式切换通知, 对方音视频开关通知
     */
    Observer<AVChatControlEvent> callControlObserver = new Observer<AVChatControlEvent>() {
        @Override
        public void onEvent(AVChatControlEvent netCallControlNotification) {
            handleCallControl(netCallControlNotification);
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
                //TODO IM 执行登出操作
                finish();
            }
        }
    };


    /**
     * 被对方挂断
     */
    private void hangUpByOther(int exitCode) {
        releaseVideo();
        avChatController.onHangUp(exitCode);
    }

    /**
     * 主动挂断
     */
    private void manualHangUp(int exitCode) {
        releaseVideo();
        avChatController.hangUp(exitCode);
    }

    private void releaseVideo() {
        if (avChatType == AVChatType.VIDEO) {
            if (isReleasedVideo) {
                return;
            }
            isReleasedVideo = true;
            AVChatManager.getInstance().stopVideoPreview();
            AVChatManager.getInstance().disableVideo();
        }
    }

    /**
     * 处理连接服务器的返回值
     *
     * @param authResult
     */
    protected void handleWithConnectServerResult(int authResult) {
        Log.i(TAG, "result code->" + authResult);
        if (authResult == 200) {
            Log.d(TAG, "onConnectServer success");
        } else if (authResult == 101) { // 连接超时
            avChatController.showQuitToast(AVChatExitCode.PEER_NO_RESPONSE);
        } else if (authResult == 401) { // 验证失败
            avChatController.showQuitToast(AVChatExitCode.CONFIG_ERROR);
        } else if (authResult == 417) { // 无效的channelId
            avChatController.showQuitToast(AVChatExitCode.INVALIDE_CHANNELID);
        } else { // 连接服务器错误，直接退出
            avChatController.showQuitToast(AVChatExitCode.CONFIG_ERROR);
        }
    }

    /**
     * 处理音视频切换请求和对方音视频开关通知
     */
    private void handleCallControl(AVChatControlEvent notification) {
        if (AVChatManager.getInstance().getCurrentChatId() != notification.getChatId()) {
            return;
        }
        switch (notification.getControlCommand()) {
            case AVChatControlCommand.SWITCH_AUDIO_TO_VIDEO:
                callingState = CallStateEnum.INCOMING_AUDIO_TO_VIDEO;
                break;
            case AVChatControlCommand.SWITCH_AUDIO_TO_VIDEO_AGREE:
                // 对方同意切成视频啦
                avChatType = AVChatType.VIDEO;
                onAudioToVideoAgree(notification.getAccount());
                break;
            case AVChatControlCommand.SWITCH_AUDIO_TO_VIDEO_REJECT:
                Toast.makeText(this, "对方不同意音频切换为视频！", Toast.LENGTH_SHORT).show();
                break;
            case AVChatControlCommand.SWITCH_VIDEO_TO_AUDIO:
                onVideoToAudio();
                break;
            case AVChatControlCommand.NOTIFY_VIDEO_OFF:
                // 收到对方关闭画面通知
                if (avChatType == AVChatType.VIDEO) {
//                    avChatVideoUI.peerVideoOff();
                }
                break;
            case AVChatControlCommand.NOTIFY_VIDEO_ON:
                // 收到对方打开画面通知
//                if (state == AVChatType.VIDEO.getValue()) {
//                    avChatVideoUI.peerVideoOn();
//                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_answer) {
            doReceiveCall(avChatType);
        } else if (id == R.id.tv_hang_up) {
            isShowFloat = false;
            if (callAction == ImMessageConstant.ACTION_INCOMING_CALL) {
                doRefuseCall();
            } else {
                doHangUp();
            }
        } else if (id == R.id.action_close) {
            isShowFloat = false;
            doHangUp();
        } else if (id == R.id.action_minimize) {
            if (FloatPermissionManager.INSTANCE.applyFloatWindow(this)) {
                if (avChatType == AVChatType.VIDEO) {
                    CallFloatBoxView.showFloatBox(mBundle, largeRender);
                } else {
                    CallFloatBoxView.showFloatBox(mBundle);
                }
                isShowFloat = true;
                finish();
            }
        } else if (id == R.id.action_camera) {
            if (canSwitchCamera) {
                avChatController.switchCamera();
            }
        } else if (id == R.id.action_switch) {
            if (avChatType == AVChatType.AUDIO) {
                avChatController.switchAudioToVideo(this);
            } else {
                avChatController.switchVideoToAudio(this);

            }
        }
    }

    /**
     * 接听来电
     */
    private void doReceiveCall(final AVChatType avChatType) {
        if (avChatType == AVChatType.VIDEO) {
            avChatController.receive(avChatType, new AVChatControllerCallback<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    canSwitchCamera = true;
                }

                @Override
                public void onFailed(int code, String errorMsg) {
                    finish();
                }
            });
        } else {
            if (callingState == CallStateEnum.INCOMING_AUDIO_CALLING) {
                avChatController.receive(avChatType, new AVChatControllerCallback<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }

                    @Override
                    public void onFailed(int code, String errorMsg) {
                        finish();
                    }
                });
            } else if (callingState == CallStateEnum.INCOMING_AUDIO_TO_VIDEO) {
                avChatController.receiveAudioToVideo(this);
            }
        }
    }

    /**
     * 拒绝来电
     */
    private void doRefuseCall() {
        if (avChatType == AVChatType.AUDIO) {
            if (callingState == CallStateEnum.INCOMING_AUDIO_CALLING) {
                avChatController.hangUp(AVChatExitCode.HANGUP);
                finish();
            } else if (callingState == CallStateEnum.INCOMING_AUDIO_TO_VIDEO) {
                rejectAudioToVideo();
            }
        } else {
            avChatController.hangUp(AVChatExitCode.HANGUP);
            finish();
        }
    }

    /**
     * 挂断
     */
    private void doHangUp() {
        releaseVideo();
        avChatController.hangUp(AVChatExitCode.HANGUP);
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!isShowFloat) {
            //界面销毁时强制尝试挂断，防止出现红米Note 4X等手机在切后台点击杀死程序时，实际没有杀死的情况
            try {
                manualHangUp(AVChatExitCode.HANGUP);
            } catch (Exception e) {
            }
            registerObserves(false);
            AVChatProfile.getInstance().setAVChatting(false);
            cancelCallingNotifier();
        }
    }

    @Override
    public void onVideoToAudio() {
        avChatType = AVChatType.AUDIO;
        camera.setVisibility(View.GONE);
        isReleasedVideo = true;
        smallPreviewLayout.setVisibility(View.GONE);
    }

    @Override
    public void onAudioToVideo() {
        camera.setVisibility(View.VISIBLE);
        smallPreviewLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onReceiveAudioToVideoAgree() {
        avChatType = AVChatType.VIDEO;
        onAudioToVideoAgree(avChatData != null ? avChatData.getAccount() : sessionId);
    }

    public void onAudioToVideoAgree(String largeAccount) {

        //打开视频
        isReleasedVideo = false;
        smallRender = new AVChatSurfaceViewRenderer(this);
        largeRender = new AVChatSurfaceViewRenderer(this);

        //打开视频
        AVChatManager.getInstance().enableVideo();
        AVChatManager.getInstance().startVideoPreview();

        initSmallSurfaceView(ImClient.getUserInfoProvider().getAccount());
        // 是否在发送视频 即摄像头是否开启
        if (AVChatManager.getInstance().isLocalVideoMuted()) {
            AVChatManager.getInstance().muteLocalVideo(false);
//            localVideoOn();
        }

        initLargeSurfaceView(largeAccount);
    }

    /**
     * 拒绝音视频切换
     */
    private void rejectAudioToVideo() {
        AVChatManager.getInstance().sendControlCommand(avChatController.getAvChatData().getChatId(), AVChatControlCommand.SWITCH_AUDIO_TO_VIDEO_REJECT, new AVChatCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.i(TAG, "rejectAudioToVideo success");
//                showAudioInitLayout();
            }

            @Override
            public void onFailed(int code) {
                Log.i(TAG, "rejectAudioToVideo onFailed");

            }

            @Override
            public void onException(Throwable exception) {
                Log.i(TAG, "rejectAudioToVideo onException");
            }
        });
    }
}
