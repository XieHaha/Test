package com.keydom.mianren.ih_doctor.activity.online_consultation.adapter;

import android.content.Context;
import android.media.AudioManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.ih_common.im.manager.AudioPlayerManager;
import com.keydom.ih_common.utils.BaseFileUtils;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.bean.ConsultationAdviceBean;
import com.keydom.mianren.ih_doctor.bean.VoiceBean;
import com.keydom.mianren.ih_doctor.utils.DateUtils;
import com.netease.nimlib.sdk.media.player.OnPlayListener;

import java.util.ArrayList;
import java.util.List;

public class ConsultationAdviceAdapter extends BaseQuickAdapter<ConsultationAdviceBean,
        BaseViewHolder> {
    private Context context;

    public ConsultationAdviceAdapter(Context context, List<ConsultationAdviceBean> data) {
        super(R.layout.item_consultation_advice, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final ConsultationAdviceBean item) {
        helper.setText(R.id.consultation_advice_name, item.getDoctorName())
                .setText(R.id.consultation_advice_depart, item.getDeptName())
                .setText(R.id.consultation_advice_date,
                        DateUtils.timestampToString(item.getCreateTime(),
                                DateUtils.YYYY_MM_DD_HH_MM_SS))
                .setText(R.id.consultation_advice_content, item.getContent());
        LinearLayout voiceLayout = helper.getView(R.id.consultation_advice_voice_layout);

        if (item.getAudioInfos() != null && item.getAudioInfos().size() > 0) {
            voiceLayout.removeAllViews();
            addVoiceView(voiceLayout, item.getAudioInfos());
        }
    }

    private void addVoiceView(LinearLayout voiceLayout, ArrayList<VoiceBean> audioInfos) {
        for (VoiceBean bean : audioInfos) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_voice, null);

            ImageView imageStart = view.findViewById(R.id.voice_start);
            TextView voiceTime = view.findViewById(R.id.voice_time);
            ProgressBar progressBar = view.findViewById(R.id.voice_progress);

            voiceTime.setText(DateUtils.getMinute(bean.getDuration()));
            progressBar.setMax(Integer.valueOf(bean.getDuration()));
            imageStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (imageStart.isSelected()) {
                        imageStart.setSelected(false);
                        if (AudioPlayerManager.getInstance().isPlaying()) {
                            AudioPlayerManager.getInstance().stopPlay();
                        }
                    } else {
                        imageStart.setSelected(true);
                        AudioPlayerManager.getInstance().setDataSource(BaseFileUtils.getHeaderUrl(bean.getUrl()))
                                .setOnPlayListener(new OnVoicePlayListener(voiceTime, progressBar
                                        , imageStart));
                        if (AudioPlayerManager.getInstance().isPlaying()) {
                            AudioPlayerManager.getInstance().stopPlay();
                        }
                        AudioPlayerManager.getInstance().start(AudioManager.STREAM_MUSIC);
                    }
                }
            });
            voiceLayout.addView(view);
        }
    }

    private class OnVoicePlayListener implements OnPlayListener {
        private ImageView imageView;
        private TextView voiceTime;
        private ProgressBar progressBar;

        public OnVoicePlayListener(TextView voiceTime, ProgressBar progressBar,
                                   ImageView imageView) {
            this.imageView = imageView;
            this.voiceTime = voiceTime;
            this.progressBar = progressBar;
        }

        @Override
        public void onPrepared() {

        }

        @Override
        public void onCompletion() {
            imageView.setSelected(false);
            progressBar.setProgress(0);
            voiceTime.setText(DateUtils.getMinute(String.valueOf(progressBar.getMax())));
        }

        @Override
        public void onInterrupt() {
            imageView.setSelected(false);
            progressBar.setProgress(0);
            voiceTime.setText(DateUtils.getMinute(String.valueOf(progressBar.getMax())));
        }

        @Override
        public void onError(String error) {
            imageView.setSelected(false);
            progressBar.setProgress(0);
            voiceTime.setText(DateUtils.getMinute(String.valueOf(progressBar.getMax())));
        }

        @Override
        public void onPlaying(long curPosition) {
            voiceTime.setText(DateUtils.getMinute(String.valueOf(progressBar.getMax() - (int) curPosition)));
            progressBar.setProgress((int) curPosition);
        }
    }

}
