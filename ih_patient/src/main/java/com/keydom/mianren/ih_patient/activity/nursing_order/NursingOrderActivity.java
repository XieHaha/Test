package com.keydom.mianren.ih_patient.activity.nursing_order;

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
import com.keydom.mianren.ih_patient.activity.nursing_order.controller.NursingOrderController;
import com.keydom.mianren.ih_patient.activity.nursing_order.view.NursingOrderView;
import com.keydom.mianren.ih_patient.adapter.FragmentViewPagerAdapter;
import com.keydom.mianren.ih_patient.fragment.NursingOrderFragment;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * created date: 2018/12/12 on 14:44
 * des:护理订单activity
 */
public class NursingOrderActivity extends BaseControllerActivity<NursingOrderController> implements NursingOrderView {

    /**
     * 启动
     */
    public static void start(Context context, int index){
        Intent intent=new Intent(context,NursingOrderActivity.class);
        intent.putExtra("index",index);
        context.startActivity(intent);
    }
    public static final int WAITEDNURSE=0;
    public static final int NURSING=1;
    public static final int COMPLETENURSE=2;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private FragmentViewPagerAdapter mViewPagerAdapter;
    private FragmentManager fm;
    private int index;


    /**
     * fragment集合
     */
    private List<Fragment> fragmentList = new ArrayList<>();
    /**
     * 标题集合
     */
    private List<String> list = new ArrayList<>();

    @Override
    public int getLayoutRes() {
        return R.layout.activity_nursing_order;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        getView();
    }

    /**
     * 查找控件
     */
    private void getView(){
        setTitle("护理订单");
        index=getIntent().getIntExtra("index",WAITEDNURSE);
        mTabLayout = findViewById(R.id.nursing_tab);
        mViewPager = findViewById(R.id.nursing_vp);
        list.add("待服务");
        list.add("服务中");
        list.add("完成");
        fragmentList.add(NursingOrderFragment.newInstance(NursingOrderFragment.WAIT_SERVICE));
        fragmentList.add(NursingOrderFragment.newInstance(NursingOrderFragment.SERVICING));
        fragmentList.add(NursingOrderFragment.newInstance(NursingOrderFragment.COMPLETE));
        fm = getSupportFragmentManager();
        mViewPagerAdapter = new FragmentViewPagerAdapter(fm, fragmentList, list);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(mViewPagerAdapter);
        LinearLayout linearLayout = (LinearLayout) mTabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this,
                R.drawable.layout_divider_vertical));
        mTabLayout.setTabTextColors(getResources().getColor(R.color.title_bar_text_color), getResources().getColor(R.color.list_tab_color));
        mTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.list_tab_color));
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.getTabAt(index).select();
    }
}
