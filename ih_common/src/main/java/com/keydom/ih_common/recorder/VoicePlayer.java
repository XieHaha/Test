package com.keydom.ih_common.recorder;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.text.TextUtils;

import java.io.IOException;

/**
 * Created by zhangsong on 17-10-20.
 */

public class VoicePlayer {
    private static final String TAG = "ConcurrentMediaPlayer";

    private static VoicePlayer instance = null;

    private AudioManager audioManager;
    private MediaPlayer mediaPlayer;

    private MediaPlayer.OnCompletionListener onCompletionListener;

    public static VoicePlayer getInstance(Context context) {
        if (instance == null) {
            synchronized (VoicePlayer.class) {
                if (instance == null) {
                    instance = new VoicePlayer(context);
                }
            }
        }
        return instance;
    }

    public MediaPlayer getPlayer() {
        return mediaPlayer;
    }

    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    public void play(final String voiceUrl, final MediaPlayer.OnCompletionListener listener) {
        if (TextUtils.isEmpty(voiceUrl)) {
            return;
        }

        if (mediaPlayer.isPlaying()) {
            stop();
        }

        onCompletionListener = listener;

        try {
            setSpeaker();
            mediaPlayer.setDataSource(voiceUrl);
            mediaPlayer.prepare();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stop();
                    onCompletionListener = null;
                }
            });
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        mediaPlayer.stop();
        mediaPlayer.reset();

        /**
         * This listener is to stop the voice play animation currently, considered the following
         * 3 conditions:
         *
         * 1.A new voice item is clicked to play, to stop the previous playing voice item animation.
         * 2.The voice is play complete, to stop it's voice play animation.
         * 3.Press the voice record button will stop the voice play and must stop voice play
         * animation.
         *
         */
        if (onCompletionListener != null) {
            onCompletionListener.onCompletion(mediaPlayer);
        }
    }

    private VoicePlayer(Context cxt) {
        Context baseContext = cxt.getApplicationContext();
        audioManager = (AudioManager) baseContext.getSystemService(Context.AUDIO_SERVICE);
        mediaPlayer = new MediaPlayer();
    }

    private void setSpeaker() {
        //        boolean speakerOn = EaseUI.getInstance().getSettingsProvider().isSpeakerOpened();
        //        if (speakerOn) {
        //            audioManager.setMode(AudioManager.MODE_NORMAL);
        //            audioManager.setSpeakerphoneOn(true);
        //            mediaPlayer.setAudioStreamType(AudioManager.STREAM_RING);
        //        } else {
        //            audioManager.setSpeakerphoneOn(false);// 关闭扬声器
        //            // 把声音设定成Earpiece（听筒）出来，设定为正在通话中
        //            audioManager.setMode(AudioManager.MODE_IN_CALL);
        //            mediaPlayer.setAudioStreamType(AudioManager.STREAM_VOICE_CALL);
        //        }

        audioManager.setMode(AudioManager.MODE_NORMAL);
        audioManager.setSpeakerphoneOn(true);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_RING);
    }
}
