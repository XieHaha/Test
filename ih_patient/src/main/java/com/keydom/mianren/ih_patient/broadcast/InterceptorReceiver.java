package com.keydom.mianren.ih_patient.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.mianren.ih_patient.activity.login.LoginActivity;

public class InterceptorReceiver extends BroadcastReceiver {
    private static GeneralDialog generalDialog;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (generalDialog == null) {
            generalDialog = new GeneralDialog(context, "登陆信息已经失效，请重新登陆！", new GeneralDialog.OnCloseListener() {
                @Override
                public void onCommit() {
                    LoginActivity.start(context);
                }
            }).setTitle("提示").setNegativeButtonIsGone(true).setCancel(false);
        }
        generalDialog.show();

    }
}
