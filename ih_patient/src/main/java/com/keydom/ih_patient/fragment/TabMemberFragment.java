package com.keydom.ih_patient.fragment;

import android.os.Bundle;

import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.fragment.controller.TabMemberController;
import com.keydom.ih_patient.fragment.view.TabMemberView;

import org.jetbrains.annotations.Nullable;

public class TabMemberFragment extends BaseControllerFragment<TabMemberController> implements TabMemberView {
    @Override
    public int getLayoutRes() {
        return R.layout.fragment_tab_member;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }



    @Override
    public void lazyLoad() {

    }
}
