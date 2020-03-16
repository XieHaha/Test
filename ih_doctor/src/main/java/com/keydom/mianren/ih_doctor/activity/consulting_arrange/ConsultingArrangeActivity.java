package com.keydom.mianren.ih_doctor.activity.consulting_arrange;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.ganxin.library.LoadDataLayout;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.consulting_arrange.controller.ConsultingArrangeController;
import com.keydom.mianren.ih_doctor.activity.consulting_arrange.view.ConsultingArrangeView;
import com.keydom.mianren.ih_doctor.adapter.ConsultingRecyclrViewAdapter;
import com.keydom.mianren.ih_doctor.bean.ConsultingBean;
import com.keydom.mianren.ih_doctor.bean.MessageEvent;
import com.keydom.mianren.ih_doctor.constant.Const;
import com.keydom.mianren.ih_doctor.constant.EventType;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.activity
 * @Description：门诊排班主页
 * @Author：song
 * @Date：18/11/19 上午11:34
 * 修改人：xusong
 * 修改时间：18/11/19 上午11:34
 */
public class ConsultingArrangeActivity extends BaseControllerActivity<ConsultingArrangeController> implements ConsultingArrangeView {

    private RecyclerView recyclerView;
    private RelativeLayout consultingCircleRl;
    private RelativeLayout consultingStopRl;
    private RefreshLayout refreshLayout;

    /**
     * 门诊排班列表适配器
     */
    private ConsultingRecyclrViewAdapter consultingRecyclrViewAdapter;

    /**
     * 门诊排班列表数据
     */
    private List<ConsultingBean> dataList = new ArrayList<>();

    /**
     * 启动门诊排班主页
     *
     * @param context
     */
    public static void start(Context context) {
        Intent starter = new Intent(context, ConsultingArrangeActivity.class);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_consulting_arrange;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        setTitle("门诊排班");
        refreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);
        recyclerView = this.findViewById(R.id.consulting_item_rv);
        consultingCircleRl = this.findViewById(R.id.consulting_circle_rl);
        consultingStopRl = this.findViewById(R.id.consulting_stop_rl);
        consultingRecyclrViewAdapter = new ConsultingRecyclrViewAdapter(dataList, this);
        recyclerView.setAdapter(consultingRecyclrViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        consultingStopRl.setOnClickListener(getController());
        consultingCircleRl.setOnClickListener(getController());
        refreshLayout.setOnRefreshListener(getController());
        refreshLayout.setEnableLoadMore(false);
        pageLoading();
        getController().getConsultingList();
        setReloadListener(new LoadDataLayout.OnReloadListener() {
            @Override
            public void onReload(View v, int status) {
                pageLoading();
                getController().getConsultingList();
            }
        });
    }

    @Override
    public void getConsultingSuccess(List<ConsultingBean> list) {
        pageLoadingSuccess();
        dataList.clear();
        dataList.addAll(list);
        consultingRecyclrViewAdapter.notifyDataSetChanged();
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void getConsultingFailed(String msg) {
        pageLoadingFail();
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Const.TYPE_ACTIVITY_REFRESH) {
            dataList.clear();
            getController().getConsultingList();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        if (messageEvent.getType() == EventType.CONSULTING_UPDATE) {
            getController().getConsultingList();
        }
    }
}
