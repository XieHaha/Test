package com.keydom.ih_common.utils

import android.content.Context
import android.os.Build
import com.keydom.ih_common.widget.FloatLayout

/**
 * 与悬浮窗交互的控制类
 */
object FloatActionController {

    /**
     * 开启服务悬浮窗
     */
    @JvmOverloads
    fun startFloatWindow(context: Context, floatLayout: FloatLayout, x: Int = 0, y: Int = 0): Boolean {
        val isPermission = FloatPermissionManager.applyFloatWindow(context)
        //有对应权限或者系统版本小于7.0
        if (isPermission || Build.VERSION.SDK_INT < 24) {
            //开启悬浮窗
            FloatWindowManager.createFloatWindow(context, floatLayout, x, y)
            return true
        }
        return false
    }

    /**
     * 停止服务悬浮窗
     */
    fun stopFloatWindow(floatLayout: FloatLayout) {
        FloatWindowManager.removeFloatWindowManager(floatLayout)
    }
}
