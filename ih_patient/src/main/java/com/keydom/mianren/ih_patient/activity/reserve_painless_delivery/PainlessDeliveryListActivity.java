package com.keydom.mianren.ih_patient.activity.reserve_painless_delivery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_patient.App;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.reserve_painless_delivery.controller.PainlessDeliveryListController;
import com.keydom.mianren.ih_patient.activity.reserve_painless_delivery.view.PainlessDeliveryListView;
import com.keydom.mianren.ih_patient.adapter.PainlessDeliveryAdapter;
import com.keydom.mianren.ih_patient.bean.Event;
import com.keydom.mianren.ih_patient.bean.ManagerUserBean;
import com.keydom.mianren.ih_patient.bean.PainlessDeliveryBean;
import com.keydom.mianren.ih_patient.constant.EventType;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author 顿顿
 * @date 20/2/25 13:52
 * @des 无痛分娩预约列表
 */
public class PainlessDeliveryListActivity extends BaseControllerActivity<PainlessDeliveryListController> implements PainlessDeliveryListView {
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private PainlessDeliveryAdapter adapter;

    private ManagerUserBean curUserBean;

    private String idCard;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_painless_delivery_list;
    }


    @Override
    protected void onResume() {
        super.onResume();
        smartRefreshLayout.autoRefresh();
    }

    /**
     * 启动
     */
    public static void start(Context context) {
        context.startActivity(new Intent(context, PainlessDeliveryListActivity.class));
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        idCard = App.userInfo.getIdCard();
        setTitle(getString(R.string.txt_painless_delivery_reserve));

        adapter = new PainlessDeliveryAdapter(new ArrayList<>());
        adapter.setOnItemChildClickListener(getController());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);

        smartRefreshLayout.setOnRefreshListener(refreshLayout -> getController().getPainlessDeliveryList(idCard));

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onVisitPeopleSelect(Event event) {
        if (event.getType() == EventType.SENDPATIENTINFO) {
            curUserBean = (ManagerUserBean) event.getData();
            setRightTxt(curUserBean.getName());
            idCard = curUserBean.getCardId();
            getController().getPainlessDeliveryList(idCard);
        }
    }

    @Override
    public void requestSuccess(List<PainlessDeliveryBean> list) {
        smartRefreshLayout.finishRefresh();
        adapter.replaceData(list);
    }

    @Override
    public void requestFailed(String msg) {
        smartRefreshLayout.finishRefresh();
        ToastUtil.showMessage(this, msg);
    }

    @Override
    public void cancelSuccess(int position) {
        ToastUtil.showMessage(this, "操作成功");
        getController().getPainlessDeliveryList(idCard);
        //        adapter.getData().remove(position);
        //        adapter.notifyItemRemoved(position);
        //        adapter.notifyItemRangeChanged(position, adapter.getItemCount() - position);
    }

    @Override
    public void cancelFailed(String msg) {
        ToastUtil.showMessage(this, msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
