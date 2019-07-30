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
import com.keydom.ih_common.im.model.custom.InquiryAttachment
import com.keydom.ih_common.utils.GlideUtils

class ImMessageItemInquiryProvider(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs), MessageProvider {

    private val inquiryName: TextView by lazy { findViewById<TextView>(R.id.inquiry_name) }
    private val inquirySex: TextView by lazy { findViewById<TextView>(R.id.inquiry_sex) }
    private val inquiryAge: TextView by lazy { findViewById<TextView>(R.id.inquiry_age) }
    private val inquiryContent: TextView by lazy { findViewById<TextView>(R.id.inquiry_content) }
    private val inquiryImage1: ImageView by lazy { findViewById<ImageView>(R.id.inquiry_image_1) }
    private val inquiryImage2: ImageView by lazy { findViewById<ImageView>(R.id.inquiry_image_2) }

    override fun containerViewLeft() {
        setBackgroundResource(R.drawable.im_message_left)
    }

    override fun containerViewRight() {
        setBackgroundResource(R.drawable.im_message_right)
    }

    override fun bindView(message: ImUIMessage) {
        val content = message.message.attachment as InquiryAttachment
        inquiryName.text = content.name
        inquirySex.text = content.sex
        inquiryAge.text = content.age
        inquiryContent.text = content.content
        content.images?.let {
            when {
                it.size == 0 -> {
                    inquiryImage1.visibility = View.GONE
                    inquiryImage2.visibility = View.GONE
                }
                it.size == 1 -> {
                    inquiryImage1.visibility = View.VISIBLE
                    inquiryImage2.visibility = View.GONE
                    GlideUtils.load(inquiryImage1,  content.images[0], 0, 0, false, null)
                }
                else -> {
                    inquiryImage1.visibility = View.VISIBLE
                    inquiryImage2.visibility = View.VISIBLE
                    GlideUtils.load(inquiryImage1, content.images[0], 0, 0, false, null)
                    GlideUtils.load(inquiryImage2, content.images[1], 0, 0, false, null)
                }
            }
        }

    }

}