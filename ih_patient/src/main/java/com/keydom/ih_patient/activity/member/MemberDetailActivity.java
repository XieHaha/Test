package com.keydom.ih_patient.activity.member;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.member.controller.MemberDetailController;
import com.keydom.ih_patient.activity.member.view.MemberDetailView;
import com.keydom.ih_patient.fragment.TabMemberFragment;

public class MemberDetailActivity extends BaseControllerActivity<MemberDetailController> implements MemberDetailView {



    //fragment容器
    private FragmentManager mFragmentManager;
    //当前Fragment
    private Fragment mCurrentFragment;

    private TabMemberFragment mTabMemberFragment;

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

        switchFragment(mTabMemberFragment);
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
}
