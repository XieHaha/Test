package com.keydom.ih_patient.activity.medical_mail.fragment;

import android.os.Bundle;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.ih_common.view.InterceptorEditText;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.medical_mail.controller.MedicalMailReceiveController;
import com.keydom.ih_patient.activity.medical_mail.view.MedicalMailReceiveView;
import com.keydom.ih_patient.bean.Event;
import com.keydom.ih_patient.bean.LocationInfo;
import com.keydom.ih_patient.bean.MedicalMailApplyBean;
import com.keydom.ih_patient.bean.PackageData;
import com.keydom.ih_patient.constant.EventType;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import butterknife.BindView;

/**
 * 病案邮寄-收件信息
 */
public class MedicalMailReceiveFragment extends BaseControllerFragment<MedicalMailReceiveController> implements MedicalMailReceiveView {
    @BindView(R.id.tv_select_address)
    TextView tvSelectAddress;
    @BindView(R.id.et_name)
    InterceptorEditText etName;
    @BindView(R.id.et_phone)
    InterceptorEditText etPhone;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.et_address_detail)
    InterceptorEditText etAddressDetail;
    @BindView(R.id.tv_next)
    TextView tvNext;

    private MedicalMailApplyBean applyBean;

    private String provinceName, cityName, areaName;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_medical_mail_receive;
    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvCity.setOnClickListener(getController());
        tvSelectAddress.setOnClickListener(getController());
        tvNext.setOnClickListener(getController());
    }

    public void setApplyBean(MedicalMailApplyBean applyBean) {
        this.applyBean = applyBean;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSelectAddress(Event event) {
        if (event.getType() == EventType.SENDLOCATION) {
            LocationInfo info = (LocationInfo) event.getData();
            bindData(info);
        }
    }

    private void bindData(LocationInfo info) {

    }

    @Override
    public void saveRegion(List<PackageData.ProvinceBean> data, int options1, int option2,
                           int options3) {
        if (data.get(options1).getCity().size() == 0) {
            provinceName = data.get(options1).getName();
            cityName = "";
            areaName = "";
        } else if (data.get(options1).getCity().get(option2).getArea().size() == 0) {
            provinceName = data.get(options1).getName();
            cityName = data.get(options1).getCity().get(option2).getName();
            areaName = "";
        } else {
            provinceName = data.get(options1).getName();
            cityName = data.get(options1).getCity().get(option2).getName();
            areaName = data.get(options1).getCity().get(option2).getArea().get(options3).getName();
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

    @Override
    public String getRecvName() {
        return etName.getText().toString();
    }

    @Override
    public String getRecvPhone() {
        return etPhone.getText().toString();
    }

    @Override
    public String getRecvCity() {
        return tvCity.getText().toString();
    }

    @Override
    public String getRecvCityDetail() {
        return etAddressDetail.getText().toString();
    }

    @Override
    public MedicalMailApplyBean getApplyData() {
        return applyBean;
    }
}
