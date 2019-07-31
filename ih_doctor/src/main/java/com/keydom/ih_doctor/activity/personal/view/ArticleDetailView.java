package com.keydom.ih_doctor.activity.personal.view;

import com.keydom.ih_common.base.BaseView;

/**
 * @Name：com.keydom.ih_doctor.activity.view
 * @Description：文章详情view
 * @Author：song
 * @Date：18/11/16 上午9:14
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:14
 */
public interface ArticleDetailView extends BaseView {


    /**
     * 获取详情成功
     */
    void getDetailSuccess();

    /**
     * 获取详情失败
     */
    void getDetailFailed(String errMsg);

    /**
     * 获取评论成功
     */
    void commentSuccess();

    /**
     * 获取评论失败
     */
    void commentFailed();

    /**
     * 收藏成功
     */
    void collectSuccess();

    /**
     * 收藏失败
     */
    void collectFailed();

    /**
     * 点赞成功
     */
    void supportSuccess();

    /**
     * 点赞失败
     */
    void supportFailed();

}