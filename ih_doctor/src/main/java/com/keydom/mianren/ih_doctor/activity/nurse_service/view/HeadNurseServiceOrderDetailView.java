package com.keydom.mianren.ih_doctor.activity.nurse_service.view;

import android.widget.TextView;

import com.baidu.mapapi.map.TextureMapView;
import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_doctor.bean.HeadNurseServiceOrderDetailBean;
import com.keydom.mianren.ih_doctor.utils.BaiduMapUtil;
import com.keydom.mianren.ih_doctor.view.DrivingRouteOverlay;

import java.util.Map;

/**
 * @Name：com.keydom.ih_doctor.fragment.view
 * @Description：描述信息
 * @Author：song
 * @Date：18/11/16 下午2:25
 * 修改人：xusong
 * 修改时间：18/11/16 下午2:25
 */
public interface HeadNurseServiceOrderDetailView extends BaseView {

    /**
     * 获取订单详情成功
     *
     * @param bean 订单详情数据
     */
    void getDetailSuccess(HeadNurseServiceOrderDetailBean bean);

    /**
     * 获取订单详情失败
     *
     * @param errMsg 失败信息
     */
    void getDetailFailed(String errMsg);

    /**
     * 退回订单成功
     *
     * @param msg 成功信息
     */
    void backOrderSuccess(String msg);

    /**
     * 退回订单失败
     *
     * @param code   失败code
     * @param errMsg 失败信息
     */
    void backOrderFailed(int code, String errMsg);

    /**
     * 接单成功
     *
     * @param msg 成功信息
     */
    void receiveSuccess(String msg);

    /**
     * 接单失败
     *
     * @param code   失败code
     * @param errMsg
     */
    void receiveFailed(int code, String errMsg);

    /**
     * 获取地图对象
     *
     * @return
     */
    TextureMapView getMapView();

    /**
     * 获取详情请求参数
     *
     * @return 请求参数
     */
    Map<String, Object> getDetailMap();

    /**
     * 获取接单请求参数
     *
     * @return 请求参数
     */
    Map<String, Object> getReceiveMap();

    /**
     * 获取订单ID
     *
     * @return 订单ID
     */
    String getOrderNumber();

    /**
     * 获取地图覆盖层
     *
     * @return
     */
    DrivingRouteOverlay getOverlay();

    /**
     * 显示／隐藏基本信息布局
     */
    void showAndHideBaseInfo();

    /**
     * 获取订单基本信息
     *
     * @return
     */
    HeadNurseServiceOrderDetailBean getBaseInfo();

    /**
     * 获取地图距离控件
     *
     * @return
     */
    TextView getDistanceTv();

    BaiduMapUtil getMapUtil();


}
