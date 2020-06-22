package com.keydom.mianren.ih_patient.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.nursing_order.ChargeBackOrderActivity;
import com.keydom.mianren.ih_patient.activity.nursing_order.SentListActivity;
import com.keydom.mianren.ih_patient.activity.nursing_order.WaitForAdmissionActivity;
import com.keydom.mianren.ih_patient.activity.order_evaluate.OrderEvaluateActivity;
import com.keydom.mianren.ih_patient.adapter.NursingOrderAdapter;
import com.keydom.mianren.ih_patient.bean.Event;
import com.keydom.mianren.ih_patient.bean.NursingOrderBean;
import com.keydom.mianren.ih_patient.bean.NursingOrderDetailBean;
import com.keydom.mianren.ih_patient.bean.OrderEvaluateBean;
import com.keydom.mianren.ih_patient.callback.GeneralCallback;
import com.keydom.mianren.ih_patient.callback.SingleClick;
import com.keydom.mianren.ih_patient.constant.EventType;
import com.keydom.mianren.ih_patient.constant.Type;
import com.keydom.mianren.ih_patient.constant.TypeEnum;
import com.keydom.mianren.ih_patient.fragment.controller.NursingOrderFragmentController;
import com.keydom.mianren.ih_patient.fragment.view.NursingOrderItemView;
import com.keydom.mianren.ih_patient.utils.SelectDialogUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * created date: 2018/12/18 on 14:39
 * des:护理订单页面
 * author: HJW HP
 */
public class NursingOrderFragment extends BaseControllerFragment<NursingOrderFragmentController> implements NursingOrderItemView {
    public static final String STATUS = "nursing_status";//全部
    public static final int WAIT_SERVICE = 0;//待服务
    public static final int SERVICING = 1;//服务中
    public static final int COMPLETE = 2;//完成

    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;

    private NursingOrderAdapter mAdapter;
    /**
     * 订单状态
     */
    private int mStatus;
    /**
     * 订单总价
     */
    BigDecimal total;

