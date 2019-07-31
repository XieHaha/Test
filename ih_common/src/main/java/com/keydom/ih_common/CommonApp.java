package com.keydom.ih_common;

import android.app.Application;
import android.support.annotation.Nullable;
import android.support.multidex.MultiDexApplication;

import com.keydom.Common;
import com.keydom.ih_common.im.ImClient;
import com.keydom.ih_common.im.listener.observer.LoginSyncDataStatusObserver;
import com.keydom.ih_common.im.manager.NimUserInfoCache;
import com.keydom.ih_common.im.manager.TeamDataCache;
import com.keydom.ih_common.im.model.custom.CustomAttachParser;
import com.keydom.ih_common.im.widget.provider.NimMessageRevokeObserver;
import com.keydom.ih_common.utils.SharePreferenceManager;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.auth.AuthServiceObserver;
import com.netease.nimlib.sdk.avchat.AVChatManager;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.util.NIMUtil;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import cn.jpush.android.api.JPushInterface;

import static com.keydom.ih_common.im.ImClient.attachmentProgressObserver;
import static com.keydom.ih_common.im.ImClient.clientsObserver;
import static com.keydom.ih_common.im.ImClient.inComingCallObserver;
import static com.keydom.ih_common.im.ImClient.incomingMessageObserver;
import static com.keydom.ih_common.im.ImClient.userInfoObserver;
import static com.keydom.ih_common.im.ImClient.userStatusObserver;

public class CommonApp extends MultiDexApplication {
    public static final String SHAREPREFERENCE_NAME = "hospital_per";
    public static Application mApplication;
    @Override
    public void onCreate() {
        super.onCreate();
        Common.INSTANCE.init(this);
        ImClient.init(this);
        mApplication=this;
        if (NIMUtil.isMainProcess(this)) {
            JPushInterface.setDebugMode(true);
            JPushInterface.init(this);
            SharePreferenceManager.init(this, SHAREPREFERENCE_NAME);
            initLogger();
            NIMClient.getService(AuthServiceObserver.class).observeOtherClients(clientsObserver, true);
            NIMClient.getService(AuthServiceObserver.class).observeOnlineStatus(userStatusObserver,true);
            NIMClient.getService(MsgService.class).registerCustomAttachmentParser(new CustomAttachParser());
            NIMClient.getService(MsgServiceObserve.class).observeReceiveMessage(incomingMessageObserver, true);
            NIMClient.getService(MsgServiceObserve.class).observeAttachmentProgress(attachmentProgressObserver, true);
            NIMClient.getService(MsgServiceObserve.class).observeRevokeMessage(new NimMessageRevokeObserver(), true);
            AVChatManager.getInstance().observeIncomingCall(inComingCallObserver, true);
            LoginSyncDataStatusObserver.getInstance().registerLoginSyncDataStatus(true);
            NimUserInfoCache.getInstance().registerObservers(true);
            TeamDataCache.getInstance().registerObservers(true);
            ImClient.getUserInfoObservable().registerObserver(userInfoObserver, true);
        }
    }

    private void initLogger() {
        PrettyFormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)
                .methodCount(1)
                .methodOffset(7)
                .tag("ih_Android")
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, @Nullable String tag) {
                return BuildConfig.DEBUG;
            }
        });
    }
}
