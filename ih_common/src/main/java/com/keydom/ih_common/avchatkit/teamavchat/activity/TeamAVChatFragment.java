package com.keydom.ih_common.avchatkit.teamavchat.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.keydom.ih_common.R;
import com.keydom.ih_common.avchatkit.AVChatKit;
import com.keydom.ih_common.avchatkit.TeamAVChatProfile;
import com.keydom.ih_common.avchatkit.common.log.LogUtil;
import com.keydom.ih_common.avchatkit.common.permission.MPermission;
import com.keydom.ih_common.avchatkit.common.permission.annotation.OnMPermissionDenied;
import com.keydom.ih_common.avchatkit.common.permission.annotation.OnMPermissionGranted;
import com.keydom.ih_common.avchatkit.common.permission.annotation.OnMPermissionNeverAskAgain;
import com.keydom.ih_common.avchatkit.common.recyclerview.decoration.SpacingDecoration;
import com.keydom.ih_common.avchatkit.common.util.ScreenUtil;
import com.keydom.ih_common.avchatkit.config.AVChatConfigs;
import com.keydom.ih_common.avchatkit.config.AVPrivatizationConfig;
import com.keydom.ih_common.avchatkit.controll.AVChatSoundPlayer;
import com.keydom.ih_common.avchatkit.teamavchat.TeamAVChatNotification;
import com.keydom.ih_common.avchatkit.teamavchat.TeamAVChatVoiceMuteDialog;
import com.keydom.ih_common.avchatkit.teamavchat.adapter.TeamAVChatAdapter;
import com.keydom.ih_common.avchatkit.teamavchat.module.TeamAVChatItem;
import com.keydom.ih_common.bean.MessageEvent;
import com.keydom.ih_common.bean.VoiceBean;
import com.keydom.ih_common.constant.EventType;
import com.keydom.ih_common.event.ConsultationEvent;
import com.keydom.ih_common.im.ImClient;
import com.keydom.ih_common.im.listener.observer.SimpleAVChatStateObserver;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.GeneralDialog;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.ResponseCode;
import com.netease.nimlib.sdk.StatusCode;
import com.netease.nimlib.sdk.auth.AuthServiceObserver;
import com.netease.nimlib.sdk.avchat.AVChatCallback;
import com.netease.nimlib.sdk.avchat.AVChatManager;
import com.netease.nimlib.sdk.avchat.AVChatStateObserverLite;
import com.netease.nimlib.sdk.avchat.constant.AVChatControlCommand;
import com.netease.nimlib.sdk.avchat.constant.AVChatType;
import com.netease.nimlib.sdk.avchat.constant.AVChatUserRole;
import com.netease.nimlib.sdk.avchat.constant.AVChatVideoCropRatio;
import com.netease.nimlib.sdk.avchat.constant.AVChatVideoScalingType;
import com.netease.nimlib.sdk.avchat.model.AVChatControlEvent;
import com.netease.nimlib.sdk.avchat.model.AVChatData;
import com.netease.nimlib.sdk.avchat.model.AVChatParameters;
import com.netease.nimlib.sdk.avchat.video.AVChatCameraCapturer;
import com.netease.nimlib.sdk.avchat.video.AVChatVideoCapturerFactory;
import com.netease.nrtc.video.render.IVideoRender;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static com.keydom.ih_common.avchatkit.teamavchat.module.TeamAVChatItem.TYPE.TYPE_DATA;


