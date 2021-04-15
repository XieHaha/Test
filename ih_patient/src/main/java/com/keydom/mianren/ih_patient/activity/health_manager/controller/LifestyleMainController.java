package com.keydom.mianren.ih_patient.activity.health_manager.controller;

import android.view.View;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.health_manager.LifestyleDataActivity;
import com.keydom.mianren.ih_patient.activity.health_manager.view.LifestyleMainView;
import com.keydom.mianren.ih_patient.bean.EatRecordBean;
import com.keydom.mianren.ih_patient.bean.SleepRecordBean;
import com.keydom.mianren.ih_patient.bean.SportsBean;
import com.keydom.mianren.ih_patient.net.ChronicDiseaseService;
import com.keydom.mianren.ih_patient.utils.DateUtils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.keydom.mianren.ih_patient.activity.health_manager.LifestyleMainActivity.LIFESTYLE_DIET;
import static com.keydom.mianren.ih_patient.activity.health_manager.LifestyleMainActivity.LIFESTYLE_SLEEP;
import static com.keydom.mianren.ih_patient.activity.health_manager.LifestyleMainActivity.LIFESTYLE_SPORTS;

/**
 * @author 顿顿
 * @date 20/3/4 10:56
 * @des 生活记录
 */
public class LifestyleMainController extends ControllerImpl<LifestyleMainView> implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        OptionsPickerView pickerView;
        switch (v.getId()) {
            case R.id.lifestyle_main_last_day_iv:
                if (v.isSelected()) {
                    getView().setNewDate(-1);
                }
                break;
            case R.id.lifestyle_main_next_day_tv:
                if (v.isSelected()) {
                    getView().setNewDate(1);
                }
                break;
            case R.id.lifestyle_bottom_cancel_tv:
                if (getView().getLifestyleType() == LIFESTYLE_SLEEP) {
                    if (getView().verifySleepRecordParams()) {
                        deleteExerciseAndSleepRecord(true);
                    }
                } else if (getView().getLifestyleType() == LIFESTYLE_SPORTS) {
                    if (getView().verifySportsRecordParams()) {
                        //重置运动
                        deleteExerciseAndSleepRecord(false);
                    }
                }
                break;
            case R.id.lifestyle_bottom_submit_tv:
                if (getView().getLifestyleType() == LIFESTYLE_SLEEP) {
                    //睡眠
                    if (getView().verifySleepRecordParams()) {
                        insertOrUpdateSleepRecord(false);
                    }
                } else {
                    //运动
                    LifestyleDataActivity.start(getContext(), getView().getLifestyleType(),
                            getView().getCurSelectDate(), getView().getRecordSportsBeans(),
                            getView().getPatientId());
                }
                break;
            case R.id.view_eat_record_add_breakfast_tv:
            case R.id.eat_record_breakfast_add_tv:
                LifestyleDataActivity.start(getContext(), getView().getLifestyleType(), 0,
                        getView().getCurSelectDate(), getView().getEatRecordBean(),
                        getView().getPatientId());
                break;
            case R.id.view_eat_record_add_lunch_tv:
            case R.id.eat_record_lunch_add_tv:
                LifestyleDataActivity.start(getContext(), getView().getLifestyleType(), 1,
                        getView().getCurSelectDate(), getView().getEatRecordBean(),
                        getView().getPatientId());
                break;
            case R.id.view_eat_record_add_dinner_tv:
            case R.id.eat_record_dinner_add_tv:
                LifestyleDataActivity.start(getContext(), getView().getLifestyleType(), 2,
                        getView().getCurSelectDate(), getView().getEatRecordBean(),
                        getView().getPatientId());
                break;
            case R.id.view_eat_record_add_extra_tv:
            case R.id.eat_record_extra_add_tv:
                LifestyleDataActivity.start(getContext(), getView().getLifestyleType(), 3,
                        getView().getCurSelectDate(), getView().getEatRecordBean(),
                        getView().getPatientId());
                break;
            case R.id.lifestyle_main_copy_tv:
                new GeneralDialog(getContext(), "确认复用到今日？",
                        () -> {
                            //复用到今日
                            if (getView().getLifestyleType() == LIFESTYLE_DIET) {
                                //饮食
                                if (getView().verifyEatRecordParams()) {
                                    insertOrUpdateFoodRecord();
                                }
                            } else if (getView().getLifestyleType() == LIFESTYLE_SLEEP) {
                                //睡眠
                                if (getView().verifySleepRecordParams()) {
                                    insertOrUpdateSleepRecord(true);
                                }
                            } else {
                                //运动
                                if (getView().verifySportsRecordParams()) {
                                    insertOrUpdateExerciseRecord();
                                }
                            }
                        }).setTitle("提示").setPositiveButton("确认").show();
                break;
            case R.id.view_eat_record_add_breakfast_iv:
                getView().expandBreakfastLayout();
                break;
            case R.id.view_eat_record_add_lunch_iv:
                getView().expandLunchLayout();
                break;
            case R.id.view_eat_record_add_dinner_iv:
                getView().expandDinnerLayout();
                break;
            case R.id.view_eat_record_add_extra_iv:
                getView().expandExtraLayout();
                break;
            case R.id.view_sleep_record_quality_tv:
                pickerView = new OptionsPickerBuilder(getContext(),
                        (options1, option2, options3, v12) -> getView().setSleepQuality(options1)).build();
                pickerView.setPicker(getView().getSleepQualityData());
                pickerView.show();
                break;
            case R.id.view_sleep_record_time_tv:
                pickerView = new OptionsPickerBuilder(getContext(),
                        (options1, option2, options3, v12) -> getView().setSleepTime(options1)).build();
                pickerView.setPicker(getView().getSleepTimeData());
                pickerView.show();
                break;
            case R.id.view_sleep_record_status_tv:
                pickerView = new OptionsPickerBuilder(getContext(),
                        (options1, option2, options3, v12) -> getView().setMentalState(options1)).build();
                pickerView.setPicker(getView().getMentalStateData());
                pickerView.show();
                break;
            default:
                break;
        }
    }

    /**
     * 查询患者就餐记录
     */
    public void foodRecordList(String patientId, String curSelectDate) {
        Map<String, String> params = new HashMap<>(16);
        params.put("time", DateUtils.transDate(curSelectDate, DateUtils.YYYY_MM_DD_CH,
                DateUtils.YYYY_MM_DD));
        params.put("patientId", patientId);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ChronicDiseaseService.class).foodRecordList(params), new HttpSubscriber<EatRecordBean>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable EatRecordBean data) {
                getView().requestFoodRecordSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().requestFoodRecordFailed();
                ToastUtil.showMessage(getContext(), msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 新增或者修改就餐记录
     */
    public void insertOrUpdateFoodRecord() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ChronicDiseaseService.class).insertOrUpdateFoodRecord(HttpService.INSTANCE.object2Body(getView().getEatRecordParams())), new HttpSubscriber<String>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable String data) {
                getView().copyFoodRecordSuccess();
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                ToastUtil.showMessage(getContext(), msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 删除就餐记录
     */
    public void deleteFoodRecord(String id) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ChronicDiseaseService.class).deleteFoodRecord(id), new HttpSubscriber<String>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable String data) {
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                ToastUtil.showMessage(getContext(), msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 查询患者睡眠记录
     */
    public void sleepRecordList(String patientId, String curSelectDate) {
        Map<String, String> params = new HashMap<>(16);
        params.put("time", DateUtils.transDate(curSelectDate, DateUtils.YYYY_MM_DD_CH,
                DateUtils.YYYY_MM_DD));
        params.put("patientId", patientId);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ChronicDiseaseService.class).sleepRecordList(params), new HttpSubscriber<List<SleepRecordBean>>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable List<SleepRecordBean> data) {
                getView().requestSleepRecordSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().requestSleepRecordFailed();
                ToastUtil.showMessage(getContext(), msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 新增或者修改睡眠记录
     */
    public void insertOrUpdateSleepRecord(boolean copyToday) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ChronicDiseaseService.class).insertOrUpdateSleepRecord(HttpService.INSTANCE.object2Body(getView().getSleepRecordParams(copyToday))), new HttpSubscriber<String>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable String data) {
                if (copyToday) {
                    getView().copyFoodRecordSuccess();
                }
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                ToastUtil.showMessage(getContext(), msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 删除睡眠记录或运动记录
     */
    public void deleteExerciseAndSleepRecord(boolean isSleep) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ChronicDiseaseService.class).deleteExerciseAndSleepRecord(getView().getDeleteSleepRecordParams(isSleep)), new HttpSubscriber<String>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable String data) {
                if (isSleep) {
                    getView().requestSleepRecordFailed();
                } else {
                    getView().requestSportsRecordFailed();
                }
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                ToastUtil.showMessage(getContext(), msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 查询患者运动记录
     */
    public void exerciseRecordList(String patientId, String curSelectDate) {
        Map<String, String> params = new HashMap<>(16);
        params.put("time", DateUtils.transDate(curSelectDate, DateUtils.YYYY_MM_DD_CH,
                DateUtils.YYYY_MM_DD));
        params.put("patientId", patientId);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ChronicDiseaseService.class).exerciseRecordList(params), new HttpSubscriber<List<SportsBean>>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable List<SportsBean> data) {
                getView().requestSportsRecordSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().requestSportsRecordFailed();
                ToastUtil.showMessage(getContext(), msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 新增或者修改运动记录
     */
    public void insertOrUpdateExerciseRecord() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ChronicDiseaseService.class).insertOrUpdateExerciseRecord(HttpService.INSTANCE.object2Body(getView().getSportRecordParams())), new HttpSubscriber<String>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable String data) {
                getView().copyFoodRecordSuccess();
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                ToastUtil.showMessage(getContext(), msg);
                return super.requestError(exception, code, msg);
            }
        });
    }


    /**
     * 删除运动记录
     */
    public void deleteExerciseRecord(String id) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ChronicDiseaseService.class).deleteExerciseRecord(id), new HttpSubscriber<String>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable String data) {
                getView().deleteSportsRecordSuccess();
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                ToastUtil.showMessage(getContext(), msg);
                return super.requestError(exception, code, msg);
            }
        });
    }
}
