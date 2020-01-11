package com.keydom.ih_patient.activity.member.controller;

import com.blankj.utilcode.util.ToastUtils;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.bean.PageBean;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_patient.activity.member.view.ChargeMemberRecordView;
import com.keydom.ih_patient.bean.RenewalRecordItem;
import com.keydom.ih_patient.constant.Const;
import com.keydom.ih_patient.constant.Global;
import com.keydom.ih_patient.constant.TypeEnum;
import com.keydom.ih_patient.net.VIPCardService;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ChargeMemberRecordController extends ControllerImpl<ChargeMemberRecordView> {


    /**
     * 续约记录
     */
    public void getRenewalRecord(SmartRefreshLayout refreshLayout, int state, final TypeEnum typeEnum) {
        if (typeEnum == TypeEnum.REFRESH) {
            setCurrentPage(1);
        }
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(VIPCardService.class).getRenewalRecord(Global.getUserId(),getCurrentPage(),Const.PAGE_SIZE), new HttpSubscriber<PageBean<RenewalRecordItem>>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable PageBean<RenewalRecordItem> data) {
                if (data != null) {
                    getView().paymentListCallBack(data.getRecords(),typeEnum);
                }
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                if (!"token解析失败".equals(msg))
                    ToastUtils.showLong(msg);
                refreshLayout.finishLoadMore();
                refreshLayout.finishRefresh();
                return super.requestError(exception, code, msg);
            }
        });

    }
}
