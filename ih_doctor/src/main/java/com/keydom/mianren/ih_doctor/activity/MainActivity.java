package com.keydom.mianren.ih_doctor.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.keydom.ih_common.bean.ScanBean;
import com.keydom.ih_common.bean.UpdateVersionBean;
import com.keydom.ih_common.constant.Const;
import com.keydom.ih_common.event.ConsultationEvent;
import com.keydom.ih_common.im.ImClient;
import com.keydom.ih_common.im.config.ImConstants;
import com.keydom.ih_common.im.manager.ImPreferences;
import com.keydom.ih_common.im.manager.NimUserInfoCache;
import com.keydom.ih_common.im.manager.TeamDataCache;
import com.keydom.ih_common.im.model.AVChatExtras;
import com.keydom.ih_common.minterface.OnLoginListener;
import com.keydom.ih_common.minterface.OnPrivateDialogListener;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.push.PushManager;
import com.keydom.ih_common.receive.HomeWatcherReceiver;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_common.utils.DownloadUtils;
import com.keydom.ih_common.utils.FileUtils;
import com.keydom.ih_common.utils.SharePreferenceManager;
import com.keydom.ih_common.utils.StatusBarUtils;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.mianren.ih_doctor.BuildConfig;
import com.keydom.mianren.ih_doctor.MyApplication;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.controller.MainController;
import com.keydom.mianren.ih_doctor.activity.my_message.MyMessageActivity;
import com.keydom.mianren.ih_doctor.activity.online_consultation.ConsultationOrderActivity;
import com.keydom.mianren.ih_doctor.activity.personal.MyServiceActivity;
import com.keydom.mianren.ih_doctor.activity.personal.PersonalInfoActivity;
import com.keydom.mianren.ih_doctor.bean.DeptBean;
import com.keydom.mianren.ih_doctor.bean.Event;
import com.keydom.mianren.ih_doctor.bean.LoginBean;
import com.keydom.mianren.ih_doctor.constant.EventType;
import com.keydom.mianren.ih_doctor.constant.TypeEnum;
import com.keydom.mianren.ih_doctor.net.LoginApiService;
import com.keydom.mianren.ih_doctor.net.MainApiService;
import com.keydom.mianren.ih_doctor.net.SignService;
import com.keydom.mianren.ih_doctor.net.UserService;
import com.keydom.mianren.ih_doctor.utils.DialogUtils;
import com.keydom.mianren.ih_doctor.utils.SignUtils;
import com.keydom.mianren.ih_doctor.view.MainView;
import com.netease.nimlib.sdk.NimIntent;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.orhanobut.logger.Logger;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.decoding.Intents;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;

import static com.keydom.mianren.ih_doctor.adapter.DiagnoseOrderRecyclrViewAdapter.IS_ORDER;


/**
 * @Name???com.kentra.yxyz.activity
 * @Description???????????????
 * @Author???song
 * @Date???18/11/2 ??????5:48
 * ????????????xusong
 * ???????????????18/11/2 ??????5:48
 */
public class MainActivity extends AppCompatActivity {


    private MainView mainView;
    /**
     * ??????????????????APP
     */
    private boolean isExitApp = false;
    private boolean isNeedJump = false;
    private boolean isNeedJump2Service = false;
    private MainController mainController;

    private DownloadUtils mDownloadUtils;

    /**
     * ?????????
     */
    private final int REQUEST_CODE = 100;
    /*private IntentFilter intentFilter1;
    private  InterceptorReceiver interceptorReceiver;*/

