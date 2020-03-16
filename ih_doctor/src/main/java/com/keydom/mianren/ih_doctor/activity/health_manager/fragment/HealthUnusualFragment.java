package com.keydom.mianren.ih_doctor.activity.health_manager.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.health_manager.controller.HealthUnusualController;
import com.keydom.mianren.ih_doctor.activity.health_manager.view.HealthUnusualView;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;

/**
 * @date 20/3/13 14:22
 * @des 体征异常
 */
public class HealthUnusualFragment extends BaseControllerFragment<HealthUnusualController> implements HealthUnusualView {
    @BindView(R.id.health_unusual_search_view)
    SearchView healthUnusualSearchView;
    @BindView(R.id.health_unusual_search_input_tv)
    TextView healthUnusualSearchInputTv;
    @BindView(R.id.search_input_rl)
    RelativeLayout searchInputRl;
    @BindView(R.id.health_unusual_recycler_view)
    RecyclerView healthUnusualRecyclerView;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_health_unusual;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }
}
