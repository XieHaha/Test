package com.keydom.mianren.ih_doctor.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ganxin.library.LoadDataLayout;
import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.mianren.ih_doctor.MyApplication;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.adapter.NurseServiceOrderRecyclrViewAdapter;
import com.keydom.mianren.ih_doctor.bean.MessageEvent;
import com.keydom.mianren.ih_doctor.bean.NurseServiceListBean;
import com.keydom.mianren.ih_doctor.constant.Const;
import com.keydom.mianren.ih_doctor.constant.EventType;
import com.keydom.mianren.ih_doctor.constant.TypeEnum;
import com.keydom.mianren.ih_doctor.fragment.controller.NurseServiceOrderFragmentController;
import com.keydom.mianren.ih_doctor.fragment.view.NurseServiceOrderFragmentView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Name：com.kentra.yxyz.fragment
 * @Description：护理订单列表页面
 * @Author：song
 * @Date：18/11/5 下午5:27
 * 修改人：xusong
 * 修改时间：18/11/5 下午5:27
 */
public class NurseServiceOrderFragment extends BaseControllerFragment<NurseServiceOrderFragmentController> implements NurseServiceOrderFragmentView {

    private RecyclerView recyclerView;
    /**
     * 护理订单适配器
     */
    private NurseServiceOrderRecyclrViewAdapter mAdapter;
    private RefreshLayout refreshLayout;
    /**
     * 护理订单列表
     */
    private List<NurseServiceListBean> dataList = new ArrayList<>();
    /**
     * 页面类型
     */
    private TypeEnum mType;
    /**
     * 关键字
     */
    private String keyword = "";


    public static final NurseServiceOrderFragment newInstance(TypeEnum type) {
        NurseServiceOrderFragment fragment = new NurseServiceOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Const.TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_nurse_service;
    }


    @Override
    public void getDataSuccess(TypeEnum type, List<NurseServiceListBean> list) {
        getController().currentPagePlus();
        if (type == TypeEnum.REFRESH) {
            dataList.clear();
        }
        dataList.addAll(list);
        mAdapter.notifyDataSetChanged();
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
        pageLoadingSuccess();

    }

    @Override
    public void getDataFailed(String errMsg) {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
        pageLoadingFail();

    }

    @Override
    public TypeEnum getType() {
        return mType;
    }

    @Override
    public Map<String, Object> getListMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("nurseId", MyApplication.userInfo.getId());
        map.put("state", getState());
        map.put("currentPage", getController().getCurrentPage());
        map.put("pageSize", Const.PAGE_SIZE);
        map.put("keyword", keyword);
        return map;
    }

    /**
     * 获取状态
     *
     * @return
     */
    private int getState() {
        if (mType == TypeEnum.HEAD_NURSE_FRAGMENT_RECEIVE_ORDER || mType == TypeEnum.COMMON_NURSE_FRAGMENT_RECEIVE_ORDER) {
            return 0;
        } else if (mType == TypeEnum.HEAD_NURSE_FRAGMENT_ALREADY_RECEIVE_ORDER || mType == TypeEnum.COMMON_NURSE_FRAGMNET_WORKING_ORDER) {
            return 1;
        } else if (mType == TypeEnum.COMMON_NURSE_FRAGMENT_FINISH_ORDER) {
            return 2;
        }
        return -1;
    }

    @Override
    public void initData(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        mType = (TypeEnum) getArguments().getSerializable(Const.TYPE);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        mAdapter = new NurseServiceOrderRecyclrViewAdapter(getContext(), mType, dataList);
        recyclerView = (RecyclerView) getView().findViewById(R.id.community_attention_rv);
        refreshLayout = (RefreshLayout) getView().findViewById(R.id.refreshLayout);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        refreshLayout.setOnLoadMoreListener(getController());
        refreshLayout.setOnRefreshListener(getController());
        setReloadListener(new LoadDataLayout.OnReloadListener() {
            @Override
            public void onReload(View v, int status) {
                pageLoading();
                getController().setCurrentPage(1);
                getController().getData(TypeEnum.REFRESH);
            }
        });
    }

    @Override
    public void lazyLoad() {
//        refreshLayout.autoRefresh();
        getController().setCurrentPage(1);
        pageLoading();
        getController().getData(TypeEnum.REFRESH);
    }

    @Override
    public void onDestroy() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        if (messageEvent.getType() == EventType.NURSE_SERVICE_ORDER_UPDATE) {
            getController().setCurrentPage(1);
            getController().getData(TypeEnum.REFRESH);
        } else if (messageEvent.getType() == EventType.SEARCH_NURSE_SERVICE_ORDER) {
            keyword = (String) messageEvent.getData();
            getController().setCurrentPage(1);
            getController().getData(TypeEnum.REFRESH);
            getController().showLoading();
        }
    }
}
