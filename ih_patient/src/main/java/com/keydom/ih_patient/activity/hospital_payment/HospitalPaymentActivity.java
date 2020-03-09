package com.keydom.ih_patient.activity.hospital_payment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ganxin.library.LoadDataLayout;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.hospital_payment.controller.HospitalPaymentController;
import com.keydom.ih_patient.activity.hospital_payment.view.HospitalPaymentView;
import com.keydom.ih_patient.adapter.HospitalPaymentAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @date 20/2/25 17:01
 * @des 住院预缴金
 */
public class HospitalPaymentActivity extends BaseControllerActivity<HospitalPaymentController>
        implements HospitalPaymentView, View.OnClickListener {
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private TextView tvName, tvTime, tvRegisterPhone, tvWard, tvBedNum, tvRechangeAmount, tvBalance;
    private HospitalPaymentAdapter adapter;

    private ArrayList<String> data;
    private View headerView;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_hospital_payment;
    }

    /**
     * 启动
     */
    public static void start(Context context) {
        context.startActivity(new Intent(context, HospitalPaymentActivity.class));
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("住院预缴金");
        swipeRefreshLayout.setOnRefreshListener(refreshLayout -> getController().getHospitalPayment());
        //模拟数据
        data = new ArrayList<>();
        data.add("");
        data.add("");
        data.add("");
        data.add("");
        adapter = new HospitalPaymentAdapter(R.layout.item_hospital_payment, data);
        headerView = getLayoutInflater().inflate(R.layout.header_hospital_payment, null);
        bindHeaderView();
        adapter.addHeaderView(headerView);
        recyclerView.setAdapter(adapter);

        bindHeaderData();

        pageLoading();
        getController().getHospitalPayment();
        setReloadListener(new LoadDataLayout.OnReloadListener() {
            @Override
            public void onReload(View v, int status) {
                getController().getHospitalPayment();
            }
        });
    }

    /**
     * 头部 view
     */
    private void bindHeaderView() {
        tvName = headerView.findViewById(R.id.tv_name);
        tvTime = headerView.findViewById(R.id.tv_go_hospital_time);
        tvRegisterPhone = headerView.findViewById(R.id.tv_register_phone);
        tvWard = headerView.findViewById(R.id.tv_ward);
        tvBedNum = headerView.findViewById(R.id.tv_bed_num);
        tvRechangeAmount = headerView.findViewById(R.id.tv_recharge_amount);
        tvBalance = headerView.findViewById(R.id.tv_balance);
    }

    /**
     * 头部数据绑定
     */
    private void bindHeaderData() {
        //测试数据
        tvName.setText("测试");
        tvTime.setText("2020年2月26日");
        tvRegisterPhone.setText("111122333");
        tvWard.setText("外科病区");
        tvBedNum.setText("32");
        tvRechangeAmount.setText("1000元");
        tvBalance.setText("100元");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }
    }

    @Override
    public void fillHospitalPaymentData(List<String> data) {
        pageLoadingSuccess();
    }
}
