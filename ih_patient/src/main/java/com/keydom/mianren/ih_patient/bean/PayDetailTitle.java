package com.keydom.mianren.ih_patient.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.keydom.mianren.ih_patient.adapter.PayDetailAdapter;

import java.util.List;

/**
 * created date: 2019/1/15 on 15:37
 * des:缴费标题
 */
public class PayDetailTitle implements MultiItemEntity {
    private String projectClassificationName;
    private double projectClassificationSumFee;
    private List<PayDetailContent> sonProject;

    public String getProjectClassificationName() {
        return projectClassificationName;
    }

    public void setProjectClassificationName(String projectClassificationName) {
        this.projectClassificationName = projectClassificationName;
    }

    public double getProjectClassificationSumFee() {
        return projectClassificationSumFee;
    }

    public void setProjectClassificationSumFee(double projectClassificationSumFee) {
        this.projectClassificationSumFee = projectClassificationSumFee;
    }

    public List<PayDetailContent> getSonProject() {
        return sonProject;
    }

    public void setSonProject(List<PayDetailContent> sonProject) {
        this.sonProject = sonProject;
    }

    @Override
    public int getItemType() {
        return PayDetailAdapter.TITLE;
    }
}
