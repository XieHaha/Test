package com.keydom.mianren.ih_patient.utils.pay.weixin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.keydom.mianren.ih_patient.R;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;


/**
 * 微信回调页
 */
public class WXCallbackActivity extends Activity implements IWXAPIEventHandler {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxpay_call_back);

        if (WXInit.getInstance() != null) {
            WXInit.getInstance().getWXApi().handleIntent(getIntent(), this);
        } else {
            finish();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        if (WXInit.getInstance() != null) {
            WXInit.getInstance().getWXApi().handleIntent(intent, this);
        }
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (WXInit.getInstance() != null) {
                if (baseResp.errStr != null) {
                    Log.e("wxpay", "errstr=" + baseResp.errStr);
                }

                WXInit.getInstance().onResp(baseResp.errCode);
                finish();
            }
        }
        if (baseResp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {
            if (WXInit.getInstance() != null) {
                WXInit.getInstance().onLoginResp(baseResp);
                finish();
            }
        }
    }
}
