package com.keydom.mianren.ih_doctor.constant;

/**
 * @Name：com.keydom.ih_doctor.constant
 * @Description：枚举，用于区分页面不同状态
 * @Author：song
 * @Date：18/11/21 上午10:18
 * 修改人：xusong
 * 修改时间：18/11/21 上午10:18
 */
public enum TypeEnum {
    MYARTICLE,//我的文章
    OHTERSARTICLE,//别人的文章
    MYCOLLECT,//我的收藏
    LOAD_MORE,//加载更多
    REFRESH,//刷新
    CHECK_NOT_FINISH,//处方审核－未完成
    CHECK_FINISH,//处方审核－已审核
    CHECK_SEND,//处方审核－已配送
    CHECK_TIME_OUT,//处方审核－超时
    CHECK_RETURN,//处方审核－退回
    CHECK_PASS,//处方审核－审核通过
    CHECK_YES_DIALOG,//处方审核－确定
    CHECK_NO_DIALOG,//处方审核－不通过
    CONSULTING_CIRCLE,//门诊排班－循环排班
    CONSULTING_STOP,//门诊排班－停诊
    CONSULTING_UPDATE,//门诊排班－修改循环排班
    FILLOUT_APPLY_GROUP,//转诊－会诊
    FILLOUT_APPLY_CHANGE,//转诊
    DIAGNOSE_CHANGE_DETAIL,//转诊详情
    DIAGNOSE_CHANGE_RECEIVE,//转诊接收
    DIAGNOSE_CHANGE_RECODER,//转诊记录
    DIAGNOSE_GROUP_RECEIVE,//会诊接收
    DIAGNOSE_GROUP_RECODER,//会诊记录
    GROUP_CREATE,//创建团队
    GROUP_UPDATE,//更新团队
    FIRST_FINISH_INFO,
    HEAD_NURSE,//护士长
    COMMON_NURSE,//普通护士
    HEAD_NURSE_FRAGMENT_RECEIVE_ORDER,//护士长未接单
    HEAD_NURSE_FRAGMENT_ALREADY_RECEIVE_ORDER,//护士长未接单
    COMMON_NURSE_FRAGMENT_RECEIVE_ORDER,//普通护士待接收
    COMMON_NURSE_FRAGMNET_WORKING_ORDER,//普通护士服务中
    COMMON_NURSE_FRAGMENT_FINISH_ORDER,//普通护士已完成
    ONLINE_DIAGNOSE_WAITTING,//在线问诊－待接诊
    ONLINE_DIAGNOSEINT,//在线问诊－问诊中
    ONLINE_DIAGNOSE_FINISH,//在线问诊－已完成
    CREATE_INSPECT_ORDER,//创建检查单
    UPDATE_INSPECT_ORDER,//修改检查单
    CREATE_TEST_ORDER,//创建检验单
    UPDATE_TEST_ORDER,//修改检验单
    INSIDE_PRESCRIPTION,//院内处方
    OUTSIDE_PRESCRIPTION,//外延处方
    CONSULTATION_WAIT,//待会诊
    CONSULTATION_ING,//会诊中
    CONSULTATION_COMPLETE,//会诊完成
    TRIAGE_WAIT,//分诊待接收
    TRIAGE_RECEIVED,//分诊已接收
}
