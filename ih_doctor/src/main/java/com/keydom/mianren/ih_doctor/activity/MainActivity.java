package com.keydom.mianren.ih_doctor.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.blankj.utilcode.util.ActivityUtils;
import com.keydom.ih_common.im.ImClient;
import com.keydom.ih_common.im.config.ImConstants;
import com.keydom.ih_common.im.manager.ImPreferences;
import com.keydom.ih_common.im.manager.NimUserInfoCache;
import com.keydom.ih_common.im.manager.TeamDataCache;
import com.keydom.ih_common.im.model.AVChatExtras;
import com.keydom.ih_common.minterface.OnLoginListener;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.push.PushManager;
import com.keydom.ih_common.utils.SharePreferenceManager;
import com.keydom.ih_common.utils.StatusBarUtils;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_doctor.MyApplication;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.controller.MainController;
import com.keydom.mianren.ih_doctor.activity.my_message.MyMessageActivity;
import com.keydom.mianren.ih_doctor.activity.personal.MyServiceActivity;
import com.keydom.mianren.ih_doctor.activity.personal.PersonalInfoActivity;
import com.keydom.mianren.ih_doctor.bean.DeptBean;
import com.keydom.mianren.ih_doctor.bean.LoginBean;
import com.keydom.mianren.ih_doctor.constant.TypeEnum;
import com.keydom.mianren.ih_doctor.net.LoginApiService;
import com.keydom.mianren.ih_doctor.net.MainApiService;
import com.keydom.mianren.ih_doctor.view.MainView;
import com.netease.nimlib.sdk.NimIntent;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.orhanobut.logger.Logger;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;

import static com.keydom.mianren.ih_doctor.adapter.DiagnoseOrderRecyclrViewAdapter.IS_ORDER;


/**
 * @Name：com.kentra.yxyz.activity
 * @Description：描述信息
 * @Author：song
 * @Date：18/11/2 下午5:48
 * 修改人：xusong
 * 修改时间：18/11/2 下午5:48
 */
public class MainActivity extends AppCompatActivity {


    private MainView mainView;
    /**
     * 是否可以推出APP
     */
    private boolean isExitApp = false;
    private boolean isNeedJump = false;
    private boolean isNeedJump2Service = false;
    private MainController mainController;
    /*private IntentFilter intentFilter1;
    private  InterceptorReceiver interceptorReceiver;*/

