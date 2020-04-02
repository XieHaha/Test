package com.keydom.mianren.ih_patient.constant;

/**
 * @date 20/3/31 10:08
 * @des 羊膜腔穿刺预约 状态
 */
public interface AmniocentesisStatus {

    /**
     * 已取消
     */
    int AMNIOCENTESIS_STATUS_CANCEL = -1;
    /**
     * 未预约
     */
    int AMNIOCENTESIS_STATUS_NONE = 0;
    /**
     * 预约
     */
    int AMNIOCENTESIS_STATUS_RESERVE = 1;
    /**
     * 已预约
     */
    int AMNIOCENTESIS_STATUS_ALREADY = 2;
}