/**
 * ????????????????????????????????????????????????????????????????????????
 * Created by huangjun on 2017/5/3.
 * <p>????????????/????????????????????????????????????
 * <ol>
 * <li>????????????????????????????????? {@link AVChatManager#createRoom(String, String, AVChatCallback)}???
 * ???????????????????????????????????????????????????????????????????????????????????????30?????????????????????????????????????????????????????????</li>
 * <li>??????????????????????????? {@link AVChatManager#observeAVChatState(AVChatStateObserverLite, boolean)} ???</li>
 * <li>???????????????????????? {@link AVChatManager#enableRtc()}??? </li>
 * <li>????????????????????????????????????????????????????????? [??????????????????] {@link AVChatParameters#KEY_SESSION_LIVE_MODE},
 * {@link AVChatParameters#KEY_SESSION_LIVE_URL}???</li>
 * <li>?????????????????? {@link AVChatManager#enableVideo()}???</li>
 * <li>???????????????????????? {@link AVChatManager#setupLocalVideoRender(IVideoRender, boolean, int)} ???</li>
 * <li>??????????????????????????????[???????????????] {@link AVChatManager#setParameter(AVChatParameters.Key, Object)},
 * {@link AVChatManager#setParameters(AVChatParameters)}???</li>
 * <li>???????????????????????????????????? {@link AVChatVideoCapturerFactory#createCameraCapturer(boolean)},
 * <li>???????????????????????? {@link AVChatManager#startVideoPreview()}???</li>
 * <li>???????????? {@link AVChatManager#joinRoom2(String, AVChatType, AVChatCallback)}???</li>
 * <li>?????????????????????????????????????????????????????????????????????</li>
 * <li>?????????????????? {@link AVChatManager#stopVideoPreview()} ???</li>
 * <li>?????????????????? {@link AVChatManager#disableVideo()} ()} ???</li>
 * <li>???????????? {@link AVChatManager#leaveRoom2(String, AVChatCallback)}???</li>
 * <li>?????????????????????, {@link AVChatManager#disableRtc()}???</li>
 * </ol></p>
 */

public class TeamAVChatFragment extends Fragment {
    // CONST
    private static final String TAG = "TeamAVChat";
    private static final String KEY_RECEIVED_CALL = "call";
    private static final String KEY_TEAM_ID = "teamid";
    private static final String KEY_ACCOUNTS = "accounts";
    private static final String KEY_APPLY = "key_apply";
    private static final int AUTO_REJECT_CALL_TIMEOUT = 45 * 1000;
    private static final int CHECK_RECEIVED_CALL_TIMEOUT = 45 * 1000;
    private static final int MAX_SUPPORT_ROOM_USERS_COUNT = 9;
    private static final int BASIC_PERMISSION_REQUEST_CODE = 0x100;
    // DATA
    private String teamId;
    private String roomId;
    private long chatId;
    private ArrayList<String> accounts;
    private boolean receivedCall;
    private boolean destroyRTC;
    private String teamName;

    // CONTEXT
    private Handler mainHandler;

    // LAYOUT
    private View callLayout;
    private View surfaceLayout;
    private View consutationLayout;

    // VIEW
    private RecyclerView recyclerView;
    private TeamAVChatAdapter adapter;
    private List<TeamAVChatItem> data;
    private View voiceMuteButton;

    // TIMER
    private Timer timer;
    private int seconds;
    private TextView timerText;
    private Runnable autoRejectTask;

    // CONTROL STATE
    boolean videoMute = false;
    boolean microphoneMute = false;
    boolean speakerMode = true;

    // AVCAHT OBSERVER
    //    private AVChatStateObserver stateObserver;
    private Observer<AVChatControlEvent> notificationObserver;
    private AVChatCameraCapturer mVideoCapturer;

    private TeamAVChatNotification notifier;

    private View view;

    /**
     * ??????????????????????????????
     */
    private boolean isApply;
    /**
     * ?????????????????????????????????
     */
    private boolean outConsultationDoctor;
    /**
     * ??????????????????????????????
     */
    private boolean isCreateMDT = false;

    public static TeamAVChatFragment newInstance(boolean receivedCall, String teamId,
                                                 ArrayList<String> accounts, boolean isApply,
                                                 boolean outConsultationDoctor) {
        TeamAVChatFragment fragment = new TeamAVChatFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(KEY_RECEIVED_CALL, receivedCall);
        bundle.putString(KEY_TEAM_ID, teamId);
        bundle.putBoolean(KEY_APPLY, isApply);
        bundle.putBoolean("outConsultationDoctor", outConsultationDoctor);
        bundle.putSerializable(KEY_ACCOUNTS, accounts);
        fragment.setArguments(bundle);
        return fragment;
    }

    @android.support.annotation.Nullable
    @Override
    public View onCreateView(@android.support.annotation.NonNull LayoutInflater inflater,
                             @android.support.annotation.Nullable ViewGroup container,
                             @android.support.annotation.Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.team_avchat_activity, container, false);

