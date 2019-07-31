package com.keydom.ih_common.im.widget.provider.item

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.keydom.ih_common.R
import com.keydom.ih_common.im.listener.MessageProvider
import com.keydom.ih_common.im.model.ImUIMessage
import com.keydom.ih_common.im.model.custom.ReferralDoctorAttachment
import com.keydom.ih_common.utils.GlideUtils

class ImMessageItemReferralDoctorProvider(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs), MessageProvider {

    val avatar: ImageView by lazy { findViewById<ImageView>(R.id.avatar) }
    val name: TextView by lazy { findViewById<TextView>(R.id.name) }
    val jobTitle: TextView by lazy { findViewById<TextView>(R.id.job_title) }
    val description: TextView by lazy { findViewById<TextView>(R.id.description) }

    override fun containerViewLeft() {
        setBackgroundResource(R.drawable.im_message_left)
    }

    override fun containerViewRight() {
        setBackgroundResource(R.drawable.im_message_right)
    }

    override fun bindView(message: ImUIMessage) {
        val attachment = message.message.attachment as ReferralDoctorAttachment
        name.text = attachment.name
        jobTitle.text = attachment.jobTitle
//        val userInfo = ImClient.getUserInfoProvider().getUserInfo(ImClient.getUserInfoProvider().account) as NimUserInfo
        description.text = String.format("您预约的%s医生暂时不能进行接诊，为您推荐选择%s医生%s为您提供接诊服务。", attachment.currentName, attachment.teamName, attachment.name)
        GlideUtils.load(avatar, attachment.avatar, R.mipmap.im_default_head_image, R.mipmap.im_default_head_image, true, null)
    }

}