
package com.keydom.ih_common.im.manager;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.keydom.ih_common.R;
import com.keydom.ih_common.im.ImClient;
import com.keydom.ih_common.im.activity.CommonVideoCallActivity;
import com.keydom.ih_common.im.config.AVChatConfigs;
import com.keydom.ih_common.im.listener.AVChatControllerCallback;
import com.keydom.ih_common.im.listener.AVSwitchListener;
import com.keydom.ih_common.im.model.AVChatExitCode;
import com.keydom.ih_common.im.model.CallStateEnum;
import com.netease.nimlib.sdk.avchat.AVChatCallback;
import com.netease.nimlib.sdk.avchat.AVChatManager;
import com.netease.nimlib.sdk.avchat.constant.AVChatControlCommand;
import com.netease.nimlib.sdk.avchat.constant.AVChatType;
import com.netease.nimlib.sdk.avchat.model.AVChatData;
import com.netease.nimlib.sdk.avchat.model.AVChatNotifyOption;
import com.netease.nimlib.sdk.avchat.video.AVChatCameraCapturer;
import com.netease.nimlib.sdk.avchat.video.AVChatVideoCapturerFactory;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 音视频控制器：用于实现音视频拨打接听，音视频切换的具体功能实现
 * Created by winnie on 2017/12/10.
 */

public class AVChatController {
    private static final String TAG = AVChatController.class.getSimpleName();

    protected CommonVideoCallActivity context;
    private long timeBase = 0;
    private AVChatData avChatData;
    private AVChatCameraCapturer mVideoCapturer;
    private AVChatConfigs avChatConfigs;


    public AtomicBoolean isCallEstablish = new AtomicBoolean(false);
    private boolean destroyRTC = false;
    private boolean isRecording = false;

    private boolean needRestoreLocalVideo = false;
    private boolean needRestoreLocalAudio = false;


    /**
     * *************************** 初始化 ************************
     */
    public AVChatController(CommonVideoCallActivity context, AVChatData avChatData, boolean isRuntime) {
        this.context = context;
        this.avChatData = avChatData;
        this.avChatConfigs = new AVChatConfigs(context, isRuntime);
    }

    /**
     * 恢复视频和语音发送
     */
    public void resumeVideo() {
        if (needRestoreLocalVideo) {
            AVChatManager.getInstance().muteLocalVideo(false);
            needRestoreLocalVideo = false;
        }

        if (needRestoreLocalAudio) {
            AVChatManager.getInstance().muteLocalAudio(false);
            needRestoreLocalAudio = false;
        }

    }

    /**
     * 关闭视频和语音发送.
     */
    public void pauseVideo() {

        if (!AVChatManager.getInstance().isLocalVideoMuted()) {
            AVChatManager.getInstance().muteLocalVideo(true);
            needRestoreLocalVideo = true;
        }

        if (!AVChatManager.getInstance().isLocalAudioMuted()) {
            AVChatManager.getInstance().muteLocalAudio(true);
            needRestoreLocalAudio = true;
        }
    }

    /**
     * 拨打
     */

    public void doCalling(String account, final AVChatType avChatType, final AVChatControllerCallback<AVChatData> callback) {

        AVChatManager.getInstance().enableRtc();
        AVChatManager.getInstance().setParameters(avChatConfigs.getAvChatParameters());

        if (avChatType == AVChatType.VIDEO) {
            AVChatManager.getInstance().enableVideo();
            if (mVideoCapturer == null) {
                mVideoCapturer = AVChatVideoCapturerFactory.createCameraPolicyCapturer(true);
                if (AVChatManager.getInstance().setupVideoCapturer(mVideoCapturer)) {
                    AVChatManager.getInstance().startVideoPreview();
                }
            }
        }
        AVChatNotifyOption notifyOption = new AVChatNotifyOption();
        AVChatManager.getInstance().call2(account, avChatType, notifyOption, new AVChatCallback<AVChatData>() {
            @Override
            public void onSuccess(AVChatData data) {
                avChatData = data;
                callback.onSuccess(data);
            }

            @Override
            public void onFailed(int code) {
                Log.d(TAG, "avChat call failed code->" + code);
                closeRtc(avChatType == AVChatType.VIDEO ? CallStateEnum.VIDEO : CallStateEnum.AUDIO);
                callback.onFailed(code, "");
            }

            @Override
            public void onException(Throwable exception) {
                Log.d(TAG, "avChat call onException->" + exception);
                closeRtc(avChatType == AVChatType.VIDEO ? CallStateEnum.VIDEO : CallStateEnum.AUDIO);
                callback.onFailed(-1, exception.toString());
            }
        });
    }

