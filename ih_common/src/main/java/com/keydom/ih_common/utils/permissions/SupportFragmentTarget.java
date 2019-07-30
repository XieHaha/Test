package com.keydom.ih_common.utils.permissions;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class SupportFragmentTarget implements Target {

    private Fragment mFragment;

    public SupportFragmentTarget(Fragment fragment) {
        this.mFragment = fragment;
    }

    @Override
    public Context getContext() {
        return mFragment.getContext();
    }

    @Override
    public void startActivity(Intent intent) {
        mFragment.startActivity(intent);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        mFragment.startActivityForResult(intent, requestCode);
    }
}
