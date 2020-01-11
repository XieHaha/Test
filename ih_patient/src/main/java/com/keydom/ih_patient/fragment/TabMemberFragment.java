package com.keydom.ih_patient.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.adapter.MemberFunctionAdapter;
import com.keydom.ih_patient.bean.VIPCardInfoListItem;
import com.keydom.ih_patient.bean.VIPCardInfoResponse;
import com.keydom.ih_patient.fragment.controller.TabMemberController;
import com.keydom.ih_patient.fragment.view.TabMemberView;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TabMemberFragment extends BaseControllerFragment<TabMemberController> implements TabMemberView {

    RecyclerView mRecyclerView;

    MemberFunctionAdapter mAdapter;

    List<VIPCardInfoListItem> mDatas;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_tab_member;
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = view.findViewById(R.id.fragment_tab_member_rv);


        view.findViewById(R.id.fragment_tab_member_get_vip_tv).setOnClickListener(getController());

        getController().getMyVipCard();
    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        mDatas = new ArrayList<VIPCardInfoListItem>();

    }


    public void initAdapter() {
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getContext(), 3);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setAdapter(mAdapter);


        mGridLayoutManager.setSmoothScrollbarEnabled(true);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
    }


    @Override
    public void lazyLoad() {

    }


    @Override
    public void getMyVipCardSuccess(VIPCardInfoResponse data) {

        if (null != data && null != data.getInfoList() && data.getInfoList().size() > 0) {

            mDatas.addAll(data.getInfoList());
            mAdapter = new MemberFunctionAdapter(getContext(), mDatas);

            initAdapter();
        }

    }
}