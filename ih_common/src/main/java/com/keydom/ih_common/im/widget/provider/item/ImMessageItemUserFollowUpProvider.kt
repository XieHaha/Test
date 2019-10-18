package com.keydom.ih_common.im.widget.provider.item

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView
import com.keydom.ih_common.R
import com.keydom.ih_common.im.listener.MessageProvider
import com.keydom.ih_common.im.model.ImUIMessage
import com.keydom.ih_common.im.model.custom.UserFollowUpAttachment

class ImMessageItemUserFollowUpProvider(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs), MessageProvider {

    val title: TextView by lazy { findViewById<TextView>(R.id.im_item_view_message_user_follow_up_title_tv) }
    val content: TextView by lazy { findViewById<TextView>(R.id.im_item_view_message_user_follow_up_title_content) }

    override fun containerViewLeft() {
        setBackgroundResource(R.drawable.im_message_left)
    }

    override fun containerViewRight() {
        setBackgroundResource(R.drawable.im_message_right)
    }

    override fun bindView(message: ImUIMessage) {
        val attachment = message.message.attachment as UserFollowUpAttachment
        content.text = "由${attachment.doctorName}医生创建"
        title.text = attachment.fileName
    }

}