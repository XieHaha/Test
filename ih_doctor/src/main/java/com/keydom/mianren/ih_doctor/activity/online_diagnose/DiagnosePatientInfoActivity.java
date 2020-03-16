package com.keydom.mianren.ih_doctor.activity.online_diagnose;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ganxin.library.LoadDataLayout;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.online_diagnose.controller.DiagnosePatientInfoController;
import com.keydom.mianren.ih_doctor.activity.online_diagnose.view.DiagnosePatientInfoView;
import com.keydom.mianren.ih_doctor.adapter.DiagnosePatientRecordAdapter;
import com.keydom.mianren.ih_doctor.bean.DiagnosePatientInfoBean;
import com.keydom.mianren.ih_doctor.constant.Const;

/**
 * @Name：com.keydom.ih_doctor.activity.personal
 * @Description：问诊中－患者资料页面
 * @Author：song
 * @Date：18/11/14 上午10:37
 * 修改人：xusong
 * 修改时间：18/11/14 上午10:37
 */
public class DiagnosePatientInfoActivity extends BaseControllerActivity<DiagnosePatientInfoController> implements DiagnosePatientInfoView {

    /**
     * 就诊人ID
     */
    private long userId;
    private RecyclerView recyclerView;
    /**
     * 问诊记录适配器
     */
    private DiagnosePatientRecordAdapter diagnosePatientRecordAdapter;
    private TextView userName, userSex, userAge, presentHistory, familyHistory, allergyHistory;

    /**
     * 启动患者资料页面
     *
     * @param context
     * @param userId  就诊人ID
     */
    public static void start(Context context, long userId) {
        Intent starter = new Intent(context, DiagnosePatientInfoActivity.class);
        starter.putExtra(Const.DATA, userId);
        ((Activity) context).startActivityForResult(starter, Const.DIAGNOSE_ORDER_SELECT);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_diagnose_patient_info;
    }

    @Override
    public void initData(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        userId = getIntent().getLongExtra(Const.DATA, 0);
        initView();
        pageLoading();
        getController().getUserInfo(userId);
        setReloadListener(new LoadDataLayout.OnReloadListener() {
            @Override
            public void onReload(View v, int status) {
                pageLoading();
                getController().getUserInfo(userId);
            }
        });
    }


    /**
     * 初始化页面
     */
    private void initView() {
        userName = findViewById(R.id.user_name);
        userSex = findViewById(R.id.user_sex);
        userAge = findViewById(R.id.user_age);
        presentHistory = findViewById(R.id.present_history);
        familyHistory = findViewById(R.id.family_history);
        allergyHistory = findViewById(R.id.allergy_history);
        recyclerView = findViewById(R.id.recoder_rv);
    }

    /**
     * 设置就诊人信息
     *
     * @param bean 就诊人资料对象
     */
    private void setInfo(DiagnosePatientInfoBean bean) {
        String nameStr="";
        if(bean.getName()!=null&&bean.getName().length()>8){
            nameStr=bean.getName().substring(0,2)+"..."+bean.getName().substring(6,8);
        }else {
            nameStr=bean.getName();

        }
        setTitle(nameStr);
        userName.setText(bean.getName());
        userSex.setText(CommonUtils.getSex(bean.getSex()));
        userAge.setText(String.valueOf(bean.getAge()));
        presentHistory.setText(bean.getPresentHistory());
        familyHistory.setText(bean.getFamilyHistory());
        allergyHistory.setText(bean.getAllergyHistory());
        diagnosePatientRecordAdapter = new DiagnosePatientRecordAdapter(this, bean.getList());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(diagnosePatientRecordAdapter);

    }

    @Override
    public void getPatientInfoSuccess(DiagnosePatientInfoBean bean) {
        setInfo(bean);
        pageLoadingSuccess();
    }

    @Override
    public void getPatientInfoFailed(String errMsg) {
        pageLoadingFail();
    }
}
