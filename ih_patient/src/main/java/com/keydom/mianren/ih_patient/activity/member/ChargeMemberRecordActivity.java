package com.keydom.mianren.ih_patient.activity.member;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.member.controller.ChargeMemberRecordController;
import com.keydom.mianren.ih_patient.activity.member.view.ChargeMemberRecordView;
import com.keydom.mianren.ih_patient.adapter.ChargeMemberRecordAdapter;
import com.keydom.mianren.ih_patient.bean.RenewalRecordItem;
import com.keydom.mianren.ih_patient.constant.TypeEnum;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ChargeMemberRecordActivity extends BaseControllerActivity<ChargeMemberRecordController> implements ChargeMemberRecordView {


    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mRefreshLayout;

    private ChargeMemberRecordAdapter mChargeMemberRecordAdapter;

    /**
     * 启动
     */
    public static void start(Context context) {
        Intent intent = new Intent(context, ChargeMemberRecordActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_charge_member_record;
    }

    @Override
    public void beforeInit() {

    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        getTitleLayout().initViewsVisible(true,true,false);
        setTitle("充值记录");

        mRecyclerView=findViewById(R.id.paied_record_rv);
        mRefreshLayout=findViewById(R.id.paied_record_refresh);

        mChargeMemberRecordAdapter = new ChargeMemberRecordAdapter(new ArrayList<>());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mChargeMemberRecordAdapter);

        mRefreshLayout.setOnRefreshListener(refreshLayout -> getController().getRenewalRecord(mRefreshLayout,1, TypeEnum.REFRESH));
        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> getController().getRenewalRecord(mRefreshLayout,1,TypeEnum.LOAD_MORE));
        mRefreshLayout.autoRefresh();
    }

    @Override
    public void paymentListCallBack(List<RenewalRecordItem> list, TypeEnum typeEnum) {
        mRefreshLayout.finishLoadMore();
        mRefreshLayout.finishRefresh();
        pageLoadingSuccess();
        if (typeEnum == TypeEnum.REFRESH) {
            mChargeMemberRecordAdapter.replaceData(list);
        }else{
            mChargeMemberRecordAdapter.addData(list);
        }
        getController().currentPagePlus();
    }
}
