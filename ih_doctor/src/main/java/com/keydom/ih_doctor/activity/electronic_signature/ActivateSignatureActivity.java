package com.keydom.ih_doctor.activity.electronic_signature;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.electronic_signature.controller.ApplySignatureController;
import com.keydom.ih_doctor.activity.electronic_signature.view.ApplySignatureView;
import com.keydom.ih_doctor.bean.SignRegInfoBean;
import com.keydom.ih_doctor.constant.Const;
import com.keydom.ih_doctor.m_interface.SingleClick;

import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * @link Author: song
 * <p>
 * Create: 19/3/6 下午3:48
 * <p>
 * Changes (from 19/3/6)
 * <p>
 * 19/3/6 : Create InspectionDetailActivity.java (song);
 */
public class ActivateSignatureActivity extends BaseControllerActivity<ApplySignatureController> implements ApplySignatureView {



    /**
     * 启动电子签名设置页面
     *
     * @param context
     */
    public static void start(Context context, SignRegInfoBean regInfoBean) {
        Intent intent = new Intent(context, ActivateSignatureActivity.class);
        intent.putExtra(Const.DATA, regInfoBean);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_activate_sign_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("激活");
        setRightTxt("提交");
        setRightBtnListener(new IhTitleLayout.OnRightTextClickListener() {
            @SingleClick(1000)
            @Override
            public void OnRightTextClick(View v) {
                ToastUtil.showMessage(ActivateSignatureActivity.this, "提交");
            }
        });
    }

    @Override
    public Map<String, Object> getApplyMap() {
        return null;
    }

    @Override
    public void applySuccess(String result) {

    }

    @Override
    public void applyFailed(String errMsg) {

    }

    @Override
    public void getIdentifyingCodeSuccess() {

    }

    @Override
    public void getIdentifyingCodeFailed(String errMsg) {

    }

    @Override
    public void getUserSuccess(SignRegInfoBean data) {

    }

    @Override
    public void getUserFailed(String errMsg) {

    }

    @Override
    public void changeMobileSuccess(String msg) {

    }

    @Override
    public void changeMobileFailed(String errMsg) {

    }

    @Override
    public void addJobIdSuccess(String msg) {

    }

    @Override
    public void addJobIdFailed(String errMsg) {

    }

}
