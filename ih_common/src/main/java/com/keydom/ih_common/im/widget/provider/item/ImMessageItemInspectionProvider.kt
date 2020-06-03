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
import com.keydom.ih_common.im.model.custom.InspectionAttachment
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo

class ImMessageItemInspectionProvider(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs), MessageProvider {

    val inspectionContent: TextView by lazy { findViewById<TextView>(R.id.inspection_content) }
    val bottomTitle: TextView by lazy { findViewById<TextView>(R.id.bottom_title) }
    val paymentAmount: TextView by lazy { findViewById<TextView>(R.id.payment_amount) }
    val resultPay: TextView by lazy { findViewById<TextView>(R.id.result_pay) }

    override fun containerViewLeft() {
        setBackgroundResource(R.drawable.im_message_left)
    }

    override fun containerViewRight() {
        setBackgroundResource(R.drawable.im_message_right)
    }

    override fun bindView(message: ImUIMessage) {
        val attachment = message.message.attachment as InspectionAttachment
        inspectionContent.text = attachment.insCheckApplication.diagnosis
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
        bottomTitle.text = if (type == ImMessageConstant.DOCTOR) "详情" else "检验申请单"
        if (type == ImMessageConstant.DOCTOR) {
            paymentAmount.visibility = View.GONE
            resultPay.visibility = View.GONE
        } else {
            paymentAmount.visibility = View.VISIBLE
            resultPay.visibility = View.VISIBLE
            paymentAmount.text = String.format("￥(%s元)", attachment.insCheckApplication.amount)
            resultPay.text = "去支付"
        }
    }

}