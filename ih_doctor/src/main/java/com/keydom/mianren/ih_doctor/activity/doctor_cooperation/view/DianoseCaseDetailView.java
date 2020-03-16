package com.keydom.mianren.ih_doctor.activity.doctor_cooperation.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_doctor.bean.DiagnoseCaseBean;

import java.util.Map;

/**
 * @Name：com.keydom.ih_doctor.activity.view
 * @Author：song
 * @Date：18/11/16 上午9:14
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:14
 */
public interface DianoseCaseDetailView extends BaseView {


    /**
     * 成功获取病历记录详情
     *
     * @param bean 病历详情
     */
    void getDetailSuccess(DiagnoseCaseBean bean);

    /**
     * 获取病历详情失败
     *
     * @param errMsg 失败信息
     */
    void getDetailFailed(String errMsg);

    /**
     * 获取病历详情参数
     *
     * @return 请求病历详情参数
     */
    Map<String, Object> getQueryMap();

}