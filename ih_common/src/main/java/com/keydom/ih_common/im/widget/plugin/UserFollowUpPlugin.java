package com.keydom.ih_common.im.widget.plugin;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.keydom.ih_common.R;
import com.keydom.ih_common.im.listener.IPluginModule;
import com.keydom.ih_common.im.widget.ImExtension;

public class UserFollowUpPlugin implements IPluginModule {
    private PluginListener mPluginListener;

    public UserFollowUpPlugin(PluginListener pluginListener) {
        mPluginListener = pluginListener;
    }

    @Override
    public Drawable obtainDrawable(Context context) {
        return ContextCompat.getDrawable(context, R.mipmap.ic_user_follow_up);
    }

    @Override
    public String obtainTitle(Context context) {
        return "随访表";
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
