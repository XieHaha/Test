package com.keydom.mianren.ih_patient.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.inspection_report.InspectionReportActivity;
import com.keydom.mianren.ih_patient.adapter.InspectionReportAdapter;
import com.keydom.mianren.ih_patient.bean.InspectionRecordBean;
import com.keydom.mianren.ih_patient.callback.GeneralCallback;
import com.keydom.mianren.ih_patient.constant.Const;
import com.keydom.mianren.ih_patient.constant.Type;
import com.keydom.mianren.ih_patient.fragment.controller.InspectionReportFmController;
import com.keydom.mianren.ih_patient.fragment.view.InspectionReportFmView;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 检查检验页面
 */
public class InspectionReportFragment extends BaseControllerFragment<InspectionReportFmController> implements InspectionReportFmView, GeneralCallback.ExaReportActivityListener {
    private SmartRefreshLayout refreshLayout;
    private RecyclerView containRv;
    private List<InspectionRecordBean> recordBeans = new ArrayList<>();
    private InspectionReportAdapter inspectionReportAdapter;
    /**
     * type 类型 Type.INSPECTIONTYPE 检验  Type.BODYCHECKTYPE 检查  selectedCardNum选中的就诊卡号
     */
    private int type;
    private String selectedCardNum;
    private RelativeLayout emptyLayout;
    private TextView emptyTv;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_order_examination_layout;
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        type = bundle.getInt(Const.TYPE, 1);

        refreshLayout = view.findViewById(R.id.containt_refresh);
        containRv = view.findViewById(R.id.containt_rv);
        inspectionReportAdapter = new InspectionReportAdapter(type,recordBeans);
        containRv.setAdapter(inspectionReportAdapter);
        refreshLayout.setOnRefreshListener(refreshLayout -> getController().getInspectionReportList());

        emptyLayout = view.findViewById(R.id.state_retry2);
        emptyTv = view.findViewById(R.id.empty_text);
        emptyLayout.setOnClickListener(view1 -> getController().getInspectionReportList());
    }

    @Override
    public Map<String, Object> getParams() {
        Map<String, Object> params = new HashMap<>();
        if (type == Type.BODYCHECKTYPE) {
            params.put("elcCardNumber", "00721984");
        } else {
            params.put("elcCardNumber", "00056999");
        }
        //        params.put("elcCardNumber", selectedCardNum);
        //        params.put("endDate", "");
        params.put("reportType", type);
        //        params.put("startDate", "");
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

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        InspectionReportActivity inspectionReportActivity = (InspectionReportActivity) activity;
        inspectionReportActivity.registerListener(this);
    }

    @Override
    public void refreshSelectedCard(String cardNum) {
        selectedCardNum = cardNum;
        getController().getInspectionReportList();
    }
}
