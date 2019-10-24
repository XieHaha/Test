package com.keydom.ih_patient.activity.my_doctor_or_nurse;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.im.ImClient;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.my_doctor_or_nurse.controller.MyDoctorOrNurseController;
import com.keydom.ih_patient.activity.my_doctor_or_nurse.view.MyDoctorOrNurseView;
import com.keydom.ih_patient.adapter.MyDoctorOrNurseAdapter;
import com.keydom.ih_patient.bean.DoctorOrNurseBean;
import com.keydom.ih_patient.constant.TypeEnum;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * created date: 2019/1/2 on 14:25
 * des:我的关注列表页面
 */
public class MyDoctorOrNurseActivity extends BaseControllerActivity<MyDoctorOrNurseController> implements MyDoctorOrNurseView {
    //启动类型 0 医生首页  1 护士首页
    public static final String TYPE = "type";
    public static final int DOCTOR = 1;
    public static final int NURSE = 2;

    private RefreshLayout refreshLayout;
    private RecyclerView mRecyclerView;
    private MyDoctorOrNurseAdapter mAdapter;
    /**
     * 医生/护士
     */
    private int mType;
    /**
     * 关注列表
     */
    private List<DoctorOrNurseBean> followsList = new ArrayList<>();
    /**
     * 请求type
     */
    private int requestType;

    @Override
    public int getLayoutRes() {
        return R.layout.acitivity_my_doctor_or_nurse;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mType = getIntent().getIntExtra(TYPE, 0);
        if (mType == DOCTOR) {
            setTitle("我的医生");
            requestType = 1;
        } else if (mType == NURSE) {
            setTitle("我的护士");
            requestType = 2;
        } else {
            ToastUtils.showShort("未选择TYPE");
        }
        mRecyclerView = findViewById(R.id.recyclerView);
        refreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MyDoctorOrNurseAdapter(new ArrayList<>());
        mRecyclerView.setAdapter(mAdapter);
        getController().getMyFollowList(requestType, TypeEnum.REFRESH);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            DoctorOrNurseBean bean = (DoctorOrNurseBean) adapter.getData().get(position);
            ImClient.getUserInfoProvider().getUserInfoAsync(bean.getImNumber(), (success, result, code) -> {
                if (success) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("jobType", mType);
                    ImClient.startConversation(getContext(), bean.getImNumber(), bundle);
                    bean.setContentNum(0);
                    mAdapter.notifyItemChanged(position);
                }
            });
        });

        refreshLayout.setOnRefreshListener(getController());
        refreshLayout.setOnLoadMoreListener(getController());

    }

    @Override
    public void myFollowsCallBack(List<DoctorOrNurseBean> list,TypeEnum typeEnum) {
        pageLoadingSuccess();
        if (typeEnum == TypeEnum.REFRESH) {
            this.followsList.clear();
        }
        this.followsList.addAll(list);
        mAdapter.notifyDataSetChanged();
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
    }

    @Override
    public List<DoctorOrNurseBean> returnFollows() {
        return followsList;
    }

    @Override
    public void mateFollows(List<DoctorOrNurseBean> list) {
        mAdapter.setNewData(list);
    }
}
