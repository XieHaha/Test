package com.keydom.ih_doctor.activity.doctor_cooperation.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_doctor.bean.DiagnoseRecoderBean;
import com.keydom.ih_doctor.constant.TypeEnum;

import java.util.List;
import java.util.Map;

/**
 * @Name：com.keydom.ih_doctor.activity.view
 * @Description：描述信息
 * @Author：song
 * @Date：18/11/16 上午9:14
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:14
 */
public interface DiagnoseRecoderView extends BaseView {

    /**
     * 成功获取问诊记录
     *
     * @param list 问诊记录列表
     * @param type 请求type，判断是刷新还是加载更多
     */
    void getDiagnoseRecoderSuccess(List<DiagnoseRecoderBean> list, TypeEnum type);

    /**
     * 获取问诊记录列表失败
     *
     * @param errMsg 失败信息
     */
    void getDiagnoseRecoderFailed(String errMsg);

    /**
     * 获取请求问诊记录参数
     *
     * @return 请求参数
     */
    Map<String, Object> getQueryMap();


}