        EventBus.getDefault().register(this);
        //        dismissKeyguard();
        initArguments();
        findLayouts();
        initHandler();
        initNotification();
        //        showViews();
        //        setChatting(true);
        NIMClient.getService(AuthServiceObserver.class).observeOnlineStatus(userStatus, true);

        AVChatManager.getInstance().observeAVChatState(stateObserver, true);

        return view;
    }

    public void setOutConsultationDoctor(boolean outConsultationDoctor) {
        this.outConsultationDoctor = outConsultationDoctor;
    }

    /**
     * ??????????????????
     */
    public void addNewDoctor(String doctorCode) {
        if (!accounts.contains(doctorCode)) {
            accounts.add(doctorCode);
        }
    }

    /**
     * ???????????????
     */
    public void startConsultation() {
        destroyRTC = false;
        ImClient.createRoom(getContext(), teamId, accounts, AVChatKit.teamChatType,"",
                new CreateRoomCallback() {
                    @Override
                    public void success(String id) {
                        isCreateMDT = true;
                        joinRoom();
                    }

                    @Override
                    public void failed(int code) {
                        if (code == 417) {
                            joinRoom();
                        } else {
                            ToastUtil.showMessage(getContext(), "????????????????????????");
                        }
                    }
                });
    }

    /**
     * ????????????????????????????????????
     */
    public void joinRoom() {
        roomId = AVChatKit.teamChatType + teamId;
        showViews();
        setChatting(true);
    }

    /**
     * ????????????
     */
    private void endConsultation() {
        hangup();
        consutationLayout.setVisibility(View.VISIBLE);
        callLayout.setVisibility(View.GONE);
        surfaceLayout.setVisibility(View.GONE);
        releaseSource();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(ConsultationEvent consultationEvent) {
        teamId = consultationEvent.getTeamId();
        teamName = consultationEvent.getTeamName();
        roomId = consultationEvent.getRoomName();
        accounts = consultationEvent.getAccounts();
        receivedCall = true;
        showViews();
        setChatting(true);
    }

    @Override
    public void onStop() {
        super.onStop();
        activeCallingNotifier(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        LogUtil.i(TAG, "TeamAVChatActivity onDestroy");
        if (stateObserver != null) {
            AVChatManager.getInstance().observeAVChatState(stateObserver, false);
        }
        releaseSource();
    }

    /**
     * ????????????
     */
    private void releaseSource() {
        if (timer != null) {
            timer.cancel();
        }

        if (timerTask != null) {
            timerTask.cancel();
        }

        //        if (stateObserver != null) {
        //            AVChatManager.getInstance().observeAVChatState(stateObserver, false);
        //        }

        if (notificationObserver != null) {
            AVChatManager.getInstance().observeControlNotification(notificationObserver, false);
        }
        if (mainHandler != null) {
            mainHandler.removeCallbacksAndMessages(null);
        }

        // ?????????????????????????????????????????????rtc?????????
        hangup();
        activeCallingNotifier(false);
        setChatting(false);
        NIMClient.getService(AuthServiceObserver.class).observeOnlineStatus(userStatus, false);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        LogUtil.i(TAG, "TeamAVChatActivity onSaveInstanceState");
    }

    /**
     * ************************************ ????????? ***************************************
     */

    // ????????????flag?????????????????????/????????????????????????
    private void dismissKeyguard() {
        getActivity().getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
        );
    }

    /**
     * handler
     */
    private void initHandler() {
        mainHandler = new Handler(getActivity().getMainLooper());
    }

    /**
     * ????????????
     */
    private void initArguments() {
        Bundle bundle = getArguments();
        receivedCall = bundle.getBoolean(KEY_RECEIVED_CALL, false);
        teamId = bundle.getString(KEY_TEAM_ID);
        accounts = (ArrayList<String>) bundle.getSerializable(KEY_ACCOUNTS);
        isApply = bundle.getBoolean(KEY_APPLY);
        outConsultationDoctor = bundle.getBoolean("outConsultationDoctor");
    }

    private void findLayouts() {
        callLayout = view.findViewById(R.id.team_avchat_call_layout);
        surfaceLayout = view.findViewById(R.id.team_avchat_surface_layout);
        voiceMuteButton = view.findViewById(R.id.avchat_shield_user);

        consutationLayout = view.findViewById(R.id.team_consultation_layout);
        consutationLayout.setVisibility(View.VISIBLE);
        TextView start = consutationLayout.findViewById(R.id.start);
        start.setText("????????????");
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (outConsultationDoctor) {
                    showApplyTipsDialog();
                } else {
                    startConsultation();
                }
            }
        });
    }

    private void showApplyTipsDialog() {
        new GeneralDialog(getContext(), "????????????????????????????????????????????????????????????",
                new GeneralDialog.OnCloseListener() {
                    @Override
                    public void onCommit() {
                        EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.APPLY_JOIN_CONSULTATION).build());
                    }
                }).setPositiveButton("??????").show();
    }

    private void initNotification() {
        notifier = new TeamAVChatNotification(getContext());
        notifier.init(teamId, teamName);
    }

    /**
     * ************************************ ????????? ***************************************
     */

    private void showViews() {
        consutationLayout.setVisibility(View.GONE);
        if (receivedCall) {
            showReceivedCallLayout();
        } else {
            showSurfaceLayout();
        }
    }

    /**
     * ??????????????????
     */
    private void setChatting(boolean isChatting) {
        TeamAVChatProfile.sharedInstance().setTeamAVChatting(isChatting);
    }


    /**
     * ????????????
     */
    private void showReceivedCallLayout() {
        receivedCall = false;
        callLayout.setVisibility(View.VISIBLE);
        // ??????
        TextView textView = callLayout.findViewById(R.id.received_call_tip);

        final String tipText = TextUtils.isEmpty(teamName) ? "????????????????????????" : teamName + "???????????????";
        textView.setText(tipText);

        // ????????????
        AVChatSoundPlayer.instance().play(AVChatSoundPlayer.RingerTypeEnum.RING);

        // ??????
        callLayout.findViewById(R.id.refuse).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AVChatSoundPlayer.instance().stop();
                cancelAutoRejectTask();
                endConsultation();
            }
        });

        // ??????
        callLayout.findViewById(R.id.receive).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AVChatSoundPlayer.instance().stop();
                cancelAutoRejectTask();
                callLayout.setVisibility(View.GONE);
                showSurfaceLayout();
            }
        });

        startAutoRejectTask();
    }

    /**
     * ????????????
     */
    private void showSurfaceLayout() {
        // ??????
        surfaceLayout.setVisibility(View.VISIBLE);
        recyclerView = surfaceLayout.findViewById(R.id.recycler_view);
        initRecyclerView();

        // ????????????
        timerText = surfaceLayout.findViewById(R.id.timer_text);

        // ????????????
        ViewGroup settingLayout = surfaceLayout.findViewById(R.id.avchat_setting_layout);
        for (int i = 0; i < settingLayout.getChildCount(); i++) {
            View v = settingLayout.getChildAt(i);
            if (v instanceof RelativeLayout) {
                ViewGroup vp = (ViewGroup) v;
                if (vp.getChildCount() == 1) {
                    vp.getChildAt(0).setOnClickListener(settingBtnClickListener);
                }
            }
        }

        // ?????????????????????
        checkPermission();
    }

    private void onPermissionChecked() {
        startRtc(); // ???????????????
    }

    /**
     * ************************************ ??????????????? ***************************************
     */

    private void startRtc() {
        // rtc init
        AVChatManager.getInstance().enableRtc(AVPrivatizationConfig.getServerAddresses(getContext()));
        AVChatManager.getInstance().enableVideo();
        LogUtil.i(TAG, "start rtc done");

        mVideoCapturer = AVChatVideoCapturerFactory.createCameraPolicyCapturer(true);
        AVChatManager.getInstance().setupVideoCapturer(mVideoCapturer);

        // state observer
        //        if (stateObserver != null) {
        //            AVChatManager.getInstance().observeAVChatState(stateObserver, false);
        //        }

        LogUtil.i(TAG, "observe rtc state done");

        // notification observer
        if (notificationObserver != null) {
            AVChatManager.getInstance().observeControlNotification(notificationObserver, false);
        }
        notificationObserver = new Observer<AVChatControlEvent>() {

            @Override
            public void onEvent(AVChatControlEvent event) {
                final String account = event.getAccount();
                if (AVChatControlCommand.NOTIFY_VIDEO_ON == event.getControlCommand()) {
                    onVideoLive(account);
                } else if (AVChatControlCommand.NOTIFY_VIDEO_OFF == event.getControlCommand()) {
                    onVideoLiveEnd(account);
                }
            }
        };
        AVChatManager.getInstance().observeControlNotification(notificationObserver, true);

        // join
        AVChatManager.getInstance().setParameter(AVChatParameters.KEY_SESSION_MULTI_MODE_USER_ROLE, AVChatUserRole.NORMAL);
        AVChatManager.getInstance().setParameter(AVChatParameters.KEY_AUDIO_REPORT_SPEAKER, true);
        AVChatManager.getInstance().setParameter(AVChatParameters.KEY_VIDEO_FIXED_CROP_RATIO,
                AVChatVideoCropRatio.CROP_RATIO_1_1);

        AVChatConfigs avChatConfigs = new AVChatConfigs(getContext());
        try {
            AVChatManager.getInstance().setParameter(AVChatParameters.KEY_SERVER_AUDIO_RECORD,
                    avChatConfigs.isServerRecordAudio());
            AVChatManager.getInstance().setParameter(AVChatParameters.KEY_SERVER_VIDEO_RECORD,
                    avChatConfigs.isServerRecordVideo());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        AVChatManager.getInstance().joinRoom2(roomId, AVChatType.VIDEO,
                new AVChatCallback<AVChatData>() {
                    @Override
                    public void onSuccess(AVChatData data) {
                        chatId = data.getChatId();
                        LogUtil.i(TAG,
                                "join room success, roomId=" + roomId + ", chatId=" + chatId);
                    }

                    @Override
                    public void onFailed(int code) {
                        onJoinRoomFailed(code, null);
                        LogUtil.i(TAG, "join room failed, code=" + code + ", roomId=" + roomId);
                    }

                    @Override
                    public void onException(Throwable exception) {
                        onJoinRoomFailed(-1, exception);
                        LogUtil.i(TAG,
                                "join room failed, e=" + exception.getMessage() + ", roomId=" + roomId);
                    }
                });
        LogUtil.i(TAG, "start join room, roomId=" + roomId);
    }

    private SimpleAVChatStateObserver stateObserver = new SimpleAVChatStateObserver() {
        @Override
        public void onJoinedChannel(int code, String audioFile, String videoFile, int i) {
            if (code == 200) {
                onJoinRoomSuccess();
            } else {
                onJoinRoomFailed(code, null);
            }
        }

        @Override
        public void onUserJoined(String account) {
            onAVChatUserJoined(account);
        }

        @Override
        public void onUserLeave(String account, int event) {
            onAVChatUserLeave(account);
        }

        @Override
        public void onReportSpeaker(Map<String, Integer> speakers, int mixedEnergy) {
            onAudioVolume(speakers);
        }

        @Override
        public void onAudioRecordingCompletion(String filePath) {
            if (isCreateMDT) {
                LogUtil.i(TAG, "????????????...filePath:" + filePath);
                //??????????????????
                if (!TextUtils.isEmpty(filePath)) {
                    VoiceBean voiceBean = new VoiceBean();
                    voiceBean.setUrl(filePath);
                    voiceBean.setDuration(String.valueOf(seconds));
                    EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.FILE).setData(voiceBean).build());
                }
            }
        }
    };

    private void onJoinRoomSuccess() {
        startTimer();
        startLocalPreview();
        startTimerForCheckReceivedCall();
        LogUtil.i(TAG, "team avchat running..." + ", roomId=" + roomId);
    }

    private void onJoinRoomFailed(int code, Throwable e) {
        if (code == ResponseCode.RES_ENONEXIST) {
            showToast("?????????????????????");
        } else {
            showToast("join room failed, code=" + code + ", e=" + (e == null ? "" :
                    e.getMessage()));
        }
        endConsultation();
    }

    public void onAVChatUserJoined(String account) {
        int index = getItemIndex(account);
        if (index >= 0) {
            TeamAVChatItem item = data.get(index);
            IVideoRender surfaceView = adapter.getViewHolderSurfaceView(item);
            if (surfaceView != null) {
                item.state = TeamAVChatItem.STATE.STATE_PLAYING;
                item.videoLive = true;
                adapter.notifyItemChanged(index);
                AVChatManager.getInstance().setupRemoteVideoRender(account, surfaceView, false,
                        AVChatVideoScalingType.SCALE_ASPECT_FIT);
            }
        }
        updateAudioMuteButtonState();

        if (isCreateMDT) {
            //??????????????????
            boolean startAudio = AVChatManager.getInstance().startAudioRecording();
            LogUtil.i(TAG, "startAudio..." + startAudio);
        }

        LogUtil.i(TAG, "on user joined, account=" + account);
    }

    public void onAVChatUserLeave(String account) {
        int index = getItemIndex(account);
        if (index >= 0) {
            TeamAVChatItem item = data.get(index);
            item.state = TeamAVChatItem.STATE.STATE_HANGUP;
            item.volume = 0;
            adapter.notifyItemChanged(index);
        }
        updateAudioMuteButtonState();

        LogUtil.i(TAG, "on user leave, account=" + account);
    }

    private void startLocalPreview() {
        if (data.size() > 1 && data.get(0).account.equalsIgnoreCase(AVChatKit.getAccount())) {
            IVideoRender surfaceView = adapter.getViewHolderSurfaceView(data.get(0));
            if (surfaceView != null) {
                AVChatManager.getInstance().setupLocalVideoRender(surfaceView, false,
                        AVChatVideoScalingType.SCALE_ASPECT_FIT);
                AVChatManager.getInstance().startVideoPreview();
                data.get(0).state = TeamAVChatItem.STATE.STATE_PLAYING;
                data.get(0).videoLive = true;
                adapter.notifyItemChanged(0);
            }
        }
    }

    /**
     * ************************************ ??????????????? ***************************************
     */

    private void onVideoLive(String account) {
        if (account.equals(AVChatKit.getAccount())) {
            return;
        }

        notifyVideoLiveChanged(account, true);
    }

    private void onVideoLiveEnd(String account) {
        if (account.equals(AVChatKit.getAccount())) {
            return;
        }

        notifyVideoLiveChanged(account, false);
    }

    private void notifyVideoLiveChanged(String account, boolean live) {
        int index = getItemIndex(account);
        if (index >= 0) {
            TeamAVChatItem item = data.get(index);
            item.videoLive = live;
            adapter.notifyItemChanged(index);
        }
    }

    private void onAudioVolume(Map<String, Integer> speakers) {
        if (speakers == null || speakers.size() == 0) {
            return;
        }
        for (TeamAVChatItem item : data) {
            if (speakers.containsKey(item.account)) {
                item.volume = speakers.get(item.account);
                adapter.updateVolumeBar(item);
            }
        }
    }

    private void updateSelfItemVideoState(boolean live) {
        int index = getItemIndex(AVChatKit.getAccount());
        if (index >= 0) {
            TeamAVChatItem item = data.get(index);
            item.videoLive = live;
            adapter.notifyItemChanged(index);
        }
    }

    private void hangup() {
        if (destroyRTC) {
            return;
        }

        if (timer != null) {
            timer.cancel();
        }

        try {
            if (isCreateMDT) {
                //??????????????????
                AVChatManager.getInstance().stopAudioRecording();
            }
            AVChatManager.getInstance().stopVideoPreview();
            AVChatManager.getInstance().disableVideo();
            AVChatManager.getInstance().leaveRoom2(roomId, new AVChatCallback<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    LogUtil.i(TAG, "onSuccess:??????room??????");
                }

                @Override
                public void onFailed(int code) {
                    LogUtil.i(TAG, "onFailed:??????room??????");
                }

                @Override
                public void onException(Throwable exception) {
                    LogUtil.i(TAG, "onException:" + exception.getMessage());
                }
            });
            AVChatManager.getInstance().disableRtc();
        } catch (Exception e) {
            e.printStackTrace();
        }

        destroyRTC = true;
        LogUtil.i(TAG, "destroy rtc & leave room, roomId=" + roomId);
    }

    /**
     * ************************************ ???????????? ***************************************
     */

    private void startTimer() {
        seconds = 0;
        timer = new Timer();
        timer.schedule(timerTask = new VideoTimeTask(), 0, 1000);
        timerText.setText("00:00");
    }

    private VideoTimeTask timerTask;

    class VideoTimeTask extends TimerTask {
        @Override
        public void run() {
            seconds++;
            int m = seconds / 60;
            int s = seconds % 60;
            final String time = String.format(Locale.CHINA, "%02d:%02d", m, s);

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    timerText.setText(time);
                }
            });
        }
    }

    private void startTimerForCheckReceivedCall() {
        mainHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int index = 0;
                for (TeamAVChatItem item : data) {
                    if (item.type == TYPE_DATA && item.state == TeamAVChatItem.STATE.STATE_WAITING) {
                        item.state = TeamAVChatItem.STATE.STATE_END;
                        adapter.notifyItemChanged(index);
                    }
                    index++;
                }
                checkAllHangUp();
            }
        }, CHECK_RECEIVED_CALL_TIMEOUT);
    }

    private void startAutoRejectTask() {
        if (autoRejectTask == null) {
            autoRejectTask = new Runnable() {
                @Override
                public void run() {
                    AVChatSoundPlayer.instance().stop();
                    releaseSource();
                    //                    getActivity().finish();
                }
            };
        }

        mainHandler.postDelayed(autoRejectTask, AUTO_REJECT_CALL_TIMEOUT);
    }

    private void cancelAutoRejectTask() {
        if (autoRejectTask != null) {
            mainHandler.removeCallbacks(autoRejectTask);
        }
    }

    /**
     * ????????????????????????????????????????????????????????????
     */
    private void checkAllHangUp() {
        for (TeamAVChatItem item : data) {
            if (item.account != null &&
                    !item.account.equals(AVChatKit.getAccount()) &&
                    item.state != TeamAVChatItem.STATE.STATE_END) {
                return;
            }
        }
        mainHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                hangup();
                releaseSource();
                //                getActivity().finish();
            }
        }, 200);
    }

    /**
     * ?????????
     */
    private void activeCallingNotifier(boolean active) {
        if (notifier != null) {
            if (destroyRTC) {
                notifier.activeCallingNotification(false);
            } else {
                notifier.activeCallingNotification(active);
            }
        }
    }

    /**
     * ************************************ ???????????? ***************************************
     */

    private View.OnClickListener settingBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int i = v.getId();
            if (i == R.id.avchat_switch_camera) {// ?????????????????????
                mVideoCapturer.switchCamera();

            } else if (i == R.id.avchat_enable_video) {// ??????
                AVChatManager.getInstance().muteLocalVideo(videoMute = !videoMute);
                // ??????????????????
                byte command = videoMute ? AVChatControlCommand.NOTIFY_VIDEO_OFF :
                        AVChatControlCommand.NOTIFY_VIDEO_ON;
                AVChatManager.getInstance().sendControlCommand(chatId, command, null);
                v.setBackgroundResource(videoMute ? R.drawable.t_avchat_camera_mute_selector :
                        R.drawable.t_avchat_camera_selector);
                updateSelfItemVideoState(!videoMute);

            } else if (i == R.id.avchat_enable_audio) {// ???????????????
                AVChatManager.getInstance().muteLocalAudio(microphoneMute = !microphoneMute);
                v.setBackgroundResource(microphoneMute ?
                        R.drawable.t_avchat_microphone_mute_selector :
                        R.drawable.t_avchat_microphone_selector);

            } else if (i == R.id.avchat_volume) {// ?????????????????????
                AVChatManager.getInstance().setSpeaker(speakerMode = !speakerMode);
                v.setBackgroundResource(speakerMode ? R.drawable.t_avchat_speaker_selector :
                        R.drawable.t_avchat_speaker_mute_selector);

            } else if (i == R.id.avchat_shield_user) {// ??????????????????
                disableUserAudio();

            } else if (i == R.id.hangup) {// ??????
                endConsultation();
                //                getActivity().finish();
            }
        }
    };

    private void updateAudioMuteButtonState() {
        boolean enable = false;
        for (TeamAVChatItem item : data) {
            if (item.state == TeamAVChatItem.STATE.STATE_PLAYING &&
                    !AVChatKit.getAccount().equals(item.account)) {
                enable = true;
                break;
            }
        }
        voiceMuteButton.setEnabled(enable);
        voiceMuteButton.invalidate();
    }

    private void disableUserAudio() {
        List<Pair<String, Boolean>> voiceMutes = new ArrayList<>();
        for (TeamAVChatItem item : data) {
            if (item.state == TeamAVChatItem.STATE.STATE_PLAYING &&
                    !AVChatKit.getAccount().equals(item.account)) {
                voiceMutes.add(new Pair<>(item.account,
                        AVChatManager.getInstance().isRemoteAudioMuted(item.account)));
            }
        }
        TeamAVChatVoiceMuteDialog dialog = new TeamAVChatVoiceMuteDialog(getContext(), teamId,
                voiceMutes);
        dialog.setTeamVoiceMuteListener(new TeamAVChatVoiceMuteDialog.TeamVoiceMuteListener() {
            @Override
            public void onVoiceMuteChange(List<Pair<String, Boolean>> voiceMuteAccounts) {
                if (voiceMuteAccounts != null) {
                    for (Pair<String, Boolean> voiceMuteAccount : voiceMuteAccounts) {
                        AVChatManager.getInstance().muteRemoteAudio(voiceMuteAccount.first,
                                voiceMuteAccount.second);
                    }
                }
            }
        });
        dialog.show();
    }

    /**
     * ************************************ ????????? ***************************************
     */

    private void initRecyclerView() {
        // ???????????????,??????????????????
        data = new ArrayList<>(accounts.size() + 1);
        for (String account : accounts) {
            if (account.equalsIgnoreCase(AVChatKit.getAccount())) {
                continue;
            }

            data.add(new TeamAVChatItem(TYPE_DATA, teamId, account));
        }

        TeamAVChatItem selfItem = new TeamAVChatItem(TYPE_DATA, teamId, AVChatKit.getAccount());
        selfItem.state = TeamAVChatItem.STATE.STATE_PLAYING; // ?????????????????????????????????
        data.add(0, selfItem);

        // ???????????????
        int holderLength = MAX_SUPPORT_ROOM_USERS_COUNT - data.size();
        for (int i = 0; i < holderLength; i++) {
            data.add(new TeamAVChatItem(teamId));
        }

        // RecyclerView
        adapter = new TeamAVChatAdapter(recyclerView, data);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.addItemDecoration(new SpacingDecoration(ScreenUtil.dip2px(1),
                ScreenUtil.dip2px(1), true));
    }

    private int getItemIndex(final String account) {
        int index = 0;
        boolean find = false;
        for (TeamAVChatItem item : data) {
            if (item.account.equalsIgnoreCase(account)) {
                find = true;
                break;
            }
            index++;
        }

        return find ? index : -1;
    }

    /**
     * ************************************ ???????????? ***************************************
     */

    private void checkPermission() {
        List<String> lackPermissions =
                AVChatManager.getInstance().checkPermission(getContext());
        if (lackPermissions.isEmpty()) {
            onBasicPermissionSuccess();
        } else {
            String[] permissions = new String[lackPermissions.size()];
            for (int i = 0; i < lackPermissions.size(); i++) {
                permissions[i] = lackPermissions.get(i);
            }
            MPermission.with(TeamAVChatFragment.this)
                    .setRequestCode(BASIC_PERMISSION_REQUEST_CODE)
                    .permissions(permissions)
                    .request();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        MPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @OnMPermissionGranted(BASIC_PERMISSION_REQUEST_CODE)
    public void onBasicPermissionSuccess() {
        onPermissionChecked();
    }

    @OnMPermissionDenied(BASIC_PERMISSION_REQUEST_CODE)
    @OnMPermissionNeverAskAgain(BASIC_PERMISSION_REQUEST_CODE)
    public void onBasicPermissionFailed() {
        Toast.makeText(getContext(), "????????????????????????????????????????????????????????????????????????????????????", Toast.LENGTH_SHORT).show();
        onPermissionChecked();
    }

    /**
     * ************************************ helper ***************************************
     */

    private void showToast(String content) {
        Toast.makeText(getContext(), content, Toast.LENGTH_SHORT).show();
    }

    /**
     * ?????????????????????
     */
    private Observer<StatusCode> userStatus = new Observer<StatusCode>() {

        @Override
        public void onEvent(StatusCode code) {
            if (code.wontAutoLogin()) {
                AVChatSoundPlayer.instance().stop();
                hangup();
                releaseSource();
                //                getActivity().finish();
            }
        }
    };

    public interface CreateRoomCallback {
        void success(String roomName);

        void failed(int code);
    }
}
