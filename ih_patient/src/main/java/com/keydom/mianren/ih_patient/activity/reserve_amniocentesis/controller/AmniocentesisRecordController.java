package com.keydom.mianren.ih_patient.activity.reserve_amniocentesis.controller;

import android.text.TextUtils;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.bean.PageBean;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.mianren.ih_patient.activity.reserve_amniocentesis.AmniocentesisDetailActivity;
import com.keydom.mianren.ih_patient.activity.reserve_amniocentesis.view.AmniocentesisRecordView;
import com.keydom.mianren.ih_patient.bean.AmniocentesisBean;
import com.keydom.mianren.ih_patient.constant.Const;
import com.keydom.mianren.ih_patient.constant.Global;
import com.keydom.mianren.ih_patient.constant.TypeEnum;
import com.keydom.mianren.ih_patient.net.AmniocentesisService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * @date 20/3/11 14:26
 * @des 羊水穿刺预约查询及取消
 */
public class AmniocentesisRecordController extends ControllerImpl<AmniocentesisRecordView> implements BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {

    /**
     * 获取穿刺预约记录
     */
    public void getAmniocentesisRecord(String idCard, TypeEnum typeEnum, boolean showDialog) {
        if (typeEnum == TypeEnum.REFRESH) {
            setCurrentPage(1);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("currentPage", getCurrentPage());
        map.put("pageSize", Const.PAGE_SIZE);
        map.put("userId", Global.getUserId());
        if (!TextUtils.isEmpty(idCard)) {
            map.put("idCard", idCard);
        }
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(AmniocentesisService.class)
                        .getAmniocentesisList(HttpService.INSTANCE.object2Body(map)),
                new HttpSubscriber<PageBean<AmniocentesisBean>>(getContext(), getDisposable(),
                        showDialog, false) {
                    @Override
                    public void requestComplete(@Nullable PageBean<AmniocentesisBean> data) {
                        getView().onAmniocentesisRecordSuccess(typeEnum, data.getRecords());
                    }

                    @Override
                    public boolean requestError(@NotNull ApiException exception, int code,
                                                @NotNull String msg) {
                        ToastUtils.showShort(msg);
                        return super.requestError(exception, code, msg);
                    }
                });

    }

    /**
     * 获取穿刺预约记录
     */
    public void getAmniocentesisRecord(String idCard, TypeEnum typeEnum) {
        getAmniocentesisRecord(idCard, typeEnum, false);
    }

    private void cancelAmniocentesisReserve(int id, int position) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(AmniocentesisService.class)
                        .cancelAmniocentesis(id),
                new HttpSubscriber<PageBean<AmniocentesisBean>>(getContext(), getDisposable(),
                        true, false) {
                    @Override
                    public void requestComplete(@Nullable PageBean<AmniocentesisBean> data) {
                        getView().onAmniocentesisCancelSuccess(position);
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
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        AmniocentesisBean bean = (AmniocentesisBean) adapter.getItem(position);
        AmniocentesisDetailActivity.start(getContext(), bean);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        new GeneralDialog(mContext, "确认取消预约？", () -> {
            AmniocentesisBean bean = (AmniocentesisBean) adapter.getItem(position);
            cancelAmniocentesisReserve(bean.getId(), position);
        }).setTitle("提示").setPositiveButton("确认").show();
    }
}
