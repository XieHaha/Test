package com.keydom.ih_doctor.activity.personal.view;

import com.keydom.ih_common.base.BaseView;

import java.util.Map;

/**
 * @Name：com.keydom.ih_doctor.activity.view
 * @Author：song
 * @Date：18/11/16 上午9:14
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:14
 */
public interface FeedBackView extends BaseView {



    /**
     * 反馈成功
     *
     * @param msg 成功信息
     */
    void feedBackSuccess(String msg);


    /**
     * 反馈失败
     *
     */
    void feedBackFailed(String errMsg);


    /**
     * 图片上传成功
     *
     */
    void uploadSuccess(String msg);


    /**
     * 图片上传失败
     *
     * @param msg 失败信息
     */
    void uploadFailed(String msg);


    /**
     * 判断点击的图片是不是最后一项
     *
     * @param position 点击的图片位置
     */
    boolean getLastItemClick(int position);


    /**
     * 获取提交反馈请求参数
     *
     * @return 请求参数
     */
    Map<String, Object> getFeedBackMap();


    /**
     * 检查是否可以提交
     *
     */
    boolean checkFeedBack();


    /**
     * 获取提交了多少张图片
     *
     */
    int getImgSize();

}