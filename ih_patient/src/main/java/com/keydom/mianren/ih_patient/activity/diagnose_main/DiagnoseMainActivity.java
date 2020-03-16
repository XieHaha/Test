package com.keydom.mianren.ih_patient.activity.diagnose_main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.diagnose_main.controller.DiagnoseMainController;
import com.keydom.mianren.ih_patient.activity.diagnose_main.view.DiagnoseMainView;
import com.keydom.mianren.ih_patient.fragment.TabDiagnoseFragment;

import org.jetbrains.annotations.Nullable;

public class DiagnoseMainActivity extends BaseControllerActivity<DiagnoseMainController> implements DiagnoseMainView {

    FrameLayout mContainerFl;

    //fragment容器
    private FragmentManager mFragmentManager;
    //当前Fragment
    private Fragment mCurrentFragment;

    private TabDiagnoseFragment mTabDiagnoseFragment;

    /**
     * 启动
     */
    public static void start(Context context) {
        Intent intent = new Intent(context, DiagnoseMainActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_diagnose_main;
    }

    @Override
    public void beforeInit() {
        //获取管理者
        mFragmentManager = getSupportFragmentManager();
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        getTitleLayout().initViewsVisible(true,true,false);
        setTitle("在线问诊");

        mContainerFl = findViewById(R.id.diagnose_main_fl);

        mTabDiagnoseFragment = new TabDiagnoseFragment();

        switchFragment(mTabDiagnoseFragment);
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
                fragmentTransaction.add(R.id.diagnose_main_fl, fragment).commit();
            } else {
                fragmentTransaction.hide(mCurrentFragment).add(R.id.diagnose_main_fl, fragment).commit();
            }

        } else {
            // 隐藏当前的fragment，显示下一个
            if (null == mCurrentFragment) {
                fragmentTransaction.add(R.id.diagnose_main_fl, fragment).commit();
            } else {
                fragmentTransaction.hide(mCurrentFragment).show(fragment).commit();
            }
        }
        mCurrentFragment = fragment;
    }
}
