package com.keydom.ih_patient.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.inspection_report.InspectionReportActivity;
import com.keydom.ih_patient.adapter.InspectionReportAdapter;
import com.keydom.ih_patient.callback.GeneralCallback;
import com.keydom.ih_patient.constant.Type;
import com.keydom.ih_patient.fragment.controller.InspectionReportFmController;
import com.keydom.ih_patient.fragment.view.InspectionReportFmView;
import com.keydom.ih_patient.utils.ToastUtil;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * 检查检验页面
 */
public class InspectionReportFragment extends BaseControllerFragment<InspectionReportFmController> implements InspectionReportFmView, GeneralCallback.ExaReportActivityListener {
    private SmartRefreshLayout containt_refresh;
    private RecyclerView containt_rv;
    private List<Object> dataList=new ArrayList<>();
    private InspectionReportAdapter inspectionReportAdapter;
    //type 类型 Type.INSPECTIONTYPE 检验  Type.BODYCHECKTYPE 检查  selectedCardNum选中的就诊卡号
    private String type,selectedCardNum;
    private RelativeLayout emptyLayout;
    private TextView emptyTv;
    @Override
    public int getLayoutRes() {
        return R.layout.fragment_order_examination_layout;
    }
    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle=getArguments();
        type=bundle.getString("type");
        containt_refresh=view.findViewById(R.id.containt_refresh);
        containt_rv=view.findViewById(R.id.containt_rv);
        inspectionReportAdapter=new InspectionReportAdapter(getContext(),dataList);
        containt_rv.setAdapter(inspectionReportAdapter);
        containt_refresh.setEnableLoadMore(false);
        containt_refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                if(Type.INSPECTIONTYPE.equals(type)){
                    getController().getInspectionReportList(selectedCardNum);
                }else {
                    getController().getBodyCheckReportList(selectedCardNum);
                }
            }
        });
        emptyLayout=view.findViewById(R.id.state_retry2);
        emptyTv=view.findViewById(R.id.empty_text);
        emptyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Type.INSPECTIONTYPE.equals(type)){
                    getController().getInspectionReportList(selectedCardNum);
                }else {
                    getController().getBodyCheckReportList(selectedCardNum);
                }
            }
        });
    }

    @Override
    public void getDataListSuccess(List<Object> dataList) {
        containt_refresh.finishRefresh();
        if(dataList!=null&&dataList.size()!=0){
            if(containt_refresh.getVisibility()==View.GONE){
                containt_refresh.setVisibility(View.VISIBLE);
                emptyLayout.setVisibility(View.GONE);
            }
            this.dataList.clear();
            this.dataList.addAll(dataList);
            inspectionReportAdapter.updateShowList(this.dataList);
            inspectionReportAdapter.notifyDataSetChanged();
        }else {
            containt_refresh.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.VISIBLE);
            emptyLayout.setClickable(false);
            if(Type.INSPECTIONTYPE.equals(type)){
                emptyTv.setText("暂无检验报告");
            }else {
                emptyTv.setText("暂无检查报告");
            }
        }
    }

    @Override
    public void getDataListFailed(int errCode,String errMsg) {
        Logger.e("errCode="+errMsg+"    errMsg="+errMsg);
        containt_refresh.finishRefresh();
        containt_refresh.setVisibility(View.GONE);
        emptyLayout.setVisibility(View.VISIBLE);
        if(errCode==300){
            if(Type.INSPECTIONTYPE.equals(type)){
                emptyTv.setText(errMsg);
            }else {
                emptyTv.setText(errMsg);
            }
            emptyLayout.setClickable(false);
        }else {
            emptyTv.setText("数据加载失败，点击重试");
            ToastUtil.shortToast(getContext(),"列表加载失败："+errMsg);
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
        selectedCardNum=cardNum;
        if(Type.INSPECTIONTYPE.equals(type)){
            getController().getInspectionReportList(selectedCardNum);
        }else {
            getController().getBodyCheckReportList(selectedCardNum);
        }
    }
}
