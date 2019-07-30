package com.keydom.ih_doctor.activity.nurse_service.view;

import android.widget.TextView;

import com.baidu.mapapi.map.TextureMapView;
import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_doctor.bean.CommonNurseServiceOrderDetailBean;
import com.keydom.ih_doctor.bean.HeadNurseServiceOrderDetailBean;
import com.keydom.ih_doctor.bean.NursingProjectInfo;
import com.keydom.ih_doctor.utils.BaiduMapUtil;
import com.keydom.ih_doctor.view.DrivingRouteOverlay;

import java.util.List;
import java.util.Map;

/**
 * @Name：com.keydom.ih_doctor.fragment.view
 * @Author：song
 * @Date：18/11/16 下午2:25
 * 修改人：xusong
 * 修改时间：18/11/16 下午2:25
 */
public interface CommonNurseServiceWorkingOrderDetailView extends BaseView {

    /**
     * 获取地图对象
     *
     * @return
     */
    TextureMapView getMapView();

    /**
     * 获取订单详情请求参数
     *
     * @return 获取详情请求参数
     */
    Map<String, Object> getDetailMap();

    /**
     * 成功获取订单详情
     *
     * @param bean 订单详情数据
     */
    void getDetailSuccess(CommonNurseServiceOrderDetailBean bean);

    /**
     * 获取订单详情失败
     *
     * @param errMsg 失败信息
     */
    void getDetailFailed(String errMsg);

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
     * 获取距离显示控件
     *
     * @return
     */
    TextView getDistanceTv();

    /**
     * 添加服务项目成功
     *
     * @param msg
     */
    void addServiceItemSuccess(String msg);

    /**
     * 添加服务项目失败
     *
     * @param errMsg
     */
    void addServiceItemFailed(String errMsg);

    /**
     * 获取最大添加的子项目次数
     *
     * @return
     */
    int getLimit();

    /**
     * 获取选中的子项目
     *
     * @return
     */
    List<NursingProjectInfo> getSelectSubItem();

    BaiduMapUtil getMapUtil();


}
