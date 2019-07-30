package com.keydom.ih_patient.activity.online_diagnoses_order;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.online_diagnoses_order.controller.ChoosePatientController;
import com.keydom.ih_patient.activity.online_diagnoses_order.view.ChoosePatientView;
import com.keydom.ih_patient.adapter.FragmentViewPagerAdapter;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择患者页面
 */
public class ChoosePatientActivity extends BaseControllerActivity<ChoosePatientController> implements ChoosePatientView {
    /**
     * 启动
     */
    public static void start(Context context,int type) {
        Intent intent=new Intent(context, ChoosePatientActivity.class);
        intent.putExtra("type",type);
        context.startActivity(intent);
    }
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private FragmentViewPagerAdapter mViewPagerAdapter;
    private FragmentManager fm;
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> list = new ArrayList<>();
    private int type;
    @Override
    public int getLayoutRes() {
        return R.layout.activity_choose_patient_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("选择就诊人");
        type=getIntent().getIntExtra("type",0);
        mTabLayout = findViewById(R.id.choose_patient_tab);
        mViewPager = findViewById(R.id.choose_patient_vp);
        if(type==1){
            list.add("就诊卡(问诊)");
            list.add("就诊人(咨询)");
            fragmentList.add(new TypeCardFragment());
            fragmentList.add(new TypePatientFragment());
        }else {
            list.add("就诊人(咨询)");
            fragmentList.add(new TypePatientFragment());
            mTabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FFFFFF"));
        }

        fm = getSupportFragmentManager();
        mViewPagerAdapter = new FragmentViewPagerAdapter(fm, fragmentList, list);
        mViewPager.setAdapter(mViewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
