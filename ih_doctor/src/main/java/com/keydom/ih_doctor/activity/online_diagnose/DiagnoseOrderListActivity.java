package com.keydom.ih_doctor.activity.online_diagnose;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.online_diagnose.controller.DiagnoseOrderListController;
import com.keydom.ih_doctor.activity.online_diagnose.view.DiagnoseOrderListView;
import com.keydom.ih_doctor.constant.Const;
import com.keydom.ih_doctor.constant.TypeEnum;
import com.keydom.ih_doctor.fragment.DiagnoseOrderFragment;

import org.jetbrains.annotations.Nullable;

/**
 * @Name：com.keydom.ih_doctor.activity
 * @Description：问诊单列表主页面
 * @Author：song
 * @Date：18/11/19 上午11:34
 * 修改人：xusong
 * 修改时间：18/11/19 上午11:34
 */
public class DiagnoseOrderListActivity extends BaseControllerActivity<DiagnoseOrderListController> implements DiagnoseOrderListView {
    /**
     * 问诊单类型
     */
    private static final int DIAGNOSE = 620;
    /**
     * 咨询单类型
     */
    private static final int CONSULT = 621;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Fragment[] mFragmentArrays;
    private String[] mTabTitles;
    /**
     * fragment类型
     */
    private int type;

    /**
     * 启动问诊订单列表页面
     *
     * @param context
     */
    public static void startDiagnose(Context context) {
        Intent starter = new Intent(context, DiagnoseOrderListActivity.class);
        starter.putExtra(Const.TYPE, DIAGNOSE);
        context.startActivity(starter);
    }

    /**
     * 启动咨询单列表页面
     *
     * @param context
     */
    public static void startConsult(Context context) {
        Intent starter = new Intent(context, DiagnoseOrderListActivity.class);
        starter.putExtra(Const.TYPE, CONSULT);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_diagnose_order_list_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        type = getIntent().getIntExtra(Const.TYPE, 0);
        if (type == DIAGNOSE) {
            setTitle("问诊订单");
        } else {
            setTitle("咨询订单");
        }
        tabLayout = this.findViewById(R.id.tablayout);
        viewPager = this.findViewById(R.id.tab_viewpager);
        LinearLayout linearLayout = (LinearLayout) tabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this,
                R.drawable.layout_divider_vertical));
        initOrderListFragment();
    }


    /**
     * 设置fragment
     */
    private void initOrderListFragment() {
        mTabTitles = new String[3];
        mFragmentArrays = new Fragment[3];
        mTabTitles[0] = "待诊";
        mTabTitles[1] = "问诊中";
        mTabTitles[2] = "完成";
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        mFragmentArrays[0] = DiagnoseOrderFragment.newInstance(TypeEnum.ONLINE_DIAGNOSE_WAITTING);
        mFragmentArrays[1] = DiagnoseOrderFragment.newInstance(TypeEnum.ONLINE_DIAGNOSEINT);
        mFragmentArrays[2] = DiagnoseOrderFragment.newInstance(TypeEnum.ONLINE_DIAGNOSE_FINISH);
        viewPager.setOffscreenPageLimit(3);
        PagerAdapter pagerAdapter = new TabViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    final class TabViewPagerAdapter extends FragmentPagerAdapter {
        public TabViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentArrays[position];
        }


        @Override
        public int getCount() {
            return mFragmentArrays.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabTitles[position];

        }
    }
}
