package com.keydom.ih_patient.activity.payment_records;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.payment_records.controller.PaiedRecordController;
import com.keydom.ih_patient.activity.payment_records.view.PaiedRecordView;
import com.keydom.ih_patient.adapter.PayRecordAdapter;
import com.keydom.ih_patient.bean.Event;
import com.keydom.ih_patient.bean.PayRecordBean;
import com.keydom.ih_patient.constant.EventType;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.Subscribe;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * 已缴费页面
 */
public class PaiedRecordFragment extends BaseControllerFragment<PaiedRecordController> implements PaiedRecordView {
    public static final String STATE = "state";

    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mRefreshLayout;

    private PayRecordAdapter mPayRecordAdapter;
    /**
     * 缴费状态
     */
    private int mState;

    /**
     * fragment创建
     */
    public static PaiedRecordFragment newInstance(int state) {
        Bundle args = new Bundle();
        args.putInt(STATE,state);
        PaiedRecordFragment fragment = new PaiedRecordFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_paied_record_layout;
    }

    @Override
    public void getView(@Nullable View view) {
        assert view != null;
        mRecyclerView=view.findViewById(R.id.paied_record_rv);
        mRefreshLayout=view.findViewById(R.id.paied_record_refresh);
    }

    @Subscribe
    public void paymenSuccess(Event event){
        if (event.getType() == EventType.PAYMENT_SUCCESS && mState == PaymentRecordActivity.PAYED){
            mRefreshLayout.autoRefresh();
        }
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        assert getArguments() != null;
        mState = getArguments().getInt(STATE);
        mPayRecordAdapter = new PayRecordAdapter(new ArrayList<>());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mPayRecordAdapter);
        mPayRecordAdapter.setOnItemClickListener((adapter, view, position) -> {
            PayRecordBean payRecordBean = (PayRecordBean) adapter.getData().get(position);
            String docNum = payRecordBean.getDocumentNo();
            Intent i = new Intent(getActivity(),PaymentDetailActivity.class);
            i.putExtra(PaymentDetailActivity.DOCUMENT_NO,docNum);
            ActivityUtils.startActivity(i);
        });

        mRefreshLayout.setOnRefreshListener(refreshLayout -> getController().getConsultationPayList(mRefreshLayout,mState));
        getController().getConsultationPayList(mRefreshLayout,mState);
    }

    @Override
    public void paymentListCallBack(List<PayRecordBean> list) {
        if (mRefreshLayout.isRefreshing()){
            mRefreshLayout.finishRefresh();
        }
        mPayRecordAdapter.setNewData(list);
    }
}
