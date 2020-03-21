package com.keydom.mianren.ih_doctor.activity.online_triage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.online_triage.controller.TriageOrderListController;
import com.keydom.mianren.ih_doctor.activity.online_triage.view.TriageOrderListView;
import com.keydom.mianren.ih_doctor.adapter.TriageOrderAdapter;
import com.keydom.mianren.ih_doctor.constant.TypeEnum;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @date 20/3/21 13:13
 * @des 分诊订单列表
 */
public class TriageOrderListActivity extends BaseControllerActivity<TriageOrderListController> implements TriageOrderListView {
    @BindView(R.id.triage_order_recycler_view)
    RecyclerView triageOrderRecyclerView;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefreshLayout;

    private TriageOrderAdapter adapter;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_triage_order_list;
    }


    @Override
    protected void onResume() {
        super.onResume();
        smartRefreshLayout.autoRefresh();
    }

    /**
     * 启动
     */
    public static void start(Context context) {
        context.startActivity(new Intent(context, TriageOrderListActivity.class));
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_triage_receive));
        adapter = new TriageOrderAdapter(R.layout.item_triage_order, new ArrayList<>());
        adapter.setOnItemClickListener(getController());
        triageOrderRecyclerView.setAdapter(adapter);

        smartRefreshLayout.setOnRefreshListener(refreshLayout -> getController().getTriageOrderList(TypeEnum.REFRESH));
    }

    @Override
    public void requestSuccess(List<String> list, TypeEnum typeEnum) {
        smartRefreshLayout.finishLoadMore();
        smartRefreshLayout.finishRefresh();
        if (typeEnum == TypeEnum.REFRESH) {
            adapter.replaceData(list);
        } else {
            adapter.addData(list);
        }
        getController().currentPagePlus();

    }

    @Override
    public void requestFailed(String msg) {
        smartRefreshLayout.finishLoadMore();
        smartRefreshLayout.finishRefresh();
        ToastUtil.showMessage(this, msg);
    }
}
