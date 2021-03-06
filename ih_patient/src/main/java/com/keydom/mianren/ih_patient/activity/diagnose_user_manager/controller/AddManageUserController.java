package com.keydom.mianren.ih_patient.activity.diagnose_user_manager.controller;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.PhoneUtils;
import com.keydom.mianren.ih_patient.App;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.diagnose_user_manager.AnamnesisActivity;
import com.keydom.mianren.ih_patient.activity.diagnose_user_manager.view.AddManageUserView;
import com.keydom.mianren.ih_patient.bean.ManagerUserBean;
import com.keydom.mianren.ih_patient.bean.PackageData;
import com.keydom.mianren.ih_patient.bean.UserInfo;
import com.keydom.mianren.ih_patient.callback.GeneralCallback;
import com.keydom.mianren.ih_patient.constant.Global;
import com.keydom.mianren.ih_patient.net.UserService;
import com.keydom.mianren.ih_patient.utils.LocalizationUtils;
import com.keydom.mianren.ih_patient.utils.SelectDialogUtils;
import com.orhanobut.logger.Logger;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * created date: 2018/12/13 on 15:03
 * des:????????????????????????
 *
 * @author ??????
 */
public class AddManageUserController extends ControllerImpl<AddManageUserView> implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sex_choose:
                KeyboardUtils.hideSoftInput((Activity) getContext());
                final List<String> sexList = new ArrayList<>();
                sexList.add("???");
                sexList.add("???");
                OptionsPickerView sexPickerView = new OptionsPickerBuilder(getContext(),
                        new OnOptionsSelectListener() {
                            @Override
                            public void onOptionsSelect(int options1, int option2, int options3,
                                                        View v) {
                                getView().setSex(sexList.get(options1));
                            }
                        }).build();
                sexPickerView.setPicker(sexList);
                sexPickerView.show();
                break;
            case R.id.birth_choose:
                KeyboardUtils.hideSoftInput((Activity) getContext());
                Calendar endDate = Calendar.getInstance();
                TimePickerView pvTime = new TimePickerBuilder(getContext(),
                        new OnTimeSelectListener() {
                            @Override
                            public void onTimeSelect(Date date, View v) {
                                getView().setBirth(date);
                            }
                        }).setRangDate(null, endDate).build();
                pvTime.show();
                break;
            case R.id.next_step:
                nextStep(getView().getStatus());
                break;
            case R.id.area_choose:
                KeyboardUtils.hideSoftInput((Activity) getContext());
                SelectDialogUtils.showRegionSelectDialog(getContext(),
                        getView().getProvinceName(), getView().getCityName(),
                        getView().getAreaName(), new GeneralCallback.SelectRegionListener() {
                            @Override
                            public void getSelectedRegion(List<PackageData.ProvinceBean> data,
                                                          int position1, int position2,
                                                          int position3) {
                                getView().saveRegion(data, position1, position2, position3);
                            }
                        });
                break;
            default:
                break;
        }
    }

    /**
     * ?????????????????????
     */
    private void nextStep(int status) {
        ManagerUserBean manager = getView().getManager();
        if (StringUtils.isEmpty(manager.getName())) {
            ToastUtils.showShort("???????????????");
            return;
        }
        //        if (!manager.isSexIsChoose()) {
        //            ToastUtils.showShort("???????????????");
        //            return;
        //        }
        if (StringUtils.isEmpty(manager.getCardId())) {
            ToastUtils.showShort("?????????????????????");
            return;
        }
        if (!RegexUtils.isIDCard15(manager.getCardId()) && !RegexUtils.isIDCard18(manager.getCardId())) {
            ToastUtils.showShort("??????????????????15???18???????????????");
            return;
        }
        if (StringUtils.isEmpty(manager.getBirthday())) {
            ToastUtils.showShort("?????????????????????");
            return;
        }
        if (StringUtils.isEmpty(manager.getPhone()) || manager.getPhone().length() < 11 || !PhoneUtils.isMobileEnable(manager.getPhone())) {
            ToastUtils.showShort("??????????????????11????????????");
            return;
        }
        if (StringUtils.isEmpty(manager.getArea())) {
            ToastUtils.showShort("???????????????");
            return;
        }
        //        if (StringUtils.isEmpty(manager.getAddress())) {
        //            ToastUtils.showShort("?????????????????????");
        //            return;
        //        }

        if (getView().isElectronic()) {
            saveInfo(manager);
        } else {
            Intent i = new Intent(getContext(), AnamnesisActivity.class);
            i.putExtra(AnamnesisActivity.MANAGER_USER_BEAN, manager);
            i.putExtra(AnamnesisActivity.STATUS, status);
            ActivityUtils.startActivity(i);
        }

    }


    /**
     * ???????????????
     */
    public void saveInfo(ManagerUserBean manager) {
        Map<String, Object> map = new HashMap<>();
        map.put("hospitalId", App.hospitalId);
        map.put("registerUserId", Global.getUserId());
        map.put("name", manager.getName());
        map.put("age", manager.getAge());
        map.put("sex", manager.getSex());
        map.put("contact", App.userInfo.getUserName());
        map.put("contactPhone", App.userInfo.getPhoneNumber());
        map.put("cardNumber", manager.getCardId());
        //????????????:1?????????????????????  01????????????  02???????????? 03????????????  04??????????????????
        map.put("cardType", "01");
        map.put("birthDate", manager.getBirthday());
        map.put("phoneNumber", manager.getPhone());
        map.put("detailAddress", manager.getAddress());
        map.put("provinceCode", getView().getProvinceCode());
        map.put("cityCode", getView().getCityCode());
        map.put("areaCode", getView().getAreaCode());
        map.put("area", manager.getArea());
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).addElectronicPatient(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<UserInfo>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable UserInfo data) {
                //???????????????????????????????????????????????????????????????????????????
                if (TextUtils.equals(App.userInfo.getPhoneNumber(), manager.getPhone())) {
                    App.userInfo = data;
                    LocalizationUtils.fileSave2Local(getContext(), data, "userInfo");
                    Logger.e("????????????????????????");
                }
                getView().addOrEditSuccess(manager);
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
