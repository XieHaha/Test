package com.keydom.mianren.ih_patient.activity.postpartum_rehabilitation.view;

import com.keydom.ih_common.base.BaseView;

import java.util.List;

/**
 * @date 20/2/26 10:47
 * @des 产后康复
 */
public interface RehabilitationRecordView extends BaseView {

    /**
     * 数据源
     */
    void fillRehabilitationRecordData(List<String> data);

    /**
     * 关注
     */
    void executeFollow();

    /**
     * 点赞
     */
    void executeAccolade();
}
