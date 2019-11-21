package com.keydom.ih_patient.activity.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.ClearEditText;
import com.keydom.ih_common.view.MButton;
import com.keydom.ih_common.view.PasswordEditText;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.login.controller.UpdatePasswordController;
import com.keydom.ih_patient.activity.login.view.UpdatePasswordView;

/**
 * 更新密码页面
 */
public class UpdatePasswordActivity extends BaseControllerActivity<UpdatePasswordController> implements UpdatePasswordView {
    private LinearLayout registStepFirst,registStepSecond,registStepComplete;
    private ClearEditText phoneNumCedt;
    private MButton getCodeBt;
    private Button StepFirstNextBtn,StepSecondNextBtn;
    private EditText messageCodeEdt;
    private PasswordEditText setPasswordPedt,resetPasswordPedt;
    private TextView completeRegistTv;

    /**
     * 启动方法
     */
    public static void start(Context context) {
        Intent starter = new Intent(context, UpdatePasswordActivity.class);//.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(starter);
    }
    @Override
    public int getLayoutRes() {
        return R.layout.activity_update_password_layout;
    }
    public void initData(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        setTitle("修改密码");
        getTitleLayout().initViewsVisible(false,true,false);
        registStepFirst=this.findViewById(R.id.regist_step_1_layout);
        registStepSecond = this.findViewById(R.id.regist_step_2_layout);
        registStepComplete=this.findViewById(R.id.regist_step_complete_layout);
        phoneNumCedt= this.findViewById(R.id.phone_num_cedt);
        messageCodeEdt=this.findViewById(R.id.message_code_cedt);
        getCodeBt=this.findViewById(R.id.get_code_bt);
        getCodeBt.setOnClickListener(getController());
        StepFirstNextBtn=this.findViewById(R.id.register_next_btn);
        StepSecondNextBtn=this.findViewById(R.id.next_step);
        setPasswordPedt =this.findViewById(R.id.set_password_pedt);
        resetPasswordPedt=this.findViewById(R.id.reset_password_pedt);
        completeRegistTv=this.findViewById(R.id.complete_regist_tv);
        StepFirstNextBtn.setOnClickListener(getController());
        StepSecondNextBtn.setOnClickListener(getController());
        completeRegistTv.setOnClickListener(getController());
    }

    @Override
    public void msgInspectSuccess() {
        registStepFirst.setVisibility(View.GONE);
        registStepSecond.setVisibility(View.VISIBLE);
    }

    @Override
    public void msgInspectFailed(String msg) {
        ToastUtil.showMessage(this,msg);

    }

    @Override
    public void registerSuccess() {
        registStepSecond.setVisibility(View.GONE);
        registStepComplete.setVisibility(View.VISIBLE);
    }

    @Override
    public void registerFailed(String msg) {
        ToastUtil.showMessage(this,msg);
    }

    @Override
    public void getMsgCodeSuccess() {
        ToastUtil.showMessage(this,"验证码已发送，请注意查看");
        getCodeBt.startTimer();
    }

    @Override
    public void getMsgCodeFailed(String errMsg) {
        ToastUtil.showMessage(this,errMsg);
    }

    @Override
    public String getPhoneNum() {
        return phoneNumCedt.getText().toString().trim();
    }

    @Override
    public String getMsgCode() {
        return messageCodeEdt.getText().toString().trim();
    }


    @Override
    public String getPassWord() {
        return setPasswordPedt.getText().toString().trim();
    }

    @Override
    public String getRePassWord() {
        return resetPasswordPedt.getText().toString().trim();
    }

    @Override
    public void completeUpdate() {
        finish();
    }
}
