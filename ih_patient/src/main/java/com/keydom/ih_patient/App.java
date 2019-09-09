package com.keydom.ih_patient;

import com.baidu.mapapi.SDKInitializer;
import com.blankj.utilcode.util.Utils;
import com.keydom.ih_common.CommonApp;
import com.keydom.ih_common.im.ImClient;
import com.keydom.ih_patient.activity.index_main.MainActivity;
import com.keydom.ih_patient.bean.UserInfo;
import com.keydom.ih_patient.utils.pay.weixin.WXInit;
import com.keydom.ih_patient.utils.pay.weixin.WXPay;
import com.netease.nimlib.sdk.util.NIMUtil;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 程序应用类
 */
public class App extends CommonApp {

    public static UserInfo userInfo;
    public static long hospitalId = -1;
    public static String hospitalName = "";
    public static boolean isNeedInit=true;

    @Override
    public void onCreate() {
        ImClient.notificationEntrance = MainActivity.class;
        super.onCreate();
        if (NIMUtil.isMainProcess(this)) {
            Utils.init(this);
            SDKInitializer.initialize(getApplicationContext());
            ZXingLibrary.initDisplayOpinion(getApplicationContext());
            String appId = "wx21ddbc498622a67f";
            WXInit.init(this, appId);
            WXPay.init(this, appId);
            closeAndroidPDialog();
        }
    }

    /**
     * 去掉在Android P上的提醒弹窗 （Detected problems with API compatibility(visit g.co/dev/appcompat for more info)
     */
    private void closeAndroidPDialog(){
        try {
            Class aClass = Class.forName("android.content.pm.PackageParser$Package");
            Constructor declaredConstructor = aClass.getDeclaredConstructor(String.class);
            declaredConstructor.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Class cls = Class.forName("android.app.ActivityThread");
            Method declaredMethod = cls.getDeclaredMethod("currentActivityThread");
            declaredMethod.setAccessible(true);
            Object activityThread = declaredMethod.invoke(null);
            Field mHiddenApiWarningShown = cls.getDeclaredField("mHiddenApiWarningShown");
            mHiddenApiWarningShown.setAccessible(true);
            mHiddenApiWarningShown.setBoolean(activityThread, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
