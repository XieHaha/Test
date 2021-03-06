package com.keydom.mianren.ih_patient.activity.hospital_payment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.hospital_payment.controller.HospitalCheckListController;
import com.keydom.mianren.ih_patient.activity.hospital_payment.view.HospitalCheckListView;
import com.keydom.mianren.ih_patient.adapter.HospitalCheckAdapter;
import com.keydom.mianren.ih_patient.bean.HospitalCheckBean;
import com.keydom.mianren.ih_patient.bean.HospitalCountBean;
import com.keydom.mianren.ih_patient.bean.MedicalCardInfo;
import com.keydom.mianren.ih_patient.constant.Const;
import com.keydom.mianren.ih_patient.utils.DateUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

/**
 * @author 顿顿
 * @date 20/2/25 17:01
 * @des 住院清单
 */
public class HospitalCheckListActivity extends BaseControllerActivity<HospitalCheckListController>
        implements HospitalCheckListView {
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.hospital_last_tv)
    TextView hospitalLastTv;
    @BindView(R.id.hospital_date_tv)
    TextView hospitalDateTv;
    @BindView(R.id.hospital_next_tv)
    TextView hospitalNextTv;
    @BindView(R.id.empty_layout)
    RelativeLayout emptyLayout;

    private HospitalCheckAdapter adapter;

    private List<HospitalCheckBean> data = new ArrayList<>();

    private Date curDate;
    private String startDateString, endDateString;

    /**
     * 就诊卡
     */
    private MedicalCardInfo medicalCardInfo;
    /**
     * 住院信息
     */
    private HospitalCountBean hospitalCountBean;

    /**
     * 住院号
     */
    private String inHospitalNo;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_hospital_check_list;
    }

    /**
     * 启动
     */
    public static void start(Context context, MedicalCardInfo medicalCardInfo,
                             String inHospitalNo) {
        Intent intent = new Intent(context, HospitalCheckListActivity.class);
        intent.putExtra(Const.DATA, medicalCardInfo);
        intent.putExtra(Const.RECORD_ID, inHospitalNo);
        context.startActivity(intent);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("住院清单");
        medicalCardInfo = (MedicalCardInfo) getIntent().getSerializableExtra(Const.DATA);
        inHospitalNo = getIntent().getStringExtra(Const.RECORD_ID);
        curDate = new Date();
        swipeRefreshLayout.setOnRefreshListener(refreshLayout -> getController().getHospitalCostType());
        adapter = new HospitalCheckAdapter(data);
        recyclerView.setAdapter(adapter);

        hospitalLastTv.setOnClickListener(getController());
        hospitalNextTv.setOnClickListener(getController());
        hospitalDateTv.setOnClickListener(getController());

        initDefaultDate();
    }

    /**
     * 初始化时间范围
     */
    private void initDefaultDate() {
        endDateString = DateUtils.dateToString(curDate);
        //默认只查一天的
        startDateString = endDateString;
        hospitalDateTv.setText(endDateString);
        getController().getHospitalCostType();
    }

    @Override
    public String getInHospitalNo() {
        return inHospitalNo;
    }

    @Override
    public Date getCurDate() {
        return curDate;
    }

    @Override
    public void setSelectDate(Date date) {
        curDate = date;
        initDefaultDate();
    }

    @Override
    public String getEndDateString() {
        return endDateString;
    }

    @Override
    public String getStartDateString() {
        return startDateString;
    }

    @Override
    public MedicalCardInfo getMedicalCardInfo() {
        return medicalCardInfo;
    }

    @Override
    public void fillHospitalPaymentData(List<HospitalCheckBean> data) {
        swipeRefreshLayout.finishRefresh();
        if (data == null || data.size() == 0) {
            swipeRefreshLayout.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.VISIBLE);
        } else {
            swipeRefreshLayout.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.GONE);
            adapter.setNewData(data);
        }
    }

    @Override
    public void setHospitalCountBean(HospitalCountBean hospitalCountBean) {
        this.hospitalCountBean = hospitalCountBean;
    }
}
