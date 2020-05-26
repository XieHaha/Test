package com.keydom.mianren.ih_doctor.activity.online_consultation.fragment;

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

import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.online_consultation.controller.ConsultationOrderController;
import com.keydom.mianren.ih_doctor.activity.online_consultation.view.ConsultationOrderView;
import com.keydom.mianren.ih_doctor.bean.InquiryBean;
import com.keydom.mianren.ih_doctor.constant.TypeEnum;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;

/**
 * @date 4月2日 15:03
 * 会诊列表
 */
public class ConsultationPatientOrderFragment extends BaseControllerFragment<ConsultationOrderController> implements ConsultationOrderView {
    @BindView(R.id.consultation_order_tab_layout)
    TabLayout consultationOrderTabLayout;
    @BindView(R.id.consultation_order_view_pager)
    ViewPager consultationOrderViewPager;
    private Fragment[] mFragmentArrays;
    private String[] mTabTitles;

    private InquiryBean inquiryBean;

    /**
     * 启动会诊订单列表页面
     */
    public static void start(Context context) {
        Intent intent = new Intent(context, ConsultationPatientOrderFragment.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_consultation_patient_order;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        LinearLayout linearLayout = (LinearLayout) consultationOrderTabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(getContext(),
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
        mFragmentArrays[0] = ConsultationOrderFragment.newInstance(TypeEnum.CONSULTATION_WAIT,
                inquiryBean.getPatientId());
        mFragmentArrays[1] = ConsultationOrderFragment.newInstance(TypeEnum.CONSULTATION_ING,
                inquiryBean.getPatientId());
        mFragmentArrays[2] = ConsultationOrderFragment.newInstance(TypeEnum.CONSULTATION_COMPLETE
                , inquiryBean.getPatientId());
        consultationOrderViewPager.setOffscreenPageLimit(2);
        PagerAdapter pagerAdapter = new TabViewPagerAdapter(getChildFragmentManager());
        consultationOrderViewPager.setAdapter(pagerAdapter);
        consultationOrderTabLayout.setupWithViewPager(consultationOrderViewPager);
    }

    public void setInquiryBean(InquiryBean inquiryBean) {
        this.inquiryBean = inquiryBean;
    }

    @Override
    public void onDestroy() {
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
