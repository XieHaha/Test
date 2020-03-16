package com.keydom.mianren.ih_patient.activity.order_physical_examination;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.common_document.CommonDocumentActivity;
import com.keydom.mianren.ih_patient.activity.order_physical_examination.controller.OrderPhysicalExaminationController;
import com.keydom.mianren.ih_patient.activity.order_physical_examination.view.OrderPhysicalExaminationView;
import com.keydom.mianren.ih_patient.adapter.PhysicalExaminationAdapter;
import com.keydom.mianren.ih_patient.bean.CommonDocumentBean;
import com.keydom.mianren.ih_patient.bean.PhysicalExaInfo;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * 体检订单页面
 */
public class OrderPhysicalExaminationActivity extends BaseControllerActivity<OrderPhysicalExaminationController> implements OrderPhysicalExaminationView {
    /**
     * 启动
     */
    public static void start(Context context){
        context.startActivity(new Intent(context,OrderPhysicalExaminationActivity.class));
    }
    private RecyclerView physical_exa_package_rv;
    private PhysicalExaminationAdapter physicalExaminationAdapter;
    private List<PhysicalExaInfo> dataList=new ArrayList<>();
    private TextView errTv;
    private LinearLayout exa_center_layout;
    private RelativeLayout exa_notice_layout,exa_process_layout;
    @Override
    public int getLayoutRes() {
        return R.layout.activity_order_physical_examination_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("预约体检");
        exa_center_layout=findViewById(R.id.exa_center_layout);
        exa_center_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonDocumentActivity.start(getContext(),CommonDocumentBean.CODE_4);
            }
        });
        exa_notice_layout=findViewById(R.id.exa_notice_layout);
        exa_notice_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonDocumentActivity.start(getContext(),CommonDocumentBean.CODE_5);
            }
        });
        exa_process_layout=findViewById(R.id.exa_process_layout);
        exa_process_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonDocumentActivity.start(getContext(),CommonDocumentBean.CODE_6);
            }
        });
        errTv=findViewById(R.id.err_tv);
        physical_exa_package_rv=this.findViewById(R.id.physical_exa_package_rv);
        physicalExaminationAdapter=new PhysicalExaminationAdapter(getContext(),dataList);
        physical_exa_package_rv.setAdapter(physicalExaminationAdapter);
        getController().queryPjysicalExaList();
        errTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getController().queryPjysicalExaList();
            }
        });
    }

    @Override
    public void fillPhysicalExaDataList(List<PhysicalExaInfo> dataList) {
        if(dataList.size()==0){
            if(errTv.getVisibility()==View.GONE)
                errTv.setVisibility(View.VISIBLE);
            errTv.setClickable(false);
            errTv.setText("该医院暂无体检套餐");
        }else {
            if(errTv.getVisibility()==View.VISIBLE)
                errTv.setVisibility(View.GONE);
            this.dataList.clear();
            this.dataList.addAll(dataList);
            physicalExaminationAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void getPhysicalExaDataFailed(String errMsg) {
        if(errTv.getVisibility()==View.GONE)
            errTv.setVisibility(View.VISIBLE);
        errTv.setClickable(true);
        errTv.setText("体检套餐获取失败，点击重试");
        ToastUtil.showMessage(getContext(),"接口异常"+errMsg);
    }
}
