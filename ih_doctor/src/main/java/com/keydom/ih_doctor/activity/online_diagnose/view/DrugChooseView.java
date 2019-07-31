package com.keydom.ih_doctor.activity.online_diagnose.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_doctor.bean.DrugBean;
import com.keydom.ih_doctor.constant.TypeEnum;

import java.util.List;
import java.util.Map;

/**
 * @Name：com.keydom.ih_doctor.activity.view
 * @Description：药品选择页面接口
 * @Author：song
 * @Date：18/11/16 上午9:14
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:14
 */
public interface DrugChooseView extends BaseView {

    /**
     * 获取药品列表请求参数
     *
     * @return
     */
    Map<String, Object> getDrugListMap();

    /**
     * 获取药品列表成功
     *
     * @param list 获取到的药品列表数据
     * @param type 判断是刷新还是加载更多
     */
    void getDrugListSuccess(List<DrugBean> list, TypeEnum type);

    /**
     * 药品列表获取失败
     *
     * @param errMsg 失败提示信息
     */
    void getDrugListFailed(String errMsg);
}