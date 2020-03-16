package com.keydom.mianren.ih_doctor.activity.personal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ganxin.library.LoadDataLayout;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.personal.controller.MyAttentionController;
import com.keydom.mianren.ih_doctor.activity.personal.view.MyAttentionView;
import com.keydom.mianren.ih_doctor.adapter.AttentionRecyclrViewAdapter;
import com.keydom.mianren.ih_doctor.bean.AttentionBean;
import com.keydom.mianren.ih_doctor.constant.TypeEnum;
import com.keydom.mianren.ih_doctor.view.SwipeItemLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.activity.personal
 * @Description：我的关注页面
 * @Author：song
 * @Date：18/11/14 下午3:05
 * 修改人：xusong
 * 修改时间：18/11/14 下午3:05
 */
public class MyAttentionActivity extends BaseControllerActivity<MyAttentionController> implements MyAttentionView {

    /**
     * 我的关注对象列表
     */
    private List<AttentionBean> list = new ArrayList<>();
    private RecyclerView recyclerView;
    /**
     * 我的关注列表适配器
     */
    private AttentionRecyclrViewAdapter attentionRecyclrViewAdapter;
    private RefreshLayout refreshLayout;
    private EditText searchInputEv;
    private TextView searchTv;

    /**
     * 启动我的文章页面
     */
    public static void start(Context context) {
        Intent starter = new Intent(context, MyAttentionActivity.class);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_attention_layout;
    }


    @Override
    public void initData(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        setTitle("我的关注");
        refreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);
        searchInputEv = (EditText) findViewById(R.id.search_input_ev);
        searchTv = (TextView) findViewById(R.id.attention_search_tv);
        searchTv.setOnClickListener(getController());
        attentionRecyclrViewAdapter = new AttentionRecyclrViewAdapter(list, this);
        recyclerView = (RecyclerView) this.findViewById(R.id.attention_list_rv);
        recyclerView.setAdapter(attentionRecyclrViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        refreshLayout.setOnRefreshListener(getController());
        refreshLayout.setOnLoadMoreListener(getController());
        pageLoading();
        getController().getAttention(getSearchStr(), TypeEnum.REFRESH);
        setReloadListener(new LoadDataLayout.OnReloadListener() {
            @Override
            public void onReload(View v, int status) {
                pageLoading();
                getController().getAttention(getSearchStr(), TypeEnum.REFRESH);
            }
        });
    }

    @Override
    public void getAttentionSuccess(ArrayList<AttentionBean> attentionBeans, TypeEnum type) {
        if (type == TypeEnum.REFRESH) {
            list.clear();
        }
        list.addAll(attentionBeans);
        attentionRecyclrViewAdapter.notifyDataSetChanged();
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
        getController().currentPagePlus();
        pageLoadingSuccess();
    }

    @Override
    public void getAttentionFailed(String errMsg) {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
        pageLoadingFail();
    }

    @Override
    public void cancelSuccess(int position, String msg) {
        list.remove(position);
        attentionRecyclrViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void cancelFailed(String errMsg) {

    }

    @Override
    public String getSearchStr() {
        return searchInputEv.getText().toString().trim();
    }


    @Override
    public RefreshLayout getRefreshLayout() {
        return refreshLayout;
    }
}
