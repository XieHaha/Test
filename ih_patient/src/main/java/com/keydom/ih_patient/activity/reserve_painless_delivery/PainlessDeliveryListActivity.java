package com.keydom.ih_patient.activity.reserve_painless_delivery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.reserve_painless_delivery.controller.PainlessDeliveryListController;
import com.keydom.ih_patient.activity.reserve_painless_delivery.view.PainlessDeliveryListView;
import com.keydom.ih_patient.adapter.PainlessDeliveryAdapter;
import com.keydom.ih_patient.bean.PainlessDeliveryBean;
import com.keydom.ih_patient.constant.TypeEnum;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @date 20/2/25 13:52
 * @des 无痛分娩预约列表
 */
public class PainlessDeliveryListActivity extends BaseControllerActivity<PainlessDeliveryListController> implements PainlessDeliveryListView {
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private PainlessDeliveryAdapter adapter;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_painless_delivery_list;
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
        context.startActivity(new Intent(context, PainlessDeliveryListActivity.class));
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_painless_delivery_reserve));
        adapter = new PainlessDeliveryAdapter(R.layout.item_painless_delivery, new ArrayList<>());
        adapter.setOnItemChildClickListener(getController());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);

        smartRefreshLayout.setOnRefreshListener(refreshLayout -> getController().getPainlessDeliveryList(TypeEnum.REFRESH));

    }

    @Override
    public void requestSuccess(List<PainlessDeliveryBean> list, TypeEnum typeEnum) {
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

    @Override
    public void cancelSuccess(int position) {
        adapter.getData().remove(position);
        adapter.notifyItemRemoved(position);
        adapter.notifyItemRangeChanged(position, adapter.getItemCount() - position);
    }

    @Override
    public void cancelFailed(String msg) {
        ToastUtil.showMessage(this, msg);
    }
}
