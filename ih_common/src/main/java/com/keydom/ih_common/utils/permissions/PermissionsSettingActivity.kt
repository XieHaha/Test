package com.keydom.ih_common.utils.permissions

import android.annotation.TargetApi
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings

@TargetApi(Build.VERSION_CODES.M)
class PermissionsSettingActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.fromParts("package", packageName, null)
        startActivityForResult(intent, REQUEST_CODE_SETTING)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_CODE_SETTING -> {
                mPermissionListener?.onActivityResult()
                finish()
            }
        }
    }

    internal interface PermissionListener {
        fun onActivityResult()
    }

    companion object {
        private const val REQUEST_CODE_SETTING = 300

        internal var mPermissionListener: PermissionListener? = null
    }
}