package com.keydom.ih_common.im.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.keydom.ih_common.R;
import com.keydom.ih_common.im.ImClient;
import com.keydom.ih_common.im.listener.IContainerItemProvider;
import com.keydom.ih_common.im.model.ImUIMessage;
import com.keydom.ih_common.im.utils.ImDateUtils;
import com.keydom.ih_common.utils.GlideUtils;
import com.netease.nimlib.sdk.msg.constant.MsgDirectionEnum;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.uinfo.model.UserInfo;

import org.jetbrains.annotations.NotNull;

public class ImProviderContainerView extends LinearLayout implements IContainerItemProvider {

    private TextView time;
    private ImageView avatarLeft;
    private ImageView avatarRight;
    private LinearLayout content;
    private TextView leftName;

    private ImageView avatar;
    private View containerProvide;

    public ImProviderContainerView(Context context) {
        this(context, null);
    }

    public ImProviderContainerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImProviderContainerView(Context context, @Nullable AttributeSet attrs,
                                   int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(@Nullable AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs,
                R.styleable.ImProviderContainerView);
        int layoutId =
                typedArray.getResourceId(R.styleable.ImProviderContainerView_ipcv_container_layout, 0);
        typedArray.recycle();
        setOrientation(LinearLayout.VERTICAL);
        LayoutInflater.from(getContext()).inflate(R.layout.im_item_view_message, this);
        time = findViewById(R.id.im_message_time);
        avatarLeft = findViewById(R.id.im_message_avatar_left);
        avatarRight = findViewById(R.id.im_message_avatar_right);
        leftName = findViewById(R.id.left_name);
        content = findViewById(R.id.im_message_content);
        if (layoutId == 0) {
            throw new NullPointerException("内容不能为空");
        }
        containerProvide = LayoutInflater.from(getContext()).inflate(layoutId, content, false);
        content.setId(View.NO_ID);
        containerProvide.setId(R.id.im_message_content);
        content.addView(containerProvide);
        containerViewLeft();
    }

    @Override
    public void containerViewLeft() {
        avatar = avatarLeft;
        avatar.setVisibility(VISIBLE);
        avatarRight.setVisibility(GONE);
        content.setGravity(Gravity.START);
        if (containerProvide instanceof IContainerItemProvider) {
            ((IContainerItemProvider) containerProvide).containerViewLeft();
        }
    }

    @Override
    public void containerViewRight() {
        avatar = avatarRight;
        avatar.setVisibility(VISIBLE);
        avatarLeft.setVisibility(GONE);
        content.setGravity(Gravity.END);
        if (containerProvide instanceof IContainerItemProvider) {
            ((IContainerItemProvider) containerProvide).containerViewRight();
        }
    }

    public void containerViewCenter() {
        avatarLeft.setVisibility(GONE);
        leftName.setVisibility(GONE);
        avatarRight.setVisibility(GONE);
        content.setGravity(Gravity.CENTER);
        if (containerProvide instanceof IContainerItemProvider) {
            ((IContainerItemProvider) containerProvide).containerViewRight();
        }
    }

    @Override
    public void bindView(@NotNull View addition, @NotNull ImUIMessage message) {
        if (containerProvide instanceof IContainerItemProvider) {
            ((IContainerItemProvider) containerProvide).bindView(addition, message);
        }
    }

    public void setDateTime(ImUIMessage previousData, ImUIMessage data) {
        time.setText(ImDateUtils.INSTANCE.getConversationFormatDate(data.getMessage().getTime()));
        if (previousData == null || ImDateUtils.INSTANCE.isShowChatTime(data.getMessage().getTime(), previousData.getMessage().getTime(), 180)) {
            time.setVisibility(VISIBLE);
        } else {
            time.setVisibility(GONE);
        }
    }

    public void setAvatar(String account) {
        UserInfo userInfo = ImClient.getUserInfoProvider().getUserInfo(account);
        GlideUtils.load(avatar, userInfo != null ? userInfo.getAvatar() : "",
                R.mipmap.im_default_head_image, R.mipmap.im_default_head_image, true, null);
    }

    public void setLeftName(IMMessage message) {
        if (message.getSessionType() == SessionTypeEnum.Team && message.getDirect() == MsgDirectionEnum.In) {
            leftName.setVisibility(VISIBLE);
            leftName.setText(message.getFromNick());
        } else {
            leftName.setVisibility(GONE);
        }
    }
}
