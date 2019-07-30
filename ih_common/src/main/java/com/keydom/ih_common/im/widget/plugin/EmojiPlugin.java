package com.keydom.ih_common.im.widget.plugin;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.blankj.utilcode.util.ToastUtils;
import com.keydom.ih_common.R;
import com.keydom.ih_common.im.listener.IPluginModule;
import com.keydom.ih_common.im.widget.ImExtension;

/**
 * @author THINKPAD B
 */
public class EmojiPlugin implements IPluginModule {
    @Override
    public Drawable obtainDrawable(Context context) {
        return ContextCompat.getDrawable(context, R.mipmap.im_plugin_emoji);
    }

    @Override
    public String obtainTitle(Context context) {
        return "表情";
    }

    @Override
    public void onClick(AppCompatActivity activity, ImExtension extension) {
        extension.setEmoticonBoard();
//        int position = extension.getPluginAdapter().getPluginPosition(this);
//        int requestCode = ImConstants.IMAGE_REQUEST;
//        AlbumUtilKt.gotoPhotoPicker(activity, true, 2, true, false, false, new WithAspectRatio(1, 1), true, null, (position + 1 << 8) + (requestCode & 255));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }
}
