package com.keydom.mianren.ih_patient.activity.health_manager.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.EatBean;
import com.keydom.mianren.ih_patient.bean.EatRecordBean;
import com.keydom.mianren.ih_patient.bean.SleepRecordBean;
import com.keydom.mianren.ih_patient.bean.SportsBean;

import java.util.List;
import java.util.Map;

/**
 * @author 顿顿
 * @date 20/3/4 10:55
 * @des 生活记录
 */
public interface LifestyleMainView extends BaseView {

    void expandBreakfastLayout();

    void expandLunchLayout();

    void expandDinnerLayout();

    void expandExtraLayout();

    List<String> getSleepQualityData();

    List<String> getSleepTimeData();

    List<String> getMentalStateData();

    void setMentalState(int position);

    void setSleepQuality(int position);

    void setSleepTime(int position);

    List<EatBean> getEatRecordParams();

    boolean verifySleepRecordParams();

    boolean verifySportsRecordParams();

    /**
     * @param copyToday 是否为复用今日
     */
    Map<String, String> getSleepRecordParams(boolean copyToday);

    Map<String, String> getDeleteSleepRecordParams(boolean isSleep);

    List<SportsBean> getSportRecordParams();

    int getLifestyleType();

    boolean isNotToday();

    String getCurSelectDate();

    EatRecordBean getEatRecordBean();

    List<SportsBean> getRecordSportsBeans();

    void requestFoodRecordSuccess(EatRecordBean bean);

    void requestFoodRecordFailed();

    void requestSleepRecordSuccess(List<SleepRecordBean> bean);

    void requestSleepRecordFailed();

    void requestSportsRecordSuccess(List<SportsBean> bean);

    void requestSportsRecordFailed();
    void deleteSportsRecordSuccess();

    void copyFoodRecordSuccess();

    Map<String, Object> getUpdateEatDataParams(EatRecordBean bean);

    void setNewDate(int value);

    String getPatientId();
}
