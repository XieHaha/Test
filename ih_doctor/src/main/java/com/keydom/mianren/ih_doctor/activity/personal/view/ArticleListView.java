package com.keydom.mianren.ih_doctor.activity.personal.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_doctor.bean.ArticleItemBean;
import com.keydom.mianren.ih_doctor.constant.TypeEnum;

import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.activity.view
 * @Description：文章列表view
 * @Author：song
 * @Date：18/11/16 上午9:14
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:14
 */
public interface ArticleListView extends BaseView {


    /**
     * 获取文章列表成功
     *
     * @param type
     * @param list 文章列表数据
     */

    void getArticleSuccess(TypeEnum type, List<ArticleItemBean> list);


    /**
     * 获取文章列表失败
     *
     * @param errMsg 失败信息
     */
    void getArticleFailed(String errMsg);

    /**
     * 删除文章成功
     *
     * @param msg 成功信息
     */
    void deleteArticleSuccess(String msg);


    /**
     * 删除失败
     *
     * @param errMsg 失败信息
     */
    void deleteArticleFailed(String errMsg);


    /**
     * 获取Type
     */
    TypeEnum getType();

    String getDoctorCode();

    void removeCollectSuccess();

    void removeCollectFailed(String msg);
}