package com.keydom.ih_patient.activity.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.SharePreferenceManager;
import com.keydom.ih_common.view.ClearEditText;
import com.keydom.ih_common.view.MButton;
import com.keydom.ih_common.view.MRadioButton;
import com.keydom.ih_common.view.PasswordEditText;
import com.keydom.ih_patient.App;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.index_main.MainActivity;
import com.keydom.ih_patient.activity.login.controller.RegisterController;
import com.keydom.ih_patient.activity.login.view.IRegisterView;
import com.keydom.ih_patient.bean.Event;
import com.keydom.ih_patient.bean.UserInfo;
import com.keydom.ih_patient.constant.EventType;
import com.keydom.ih_patient.constant.Global;
import com.keydom.ih_patient.utils.LocalizationUtils;
import com.keydom.ih_patient.utils.ToastUtil;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

/**
 * 注册页面
 */
public class RegisterActivity extends BaseControllerActivity<RegisterController> implements IRegisterView {
    public static final String TYPE = "type";
    public static final String UID = "uid";
    private String mUid;
    private int mType;
    private boolean isBind = false;
    private LinearLayout registStepFirst,registStepSecond,registStepComplete;
    private ClearEditText phoneNumCedt;
    private MButton getIdentifyingCodeBt;
    private Button registerStepFirstNextBtn,registerStepSecondNextBtn;
    private EditText accoutEdt,messageCodeEdt;
    private PasswordEditText setPasswordPedt,resetPasswordPedt;
    private MRadioButton agreementRb;
    private TextView fillPersonalTv,completeRegistTv,jump_to_user_agreement_tv;

    /**
     * 启动方法
     */
    public static void start(Context context) {
        Intent starter = new Intent(context, RegisterActivity.class);//.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(starter);
    }
    @Override
    public int getLayoutRes() {
        return R.layout.activity_register_layout;
    }
    @Override
    public void initData(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        mType = getIntent().getIntExtra(TYPE,0);
        mUid = getIntent().getStringExtra(UID);
        setTitle(getString(R.string.regist_title));
        getTitleLayout().initViewsVisible(false,true,false);
        registStepFirst=this.findViewById(R.id.regist_step_1_layout);
        registStepSecond = this.findViewById(R.id.regist_step_2_layout);
        registStepComplete=this.findViewById(R.id.regist_step_complete_layout);
        phoneNumCedt= this.findViewById(R.id.phone_num_cedt);
        messageCodeEdt=this.findViewById(R.id.message_code_cedt);
        jump_to_user_agreement_tv=this.findViewById(R.id.jump_to_user_agreement_tv);
        jump_to_user_agreement_tv.setOnClickListener(getController());
        getIdentifyingCodeBt=this.findViewById(R.id.get_identifying_code_bt);
        registerStepFirstNextBtn=this.findViewById(R.id.register_next_btn);
        registerStepSecondNextBtn=this.findViewById(R.id.next_step);
        accoutEdt=this.findViewById(R.id.accout_edt);
        setPasswordPedt =this.findViewById(R.id.set_password_pedt);
        resetPasswordPedt=this.findViewById(R.id.reset_password_pedt);
        agreementRb=this.findViewById(R.id.agreement_rb);
        fillPersonalTv=this.findViewById(R.id.fill_personal_tv);
        completeRegistTv=this.findViewById(R.id.complete_regist_tv);
        registerStepFirstNextBtn.setOnClickListener(getController());
        registerStepSecondNextBtn.setOnClickListener(getController());
        getIdentifyingCodeBt.setOnClickListener(getController());
        fillPersonalTv.setOnClickListener(getController());
        completeRegistTv.setOnClickListener(getController());
        agreementRb.setOnCheckedChangeListener(getController());
    }

    @Override
    public void msgInspectSuccess() {
        registStepFirst.setVisibility(View.GONE);
        registStepSecond.setVisibility(View.VISIBLE);
    }

    @Override
    public void msgInspectFailed(String msg) {
        ToastUtil.shortToast(this,msg);

    }

    @Override
    public void registerSuccess(UserInfo data) {
        App.userInfo=data;
        LocalizationUtils.fileSave2Local(getContext(),data,"userInfo");
        Global.setUserId(data.getId());
        SharePreferenceManager.setToken("User"+data.getToken());
        EventBus.getDefault().post(new Event(EventType.UPDATELOGINSTATE,null));
        Logger.e("savedUserId=="+Global.getUserId());
        registStepSecond.setVisibility(View.GONE);
        registStepComplete.setVisibility(View.VISIBLE);
    }

    @Override
    public void registerFailed(String msg) {
        ToastUtil.shortToast(this,msg);
    }

    @Override
    public void getMsgCodeSuccess() {
        ToastUtil.shortToast(this,"验证码已发送，请注意查看");
        getIdentifyingCodeBt.startTimer();
    }

    @Override
    public void getMsgCodeFailed(String errMsg) {
        ToastUtil.shortToast(this,errMsg);
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
    public String getAccount() {
        return accoutEdt.getText().toString().trim();
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
    public void Finish() {
        finish();
    }

    @Override
    public int getType() {
        return mType;
    }

    @Override
    public String getUid() {
        return mUid;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(registStepComplete.getVisibility()==View.VISIBLE){
                MainActivity.start(getContext(),false);
                return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void bindPhone() {
        isBind = true;
    }

    @Override
    public boolean isBind() {
        return isBind;
    }

}
