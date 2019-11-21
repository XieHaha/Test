package com.keydom.ih_patient.activity.welcome;

import android.Manifest;
import android.os.Bundle;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.im.ImClient;
import com.keydom.ih_common.im.manager.ImPreferences;
import com.keydom.ih_common.im.manager.NimUserInfoCache;
import com.keydom.ih_common.im.manager.TeamDataCache;
import com.keydom.ih_common.minterface.OnLoginListener;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_patient.App;
import com.keydom.ih_patient.BuildConfig;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.index_main.MainActivity;
import com.keydom.ih_patient.activity.welcome.controller.WelcomeController;
import com.keydom.ih_patient.activity.welcome.view.WelcomeView;
import com.keydom.ih_patient.bean.UserIndexSave;
import com.keydom.ih_patient.bean.UserInfo;
import com.keydom.ih_patient.constant.Global;
import com.keydom.ih_patient.utils.LocalizationUtils;
import com.orhanobut.logger.Logger;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.jetbrains.annotations.Nullable;

import io.reactivex.functions.Consumer;

/**
 * 欢迎页面
 */
public class WelcomeActivity extends BaseControllerActivity<WelcomeController> implements WelcomeView {
    @Override
    public int getLayoutRes() {
        return R.layout.activity_welcome_layout;
    }

    private LocationClient locationClient;
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initLoginStatus();
    }

    /**
     * 初始化登录状态
     */
    private void initLoginStatus() {
        UserInfo userInfo = (UserInfo) LocalizationUtils.readFileFromLocal(this, "userInfo");
        if (userInfo != null) {
            Logger.e("取到本地数据,用户ID为" + userInfo.getId());
            Global.setUserId(userInfo.getId());
            App.userInfo = userInfo;
            ImClient.loginIM(userInfo.getUserAccount(), userInfo.getImToken(), new OnLoginListener() {
                @Override
                public void success(String msg) {
                    ImClient.getUserInfoProvider().setAccount(userInfo.getUserAccount());
                    NimUserInfoCache.getInstance().buildCache();
                    TeamDataCache.getInstance().buildCache();
                    ImPreferences.saveUserAccount(userInfo.getUserAccount());
                    ImPreferences.saveUserToken(userInfo.getImToken());
                    if (BuildConfig.DEBUG) {
                        Toast.makeText(WelcomeActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void failed(String errMsg) {
                    Logger.e(errMsg);
                }
            });
            String filename="user_index_"+Global.getUserId();
            UserIndexSave userIndexSave= (UserIndexSave) LocalizationUtils.readFileFromLocal(getContext(),filename);
            if(userIndexSave!=null){
                App.hospitalId=userIndexSave.getHospitalId();
                App.hospitalName=userIndexSave.getHospitalName();
                Global.setLocationCountry(userIndexSave.getLocationCountry());
                Global.setLocationProvince(userIndexSave.getLocationProvince());
                Global.setLocationCity(userIndexSave.getCityName());
                Global.setSelectedCityCode(userIndexSave.getCityCode());
                MainActivity.start(getContext(),false);
                finish();
            }
            initLocation();
        } else {
            Logger.e("没有取到数据");
            Global.setUserId(-1);
            initLocation();
        }

    }

    /**
     * 初始化位置
     */
    private void initLocation() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) throws Exception {
                        if (granted) {
                            Logger.e("权限已打开");
                            locationClient= new LocationClient(getContext());
                            MyLocationListener myListener = new MyLocationListener();
                            locationClient.registerLocationListener(myListener);
                            LocationClientOption option = new LocationClientOption();
                            option.setIsNeedAddress(true);
                            locationClient.setLocOption(option);
                            locationClient.start();
                        }else {
                            Logger.e("权限未打开");
                            ToastUtil.showMessage(getContext(),"未打开定位权限，无法定位到您当前所在城市");
                            MainActivity.start(getContext(),false);
                            finish();
                        }
                    }
                });

    }
    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {

            String addr = location.getAddrStr();    //获取详细地址信息
            String country = location.getCountry();    //获取国家
            String province = location.getProvince();    //获取省份
            String city = location.getCity();    //获取城市
            String district = location.getDistrict();    //获取区县
            String street = location.getStreet();    //获取街道信息
            Logger.e("当前定位位置为："+country+province+city+addr);
            Global.setLocationCountry(country);
            Global.setLocationProvince(province);
            Global.setLocationCity(city);
            getController().initLocation(city);
        }
    }

}
