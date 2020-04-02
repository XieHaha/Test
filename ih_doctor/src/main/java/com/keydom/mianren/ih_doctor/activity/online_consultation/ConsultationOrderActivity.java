package com.keydom.mianren.ih_doctor.activity.online_consultation;

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
import com.keydom.mianren.ih_doctor.activity.online_consultation.controller.ConsultationOrderController;
import com.keydom.mianren.ih_doctor.activity.online_consultation.fragment.ConsultationOrderFragment;
import com.keydom.mianren.ih_doctor.activity.online_consultation.view.ConsultationOrderView;
import com.keydom.mianren.ih_doctor.constant.TypeEnum;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;

/**
 * @date 4月2日 15:03
 * 会诊列表
 */
public class ConsultationOrderActivity extends BaseControllerActivity<ConsultationOrderController> implements ConsultationOrderView {
    @BindView(R.id.consultation_order_tab_layout)
    TabLayout consultationOrderTabLayout;
    @BindView(R.id.consultation_order_view_pager)
    ViewPager consultationOrderViewPager;
    private Fragment[] mFragmentArrays;
    private String[] mTabTitles;

    /**
     * 启动会诊订单列表页面
     */
    public static void start(Context context) {
        Intent intent = new Intent(context, ConsultationOrderActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_consultation_order;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("多学科会诊");

        LinearLayout linearLayout = (LinearLayout) consultationOrderTabLayout.getChildAt(0);
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
        mTabTitles[0] = "待会诊";
        mTabTitles[1] = "会诊中";
        mTabTitles[2] = "会诊完成";
        consultationOrderTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mFragmentArrays[0] = ConsultationOrderFragment.newInstance(TypeEnum.CONSULTATION_WAIT);
        mFragmentArrays[1] = ConsultationOrderFragment.newInstance(TypeEnum.CONSULTATION_ING);
        mFragmentArrays[2] = ConsultationOrderFragment.newInstance(TypeEnum.CONSULTATION_COMPLETE);
        consultationOrderViewPager.setOffscreenPageLimit(3);
        PagerAdapter pagerAdapter = new TabViewPagerAdapter(getSupportFragmentManager());
        consultationOrderViewPager.setAdapter(pagerAdapter);
        consultationOrderTabLayout.setupWithViewPager(consultationOrderViewPager);
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
