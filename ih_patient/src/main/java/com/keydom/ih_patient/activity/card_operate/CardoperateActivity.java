package com.keydom.ih_patient.activity.card_operate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.card_operate.controller.CardOperateController;
import com.keydom.ih_patient.activity.card_operate.view.CardOperateView;
import com.keydom.ih_patient.adapter.ViewPagerAdapter;
import com.keydom.ih_patient.bean.Event;
import com.keydom.ih_patient.constant.EventType;
import com.keydom.ih_patient.fragment.CardBindFragment;
import com.keydom.ih_patient.fragment.CardCreateFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
*@Author: LiuJie
*@Date: 2019/3/4 0004
*@Desc: 办卡绑卡页面
*/
public class CardoperateActivity extends BaseControllerActivity<CardOperateController> implements CardOperateView {
    private TextView cardOperateTv;
    private TabLayout cardOperateTab;
    private ViewPager cardOperateVp;
    private LinearLayout card_operate_layout,bind_success_layout;
    private ViewPagerAdapter viewPagerAdapter;
    private FragmentManager fm;
    private List<Fragment> fragmentList=new ArrayList<>();
    private List<String> list=new ArrayList<>();
    @Override
    public int getLayoutRes() {
        return R.layout.activity_create_or_bind_card_layout;
    }

    /**
     * 启动方法
     */
    public static void start(Context context){
        context.startActivity(new Intent(context,CardoperateActivity.class));
    }
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.creat_or_bind_card_title));
        getTitleLayout().initViewsVisible(true,true,false);
        card_operate_layout=this.findViewById(R.id.card_operate_layout);
        bind_success_layout=this.findViewById(R.id.bind_success_layout);
        cardOperateTv=this.findViewById(R.id.card_operate_tv);
        cardOperateTv.setOnClickListener(getController());
        cardOperateTab=this.findViewById(R.id.card_operate_tab);
        cardOperateVp=findViewById(R.id.card_operate_vp);
        list.add(getString(R.string.creat_or_bind_card_func_create));
        list.add(getString(R.string.creat_or_bind_card_func_bind));
        fm=getSupportFragmentManager();
        fragmentList.add(new CardCreateFragment());
        fragmentList.add(new CardBindFragment());
        if(viewPagerAdapter==null){
            viewPagerAdapter=new ViewPagerAdapter(fm,fragmentList,list);
        }
        cardOperateVp.setAdapter(viewPagerAdapter);
        cardOperateTab.setupWithViewPager(cardOperateVp);
        EventBus.getDefault().register(getContext());
    }

    /**
     * 绑卡成功监听
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void bindSuccess(Event event){
        if(event.getType()==EventType.CARDBINDSUCCESS){
            card_operate_layout.setVisibility(View.GONE);
            bind_success_layout.setVisibility(View.VISIBLE);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(getContext());
    }
}
