package com.keydom.ih_common.im.listener;

import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

public interface IExtensionClickListener extends TextWatcher {

    int RESULT_IMAGE = 0x0818;
    int RESULT_VIDEO = 0x0819;

    /**
     * 发送按钮点击回调
     *
     * @param view
     * @param text
     */
    void onSendToggleClick(View view, String text);

    /**
     * 语音触摸回调
     *
     * @param view
     * @param event
     */
    void onVoiceInputToggleTouch(View view, MotionEvent event);

    /**
     * 语音回调
     *
     * @param duration
     * @param audioPath
     */
    void onVoiceResult(long duration, String audioPath);

    /**
     * 按键
     *
     * @param view
     * @param keyCode
     * @param event
     * @return
     */
    Boolean onKey(View view, int keyCode, KeyEvent event);

    /**
     * 扩展关闭回调
     */
    void onExtensionCollapsed();

    /**
     * 扩展展开回调
     *
     * @param h
     */
    void onExtensionExpanded(int h);

    /**
     * 图片/视频选择回调
     *
     * @param selectedImages
     */
    void onImageResult(List<LocalMedia> selectedImages, int resultType);

}
