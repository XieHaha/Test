package com.keydom.mianren.ih_doctor.activity.online_consultation.controller;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.mianren.ih_doctor.activity.online_consultation.view.ConsultationOrderFragmentView;
import com.keydom.mianren.ih_doctor.constant.TypeEnum;

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
        getView().getDataSuccess(type, null);
//        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(DiagnoseApiService.class).listInquisition(getView().getListMap()), new HttpSubscriber<List<InquiryBean>>() {
//            @Override
//            public void requestComplete(@Nullable List<InquiryBean> data) {
//                getView().getDataSuccess(type, data);
//            }
//
//            @Override
//            public boolean requestError(@NotNull ApiException exception, int code,
//                                        @NotNull String msg) {
//                getView().getDataFailed(msg);
//                return super.requestError(exception, code, msg);
//            }
//        });
    }


}
