package com.keydom.mianren.ih_doctor.activity.personal.controller;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_doctor.activity.personal.view.MyServiceView;
import com.keydom.mianren.ih_doctor.bean.ServiceItemBean;
import com.keydom.mianren.ih_doctor.net.PersonalApiService;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.activity.controller
 * @Description：我的服务控制器
 * @Author：song
 * @Date：18/11/16 上午9:09
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:09
 */
public class MyServiceController extends ControllerImpl<MyServiceView> implements OnRefreshListener, OnLoadMoreListener {


    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        getService();
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        getService();
    }


    /**
     * 获取全部服务
     */
    public void getService() {
        HashMap<String, Object> map = new HashMap<>();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PersonalApiService.class).getMySrevice(map), new HttpSubscriber<ArrayList<ServiceItemBean>>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable ArrayList<ServiceItemBean> data) {
                List<MultiItemEntity> entities = handleList(data);
                getView().getServiceSuccess(entities);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                return super.requestError(exception, code, msg);
            }
        });
    }

    private List<MultiItemEntity> handleList(ArrayList<ServiceItemBean> data) {
        List<MultiItemEntity> datas = new ArrayList<>();
        if (data == null) {
            return datas;
        }
        for (int i = 0; i < data.size(); i++) {
            datas.add(data.get(i));
            if (data.get(i).getDoctorServiceSubVoList() != null) {
                for (int j = 0; j < data.get(i).getDoctorServiceSubVoList().size(); j++) {
                    datas.add(data.get(i).getDoctorServiceSubVoList().get(j));
                }
            }
        }
        return datas;
    }


    /**
     * 停止服务
     */
    public void stopService(long serviceId) {
        showLoading();
        HashMap<String, Object> map = new HashMap<>();
        map.put("serviceId", serviceId);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PersonalApiService.class).disabledService(map), new HttpSubscriber<String>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable String data) {
                hideLoading();
                getView().disableServcieSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().disableServiceFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 开始服务
     */
    public void openService(long serviceId) {
        showLoading();
        HashMap<String, Object> map = new HashMap<>();
        map.put("serviceId", serviceId);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PersonalApiService.class).enabledService(map), new HttpSubscriber<String>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable String data) {
                getView().enableServiceSuccess(data);
                hideLoading();
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().enableServiceFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }


}
