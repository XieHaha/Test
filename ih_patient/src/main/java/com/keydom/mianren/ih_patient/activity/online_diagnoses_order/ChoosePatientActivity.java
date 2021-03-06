package com.keydom.mianren.ih_patient.activity.online_diagnoses_order;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.online_diagnoses_order.controller.ChoosePatientController;
import com.keydom.mianren.ih_patient.activity.online_diagnoses_order.view.ChoosePatientView;
import com.keydom.mianren.ih_patient.adapter.FragmentViewPagerAdapter;
import com.keydom.mianren.ih_patient.constant.Const;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择患者页面
 *
 * @author 顿顿
 */
public class ChoosePatientActivity extends BaseControllerActivity<ChoosePatientController> implements ChoosePatientView {
    /**
     * 启动
     */
    public static void start(Context context, int type, boolean isNeed) {
        Intent intent = new Intent(context, ChoosePatientActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("isNeed", isNeed);
        context.startActivity(intent);
    }

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private FragmentViewPagerAdapter mViewPagerAdapter;
    private FragmentManager fm;
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> list = new ArrayList<>();
    private int type;

    private boolean isNeed;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_choose_patient_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        type = getIntent().getIntExtra("type", Const.PATIENT_TYPE_PERSON);
        mTabLayout = findViewById(R.id.choose_patient_tab);
        mViewPager = findViewById(R.id.choose_patient_vp);
        if (type == Const.PATIENT_TYPE_ALL) {
            setTitle(R.string.txt_select_visit_people);
            list.add("就诊人(问诊)");
            //            list.add("就诊人(咨询)");
            TypeCardFragment typeCardFragment = new TypeCardFragment();
            Bundle bundle = new Bundle();
            bundle.putBoolean(Const.IS_NEED_TO_PREGNANCY, false);
            typeCardFragment.setArguments(bundle);
            fragmentList.add(typeCardFragment);
            //            fragmentList.add(new TypePatientFragment());
        } else if (type == Const.PATIENT_TYPE_CARD) {
            setTitle("选择就诊卡");
            list.add("就诊卡");
            isNeed = getIntent().getBooleanExtra("isNeed", true);
            TypeCardFragment typeCardFragment = new TypeCardFragment();
            Bundle bundle = new Bundle();
            bundle.putBoolean(Const.IS_NEED_TO_PREGNANCY, isNeed);
            typeCardFragment.setArguments(bundle);
            fragmentList.add(typeCardFragment);
            mTabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FFFFFF"));

        } else {
            setTitle(R.string.txt_select_visit_people);
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
