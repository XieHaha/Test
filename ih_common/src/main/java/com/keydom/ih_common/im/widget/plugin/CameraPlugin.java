package com.keydom.ih_common.im.widget.plugin;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.keydom.ih_common.R;
import com.keydom.ih_common.im.config.ImConstants;
import com.keydom.ih_common.im.listener.IPluginModule;
import com.keydom.ih_common.im.utils.AlbumUtilKt;
import com.keydom.ih_common.im.utils.WithAspectRatio;
import com.keydom.ih_common.im.widget.ImExtension;

public class CameraPlugin implements IPluginModule {
    @Override
    public Drawable obtainDrawable(Context context) {
        return ContextCompat.getDrawable(context, R.mipmap.ic_launcher);
    }

    @Override
    public String obtainTitle(Context context) {
        return "拍摄";
    }

    @Override
    public void onClick(AppCompatActivity activity, ImExtension extension) {
        int position = extension.getPluginAdapter().getPluginPosition(this);
        int requestCode = ImConstants.CAMERA_REQUEST;
        AlbumUtilKt.gotoPhotoPicker(activity, true, 1, true, false, false, new WithAspectRatio(1, 1), false, null, (position + 1 << 8) + (requestCode & 255), null, true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }
}
