package com.keydom.ih_common.im.widget.provider.item

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import com.keydom.ih_common.R
import com.keydom.ih_common.im.listener.MessageProvider
import com.keydom.ih_common.im.model.ImUIMessage
import com.netease.nimlib.sdk.avchat.constant.AVChatRecordState
import com.netease.nimlib.sdk.avchat.model.AVChatAttachment
import com.netease.nimlib.sdk.msg.constant.MsgDirectionEnum

class ImMessageItemAVChatProvider(context: Context?, attrs: AttributeSet?) : AppCompatTextView(context, attrs), MessageProvider {
    override fun containerViewLeft() {
        setBackgroundResource(R.drawable.im_message_left)
        setTextColor(ContextCompat.getColor(context, R.color.im_context_text))
    }

    override fun containerViewRight() {
        setBackgroundResource(R.drawable.im_message_right)
        setTextColor(ContextCompat.getColor(context, R.color.im_context_text))
    }

    override fun bindView(message: ImUIMessage) {
        val avChatAttachment = message.message.attachment as AVChatAttachment
//        val drawable = if (avChatAttachment.type == AVChatType.AUDIO) {
//            ContextCompat.getDrawable(context, R.mipmap.ic_launcher)
//        } else {
        val drawable = ContextCompat.getDrawable(context, R.mipmap.video_end_icon)
//        }
        if (message.message.direct == MsgDirectionEnum.Out) {
            setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawable, null)
        } else {
            setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, null, null, null)
        }

        text = when (avChatAttachment.state) {
            AVChatRecordState.Canceled -> if (message.message.direct == MsgDirectionEnum.Out) "对方已取消" else "已取消"
            AVChatRecordState.Missed -> if (message.message.direct == MsgDirectionEnum.Out) "已取消" else "未接听"
//            ImMessageConstant.AVCHAT_REJECT -> "已拒绝"
//            ImMessageConstant.AVCHAT_REMOTE_CANCEL -> "对方已取消"
            AVChatRecordState.Rejected -> if (message.message.direct == MsgDirectionEnum.Out) "对方已拒绝" else "已拒绝"
//            ImMessageConstant.AVCHAT_BUSY_LINE -> "对方忙,请稍后再拨"
            AVChatRecordState.Success -> "通话时长 ${if (avChatAttachment.duration >= 3600)
                String.format("%d:%02d:%02d", avChatAttachment.duration / 3600, avChatAttachment.duration % 3600 / 60, avChatAttachment.duration % 60)
            else
                String.format("%02d:%02d", avChatAttachment.duration % 3600 / 60, avChatAttachment.duration % 60)
            }"
            else -> ""
        }

    }
}