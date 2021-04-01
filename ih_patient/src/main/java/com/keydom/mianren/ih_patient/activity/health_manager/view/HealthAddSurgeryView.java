package com.keydom.mianren.ih_patient.activity.health_manager.view;

import com.keydom.ih_common.base.BaseView;

import java.util.Date;
import java.util.List;

/**
 * @author 顿顿
 * @date 20/3/4 10:55
 * @des 添加手术史
 */
public interface HealthAddSurgeryView extends BaseView {

    List<String> getSurgeryStatus();

    void setSurgeryStatus(int position);

    void selectSurgeryDate(Date date);
}
