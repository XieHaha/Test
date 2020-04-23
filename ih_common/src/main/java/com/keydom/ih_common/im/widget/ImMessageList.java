package com.keydom.ih_common.im.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemLongClickListener;
import com.keydom.ih_common.R;
import com.keydom.ih_common.im.ImClient;
import com.keydom.ih_common.im.activity.PlayVideoActivity;
import com.keydom.ih_common.im.adapter.ImMessageAdapter;
import com.keydom.ih_common.im.listener.IConversationBehaviorListener;
import com.keydom.ih_common.im.listener.QueryMessageListener;
import com.keydom.ih_common.im.manager.RevokeMessageHelper;
import com.keydom.ih_common.im.model.ImUIMessage;
import com.keydom.ih_common.im.model.custom.ConsultationResultAttachment;
import com.keydom.ih_common.im.model.custom.DisposalAdviceAttachment;
import com.keydom.ih_common.im.model.custom.EndInquiryAttachment;
import com.keydom.ih_common.im.model.custom.ReferralApplyAttachment;
import com.keydom.ih_common.im.model.custom.ReferralDoctorAttachment;
import com.keydom.ih_common.im.model.event.BlackEvent;
import com.keydom.ih_common.im.model.event.EndInquiryEvent;
import com.keydom.ih_common.im.model.event.PrescriptionEvent;
import com.keydom.ih_common.im.model.event.ReferralApplyEvent;
import com.keydom.ih_common.im.model.event.RevokeEvent;
import com.keydom.ih_common.im.model.event.StartInquiryEvent;
import com.keydom.ih_common.im.model.event.UserInfoChangeEvent;
import com.keydom.ih_common.im.utils.AlbumUtilKt;
import com.keydom.ih_common.im.widget.provider.item.ImMessageItemVoiceProvider;
import com.keydom.ih_common.view.GeneralDialog;
import com.luck.picture.lib.entity.LocalMedia;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.ResponseCode;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.attachment.AudioAttachment;
import com.netease.nimlib.sdk.msg.attachment.ImageAttachment;
import com.netease.nimlib.sdk.msg.attachment.VideoAttachment;
import com.netease.nimlib.sdk.msg.constant.MsgDirectionEnum;
import com.netease.nimlib.sdk.msg.constant.MsgTypeEnum;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.msg.model.QueryDirectionEnum;
import com.netease.nimlib.sdk.team.TeamService;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;


/**
 * @author THINKPAD B
 */
public class ImMessageList extends FrameLayout {

    private RecyclerView mRecycler;
    private SmartRefreshLayout mRefresh;
    private String sessionId;
    private SessionTypeEnum type;

    private ImMessageAdapter mAdapter = new ImMessageAdapter(new ArrayList<ImUIMessage>());

    private IConversationBehaviorListener mConversationBehaviorListener = null;
    private int pageSize = 100;
    private int pageIndex = 1;
    private IMMessage anchorMessage;
    /**
     * 需要撤回的item角标
     */
    private int revokePosition;

    public void setConversationBehaviorListener(IConversationBehaviorListener listener) {
        mConversationBehaviorListener = listener;
    }

    public ImMessageList(@NonNull Context context) {
        this(context, null);
    }

