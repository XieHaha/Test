package com.keydom.ih_doctor.activity.nurse_service.view;

import android.widget.TextView;

import com.baidu.mapapi.map.TextureMapView;
import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_doctor.bean.CommonNurseServiceOrderDetailBean;
import com.keydom.ih_doctor.bean.HeadNurseServiceOrderDetailBean;
import com.keydom.ih_doctor.utils.BaiduMapUtil;
import com.keydom.ih_doctor.view.DrivingRouteOverlay;

import java.util.Map;

/**
 * @Name：com.keydom.ih_doctor.fragment.view
 * @Description：描述信息
 * @Author：song
 * @Date：18/11/16 下午2:25
 * 修改人：xusong
 * 修改时间：18/11/16 下午2:25
 */
public interface CommonNurseServiceOrderDetailView extends BaseView {

    /**
     * 获取地图对象
     *
     * @return
     */
    TextureMapView getMapView();

    /**
     * 获取详情页面请求参数
     *
     * @return
     */
    Map<String, Object> getDetailMap();

    /**
     * 获取转单请求参数
     *
     * @return
     */
    Map<String, Object> getTransferNurseMap();

    /**
     * 获取详情成功
     *
     * @param bean 订单详情数据
     */
    void getDetailSuccess(CommonNurseServiceOrderDetailBean bean);

    /**
     * 获取详情失败
     *
     * @param errMsg 失败信息
     */
    void getDetailFailed(String errMsg);

    /**
     * 接收订单成功
     *
     * @param msg 成功信息
     */
    void receiveSuccess(String msg);

    /**
     * 接收订单失败
     *
     * @param errMsg 失败信息
     */
    void receiveFailed(String errMsg);

    /**
     * 出发成功
     *
     * @param msg 成功信息
     */
    void goSuccess(String msg);

    /**
     * 出发失败
     *
     * @param errMsg 失败提示信息
     */
    void goFailed(String errMsg);

    /**
     * 转单成功
     *
     * @param msg 成功信息
     */
    void transferSuccess(String msg);

    /**
     * 转单失败
     *
     * @param errMsg 失败信息
     */
    void transferFailed(String errMsg);

    /**
     * 到达成功
     *
     * @param msg 成功信息
     */
    void arriveSuccess(String msg);

    /**
     * 到达失败
     *
     * @param errMsg 失败信息
     */
    void arriveFailed(String errMsg);

    /**
     * 获取地图覆盖层
     *
     * @return
     */
    DrivingRouteOverlay getOverlay();

    /**
     * 显示／隐藏基本信息
     */
    void showAndHideBaseInfo();

    /**
     * 获取订单ID
     *
     * @return 订单ID
     */
    String getOrderId();

    /**
     * 获取基本信息
     *
     * @return 订单基本信息
     */
    HeadNurseServiceOrderDetailBean getBaseInfo();

    /**
     * 获取距离显示控件
     *
     * @return
     */
    TextView getDistanceTv();

    BaiduMapUtil getMapUtil();


}
