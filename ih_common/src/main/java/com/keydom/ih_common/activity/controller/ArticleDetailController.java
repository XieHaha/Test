package com.keydom.ih_common.activity.controller;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.keydom.ih_common.R;
import com.keydom.ih_common.activity.ArticleDetailActivity;
import com.keydom.ih_common.activity.view.ArticleDetailView;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.bean.Article;
import com.keydom.ih_common.bean.ArticleCommentBean;
import com.keydom.ih_common.bean.ArticleLikeBean;
import com.keydom.ih_common.bean.HealthArticalInfo;
import com.keydom.ih_common.bean.HealthArticleCommentBean;
import com.keydom.ih_common.bean.NoticeInfoBean;
import com.keydom.ih_common.constant.Const;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.ApiService;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.CalculateTimeUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.activity.controller
 * @Description：描述信息
 * @Author：song
 * @Date：18/11/16 上午9:09
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:09
 */
public class ArticleDetailController extends ControllerImpl<ArticleDetailView> implements View.OnClickListener, ExpandableListView.OnGroupClickListener, ExpandableListView.OnChildClickListener, ExpandableListView.OnGroupExpandListener, TextWatcher, OnLoadMoreListener {
    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.attention_tv) {
            if (CalculateTimeUtils.isFastClick(2000)) {
                return;
            }
            updateAttention(getView().getAttentionMap());
        } else if (v.getId() == R.id.comment_tv) {
            ((ArticleDetailActivity) getContext()).moveToComment();
        } else if (v.getId() == R.id.collect_iv) {
            if (CalculateTimeUtils.isFastClick(2000)) {
                return;
            }
            updateMyCollect(getView().getCollectMap());
        } else if (v.getId() == R.id.comment_input_ev) {
            if (getView().getType() == Const.HEALTH_DETAILS) {
                ((ArticleDetailActivity) getContext()).showHealthCommentDialog();
            } else if (getView().getType() == Const.ARTICLE_DETAILS) {
                ((ArticleDetailActivity) getContext()).showCommentDialog();
            }

        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        Toast.makeText(getContext(), "点击了回复", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {


        if (getView().getType() == Const.HEALTH_DETAILS) {
            ((ArticleDetailActivity) getContext()).showHealthReplyDialog(groupPosition);
        } else if (getView().getType() == Const.ARTICLE_DETAILS) {
            ((ArticleDetailActivity) getContext()).showReplyDialog(groupPosition);
        }
        return true;
    }

    @Override
    public void onGroupExpand(int groupPosition) {

    }


    /**
     * 获取文章详情
     */

    public void getArticleDetails(int type, HashMap<String, Object> map) {

        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ApiService.class).articleInfo(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<Article>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable Article data) {
                if (getView() != null) {
                    getView().articleInfoSuccess(data);
                }
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                if (getView() != null) {
                    getView().articleInfoFailed(msg);
                }
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 获取健康知识详情
     */
    public void getHealthDetails(HashMap<String, Object> map) {

        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ApiService.class).getHealthKnowledgeInfo(map), new HttpSubscriber<HealthArticalInfo>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable HealthArticalInfo data) {
                getView().healthInfoSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().healthInfoFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 获取评论
     */
    public void getArticleComments(HashMap<String, Object> map) {

//        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ApiService.class).articleCommentList(map), new HttpSubscriber<List<ArticleCommentBean>>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable List<ArticleCommentBean> data) {
                if (getView() != null) {
                    hideLoading();
                    getView().articleCommentListSuccess(data);
                }

            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                if (getView() != null) {
                    getView().articleInfoFailed(msg);
                }
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 获取健康知识评论
     */
    public void getHealthArticleComments(HashMap<String, Object> map) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ApiService.class).healthArticleCommentList(map), new HttpSubscriber<List<HealthArticleCommentBean>>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable List<HealthArticleCommentBean> data) {
                getView().healthArticleCommentListSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().healthArticleCommentListFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }


    /**
     * 添加评论
     *
     * @param articleId
     * @param criticsId
     * @param criticsName
     * @param criticsImage
     * @param byCriticsName
     * @param byCommentsContext
     */
    public void addArticleComment(long byCriticsId, long articleId, long criticsId, String criticsName, String criticsImage, String byCriticsName, String byCommentsContext, String myCommentContext) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("articleId", String.valueOf(articleId));
        map.put("criticsId", String.valueOf(criticsId));
        map.put("criticsName", criticsName);
        map.put("criticsImage", criticsImage);
        map.put("byCriticsName", byCriticsName);
        map.put("byCommentsContext", byCommentsContext);
        map.put("myCommentContext", myCommentContext);
        map.put("byCriticsId", byCriticsId);
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ApiService.class).addArticleComment(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<ArticleCommentBean>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable ArticleCommentBean data) {
                if (getView() != null) {
                    hideLoading();
                    getView().addArticleCommentSuccess(data);
                }
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                if (getView() != null) {
                    hideLoading();
                    getView().addArticleCommentFailed(msg);
                }
                return super.requestError(exception, code, msg);
            }
        });
    }


    /**
     * 健康知识添加评论
     *
     * @param articleId
     * @param criticsId
     * @param ibyCriticsId
     * @param byCommentsContext
     * @param myCommentContexxt
     */
    public void addHealthArticleComment(long articleId, long criticsId, long ibyCriticsId, String byCommentsContext, String myCommentContexxt) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("healthKnowledgeId", articleId);
        map.put("criticsId", criticsId);
        if (ibyCriticsId != -1) {
            map.put("ibyCriticsId", ibyCriticsId);
        }
        if (byCommentsContext != null) {
            map.put("byCommentsContext", byCommentsContext);
        }
        map.put("myCommentContexxt", myCommentContexxt);
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ApiService.class).addHealthArticleComment(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<HealthArticleCommentBean>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable HealthArticleCommentBean data) {
                if (getView() != null) {
                    hideLoading();
                    getView().addHealthArticleCommentSuccess(data);
                }
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                if (getView() != null) {
                    hideLoading();
                    getView().addHealthArticleCommentFailed(msg);
                }
                return super.requestError(exception, code, msg);
            }
        });
    }


    /**
     * 评论点赞
     *
     * @param id
     * @param isLike
     */
    public void updateArticleCommentLike(int id, int isLike) {
        HashMap<String, Object> map = getView().getCommentLikeMap();
        map.put("id", String.valueOf(id));
        map.put("isLike", String.valueOf(isLike));
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ApiService.class).updateArticleCommentLike(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<String>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable String data) {
                if (getView() != null) {
                    hideLoading();
                }
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                if (getView() != null) {
                    hideLoading();
                }
                return super.requestError(exception, code, msg);
            }
        });
    }


    /**
     * 收藏
     */
    public void updateMyCollect(HashMap<String, Object> map) {

        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ApiService.class).collect(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<String>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable String data) {
                if (getView() != null) {
                    hideLoading();
                    getView().collectSuccess(data);
                }
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                if (getView() != null) {
                    hideLoading();
                    getView().collectFailed(msg);

                }
                return super.requestError(exception, code, msg);
            }
        });
    }


    /**
     * 关注
     *
     * @param map
     */
    public void updateAttention(HashMap<String, Object> map) {

        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ApiService.class).updateMyAttention(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<String>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable String data) {
                if (getView() != null) {
                    hideLoading();
                    getView().attentionSuccess(data);
                }
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                if (getView() != null) {
                    hideLoading();
                    getView().attentionFailed(msg);
                }
                return super.requestError(exception, code, msg);
            }
        });
    }


    public void getArticleLikeList(HashMap<String, Object> map) {

//        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ApiService.class).articleLikeInfo(map), new HttpSubscriber<ArticleLikeBean>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable ArticleLikeBean data) {
                getView().articleLikeInfoSuccess(data);
//                hideLoading();
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().articleLikeInfoFailed(msg);
//                hideLoading();
                return super.requestError(exception, code, msg);
            }
        });
    }


    public void getNoticeDetail(HashMap<String, Object> map) {

        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ApiService.class).getNoticeInfo(map), new HttpSubscriber<NoticeInfoBean>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable NoticeInfoBean data) {
                hideLoading();
                getView().getNoticeInfoSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().getNoticeInfoFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }


    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        if (getView().getType() == Const.HEALTH_DETAILS) {
            getHealthArticleComments(getView().getHealthCommentMap());
        } else if (getView().getType() == Const.ARTICLE_DETAILS) {
            getArticleComments(getView().getCommentMap());
        }

    }


}
