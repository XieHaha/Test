package com.keydom.mianren.ih_patient.activity.my_doctor_or_nurse.controller;

import com.blankj.utilcode.util.ToastUtils;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.bean.PageBean;
import com.keydom.ih_common.im.ImClient;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_patient.App;
import com.keydom.mianren.ih_patient.activity.my_doctor_or_nurse.view.MyDoctorOrNurseView;
import com.keydom.mianren.ih_patient.bean.DoctorOrNurseBean;
import com.keydom.mianren.ih_patient.constant.Const;
import com.keydom.mianren.ih_patient.constant.Global;
import com.keydom.mianren.ih_patient.constant.TypeEnum;
import com.keydom.mianren.ih_patient.net.UserService;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * created date: 2019/1/2 on 14:25
 * des:我的关注医生控制器
 */
public class MyDoctorOrNurseController extends ControllerImpl<MyDoctorOrNurseView> implements OnRefreshListener, OnLoadMoreListener {

    private int requestType;

    /**
     * 获取我的关注列表
     */
    public void getMyFollowList(int type,final TypeEnum typeEnum) {
        requestType = type;
        if (typeEnum == TypeEnum.REFRESH) {
            setCurrentPage(1);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("type", type);
        map.put("userId", Global.getUserId());
        map.put("hospitalId", App.hospitalId);
        map.put("currentPage", getCurrentPage());
        map.put("pageSize", Const.PAGE_SIZE);
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).getMyFollowList(map), new HttpSubscriber<PageBean<DoctorOrNurseBean>>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable PageBean<DoctorOrNurseBean> data) {
                hideLoading();
                if(null != data){
                    List<DoctorOrNurseBean> list = data.getRecords();
                    if (list != null && list.size()!=0) {
                        for (int i = 0; i < list.size(); i++) {
                            list.get(i).setType(type);
                        }
                        getView().myFollowsCallBack(list,typeEnum);
                        getChatList();
                    }
                }

            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                ToastUtils.showShort(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 获取聊天列表
     */
    public void getChatList(){
        ImClient.queryRecentContacts(result -> {
            List<DoctorOrNurseBean> data = getView().returnFollows();
            if (data==null){
                return;
            }
            for (int i = 0; i < data.size(); i++) {
                for (int j = 0; j < result.size(); j++) {
                    if (data.get(i).getImNumber()!=null && data.get(i).getImNumber().toLowerCase().equals(result.get(j).getContactId())){
                        data.get(i).setContent(result.get(j).getContent());
                        data.get(i).setContentNum(result.get(j).getUnreadCount());
                        data.get(i).setTime(result.get(j).getTime());
                    }
                }
            }
            getView().mateFollows(data);
        });
    }


    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        currentPagePlus();
        getMyFollowList(requestType,TypeEnum.LOAD_MORE);

    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        setCurrentPage(1);
        getMyFollowList(requestType,TypeEnum.REFRESH);
    }
}
