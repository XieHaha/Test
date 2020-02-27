package com.keydom.ih_patient.activity.child_health;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.child_health.controller.ChildHealthController;
import com.keydom.ih_patient.activity.child_health.view.ChildHealthView;
import com.keydom.ih_patient.adapter.ChildHealthAdapter;
import com.keydom.ih_patient.constant.TypeEnum;
import com.keydom.ih_patient.view.MyNestedScollView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @date 20/2/27 11:37
 * @des 儿童保健首页
 */
public class ChildHealthActivity extends BaseControllerActivity<ChildHealthController> implements ChildHealthView {
    @BindView(R.id.layout_title)
    RelativeLayout layoutBg;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.nested_scroll_view)
    MyNestedScollView scrollView;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private ChildHealthAdapter adapter;
    private ArrayList<String> data;

    /**
     * 启动
     */
    public static void start(Context context) {
        context.startActivity(new Intent(context, ChildHealthActivity.class));
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_child_health;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvTitle.setText("儿童保健");
        layoutBg.setAlpha(0);
        //模拟数据
        data = new ArrayList<>();
        data.add("");
        data.add("");
        data.add("");
        data.add("");
        adapter = new ChildHealthAdapter(R.layout.item_child_health, data);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);

        scrollView.setScrollViewListener((scrollView, x, y, oldX, oldY) -> getController().transTitleBar(y));
        swipeRefreshLayout.setOnRefreshListener(refreshLayout -> getController().getChildHealthList(TypeEnum.REFRESH));
        swipeRefreshLayout.setOnLoadMoreListener(refreshLayout -> getController().getChildHealthList(TypeEnum.LOAD_MORE));

    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }


    @Override
    public void transTitleBar(boolean direction, float scale) {
        if (direction) {
            tvTitle.setSelected(false);
            ivBack.setSelected(false);
        } else {
            tvTitle.setSelected(true);
            ivBack.setSelected(true);
        }
        layoutBg.setAlpha(scale);
    }
}