    /**
     * 接听
     *
     * @param avChatType
     * @param callback
     */
    public void receive(final AVChatType avChatType, final AVChatControllerCallback<Void> callback) {

        AVChatManager.getInstance().enableRtc();
        AVChatManager.getInstance().setParameters(avChatConfigs.getAvChatParameters());

        if (avChatType == AVChatType.VIDEO) {
            AVChatManager.getInstance().enableVideo();
            if (mVideoCapturer == null) {
                mVideoCapturer = AVChatVideoCapturerFactory.createCameraPolicyCapturer(true);
                AVChatManager.getInstance().setupVideoCapturer(mVideoCapturer);
            }
            AVChatManager.getInstance().startVideoPreview();
        }

        AVChatManager.getInstance().accept2(avChatData.getChatId(), new AVChatCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.i(TAG, "accept success");

                isCallEstablish.set(true);

                callback.onSuccess(aVoid);
            }

            @Override
            public void onFailed(int code) {
                if (code == -1) {
                    Toast.makeText(context, "本地音视频启动失败", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "建立连接失败", Toast.LENGTH_SHORT).show();
                }
                Log.e(TAG, "accept onFailed->" + code);
                handleAcceptFailed(avChatType == AVChatType.VIDEO ?
                        CallStateEnum.VIDEO_CONNECTING : CallStateEnum.AUDIO);
                callback.onFailed(code, "");
            }

            @Override
            public void onException(Throwable exception) {
                Log.d(TAG, "accept exception->" + exception);
                handleAcceptFailed(avChatType == AVChatType.VIDEO ?
                        CallStateEnum.VIDEO_CONNECTING : CallStateEnum.AUDIO);
                callback.onFailed(-1, exception.toString());
            }
        });
        AVChatSoundPlayer.instance().stop();
    }

    public void toggleMute() {
        if (!AVChatManager.getInstance().isLocalAudioMuted()) { // isMute是否处于静音状态
            // 关闭音频
            AVChatManager.getInstance().muteLocalAudio(true);
        } else {
            // 打开音频
            AVChatManager.getInstance().muteLocalAudio(false);
        }
    }

    /**
     * ********************* 音视频切换 ***********************
     */

    /**
     * 发送视频切换为音频命令
     */
    public void switchVideoToAudio(final AVSwitchListener avSwitchListener) {
        AVChatManager.getInstance().sendControlCommand(avChatData.getChatId(), AVChatControlCommand.SWITCH_VIDEO_TO_AUDIO, new AVChatCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "videoSwitchAudio onSuccess");
                //关闭视频
                AVChatManager.getInstance().stopVideoPreview();
                AVChatManager.getInstance().disableVideo();

                // 界面布局切换。
                avSwitchListener.onVideoToAudio();
            }

            @Override
            public void onFailed(int code) {
                Log.d(TAG, "videoSwitchAudio onFailed");
            }

            @Override
            public void onException(Throwable exception) {
                Log.d(TAG, "videoSwitchAudio onException");
            }
        });
    }

    // 发送音频切换为视频命令
    public void switchAudioToVideo(final AVSwitchListener avSwitchListener) {
        AVChatManager.getInstance().sendControlCommand(avChatData.getChatId(), AVChatControlCommand.SWITCH_AUDIO_TO_VIDEO, new AVChatCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "requestSwitchToVideo onSuccess");
                avSwitchListener.onAudioToVideo();
            }

            @Override
            public void onFailed(int code) {
                Log.d(TAG, "requestSwitchToVideo onFailed" + code);
            }

            @Override
            public void onException(Throwable exception) {
                Log.d(TAG, "requestSwitchToVideo onException" + exception);
            }
        });
    }

    // 发送同意从音频切换为视频的命令
    public void receiveAudioToVideo(final AVSwitchListener avSwitchListener) {
        AVChatManager.getInstance().sendControlCommand(avChatData.getChatId(), AVChatControlCommand.SWITCH_AUDIO_TO_VIDEO_AGREE, new AVChatCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "receiveAudioToVideo onSuccess");

                avSwitchListener.onReceiveAudioToVideoAgree();
            }

            @Override
            public void onFailed(int code) {
                Log.d(TAG, "receiveAudioToVideo onFailed");
            }

            @Override
            public void onException(Throwable exception) {
                Log.d(TAG, "receiveAudioToVideo onException");
            }
        });
    }


    /**
     * ********************* 其他设置 **************************
     */

    /**
     * 切换摄像头（主要用于前置和后置摄像头切换）
     */
    public void switchCamera() {
        mVideoCapturer.switchCamera();
    }

    /**
     * ********************** 挂断相关操作 **********************
     */
    public void hangUp(int type) {
        ImClient.stopTimer();
        if (destroyRTC) {
            return;
        }
        boolean flag = (type == AVChatExitCode.HANGUP
                || type == AVChatExitCode.PEER_NO_RESPONSE
                || type == AVChatExitCode.CANCEL
                || type == AVChatExitCode.REJECT)
                && avChatData != null;
        if (flag) {
            AVChatManager.getInstance().hangUp2(avChatData.getChatId(), new AVChatCallback<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                }

                @Override
                public void onFailed(int code) {
                    Log.d(TAG, "hangup onFailed->" + code);
                }

                @Override
                public void onException(Throwable exception) {
                    Log.d(TAG, "hangup onException->" + exception);
                }
            });
        }
        AVChatManager.getInstance().disableRtc();
        destroyRTC = true;
        AVChatSoundPlayer.instance().stop();
        showQuitToast(type);
    }

    /**
     * 收到挂断通知，自己的处理
     */
    public void onHangUp(int exitCode) {
        if (destroyRTC) {
            return;
        }
        AVChatSoundPlayer.instance().stop();
        AVChatManager.getInstance().disableRtc();
        destroyRTC = true;
        showQuitToast(exitCode);
        ((Activity) context).finish();
    }

    /**
     * 显示退出toast
     */
    public void showQuitToast(int code) {
        switch (code) {
            case AVChatExitCode.NET_CHANGE: // 网络切换
            case AVChatExitCode.NET_ERROR: // 网络异常
            case AVChatExitCode.CONFIG_ERROR: // 服务器返回数据错误
                Toast.makeText(context, R.string.avchat_net_error_then_quit, Toast.LENGTH_SHORT).show();
                break;
            case AVChatExitCode.REJECT:
                Toast.makeText(context, R.string.avchat_call_reject, Toast.LENGTH_SHORT).show();
                break;
            case AVChatExitCode.PEER_HANGUP:
            case AVChatExitCode.HANGUP:
                if (isCallEstablish.get()) {
                    Toast.makeText(context, R.string.avchat_call_finish, Toast.LENGTH_SHORT).show();
                }
                break;
            case AVChatExitCode.PEER_BUSY:
                Toast.makeText(context, R.string.avchat_peer_busy, Toast.LENGTH_SHORT).show();
                break;
            case AVChatExitCode.PROTOCOL_INCOMPATIBLE_PEER_LOWER:
                Toast.makeText(context, R.string.avchat_peer_protocol_low_version, Toast.LENGTH_SHORT).show();
                break;
            case AVChatExitCode.PROTOCOL_INCOMPATIBLE_SELF_LOWER:
                Toast.makeText(context, R.string.avchat_local_protocol_low_version, Toast.LENGTH_SHORT).show();
                break;
            case AVChatExitCode.INVALIDE_CHANNELID:
                Toast.makeText(context, R.string.avchat_invalid_channel_id, Toast.LENGTH_SHORT).show();
                break;
            case AVChatExitCode.LOCAL_CALL_BUSY:
                Toast.makeText(context, R.string.avchat_local_call_busy, Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    private void closeRtc(CallStateEnum callingState) {
        if (destroyRTC) {
            return;
        }
        if (callingState == CallStateEnum.OUTGOING_VIDEO_CALLING || callingState == CallStateEnum.VIDEO) {
            AVChatManager.getInstance().stopVideoPreview();
            AVChatManager.getInstance().disableVideo();
        }
        AVChatManager.getInstance().disableRtc();
        destroyRTC = true;
        AVChatSoundPlayer.instance().stop();
    }

    private void handleAcceptFailed(CallStateEnum callingState) {
        if (callingState == CallStateEnum.VIDEO_CONNECTING) {
            AVChatManager.getInstance().stopVideoPreview();
            AVChatManager.getInstance().disableVideo();
        }
        hangUp(AVChatExitCode.CANCEL);
    }

    /**
     * ************************* 其他数据 ***********************
     */

    public long getTimeBase() {
        return timeBase;
    }

    public void setTimeBase(long timeBase) {
        this.timeBase = timeBase;
    }

    public AVChatData getAvChatData() {
        return avChatData;
    }

    public void setAvChatData(AVChatData avChatData) {
        this.avChatData = avChatData;
    }

    public boolean isRecording() {
        return isRecording;
    }

    public void setRecording(boolean recording) {
        isRecording = recording;
    }

}
