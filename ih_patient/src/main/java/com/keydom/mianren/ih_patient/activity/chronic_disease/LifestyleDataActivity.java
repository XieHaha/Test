package com.keydom.mianren.ih_patient.activity.chronic_disease;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.chronic_disease.controller.LifestyleDataController;
import com.keydom.mianren.ih_patient.activity.chronic_disease.fragment.LifestyleDataFragment;
import com.keydom.mianren.ih_patient.activity.chronic_disease.view.LifestyleDataView;
import com.keydom.mianren.ih_patient.adapter.ViewPagerAdapter;
import com.keydom.mianren.ih_patient.constant.Const;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author 顿顿
 * @date 21/3/17 14:27
 * @des 生活数据
 */
public class LifestyleDataActivity extends BaseControllerActivity<LifestyleDataController> implements LifestyleDataView {
    @BindView(R.id.lifestyle_data_tab_layout)
    TabLayout lifestyleDataTabLayout;
    @BindView(R.id.lifestyle_data_view_pager)
    ViewPager lifestyleDataViewPager;
    @BindView(R.id.lifestyle_data_select_hint_tv)
    TextView lifestyleDataSelectHintTv;
    @BindView(R.id.lifestyle_data_select_num_tv)
    TextView lifestyleDataSelectNumTv;
    @BindView(R.id.lifestyle_data_select_sure_tv)
    TextView lifestyleDataSelectSureTv;

    private FragmentManager fm;
    private ViewPagerAdapter viewPagerAdapter;

    List<Fragment> fragmentList = new ArrayList<>();
    List<String> titles = new ArrayList<>();
    /**
     * 饮食
     */
    public static final int LIFESTYLE_DIET = 1;
    /**
     * 运动
     */
    public static final int LIFESTYLE_SPORTS = 3;

    private int lifestyleType = -1;

    /**
     * 启动
     */
    public static void start(Context context, int type) {
        Intent intent = new Intent(context, LifestyleDataActivity.class);
        intent.putExtra(Const.TYPE, type);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_lifestyle_data;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        lifestyleType = getIntent().getIntExtra(Const.TYPE, -1);
        if (lifestyleType == LIFESTYLE_DIET) {
            setTitle("食物库");
            lifestyleDataSelectHintTv.setText("已选择食物");
        } else {
            setTitle("运动选择");
            lifestyleDataSelectHintTv.setText("已选择运动");
        }

        lifestyleDataSelectSureTv.setOnClickListener(getController());
        fm = getSupportFragmentManager();
        fragmentList.add(LifestyleDataFragment.newInstance(1, lifestyleType));
        fragmentList.add(LifestyleDataFragment.newInstance(1, lifestyleType));
        fragmentList.add(LifestyleDataFragment.newInstance(1, lifestyleType));
        fragmentList.add(LifestyleDataFragment.newInstance(1, lifestyleType));
        fragmentList.add(LifestyleDataFragment.newInstance(1, lifestyleType));
        fragmentList.add(LifestyleDataFragment.newInstance(1, lifestyleType));
        fragmentList.add(LifestyleDataFragment.newInstance(1, lifestyleType));

        titles.add("测试");
        titles.add("测试2");
        titles.add("测试3");
        titles.add("测试4");
        titles.add("测试5");
        titles.add("测试6");
        titles.add("测试7");

        if (viewPagerAdapter == null) {
            viewPagerAdapter = new ViewPagerAdapter(fm, fragmentList, titles);
        }
        lifestyleDataViewPager.setAdapter(viewPagerAdapter);
        lifestyleDataViewPager.setOffscreenPageLimit(3);

        for (int i = 0; i < titles.size(); i++) {
            lifestyleDataTabLayout.addTab(lifestyleDataTabLayout.newTab());
        }
        lifestyleDataTabLayout.setupWithViewPager(lifestyleDataViewPager);

        for (int i = 0; i < titles.size(); i++) {
            //自定义view
            TabLayout.Tab tab = lifestyleDataTabLayout.getTabAt(i);
            tab.setCustomView(makeTabView(tab, i));
        }

        //默认选中第一个
        ViewHolder holder = (ViewHolder) lifestyleDataTabLayout.getTabAt(0).getTag();
        holder.imageView.setVisibility(View.VISIBLE);
        holder.textView.setTextColor(ContextCompat.getColor(LifestyleDataActivity.this,
                R.color.color_57a7fc));

        lifestyleDataTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ViewHolder holder = (ViewHolder) tab.getTag();
                holder.imageView.setVisibility(View.VISIBLE);
                holder.textView.setTextColor(ContextCompat.getColor(LifestyleDataActivity.this,
                        R.color.color_57a7fc));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                ViewHolder holder = (ViewHolder) tab.getTag();
                holder.imageView.setVisibility(View.INVISIBLE);
                holder.textView.setTextColor(ContextCompat.getColor(LifestyleDataActivity.this,
                        R.color.color_333333));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    /**
     * 引入布局设置图标和标题
     */
    private View makeTabView(TabLayout.Tab tab, int position) {
        ViewHolder holder = new ViewHolder();
        View tabView =
                getLayoutInflater().inflate(R.layout.view_lifestyle_data_title_layout, null);
        holder.textView = tabView.findViewById(R.id.view_lifestyle_data_tv);
        holder.imageView = tabView.findViewById(R.id.view_lifestyle_data_iv);
        holder.textView.setText(titles.get(position));
        tab.setTag(holder);
        return tabView;
    }

    static class ViewHolder {
        private ImageView imageView;
        private TextView textView;
    }
}
