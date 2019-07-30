package com.keydom.ih_common.im.activity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.keydom.ih_common.R;
import com.keydom.ih_common.base.BaseActivity;
import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.ih_common.utils.ImageLoadingView;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.attachment.VideoAttachment;
import com.netease.nimlib.sdk.msg.constant.AttachStatusEnum;
import com.netease.nimlib.sdk.msg.model.AttachmentProgress;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class PlayVideoActivity extends BaseActivity implements SeekBar.OnSeekBarChangeListener, View.OnClickListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {

    private SurfaceView mSurfaceView;
    private ImageView mPlayImage;
    private ImageView mPreviewImage;
    private TextView mTime;
    private TextView mTotalTime;
    private SeekBar mSeekBar;
    private ImageLoadingView mLoadingView;

    private MediaPlayer mPlayer;
    private SurfaceHolder mHolder;

    private int duration;

    private Disposable mDisposable;
    private IMMessage mMessage;
    private VideoAttachment attachment;
    private String videoFilePath;
    private boolean isShow = false;
    private long maxDuration;

    private int playState = PLAY_STATE_STOP;

    private final static int PLAY_STATE_PLAYING = 1;

    private final static int PLAY_STATE_STOP = 2;

    private final static int PLAY_STATE_PAUSE = 3;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_paly_video;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mSurfaceView = findViewById(R.id.surface);
        mPlayImage = findViewById(R.id.play);
        mPreviewImage = findViewById(R.id.preview_image);
        mTime = findViewById(R.id.time);
        mTotalTime = findViewById(R.id.time_total);
        mSeekBar = findViewById(R.id.seek_bar);
        mLoadingView = findViewById(R.id.loading_view);

        mTime.setText("00:00");

        mHolder = mSurfaceView.getHolder();
        mHolder.addCallback(new CallBack());

        mMessage = (IMMessage) getIntent().getSerializableExtra("IMMessage");
        attachment = (VideoAttachment) mMessage.getAttachment();
        GlideUtils.load(mPreviewImage, attachment.getThumbUrl(), 0, 0, false, null);

        mSurfaceView.setOnClickListener(this);
        mSeekBar.setOnSeekBarChangeListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPlayer = new MediaPlayer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopPlay();
    }

    private void stopPlay() {
        if (mPlayer != null) {
            if (mPlayer.isPlaying()) {
                mPlayer.stop();
            }
            mPlayer.reset();
            mPlayer.release();
            mPlayer = null;
        }
    }

    protected void pauseVideo() {
        if (mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.pause();
            playState = PLAY_STATE_PAUSE;
        }
    }

    protected void resumeVideo() {
        if (mPlayer != null) {
            if (!mPlayer.isPlaying()) {
                mPlayer.start();
                playState = PLAY_STATE_PLAYING;
            }
        }
    }

    protected void playVideo() {
        if (mPlayer != null) {
            if (mPlayer.isPlaying()) {
                mPlayer.stop();
            } else {
                mPlayer.setDisplay(mHolder);
            }
            mPlayer.reset();
            try {
                mPlayer.setDataSource(videoFilePath);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            mPlayer.setOnPreparedListener(this);
            mPlayer.setOnCompletionListener(this);
            mPlayer.prepareAsync();
            mSeekBar.setMax(mPlayer.getDuration());
            mSeekBar.setProgress(0);
            mTotalTime.setText(getFormatterTime(maxDuration));
        }
    }

    private String getFormatterTime(long duration) {
        int time = (int) Math.ceil(duration / 1000.0);
        if (time < 10) {
            return String.format("00:0%d", time);
        } else if (time <= 59) {
            return String.format("00:%d", time);
        } else {
            int minute = time / 60;
            int second = time % 60;
            if (second < 10) {
                return String.format("%d:0%d", minute, time);
            } else {
                return String.format("%d:%d", minute, time);
            }
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        Logger.e("----->" + fromUser + progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        pauseVideo();
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        int progress = seekBar.getProgress();
        if (mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.seekTo(progress);
        }
        resumeVideo();
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.play) {
            switch (playState) {
                case PLAY_STATE_PAUSE:
                    resumeVideo();
                    break;
                case PLAY_STATE_PLAYING:
                    pauseVideo();
                    break;
                case PLAY_STATE_STOP:
                    playVideo();
                    break;
                default:
            }
        } else if (id == R.id.surface) {
            isShow = !isShow;
            setShowIcon(isShow);
        }
    }

    private void setShowIcon(boolean isShow) {
        mPlayImage.setVisibility(isShow ? View.VISIBLE : View.GONE);
        mTime.setVisibility(isShow ? View.VISIBLE : View.GONE);
        mTotalTime.setVisibility(isShow ? View.VISIBLE : View.GONE);
        mSeekBar.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onPrepared(final MediaPlayer mp) {
        mp.start();
        mDisposable = Flowable.intervalRange(0, duration, 0, 200, TimeUnit.MILLISECONDS)
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {

                    }
                }).subscribe();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        playState = PLAY_STATE_STOP;
        setShowIcon(true);
    }

    private class CallBack implements SurfaceHolder.Callback {

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            if ((mMessage.getAttachStatus() == AttachStatusEnum.transferred && !TextUtils.isEmpty(attachment.getPath()))) {
                mPreviewImage.setVisibility(View.GONE);
                mLoadingView.setVisibility(View.GONE);
                videoFilePath = attachment.getPath();
                maxDuration = attachment.getDuration();
                playVideo();
            } else {
                mPreviewImage.setVisibility(View.VISIBLE);
                mLoadingView.setVisibility(View.VISIBLE);
                NIMClient.getService(MsgService.class).downloadAttachment(mMessage, false);
                EventBus.getDefault().register(this);
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAttachmentDownloadEvent(AttachmentProgress progress) {
        if (progress.getUuid().equals(mMessage.getUuid())) {
            mLoadingView.setProgress((double) progress.getTransferred() / (double) progress.getTotal());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAttachmentStatusEvent(IMMessage message) {
        if (message.getUuid().equals(mMessage.getUuid())) {
            if (message.getAttachStatus() == AttachStatusEnum.transferred && !TextUtils.isEmpty(((VideoAttachment) mMessage.getAttachment()).getPath())) {
                mLoadingView.setVisibility(View.GONE);
                mPreviewImage.setVisibility(View.GONE);
                videoFilePath = ((VideoAttachment) mMessage.getAttachment()).getPath();
                maxDuration = ((VideoAttachment) mMessage.getAttachment()).getDuration();
                playVideo();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
        if (mDisposable != null) {
            mDisposable.dispose();
            mDisposable = null;
        }
    }
}