    public ImMessageList(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImMessageList(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        EventBus.getDefault().register(this);
        init(context);
        initListener();
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.im_message_list, this, true);
        mRefresh = findViewById(R.id.srl_refreshLayout);
        mRefresh.setEnableRefresh(false);
        mRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                pageIndex++;
                initData(anchorMessage);
            }
        });

        mRecycler = findViewById(R.id.im_list);
        mRecycler.setLayoutManager(new LinearLayoutManager(context));
        mRecycler.setHasFixedSize(true);
        mRecycler.setAdapter(mAdapter);

    }

    private void initListener() {
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ImUIMessage data = (ImUIMessage) adapter.getItem(position);
                IMMessage message = data.getMessage();
                int i = view.getId();
                if (i == R.id.im_message_avatar_left || i == R.id.im_message_avatar_right) {
                    if (mConversationBehaviorListener != null) {
                        mConversationBehaviorListener.onUserPortraitClick(getContext(), message);
                    }
                } else if (i == R.id.im_message_content) {
                    if (mConversationBehaviorListener != null) {
                        if (!mConversationBehaviorListener.onMessageClick(getContext(), view,
                                message)) {
                            if (message.getAttachment() instanceof ImageAttachment) {
                                LocalMedia media = new LocalMedia();
                                if (((ImageAttachment) message.getAttachment()).getPath() != null) {
                                    media.setPath(((ImageAttachment) message.getAttachment()).getPath());
                                } else {
                                    media.setPath(((ImageAttachment) message.getAttachment()).getUrl());
                                }
                                List<LocalMedia> mediaList = new ArrayList<>();
                                mediaList.add(media);
                                AlbumUtilKt.previewPhotos((Activity) getContext(), 0, mediaList);
                            } else if (message.getAttachment() instanceof AudioAttachment) {
                                ImMessageItemVoiceProvider containerView =
                                        (ImMessageItemVoiceProvider) view;
                                AudioAttachment audioAttachment =
                                        (AudioAttachment) message.getAttachment();
                                if (audioAttachment.getPath() != null) {
                                    containerView.play(audioAttachment.getPath());
                                }
                            } else if (message.getAttachment() instanceof VideoAttachment) {
                                Intent intent = new Intent(getContext(), PlayVideoActivity.class);
                                intent.putExtra("IMMessage", message);
                                getContext().startActivity(intent);
                            }
                        }
                    }
                } else {
                    if (message.getAttachment() instanceof ConsultationResultAttachment && i == R.id.prescription) {
                        if (mConversationBehaviorListener != null) {
                            mConversationBehaviorListener.onPrescriptionClick(getContext(),
                                    message);
                        }

                    } else if (mConversationBehaviorListener != null) {
                        mConversationBehaviorListener.onPayClick(getContext(), view, message);
                    }
                }
            }
        });
        mRecycler.addOnItemTouchListener(new OnItemLongClickListener() {
            @Override
            public void onSimpleItemLongClick(BaseQuickAdapter adapter, View view,
                                              final int position) {
                ImUIMessage data = (ImUIMessage) adapter.getItem(position);
                final IMMessage message = data.getMessage();
                if (message.getDirect() == MsgDirectionEnum.Out && (message.getMsgType() == MsgTypeEnum.text || message.getMsgType() == MsgTypeEnum.image || message.getMsgType() == MsgTypeEnum.file || message.getMsgType() == MsgTypeEnum.audio)) {
                    new GeneralDialog(getContext(), "是否撤回此条消息？",
                            new GeneralDialog.OnCloseListener() {
                        @Override
                        public void onCommit() {
                            NIMClient.getService(MsgService.class).revokeMessage(message).setCallback(new RequestCallback<Void>() {
                                @Override
                                public void onSuccess(Void param) {
                                    revokePosition = position;
                                    getChatMessageList();
                                    RevokeMessageHelper.getInstance().onRevokeMessage(message,
                                            ImClient.getUserInfoProvider().getAccount());
                                }

                                @Override
                                public void onFailed(int code) {
                                    if (code == ResponseCode.RES_OVERDUE) {
                                        Toast.makeText(getContext(), "发送时间超过2分钟的消息，不能被撤回",
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        Logger.e("revoke msg failed, code:" + code);
                                    }
                                }

                                @Override
                                public void onException(Throwable exception) {

                                }
                            });
                        }
                    }).setTitle("撤回").setNegativeButton("取消").setPositiveButton("确认").show();
                }
            }
        });
    }


    public RecyclerView getRecycler() {
        return mRecycler;
    }

    public ImMessageAdapter getAdapter() {
        return mAdapter;
    }

    public void setMessageInfo(String sessionId, SessionTypeEnum type) {
        this.sessionId = sessionId;
        this.type = type;
        getChatMessageList();
    }

    private void initData(final IMMessage anchor) {
        ImClient.queryMessageListEx(anchor, QueryDirectionEnum.QUERY_OLD, pageSize, true,
                new QueryMessageListener() {
            @Override
            public void onSuccess(List<IMMessage> param) {
                if (mRefresh.isEnableRefresh()) {
                    mRefresh.finishRefresh();
                    if (param.size() == 0) {
                        setEnableLoadMore(false);
                    }
                }
                if (!param.isEmpty()) {
                    anchorMessage = param.get(0);
                }
                if (param.size() >= pageSize) {
                    setEnableLoadMore(true);
                }

                List<ImUIMessage> uiMessages = new ArrayList<>();
                for (IMMessage message : param) {
                    boolean isEndInquiryMsgType =
                            message.getMsgType() == MsgTypeEnum.custom && (message.getAttachment() instanceof EndInquiryAttachment);
                    if (message.getMsgType() != MsgTypeEnum.notification && !isEndInquiryMsgType) {
                        uiMessages.add(ImUIMessage.obtain(message));
                    }
                }

                setData(uiMessages);
                if (type == SessionTypeEnum.Team) {
                    NIMClient.getService(TeamService.class).refreshTeamMessageReceipt(param);
                }
            }

            @Override
            public void onFailed(int code) {
                if (mRefresh.isEnableRefresh()) {
                    mRefresh.finishRefresh();
                }
            }

            @Override
            public void onException(Throwable exception) {
                if (mRefresh.isEnableRefresh()) {
                    mRefresh.finishRefresh();
                }
            }
        });
    }

    public void setEnableLoadMore(boolean enable) {
        mRefresh.setEnableRefresh(enable);

    }

    /**
     * 此处的EventBus的EndInquiryEvent事件在患者端和医生端的ConversationActivity#onEndInquiryEvent方法中处理
     * 此处的EventBus的PrescriptionEvent事件在患者端的ConversationActivity#onPrescriptionEvent方法中处理
     * 此处的EventBus的ReferralApplyEvent事件在患者端的ConversationActivity#onReferralApplyEvent方法中处理
     * 此处的EventBus的StartInquiryEvent事件在患者端的ConversationActivity#onStartInquiryEvent方法中处理
     *
     * @param message 消息实体
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ImUIMessage message) {
        if (message.getMessage().getSessionId().equals(sessionId.toLowerCase())) {
            if (message.getMessage().getMsgType() == MsgTypeEnum.custom) {
                if (!(message.getMessage().getAttachment() instanceof EndInquiryAttachment)) {
                    if (message.getMessage().getAttachment() instanceof ConsultationResultAttachment || message.getMessage().getAttachment() instanceof DisposalAdviceAttachment) {
                        EventBus.getDefault().post(new PrescriptionEvent(message.getMessage()));
                    } else if (message.getMessage().getAttachment() instanceof ReferralApplyAttachment) {
                        EventBus.getDefault().post(new ReferralApplyEvent(message.getMessage()));
                    } else if (message.getMessage().getAttachment() instanceof ReferralDoctorAttachment) {
                        addData(ImUIMessage.obtain(ImClient.createLocalTipMessage(sessionId.toLowerCase(), type, "预约医生暂时不能接诊，医院已为您换医生团队其他医生接诊，请确认")));
                    }
                    addData(message);
                } else {
                    EventBus.getDefault().post(new EndInquiryEvent(message.getMessage()));
                }
            } else if (message.getMessage().getMsgType() == MsgTypeEnum.notification) {
                //todo
            } else {
                if (message.getMessage().getMsgType() == MsgTypeEnum.tip) {
                    if (message.getMessage().getContent().contains("问诊开始，本次问诊可持续")) {
                        EventBus.getDefault().post(new StartInquiryEvent(message.getMessage()));
                    }
                }
                addData(message);
            }
        }
    }

    /**
     * 此处的Event事件室友IContainerItemProvider发出来的
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBlackEvent(BlackEvent event) {
        IMMessage message = ImClient.createLocalTipMessage(sessionId, type, "消息已发出，但被对方拒收了。");
        addData(ImUIMessage.obtain(message, MsgDirectionEnum.Out));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRevokeEvent(RevokeEvent event) {
        getChatMessageList();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserInfoChangeEvent(UserInfoChangeEvent changeEvent) {
        if (type == SessionTypeEnum.P2P) {
            if (changeEvent.getAccounts().contains(sessionId) || changeEvent.getAccounts().contains(ImClient.getUserInfoProvider().getAccount())) {
                mAdapter.notifyDataSetChanged();
            }
        } else { // 群的，简单的全部重刷
            mAdapter.notifyDataSetChanged();
        }
    }

    public void addData(ImUIMessage model) {
        if (model.getMessage().getAttachment() instanceof EndInquiryAttachment) {
            ImClient.sentMessage(model.getMessage(), false, null);
        } else {
            mAdapter.addData(model);
            mRecycler.scrollToPosition(mAdapter.getItemCount() - 1);
        }
    }


    public void setData(List<ImUIMessage> data) {
        if (!data.isEmpty()) {
            if (pageIndex == 1) {
                mAdapter.setNewData(data);
            } else {
                mAdapter.addData(0, data);
            }
            mRecycler.scrollToPosition(pageIndex != 1 ? (pageIndex - 1) * pageSize - 1 :
                    mAdapter.getItemCount() - 1);
        }
    }

    public void clearData() {
        mAdapter.setNewData(new ArrayList<ImUIMessage>());
    }


    public void getChatMessageList() {
        IMMessage anchor = MessageBuilder.createEmptyMessage(sessionId, type,
                System.currentTimeMillis());
        initData(anchor);
    }

    public List<IMMessage> getData() {
        List<IMMessage> messages = new ArrayList<>();
        for (ImUIMessage uiMessage : getAdapter().getData()) {
            messages.add(uiMessage.getMessage());
        }
        return messages;
    }

    @Override
    protected void onDetachedFromWindow() {
        EventBus.getDefault().unregister(this);
        super.onDetachedFromWindow();
    }
}
