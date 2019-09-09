package com.keydom.ih_doctor.activity.online_diagnose;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ganxin.library.LoadDataLayout;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.online_diagnose.controller.DiagnoseOrderSelectController;
import com.keydom.ih_doctor.activity.online_diagnose.view.DiagnoseOrderSelectView;
import com.keydom.ih_doctor.adapter.DiagnoseOrderSelectRecyclrViewAdapter;
import com.keydom.ih_doctor.bean.InquiryBean;
import com.keydom.ih_doctor.constant.Const;
import com.keydom.ih_doctor.constant.TypeEnum;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.activity.personal
 * @Description：转诊选择问诊订单页面
 * @Author：song
 * @Date：18/11/14 上午10:37
 * 修改人：xusong
 * 修改时间：18/11/14 上午10:37
 */
public class DiagnoseOrderSelectActivity extends BaseControllerActivity<DiagnoseOrderSelectController> implements DiagnoseOrderSelectView {

    private RecyclerView articleListRv;
    private RefreshLayout refreshLayout;
    /**
     * 问诊单列表
     */
    private List<InquiryBean> mlist = new ArrayList<>();
    /**
     * 问诊单列表适配器
     */
    private DiagnoseOrderSelectRecyclrViewAdapter diagnoseOrderSelectRecyclrViewAdapter;

    //doctorType  0都没开通，1开通图文，2开通视频，3开通视频和图文
    private int doctorType = 3;

    /**
     * 启动问诊单选择页面
     *
     * @param context
     */
    public static void start(Context context) {
        Intent starter = new Intent(context, DiagnoseOrderSelectActivity.class);
        ((Activity) context).startActivityForResult(starter, Const.DIAGNOSE_ORDER_SELECT);
    }

    public static void start(Context context, int doctorType) {
        Intent starter = new Intent(context, DiagnoseOrderSelectActivity.class);
        starter.putExtra("doctorType", doctorType);
        ((Activity) context).startActivityForResult(starter, Const.DIAGNOSE_ORDER_SELECT);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_article_list_layout;
    }

    @Override
    public void initData(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        articleListRv = (RecyclerView) this.findViewById(R.id.article_list_rv);
        refreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);
        doctorType = getIntent().getIntExtra("doctorType", 3);
        diagnoseOrderSelectRecyclrViewAdapter = new DiagnoseOrderSelectRecyclrViewAdapter(this, mlist,doctorType);
        articleListRv.setAdapter(diagnoseOrderSelectRecyclrViewAdapter);
        articleListRv.setLayoutManager(new LinearLayoutManager(this));
        articleListRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        refreshLayout.setOnRefreshListener(getController());
        refreshLayout.setOnLoadMoreListener(getController());
        pageLoading();
        getController().getOrder(TypeEnum.REFRESH);
        setTitle("选择问诊单");
        refreshLayout.setEnableLoadMore(false);
        setReloadListener(new LoadDataLayout.OnReloadListener() {
            @Override
            public void onReload(View v, int status) {
                pageLoading();
                getController().getOrder(TypeEnum.REFRESH);
            }
        });

    }

    @Override
    public void getOrderSuccess(TypeEnum type, List<InquiryBean> list) {
        if (type == TypeEnum.REFRESH) {
            this.mlist.clear();
        }
        pageLoadingSuccess();
        this.mlist.addAll(list);
        diagnoseOrderSelectRecyclrViewAdapter.notifyDataSetChanged();
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
    }

    @Override
    public void getOrderFailed(String errMsg) {
        pageLoadingFail();
    }

}
