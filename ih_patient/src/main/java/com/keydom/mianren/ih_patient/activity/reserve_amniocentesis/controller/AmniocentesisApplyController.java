package com.keydom.mianren.ih_patient.activity.reserve_amniocentesis.controller;

import android.app.Activity;
import android.view.View;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.StringUtils;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.PhoneUtils;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.reserve_amniocentesis.view.AmniocentesisApplyView;
import com.keydom.mianren.ih_patient.bean.Event;
import com.keydom.mianren.ih_patient.constant.EventType;
import com.keydom.mianren.ih_patient.net.LoginService;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @date 20/3/9 16:09
 * @des 羊水穿刺预约申请
 */
public class AmniocentesisApplyController extends ControllerImpl<AmniocentesisApplyView> implements IhTitleLayout.OnRightTextClickListener, View.OnClickListener {
    @Override
    public void OnRightTextClick(View v) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.amniocentesis_apply_surgery_time_layout:
            case R.id.amniocentesis_apply_last_menstruation_layout:
            case R.id.amniocentesis_apply_due_date_layout:
            case R.id.amniocentesis_apply_birth_layout:
                KeyboardUtils.hideSoftInput((Activity) getContext());
                TimePickerView pickerView = new TimePickerBuilder(getContext(),
                        (date, v1) -> getView().onDateSelect(v, date)).build();
                pickerView.show();
                break;
            case R.id.amniocentesis_apply_get_verify_tv:
                String phone = getView().getPhone();
                if (StringUtils.isEmpty(phone) || !PhoneUtils.isMobileEnable(phone)) {
                    ToastUtil.showMessage(getContext(), "请填写正确的手机格式");
                } else {
                    getVerifyCode(phone);
                }
                break;
            case R.id.amniocentesis_apply_next_tv:
                EventBus.getDefault().post(new Event(EventType.AMNIOCENTESIS_WEB_NOTICE,
                        getView().getReserveBean()));
                break;
            default:
                break;
        }
    }

    /**
     * 获取验证码
     */
    private void getVerifyCode(String phone) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(LoginService.class).sendValidate(phone), new HttpSubscriber<Object>(getContext(), getDisposable(), true, false) {

            @Override
            public void requestComplete(@Nullable Object data) {
                getView().getMsgCodeSuccess();
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().getMsgCodeFailed(msg);
                return super.requestError(exception, code, msg);

            }
        });
    }
}
