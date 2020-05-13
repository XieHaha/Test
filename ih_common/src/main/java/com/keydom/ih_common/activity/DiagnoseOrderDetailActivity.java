package com.keydom.ih_common.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ganxin.library.LoadDataLayout;
import com.keydom.ih_common.R;
import com.keydom.ih_common.activity.controller.DiagnoseOrderDetailController;
import com.keydom.ih_common.activity.view.DiagnoseOrderDetailView;
import com.keydom.ih_common.adapter.DiagnoseConditionImgAdapter;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.bean.DiagnoseOrderDetailBean;
import com.keydom.ih_common.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.activity.personal
 * @Description：描述信息
 * @Author：song
 * @Date：18/11/14 上午10:37
 * 修改人：xusong
 * 修改时间：18/11/14 上午10:37
 */
public class DiagnoseOrderDetailActivity extends BaseControllerActivity<DiagnoseOrderDetailController> implements DiagnoseOrderDetailView {


    private long orderId;
    private TextView userName, userAge, userPhone, orderTime, diseaseHistoryTv, questionDescTv;
    private RecyclerView conditionRv;
    private static String ORDER_ID = "order_id";
    private DiagnoseConditionImgAdapter diagnoseConditionImgAdapter;
    private List<String> list = new ArrayList<>();

    /**
     * @param context
     */
    public static void start(Context context, long orderId) {
        Intent starter = new Intent(context, DiagnoseOrderDetailActivity.class);
        starter.putExtra(ORDER_ID, orderId);
        ((Activity) context).startActivity(starter);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_online_diagnose_order_detail;
    }

    @Override
    public void initData(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        orderId = getIntent().getLongExtra(ORDER_ID, 0);
        setTitle("问诊单");
        initView();
        pageLoading();
        getController().getPatientInquisitionById();
        setReloadListener(new LoadDataLayout.OnReloadListener() {
            @Override
            public void onReload(View v, int status) {
                getController().getPatientInquisitionById();
            }
        });
    }


    private void initView() {
        userName = this.findViewById(R.id.user_name);
        userAge = this.findViewById(R.id.user_age);
        userPhone = this.findViewById(R.id.user_phone);
        orderTime = this.findViewById(R.id.order_time);
        //        descTv = this.findViewById(R.id.desc_tv);
        diseaseHistoryTv = this.findViewById(R.id.disease_history_tv);
        questionDescTv = this.findViewById(R.id.question_desc_tv);
        conditionRv = this.findViewById(R.id.condition_rv);
    }

    private void setInfo(DiagnoseOrderDetailBean bean) {
        diagnoseConditionImgAdapter = new DiagnoseConditionImgAdapter(this,
                CommonUtils.getImgList(bean.getConditionData()));
        //        LinearLayoutManager diagnoseInfoImgRvLm = new LinearLayoutManager(this);
        //        diagnoseInfoImgRvLm.setOrientation(LinearLayoutManager.HORIZONTAL);
        conditionRv.setAdapter(diagnoseConditionImgAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2,
                LinearLayoutManager.VERTICAL, false);
        conditionRv.setLayoutManager(layoutManager);
        userName.setText(bean.getName());
        userAge.setText(bean.getAge());
        userPhone.setText(bean.getPhoneNumber());
        orderTime.setText(bean.getApplyTime());
        //        descTv.setText(bean.getConditionDesc());
        diseaseHistoryTv.setText(bean.getPastMedicalHistory());
        questionDescTv.setText(bean.getConditionDesc());

    }

    @Override
    public long getOrderId() {
        return orderId;
    }

    @Override
    public void getDetailSuccess(DiagnoseOrderDetailBean bean) {
        setInfo(bean);
        pageLoadingSuccess();
    }

    @Override
    public void getDetailFailed(String errMsg) {
        pageLoadingFail();
    }
}
