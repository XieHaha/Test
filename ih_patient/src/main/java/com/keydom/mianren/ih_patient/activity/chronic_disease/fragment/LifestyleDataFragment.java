package com.keydom.mianren.ih_patient.activity.chronic_disease.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.chronic_disease.controller.LifestyleDataFragController;
import com.keydom.mianren.ih_patient.activity.chronic_disease.view.LifestyleDataFragView;
import com.keydom.mianren.ih_patient.adapter.LifestyleDataAdapter;
import com.keydom.mianren.ih_patient.bean.LifestyleDataBean;
import com.keydom.mianren.ih_patient.constant.Const;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 生活方式数据
 *
 * @author 顿顿
 */
public class LifestyleDataFragment extends BaseControllerFragment<LifestyleDataFragController> implements LifestyleDataFragView {
    @BindView(R.id.frag_lifestyle_data_recycler_view)
    RecyclerView fragLifestyleDataRecyclerView;
    @BindView(R.id.frag_lifestyle_data_refresh_layout)
    SmartRefreshLayout fragLifestyleDataRefreshLayout;

    private LifestyleDataAdapter lifestyleDataAdapter;

    private ArrayList<LifestyleDataBean> data = new ArrayList<>();
    private ArrayList<LifestyleDataBean> selectData = new ArrayList<>();

    /**
     * 运动3  食物1
     */
    private int lifestyleType;

    /**
     * fragment创建
     */
    public static LifestyleDataFragment newInstance(long patientId, int type) {
        Bundle args = new Bundle();
        args.putInt(Const.TYPE, type);
        LifestyleDataFragment fragment = new LifestyleDataFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_lifestyle_data_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        assert getArguments() != null;
        lifestyleType = getArguments().getInt(Const.TYPE);
        data.add(new LifestyleDataBean());
        data.add(new LifestyleDataBean());
        data.add(new LifestyleDataBean());
        data.add(new LifestyleDataBean());
        data.add(new LifestyleDataBean());
        lifestyleDataAdapter = new LifestyleDataAdapter(data, lifestyleType);
        fragLifestyleDataRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        fragLifestyleDataRecyclerView.setAdapter(lifestyleDataAdapter);
        lifestyleDataAdapter.setOnItemClickListener((adapter, view, position) -> {
            data.get(position).changeSelectStatus();
            lifestyleDataAdapter.notifyDataSetChanged();
        });

        fragLifestyleDataRefreshLayout.setOnRefreshListener(null);
    }

}
