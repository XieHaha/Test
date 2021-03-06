package com.keydom.mianren.ih_doctor.activity.online_consultation.adapter;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.ih_common.im.manager.AudioPlayerManager;
import com.keydom.ih_common.utils.BaseFileUtils;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.ih_common.bean.VoiceBean;
import com.netease.nimlib.sdk.media.player.OnPlayListener;

import java.util.List;

public class ConsultationVoiceAdapter extends BaseQuickAdapter<VoiceBean,
        BaseViewHolder> {
    private Context context;
    private AnimationDrawable animationDrawable;

    public ConsultationVoiceAdapter(Context context, List<VoiceBean> data) {
        super(R.layout.item_consultation_voice, data);
        this.context = context;
        animationDrawable = (AnimationDrawable) ContextCompat.getDrawable(context,
                R.drawable.im_anim_voice_sent);
    }

    @Override
    protected void convert(BaseViewHolder helper, final VoiceBean item) {
        helper.setText(R.id.consultation_voice_time, Long.valueOf(item.getDuration()) + "'")
                .addOnClickListener(R.id.consultation_voice_delete);
        ImageView voiceImg = helper.getView(R.id.consultation_voice_img);
        RelativeLayout layout = helper.getView(R.id.consultation_voice_layout);
        layout.setOnClickListener(v -> {
            AudioPlayerManager.getInstance().setDataSource(BaseFileUtils.getHeaderUrl(item.getUrl()))
                    .setOnPlayListener(new OnVoicePlayListener(voiceImg));
            if (AudioPlayerManager.getInstance().isPlaying()) {
                AudioPlayerManager.getInstance().stopPlay();
            }
            AudioPlayerManager.getInstance().start(AudioManager.STREAM_MUSIC);
        });
    }

    private class OnVoicePlayListener implements OnPlayListener {
        private ImageView imageView;

        OnVoicePlayListener(ImageView imageView) {
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
