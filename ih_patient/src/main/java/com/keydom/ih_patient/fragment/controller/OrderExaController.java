package com.keydom.ih_patient.fragment.controller;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.bean.PageBean;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_patient.bean.ExaminationInfo;
import com.keydom.ih_patient.constant.Const;
import com.keydom.ih_patient.constant.Global;
import com.keydom.ih_patient.constant.TypeEnum;
import com.keydom.ih_patient.fragment.view.OrderExaView;
import com.keydom.ih_patient.net.OrderService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 检查预约控制器
 */
public class OrderExaController extends ControllerImpl<OrderExaView> {
    /**
     * 查询预约列表
     */
    public void QueryAllAppointment(final TypeEnum typeEnum){
        if (typeEnum == TypeEnum.REFRESH) {
            setCurrentPage(1);
        }
        Map<String,Object> map=new HashMap<>();
        map.put("cardNumber",Global.getSelectedCardNum());
        map.put("state",0);
        map.put("currentPage", getCurrentPage());
        map.put("pageSize", Const.PAGE_SIZE);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(OrderService.class).findAppointmentAll(map), new HttpSubscriber<PageBean<ExaminationInfo>>(getContext(),getDisposable(),false) {
            @Override
            public void requestComplete(@Nullable PageBean<ExaminationInfo> data) {
                getView().fillExaminationList(data.getRecords(),typeEnum);
            }
            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                if(300 == code){
                    getView().fillExaminationList(new ArrayList<>(),typeEnum);
                }else{
                    getView().fillExaminationListFailed(msg);
                }
                return super.requestError(exception, code, msg);
            }
        });
    }
}
