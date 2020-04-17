package com.keydom.mianren.ih_doctor.activity.online_consultation.controller;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.bean.PageBean;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_doctor.activity.online_consultation.view.ConsultationOrderFragmentView;
import com.keydom.mianren.ih_doctor.bean.InquiryBean;
import com.keydom.mianren.ih_doctor.constant.TypeEnum;
import com.keydom.mianren.ih_doctor.net.ConsultationService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @date 4月2日 15:03
 * 会诊列表
 */
public class ConsultationOrderFragmentController extends ControllerImpl<ConsultationOrderFragmentView> {

    /**
     * 获取问诊订单
     *
     * @param type 订单type
     */
    public void getConsultationOrderList(final TypeEnum type) {
        if (type == TypeEnum.REFRESH) {
            setCurrentPage(1);
        }
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ConsultationService.class).consultationOrderApplyList(getView().getListMap()), new HttpSubscriber<PageBean<InquiryBean>>() {
            @Override
            public void requestComplete(@Nullable PageBean<InquiryBean> data) {
                if (data == null) {
                    return;
                }
                List<InquiryBean> list = data.getRecords();
                if (list == null) {
                    list = new ArrayList<>();
                }
                getView().getDataSuccess(type, list);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().getDataFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }


}
