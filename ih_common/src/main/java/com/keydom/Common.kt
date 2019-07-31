package com.keydom

import android.app.Application
import android.content.Context
import com.keydom.ih_common.R
import com.keydom.ih_common.net.exception.BaseException
import com.keydom.ih_common.utils.permissions.IHPermissions
import com.keydom.ih_common.utils.permissions.OnDialogClickListener
import com.keydom.ih_common.utils.permissions.RationaleListener
import com.keydom.ih_common.view.GeneralDialog
import com.netease.nimlib.sdk.avchat.model.AVChatData


object Common {

    private lateinit var application: Application
    private var baseException: BaseException? = null
    private lateinit var avChatData: AVChatData

    fun setAVChatData(avChatData: AVChatData) {
        this.avChatData = avChatData
    }

    fun getAVChatData(): AVChatData? {
        return avChatData
    }

    /**
     * 整个Common的初始化。
     */
    fun init(app: Application): Common {
        application = app
        return this
    }

    /**
     *外部设置异常处理的类，需要实现BaseException接口
     */
    fun setHttpException(baseException: BaseException?): Common {
        Common.baseException = baseException
        return this
    }

    /**
     * 获取全局上下文对象
     */
    fun getApplication() = application

    /**
     * 获取异常
     */
    fun getBaseException() = baseException

    /**
     * 动态权限
     */
    fun getPermissions(): IHPermissions {
        return IHPermissions
    }

    /**
     * 动态权限申请
     */
    fun getPermissions(context: Context) =
            getPermissions().with(context).setRationaleListener(object : RationaleListener {
                override fun openPermissionSetting(listener: OnDialogClickListener) {
                    GeneralDialog(context, context.getString(R.string.permission_message_permission_failed),
                            GeneralDialog.OnCloseListener {
                                listener.resume()
                            })
                            .setTitle(context.getString(R.string.permission_title_permission_failed))
                            .setNegativeButton(context.getString(R.string.permission_cancel))
                            .setPositiveButton(context.getString(R.string.permission_setting))
                            .show()

//                    GeneralDialog(getContext(), "该订单已支付！") { }.setTitle("提示").setPositiveButton("确认").show()

//                    CommonChooseDialog.show(context, context.getString(R.string.permission_title_permission_failed)
//                            , context.getString(R.string.permission_message_permission_failed)
//                            , context.getString(R.string.permission_cancel), context.getString(R.string.permission_setting)) {
//                        if (it) {
//                            listener.cancel()
//                        } else {
//                            listener.resume()
//                        }
//                    }
                }

                override fun showRequestPermissionRationale(listener: OnDialogClickListener) {
                    GeneralDialog(context, context.getString(R.string.permission_message_permission_rationale),
                            GeneralDialog.OnCloseListener {
                                listener.resume()
                            })
                            .setTitle(context.getString(R.string.permission_title_permission_rationale))
                            .setNegativeButton(context.getString(R.string.permission_cancel))
                            .setPositiveButton(context.getString(R.string.permission_setting))
                            .show()

//                    CommonChooseDialog.show(context, context.getString(R.string.permission_title_permission_rationale)
//                            , context.getString(R.string.permission_message_permission_rationale)
//                            , context.getString(R.string.permission_cancel), context.getString(R.string.permission_resume)) {
//                        if (it) {
//                            listener.cancel()
//                        } else {
//                            listener.resume()
//                        }
//                    }
                }

            })

}