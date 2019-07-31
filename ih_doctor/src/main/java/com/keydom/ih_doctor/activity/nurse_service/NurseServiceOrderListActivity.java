package com.keydom.ih_doctor.activity.nurse_service;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_doctor.MyApplication;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.nurse_service.controller.ServiceOrderListController;
import com.keydom.ih_doctor.activity.nurse_service.view.ServiceOrderListView;
import com.keydom.ih_doctor.bean.Event;
import com.keydom.ih_doctor.bean.MessageEvent;
import com.keydom.ih_doctor.bean.OrderStatisticBean;
import com.keydom.ih_doctor.constant.Const;
import com.keydom.ih_doctor.constant.EventType;
import com.keydom.ih_doctor.constant.TypeEnum;
import com.keydom.ih_doctor.fragment.NurseServiceOrderFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * @Name：com.keydom.ih_doctor.activity
 * @Author：song
 * @Date：18/11/19 上午11:34
 * 修改人：xusong
 * 修改时间：18/11/19 上午11:34
 */
public class NurseServiceOrderListActivity extends BaseControllerActivity<ServiceOrderListController> implements ServiceOrderListView {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Fragment[] mFragmentArrays;

    private EditText searchInputEv;
    private TextView searchTv,order_num_tv;
    private LinearLayout order_num_layout;
    /**
     * tab列表
     */
    private String[] mTabTitles;
    /**
     * 判断是护士长还是护士
     */
    private TypeEnum mType;

    /**
     * 护士长入口
     *
     * @param context
     */
    public static void headNurseStart(Context context) {
        Intent starter = new Intent(context, NurseServiceOrderListActivity.class);
        starter.putExtra(Const.TYPE, TypeEnum.HEAD_NURSE);
        context.startActivity(starter);
    }

    /**
     * 普通护士入口
     *
     * @param context
     */
    public static void commonNurseStart(Context context) {
        Intent starter = new Intent(context, NurseServiceOrderListActivity.class);
        starter.putExtra(Const.TYPE, TypeEnum.COMMON_NURSE);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_prescription_check_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        mType = (TypeEnum) getIntent().getSerializableExtra(Const.TYPE);
        setTitle("护理服务订单");
        order_num_layout=this.findViewById(R.id.order_num_layout);
        order_num_tv=this.findViewById(R.id.order_num_tv);
        tabLayout = this.findViewById(R.id.tablayout);
        viewPager = this.findViewById(R.id.tab_viewpager);
        searchInputEv = this.findViewById(R.id.search_input_ev);
        searchTv = this.findViewById(R.id.search_tv);
        searchTv.setOnClickListener(getController());
        if (mType == TypeEnum.HEAD_NURSE) {
            order_num_layout.setVisibility(View.VISIBLE);
            getController().getOrderStatistic(getStatisticMap());
            initNurseManager();
        } else {
            order_num_layout.setVisibility(View.GONE);
            initNurse();
        }


    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        if (messageEvent.getType() == EventType.UPDATENURSENUM) {
            if (mType == TypeEnum.HEAD_NURSE) {
                getController().getOrderStatistic(getStatisticMap());
            }
        }
    }

    @Override
    public Map<String,Object> getStatisticMap(){
        Map<String,Object> map=new HashMap<>();
        map.put("keyword",searchInputEv.getText().toString().trim());
        map.put("nurseId",MyApplication.userInfo.getId());
        return map;
    }


    /**
     * 设置护士长页面
     */
    private void initNurseManager() {
        PagerAdapter pagerAdapter = new TabViewPagerAdapter(getSupportFragmentManager());
        mTabTitles = new String[2];
        mFragmentArrays = new Fragment[2];
        mTabTitles[0] = "未接单";
        mTabTitles[1] = "已接单";
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        mFragmentArrays[0] = NurseServiceOrderFragment.newInstance(TypeEnum.HEAD_NURSE_FRAGMENT_RECEIVE_ORDER);
        mFragmentArrays[1] = NurseServiceOrderFragment.newInstance(TypeEnum.HEAD_NURSE_FRAGMENT_ALREADY_RECEIVE_ORDER);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }


    /**
     * 设置普通护士页面
     */
    private void initNurse() {
        mTabTitles = new String[3];
        mFragmentArrays = new Fragment[3];
        mTabTitles[0] = "待接收";
        mTabTitles[1] = "服务中";
        mTabTitles[2] = "已完成";
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        mFragmentArrays[0] = NurseServiceOrderFragment.newInstance(TypeEnum.COMMON_NURSE_FRAGMENT_RECEIVE_ORDER);
        mFragmentArrays[1] = NurseServiceOrderFragment.newInstance(TypeEnum.COMMON_NURSE_FRAGMNET_WORKING_ORDER);
        mFragmentArrays[2] = NurseServiceOrderFragment.newInstance(TypeEnum.COMMON_NURSE_FRAGMENT_FINISH_ORDER);
        viewPager.setOffscreenPageLimit(3);
        PagerAdapter pagerAdapter = new TabViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public TypeEnum getType() {
        return null;
    }

    @Override
    public String getKeyword() {
        return searchInputEv.getText().toString().trim();
    }

    @Override
    public void getOrderStatistic(OrderStatisticBean data) {
        order_num_tv.setText("未接单："+data.getExpireNum()+"单（即将过期）/"+data.getAllNum()+"单");
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
