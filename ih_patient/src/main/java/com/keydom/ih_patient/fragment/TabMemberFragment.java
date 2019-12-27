package com.keydom.ih_patient.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.bean.Event;
import com.keydom.ih_patient.constant.EventType;
import com.keydom.ih_patient.constant.Global;
import com.keydom.ih_patient.fragment.controller.TabMemberController;
import com.keydom.ih_patient.fragment.view.TabMemberView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TabMemberFragment extends BaseControllerFragment<TabMemberController> implements TabMemberView {

    LinearLayout mDescRootLl;
    LinearLayout mFuncRootLl;
    LinearLayout mMemberInstRootLl;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_tab_member;
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMemberInstRootLl = view.findViewById(R.id.fragmnet_tab_member_member_inst_root_ll);
        mFuncRootLl = view.findViewById(R.id.fragment_tab_member_func_root_ll);
        mDescRootLl = view.findViewById(R.id.fragment_tab_member_desc_root_ll);

        view.findViewById(R.id.fragment_tab_member_open_now_tv).setOnClickListener(getController());
        view.findViewById(R.id.fragment_tab_member_func_charge_record_tv).setOnClickListener(getController());
        view.findViewById(R.id.fragment_tab_member_func_charge_tv).setOnClickListener(getController());

        memberLayoutShow();

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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRecieve(Event event) {
        if (event.getType() == EventType.UPDATELOGINSTATE) {
            memberLayoutShow();
        }
    }

    private void memberLayoutShow() {
        if(Global.isMember()){
            mMemberInstRootLl.setVisibility(View.GONE);
            mFuncRootLl.setVisibility(View.VISIBLE);
            mDescRootLl.setVisibility(View.VISIBLE);
        }else{
            mMemberInstRootLl.setVisibility(View.VISIBLE);
            mFuncRootLl.setVisibility(View.GONE);
            mDescRootLl.setVisibility(View.GONE);
        }
    }
}
