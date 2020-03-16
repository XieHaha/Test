package com.keydom.mianren.ih_doctor.fragment.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_doctor.bean.InquiryBean;
import com.keydom.mianren.ih_doctor.constant.TypeEnum;

import java.util.List;
import java.util.Map;

/**
 * @Name：com.keydom.ih_doctor.fragment.view
 * @Description：描述信息
 * @Author：song
 * @Date：18/11/16 下午2:25
 * 修改人：xusong
 * 修改时间：18/11/16 下午2:25
 */
public interface DiagnoseOrderFragmentView extends BaseView {
    /**
     * 获取问诊单成功
     *
     * @param type 刷新／加载更多
     * @param list 问诊单列表
     */
    void getDataSuccess(TypeEnum type, List<InquiryBean> list);

    /**
     * 获取问诊单失败
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
    Map<String, Object> getListMap();
}
