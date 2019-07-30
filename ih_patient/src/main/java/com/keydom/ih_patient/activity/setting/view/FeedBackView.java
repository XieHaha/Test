package com.keydom.ih_patient.activity.setting.view;

import com.keydom.ih_common.base.BaseView;

import java.util.Map;

/**
 * @Name：com.keydom.ih_doctor.activity.view
 * @Description：意见反馈接口
 * @Author：song
 * @Date：18/11/16 上午9:14
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:14
 */
public interface FeedBackView extends BaseView {


    /**
     * 提交意见成功
     */
    void feedBackSuccess(String msg);

    /**
     * 提交失败
     */
    void feedBackFailed(String errMsg);

    /**
     * 上传图片成功
     */
    void uploadSuccess(String msg);

    /**
     * 上传失败
     */
    void uploadFailed(String msg);

    /**
     * 判断最近一次点击item
     */
    boolean getLastItemClick(int position);

    /**
     * 获取提交意见参数
     */
    Map<String, Object> getFeedBackMap();

    /**
     * 判断是否提交
     */
    boolean checkFeedBack();

    /**
     * 获取图片大小
     */
    int getImgSize();

    String getPicUrl(int position);

}