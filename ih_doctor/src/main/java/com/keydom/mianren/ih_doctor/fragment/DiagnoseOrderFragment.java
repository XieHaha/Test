package com.keydom.mianren.ih_doctor.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.adapter.BaseEmptyAdapter;
import com.keydom.mianren.ih_doctor.adapter.DiagnoseOrderRecyclrViewAdapter;
import com.keydom.mianren.ih_doctor.adapter.VIPDiagnoseOrderRecyclrViewAdapter;
import com.keydom.mianren.ih_doctor.bean.InquiryBean;
import com.keydom.mianren.ih_doctor.bean.MessageEvent;
import com.keydom.mianren.ih_doctor.constant.Const;
import com.keydom.mianren.ih_doctor.constant.EventType;
import com.keydom.mianren.ih_doctor.constant.TypeEnum;
import com.keydom.mianren.ih_doctor.fragment.controller.DiagnoseOrderFragmentController;
import com.keydom.mianren.ih_doctor.fragment.view.DiagnoseOrderFragmentView;
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
 * @Description：问诊订单页面
 * @Author：song
 * @Date：18/11/5 下午5:27
 * 修改人：xusong
 * 修改时间：18/11/5 下午5:27
 */
public class DiagnoseOrderFragment extends BaseControllerFragment<DiagnoseOrderFragmentController> implements DiagnoseOrderFragmentView {

    private RecyclerView recyclerView;
    /**
     * 问诊单适配器
     */
    private BaseEmptyAdapter mAdapter;
    private RefreshLayout refreshLayout;
    /**
     * 问诊单列表
     */
    private List<InquiryBean> dataList = new ArrayList<>();
    /**
     * 页面类型
     */
    private TypeEnum mType;
    /**
     * 状态
     */
    private int state = -1;


    private boolean isVIPDiag = false;


    public static final DiagnoseOrderFragment newInstance(TypeEnum type) {

        return newInstance(type, false);
    }


    public static final DiagnoseOrderFragment newInstance(TypeEnum type, boolean isVIPDiag) {
        DiagnoseOrderFragment fragment = new DiagnoseOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Const.TYPE, type);
        bundle.putBoolean(Const.IS_VIP_ONLINE_DIAG, isVIPDiag);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_diagnose_order_list_layout;
    }


    @Override
    public void getDataSuccess(TypeEnum type, List<InquiryBean> list) {
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
        map.put("currentPage", getController().getCurrentPage());
        map.put("pageSize", Const.PAGE_SIZE);
        map.put("state", state);
        return map;
    }

    @Override
    public void initData(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        mType = (TypeEnum) getArguments().getSerializable(Const.TYPE);
        if (mType == TypeEnum.ONLINE_DIAGNOSE_WAITTING) {
            state = 0;
        } else if (mType == TypeEnum.ONLINE_DIAGNOSEINT) {
            state = 1;
        } else if (mType == TypeEnum.ONLINE_DIAGNOSE_FINISH) {
            state = 2;
        }
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        if (isVIPDiag) {
            mAdapter = new VIPDiagnoseOrderRecyclrViewAdapter(getContext(), dataList);
        } else {
            mAdapter = new DiagnoseOrderRecyclrViewAdapter(getContext(), dataList);
        }
        recyclerView = getView().findViewById(R.id.common_rv);
        refreshLayout = getView().findViewById(R.id.refreshLayout);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        refreshLayout.setOnLoadMoreListener(getController());
        refreshLayout.setOnRefreshListener(getController());
        setReloadListener((v, status) -> {
            pageLoading();
            getController().setCurrentPage(1);
            getController().getData(TypeEnum.REFRESH);
        });
    }

    @Override
    public void lazyLoad() {
        pageLoading();
        getController().setCurrentPage(1);
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
        if (messageEvent.getType() == EventType.DIAGNOSE_ORDER_UPDATE) {
            getController().setCurrentPage(1);
            getController().getData(TypeEnum.REFRESH);
        }
    }
}
