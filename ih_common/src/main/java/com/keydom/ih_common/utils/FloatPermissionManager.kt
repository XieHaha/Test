package com.keydom.ih_common.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.support.annotation.RequiresApi
import android.util.Log
import com.keydom.ih_common.activity.FloatPermissionActivity
import com.keydom.ih_common.utils.rom.*

/**
 * Author:xishuang
 * Date:2017.08.01
 * Des:悬浮窗权限适配，很全的适配http://blog.csdn.net/self_study/article/details/52859790
 */

object FloatPermissionManager {

    private const val TAG = "FloatPermissionManager"

    fun applyFloatWindow(context: Context): Boolean =
            if (checkPermission(context)) {
                true
            } else {
                applyPermission(context)
                false
            }


    private fun checkPermission(context: Context): Boolean {
        //6.0 版本之后由于 google 增加了对悬浮窗权限的管理，所以方式就统一了
        if (Build.VERSION.SDK_INT < 23) {
            when {
                RomUtils.checkIsMiuiRom() -> return miuiPermissionCheck(context)
                RomUtils.checkIsMeizuRom() -> return meizuPermissionCheck(context)
                RomUtils.checkIsHuaweiRom() -> return huaweiPermissionCheck(context)
                RomUtils.checkIs360Rom() -> return qikuPermissionCheck(context)
            }
        }
        return commonROMPermissionCheck(context)
    }

    private fun huaweiPermissionCheck(context: Context): Boolean = HuaweiUtils.checkFloatWindowPermission(context)

    private fun miuiPermissionCheck(context: Context): Boolean = MiuiUtils.checkFloatWindowPermission(context)

    private fun meizuPermissionCheck(context: Context): Boolean = MeizuUtils.checkFloatWindowPermission(context)

    private fun qikuPermissionCheck(context: Context): Boolean = QikuUtils.checkFloatWindowPermission(context)

    private fun commonROMPermissionCheck(context: Context): Boolean {
        //最新发现魅族6.0的系统这种方式不好用，天杀的，只有你是奇葩，没办法，单独适配一下
        if (RomUtils.checkIsMeizuRom()) {
            return meizuPermissionCheck(context)
        } else {
            var result = true
            if (Build.VERSION.SDK_INT >= 23) {
                try {
//                    val clazz = Settings::class.java
//                    val canDrawOverlays = clazz.getDeclaredMethod("canDrawOverlays", Context::class.java)
//                    result = canDrawOverlays.invoke(null, context) as Boolean
                    result = Settings.canDrawOverlays(context)
                } catch (e: Exception) {
                    Log.e(TAG, Log.getStackTraceString(e))
                }

            }
            return result
        }
    }

    private fun applyPermission(context: Context) {
        if (Build.VERSION.SDK_INT < 23) {
            when {
                RomUtils.checkIsMiuiRom() -> miuiROMPermissionApply(context)
                RomUtils.checkIsMeizuRom() -> meizuROMPermissionApply(context)
                RomUtils.checkIsHuaweiRom() -> huaweiROMPermissionApply(context)
                RomUtils.checkIs360Rom() -> ROM360PermissionApply(context)
                else -> commonROMPermissionApply(context)
            }
        }else {
            commonROMPermissionApply(context)
        }
    }

    private fun ROM360PermissionApply(context: Context) {
        showConfirmDialog(context, object : OnConfirmResult {
            override fun confirmResult(confirm: Boolean) {
                if (confirm) {
                    QikuUtils.applyPermission(context)
                } else {
                    Log.e(TAG, "ROM:360, user manually refuse OVERLAY_PERMISSION")
                }
            }
        })
    }

    private fun huaweiROMPermissionApply(context: Context) {
        showConfirmDialog(context, object : OnConfirmResult {
            override fun confirmResult(confirm: Boolean) {
                if (confirm) {
                    HuaweiUtils.applyPermission(context)
                } else {
                    Log.e(TAG, "ROM:huawei, user manually refuse OVERLAY_PERMISSION")
                }
            }
        })
    }

    private fun meizuROMPermissionApply(context: Context) {
        showConfirmDialog(context, object : OnConfirmResult {
            override fun confirmResult(confirm: Boolean) {
                if (confirm) {
                    MeizuUtils.applyPermission(context)
                } else {
                    Log.e(TAG, "ROM:meizu, user manually refuse OVERLAY_PERMISSION")
                }
            }
        })
    }

    private fun miuiROMPermissionApply(context: Context) {
        showConfirmDialog(context, object : OnConfirmResult {
            override fun confirmResult(confirm: Boolean) {
                if (confirm) {
                    MiuiUtils.applyMiuiPermission(context)
                } else {
                    Log.e(TAG, "ROM:miui, user manually refuse OVERLAY_PERMISSION")
                }
            }
        })
    }

    /**
     * 通用 rom 权限申请
     */
    @JvmOverloads
    public fun commonROMPermissionApply(context: Context) {
        //这里也一样，魅族系统需要单独适配
        if (RomUtils.checkIsMeizuRom()) {
            meizuROMPermissionApply(context)
        } else {
            if (Build.VERSION.SDK_INT >= 23) {
                showConfirmDialog(context, object : OnConfirmResult {
                    @RequiresApi(Build.VERSION_CODES.M)
                    override fun confirmResult(confirm: Boolean) {
                        if (confirm) {
                            try {
                                val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + context.packageName))
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                context.startActivity(intent)
                            } catch (e: Exception) {
                                Log.e(TAG, Log.getStackTraceString(e))
                            }
                        } else {
                            Log.d(TAG, "user manually refuse OVERLAY_PERMISSION")
                            //需要做统计效果
                        }
                    }
                })
            }
        }
    }

    private fun showConfirmDialog(context: Context, result: OnConfirmResult) {
        showConfirmDialog(context, "您的手机没有授予悬浮窗权限，请开启后再试", result)
    }

    private fun showConfirmDialog(context: Context, message: String, result: OnConfirmResult) {
        FloatPermissionActivity.result = result
        val intent = Intent(context, FloatPermissionActivity::class.java)
        intent.putExtra("message", message)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    interface OnConfirmResult {
        fun confirmResult(confirm: Boolean)
    }
}
