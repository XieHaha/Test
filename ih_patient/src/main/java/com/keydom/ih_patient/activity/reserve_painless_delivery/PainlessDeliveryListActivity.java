package com.keydom.ih_patient.activity.reserve_painless_delivery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.keydom.ih_common.base.BaseActivity;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.adapter.PainlessDeliveryAdapter;
import com.keydom.ih_patient.bean.PainlessDeliveryInfo;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @date 20/2/25 13:52
 * @des 无痛分娩预约列表
 */
public class PainlessDeliveryListActivity extends BaseActivity implements OnRefreshListener {
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private PainlessDeliveryAdapter adapter;

    private ArrayList<PainlessDeliveryInfo> data;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_painless_delivery_list;
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
        smartRefreshLayout.setOnRefreshListener(this);
        //假数据
        PainlessDeliveryInfo info = new PainlessDeliveryInfo();
        data = new ArrayList<>();
        data.add(info);
        data.add(info);
        data.add(info);
        adapter = new PainlessDeliveryAdapter(R.layout.item_painless_delivery, data);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        refreshLayout.finishRefresh();
    }
}
