package com.keydom.mianren.ih_patient.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author 顿顿
 * @date 21/4/2 15:05
 * @des
 */
public class EatRecordBean implements Serializable {
    private static final long serialVersionUID = 4994957793634044465L;

    private List<EatBean> breakfastList;
    private List<EatBean> lunchList;
    private List<EatBean> dinnerList;
    private List<EatBean> snacksList;

    public List<EatBean> getBreakfastList() {
        return breakfastList;
    }

    public void setBreakfastList(List<EatBean> breakfastList) {
        this.breakfastList = breakfastList;
    }

    public List<EatBean> getLunchList() {
        return lunchList;
    }

    public void setLunchList(List<EatBean> lunchList) {
        this.lunchList = lunchList;
    }

    public List<EatBean> getDinnerList() {
        return dinnerList;
    }

    public void setDinnerList(List<EatBean> dinnerList) {
        this.dinnerList = dinnerList;
    }

    public List<EatBean> getSnacksList() {
        return snacksList;
    }

    public void setSnacksList(List<EatBean> snacksList) {
        this.snacksList = snacksList;
    }
}
