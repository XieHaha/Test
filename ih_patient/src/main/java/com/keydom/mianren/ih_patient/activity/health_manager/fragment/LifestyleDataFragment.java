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
import com.keydom.mianren.ih_patient.bean.EatBean;
import com.keydom.mianren.ih_patient.bean.EatItemBean;
import com.keydom.mianren.ih_patient.bean.SportsItemBean;
import com.keydom.mianren.ih_patient.constant.Const;
import com.keydom.mianren.ih_patient.view.LifestyleDataEditDialog;

import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    private List<EatItemBean> eatItemBeans = new ArrayList<>();
    private List<SportsItemBean> sportsItemBeans = new ArrayList<>();

    private List<EatBean> selectEatBeans;

    /**
     * 运动3  食物1
     */
    private int lifestyleType;
    /**
     * 种类id
     */
    private int projectId;
    private int mealType;
    private String patientId;
    private String curSelectDate;

    /**
     * fragment创建
     */
    public static LifestyleDataFragment newInstance(int projectId, int lifestyleType,
                                                    int mealType, String curSelectDate,
                                                    String patientId,
                                                    List<EatBean> selectEatBeans,
                                                    OnItemSelectedListener listener) {
        Bundle args = new Bundle();
        args.putInt(Const.TYPE, lifestyleType);
        args.putInt("mealType", mealType);
        args.putInt("id", projectId);
        args.putString(Const.PATIENT_ID, patientId);
        args.putString("curSelectDate", curSelectDate);
        args.putSerializable("list", (Serializable) selectEatBeans);
        LifestyleDataFragment fragment = new LifestyleDataFragment();
        fragment.setArguments(args);
        fragment.setOnItemSelectedListener(listener);
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
        mealType = getArguments().getInt("mealType");
        patientId = getArguments().getString(Const.PATIENT_ID);
        curSelectDate = getArguments().getString("curSelectDate");
        selectEatBeans = (List<EatBean>) getArguments().getSerializable("list");

        fragLifestyleDataRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (lifestyleType == LIFESTYLE_DIET) {
            eatDataAdapter = new LifestyleEatDataAdapter(eatItemBeans, lifestyleType);
            fragLifestyleDataRecyclerView.setAdapter(eatDataAdapter);
            eatDataAdapter.setOnItemClickListener((adapter, view, position) -> {
                if (eatItemBeans.get(position).isSelected()) {
                    EatBean eatBean = new EatBean();
                    eatBean.setName(eatItemBeans.get(position).getName());
                    updateSelectEatItem(position, eatBean);
                } else {
                    LifestyleDataEditDialog dialog = new LifestyleDataEditDialog(getContext(),
                            mealType, patientId, curSelectDate, eatItemBeans.get(position),
                            eatBean -> updateSelectEatItem(position, eatBean));
                    dialog.show();
                }
            });
            getController().foodBankList();
        } else {
            sportsDataAdapter = new LifestyleSportsDataAdapter(sportsItemBeans, lifestyleType);
            fragLifestyleDataRecyclerView.setAdapter(sportsDataAdapter);
            sportsDataAdapter.setOnItemClickListener((adapter, view, position) -> {
                //                LifestyleDataEditDialog dialog = new LifestyleDataEditDialog
                //                (getContext(),
                //                        patientId,
                //                        eatItemBeans.get(position), new LifestyleDataEditDialog
                //                        .OnCommitListener() {
                //                    @Override
                //                    public void commit() {
                //                        sportsItemBeans.get(position).changeSelectStatus();
                //                        sportsDataAdapter.notifyDataSetChanged();
                //                    }
                //                });
                //                dialog.show();
            });
            getController().exerciseBankList();
        }
    }

    private void initEatSelectData() {
        for (EatBean bean : selectEatBeans) {
            for (EatItemBean item : eatItemBeans) {
                if (bean.getName().equals(item.getName())) {
                    item.setSelected(true);
                }
            }
        }
        eatDataAdapter.setNewData(eatItemBeans);
    }


    /**
     * 饮食选择
     */
    private void updateSelectEatItem(int position, EatBean eatBean) {
        eatItemBeans.get(position).changeSelectStatus();
        eatDataAdapter.notifyDataSetChanged();
        if (onItemSelectedListener != null) {
            onItemSelectedListener.onEatItemSelect(eatBean);
        }
    }

    @Override
    public int getProjectId() {
        return projectId;
    }

    @Override
    public String getPatientId() {
        return patientId;
    }

    @Override
    public int getLifestyleType() {
        return lifestyleType;
    }

    @Override
    public void requestFoodBankListSuccess(List<EatItemBean> data) {
        eatItemBeans = data;
        initEatSelectData();
    }

    @Override
    public void requestExerciseBankListSuccess(List<SportsItemBean> data) {
        sportsItemBeans = data;
        sportsDataAdapter.setNewData(sportsItemBeans);
    }

    private OnItemSelectedListener onItemSelectedListener;

    public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }

    public interface OnItemSelectedListener {
        void onEatItemSelect(EatBean bean);

        void onSportsItemSelect(SportsItemBean bean);
    }
}
