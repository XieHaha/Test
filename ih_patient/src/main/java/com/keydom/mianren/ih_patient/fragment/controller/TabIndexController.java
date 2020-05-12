package com.keydom.mianren.ih_patient.fragment.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.bean.MainLoadingEvent;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_patient.App;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.global_search.SearchActivity;
import com.keydom.mianren.ih_patient.activity.index_main.ChooseCityActivity;
import com.keydom.mianren.ih_patient.activity.member.MemberDetailActivity;
import com.keydom.mianren.ih_patient.activity.my_message.MyMessageActivity;
import com.keydom.mianren.ih_patient.bean.CityBean;
import com.keydom.mianren.ih_patient.bean.Event;
import com.keydom.mianren.ih_patient.bean.HospitalAreaInfo;
import com.keydom.mianren.ih_patient.bean.IndexData;
import com.keydom.mianren.ih_patient.bean.IndexFunction;
import com.keydom.mianren.ih_patient.callback.SingleClick;
import com.keydom.mianren.ih_patient.constant.EventType;
import com.keydom.mianren.ih_patient.constant.FunctionConfig;
import com.keydom.mianren.ih_patient.constant.Global;
import com.keydom.mianren.ih_patient.constant.Type;
import com.keydom.mianren.ih_patient.fragment.view.TabIndexView;
import com.keydom.mianren.ih_patient.net.IndexService;
import com.keydom.mianren.ih_patient.net.UserService;
import com.keydom.mianren.ih_patient.utils.DepartmentDataHelper;
import com.keydom.mianren.ih_patient.utils.LocalizationUtils;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 主页index控制器
 */
public class TabIndexController extends ControllerImpl<TabIndexView> implements View.OnClickListener {
    private IndexFunction allFunction;

