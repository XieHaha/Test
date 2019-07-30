package com.keydom.ih_doctor.fragment.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_doctor.bean.ArticleListBean;
import com.keydom.ih_doctor.constant.TypeEnum;

import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.fragment.view
 * @Description：描述信息
 * @Author：song
 * @Date：18/11/16 下午2:25
 * 修改人：xusong
 * 修改时间：18/11/16 下午2:25
 */
public interface TrendsFragmentView extends BaseView {
    /**
     * 获取文章列表成功
     *
     * @param type    刷新还是加载更多
     * @param article 文章列表
     */
    void getDataSuccess(TypeEnum type, List<ArticleListBean> article);

    /**
     * 获取文章列表失败
     *
     * @param errMsg 失败提示信息
     */
    void getDataFailed(String errMsg);

    /**
     * 点赞／取消点赞成功
     */
    void articleLikeSuccess();

    /**
     * 点赞／取消点赞失败
     *
     * @param errMsg 失败提示信息
     */
    void articleLikeFailed(String errMsg);

    /**
     * 获取置顶文章成功
     *
     * @param article 置顶文章对象
     */
    void getTopDataSuccess(ArticleListBean article);

    /**
     * 获取置顶文章失败
     *
     * @param errMsg 失败提示信息
     */
    void getTopFailed(String errMsg);

}
