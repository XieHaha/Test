package com.keydom.ih_patient.activity.order_hospital_cure;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.order_hospital_cure.controller.OrderHospitalCureListController;
import com.keydom.ih_patient.activity.order_hospital_cure.view.OrderHospitalCureListView;
import com.keydom.ih_patient.adapter.UnOrderCureAdapter;
import com.keydom.ih_patient.bean.HospitalCureInfo;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * 入院列表页面
 */
public class OrderHospitalCureListActivity extends BaseControllerActivity<OrderHospitalCureListController> implements OrderHospitalCureListView {
    /**
     * 启动
     */
    public static void start(Context context){
        context.startActivity(new Intent(context,OrderHospitalCureListActivity.class));
    }
    private SmartRefreshLayout order_cure_refresh;
    private RecyclerView order_cure_rv;
    private UnOrderCureAdapter unOrderCureAdapter;
    private List<HospitalCureInfo> dataList=new ArrayList<>();
    @Override
    public int getLayoutRes() {
        return R.layout.activity_hospital_cure_list_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("入院证列表");
        order_cure_refresh=this.findViewById(R.id.order_cure_refresh);
        order_cure_refresh.setOnRefreshListener(getController());
        order_cure_rv=this.findViewById(R.id.order_cure_rv);
        unOrderCureAdapter=new UnOrderCureAdapter(getContext(),dataList);
        order_cure_rv.setAdapter(unOrderCureAdapter);
        order_cure_refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                getController().queryAdmissionCardList();
            }
        });
    }

    @Override
    protected void onResume() {
        getController().queryAdmissionCardList();
        super.onResume();
    }

    @Override
    public void fillHospitalCureList(List<HospitalCureInfo> dataList) {
        this.dataList.clear();
        this.dataList.addAll(dataList);
        unOrderCureAdapter.notifyDataSetChanged();
        order_cure_refresh.finishRefresh();
    }

    @Override
    public void fillHospitalCureListFailed(String errMsg) {
        order_cure_refresh.finishRefresh();
    }
}
