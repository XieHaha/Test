package com.keydom.mianren.ih_doctor.activity.online_consultation.controller;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.bean.PageBean;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_doctor.activity.online_consultation.ConsultationRoomActivity;
import com.keydom.mianren.ih_doctor.activity.online_consultation.view.ConsultationOrderFragmentView;
import com.keydom.mianren.ih_doctor.bean.ConsultationBean;
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
public class ConsultationOrderFragmentController extends ControllerImpl<ConsultationOrderFragmentView> implements BaseQuickAdapter.OnItemClickListener {

    /**
     * 获取问诊订单
     *
     * @param type 订单type
     */
    public void getConsultationOrderList(final TypeEnum type) {
        if (type == TypeEnum.REFRESH) {
            setCurrentPage(1);
        }
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ConsultationService.class).consultationOrderApplyList(getView().getListMap()), new HttpSubscriber<PageBean<ConsultationBean>>() {
            @Override
            public void requestComplete(@Nullable PageBean<ConsultationBean> data) {
                if (data == null) {
                    return;
                }
                List<ConsultationBean> list = data.getRecords();
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


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ConsultationBean bean = (ConsultationBean) adapter.getItem(position);
        if (bean != null) {
            ConsultationRoomActivity.start(mContext, bean.getApplicationId(),
                    bean.getApplicantId());
        }
    }
}
