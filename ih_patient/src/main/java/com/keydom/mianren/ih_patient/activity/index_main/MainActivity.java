package com.keydom.mianren.ih_patient.activity.index_main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.bean.MainLoadingEvent;
import com.keydom.ih_common.bean.UpdateVersionBean;
import com.keydom.ih_common.constant.Const;
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
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_patient.App;
import com.keydom.mianren.ih_patient.BuildConfig;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.controller.MainController;
import com.keydom.mianren.ih_patient.activity.index_main.Controller.IndexMainController;
import com.keydom.mianren.ih_patient.activity.index_main.view.IndexMainView;
import com.keydom.mianren.ih_patient.activity.login.LoginActivity;
import com.keydom.mianren.ih_patient.activity.my_doctor_or_nurse.DoctorOrNurseDetailActivity;
import com.keydom.mianren.ih_patient.activity.my_message.MyMessageActivity;
import com.keydom.mianren.ih_patient.bean.Event;
import com.keydom.mianren.ih_patient.bean.UserIndexSave;
import com.keydom.mianren.ih_patient.bean.UserInfo;
import com.keydom.mianren.ih_patient.broadcast.NetWorkBroadCast;
import com.keydom.mianren.ih_patient.constant.EventType;
import com.keydom.mianren.ih_patient.constant.Global;
import com.keydom.mianren.ih_patient.constant.Type;
import com.keydom.mianren.ih_patient.net.UserService;
import com.keydom.mianren.ih_patient.utils.LocalizationUtils;
import com.keydom.mianren.ih_patient.view.DialogUtils;
import com.keydom.mianren.ih_patient.view.MainView;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;

/**
 * @Name：com.kentra.yxyz.activity
 * @Description：主页
 * @Author：song
 * @Date：18/11/2 下午5:48
 * 修改人：xusong
 * 修改时间：18/11/2 下午5:48
 */
public class MainActivity extends BaseControllerActivity<IndexMainController> implements IndexMainView {

    private boolean isExitApp = false;
    private MainController mainController;
    private MainView mainView;
    private final int REQUEST_CODE = 100;
    private LocationClient locationClient;
    private boolean isNeedJump = false;

    private NetWorkBroadCast netWorkBroadCast;

    private DownloadUtils mDownloadUtils;

    /**
     * 启动页面
     */
    public static void start(Context context, boolean isNeedJump) {
        Intent starter = new Intent(context, MainActivity.class);//.setFlags(Intent
        // .FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK)
        starter.putExtra("isNeedJump", isNeedJump);
        starter.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(starter);
    }

    @Override
    public void initData(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        if (parseIntent()) {
            finish();
            return;
        }
        getController().showLoading();
        mainView = this.findViewById(R.id.main_view);
        mainView.initModule();

        this.findViewById(R.id.tab_member).setOnClickListener(getController());

        netWorkBroadCast = new NetWorkBroadCast();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(netWorkBroadCast, intentFilter);
        /*InterceptorReceiver interceptorReceiver=new InterceptorReceiver();
        IntentFilter intentFilter1=new IntentFilter("common.action.interceptor");
        registerReceiver(interceptorReceiver, intentFilter1);*/
        isNeedJump = getIntent().getBooleanExtra("isNeedJump", false);
        initLoginStatus();
        EventBus.getDefault().register(this);

        registerHomeKeyReceiver();

        getVersion();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        parseIntent();
    }

    /**
     * 网络状态监听
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void netWorkRecover(Event event) {
        if (event.getType() == EventType.NETWORKRECOVER) {
            initLoginStatus();
        }
    }

    /**
     * 跳转QR监听
     */
    @SuppressLint("CheckResult")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void jumpToQr(Event event) {
        if (event.getType() == EventType.STARTTOQR) {
            RxPermissions rxPermissions = new RxPermissions(this);
            rxPermissions.request(Manifest.permission.CAMERA).subscribe(aBoolean -> {
                if (aBoolean) {
                    Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
                    intent.setAction(Intents.Scan.ACTION);
                    intent.putExtra(Intents.Scan.SCAN_FORMATS, "QR_CODE");
                    startActivityForResult(intent, REQUEST_CODE);
                } else {
                    ToastUtil.showMessage(getContext(), "未获取摄像头使用权限，无法使用二维码功能");
                }
            });

        } else if (event.getType() == EventType.SHOW_NURSE_SERVICE_PAGE) {
/*            this.getWindow().getDecorView().setSystemUiVisibility(View
.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            StatusBarUtils.setWindowStatusBarColor(this, R.color.primary_color);
            mainView.setCurrentItem(2, false);*/
        } else if (event.getType() == EventType.SHOW_ONLINE_DIAGNOSE_PAGE) {
/*            this.getWindow().getDecorView().setSystemUiVisibility(View
.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            StatusBarUtils.setWindowStatusBarColor(this, R.color.primary_color);
            mainView.setCurrentItem(1, false);*/

        }
    }


