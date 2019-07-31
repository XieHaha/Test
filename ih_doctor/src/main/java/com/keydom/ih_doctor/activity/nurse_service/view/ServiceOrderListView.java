package com.keydom.ih_doctor.activity.nurse_service.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_doctor.bean.OrderStatisticBean;
import com.keydom.ih_doctor.constant.TypeEnum;

import java.util.Map;

/**
 * @Name：com.keydom.ih_doctor.fragment.view
 * @Description：描述信息
 * @Author：song
 * @Date：18/11/16 下午2:25
 * 修改人：xusong
 * 修改时间：18/11/16 下午2:25
 */
public interface ServiceOrderListView extends BaseView {

    /**
     * 判断状态
     *
     * @return
     */
    TypeEnum getType();

    /**
     * 获取搜索关键字
     * @return
     */
    String getKeyword();

    void getOrderStatistic(OrderStatisticBean data);

    Map<String,Object> getStatisticMap();
}
