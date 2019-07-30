package com.keydom.ih_doctor.activity.controller;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.keydom.ih_common.utils.StatusBarUtils;
import com.keydom.ih_common.view.ScrollControlViewPager;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.MainActivity;
import com.keydom.ih_doctor.adapter.ViewPagerAdapter;
import com.keydom.ih_doctor.fragment.CommunityFragment;
import com.keydom.ih_doctor.fragment.PatientManageFragment;
import com.keydom.ih_doctor.fragment.PersonalFragment;
import com.keydom.ih_doctor.fragment.WorkFragment;
import com.keydom.ih_doctor.view.MainView;

import java.util.ArrayList;
import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.controller
 * @Description：描述信息
 * @Author：song
 * @Date：18/11/8 下午7:52
 * 修改人：xusong
 * 修改时间：18/11/8 下午7:52
 */
public class MainController implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private ScrollControlViewPager scrollControlViewPager;
    private CommunityFragment communityFragment;
    private PersonalFragment personalFragment;
    private WorkFragment workFragment;
    private PatientManageFragment patientManageFragment;
    private MainActivity mContext;
    private MainView mainView;

    public MainController(MainActivity context, MainView mainView) {
        this.mContext = context;
        this.mainView = mainView;
        initView();
        setViewPager();
    }

    private void initView() {
        scrollControlViewPager = (ScrollControlViewPager) mainView.findViewById(R.id.viewpager);
        scrollControlViewPager.setOffscreenPageLimit(4);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0) {
            indexStatus();
        } else {
            withOutIndexStatus();
        }
        mainView.setButtonColor(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tab_work:
                indexStatus();
                mainView.setCurrentItem(0, false);
                break;
            case R.id.tab_patient_manage:
                withOutIndexStatus();
                mainView.setCurrentItem(1, false);
                break;
            case R.id.tab_community:
                withOutIndexStatus();
                mainView.setCurrentItem(2, false);
                break;
            case R.id.tab_personal_center:
                withOutIndexStatus();
                mainView.setCurrentItem(3, false);
                break;
        }

    }


    /**
     * 设置fragment
     */
    private void setViewPager() {
        final List<Fragment> fragments = new ArrayList<>();
        communityFragment = new CommunityFragment();
        patientManageFragment = new PatientManageFragment();
        personalFragment = new PersonalFragment();
        workFragment = new WorkFragment();
        fragments.add(workFragment);
        fragments.add(patientManageFragment);
        fragments.add(communityFragment);
        fragments.add(personalFragment);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(mContext.getSupportFragmentManager(),
                fragments);
        mainView.setViewPagerAdapter(viewPagerAdapter);

    }

    /**
     * 首页的状态栏颜色
     */
    private void indexStatus() {
        mContext.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        StatusBarUtils.setWindowStatusBarColor(mContext, R.color.status_bar_color_work);
    }

    /**
     * 非首页的状态栏颜色
     */
    private void withOutIndexStatus() {
        StatusBarUtils.setWindowStatusBarColor(mContext, R.color.primary_bg_color);
        mContext.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }
}
