package com.keydom.ih_common.im.widget;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ToastUtils;
import com.keydom.ih_common.R;
import com.keydom.ih_common.base.BaseActivity;
import com.keydom.ih_common.im.ImClient;
import com.keydom.ih_common.im.adapter.PluginAdapter;
import com.keydom.ih_common.im.listener.IConversationBehaviorListener;
import com.keydom.ih_common.im.listener.IExtensionClickListener;
import com.keydom.ih_common.im.listener.IPluginModule;
import com.keydom.ih_common.im.model.ImUIMessage;
import com.luck.picture.lib.entity.LocalMedia;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.constant.MsgDirectionEnum;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.msg.model.LocalAntiSpamResult;

import java.io.File;
import java.util.List;

/**
 * @author THINKPAD B
 */
public class ImMessageView extends LinearLayout implements IExtensionClickListener, LifecycleObserver {

    private ImMessageList messageList;
    private ImExtension mExtension;

    private String sessionId;
    private SessionTypeEnum type;

    /**
     * 是否是医生从患者管理主动找患者发起的聊天<br>
     * true-患者只能回复三句话<br>
     * false-在线问诊中，不限制回复次数<br>
     */
    private boolean chatting = false;

    /**
     * 是否是护士<br>
     * true-暂时不限制三条会话
     */
//    private boolean isNurse = false;
    public void setChatting(boolean chatting) {
        this.chatting = chatting;
    }

//    public void setNurse(boolean nurse) {
//        isNurse = nurse;
//    }

    public ImMessageView(Context context) {
        this(context, null);
    }

