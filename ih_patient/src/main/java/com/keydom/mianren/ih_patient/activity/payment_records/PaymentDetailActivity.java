package com.keydom.mianren.ih_patient.activity.payment_records;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.payment_records.controller.PaymentDetailController;
import com.keydom.mianren.ih_patient.activity.payment_records.view.PaymentDetailView;
import com.keydom.mianren.ih_patient.adapter.PayDetailAdapter;
import com.keydom.mianren.ih_patient.bean.PayRecordDetailBean;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;


/**
 * created date: 2019/1/15 on 15:29
 * des:缴费记录详情
 */
public class PaymentDetailActivity extends BaseControllerActivity<PaymentDetailController> implements PaymentDetailView {
    public static final String DOCUMENT_NO = "document_no";

    /**
     * 订单号
     */
    private String mDocumentNo;
    private TextView mName;
    private TextView mCard;
    private TextView mHospital;
    private TextView mInfo;
    private TextView mTotalMoney;
    private PayDetailAdapter mPayDetailAdapter;
    private RecyclerView mRecyclerView;


    @Override
    public int getLayoutRes() {
        return R.layout.acitvity_payment_detail_layout;
    }

    /**
     * 获取控件
     */
    private void getView() {
        mName = findViewById(R.id.name_tv);
        mCard = findViewById(R.id.card_num_tv);
        mHospital = findViewById(R.id.hospital_name_tv);
        mInfo = findViewById(R.id.hospitalization_guidance_tv);
        mTotalMoney = findViewById(R.id.all_price_tv);
        mRecyclerView = findViewById(R.id.project_detail_rv);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        getView();
        setTitle("缴费详情");
        mDocumentNo = getIntent().getStringExtra(DOCUMENT_NO);
        mPayDetailAdapter = new PayDetailAdapter(new ArrayList<>());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mPayDetailAdapter);
        getController().getConsultationPayInfo(mDocumentNo);
    }

    @Override
    public void getDetailCallBack(PayRecordDetailBean detailBean, List<MultiItemEntity> list) {
        if (detailBean != null) {
            mName.setText(detailBean.getName());
            mCard.setText(detailBean.getPatientNumber());
            mHospital.setText(detailBean.getHospitalName());
            mInfo.setText(detailBean.getGuideInfo());
            mTotalMoney.setText("¥" + detailBean.getSumFee());
        }
        mPayDetailAdapter.setNewData(list);
    }


}
