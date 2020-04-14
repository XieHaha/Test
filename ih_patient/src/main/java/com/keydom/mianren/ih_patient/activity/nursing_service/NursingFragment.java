package com.keydom.mianren.ih_patient.activity.nursing_service;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.nursing_service.controller.NursingController;
import com.keydom.mianren.ih_patient.activity.nursing_service.view.NursingView;
import com.keydom.mianren.ih_patient.adapter.NursingProjectChooseAdapter;
import com.keydom.mianren.ih_patient.bean.NursingProjectInfo;
import com.keydom.mianren.ih_patient.constant.TypeEnum;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 护理服务fragment
 */
public class NursingFragment extends BaseControllerFragment<NursingController> implements NursingView {
    public static final String ID = "id";
    public static final String LIST = "selectProjectList";
    private View emptyLayout;
    private TextView emptyTv;
    private RecyclerView serviceListView;
    private SmartRefreshLayout mRefresh;
    private List<NursingProjectInfo> selectProjectList;
    private NursingProjectChooseAdapter mAdapter;

    /**
     * fragment创建方法
     */
    public static NursingFragment newInstance(long id, List<NursingProjectInfo> selectProjectList) {
        Bundle args = new Bundle();
        args.putLong(ID, id);
        args.putSerializable(LIST, (Serializable) selectProjectList);
        NursingFragment fragment = new NursingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.general_recyclerview_layout;
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        emptyLayout = LayoutInflater.from(getContext()).inflate(R.layout.empty_layout, null);
        emptyTv = emptyLayout.findViewById(R.id.empty_text);
        serviceListView = view.findViewById(R.id.conten_rv);
        mRefresh = view.findViewById(R.id.conten_refresh_layout);
        mAdapter = new NursingProjectChooseAdapter(new ArrayList<>());
        mAdapter.setEmptyView(emptyLayout);
        serviceListView.setLayoutManager(new LinearLayoutManager(getContext()));
        serviceListView.setAdapter(mAdapter);
        long id = getArguments().getLong(ID);
        selectProjectList = (List<NursingProjectInfo>) getArguments().getSerializable(LIST);
        mRefresh.setOnRefreshListener(refreshLayout -> {
            getController().getNurseServiceProjectByCateId(String.valueOf(id), TypeEnum.REFRESH);
        });
        mRefresh.setOnLoadMoreListener(refreshLayout -> getController().getNurseServiceProjectByCateId(String.valueOf(id), TypeEnum.LOAD_MORE));
        getController().getNurseServiceProjectByCateId(String.valueOf(id), TypeEnum.REFRESH);
        mAdapter.setOnItemChildClickListener((adapter, view1, position) -> {
            switch (view1.getId()) {
                case R.id.service_selected_img:
                    List<NursingProjectInfo> data = ChooseNursingServiceActivity.mChooseProjects;
                    long dept = 0;
                    for (int i = 0; i < data.size(); i++) {
                        if (data.get(i).getHospitalDeptId() != 0) {
                            dept = data.get(i).getHospitalDeptId();
                        }
                    }
                    NursingProjectInfo projectInfo =
                            (NursingProjectInfo) adapter.getData().get(position);
                    if (dept != 0 && data.size() != 0 && !projectInfo.isSelect() && dept != projectInfo.getHospitalDeptId() && projectInfo.getHospitalDeptId() != 0) {
                        ToastUtils.showShort("只能选择同科室的项目");
                    } else {
                        if (!projectInfo.isSelect()) {
                            ChooseNursingServiceActivity.mChooseProjects.add(projectInfo);
                        } else {
                            for (int i = 0; i < data.size(); i++) {
                                if (projectInfo.getId() == data.get(i).getId()) {
                                    ChooseNursingServiceActivity.mChooseProjects.remove(i);
                                    break;
                                }
                            }

                        }
                        projectInfo.setSelect(!projectInfo.isSelect());
                    }

                    mAdapter.notifyItemChanged(position);
                    break;
            }
        });
    }

    /**
     * 获取项目list
     */
    public List<NursingProjectInfo> getProjects() {
        List<NursingProjectInfo> data = new ArrayList<>();
        for (int i = 0; i < mAdapter.getData().size(); i++) {
            if (mAdapter.getData().get(i).isSelect()) {
                data.add(mAdapter.getData().get(i));
            }
        }
        return data;
    }

    @Override
    public void getNursingProjectSuccess(List<NursingProjectInfo> dataList, TypeEnum typeEnum) {
        for (int i = 0; i < dataList.size(); i++) {
            Logger.e("第" + i + "项cateId=" + dataList.get(i).getCateId());
            NursingProjectInfo projectInfo = dataList.get(i);
            for (int j = 0; j < selectProjectList.size(); j++) {
                NursingProjectInfo projectInfo1 = selectProjectList.get(j);
                if (projectInfo.getId() == projectInfo1.getId()) {
                    projectInfo.setSelect(true);
                }
            }
        }
        mRefresh.finishLoadMore();
        mRefresh.finishRefresh();
        pageLoadingSuccess();
        if (typeEnum == TypeEnum.REFRESH) {
            mAdapter.replaceData(dataList);
        } else {
            mAdapter.addData(dataList);
        }
        getController().currentPagePlus();
    }

    @Override
    public void getNursingProjectFailed(String errMsg) {
        if (mRefresh.isRefreshing()) {
            mRefresh.finishRefresh();
        }
        if (mRefresh.isLoading()) {
            mRefresh.finishLoadMore();
        }
        ToastUtils.showShort(errMsg);
    }
}
