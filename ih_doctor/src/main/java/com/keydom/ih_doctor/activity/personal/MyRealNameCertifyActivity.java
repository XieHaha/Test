package com.keydom.ih_doctor.activity.personal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.widget.EditText;

import com.blankj.utilcode.util.RegexUtils;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.PhoneUtils;
import com.keydom.ih_common.utils.SharePreferenceManager;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.MButton;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.personal.controller.MyRealNameCertifyController;
import com.keydom.ih_doctor.activity.personal.view.MyRealNameCertifyView;

import java.util.HashMap;
import java.util.Map;

/**
 * @Name：com.keydom.ih_doctor.activity.personal
 * @Description：我的实名页面
 * @Author：song
 * @Date：18/11/14 下午3:05
 * 修改人：xusong
 * 修改时间：18/11/14 下午3:05
 */
public class MyRealNameCertifyActivity extends BaseControllerActivity<MyRealNameCertifyController> implements MyRealNameCertifyView {


    private EditText phoneEt, codeEt, nameEt, idCardEt;
    private MButton getIdentifyingCodeBt;

    /**
     * 启动
     */
    public static void start(Context context, int requestCode) {
        Intent starter = new Intent(context, MyRealNameCertifyActivity.class);
        ((Activity) context).startActivityForResult(starter, requestCode);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_real_name_certify_layout;
    }


    @Override
    public void initData(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        setTitle("实名认证");
        setRightTxt("提交");
        setRightBtnListener(getController());
        phoneEt = this.findViewById(R.id.phone_et);
        codeEt = this.findViewById(R.id.code_et);
        nameEt = this.findViewById(R.id.name_et);
        idCardEt = this.findViewById(R.id.id_card_et);
        getIdentifyingCodeBt = this.findViewById(R.id.get_identifying_code_bt);
        getIdentifyingCodeBt.setOnClickListener(getController());
        phoneEt.setText(SharePreferenceManager.getPhoneNumber());
    }

    @Override
    public Map<String, Object> getRealNameMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("code", codeEt.getText().toString().trim());
        map.put("idCard", idCardEt.getText().toString().trim());
        map.put("name", nameEt.getText().toString().trim());
        map.put("phoneNumber", phoneEt.getText().toString().trim());
        return map;
    }

    @Override
    public Map<String, Object> getCodeMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("phoneNumber", phoneEt.getText().toString().trim());
        return map;
    }

    @Override
    public void realNameSuccess(String msg) {
        SharePreferenceManager.setAutonyState(1);
        ToastUtil.showMessage(this, "认证成功");
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void realNameFailed(String errMsg) {
        ToastUtil.showMessage(this, errMsg);
    }

    @Override
    public void getCodeSuccess(String msg) {
        getIdentifyingCodeBt.startTimer();
        ToastUtil.showMessage(this, "验证码发送成功");
    }

    @Override
    public void getCodeFailed(String msg) {
        ToastUtil.showMessage(this, msg);
    }

    @Override
    public boolean checkPhone() {
        if (!PhoneUtils.isMobileEnable(phoneEt.getText().toString())) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("无法获取");
            builder.setMessage("请输入正确的手机号！");
            builder.create().show();
            return false;
        }
        return true;
    }

    @Override
    public boolean checkRealMap() {

        if ("".equals(nameEt.getText().toString().trim())) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("无法提交");
            builder.setMessage("请输入正确的姓名！");
            builder.create().show();
            return false;
        }

        if (!PhoneUtils.isMobileEnable(phoneEt.getText().toString())) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("无法提交");
            builder.setMessage("请输入正确的手机号！");
            builder.create().show();
            return false;
        }
        if (!RegexUtils.isIDCard15(idCardEt.getText().toString().trim()) && !RegexUtils.isIDCard18(idCardEt.getText().toString().trim())) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("无法提交");
            builder.setMessage("请输入正确的身份证号！");
            builder.create().show();
            return false;
        }
        return true;
    }

    @Override
    public String getIdCard() {
        if(null != idCardEt && !TextUtils.isEmpty(idCardEt.getText().toString().trim())){
            return idCardEt.getText().toString().trim();
        }
        return "";
    }
}
