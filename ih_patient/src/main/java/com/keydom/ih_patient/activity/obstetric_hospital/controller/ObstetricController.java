package com.keydom.ih_patient.activity.obstetric_hospital.controller;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_patient.activity.obstetric_hospital.view.ObstetricView;
import com.keydom.ih_patient.constant.TypeEnum;

/**
 * 产科住院记录
 */
public class ObstetricController extends ControllerImpl<ObstetricView> {

    /**
     * 获取挂号列表
     */
    public void queryObstetricRecordList(String state, final TypeEnum typeEnum) {
        if (typeEnum == TypeEnum.REFRESH) {
            setCurrentPage(1);
        }
        //        Map<String, Object> map = new HashMap<>();
        //        map.put("userId", Global.getUserId());
        //        map.put("hospitalId", App.hospitalId);
        //        map.put("state", state);
        //        map.put("currentPage", getCurrentPage());
        //        map.put("pageSize", Const.PAGE_SIZE);
        //        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(OrderService
        //        .class).list(map), new HttpSubscriber<PageBean<RegistrationRecordInfo>>
        //        (getContext(), getDisposable(), false) {
        //            @Override
        //            public void requestComplete(@Nullable PageBean<RegistrationRecordInfo> data) {
        //                getView().getMedicalMailedSuccess(data.getRecords(), typeEnum);
        //            }
        //
        //            @Override
        //            public boolean requestError(@NotNull ApiException exception, int code,
        //                                        @NotNull String msg) {
        //                getView().getMedicalMailedFailed(msg);
        //                return super.requestError(exception, code, msg);
        //            }
        //        });
    }
}
