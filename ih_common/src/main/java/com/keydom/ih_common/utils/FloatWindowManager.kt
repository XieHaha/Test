package com.keydom.ih_common.utils

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.PixelFormat
import android.os.Build
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.WindowManager
import com.keydom.ih_common.widget.FloatLayout

/**
 * Author:xishuang
 * Date:2017.08.01
 * Des:悬浮窗统一管理，与悬浮窗交互的真正实现
 */
object FloatWindowManager {
    /**
     * 悬浮窗
     */
    private var mWindowManager: WindowManager? = null

    /**
     * 创建一个小悬浮窗。初始位置为屏幕的右部中间位置。
     *
     * @param context 必须为应用程序的Context.
     */
    @JvmStatic
    fun createFloatWindow(context: Context, mFloatLayout: FloatLayout, x: Int = 0, y: Int = 0) {
        val wmParams = WindowManager.LayoutParams()
        val windowManager = getWindowManager(context)
        if (Build.VERSION.SDK_INT >= 26) {//8.0新特性
            wmParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else if (Build.VERSION.SDK_INT >= 24) { /*android7.0不能用TYPE_TOAST*/
            wmParams.type = WindowManager.LayoutParams.TYPE_PHONE
        } else { /*以下代码块使得android6.0之后的用户不必再去手动开启悬浮窗权限*/
            val packname = context.packageName
            val pm = context.packageManager
            val permission = PackageManager.PERMISSION_GRANTED == pm.checkPermission("android.permission.SYSTEM_ALERT_WINDOW", packname)
            if (permission) {
                wmParams.type = WindowManager.LayoutParams.TYPE_PHONE
            } else {
                wmParams.type = WindowManager.LayoutParams.TYPE_TOAST
            }
        }

        //设置图片格式，效果为背景透明
        wmParams.format = PixelFormat.RGBA_8888
        //设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        //调整悬浮窗显示的停靠位置为左侧置顶
        wmParams.gravity = Gravity.START or Gravity.TOP

        val dm = DisplayMetrics()
        //以屏幕左上角为原点，设置x、y初始值，相对于gravity
        wmParams.x = x
        wmParams.y = y

        //设置悬浮窗口长宽数据
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        mFloatLayout.setParams(wmParams)
        windowManager?.addView(mFloatLayout, wmParams)
    }

    /**
     * 移除悬浮窗
     */
    @JvmStatic
    fun removeFloatWindowManager(mFloatLayout: FloatLayout) {
        //移除悬浮窗口
        var isAttach = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            isAttach = mFloatLayout.isAttachedToWindow
        }
        if (mFloatLayout.isShow && isAttach && mWindowManager != null)
            mWindowManager?.removeView(mFloatLayout)
    }

    /**
     * 返回当前已创建的WindowManager。
     */
    private fun getWindowManager(context: Context): WindowManager? {
        if (mWindowManager == null) {
            mWindowManager = context.getSystemService(Context.WINDOW_SERVICE) as? WindowManager
        }
        return mWindowManager
    }
}
