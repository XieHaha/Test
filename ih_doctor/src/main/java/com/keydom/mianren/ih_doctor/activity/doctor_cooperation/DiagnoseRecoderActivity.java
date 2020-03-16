package com.keydom.mianren.ih_doctor.activity.doctor_cooperation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ganxin.library.LoadDataLayout;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.mianren.ih_doctor.MyApplication;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.doctor_cooperation.controller.DiagnoseRecoderController;
import com.keydom.mianren.ih_doctor.activity.doctor_cooperation.view.DiagnoseRecoderView;
import com.keydom.mianren.ih_doctor.adapter.DiagnoseRecoderRecyclrViewAdapter;
import com.keydom.mianren.ih_doctor.bean.DiagnoseRecoderBean;
import com.keydom.mianren.ih_doctor.constant.Const;
import com.keydom.mianren.ih_doctor.constant.TypeEnum;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Name：com.keydom.ih_doctor.activity.personal
 * @Description：问诊记录页面
 * @Author：song
 * @Date：18/11/14 上午10:37
 * 修改人：xusong
 * 修改时间：18/11/14 上午10:37
 */
public class DiagnoseRecoderActivity extends BaseControllerActivity<DiagnoseRecoderController> implements DiagnoseRecoderView {

    /**
     * 问诊记录列表数据
     */
    private List<DiagnoseRecoderBean> mList = new ArrayList<>();
    /**
     * 问诊记录适配器
     */
    private DiagnoseRecoderRecyclrViewAdapter diagnoseRecoderRecyclrViewAdapter;

    private RecyclerView recyclerView;
    private RefreshLayout refreshLayout;
    private TextView searchTv;
    private EditText searchEt;

    /**
     * 启动问诊记录页面
     *
     * @param context
     */
    public static void start(Context context) {
        Intent starter = new Intent(context, DiagnoseRecoderActivity.class);
        context.startActivity(starter);
    }


    @Override
    public int getLayoutRes() {
        return R.layout.activity_diagnose_recoder_layout;
    }

    @Override
    public void initData(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        setTitle("问诊记录");
        recyclerView = this.findViewById(R.id.diagnose_recoder_rv);
        diagnoseRecoderRecyclrViewAdapter = new DiagnoseRecoderRecyclrViewAdapter(this, mList);
        recyclerView.setAdapter(diagnoseRecoderRecyclrViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        refreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);
        searchTv = (TextView) findViewById(R.id.search_tv);
        searchEt = (EditText) findViewById(R.id.search_input_ev);
        refreshLayout.setOnRefreshListener(getController());
        refreshLayout.setOnLoadMoreListener(getController());
        searchTv.setOnClickListener(getController());
        pageLoading();
        getController().getInquisitionRecordByDocId(TypeEnum.REFRESH);
        setReloadListener(new LoadDataLayout.OnReloadListener() {
            @Override
            public void onReload(View v, int status) {
                pageLoading();
                getController().getInquisitionRecordByDocId(TypeEnum.REFRESH);
            }
        });
    }


    @Override
    public void getDiagnoseRecoderSuccess(List<DiagnoseRecoderBean> list, TypeEnum type) {
        if (type == TypeEnum.REFRESH) {
            mList.clear();
        }
        pageLoadingSuccess();
        mList.addAll(list);
        diagnoseRecoderRecyclrViewAdapter.notifyDataSetChanged();
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
        getController().currentPagePlus();
    }

    @Override
    public void getDiagnoseRecoderFailed(String errMsg) {
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
        pageLoadingFail();
    }

    @Override
    public Map<String, Object> getQueryMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("currentPage", getController().getCurrentPage());
        map.put("pageSize", Const.PAGE_SIZE);
        map.put("doctorId", MyApplication.userInfo.getId());
        map.put("keyword", searchEt.getText().toString().trim());
        return map;
    }
}
