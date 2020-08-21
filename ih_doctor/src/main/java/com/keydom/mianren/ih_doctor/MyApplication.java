package com.keydom.mianren.ih_doctor;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.widget.LinearLayout;

import com.baidu.mapapi.SDKInitializer;
import com.blankj.utilcode.util.Utils;
import com.ganxin.library.LoadDataLayout;
import com.iflytek.cloud.SpeechUtility;
import com.keydom.ih_common.CommonApp;
import com.keydom.ih_common.im.ImClient;
import com.keydom.ih_common.utils.CustomActivityLifecycleCallback;
import com.keydom.ih_common.utils.SharePreferenceManager;
import com.keydom.mianren.ih_doctor.activity.MainActivity;
import com.keydom.mianren.ih_doctor.bean.AccessInfoBean;
import com.keydom.mianren.ih_doctor.bean.DeptBean;
import com.keydom.mianren.ih_doctor.bean.ServiceBean;
import com.keydom.mianren.ih_doctor.bean.UserInfo;
import com.keydom.mianren.ih_doctor.constant.Const;
import com.keydom.mianren.ih_doctor.view.CustomTopBar;
import com.netease.nimlib.sdk.util.NIMUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import org.jaaksi.pickerview.picker.BasePicker;
import org.jaaksi.pickerview.topbar.ITopBar;
import org.jaaksi.pickerview.util.Util;
import org.jaaksi.pickerview.widget.DefaultCenterDecoration;
import org.jaaksi.pickerview.widget.PickerView;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @Name：MyApplication
 * @Description：初始化网络请求
 * @Author：song
 * @Date：18/11/2 上午11:50
 * 修改人：song
 * 修改时间：18/11/2 上午11:50
 * 修改备注：
 */
public class MyApplication extends CommonApp {

    public static UserInfo userInfo = new UserInfo();
    public static AccessInfoBean accessInfoBean;
    public static List<DeptBean> deptBeanList = new ArrayList<>();
    public static List<DeptBean> filterDeptList = new ArrayList<>();
    public static boolean isNeedInit = true;
    public static int receiveReferral = 0;

    /**
     * 是否开启ca签名
     */
    public static boolean signAble = true;


    static {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.refreshBg, R.color.fontClickEnable);
                layout.setReboundDuration(200);
                return new ClassicsHeader(context);
            }
        });
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                layout.setReboundDuration(200);
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    @Override
    public void onCreate() {
        ImClient.notificationEntrance = MainActivity.class;
        super.onCreate();
        if (NIMUtil.isMainProcess(this)) {
            closeAndroidPDialog();
            initDefaultPicker();
            Utils.init(this);
            ZXingLibrary.initDisplayOpinion(this);
            SDKInitializer.initialize(getApplicationContext());
            SpeechUtility.createUtility(this, "appid=" + getString(R.string.xunfei_app_id));
            SharePreferenceManager.init(this, Const.SHAREPREFERENCE_NAME);
            initData();
            registerActivityLifecycleCallbacks(new CustomActivityLifecycleCallback());
            //CrashHandler.getInstance().init(getApplicationContext());
        }
    }

    private void initData() {
        LoadDataLayout.getBuilder()
                .setLoadingTextSize(16)
                .setLoadingTextColor(R.color.colorPrimary)
                .setEmptyImgId(R.drawable.ic_empty)
                .setErrorImgId(R.drawable.ic_error)
                .setNoNetWorkImgId(R.drawable.ic_no_network)
                .setEmptyImageVisible(true)
                .setErrorImageVisible(true)
                .setNoNetWorkImageVisible(true)
                .setEmptyText("数据为空，请重新加载")
                .setErrorText("加载错误，请重新加载")
                .setNoNetWorkText("无网络，请检查网络")
                .setAllTipTextSize(16)
                .setAllTipTextColor(R.color.text_color_child)
                .setAllPageBackgroundColor(R.color.white)
                .setReloadBtnText("重新加载")
                .setReloadBtnTextSize(16)
                .setReloadBtnTextColor(R.color.colorPrimary)
                .setReloadBtnBackgroundResource(R.drawable.selector_btn_normal)
                .setReloadBtnVisible(true)
                .setReloadClickArea(LoadDataLayout.FULL);

    }

    private void initDefaultPicker() {
        // 利用修改静态默认属性值，快速定制一套满足自己app样式需求的Picker.
        // BasePickerView
        PickerView.sDefaultVisibleItemCount = 3;
        PickerView.sDefaultItemSize = 40;
        PickerView.sDefaultIsCirculation = true;

        // PickerView
        PickerView.sOutTextSize = 15;
        PickerView.sCenterTextSize = 15;
        PickerView.sCenterColor = R.color.fontColorPrimary;
        PickerView.sOutColor = Color.GRAY;

        // BasePicker
        int padding = Util.dip2px(this, 10);
        BasePicker.sDefaultPaddingRect = new Rect(padding, padding, padding, padding);
        BasePicker.sDefaultPickerBackgroundColor = Color.WHITE;
        // 自定义 TopBar
        BasePicker.sDefaultTopBarCreator = new BasePicker.IDefaultTopBarCreator() {
            @Override
            public ITopBar createDefaultTopBar(LinearLayout parent) {
                return new CustomTopBar(parent);
            }
        };

        // DefaultCenterDecoration
        DefaultCenterDecoration.sDefaultLineWidth = 1;
        DefaultCenterDecoration.sDefaultLineColor = R.color.line_color;
        //DefaultCenterDecoration.sDefaultDrawable = new ColorDrawable(Color.WHITE);
        int leftMargin = Util.dip2px(this, 10);
        int topMargin = Util.dip2px(this, 2);
        DefaultCenterDecoration.sDefaultMarginRect =
                new Rect(leftMargin, -topMargin, leftMargin, -topMargin);
    }


    /**
     * 解决androidP弹窗
     */
    private void closeAndroidPDialog() {
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

    public static boolean serviceEnable(String[] codes) {
        if (accessInfoBean == null || accessInfoBean.getService() == null) {
            return false;
        }
        boolean enalbe = false;
        for (ServiceBean bean : accessInfoBean.getService()) {
            for (ServiceBean.DoctorServiceSubVoBean doctorServiceSubVoBean :
                    bean.getDoctorServiceSubVoList()) {
                for (String code : codes) {
                    if (code.equals(doctorServiceSubVoBean.getSubCode())) {
                        if (bean.getState() == 1) {
                            return true;
                        }
                    }
                }
            }

        }
        return false;
    }

    /**
     * 科室处理
     */
    public static ArrayList<String> filterDept(boolean all) {
        ArrayList<String> filterDept = new ArrayList<>();
        ArrayList<String> allDept = new ArrayList<>();
        for (DeptBean bean : deptBeanList) {
            if (bean.getId() == MyApplication.userInfo.getDeptId()) {
                allDept.add(bean.getName());
                continue;
            }
            filterDept.add(bean.getName());
            allDept.add(bean.getName());
        }
        if (all) {
            return allDept;
        }
        return filterDept;
    }

    public static void filterDept() {
        MyApplication.filterDeptList.clear();
        for (DeptBean bean : deptBeanList) {
            if (bean.getId() == MyApplication.userInfo.getDeptId()) {
                continue;
            }
            MyApplication.filterDeptList.add(bean);
        }
    }
}
