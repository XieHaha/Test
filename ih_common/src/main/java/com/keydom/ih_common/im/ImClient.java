package com.keydom.ih_common.im;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;

import com.keydom.Common;
import com.keydom.ih_common.R;
import com.keydom.ih_common.avchatkit.AVChatKit;
import com.keydom.ih_common.avchatkit.TeamAVChatProfile;
import com.keydom.ih_common.avchatkit.common.log.LogUtil;
import com.keydom.ih_common.avchatkit.teamavchat.activity.TeamAVChatFragment;
import com.keydom.ih_common.bean.MessageEvent;
import com.keydom.ih_common.constant.Const;
import com.keydom.ih_common.constant.EventType;
import com.keydom.ih_common.im.activity.TeamNotificationHelper;
import com.keydom.ih_common.im.config.ImConstants;
import com.keydom.ih_common.im.listener.OnRecentContactListener;
import com.keydom.ih_common.im.listener.OnRecentContactsListener;
import com.keydom.ih_common.im.listener.OnTimeListener;
import com.keydom.ih_common.im.listener.QueryMessageListener;
import com.keydom.ih_common.im.listener.observer.LoginSyncDataStatusObserver;
import com.keydom.ih_common.im.listener.observer.PhoneCallStateObserver;
import com.keydom.ih_common.im.listener.observer.SimpleAVChatStateObserver;
import com.keydom.ih_common.im.listener.observer.TeamChangedObservable;
import com.keydom.ih_common.im.listener.observer.UserInfoObservable;
import com.keydom.ih_common.im.listener.observer.UserInfoObserver;
import com.keydom.ih_common.im.manager.ImPreferences;
import com.keydom.ih_common.im.manager.NimUserInfoCache;
import com.keydom.ih_common.im.manager.TeamDataCache;
import com.keydom.ih_common.im.model.ImMessageConstant;
import com.keydom.ih_common.im.model.ImUIMessage;
import com.keydom.ih_common.im.model.event.UserInfoChangeEvent;
import com.keydom.ih_common.im.profile.AVChatProfile;
import com.keydom.ih_common.im.widget.provider.DefaultTeamProvider;
import com.keydom.ih_common.im.widget.provider.DefaultUserInfoProvider;
import com.keydom.ih_common.im.widget.provider.IUserInfoProvider;
import com.keydom.ih_common.im.widget.provider.NimUserInfoProvider;
import com.keydom.ih_common.im.widget.provider.TeamProvider;
import com.keydom.ih_common.minterface.OnLoginListener;
import com.keydom.ih_common.utils.FloatPermissionManager;
import com.keydom.ih_common.utils.ImageLoader;
import com.keydom.ih_common.utils.permissions.PermissionListener;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.ResponseCode;
import com.netease.nimlib.sdk.SDKOptions;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.StatusCode;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.ClientType;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.auth.OnlineClient;
import com.netease.nimlib.sdk.avchat.AVChatCallback;
import com.netease.nimlib.sdk.avchat.AVChatManager;
import com.netease.nimlib.sdk.avchat.constant.AVChatControlCommand;
import com.netease.nimlib.sdk.avchat.model.AVChatChannelInfo;
import com.netease.nimlib.sdk.avchat.model.AVChatData;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MessageNotifierCustomization;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.attachment.AudioAttachment;
import com.netease.nimlib.sdk.msg.attachment.ImageAttachment;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachment;
import com.netease.nimlib.sdk.msg.attachment.NotificationAttachment;
import com.netease.nimlib.sdk.msg.attachment.VideoAttachment;
import com.netease.nimlib.sdk.msg.constant.MsgDirectionEnum;
import com.netease.nimlib.sdk.msg.constant.MsgStatusEnum;
import com.netease.nimlib.sdk.msg.constant.MsgTypeEnum;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.AttachmentProgress;
import com.netease.nimlib.sdk.msg.model.CustomMessageConfig;
import com.netease.nimlib.sdk.msg.model.CustomNotification;
import com.netease.nimlib.sdk.msg.model.CustomNotificationConfig;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.msg.model.QueryDirectionEnum;
import com.netease.nimlib.sdk.msg.model.RecentContact;
import com.netease.nimlib.sdk.team.TeamService;
import com.netease.nimlib.sdk.team.constant.TeamFieldEnum;
import com.netease.nimlib.sdk.team.constant.TeamTypeEnum;
import com.netease.nimlib.sdk.team.model.CreateTeamResult;
import com.netease.nimlib.sdk.uinfo.UserService;
import com.netease.nimlib.sdk.uinfo.constant.UserInfoFieldEnum;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;
import com.orhanobut.logger.Logger;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class ImClient {

    private static WeakReference<Context> contextWeakReference;

    /**
     * 音视频通话时长观察者
     */
    private static Disposable timeDisposable;
    /**
     * 音视频通话监听
     */
    private static OnTimeListener listener;

    /**
     * 用户信息提供者
     */
    private static IUserInfoProvider userInfoProvider;

    /**
     * 用户信息变更观察者
     */
    private static UserInfoObservable userInfoObservable;
    /**
     * 群资料提供者
     */
    private static DefaultTeamProvider teamProvider;
    /**
     * 群信息变更观察者
     */
    private static TeamChangedObservable teamChangedObservable;

    /**
     * 通知栏图片管理组件
     */
    private static ImageLoader imageLoader;

    /**
     * IM 通知栏跳转的Activity
     */
    public static Class<? extends Activity> notificationEntrance;

    /**
     * 通知栏提醒数组
     */
    private static SparseArray<Notification> notifications = new SparseArray<>();

    /**
     * 多端登录观察者
     */
    public static Observer<List<OnlineClient>> clientsObserver =
            new Observer<List<OnlineClient>>() {
                @Override
                public void onEvent(List<OnlineClient> onlineClients) {
                    if (onlineClients == null || onlineClients.size() == 0) {
                        return;
                    }
                    OnlineClient client = onlineClients.get(0);
                    switch (client.getClientType()) {
                        case ClientType.Windows:
                            // PC端
                            break;
                        case ClientType.MAC:
                            // MAC端
                            break;
                        case ClientType.Web:
                            // Web端
                            break;
                        case ClientType.iOS:
                            // IOS端
                            break;
                        case ClientType.Android:
                            // Android端
                            break;
                        default:
                            break;
                    }
                }
            };

    /**
     * 监听用户在线状态
     */
    public static Observer<StatusCode> userStatusObserver = new Observer<StatusCode>() {
        @Override
        public void onEvent(StatusCode status) {
            if (status.wontAutoLoginForever()) {
                //  被踢出、账号被禁用、密码错误等情况，自动登录失败，需要返回到登录界面进行重新登录操作
                Logger.e("发送挤掉线通知。");
                EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.OFFLINE).build());
            }
        }
    };

    /**
     * 消息接收观察者
     * 此处EventBus 发送的事件在{@link com.keydom.ih_common.im.widget.ImMessageList#onMessageEvent}中处理
     */
    public static Observer<List<IMMessage>> incomingMessageObserver =
            new Observer<List<IMMessage>>() {
                @Override
                public void onEvent(List<IMMessage> imMessages) {
                    for (final IMMessage imMessage : imMessages) {
                        if (imMessage.getDirect() == MsgDirectionEnum.In) {
                            if (imMessage.getAttachment() instanceof ImageAttachment
                                    || imMessage.getAttachment() instanceof VideoAttachment
                                    || imMessage.getAttachment() instanceof AudioAttachment) {
                                NIMClient.getService(MsgService.class).downloadAttachment(imMessage, true).setCallback(new RequestCallbackWrapper() {
                                    @Override
                                    public void onResult(int code, Object result,
                                                         Throwable exception) {
                                        if (code == ResponseCode.RES_SUCCESS) {
                                            EventBus.getDefault().post(ImUIMessage.obtain(imMessage,
                                                    imMessage.getDirect()));
                                        }
                                    }
                                });
                            } else if (imMessage.getAttachment() instanceof NotificationAttachment) {
                                teamSystemMessageNotify(imMessage);
                            } else {
                                EventBus.getDefault().post(ImUIMessage.obtain(imMessage,
                                        imMessage.getDirect()));
                            }
                        } else {
                            if (imMessage.getAttachment() instanceof NotificationAttachment) {
                                teamSystemMessageNotify(imMessage);
                            } else {
                                EventBus.getDefault().post(ImUIMessage.obtain(imMessage));
                            }
                        }
                    }
                }
            };

    public static void teamSystemMessageNotify(IMMessage imMessage) {
        NotificationAttachment attachment = (NotificationAttachment) imMessage.getAttachment();
        String msg = TeamNotificationHelper.getTeamNotificationText(imMessage.getSessionId(),
                imMessage.getFromAccount(), attachment);
        IMMessage message = ImClient.createLocalTipMessage(imMessage.getSessionId(),
                imMessage.getSessionType(), msg);
        EventBus.getDefault().post(ImUIMessage.obtain(message, message.getDirect()));
    }
    //    /**
    //     * 系统消息
    //     */
    //    public static Observer<SystemMessage> incomingSystemMessageObserver =
    //            new Observer<SystemMessage>() {
    //
    //        @Override
    //        public void onEvent(SystemMessage systemMessage) {
    //            if (systemMessage != null) {
    //                Logger.e("systemMessage null");
    //            } else {
    //                Logger.e("systemMessage:" + systemMessage.getContent());
    //            }
    //        }
    //    };
    //    /**
    //     * 系统消息
    //     */
    //    public static Observer<CustomNotification> customNotificationObserver =
    //            new Observer<CustomNotification>() {
    //
    //        @Override
    //        public void onEvent(CustomNotification customNotification) {
    //            if (customNotification != null) {
    //                Logger.e("customNotification null");
    //            } else {
    //                Logger.e("customNotification:" + customNotification.getContent());
    //            }
    //        }
    //    };


    /**
     * 消息附件上传/下载进度观察者
     * 此处EventBus
     * 发送的事件在{@link com.keydom.ih_common.im.activity.PlayVideoActivity#onAttachmentDownloadEvent}中处理
     */
    public static Observer<AttachmentProgress> attachmentProgressObserver =
            new Observer<AttachmentProgress>() {
                @Override
                public void onEvent(AttachmentProgress progress) {
                    EventBus.getDefault().post(progress);
                }
            };

    /**
     * 消息状态观察者
     */
    //    public static Observer<IMMessage> statusObserver = new Observer<IMMessage>() {
    //        @Override
    //        public void onEvent(IMMessage message) {
    //            EventBus.getDefault().post(message);
    //        }
    //    };

    public static SimpleAVChatStateObserver simpleAVChatStateObserver =
            new SimpleAVChatStateObserver();


    public static Observer<AVChatData> inComingCallObserver = new Observer<AVChatData>() {
        @Override
        public void onEvent(AVChatData avChatData) {
            String extra = avChatData.getExtra();
            Log.e("Extra", "Extra Message->" + extra);
            if (PhoneCallStateObserver.getInstance().getPhoneCallState() != PhoneCallStateObserver.PhoneCallStateEnum.IDLE
                    || AVChatProfile.getInstance().isAVChatting()
                    || AVChatManager.getInstance().getCurrentChatId() != 0) {
                Logger.i("reject incoming call data =" + avChatData.toString() + " as local phone" +
                        " is not idle");
                AVChatManager.getInstance().sendControlCommand(avChatData.getChatId(),
                        AVChatControlCommand.BUSY, null);
                return;
            }
            // 有网络来电打开AVChatActivity
            AVChatProfile.getInstance().setAVChatting(true);
            AVChatProfile.getInstance().launchActivity(avChatData, "", 0);

        }
    };


    public static void init(Context context) {
        contextWeakReference = new WeakReference<>(context);
        NIMClient.init(context, loginInfo(), options(context));
        NIMClient.toggleNotification(true);
    }

    private static SDKOptions options(Context context) {
        SDKOptions options = new SDKOptions();

        // 如果将新消息通知提醒托管给 SDK 完成，需要添加以下配置。否则无需设置。
        StatusBarNotificationConfig config = new StatusBarNotificationConfig();
        config.notificationEntrance = notificationEntrance;
        //        config.notificationSmallIconId = R.drawable.ic_stat_notify_msg;
        // 呼吸灯配置
        config.ledARGB = Color.GREEN;
        config.ledOnMs = 1000;
        config.ledOffMs = 1500;
        // 通知铃声的uri字符串
        //        config.notificationSound = "android.resource://com.netease.nim.demo/raw/msg";
        options.statusBarNotificationConfig = config;
        options.messageNotifierCustomization = new MessageNotifierCustomization() {
            @Override
            public String makeNotifyContent(String nick, IMMessage message) {
                if (message.getMsgType() == MsgTypeEnum.custom) {
                    return message.getContent();
                } else {
                    return null;
                }
            }

            @Override
            public String makeTicker(String nick, IMMessage message) {
                return null;
            }

            @Override
            public String makeRevokeMsgTip(String revokeAccount, IMMessage item) {
                return null;
            }
        };
        // 配置保存图片，文件，log 等数据的目录
        // 如果 options 中没有设置这个值，SDK 会使用采用默认路径作为 SDK 的数据目录。
        // 该目录目前包含 log, file, image, audio, video, thumb 这6个目录。
        // 可以不设置，那么将采用默认路径
        if (getAppCacheDir(context) != null) {
            options.sdkStorageRootPath = getAppCacheDir(context);
        }
        // 配置是否需要预下载附件缩略图，默认为 true
        options.preloadAttach = true;

        // 配置附件缩略图的尺寸大小。表示向服务器请求缩略图文件的大小
        // 该值一般应根据屏幕尺寸来确定， 默认值为 Screen.width / 2
        //        options.thumbnailSize = ${Screen.width} / 2;

        // 用户资料提供者, 目前主要用于提供用户资料，用于新消息通知栏中显示消息来源的头像和昵称
        options.userInfoProvider = new NimUserInfoProvider(contextWeakReference.get());

        return options;
    }

    public static IUserInfoProvider getUserInfoProvider() {
        if (userInfoProvider == null) {
            userInfoProvider = new DefaultUserInfoProvider();
        }
        return userInfoProvider;
    }


    public static UserInfoObservable getUserInfoObservable() {
        if (userInfoObservable == null) {
            userInfoObservable = new UserInfoObservable(contextWeakReference.get());
        }
        return userInfoObservable;
    }

    public static TeamProvider getTeamProvider() {
        if (teamProvider == null) {
            teamProvider = new DefaultTeamProvider();
        }
        return teamProvider;
    }

    public static TeamChangedObservable getTeamChangedObservable() {
        if (teamChangedObservable == null) {
            teamChangedObservable = new TeamChangedObservable(contextWeakReference.get());
        }
        return teamChangedObservable;
    }

    public static ImageLoader getImageLoader() {
        if (imageLoader == null) {
            imageLoader = new ImageLoader(contextWeakReference.get());
        }
        return imageLoader;
    }


    /**
     * 获取通知栏提醒数组
     */
    public static SparseArray<Notification> getNotifications() {
        return notifications;
    }

    /**
     * 用户信息变更观察者<br>
     * 此Event事件在{@link com.keydom.ih_common.im.widget.ImMessageList#onUserInfoChangeEvent}中处理
     */
    public static UserInfoObserver userInfoObserver = new UserInfoObserver() {
        @Override
        public void onUserInfoChanged(List<String> accounts) {
            UserInfoChangeEvent changeEvent = new UserInfoChangeEvent();
            changeEvent.setAccounts(accounts);
            EventBus.getDefault().post(changeEvent);
        }
    };

    /**
     * 修改IM 用户资料<br>
     *
     * @param field    必须是UserInfoFieldEnum里面的字段
     * @param value    值
     * @param callback callback 修改回调， code==200 成功
     */
    public static void updateUserInfo(final UserInfoFieldEnum field, final Object value,
                                      RequestCallbackWrapper<Void> callback) {
        Map<UserInfoFieldEnum, Object> fields = new HashMap<>(1);
        fields.put(field, value);
        updateUserInfo(fields, callback);
    }

    /**
     * 修改IM 用户资料<br>
     * <p>
     *
     * @param fields   Map集合，key必须是UserInfoFieldEnum里面的字段
     * @param callback 修改回调， code==200 成功
     */
    public static void updateUserInfo(final Map<UserInfoFieldEnum, Object> fields,
                                      final RequestCallbackWrapper<Void> callback) {
        NIMClient.getService(UserService.class).updateUserInfo(fields).setCallback(new RequestCallbackWrapper<Void>() {
            @Override
            public void onResult(int code, Void result, Throwable exception) {
                if (callback != null) {
                    callback.onResult(code, result, exception);
                }
            }
        });
    }

    /**
     * 查询最近联系人列表数据<br>
     * 最后一条消息，未读消息数等，都在RecentContact里面
     *
     * @param listener 回调
     */
    public static void queryRecentContacts(final OnRecentContactsListener listener) {
        NIMClient.getService(MsgService.class).queryRecentContacts()
                .setCallback(new RequestCallbackWrapper<List<RecentContact>>() {
                    @Override
                    public void onResult(int code, List<RecentContact> recents, Throwable e) {
                        if (listener != null && code == ResponseCode.RES_SUCCESS) {
                            listener.onRecentResult(recents);
                        }
                    }
                });
    }


    /**
     * 查询联系人聊天数据<br>
     * 最后一条消息，未读消息数等，都在RecentContact里面
     *
     * @param contactId   会话id ，对方帐号或群组id。
     * @param sessionType 会话类型。
     * @param listener    回调
     * @return RecentContact ，如果没有，则返回null。
     */
    public static void queryRecentContact(String contactId, SessionTypeEnum sessionType,
                                          final OnRecentContactListener listener) {
        if (null != listener) {
            listener.onRecentResult(NIMClient.getService(MsgService.class).queryRecentContact(contactId, sessionType));
        }
    }

    /**
     * 如果已经存在用户登录信息，返回LoginInfo，否则返回null即可
     */
    private static LoginInfo loginInfo() {
        String account = ImPreferences.getUserAccount();
        String token = ImPreferences.getUserToken();
        if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(token)) {
            return new LoginInfo(account, token);
        }
        return null;
    }

    /**
     * 配置 APP 保存图片/语音/文件/log等数据的目录
     * SD卡的应用扩展存储目录
     * SD卡应用公共存储区(APP卸载后，该目录不会被清除，下载安装APP后，缓存数据依然可以被加载。SDK默认使用此目录)，该存储区域需要写权限!
     */
    private static String getAppCacheDir(Context context) {
        String storageRootPath = null;
        if (context.getExternalCacheDir() != null) {
            try {
                storageRootPath = context.getExternalCacheDir().getCanonicalPath();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (TextUtils.isEmpty(storageRootPath)) {
                storageRootPath =
                        Environment.getExternalStorageDirectory() + "/" + context.getPackageName();
            }
            return storageRootPath;
        }
        return null;
    }

    /**
     * 判断im登录状态
     */
    public static boolean isLoginedIM() {
        StatusCode status = NIMClient.getStatus();
        return status == StatusCode.LOGINED;
    }

    public static void loginIM(final String account, String token, final OnLoginListener listener) {
        LoginInfo loginInfo = new LoginInfo(account, token);
        RequestCallback<LoginInfo> callback = new RequestCallback<LoginInfo>() {
            @Override
            public void onSuccess(LoginInfo param) {
                if (listener != null) {
                    listener.success("网易云信登录成功");
                }
               /* if (BuildConfig.DEBUG) {

                }*/
            }

            @Override
            public void onFailed(int code) {
                if (listener != null) {
                    Logger.e("网易云信登录失败:" + code);
                    listener.failed("网易云信登录失败" + code);
                }

            }

            @Override
            public void onException(Throwable exception) {
                //                Toast.makeText(contextWeakReference.get(), "IM登录异常" + exception
                //                .getMessage(), Toast.LENGTH_SHORT).show();
                exception.printStackTrace();
            }
        };
        NIMClient.getService(AuthService.class).login(loginInfo).setCallback(callback);
    }


    /**
     * IM登出
     */
    public static void loginOut() {
        ImPreferences.saveUserAccount("");
        ImPreferences.saveUserToken("");
        NimUserInfoCache.getInstance().clear();
        TeamDataCache.getInstance().clear();
        NIMClient.getService(AuthService.class).logout();
        LoginSyncDataStatusObserver.getInstance().reset();
    }

    /**
     * 设置音视频通话时间监听
     *
     * @param listener OnTimeListener
     */
    public static void setOnTimeListener(OnTimeListener listener) {
        ImClient.listener = listener;
    }

    /**
     * 启动音视频通话时长计时
     */
    public static void startTimer() {
        timeDisposable = Flowable.interval(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if (listener != null) {
                            listener.onTimeResult(aLong);
                        }
                    }
                });
    }

    /**
     * 结束音视频通话时长计时
     */
    public static void stopTimer() {
        if (timeDisposable != null) {
            if (!timeDisposable.isDisposed()) {
                timeDisposable.dispose();
            }
            timeDisposable = null;
        }
    }

    /**
     * 创建群组
     *
     * @param members  邀请加入的成员帐号列表
     * @param callback
     */
    private static void createTeam(List<String> members,
                                   RequestCallback<CreateTeamResult> callback) {
        createTeam(null, TeamTypeEnum.Advanced, "", members, callback);
    }

    /**
     * @param fields     群组预设资料, key为数据字段，value对应的值，
     *                   该值类型必须和field中定义的fieldType一致。
     * @param postscript 邀请入群的附言。如果是创建临时群，该参数无效
     * @param members    邀请加入的成员帐号列表
     */
    public static void createTeam(Map<TeamFieldEnum, Serializable> fields, String postscript,
                                  List<String> members,
                                  RequestCallback<CreateTeamResult> callback) {
        createTeam(fields, TeamTypeEnum.Advanced, postscript, members, callback);
    }

    /**
     * @param fields     群组预设资料, key为数据字段，value对应的值，
     *                   该值类型必须和field中定义的fieldType一致。
     * @param type       要创建的群组类型
     * @param postscript 邀请入群的附言。如果是创建临时群，该参数无效
     * @param members    邀请加入的成员帐号列表
     */
    public static void createTeam(Map<TeamFieldEnum, Serializable> fields, TeamTypeEnum type,
                                  String postscript, List<String> members,
                                  RequestCallback<CreateTeamResult> callback) {
        NIMClient.getService(TeamService.class).createTeam(fields, type, postscript, members).setCallback(callback);
    }


    /**
     * 启动会话界面
     *
     * @param context   上下文
     * @param sessionId 会话对象ID
     * @param bundle    bundle界面需要字段，与IM无关
     */
    public static void startConversation(Context context, String sessionId, Bundle bundle) {
        if (requestCallPermissions((AppCompatActivity) context) && FloatPermissionManager.INSTANCE.applyFloatWindow(context)) {
            context.startActivity(getConversationIntent(context, sessionId, bundle));
        }
    }

    public static final int MY_DOCTOR_CONVERSATION = 520;

    public static void startMyDoctorConversation(Context context, String sessionId, Bundle bundle) {
        if (requestCallPermissions((AppCompatActivity) context) && FloatPermissionManager.INSTANCE.applyFloatWindow(context)) {
            Intent starter = getConversationIntent(context, sessionId, bundle);
            starter.putExtra(Const.TYPE, MY_DOCTOR_CONVERSATION);
            context.startActivity(getConversationIntent(context, sessionId, bundle));
        }
    }

    /**
     * 获取启动会话界面用Intent
     *
     * @param context   上下文
     * @param sessionId 会话对象Id
     * @param bundle    bundle
     * @return
     */
    private static Intent getConversationIntent(Context context, String sessionId, Bundle bundle) {
        NimUserInfo userInfo = (NimUserInfo) getUserInfoProvider().getUserInfo(sessionId);
        String userType;
        String path = "";
        if (userInfo != null && userInfo.getExtensionMap() != null) {
            userType = userInfo.getExtensionMap().get(ImConstants.CALL_USER_TYPE).toString();
        } else {
            if ("com.keydom.mianren.ih_patient".equals(context.getPackageName())) {
                userType = ImMessageConstant.DOCTOR;
            } else {
                userType = ImMessageConstant.PATIENT;
            }
        }

        switch (userType) {
            case ImMessageConstant.DOCTOR:
                path = "from_doctor_conversation";
                break;
            case ImMessageConstant.PATIENT:
                path = "from_patient_conversation";
                break;
            default:
        }
        Uri.Builder builder = Uri.parse("im://" + context.getPackageName()).buildUpon();
        builder.appendPath(path);
        builder.appendQueryParameter(ImConstants.CALL_SESSION_ID, sessionId.toLowerCase());
        Uri uri = builder.build();
        Intent intent = new Intent("android.intent.action.VIEW", uri);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        return intent;
    }

    /**
     * 启动医生协作聊天界面
     */
    public static void startTeamChart(Context context, String teamId, Bundle bundle) {
        if (requestCallPermissions((AppCompatActivity) context) && FloatPermissionManager.INSTANCE.applyFloatWindow(context)) {
            context.startActivity(getTeamChatIntent(context, teamId, bundle));
        }
    }

    /**
     * 获取启动会话界面用Intent
     *
     * @param context 上下文
     * @param teamId  群聊Id
     * @param bundle  bundle
     * @return
     */
    private static Intent getTeamChatIntent(Context context, String teamId, Bundle bundle) {
        String path = "from_doctor_team";
        Uri.Builder builder = Uri.parse("im://" + context.getPackageName()).buildUpon();
        builder.appendPath(path);
        builder.appendQueryParameter(ImConstants.CALL_SESSION_ID, teamId);
        Uri uri = builder.build();
        Intent intent = new Intent("android.intent.action.VIEW", uri);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        return intent;
    }

    /**
     * 发起视频聊天
     *
     * @param activity  当前Activity
     * @param sessionId 发起音视频聊天对方id
     */
    @SuppressLint("CheckResult")
    public static void startAVChatCall(final AppCompatActivity activity, final String sessionId,
                                       final int avChatType) {
        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO,
                Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) throws Exception {
                        if (granted) {
                            NimUserInfo userInfo =
                                    (NimUserInfo) getUserInfoProvider().getUserInfo(sessionId);
                            String type;
                            if (userInfo == null || null == userInfo.getExtensionMap()) {
                                if ("com.keydom.mianren.ih_patient".equals(activity.getPackageName())) {
                                    type = ImMessageConstant.DOCTOR;
                                } else {
                                    type = ImMessageConstant.PATIENT;
                                }
                            } else {
                                type = userInfo.getExtensionMap().get(ImConstants.CALL_USER_TYPE).toString();
                            }

                            String action = "";
                            if (ImMessageConstant.DOCTOR.equals(type)) {
                                action = ImConstants.IM_INTENT_ACTION_VIDEO_DOCTOR;
                            } else if (ImMessageConstant.PATIENT.equals(type)) {
                                action = ImConstants.IM_INTENT_ACTION_VIDEO_USER;
                            }
                            Intent intent = new Intent(action);
                            Bundle bundle = new Bundle();
                            bundle.putString(ImConstants.CALL_SESSION_ID, sessionId);
                            bundle.putInt(ImConstants.CALL_AVCHAT_TYPE, avChatType);
                            bundle.putString(ImConstants.CALL_USER_TYPE, type);
                            intent.putExtra(ImConstants.CALL_CALL_ACTION,
                                    ImMessageConstant.ACTION_OUTGOING_CALL);
                            intent.putExtras(bundle);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.setPackage(activity.getPackageName());
                            activity.startActivity(intent);
                        }
                    }
                });
    }


    /**
     * 接起音视频通话
     *
     * @param context    上下文
     * @param avChatData 音视频通话信息
     */
    public static void startVideoActivity(Context context, AVChatData avChatData) {
        NimUserInfo userInfo =
                (NimUserInfo) getUserInfoProvider().getUserInfo(avChatData.getAccount());
        String type;
        if (null == userInfo.getExtensionMap()) {
            if ("com.keydom.mianren.ih_patient".equals(context.getPackageName())) {
                type = ImMessageConstant.DOCTOR;
            } else {
                type = ImMessageConstant.PATIENT;
            }
        } else {
            type = userInfo.getExtensionMap().get(ImConstants.CALL_USER_TYPE).toString();
        }
        String action = "";
        if (ImMessageConstant.DOCTOR.equals(type)) {
            action = ImConstants.IM_INTENT_ACTION_VIDEO_DOCTOR;
        } else if (ImMessageConstant.PATIENT.equals(type)) {
            action = ImConstants.IM_INTENT_ACTION_VIDEO_USER;
        }
        Intent intent = new Intent(action);
        Bundle bundle = new Bundle();
        bundle.putString(ImConstants.CALL_USER_TYPE, type);
        intent.putExtra(ImConstants.CALL_CALL_ACTION, ImMessageConstant.ACTION_INCOMING_CALL);
        bundle.putSerializable(ImConstants.CALL_AVCHAT_DATA, avChatData);
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setPackage(context.getPackageName());
        context.startActivity(intent);
    }

    public static void createRoom(final Context context, final String teamId,
                                  final ArrayList<String> accounts) {
        createRoom(context, teamId, accounts, "", null);
    }

    public static void createRoom(final Context context, final String teamId,
                                  final ArrayList<String> accounts, String teamChatType,
                                  final TeamAVChatFragment.CreateRoomCallback callback) {
        //final String roomName = teamChatType + UUID.randomUUID().toString().replaceAll("-", "");
        final String roomName = teamChatType + teamId;
        // 创建房间
        AVChatManager.getInstance().createRoom(roomName, null,
                new AVChatCallback<AVChatChannelInfo>() {
                    @Override
                    public void onSuccess(AVChatChannelInfo avChatChannelInfo) {
                        LogUtil.ui("create room " + roomName + " success !");
                        onCreateRoomSuccess(context, teamId, roomName, accounts);

                        String teamName = getTeamProvider().getTeamById(teamId).getName();

                        TeamAVChatProfile.sharedInstance().setTeamAVChatting(true);

                        if (callback == null) {
                            AVChatKit.outgoingTeamCall(context, false, teamId, roomName, accounts,
                                    teamName);
                        } else {
                            callback.success(roomName);
                        }
                    }

                    @Override
                    public void onFailed(int code) {
                        onCreateRoomFail(context, teamId);
                        if (callback != null) {
                            callback.failed(code);
                        }
                    }

                    @Override
                    public void onException(Throwable exception) {
                        onCreateRoomFail(context, teamId);
                        if (callback != null) {
                            callback.failed(-1);
                        }
                    }
                });
    }

    private static void onCreateRoomSuccess(Context context, String teamID, String roomName,
                                            ArrayList<String> accounts) {
        // 在群里发送tip消息
        IMMessage message = MessageBuilder.createTipMessage(teamID, SessionTypeEnum.Team);
        CustomMessageConfig tipConfig = new CustomMessageConfig();
        tipConfig.enableHistory = false;
        tipConfig.enableRoaming = false;
        tipConfig.enablePush = false;
        String teamNick =
                ImClient.getUserInfoProvider().getUserInfo(AVChatKit.getAccount()).getName();
        message.setContent(teamNick + context.getString(R.string.t_avchat_start));
        message.setConfig(tipConfig);
        sentMessage(message, false, null);
        // 对各个成员发送点对点自定义通知
        String teamName = getTeamProvider().getTeamById(teamID).getName();
        String content = TeamAVChatProfile.sharedInstance().buildContent(roomName, teamID,
                accounts, teamName);
        CustomNotificationConfig config = new CustomNotificationConfig();
        config.enablePush = true;
        config.enablePushNick = false;
        config.enableUnreadCount = true;

        for (String account : accounts) {
            CustomNotification command = new CustomNotification();
            command.setSessionId(account);
            command.setSessionType(SessionTypeEnum.P2P);
            command.setConfig(config);
            command.setContent(content);
            command.setApnsText(teamNick + context.getString(R.string.t_avchat_push_content));

            command.setSendToOnlineUserOnly(false);
            NIMClient.getService(MsgService.class).sendCustomNotification(command);
        }
    }

    private static void onCreateRoomFail(Context context, String teamId) {
        // 本地插一条tip消息
        IMMessage message = MessageBuilder.createTipMessage(teamId, SessionTypeEnum.Team);
        message.setContent(context.getString(R.string.t_avchat_create_room_fail));
        LogUtil.i("status", "team action set:" + MsgStatusEnum.success);
        message.setStatus(MsgStatusEnum.success);
        NIMClient.getService(MsgService.class).saveMessageToLocal(message, true);
    }

    /**
     * 设置是否允许播放远端用户语音流、视频流数据。
     *
     * @param muted true 不解码
     */
    public static void muteRemoteAudioAndVideo(String account, boolean muted) {
        //音频
        AVChatManager.getInstance().muteRemoteAudio(account, muted);
        //视频
        AVChatManager.getInstance().muteRemoteVideo(account, muted);
    }

    public static boolean isRemoteAudioMuted(String account) {
        return AVChatManager.getInstance().isRemoteAudioMuted(account);
    }

    public static boolean isRemoteVideoMuted(String account) {
        return AVChatManager.getInstance().isRemoteVideoMuted(account);
    }

    /**
     * 发送消息
     *
     * @param imMessage 消息实体
     * @param resend    是否是重新发送
     * @param callback  回调
     */
    public static void sentMessage(IMMessage imMessage, boolean resend,
                                   RequestCallback<Void> callback) {
        if (imMessage.getMsgType() == MsgTypeEnum.tip) {
            Map<String, Object> map = imMessage.getRemoteExtension();
            if (map != null && map.containsKey(ImConstants.LOCAL_TIP)) {
                NIMClient.getService(MsgService.class)
                        .saveMessageToLocal(imMessage, resend)
                        .setCallback(getRequestCallback(callback));
            } else {
                NIMClient.getService(MsgService.class)
                        .sendMessage(imMessage, resend)
                        .setCallback(getRequestCallback(callback));
            }
        } else {
            NIMClient.getService(MsgService.class)
                    .sendMessage(imMessage, resend)
                    .setCallback(getRequestCallback(callback));
        }
    }

    private static RequestCallback<Void> getRequestCallback(final RequestCallback<Void> callback) {
        return new RequestCallback<Void>() {
            @Override
            public void onSuccess(Void param) {
                Log.e("IM_TAG", "消息发送成功");
                if (callback != null) {
                    callback.onSuccess(param);
                }
            }

            @Override
            public void onFailed(int code) {
                Log.e("IM_TAG", "消息发送失败:" + code);
                if (callback != null) {
                    callback.onFailed(code);
                }
            }

            @Override
            public void onException(Throwable exception) {
                exception.printStackTrace();
                if (callback != null) {
                    callback.onException(exception);
                }
            }
        };
    }

    /**
     * 检索指定的会话，返回该会话中与检索串匹配的所有消息记录。（异步函数）
     *
     * @param query     待检索的字符串
     * @param sessionId 待检索的会话ID
     */
    /*public static void searchSession(String query, String sessionId, final
    MsgIndexRecordListener listener) {
        NIMClient.getService(LuceneService.class).searchSession(query, SessionTypeEnum.P2P,
        sessionId)
                .setCallback(new RequestCallbackWrapper<List<MsgIndexRecord>>() {
                    @Override
                    public void onResult(int code, List<MsgIndexRecord> result, Throwable
                    exception) {
                        if (listener != null) {
                            if (code == ResponseCode.RES_SUCCESS) {
                                listener.onSuccess(result);
                            } else {
                                listener.onFailed(code, exception);
                            }
                        }
                    }
                });
    }*/

    /**
     * 根据锚点和方向从本地消息数据库中查询消息历史。
     * 根据提供的方向(direct)，查询比anchor更老（QUERY_OLD）或更新（QUERY_NEW）的最靠近anchor的limit条数据。
     * 调用者可使用asc参数指定结果排序规则，结果使用time作为排序字段。
     * 注意：查询结果不包含锚点。
     *
     * @param anchor    锚点
     * @param direction 方向
     * @param limit     查询条数
     * @param asc       排序规则，如果为 true，结果按照时间升序排列，如果为 false，按照时间降序排列
     */
    public static void queryMessageListEx(IMMessage anchor, QueryDirectionEnum direction,
                                          final int limit, boolean asc,
                                          final QueryMessageListener listener) {

        //        NIMClient.getService(MsgService.class).pullMessageHistory(anchor, limit, asc)
        //                .setCallback(new RequestCallback<List<IMMessage>>() {
        //                    @Override
        //                    public void onSuccess(List<IMMessage> param) {
        //                        if (listener != null) {
        //                            if (param != null && param.size() > 0) {
        //                                Collections.reverse(param);
        //                            }
        //                            listener.onSuccess(param);
        //                        }
        //                    }
        //
        //                    @Override
        //                    public void onFailed(int code) {
        //                        if (listener != null) {
        //                            listener.onFailed(code);
        //                        }
        //                    }
        //
        //                    @Override
        //                    public void onException(Throwable exception) {
        //                        if (listener != null) {
        //                            listener.onException(exception);
        //                        }
        //                    }
        //                });


        NIMClient.getService(MsgService.class).queryMessageListEx(anchor, direction, limit, asc)
                .setCallback(new RequestCallback<List<IMMessage>>() {
                    @Override
                    public void onSuccess(List<IMMessage> param) {
                        if (listener != null) {
                            listener.onSuccess(param);
                        }
                    }

                    @Override
                    public void onFailed(int code) {
                        if (listener != null) {
                            listener.onFailed(code);
                        }
                    }

                    @Override
                    public void onException(Throwable exception) {
                        if (listener != null) {
                            listener.onException(exception);
                        }
                    }
                });
    }

    /**
     * 根据起始、截止时间点以及查询方向从本地消息数据库中查询消息历史。
     * 根据提供的方向 (direction)，以 anchor 为起始点，往前或往后查询由 anchor 到 toTime 之间靠近 anchor 的 limit 条消息。
     * 查询范围由 toTime 和 limit 共同决定，以先到为准。如果到 toTime 之间消息大于 limit 条，返回 limit 条记录，如果小于 limit 条，返回实际条数。
     * 查询结果排序规则：direction 为 QUERY_OLD 时 按时间降序排列，direction 为 QUERY_NEW 时按照时间升序排列。
     * 注意：查询结果不包含锚点。
     *
     * @return 调用跟踪，可设置回调函数，接收查询结果
     */
    public static void queryMessageListExTime(String sessionId, SessionTypeEnum sessionType,
                                              long targetTime, long toTime,
                                              QueryDirectionEnum direction, int limit,
                                              final QueryMessageListener listener) {
        IMMessage anchor = MessageBuilder.createEmptyMessage(sessionId, sessionType, targetTime);
        NIMClient.getService(MsgService.class).queryMessageListExTime(anchor, toTime, direction,
                limit)
                .setCallback(new RequestCallback<List<IMMessage>>() {
                    @Override
                    public void onSuccess(List<IMMessage> param) {
                        if (listener != null) {
                            listener.onSuccess(param);
                        }
                    }

                    @Override
                    public void onFailed(int code) {
                        Logger.e("获取历史消息失败：" + code);
                    }

                    @Override
                    public void onException(Throwable exception) {
                        exception.printStackTrace();
                    }
                });
    }


    /**
     * 创建一条文本消息
     *
     * @param sessionId   聊天对象ID
     * @param sessionType 会话类型
     * @param text        文本内容
     * @return IMMessage
     */
    public static IMMessage createTextMessage(String sessionId, SessionTypeEnum sessionType,
                                              String text) {
        return MessageBuilder.createTextMessage(sessionId, sessionType, text);
    }

    /**
     * 创建一条图片消息
     *
     * @param sessionId   聊天对象ID
     * @param sessionType 会话类型
     * @param file        图片文件
     * @param displayName 图片文件的显示名，可不同于文件名
     * @return IMMessage
     */
    public static IMMessage createImageMessage(String sessionId, SessionTypeEnum sessionType,
                                               File file, String displayName) {
        return MessageBuilder.createImageMessage(sessionId, sessionType, file, displayName);
    }


    /**
     * 创建一条视频消息
     *
     * @param sessionId   聊天对象ID
     * @param sessionType 会话类型
     * @param filePath    视频文件路径
     */
    public static IMMessage createVideoMessage(String sessionId, SessionTypeEnum sessionType,
                                               String filePath) {
        File file = new File(filePath);
        MediaPlayer mediaPlayer = MediaPlayer.create(contextWeakReference.get(),
                Uri.fromFile(file));
        long duration = mediaPlayer == null ? 0 : mediaPlayer.getDuration();
        int height = mediaPlayer == null ? 0 : mediaPlayer.getVideoHeight();
        int width = mediaPlayer == null ? 0 : mediaPlayer.getVideoWidth();
        return MessageBuilder.createVideoMessage(sessionId, sessionType, file, duration, width,
                height, file.getName());
    }

    /**
     * 创建一条音频消息
     *
     * @param sessionId   聊天对象ID
     * @param sessionType 会话类型
     * @param file        音频文件对象
     * @param duration    音频文件持续时间，单位是ms
     */
    public static IMMessage createAudioMessage(String sessionId, SessionTypeEnum sessionType,
                                               File file, long duration) {
        return MessageBuilder.createAudioMessage(sessionId, sessionType, file, duration);
    }

    /**
     * 创建一条APP自定义类型消息(问诊单), 同时提供描述字段，可用于推送以及状态栏消息提醒的展示。
     *
     * @param sessionId   聊天对象ID
     * @param sessionType 会话类型
     * @param content     消息简要描述，可通过IMMessage#getContent()获取，主要用于用户推送展示。
     * @param attachment  消息附件对象
     */
    public static IMMessage createInquiryMessage(String sessionId, SessionTypeEnum sessionType,
                                                 String content, MsgAttachment attachment) {
        return MessageBuilder.createCustomMessage(sessionId, sessionType, content, attachment,
                new CustomMessageConfig());
    }

    /**
     * 创建一条APP自定义类型消息(问诊结果，处方单), 同时提供描述字段，可用于推送以及状态栏消息提醒的展示。
     *
     * @param sessionId   聊天对象ID
     * @param sessionType 会话类型
     * @param content     消息简要描述，可通过{@link IMMessage#getContent()}获取，主要用于用户推送展示。
     * @param attachment  消息附件对象
     */
    public static IMMessage createConsultationResultMessage(String sessionId,
                                                            SessionTypeEnum sessionType,
                                                            String content,
                                                            MsgAttachment attachment) {
        return MessageBuilder.createCustomMessage(sessionId, sessionType, content, attachment,
                new CustomMessageConfig());
    }

    /**
     * 创建一条APP自定义类型消息(检验申请单), 同时提供描述字段，可用于推送以及状态栏消息提醒的展示。
     *
     * @param sessionId   聊天对象ID
     * @param sessionType 会话类型
     * @param content     消息简要描述，可通过{@link IMMessage#getContent()}获取，主要用于用户推送展示。
     * @param attachment  消息附件对象
     */
    public static IMMessage createInspectionMessage(String sessionId, SessionTypeEnum sessionType
            , String content, MsgAttachment attachment) {
        return MessageBuilder.createCustomMessage(sessionId, sessionType, content, attachment,
                new CustomMessageConfig());
    }

    /**
     * 创建一条APP自定义类型消息(检查申请单), 同时提供描述字段，可用于推送以及状态栏消息提醒的展示。
     *
     * @param sessionId   聊天对象ID
     * @param sessionType 会话类型
     * @param content     消息简要描述，可通过{@link IMMessage#getContent()}获取，主要用于用户推送展示。
     * @param attachment  消息附件对象
     */
    public static IMMessage createExaminationMessage(String sessionId,
                                                     SessionTypeEnum sessionType, String content,
                                                     MsgAttachment attachment) {
        return MessageBuilder.createCustomMessage(sessionId, sessionType, content, attachment,
                new CustomMessageConfig());
    }

    /**
     * 创建一条提示消息
     *
     * @param sessionId   聊天对象ID
     * @param sessionType 会话类型
     * @param tips        提示消息内容
     * @return IMMessage 生成的消息对象
     */
    public static IMMessage createTipMessage(String sessionId, SessionTypeEnum sessionType,
                                             String tips) {
        IMMessage message = MessageBuilder.createTipMessage(sessionId, sessionType);
        message.setContent(tips);
        CustomMessageConfig config = new CustomMessageConfig();
        config.enableUnreadCount = false;
        message.setConfig(config);
        message.setStatus(MsgStatusEnum.success);
        return message;
    }

    /**
     * 创建一条本地提示消息
     *
     * @param sessionId   聊天对象ID
     * @param sessionType 会话类型
     * @param tips        提示消息内容
     * @return IMMessage 生成的消息对象
     */
    public static IMMessage createLocalTipMessage(String sessionId, SessionTypeEnum sessionType,
                                                  String tips) {
        Map<String, Object> map = new HashMap<>();
        map.put(ImConstants.LOCAL_TIP, "local");
        IMMessage message = MessageBuilder.createTipMessage(sessionId, sessionType);
        message.setContent(tips);
        CustomMessageConfig config = new CustomMessageConfig();
        config.enableUnreadCount = false;
        message.setConfig(config);
        message.setStatus(MsgStatusEnum.success);
        message.setRemoteExtension(map);
        return message;
    }

    /**
     * 创建一条APP自定义类型消息(转诊单), 同时提供描述字段，可用于推送以及状态栏消息提醒的展示。
     *
     * @param sessionId   聊天对象ID
     * @param sessionType 会话类型
     * @param content     消息简要描述，可通过{@link IMMessage#getContent()}获取，主要用于用户推送展示。
     * @param attachment  消息附件对象
     */
    public static IMMessage createReferralApplyMessage(String sessionId,
                                                       SessionTypeEnum sessionType,
                                                       String content, MsgAttachment attachment) {
        return MessageBuilder.createCustomMessage(sessionId, sessionType, content, attachment,
                new CustomMessageConfig());
    }

    /**
     * 创建一条APP自定义类型消息(换诊), 同时提供描述字段，可用于推送以及状态栏消息提醒的展示。
     *
     * @param sessionId   聊天对象ID
     * @param sessionType 会话类型
     * @param content     消息简要描述，可通过{@link IMMessage#getContent()}获取，主要用于用户推送展示。
     * @param attachment  消息附件对象
     */
    public static IMMessage createReferralDoctorMessage(String sessionId,
                                                        SessionTypeEnum sessionType,
                                                        String content, MsgAttachment attachment) {
        return MessageBuilder.createCustomMessage(sessionId, sessionType, content, attachment,
                new CustomMessageConfig());
    }

    /**
     * 创建一条结束问诊消息
     *
     * @param sessionId   聊天对象ID
     * @param sessionType 会话类型
     * @param content     消息简要描述，可通过{@link IMMessage#getContent()}获取，主要用于用户推送展示。
     * @param attachment  消息附件对象
     */
    public static IMMessage createEndInquiryMessage(String sessionId, SessionTypeEnum sessionType
            , String content, MsgAttachment attachment) {
        return MessageBuilder.createCustomMessage(sessionId, sessionType, content, attachment,
                new CustomMessageConfig());
    }

    /**
     * 创建一条处置建议消息
     *
     * @param sessionId   聊天对象ID
     * @param sessionType 会话类型
     * @param content     消息简要描述，可通过{@link IMMessage#getContent()}获取，主要用于用户推送展示。
     * @param attachment  消息附件对象
     */
    public static IMMessage createDisposalAdviceMessage(String sessionId,
                                                        SessionTypeEnum sessionType,
                                                        String content, MsgAttachment attachment) {
        return MessageBuilder.createCustomMessage(sessionId, sessionType, content, attachment,
                new CustomMessageConfig());
    }

    /**
     * 创建一条随访表消息
     *
     * @param sessionId   聊天对象ID
     * @param sessionType 会话类型
     * @param content     消息简要描述，可通过{@link IMMessage#getContent()}获取，主要用于用户推送展示。
     * @param attachment  消息附件对象
     */
    public static IMMessage createUserFollowUpMessage(String sessionId,
                                                      SessionTypeEnum sessionType, String content
            , MsgAttachment attachment) {
        return MessageBuilder.createCustomMessage(sessionId, sessionType, content, attachment,
                new CustomMessageConfig());
    }

    private static boolean permissionsResult;

    @SuppressLint("CheckResult")
    private static boolean requestCallPermissions(AppCompatActivity appCompatActivity) {
        String[] permissions = new String[]{Manifest.permission.RECORD_AUDIO,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.INTERNET, Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.MODIFY_AUDIO_SETTINGS};
        boolean granted = true;
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(appCompatActivity, permission) != PackageManager.PERMISSION_GRANTED) {
                granted = false;
            }
        }

        if (granted) {
            permissionsResult = true;
        } else {
            Common.INSTANCE.getPermissions(appCompatActivity).requestPermissions(permissions,
                    new PermissionListener() {
                        @Override
                        public void onGranted() {

                        }

                        @Override
                        public void onDenied(@NotNull List<String> deniedPermissions) {

                        }
                    });

        }
        return permissionsResult;
    }


    /**
     * 将指定最近联系人的未读数清零(标记已读)。<br>
     * 调用该接口后，会触发通知
     *
     * @param account     聊天对象帐号
     * @param sessionType 会话类型
     */
    public static void clearUnreadCount(String account, SessionTypeEnum sessionType) {
        NIMClient.getService(MsgService.class).clearUnreadCount(account, sessionType);
    }
}

