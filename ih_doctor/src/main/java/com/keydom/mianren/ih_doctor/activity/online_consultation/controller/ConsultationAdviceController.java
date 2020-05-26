package com.keydom.mianren.ih_doctor.activity.online_consultation.controller;

import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.im.manager.AudioPlayerManager;
import com.keydom.ih_common.im.manager.AudioRecorderManager;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.online_consultation.view.ConsultationAdviceView;
import com.keydom.mianren.ih_doctor.bean.ConsultationAdviceBean;
import com.keydom.ih_common.bean.VoiceBean;
import com.keydom.mianren.ih_doctor.net.ConsultationService;
import com.keydom.mianren.ih_doctor.net.MainApiService;
import com.netease.nimlib.sdk.media.record.IAudioRecordCallback;
import com.netease.nimlib.sdk.media.record.RecordType;
import com.orhanobut.logger.Logger;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @date 20/4/9 11:28
 * @des 会诊室-视频
 */
public class ConsultationAdviceController extends ControllerImpl<ConsultationAdviceView> implements View.OnClickListener, View.OnTouchListener {
    private IAudioRecordCallback mCallback;
    /**
     * 语音
     */
    private float mLastTouchY = 0;
    private boolean mUpDirection = false;
    private float mOffsetLimit = 0;

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.consultation_advice_commit_tv) {
            if (TextUtils.isEmpty(getView().getConsultationAdvice()) && getView().getConsultationVoiceAdvice().size() == 0) {
                ToastUtil.showMessage(mContext, "会诊意见不能为空");
                return;
            }
            commitConsultationAdvice();
        }
    }

    /**
     * 录音处理
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                v.setSelected(true);
                if (AudioPlayerManager.getInstance().isPlaying()) {
                    AudioPlayerManager.getInstance().stopPlay();
                }
                initCallback();
                AudioRecorderManager.getInstance().init((AppCompatActivity) mContext, mCallback);
                AudioRecorderManager.getInstance().setTouched(true);
                AudioRecorderManager.getInstance().onStartAudioRecord();
                mLastTouchY = event.getY();
                mUpDirection = false;
                break;
            case MotionEvent.ACTION_MOVE:
                //                if (mLastTouchY - event.getY() > mOffsetLimit && !mUpDirection) {
                //                    Logger.e(
                //                            "mLastTouchY - event.getY()=" + (mLastTouchY -
                //                            event.getY()));
                //                    AudioRecorderManager.getInstance().cancelAudioRecord(true);
                //                    mUpDirection = true;
                //                    AudioRecorderManager.getInstance().setUpDirection
                //                    (mUpDirection);
                //                } else if (event.getY() - mLastTouchY > -mOffsetLimit &&
                //                mUpDirection) {
                //                    Logger.e(
                //                            "event.getY() - mLastTouchY=" + (event.getY() -
                //                            mLastTouchY));
                //                    mUpDirection = false;
                //                    AudioRecorderManager.getInstance().setUpDirection
                //                    (mUpDirection);
                //                    AudioRecorderManager.getInstance().cancelAudioRecord(false);
                //                }
                //                AudioRecorderManager.getInstance().setTouched(true);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                v.setSelected(false);
                //                if (mLastTouchY - event.getY() > mOffsetLimit) {
                //                    AudioRecorderManager.getInstance().onEndAudioRecord(true);
                //
                //                } else /*if (event.getY() - mLastTouchY > -mOffsetLimit)*/ {
                //                    AudioRecorderManager.getInstance().onEndAudioRecord(false);
                //                }
                AudioRecorderManager.getInstance().onEndAudioRecord(false);
                AudioRecorderManager.getInstance().setTouched(false);
                break;
            default:
        }
        return true;
    }

    /**
     * 上传file
     */
    private void uploadVoiceFile(File file, long audioLength) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("application/otcet-stream"),
                file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(),
                requestFile);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(MainApiService.class).upload(body), new HttpSubscriber<String>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable String data) {
                VoiceBean bean = new VoiceBean();
                bean.setDuration(String.valueOf(audioLength));
                bean.setCreateTime(String.valueOf(System.currentTimeMillis()));
                bean.setUrl(data);
                getView().onRecordSuccess(bean);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 提交会诊意见
     */
    private void commitConsultationAdvice() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ConsultationService.class).consultationOrderCreateAdvice(HttpService.INSTANCE.object2Body(getView().getCommitParams())), new HttpSubscriber<String>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable String data) {
                getView().commitSuccess();
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().commitFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 会诊意见列表
     */
    public void getConsultationAdviceList() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ConsultationService.class).consultationOrderAdviceList(getView().getRecordId()), new HttpSubscriber<List<ConsultationAdviceBean>>() {
            @Override
            public void requestComplete(@Nullable List<ConsultationAdviceBean> data) {
                getView().getConsultationAdviceSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().getConsultationAdviceFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    private void initCallback() {
        mCallback = new IAudioRecordCallback() {
            @Override
            public void onRecordReady() {
                Logger.e("onRecordReady");
            }

            @Override
            public void onRecordStart(File audioFile, RecordType recordType) {
                Logger.e("onRecordStart");
                AudioRecorderManager.getInstance().setStarted(true);
                if (!AudioRecorderManager.getInstance().isTouched()) {
                    return;
                }
                AudioRecorderManager.getInstance().initAudioPopView(getView().getRoot());
            }

            @Override
            public void onRecordSuccess(File audioFile, long audioLength, RecordType recordType) {
                Logger.e("onRecordSuccess");
                if (audioLength < 1000) {
                    AudioRecorderManager.getInstance().setTimeShortView();
                } else {
                    uploadVoiceFile(audioFile, audioLength);
                }
            }

            @Override
            public void onRecordFail() {
                Logger.e("onRecordFail");
            }

            @Override
            public void onRecordCancel() {
                Logger.e("onRecordCancel");
            }

            @Override
            public void onRecordReachedMaxTime(int maxTime) {
                Logger.e("onRecordReachedMaxTime");
                AudioRecorderManager.getInstance().setTimeoutView(-1);
                AudioRecorderManager.getInstance().getAudioRecorder().handleEndRecord(true,
                        maxTime);

            }
        };
    }
}
