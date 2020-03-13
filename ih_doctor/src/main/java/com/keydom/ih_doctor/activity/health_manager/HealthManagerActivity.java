package com.keydom.ih_doctor.activity.health_manager;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.widget.DecoratorViewPager;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.health_manager.controller.HealthManagerController;
import com.keydom.ih_doctor.activity.health_manager.fragment.HealthMsgFragment;
import com.keydom.ih_doctor.activity.health_manager.fragment.HealthUnusualFragment;
import com.keydom.ih_doctor.activity.health_manager.view.HealthManagerView;
import com.keydom.ih_doctor.adapter.HealthViewPagerAdapter;
import com.keydom.ih_doctor.fragment.PatientContactFragment;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @date 20/3/12 16:25
 * @des 健康管理
 */
public class HealthManagerActivity extends BaseControllerActivity<HealthManagerController> implements HealthManagerView {
    @BindView(R.id.health_manager_tab)
    TabLayout healthManagerTab;
    @BindView(R.id.health_manager_view_pager)
    DecoratorViewPager healthManagerViewPager;

    private HealthViewPagerAdapter pagerAdapter;
    private FragmentManager fragmentManager;
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> list = new ArrayList<>();

    /**
     * 启动
     */
    public static void startConsult(Context context) {
        Intent intent = new Intent(context, HealthManagerActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_health_manager;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_health_manager));
        list.add(getString(R.string.txt_message));
        list.add(getString(R.string.txt_unusual_patient));
        list.add(getString(R.string.txt_all_people));
        fragmentManager = getSupportFragmentManager();
        fragmentList.add(new HealthMsgFragment());
        fragmentList.add(new HealthUnusualFragment());
        fragmentList.add(new PatientContactFragment());

        pagerAdapter = new HealthViewPagerAdapter(fragmentManager, fragmentList, list);
        healthManagerViewPager.setAdapter(pagerAdapter);
        healthManagerTab.setupWithViewPager(healthManagerViewPager);
    }
}
