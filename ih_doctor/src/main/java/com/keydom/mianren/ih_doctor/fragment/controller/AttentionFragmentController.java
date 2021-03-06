package com.keydom.mianren.ih_doctor.fragment.controller;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_doctor.MyApplication;
import com.keydom.mianren.ih_doctor.bean.ArticleListBean;
import com.keydom.mianren.ih_doctor.constant.Const;
import com.keydom.mianren.ih_doctor.constant.TypeEnum;
import com.keydom.mianren.ih_doctor.fragment.view.AttentionFragmentView;
import com.keydom.mianren.ih_doctor.net.MainApiService;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.fragment.controller
 * @Description：描述信息
 * @Author：song
 * @Date：18/11/16 下午2:26
 * 修改人：xusong
 * 修改时间：18/11/16 下午2:26
 */
public class AttentionFragmentController extends ControllerImpl<AttentionFragmentView> implements OnRefreshListener, OnLoadMoreListener {


    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        getAttentionArticle(TypeEnum.LOAD_MORE, MyApplication.userInfo.getId(), getCurrentPage(), Const.PAGE_SIZE);
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        setCurrentPage(1);
        getAttentionArticle(TypeEnum.REFRESH, MyApplication.userInfo.getId(), getCurrentPage(), Const.PAGE_SIZE);
    }

    /**
     * 获取我的关注列表
     *
     * @param type        刷新还是加载更多
     * @param userId      用户ID
     * @param currentPage 当前页
     * @param pageSize    每一页的数量
     */
    public void getAttentionArticle(final TypeEnum type, long userId, int currentPage, int pageSize) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("currentPage", currentPage);
        map.put("pageSize", pageSize);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(MainApiService.class).getAttentionArticle(map), new HttpSubscriber<List<ArticleListBean>>(getContext()) {
            @Override
            public void requestComplete(@Nullable List<ArticleListBean> data) {
                hideLoading();
                getView().getDataSuccess(type, data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {

                hideLoading();
                getView().getDataFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }


    /**
     * 获取置顶文章列表
     *
     * @param hospitalId 医院ID
     */
    public void getTopArticle(String hospitalId) {
        HashMap<String, String> map = new HashMap<>();
        map.put("hospitalId", hospitalId);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(MainApiService.class).getstickArticle(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<ArticleListBean>(getContext()) {
            @Override
            public void requestComplete(@Nullable ArticleListBean data) {
                hideLoading();
                getView().getTopDataSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().getTopFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }


    /**
     * 文章点赞
     *
     * @param userId    用户ID
     * @param userIcon  用户头像
     * @param articleId 文章ID
     * @param isLike    点赞还是取消点赞
     */
    public void articleLike(int userId, String userIcon, int articleId, int isLike) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("doctorImage", userIcon);
        map.put("id", articleId);
        map.put("isLike", isLike);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(MainApiService.class).likeArticle(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<String>() {
            @Override
            public void requestComplete(@Nullable String data) {
                hideLoading();
                getView().articleLikeSuccess();
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().articleLikeFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }


}
