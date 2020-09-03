package com.keydom.mianren.ih_patient.activity.reserve_obstetric_hospital.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_patient.App;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.reserve_obstetric_hospital.controller.ObstetricController;
import com.keydom.mianren.ih_patient.activity.reserve_obstetric_hospital.view.ObstetricView;
import com.keydom.mianren.ih_patient.adapter.ObstetricRecordAdapter;
import com.keydom.mianren.ih_patient.constant.TypeEnum;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * 产科住院
 *
 * @author 顿顿
 */
public class ObstetricHospitalFragment extends BaseControllerFragment<ObstetricController> implements ObstetricView {
    private SmartRefreshLayout refreshLayout;
    private List<String> dataList = new ArrayList<>();
    private ObstetricRecordAdapter obstetricRecordAdapter;

    /**
     * 1 已住院  0 未住院
     */
    private int type;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_obstetric_hospital;
    }

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        type = getArguments().getInt("type", 0);

        refreshLayout = view.findViewById(R.id.containt_refresh);
        RecyclerView recyclerView = view.findViewById(R.id.containt_rv);
        obstetricRecordAdapter = new ObstetricRecordAdapter(getContext(), dataList);
        recyclerView.setAdapter(obstetricRecordAdapter);
        refreshLayout.setOnRefreshListener(refreshLayout -> getData());

        getData();
        super.onViewCreated(view, savedInstanceState);
    }

    private void getData() {
        getController().getObsByCardNo(type, App.userInfo.getIdCard());
    }

    @Override
    public void getObstetricListSuccess(List<String> dataList, TypeEnum typeEnum) {
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
        pageLoadingSuccess();
        if (dataList != null) {
            if (typeEnum == TypeEnum.REFRESH) {
                this.dataList.clear();
            }
            this.dataList.addAll(dataList);
            obstetricRecordAdapter.notifyDataSetChanged();
            getController().currentPagePlus();
        }
    }

    @Override
    public void getObstetricListFailed(String errMsg) {
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
        ToastUtil.showMessage(getContext(), errMsg);
    }
}
