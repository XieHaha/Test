package com.keydom.ih_patient.activity.inspection_report;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.inspection_report.controller.BodyCheckDetailController;
import com.keydom.ih_patient.activity.inspection_report.view.BodyCheckDetailView;
import com.keydom.ih_patient.adapter.BodyCheckDetailAdapter;
import com.keydom.ih_patient.bean.BodyCheckDetailInfo;
import com.keydom.ih_patient.utils.ToastUtil;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
/**
*@Author: LiuJie
*@Date: 2019/3/4 0004
*@Desc: 检查报告单详情页面
*/
public class BodyCheckDetailActivity extends BaseControllerActivity<BodyCheckDetailController> implements BodyCheckDetailView {
    /**
     * 启动
     */
    public static void start(Context context, String applyNumber, String checkoutName){
        Intent intent=new Intent(context,BodyCheckDetailActivity.class);
        intent.putExtra("applyNumber",applyNumber);
        intent.putExtra("checkoutName",checkoutName);
        context.startActivity(intent);
    }
    private TextView bodycheck_title_tv,name_tv,sex_tv,age_tv,department_tv,order_num_tv,body_check_part_tv,body_check_photo_desc_tv,body_check_suggestion_tv,report_doctor_name_tv,audit_doctor_name_tv,date_tv;
    private RecyclerView body_check_photo_rv;
    private String applyNumber,checkoutName;
    private BodyCheckDetailAdapter bodyCheckDetailAdapter;
    private List<String> urlList=new ArrayList<>();
    @Override
    public int getLayoutRes() {
        return R.layout.activity_bodycheck_detail_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("检查报告单结果");
        bodycheck_title_tv=findViewById(R.id.bodycheck_title_tv);
        name_tv=findViewById(R.id.name_tv);
        sex_tv=findViewById(R.id.sex_tv);
        age_tv=findViewById(R.id.age_tv);
        department_tv=findViewById(R.id.department_tv);
        order_num_tv=findViewById(R.id.order_num_tv);
        body_check_part_tv=findViewById(R.id.body_check_part_tv);
        body_check_photo_desc_tv=findViewById(R.id.body_check_photo_desc_tv);
        body_check_suggestion_tv=findViewById(R.id.body_check_suggestion_tv);
        report_doctor_name_tv=findViewById(R.id.report_doctor_name_tv);
        audit_doctor_name_tv=findViewById(R.id.audit_doctor_name_tv);
        date_tv=findViewById(R.id.date_tv);
        body_check_photo_rv=findViewById(R.id.body_check_photo_rv);
        body_check_photo_rv.setLayoutManager(new GridLayoutManager(getContext(),4));
        bodyCheckDetailAdapter=new BodyCheckDetailAdapter(urlList,getContext());
        body_check_photo_rv.setAdapter(bodyCheckDetailAdapter);
        applyNumber=getIntent().getStringExtra("applyNumber");
        checkoutName=getIntent().getStringExtra("checkoutName");
        bodycheck_title_tv.setText(checkoutName+"检查报告单");
        getController().getBodyCheckDetail(applyNumber);
    }

    @Override
    public void getBodyCheckDetailSuccess(BodyCheckDetailInfo bodyCheckDetailInfo) {
        if(bodyCheckDetailInfo!=null){
            name_tv.setText(bodyCheckDetailInfo.getPatientName()!=null&&!"".equals(bodyCheckDetailInfo.getPatientName())?bodyCheckDetailInfo.getPatientName():"");
            sex_tv.setText(CommonUtils.getPatientSex(bodyCheckDetailInfo.getSex()));
            age_tv.setText(bodyCheckDetailInfo.getAge()!=null&&!"".equals(bodyCheckDetailInfo.getAge())?bodyCheckDetailInfo.getAge():"");
            department_tv.setText(bodyCheckDetailInfo.getHospitalDept()!=null&&!"".equals(bodyCheckDetailInfo.getHospitalDept())?bodyCheckDetailInfo.getHospitalDept():"");
            order_num_tv.setText(bodyCheckDetailInfo.getInspectNumber()!=null&&!"".equals(bodyCheckDetailInfo.getInspectNumber())?bodyCheckDetailInfo.getInspectNumber():"");
            body_check_part_tv.setText(bodyCheckDetailInfo.getInspectionSite()!=null&&!"".equals(bodyCheckDetailInfo.getInspectionSite())?bodyCheckDetailInfo.getInspectionSite():"");
            body_check_photo_desc_tv.setText(bodyCheckDetailInfo.getInspectFinding()!=null&&!"".equals(bodyCheckDetailInfo.getInspectFinding())?bodyCheckDetailInfo.getInspectFinding():"");
            body_check_suggestion_tv.setText(bodyCheckDetailInfo.getSuggest()!=null&&!"".equals(bodyCheckDetailInfo.getSuggest())?bodyCheckDetailInfo.getSuggest():"");
            report_doctor_name_tv.setText(bodyCheckDetailInfo.getReporter()!=null&&!"".equals(bodyCheckDetailInfo.getReporter())?bodyCheckDetailInfo.getReporter():"");
            audit_doctor_name_tv.setText(bodyCheckDetailInfo.getAuditedDoctor()!=null&&!"".equals(bodyCheckDetailInfo.getAuditedDoctor())?bodyCheckDetailInfo.getAuditedDoctor():"");
            date_tv.setText(bodyCheckDetailInfo.getResultTime()!=null&&!"".equals(bodyCheckDetailInfo.getResultTime())?bodyCheckDetailInfo.getResultTime():"");
            bodyCheckDetailAdapter.setNewData(bodyCheckDetailInfo.getImageList());
        }

    }

    @Override
    public void getBodyCheckDetailFailed(String errMsg) {
        ToastUtil.shortToast(getContext(),checkoutName+"检查报告单详情获取失败"+errMsg);
        finish();
    }
}
