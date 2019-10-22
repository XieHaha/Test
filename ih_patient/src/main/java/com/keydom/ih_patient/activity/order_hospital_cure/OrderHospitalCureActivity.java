package com.keydom.ih_patient.activity.order_hospital_cure;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.PhoneUtils;
import com.keydom.ih_common.view.InterceptorEditText;
import com.keydom.ih_patient.App;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.order_hospital_cure.controller.OrderHospitalCureController;
import com.keydom.ih_patient.activity.order_hospital_cure.view.OrderHospitalCureView;
import com.keydom.ih_patient.bean.HospitalCureInfo;
import com.keydom.ih_patient.constant.Global;
import com.keydom.ih_patient.constant.Type;
import com.keydom.ih_patient.utils.DateUtils;
import com.keydom.ih_patient.utils.ToastUtil;
import com.orhanobut.logger.Logger;

import org.jetbrains.annotations.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 入院页面
 */
public class OrderHospitalCureActivity extends BaseControllerActivity<OrderHospitalCureController> implements OrderHospitalCureView {
    /**
     * 启动
     */
    public static void start(Context context, String type, HospitalCureInfo hospitalCureInfo){
        HospitalCureInfo=hospitalCureInfo;
        Intent intent=new Intent(context,OrderHospitalCureActivity.class);
        intent.putExtra("type",type);
        context.startActivity(intent);
    }
    private static HospitalCureInfo HospitalCureInfo;
    private String type;
    private ImageView node_of_order,node_of_audit,node_of_confirm,order_cure_back_img;
    private InterceptorEditText order_name_edt,order_phone_edt,relationshiper_phone_edt,order_address_edt,cure_service_edt,health_care_card_num,order_note_edt;
    private TextView order_region_tv,order_date_tv,order_health_care_type_tv,order_health_care_region_tv,order_settlement_type_tv,click_to_show_more_tv,forecast_ordered_time_tv,cure_attention_tv,remove_order_tv,commit_order_tv,unconfirm_tv,confirm_tv,confirm_head_label_tv;
    private InterceptorEditText apply_date_edt,report_date_edt,report_address_edt,report_kown_edt;
    private RelativeLayout click_to_show_more_layout,order_audit_head_layout,order_confirm_head_layout;
    private LinearLayout confirm_order_layout,order_top_layout,order_bottom_layout,heal_care_layout,report_layout;
    private View procee_to_audit,process_to_confirm;
    private String orderRegionCode,orderRegionName,healthCareRegionCode,healthCareRegionName;
    private int healthTypeCode=-1,SettlementTypeCode=-1;
    private HospitalCureInfo queryHospitalCureInfo;
    private TextView order_time_tv,audit_time_tv,confirm_time_tv;
    @Override
    public int getLayoutRes() {
        return R.layout.order_in_hospital_cure_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        type=getIntent().getStringExtra("type");
        Logger.e("type=="+type);
        order_time_tv=findViewById(R.id.order_time_tv);
        audit_time_tv=findViewById(R.id.audit_time_tv);
        confirm_time_tv=findViewById(R.id.confirm_time_tv);
        node_of_order=this.findViewById(R.id.node_of_order);
        node_of_audit=this.findViewById(R.id.node_of_audit);
        node_of_confirm=this.findViewById(R.id.node_of_confirm);
        procee_to_audit=this.findViewById(R.id.procee_to_audit);
        process_to_confirm=this.findViewById(R.id.process_to_confirm);
        heal_care_layout=this.findViewById(R.id.heal_care_layout);
        confirm_head_label_tv=this.findViewById(R.id.confirm_head_label_tv);
        report_layout=this.findViewById(R.id.report_layout);
        apply_date_edt=this.findViewById(R.id.apply_date_edt);
        report_date_edt=this.findViewById(R.id.report_date_edt);
        report_address_edt=this.findViewById(R.id.report_address_edt);
        report_kown_edt=this.findViewById(R.id.report_kown_edt);
        order_cure_back_img=findViewById(R.id.order_cure_back_img);
        order_cure_back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        order_name_edt=this.findViewById(R.id.order_name_edt);
        order_phone_edt=this.findViewById(R.id.order_phone_edt);
        relationshiper_phone_edt=this.findViewById(R.id.relationshiper_phone_edt);
        order_address_edt=this.findViewById(R.id.order_address_edt);
        cure_service_edt=this.findViewById(R.id.cure_service_edt);
        order_note_edt=this.findViewById(R.id.order_note_edt);

        health_care_card_num=this.findViewById(R.id.health_care_card_num);
        order_region_tv=this.findViewById(R.id.order_region_tv);
        order_region_tv.setOnClickListener(getController());
        order_date_tv=this.findViewById(R.id.order_date_tv);
        order_health_care_type_tv=this.findViewById(R.id.order_health_care_type_tv);
        order_health_care_type_tv.setOnClickListener(getController());
        order_health_care_region_tv=this.findViewById(R.id.order_health_care_region_tv);
        order_health_care_region_tv.setOnClickListener(getController());
        order_settlement_type_tv=this.findViewById(R.id.order_settlement_type_tv);
        order_settlement_type_tv.setOnClickListener(getController());
        click_to_show_more_tv=this.findViewById(R.id.click_to_show_more_tv);
        forecast_ordered_time_tv=this.findViewById(R.id.forecast_ordered_time_tv);
        cure_attention_tv=this.findViewById(R.id.cure_attention_tv);
        cure_attention_tv.setOnClickListener(getController());
        remove_order_tv=this.findViewById(R.id.remove_order_tv);
        remove_order_tv.setOnClickListener(getController());
        commit_order_tv=this.findViewById(R.id.commit_order_tv);
        commit_order_tv.setOnClickListener(getController());
        unconfirm_tv=this.findViewById(R.id.unconfirm_tv);
        unconfirm_tv.setOnClickListener(getController());
        confirm_tv=this.findViewById(R.id.confirm_tv);
        confirm_tv.setOnClickListener(getController());
        click_to_show_more_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click_to_show_more_layout.setVisibility(View.GONE);
                order_bottom_layout.setVisibility(View.VISIBLE);
            }
        });

        order_top_layout=this.findViewById(R.id.order_top_layout);
        click_to_show_more_layout=this.findViewById(R.id.click_to_show_more_layout);
        order_audit_head_layout=this.findViewById(R.id.order_audit_head_layout);
        order_confirm_head_layout=this.findViewById(R.id.order_confirm_head_layout);
        confirm_order_layout=this.findViewById(R.id.confirm_order_layout);
        order_bottom_layout=this.findViewById(R.id.order_bottom_layout);
        order_name_edt.setEnabled(false);
