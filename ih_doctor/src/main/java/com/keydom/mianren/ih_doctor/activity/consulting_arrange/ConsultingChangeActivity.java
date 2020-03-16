package com.keydom.mianren.ih_doctor.activity.consulting_arrange;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.mianren.ih_doctor.MyApplication;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.consulting_arrange.controller.ConsultingChangeController;
import com.keydom.mianren.ih_doctor.activity.consulting_arrange.view.ConsultingChangeView;
import com.keydom.mianren.ih_doctor.bean.ConsultingBean;
import com.keydom.mianren.ih_doctor.bean.MessageEvent;
import com.keydom.mianren.ih_doctor.constant.Const;
import com.keydom.mianren.ih_doctor.constant.EventType;
import com.keydom.mianren.ih_doctor.utils.CalculateTimeUtils;
import com.keydom.mianren.ih_doctor.utils.DateUtils;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.activity
 * @Description：门诊排班修改共用
 * @Author：song
 * @Date：18/11/19 上午11:34
 * 修改人：xusong
 * 修改时间：18/11/19 上午11:34
 */
public class ConsultingChangeActivity extends BaseControllerActivity<ConsultingChangeController> implements ConsultingChangeView {
    /**
     * 新增停诊界面
     */
    public static final int CONSULTING_STOP = 400;
    /**
     * 新增循环排班界面
     */
    public static final int CONSULTING_CIRCLE = 401;
    /**
     * 修改排班信息界面
     */
    public static final int CONSULTING_UPDATE = 402;
    /**
     * 修改停诊
     */
    public static final int CONSULTING_STOP_UPDATE = 403;
    /**
     * 修改循环排班
     */
    public static final int CONSULTING_CIRCLE_UPDATE = 404;
    /**
     * 是否停诊
     */
    private Integer isStop = STOP;

    /**
     * 停诊
     */
    private static final Integer STOP = 1;

    /**
     * 不停诊
     */
    private static final Integer NOT_STOP = 0;
    /**
     * 接收页面类型type
     */
    private int type;

    /**
     * 停诊选时间
     */
    private TimePickerView stopPvTime;
    /**
     * 循环排班选时间
     */
    private TimePickerView circulatePvTime;
    /**
     * 选择星期
     */
    private OptionsPickerView weekPvOptions;
    /**
     * 选择起止时间
     */
    private OptionsPickerView startEndTime;
    /**
     * 需要修改的排班对象
     */
    private ConsultingBean consultingBean;
    /**
     * 开始时间
     */
    private Date startTimeStr;
    /**
     * 结束时间
     */
    private Date endTimeStr;
    /**
     * 日期
     */
    private Date dataStr;
    /**
     * 星期几
     */
    private Integer week;
    /**
     * 排班列表，用于判断是否有重复提交的星期
     */
    private List<ConsultingBean> mList;
    private RelativeLayout consultingCircleRl;
    private TextView consultingOneItemValueTv, consultingTwoItemValueTv, consultingOneItemTv, consultingTwoItemTv, consultingThreeItemTv, consultingChangeTipTv;
    public ImageView itemIcon, actionBg, actionOpen, actionClose;
    public RelativeLayout actionRl;

    /**
     * 启动循环排班修改页面
     *
     * @param context
     * @param type    页面类型
     * @param bean    要修改的排班对象
     * @param list    排班列表
     */
    public static void start(Context context, int type, ConsultingBean bean, List<ConsultingBean> list) {
        Intent starter = new Intent(context, ConsultingChangeActivity.class);
        starter.putExtra(Const.TYPE, type);
        starter.putExtra(Const.CONSULTING_BEAN, bean);
        starter.putExtra(Const.DATA, (Serializable) list);
        ((Activity) context).startActivityForResult(starter, Const.TYPE_ACTIVITY_REFRESH);
    }

    /**
     * 启动新增排班页面
     *
     * @param context
     * @param type    页面类型
     * @param list    排班列表
     */
    public static void start(Context context, int type, List<ConsultingBean> list) {
        Intent starter = new Intent(context, ConsultingChangeActivity.class);
        starter.putExtra(Const.TYPE, type);
        starter.putExtra(Const.DATA, (Serializable) list);
        ((Activity) context).startActivityForResult(starter, Const.TYPE_ACTIVITY_REFRESH);
    }


