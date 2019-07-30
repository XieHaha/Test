package com.keydom.ih_common.utils.permissions

import android.annotation.TargetApi
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat

/**
 * 用于处理授权回掉
 */
@TargetApi(Build.VERSION_CODES.M)
class PermissionsShadowActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = intent
        val permissions = intent.getStringArrayExtra(KEY_INPUT_PERMISSIONS)

        if (permissions == null) {
            finish()
            return
        }

        //先判断是否拒绝过
        mRationaleListener?.let {
            var rationale = false
            for (permission in permissions) {
                rationale = ActivityCompat.shouldShowRequestPermissionRationale(this, permission)
                if (rationale) break
            }
            if (rationale && !it.onRationaleResult()) {
                mRationaleListener = null
                finish()
                return
            }
        }
        //请求权限
        mPermissionListener?.let { requestPermissions(permissions, 1) }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        val deniedList = permissions.indices.filter { grantResults[it] != PackageManager.PERMISSION_GRANTED }.map { permissions[it] }
        var rationale = true
        if (deniedList.isNotEmpty()) {
            for (permission in permissions) {
                rationale = ActivityCompat.shouldShowRequestPermissionRationale(this, permission)
                if (!rationale) break
            }
        }
        if (!rationale) {//返回false代表选中了“不再提醒”
            mPermissionListener?.onNeverAskAgain()
        } else {
            mPermissionListener?.onRequestPermissionsResult(permissions, grantResults)
        }
        mPermissionListener = null
        finish()
    }

    internal interface RationaleListener {
        fun onRationaleResult(): Boolean
    }

    internal interface PermissionListener {
        fun onRequestPermissionsResult(permissions: Array<String>, grantResults: IntArray)
        fun onNeverAskAgain()
    }

    companion object {
        internal const val KEY_INPUT_PERMISSIONS = "KEY_INPUT_PERMISSIONS"

        internal var mRationaleListener: RationaleListener? = null
        internal var mPermissionListener: PermissionListener? = null
    }
}