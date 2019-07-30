package com.keydom.ih_doctor.activity.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_common.bean.Article;

import java.util.HashMap;

/**
 * @Name：com.keydom.ih_doctor.activity.view
 * @Description：文章发布
 * @Author：song
 * @Date：18/11/16 上午9:14
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:14
 */
public interface IssueArticleView extends BaseView {

    /**
     * 发布成功
     *
     * @param successMsg 成功信息
     */
    void issueSuccess(String successMsg);

    /**
     * 发布失败
     *
     * @param errMsg 失败信息
     */
    void issueFailed(String errMsg);

    /**
     * 判断是否点击的最后一项，点击图片最后一项添加图片
     *
     * @param position
     * @return true 最后一项<br/>false 不是最后一项
     */
    boolean getLastItemClick(int position);

    /**
     * 获取发布文章参数
     *
     * @return 文章发布参数
     */
    HashMap<String, Object> getArticleMap();

    /**
     * 获取文章详情参数
     *
     * @return 获取文章详情参数
     */
    HashMap<String, Object> getDetailMap();

    /**
     * 图片上传成功
     *
     * @param path 上传的图片地址
     */
    void uploadImgSuccess(String path);

    /**
     * 图片上传失败
     *
     * @param errMsg 失败信息
     */
    void uploadImgFailed(String errMsg);

    /**
     * 获取文章详情成功
     *
     * @param article 文章详情
     */
    void articleInfoSuccess(Article article);

    /**
     * 获取文章详情失败
     *
     * @param msg 失败信息
     */
    void articleInfoFailed(String msg);

    /**
     * 获取图片数量限制
     *
     * @return 图片数量限制
     */
    int getImageLimit();

}