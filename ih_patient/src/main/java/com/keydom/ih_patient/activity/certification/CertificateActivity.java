package com.keydom.ih_patient.activity.certification;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.view.MButton;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.certification.controller.CertificateController;
import com.keydom.ih_patient.activity.certification.view.CertificateView;
import com.keydom.ih_patient.bean.Event;
import com.keydom.ih_patient.constant.EventType;
import com.keydom.ih_patient.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.Nullable;
/**
*@Author: LiuJie
*@Date: 2019/3/4 0004
*@Desc: 短信验证和身份证实名认证页面
*/
public class CertificateActivity extends BaseControllerActivity<CertificateController> implements CertificateView {
    private LinearLayout id_card_certificate_layout,phone_certificate_layout;
    //启动类型 phone_certificate 短信验证  id_card_certificate 实名认证
    private String type;
    private EditText id_card_name_edt,id_card_num_edt,phone_num_edt,message_edt;
    private MButton get_message_bt;

    /**
     * 启动页面
     */
    public static void start(Context context,String type){
        Intent intent=new Intent(context,CertificateActivity.class);
        intent.putExtra("type",type);
        context.startActivity(intent);
    }
    @Override
    public int getLayoutRes() {
        return R.layout.activity_certificate_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        type=getIntent().getStringExtra("type");
        getController().getType(type);
        getTitleLayout().initViewsVisible(true,true,true);
        getTitleLayout().setRightTitle("提交");
        id_card_certificate_layout=this.findViewById(R.id.id_card_certificate_layout);
        phone_certificate_layout=this.findViewById(R.id.phone_certificate_layout);
        id_card_name_edt=this.findViewById(R.id.id_card_name_edt);
        id_card_num_edt=this.findViewById(R.id.id_card_num_edt);
        phone_num_edt=this.findViewById(R.id.phone_num_edt);
        message_edt=this.findViewById(R.id.message_edt);
        get_message_bt=this.findViewById(R.id.get_message_bt);
        if(type.equals("phone_certificate")){
            setTitle("短信验证");
            id_card_certificate_layout.setVisibility(View.GONE);
            phone_certificate_layout.setVisibility(View.VISIBLE);
        }else {
            setTitle("身份证验证");
            id_card_certificate_layout.setVisibility(View.VISIBLE);
            phone_certificate_layout.setVisibility(View.GONE);
        }
        get_message_bt.setOnClickListener(getController());
        getTitleLayout().setOnRightTextClickListener(getController());

    }

    @Override
    public void msgInspectSuccess() {
        Event event=new Event(EventType.PHONECERTIFICATESUCCESS,phone_num_edt.getText().toString().trim());
        EventBus.getDefault().post(event);
        finish();
    }

    @Override
    public void msgInspectFailed(String msg) {
        ToastUtil.shortToast(this,msg);
    }

    @Override
    public void getMsgCodeSuccess() {
        get_message_bt.startTimer();
        ToastUtil.shortToast(this,"验证码已发送，注意查看");
    }

    @Override
    public void getMsgCodeFailed(String errMsg) {
        ToastUtil.shortToast(this,errMsg);
    }

    @Override
    public void idCardCertificateSuccess() {
        Event event=new Event(EventType.IDCARDCERTIFICATESUCCESS,null);
        EventBus.getDefault().post(event);
        finish();
    }

    @Override
    public void idCardCertificateFailed(String errMsg) {
        ToastUtil.shortToast(this,errMsg);
    }

    @Override
    public String getName() {
        return id_card_name_edt.getText().toString();
    }

    @Override
    public String getIdCardNum() {
        return id_card_num_edt.getText().toString().trim();
    }

    @Override
    public String getPhoneNum() {
        return phone_num_edt.getText().toString().trim();
    }

    @Override
    public String getMessageCode() {
        return message_edt.getText().toString().trim();
    }
}
