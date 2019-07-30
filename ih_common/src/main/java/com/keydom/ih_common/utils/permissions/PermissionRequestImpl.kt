package com.keydom.ih_common.utils.permissions

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat

/**
 * Request permission and callback.
 */
internal class PermissionRequestImpl internal constructor(private val target: Target) : PermissionRequest, PermissionsShadowActivity.RationaleListener, PermissionsShadowActivity.PermissionListener, PermissionsSettingActivity.PermissionListener {

    private val rationaleDialogListener = object : OnDialogClickListener {
        override fun resume() {
            request()
        }

        override fun cancel() {
            check()
        }
    }
    private val settingDialogListener = object : OnDialogClickListener {
        override fun resume() {
            PermissionsSettingActivity.mPermissionListener = this@PermissionRequestImpl
            val intent = Intent(target.context, PermissionsSettingActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            target.startActivity(intent)
        }

        override fun cancel() {
            check()
        }
    }
    private var listener: PermissionListener? = null
    private var mPermissions: Array<String>? = null
    private var mDeniedPermissions: Array<String>? = null

    private var rationaleListener: RationaleListener? = object : RationaleListener {
        override fun showRequestPermissionRationale(listener: OnDialogClickListener) {
            IHPermissions.rationaleDialog(target.context, listener)
        }

        override fun openPermissionSetting(listener: OnDialogClickListener) {
            IHPermissions.settingDialog(target.context, listener)
        }
    }

    override fun setRationaleListener(rationaleListener: RationaleListener?): PermissionRequestImpl {
        this.rationaleListener = rationaleListener
        return this
    }

    /**
     * 申请权限
     */
    @Synchronized
    override fun requestPermissions(vararg permissions: String, listener: PermissionListener) {
        this.mPermissions = arrayOf(*permissions)
        this.listener = listener
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            callbackSucceed()
        } else {
            mDeniedPermissions = getDeniedPermissions(target.context, *permissions).toTypedArray()
            if (mDeniedPermissions?.isNotEmpty() == true) {
                PermissionsShadowActivity.mRationaleListener = this
                request()
            } else {
                callbackSucceed()
            }
        }
    }

    /**
     * 获取未授权列表
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private fun getDeniedPermissions(context: Context, vararg permissions: String): List<String> {
        return permissions.filter { ContextCompat.checkSelfPermission(context, it) != PackageManager.PERMISSION_GRANTED }
    }

    /**
     * 返回成功
     */
    private fun callbackSucceed() {
        listener?.onGranted()
    }

    /**
     * 返回失败
     */
    private fun callbackFailed(deniedList: List<String>) {
        listener?.onDenied(deniedList)
    }

    private fun request() {
        PermissionsShadowActivity.mPermissionListener = this
        val intent = Intent(target.context, PermissionsShadowActivity::class.java)
        intent.putExtra(PermissionsShadowActivity.KEY_INPUT_PERMISSIONS, mDeniedPermissions)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        target.startActivity(intent)
    }

    private fun check() {
        mDeniedPermissions?.let {
            val results = IntArray(it.size)
            for (i in it.indices)
                results[i] = ContextCompat.checkSelfPermission(target.context, it[i])
            onRequestPermissionsResult(it, results)
        }
    }

    override fun onRationaleResult(): Boolean {
        if (rationaleListener != null) {
            rationaleListener?.showRequestPermissionRationale(rationaleDialogListener)
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(permissions: Array<String>, grantResults: IntArray) {
        val deniedList = permissions.indices.filter { grantResults[it] != PackageManager.PERMISSION_GRANTED }.map { permissions[it] }
        when {
            deniedList.isEmpty() -> callbackSucceed()
            else -> callbackFailed(deniedList)
        }
    }

    override fun onNeverAskAgain() {
        rationaleListener?.openPermissionSetting(settingDialogListener)
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    override fun onActivityResult() {
        check()
    }
}

interface PermissionRequest {

    fun setRationaleListener(rationaleListener: RationaleListener?): PermissionRequest
    fun requestPermissions(vararg permissions: String, listener: PermissionListener)
}

interface RationaleListener {
    fun showRequestPermissionRationale(listener: OnDialogClickListener)
    fun openPermissionSetting(listener: OnDialogClickListener)
}


interface PermissionListener {
    /**
     * 授权成功
     */
    fun onGranted()

    /**
     * 申请被拒
     */
    fun onDenied(deniedPermissions: List<String>)
}

interface Target {

    val context: Context

    fun startActivity(intent: Intent)

    fun startActivityForResult(intent: Intent, requestCode: Int)

}