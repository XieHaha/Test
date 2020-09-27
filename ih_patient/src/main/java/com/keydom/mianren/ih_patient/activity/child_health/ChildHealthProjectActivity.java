package com.keydom.mianren.ih_patient.activity.child_health;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.child_health.controller.ChildHealthProjectController;
import com.keydom.mianren.ih_patient.activity.child_health.view.ChildHealthProjectView;
import com.keydom.mianren.ih_patient.adapter.ChildHealthProjectAdapter;
import com.keydom.mianren.ih_patient.bean.ChildHealthProjectBean;
import com.keydom.mianren.ih_patient.bean.Event;
import com.keydom.mianren.ih_patient.bean.MedicalCardInfo;
import com.keydom.mianren.ih_patient.constant.Const;
import com.keydom.mianren.ih_patient.constant.EventType;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author 顿顿
 * @date 20/2/27 11:37
 * @des 儿童保健项目列表
 */
public class ChildHealthProjectActivity extends BaseControllerActivity<ChildHealthProjectController> implements ChildHealthProjectView {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;

    private ChildHealthProjectAdapter projectAdapter;

    private List<ChildHealthProjectBean> projectBeans;

    private MedicalCardInfo cardInfo;

    /**
     * 启动
     */
    public static void start(Context context, MedicalCardInfo cardInfo) {
        Intent intent = new Intent(context, ChildHealthProjectActivity.class);
        intent.putExtra(Const.DATA, cardInfo);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_child_health_project;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("儿保月龄项目");
        EventBus.getDefault().register(this);
        cardInfo = (MedicalCardInfo) getIntent().getSerializableExtra(Const.DATA);
        projectAdapter = new ChildHealthProjectAdapter(projectBeans);
        projectAdapter.setOnItemClickListener(getController());
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(projectAdapter);
        smartRefresh.setOnRefreshListener(refreshLayout -> getController().childProjectList());

        getController().childProjectList();
    }

    @Override
    public MedicalCardInfo getCardInfo() {
        return cardInfo;
    }

    @Override
    public void requestProjectSuccess(List<ChildHealthProjectBean> data) {
        smartRefresh.finishRefresh();
        projectBeans = data;
        if (projectBeans != null) {
            projectAdapter.setNewData(projectBeans);
        } else {
            projectAdapter.setNewData(new ArrayList<>());
        }
    }

    @Override
    public void requestProjectFailed(String msg) {
        smartRefresh.finishRefresh();
        ToastUtil.showMessage(this, msg);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChileHealthUpdate(Event event) {
        if (event.getType() == EventType.CHILD_HEALTH_APPLY) {
            ToastUtil.showMessage(this, "操作成功，等待医生开具儿保医嘱");
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
