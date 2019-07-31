package com.keydom.ih_common.im.listener

import android.view.View
import com.keydom.ih_common.R
import com.keydom.ih_common.im.ImClient
import com.keydom.ih_common.im.model.ImMessageConstant
import com.keydom.ih_common.im.model.ImUIMessage
import com.keydom.ih_common.im.model.event.BlackEvent
import com.netease.nimlib.sdk.RequestCallback
import com.netease.nimlib.sdk.ResponseCode
import com.netease.nimlib.sdk.avchat.model.AVChatAttachment
import org.greenrobot.eventbus.EventBus

/**
 * @author THINKPAD B
 * 这个类是用kotlin的原因是其子类需要继承其他类，而本接口默认有一些实现，java1.7版本接口做不到默认实现，
 * 做成抽象类又做不到多继承。
 */
interface IContainerItemProvider {
    fun containerViewLeft()

    fun containerViewRight()

    fun bindView(addition: View, message: ImUIMessage)
}


@Suppress("UNCHECKED_CAST")
interface MessageProvider : IContainerItemProvider {
    override fun bindView(addition: View, message: ImUIMessage) {
        statusChange(addition, message)
        bindView(message)
    }


    fun statusChange(addition: View, uiMessage: ImUIMessage, resend: Boolean = false) {
        val progress = addition.findViewById<View>(R.id.im_progress)
        val warning = addition.findViewById<View>(R.id.im_warning)
        when (uiMessage.sentStatus) {
            ImMessageConstant.SENDING -> {
                addition.visibility = View.VISIBLE
                progress.visibility = View.VISIBLE
                warning.visibility = View.GONE
                if (uiMessage.message.attachment is AVChatAttachment) {
                    uiMessage.sentStatus = ImMessageConstant.FINISH
                    statusChange(addition, uiMessage)
                } else {
                    ImClient.sentMessage(uiMessage.message, resend,
                            object : RequestCallback<Void> {
                                override fun onSuccess(param: Void?) {
                                    uiMessage.sentStatus = ImMessageConstant.FINISH
                                    statusChange(addition, uiMessage)
                                }

                                override fun onFailed(code: Int) {
                                    if (code == ResponseCode.RES_IN_BLACK_LIST.toInt()) {
                                        uiMessage.sentStatus = ImMessageConstant.FINISH
                                        EventBus.getDefault().post(BlackEvent())
                                    } else {
                                        uiMessage.sentStatus = ImMessageConstant.FAILED
                                    }
                                    statusChange(addition, uiMessage)
                                }

                                override fun onException(exception: Throwable) {
                                    exception.printStackTrace()
                                }
                            })
                }
            }
            ImMessageConstant.FAILED -> {
                addition.visibility = View.VISIBLE
                warning.visibility = View.VISIBLE
                progress.visibility = View.GONE
                warning.setOnClickListener {
                    uiMessage.sentStatus = ImMessageConstant.SENDING
                    statusChange(addition, uiMessage, true)
                }
            }
            ImMessageConstant.FINISH -> {
                addition.visibility = View.GONE
            }
        }
    }

    fun bindView(message: ImUIMessage)
}