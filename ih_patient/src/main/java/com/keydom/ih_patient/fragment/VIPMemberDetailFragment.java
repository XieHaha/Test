package com.keydom.ih_patient.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.adapter.MemberFunctionAdapter;
import com.keydom.ih_patient.bean.IndexFunction;
import com.keydom.ih_patient.bean.VIPDetailBean;
import com.keydom.ih_patient.bean.event.ChargeMemberSuccess;
import com.keydom.ih_patient.fragment.controller.VIPMemberDetailController;
import com.keydom.ih_patient.fragment.view.VIPMemberDetailView;
import com.keydom.ih_patient.utils.DateUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class VIPMemberDetailFragment extends BaseControllerFragment<VIPMemberDetailController> implements VIPMemberDetailView {

    RecyclerView mRecyclerView;

    MemberFunctionAdapter mAdapter;

    List<IndexFunction> mDatas;

    TextView mCardNumTv;
    TextView mCardNameTv;
    TextView mCardDateTv;
    TextView mBalanceTv;
    TextView mDateTv;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_vip_member_detail;
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCardNumTv = view.findViewById(R.id.fragment_tab_member_card_num_tv);
        mCardNameTv = view.findViewById(R.id.fragment_vip_member_detail_card_name_tv);
        mCardDateTv = view.findViewById(R.id.fragment_vip_member_detail_card_date_tv);
        mBalanceTv = view.findViewById(R.id.fragment_vip_member_detail_balance_tv);
        mDateTv = view.findViewById(R.id.fragment_vip_member_detail_date_tv);

        view.findViewById(R.id.fragment_vip_member_detail_charge_ll).setOnClickListener(getController());
        view.findViewById(R.id.fragment_vip_member_detail_charge_record_ll).setOnClickListener(getController());

        pageLoading();
        getController().getMyVipCard();

        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }


    @Override
    public void lazyLoad() {

    }


    @Override
    public void getMyVipCardSuccess(VIPDetailBean data) {
        pageLoadingSuccess();
        refreshData(data);
    }

    @Override
    public void getMyVipCardFail(String msg) {
        pageLoadingFail();
    }


    public void refreshData(VIPDetailBean data) {
        if (null != data) {
            if (!TextUtils.isEmpty(data.getCardNumber()))
                mCardNumTv.setText(data.getCardNumber());

            if (!TextUtils.isEmpty(data.getCardHolder()))
                mCardNameTv.setText("持卡人：" + data.getCardHolder());

            if (null != data.getEndTime()) {
                mCardDateTv.setText("有效期：" + DateUtils.getYMDfromYMDHMS(data.getEndTime()));
                mDateTv.setText(DateUtils.getYMDfromYMDHMS(data.getEndTime()));
            }

            mBalanceTv.setText(String.valueOf(data.getSurplusAmount()));

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRecieve(ChargeMemberSuccess success) {
        getController().getMyVipCard();
    }
}
