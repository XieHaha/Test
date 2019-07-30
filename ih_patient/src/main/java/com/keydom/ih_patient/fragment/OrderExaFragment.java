package com.keydom.ih_patient.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.order_examination.OrderExaminationActivity;
import com.keydom.ih_patient.adapter.OrderAdapter;
import com.keydom.ih_patient.bean.Event;
import com.keydom.ih_patient.bean.ExaminationInfo;
import com.keydom.ih_patient.constant.EventType;
import com.keydom.ih_patient.fragment.controller.OrderExaController;
import com.keydom.ih_patient.fragment.view.OrderExaView;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * 挂号订单页面
 */
public class OrderExaFragment extends BaseControllerFragment<OrderExaController> implements OrderExaView {
    private RecyclerView containRv;
    private SmartRefreshLayout containtRefresh;
    private OrderAdapter orderAdapter;
    /**
     * 挂号集合
     */
    private List<ExaminationInfo> dataList =new ArrayList<>();
    private RelativeLayout state_retry2;
    private TextView empty_text;
    @Override
    public int getLayoutRes() {
        return R.layout.fragment_order_examination_layout;
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        containtRefresh=view.findViewById(R.id.containt_refresh);
        containtRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                getController().QueryAllAppointment();
            }
        });
        containtRefresh.setEnableLoadMore(false);
        containRv=view.findViewById(R.id.containt_rv);
        orderAdapter=new OrderAdapter((OrderExaminationActivity) getActivity(),dataList);
        containRv.setAdapter(orderAdapter);
        state_retry2=view.findViewById(R.id.state_retry2);
        empty_text= view.findViewById(R.id.empty_text);
        EventBus.getDefault().register(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void uploadExamination(Event event){
        if(EventType.UPLOADEXAMINATION==event.getType()){
            getController().QueryAllAppointment();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    public void fillExaminationList(List<ExaminationInfo> dataList) {
        containtRefresh.finishRefresh();
        if(dataList!=null&&dataList.size()!=0){
            if(containtRefresh.getVisibility()==View.GONE){
                containtRefresh.setVisibility(View.VISIBLE);
                state_retry2.setVisibility(View.GONE);
            }
            this.dataList.clear();
            this.dataList.addAll(dataList);
            orderAdapter.notifyDataSetChanged();
        } else {
            containtRefresh.setVisibility(View.GONE);
            state_retry2.setVisibility(View.VISIBLE);
            empty_text.setText("暂无预约完成项目，请先前往预约");
        }

    }

    @Override
    public void fillExaminationListFailed(String errMsg) {
        containtRefresh.finishRefresh();
        if(containtRefresh.getVisibility()==View.VISIBLE){
            containtRefresh.setVisibility(View.GONE);
            state_retry2.setVisibility(View.VISIBLE);
            if(errMsg.equals("解析错误")){
                empty_text.setText("暂无预约完成项目，请先前往预约");
            }else {
                empty_text.setText("数据加载错误，点击重试");
                state_retry2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getController().QueryAllAppointment();
                    }
                });
            }

        }
    }

    @Override
    public void onDestroy() {
        Logger.e("onDestroy");
        EventBus.getDefault().unregister(this);
        super.onDestroy();


    }
}
