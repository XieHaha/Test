package com.keydom.mianren.ih_doctor.constant;

/**
 * @Description：EventBus事件枚举类
 * @Author：song
 * @Date：18/11/2 下午1:49
 * 修改人：xusong
 * 修改时间：18/11/2 下午1:49
 */
public enum EventType {
    GROUP_CUT,//更换团队
    GROUP_REFRESH,//刷新团队
    DIAGNOSE_ORDER_UPDATE,//更新问诊单、转诊单列表
    UPDATE_USER_INFO,//刷新用户信息
    PRESCRIPTION_UPDATE,//刷新处方
    SEARCH_PRESCRIPTION,//搜索处方
    SEARCH_NURSE_SERVICE_ORDER,//搜索护理订单
    NURSE_SERVICE_ORDER_UPDATE,//刷新护理订单
    SELECT_PATIENT_RESULT,//选择患者结果
    UN_SELECT_PATIENT_RESULT,//未选中患者
    CONSULTING_UPDATE,//更新门诊排班
    UPDATE_MSG_LIST,//更新消息列表
    PATIENT_UPDATE_USER_LIST,//刷新患者列表
    SENDSELECTNURSINGPROJECT,//发送选中的项目列表
    CHOOSE_MEDICAL_RECORD_TEMPLET,//选择病历记录模版
    MAIN_SUIT_CONTENT,//添加主诉
    CHOOSE_PRESCRIPTION_TEMPLET,//选中处方模版
    CHOOSE_DRUG_LIST,//选中药品列表
    CHOOSE_MATERIAL_USE,//选择药品用法
    GET_ICD_10_VALUE,//搜索ICD－10
    UPDATE_ARTICLE,//刷新文章列表
    UPDATE_NOTIFATION,
    UPDATE_MY_SERVICE,
    UPDATE_SIGN_STATUS,//刷新签名状态
    UPDATEWARRANTLIST,//刷新授权列表
    UPDATENURSENUM,//刷新为接单数据
    UPDATECONSULTATION,//刷新会诊列表
    STARTTOQR//扫一扫
}
