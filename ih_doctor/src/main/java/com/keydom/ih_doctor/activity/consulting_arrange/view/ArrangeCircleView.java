package com.keydom.ih_doctor.activity.consulting_arrange.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_doctor.bean.ConsultingBean;
import com.keydom.ih_doctor.constant.TypeEnum;

import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.activity.view
 * @Description：门诊排班接口
 * @Author：song
 * @Date：18/11/16 上午9:14
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:14
 */
public interface ArrangeCircleView extends BaseView {


    /**
     * 获取列表数据成功，循环排班和停诊共用这一个回调方法
     *
     * @param type 判断是刷新还是加载更多,
     * @param list 获取到的列表数据
     */
    void getConsultingSuccess(TypeEnum type, List<ConsultingBean> list);

    /**
     * 获取列表失败
     *
     * @param errMsg 失败信息
     */
    void getConsultingFailed(String errMsg);

    /**
     * 获取type  判断是循环排班还是停诊
     *
     * @return
     */
    int getType();


}