    @Override
    public int getLayoutRes() {
        return R.layout.activity_consulting_change;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        type = getIntent().getIntExtra(Const.TYPE, -1);
        mList = (List<ConsultingBean>) getIntent().getSerializableExtra(Const.DATA);
        consultingBean = (ConsultingBean) getIntent().getSerializableExtra(Const.CONSULTING_BEAN);
        consultingCircleRl = findViewById(R.id.consulting_circle_rl);
        consultingOneItemValueTv = findViewById(R.id.consulting_item_one_value);
        consultingOneItemTv = findViewById(R.id.consulting_item_one);
        consultingTwoItemValueTv = findViewById(R.id.consulting_item_two_value);
        consultingTwoItemTv = findViewById(R.id.consulting_item_two);
        consultingThreeItemTv = findViewById(R.id.consulting_item_three);
        consultingChangeTipTv = findViewById(R.id.consulting_change_tip_tv);
        itemIcon = (ImageView) findViewById(R.id.consulting_item_icon);
        actionBg = (ImageView) findViewById(R.id.consulting_action_bg);
        actionOpen = (ImageView) findViewById(R.id.consulting_open);
        actionClose = (ImageView) findViewById(R.id.consulting_close);
        actionRl = (RelativeLayout) findViewById(R.id.action_rl);
        actionRl.setOnClickListener(getController());
        if (type == CONSULTING_STOP) {
            initConsultingAddArrange();
        } else if (type == CONSULTING_CIRCLE) {
            initConsultingAddCircle();
        } else if (type == CONSULTING_STOP_UPDATE) {
            initConsultingUpdateArrange();
        } else if (type == CONSULTING_CIRCLE_UPDATE) {
            initConsultingUpdateCircle();
        } else {
            initConsultingUpdate();
        }
        setRightTxt("保存");
        setRightBtnListener(new IhTitleLayout.OnRightTextClickListener() {
            @Override
            public void OnRightTextClick(View v) {
                if (type == CONSULTING_UPDATE) {
                    getController().changeConsulting();
                } else if (type == CONSULTING_STOP || type == CONSULTING_STOP_UPDATE) {
                    getController().addStopConsulting();
                } else {
                    getController().addLoopConsulting();
                }
            }
        });

        consultingOneItemValueTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectOne();
            }
        });
        consultingTwoItemValueTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTwo();
            }
        });

    }


    /**
     * 公用界面，从上往下  第一个选择框选择
     */
    private void selectOne() {
        if (type == CONSULTING_UPDATE || type == CONSULTING_CIRCLE_UPDATE) {
            return;
        }
        if (type == CONSULTING_STOP || type == CONSULTING_STOP_UPDATE) {
            Calendar startDate = Calendar.getInstance();
            Calendar endDate = Calendar.getInstance();
            startDate.setTime(new Date());
            startDate.set(Calendar.WEEK_OF_YEAR, startDate.get(Calendar.WEEK_OF_YEAR) + 1);//加一周
            startDate.set(Calendar.DAY_OF_YEAR, startDate.get(Calendar.DAY_OF_YEAR) + 1);//加一天
            endDate.set(CalculateTimeUtils.LAST_YEAR, CalculateTimeUtils.LAST_HOUR, CalculateTimeUtils.LAST_MINUTE);
            if (stopPvTime == null) {
                stopPvTime = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date mdate, View v) {
                        dataStr = mdate;
                        startTimeStr = dataStr;
                        consultingOneItemValueTv.setText(CalculateTimeUtils.getUiDate(mdate) + "  " + CalculateTimeUtils.getWeekOfDate(mdate));
                    }
                }).setRangDate(startDate, endDate).build();
            }
            stopPvTime.show();
        } else {
            final List<String> list = new ArrayList<>();
            list.add("星期一");
            list.add("星期二");
            list.add("星期三");
            list.add("星期四");
            list.add("星期五");
            list.add("星期六");
            list.add("星期日");
            if (weekPvOptions == null) {
                weekPvOptions = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        week = options1 + 1;
                        consultingOneItemValueTv.setText(list.get(options1));
                    }
                }).build();
                weekPvOptions.setPicker(list);
            }
            weekPvOptions.show();
        }

    }


    /**
     * 公用界面，从上往下  第二个选择框选择
     */
    private void selectTwo() {
        if (type == CONSULTING_STOP || type == CONSULTING_STOP_UPDATE) {
            if (startTimeStr == null) {
                ToastUtil.showMessage(this, "请先选择开始时间！");
                return;
            }
            if (circulatePvTime == null) {
                Calendar startDate = Calendar.getInstance();
                Calendar endDate = Calendar.getInstance();
                startDate.setTime(startTimeStr);
                endDate.set(2050, 12, 30);
                circulatePvTime = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date mdate, View v) {
                        endTimeStr = mdate;
                        consultingTwoItemValueTv.setText(CalculateTimeUtils.getUiDate(mdate) + "  " + CalculateTimeUtils.getWeekOfDate(mdate));
                    }
                }).setRangDate(startDate, endDate).build();
            }
            circulatePvTime.show();
        } else {
            if (startEndTime == null) {
                startEndTime = DateUtils.showDateIntervalChooseDialog(getContext(), 0, 24, new DateUtils.DateIntervalSelectedListener() {
                    @Override
                    public void getSelectedDateInterval(String date, String startTime, String endTime) {
                        startTimeStr = CalculateTimeUtils.getTime(startTime);
                        endTimeStr = CalculateTimeUtils.getTime(endTime);
//                        consultingTwoItemValueTv.setText(CalculateTimeUtils.getTime(startTimeStr) + "-" + CalculateTimeUtils.getTime(endTimeStr));
                        consultingTwoItemValueTv.setText(CalculateTimeUtils.getRoundTime(startTime) + "-" + CalculateTimeUtils.getRoundTime(endTime));
                    }
                });
            }
            startEndTime.show();
        }

    }

    /**
     * 初始化修改排班页面
     */
    private void initConsultingUpdate() {
        setTitle("修改排班");
        startTimeStr = CalculateTimeUtils.getTime(consultingBean.getBeginPointTime());
        endTimeStr = CalculateTimeUtils.getTime(consultingBean.getEndPointTime());
        dataStr = CalculateTimeUtils.getDate(consultingBean.getDate());
        consultingOneItemTv.setText("接诊日期");
        consultingOneItemValueTv.setText(CalculateTimeUtils.getUiDate(dataStr) + "  " + CalculateTimeUtils.getWeekOfDate(dataStr));
        consultingTwoItemTv.setText("接诊时间");
        consultingTwoItemValueTv.setText(CalculateTimeUtils.getRoundTime(consultingBean.getBeginPointTime()) + "-" + CalculateTimeUtils.getRoundTime(consultingBean.getEndPointTime()));
        consultingThreeItemTv.setText("停诊");
        consultingChangeTipTv.setText("如果新增排班与循环排班冲突，以新增排班为准！");
        isStop = consultingBean.getIsStop();
        if (isStop!=null&&isStop.equals(STOP)) {
            setOpen();
        } else {
            setStop();
        }
    }

    /**
     * 初始化修改停诊页面
     */
    private void initConsultingUpdateArrange() {
        setTitle("修改停诊");
        consultingOneItemTv.setText("停诊开始日期");
        consultingTwoItemTv.setText("停诊结束日期");
        consultingCircleRl.setVisibility(View.GONE);
        consultingChangeTipTv.setText("如果已有排班，则自动停诊已有排班。");
        dataStr = CalculateTimeUtils.getDate(consultingBean.getBeginDate());
        startTimeStr = dataStr;
        consultingOneItemValueTv.setText(CalculateTimeUtils.getUiDate(dataStr) + "  " + CalculateTimeUtils.getWeekOfDate(dataStr));
        endTimeStr = CalculateTimeUtils.getDate(consultingBean.getEndDate());
        if (endTimeStr != null) {
            consultingTwoItemValueTv.setText(CalculateTimeUtils.getUiDate(endTimeStr) + "  " + CalculateTimeUtils.getWeekOfDate(endTimeStr));
        } else {
            consultingTwoItemValueTv.setText("请选择");
        }


    }

    /**
     * 初始化修改循环排班页面
     */
    private void initConsultingUpdateCircle() {
        setTitle("修改循环排班");
        if (consultingBean != null && "0".equals(consultingBean.getState())) {
            setStop();
        } else {
            setOpen();
        }
        consultingOneItemTv.setText("接诊周期");
        consultingTwoItemTv.setText("接诊时间");
        consultingThreeItemTv.setText("循环排班");
        consultingChangeTipTv.setText("新增/修改循环排班后，不影响已生成的停诊和特殊排班。");
        week = Integer.valueOf(consultingBean.getWeek());
        consultingOneItemValueTv.setText(CalculateTimeUtils.getWeek(consultingBean.getWeek()));
        startTimeStr = CalculateTimeUtils.getTime(consultingBean.getBeginPointTime());
        endTimeStr = CalculateTimeUtils.getTime(consultingBean.getEndPointTime());
        consultingTwoItemValueTv.setText(CalculateTimeUtils.getRoundTime(CalculateTimeUtils.getTime(startTimeStr)) + "-" + CalculateTimeUtils.getRoundTime(CalculateTimeUtils.getTime(endTimeStr)));
    }

    /**
     * 初始化新增停诊页面
     */
    private void initConsultingAddArrange() {
        setTitle("新增停诊");
        consultingOneItemTv.setText("停诊开始日期");
        consultingTwoItemTv.setText("停诊结束日期");
        consultingCircleRl.setVisibility(View.GONE);
        consultingChangeTipTv.setText("如果已有排班，则自动停诊已有排班。");
    }

    /**
     * 初始化新增循环排班页面
     */
    private void initConsultingAddCircle() {
        setTitle("新增循环排班");
        consultingOneItemTv.setText("接诊周期");
        consultingTwoItemTv.setText("接诊时间");
        consultingThreeItemTv.setText("循环排班");
        consultingChangeTipTv.setText("新增/修改循环排班后，不影响已生成的停诊和特殊排班。");
    }


    /**
     * 设置选择按钮关闭操作时按钮显示
     */
    private void setStop() {
        actionOpen.setVisibility(View.GONE);
        actionClose.setVisibility(View.VISIBLE);
        actionBg.setImageDrawable(this.getResources().getDrawable(R.mipmap.consulting_close_bg));
    }

    /**
     * 设置开关按钮打开操作时按钮显示
     */
    private void setOpen() {
        actionOpen.setVisibility(View.VISIBLE);
        actionClose.setVisibility(View.GONE);
        actionBg.setImageDrawable(this.getResources().getDrawable(R.mipmap.consulting_open_bg));
    }

    @Override
    public void changeArrange() {
        if (isStop == NOT_STOP) {
            setOpen();
            isStop = STOP;
        } else {
            setStop();
            isStop = NOT_STOP;

        }
    }

    @Override
    public void reqSuccess(String msg) {
        EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.CONSULTING_UPDATE).build());
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void reqFailed(String errMsg) {
        ToastUtil.showMessage(this, "操作失败");
    }

    @Override
    public HashMap<String, Object> getChangeMap() {
        if (startTimeStr == null || endTimeStr == null || dataStr == null) {
            ToastUtil.showMessage(this, "请完善信息后再提交");
            return null;
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", consultingBean.getId());
        map.put("date", CalculateTimeUtils.requestDate(dataStr));
        map.put("beginPointTime", CalculateTimeUtils.getTime(startTimeStr));
        map.put("endPointTime", CalculateTimeUtils.getTime(endTimeStr));
        map.put("isStop", isStop);
        return map;
    }

    @Override
    public HashMap<String, Object> getAddLoopMap() {
        if (mList != null) {
            for (int i = 0; i < mList.size(); i++) {
                if (String.valueOf(week).equals(mList.get(i).getWeek())) {
                    ToastUtil.showMessage(this, "不可添加重复的循环排班");
                    return null;
                }
            }
        }
        if (startTimeStr == null || endTimeStr == null) {
            ToastUtil.showMessage(this, "请完善信息后再提交");
            return null;
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("week", week);
        map.put("beginPointTime", CalculateTimeUtils.getTime(startTimeStr));
        map.put("endPointTime", CalculateTimeUtils.getTime(endTimeStr));
        map.put("state", isStop);
        if (type == CONSULTING_CIRCLE_UPDATE) {
            map.put("id", consultingBean.getId());
        }
        return map;
    }

    @Override
    public HashMap<String, Object> getAddStopMap() {
        if (startTimeStr == null) {
            ToastUtil.showMessage(this, "请选择停诊开始时间");
            return null;
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("uuid", MyApplication.userInfo.getId());
        map.put("beginDate", CalculateTimeUtils.requestDate(startTimeStr));
        map.put("endDate", CalculateTimeUtils.requestDate(endTimeStr));
        if (type == CONSULTING_STOP_UPDATE) {
            map.put("id", consultingBean.getId());
        }
        return map;
    }

    @Override
    public int getType() {
        return type;
    }
}
