package com.keydom.mianren.ih_patient.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.adapter.InspectionReportAdapter;
import com.keydom.mianren.ih_patient.bean.InspectionRecordBean;
import com.keydom.mianren.ih_patient.bean.MedicalCardInfo;
import com.keydom.mianren.ih_patient.constant.Const;
import com.keydom.mianren.ih_patient.constant.Type;
import com.keydom.mianren.ih_patient.fragment.controller.InspectionReportFmController;
import com.keydom.mianren.ih_patient.fragment.view.InspectionReportFmView;
import com.keydom.mianren.ih_patient.utils.DateUtils;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 检查检验页面
 *
 * @author 顿顿
 */
public class InspectionReportFragment extends BaseControllerFragment<InspectionReportFmController> implements InspectionReportFmView {
    @BindView(R.id.medical_record_name_tv)
    TextView medicalRecordNameTv;
    @BindView(R.id.medical_record_card_number_tv)
    TextView medicalRecordCardNumberTv;
    @BindView(R.id.containt_rv)
    RecyclerView containRv;
    @BindView(R.id.containt_refresh)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.progress_wheel)
    ImageView progressWheel;
    @BindView(R.id.empty_text)
    TextView emptyTv;
    @BindView(R.id.medical_record_start_date_tv)
    TextView startDateTv;
    @BindView(R.id.medical_record_end_date_tv)
    TextView endDateTv;
    @BindView(R.id.state_retry2)
    RelativeLayout emptyLayout;
    @BindView(R.id.medical_record_patient_layout)
    RelativeLayout patientLayout;
    @BindView(R.id.medical_record_date_layout)
    LinearLayout dateLayout;
    @BindView(R.id.medical_record_start_date_layout)
    LinearLayout startDateLayout;
    @BindView(R.id.medical_record_end_date_layout)
    LinearLayout endDateLayout;
    private List<InspectionRecordBean> recordBeans = new ArrayList<>();
    private InspectionReportAdapter inspectionReportAdapter;
    /**
     * type 类型 Type.INSPECTIONTYPE 检验  Type.BODYCHECKTYPE 检查  selectedCardNum选中的就诊卡号
     */
    private int type;

    private String startDate, endDate;

    private MedicalCardInfo medicalCardInfo;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_order_examination_layout;
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        type = bundle.getInt(Const.TYPE, 1);
        medicalCardInfo = (MedicalCardInfo) bundle.getSerializable(Const.DATA);

        initDefaultDate();

        startDateTv.setText(startDate);
        endDateTv.setText(endDate);
        medicalRecordNameTv.setText(medicalCardInfo.getName());
        medicalRecordCardNumberTv.setText(medicalCardInfo.getEleCardNumber());
        startDateLayout.setOnClickListener(getController());
        endDateLayout.setOnClickListener(getController());

        inspectionReportAdapter = new InspectionReportAdapter(type, recordBeans);
        containRv.setAdapter(inspectionReportAdapter);
        refreshLayout.setOnRefreshListener(refreshLayout -> getController().getInspectionReportList());
        emptyLayout.setOnClickListener(view1 -> getController().getInspectionReportList());

        getController().getInspectionReportList();
    }

    /**
     * 初始化时间范围
     */
    private void initDefaultDate() {
        Date date = new Date();
        startDate = DateUtils.getInterValDate(date, -1);
        endDate = DateUtils.dateToString(date);
    }

    @Override
    public void setStartDate(Date date) {
        startDate = DateUtils.dateToString(date);
        startDateTv.setText(startDate);
        getController().getInspectionReportList();
    }

    @Override
    public void setEndDate(Date date) {
        endDate = DateUtils.dateToString(date);
        endDateTv.setText(endDate);
        getController().getInspectionReportList();
    }

    @Override
    public Map<String, Object> getParams() {
        Map<String, Object> params = new HashMap<>();
        //        params.put("eleCardNumber", medicalCardInfo.getEleCardNumber());
        params.put("eleCardNumber", "00900466");
        params.put("endDate", endDate);
        params.put("startDate", startDate);
        params.put("reportType", type);
        return params;
    }

    @Override
    public void getDataListSuccess(List<InspectionRecordBean> dataList) {
        pageLoadingSuccess();
        refreshLayout.finishRefresh();
        if (dataList != null && dataList.size() != 0) {
            if (refreshLayout.getVisibility() == View.GONE) {
                refreshLayout.setVisibility(View.VISIBLE);
                emptyLayout.setVisibility(View.GONE);
            }
            this.recordBeans.clear();
            this.recordBeans.addAll(dataList);
            inspectionReportAdapter.notifyDataSetChanged();
            getController().currentPagePlus();
        } else {
            refreshLayout.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.VISIBLE);
            emptyLayout.setClickable(false);
            if (Type.INSPECTIONTYPE == type) {
                emptyTv.setText("暂无检验报告");
            } else {
                emptyTv.setText("暂无检查报告");
            }
        }
    }

    @Override
    public void getDataListFailed(int errCode, String errMsg) {
        Logger.e("errCode=" + errMsg + "    errMsg=" + errMsg);
        refreshLayout.finishRefresh();
        refreshLayout.setVisibility(View.GONE);
        emptyLayout.setVisibility(View.VISIBLE);
        if (errCode == 300) {
            if (Type.INSPECTIONTYPE == type) {
                emptyTv.setText(errMsg);
            } else {
                emptyTv.setText(errMsg);
            }
            emptyLayout.setClickable(false);
        } else {
            emptyTv.setText("数据加载失败，点击重试");
            ToastUtil.showMessage(getContext(), "列表加载失败：" + errMsg);
            emptyLayout.setClickable(true);
        }
    }
}
