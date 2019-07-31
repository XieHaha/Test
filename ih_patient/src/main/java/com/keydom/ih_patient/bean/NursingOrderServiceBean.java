package com.keydom.ih_patient.bean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.keydom.ih_patient.adapter.NursingOrderServiceAdapter;

import java.io.Serializable;
import java.util.List;

/**
 * created date: 2018/12/20 on 15:08
 * des:护理子订单
 */
public class NursingOrderServiceBean extends AbstractExpandableItem<NursingOrderServiceItemBean> implements MultiItemEntity,Serializable {

    private int frequency;//第几次
    private int state;
    private String stateString;
    private List<NursingOrderServiceItemBean> list;
    private String subOrderString;

    public String getSubOrderString() {
        return subOrderString;
    }

    public void setSubOrderString(String subOrderString) {
        this.subOrderString = subOrderString;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateString() {
        return stateString;
    }

    public void setStateString(String stateString) {
        this.stateString = stateString;
    }

    public List<NursingOrderServiceItemBean> getList() {
        return list;
    }

    public void setList(List<NursingOrderServiceItemBean> list) {
        this.list = list;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getItemType() {
        return NursingOrderServiceAdapter.TYPE_LEVEL_0;
    }
}
