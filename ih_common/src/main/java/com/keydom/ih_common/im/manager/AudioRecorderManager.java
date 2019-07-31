package com.keydom.ih_common.im.manager;


import android.Manifest;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.keydom.ih_common.R;
import com.netease.nimlib.sdk.media.record.AudioRecorder;
import com.netease.nimlib.sdk.media.record.IAudioRecordCallback;
import com.netease.nimlib.sdk.media.record.RecordType;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class AudioRecorderManager {
    private int maxDuration = DEFAULT_MAX_DURATION;
    private static final int DEFAULT_MAX_DURATION = 60;
    private static final String TAG = "AudioRecorderManager";

    private AppCompatActivity mActivity;

    /**************语音Pop相关************/
    private PopupWindow mRecordWindow;
    private ImageView mStateIV;
    private TextView mStateTV;
    private TextView mTimerTV;
    private int counter = 10;
    private Disposable mDisposable;
    private Disposable mDisposable2;
    /**
     * 是否按着
     */
    private boolean touched = false;
    private boolean started;
    private boolean cancelled;
    /**
     * 录音是否进入倒计时
     */
    private boolean isCountdown = false;

    private AudioRecorder mAudioRecorder;
    private boolean mUpDirection;

    private Handler mHandler;

    public boolean isTouched() {
        return touched;
    }

    public void setTouched(boolean touched) {
        this.touched = touched;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public boolean isUpDirection() {
        return mUpDirection;
    }

    public void setUpDirection(boolean upDirection) {
        mUpDirection = upDirection;
    }

    public AudioRecorder getAudioRecorder() {
        return mAudioRecorder;
    }

    public void setAudioRecorder(AudioRecorder audioRecorder) {
        mAudioRecorder = audioRecorder;
    }

    private AudioRecorderManager() {
    }

    public void init(AppCompatActivity context,
                     IAudioRecordCallback cb) {
        mActivity = context;
        mAudioRecorder = new AudioRecorder(context, RecordType.AAC, maxDuration, cb);
    }

    public int getMaxDuration() {
        return maxDuration;
    }

    public void setMaxDuration(int maxDuration) {
        this.maxDuration = maxDuration;
    }


    /**
     * 开始语音录制
     */
    @SuppressLint("CheckResult")
    public void onStartAudioRecord() {
        RxPermissions rxPermissions = new RxPermissions(mActivity);
        rxPermissions.request(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) throws Exception {
                        if (granted) {
                            mActivity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                            mAudioRecorder.startRecord();
                            cancelled = false;
                        } else {
                            Toast.makeText(mActivity, "发送语音消息，需要开启录音权限", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    /**
     * 结束语音录制
     *
     * @param cancel
     */
    public void onEndAudioRecord(boolean cancel) {
        started = false;
        mActivity.getWindow().setFlags(0, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mAudioRecorder.completeRecord(cancel);
        if (mRecordWindow != null) {
            mDisposable.dispose();
            mDisposable2.dispose();
            mRecordWindow.dismiss();
            isCountdown = false;
        }
    }

    /**
     * 取消语音录制
     *
     * @param cancel
     */
    public void cancelAudioRecord(boolean cancel) {
        // reject
        if (!started) {
            return;
        }
        // no change
        if (cancelled == cancel) {
            return;
        }

        cancelled = cancel;
        if (cancel) {
            setCancelView();
        } else {
            setRecordingView();
        }
    }

    public void initAudioPopView(View root) {
        mHandler = new Handler(root.getHandler().getLooper(), null);
        LayoutInflater inflater = LayoutInflater.from(mActivity);
        View view = inflater.inflate(R.layout.rc_wi_vo_popup, null);
        mStateIV = view.findViewById(R.id.rc_audio_state_image);
        mStateTV = view.findViewById(R.id.rc_audio_state_text);
        mTimerTV = view.findViewById(R.id.rc_audio_timer);
        mRecordWindow = new PopupWindow(view, -1, -1);
        mRecordWindow.showAtLocation(root, 17, 0, 0);
        mRecordWindow.setFocusable(true);
        mRecordWindow.setOutsideTouchable(false);
        mRecordWindow.setTouchable(false);
        setRecordingView();

        mDisposable = Flowable.intervalRange(0, maxDuration * 10, 0, 100, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if (!mUpDirection && touched && aLong < maxDuration * 10 - counter * 10) {
                            audioDBChanged();
                        }
                    }
                }).subscribe();
        mDisposable2 = Flowable.intervalRange(0, maxDuration, 0, 1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if (aLong >= maxDuration - counter) {
                            isCountdown = true;
                        }
                        if (!mUpDirection && aLong >= maxDuration - counter) {
                            setTimeoutView((int) (maxDuration - aLong));
                        }
                    }
                }).subscribe();
    }

    public void setTimeoutView(int counter) {
        if (counter > 0) {
            if (mRecordWindow != null) {
                mStateIV.setVisibility(View.GONE);
                mStateTV.setVisibility(View.VISIBLE);
                mStateTV.setText(R.string.im_voice_rec);
                mStateTV.setBackgroundColor(Color.TRANSPARENT);
                mTimerTV.setText(String.format("%s", counter));
                mTimerTV.setVisibility(View.VISIBLE);
            }
        } else if (mRecordWindow != null) {
            mStateIV.setVisibility(View.VISIBLE);
            mStateIV.setImageResource(R.mipmap.rc_ic_volume_wraning);
            mStateTV.setText(R.string.im_voice_too_long);
            mTimerTV.setVisibility(View.GONE);
            mStateTV.setBackgroundColor(Color.TRANSPARENT);
            popDismiss();
        }

    }

    /**
     * 延时500ms关闭Pop
     */
    private void popDismiss() {

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mDisposable.dispose();
                mDisposable2.dispose();
                mRecordWindow.dismiss();
                isCountdown = false;
            }
        }, 500);

//        ThreadPoolExecutor executor = new ThreadPoolExecutor(1,
//                1,
//                0,
//                TimeUnit.MILLISECONDS,
//                new LinkedBlockingDeque<Runnable>(),
//                new ThreadFactory() {
//                    @Override
//                    public Thread newThread(@NonNull Runnable r) {
//                        Thread t = new Thread();
//                        t.setName("语音发送线程—1");
//                        return t;
//                    }
//                },
//                new ThreadPoolExecutor.AbortPolicy());
//        executor.execute(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(500);
//                    mActivity.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//
//                        }
//                    });
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }

    private void setRecordingView() {
        if (mRecordWindow != null && !isCountdown) {
            mStateIV.setVisibility(View.VISIBLE);
            mStateIV.setImageResource(R.mipmap.rc_ic_volume_1);
            mStateTV.setVisibility(View.VISIBLE);
            mStateTV.setText(R.string.im_voice_rec);
            mTimerTV.setVisibility(View.GONE);
            mStateTV.setBackgroundColor(Color.TRANSPARENT);
        }

    }

    private void setCancelView() {
        if (mRecordWindow != null) {
            mTimerTV.setVisibility(View.GONE);
            mStateIV.setVisibility(View.VISIBLE);
            mStateIV.setImageResource(R.mipmap.rc_ic_volume_cancel);
            mStateTV.setVisibility(View.VISIBLE);
            mStateTV.setText(R.string.im_voice_cancel);
            mStateTV.setBackgroundResource(R.drawable.rc_corner_voice_style);
        }

    }

    private void audioDBChanged() {
        if (mAudioRecorder != null) {
            int db = mAudioRecorder.getCurrentRecordMaxAmplitude() / 600;
            switch (db / 5) {
                case 0:
                    mStateIV.setImageResource(R.mipmap.rc_ic_volume_1);
                    break;
                case 1:
                    mStateIV.setImageResource(R.mipmap.rc_ic_volume_2);
                    break;
                case 2:
                    mStateIV.setImageResource(R.mipmap.rc_ic_volume_3);
                    break;
                case 3:
                    mStateIV.setImageResource(R.mipmap.rc_ic_volume_4);
                    break;
                case 4:
                    mStateIV.setImageResource(R.mipmap.rc_ic_volume_5);
                    break;
                case 5:
                    mStateIV.setImageResource(R.mipmap.rc_ic_volume_6);
                    break;
                case 6:
                    mStateIV.setImageResource(R.mipmap.rc_ic_volume_7);
                    break;
                default:
                    mStateIV.setImageResource(R.mipmap.rc_ic_volume_8);
            }
        }

    }

    public void setTimeShortView() {
        mStateIV.setImageResource(R.mipmap.rc_ic_volume_wraning);
        mStateTV.setText(R.string.im_voice_short);
    }

    public static AudioRecorderManager getInstance() {
        return SingletonHolder.instance;
    }

    static class SingletonHolder {
        static AudioRecorderManager instance = new AudioRecorderManager();

        SingletonHolder() {
        }
    }
}
