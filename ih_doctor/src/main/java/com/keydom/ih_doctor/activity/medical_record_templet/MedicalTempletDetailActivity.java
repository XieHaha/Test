package com.keydom.ih_doctor.activity.medical_record_templet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.medical_record_templet.controller.MedicalTempletDetailController;
import com.keydom.ih_doctor.activity.medical_record_templet.view.MedicalTempletDetailView;
import com.keydom.ih_doctor.adapter.MedicalTempletDetailAdapter;
import com.keydom.ih_doctor.bean.Event;
import com.keydom.ih_doctor.bean.MedicalRecordTempletBean;
import com.keydom.ih_doctor.bean.MedicalTempletDetailBean;
import com.keydom.ih_doctor.constant.EventType;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * created date: 2019/1/8 on 15:43
 * des:
 * author: HJW HP
 */
public class MedicalTempletDetailActivity extends BaseControllerActivity<MedicalTempletDetailController> implements MedicalTempletDetailView {
    /**
     * 病历模版ID key
     */
    public final static String ID = "id";
    private TextView mHeadView;
    private RecyclerView mRecyclerView;

    /**
     * 病历模版详情适配器
     */
    private MedicalTempletDetailAdapter mAdapter;
    /**
     * 病历模版数据对象
     */
    private MedicalRecordTempletBean mBean;

    /**
     * 启动病历模版页面
     *
     * @param context
     */
    public static void start(Context context) {
        Intent starter = new Intent(context, MedicalTempletDetailActivity.class);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_medical_templet_detail;
    }

    private void getView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mHeadView = (TextView) LayoutInflater.from(this).inflate(R.layout.head_medical_templet_detail, null);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        getView();
        setTitle("病历模板");
        setRightTxt("确定");
        setRightBtnListener(v -> {
            Event event = new Event(EventType.CHOOSE_MEDICAL_RECORD_TEMPLET, mBean);
            EventBus.getDefault().post(event);
            ActivityUtils.finishActivity(MedicalRecordTempletActivity.class);
            finish();
        });
        mAdapter = new MedicalTempletDetailAdapter(new ArrayList<>());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.addHeaderView(mHeadView);
        long id = getIntent().getLongExtra(ID, 0);
        getController().getTemplateDetail(id);
    }

    @Override
    public void templateDetailrequestCallBack(List<MedicalTempletDetailBean> list, MedicalRecordTempletBean bean) {
        mBean = bean;
        if (bean != null) {
            mHeadView.setText(bean.getTemplateName());
        }
        mAdapter.setNewData(list);
    }
}
