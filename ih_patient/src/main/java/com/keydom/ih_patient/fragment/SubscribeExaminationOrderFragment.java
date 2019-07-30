package com.keydom.ih_patient.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.order_evaluate.OrderEvaluateActivity;
import com.keydom.ih_patient.adapter.SubscribeExaminationAdapter;
import com.keydom.ih_patient.bean.Event;
import com.keydom.ih_patient.bean.OrderEvaluateBean;
import com.keydom.ih_patient.bean.SubscribeExaminationBean;
import com.keydom.ih_patient.callback.GeneralCallback;
import com.keydom.ih_patient.constant.EventType;
import com.keydom.ih_patient.constant.Type;
import com.keydom.ih_patient.fragment.controller.SubscribeExaminationOrderController;
import com.keydom.ih_patient.fragment.view.SubscribeExaminationOrderView;
import com.keydom.ih_patient.utils.SelectDialogUtils;
import com.keydom.ih_patient.utils.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * created date: 2018/12/12 on 17:20
 * des:体检预约页面
 */
public class SubscribeExaminationOrderFragment extends BaseControllerFragment<SubscribeExaminationOrderController> implements SubscribeExaminationOrderView, SubscribeExaminationAdapter.onItemBtnClickListener {

    public static final String STATUS = "subscribe_status";//全部

    private SubscribeExaminationAdapter mAdapter;

    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;

    /**
     * 订单状态
     */
    private int mStatus;

    /**
     * fragment实例化
     */
    public static SubscribeExaminationOrderFragment newInstance(int status) {
        Bundle args = new Bundle();
        args.putInt(STATUS, status);
        SubscribeExaminationOrderFragment fragment = new SubscribeExaminationOrderFragment();
        fragment.setArguments(args);
        EventBus.getDefault().register(fragment);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_subscribe_examination_order;
    }

    @Override
    public void getView(View view) {
        mRefreshLayout = view.findViewById(R.id.examination_refresh);
        mRecyclerView = view.findViewById(R.id.examination_rv);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ArrayList<SubscribeExaminationBean> subscribeExaminationBeans = new ArrayList<>();
        mAdapter = new SubscribeExaminationAdapter(subscribeExaminationBeans,this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        assert getArguments() != null;
        mStatus = getArguments().getInt(STATUS);
        switchStatus();
        mRefreshLayout.setOnRefreshListener(refreshLayout -> switchStatus());
    }

    /**
     * 根据状态获取列表数据
     */
    private void switchStatus() {
        getController().getExaminationData(mStatus);
    }

    @Override
    public void getDataSuccess(final List<SubscribeExaminationBean> data) {
        if (mRefreshLayout.isRefreshing()){
            mRefreshLayout.finishRefresh();
        }
        mAdapter.removeAllFooterView();
        mAdapter.setNewData(data);
        if (data!=null && data.size()!=0){
            ImageView footer = new ImageView(getActivity());
            footer.setImageResource(R.mipmap.colorful_line);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            footer.setLayoutParams(params);
            mAdapter.addFooterView(footer);
        }
    }

    @Subscribe
    public void refreshData(Event event){
        if (event.getType() == EventType.UPDATESUBSCRIBEEXAM){
            int state = (int) event.getData();
            if (state == mStatus){
                switchStatus();
            }
        }
    }

    @Override
    public void getDataFailed(String msg) {
        ToastUtil.shortToast(getContext(), msg);
    }

    @Override
    public void paySuccess() {
        getActivity().finish();
    }

    /**
     * 取消订单
     */
    private void cancelOrder(SubscribeExaminationBean item){
        new GeneralDialog(getContext(), "确认取消订单吗？", () -> getController().cancelExaminationOrder(item)).setTitle("提示").setPositiveButton("确认").show();
    }

    /**
     * 退单
     */
    private void chargeBackOrder(SubscribeExaminationBean item){
        new GeneralDialog(getContext(), "确认退单吗？", () -> getController().chargeBackExaminationOrder(item)).setTitle("提示").setPositiveButton("确认").show();
    }

    //待付款1
    //待体检2
    //已完成3
    //已取消4
    //体检中5
    //已退单6
    @Override
    public void onLeftClick(SubscribeExaminationBean item) {
        switch (item.getItemState()) {
            case SubscribeExaminationAdapter.WAIT_PAY:
                cancelOrder(item);
                break;
            case SubscribeExaminationAdapter.WAIT_EXAM:
                chargeBackOrder(item);
                break;
            case SubscribeExaminationAdapter.COMPLETE:
                OrderEvaluateActivity.start(getContext(),OrderEvaluateBean.subscribe_exam_order_title,Type.SUBSCRIBE_EXAM_ORDER_EVALUATE,item);
                break;
//            case SubscribeExaminationAdapter.CANCEL:
//                OrderEvaluateActivity.start(getContext(),OrderEvaluateBean.subscribe_exam_order_title,Type.SUBSCRIBE_EXAM_ORDER_EVALUATE,item);
//                break;
        }
    }

    @Override
    public void onDeleteClick(SubscribeExaminationBean item) {
        new GeneralDialog(getContext(), "确认删除订单吗？", () -> getController().deleteExaminationOrder(item)).setTitle("提示").setPositiveButton("确认").show();
    }

    /**
     * 展示支付弹框
     */
    private void showPayDialog(SubscribeExaminationBean data){
        SelectDialogUtils.showPayDialog(getContext(), data.getFee(), "", new GeneralCallback.SelectPayMentListener() {
            @Override
            public void getSelectPayMent(String type) {
                int payType = 0;
                if (type.equals(Type.WECHATPAY)){
                    payType = 1;
                }
                if (type.equals(Type.ALIPAY)){
                    payType = 2;
                }
                getController().goPayExaminationOrder(String.valueOf(payType),data);
            }
        });
    }

    @Override
    public void onRightClick(SubscribeExaminationBean item) {
        switch (item.getItemState()) {
            case SubscribeExaminationAdapter.WAIT_PAY:
                showPayDialog(item);
                break;
            case SubscribeExaminationAdapter.WAIT_EXAM:
            case SubscribeExaminationAdapter.COMPLETE:
            case SubscribeExaminationAdapter.CHARGE_BACK:
            case SubscribeExaminationAdapter.CANCEL:
            case SubscribeExaminationAdapter.EXAMINATING:
                getController().goBuyExaminationOrder(item.getHealthCheckCombId());
                break;
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
