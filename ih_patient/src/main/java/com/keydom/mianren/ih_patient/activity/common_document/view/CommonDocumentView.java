package com.keydom.mianren.ih_patient.activity.common_document.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.CommonDocumentBean;

/**
 * created date: 2019/3/27 on 16:39
 * des:公共文书view
 */
public interface CommonDocumentView extends BaseView{
    void getData(CommonDocumentBean bean);

    void onReserveProtocolSelect(boolean agree);

    boolean isSelectReserveProtocol();
}
