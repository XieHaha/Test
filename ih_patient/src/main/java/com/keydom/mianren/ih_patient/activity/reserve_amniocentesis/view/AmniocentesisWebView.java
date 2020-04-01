package com.keydom.mianren.ih_patient.activity.reserve_amniocentesis.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_patient.bean.AmniocentesisReserveBean;
import com.keydom.mianren.ih_patient.constant.AmniocentesisProtocol;

import java.util.Map;

/**
 * @date 20/3/11 14:25
 * @des 羊水穿刺预约web
 */
public interface AmniocentesisWebView extends BaseView {

    AmniocentesisProtocol getProtocol();

    AmniocentesisReserveBean getReserveBean();

    /**
     * 预约协议
     */
    void onReserveProtocolSelect(boolean agree);

    boolean isSelectReserveProtocol();

    /**
     * 知情同意书
     */
    void onAgreeProtocolSelect();

    boolean isSelectAgreeProtocol();

    /**
     * 手术注意事项
     *
     * @param type 1、同意书  2、注意事项
     */
    void onNoticeProtocolSelect(int type);

    boolean isSelectNoticeProtocol();

    Map<String, Object> getParamsMap();

}
