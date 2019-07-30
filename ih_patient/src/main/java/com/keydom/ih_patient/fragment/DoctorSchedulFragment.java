package com.keydom.ih_patient.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.order_doctor_register.DoctorIndexActivity;
import com.keydom.ih_patient.adapter.DoctorSchedulAdapter;
import com.keydom.ih_patient.bean.DoctorSchedulingBean;
import com.keydom.ih_patient.fragment.controller.DoctorSchedulController;
import com.keydom.ih_patient.fragment.view.DoctorSchedulView;
import com.keydom.ih_patient.utils.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * 医生排班页面
 */
public class DoctorSchedulFragment extends BaseControllerFragment<DoctorSchedulController> implements DoctorSchedulView {
    private RecyclerView containRv;
    private DoctorSchedulAdapter doctorSchedulAdapter;
    private List<DoctorSchedulingBean> dataList =new ArrayList<>();
    //deptId 科室ID deptUserId ，doctorId 医生ID
    private long deptId,deptUserId,doctorId;
    //doctorName 医生姓名
    private String doctorName;
    private SmartRefreshLayout containt_refresh;
    @Override
    public int getLayoutRes() {
        return R.layout.fragment_order_examination_layout;
    }


    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        Bundle bundle =this.getArguments();
        deptId=bundle.getLong("deptId",-1);
        deptUserId=bundle.getLong("deptUserId",-1);
        doctorName=bundle.getString("doctorName");
        doctorId=bundle.getLong("doctorId",-1);
        containRv=view.findViewById(R.id.containt_rv);
        doctorSchedulAdapter=new DoctorSchedulAdapter((DoctorIndexActivity) getActivity(),dataList,doctorName,doctorId);
        containRv.setAdapter(doctorSchedulAdapter);
        containt_refresh=view.findViewById(R.id.containt_refresh);
        containt_refresh.setEnableLoadMore(false);
        containt_refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                getController().getDoctorSchedulingList(deptId,deptUserId);
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void lazyLoad() {
        containt_refresh.autoRefresh();
    }

    @Override
    public void onResume() {
        super.onResume();
        containt_refresh.autoRefresh();
    }

    @Override
    public void getDoctorSchedulingListSuccess(List<DoctorSchedulingBean> dataList) {
        containt_refresh.finishRefresh();
        this.dataList.clear();
        this.dataList.addAll(dataList);
        doctorSchedulAdapter.notifyDataSetChanged();
    }

    @Override
    public void getDoctorSchedulingListFailde(String errMsg) {
        containt_refresh.finishRefresh();
        ToastUtil.shortToast(getContext(),"获取门诊排班失败"+errMsg+"，请下拉刷新");
    }
}
