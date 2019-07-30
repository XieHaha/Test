package com.keydom.ih_common.im.listener;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;

import com.keydom.ih_common.im.widget.ImExtension;

public interface IPluginModule {
    Drawable obtainDrawable(Context context);

    String obtainTitle(Context context);

    void onClick(AppCompatActivity activity, ImExtension extension);

    void onActivityResult(int requestCode, int resultCode, Intent data);
}
