package com.keydom.mianren.ih_doctor.activity.online_consultation.view;

import com.keydom.ih_common.base.BaseView;

public interface ConsultationRoomView extends BaseView {
    void endConsultationSuccess();

    void endConsultationFailed(String msg);
}