    /**
     * ???????????????<br/>
     * ?????????????????????????????????<br/>
     * ?????????????????????????????????<br/>
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

        registerHomeKeyReceiver();

        getVersion();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        unregisterHomeKeyReceiver();
        //        unregisterReceiver(interceptorReceiver);
        if (null != mDownloadUtils) {
            mDownloadUtils.destroy();
        }
        super.onDestroy();
    }

    private static HomeWatcherReceiver mHomeKeyReceiver = null;

    private void registerHomeKeyReceiver() {
        mHomeKeyReceiver = new HomeWatcherReceiver();
        final IntentFilter homeFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        registerReceiver(mHomeKeyReceiver, homeFilter);
    }

    private void unregisterHomeKeyReceiver() {
        if (null != mHomeKeyReceiver) {
            unregisterReceiver(mHomeKeyReceiver);
        }
    }

    /**
     * ???????????????
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
     * ??????APP
     */
    private void exitApp() {
        isExitApp = true;
        ToastUtil.showMessage(this, "????????????????????????");
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
     * ??????????????????????????????
     */
    public void getDept() {
        Map<String, Object> map = new HashMap<>();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(MainApiService.class).getDept(map), new HttpSubscriber<List<DeptBean>>() {
            @Override
            public void requestComplete(@org.jetbrains.annotations.Nullable List<DeptBean> data) {
                if (data == null) {
                    MyApplication.deptBeanList.clear();
                    MyApplication.filterDeptList.clear();
                    return;
                }
                MyApplication.deptBeanList.clear();
                MyApplication.deptBeanList.addAll(data);
                if (MyApplication.userInfo.getId() != 0) {//??????????????????????????????????????????????????????????????????
                    MyApplication.filterDept();
                }
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                MyApplication.deptBeanList.clear();
                MyApplication.filterDeptList.clear();
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * ????????????
     */
    public void sendCaSuccessToPc(String signature, String doctorCode, String jobId) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(MainApiService.class).sendCaSuccessToPc(signature, doctorCode, jobId), new HttpSubscriber<String>() {
            @Override
            public void requestComplete(@org.jetbrains.annotations.Nullable String data) {
                ToastUtil.showMessage(MainActivity.this, "????????????");
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                ToastUtil.showMessage(MainActivity.this, "????????????");
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * ca??????
     */
    private void caCount(int type) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(SignService.class).caCount(type), new HttpSubscriber<String>() {
            @Override
            public void requestComplete(@org.jetbrains.annotations.Nullable String data) {
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
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
     * ???????????????IM?????????Intent
     */
    private boolean parseIntent() {

        if (TextUtils.isEmpty(ImClient.getUserInfoProvider().getAccount())) {
            // ????????????app??????????????????
            if (!stackResumed(this)) {
                LoginActivity.start(this);
            }
            finish();
        } else {
            // ??????????????????????????????????????????
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
                            Logger.e("???????????????");

                        } else {
                            Logger.e("???????????????");
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
            builder.setTitle("????????????");
            builder.setMessage("??????????????????????????????????????????????????????");
            builder.setCancelable(false);
            builder.setNegativeButton("??????", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SharePreferenceManager.clearData();
                    PushManager.deleteAlias(MainActivity.this);
                    LoginActivity.start(MainActivity.this);
                }
            });
            builder.setPositiveButton("????????????", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    HashMap<String, Object> map = new HashMap<>();
                    map.put("accountMobile", SharePreferenceManager.getUserId());
                    map.put("password", SharePreferenceManager.getUserPwd());
                    ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(LoginApiService.class).login(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<LoginBean>() {
                        @Override
                        public void requestComplete(@org.jetbrains.annotations.Nullable final LoginBean data) {
                            if (data.getUserCode() == null || data.getImToken() == null || "".equals(data.getUserCode()) || "".equals(data.getImToken())) {
                                ToastUtil.showMessage(MainActivity.this, "????????????????????????????????????");
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
                                                    builder.setTitle("???????????????");
                                                    builder.setMessage("?????????????????????????????????????????????????????????????????????");
                                                    builder.setNegativeButton("??????",
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
                                                        "???????????????????????????");
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(ConsultationEvent event) {
        new GeneralDialog(this, "????????????????????????,???????????????",
                () -> ConsultationOrderActivity.start(MainActivity.this)).show();
    }

    /**
     * ??????QR??????
     */
    @SuppressLint("CheckResult")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void jumpToQr(Event event) {
        if (event.getType() == EventType.STARTTOQR) {
            RxPermissions rxPermissions = new RxPermissions(this);
            rxPermissions.request(Manifest.permission.CAMERA).subscribe(aBoolean -> {
                if (aBoolean) {
                    Intent intent = new Intent(this, CaptureActivity.class);
                    intent.setAction(Intents.Scan.ACTION);
                    intent.putExtra(Intents.Scan.SCAN_FORMATS, "QR_CODE");
                    startActivityForResult(intent, REQUEST_CODE);
                } else {
                    ToastUtil.showMessage(this, "????????????????????????????????????????????????????????????");
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE) {
            Bundle bundle = data.getExtras();
            String result = bundle.getString(CodeUtils.RESULT_STRING);
            if (TextUtils.isEmpty(result)) {
                ToastUtil.showMessage(this, "??????????????????,??????????????????????????????");
            } else {
                ScanBean scanBean = new Gson().fromJson(result, ScanBean.class);
                if (scanBean.getType() == 1) {
                    SignUtils.signApi(this, scanBean.getContent(), new SignUtils.SignCallBack() {
                        @Override
                        public void signSuccess(String signature, String jobId) {
                            sendCaSuccessToPc(signature, scanBean.getDoctorCode(), jobId);
                            caCount(scanBean.getSignType());
                        }

                        @Override
                        public void signFailed(String code) {
                            ToastUtil.showMessage(MainActivity.this, "????????????");
                        }
                    });
                }
            }
        } else if (requestCode == 10001) {

            Uri apkUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID +
                    ".fileprovider", new File(FileUtils.initPath(), "mianren.apk"));
            Intent install = new Intent(Intent.ACTION_VIEW);
            install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            install.setDataAndType(apkUri, "application/vnd.android.package-archive");
            this.startActivity(install);
        }
    }


    /**
     * ????????????
     */
    public void getVersion() {
        Map<String, Object> map = new HashMap<>();
        map.put("versionNumber", Integer.valueOf(CommonUtils.getAppVersionCode(this)));
        map.put("applicationSystem", "?????????App");
        map.put("systemPlatform", "Android");
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).getVersion(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<UpdateVersionBean>() {
            @Override
            public void requestComplete(UpdateVersionBean data) {
                if (null != data && !TextUtils.isEmpty(data.getUrl())) {
                    DialogUtils.createUpdateDialog(MainActivity.this, data.getVersionName(),
                            data.getUpdateContent(), new OnPrivateDialogListener() {
                                @Override
                                public void confirm() {
                                    mDownloadUtils = new DownloadUtils(MainActivity.this, Const.RELEASE_HOST + data.getUrl(), "mianren.apk", BuildConfig.APPLICATION_ID);
                                }

                                @Override
                                public void cancel() {
                                    finish();
                                }
                            }).show();
                }
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                ToastUtils.showShort(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }
}
