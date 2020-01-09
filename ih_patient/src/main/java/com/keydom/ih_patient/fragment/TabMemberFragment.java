package com.keydom.ih_patient.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.adapter.MemberFunctionAdapter;
import com.keydom.ih_patient.bean.IndexFunction;
import com.keydom.ih_patient.fragment.controller.TabMemberController;
import com.keydom.ih_patient.fragment.view.TabMemberView;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TabMemberFragment extends BaseControllerFragment<TabMemberController> implements TabMemberView {

    RecyclerView mRecyclerView;

    MemberFunctionAdapter mAdapter;

    List<IndexFunction> mDatas;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_tab_member;
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = view.findViewById(R.id.fragment_tab_member_rv);

        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getContext(),3);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setAdapter(mAdapter);


        mGridLayoutManager.setSmoothScrollbarEnabled(true);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        view.findViewById(R.id.fragment_tab_member_get_vip_tv).setOnClickListener(getController());
    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        mDatas = new ArrayList<IndexFunction>();

        mDatas.add(new IndexFunction(R.mipmap.vip_medical_record_mail_icon,"私人医生服务"));
        mDatas.add(new IndexFunction(R.mipmap.appointment_register_icon,"全天候就医服务"));
        mDatas.add(new IndexFunction(R.mipmap.vip_health_record_icon,"就医绿色通道"));

        mAdapter = new MemberFunctionAdapter(getContext(),mDatas);



    }



    @Override
    public void lazyLoad() {

    }



}
