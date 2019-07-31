package com.keydom.ih_doctor.activity.medical_record_templet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.medical_record_templet.controller.MedicalRecordTempletController;
import com.keydom.ih_doctor.activity.medical_record_templet.view.MedicalRecordTempletView;
import com.keydom.ih_doctor.adapter.MedicalRecordTempletAdapter;
import com.keydom.ih_doctor.bean.MedicalRecordTempletBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * created date: 2019/1/8 on 14:42
 * des:
 * author: HJW HP
 */
public class MedicalRecordTempletActivity extends BaseControllerActivity<MedicalRecordTempletController> implements MedicalRecordTempletView {

    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private TextView mDepartment, search;
    private EditText mSearchEt;


    /**
     * 病历模版适配器
     */
    private MedicalRecordTempletAdapter mAdapter;
    /**
     * 模版类型
     */
    private int mType = 0;

    /**
     * 启动病历模版页面
     *
     * @param context
     */
    public static void start(Context context) {
        Intent starter = new Intent(context, MedicalRecordTempletActivity.class);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_medical_record_templet;
    }

    private void getView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRefreshLayout = findViewById(R.id.refresh_layout);
        mDepartment = findViewById(R.id.department);
        mSearchEt = findViewById(R.id.search_et);
        search = findViewById(R.id.search);
        mDepartment.setOnClickListener(getController());
        //搜索
        search.setOnClickListener(v -> {
            getController().setDefaultPage();
            getData();
            CommonUtils.hideSoftKeyboard(MedicalRecordTempletActivity.this);
        });
        //刷新
        mRefreshLayout.setOnRefreshListener(refreshLayout -> {
            getController().setDefaultPage();
            getData();
        });
        //加载更多
        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> getData());
        getData();
    }

    private void getData() {
        getController().getTemplateList(mType);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("病历模板");
        getView();
        mAdapter = new MedicalRecordTempletAdapter(new ArrayList<>());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            MedicalRecordTempletBean bean = (MedicalRecordTempletBean) adapter.getData().get(position);
            long id = bean.getId();
            Intent i = new Intent(MedicalRecordTempletActivity.this, MedicalTempletDetailActivity.class);
            i.putExtra(MedicalTempletDetailActivity.ID, id);
            ActivityUtils.startActivity(i);
        });
    }

    @Override
    public void templateListRequestCallBack(List<MedicalRecordTempletBean> data) {
        if (mRefreshLayout.isRefreshing()) {
            mRefreshLayout.finishRefresh();
        }
        if (mRefreshLayout.isLoading()) {
            mRefreshLayout.finishLoadMore();
        }
        if (getController().getCurrentPage() == 1) {
            mAdapter.setNewData(data);
        } else {
            mAdapter.addData(data);
        }
        getController().currentPagePlus();
    }

    @Override
    public void requestErrorCallBack() {
        if (mRefreshLayout.isRefreshing()) {
            mRefreshLayout.finishRefresh();
        }
        if (mRefreshLayout.isLoading()) {
            mRefreshLayout.finishLoadMore();
        }
    }

    @Override
    public String getSearchStr() {
        return mSearchEt.getText().toString();
    }

    @Override
    public void setDept(String dept) {
        mDepartment.setText(dept);
    }

    @Override
    public void setType(int type) {
        mType = type;
        getData();
    }
}