    /**
     * fragment实例
     *
     * @param status
     * @return
     */
    public static NursingOrderFragment newInstance(int status) {
        Bundle args = new Bundle();
        args.putInt(STATUS, status);
        NursingOrderFragment fragment = new NursingOrderFragment();
        fragment.setArguments(args);
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
        EventBus.getDefault().register(this);
        mAdapter = new NursingOrderAdapter(new ArrayList<>());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        assert getArguments() != null;
        mStatus = getArguments().getInt(STATUS);

        mRefreshLayout.setOnRefreshListener(refreshLayout -> switchState(mStatus,
                TypeEnum.REFRESH));
        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> switchState(mStatus,
                TypeEnum.LOAD_MORE));
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @SingleClick(1000)
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {


                NursingOrderBean bean = (NursingOrderBean) adapter.getData().get(position);
                switch (view.getId()) {
                    case R.id.num:
                        if (bean.getState() == -2) {
                            getController().goChangeOrder(bean.getId(), bean.getState());
                        }
                        break;
                    case R.id.group:
                        Intent i = new Intent();
                        if (bean.getState() == -2) {
                            return;
                        } else if (bean.getState() == NursingOrderDetailBean.STATE0 || bean.getState() == NursingOrderDetailBean.STATE5) {
                            i.putExtra(WaitForAdmissionActivity.ID, bean.getId());
                            i.putExtra(WaitForAdmissionActivity.STATE, bean.getState());
                            i.setClass(getContext(), WaitForAdmissionActivity.class);
                            ActivityUtils.startActivity(i);
                        } else {
                            i.putExtra(SentListActivity.ID, bean.getId());
                            i.putExtra(SentListActivity.STATE, bean.getState());
                            i.setClass(getContext(), SentListActivity.class);
                            ActivityUtils.startActivity(i);
                        }
                        break;
                    case R.id.go_pay:
                        getController().getDataList(bean.getId(), bean.getState());
                        break;
                    case R.id.assess:
                        OrderEvaluateActivity.start(getContext(),
                                OrderEvaluateBean.nursing_order_title,
                                Type.NURSING_ORDER_EVALUATE, bean);
                        break;
                    case R.id.charge_back:
                        Intent i1 = new Intent(getContext(), ChargeBackOrderActivity.class);
                        i1.putExtra(ChargeBackOrderActivity.ORDERNUM, bean.getOrderNumber());
                        i1.putExtra(ChargeBackOrderActivity.STATUS, bean.getState());
                        ActivityUtils.startActivity(i1);
                        break;
                    default:
                        break;
                }
            }
        });
        switchState(mStatus, TypeEnum.REFRESH);
    }

    /**
     * 支付
     *
     * @param type
     * @param bean
     * @param price
     */
    private void goPay(String type, NursingOrderDetailBean bean, BigDecimal price) {
        getController().goPay(type, bean, price);
    }

    /**
     * 根据状态更新列表
     *
     * @param state
     */
    private void switchState(int state, TypeEnum typeEnum) {
        getController().getNursingListData(state, typeEnum);
    }

    @Override
    public void getDataSuccess(List<NursingOrderBean> data, TypeEnum typeEnum) {
        mRefreshLayout.finishLoadMore();
        mRefreshLayout.finishRefresh();
        mAdapter.removeAllFooterView();
        if (data != null && data.size() != 0) {
            ImageView footer = new ImageView(getActivity());
            footer.setImageResource(R.mipmap.colorful_line);
            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
            footer.setLayoutParams(params);
            mAdapter.addFooterView(footer);


            pageLoadingSuccess();
            if (typeEnum == TypeEnum.REFRESH) {
                mAdapter.replaceData(data);
            } else {
                mAdapter.addData(data);
            }
            getController().currentPagePlus();
        }
    }

    @Override
    public void paySuccess() {
        switchState(mStatus, TypeEnum.REFRESH);
    }

    @Subscribe
    public void orderPaySuccess(Event event) {
        if (event.getType() == EventType.NURSING_PAY_SUCCESS || event.getType() == EventType.CREATE_NURSING_SUCCESS || event.getType() == EventType.CHANGE_NURSING_SUCCESS || event.getType() == EventType.Evaluted_success) {
            switchState(mStatus, TypeEnum.REFRESH);
        }
    }

    @Override
    public void getBasicData(NursingOrderDetailBean data) {
        if (data == null) {
            return;
        }
        total = new BigDecimal(0.00);
        if (data.getState() == NursingOrderDetailBean.STATE3 || data.getState() == NursingOrderDetailBean.STATE1 || data.getState() == NursingOrderDetailBean.STATE2 || data.getState() == NursingOrderDetailBean.STATE4 || data.getState() == NursingOrderDetailBean.Evaluted) {
            if (data.getSubOrders() != null && data.getSubOrders().size() != 0) {
                for (int i = 0; i < data.getSubOrders().size(); i++) {
                    if (data.getSubOrders().get(i) != null && data.getSubOrders().get(i).getPay() != 1 && data.getSubOrders().get(i).getFee() != null) {
                        total = total.add(data.getSubOrders().get(i).getFee());
                    }
                }
            }
            if (data.getEquipmentItem() != null) {
                for (int i = 0; i < data.getEquipmentItem().size(); i++) {
                    if (data.getEquipmentItem().get(i) != null && data.getEquipmentItem().get(i).getTotalMoney() != null && data.getEquipmentItem().get(i).getPay() != 1) {
                        total = total.add(data.getEquipmentItem().get(i).getTotalMoney());
                    }
                }
            }
        }
        if (data.getState() == NursingOrderDetailBean.STATE5) {
            total = data.getNursingServiceOrderDetailBaseDto().getReservationSet();
        }
        SelectDialogUtils.showPayDialog(getContext(), String.valueOf(total), "",
                new GeneralCallback.SelectPayMentListener() {
            @Override
            public void getSelectPayMent(String type) {
                int payType = 0;
                if (type.equals(Type.WECHATPAY)) {
                    payType = 1;
                }
                if (type.equals(Type.ALIPAY)) {
                    payType = 2;
                }
                goPay(String.valueOf(payType), data, total);
            }
        });
    }

    @Subscribe
    public void chargeBackSuccess(Event event) {
        if (event.getType() == EventType.CHARGEBACKSUCCESS) {
            switchState(mStatus, TypeEnum.REFRESH);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
