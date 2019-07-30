package com.keydom.ih_doctor.activity.issue_information.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_common.bean.NoticeInfoBean;

import java.util.HashMap;

/**
 * @Name：com.keydom.ih_doctor.activity.view
 * @Description：文章发布
 * @Author：song
 * @Date：18/11/16 上午9:14
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:14
 */
public interface IssueNotificationView extends BaseView {

    /**
     * 发布或者修改成功
     *
     * @param successMsg 成功信息
     */
    void issueSuccess(String successMsg);

    /**
     * 发布或者修改失败
     *
     * @param errMsg 失败信息
     */
    void issueFailed(String errMsg);

    /**
     * 判断是否点击的图片最后一张
     *
     * @param position 点击的position
     * @return true 是  false 不是
     */
    boolean getLastItemClick(int position);

    /**
     * 获取通知公告详情参数
     *
     * @return 通知公告请求参数
     */
    HashMap<String, Object> getNotificationMap();

    /**
     * 图片上传成功
     *
     * @param path 图片地址
     */
    void uploadImgSuccess(String path);

    /**
     * 图片上传失败
     *
     * @param errMsg 失败信息
     */
    void uploadImgFailed(String errMsg);

    /**
     * 获取通知公告详情成功
     *
     * @param bean 通知公告详情数据
     */
    void getNotificationSuccess(NoticeInfoBean bean);

    /**
     * 获取通知公告详情数据失败
     *
     * @param errMsg 失败信息
     */
    void getNotificationFailed(String errMsg);

    /**
     * 获取还能添加多少张图片
     *
     * @return 可以上传的图片数量
     */
    int getImageLimit();

}