    /**
     * 跳转主页面<br/>
     * 先判断是否需要查看协议<br/>
     * 再判断是否是否需要修改<br/>
     *
     * @param context
     */
    public static void start(Context context, boolean isNeedJump, boolean isNeedJump2Service) {
        if (SharePreferenceManager.getIsAgreement()) {
            AgreementActivity.startService(context);
        } else if (SharePreferenceManager.getFirstFinishInfo()) {
            PersonalInfoActivity.start(context, TypeEnum.FIRST_FINISH_INFO);
        } else {
            Intent starter =
                    new Intent(context, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            starter.putExtra("isNeedJump", isNeedJump);
            starter.putExtra("isNeedJump2Service", isNeedJump2Service);
            context.startActivity(starter);
            SharePreferenceManager.setIsFirst(false);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        isNeedJump = getIntent().getBooleanExtra("isNeedJump", false);
        isNeedJump2Service = getIntent().getBooleanExtra("isNeedJump2Service", false);
        StatusBarUtils.setWindowStatusBarColor(this, R.color.status_bar_color_work);
        if (parseIntent()) {
            finish();
            return;
        }
        initView();
        getDept();
        initPremissions();
        if (isNeedJump) {
            MyMessageActivity.start(this, null);
        }
        if (isNeedJump2Service) {
            MyServiceActivity.start(this, true);
        }
        /*interceptorReceiver =new InterceptorReceiver();
        intentFilter1=new IntentFilter("common.action.interceptor");
        registerReceiver(interceptorReceiver, intentFilter1);*/

    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        //        unregisterReceiver(interceptorReceiver);
        super.onDestroy();
    }

    /**
     * 初始化布局
     */
    protected void initView() {
        mainView = this.findViewById(R.id.main_view);
        mainView.initModule();
        mainController = new MainController(this, mainView);
        mainView.setOnClickListener(mainController);
        mainView.setOnPageChangeListener(mainController);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {


            if (isExitApp) {
                this.finish();
            } else {
                exitApp();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    /**
     * 退出APP
     */
    private void exitApp() {
        isExitApp = true;
        ToastUtil.showMessage(this, "再按一次返回退出");
        new CountDownTimer(2000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                isExitApp = false;
                this.cancel();
            }
        }.start();
    }

    /**
     * 获取院区下面所有科室
     */
    public void getDept() {
        Map<String, Object> map = new HashMap<>();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(MainApiService.class).getDept(map), new HttpSubscriber<List<DeptBean>>() {
            @Override
            public void requestComplete(@org.jetbrains.annotations.Nullable List<DeptBean> data) {
                if (data == null) {
                    MyApplication.deptBeanList.clear();
                    MyApplication.deptSpannerList.clear();
                    return;
                }
                MyApplication.deptBeanList.clear();
                MyApplication.deptBeanList.addAll(data);
                if (MyApplication.userInfo.getId() != 0) {//如果用户信息已经拿到，则过滤，否则不过滤科室
                    MyApplication.allDept();
                    MyApplication.filterDept();
                }
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                MyApplication.deptBeanList.clear();
                MyApplication.deptSpannerList.clear();
                return super.requestError(exception, code, msg);
            }
        });
    }


    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        parseIntent();
    }

    /**
     * 处理收到的IM相关的Intent
     */
    private boolean parseIntent() {

        if (TextUtils.isEmpty(ImClient.getUserInfoProvider().getAccount())) {
            // 判断当前app是否正在运行
            if (!stackResumed(this)) {
                LoginActivity.start(this);
            }
            finish();
        } else {
            // 已经登录过了，处理过来的请求
            Intent intent = getIntent();
            if (intent != null) {
                if (intent.hasExtra(NimIntent.EXTRA_NOTIFY_CONTENT)) {
                    parseNotifyIntent(intent);
                    return true;
                } else if (intent.hasExtra(AVChatExtras.EXTRA_FROM_NOTIFICATION)) {
                    String account = intent.getStringExtra(AVChatExtras.EXTRA_ACCOUNT);
                    if (!TextUtils.isEmpty(account)) {
                        intent.removeExtra(AVChatExtras.EXTRA_FROM_NOTIFICATION);
                        ImClient.startConversation(this, account, null);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void parseNotifyIntent(Intent intent) {
        ArrayList<IMMessage> messages =
                (ArrayList<IMMessage>) intent.getSerializableExtra(NimIntent.EXTRA_NOTIFY_CONTENT);
        if (messages != null && messages.size() <= 1) {
            Bundle bundle;
            switch (messages.get(0).getSessionType()) {
                case P2P:
                    bundle = new Bundle();
                    bundle.putBoolean(IS_ORDER, true);
                    ImClient.startConversation(this, messages.get(0).getSessionId(), bundle);
                    break;
                case Team:
                    bundle = new Bundle();
                    bundle.putBoolean(ImConstants.TEAM, true);
                    ImClient.startConversation(this, messages.get(0).getSessionId(), bundle);
                    break;
                default:
            }
        }
    }

    @SuppressLint("CheckResult")
    private void initPremissions() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) throws Exception {
                        if (granted) {
                            Logger.e("权限已打开");

                        } else {
                            Logger.e("权限未打开");
                            MainActivity.start(MainActivity.this, isNeedJump, isNeedJump2Service);
                            finish();
                        }
                    }
                });

    }

    public boolean stackResumed(Context context) {
        ActivityManager manager = (ActivityManager) context
                .getApplicationContext().getSystemService(
                        Context.ACTIVITY_SERVICE);
        String packageName = context.getApplicationContext().getPackageName();
        List<ActivityManager.RunningTaskInfo> recentTaskInfos = manager.getRunningTasks(1);
        if (recentTaskInfos != null && recentTaskInfos.size() > 0) {
            ActivityManager.RunningTaskInfo taskInfo = recentTaskInfos.get(0);
            if (taskInfo.baseActivity.getPackageName().equals(packageName) && taskInfo.numActivities > 1) {
                return true;
            }
        }

        return false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(com.keydom.ih_common.bean.MessageEvent messageEvent) {
        if (messageEvent.getType() == com.keydom.ih_common.constant.EventType.OFFLINE) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ActivityUtils.getTopActivity());
            builder.setTitle("重新登陆");
            builder.setMessage("该账号在其他设备登陆，是否重新登陆！");
            builder.setCancelable(false);
            builder.setNegativeButton("退出", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SharePreferenceManager.clearData();
                    PushManager.deleteAlias(MainActivity.this);
                    LoginActivity.start(MainActivity.this);
                }
            });
            builder.setPositiveButton("重新登陆", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    HashMap<String, Object> map = new HashMap<>();
                    map.put("accountMobile", SharePreferenceManager.getUserId());
                    map.put("password", SharePreferenceManager.getUserPwd());
                    ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(LoginApiService.class).login(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<LoginBean>() {
                        @Override
                        public void requestComplete(@org.jetbrains.annotations.Nullable final LoginBean data) {
                            if (data.getUserCode() == null || data.getImToken() == null || "".equals(data.getUserCode()) || "".equals(data.getImToken())) {
                                ToastUtil.showMessage(MainActivity.this, "帐号错误，请检查后重试！");
                            } else {
                                ImClient.loginIM(data.getUserCode(), data.getImToken(),
                                        new OnLoginListener() {
                                            @Override
                                            public void success(String msg) {
                                                ImClient.getUserInfoProvider().setAccount(data.getUserCode());
                                                NimUserInfoCache.getInstance().buildCache();
                                                TeamDataCache.getInstance().buildCache();
                                                ImPreferences.saveUserAccount(data.getUserCode());
                                                ImPreferences.saveUserToken(data.getImToken());
                                                SharePreferenceManager.setToken("Bearer " + data.getToken());
                                                SharePreferenceManager.setImToken(data.getImToken());
                                                SharePreferenceManager.setUserCode(data.getUserCode());
                                                SharePreferenceManager.setPhoneNumber(data.getPhoneNumber());
                                                SharePreferenceManager.setHospitalId(data.getHospitalId());
                                                SharePreferenceManager.setAutonyState(data.getAutonymState());
                                                SharePreferenceManager.setIdCard(data.getIdCard());
                                                if (data.getRoleIds() != null && data.getRoleIds().size() > 0) {
                                                    SharePreferenceManager.setRoleId(data.getRoleIds().get(0));
                                                    SharePreferenceManager.setPositionId(data.getNurseMonitorState());
                                                } else {
                                                    SharePreferenceManager.setRoleId(-1);
                                                    AlertDialog.Builder builder =
                                                            new AlertDialog.Builder(ActivityUtils.getTopActivity());
                                                    builder.setTitle("无角色权限");
                                                    builder.setMessage("没有分配角色，请联系管理员分配角色后重新登录！");
                                                    builder.setNegativeButton("确定",
                                                            new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog,
                                                                                    int which) {
                                                                    EventBus.getDefault().post(new com.keydom.ih_common.bean.MessageEvent.Buidler().setType(com.keydom.ih_common.constant.EventType.OFFLINE).build());
                                                                }
                                                            });
                                                    builder.setCancelable(false);
                                                    builder.create().show();
                                                    return;
                                                }

                                            }

                                            @Override
                                            public void failed(String errMsg) {
                                                ToastUtil.showMessage(MainActivity.this,
                                                        "聊天服务器登录失败");
                                                EventBus.getDefault().post(new com.keydom.ih_common.bean.MessageEvent.Buidler().setType(com.keydom.ih_common.constant.EventType.OFFLINE).build());
                                            }
                                        });
                            }
                        }

                        @Override
                        public boolean requestError(@NotNull ApiException exception, int code,
                                                    @NotNull String msg) {
                            EventBus.getDefault().post(new com.keydom.ih_common.bean.MessageEvent.Buidler().setType(com.keydom.ih_common.constant.EventType.OFFLINE).build());
                            return super.requestError(exception, code, msg);
                        }
                    });
                }
            });
            builder.create().show();
        }
    }
}
