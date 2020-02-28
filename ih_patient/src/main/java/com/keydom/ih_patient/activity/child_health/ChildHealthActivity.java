package com.keydom.ih_patient.activity.child_health;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.child_health.controller.ChildHealthController;
import com.keydom.ih_patient.activity.child_health.view.ChildHealthView;
import com.keydom.ih_patient.adapter.ChildHealthAdapter;
import com.keydom.ih_patient.constant.TypeEnum;
import com.keydom.ih_patient.utils.StatusBarUtils;
import com.keydom.ih_patient.view.MyNestedScollView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @date 20/2/27 11:37
 * @des 儿童保健首页
 */
public class ChildHealthActivity extends BaseControllerActivity<ChildHealthController> implements ChildHealthView {
    @BindView(R.id.layout_title)
    RelativeLayout layoutBg;
    @BindView(R.id.status_bar)
    View statusBar;
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
    @BindView(R.id.iv_online)
    ImageView ivOnline;

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
        statusBar.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                StatusBarUtils.getStateBarHeight(this)));
        StatusBarUtils.setStatusBarTranslucent(this);
        tvTitle.setText("儿童保健");
        layoutBg.setAlpha(0);
        statusBar.setAlpha(0);
        StatusBarUtils.setStatusBarColor(this, true);
        //模拟数据
        data = new ArrayList<>();
        data.add("");
        data.add("");
        data.add("");
        data.add("");
        adapter = new ChildHealthAdapter(R.layout.item_child_health, data);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(getController());
        ivOnline.setOnClickListener(getController());
        ivBack.setOnClickListener(getController());
        scrollView.setScrollViewListener((scrollView, x, y, oldX, oldY) -> getController().transTitleBar(y));
        swipeRefreshLayout.setOnRefreshListener(refreshLayout -> getController().getChildHealthList(TypeEnum.REFRESH));
        swipeRefreshLayout.setOnLoadMoreListener(refreshLayout -> getController().getChildHealthList(TypeEnum.LOAD_MORE));
    }


    @Override
    public void transTitleBar(boolean direction, float scale) {
        tvTitle.setSelected(!direction);
        ivBack.setSelected(!direction);
        layoutBg.setAlpha(scale);
        statusBar.setAlpha(scale);
        StatusBarUtils.setStatusBarColor(this, direction);
    }

    @Override
    public void finishPage() {
        finish();
    }
}