    /**
     * 初始化位置状态
     */
    private void initLoginStatus() {
        UserInfo userInfo = (UserInfo) LocalizationUtils.readFileFromLocal(this, "userInfo");
        if (userInfo != null) {
            Logger.e("取到本地数据,用户ID为" + userInfo.getId());
            SharePreferenceManager.setToken("User " + userInfo.getToken());
            ImClient.loginIM(userInfo.getId() + "", userInfo.getImToken(), new OnLoginListener() {
                @Override
                public void success(String msg) {
                    ImClient.getUserInfoProvider().setAccount(userInfo.getId() + "");
                    NimUserInfoCache.getInstance().buildCache();
                    TeamDataCache.getInstance().buildCache();
                    ImPreferences.saveUserAccount(userInfo.getId() + "");
                    ImPreferences.saveUserToken(userInfo.getImToken());
                    Global.setUserId(userInfo.getId());
                    Global.setMember(userInfo.getMember());
                    App.userInfo = userInfo;
                    PushManager.setAlias(getContext(), userInfo.getId() + "");
                    App.isNeedInit = false;
                    if (isNeedJump) {
                        MyMessageActivity.start(getContext(), Type.MYMESSAGE, null);
                    }
                }

                @Override
                public void failed(String errMsg) {
                    Logger.e(errMsg);
                    Logger.e("IM登陆失败");
                    Global.setUserId(-1);
                    Global.setMember(0);
                }
            });
            String filename = "user_index_" + Global.getUserId();
            UserIndexSave userIndexSave =
                    (UserIndexSave) LocalizationUtils.readFileFromLocal(getContext(), filename);
        } else {
            Logger.e("没有取到数据");
            Global.setUserId(-1);
            Global.setMember(0);
        }
        initLocation();

    }

