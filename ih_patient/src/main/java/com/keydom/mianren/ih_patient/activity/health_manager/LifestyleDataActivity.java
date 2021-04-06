package com.keydom.mianren.ih_patient.activity.health_manager;

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
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.health_manager.controller.LifestyleDataController;
import com.keydom.mianren.ih_patient.activity.health_manager.fragment.LifestyleDataFragment;
import com.keydom.mianren.ih_patient.activity.health_manager.view.LifestyleDataView;
import com.keydom.mianren.ih_patient.adapter.ViewPagerAdapter;
import com.keydom.mianren.ih_patient.bean.EatBean;
import com.keydom.mianren.ih_patient.bean.EatRecordBean;
import com.keydom.mianren.ih_patient.bean.Event;
import com.keydom.mianren.ih_patient.bean.SportsItemBean;
import com.keydom.mianren.ih_patient.constant.Const;
import com.keydom.mianren.ih_patient.constant.EventType;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
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

    private EatRecordBean recordBean;
    private List<EatBean> selectEatBeans;
    private List<SportsItemBean> selectSportItemBeans = new ArrayList<>();

    private String patientId;
    private String curSelectDate;
    /**
     * 就餐类型
     */
    private int mealType;
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
    public static void start(Context context, int type, int mealType, String curSelectDate,
                             EatRecordBean recordBean, String patientId) {
        Intent intent = new Intent(context, LifestyleDataActivity.class);
        intent.putExtra("mealType", mealType);
        intent.putExtra("recordBean", (Serializable) recordBean);
        intent.putExtra("curSelectDate", curSelectDate);
        intent.putExtra(Const.TYPE, type);
        intent.putExtra(Const.PATIENT_ID, patientId);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_lifestyle_data;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mealType = getIntent().getIntExtra("mealType", -1);
        lifestyleType = getIntent().getIntExtra(Const.TYPE, -1);
        patientId = getIntent().getStringExtra(Const.PATIENT_ID);
        curSelectDate = getIntent().getStringExtra("curSelectDate");
        recordBean = (EatRecordBean) getIntent().getSerializableExtra("recordBean");
        switch (mealType) {
            case 0:
                selectEatBeans = recordBean.getBreakfastList();
                break;
            case 1:
                selectEatBeans = recordBean.getLunchList();
                break;
            case 2:
                selectEatBeans = recordBean.getDinnerList();
                break;
            case 3:
                selectEatBeans = recordBean.getSnacksList();
                break;
            default:
                break;
        }
        if (selectEatBeans == null) {
            selectEatBeans = new ArrayList<>();
        }

        if (lifestyleType == LIFESTYLE_DIET) {
            setTitle("食物库");
            lifestyleDataSelectHintTv.setText("已选择食物");
            String[] eatType = getResources().getStringArray(R.array.eat_type);
            titles = new ArrayList<>(eatType.length);
            Collections.addAll(titles, eatType);
        } else {
            setTitle("运动选择");
            lifestyleDataSelectHintTv.setText("已选择运动");
            String[] eatType = getResources().getStringArray(R.array.sports_type);
            titles = new ArrayList<>(eatType.length);
            Collections.addAll(titles, eatType);
        }

        lifestyleDataSelectSureTv.setOnClickListener(getController());
        fm = getSupportFragmentManager();

        initTabLayout();
    }

    private void initTabLayout() {
        for (int i = 0; i < titles.size(); i++) {
            fragmentList.add(LifestyleDataFragment.newInstance(i, lifestyleType, mealType,
                    curSelectDate, patientId, selectEatBeans, listener));
            lifestyleDataTabLayout.addTab(lifestyleDataTabLayout.newTab());
        }
        viewPagerAdapter = new ViewPagerAdapter(fm, fragmentList, titles);
        lifestyleDataViewPager.setAdapter(viewPagerAdapter);
        lifestyleDataViewPager.setOffscreenPageLimit(3);

        lifestyleDataTabLayout.setupWithViewPager(lifestyleDataViewPager);

        for (int i = 0; i < titles.size(); i++) {
            //自定义view
            TabLayout.Tab tab = lifestyleDataTabLayout.getTabAt(i);
            tab.setCustomView(makeTabView(tab, i));
        }

        //默认选中第一个
        ViewHolder holder = (ViewHolder) lifestyleDataTabLayout.getTabAt(0).getTag();
        holder.imageView.setVisibility(View.VISIBLE);
        holder.textView.setTextColor(ContextCompat.getColor(this, R.color.color_57a7fc));
        if (lifestyleType == LIFESTYLE_DIET) {
            holder.imageView.setImageResource(R.mipmap.icon_eat_top);
        } else {
            holder.imageView.setImageResource(R.mipmap.icon_sports_top);
        }

        lifestyleDataTabLayout.addOnTabSelectedListener(new TabLayout
                .OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ViewHolder holder = (ViewHolder) tab.getTag();
                holder.imageView.setVisibility(View.VISIBLE);
                if (lifestyleType == LIFESTYLE_DIET) {
                    holder.imageView.setImageResource(R.mipmap.icon_eat_top);
                } else {
                    holder.imageView.setImageResource(R.mipmap.icon_sports_top);
                }
                holder.textView.setTextColor(ContextCompat.getColor
                        (LifestyleDataActivity.this,
                                R.color.color_57a7fc));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                ViewHolder holder = (ViewHolder) tab.getTag();
                holder.imageView.setVisibility(View.INVISIBLE);
                holder.textView.setTextColor(ContextCompat.getColor
                        (LifestyleDataActivity.this,
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

    @Override
    public List<EatBean> getSelectEatBeans() {
        return selectEatBeans;
    }

    @Override
    public List<EatBean> getParams() {
        List<EatBean> list = new ArrayList<>();
        list.addAll(selectEatBeans);
        switch (mealType) {
            case 0:
                if (recordBean.getLunchList() != null && recordBean.getLunchList().size() > 0) {
                    list.addAll(recordBean.getLunchList());
                }
                if (recordBean.getDinnerList() != null && recordBean.getDinnerList().size() > 0) {
                    list.addAll(recordBean.getDinnerList());
                }
                if (recordBean.getSnacksList() != null && recordBean.getSnacksList().size() > 0) {
                    list.addAll(recordBean.getSnacksList());
                }
                break;
            case 1:
                if (recordBean.getBreakfastList() != null && recordBean.getBreakfastList().size() > 0) {
                    list.addAll(recordBean.getBreakfastList());
                }
                if (recordBean.getDinnerList() != null && recordBean.getDinnerList().size() > 0) {
                    list.addAll(recordBean.getDinnerList());
                }
                if (recordBean.getSnacksList() != null && recordBean.getSnacksList().size() > 0) {
                    list.addAll(recordBean.getSnacksList());
                }
                break;
            case 2:
                if (recordBean.getBreakfastList() != null && recordBean.getBreakfastList().size() > 0) {
                    list.addAll(recordBean.getBreakfastList());
                }
                if (recordBean.getLunchList() != null && recordBean.getLunchList().size() > 0) {
                    list.addAll(recordBean.getLunchList());
                }
                if (recordBean.getSnacksList() != null && recordBean.getSnacksList().size() > 0) {
                    list.addAll(recordBean.getSnacksList());
                }
                break;
            case 3:
                if (recordBean.getBreakfastList() != null && recordBean.getBreakfastList().size() > 0) {
                    list.addAll(recordBean.getBreakfastList());
                }
                if (recordBean.getLunchList() != null && recordBean.getLunchList().size() > 0) {
                    list.addAll(recordBean.getLunchList());
                }
                if (recordBean.getDinnerList() != null && recordBean.getDinnerList().size() > 0) {
                    list.addAll(recordBean.getDinnerList());
                }
                break;
            default:
                break;
        }
        return list;
    }

    private LifestyleDataFragment.OnItemSelectedListener listener =
            new LifestyleDataFragment.OnItemSelectedListener() {

                @Override
                public void onEatItemSelect(EatBean bean) {
                    if (selectEatBeans.contains(bean)) {
                        selectEatBeans.remove(bean);
                    } else {
                        selectEatBeans.add(bean);
                    }
                    lifestyleDataSelectNumTv.setText(String.valueOf(selectEatBeans.size()));
                }

                @Override
                public void onSportsItemSelect(SportsItemBean bean) {

                }
            };

    @Override
    public void updateFoodRecordSuccess() {
        ToastUtil.showMessage(this, "保存成功");
        EventBus.getDefault().post(new Event(EventType.UPDATE_LIFESTYLE, null));
        finish();
    }
}
