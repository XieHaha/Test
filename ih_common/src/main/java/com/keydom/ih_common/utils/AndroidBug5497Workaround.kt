package com.keydom.ih_common.utils

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.os.Build
import android.util.TypedValue
import android.view.View
import android.widget.FrameLayout

class AndroidBug5497Workaround
private constructor(private val activity: Activity, private val mScrollY: Int = 0) {
    internal var navigationBarHeight = 0

    private val mChildOfContent: View
    private var usableHeightPrevious: Int = 0
    private val frameLayoutParams: FrameLayout.LayoutParams
    private var content: FrameLayout? = null

    init {
        if (checkDeviceHasNavigationBar(activity)) {
            navigationBarHeight = getNavigationBarHeight(activity)
        }
        content = activity.findViewById<View>(android.R.id.content) as FrameLayout
        mChildOfContent = content!!.getChildAt(0)
        mChildOfContent.viewTreeObserver.addOnGlobalLayoutListener { possiblyResizeChildOfContent() }
        frameLayoutParams = mChildOfContent.layoutParams as FrameLayout.LayoutParams
    }

    private fun possiblyResizeChildOfContent() {
        val usableHeightNow = computeUsableHeight()
        if (usableHeightNow != usableHeightPrevious) {
            val usableHeightSansKeyboard = mChildOfContent.rootView.height
            val heightDifference = usableHeightSansKeyboard - usableHeightNow
            if (heightDifference > usableHeightSansKeyboard / 4) {
                // keyboard probably just became visible
                if (mScrollY == 0) {
                    frameLayoutParams.height = usableHeightSansKeyboard - heightDifference
                } else {
                    content?.scrollY = mScrollY + if (navigationBarHeight == 0) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20f, content?.resources?.displayMetrics).toInt() else navigationBarHeight
                }
            } else {
                // keyboard probably just became hidden
                frameLayoutParams.height = usableHeightSansKeyboard - navigationBarHeight
                content?.scrollY = 0
            }
            mChildOfContent.requestLayout()
            usableHeightPrevious = usableHeightNow
        }
    }

    private fun computeUsableHeight(): Int {

        val frame = Rect()
        activity.window.decorView.getWindowVisibleDisplayFrame(frame)
        val statusBarHeight = frame.top
        val r = Rect()
        mChildOfContent.getWindowVisibleDisplayFrame(r)

        //这个判断是为了解决19之后的版本在弹出软键盘时，键盘和推上去的布局（adjustResize）之间有黑色区域的问题
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            r.bottom - r.top + statusBarHeight
        } else r.bottom - r.top
// 全屏模式下： return r.bottom
    }

    fun getNavigationBarHeight(activity: Activity): Int {
        val resources = activity.resources
        val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        //获取NavigationBar的高度
        return resources.getDimensionPixelSize(resourceId)
    }

    companion object {

        // For more information, see https://code.google.com/p/android/issues/detail?id=5497
        // To use this class, simply invoke assistActivity() on an Activity that already has its content view set.

        fun assistActivity(activity: Activity, mScrollY: Int = 0) {
            AndroidBug5497Workaround(activity, mScrollY)
        }

        //获取是否存在NavigationBar
        fun checkDeviceHasNavigationBar(context: Context): Boolean {
            var hasNavigationBar = false
            val rs = context.resources
            val id = rs.getIdentifier("config_showNavigationBar", "bool", "android")
            if (id > 0) {
                hasNavigationBar = rs.getBoolean(id)
            }
            try {
                val systemPropertiesClass = Class.forName("android.os.SystemProperties")
                val m = systemPropertiesClass.getMethod("get", String::class.java)
                val navBarOverride = m.invoke(systemPropertiesClass, "qemu.hw.mainkeys") as String
                if ("1" == navBarOverride) {
                    hasNavigationBar = false
                } else if ("0" == navBarOverride) {
                    hasNavigationBar = true
                }
            } catch (e: Exception) {

            }

            return hasNavigationBar

        }
    }


}