package com.keydom.ih_common.im.manager;

import com.keydom.Common;
import com.netease.nimlib.sdk.media.player.AudioPlayer;
import com.netease.nimlib.sdk.media.player.OnPlayListener;

public class AudioPlayerManager {

    private AudioPlayer mAudioPlayer = new AudioPlayer(Common.INSTANCE.getApplication());
    private String mAudioFile;
    private OnPlayListener mListener;

    private AudioPlayerManager() {
    }

    public static AudioPlayerManager getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * 设置音频来源
     *
     * @param audioFile 待播放音频的文件路径
     */
    public AudioPlayerManager setDataSource(String audioFile) {
        mAudioFile = audioFile;
        mAudioPlayer.setDataSource(audioFile);
        return this;
    }

    /**
     * 设置播放监听
     *
     * @param listener
     */
    public AudioPlayerManager setOnPlayListener(OnPlayListener listener) {
        mListener = listener;
        mAudioPlayer.setOnPlayListener(listener);
        return this;
    }

    public boolean isPlaying() {
        return mAudioPlayer.isPlaying();
    }

    public void stopPlay() {
        mAudioPlayer.stop();
    }

    /**
     * 开始播放。需要传入一个 Stream Type 参数，表示是用听筒播放还是扬声器。取值可参见
     * android.media.AudioManager#STREAM_***
     * AudioManager.STREAM_VOICE_CALL 表示使用听筒模式
     * AudioManager.STREAM_MUSIC 表示使用扬声器模式
     */
    public void start(int streamType){
        mAudioPlayer.start(streamType);
    }

    static class SingletonHolder {
        static AudioPlayerManager instance = new AudioPlayerManager();

        SingletonHolder() {
        }
    }

}
