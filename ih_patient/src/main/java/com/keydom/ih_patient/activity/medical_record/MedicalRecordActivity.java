package com.keydom.ih_patient.activity.medical_record;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.medical_record.controller.MedicalRecordController;
import com.keydom.ih_patient.activity.medical_record.view.MedicalRecordView;
import com.keydom.ih_patient.adapter.MedicalRecordAdapter;
import com.keydom.ih_patient.adapter.MedicalRecordPopupWindowAdapter;
import com.keydom.ih_patient.bean.MedicalCardInfo;
import com.keydom.ih_patient.bean.MedicalRecordBean;
import com.keydom.ih_patient.constant.TypeEnum;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * created date: 2019/1/4 on 13:00
 * des:门诊记录页面
 */
public class MedicalRecordActivity  extends BaseControllerActivity<MedicalRecordController> implements MedicalRecordView {
    private TextView choosePatientTv;
    private LinearLayout backLayout;
    private PopupWindow cardpopupWindow;
    /**
     * 就诊卡集合
     */
    private List<MedicalCardInfo> cardList=new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RefreshLayout refreshLayout;

    MedicalRecordPopupWindowAdapter exaReportCardPopupWindowAdapter = new MedicalRecordPopupWindowAdapter(cardList);
    private MedicalRecordAdapter mAdapter;
    @Override
    public int getLayoutRes() {
        return R.layout.activity_medical_record;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        choosePatientTv=this.findViewById(R.id.choose_patient_tv);
        choosePatientTv.setOnClickListener(getController());
        backLayout=this.findViewById(R.id.back_layout);
        backLayout.setOnClickListener(view -> finish());
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MedicalRecordAdapter(new ArrayList<>());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            MedicalRecordBean bean = (MedicalRecordBean) adapter.getData().get(position);
            Intent i = new Intent(this,MedicalRecordDetailActivity.class);
            i.putExtra(MedicalRecordDetailActivity.MEDICAL_ID,bean.getMedicalId());
            ActivityUtils.startActivity(i);
        });

        refreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(getController());
        refreshLayout.setOnLoadMoreListener(getController());
        getController().fillData();
    }

    @Override
    public void showCardPopupWindow() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.general_popupwindow_layout,null);
        RecyclerView recyclerView = view.findViewById(R.id.popup_rv);
        recyclerView.setAdapter(exaReportCardPopupWindowAdapter);
        exaReportCardPopupWindowAdapter.setOnItemClickListener((adapter, view1, position) -> {
            MedicalCardInfo cardInfo = (MedicalCardInfo) adapter.getData().get(position);
            choosePatientTv.setText(cardInfo.getName());
            getController().getIndAllData(cardInfo.getEleCardNumber(), TypeEnum.REFRESH);
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
        if (dataList!=null && dataList.size()>0){
            getController(). getIndAllData(dataList.get(0).getEleCardNumber(), TypeEnum.REFRESH);
            choosePatientTv.setText(dataList.get(0).getName());
        }
    }

    @Override
    public void getRecordList(List<MedicalRecordBean> list, TypeEnum typeEnum) {
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
        pageLoadingSuccess();
        if (typeEnum == TypeEnum.REFRESH) {
            mAdapter.replaceData(list);
        }else{
            mAdapter.addData(list);
        }
    }
}
