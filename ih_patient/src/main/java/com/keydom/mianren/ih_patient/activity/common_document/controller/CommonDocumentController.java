package com.keydom.mianren.ih_patient.activity.common_document.controller;

import android.app.Activity;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_patient.App;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.common_document.view.CommonDocumentView;
import com.keydom.mianren.ih_patient.activity.reserve_painless_delivery.ReservePainlessDeliveryActivity;
import com.keydom.mianren.ih_patient.bean.CommonDocumentBean;
import com.keydom.mianren.ih_patient.net.UserService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * created date: 2019/3/27 on 16:40
 * des:公共文书维护控制器
 *
 * @author 顿顿
 */
public class CommonDocumentController extends ControllerImpl<CommonDocumentView> implements View.OnClickListener {
    /**
     * 获取文书维护详情内容
     */
    public void getOfficialDispatchAllMsgByCode(String code) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("hospitalId", App.hospitalId);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).getOfficialDispatchAllMsgByCode(map), new HttpSubscriber<CommonDocumentBean>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable CommonDocumentBean data) {
                getView().getData(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                ToastUtils.showShort(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.document_bottom_yes_layout:
                getView().onReserveProtocolSelect(true);
                break;
            case R.id.document_bottom_no_layout:
                getView().onReserveProtocolSelect(false);
                break;
            case R.id.document_bottom_next_tv:
                if (!getView().isSelectReserveProtocol()) {
                    ToastUtil.showMessage(mContext, "请仔细阅读并同意以上协议内容");
                    return;
                }
                ReservePainlessDeliveryActivity.start(getContext());
                ((Activity) mContext).finish();
                break;
            default:
        }
    }
}
