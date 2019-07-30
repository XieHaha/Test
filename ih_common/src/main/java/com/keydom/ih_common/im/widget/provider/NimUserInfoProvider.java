package com.keydom.ih_common.im.widget.provider;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

import com.keydom.ih_common.R;
import com.keydom.ih_common.im.ImClient;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.team.model.Team;
import com.netease.nimlib.sdk.team.model.TeamMember;
import com.netease.nimlib.sdk.uinfo.UserInfoProvider;
import com.netease.nimlib.sdk.uinfo.model.UserInfo;

/**
 * 初始化sdk 需要的用户信息提供者，现主要用于内置通知提醒获取昵称和头像
 * <p>
 * 注意不要与 IUserInfoProvider 混淆
 * <p>
 */

public class NimUserInfoProvider implements UserInfoProvider {

    private Context context;

    public NimUserInfoProvider(Context context) {
        this.context = context;
    }

    @Override
    public UserInfo getUserInfo(String account) {
        return ImClient.getUserInfoProvider().getUserInfo(account);
    }

    @Override
    public Bitmap getAvatarForMessageNotifier(SessionTypeEnum sessionType, String sessionId) {
        /*
         * 注意：这里最好从缓存里拿，如果加载时间过长会导致通知栏延迟弹出！该函数在后台线程执行！
         */
        Bitmap bm = null;
        int defResId = R.mipmap.im_default_head_image;

        if (SessionTypeEnum.P2P == sessionType) {
            UserInfo user = getUserInfo(sessionId);
            bm = (user != null) ? ImClient.getImageLoader().getNotificationBitmapFromCache(user.getAvatar()) : null;
        } else if (SessionTypeEnum.Team == sessionType) {
            Team team = ImClient.getTeamProvider().getTeamById(sessionId);
            bm = (team != null) ? ImClient.getImageLoader().getNotificationBitmapFromCache(team.getIcon()) : null;
            defResId = R.mipmap.im_default_head_image;
        }

        if (bm == null) {
            Drawable drawable = context.getResources().getDrawable(defResId);
            if (drawable instanceof BitmapDrawable) {
                bm = ((BitmapDrawable) drawable).getBitmap();
            }
        }

        return bm;
    }

    @Override
    public String getDisplayNameForMessageNotifier(String account, String sessionId, SessionTypeEnum sessionType) {
        String nick = null;
        if (sessionType == SessionTypeEnum.P2P) {
            UserInfo user = getUserInfo(sessionId);
            nick = user == null ? null : user.getName();
        } else if (sessionType == SessionTypeEnum.Team) {
            TeamMember teamMember = ImClient.getTeamProvider().getTeamMember(sessionId, account);
            nick = teamMember == null ? null : teamMember.getTeamNick();
        }

        if (TextUtils.isEmpty(nick)) {
            // 返回null，交给sdk处理。如果对方有设置nick，sdk会显示nick
            return null;
        }
        return nick;
    }
}
