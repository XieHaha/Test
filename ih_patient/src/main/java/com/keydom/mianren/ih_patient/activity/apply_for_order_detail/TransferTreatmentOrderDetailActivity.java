package com.keydom.mianren.ih_patient.activity.apply_for_order_detail;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ganxin.library.LoadDataLayout;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.constant.Const;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.apply_for_order_detail.controller.TransferTreatmentOrderDetailController;
import com.keydom.mianren.ih_patient.activity.apply_for_order_detail.view.TransferTreatmentOrderDetailView;
import com.keydom.mianren.ih_patient.adapter.DiagnoseOrderDetailAdapter;
import com.keydom.mianren.ih_patient.bean.DiagnoseOrderDetailBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description：检查单详情页面
 * @Author：song
 * @Date：18/11/14 上午10:37
 * 修改人：xusong
 * 修改时间：18/11/14 上午10:37
 */
public class TransferTreatmentOrderDetailActivity extends BaseControllerActivity<TransferTreatmentOrderDetailController> implements TransferTreatmentOrderDetailView {

    public static final int ORDER_ACTION = 1300;
    public static final int ORDER_WITHOUT_ACTION = 1301;
    public static final String ID = "id";
    private long id;
    private TextView userName, userSex, userAge, diagnoseInfoTv, diagnoseExplainTv, doctorName, doctorJob, doctorDept, doctorBeGood, applyTime;

    private RecyclerView diagnoseInfoImgRv, diagnoseMaterialRv;

    private Button returnBt, receiveBt;

    private LinearLayout buttonLl;

    private DiagnoseOrderDetailAdapter diagnoseInfoImgAdapter, diagnoseMaterialAdapter;

    private List<String> diagnoseInfoImgList = new ArrayList<>();
    private List<String> diagnoseMaterialList = new ArrayList<>();
    private int mType;
    private Dialog notReceiveDialog;
    private EditText dialogInput;


    /**
     *启动方法
     */
    public static void startCommon(Context context, long id) {
        Intent starter = new Intent(context, TransferTreatmentOrderDetailActivity.class);
        starter.putExtra(ID, id);
        starter.putExtra(Const.TYPE, ORDER_WITHOUT_ACTION);
        context.startActivity(starter);
    }


    /**
     * 启动方法
     */
    public static void startWithAction(Context context, long id) {
        Intent starter = new Intent(context, TransferTreatmentOrderDetailActivity.class);
        starter.putExtra(ID, id);
        starter.putExtra(Const.TYPE, ORDER_WITHOUT_ACTION);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.diagnose_order_detail_layout;
    }

    @Override
    public void initData(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        id = getIntent().getLongExtra(ID, -1);
        mType = getIntent().getIntExtra(Const.TYPE, 0);
        setTitle("转诊详情");
        initView();
        pageLoading();
        getController().getDetail();
        setReloadListener(new LoadDataLayout.OnReloadListener() {
            @Override
            public void onReload(View v, int status) {
                pageLoading();
                getController().getDetail();
            }
        });
    }

    /**
     * 初始化控件
     */
    private void initView() {
        userName = this.findViewById(R.id.user_name);
        userSex = this.findViewById(R.id.user_sex);
        userAge = this.findViewById(R.id.user_age);
        diagnoseInfoTv = this.findViewById(R.id.diagnose_info_tv);
        diagnoseExplainTv = this.findViewById(R.id.diagnose_explain_tv);
        doctorName = this.findViewById(R.id.doctor_name);
        doctorJob = this.findViewById(R.id.doctor_job);
        doctorDept = this.findViewById(R.id.doctor_dept);
        doctorBeGood = this.findViewById(R.id.doctor_be_good);
        applyTime = this.findViewById(R.id.apply_time);
        buttonLl = this.findViewById(R.id.button_ll);
        diagnoseInfoImgRv = this.findViewById(R.id.diagnose_info_img_rv);
        diagnoseMaterialRv = this.findViewById(R.id.diagnose_material_rv);
        returnBt = this.findViewById(R.id.return_bt);
        receiveBt = this.findViewById(R.id.receive_bt);
        if (mType == ORDER_ACTION) {
            buttonLl.setVisibility(View.VISIBLE);
        } else {
            buttonLl.setVisibility(View.GONE);
        }
    }


    /**
     * 设置页面数据
     */
    private void setInfo(DiagnoseOrderDetailBean bean) {
        userName.setText(bean.getPatientName());
        userSex.setText(CommonUtils.getSex(bean.getPatientSex()));
        userAge.setText(String.valueOf(bean.getPatientAge()));
        diagnoseInfoTv.setText(bean.getContent());
        diagnoseExplainTv.setText(bean.getReferralExplain());
        doctorName.setText(bean.getDoctor());
        doctorJob.setText(bean.getJobTitle());
        doctorDept.setText(bean.getDept());
        doctorBeGood.setText(bean.getAdept());
        applyTime.setText(bean.getReferralTime());
        String[] diagnoseInfo;
        if (bean.getDiseaseData() != null) {
            diagnoseInfo = bean.getDiseaseData().split(",");
        } else {
            diagnoseInfo = new String[1];
        }
        for (int i = 0; i < diagnoseInfo.length; i++) {
            diagnoseMaterialList.add(diagnoseInfo[i]);
        }

        String[] materialInfo;
        if (bean.getPatientImages() != null) {
            materialInfo = bean.getPatientImages().split(",");
        } else {
            materialInfo = new String[1];
        }
        for (int i = 0; i < materialInfo.length; i++) {
            diagnoseInfoImgList.add(materialInfo[i]);
        }

        diagnoseInfoImgAdapter = new DiagnoseOrderDetailAdapter(this, diagnoseInfoImgList);
        diagnoseMaterialAdapter = new DiagnoseOrderDetailAdapter(this, diagnoseMaterialList);

        LinearLayoutManager diagnoseInfoImgRvLm = new LinearLayoutManager(this);
        diagnoseInfoImgRvLm.setOrientation(LinearLayoutManager.HORIZONTAL);
        diagnoseInfoImgRv.setAdapter(diagnoseInfoImgAdapter);
        diagnoseInfoImgRv.setLayoutManager(diagnoseInfoImgRvLm);

        LinearLayoutManager diagnoseMaterialRvLm = new LinearLayoutManager(this);
        diagnoseMaterialRvLm.setOrientation(LinearLayoutManager.HORIZONTAL);
        diagnoseMaterialRv.setAdapter(diagnoseMaterialAdapter);
        diagnoseMaterialRv.setLayoutManager(diagnoseMaterialRvLm);
    }


    @Override
    public Map<String, Object> getQueryMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        return map;
    }

    @Override
    public void getDetailSuccess(DiagnoseOrderDetailBean bean) {
        if (bean != null) {
            setInfo(bean);
            pageLoadingSuccess();
        } else {
            pageLoadingFail();
        }

    }

    @Override
    public void getDetailFailed(String errMsg) {
        pageLoadingFail();
    }


}