//        order_phone_edt.setEnabled(false);
        cure_service_edt.setEnabled(false);

        if(!type.equals(Type.ORDERCUREAPPLY))
        {
            relationshiper_phone_edt.setEnabled(false);
            order_address_edt.setEnabled(false);

            health_care_card_num.setEnabled(false);
            order_note_edt.setEnabled(false);
            apply_date_edt.setEnabled(false);
            report_date_edt.setEnabled(false);
            report_address_edt.setEnabled(false);
            report_kown_edt.setEnabled(false);
            order_date_tv.setClickable(false);
            order_region_tv.setClickable(false);
            order_health_care_type_tv.setClickable(false);
            order_health_care_region_tv.setClickable(false);
            order_settlement_type_tv.setClickable(false);
            getController().queryHospitalCure(HospitalCureInfo.getNumber());
        }else {
            order_name_edt.setText(HospitalCureInfo.getName());
            order_phone_edt.setText(HospitalCureInfo.getPhoneNumber());
            cure_service_edt.setText(HospitalCureInfo.getAdmissionDept());
            order_date_tv.setText(HospitalCureInfo.getIssuingTime());
        }
        if(type.equals(Type.ORDERCUREAUDIT)){
            procee_to_audit.setBackgroundColor(getResources().getColor(R.color.primary_color));
            node_of_order.setImageResource(R.mipmap.process_done);
            node_of_audit.setImageResource(R.mipmap.process_doing);
            click_to_show_more_layout.setVisibility(View.VISIBLE);
            order_bottom_layout.setVisibility(View.GONE);
            commit_order_tv.setVisibility(View.GONE);
            remove_order_tv.setVisibility(View.VISIBLE);
            order_audit_head_layout.setVisibility(View.VISIBLE);
        }else if(type.equals(Type.ORDERCURECONFIRM)){
            procee_to_audit.setBackgroundColor(getResources().getColor(R.color.primary_color));
            process_to_confirm.setBackgroundColor(getResources().getColor(R.color.primary_color));
            node_of_order.setImageResource(R.mipmap.process_done);
            node_of_audit.setImageResource(R.mipmap.process_done);
            node_of_confirm.setImageResource(R.mipmap.process_doing);
            commit_order_tv.setVisibility(View.GONE);
            heal_care_layout.setVisibility(View.GONE);
            report_layout.setVisibility(View.VISIBLE);
            confirm_order_layout.setVisibility(View.VISIBLE);

        }else if(type.equals(Type.ORDERCURESUCCESS)){
            procee_to_audit.setBackgroundColor(getResources().getColor(R.color.primary_color));
            process_to_confirm.setBackgroundColor(getResources().getColor(R.color.primary_color));
            node_of_order.setImageResource(R.mipmap.process_done);
            node_of_audit.setImageResource(R.mipmap.process_done);
            node_of_confirm.setImageResource(R.mipmap.process_done);
            commit_order_tv.setVisibility(View.GONE);
            click_to_show_more_layout.setVisibility(View.VISIBLE);
            order_confirm_head_layout.setVisibility(View.VISIBLE);
            order_bottom_layout.setVisibility(View.GONE);
            heal_care_layout.setVisibility(View.GONE);
            report_layout.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams layoutParams= (LinearLayout.LayoutParams) cure_attention_tv.getLayoutParams();
            layoutParams.setMargins(0,getResources().getDimensionPixelSize(R.dimen.dp_43),0,getResources().getDimensionPixelSize(R.dimen.dp_43));
            cure_attention_tv.setLayoutParams(layoutParams);
            cure_attention_tv.setText("请仔细阅读《入院注意事项》");
        }

    }

    @Override
    public void getOrderRegion(String regionName, String regionCode) {
        orderRegionCode=regionCode;
        orderRegionName=regionName;
        order_region_tv.setText(orderRegionName);
    }

    @Override
    public void getHealthCareRegion(String regionName, String regionCode) {
        healthCareRegionCode=regionCode;
        healthCareRegionName=regionName;
        order_health_care_region_tv.setText(healthCareRegionName);
    }

    @Override
    public void getHealthType(int healthTypeCode) {
        this.healthTypeCode=healthTypeCode;
        if(healthTypeCode==0){
            order_health_care_type_tv.setText("城镇职工基本医疗保险");
        }else if(healthTypeCode==1){
            order_health_care_type_tv.setText("新型农村合作医疗");
        }else {
            order_health_care_type_tv.setText("城镇居民基本医疗保险");
        }
    }

    @Override
    public void getSettlementType(int SettlementTypeCode) {
        this.SettlementTypeCode=SettlementTypeCode;
        if(SettlementTypeCode==0){
            order_settlement_type_tv.setText("异地结算");
        }else if(SettlementTypeCode==1){
            order_settlement_type_tv.setText("回属地报销");
        }else {
            order_settlement_type_tv.setText("现金");
        }
    }

    @Override
    public void applyHealthCureSuccess() {
        ToastUtil.shortToast(getContext(),"申请成功，正在审核中");
        finish();
    }

    @Override
    public void applyHealthCureFailed(String errMsg) {
        ToastUtil.shortToast(getContext(),errMsg);
    }

    @Override
    public void getHealthCureSuccess(com.keydom.ih_patient.bean.HospitalCureInfo hospitalCureInfo) {
        queryHospitalCureInfo=hospitalCureInfo;
        if(queryHospitalCureInfo.getApplyTime()!=null&&!"".equals(queryHospitalCureInfo.getApplyTime()))
            order_time_tv.setText(DateUtils.getYMDfromYMDHMS(queryHospitalCureInfo.getApplyTime()));
        if(queryHospitalCureInfo.getApprovalTime()!=null&&!"".equals(queryHospitalCureInfo.getApprovalTime()))
            audit_time_tv.setText(DateUtils.getYMDfromYMDHMS(queryHospitalCureInfo.getApprovalTime()));
        if(queryHospitalCureInfo.getConfirmationTime()!=null&&!"".equals(queryHospitalCureInfo.getConfirmationTime()))
            confirm_time_tv.setText(DateUtils.getYMDfromYMDHMS(queryHospitalCureInfo.getConfirmationTime()));
        order_name_edt.setText("".equals(queryHospitalCureInfo.getName())||queryHospitalCureInfo.getName()==null?"":queryHospitalCureInfo.getName());
        order_phone_edt.setText("".equals(queryHospitalCureInfo.getPhoneNumber())||queryHospitalCureInfo.getPhoneNumber()==null?"":queryHospitalCureInfo.getPhoneNumber());
        relationshiper_phone_edt.setText("".equals(queryHospitalCureInfo.getContactPhone())||queryHospitalCureInfo.getContactPhone()==null?"":queryHospitalCureInfo.getContactPhone());
        order_region_tv.setText("".equals(queryHospitalCureInfo.getDistrict())||queryHospitalCureInfo.getDistrict()==null?"":queryHospitalCureInfo.getDistrict());
        order_address_edt.setText("".equals(queryHospitalCureInfo.getAddress())||queryHospitalCureInfo.getAddress()==null?"":queryHospitalCureInfo.getAddress());
        cure_service_edt.setText("".equals(queryHospitalCureInfo.getAdmissionDept())||queryHospitalCureInfo.getAdmissionDept()==null?"":queryHospitalCureInfo.getAdmissionDept());
        order_date_tv.setText("".equals(queryHospitalCureInfo.getAdmissionTime())||queryHospitalCureInfo.getAdmissionTime()==null?"":queryHospitalCureInfo.getAdmissionTime());
        if(queryHospitalCureInfo.getHealthType()==0){
            order_health_care_type_tv.setText("城镇职工基本医疗保险");
        }else if(queryHospitalCureInfo.getHealthType()==1){
            order_health_care_type_tv.setText("新型农村合作医疗");
        }else {
            order_health_care_type_tv.setText("城镇居民基本医疗保险");
        }

        health_care_card_num.setText("".equals(queryHospitalCureInfo.getHealthNumber())||queryHospitalCureInfo.getHealthNumber()==null?"":queryHospitalCureInfo.getHealthNumber());
        order_health_care_region_tv.setText("".equals(queryHospitalCureInfo.getHealthSource())||queryHospitalCureInfo.getHealthSource()==null?"":queryHospitalCureInfo.getHealthSource());
        if(queryHospitalCureInfo.getSettlementType()==0){
            order_settlement_type_tv.setText("异地结算");
        }else if(queryHospitalCureInfo.getSettlementType()==1){
            order_settlement_type_tv.setText("回属地报销");
        }else {
            order_settlement_type_tv.setText("现金");
        }
        order_note_edt.setText("".equals(queryHospitalCureInfo.getRemark())||queryHospitalCureInfo.getRemark()==null?"":queryHospitalCureInfo.getRemark());
        forecast_ordered_time_tv.setText("".equals(queryHospitalCureInfo.getExpectedBedTime())||queryHospitalCureInfo.getExpectedBedTime()==null?"":queryHospitalCureInfo.getExpectedBedTime());

        apply_date_edt.setText("".equals(queryHospitalCureInfo.getApplyTime())||queryHospitalCureInfo.getApplyTime()==null?"":queryHospitalCureInfo.getApplyTime());
        report_date_edt.setText("".equals(queryHospitalCureInfo.getReportTime())||queryHospitalCureInfo.getReportTime()==null?"":queryHospitalCureInfo.getReportTime()+"前");
        report_address_edt.setText("".equals(queryHospitalCureInfo.getReportAddress())||queryHospitalCureInfo.getReportAddress()==null?"":queryHospitalCureInfo.getReportAddress());
        report_kown_edt.setText("".equals(queryHospitalCureInfo.getReportNotice())||queryHospitalCureInfo.getReportNotice()==null?"":queryHospitalCureInfo.getReportNotice());
        if(queryHospitalCureInfo.getState()==4){
            String refuseReason="";
            if(queryHospitalCureInfo.getNoPunctualReason()!=null&&queryHospitalCureInfo.getNoPunctualReason().length()>20)
                refuseReason=queryHospitalCureInfo.getNoPunctualReason().substring(0,19)+"...";
            else if(queryHospitalCureInfo.getNoPunctualReason()!=null&&queryHospitalCureInfo.getNoPunctualReason().length()<=20)
                refuseReason=queryHospitalCureInfo.getNoPunctualReason();
            confirm_head_label_tv.setText("您已以“"+refuseReason+"”为由拒绝住院申请，本院将近期致电联系您核实，请注意保持电话畅通");
        }else if(queryHospitalCureInfo.getState()==5){
            confirm_head_label_tv.setText("您已成功确定入院，请带上相关资料到入院服务中心报到");
        }else if(queryHospitalCureInfo.getState()==6||queryHospitalCureInfo.getState()==7){
            confirm_head_label_tv.setText("您已取消本次入院申请");
        }
    }

    @Override
    public void getHealthCureFailed(String errMsg) {
        ToastUtil.shortToast(getContext(),"入院单获取失败");
        finish();
    }

    @Override
    public void updateStatusSuccess() {
        ToastUtil.shortToast(getContext(),"提交成功");
        finish();
    }

    @Override
    public void updateStatusFailed(String errMsg) {
        ToastUtil.shortToast(getContext(),"提交失败："+errMsg);
    }

    @Override
    public void cancellationApplySuccess() {
        ToastUtil.shortToast(getContext(),"取消成功");
        finish();
    }

    @Override
    public void cancellationApplyFailed(String msg) {
        ToastUtil.shortToast(getContext(),"取消失败："+msg);
    }

    @Override
    public String getAdmissionNumber() {
        return queryHospitalCureInfo.getAdmissionNumber();
    }

    @Override
    public Map<String, Object> getApplyMap() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        Map<String,Object> map=new HashMap<>();
        map.put("hospitalId", App.hospitalId);
        map.put("userId",Global.getUserId());
        map.put("admissionNumber",HospitalCureInfo.getNumber());
        map.put("applyTime",dateString);
        map.put("name",HospitalCureInfo.getName());
        if(relationshiper_phone_edt.getText().toString().trim()==null||"".equals(relationshiper_phone_edt.getText().toString().trim())){
            ToastUtil.shortToast(getContext(),"请输入电话");
            return null;
        }else {
            if(PhoneUtils.isMobileEnable(relationshiper_phone_edt.getText().toString())){
                map.put("phoneNumber",order_phone_edt.getText().toString());
            }else {
                ToastUtil.shortToast(getContext(),"请填写正确的电话");
                return null;
            }

        }
        if(relationshiper_phone_edt.getText().toString().trim()==null||"".equals(relationshiper_phone_edt.getText().toString().trim())){
            ToastUtil.shortToast(getContext(),"请输入联系人电话");
            return null;
        }else {
            if(PhoneUtils.isMobileEnable(relationshiper_phone_edt.getText().toString())){
                map.put("contactPhone",relationshiper_phone_edt.getText().toString().trim());
            }else {
                ToastUtil.shortToast(getContext(),"请填写正确的联系人电话");
                return null;
            }

        }
        if(orderRegionName==null||"".equals(orderRegionName)){
            ToastUtil.shortToast(getContext(),"请选择居住地区");
            return null;
        }else {
            map.put("district",orderRegionName);
        }
        if(orderRegionCode==null||"".equals(orderRegionCode)){
            ToastUtil.shortToast(getContext(),"请选择居住地区");
            return null;
        }else {
            map.put("districtCode",orderRegionCode);
        }
        if(order_address_edt.getText().toString().trim()!=null&&!"".equals(order_address_edt.getText().toString().trim())){
            map.put("address",order_address_edt.getText().toString().trim());

        }else {
            ToastUtil.shortToast(getContext(),"请输入详细居住地址");
            return null;
        }
        map.put("admissionDept",HospitalCureInfo.getAdmissionDept());
        map.put("admissionTime",HospitalCureInfo.getIssuingTime());
        if(healthTypeCode!=-1){
            /*ToastUtil.shortToast(getContext(),"请选择医保类型");
            return null;*/
            map.put("healthType",healthTypeCode);
        }
        if(health_care_card_num.getText().toString().trim()!=null&&!"".equals(health_care_card_num.getText().toString().trim())){
            map.put("healthNumber",health_care_card_num.getText().toString().trim());

        }/*else {
            ToastUtil.shortToast(getContext(),"请输入医保卡号");
            return null;
        }*/

        if(healthCareRegionName==null||"".equals(healthCareRegionName)){
            /*ToastUtil.shortToast(getContext(),"请选择医保来源地");
            return null;*/
        }else {
            map.put("healthSource",healthCareRegionName);
        }
        if(healthCareRegionCode==null||"".equals(healthCareRegionCode)){
           /* ToastUtil.shortToast(getContext(),"请选择医保来源地");
            return null;*/
        }else {
            map.put("healthSourceCode",healthCareRegionCode);
        }

        if(SettlementTypeCode!=-1){
            map.put("settlementType",SettlementTypeCode);

        }

        if(order_note_edt.getText().toString().trim()==null||"".equals(order_note_edt.getText().toString().trim())){
            map.put("remark","");
        }else {
            map.put("remark",order_note_edt.getText().toString());
        }
        return map;
    }
}
