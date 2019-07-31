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
import com.keydom.ih_common.im.model.custom.ReferralApplyAttachment
import com.keydom.ih_common.utils.GlideUtils

class ImMessageItemReferralApplyProvider(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs), MessageProvider {
    private val referralImage1: ImageView by lazy { findViewById<ImageView>(R.id.referral_image_1) }
    private val referralImage2: ImageView by lazy { findViewById<ImageView>(R.id.referral_image_2) }
    val description: TextView by lazy { findViewById<TextView>(R.id.referral_apply_description) }

    override fun containerViewLeft() {
        setBackgroundResource(R.drawable.im_message_left)
    }

    override fun containerViewRight() {
        setBackgroundResource(R.drawable.im_message_right)
    }


    override fun bindView(message: ImUIMessage) {
        val attachment = message.message.attachment as ReferralApplyAttachment
        description.text = attachment.description
        attachment.descriptionImage?.let {
            when {
                it.size == 0 -> {
                    referralImage1.visibility = View.GONE
                    referralImage2.visibility = View.GONE
                }
                it.size == 1 -> {
                    referralImage2.visibility = View.GONE
                    referralImage1.visibility = View.VISIBLE
                    GlideUtils.load(referralImage1, attachment.descriptionImage[0], 0, 0, false, null)
                }
                else -> {
                    referralImage1.visibility = View.VISIBLE
                    referralImage2.visibility = View.VISIBLE
                    GlideUtils.load(referralImage1, attachment.descriptionImage[0], 0, 0, false, null)
                    GlideUtils.load(referralImage2, attachment.descriptionImage[1], 0, 0, false, null)
                }
            }

        }

    }

}