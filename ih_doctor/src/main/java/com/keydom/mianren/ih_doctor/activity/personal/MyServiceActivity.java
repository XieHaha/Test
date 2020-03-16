package com.keydom.mianren.ih_doctor.activity.personal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.ganxin.library.LoadDataLayout;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.AgreementActivity;
import com.keydom.mianren.ih_doctor.activity.MainActivity;
import com.keydom.mianren.ih_doctor.activity.personal.controller.MyServiceController;
import com.keydom.mianren.ih_doctor.activity.personal.view.MyServiceView;
import com.keydom.mianren.ih_doctor.adapter.ServiceRecyclrViewAdapter;
import com.keydom.mianren.ih_doctor.bean.MessageEvent;
import com.keydom.mianren.ih_doctor.bean.ServiceContentBean;
import com.keydom.mianren.ih_doctor.constant.EventType;
import com.keydom.mianren.ih_doctor.m_interface.SingleClick;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.activity.personal
 * @Description：我的服务页面
 * @Author：song
 * @Date：18/11/14 下午3:05
 * 修改人：xusong
 * 修改时间：18/11/14 下午3:05
 */
public class MyServiceActivity extends BaseControllerActivity<MyServiceController> implements MyServiceView {

    private RecyclerView articleListRv;
    private RefreshLayout refreshLayout;
    private ServiceRecyclrViewAdapter mAdapter;
    private boolean isFirstLogin=false;
    /**
     * 我的服务对象列表
     */
    private List<MultiItemEntity> dataList = new ArrayList<>();

    /**
     * 启动
     */
    public static void start(Context context,boolean isFirstLogin) {
        Intent starter = new Intent(context, MyServiceActivity.class);
        starter.putExtra("isFirstLogin",isFirstLogin);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_article_list_layout;
    }


    @Override
    public void initData(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        setTitle("我的服务");
        isFirstLogin=getIntent().getBooleanExtra("isFirstLogin",false);
        if(isFirstLogin){
            setRightTxt("完成");
            setRightBtnListener(new IhTitleLayout.OnRightTextClickListener() {
                @Override
                public void OnRightTextClick(View v) {
                    MainActivity.start(getContext(),false,false);
                }
            });
        }
        EventBus.getDefault().register(this);
        articleListRv = (RecyclerView) this.findViewById(R.id.article_list_rv);
        refreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);
        mAdapter = new ServiceRecyclrViewAdapter(null);
        articleListRv.setAdapter(mAdapter);
        articleListRv.setLayoutManager(new LinearLayoutManager(this));
        articleListRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        refreshLayout.setOnRefreshListener(getController());
//        refreshLayout.setOnLoadMoreListener(getController());
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setEnableLoadMore(false);
        pageLoading();
        getController().getService();
        setReloadListener(new LoadDataLayout.OnReloadListener() {
            @Override
            public void onReload(View v, int status) {
                pageLoading();
                getController().getService();
            }
        });
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @SingleClick(1000)
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MultiItemEntity entity = (MultiItemEntity) adapter.getData().get(position);
                if (entity.getItemType() == ServiceRecyclrViewAdapter.TYPE_CONTENT){
                    ServiceContentBean contentBean = (ServiceContentBean) entity;
                    if (contentBean.getSubState()== ServiceContentBean.OPEN) {
                        new GeneralDialog(getContext(), "确定关闭该服务?", new GeneralDialog.OnCloseListener() {
                            @Override
                            public void onCommit() {
                                getController().stopService(contentBean.getSubId());
                            }
                        }).show();
                    } else {
                        new GeneralDialog(getContext(), "确定开启该服务?", new GeneralDialog.OnCloseListener() {
                            @Override
                            public void onCommit() {
                                AgreementActivity.startSingleService(getContext(),contentBean.getSubId());
                            }
                        }).show();
                    }
                }
            }
        });

    }

    @Override
    public void getServiceSuccess(List<MultiItemEntity> list) {
        dataList.clear();
        dataList.addAll(list);
        mAdapter.setNewData(list);
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
        pageLoadingSuccess();

    }

    @Override
    public void getServiceFailed(String errMsg) {
        pageLoadingFail();
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
    }

    @Override
    public void enableServiceSuccess(String msg) {
        getController().getService();
        EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.UPDATE_USER_INFO).build());
    }

    @Override
    public void enableServiceFailed(String errMsg) {
        ToastUtil.showMessage(this, errMsg);
    }

    @Override
    public void disableServcieSuccess(String msg) {
        getController().getService();
        EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.UPDATE_USER_INFO).build());
    }

    @Override
    public void disableServiceFailed(String errMsg) {
        ToastUtil.showMessage(this, errMsg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        if (messageEvent.getType() == EventType.UPDATE_MY_SERVICE) {
            getController().getService();
        }
    }
}
