package com.keydom.ih_common.im.widget.provider.item

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.keydom.ih_common.R
import com.keydom.ih_common.im.listener.MessageProvider
import com.keydom.ih_common.im.model.ImUIMessage
import com.keydom.ih_common.im.model.custom.TriageOrderAttachment
import com.keydom.ih_common.utils.GlideUtils

class ImMessageItemTriageOrderProvider(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs), MessageProvider {

    private val triageOderContent: TextView by lazy { findViewById<TextView>(R.id.triage_order_content_tv) }
    private val triageOderImage1: ImageView by lazy { findViewById<ImageView>(R.id.triage_order_image_1) }
    private val triageOderImage2: ImageView by lazy { findViewById<ImageView>(R.id.triage_order_image_2) }

    override fun containerViewLeft() {
        setBackgroundResource(R.drawable.im_message_left)
    }

    override fun containerViewRight() {
        setBackgroundResource(R.drawable.im_message_right)
    }

    override fun bindView(message: ImUIMessage) {
        val content = message.message.attachment as TriageOrderAttachment
        triageOderContent.text = content.content
        content.images?.let {
            when {
                it.size == 0 -> {
                    triageOderImage1.visibility = View.GONE
                    triageOderImage2.visibility = View.GONE
                }
                it.size == 1 -> {
                    triageOderImage1.visibility = View.VISIBLE
                    triageOderImage2.visibility = View.GONE
                    GlideUtils.load(triageOderImage1, content.images[0], 0, 0, false, null)
                }
                else -> {
                    triageOderImage1.visibility = View.VISIBLE
                    triageOderImage2.visibility = View.VISIBLE
                    GlideUtils.load(triageOderImage1, content.images[0], 0, 0, false, null)
                    GlideUtils.load(triageOderImage2, content.images[1], 0, 0, false, null)
                }
            }
        }

    }

}