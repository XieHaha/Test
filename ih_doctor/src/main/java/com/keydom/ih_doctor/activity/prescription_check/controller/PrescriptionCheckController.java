package com.keydom.ih_doctor.activity.prescription_check.controller;

import android.app.Activity;
import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.prescription_check.view.PrescriptionCheckView;
import com.keydom.ih_doctor.bean.MessageEvent;
import com.keydom.ih_doctor.constant.EventType;

import org.greenrobot.eventbus.EventBus;

/**
 * @Name：com.keydom.ih_doctor.activity.controller
 * @Description：描述信息
 * @Author：song
 * @Date：18/11/16 上午9:09
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:09
 */
public class PrescriptionCheckController extends ControllerImpl<PrescriptionCheckView> implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.search_tv:
                EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.SEARCH_PRESCRIPTION).setData(getView().getSearchValue()).build());
                CommonUtils.hideSoftKeyboard((Activity) getContext());
                break;
            default:
        }

    }

}
