package com.keydom.ih_common.im.widget.plugin;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.keydom.ih_common.R;
import com.keydom.ih_common.im.listener.IPluginModule;
import com.keydom.ih_common.im.widget.ImExtension;

public class EndInquiryPlugin implements IPluginModule {
    private PluginListener mPluginListener;

    public EndInquiryPlugin(PluginListener pluginListener) {
        mPluginListener = pluginListener;
    }

    @Override
    public Drawable obtainDrawable(Context context) {
        return ContextCompat.getDrawable(context, R.mipmap.im_plugin_end);
    }

    @Override
    public String obtainTitle(Context context) {
        return "结束问诊";
    }

    @Override
    public void onClick(AppCompatActivity activity, ImExtension extension) {
        if (mPluginListener != null) {
            mPluginListener.onClick();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }
}
