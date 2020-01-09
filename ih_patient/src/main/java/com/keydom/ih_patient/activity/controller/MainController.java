package com.keydom.ih_patient.activity.controller;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.keydom.ih_common.utils.StatusBarUtils;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.index_main.MainActivity;
import com.keydom.ih_patient.adapter.ViewPagerAdapter;
import com.keydom.ih_patient.fragment.TabIndexFragment;
import com.keydom.ih_patient.fragment.TabMineFragment;
import com.keydom.ih_patient.view.MainView;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description：主页控制器
 * @Author：song
 * @Date：18/11/8 下午7:21
 * 修改人：xusong
 * 修改时间：18/11/8 下午7:21
 */
public class MainController implements View.OnClickListener,ViewPager.OnPageChangeListener{

    private MainView mainView;
    private MainActivity mContext;
    private TabIndexFragment tabIndexFragment;
    private TabMineFragment tabMineFragment;

    /**
     * 构造方法
     */
    public MainController(MainActivity context, MainView mainView){

        this.mainView=mainView;
        this.mContext=context;
        setViewPager();
        mContext.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        StatusBarUtils.setWindowStatusBarColor(mContext,R.color.primary_color);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mainView.setButtonColor(position);

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab_index:
                mContext.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                StatusBarUtils.setWindowStatusBarColor(mContext,R.color.primary_color);
                mainView.setCurrentItem(0, false);
                break;
            case R.id.tab_mine:
                mContext.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                StatusBarUtils.setWindowStatusBarColor(mContext,R.color.mine_color);
                mainView.setCurrentItem(1, false);
                break;
            case R.id.tab_member:
                break;
        }
    }

    /**
     * 设置viewpager
     */
    private void setViewPager() {
        final List<Fragment> fragments = new ArrayList<>();
        tabIndexFragment = new TabIndexFragment();
        tabMineFragment = new TabMineFragment();

        fragments.add(tabIndexFragment);
        fragments.add(tabMineFragment);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(mContext.getSupportFragmentManager(),fragments);
        mainView.setViewPagerAdapter(viewPagerAdapter);

    }

}
