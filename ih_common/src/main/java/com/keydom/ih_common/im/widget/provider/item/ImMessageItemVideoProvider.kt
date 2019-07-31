package com.keydom.ih_common.im.widget.provider.item

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.keydom.ih_common.R
import com.keydom.ih_common.im.listener.MessageProvider
import com.keydom.ih_common.im.model.ImUIMessage
import com.keydom.ih_common.utils.GlideUtils
import com.netease.nimlib.sdk.msg.attachment.VideoAttachment
import com.netease.nimlib.sdk.msg.constant.MsgDirectionEnum


class ImMessageItemVideoProvider(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs), MessageProvider {

    private val thumbImage: ImageView by lazy { findViewById<ImageView>(R.id.video_thumb) }
    private val play: ImageView by lazy { findViewById<ImageView>(R.id.video_play) }
    private val timeText: TextView by lazy { findViewById<TextView>(R.id.video_time) }

    override fun containerViewLeft() {
        setBackgroundResource(R.drawable.im_message_left)
    }

    override fun containerViewRight() {
        setBackgroundResource(R.drawable.im_message_right)
    }

    override fun bindView(message: ImUIMessage) {
        val videoAttachment = message.message.attachment as VideoAttachment
        if (message.message.direct == MsgDirectionEnum.In) {
            GlideUtils.load(thumbImage, videoAttachment.thumbPath, 0, 0, false, null)
        } else {
            GlideUtils.load(thumbImage, videoAttachment.path, 0, 0, false, null)
        }
        val time = Math.ceil(videoAttachment.duration / 1000.0).toInt()
        timeText.text = when {
            time < 10 -> "0:0$time"
            time in 10..59 -> "0:$time"
            else -> {
                val minute = time / 60
                val second = time % 60
                if (second < 10) {
                    "$minute:0$second"
                } else {
                    "$minute:$second"
                }

            }
        }

    }
}