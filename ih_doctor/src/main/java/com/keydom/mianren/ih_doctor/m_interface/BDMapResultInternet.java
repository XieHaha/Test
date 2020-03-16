package com.keydom.mianren.ih_doctor.m_interface;

import com.baidu.location.BDLocation;

/**
 * @Name：com.keydom.ih_doctor.m_interface
 * @Description：描述信息
 * @Author：song
 * @Date：19/1/3 上午9:53
 * 修改人：xusong
 * 修改时间：19/1/3 上午9:53
 */
public interface BDMapResultInternet {

    /**
     * 定位完成后结果回调
     *
     * @param location
     */
    void getBDLocation(BDLocation location);


}