    @SingleClick(1000)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_tab_index_open_vip_ll:
                MemberDetailActivity.start(getContext());
                break;
            case R.id.location_layout:
                ChooseCityActivity.start(getContext());
                break;
            case R.id.search_layout:
                //                Toast.makeText(getContext(), "点击了Search", Toast.LENGTH_SHORT)
                //                .show();
                SearchActivity.start(getContext());
                break;
            case R.id.qr_code_layout:
                //Toast.makeText(getContext(),"点击了Qr",Toast.LENGTH_SHORT).show();
                if (Global.getUserId() != -1) {
                    EventBus.getDefault().post(new Event(EventType.STARTTOQR, null));
                } else {
                    ToastUtil.showMessage(getContext(), "你未登录,请登录后尝试");
                }
                break;
            case R.id.empty_layout:
                fillViewData();
                fillFunction();
                break;
            case R.id.search_edt:
                getView().showHospitalPopupWindow();
                break;
            case R.id.more_tv:
                if (getView().getNoticeList() != null && getView().getNoticeList().size() > 0)
                    MyMessageActivity.start(getContext(), Type.NOTICEMESSAGE, null);
                else
                    ToastUtil.showMessage(getContext(), "暂无通知公告");
                break;
            default:
        }

    }

    /**
     * 查找功能
     */
    public void fillFunction() {
        Map<String, Object> map = new HashMap<>();
        map.put("roleId", "4");
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(IndexService.class).initFunctionData(map), new HttpSubscriber<List<IndexFunction>>(getContext(), getDisposable(), false) {

            @Override
            public void requestComplete(@Nullable List<IndexFunction> data) {

                for (int i = 0; i < data.size(); i++) {
                    data.get(i).setFunctionIcon(FunctionConfig.getIcon(data.get(i).getId()));
                    data.get(i).setSelected(false);
                }
                Global.setFuncitonList(data);
                String filename = "index_function_" + Global.getUserId();
                String allFunctionFilename = "all_function_" + Global.getUserId();
                List<IndexFunction> dataList =
                        (List<IndexFunction>) LocalizationUtils.readFileFromLocal(getContext(),
                                filename);
                List<IndexFunction> savedLocalFunction = new ArrayList<>();
                if (dataList != null && dataList.size() != 0) {
                    Map<Long, IndexFunction> id2IndexFunctionServerMap = new LinkedHashMap<>();
                    for (int i = 0; i < data.size(); i++) {
                        data.get(i).setFunctionIcon(FunctionConfig.getIcon(data.get(i).getId()));
                        data.get(i).setSelected(false);
                        id2IndexFunctionServerMap.put(data.get(i).getId(), data.get(i));
                    }
                    Map<Long, IndexFunction> id2IndexFunctionLocalMap = new LinkedHashMap<>();
                    for (int i = 0; i < dataList.size(); i++) {
                        id2IndexFunctionLocalMap.put(dataList.get(i).getId(), dataList.get(i));
                    }


                    for (Map.Entry<Long, IndexFunction> entry :
                            id2IndexFunctionLocalMap.entrySet()) {
                        if (null != id2IndexFunctionServerMap.get(entry.getKey())) {
                            id2IndexFunctionServerMap.get(entry.getKey()).setSelected(true);
                            savedLocalFunction.add(id2IndexFunctionServerMap.get(entry.getKey()));
                        }
                    }
                    List<IndexFunction> savedServiceFunction = new ArrayList<>();
                    for (Map.Entry<Long, IndexFunction> entry :
                            id2IndexFunctionServerMap.entrySet()) {
                        savedServiceFunction.add(id2IndexFunctionServerMap.get(entry.getKey()));
                    }
                    if (Global.getUserId() != -1) {
                        LocalizationUtils.fileSave2Local(getContext(), savedLocalFunction,
                                filename);
                        Logger.e("更改后菜单配置写入成功");

                    }
                    LocalizationUtils.fileSave2Local(getContext(), data, allFunctionFilename);
                    //                    Global.setFuncitonList(data);
                } else {
                    int size = data.size() > 7 ? 7 : data.size();
                    for (int i = 0; i < size; i++) {
                        data.get(i).setFunctionIcon(FunctionConfig.getIcon(data.get(i).getId()));
                        data.get(i).setSelected(true);
                        data.get(i).setRedPointShow(false);
                        savedLocalFunction.add(data.get(i));
                    }
                    LocalizationUtils.fileSave2Local(getContext(), data, allFunctionFilename);
                    //                    Global.setFuncitonList(data);
                    //                    savedLocalFunction.addAll(data);
                    if (Global.getUserId() != -1) {
                        LocalizationUtils.fileSave2Local(getContext(), savedLocalFunction,
                                filename);
                        Logger.e("首次菜单配置写入成功");

                    }

                }
                allFunction = new IndexFunction();
                allFunction.setName("全部");
                allFunction.setId(1);
                allFunction.setFunctionIcon(R.mipmap.more_icon);
                savedLocalFunction.add(allFunction);
                getView().setFunctionRvData(savedLocalFunction);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().dataRequestFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 查找控件数据
     */
    public void fillViewData() {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", Global.getUserId());
        map.put("hospitalId", App.hospitalId);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(IndexService.class).initIndexData(map), new HttpSubscriber<IndexData>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable IndexData data) {
                requestDataSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().dataRequestFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });

    }

    /**
     * 查找健康知识
     */
    public void fillHealthKnowledges(int currentPage) {
        Map<String, Object> map = new HashMap<>();
        map.put("currentPage", currentPage);
        map.put("pageSize", 10);
        map.put("hospitalId", App.hospitalId);
        //        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(IndexService
        //        .class).getHealthKnowledgeLimit(map), new HttpSubscriber<HealthKnowledgeBean>
        //        (getContext(), getDisposable(), false) {
        //            @Override
        //            public void requestComplete(@Nullable HealthKnowledgeBean data) {
        //                getView().setArticleData(data.getRecords());
        //            }
        //
        //            @Override
        //            public boolean requestError(@NotNull ApiException exception, int code,
        //            @NotNull String msg) {
        ////                getView().dataRequestFailed(msg);
        //                return super.requestError(exception, code, msg);
        //            }
        //        });
    }


    /**
     * 初始化医院列表
     */
    public void initHospitalList(String cityCode) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).getHospitalList(cityCode, Global.getUserId()), new HttpSubscriber<List<HospitalAreaInfo>>(getContext()) {
            @Override
            public void requestComplete(@Nullable List<HospitalAreaInfo> data) {
                getView().getHospitalListSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 获取数据成功
     */
    private void requestDataSuccess(IndexData data) {
        getView().setPicBannerData(data.getHeaderbanner());
        getView().setAdBannerData(data.getAdvertisement());
        getView().setNoticeData(data.getNotifications());
        //        getView().setArticleData(data.getHealthKnowledges());
        getView().setRedPointView(data);
        EventBus.getDefault().post(new MainLoadingEvent());
    }

    /**
     * 更新数据
     */
    public void upDateData(List<IndexFunction> dataList) {
        Logger.e("执行upDateData");
        List<IndexFunction> indexFunctionList = new ArrayList<>();
        indexFunctionList.addAll(dataList);
        indexFunctionList.add(allFunction);
        for (int i = 0; i < dataList.size(); i++) {
            dataList.get(i).setRedPointShow(false);
        }
        getView().setFunctionRvData(indexFunctionList);
    }

    /**
     * 通过关键字搜索城市
     */
    public void queryCityListByKeyword(String keyWord) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).findOrderByPinyin(keyWord), new HttpSubscriber<CityBean>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable CityBean data) {
                getView().getCityListSuccess(DepartmentDataHelper.getCityBeanAfterHandle(data));
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().getCityListFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

}
