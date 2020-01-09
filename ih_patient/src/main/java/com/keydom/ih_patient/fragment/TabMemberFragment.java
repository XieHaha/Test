package com.keydom.ih_patient.fragment;

import android.os.Bundle;
import android.view.View;

import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.bean.Event;
import com.keydom.ih_patient.constant.EventType;
import com.keydom.ih_patient.fragment.controller.TabMemberController;
import com.keydom.ih_patient.fragment.view.TabMemberView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TabMemberFragment extends BaseControllerFragment<TabMemberController> implements TabMemberView {


    @Override
    public int getLayoutRes() {
        return R.layout.fragment_tab_member;
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EventBus.getDefault().register(this);

        view.findViewById(R.id.fragment_tab_member_get_vip_tv).setOnClickListener(getController());
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
        }
    }

}
