package com.keydom.mianren.ih_doctor.activity.online_consultation.adapter;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.ih_common.im.manager.AudioPlayerManager;
import com.keydom.ih_common.utils.BaseFileUtils;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.bean.ConsultationAdviceBean;
import com.keydom.ih_common.bean.VoiceBean;
import com.keydom.mianren.ih_doctor.utils.DateUtils;
import com.netease.nimlib.sdk.media.player.OnPlayListener;

import java.util.ArrayList;
import java.util.List;

public class ConsultationAdviceAdapter extends BaseQuickAdapter<ConsultationAdviceBean,
        BaseViewHolder> {
    private Context context;
    private AnimationDrawable animationDrawable;

    public ConsultationAdviceAdapter(Context context, List<ConsultationAdviceBean> data) {
        super(R.layout.item_consultation_advice, data);
        this.context = context;
        animationDrawable = (AnimationDrawable) ContextCompat.getDrawable(context,
                R.drawable.im_anim_voice_sent);
    }

    @Override
    protected void convert(BaseViewHolder helper, final ConsultationAdviceBean item) {
        helper.setText(R.id.consultation_advice_name, item.getDoctorName())
                .setText(R.id.consultation_advice_depart, item.getDeptName())
                .setText(R.id.consultation_advice_date,
                        DateUtils.timestampToString(item.getCreateTime(),
                                DateUtils.YYYY_MM_DD_HH_MM_SS));
        TextView tvContent = helper.getView(R.id.consultation_advice_content);
        if (TextUtils.isEmpty(item.getContent())) {
            tvContent.setVisibility(View.GONE);
        } else {
            tvContent.setVisibility(View.VISIBLE);
            tvContent.setText(item.getContent());
        }
        LinearLayout voiceLayout = helper.getView(R.id.consultation_advice_voice_layout);

        if (item.getAudioInfos() != null && item.getAudioInfos().size() > 0) {
            voiceLayout.removeAllViews();
            addVoiceView(voiceLayout, item.getAudioInfos());
        }
    }

    private void addVoiceView(LinearLayout voiceLayout, ArrayList<VoiceBean> audioInfos) {
        for (VoiceBean bean : audioInfos) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_consultation_voice,
                    null);

            ImageView imageStart = view.findViewById(R.id.consultation_voice_img);
            TextView voiceTime = view.findViewById(R.id.consultation_voice_time);
            TextView deleteTv = view.findViewById(R.id.consultation_voice_delete);
            deleteTv.setVisibility(View.GONE);
            voiceTime.setText(DateUtils.getMinute(bean.getDuration()));

            view.setOnClickListener(v -> {
                AudioPlayerManager.getInstance().setDataSource(BaseFileUtils.getHeaderUrl(bean.getUrl()))
                        .setOnPlayListener(new OnVoicePlayListener(imageStart));
                if (AudioPlayerManager.getInstance().isPlaying()) {
                    AudioPlayerManager.getInstance().stopPlay();
                }
                AudioPlayerManager.getInstance().start(AudioManager.STREAM_MUSIC);
            });
            voiceLayout.addView(view);
        }
    }

    private class OnVoicePlayListener implements OnPlayListener {
        private ImageView imageView;

        public OnVoicePlayListener(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        public void onPrepared() {

        }

        @Override
        public void onCompletion() {
            setVoiceAnim(imageView, false);
        }

        @Override
        public void onInterrupt() {
            setVoiceAnim(imageView, false);
        }

        @Override
        public void onError(String error) {
            setVoiceAnim(imageView, false);
        }

        @Override
        public void onPlaying(long curPosition) {
            setVoiceAnim(imageView, true);
        }
    }

    private void setVoiceAnim(ImageView imageView, boolean playing) {
        if (playing) {
            imageView.setImageDrawable(animationDrawable);
            animationDrawable.start();
        } else {
            imageView.setImageResource(R.mipmap.im_voice_sent);
            animationDrawable.stop();
        }
    }
}
