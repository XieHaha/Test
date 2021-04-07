package com.keydom.mianren.ih_patient.activity.health_manager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.health_manager.controller.LifestyleMainController;
import com.keydom.mianren.ih_patient.activity.health_manager.view.LifestyleMainView;
import com.keydom.mianren.ih_patient.bean.EatBean;
import com.keydom.mianren.ih_patient.bean.EatRecordBean;
import com.keydom.mianren.ih_patient.bean.Event;
import com.keydom.mianren.ih_patient.bean.SleepRecordBean;
import com.keydom.mianren.ih_patient.bean.SportsBean;
import com.keydom.mianren.ih_patient.constant.Const;
import com.keydom.mianren.ih_patient.constant.EventType;
import com.keydom.mianren.ih_patient.utils.DateUtils;
import com.keydom.mianren.ih_patient.utils.StatusBarUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * @author 顿顿
 * @date 21/3/17 14:27
 * @des 生活记录
 */
public class LifestyleMainActivity extends BaseControllerActivity<LifestyleMainController> implements LifestyleMainView {
    @BindView(R.id.layout_title)
    RelativeLayout layoutBg;
    @BindView(R.id.status_bar)
    View statusBar;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.lifestyle_main_bg_iv)
    ImageView lifestyleMainBgIv;
    @BindView(R.id.lifestyle_main_last_day_iv)
    ImageView lifestyleMainLastDayIv;
    @BindView(R.id.lifestyle_main_day_tv)
    TextView lifestyleMainDayTv;
    @BindView(R.id.lifestyle_main_day_edit_tv)
    TextView lifestyleMainDayEditTv;
    @BindView(R.id.lifestyle_main_next_day_tv)
    ImageView lifestyleMainNextDayTv;
    @BindView(R.id.lifestyle_main_title_tv)
    TextView lifestyleMainTitleTv;
    @BindView(R.id.lifestyle_main_hint_tv)
    TextView lifestyleMainHintTv;
    @BindView(R.id.lifestyle_main_copy_tv)
    TextView lifestyleMainCopyTv;
    @BindView(R.id.lifestyle_eat_record_layout)
    View lifestyleMainEatView;
    @BindView(R.id.lifestyle_sleep_record_layout)
    View lifestyleMainSleepView;
    @BindView(R.id.lifestyle_sports_record_layout)
    View lifestyleMainSportsView;
    @BindView(R.id.lifestyle_bottom_btn_layout)
    LinearLayout lifestyleBottomBtnLayout;
    @BindView(R.id.lifestyle_bottom_cancel_tv)
    TextView lifestyleBottomCancelTv;
    @BindView(R.id.lifestyle_bottom_submit_tv)
    TextView lifestyleBottomSubmitTv;
    @BindView(R.id.view_eat_record_add_breakfast_tv)
    TextView viewEatRecordAddBreakfastTv;
    @BindView(R.id.view_eat_record_add_breakfast_iv)
    ImageView viewEatRecordAddBreakfastIv;
    @BindView(R.id.view_eat_record_add_lunch_tv)
    TextView viewEatRecordAddLunchTv;
    @BindView(R.id.view_eat_record_add_lunch_iv)
    ImageView viewEatRecordAddLunchIv;
    @BindView(R.id.view_eat_record_add_dinner_tv)
    TextView viewEatRecordAddDinnerTv;
    @BindView(R.id.view_eat_record_add_dinner_iv)
    ImageView viewEatRecordAddDinnerIv;
    @BindView(R.id.view_eat_record_add_extra_tv)
    TextView viewEatRecordAddExtraTv;
    @BindView(R.id.view_eat_record_add_extra_iv)
    ImageView viewEatRecordAddExtraIv;
    @BindView(R.id.eat_record_breakfast_item_layout)
    LinearLayout eatRecordBreakfastItemLayout;
    @BindView(R.id.eat_record_breakfast_add_tv)
    TextView eatRecordBreakfastAddTv;
    @BindView(R.id.eat_record_breakfast_root_layout)
    LinearLayout eatRecordBreakfastRootLayout;
    @BindView(R.id.eat_record_lunch_item_layout)
    LinearLayout eatRecordLunchItemLayout;
    @BindView(R.id.eat_record_lunch_add_tv)
    TextView eatRecordLunchAddTv;
    @BindView(R.id.eat_record_lunch_root_layout)
    LinearLayout eatRecordLunchRootLayout;
    @BindView(R.id.eat_record_dinner_item_layout)
    LinearLayout eatRecordDinnerItemLayout;
    @BindView(R.id.eat_record_dinner_add_tv)
    TextView eatRecordDinnerAddTv;
    @BindView(R.id.eat_record_dinner_root_layout)
    LinearLayout eatRecordDinnerRootLayout;
    @BindView(R.id.eat_record_extra_item_layout)
    LinearLayout eatRecordExtraItemLayout;
    @BindView(R.id.eat_record_extra_add_tv)
    TextView eatRecordExtraAddTv;
    @BindView(R.id.eat_record_extra_root_layout)
    LinearLayout eatRecordExtraRootLayout;
    @BindView(R.id.view_sleep_record_quality_tv)
    TextView viewSleepRecordQualityTv;
    @BindView(R.id.view_sleep_record_time_tv)
    TextView viewSleepRecordTimeTv;
    @BindView(R.id.view_sleep_record_status_tv)
    TextView viewSleepRecordStatusTv;
    @BindView(R.id.view_sports_record_minute_tv)
    TextView viewSportsRecordMinuteTv;
    @BindView(R.id.view_sports_record_kcal_tv)
    TextView viewSportsRecordKcalTv;
    @BindView(R.id.view_sports_record_layout)
    LinearLayout viewSportsRecordLayout;

    /**
     * 饮食记录
     */
    private EatRecordBean eatRecordBean;
    /**
     * 运动记录
     */
    private List<SportsBean> recordSportsBeans = new ArrayList<>();
    /**
     * 睡眠记录
     */
    private SleepRecordBean sleepRecordBean;
    private Calendar calendar;

    private List<String> sleepQualityData;
    private List<String> sleepTimeData;
    private List<String> mentalStateData;
    /**
     * 睡眠质量  时间  精神状态
     */
    private String mentalState, sleepQuality, sleepTime;
    /**
     * 运动总分钟 总消耗
     */
    private int minute = 0, sumHeat = 0;
    /**
     * 当前日期
     */
    private String curSelectDate;
    private String patientId;
    /**
     * 饮食
     */
    public static final int LIFESTYLE_DIET = 1;
    /**
     * 睡眠
     */
    public static final int LIFESTYLE_SLEEP = 2;
    /**
     * 运动
     */
    public static final int LIFESTYLE_SPORTS = 3;

    private int lifestyleType = -1;

    /**
     * 启动
     */
    public static void start(Context context, String patientId, int type) {
        Intent intent = new Intent(context, LifestyleMainActivity.class);
        intent.putExtra(Const.TYPE, type);
        intent.putExtra(Const.PATIENT_ID, patientId);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_lifestyle_main;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        statusBar.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                StatusBarUtils.getStateBarHeight(this)));
        StatusBarUtils.setStatusBarTranslucent(this);
        tvTitle.setText(R.string.txt_child_maintain);
        layoutBg.setAlpha(0);
        statusBar.setAlpha(0);
        StatusBarUtils.setStatusBarColor(this, false);
        ivBack.setOnClickListener(v -> finish());


        lifestyleType = getIntent().getIntExtra(Const.TYPE, -1);
        patientId = getIntent().getStringExtra(Const.PATIENT_ID);

        //获取当前时间
        calendar = Calendar.getInstance();
        initCalendarView();
        bindView();
    }

    private void bindView() {
        if (lifestyleType == LIFESTYLE_DIET) {
            tvTitle.setText(R.string.txt_eat_habits);
            lifestyleMainBgIv.setImageResource(R.mipmap.icon_eat_bg);
            lifestyleMainTitleTv.setText(R.string.txt_eat);
            lifestyleMainHintTv.setText(R.string.txt_eat_record_hint);
            lifestyleMainEatView.setVisibility(View.VISIBLE);
            lifestyleMainSleepView.setVisibility(View.GONE);
            lifestyleMainSportsView.setVisibility(View.GONE);
            lifestyleBottomBtnLayout.setVisibility(View.GONE);

            viewEatRecordAddBreakfastTv.setOnClickListener(getController());
            viewEatRecordAddBreakfastIv.setOnClickListener(getController());
            eatRecordBreakfastAddTv.setOnClickListener(getController());
            viewEatRecordAddLunchTv.setOnClickListener(getController());
            viewEatRecordAddLunchIv.setOnClickListener(getController());
            eatRecordLunchAddTv.setOnClickListener(getController());
            viewEatRecordAddDinnerTv.setOnClickListener(getController());
            viewEatRecordAddDinnerIv.setOnClickListener(getController());
            eatRecordDinnerAddTv.setOnClickListener(getController());
            viewEatRecordAddExtraTv.setOnClickListener(getController());
            viewEatRecordAddExtraIv.setOnClickListener(getController());
            eatRecordExtraAddTv.setOnClickListener(getController());

        } else if (lifestyleType == LIFESTYLE_SLEEP) {
            tvTitle.setText(R.string.txt_sleep_habits);
            tvTitle.setTextColor(ContextCompat.getColor(this, R.color.white));
            lifestyleMainBgIv.setImageResource(R.mipmap.icon_sleep_bg);
            lifestyleMainTitleTv.setText(R.string.txt_sleep);
            lifestyleMainHintTv.setText(R.string.txt_sleep_record_hint);
            lifestyleMainEatView.setVisibility(View.GONE);
            lifestyleMainSleepView.setVisibility(View.VISIBLE);
            lifestyleMainSportsView.setVisibility(View.GONE);
            lifestyleBottomBtnLayout.setVisibility(View.VISIBLE);
            lifestyleBottomSubmitTv.setText(R.string.txt_save_info);

            viewSleepRecordQualityTv.setOnClickListener(getController());
            viewSleepRecordTimeTv.setOnClickListener(getController());
            viewSleepRecordStatusTv.setOnClickListener(getController());

            localSleepData();
        } else {
            tvTitle.setText(R.string.txt_sports_habits);
            lifestyleMainBgIv.setImageResource(R.mipmap.icon_sports_bg);
            lifestyleMainTitleTv.setText(R.string.txt_sports);
            lifestyleMainHintTv.setText(R.string.txt_sports_record_hint);
            lifestyleMainEatView.setVisibility(View.GONE);
            lifestyleMainSleepView.setVisibility(View.GONE);
            lifestyleMainSportsView.setVisibility(View.VISIBLE);
            lifestyleBottomBtnLayout.setVisibility(View.VISIBLE);

            lifestyleBottomSubmitTv.setText("添加运动");
        }

        lifestyleMainLastDayIv.setOnClickListener(getController());
        lifestyleMainNextDayTv.setOnClickListener(getController());
        lifestyleBottomCancelTv.setOnClickListener(getController());
        lifestyleBottomSubmitTv.setOnClickListener(getController());

        lifestyleMainCopyTv.setOnClickListener(getController());
    }

    /**
     * 睡眠本地数据获取
     */
    private void localSleepData() {
        String[] quality = getResources().getStringArray(R.array.sleep_quality);
        sleepQualityData = new ArrayList<>(quality.length);
        Collections.addAll(sleepQualityData, quality);

        String[] time = getResources().getStringArray(R.array.sleep_time);
        sleepTimeData = new ArrayList<>(time.length);
        Collections.addAll(sleepTimeData, time);

        String[] mental = getResources().getStringArray(R.array.mental_state);
        mentalStateData = new ArrayList<>(mental.length);
        Collections.addAll(mentalStateData, mental);
    }

    /**
     * 日期处理
     */
    private void initCalendarView() {
        Date date = calendar.getTime();
        curSelectDate = DateUtils.dateToString(date, DateUtils.YYYY_MM_DD_CH);
        lifestyleMainLastDayIv.setSelected(true);
        if (DateUtils.isToday(date)) {
            lifestyleMainNextDayTv.setSelected(false);
            lifestyleMainDayTv.setText("今日");
            lifestyleMainCopyTv.setVisibility(View.INVISIBLE);
        } else {
            lifestyleMainNextDayTv.setSelected(true);
            lifestyleMainDayTv.setText(curSelectDate);
            lifestyleMainCopyTv.setVisibility(View.VISIBLE);
        }

        if (lifestyleType == LIFESTYLE_DIET) {
            getController().foodRecordList(patientId, curSelectDate);
        } else if (lifestyleType == LIFESTYLE_SLEEP) {
            getController().sleepRecordList(patientId, curSelectDate);
        } else {
            getController().exerciseRecordList(patientId, curSelectDate);
        }
    }

    /**
     * 就餐数据处理
     */
    private void initLifestyleData() {
        //早餐
        List<EatBean> breakfastBeans = eatRecordBean.getBreakfastList();
        if (breakfastBeans != null && breakfastBeans.size() > 0) {
            viewEatRecordAddBreakfastIv.setSelected(true);
            eatRecordBreakfastRootLayout.setVisibility(View.VISIBLE);
            viewEatRecordAddBreakfastTv.setVisibility(View.GONE);
            viewEatRecordAddBreakfastIv.setVisibility(View.VISIBLE);
            addBreakfastView(breakfastBeans);
        } else {
            eatRecordBreakfastRootLayout.setVisibility(View.GONE);
            viewEatRecordAddBreakfastIv.setVisibility(View.GONE);
            viewEatRecordAddBreakfastTv.setVisibility(View.VISIBLE);
        }

        //午餐
        List<EatBean> lunchBeans = eatRecordBean.getLunchList();
        if (lunchBeans != null && lunchBeans.size() > 0) {
            viewEatRecordAddLunchIv.setSelected(true);
            eatRecordLunchRootLayout.setVisibility(View.VISIBLE);
            viewEatRecordAddLunchTv.setVisibility(View.GONE);
            viewEatRecordAddLunchIv.setVisibility(View.VISIBLE);
            addLunchView(lunchBeans);
        } else {
            eatRecordLunchRootLayout.setVisibility(View.GONE);
            viewEatRecordAddLunchIv.setVisibility(View.GONE);
            viewEatRecordAddLunchTv.setVisibility(View.VISIBLE);
        }

        //晚餐
        List<EatBean> dinnerBeans = eatRecordBean.getDinnerList();
        if (dinnerBeans != null && dinnerBeans.size() > 0) {
            viewEatRecordAddDinnerIv.setSelected(true);
            eatRecordDinnerRootLayout.setVisibility(View.VISIBLE);
            viewEatRecordAddDinnerTv.setVisibility(View.GONE);
            viewEatRecordAddDinnerIv.setVisibility(View.VISIBLE);
            addDinnerView(dinnerBeans);
        } else {
            eatRecordDinnerRootLayout.setVisibility(View.GONE);
            viewEatRecordAddDinnerIv.setVisibility(View.GONE);
            viewEatRecordAddDinnerTv.setVisibility(View.VISIBLE);
        }

        //加餐
        List<EatBean> snacksBeans = eatRecordBean.getSnacksList();
        if (snacksBeans != null && snacksBeans.size() > 0) {
            viewEatRecordAddExtraIv.setSelected(true);
            eatRecordExtraRootLayout.setVisibility(View.VISIBLE);
            viewEatRecordAddExtraTv.setVisibility(View.GONE);
            viewEatRecordAddExtraIv.setVisibility(View.VISIBLE);
            addExtraView(snacksBeans);
        } else {
            eatRecordExtraRootLayout.setVisibility(View.GONE);
            viewEatRecordAddExtraIv.setVisibility(View.GONE);
            viewEatRecordAddExtraTv.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 早餐
     */
    private void addBreakfastView(List<EatBean> breakfastBeans) {
        eatRecordBreakfastItemLayout.removeAllViews();
        for (int i = 0; i < breakfastBeans.size(); i++) {
            EatBean bean = breakfastBeans.get(i);
            View view = getLayoutInflater().inflate(R.layout.item_lifestyle_eat, null);
            TextView name = view.findViewById(R.id.item_lifestyle_eat_name);
            TextView num = view.findViewById(R.id.item_lifestyle_eat_num);
            TextView copies = view.findViewById(R.id.item_lifestyle_eat_copies);
            ImageView delete = view.findViewById(R.id.item_lifestyle_eat_delete);
            name.setText(bean.getName());
            num.setText(bean.getAmount() + "克  " + bean.getSumHeat() + "千卡");
            copies.setText(bean.getCopies() + "份");
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    breakfastBeans.remove(bean);
                    eatRecordBreakfastItemLayout.removeView(view);
                    if (breakfastBeans.size() == 0) {
                        eatRecordBreakfastRootLayout.setVisibility(View.GONE);
                        viewEatRecordAddBreakfastIv.setVisibility(View.GONE);
                        viewEatRecordAddBreakfastTv.setVisibility(View.VISIBLE);
                    }
                    //删除
                    getController().deleteFoodRecord(bean.getId());
                }
            });
            eatRecordBreakfastItemLayout.addView(view);
        }
    }

    @Override
    public void expandBreakfastLayout() {
        if (viewEatRecordAddBreakfastIv.isSelected()) {
            eatRecordBreakfastRootLayout.setVisibility(View.GONE);
            viewEatRecordAddBreakfastIv.setSelected(false);
        } else {
            eatRecordBreakfastRootLayout.setVisibility(View.VISIBLE);
            viewEatRecordAddBreakfastIv.setSelected(true);
        }
    }

    /**
     * 午餐
     */
    private void addLunchView(List<EatBean> lunchBeans) {
        eatRecordLunchItemLayout.removeAllViews();
        for (int i = 0; i < lunchBeans.size(); i++) {
            EatBean bean = lunchBeans.get(i);
            View view = getLayoutInflater().inflate(R.layout.item_lifestyle_eat, null);
            TextView name = view.findViewById(R.id.item_lifestyle_eat_name);
            TextView num = view.findViewById(R.id.item_lifestyle_eat_num);
            TextView copies = view.findViewById(R.id.item_lifestyle_eat_copies);
            ImageView delete = view.findViewById(R.id.item_lifestyle_eat_delete);
            name.setText(bean.getName());
            num.setText(bean.getAmount() + "克  " + bean.getSumHeat() + "千卡");
            copies.setText(bean.getCopies() + "份");
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lunchBeans.remove(bean);
                    eatRecordLunchItemLayout.removeView(view);
                    if (lunchBeans.size() == 0) {
                        eatRecordLunchRootLayout.setVisibility(View.GONE);
                        viewEatRecordAddLunchIv.setVisibility(View.GONE);
                        viewEatRecordAddLunchTv.setVisibility(View.VISIBLE);
                    }
                    //删除
                    getController().deleteFoodRecord(bean.getId());
                }
            });
            eatRecordLunchItemLayout.addView(view);
        }
    }

    @Override
    public void expandLunchLayout() {
        if (viewEatRecordAddLunchIv.isSelected()) {
            eatRecordLunchRootLayout.setVisibility(View.GONE);
            viewEatRecordAddLunchIv.setSelected(false);
        } else {
            eatRecordLunchRootLayout.setVisibility(View.VISIBLE);
            viewEatRecordAddLunchIv.setSelected(true);
        }
    }

    /**
     * 晚餐
     */
    private void addDinnerView(List<EatBean> dinnerBeans) {
        eatRecordDinnerItemLayout.removeAllViews();
        for (int i = 0; i < dinnerBeans.size(); i++) {
            EatBean bean = dinnerBeans.get(i);
            View view = getLayoutInflater().inflate(R.layout.item_lifestyle_eat, null);
            TextView name = view.findViewById(R.id.item_lifestyle_eat_name);
            TextView num = view.findViewById(R.id.item_lifestyle_eat_num);
            TextView copies = view.findViewById(R.id.item_lifestyle_eat_copies);
            ImageView delete = view.findViewById(R.id.item_lifestyle_eat_delete);
            name.setText(bean.getName());
            num.setText(bean.getAmount() + "克  " + bean.getSumHeat() + "千卡");
            copies.setText(bean.getCopies() + "份");
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dinnerBeans.remove(bean);
                    eatRecordDinnerItemLayout.removeView(view);
                    if (dinnerBeans.size() == 0) {
                        eatRecordDinnerRootLayout.setVisibility(View.GONE);
                        viewEatRecordAddDinnerIv.setVisibility(View.GONE);
                        viewEatRecordAddDinnerTv.setVisibility(View.VISIBLE);
                    }
                    //删除
                    getController().deleteFoodRecord(bean.getId());
                }
            });
            eatRecordDinnerItemLayout.addView(view);
        }
    }

    @Override
    public void expandDinnerLayout() {
        if (viewEatRecordAddDinnerIv.isSelected()) {
            eatRecordDinnerRootLayout.setVisibility(View.GONE);
            viewEatRecordAddDinnerIv.setSelected(false);
        } else {
            eatRecordDinnerRootLayout.setVisibility(View.VISIBLE);
            viewEatRecordAddDinnerIv.setSelected(true);
        }
    }

    /**
     * 加餐
     */
    private void addExtraView(List<EatBean> extraBeans) {
        eatRecordExtraItemLayout.removeAllViews();
        for (int i = 0; i < extraBeans.size(); i++) {
            EatBean bean = extraBeans.get(i);
            View view = getLayoutInflater().inflate(R.layout.item_lifestyle_eat, null);
            TextView name = view.findViewById(R.id.item_lifestyle_eat_name);
            TextView num = view.findViewById(R.id.item_lifestyle_eat_num);
            TextView copies = view.findViewById(R.id.item_lifestyle_eat_copies);
            ImageView delete = view.findViewById(R.id.item_lifestyle_eat_delete);
            name.setText(bean.getName());
            num.setText(bean.getAmount() + "克  " + bean.getSumHeat() + "千卡");
            copies.setText(bean.getCopies() + "份");
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    extraBeans.remove(bean);
                    eatRecordExtraItemLayout.removeView(view);
                    if (extraBeans.size() == 0) {
                        eatRecordExtraRootLayout.setVisibility(View.GONE);
                        viewEatRecordAddExtraIv.setVisibility(View.GONE);
                        viewEatRecordAddExtraTv.setVisibility(View.VISIBLE);
                    }
                    //删除
                    getController().deleteFoodRecord(bean.getId());
                }
            });
            eatRecordExtraItemLayout.addView(view);
        }
    }

    @Override
    public void expandExtraLayout() {
        if (viewEatRecordAddExtraIv.isSelected()) {
            eatRecordExtraRootLayout.setVisibility(View.GONE);
            viewEatRecordAddExtraIv.setSelected(false);
        } else {
            eatRecordExtraRootLayout.setVisibility(View.VISIBLE);
            viewEatRecordAddExtraIv.setSelected(true);
        }
    }

    /**
     * 睡眠数据处理
     */
    private void initSleepData() {
        mentalState = sleepRecordBean.getMentalState();
        sleepQuality = sleepRecordBean.getSleepQuality();
        sleepTime = sleepRecordBean.getSleepTime();
        if (TextUtils.isEmpty(sleepRecordBean.getId())) {
            viewSleepRecordStatusTv.setText(R.string.txt_select);
            viewSleepRecordQualityTv.setText(R.string.txt_select);
            viewSleepRecordTimeTv.setText(R.string.txt_select);
        } else {
            viewSleepRecordStatusTv.setText(mentalState);
            viewSleepRecordQualityTv.setText(sleepQuality);
            viewSleepRecordTimeTv.setText(sleepTime);
        }
    }

    /**
     * 运动数据处理
     */
    private void initSportsData() {
        viewSportsRecordLayout.removeAllViews();
        if (recordSportsBeans != null && recordSportsBeans.size() > 0) {
            addSportsView();
        } else {
            minute = 0;
            sumHeat = 0;
        }
        viewSportsRecordMinuteTv.setText(String.valueOf(minute));
        viewSportsRecordKcalTv.setText(String.valueOf(sumHeat));
    }

    /**
     * 运动view
     */
    private void addSportsView() {
        for (int i = 0; i < recordSportsBeans.size(); i++) {
            SportsBean bean = recordSportsBeans.get(i);
            minute += bean.getMinute();
            sumHeat += bean.getSumHeat();
            View view = getLayoutInflater().inflate(R.layout.item_lifestyle_sports, null);
            TextView name = view.findViewById(R.id.item_lifestyle_sports_name);
            TextView num = view.findViewById(R.id.item_lifestyle_sports_num);
            ImageView delete = view.findViewById(R.id.item_lifestyle_sports_delete);
            name.setText(bean.getName());
            num.setText(bean.getMinute() + "分钟，" + bean.getSumHeat() + "千卡");
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recordSportsBeans.remove(bean);
                    viewSportsRecordLayout.removeView(view);
                    //删除
                    getController().deleteExerciseRecord(bean.getId());
                }
            });
            viewSportsRecordLayout.addView(view);
        }
    }

    @Override
    public List<String> getSleepQualityData() {
        return sleepQualityData;
    }

    @Override
    public List<String> getSleepTimeData() {
        return sleepTimeData;
    }

    @Override
    public List<String> getMentalStateData() {
        return mentalStateData;
    }

    @Override
    public void setMentalState(int position) {
        this.mentalState = mentalStateData.get(position);
        viewSleepRecordStatusTv.setText(mentalState);
    }

    @Override
    public void setSleepQuality(int position) {
        this.sleepQuality = sleepQualityData.get(position);
        viewSleepRecordQualityTv.setText(sleepQuality);
    }

    @Override
    public void setSleepTime(int position) {
        this.sleepTime = sleepTimeData.get(position);
        viewSleepRecordTimeTv.setText(sleepTime);
    }

    @Override
    public List<EatBean> getEatRecordParams() {
        List<EatBean> list = new ArrayList<>();
        if (eatRecordBean.getBreakfastList() != null && eatRecordBean.getBreakfastList().size() > 0) {
            list.addAll(eatRecordBean.getBreakfastList());
        }
        if (eatRecordBean.getLunchList() != null && eatRecordBean.getLunchList().size() > 0) {
            list.addAll(eatRecordBean.getLunchList());
        }
        if (eatRecordBean.getDinnerList() != null && eatRecordBean.getDinnerList().size() > 0) {
            list.addAll(eatRecordBean.getDinnerList());
        }
        if (eatRecordBean.getSnacksList() != null && eatRecordBean.getSnacksList().size() > 0) {
            list.addAll(eatRecordBean.getSnacksList());
        }
        //日期修改为今天
        for (EatBean bean : list) {
            bean.setRecordTime(DateUtils.dateToString(new Date()));
        }
        return list;
    }

    @Override
    public boolean verifySleepRecordParams() {
        if (TextUtils.isEmpty(mentalState) || TextUtils.isEmpty(sleepQuality) || TextUtils.isEmpty(sleepTime)) {
            ToastUtil.showMessage(this, "暂无数据");
            return false;
        }
        return true;
    }

    @Override
    public boolean verifySportsRecordParams() {
        if (minute == 0 || sumHeat == 0) {
            ToastUtil.showMessage(this, "暂无数据");
            return false;
        }
        return true;
    }

    @Override
    public Map<String, String> getSleepRecordParams(boolean copyToday) {
        Map<String, String> params = new HashMap<>(16);
        params.put("mentalState", mentalState);
        params.put("patientId", patientId);
        if (copyToday) {
            params.put("recordTime", DateUtils.dateToString(new Date()));
        } else {
            params.put("recordTime", curSelectDate);
        }
        params.put("sleepQuality", sleepQuality);
        params.put("sleepTime", sleepTime);
        return params;
    }

    @Override
    public Map<String, String> getDeleteSleepRecordParams() {
        Map<String, String> params = new HashMap<>(16);
        params.put("patientId", patientId);
        params.put("recordTime", curSelectDate);
        //睡眠
        params.put("type", "1");
        return params;
    }

    @Override
    public List<SportsBean> getSportRecordParams() {
        List<SportsBean> list = new ArrayList<>();
        for (SportsBean bean : recordSportsBeans) {
            bean.setRecordTime(DateUtils.dateToString(new Date()));
            list.add(bean);
        }
        return list;
    }

    /**
     * 记录变化更新
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateLifestyle(Event event) {
        if (event.getType() == EventType.UPDATE_EAT_LIFESTYLE) {
            getController().foodRecordList(patientId, curSelectDate);
        } else if (event.getType() == EventType.UPDATE_SPORTS_LIFESTYLE) {
            getController().exerciseRecordList(patientId, curSelectDate);
        }
    }

    @Override
    public int getLifestyleType() {
        return lifestyleType;
    }

    @Override
    public boolean isNotToday() {
        return lifestyleMainNextDayTv.isSelected();
    }

    @Override
    public String getCurSelectDate() {
        return curSelectDate;
    }

    @Override
    public EatRecordBean getEatRecordBean() {
        return eatRecordBean;
    }

    @Override
    public List<SportsBean> getRecordSportsBeans() {
        return recordSportsBeans;
    }

    @Override
    public void requestFoodRecordSuccess(EatRecordBean bean) {
        eatRecordBean = bean;
        initLifestyleData();
    }

    @Override
    public void requestFoodRecordFailed() {
        eatRecordBean = new EatRecordBean();
        initLifestyleData();
    }

    @Override
    public void requestSleepRecordSuccess(List<SleepRecordBean> beans) {
        if (beans != null && beans.size() > 0) {
            sleepRecordBean = beans.get(0);
        } else {
            sleepRecordBean = new SleepRecordBean();
        }
        initSleepData();
    }

    @Override
    public void requestSleepRecordFailed() {
        sleepRecordBean = new SleepRecordBean();
        initSleepData();
    }

    @Override
    public void requestSportsRecordSuccess(List<SportsBean> bean) {
        recordSportsBeans.clear();
        recordSportsBeans.addAll(bean);
        initSportsData();
    }

    @Override
    public void requestSportsRecordFailed() {

    }

    @Override
    public void deleteSportsRecordSuccess() {
        getController().exerciseRecordList(patientId, curSelectDate);
    }

    @Override
    public void copyFoodRecordSuccess() {
        calendar = Calendar.getInstance();
        initCalendarView();
    }

    @Override
    public Map<String, Object> getUpdateEatDataParams(EatRecordBean bean) {
        Map<String, Object> params = new HashMap<>();
        return params;
    }

    @Override
    public void setNewDate(int value) {
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + value);
        initCalendarView();
    }

    @Override
    public String getPatientId() {
        return patientId;
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
