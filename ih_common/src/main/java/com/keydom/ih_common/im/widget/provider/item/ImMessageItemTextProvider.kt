package com.keydom.ih_common.im.widget.provider.item

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import com.keydom.ih_common.R
import com.keydom.ih_common.im.listener.MessageProvider
import com.keydom.ih_common.im.model.ImUIMessage

class ImMessageItemTextProvider(context: Context?, attrs: AttributeSet?) : AppCompatTextView(context, attrs), MessageProvider {

    override fun containerViewLeft() {
        setBackgroundResource(R.drawable.im_message_left)
        setTextColor(ContextCompat.getColor(context, R.color.im_context_text))
    }

    override fun containerViewRight() {
        setBackgroundResource(R.drawable.im_message_right)
        setTextColor(ContextCompat.getColor(context, R.color.im_context_text))
    }

    override fun bindView(message: ImUIMessage) {
        text = message.message.content
    }
}