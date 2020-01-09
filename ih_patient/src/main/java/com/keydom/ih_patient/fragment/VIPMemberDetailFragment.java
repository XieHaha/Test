package com.keydom.ih_patient.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.adapter.MemberFunctionAdapter;
import com.keydom.ih_patient.bean.IndexFunction;
import com.keydom.ih_patient.fragment.controller.VIPMemberDetailController;
import com.keydom.ih_patient.fragment.view.VIPMemberDetailView;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class VIPMemberDetailFragment extends BaseControllerFragment<VIPMemberDetailController> implements VIPMemberDetailView {

    RecyclerView mRecyclerView;

    MemberFunctionAdapter mAdapter;

    List<IndexFunction> mDatas;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_vip_member_detail;
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.fragment_vip_member_detail_charge_ll).setOnClickListener(getController());
        view.findViewById(R.id.fragment_vip_member_detail_charge_record_ll).setOnClickListener(getController());
    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }



    @Override
    public void lazyLoad() {

    }



}
