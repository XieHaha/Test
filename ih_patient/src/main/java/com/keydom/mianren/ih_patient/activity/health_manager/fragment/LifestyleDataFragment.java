package com.keydom.mianren.ih_patient.activity.health_manager.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.health_manager.controller.LifestyleDataFragController;
import com.keydom.mianren.ih_patient.activity.health_manager.view.LifestyleDataFragView;
import com.keydom.mianren.ih_patient.adapter.LifestyleEatDataAdapter;
import com.keydom.mianren.ih_patient.adapter.LifestyleSportsDataAdapter;
import com.keydom.mianren.ih_patient.bean.EatItemBean;
import com.keydom.mianren.ih_patient.bean.SportsItemBean;
import com.keydom.mianren.ih_patient.constant.Const;
import com.keydom.mianren.ih_patient.view.LifestyleDataEditDialog;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import butterknife.BindView;

import static com.keydom.mianren.ih_patient.activity.health_manager.LifestyleDataActivity.LIFESTYLE_DIET;

/**
 * 生活方式数据
 *
 * @author 顿顿
 */
public class LifestyleDataFragment extends BaseControllerFragment<LifestyleDataFragController> implements LifestyleDataFragView {
    @BindView(R.id.frag_lifestyle_data_recycler_view)
    RecyclerView fragLifestyleDataRecyclerView;
    //    @BindView(R.id.frag_lifestyle_data_refresh_layout)
    //    SmartRefreshLayout fragLifestyleDataRefreshLayout;

    private LifestyleSportsDataAdapter sportsDataAdapter;
    private LifestyleEatDataAdapter eatDataAdapter;

    private ArrayList<EatItemBean> eatItemBeans = new ArrayList<>();
    private ArrayList<SportsItemBean> sportsItemBeans = new ArrayList<>();

    /**
     * 运动3  食物1
     */
    private int lifestyleType;
    /**
     * 种类id
     */
    private int projectId;

    /**
     * fragment创建
     */
    public static LifestyleDataFragment newInstance(int projectId, int lifestyleType) {
        Bundle args = new Bundle();
        args.putInt(Const.TYPE, lifestyleType);
        args.putInt("id", projectId);
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
        projectId = getArguments().getInt("id");

        fragLifestyleDataRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (lifestyleType == LIFESTYLE_DIET) {
            eatDataAdapter = new LifestyleEatDataAdapter(eatItemBeans, lifestyleType);
            fragLifestyleDataRecyclerView.setAdapter(eatDataAdapter);
            eatDataAdapter.setOnItemClickListener((adapter, view, position) -> {
                LifestyleDataEditDialog dialog = new LifestyleDataEditDialog(getContext(),
                        new LifestyleDataEditDialog.OnCommitListener() {
                            @Override
                            public void commit() {
                                eatItemBeans.get(position).changeSelectStatus();
                                eatDataAdapter.notifyDataSetChanged();
                            }
                        });
                dialog.show();
            });
        } else {
            sportsDataAdapter = new LifestyleSportsDataAdapter(sportsItemBeans, lifestyleType);
            fragLifestyleDataRecyclerView.setAdapter(sportsDataAdapter);
            sportsDataAdapter.setOnItemClickListener((adapter, view, position) -> {
                LifestyleDataEditDialog dialog = new LifestyleDataEditDialog(getContext(),
                        new LifestyleDataEditDialog.OnCommitListener() {
                            @Override
                            public void commit() {
                                sportsItemBeans.get(position).changeSelectStatus();
                                sportsDataAdapter.notifyDataSetChanged();
                            }
                        });
                dialog.show();
            });
        }


        getController().foodBankList();
        getController().exerciseBankList();
    }

    @Override
    public int getProjectId() {
        return projectId;
    }
}
