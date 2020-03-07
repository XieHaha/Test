package com.keydom.ih_patient.activity.medical_mail.controller;

import android.text.TextUtils;
import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.location_manage.LocationManageActivity;
import com.keydom.ih_patient.activity.medical_mail.view.MedicalMailReceiveView;
import com.keydom.ih_patient.bean.Event;
import com.keydom.ih_patient.bean.MedicalMailApplyBean;
import com.keydom.ih_patient.constant.EventType;
import com.keydom.ih_patient.constant.Type;
import com.keydom.ih_patient.utils.SelectDialogUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * 病案邮寄-收件信息
 */
public class MedicalMailReceiveController extends ControllerImpl<MedicalMailReceiveView> implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_next:
                onNext();
                break;
            case R.id.tv_city:
                SelectDialogUtils.showRegionSelectDialog(getContext(),
                        getView().getProvinceName(), getView().getCityName(),
                        getView().getAreaName(),
                        (data, position1, position2, position3) -> getView().saveRegion(data,
                                position1, position2, position3));
                break;
            case R.id.tv_select_address:
                LocationManageActivity.start(getContext(), Type.STARTLOCATIONWITHRESULT);
                break;
            default:
                break;
        }
    }

    private void onNext() {
        if (TextUtils.isEmpty(getView().getRecvName())) {
            ToastUtil.showMessage(getContext(), "收件人姓名不能为空");
            return;
        }
        MedicalMailApplyBean bean = getView().getApplyData();
        EventBus.getDefault().post(new Event(EventType.MEDICAL_STEP_THREE, bean));
    }
}
