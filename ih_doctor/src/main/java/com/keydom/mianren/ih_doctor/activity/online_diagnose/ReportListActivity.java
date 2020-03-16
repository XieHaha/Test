package com.keydom.mianren.ih_doctor.activity.online_diagnose;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.keydom.ih_common.base.BaseActivity;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.adapter.ReportListAdapter;
import com.keydom.mianren.ih_doctor.bean.ReportItemBean;
import com.keydom.mianren.ih_doctor.constant.Const;

import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.List;

/**
 * @link Author: song
 * <p>
 * Create: 19/3/7 上午10:53
 * <p>
 * Changes (from 19/3/7)
 * <p>
 * 19/3/7 : Create ReportListActivity.java (song);
 */
public class ReportListActivity extends BaseActivity {
    /**
     * 检查报告单列表
     */
    public static final int REPORT_INSPECT = 2000;
    /**
     * 检验报告单列表
     */
    public static final int REPORT_CHECK_OUT = 2001;
    /**
     * 页面类型
     */
    private int mType;
    /**
     * 报告单项目列表
     */
    private List<ReportItemBean> reportList;
    private RecyclerView recyclerView;
    /**
     * 报告单项目列表适配器
     */
    private ReportListAdapter reportListAdapter;


    /**
     * 启动检查报告列表页面
     *
     * @param context
     * @param list    检查报告列表
     */
    public static void startInspectPage(Context context, List<ReportItemBean> list) {
        Intent intent = new Intent(context, ReportListActivity.class);
        intent.putExtra(Const.DATA, (Serializable) list);
        intent.putExtra(Const.TYPE, REPORT_INSPECT);
        context.startActivity(intent);
    }

    /**
     * 启动检验报告列表页面
     *
     * @param context
     * @param list    检验报告列表
     */
    public static void startCheckOutPage(Context context, List<ReportItemBean> list) {
        Intent intent = new Intent(context, ReportListActivity.class);
        intent.putExtra(Const.DATA, (Serializable) list);
        intent.putExtra(Const.TYPE, REPORT_CHECK_OUT);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_report_list_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mType = getIntent().getIntExtra(Const.TYPE, 0);
        reportList = (List<ReportItemBean>) getIntent().getSerializableExtra(Const.DATA);
        if (mType == REPORT_CHECK_OUT) {
            setTitle("检验报告");
        } else if (mType == REPORT_INSPECT) {
            setTitle("检查报告");
        }
        recyclerView = findViewById(R.id.report_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        reportListAdapter = new ReportListAdapter(reportList, mType);
        recyclerView.setAdapter(reportListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
