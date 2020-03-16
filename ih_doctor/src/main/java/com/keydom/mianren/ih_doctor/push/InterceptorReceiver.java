package com.keydom.mianren.ih_doctor.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.WindowManager;

import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.mianren.ih_doctor.activity.LoginActivity;
import com.orhanobut.logger.Logger;

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
            generalDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        }
        generalDialog.show();
        Logger.e("收到广播");

    }
}
