package com.keydom.ih_common.im.widget.provider.item

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.keydom.ih_common.R
import com.keydom.ih_common.im.ImClient
import com.keydom.ih_common.im.config.ImConstants
import com.keydom.ih_common.im.listener.MessageProvider
import com.keydom.ih_common.im.model.ImMessageConstant
import com.keydom.ih_common.im.model.ImUIMessage
import com.keydom.ih_common.im.model.custom.ConsultationResultAttachment
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo

class ImMessageItemConsultationResultProvider(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs), MessageProvider {

    val title: TextView by lazy { findViewById<TextView>(R.id.result_title) }
    val content: TextView by lazy { findViewById<TextView>(R.id.result_content) }
    val amount: TextView by lazy { findViewById<TextView>(R.id.payment_amount) }
    val payStatus: TextView by lazy { findViewById<TextView>(R.id.result_pay) }

    override fun containerViewLeft() {
        setBackgroundResource(R.drawable.im_message_left)
    }

    override fun containerViewRight() {
        setBackgroundResource(R.drawable.im_message_right)
    }

    override fun bindView(message: ImUIMessage) {
        val resultAttachment = message.message.attachment as ConsultationResultAttachment
        title.text = resultAttachment.title
        content.text = resultAttachment.content
        val userInfo = ImClient.getUserInfoProvider().getUserInfo(ImClient.getUserInfoProvider().account) as NimUserInfo

        var type = ImMessageConstant.DOCTOR
        if(null == userInfo.extensionMap){
            if ("com.keydom.mianren.ih_patient" == context.packageName) {
                type = ImMessageConstant.DOCTOR
            } else {
                type = ImMessageConstant.PATIENT
            }
        }else{
            type = userInfo.extensionMap[ImConstants.CALL_USER_TYPE].toString()
        }
        if (type == ImMessageConstant.DOCTOR) {

            amount.visibility = View.VISIBLE
            payStatus.visibility = View.VISIBLE
            amount.text = ""
            payStatus.text = ""
        } else {
            payStatus.visibility = View.VISIBLE
            amount.visibility = View.VISIBLE
            amount.text = String.format("(￥%s元)", resultAttachment.amount)
            payStatus.text = "去支付"
        }
    }

}