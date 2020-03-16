package com.keydom.mianren.ih_doctor.activity.consulting_arrange.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_doctor.bean.ConsultingBean;

import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.activity.view
 * @Author：song
 * @Date：18/11/16 上午9:14
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:14
 */
public interface ConsultingArrangeView extends BaseView {

    /**
     * 获取门诊排班成功
     *
     * @param list 获取到的门诊排班列表数据
     */
    void getConsultingSuccess(List<ConsultingBean> list);

    /**
     * 获取门诊排班失败
     *
     * @param msg 失败信息
     */
    void getConsultingFailed(String msg);
}