package com.keydom.mianren.ih_doctor.fragment;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ToastUtils;
import com.ganxin.library.LoadDataLayout;
import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_doctor.MyApplication;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.adapter.CommunityRecyclrViewAdapter;
import com.keydom.mianren.ih_doctor.adapter.WrapRecyclerAdapter;
import com.keydom.mianren.ih_doctor.bean.ArticleListBean;
import com.keydom.mianren.ih_doctor.constant.Const;
import com.keydom.mianren.ih_doctor.constant.TypeEnum;
import com.keydom.mianren.ih_doctor.fragment.controller.AttentionFragmentController;
import com.keydom.mianren.ih_doctor.fragment.view.AttentionFragmentView;
import com.keydom.mianren.ih_doctor.view.WrapRecyclerView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @Name：com.kentra.yxyz.fragment
 * @Description：社区－我的关注页面
 * @Author：song
 * @Date：18/11/5 下午5:27
 * 修改人：xusong
 * 修改时间：18/11/5 下午5:27
 */
public class CommunityAttentionFragment extends BaseControllerFragment<AttentionFragmentController> implements AttentionFragmentView {

    private WrapRecyclerView communityAttentionRv;
    /**
     * 文章适配器
     */
    private WrapRecyclerAdapter mAdapter;
    private RefreshLayout refreshLayout;
    private LinearLayout headView;
    private List<ArticleListBean> dataList = new ArrayList<>();

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_community_attention;
    }


    @Override
    public void getDataSuccess(TypeEnum type, List<ArticleListBean> articles) {
        if (type == TypeEnum.REFRESH) {
            dataList.clear();
            if (articles != null && articles.size() > 0) {
                mAdapter.setTopData(articles.get(0));
                mAdapter.addHeaderView(headView);
                articles.remove(0);
            }
        }
        getController().currentPagePlus();
        dataList.addAll(articles);
        mAdapter.notifyDataSetChanged();
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
        pageLoadingSuccess();
    }

    @Override
    public void getDataFailed(String errMsg) {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
        pageLoadingFail();
    }

    @Override
    public void getTopDataSuccess(ArticleListBean article) {
        if (article != null) {
            mAdapter.setTopData(article);
            mAdapter.addHeaderView(headView);
        }
    }

    @Override
    public void getTopFailed(String errMsg) {
        ToastUtils.showShort(errMsg);
    }

    @Override
    public void articleLikeSuccess() {

    }

    @Override
    public void articleLikeFailed(String errMsg) {
        ToastUtil.showMessage(getContext(), errMsg);
    }

    @Override
    public void initData(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        headView = (LinearLayout) getLayoutInflater().inflate(R.layout.community_trends_head_item, null);
        mAdapter = new WrapRecyclerAdapter(getContext(), new CommunityRecyclrViewAdapter(dataList, getContext()));
        communityAttentionRv = (WrapRecyclerView) getView().findViewById(R.id.community_attention_rv);
        refreshLayout = (RefreshLayout) getView().findViewById(R.id.refreshLayout);
        communityAttentionRv.setAdapter(mAdapter);
        communityAttentionRv.setLayoutManager(new LinearLayoutManager(getContext()));
        communityAttentionRv.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        refreshLayout.setOnLoadMoreListener(getController());
        refreshLayout.setOnRefreshListener(getController());
        setReloadListener(new LoadDataLayout.OnReloadListener() {
            @Override
            public void onReload(View v, int status) {
                pageLoading();
                getController().getAttentionArticle(TypeEnum.REFRESH, MyApplication.userInfo.getId(), getController().getCurrentPage(), Const.PAGE_SIZE);
            }
        });
    }

    @Override
    public void lazyLoad() {
        pageLoading();
        getController().getAttentionArticle(TypeEnum.REFRESH, MyApplication.userInfo.getId(), getController().getCurrentPage(), Const.PAGE_SIZE);
    }
}
