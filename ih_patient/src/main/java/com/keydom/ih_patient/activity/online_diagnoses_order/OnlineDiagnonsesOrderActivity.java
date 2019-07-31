package com.keydom.ih_patient.activity.online_diagnoses_order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.online_diagnoses_order.controller.OnlineDiagnonsesOrderController;
import com.keydom.ih_patient.adapter.FragmentViewPagerAdapter;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * 在线问诊订单页面
 */
public class OnlineDiagnonsesOrderActivity extends BaseControllerActivity<OnlineDiagnonsesOrderController>{

    /**
     * 启动
     */
    public static void start(Context context,int index){
        Intent intent=new Intent(context,OnlineDiagnonsesOrderActivity.class);
        intent.putExtra("index",index);
        context.startActivity(intent);
    }
    public static final int WAITEDIAGNOSES=0;
    public static final int DIAGNOSESING=1;
    public static final int COMPLETEDIAGNOSES=2;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private FragmentViewPagerAdapter mViewPagerAdapter;
    private FragmentManager fm;
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> list = new ArrayList<>();
    private int index;
    @Override
    public int getLayoutRes() {
        return R.layout.activity_subscirbe_examination_order;
    }
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("问诊订单");
        index=getIntent().getIntExtra("index",WAITEDIAGNOSES);
        getView();
        list.add("待诊");
        list.add("问诊中");
        list.add("完成");
        fragmentList.add(OnlineDiagnonsesOrderFragment.newInstance(OnlineDiagnonsesOrderController.WAITINGDIAGNONSES));
        fragmentList.add(OnlineDiagnonsesOrderFragment.newInstance(OnlineDiagnonsesOrderController.DIAGNONSING));
        fragmentList.add(OnlineDiagnonsesOrderFragment.newInstance(OnlineDiagnonsesOrderController.COMPLETEIAGNONSES));
        fm = getSupportFragmentManager();
        mViewPagerAdapter = new FragmentViewPagerAdapter(fm, fragmentList, list);
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setAdapter(mViewPagerAdapter);
        LinearLayout linearLayout = (LinearLayout) mTabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this,
                R.drawable.layout_divider_vertical));
        mTabLayout.setTabTextColors(getResources().getColor(R.color.tab_nol_color), getResources().getColor(R.color.list_tab_color));
        mTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.list_tab_color));
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.getTabAt(index).select();
    }
    private void getView() {
        mTabLayout = findViewById(R.id.subscribe_tab);
        mViewPager = findViewById(R.id.subscribe_vp);
    }
}
