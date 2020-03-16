package com.keydom.mianren.ih_doctor.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ganxin.library.LoadDataLayout;
import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.adapter.NursingProjectChooseAdapter;
import com.keydom.mianren.ih_doctor.bean.NursingProjectInfo;
import com.keydom.mianren.ih_doctor.constant.TypeEnum;
import com.keydom.mianren.ih_doctor.fragment.controller.NursingController;
import com.keydom.mianren.ih_doctor.fragment.view.NursingView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NursingFragment extends BaseControllerFragment<NursingController> implements NursingView {
    /**
     * ID
     */
    public static final String ID = "id";
    /**
     * 选择的项目列表
     */
    public static final String LIST = "selectProjectList";
    private View emptyLayout;
    private TextView emptyTv;
    private RecyclerView serviceListView;
    private SmartRefreshLayout mRefresh;
    /**
     * 护理项目列表
     */
    private List<NursingProjectInfo> selectProjectList;
    /**
     * 项目适配器
     */
    private NursingProjectChooseAdapter mAdapter;


    private long id;

    /**
     * 获取本类对象
     *
     * @param id                护理服务ID
     * @param selectProjectList 已经选择了的护理服务项目
     * @return NursingFragment
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
        id = getArguments().getLong(ID);
        super.onViewCreated(view, savedInstanceState);
        pageLoading();
        emptyLayout = LayoutInflater.from(getContext()).inflate(R.layout.empty_layout, null);
        emptyTv = emptyLayout.findViewById(R.id.empty_text);
        serviceListView = view.findViewById(R.id.conten_rv);
        mRefresh = view.findViewById(R.id.conten_refresh_layout);
        mAdapter = new NursingProjectChooseAdapter(new ArrayList<>());
        mAdapter.setEmptyView(emptyLayout);
        serviceListView.setLayoutManager(new LinearLayoutManager(getContext()));
        serviceListView.setAdapter(mAdapter);
        selectProjectList = (List<NursingProjectInfo>) getArguments().getSerializable(LIST);
        mRefresh.setOnRefreshListener(refreshLayout -> {
            getController().getNurseServiceProjectByCateId(String.valueOf(id), TypeEnum.REFRESH);
        });
        mRefresh.setOnLoadMoreListener(refreshLayout -> getController().getNurseServiceProjectByCateId(String.valueOf(id), TypeEnum.LOAD_MORE));
        setReloadListener(new LoadDataLayout.OnReloadListener() {
            @Override
            public void onReload(View v, int status) {
                pageLoading();
                getController().getNurseServiceProjectByCateId(String.valueOf(id), TypeEnum.REFRESH);
            }
        });
        mAdapter.setOnItemChildClickListener((adapter, view1, position) -> {
            switch (view1.getId()) {
                case R.id.nursing_item:
                    NursingProjectInfo projectInfo = (NursingProjectInfo) adapter.getData().get(position);
                    projectInfo.setSelect(!projectInfo.isSelect());
                    mAdapter.notifyItemChanged(position);
                    break;
            }
        });
    }

    /**
     * 获取项目列表
     *
     * @return List<NursingProjectInfo>
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
        pageLoadingSuccess();
        mRefresh.finishLoadMore();
        mRefresh.finishRefresh();
        for (int i = 0; i < dataList.size(); i++) {
            NursingProjectInfo projectInfo = dataList.get(i);
            if (selectProjectList != null) {
                for (int j = 0; j < selectProjectList.size(); j++) {
                    NursingProjectInfo projectInfo1 = selectProjectList.get(j);
                    if (projectInfo.getId() == projectInfo1.getId()) {
                        projectInfo.setSelect(true);
                    }
                }
            }

        }

        if (typeEnum == TypeEnum.REFRESH) {
            mAdapter.replaceData(dataList);
        }else{
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
        pageLoadingFail();
    }

    @Override
    public void lazyLoad() {
        getController().getNurseServiceProjectByCateId(String.valueOf(id), TypeEnum.REFRESH);
    }
}
