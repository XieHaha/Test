package com.keydom.mianren.ih_doctor.activity.personal;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ganxin.library.LoadDataLayout;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.CostomToastUtils;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.IssueArticleActivity;
import com.keydom.mianren.ih_doctor.activity.personal.controller.ArticleListController;
import com.keydom.mianren.ih_doctor.activity.personal.view.ArticleListView;
import com.keydom.mianren.ih_doctor.adapter.ArticleListRecyclrViewAdapter;
import com.keydom.mianren.ih_doctor.bean.ArticleItemBean;
import com.keydom.mianren.ih_doctor.bean.MessageEvent;
import com.keydom.mianren.ih_doctor.constant.Const;
import com.keydom.mianren.ih_doctor.constant.EventType;
import com.keydom.mianren.ih_doctor.constant.TypeEnum;
import com.keydom.mianren.ih_doctor.view.SwipeItemLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.activity.personal
 * @Description：我的文章和我的收藏公用页面
 * @Author：song
 * @Date：18/11/14 上午10:37
 * 修改人：xusong
 * 修改时间：18/11/14 上午10:37
 */
public class ArticleListActivity extends BaseControllerActivity<ArticleListController> implements ArticleListView {

    private RecyclerView articleListRv;
    private RefreshLayout refreshLayout;
    private String doctorCode;
    private String doctorName;
    /**
     * 区分我的文章列表、我的收藏列表
     */
    private TypeEnum type;
    /**
     * 文章列表
     */
    private List<ArticleItemBean> mlist = new ArrayList<>();
    /**
     * 文章列表适配器
     */
    private ArticleListRecyclrViewAdapter articleListRecyclrViewAdapter;
    /**
     * 删除的文章处于文章列表的下标
     */
    private int currentPosition = Const.INT_DEFAULT;

    /**
     * type区分是我的文章还是我的收藏
     */
    public static void start(Context context, TypeEnum type,String doctorCode,String doctorName) {
        Intent starter = new Intent(context, ArticleListActivity.class);
        starter.putExtra(Const.TYPE, type);
        starter.putExtra("doctorCode", doctorCode);
        starter.putExtra("doctorName", doctorName);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_article_list_layout;
    }

    @Override
    public void initData(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        type = (TypeEnum) getIntent().getSerializableExtra(Const.TYPE);
        doctorCode=getIntent().getStringExtra("doctorCode");
        doctorName=getIntent().getStringExtra("doctorName");
        articleListRv = (RecyclerView) this.findViewById(R.id.article_list_rv);
        refreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);
        articleListRecyclrViewAdapter = new ArticleListRecyclrViewAdapter(mlist, this,type);
        articleListRv.setAdapter(articleListRecyclrViewAdapter);
        articleListRv.setLayoutManager(new LinearLayoutManager(this));
        articleListRv.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(getContext()));
        articleListRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        refreshLayout.setOnRefreshListener(getController());
        refreshLayout.setOnLoadMoreListener(getController());
        pageLoading();
        if (type == TypeEnum.MYARTICLE) {
            getController().getArticle(TypeEnum.REFRESH,doctorCode);
            setTitle("我的文章");
        } else if(type == TypeEnum.OHTERSARTICLE){
            getController().getArticle(TypeEnum.REFRESH,doctorCode);
            setTitle(doctorName+"的文章");
        }else {
            getController().getCollect(TypeEnum.REFRESH);
            setTitle("我的收藏");
        }
        setReloadListener(new LoadDataLayout.OnReloadListener() {
            @Override
            public void onReload(View v, int status) {
                pageLoading();
                if (type == TypeEnum.MYARTICLE) {
                    getController().getArticle(TypeEnum.REFRESH,doctorCode);
                    setTitle("我的文章");
                }else if(type == TypeEnum.OHTERSARTICLE){
                    getController().getArticle(TypeEnum.REFRESH,doctorCode);
                    setTitle(doctorName+"的文章");
                } else {
                    getController().getCollect(TypeEnum.REFRESH);
                    setTitle("我的收藏");
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void getArticleSuccess(TypeEnum type, List<ArticleItemBean> list) {
        pageLoadingSuccess();
        if (type == TypeEnum.REFRESH) {
            this.mlist.clear();
        }
        this.mlist.addAll(list);
        articleListRecyclrViewAdapter.notifyDataSetChanged();
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
    }

    @Override
    public void getArticleFailed(String errMsg) {
        pageLoadingFail();
    }

    @Override
    public void deleteArticleSuccess(String msg) {
        if (currentPosition >= 0) {
            mlist.remove(currentPosition);
            articleListRecyclrViewAdapter.notifyDataSetChanged();
            currentPosition = -1;
        }
    }

    @Override
    public void deleteArticleFailed(String errMsg) {
        ToastUtil.showMessage(this, errMsg);
    }

    @Override
    public TypeEnum getType() {
        return type;
    }

    @Override
    public String getDoctorCode() {
        return doctorCode;
    }

    @Override
    public void removeCollectSuccess() {
        CostomToastUtils.getInstance().ToastMessage(getContext(), "取消收藏成功");
        getController().getCollect(TypeEnum.REFRESH);
    }

    @Override
    public void removeCollectFailed(String msg) {
        ToastUtil.showMessage(getContext(),"取消收藏失败："+msg);
    }

    Dialog dialog;

    /**
     * 显示删除／修改按钮
     *
     * @param pos      操作的数据位置
     * @param type 0 修改  1 删除
     */
    public void todoArticle(final int pos, int type) {

        if(type==0)
            IssueArticleActivity.startForUpdate(ArticleListActivity.this, mlist.get(pos).getArticleId());
        else{
            getController().delete(mlist.get(pos).getArticleId());
            currentPosition = pos;
        }
       /*
        dialog = DialogCreator.createDelDialog(this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.delete_ll:
                        getController().delete(mlist.get(pos).getArticleId());
                        currentPosition = pos;
                        dialog.hide();
                        break;
                    case R.id.update_ll:
                        IssueArticleActivity.startForUpdate(ArticleListActivity.this, mlist.get(pos).getArticleId());
                        dialog.hide();
                        break;
                    default:
                }
            }
        }, isUpdate);
        dialog.show();*/
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        if (messageEvent.getType() == EventType.UPDATE_ARTICLE) {
            getController().getArticle(TypeEnum.REFRESH,doctorCode);
        }
    }
}
