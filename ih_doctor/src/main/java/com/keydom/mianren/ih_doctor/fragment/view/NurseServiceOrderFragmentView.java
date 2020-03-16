package com.keydom.mianren.ih_doctor.fragment.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_doctor.bean.NurseServiceListBean;
import com.keydom.mianren.ih_doctor.constant.TypeEnum;

import java.util.List;
import java.util.Map;

/**
 * @Name：com.keydom.ih_doctor.fragment.view
 * @Description：护理订单列表页面接口
 * @Author：song
 * @Date：18/11/16 下午2:25
 * 修改人：xusong
 * 修改时间：18/11/16 下午2:25
 */
public interface NurseServiceOrderFragmentView extends BaseView {
    /**
     * 获取护理订单成功
     *
     * @param type 刷新／加载更多
     * @param list 护理订单列表
     */
    void getDataSuccess(TypeEnum type, List<NurseServiceListBean> list);

    /**
     * 获取护理订单失败
     *
     * @param errMsg 失败提示信息
     */
    void getDataFailed(String errMsg);

    /**
     * 判断状态
     *
     * @return
     */
    TypeEnum getType();

    /**
     * 获取请求参数
     *
     * @return 获取护理订单的请求参数
     */
    Map<String, Object> getListMap();
}
