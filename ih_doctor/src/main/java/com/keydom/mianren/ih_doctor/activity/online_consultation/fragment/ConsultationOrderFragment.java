package com.keydom.mianren.ih_doctor.activity.online_consultation.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.online_consultation.adapter.ConsultationOrderAdapter;
import com.keydom.mianren.ih_doctor.activity.online_consultation.controller.ConsultationOrderFragmentController;
import com.keydom.mianren.ih_doctor.activity.online_consultation.view.ConsultationOrderFragmentView;
import com.keydom.mianren.ih_doctor.bean.ConsultationBean;
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
 * 会诊列表
 */
public class ConsultationOrderFragment extends BaseControllerFragment<ConsultationOrderFragmentController> implements ConsultationOrderFragmentView {
    @BindView(R.id.empty_layout)
    RelativeLayout emptyLayout;
    @BindView(R.id.consultation_order_recycler_view)
    RecyclerView consultationOrderRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    /**
     * 会诊单适配器
     */
    private ConsultationOrderAdapter mAdapter;
    /**
     * 会诊单列表
     */
    private List<ConsultationBean> dataList = new ArrayList<>();
    /**
     * 页面类型
     */
    private TypeEnum mType;
    /**
     * 状态
     */
    private int status = -1;

    /**
     * 患者id
     */
    private long patientId;


    public static ConsultationOrderFragment newInstance(TypeEnum type) {
        return newInstance(type, 0);
    }

    public static ConsultationOrderFragment newInstance(TypeEnum type, long patientId) {
        ConsultationOrderFragment fragment = new ConsultationOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Const.TYPE, type);
        bundle.putLong(Const.PATIENT_ID, patientId);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public int getLayoutRes() {
        return R.layout.fragment_consultation_order;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mType = (TypeEnum) getArguments().getSerializable(Const.TYPE);
        patientId = getArguments().getLong(Const.PATIENT_ID);
        if (mType == TypeEnum.CONSULTATION_WAIT) {
            status = 0;
            //        } else if (mType == TypeEnum.CONSULTATION_ING) {
            //            status = 1;
        } else if (mType == TypeEnum.CONSULTATION_COMPLETE) {
            status = 1;
        }
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        mAdapter = new ConsultationOrderAdapter(dataList);
        mAdapter.setOnItemClickListener(getController());
        consultationOrderRecyclerView.setAdapter(mAdapter);
        consultationOrderRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        refreshLayout.setOnRefreshListener(refreshLayout -> getController().getConsultationOrderList(TypeEnum.REFRESH));
        refreshLayout.setOnLoadMoreListener(refreshLayout -> getController().getConsultationOrderList(TypeEnum.LOAD_MORE));
        setReloadListener((v, status) -> {
            pageLoading();
            getController().getConsultationOrderList(TypeEnum.REFRESH);
        });
    }


    @Override
    public void getDataSuccess(TypeEnum type, List<ConsultationBean> list) {
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
    public Map<String, Object> getListMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("currentPage", getController().getCurrentPage());
        map.put("pageSize", Const.PAGE_SIZE);
        map.put("status", status);
        if (patientId != 0) {
            map.put("patientId", patientId);
        }
        return map;
    }

    @Override
    public void lazyLoad() {
        pageLoading();
        getController().getConsultationOrderList(TypeEnum.REFRESH);
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
            getController().getConsultationOrderList(TypeEnum.REFRESH);
        }
    }
}
