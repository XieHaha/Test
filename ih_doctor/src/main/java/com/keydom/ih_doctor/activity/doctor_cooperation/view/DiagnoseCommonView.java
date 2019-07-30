package com.keydom.ih_doctor.activity.doctor_cooperation.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_doctor.bean.ChangeDiagnoseRecoderBean;
import com.keydom.ih_doctor.constant.TypeEnum;

import java.util.List;
import java.util.Map;

/**
 * @Name：com.keydom.ih_doctor.activity.view
 * @Author：song
 * @Date：18/11/16 上午9:14
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:14
 */
public interface DiagnoseCommonView extends BaseView {


    /**
     * 成功获取记录列表
     *
     * @param list 转诊记录列表
     * @param type 判断是刷新操作还是加载更多
     */
    void getListSuccess(List<ChangeDiagnoseRecoderBean> list, TypeEnum type);

    /**
     * 获取记录列表失败
     *
     * @param errMsg 失败信息
     */
    void getListFailed(String errMsg);

    /**
     * 获取查询参数
     *
     * @return 查询参数
     */
    Map<String, Object> getQueryMap();

}