package com.keydom.mianren.ih_patient.activity.reserve_amniocentesis.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.constant.AmniocentesisProtocol;

/**
 * @date 20/3/11 14:25
 * @des 羊水穿刺预约web
 */
public interface AmniocentesisWebView extends BaseView {

    AmniocentesisProtocol getProtocol();

    void onProtocolSelect(boolean agree);

    boolean isSelectProtocol();
}
