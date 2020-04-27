package com.keydom.mianren.ih_doctor.activity.online_triage.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.ih_common.bean.TriageBean;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.online_triage.controller.TriageOrderFragmentController;
import com.keydom.mianren.ih_doctor.activity.online_triage.view.TriageOrderFragmentView;
import com.keydom.mianren.ih_doctor.adapter.TriageOrderAdapter;
import com.keydom.mianren.ih_doctor.bean.MessageEvent;
import com.keydom.mianren.ih_doctor.constant.Const;
import com.keydom.mianren.ih_doctor.constant.EventType;
import com.keydom.mianren.ih_doctor.constant.TypeEnum;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * @date 4月2日 15:03
 * 分诊列表
 */
public class TriageOrderFragment extends BaseControllerFragment<TriageOrderFragmentController> implements TriageOrderFragmentView {
    @BindView(R.id.empty_layout)
    RelativeLayout emptyLayout;
    @BindView(R.id.triage_order_recycler_view)
    RecyclerView triageOrderRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    /**
     * 分诊单适配器
     */
    private TriageOrderAdapter mAdapter;
    /**
     * 分诊单列表
     */
    private ArrayList<TriageBean> dataList = new ArrayList<>();
    /**
     * 页面类型
     */
    private TypeEnum mType;
    /**
     * 状态
     */
    private int status = -1;


    public static TriageOrderFragment newInstance(TypeEnum type) {
        TriageOrderFragment fragment = new TriageOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Const.TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_triage_order;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mType = (TypeEnum) getArguments().getSerializable(Const.TYPE);
        if (mType == TypeEnum.TRIAGE_WAIT) {
            status = 0;
        } else if (mType == TypeEnum.TRIAGE_RECEIVED) {
            status = 1;
        }
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        //模拟数据
        mAdapter = new TriageOrderAdapter(dataList);
        mAdapter.setOnItemClickListener(getController());
        mAdapter.setStatus(status);
        triageOrderRecyclerView.setAdapter(mAdapter);
        triageOrderRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        refreshLayout.setOnLoadMoreListener(refreshLayout -> getController().getTriageOrderList(TypeEnum.LOAD_MORE));
        refreshLayout.setOnRefreshListener(refreshLayout -> getController().getTriageOrderList(TypeEnum.REFRESH));
        setReloadListener((v, status) -> {
            pageLoading();
            getController().getTriageOrderList(TypeEnum.REFRESH);
        });
    }

    @Override
    public void getDataSuccess(TypeEnum type, List<TriageBean> list) {
        if (list.size() < Const.PAGE_SIZE) {
            refreshLayout.finishLoadMoreWithNoMoreData();
        } else {
            refreshLayout.finishLoadMore();
        }
        getController().currentPagePlus();
        if (type == TypeEnum.REFRESH) {
            dataList.clear();
        }
        dataList.addAll(list);
        mAdapter.notifyDataSetChanged();
        refreshLayout.finishRefresh();
        pageLoadingSuccess();
        if (dataList.size() == 0) {
            emptyLayout.setVisibility(View.VISIBLE);
        } else {
            emptyLayout.setVisibility(View.GONE);
        }
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
    public Map<String, Object> getParamsMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("currentPage", getController().getCurrentPage());
        map.put("pageSize", Const.PAGE_SIZE);
        map.put("state", status);
        return map;
    }

    @Override
    public void lazyLoad() {
        pageLoading();
        getController().getTriageOrderList(TypeEnum.REFRESH);
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
            getController().getTriageOrderList(TypeEnum.REFRESH);
        }
    }
}
