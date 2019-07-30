package com.keydom.ih_doctor.activity.issue_information.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_doctor.bean.NotificationBean;
import com.keydom.ih_doctor.constant.TypeEnum;

import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.activity.view
 * @Description：描述信息
 * @Author：song
 * @Date：18/11/16 上午9:14
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:14
 */
public interface NotificationListView extends BaseView {


    /**
     * 获取通知公告成功
     *
     * @param type 判断是刷新还是加载更多
     * @param list 通知公告列表数据
     */
    void getNotificationSuccess(TypeEnum type, List<NotificationBean> list);

    /**
     * 获取通知公告列表失败
     *
     * @param errMsg 失败信息
     */
    void getNotificationFailed(String errMsg);

    /**
     * 删除通知公告成功
     *
     * @param position 删除的通知公告在当前列表中的位置，主要用于刷新本地列表
     */
    void deleteNotificationSuccess(int position);

    /**
     * 删除通知公告失败
     *
     * @param errMsg 失败信息
     */
    void deleteNotificationFailed(String errMsg);

}