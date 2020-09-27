package com.keydom.mianren.ih_patient.activity.child_health;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.child_health.controller.ChildHealthHistoryController;
import com.keydom.mianren.ih_patient.activity.child_health.view.ChildHealthHistoryView;
import com.keydom.mianren.ih_patient.adapter.ChildHealthHistoryAdapter;
import com.keydom.mianren.ih_patient.bean.ChildHealthDoingBean;
import com.keydom.mianren.ih_patient.bean.MedicalCardInfo;
import com.keydom.mianren.ih_patient.constant.Const;

import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;

/**
 * @author 顿顿
 * @date 20/2/27 11:37
 * @des 儿童保健历史记录
 */
public class ChildHealthHistoryActivity extends BaseControllerActivity<ChildHealthHistoryController> implements ChildHealthHistoryView {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private ChildHealthHistoryAdapter historyAdapter;

    private List<ChildHealthDoingBean> healthHistoryBeans;

    private MedicalCardInfo cardInfo;

    /**
     * 启动
     */
    public static void start(Context context, MedicalCardInfo cardInfo,
                             List<ChildHealthDoingBean> healthHistoryBeans) {
        Intent intent = new Intent(context, ChildHealthHistoryActivity.class);
        intent.putExtra(Const.DATA, cardInfo);
        intent.putExtra("list", (Serializable) healthHistoryBeans);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_child_health_history;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("儿保预约历史");
        cardInfo = (MedicalCardInfo) getIntent().getSerializableExtra(Const.DATA);
        healthHistoryBeans = (List<ChildHealthDoingBean>) getIntent().getSerializableExtra("list");

        historyAdapter = new ChildHealthHistoryAdapter(healthHistoryBeans);
        historyAdapter.setOnItemClickListener(getController());
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(historyAdapter);
    }

    @Override
    public MedicalCardInfo getCardInfo() {
        return cardInfo;
    }

}
