package com.keydom.mianren.ih_doctor.activity.online_diagnose;

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
import com.keydom.mianren.ih_doctor.activity.online_diagnose.controller.PrescriptionTempletController;
import com.keydom.mianren.ih_doctor.activity.online_diagnose.view.PrescriptionTempletView;
import com.keydom.mianren.ih_doctor.constant.Const;
import com.keydom.mianren.ih_doctor.constant.TypeEnum;
import com.keydom.mianren.ih_doctor.fragment.PrescriptionTempletFragment;

import org.jetbrains.annotations.Nullable;
/**
 *
 * @link
 *
 * Author: song
 *
 * Create: 19/3/7 上午10:49
 *
 * Changes (from 19/3/7)
 *
 * 19/3/7 : Create PrescriptionTempletActivity.java (song);
 *
 *
 *
 */
public class PrescriptionTempletActivity extends BaseControllerActivity<PrescriptionTempletController> implements PrescriptionTempletView {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Fragment[] mFragmentArrays;
    private String[] mTabTitles;
    private int isOutPrescription = -1;

    /**
     * 开启处方模版页面
     * @param context
     */
    public static void start(Context context,int isOutPrescription) {
        Intent intent = new Intent(context, PrescriptionTempletActivity.class);
        intent.putExtra(Const.IS_OUT_PRESCRIPTION, isOutPrescription);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_prescription_templet_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("处方模板");
        isOutPrescription = getIntent().getIntExtra(Const.IS_OUT_PRESCRIPTION, -1);
        tabLayout = this.findViewById(R.id.tablayout);
        viewPager = this.findViewById(R.id.tab_viewpager);
        LinearLayout linearLayout = (LinearLayout) tabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this,
                R.drawable.layout_divider_vertical));
        initFragment();
    }

    /**
     * 设置fragment
     */
    private void initFragment() {
        if(isOutPrescription < 0){ //都展示
            mTabTitles = new String[2];
            mFragmentArrays = new Fragment[2];
            mTabTitles[0] = "院内处方";
            mTabTitles[1] = "外延处方";
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
            mFragmentArrays[0] = PrescriptionTempletFragment.newInstance(TypeEnum.INSIDE_PRESCRIPTION);
            mFragmentArrays[1] = PrescriptionTempletFragment.newInstance(TypeEnum.OUTSIDE_PRESCRIPTION);

        }else if(isOutPrescription == 0){ //院内
            mTabTitles = new String[1];
            mFragmentArrays = new Fragment[1];
            mTabTitles[0] = "院内处方";
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
            mFragmentArrays[0] = PrescriptionTempletFragment.newInstance(TypeEnum.INSIDE_PRESCRIPTION);
        }else{ //外延
            mTabTitles = new String[1];
            mFragmentArrays = new Fragment[1];
            mTabTitles[0] = "外延处方";
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
            mFragmentArrays[0] = PrescriptionTempletFragment.newInstance(TypeEnum.OUTSIDE_PRESCRIPTION);
        }

        viewPager.setOffscreenPageLimit(2);
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
