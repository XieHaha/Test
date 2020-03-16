package com.keydom.mianren.ih_doctor.activity.nurse_service.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_doctor.bean.MaterialBean;
import com.keydom.mianren.ih_doctor.constant.TypeEnum;

import java.util.List;
import java.util.Map;

/**
 * @Name：com.keydom.ih_doctor.activity.view
 * @Description：描述信息
 * @Author：song
 * @Date：18/11/16 上午9:14
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:14
 */
public interface MaterialChooseView extends BaseView {

    /**
     * 获取耗材请求参数
     *
     * @return 请求参数
     */
    Map<String, Object> getMaterialListMap();

    /**
     * 耗材获取成功
     *
     * @param list 获取到的耗材列表
     * @param type 判断是刷新还是加载更多
     */
    void getMaterialListSuccess(List<MaterialBean> list, TypeEnum type);

    /**
     * 获取耗材失败
     *
     * @param errMsg 失败信息
     */
    void getMaterialListFailed(String errMsg);
}