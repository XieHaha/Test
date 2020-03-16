package com.keydom.mianren.ih_patient.activity.subscribe_examination_order;

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
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.subscribe_examination_order.controller.SubscribeExaminationController;
import com.keydom.mianren.ih_patient.activity.subscribe_examination_order.view.SubscribeExaminationView;
import com.keydom.mianren.ih_patient.adapter.FragmentViewPagerAdapter;
import com.keydom.mianren.ih_patient.fragment.SubscribeExaminationOrderFragment;
import com.keydom.mianren.ih_patient.fragment.controller.SubscribeExaminationOrderController;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * created date: 2018/12/12 on 14:44
 * des:预约体检订单activity
 */
public class SubscribeExaminationActivity extends BaseControllerActivity<SubscribeExaminationController> implements SubscribeExaminationView {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private FragmentViewPagerAdapter mViewPagerAdapter;
    private FragmentManager fm;
    /**
     * fragment集合
     */
    private List<Fragment> fragmentList = new ArrayList<>();
    /**
     * 标题集合
     */
    private List<String> list = new ArrayList<>();

    /**
     * 启动
     */
    public static void start(Context context) {
        context.startActivity(new Intent(context, SubscribeExaminationActivity.class));
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_subscirbe_examination_order;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.subscribe_examination_order_title));
        getView();

        list.add("全部");
        list.add("待付款");
        list.add("待体检");
        list.add("已完成");
        fragmentList.add(SubscribeExaminationOrderFragment.newInstance(SubscribeExaminationOrderController.ALL));
        fragmentList.add(SubscribeExaminationOrderFragment.newInstance(SubscribeExaminationOrderController.PAY));
        fragmentList.add(SubscribeExaminationOrderFragment.newInstance(SubscribeExaminationOrderController.EXAMINATION));
        fragmentList.add(SubscribeExaminationOrderFragment.newInstance(SubscribeExaminationOrderController.COMPELETE));
        fm = getSupportFragmentManager();
        mViewPagerAdapter = new FragmentViewPagerAdapter(fm, fragmentList, list);
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setAdapter(mViewPagerAdapter);
        LinearLayout linearLayout = (LinearLayout) mTabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this,
                R.drawable.layout_divider_vertical));
        mTabLayout.setTabTextColors(getResources().getColor(R.color.title_bar_text_color), getResources().getColor(R.color.list_tab_color));
        mTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.list_tab_color));
        mTabLayout.setupWithViewPager(mViewPager);
    }

    /**
     * 查找控件
     */
    private void getView() {
        mTabLayout = findViewById(R.id.subscribe_tab);
        mViewPager = findViewById(R.id.subscribe_vp);
    }
}
