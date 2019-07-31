package com.keydom.ih_common.im.model.def;

import android.support.annotation.IntDef;

import com.keydom.ih_common.im.model.custom.EndInquiryAttachment;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 结束问诊类型
 */
@IntDef({EndInquiryAttachment.DOCTOR_APPLY_END, EndInquiryAttachment.PATIENT_AGREE, EndInquiryAttachment.PATIENT_REFUSE, EndInquiryAttachment.PATIENT_END})
@Retention(RetentionPolicy.SOURCE)
public @interface EndType {
}
