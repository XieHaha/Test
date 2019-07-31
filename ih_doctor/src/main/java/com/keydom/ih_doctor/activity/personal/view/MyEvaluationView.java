package com.keydom.ih_doctor.activity.personal.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_doctor.bean.EvaluationRes;
import com.keydom.ih_doctor.constant.TypeEnum;

/**
 * @Name：com.keydom.ih_doctor.activity.view
 * @Description：我的评论view
 * @Author：song
 * @Date：18/11/16 上午9:14
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:14
 */
public interface MyEvaluationView extends BaseView {


    /**
     * 获取评论成功
     */
    void getEvaluationSuccess(EvaluationRes bean, TypeEnum type);

    /**
     * 获取评论失败
     */
    void getEvaluationFailed(String errMsg);

    /**
     * 获取类型
     */
    String getType();


    /**
     * 选中问诊评论
     */
    void setDiagnose();

    /**
     * 选中咨询评论
     */
    void setConsult();

    /**
     * 获取请求Type
     */
    int getReqType();

}