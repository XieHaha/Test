package com.keydom.mianren.ih_patient.activity.nursing_service;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.nursing_service.controller.NursingController;
import com.keydom.mianren.ih_patient.activity.nursing_service.view.NursingView;
import com.keydom.mianren.ih_patient.adapter.NursingProjectAdapter;
import com.keydom.mianren.ih_patient.bean.NursingProjectInfo;
import com.keydom.mianren.ih_patient.constant.Type;
import com.keydom.mianren.ih_patient.constant.TypeEnum;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * 护理服务页面
 */
public class NursingActivity extends BaseControllerActivity<NursingController> implements NursingView {

    /**
     * 启动
     */
    public static void start(Context context,String type, long nursingTypeId){
        Intent intent=new Intent(context,NursingActivity.class);
        intent.putExtra("type",type);
        intent.putExtra("nursingTypeId",nursingTypeId);
        context.startActivity(intent);
    }
    private RelativeLayout emptyLayout;
    private TextView emptyTv;
    private RecyclerView serviceListView;
    private String type;
    private long nursingTypeId;
    private SmartRefreshLayout conten_refresh_layout;
    private RecyclerView conten_rv;
    private NursingProjectAdapter nursingProjectAdapter;
    private List<NursingProjectInfo> dataList=new ArrayList<>();

    @Override
    public int getLayoutRes() {
        return R.layout.activity_nursing_service_list_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        type=getIntent().getStringExtra("type");
        nursingTypeId=getIntent().getLongExtra("nursingTypeId",-1);
        Logger.e("type=="+type);
        if(Type.BASENURSING.equals(type)){
            setTitle("基础护理");
        }else if(Type.PROFESSIONALNURSING.equals(type)){
            setTitle("专科护理");
        }else if(Type.POSTPARTUMNURSING.equals(type)){
            setTitle("产后护理");
        }
        conten_refresh_layout=findViewById(R.id.conten_refresh_layout);
        conten_refresh_layout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                getController().getNurseServiceProjectByCateId(String.valueOf(nursingTypeId),TypeEnum.REFRESH);
                refreshLayout.finishRefresh();
            }
        });
        conten_refresh_layout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                getController().getNurseServiceProjectByCateId(String.valueOf(nursingTypeId), TypeEnum.LOAD_MORE);
                refreshLayout.finishLoadMore();
            }
        });
        conten_rv=findViewById(R.id.conten_rv);
        emptyLayout=findViewById(R.id.state_retry2);
        emptyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getController().getNurseServiceProjectByCateId(String.valueOf(nursingTypeId),TypeEnum.REFRESH);
            }
        });
        emptyTv=findViewById(R.id.empty_text);
        serviceListView=findViewById(R.id.conten_rv);
        nursingProjectAdapter=new NursingProjectAdapter(Type.ENABLESELCTEDLIST,dataList, new NursingProjectAdapter.onItemBtnClickListener() {
            @Override
            public void onRightSelectListener() {

            }

            @Override
            public void onClickListener(NursingProjectInfo item) {
                NursingProjectDetailActivity.start(getContext(), String.valueOf(item.getId()),item.getName());
            }
        });
        conten_rv.setAdapter(nursingProjectAdapter);
        getController().getNurseServiceProjectByCateId(String.valueOf(nursingTypeId),TypeEnum.REFRESH);
    }

    @Override
    public void getNursingProjectSuccess(List<NursingProjectInfo> dataList, TypeEnum typeEnum) {
        conten_refresh_layout.finishLoadMore();
        conten_refresh_layout.finishRefresh();
        pageLoadingSuccess();
        if(dataList.size()!=0){

            if(emptyLayout.getVisibility()==View.VISIBLE)
                emptyLayout.setVisibility(View.GONE);

            if (typeEnum == TypeEnum.REFRESH) {
                nursingProjectAdapter.replaceData(dataList);
            }else{
                nursingProjectAdapter.addData(dataList);
            }
            getController().currentPagePlus();
        }else {
            if(typeEnum == TypeEnum.REFRESH){
                emptyLayout.setVisibility(View.VISIBLE);
                emptyTv.setText("暂无数据");
                emptyLayout.setClickable(false);
            }
        }


    }

    @Override
    public void getNursingProjectFailed(String errMsg) {
        conten_refresh_layout.finishLoadMore();
        conten_refresh_layout.finishRefresh();
        emptyLayout.setVisibility(View.VISIBLE);
        emptyTv.setText("加载失败 点击重试");
        emptyLayout.setClickable(true);
    }
}