    public ImMessageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImMessageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init(Context context, @Nullable AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ImMessageView);
        int layoutId = typedArray.getResourceId(R.styleable.ImMessageView_imv_extend_layout, 0);
        typedArray.recycle();
        setBackgroundResource(R.color.im_bg);
        setOrientation(VERTICAL);
        messageList = new ImMessageList(context, attrs);
        FrameLayout extend = new FrameLayout(context, attrs);
        if (layoutId != 0) {
            LayoutInflater.from(context).inflate(layoutId, extend);
        }
        mExtension = new ImExtension(context, attrs);
        setExtensionClickListener(this);
        messageList.getRecycler().setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN && mExtension.isExtensionExpanded()) {
                    mExtension.collapseExtension();
                }
                return false;
            }
        });

        addView(messageList, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0, 1f));
        addView(extend);
        addView(mExtension);
    }

    public void setMessageInfo(String sessionId, SessionTypeEnum type) {
        this.sessionId = sessionId;
        this.type = type;
        messageList.setMessageInfo(sessionId, type);
        mExtension.setMessageInfo(sessionId);
    }

    private void setExtensionClickListener(IExtensionClickListener listener) {
        mExtension.setExtensionClickListener(listener);
    }

    public void setOnConversationBehaviorListener(IConversationBehaviorListener listener) {
        messageList.setConversationBehaviorListener(listener);
    }

    public void setEnableLoadMore(boolean enable) {
        messageList.setEnableLoadMore(enable);
    }

    public void collapseExtension() {
        mExtension.collapseExtension();
    }

    public boolean isExtensionExpanded() {
        return mExtension.isExtensionExpanded();
    }

    public void hideExtension() {
        messageList.getRecycler().scrollToPosition(messageList.getAdapter().getItemCount() - 1);
        collapseExtension();
        mExtension.setVisibility(GONE);
    }

    public void showExtension() {
        messageList.getRecycler().scrollToPosition(messageList.getAdapter().getItemCount() - 1);
        collapseExtension();
        mExtension.setVisibility(VISIBLE);
    }

    public void setExtensionBarVisibility(int visibility) {
        mExtension.setExtensionBarVisibility(visibility);
    }

    public void addPlugin(IPluginModule pluginModule) {
        mExtension.addPlugin(pluginModule);
    }

    public void removePlugin(IPluginModule pluginModule) {
        mExtension.removePlugin(pluginModule);
    }

    public PluginAdapter getPluginAdapter() {
        return mExtension.getPluginAdapter();
    }

    public boolean onActivityPluginResult(int requestCode, int resultCode, Intent data) {
        return mExtension.onActivityPluginResult(requestCode, resultCode, data);
    }

    public void addData(IMMessage message) {
        boolean isNewChatting = false;
        ImUIMessage lastOneMessage = null;
        ImUIMessage secondLastMessage = null;
        ImUIMessage thirdLastMessage = null;
        if (messageList.getAdapter().getData().size() == 0) {
            isNewChatting = true;
        } else {
            lastOneMessage = messageList.getAdapter().getItem(messageList.getAdapter().getItemCount() - 1);
            secondLastMessage = messageList.getAdapter().getItem(messageList.getAdapter().getItemCount() - 2);
            thirdLastMessage = messageList.getAdapter().getItem(messageList.getAdapter().getItemCount() - 3);
        }
        ImUIMessage imUIMessage = ImUIMessage.obtain(message, MsgDirectionEnum.Out);
        if (chatting) {
            if (isNewChatting) {
                ToastUtils.showShort("您未购买在线问诊服务，聊天只能医生发起");
            } else if (lastOneMessage == null || (lastOneMessage != null && lastOneMessage.getMessage().getDirect() == MsgDirectionEnum.Out)
                    && (secondLastMessage == null || (secondLastMessage != null && secondLastMessage.getMessage().getDirect() == MsgDirectionEnum.Out))
                    && (thirdLastMessage == null || (thirdLastMessage != null && thirdLastMessage.getMessage().getDirect() == MsgDirectionEnum.Out))) {
                ToastUtils.showShort("您输入过于频繁，等对方回复后您才有输入权限");
            } else {
                messageList.addData(imUIMessage);
            }
        } else {
            messageList.addData(imUIMessage);
        }
    }

    @Override
    public void onSendToggleClick(View view, String text) {
        if (!TextUtils.isEmpty(text)) {
            IMMessage message = ImClient.createTextMessage(sessionId, type, text);
            if (checkLocalAntiSpam(message)) {
                addData(message);
            }
        }
    }

    private static boolean checkLocalAntiSpam(IMMessage message) {
        LocalAntiSpamResult result = NIMClient.getService(MsgService.class).checkLocalAntiSpam(message.getContent(), "**");
        int operator = result == null ? 0 : result.getOperator();
        switch (operator) {
            // 替换，允许发送
            case 1:
                message.setContent(result.getContent());
                return true;
            // 拦截，不允许发送
            case 2:
                return false;
            // 允许发送，交给服务器
            case 3:
                message.setClientAntiSpam(true);
                return true;
            case 0:
            default:
                break;
        }

        return true;
    }

    public void clearData() {
        messageList.clearData();
    }

    @Override
    public void onVoiceInputToggleTouch(View view, MotionEvent event) {
    }

    @Override
    public void onVoiceResult(long duration, String audioPath) {
        addData(ImClient.createAudioMessage(sessionId, type, new File(audioPath), duration));

    }

    @Override
    public Boolean onKey(View view, int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public void onExtensionCollapsed() {

    }

    @Override
    public void onExtensionExpanded(int h) {
        messageList.getRecycler().scrollToPosition(messageList.getAdapter().getItemCount() - 1);
    }

    @Override
    public void onImageResult(List<LocalMedia> selectedImages, int resultType) {
        LocalMedia media = selectedImages.get(0);
        if (resultType == IExtensionClickListener.RESULT_IMAGE) {
            File file = new File(media.getPath());
            addData(ImClient.createImageMessage(sessionId, type, file, file.getName()));
        } else {
            addData(ImClient.createVideoMessage(sessionId, type, media.getPath()));
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (getContext() instanceof BaseActivity) {
            mExtension.setActivity((BaseActivity) getContext());
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        mExtension.onPause();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        mExtension.onDestroy();
    }
}
