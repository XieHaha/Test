package com.keydom.mianren.ih_doctor.activity.consulting_arrange;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ganxin.library.LoadDataLayout;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.consulting_arrange.controller.ArrangeCircleController;
import com.keydom.mianren.ih_doctor.activity.consulting_arrange.view.ArrangeCircleView;
import com.keydom.mianren.ih_doctor.adapter.ConsultingCircleWithStopAdapter;
import com.keydom.mianren.ih_doctor.bean.ConsultingBean;
import com.keydom.mianren.ih_doctor.constant.Const;
import com.keydom.mianren.ih_doctor.constant.TypeEnum;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.activity
 * @Description：循环排班、停诊共用
 * @Author：song
 * @Date：18/11/19 上午11:34
 * 修改人：xusong
 * 修改时间：18/11/19 上午11:34
 */
public class ArrangeCircleActivity extends BaseControllerActivity<ArrangeCircleController> implements ArrangeCircleView {

    /**
     * 停诊列表
     */
    public static final int CONSULTING_STOP = 400;
    /**
     * 循环排班列表
     */
    public static final int CONSULTING_CIRCLE = 401;

    /**
     * 循环排班标题
     */
    public static final String CONSULTING_CIRCLE_TITLE = "循环排班";

    /**
     * 停诊标题
     */
    public static final String CONSULTING_STOP_TITLE = "停诊";

    /**
     * 右侧标题
     */
    public static final String RIGHT_BUTTON_TXT = "新增";

    /**
     * 判断是停诊列表还是循环排班列表
     */
    private int type;
    /**
     * 列表数据
     */
    private List<ConsultingBean> dataList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ConsultingCircleWithStopAdapter consultingCircleWithStopAdapter;
    private RefreshLayout refreshLayout;

    /**
     * 启动循环排班列表页面
     *
     * @param context
     */
    public static void startConsultingCircle(Context context) {
        Intent starter = new Intent(context, ArrangeCircleActivity.class);
        starter.putExtra(Const.TYPE, CONSULTING_CIRCLE);
        context.startActivity(starter);
    }

    /**
     * 启动停诊列表页面
     *
     * @param context
     */
    public static void startConsultingStop(Context context) {
        Intent starter = new Intent(context, ArrangeCircleActivity.class);
        starter.putExtra(Const.TYPE, CONSULTING_STOP);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_arrange_circle;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        type = getIntent().getIntExtra(Const.TYPE, Const.INT_DEFAULT);
        refreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);
        if (type == CONSULTING_STOP) {
            setTitle(CONSULTING_STOP_TITLE);
        } else {
            setTitle(CONSULTING_CIRCLE_TITLE);
            refreshLayout.setEnableLoadMore(false);
            refreshLayout.setEnableRefresh(false);
        }
        setRightTxt(RIGHT_BUTTON_TXT);
        setRightBtnListener(new IhTitleLayout.OnRightTextClickListener() {
            @Override
            public void OnRightTextClick(View v) {
                ConsultingChangeActivity.start(ArrangeCircleActivity.this, type, dataList);
            }
        });
        recyclerView = this.findViewById(R.id.consulting_list_rv);
        consultingCircleWithStopAdapter = new ConsultingCircleWithStopAdapter(dataList, this);
        recyclerView.setAdapter(consultingCircleWithStopAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        refreshLayout.setOnRefreshListener(getController());
        refreshLayout.setOnLoadMoreListener(getController());
        pageLoading();
        getController().getData();
        setReloadListener(new LoadDataLayout.OnReloadListener() {
            @Override
            public void onReload(View v, int status) {
                pageLoading();
                getController().getData();
            }
        });
    }

    @Override
    public void getConsultingSuccess(TypeEnum type, List<ConsultingBean> list) {
        if (type == TypeEnum.REFRESH) {
            dataList.clear();
        }
        pageLoadingSuccess();
        dataList.addAll(list);
        consultingCircleWithStopAdapter.notifyDataSetChanged();
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();

    }

    @Override
    public void getConsultingFailed(String errMsg) {
        pageLoadingFail();
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Const.TYPE_ACTIVITY_REFRESH) {
            dataList.clear();
            getController().setDefaultPage();
            getController().getData();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
