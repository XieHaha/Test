package com.keydom.mianren.ih_doctor.activity.online_triage.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_doctor.constant.TypeEnum;

import java.util.List;
import java.util.Map;

/**
 * @date 4月2日 15:03
 * 分诊列表
 */
public interface TriageOrderFragmentView extends BaseView {
    /**
     * 获取分诊单成功
     *
     * @param type 刷新／加载更多
     * @param list 问诊单列表
     */
    void getDataSuccess(TypeEnum type, List<String> list);

    /**
     * 获取分诊单失败
     *
     * @param errMsg 失败提示信息
     */
    void getDataFailed(String errMsg);

    /**
     * 获取订单TYPE
     *
     * @return
     */
    TypeEnum getType();

    /**
     * 获取请求参数
     *
     * @return 参数
     */
    Map<String, Object> getParamsMap();
}
