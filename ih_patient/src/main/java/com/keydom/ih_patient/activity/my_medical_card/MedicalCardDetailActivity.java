package com.keydom.ih_patient.activity.my_medical_card;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.my_medical_card.controller.MedicalCardDetailController;
import com.keydom.ih_patient.activity.my_medical_card.view.MedicalCardDetailView;
import com.keydom.ih_patient.bean.MedicalCardInfo;
import com.keydom.ih_patient.utils.DateUtils;
import com.orhanobut.logger.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 就诊卡详情页面
 */
public class MedicalCardDetailActivity extends BaseControllerActivity<MedicalCardDetailController> implements MedicalCardDetailView {
    private TextView card_detail_name,card_detail_card_num,card_detail_sex,card_detail_user_phone,card_detail_id_card_num,card_detail_bind_card_time,card_detail_real_name_status,card_detail_remove_bind,contactor_name_tv,contactor_phone_tv,contactor_relationship_tv;
    /**
     * 就诊卡实体
     */
    private MedicalCardInfo medicalCardInfo;

    /**
     * 启动
     */
    public static void start(Context context,MedicalCardInfo medicalCardInfo){
        Intent intent=new Intent(context,MedicalCardDetailActivity.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("medicalCardInfo",medicalCardInfo);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
    @Override
    public int getLayoutRes() {
        return R.layout.activity_medical_card_detail_layout;
    }

    @Override
    public void initData(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        Log.d("CARD","initData");
        setTitle(getString(R.string.medical_card_detail_title));
        getTitleLayout().initViewsVisible(true,true,false);
        contactor_name_tv=this.findViewById(R.id.contactor_name_tv);
        contactor_phone_tv=this.findViewById(R.id.contactor_phone_tv);
        contactor_relationship_tv=this.findViewById(R.id.contactor_relationship_tv);
        card_detail_name=this.findViewById(R.id.card_detail_name);
        card_detail_card_num=this.findViewById(R.id.card_detail_card_num);
        card_detail_sex=this.findViewById(R.id.card_detail_sex);
        card_detail_user_phone=this.findViewById(R.id.card_detail_user_phone);
        card_detail_id_card_num=this.findViewById(R.id.card_detail_id_card_num);
        card_detail_bind_card_time=this.findViewById(R.id.card_detail_bind_card_time);
        card_detail_real_name_status=this.findViewById(R.id.card_detail_real_name_status);
        card_detail_remove_bind=this.findViewById(R.id.card_detail_remove_bind);

        medicalCardInfo= (MedicalCardInfo) getIntent().getSerializableExtra("medicalCardInfo");
        card_detail_name.setText(medicalCardInfo.getName());
        card_detail_card_num.setText((medicalCardInfo.getEleCardNumber()==null||"".equals(medicalCardInfo.getEleCardNumber())?medicalCardInfo.getEntCardNumber():medicalCardInfo.getEleCardNumber()));
        card_detail_sex.setText(CommonUtils.getSex(medicalCardInfo.getSex()));
        card_detail_user_phone.setText(medicalCardInfo.getPhoneNumber());
        card_detail_id_card_num.setText(medicalCardInfo.getIdCard());
        contactor_name_tv.setText(medicalCardInfo.getContact()==null||"".equals(medicalCardInfo.getContact())?medicalCardInfo.getName():medicalCardInfo.getContact());
        contactor_phone_tv.setText(medicalCardInfo.getContactPhone()==null||"".equals(medicalCardInfo.getContactPhone())?medicalCardInfo.getPhoneNumber():medicalCardInfo.getContactPhone());
        contactor_relationship_tv.setText(medicalCardInfo.getRelationship()==null||"".equals(medicalCardInfo.getRelationship())?"本人":medicalCardInfo.getRelationship());

        String dateStr=medicalCardInfo.getBindTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date date=sdf.parse(dateStr);
            SimpleDateFormat sdfChange = new SimpleDateFormat("yyyy.MM.dd");
            dateStr=sdfChange.format(date);
            card_detail_bind_card_time.setText(dateStr);
            SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd");
            String nowDateStr=df.format(new Date());
            Logger.e("相差月份为："+ DateUtils.getMonthDiff(dateStr,nowDateStr));
            if(DateUtils.getMonthDiff(dateStr,nowDateStr)<Integer.parseInt(medicalCardInfo.getReleaseTime())){
                card_detail_remove_bind.setVisibility(View.GONE);
            }else {
                card_detail_remove_bind.setOnClickListener(getController());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(medicalCardInfo.getIsUnbind()==1){
            card_detail_remove_bind.setVisibility(View.GONE);
        }else if(medicalCardInfo.getIsUnbind()==0){
            card_detail_remove_bind.setVisibility(View.VISIBLE);
            card_detail_remove_bind.setOnClickListener(getController());
        }
        card_detail_real_name_status.setText(medicalCardInfo.getState()==1?"已实名认证":"未实名认证");
    }

    @Override
    public void removeBindSuccess() {
        ToastUtil.showMessage(getContext(),"解绑成功");
        finish();
    }

    @Override
    public void removeBindFailed(String msg) {
        ToastUtil.showMessage(getContext(),msg);
    }

    @Override
    public MedicalCardInfo getCardObject() {
        return medicalCardInfo;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
