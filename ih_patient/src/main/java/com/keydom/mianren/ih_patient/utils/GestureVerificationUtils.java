package com.keydom.mianren.ih_patient.utils;

import android.content.Context;

import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.mianren.ih_patient.activity.setting.GestureUnlockActivity;
import com.keydom.mianren.ih_patient.constant.Global;
import com.keydom.mianren.ih_patient.net.UserService;

import org.jetbrains.annotations.Nullable;

public class GestureVerificationUtils {
    //手势密码验证公用方法
    public static void isGesturePassed(Context context,String recordType){
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).getPassword(Global.getUserId()), new HttpSubscriber<String>(context) {
            @Override
            public void requestComplete(@Nullable String data) {
                if(data==null){
                    //创建手势密码
                    CreatGesture(context,recordType);
                }else
                    //验证手势密码
                    validateGesture(context,data,recordType);
            }
        });
    }
    //创建手势密码
    private static void CreatGesture(Context context, String recordType){
        new GeneralDialog(context, "您还未设置手势密码，立即前去设置？", new GeneralDialog.OnCloseListener() {
            @Override
            public void onCommit() {
                GestureUnlockActivity.start(context,GestureUnlockActivity.CREATETYPE,"",recordType);
            }
        }).setTitle("提示").setPositiveButton("确认").show();
    }
    //验证手势密码
    private static  void validateGesture(Context context, String gesturePassword, String recordType){
        GestureUnlockActivity.start(context,GestureUnlockActivity.VALIDATETYPE,gesturePassword,recordType);

    }
}