    /**
     * 初始化位置
     */
    @SuppressLint("CheckResult")
    private void initLocation() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) throws Exception {
                        if (granted) {
                            Logger.e("权限已打开");
                            if (locationClient == null) {
                                locationClient = new LocationClient(getContext());
                            }
                            MyLocationListener myListener = new MyLocationListener();
                            locationClient.registerLocationListener(myListener);
                            LocationClientOption option = new LocationClientOption();
                            option.setIsNeedAddress(true);
                            locationClient.setLocOption(option);
                            locationClient.start();
                        } else {
                            Logger.e("权限未打开");
                            ToastUtil.showMessage(getContext(), "未打开定位权限，无法定位到您当前所在城市");

                        }
                    }
                });

    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            Global.setLatitude(location.getLatitude());
            Global.setLongitude(location.getLongitude());
            String addr = location.getAddrStr();    //获取详细地址信息
            Global.setLocationAddress(addr);
            String country = location.getCountry();    //获取国家
            String province = location.getProvince();    //获取省份
            String city = location.getCity();    //获取城市
            Global.setLocationCityName(city);
            String district = location.getDistrict();    //获取区县
            String street = location.getStreet();    //获取街道信息
            Logger.e("当前定位位置为：" + country + province + city + addr);
            Global.setLocationCountry(country);
            Global.setLocationProvince(province);
            Global.setLocationCity(city);
            Global.setSelectedCityName(city);
            getController().initLocation(city);
        }
    }


    /**
     * 获取版本
     */
    public void getVersion() {
        Map<String, Object> map = new HashMap<>();
        map.put("versionNumber", Integer.valueOf(CommonUtils.getAppVersionCode(this)));
        map.put("applicationSystem", "患者端App");
        map.put("systemPlatform", "Android");
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).getVersion(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<UpdateVersionBean>() {
            @Override
            public void requestComplete(UpdateVersionBean data) {
                if (null != data && !TextUtils.isEmpty(data.getUrl())) {
                    DialogUtils.createUpdateDialog(MainActivity.this, data.getVersionName(),
                            data.getUpdateContent(), new OnPrivateDialogListener() {
                                @Override
                                public void confirm() {
                                    mDownloadUtils = new DownloadUtils(MainActivity.this,
                                            Const.RELEASE_HOST + data.getUrl(), "mianren.apk",
                                            BuildConfig.APPLICATION_ID);
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

    @Override
    protected void onDestroy() {
        if (netWorkBroadCast != null) {
            unregisterReceiver(netWorkBroadCast);
        }
        if (null != mDownloadUtils) {
            mDownloadUtils.destroy();
        }
        unregisterHomeKeyReceiver();
        EventBus.getDefault().unregister(this);
        super.onDestroy();

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
     * 退出app
     */
    private void exitApp() {
        isExitApp = true;
        ToastUtil.showMessage(MainActivity.this, "再按一次返回退出");
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    //                    ToastUtil.showMessage(getContext(),result);
                    Logger.e(result);
                    if (result.startsWith("?")) {
                        String temp = result.replace("?", "");
                        String[] keyValue = temp.split("&");
                        String[] values = keyValue[0].split("=");
                        DoctorOrNurseDetailActivity.startDoctorPage(getContext(), 0, values[1]);
                    } else {
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            JSONArray array = jsonObject.getJSONArray("typeAndNames");
                            JSONObject typeObj = array.getJSONObject(0);
                            int type;
                            if (typeObj.getInt("type") == 1) {
                                type = 0;
                            } else {
                                type = 1;
                            }
                            String code = jsonObject.getString("userCode");
                            DoctorOrNurseDetailActivity.startDoctorPage(MainActivity.this, type,
                                    code);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "解析数据失败,请确认你扫描的是医生的名片二维码",
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(MainActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
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

    @Override
    public int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        if (mainController == null) {
            mainController = new MainController(this, mainView);
            mainView.setOnClickListener(mainController);
            mainView.setOnPageChangeListener(mainController);
        }

    }

    @Override
    public void locationComplete() {
        locationClient.stop();
    }

    /**
     * 处理收到的IM相关的Intent
     */
    private boolean parseIntent() {
        // 已经登录过了，处理过来的请求
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.getData() != null) {
                Uri uri = intent.getData();
                String code = uri.getQueryParameter("doctorId");
                DoctorOrNurseDetailActivity.startDoctorPage(getContext(), 0, code);
            } else if (intent.hasExtra(NimIntent.EXTRA_NOTIFY_CONTENT)) {
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
        return false;
    }

    /**
     * 解析通知消息
     */
    private void parseNotifyIntent(Intent intent) {
        ArrayList<IMMessage> messages =
                (ArrayList<IMMessage>) intent.getSerializableExtra(NimIntent.EXTRA_NOTIFY_CONTENT);
        if (messages != null && messages.size() <= 1) {
            Bundle bundle;
            switch (messages.get(0).getSessionType()) {
                case P2P:
                    bundle = new Bundle();
                    bundle.putBoolean(ImConstants.CHATTING, false);
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

    /**
     * stackresumed
     */
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
            if (LocalizationUtils.deleteFileFromLocal(getContext(), "userInfo")) {
                Logger.e("本地用户数据清除成功");
                Global.setUserId(-1);
                Global.setMember(0);
            }
            ImClient.loginOut();
            EventBus.getDefault().post(new Event(EventType.UPDATELOGINSTATE, null));
            AlertDialog.Builder builder = new AlertDialog.Builder(ActivityUtils.getTopActivity());
            builder.setTitle("重新登陆");
            builder.setMessage("该账号在其他设备登陆，是否重新登陆！");
            builder.setCancelable(false);
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    start(getContext(), false);
                }
            });
            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    LoginActivity.start(MainActivity.this);
                }
            });
            builder.create().show();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MainLoadingEvent mainLoadingEvent) {
        getController().hideLoading();
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
}
