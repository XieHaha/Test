package com.keydom.ih_common.avchatkit.teamavchat.holder;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.keydom.ih_common.R;
import com.keydom.ih_common.avchatkit.AVChatKit;
import com.keydom.ih_common.avchatkit.common.recyclerview.adapter.BaseMultiItemFetchLoadAdapter;
import com.keydom.ih_common.avchatkit.common.recyclerview.holder.BaseViewHolder;
import com.keydom.ih_common.avchatkit.teamavchat.module.TeamAVChatItem;
import com.keydom.ih_common.im.ImClient;
import com.netease.nimlib.sdk.avchat.model.AVChatTextureViewRenderer;
import com.netease.nimlib.sdk.nos.model.NosThumbParam;
import com.netease.nimlib.sdk.nos.util.NosThumbImageUtil;
import com.netease.nimlib.sdk.uinfo.model.UserInfo;
import com.netease.nrtc.video.render.IVideoRender;

import static android.view.View.GONE;

/**
 * Created by huangjun on 2017/5/4.
 */

public class TeamAVChatItemViewHolder extends TeamAVChatItemViewHolderBase {

    private static final int DEFAULT_AVATAR_THUMB_SIZE =
            (int) AVChatKit.getContext().getResources().getDimension(R.dimen.dp_60);

    private ImageView avatarImage;

    private ImageView loadingImage;

    private AVChatTextureViewRenderer surfaceView;

    private TextView nickNameText;

    private TextView stateText;

    private ProgressBar volumeBar;


    public TeamAVChatItemViewHolder(BaseMultiItemFetchLoadAdapter adapter) {
        super(adapter);
    }

    @Override
    protected void inflate(final BaseViewHolder holder) {
        avatarImage = holder.getView(R.id.avatar_image);
        loadingImage = holder.getView(R.id.loading_image);
        surfaceView = holder.getView(R.id.surface);
        nickNameText = holder.getView(R.id.nick_name_text);
        stateText = holder.getView(R.id.avchat_state_text);
        volumeBar = holder.getView(R.id.avchat_volume);
    }

    @Override
    protected void refresh(final TeamAVChatItem data) {
        UserInfo userInfo = ImClient.getUserInfoProvider().getUserInfo(data.account);
        if (userInfo != null) {
            nickNameText.setText(userInfo.getName());
        } else {
            nickNameText.setText("");
        }
        loadAvatar(data);
        if (data.state == TeamAVChatItem.STATE.STATE_WAITING) {
            // ????????????
            Glide.with(AVChatKit.getContext()).asGif().load(R.mipmap.t_avchat_loading).into(loadingImage);
            loadingImage.setVisibility(View.VISIBLE);
            surfaceView.setVisibility(View.INVISIBLE);
            stateText.setVisibility(GONE);
        } else if (data.state == TeamAVChatItem.STATE.STATE_PLAYING) {
            // ????????????
            loadingImage.setVisibility(GONE);
            surfaceView.setVisibility(data.videoLive ? View.VISIBLE : View.INVISIBLE);
            // ?????????????????????SurfaceView
            stateText.setVisibility(GONE);
        } else if (data.state == TeamAVChatItem.STATE.STATE_END || data.state == TeamAVChatItem.STATE.STATE_HANGUP) {
            // ?????????/??????
            loadingImage.setVisibility(GONE);
            surfaceView.setVisibility(GONE);
            stateText.setVisibility(View.VISIBLE);
            stateText.setText(data.state ==
                    TeamAVChatItem.STATE.STATE_HANGUP ? R.string.avchat_has_hangup :
                    R.string.avchat_no_pick_up);
        }else if(data.state == TeamAVChatItem.STATE.STATE_LIMIT) {
            loadingImage.setVisibility(GONE);
            surfaceView.setVisibility(GONE);
            nickNameText.setText("");
        }
        updateVolume(data.volume);
    }

    private void loadAvatar(TeamAVChatItem data) {
        final UserInfo userInfo = ImClient.getUserInfoProvider().getUserInfo(data.account);
        final int defaultResId = R.mipmap.t_avchat_avatar_default;
        changeUrlBeforeLoad(userInfo != null ? userInfo.getAvatar() : null, defaultResId,
                DEFAULT_AVATAR_THUMB_SIZE);
    }

    /**
     * ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
     * ??????????????????????????????????????????????????????????????????????????????
     */
    private void changeUrlBeforeLoad(final String url, final int defaultResId,
                                     final int thumbSize) {
        if (TextUtils.isEmpty(url)) {
            // avoid useless call
            onLoad(url, defaultResId, thumbSize);
        } else {
            /*
             * ?????????????????????????????????????????????????????????????????????????????????????????????URL
             * ???????????????????????????????????????????????????????????????NosThumbImageUtil
             */
            //            NIMClient.getService(NosService.class).getOriginUrlFromShortUrl(url)
            //            .setCallback(
            //                    new RequestCallbackWrapper<String>() {
            //
            //                        @Override
            //                        public void onResult(int code, String result, Throwable
            //                        exception) {
            //                            if (TextUtils.isEmpty(result)) {
            //                                result = url;
            //                            }
            //                            onLoad(result, defaultResId, thumbSize);
            //                        }
            //                    });
        }
    }

    private void onLoad(String url, int defaultResId, int thumbSize) {
        final String thumbUrl = makeAvatarThumbNosUrl(url, DEFAULT_AVATAR_THUMB_SIZE);
        Glide.with(AVChatKit.getContext()).asBitmap().load(thumbUrl).apply(
                new RequestOptions().centerCrop().placeholder(defaultResId).error(defaultResId)
                        .override(thumbSize, thumbSize)).into(avatarImage);
    }

    /**
     * ?????????????????????NOS URL???????????????ImageLoader?????????key???
     */
    private static String makeAvatarThumbNosUrl(final String url, final int thumbSize) {
        if (TextUtils.isEmpty(url)) {
            return url;
        }
        return thumbSize > 0 ? NosThumbImageUtil.makeImageThumbUrl(url,
                NosThumbParam.ThumbType.Crop, thumbSize,
                thumbSize) : url;
    }

    public IVideoRender getSurfaceView() {
        return surfaceView;
    }

    public void updateVolume(int volume) {
        volumeBar.setProgress(volume);
    }
}
