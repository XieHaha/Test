package com.keydom.mianren.ih_patient.activity.prescription_check;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.prescription_check.controller.PrescriptionController;
import com.keydom.mianren.ih_patient.activity.prescription_check.view.PrescriptionView;
import com.keydom.mianren.ih_patient.adapter.MedicalRecordPopupWindowAdapter;
import com.keydom.mianren.ih_patient.adapter.PrescriptionListAdapter;
import com.keydom.mianren.ih_patient.bean.MedicalCardInfo;
import com.keydom.mianren.ih_patient.constant.TypeEnum;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * PrescriptionListActivity
 * created date: 2019/1/17 on 20:02
 * des:处方列表页面
 */
public class PrescriptionListActivity extends BaseControllerActivity<PrescriptionController> implements PrescriptionView {

    private TextView choosePatientTv;
    private LinearLayout backLayout;
    /**
     * 就诊卡选择框
     */
    private PopupWindow cardpopupWindow;
    /**
     * 就诊卡集合
     */
    private List<MedicalCardInfo> cardList = new ArrayList<>();
    MedicalRecordPopupWindowAdapter exaReportCardPopupWindowAdapter = new MedicalRecordPopupWindowAdapter(cardList);
    private RecyclerView mRecyclerView;
    private RefreshLayout refreshLayout;
    private PrescriptionListAdapter mAdapter;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_prescription_list;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        choosePatientTv = this.findViewById(R.id.choose_patient_tv);
        choosePatientTv.setOnClickListener(getController());
        backLayout = this.findViewById(R.id.back_layout);
        backLayout.setOnClickListener(view -> finish());
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new PrescriptionListAdapter(new ArrayList<>());
        mRecyclerView.setAdapter(mAdapter);

        refreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(getController());
        refreshLayout.setOnLoadMoreListener(getController());
        getController().fillData();
    }

    @Override
    public void showCardPopupWindow() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.general_popupwindow_layout, null);
        RecyclerView recyclerView = view.findViewById(R.id.popup_rv);
        recyclerView.setAdapter(exaReportCardPopupWindowAdapter);
        exaReportCardPopupWindowAdapter.setOnItemClickListener((adapter, view1, position) -> {
            MedicalCardInfo cardInfo = (MedicalCardInfo) adapter.getData().get(position);
            choosePatientTv.setText(cardInfo.getName());
            getController().getPrescriptionList(cardInfo.getEleCardNumber(),TypeEnum.REFRESH);
            choosePatientTv.setText(cardInfo.getName());
            cardpopupWindow.dismiss();
        });
        cardpopupWindow = new PopupWindow(getContext(), null, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        cardpopupWindow.setContentView(view);
        cardpopupWindow.setFocusable(true);
        cardpopupWindow.setWidth(choosePatientTv.getWidth());
        cardpopupWindow.showAsDropDown(choosePatientTv);
    }

    @Override
    public void fillDataList(List<MedicalCardInfo> dataList) {
        exaReportCardPopupWindowAdapter.setNewData(dataList);
        if (dataList != null && dataList.size() > 0) {
            getController().getPrescriptionList(dataList.get(0).getEleCardNumber(),TypeEnum.REFRESH);
            choosePatientTv.setText(dataList.get(0).getName());
        }
    }

    @Override
    public void listDataCallBack(List<MultiItemEntity> data, TypeEnum typeEnum) {
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
        pageLoadingSuccess();
        if (typeEnum == TypeEnum.REFRESH) {
            mAdapter.replaceData(data);
        }else{
            mAdapter.addData(data);
        }
        mAdapter.notifyDataSetChanged();
    }
}
