package com.keydom.mianren.ih_doctor.activity.online_triage;

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
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.online_triage.controller.TriageOrderListController;
import com.keydom.mianren.ih_doctor.activity.online_triage.fragment.TriageOrderFragment;
import com.keydom.mianren.ih_doctor.activity.online_triage.view.TriageOrderListView;
import com.keydom.mianren.ih_doctor.constant.TypeEnum;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;

/**
 * @date 20/3/21 13:13
 * @des 分诊订单列表
 */
public class TriageOrderListActivity extends BaseControllerActivity<TriageOrderListController> implements TriageOrderListView {
    @BindView(R.id.triage_order_tab_layout)
    TabLayout triageOrderTabLayout;
    @BindView(R.id.triage_order_view_pager)
    ViewPager triageOrderViewPager;
    private Fragment[] mFragmentArrays;
    private String[] mTabTitles;

    /**
     * 启动会诊订单列表页面
     */
    public static void start(Context context) {
        Intent intent = new Intent(context, TriageOrderListActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_triage_order_list;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("分诊接收");
        LinearLayout linearLayout = (LinearLayout) triageOrderTabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this,
                R.drawable.layout_divider_vertical));
        initOrderListFragment();
    }


    /**
     * 设置fragment
     */
    private void initOrderListFragment() {
        mTabTitles = new String[2];
        mFragmentArrays = new Fragment[2];
        mTabTitles[0] = "待接收";
        mTabTitles[1] = "已接收";
        triageOrderTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mFragmentArrays[0] = TriageOrderFragment.newInstance(TypeEnum.TRIAGE_WAIT);
        mFragmentArrays[1] = TriageOrderFragment.newInstance(TypeEnum.TRIAGE_RECEIVED);
        triageOrderViewPager.setOffscreenPageLimit(2);
        PagerAdapter pagerAdapter = new TabViewPagerAdapter(getSupportFragmentManager());
        triageOrderViewPager.setAdapter(pagerAdapter);
        triageOrderTabLayout.setupWithViewPager(triageOrderViewPager);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    final class TabViewPagerAdapter extends FragmentPagerAdapter {
        private TabViewPagerAdapter(FragmentManager fm) {
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
