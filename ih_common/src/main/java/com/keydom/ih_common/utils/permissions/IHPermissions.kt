package com.keydom.ih_common.utils.permissions

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.view.WindowManager
import com.keydom.ih_common.R
import com.orhanobut.logger.Logger
import java.util.*

/**
 *  App权限管理
 */
object IHPermissions {

    private val mPermissionMap = mutableListOf<String>()

    init {
        initializePermissionsMap()
    }

    /**
     * 获取当前Android版本所有可用权限，因为所申请在低版本中可能不存在，申请不存在的权限会报错
     */
    @Synchronized
    private fun initializePermissionsMap() {
        val fields = Manifest.permission::class.java.fields
        for (field in fields) {
            try {
                val name = field.get("") as String
                mPermissionMap.add(name)
            } catch (e: Exception) {
                Logger.e(e, e.message!!, "")
            }
        }
    }

    /**
     * 获取当前程序manifest.xml中申请的所有权限
     */
    @Synchronized
    fun getManifestPermissions(context: Context): Array<String> {
        var packageInfo: PackageInfo? = null
        val list = ArrayList<String>(1)
        try {
            packageInfo = context.packageManager.getPackageInfo(context.packageName, PackageManager.GET_PERMISSIONS)
        } catch (e: PackageManager.NameNotFoundException) {
            Logger.e(e, e.message!!, "")
        }

        if (packageInfo != null) {
            val permissions = packageInfo.requestedPermissions
            if (permissions != null) {
                Collections.addAll(list, *permissions)
            }
        }
        return list.toTypedArray()
    }

    /**
     * 判断App是否拥有权限
     */
    @Synchronized
    fun hasPermission(context: Context, permission: String): Boolean {
        return (ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED || !mPermissionMap.contains(permission))
    }

    fun hasAlwaysDeniedPermission(activity: Activity, deniedPermissions: List<String>): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return false
        if (deniedPermissions.isEmpty()) return false
        return deniedPermissions.map { ActivityCompat.shouldShowRequestPermissionRationale(activity, it) }.none { it }
    }

    fun rationaleDialog(context: Context, listener: OnDialogClickListener) {
        val dialog = CommonDialog(context)
        dialog.setTitle(context.getString(R.string.permission_title_permission_rationale))
        dialog.setContent(context.getString(R.string.permission_message_permission_rationale))
        dialog.setPositiveButton(context.getString(R.string.permission_resume))
        dialog.setNegativeButton(context.getString(R.string.permission_cancel))
        dialog.setListener(object : CommonDialog.OnCloseListener {
            override fun onClick(dialog: Dialog, confirm: Boolean) {
                if (confirm) listener.resume()
                else listener.cancel()
            }
        })
        if (context !is Activity) {
            dialog.window.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT)
        }
        return dialog.show()
    }

    fun settingDialog(context: Context, listener: OnDialogClickListener) {
        val dialog = CommonDialog(context)
        dialog.setTitle(context.getString(R.string.permission_title_permission_failed))
        dialog.setContent(context.getString(R.string.permission_message_permission_failed))
        dialog.setPositiveButton(context.getString(R.string.permission_setting))
        dialog.setNegativeButton(context.getString(R.string.permission_cancel))
        dialog.setListener(object : CommonDialog.OnCloseListener {
            override fun onClick(dialog: Dialog, confirm: Boolean) {
                if (confirm) listener.resume()
                else listener.cancel()
            }
        })
        if (context !is Activity) {
            dialog.window.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT)
        }
        return dialog.show()
    }

    fun with(activity: Activity): PermissionRequest {
        return PermissionRequestImpl(AppActivityTarget(activity))
    }

    fun with(fragment: android.support.v4.app.Fragment): PermissionRequest {
        return PermissionRequestImpl(SupportFragmentTarget(fragment))
    }

    fun with(fragment: android.app.Fragment): PermissionRequest {
        return PermissionRequestImpl(AppFragmentTarget(fragment))
    }

    fun with(context: Context): PermissionRequest {
        return PermissionRequestImpl(ContextTarget(context))
    }

    /**
     * 申请当前程序manifest.xml中申请的所有权限
     */
//    fun requestAllManifestPermissions(target: Target, listener: PermissionListener) {
//        val perms = getManifestPermissions(target.context)
//        with(target.context).requestPermissions(*perms, listener = listener)
//    }
}

interface OnDialogClickListener {
    fun resume()
    fun cancel()
}