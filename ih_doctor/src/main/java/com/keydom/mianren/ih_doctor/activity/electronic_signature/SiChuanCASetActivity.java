package com.keydom.mianren.ih_doctor.activity.electronic_signature;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.constant.Const;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_common.utils.SharePreferenceManager;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_doctor.MyApplication;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.electronic_signature.controller.SiChuanCASetController;
import com.keydom.mianren.ih_doctor.activity.electronic_signature.view.SiChuanCASetView;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * ca设置
 */
public class SiChuanCASetActivity extends BaseControllerActivity<SiChuanCASetController> implements SiChuanCASetView {
    TextView setSignTipTv;
    ImageView setSignDeleteIv;
    LinearLayout setSignTipsLayout;
    EditText setSignVerifyCodeEt;
    TextView setSignVerifySendTv;
    EditText setSignPdEt;
    EditText setSignPdTwoEt;
    TextView setSignNextTv;

    private String verifyCode, signCode, signVerifyCode;
    private boolean isSign;

    /**
     * 启动电子签名设置页面
     */
    public static void start(Context context, boolean reset, int resultCode) {
        Intent intent = new Intent(context, SiChuanCASetActivity.class);
        intent.putExtra(Const.DATA, reset);
        ((Activity) context).startActivityForResult(intent, resultCode);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_sichuan_ca_set;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("电子签章");

        setSignTipTv = findViewById(R.id.set_sign_tip_tv);
        setSignDeleteIv = findViewById(R.id.set_sign_delete_iv);
        setSignTipsLayout = findViewById(R.id.set_sign_tips_layout);
        setSignVerifyCodeEt = findViewById(R.id.set_sign_verify_code_et);
        setSignVerifySendTv = findViewById(R.id.set_sign_verify_send_tv);
        setSignPdEt = findViewById(R.id.set_sign_pd_et);
        setSignPdTwoEt = findViewById(R.id.set_sign_pd_two_et);
        setSignNextTv = findViewById(R.id.set_sign_next_tv);

        isSign = getIntent().getBooleanExtra(Const.DATA, false);
        if (!TextUtils.isEmpty(SharePreferenceManager.getPhoneNumber())) {
            getController().sendCode();
        }
        setSignVerifySendTv.setOnClickListener(getController());
        setSignNextTv.setOnClickListener(getController());

        setSignVerifyCodeEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                verifyCode = s.toString().trim();
                initButtonAble();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        setSignPdEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                signCode = s.toString().trim();
                initButtonAble();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        setSignPdTwoEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                signVerifyCode = s.toString().trim();
                initButtonAble();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 按钮处理
     */
    private void initButtonAble() {
        if (TextUtils.isEmpty(verifyCode) || TextUtils.isEmpty(signCode) || TextUtils.isEmpty(signVerifyCode)) {
            setSignNextTv.setSelected(false);
        } else {
            setSignNextTv.setSelected(true);
        }
    }

    @Override
    public boolean enable() {
        if (setSignNextTv.isSelected()) {
            if (!signCode.equals(signVerifyCode)) {
                ToastUtil.showMessage(this, "两次输入口令不一致");
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isSign() {
        return isSign;
    }

    @Override
    public Map<String, Object> getApplyMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", MyApplication.userInfo.getName());
        map.put("mobile", SharePreferenceManager.getPhoneNumber());
        map.put("verificationCode", verifyCode);
        map.put("idNum", SharePreferenceManager.getIdCard());
        map.put("signParam", "{x: 300,y: 100,imgWidth: 200," + "imgHeight: 200,pageNo: 1}");
        map.put("pin", signCode);
        return map;
    }

    @Override
    public Map<String, Object> getResetPinMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("mobile", SharePreferenceManager.getPhoneNumber());
        map.put("verificationCode", verifyCode);
        map.put("pin", signCode);
        return map;
    }

    @Override
    public void applySuccess(String result) {
        ToastUtil.showMessage(this, result);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void applyFailed(String errMsg) {
        ToastUtil.showMessage(this, errMsg);
    }

    @Override
    public void getIdentifyingCodeSuccess() {
        setSignTipTv.setText("已发送验证码到您的手机：" + CommonUtils.getStarString(SharePreferenceManager.getPhoneNumber(), 3, 6));
        startTimer();
    }

    @Override
    public void getIdentifyingCodeFailed(String errMsg) {
        ToastUtil.showMessage(this, errMsg);
    }

    public void startTimer() {
        CountDownTimer timer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                setSignVerifySendTv.setText(millisUntilFinished / 1000 + "s");
            }

            @Override
            public void onFinish() {
                setSignVerifySendTv.setEnabled(true);
                setSignVerifySendTv.setText("重发验证码");
            }
        }.start();
    }
}
