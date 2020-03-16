package com.keydom.mianren.ih_doctor.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.keydom.ih_common.base.BaseActivity;
import com.keydom.mianren.ih_doctor.MyApplication;
import com.keydom.mianren.ih_doctor.R;

import cn.org.bjca.signet.component.core.activity.SignetCoreApi;
import cn.org.bjca.signet.component.core.bean.results.FindBackUserResult;
import cn.org.bjca.signet.component.core.bean.results.LoginResult;
import cn.org.bjca.signet.component.core.bean.results.SignDataResult;
import cn.org.bjca.signet.component.core.callback.FindBackUserCallBack;
import cn.org.bjca.signet.component.core.callback.LoginCallBack;
import cn.org.bjca.signet.component.core.callback.SignDataCallBack;
import cn.org.bjca.signet.component.core.enums.IdCardType;

public class TestActivity extends BaseActivity {

    private Button back, login, sign;

    public static void start(Context context) {
        Intent starter = new Intent(context, TestActivity.class);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.test;
    }


    @Override
    public void initData(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        back = findViewById(R.id.back);
        login = findViewById(R.id.login);
        sign = findViewById(R.id.sign);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = "叶仕川";
                String cardNumber = "511502198801275176";
                IdCardType cardType = IdCardType.SF;
                SignetCoreApi.useCoreFunc(new FindBackUserCallBack(TestActivity.this, name, cardNumber, cardType) {

                    @Override
                    public void onFindBackResult(FindBackUserResult result) {
                        Log.e("TEST", result.getErrMsg());

                    }
                });
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String signId = "SD_47bae105-462f-4da8-8c44-00f015330d4e";
                String msspID = MyApplication.userInfo.getMsspId();
//                String msspID = "72dae2a41aa18bd5d4ffd9c2262b1f303bff95b52f19f95f0268185b143df498";
                SignetCoreApi.useCoreFunc(new LoginCallBack(TestActivity.this, msspID, signId) {

                    @Override
                    public void onLoginResult(LoginResult loginResult) {
                        Log.e("TEST", loginResult.getErrMsg());
                    }
                });

            }
        });

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msspID = MyApplication.userInfo.getMsspId();
//                String msspID = "72dae2a41aa18bd5d4ffd9c2262b1f303bff95b52f19f95f0268185b143df498";
                String signId = "SD_47bae105-462f-4da8-8c44-00f015330d4e";
                SignetCoreApi.useCoreFunc(new SignDataCallBack(TestActivity.this, msspID, signId) {
                    @Override
                    public void onSignDataResult(SignDataResult result) {
                        Log.e("TEST", result.getErrMsg());
                    }
                });

            }
        });

    }

}
