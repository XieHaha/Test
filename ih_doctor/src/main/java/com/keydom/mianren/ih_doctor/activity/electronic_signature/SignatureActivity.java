package com.keydom.mianren.ih_doctor.activity.electronic_signature;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_doctor.MyApplication;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.electronic_signature.controller.ApplySignatureController;
import com.keydom.mianren.ih_doctor.activity.electronic_signature.view.ApplySignatureView;
import com.keydom.mianren.ih_doctor.bean.MessageEvent;
import com.keydom.mianren.ih_doctor.bean.SignRegInfoBean;
import com.keydom.mianren.ih_doctor.constant.EventType;
import com.keydom.mianren.ih_doctor.m_interface.SingleClick;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

import cn.org.bjca.signet.component.core.activity.SignetCoreApi;
import cn.org.bjca.signet.component.core.bean.results.FindBackUserResult;
import cn.org.bjca.signet.component.core.bean.results.UserStateResult;
import cn.org.bjca.signet.component.core.callback.CheckStateCallBack;
import cn.org.bjca.signet.component.core.callback.FindBackUserCallBack;
import cn.org.bjca.signet.component.core.enums.IdCardType;

/**
 * @link Author: song
 * <p>
 * Create: 19/3/6 下午3:48
 * <p>
 * Changes (from 19/3/6)
 * <p>
 * 19/3/6 : Create InspectionDetailActivity.java (song);
 */
public class SignatureActivity extends BaseControllerActivity<ApplySignatureController> implements ApplySignatureView {


    private TextView right_btn_tv, sign_tip_tv, user_number_tv, code_tv;
    private ImageView qr_iv, sign_tip_iv;
    private SignRegInfoBean info;
    private boolean isCart = false;

    /**
     * 启动电子签名设置页面
     *
     * @param context
     */
    public static void start(Context context) {
        Intent intent = new Intent(context, SignatureActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_sign_layout;
    }

    private void initView() {
        right_btn_tv = findViewById(R.id.right_btn_tv);
        sign_tip_tv = findViewById(R.id.sign_tip_tv);
        user_number_tv = findViewById(R.id.user_number_tv);
        code_tv = findViewById(R.id.code_tv);
        sign_tip_iv = findViewById(R.id.sign_tip_iv);
        qr_iv = findViewById(R.id.qr_iv);
        right_btn_tv.setOnClickListener(new View.OnClickListener() {
            @SingleClick(1000)
            @Override
            public void onClick(View v) {
                if (!isCart) {
                    SignetCoreApi.useCoreFunc(new FindBackUserCallBack(SignatureActivity.this, info.getName(), info.getIdCard(), IdCardType.SF) {

                        @Override
                        public void onFindBackResult(FindBackUserResult result) {
                            updateSignStatus();
                        }
                    });
                } else {
                    ChangeSignatureActivity.start(SignatureActivity.this, info.getMsspId());
                }
            }
        });

    }

    private void setInfo() {
        user_number_tv.setText(info.getMsspId());
        code_tv.setText(info.getAuthCode());
        Bitmap mBitmap = CodeUtils.createImage(info.getQrCode(), 400, 400, null);
        qr_iv.setImageBitmap(mBitmap);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("电子签名");
        initView();
        EventBus.getDefault().register(this);
        getController().getUserElectronicsInfo();
        updateSignStatus();
    }

    private void updateSignStatus() {
        SignetCoreApi.useCoreFunc(new CheckStateCallBack(SignatureActivity.this, MyApplication.userInfo.getMsspId()) {
            @Override
            public void onCheckKeyStateResult(UserStateResult userStateResult) {
                if (userStateResult != null&&userStateResult.getUserStateCode()!=null) {
                    switch (userStateResult.getUserStateCode()) {
                        case "0":
                            isCart = true;
                            break;
                        case "0x81200003"://云服务平台内有此用户但用户未激活过证书 引导用户注册并生成证书
                        case "0x14300001"://用户已激活但在设备上无证书 引导用户找回证书
                        case "0x81200006"://用户已激活且在设备上有证书但证书被锁定 引导用户等待或找回证书
                        case "0x8120000B"://用户已激活且在设备上有证书但证书已废止 引导用户找回证书
                        default:
                            isCart = false;
                            ToastUtil.showMessage(getContext(), userStateResult.getErrMsg());
                            break;
                    }
                }
                if (isCart) {
                    sign_tip_tv.setText("您已激活电子签名");
                    right_btn_tv.setText("去变更");
                    sign_tip_iv.setVisibility(View.VISIBLE);
                } else {
                    sign_tip_tv.setText("请现在去激活，并设置签名口令，获取证书。");
                    right_btn_tv.setText("去激活");
                    sign_tip_iv.setVisibility(View.INVISIBLE);
                }
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
        info = data;
        setInfo();
    }

    @Override
    public void getUserFailed(String errMsg) {
        ToastUtil.showMessage(SignatureActivity.this, errMsg);
        finish();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        if (messageEvent.getType() == EventType.UPDATE_SIGN_STATUS) {
            updateSignStatus();
        }
    }
}
