package com.keydom.mianren.ih_patient.activity.location_manage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.PhoneUtils;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.InterceptorEditText;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.location_manage.controller.AddLocationController;
import com.keydom.mianren.ih_patient.activity.location_manage.view.AddLocationView;
import com.keydom.mianren.ih_patient.bean.Event;
import com.keydom.mianren.ih_patient.bean.LocationInfo;
import com.keydom.mianren.ih_patient.bean.PackageData;
import com.keydom.mianren.ih_patient.constant.EventType;
import com.keydom.mianren.ih_patient.constant.Global;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 添加地址页面
 */
public class AddLocationActivity extends BaseControllerActivity<AddLocationController> implements AddLocationView, View.OnClickListener {
    private TextView tvCenterTitle, add_region_choose_tv, location_add_commit;
    private ImageView right_close_img;
    //启动类型 add_location 新增地址 edit_location 修改地址
    private String type;
    private InterceptorEditText add_name_edt, add_phone_edt, add_address_edt;
    private String provinceName, cityName, areaName;
    private LocationInfo locationInfo = new LocationInfo();

    /**
     * 启动页
     */
    public static void start(Context context, String type) {
        Intent intent = new Intent(context, AddLocationActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    public void initData(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        type = getIntent().getStringExtra("type");
        right_close_img = findViewById(R.id.right_close_img);
        right_close_img.setOnClickListener(this);
        tvCenterTitle = findViewById(R.id.tvCenterTitle);
        add_region_choose_tv = findViewById(R.id.add_region_choose_tv);
        add_region_choose_tv.setOnClickListener(getController());
        location_add_commit = findViewById(R.id.location_add_commit);
        location_add_commit.setOnClickListener(getController());
        add_name_edt = findViewById(R.id.add_name_edt);
        add_phone_edt = findViewById(R.id.add_phone_edt);
        add_address_edt = findViewById(R.id.add_address_edt);
        if (type.equals("add_location")) {
            tvCenterTitle.setText("新增地址");
            location_add_commit.setText("新增");
        } else {
            tvCenterTitle.setText("修改地址");
            location_add_commit.setText("保存");
        }
        EventBus.getDefault().register(this);
    }

    /**
     * 获得位置信息
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getLocationInfo(Event event) {
        if (type.equals("add_location")) {
            return;
        } else {
            if (event.getType() == EventType.SENDLOCATIONINFO) {
                locationInfo = (LocationInfo) event.getData();
                add_name_edt.setText(locationInfo.getAddressName());
                add_phone_edt.setText(locationInfo.getPhone());
                add_address_edt.setText(locationInfo.getAddress());
                add_region_choose_tv.setText(locationInfo.getProvinceName() + "-" + locationInfo.getCityName() + "-" + locationInfo.getAreaName());
                if (locationInfo.getProvinceName() != null)
                    provinceName = locationInfo.getProvinceName();
                else
                    provinceName = "";
                if (locationInfo.getCityName() != null)
                    cityName = locationInfo.getCityName();
                else
                    cityName = "";
                if (locationInfo.getAreaName() != null)
                    areaName = locationInfo.getAreaName();
                else
                    areaName = locationInfo.getAreaName();
            }
        }

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.right_close_img:
                finish();
                break;

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.location_manage_add_layout;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public Map<String, Object> getAddMap() {
        locationInfo.setUserId(Global.getUserId());
        if (add_name_edt.getText().toString().trim() != null && !"".equals(add_name_edt.getText().toString().trim())) {
            locationInfo.setAddressName(add_name_edt.getText().toString().trim());
        } else {
            ToastUtil.showMessage(getContext(), "请选择姓名");
            return null;
        }
        if (StringUtils.isEmpty(add_phone_edt.getText().toString()) || add_phone_edt.getText().toString().length() < 11 || !PhoneUtils.isMobileEnable(add_phone_edt.getText().toString())){
            ToastUtil.showMessage(getContext(), "请输入正确的11位手机号");
            return null;
        }else{
            locationInfo.setPhone(add_phone_edt.getText().toString());
        }
        if (add_address_edt.getText().toString().trim() != null && !"".equals(add_address_edt.getText().toString().trim())) {
            locationInfo.setAddress(add_address_edt.getText().toString().trim());
        } else {
            ToastUtil.showMessage(getContext(), "请填写详细地址");
            return null;
        }
        if (locationInfo.getProvinceCode() == null || "".equals(locationInfo.getProvinceCode())) {
            ToastUtil.showMessage(getContext(), "请选择区域");
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("userId", locationInfo.getUserId());
        map.put("isdefault", 0);
        map.put("phone", locationInfo.getPhone());
        map.put("addressName", locationInfo.getAddressName());
        map.put("address", locationInfo.getAddress());
        map.put("provinceCode", locationInfo.getProvinceCode());
        map.put("provinceName", locationInfo.getProvinceName());
        map.put("cityCode", locationInfo.getCityCode());
        map.put("cityName", locationInfo.getCityName());
        map.put("areaCode", locationInfo.getAreaCode());
        map.put("areaName", locationInfo.getAreaName());
        return map;
    }

    @Override
    public Map<String, Object> getEditMap() {
        locationInfo.setUserId(Global.getUserId());
        if (add_name_edt.getText().toString().trim() != null && !"".equals(add_name_edt.getText().toString().trim())) {
            if (add_name_edt.getText().toString().trim().length() > 12) {
                ToastUtil.showMessage(getContext(), "姓名长度不能超过12位");
                return null;
            } else
                locationInfo.setAddressName(add_name_edt.getText().toString().trim());
        } else {
            ToastUtil.showMessage(getContext(), "请选择姓名");
            return null;
        }

        if (add_phone_edt.getText().toString().trim() != null && !"".equals(add_phone_edt.getText().toString().trim())) {
            if (!PhoneUtils.isMobileEnable(add_phone_edt.getText().toString().trim())) {
                ToastUtil.showMessage(getContext(), "请填入正确的手机格式");
                return null;
            } else
                locationInfo.setPhone(add_phone_edt.getText().toString().trim());
        } else {
            ToastUtil.showMessage(getContext(), "请选择联系电话");
            return null;
        }

        if (add_address_edt.getText().toString().trim() != null && !"".equals(add_address_edt.getText().toString().trim())) {
            if (add_address_edt.getText().toString().trim().length() > 200) {
                ToastUtil.showMessage(getContext(), "详细地址长度不能超过200位");
                return null;
            } else
                locationInfo.setAddress(add_address_edt.getText().toString().trim());
        } else {
            ToastUtil.showMessage(getContext(), "请填写详细地址");
            return null;
        }
        if (locationInfo.getProvinceCode() == null || "".equals(locationInfo.getProvinceCode())) {
            ToastUtil.showMessage(getContext(), "请选择区域");
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("userId", locationInfo.getUserId());
        map.put("isdefault", locationInfo.getIsdefault());
        map.put("phone", locationInfo.getPhone());
        map.put("addressName", locationInfo.getAddressName());
        map.put("address", locationInfo.getAddress());
        map.put("provinceCode", locationInfo.getProvinceCode());
        map.put("cityCode", locationInfo.getCityCode());
        map.put("areaCode", locationInfo.getAreaCode());
        map.put("id", locationInfo.getId());
        return map;
    }

    @Override
    public void addSuccess() {
        EventBus.getDefault().post(new Event(EventType.RETURNLOCATIONINFO, null));
        finish();
    }

    @Override
    public void addFailed(String errMsg) {
        ToastUtil.showMessage(getContext(), "添加失败：" + errMsg);
    }

    @Override
    public void editSuccess() {
        EventBus.getDefault().post(new Event(EventType.RETURNLOCATIONINFO, null));
        finish();
    }

    @Override
    public void editFailed(String errMsg) {
        ToastUtil.showMessage(getContext(), "修改失败：" + errMsg);
    }

    @Override
    public void saveRegion(List<PackageData.ProvinceBean> data, int options1, int option2, int options3) {
        if (data.get(options1).getCity().size() == 0) {
            add_region_choose_tv.setText(data.get(options1).getName());
            provinceName=data.get(options1).getName();
            cityName="";
            areaName="";
            locationInfo.setProvinceCode(data.get(options1).getCode());
            locationInfo.setProvinceName(data.get(options1).getName());
        } else if (data.get(options1).getCity().get(option2).getArea().size() == 0) {
            add_region_choose_tv.setText(data.get(options1).getName() + "-" + data.get(options1).getCity().get(option2).getName());
            locationInfo.setProvinceCode(data.get(options1).getCode());
            locationInfo.setProvinceName(data.get(options1).getName());
            locationInfo.setCityCode(data.get(options1).getCity().get(option2).getCode());
            locationInfo.setCityName(data.get(options1).getCity().get(option2).getName());
            provinceName=data.get(options1).getName();
            cityName=data.get(options1).getCity().get(option2).getName();
            areaName="";
        } else {
            add_region_choose_tv.setText(data.get(options1).getName() + "-" + data.get(options1).getCity().get(option2).getName() + "-" + data.get(options1).getCity().get(option2).getArea().get(options3).getName());
            locationInfo.setProvinceCode(data.get(options1).getCode());
            locationInfo.setProvinceName(data.get(options1).getName());
            locationInfo.setCityCode(data.get(options1).getCity().get(option2).getCode());
            locationInfo.setCityName(data.get(options1).getCity().get(option2).getName());
            locationInfo.setAreaCode(data.get(options1).getCity().get(option2).getArea().get(options3).getCode());
            locationInfo.setAreaName(data.get(options1).getCity().get(option2).getArea().get(options3).getName());
            provinceName=data.get(options1).getName();
            cityName=data.get(options1).getCity().get(option2).getName();
            areaName=data.get(options1).getCity().get(option2).getArea().get(options3).getName();
        }
    }
    @Override
    public String getProvinceName() {
        return provinceName;
    }

    @Override
    public String getCityName() {
        return cityName;
    }

    @Override
    public String getAreaName() {
        return areaName;
    }
}
