package com.keydom.mianren.ih_doctor.fragment.controller;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.bean.PageBean;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_doctor.bean.InquiryBean;
import com.keydom.mianren.ih_doctor.constant.TypeEnum;
import com.keydom.mianren.ih_doctor.fragment.view.DiagnoseOrderFragmentView;
import com.keydom.mianren.ih_doctor.net.DiagnoseApiService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.fragment.controller
 * @Description：描述信息
 * @Author：song
 * @Date：18/11/16 下午2:26
 * 修改人：xusong
 * 修改时间：18/11/16 下午2:26
 */
public class DiagnoseOrderFragmentController extends ControllerImpl<DiagnoseOrderFragmentView> {

    /**
     * 获取问诊订单
     *
     * @param type 订单type
     */
    public void getHeadNurseServiceOrderList(final TypeEnum type) {
        if (type == TypeEnum.REFRESH) {
            setCurrentPage(1);
        }
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(DiagnoseApiService.class).listInquisition(getView().getListMap()), new HttpSubscriber<PageBean<InquiryBean>>() {
            @Override
            public void requestComplete(@Nullable PageBean<InquiryBean> data) {
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
