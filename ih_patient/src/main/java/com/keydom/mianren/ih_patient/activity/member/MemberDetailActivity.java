package com.keydom.mianren.ih_patient.activity.member;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.member.controller.MemberDetailController;
import com.keydom.mianren.ih_patient.activity.member.view.MemberDetailView;
import com.keydom.mianren.ih_patient.bean.Event;
import com.keydom.mianren.ih_patient.constant.EventType;
import com.keydom.mianren.ih_patient.constant.Global;
import com.keydom.mianren.ih_patient.fragment.TabMemberFragment;
import com.keydom.mianren.ih_patient.fragment.VIPMemberDetailFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MemberDetailActivity extends BaseControllerActivity<MemberDetailController> implements MemberDetailView {



    //fragment容器
    private FragmentManager mFragmentManager;
    //当前Fragment
    private Fragment mCurrentFragment;

    private TabMemberFragment mTabMemberFragment;
    private VIPMemberDetailFragment mVIPMemberDetailFragment;

    /**
     * 启动
     */
    public static void start(Context context) {
        Intent intent = new Intent(context, MemberDetailActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_member_detail;
    }

    @Override
    public void beforeInit() {
        //获取管理者
        mFragmentManager = getSupportFragmentManager();
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        getTitleLayout().initViewsVisible(true,true,false);
        setTitle("仁医金卡签约");

        mTabMemberFragment = new TabMemberFragment();
        mVIPMemberDetailFragment = new VIPMemberDetailFragment();

        memberShow();

        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 切换Fragment
     */
    private void switchFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        // 先判断是否被add过
        if (!fragment.isAdded()) {
            // 隐藏当前的fragment，add新fragment
            if (null == mCurrentFragment) {
                fragmentTransaction.add(R.id.member_detail_fl, fragment).commit();
            } else {
                fragmentTransaction.hide(mCurrentFragment).add(R.id.member_detail_fl, fragment).commit();
            }

        } else {
            // 隐藏当前的fragment，显示下一个
            if (null == mCurrentFragment) {
                fragmentTransaction.add(R.id.member_detail_fl, fragment).commit();
            } else {
                fragmentTransaction.hide(mCurrentFragment).show(fragment).commit();
            }
        }
        mCurrentFragment = fragment;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRecieve(Event event) {
        if (event.getType() == EventType.UPDATELOGINSTATE) {
            memberShow();
        }
    }


    private void memberShow() {
        if (Global.isMember()) {
            switchFragment(mVIPMemberDetailFragment);
        } else {
            switchFragment(mTabMemberFragment);
        }
    }
}
