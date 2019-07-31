package com.keydom.ih_patient.activity.diagnose_user_manager.controller;

import android.app.Activity;
import android.content.Intent;
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
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.diagnose_user_manager.AnamnesisActivity;
import com.keydom.ih_patient.activity.diagnose_user_manager.view.AddManageUserView;
import com.keydom.ih_patient.bean.ManagerUserBean;
import com.keydom.ih_patient.bean.PackageData;
import com.keydom.ih_patient.callback.GeneralCallback;
import com.keydom.ih_patient.utils.SelectDialogUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * created date: 2018/12/13 on 15:03
 * des:添加就诊人控制器
 */
public class AddManageUserController extends ControllerImpl<AddManageUserView> implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sex_choose:
                KeyboardUtils.hideSoftInput((Activity) getContext());
                final List<String> sexList = new ArrayList<>();
                sexList.add("男");
                sexList.add("女");
                OptionsPickerView sexPickerView = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        getView().setSex(sexList.get(options1));
                    }
                }).build();
                sexPickerView.setPicker(sexList);
                sexPickerView.show();
                break;
            case R.id.birth_choose:
                KeyboardUtils.hideSoftInput((Activity) getContext());
                TimePickerView pvTime = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        getView().setBirth(date);
                    }
                }).build();
                pvTime.show();
                break;
            case R.id.next_step:
                nextStep(getView().getStatus());
                break;
            case R.id.area_choose:
                KeyboardUtils.hideSoftInput((Activity) getContext());
                SelectDialogUtils.showRegionSelectDialog(getContext(),getView().getProvinceName(),getView().getCityName(),getView().getAreaName(), new GeneralCallback.SelectRegionListener() {
                    @Override
                    public void getSelectedRegion(List<PackageData.ProvinceBean> data, int position1, int position2, int position3) {
                        getView().saveRegion(data, position1, position2, position3);
                    }
                });
                break;
        }
    }

    /**
     * 点击下一步操作
     */
    private void nextStep(int status) {
        ManagerUserBean manager = getView().getManager();
        if (StringUtils.isEmpty(manager.getName())) {
            ToastUtils.showShort("请输入姓名");
            return;
        }
        if (!manager.isSexIsChoose()) {
            ToastUtils.showShort("请选择性别");
            return;
        }
        if (StringUtils.isEmpty(manager.getCardId())) {
            ToastUtils.showShort("请输入身份证号");
            return;
        }
        if (!RegexUtils.isIDCard15(manager.getCardId()) && !RegexUtils.isIDCard18(manager.getCardId())) {
            ToastUtils.showShort("请输入正确的15或18位身份证号");
            return;
        }
        if (StringUtils.isEmpty(manager.getBirthday())) {
            ToastUtils.showShort("请选择出生日期");
            return;
        }
        if (StringUtils.isEmpty(manager.getPhone()) || manager.getPhone().length() < 11 || !RegexUtils.isMobileExact(manager.getPhone())) {
            ToastUtils.showShort("请输入正确的11位手机号");
            return;
        }
        if (StringUtils.isEmpty(manager.getArea())) {
            ToastUtils.showShort("请选择地区");
            return;
        }
        if (StringUtils.isEmpty(manager.getAddress())) {
            ToastUtils.showShort("请输入详细地址");
            return;
        }
        Intent i = new Intent(getContext(), AnamnesisActivity.class);
        i.putExtra(AnamnesisActivity.MANAGER_USER_BEAN, manager);
        i.putExtra(AnamnesisActivity.STATUS, status);
        ActivityUtils.startActivity(i);
    }
}
