package com.keydom.mianren.ih_doctor.activity.personal.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.ApiService;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_doctor.activity.personal.view.ArticleListView;
import com.keydom.mianren.ih_doctor.bean.ArticleItemBean;
import com.keydom.mianren.ih_doctor.constant.Const;
import com.keydom.mianren.ih_doctor.constant.TypeEnum;
import com.keydom.mianren.ih_doctor.m_interface.SingleClick;
import com.keydom.mianren.ih_doctor.net.PersonalApiService;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @Name：com.keydom.ih_doctor.activity.controller
 * @Description：文章列表控制器
 * @Author：song
 * @Date：18/11/16 上午9:09
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:09
 */
public class ArticleListController extends ControllerImpl<ArticleListView> implements View.OnClickListener, OnRefreshListener, OnLoadMoreListener {
    @SingleClick(1000)
    @Override
    public void onClick(View v) {


    }


    /**getArticleSuccess
     * 获取文章
     */
    public void getArticle(final TypeEnum type,String doctorCode) {
        if (type == TypeEnum.REFRESH) {
            setCurrentPage(1);
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("currentPage", getCurrentPage());
        map.put("pageSize", Const.PAGE_SIZE);
        if(getView().getDoctorCode()==null)
            map.put("doctorCode","");
        else
            map.put("doctorCode",getView().getDoctorCode());
//        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PersonalApiService.class).getMyArticle(map), new HttpSubscriber<ArrayList<ArticleItemBean>>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable ArrayList<ArticleItemBean> data) {
                hideLoading();
                getView().getArticleSuccess(type, data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().getArticleFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 获取我的收藏
     */
    public void getCollect(final TypeEnum type) {
        if (type == TypeEnum.REFRESH) {
            setCurrentPage(1);
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("currentPage", getCurrentPage());
        map.put("pageSize", Const.PAGE_SIZE);

//        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PersonalApiService.class).getMyCollect(map), new HttpSubscriber<ArrayList<ArticleItemBean>>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable ArrayList<ArticleItemBean> data) {
                hideLoading();
                getView().getArticleSuccess(type, data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().getArticleFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }


    /**
     * 删除文章
     * @param id 文章ID
     */
    public void delete(long id) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PersonalApiService.class).deleteArticle(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<String>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable String data) {
                hideLoading();
                getView().deleteArticleSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().deleteArticleFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 取消收藏
     */
    public void removeCollect(HashMap<String, Object> map) {

        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ApiService.class).collect(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<String>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable String data) {
                if (getView() != null) {
                    hideLoading();
                    getView().removeCollectSuccess();
                }
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                if (getView() != null) {
                    hideLoading();
                    getView().removeCollectFailed(msg);

                }
                return super.requestError(exception, code, msg);
            }
        });
    }


    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        currentPagePlus();
        if (getView().getType() == TypeEnum.MYARTICLE || getView().getType() == TypeEnum.OHTERSARTICLE) {
            getArticle(TypeEnum.LOAD_MORE,getView().getDoctorCode());
        } else {
            getCollect(TypeEnum.LOAD_MORE);
        }

    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        setCurrentPage(1);
        if (getView().getType() == TypeEnum.MYARTICLE || getView().getType() == TypeEnum.OHTERSARTICLE) {
            getArticle(TypeEnum.REFRESH,getView().getDoctorCode());
        } else {
            getCollect(TypeEnum.REFRESH);
        }
    }
}
