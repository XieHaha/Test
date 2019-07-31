package com.keydom.ih_common.im.widget.provider.item

import android.content.Context
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import com.keydom.ih_common.im.listener.MessageProvider
import com.keydom.ih_common.im.model.ImUIMessage

class ImMessageItemUnKnownProvider(context: Context?, attrs: AttributeSet?) : AppCompatTextView(context, attrs), MessageProvider {

    override fun containerViewLeft() {
    }

    override fun containerViewRight() {
    }

    override fun bindView(message: ImUIMessage) {
        text = "未知消息"
    }
}