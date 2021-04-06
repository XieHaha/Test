package com.keydom.mianren.ih_patient.activity.health_manager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.health_manager.controller.LifestyleMainController;
import com.keydom.mianren.ih_patient.activity.health_manager.view.LifestyleMainView;
import com.keydom.mianren.ih_patient.bean.EatRecordBean;
import com.keydom.mianren.ih_patient.constant.Const;
import com.keydom.mianren.ih_patient.utils.DateUtils;
import com.keydom.mianren.ih_patient.utils.StatusBarUtils;

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
    @BindView(R.id.view_eat_record_add_lunch_tv)
    TextView viewEatRecordAddLunchTv;
    @BindView(R.id.view_eat_record_add_dinner_tv)
    TextView viewEatRecordAddDinnerTv;
    @BindView(R.id.view_eat_record_add_extra_tv)
    TextView viewEatRecordAddExtraTv;
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

    /**
     * 饮食记录
     */
    private EatRecordBean eatRecordBean;
    private Calendar calendar;

    private List<String> sleepQualityData;
    private List<String> sleepTimeData;
    private List<String> mentalStateData;
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
        localData();
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
            lifestyleBottomCancelTv.setText(R.string.txt_reset);
            lifestyleBottomSubmitTv.setText(R.string.txt_save_info);
        } else {
            tvTitle.setText(R.string.txt_sports_habits);
            lifestyleMainBgIv.setImageResource(R.mipmap.icon_sports_bg);
            lifestyleMainTitleTv.setText(R.string.txt_sports);
            lifestyleMainHintTv.setText(R.string.txt_sports_record_hint);
            lifestyleMainEatView.setVisibility(View.GONE);
            lifestyleMainSleepView.setVisibility(View.GONE);
            lifestyleMainSportsView.setVisibility(View.VISIBLE);
            lifestyleBottomBtnLayout.setVisibility(View.VISIBLE);
        }

        lifestyleMainLastDayIv.setOnClickListener(getController());
        lifestyleMainNextDayTv.setOnClickListener(getController());
        lifestyleBottomCancelTv.setOnClickListener(getController());
        lifestyleBottomSubmitTv.setOnClickListener(getController());

        viewEatRecordAddBreakfastTv.setOnClickListener(getController());
        eatRecordBreakfastAddTv.setOnClickListener(getController());
        viewEatRecordAddLunchTv.setOnClickListener(getController());
        eatRecordLunchAddTv.setOnClickListener(getController());
        viewEatRecordAddDinnerTv.setOnClickListener(getController());
        eatRecordDinnerAddTv.setOnClickListener(getController());
        viewEatRecordAddExtraTv.setOnClickListener(getController());
        eatRecordExtraAddTv.setOnClickListener(getController());
    }

    /**
     * 本地数据获取
     */
    private void localData() {
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
        } else {
            lifestyleMainNextDayTv.setSelected(true);
            lifestyleMainDayTv.setText(curSelectDate);
        }

        getController().foodRecordList(patientId, curSelectDate);
    }

    @Override
    public void requestFoodRecordSuccess(EatRecordBean bean) {
        updateFoodRecordSuccess(bean);
    }

    @Override
    public void updateFoodRecordSuccess(EatRecordBean bean) {
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
}
