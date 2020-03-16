package com.keydom.mianren.ih_doctor.activity.issue_information;

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
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.issue_information.controller.NotificationListController;
import com.keydom.mianren.ih_doctor.activity.issue_information.view.NotificationListView;
import com.keydom.mianren.ih_doctor.adapter.NotificationRecyclrViewAdapter;
import com.keydom.mianren.ih_doctor.bean.MessageEvent;
import com.keydom.mianren.ih_doctor.bean.NotificationBean;
import com.keydom.mianren.ih_doctor.constant.EventType;
import com.keydom.mianren.ih_doctor.constant.TypeEnum;
import com.keydom.mianren.ih_doctor.m_interface.SingleClick;
import com.keydom.mianren.ih_doctor.view.SwipeItemLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.activity.personal
 * @Author：song
 * @Date：18/11/14 上午10:37
 * 修改人：xusong
 * 修改时间：18/11/14 上午10:37
 */
public class NotificationListActivity extends BaseControllerActivity<NotificationListController> implements NotificationListView {

    private RecyclerView articleListRv;
    private RefreshLayout refreshLayout;
    private Dialog dialog;
    /**
     * 公告列表
     */
    private List<NotificationBean> mlist = new ArrayList<>();
    /**
     * 公告列表适配器
     */
    private NotificationRecyclrViewAdapter notificationRecyclrViewAdapter;

    /**
     * 启动通知公告列表页面
     *
     * @param context
     */
    public static void start(Context context) {
        Intent starter = new Intent(context, NotificationListActivity.class);
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
        articleListRv = (RecyclerView) this.findViewById(R.id.article_list_rv);
        refreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);
        notificationRecyclrViewAdapter = new NotificationRecyclrViewAdapter(this, mlist);
        articleListRv.setAdapter(notificationRecyclrViewAdapter);
        articleListRv.setLayoutManager(new LinearLayoutManager(this));
        articleListRv.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(getContext()));
        articleListRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        refreshLayout.setOnRefreshListener(getController());
        refreshLayout.setOnLoadMoreListener(getController());
        setTitle("公告栏");
        setRightTxt("发布新公告");
        setRightBtnListener(new IhTitleLayout.OnRightTextClickListener() {
            @SingleClick(1000)
            @Override
            public void OnRightTextClick(View v) {
                IssueNotificationActivity.start(NotificationListActivity.this);
            }
        });
        pageLoading();
        getController().getNotification(TypeEnum.REFRESH);
        setReloadListener(new LoadDataLayout.OnReloadListener() {
            @Override
            public void onReload(View v, int status) {
                pageLoading();
                getController().getNotification(TypeEnum.REFRESH);
            }
        });
    }

    @Override
    public void getNotificationSuccess(TypeEnum type, List<NotificationBean> list) {
        if (type == TypeEnum.REFRESH) {
            this.mlist.clear();
        }
        this.mlist.addAll(list);
        notificationRecyclrViewAdapter.notifyDataSetChanged();
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
        pageLoadingSuccess();
    }

    @Override
    public void getNotificationFailed(String errMsg) {
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
        pageLoadingFail();
    }

    @Override
    public void deleteNotificationSuccess(int position) {
        mlist.remove(position);
        notificationRecyclrViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void deleteNotificationFailed(String errMsg) {
        ToastUtil.showMessage(this, errMsg);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    /**
     * 弹出操作框，修改／删除
     *
     * @param pos
     * @param type 0 修改  1 删除
     */
    public void todoNotification(final int pos, int type) {
        if(type==0)
            IssueNotificationActivity.startWithUpdate(getContext(), mlist.get(pos).getId());
        else
            getController().deleteNotication(mlist.get(pos).getId(), pos);

       /* dialog = DialogCreator.createDelDialog(this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.delete_ll:
                        getController().deleteNotication(mlist.get(pos).getId(), pos);
                        dialog.hide();
                        break;
                    case R.id.update_ll:
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
        if (messageEvent.getType() == EventType.UPDATE_NOTIFATION) {
            getController().getNotification(TypeEnum.REFRESH);
        }
    }

}
