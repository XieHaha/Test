package com.keydom.ih_common.utils.permissions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class ContextTarget implements Target {

    private Context mContext;

    public ContextTarget(Context context) {
        this.mContext = context;
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public void startActivity(Intent intent) {
        mContext.startActivity(intent);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        if (mContext instanceof Activity) ((Activity) mContext).startActivityForResult(intent, requestCode);
        else mContext.startActivity(intent);
    }
}
