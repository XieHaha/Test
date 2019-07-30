package com.keydom.ih_common.utils.permissions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class AppActivityTarget implements Target {

    private Activity mActivity;

    public AppActivityTarget(Activity activity) {
        this.mActivity = activity;
    }

    @Override
    public Context getContext() {
        return mActivity;
    }

    @Override
    public void startActivity(Intent intent) {
        mActivity.startActivity(intent);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        mActivity.startActivityForResult(intent, requestCode);
    }
}
