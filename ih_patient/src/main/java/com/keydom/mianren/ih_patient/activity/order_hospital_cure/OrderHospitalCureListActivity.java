package com.keydom.mianren.ih_patient.activity.order_hospital_cure;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.login.LoginActivity;
import com.keydom.mianren.ih_patient.activity.order_hospital_cure.controller.OrderHospitalCureListController;
import com.keydom.mianren.ih_patient.activity.order_hospital_cure.view.OrderHospitalCureListView;
import com.keydom.mianren.ih_patient.adapter.UnOrderCureAdapter;
import com.keydom.mianren.ih_patient.bean.HospitalCureInfo;
import com.keydom.mianren.ih_patient.constant.Global;
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
        if (Global.getUserId() == -1) {
            new GeneralDialog(getContext(), "该功能需要登录才能使用，是否立即登录？", new GeneralDialog.OnCloseListener() {
                @Override
                public void onCommit() {
                    LoginActivity.start(getContext());
                }
            }, new GeneralDialog.CancelListener() {
                @Override
                public void onCancel() {
                    finish();
                }
            }).setTitle("提示").setCancel(false).setPositiveButton("登录").show();
        }
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
